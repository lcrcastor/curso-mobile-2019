package cz.msebera.android.httpclient.impl.conn;

import cz.msebera.android.httpclient.Consts;
import cz.msebera.android.httpclient.annotation.Immutable;
import cz.msebera.android.httpclient.io.EofSensor;
import cz.msebera.android.httpclient.io.HttpTransportMetrics;
import cz.msebera.android.httpclient.io.SessionInputBuffer;
import cz.msebera.android.httpclient.util.CharArrayBuffer;

@Immutable
@Deprecated
public class LoggingSessionInputBuffer implements EofSensor, SessionInputBuffer {
    private final SessionInputBuffer a;
    private final EofSensor b;
    private final Wire c;
    private final String d;

    public LoggingSessionInputBuffer(SessionInputBuffer sessionInputBuffer, Wire wire, String str) {
        this.a = sessionInputBuffer;
        this.b = sessionInputBuffer instanceof EofSensor ? (EofSensor) sessionInputBuffer : null;
        this.c = wire;
        if (str == null) {
            str = Consts.ASCII.name();
        }
        this.d = str;
    }

    public LoggingSessionInputBuffer(SessionInputBuffer sessionInputBuffer, Wire wire) {
        this(sessionInputBuffer, wire, null);
    }

    public boolean isDataAvailable(int i) {
        return this.a.isDataAvailable(i);
    }

    public int read(byte[] bArr, int i, int i2) {
        int read = this.a.read(bArr, i, i2);
        if (this.c.enabled() && read > 0) {
            this.c.input(bArr, i, read);
        }
        return read;
    }

    public int read() {
        int read = this.a.read();
        if (this.c.enabled() && read != -1) {
            this.c.input(read);
        }
        return read;
    }

    public int read(byte[] bArr) {
        int read = this.a.read(bArr);
        if (this.c.enabled() && read > 0) {
            this.c.input(bArr, 0, read);
        }
        return read;
    }

    public String readLine() {
        String readLine = this.a.readLine();
        if (this.c.enabled() && readLine != null) {
            StringBuilder sb = new StringBuilder();
            sb.append(readLine);
            sb.append("\r\n");
            this.c.input(sb.toString().getBytes(this.d));
        }
        return readLine;
    }

    public int readLine(CharArrayBuffer charArrayBuffer) {
        int readLine = this.a.readLine(charArrayBuffer);
        if (this.c.enabled() && readLine >= 0) {
            String str = new String(charArrayBuffer.buffer(), charArrayBuffer.length() - readLine, readLine);
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append("\r\n");
            this.c.input(sb.toString().getBytes(this.d));
        }
        return readLine;
    }

    public HttpTransportMetrics getMetrics() {
        return this.a.getMetrics();
    }

    public boolean isEof() {
        if (this.b != null) {
            return this.b.isEof();
        }
        return false;
    }
}
