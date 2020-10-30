package cz.msebera.android.httpclient.impl.conn;

import cz.msebera.android.httpclient.annotation.NotThreadSafe;
import java.io.IOException;
import java.io.InputStream;

@NotThreadSafe
class LoggingInputStream extends InputStream {
    private final InputStream a;
    private final Wire b;

    public boolean markSupported() {
        return false;
    }

    public LoggingInputStream(InputStream inputStream, Wire wire) {
        this.a = inputStream;
        this.b = wire;
    }

    public int read() {
        try {
            int read = this.a.read();
            if (read == -1) {
                this.b.input("end of stream");
            } else {
                this.b.input(read);
            }
            return read;
        } catch (IOException e) {
            Wire wire = this.b;
            StringBuilder sb = new StringBuilder();
            sb.append("[read] I/O error: ");
            sb.append(e.getMessage());
            wire.input(sb.toString());
            throw e;
        }
    }

    public int read(byte[] bArr) {
        try {
            int read = this.a.read(bArr);
            if (read == -1) {
                this.b.input("end of stream");
            } else if (read > 0) {
                this.b.input(bArr, 0, read);
            }
            return read;
        } catch (IOException e) {
            Wire wire = this.b;
            StringBuilder sb = new StringBuilder();
            sb.append("[read] I/O error: ");
            sb.append(e.getMessage());
            wire.input(sb.toString());
            throw e;
        }
    }

    public int read(byte[] bArr, int i, int i2) {
        try {
            int read = this.a.read(bArr, i, i2);
            if (read == -1) {
                this.b.input("end of stream");
            } else if (read > 0) {
                this.b.input(bArr, i, read);
            }
            return read;
        } catch (IOException e) {
            Wire wire = this.b;
            StringBuilder sb = new StringBuilder();
            sb.append("[read] I/O error: ");
            sb.append(e.getMessage());
            wire.input(sb.toString());
            throw e;
        }
    }

    public long skip(long j) {
        try {
            return super.skip(j);
        } catch (IOException e) {
            Wire wire = this.b;
            StringBuilder sb = new StringBuilder();
            sb.append("[skip] I/O error: ");
            sb.append(e.getMessage());
            wire.input(sb.toString());
            throw e;
        }
    }

    public int available() {
        try {
            return this.a.available();
        } catch (IOException e) {
            Wire wire = this.b;
            StringBuilder sb = new StringBuilder();
            sb.append("[available] I/O error : ");
            sb.append(e.getMessage());
            wire.input(sb.toString());
            throw e;
        }
    }

    public void mark(int i) {
        super.mark(i);
    }

    public void reset() {
        super.reset();
    }

    public void close() {
        try {
            this.a.close();
        } catch (IOException e) {
            Wire wire = this.b;
            StringBuilder sb = new StringBuilder();
            sb.append("[close] I/O error: ");
            sb.append(e.getMessage());
            wire.input(sb.toString());
            throw e;
        }
    }
}
