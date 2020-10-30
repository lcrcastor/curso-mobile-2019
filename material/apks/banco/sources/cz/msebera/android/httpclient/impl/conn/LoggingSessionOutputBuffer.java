package cz.msebera.android.httpclient.impl.conn;

import cz.msebera.android.httpclient.Consts;
import cz.msebera.android.httpclient.annotation.Immutable;
import cz.msebera.android.httpclient.io.HttpTransportMetrics;
import cz.msebera.android.httpclient.io.SessionOutputBuffer;
import cz.msebera.android.httpclient.util.CharArrayBuffer;

@Immutable
@Deprecated
public class LoggingSessionOutputBuffer implements SessionOutputBuffer {
    private final SessionOutputBuffer a;
    private final Wire b;
    private final String c;

    public LoggingSessionOutputBuffer(SessionOutputBuffer sessionOutputBuffer, Wire wire, String str) {
        this.a = sessionOutputBuffer;
        this.b = wire;
        if (str == null) {
            str = Consts.ASCII.name();
        }
        this.c = str;
    }

    public LoggingSessionOutputBuffer(SessionOutputBuffer sessionOutputBuffer, Wire wire) {
        this(sessionOutputBuffer, wire, null);
    }

    public void write(byte[] bArr, int i, int i2) {
        this.a.write(bArr, i, i2);
        if (this.b.enabled()) {
            this.b.output(bArr, i, i2);
        }
    }

    public void write(int i) {
        this.a.write(i);
        if (this.b.enabled()) {
            this.b.output(i);
        }
    }

    public void write(byte[] bArr) {
        this.a.write(bArr);
        if (this.b.enabled()) {
            this.b.output(bArr);
        }
    }

    public void flush() {
        this.a.flush();
    }

    public void writeLine(CharArrayBuffer charArrayBuffer) {
        this.a.writeLine(charArrayBuffer);
        if (this.b.enabled()) {
            String str = new String(charArrayBuffer.buffer(), 0, charArrayBuffer.length());
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append("\r\n");
            this.b.output(sb.toString().getBytes(this.c));
        }
    }

    public void writeLine(String str) {
        this.a.writeLine(str);
        if (this.b.enabled()) {
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append("\r\n");
            this.b.output(sb.toString().getBytes(this.c));
        }
    }

    public HttpTransportMetrics getMetrics() {
        return this.a.getMetrics();
    }
}
