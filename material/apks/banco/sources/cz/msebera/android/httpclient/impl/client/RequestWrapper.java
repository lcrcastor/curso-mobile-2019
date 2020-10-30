package cz.msebera.android.httpclient.impl.client;

import cz.msebera.android.httpclient.HttpRequest;
import cz.msebera.android.httpclient.ProtocolException;
import cz.msebera.android.httpclient.ProtocolVersion;
import cz.msebera.android.httpclient.RequestLine;
import cz.msebera.android.httpclient.annotation.NotThreadSafe;
import cz.msebera.android.httpclient.client.methods.HttpUriRequest;
import cz.msebera.android.httpclient.message.AbstractHttpMessage;
import cz.msebera.android.httpclient.message.BasicRequestLine;
import cz.msebera.android.httpclient.params.HttpProtocolParams;
import cz.msebera.android.httpclient.util.Args;
import java.net.URI;
import java.net.URISyntaxException;

@NotThreadSafe
@Deprecated
public class RequestWrapper extends AbstractHttpMessage implements HttpUriRequest {
    private final HttpRequest a;
    private URI b;
    private String c;
    private ProtocolVersion d;
    private int e;

    public boolean isAborted() {
        return false;
    }

    public boolean isRepeatable() {
        return true;
    }

    public RequestWrapper(HttpRequest httpRequest) {
        Args.notNull(httpRequest, "HTTP request");
        this.a = httpRequest;
        setParams(httpRequest.getParams());
        setHeaders(httpRequest.getAllHeaders());
        if (httpRequest instanceof HttpUriRequest) {
            HttpUriRequest httpUriRequest = (HttpUriRequest) httpRequest;
            this.b = httpUriRequest.getURI();
            this.c = httpUriRequest.getMethod();
            this.d = null;
        } else {
            RequestLine requestLine = httpRequest.getRequestLine();
            try {
                this.b = new URI(requestLine.getUri());
                this.c = requestLine.getMethod();
                this.d = httpRequest.getProtocolVersion();
            } catch (URISyntaxException e2) {
                StringBuilder sb = new StringBuilder();
                sb.append("Invalid request URI: ");
                sb.append(requestLine.getUri());
                throw new ProtocolException(sb.toString(), e2);
            }
        }
        this.e = 0;
    }

    public void resetHeaders() {
        this.headergroup.clear();
        setHeaders(this.a.getAllHeaders());
    }

    public String getMethod() {
        return this.c;
    }

    public void setMethod(String str) {
        Args.notNull(str, "Method name");
        this.c = str;
    }

    public ProtocolVersion getProtocolVersion() {
        if (this.d == null) {
            this.d = HttpProtocolParams.getVersion(getParams());
        }
        return this.d;
    }

    public void setProtocolVersion(ProtocolVersion protocolVersion) {
        this.d = protocolVersion;
    }

    public URI getURI() {
        return this.b;
    }

    public void setURI(URI uri) {
        this.b = uri;
    }

    public RequestLine getRequestLine() {
        ProtocolVersion protocolVersion = getProtocolVersion();
        String aSCIIString = this.b != null ? this.b.toASCIIString() : null;
        if (aSCIIString == null || aSCIIString.isEmpty()) {
            aSCIIString = "/";
        }
        return new BasicRequestLine(getMethod(), aSCIIString, protocolVersion);
    }

    public void abort() {
        throw new UnsupportedOperationException();
    }

    public HttpRequest getOriginal() {
        return this.a;
    }

    public int getExecCount() {
        return this.e;
    }

    public void incrementExecCount() {
        this.e++;
    }
}
