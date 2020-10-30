package cz.msebera.android.httpclient.client.methods;

import ar.com.santander.rio.mbanking.utils.UtilsCuentas;
import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpEntityEnclosingRequest;
import cz.msebera.android.httpclient.HttpHost;
import cz.msebera.android.httpclient.HttpRequest;
import cz.msebera.android.httpclient.ProtocolVersion;
import cz.msebera.android.httpclient.RequestLine;
import cz.msebera.android.httpclient.annotation.NotThreadSafe;
import cz.msebera.android.httpclient.message.AbstractHttpMessage;
import cz.msebera.android.httpclient.message.BasicRequestLine;
import cz.msebera.android.httpclient.params.HttpParams;
import cz.msebera.android.httpclient.protocol.HTTP;
import cz.msebera.android.httpclient.util.Args;
import java.net.URI;

@NotThreadSafe
public class HttpRequestWrapper extends AbstractHttpMessage implements HttpUriRequest {
    private final HttpRequest a;
    private final HttpHost b;
    private final String c;
    private ProtocolVersion d;
    private URI e;

    static class HttpEntityEnclosingRequestWrapper extends HttpRequestWrapper implements HttpEntityEnclosingRequest {
        private HttpEntity a;

        HttpEntityEnclosingRequestWrapper(HttpEntityEnclosingRequest httpEntityEnclosingRequest, HttpHost httpHost) {
            super(httpEntityEnclosingRequest, httpHost);
            this.a = httpEntityEnclosingRequest.getEntity();
        }

        public HttpEntity getEntity() {
            return this.a;
        }

        public void setEntity(HttpEntity httpEntity) {
            this.a = httpEntity;
        }

        public boolean expectContinue() {
            Header firstHeader = getFirstHeader("Expect");
            return firstHeader != null && HTTP.EXPECT_CONTINUE.equalsIgnoreCase(firstHeader.getValue());
        }
    }

    public boolean isAborted() {
        return false;
    }

    private HttpRequestWrapper(HttpRequest httpRequest, HttpHost httpHost) {
        this.a = (HttpRequest) Args.notNull(httpRequest, "HTTP request");
        this.b = httpHost;
        this.d = this.a.getRequestLine().getProtocolVersion();
        this.c = this.a.getRequestLine().getMethod();
        if (httpRequest instanceof HttpUriRequest) {
            this.e = ((HttpUriRequest) httpRequest).getURI();
        } else {
            this.e = null;
        }
        setHeaders(httpRequest.getAllHeaders());
    }

    public ProtocolVersion getProtocolVersion() {
        return this.d != null ? this.d : this.a.getProtocolVersion();
    }

    public void setProtocolVersion(ProtocolVersion protocolVersion) {
        this.d = protocolVersion;
    }

    public URI getURI() {
        return this.e;
    }

    public void setURI(URI uri) {
        this.e = uri;
    }

    public String getMethod() {
        return this.c;
    }

    public void abort() {
        throw new UnsupportedOperationException();
    }

    public RequestLine getRequestLine() {
        String str;
        if (this.e != null) {
            str = this.e.toASCIIString();
        } else {
            str = this.a.getRequestLine().getUri();
        }
        if (str == null || str.isEmpty()) {
            str = "/";
        }
        return new BasicRequestLine(this.c, str, getProtocolVersion());
    }

    public HttpRequest getOriginal() {
        return this.a;
    }

    public HttpHost getTarget() {
        return this.b;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getRequestLine());
        sb.append(UtilsCuentas.SEPARAOR2);
        sb.append(this.headergroup);
        return sb.toString();
    }

    public static HttpRequestWrapper wrap(HttpRequest httpRequest) {
        return wrap(httpRequest, null);
    }

    public static HttpRequestWrapper wrap(HttpRequest httpRequest, HttpHost httpHost) {
        Args.notNull(httpRequest, "HTTP request");
        if (httpRequest instanceof HttpEntityEnclosingRequest) {
            return new HttpEntityEnclosingRequestWrapper((HttpEntityEnclosingRequest) httpRequest, httpHost);
        }
        return new HttpRequestWrapper(httpRequest, httpHost);
    }

    @Deprecated
    public HttpParams getParams() {
        if (this.params == null) {
            this.params = this.a.getParams().copy();
        }
        return this.params;
    }
}
