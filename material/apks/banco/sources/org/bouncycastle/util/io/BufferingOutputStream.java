package org.bouncycastle.util.io;

import java.io.OutputStream;
import org.bouncycastle.util.Arrays;

public class BufferingOutputStream extends OutputStream {
    private final OutputStream a;
    private final byte[] b;
    private int c;

    public BufferingOutputStream(OutputStream outputStream) {
        this.a = outputStream;
        this.b = new byte[4096];
    }

    public BufferingOutputStream(OutputStream outputStream, int i) {
        this.a = outputStream;
        this.b = new byte[i];
    }

    public void close() {
        flush();
        this.a.close();
    }

    public void flush() {
        this.a.write(this.b, 0, this.c);
        this.c = 0;
        Arrays.fill(this.b, 0);
    }

    public void write(int i) {
        byte[] bArr = this.b;
        int i2 = this.c;
        this.c = i2 + 1;
        bArr[i2] = (byte) i;
        if (this.c == this.b.length) {
            flush();
        }
    }

    public void write(byte[] bArr, int i, int i2) {
        if (i2 >= this.b.length - this.c) {
            int length = this.b.length - this.c;
            System.arraycopy(bArr, i, this.b, this.c, length);
            this.c += length;
            flush();
            i += length;
            while (true) {
                i2 -= length;
                if (i2 < this.b.length) {
                    break;
                }
                this.a.write(bArr, i, this.b.length);
                i += this.b.length;
                length = this.b.length;
            }
            if (i2 <= 0) {
                return;
            }
        }
        System.arraycopy(bArr, i, this.b, this.c, i2);
        this.c += i2;
    }
}
