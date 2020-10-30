package cz.msebera.android.httpclient.client.entity;

import cz.msebera.android.httpclient.annotation.NotThreadSafe;
import java.io.InputStream;

@NotThreadSafe
class LazyDecompressingInputStream extends InputStream {
    private final InputStream a;
    private final InputStreamFactory b;
    private InputStream c;

    public boolean markSupported() {
        return false;
    }

    public LazyDecompressingInputStream(InputStream inputStream, InputStreamFactory inputStreamFactory) {
        this.a = inputStream;
        this.b = inputStreamFactory;
    }

    private void a() {
        if (this.c == null) {
            this.c = this.b.create(this.a);
        }
    }

    public int read() {
        a();
        return this.c.read();
    }

    public int read(byte[] bArr) {
        a();
        return this.c.read(bArr);
    }

    public int read(byte[] bArr, int i, int i2) {
        a();
        return this.c.read(bArr, i, i2);
    }

    public long skip(long j) {
        a();
        return this.c.skip(j);
    }

    public int available() {
        a();
        return this.c.available();
    }

    public void close() {
        try {
            if (this.c != null) {
                this.c.close();
            }
        } finally {
            this.a.close();
        }
    }
}
