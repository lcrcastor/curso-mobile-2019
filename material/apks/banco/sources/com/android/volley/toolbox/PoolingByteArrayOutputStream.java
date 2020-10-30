package com.android.volley.toolbox;

import java.io.ByteArrayOutputStream;

public class PoolingByteArrayOutputStream extends ByteArrayOutputStream {
    private final ByteArrayPool a;

    public PoolingByteArrayOutputStream(ByteArrayPool byteArrayPool) {
        this(byteArrayPool, 256);
    }

    public PoolingByteArrayOutputStream(ByteArrayPool byteArrayPool, int i) {
        this.a = byteArrayPool;
        this.buf = this.a.getBuf(Math.max(i, 256));
    }

    public void close() {
        this.a.returnBuf(this.buf);
        this.buf = null;
        super.close();
    }

    public void finalize() {
        this.a.returnBuf(this.buf);
    }

    private void a(int i) {
        if (this.count + i > this.buf.length) {
            byte[] buf = this.a.getBuf((this.count + i) * 2);
            System.arraycopy(this.buf, 0, buf, 0, this.count);
            this.a.returnBuf(this.buf);
            this.buf = buf;
        }
    }

    public synchronized void write(byte[] bArr, int i, int i2) {
        a(i2);
        super.write(bArr, i, i2);
    }

    public synchronized void write(int i) {
        a(1);
        super.write(i);
    }
}
