package cz.msebera.android.httpclient.impl.conn;

import ar.com.santander.rio.mbanking.utils.UtilsCuentas;
import cz.msebera.android.httpclient.annotation.Immutable;
import cz.msebera.android.httpclient.extras.HttpClientAndroidLog;
import cz.msebera.android.httpclient.message.TokenParser;
import cz.msebera.android.httpclient.util.Args;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

@Immutable
public class Wire {
    private final String a;
    public HttpClientAndroidLog log;

    public Wire(HttpClientAndroidLog httpClientAndroidLog, String str) {
        this.log = httpClientAndroidLog;
        this.a = str;
    }

    public Wire(HttpClientAndroidLog httpClientAndroidLog) {
        this(httpClientAndroidLog, "");
    }

    private void a(String str, InputStream inputStream) {
        StringBuilder sb = new StringBuilder();
        while (true) {
            int read = inputStream.read();
            if (read == -1) {
                break;
            } else if (read == 13) {
                sb.append("[\\r]");
            } else if (read == 10) {
                sb.append("[\\n]\"");
                sb.insert(0, "\"");
                sb.insert(0, str);
                HttpClientAndroidLog httpClientAndroidLog = this.log;
                StringBuilder sb2 = new StringBuilder();
                sb2.append(this.a);
                sb2.append(UtilsCuentas.SEPARAOR2);
                sb2.append(sb.toString());
                httpClientAndroidLog.debug(sb2.toString());
                sb.setLength(0);
            } else if (read < 32 || read > 127) {
                sb.append("[0x");
                sb.append(Integer.toHexString(read));
                sb.append("]");
            } else {
                sb.append((char) read);
            }
        }
        if (sb.length() > 0) {
            sb.append(TokenParser.DQUOTE);
            sb.insert(0, TokenParser.DQUOTE);
            sb.insert(0, str);
            HttpClientAndroidLog httpClientAndroidLog2 = this.log;
            StringBuilder sb3 = new StringBuilder();
            sb3.append(this.a);
            sb3.append(UtilsCuentas.SEPARAOR2);
            sb3.append(sb.toString());
            httpClientAndroidLog2.debug(sb3.toString());
        }
    }

    public boolean enabled() {
        return this.log.isDebugEnabled();
    }

    public void output(InputStream inputStream) {
        Args.notNull(inputStream, "Output");
        a(">> ", inputStream);
    }

    public void input(InputStream inputStream) {
        Args.notNull(inputStream, "Input");
        a("<< ", inputStream);
    }

    public void output(byte[] bArr, int i, int i2) {
        Args.notNull(bArr, "Output");
        a(">> ", new ByteArrayInputStream(bArr, i, i2));
    }

    public void input(byte[] bArr, int i, int i2) {
        Args.notNull(bArr, "Input");
        a("<< ", new ByteArrayInputStream(bArr, i, i2));
    }

    public void output(byte[] bArr) {
        Args.notNull(bArr, "Output");
        a(">> ", new ByteArrayInputStream(bArr));
    }

    public void input(byte[] bArr) {
        Args.notNull(bArr, "Input");
        a("<< ", new ByteArrayInputStream(bArr));
    }

    public void output(int i) {
        output(new byte[]{(byte) i});
    }

    public void input(int i) {
        input(new byte[]{(byte) i});
    }

    public void output(String str) {
        Args.notNull(str, "Output");
        output(str.getBytes());
    }

    public void input(String str) {
        Args.notNull(str, "Input");
        input(str.getBytes());
    }
}
