package cz.msebera.android.httpclient.impl.conn;

import cz.msebera.android.httpclient.annotation.NotThreadSafe;
import java.io.IOException;
import java.io.OutputStream;

@NotThreadSafe
class LoggingOutputStream extends OutputStream {
    private final OutputStream a;
    private final Wire b;

    public LoggingOutputStream(OutputStream outputStream, Wire wire) {
        this.a = outputStream;
        this.b = wire;
    }

    public void write(int i) {
        try {
            this.b.output(i);
        } catch (IOException e) {
            Wire wire = this.b;
            StringBuilder sb = new StringBuilder();
            sb.append("[write] I/O error: ");
            sb.append(e.getMessage());
            wire.output(sb.toString());
            throw e;
        }
    }

    public void write(byte[] bArr) {
        try {
            this.b.output(bArr);
            this.a.write(bArr);
        } catch (IOException e) {
            Wire wire = this.b;
            StringBuilder sb = new StringBuilder();
            sb.append("[write] I/O error: ");
            sb.append(e.getMessage());
            wire.output(sb.toString());
            throw e;
        }
    }

    public void write(byte[] bArr, int i, int i2) {
        try {
            this.b.output(bArr, i, i2);
            this.a.write(bArr, i, i2);
        } catch (IOException e) {
            Wire wire = this.b;
            StringBuilder sb = new StringBuilder();
            sb.append("[write] I/O error: ");
            sb.append(e.getMessage());
            wire.output(sb.toString());
            throw e;
        }
    }

    public void flush() {
        try {
            this.a.flush();
        } catch (IOException e) {
            Wire wire = this.b;
            StringBuilder sb = new StringBuilder();
            sb.append("[flush] I/O error: ");
            sb.append(e.getMessage());
            wire.output(sb.toString());
            throw e;
        }
    }

    public void close() {
        try {
            this.a.close();
        } catch (IOException e) {
            Wire wire = this.b;
            StringBuilder sb = new StringBuilder();
            sb.append("[close] I/O error: ");
            sb.append(e.getMessage());
            wire.output(sb.toString());
            throw e;
        }
    }
}
