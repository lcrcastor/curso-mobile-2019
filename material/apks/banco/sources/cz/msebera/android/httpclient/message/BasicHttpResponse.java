package cz.msebera.android.httpclient.message;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.HttpVersion;
import cz.msebera.android.httpclient.ProtocolVersion;
import cz.msebera.android.httpclient.ReasonPhraseCatalog;
import cz.msebera.android.httpclient.StatusLine;
import cz.msebera.android.httpclient.annotation.NotThreadSafe;
import cz.msebera.android.httpclient.util.Args;
import java.util.Locale;

@NotThreadSafe
public class BasicHttpResponse extends AbstractHttpMessage implements HttpResponse {
    private StatusLine a;
    private ProtocolVersion b;
    private int c;
    private String d;
    private HttpEntity e;
    private final ReasonPhraseCatalog f;
    private Locale g;

    public BasicHttpResponse(StatusLine statusLine, ReasonPhraseCatalog reasonPhraseCatalog, Locale locale) {
        this.a = (StatusLine) Args.notNull(statusLine, "Status line");
        this.b = statusLine.getProtocolVersion();
        this.c = statusLine.getStatusCode();
        this.d = statusLine.getReasonPhrase();
        this.f = reasonPhraseCatalog;
        this.g = locale;
    }

    public BasicHttpResponse(StatusLine statusLine) {
        this.a = (StatusLine) Args.notNull(statusLine, "Status line");
        this.b = statusLine.getProtocolVersion();
        this.c = statusLine.getStatusCode();
        this.d = statusLine.getReasonPhrase();
        this.f = null;
        this.g = null;
    }

    public BasicHttpResponse(ProtocolVersion protocolVersion, int i, String str) {
        Args.notNegative(i, "Status code");
        this.a = null;
        this.b = protocolVersion;
        this.c = i;
        this.d = str;
        this.f = null;
        this.g = null;
    }

    public ProtocolVersion getProtocolVersion() {
        return this.b;
    }

    public StatusLine getStatusLine() {
        String str;
        if (this.a == null) {
            ProtocolVersion protocolVersion = this.b != null ? this.b : HttpVersion.HTTP_1_1;
            int i = this.c;
            if (this.d != null) {
                str = this.d;
            } else {
                str = getReason(this.c);
            }
            this.a = new BasicStatusLine(protocolVersion, i, str);
        }
        return this.a;
    }

    public HttpEntity getEntity() {
        return this.e;
    }

    public Locale getLocale() {
        return this.g;
    }

    public void setStatusLine(StatusLine statusLine) {
        this.a = (StatusLine) Args.notNull(statusLine, "Status line");
        this.b = statusLine.getProtocolVersion();
        this.c = statusLine.getStatusCode();
        this.d = statusLine.getReasonPhrase();
    }

    public void setStatusLine(ProtocolVersion protocolVersion, int i) {
        Args.notNegative(i, "Status code");
        this.a = null;
        this.b = protocolVersion;
        this.c = i;
        this.d = null;
    }

    public void setStatusLine(ProtocolVersion protocolVersion, int i, String str) {
        Args.notNegative(i, "Status code");
        this.a = null;
        this.b = protocolVersion;
        this.c = i;
        this.d = str;
    }

    public void setStatusCode(int i) {
        Args.notNegative(i, "Status code");
        this.a = null;
        this.c = i;
        this.d = null;
    }

    public void setReasonPhrase(String str) {
        this.a = null;
        this.d = str;
    }

    public void setEntity(HttpEntity httpEntity) {
        this.e = httpEntity;
    }

    public void setLocale(Locale locale) {
        this.g = (Locale) Args.notNull(locale, "Locale");
        this.a = null;
    }

    /* access modifiers changed from: protected */
    public String getReason(int i) {
        Locale locale;
        if (this.f == null) {
            return null;
        }
        ReasonPhraseCatalog reasonPhraseCatalog = this.f;
        if (this.g != null) {
            locale = this.g;
        } else {
            locale = Locale.getDefault();
        }
        return reasonPhraseCatalog.getReason(i, locale);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getStatusLine());
        sb.append(TokenParser.SP);
        sb.append(this.headergroup);
        if (this.e != null) {
            sb.append(TokenParser.SP);
            sb.append(this.e);
        }
        return sb.toString();
    }
}
