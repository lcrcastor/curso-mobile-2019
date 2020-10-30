package cz.msebera.android.httpclient.impl.io;

import cz.msebera.android.httpclient.annotation.NotThreadSafe;
import cz.msebera.android.httpclient.io.SessionOutputBuffer;
import java.io.IOException;
import java.io.OutputStream;

@NotThreadSafe
public class ChunkedOutputStream extends OutputStream {
    private final SessionOutputBuffer a;
    private final byte[] b;
    private int c;
    private boolean d;
    private boolean e;

    @Deprecated
    public ChunkedOutputStream(SessionOutputBuffer sessionOutputBuffer, int i) {
        this(i, sessionOutputBuffer);
    }

    @Deprecated
    public ChunkedOutputStream(SessionOutputBuffer sessionOutputBuffer) {
        this(2048, sessionOutputBuffer);
    }

    public ChunkedOutputStream(int i, SessionOutputBuffer sessionOutputBuffer) {
        this.c = 0;
        this.d = false;
        this.e = false;
        this.b = new byte[i];
        this.a = sessionOutputBuffer;
    }

    /* access modifiers changed from: protected */
    public void flushCache() {
        if (this.c > 0) {
            this.a.writeLine(Integer.toHexString(this.c));
            this.a.write(this.b, 0, this.c);
            this.a.writeLine("");
            this.c = 0;
        }
    }

    /* access modifiers changed from: protected */
    public void flushCacheWithAppend(byte[] bArr, int i, int i2) {
        this.a.writeLine(Integer.toHexString(this.c + i2));
        this.a.write(this.b, 0, this.c);
        this.a.write(bArr, i, i2);
        this.a.writeLine("");
        this.c = 0;
    }

    /* access modifiers changed from: protected */
    public void writeClosingChunk() {
        this.a.writeLine("0");
        this.a.writeLine("");
    }

    public void finish() {
        if (!this.d) {
            flushCache();
            writeClosingChunk();
            this.d = true;
        }
    }

    public void write(int i) {
        if (this.e) {
            throw new IOException("Attempted write to closed stream.");
        }
        this.b[this.c] = (byte) i;
        this.c++;
        if (this.c == this.b.length) {
            flushCache();
        }
    }

    public void write(byte[] bArr) {
        write(bArr, 0, bArr.length);
    }

    public void write(byte[] bArr, int i, int i2) {
        if (this.e) {
            throw new IOException("Attempted write to closed stream.");
        } else if (i2 >= this.b.length - this.c) {
            flushCacheWithAppend(bArr, i, i2);
        } else {
            System.arraycopy(bArr, i, this.b, this.c, i2);
            this.c += i2;
        }
    }

    public void flush() {
        flushCache();
        this.a.flush();
    }

    public void close() {
        if (!this.e) {
            this.e = true;
            finish();
            this.a.flush();
        }
    }
}
