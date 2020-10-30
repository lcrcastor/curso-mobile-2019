package cz.msebera.android.httpclient.client.methods;

import ar.com.santander.rio.mbanking.utils.UtilsCuentas;
import cz.msebera.android.httpclient.ProtocolVersion;
import cz.msebera.android.httpclient.RequestLine;
import cz.msebera.android.httpclient.annotation.NotThreadSafe;
import cz.msebera.android.httpclient.client.config.RequestConfig;
import cz.msebera.android.httpclient.message.BasicRequestLine;
import cz.msebera.android.httpclient.params.HttpProtocolParams;
import java.net.URI;

@NotThreadSafe
public abstract class HttpRequestBase extends AbstractExecutionAwareRequest implements Configurable, HttpUriRequest {
    private ProtocolVersion a;
    private URI b;
    private RequestConfig c;

    public abstract String getMethod();

    public void started() {
    }

    public void setProtocolVersion(ProtocolVersion protocolVersion) {
        this.a = protocolVersion;
    }

    public ProtocolVersion getProtocolVersion() {
        return this.a != null ? this.a : HttpProtocolParams.getVersion(getParams());
    }

    public URI getURI() {
        return this.b;
    }

    public RequestLine getRequestLine() {
        String method = getMethod();
        ProtocolVersion protocolVersion = getProtocolVersion();
        URI uri = getURI();
        String aSCIIString = uri != null ? uri.toASCIIString() : null;
        if (aSCIIString == null || aSCIIString.isEmpty()) {
            aSCIIString = "/";
        }
        return new BasicRequestLine(method, aSCIIString, protocolVersion);
    }

    public RequestConfig getConfig() {
        return this.c;
    }

    public void setConfig(RequestConfig requestConfig) {
        this.c = requestConfig;
    }

    public void setURI(URI uri) {
        this.b = uri;
    }

    public void releaseConnection() {
        reset();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getMethod());
        sb.append(UtilsCuentas.SEPARAOR2);
        sb.append(getURI());
        sb.append(UtilsCuentas.SEPARAOR2);
        sb.append(getProtocolVersion());
        return sb.toString();
    }
}
