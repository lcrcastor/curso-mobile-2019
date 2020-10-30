package cz.msebera.android.httpclient.client.protocol;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpRequest;
import cz.msebera.android.httpclient.HttpRequestInterceptor;
import cz.msebera.android.httpclient.annotation.Immutable;
import cz.msebera.android.httpclient.client.params.ClientPNames;
import cz.msebera.android.httpclient.protocol.HttpContext;
import cz.msebera.android.httpclient.util.Args;
import java.util.Collection;

@Immutable
public class RequestDefaultHeaders implements HttpRequestInterceptor {
    private final Collection<? extends Header> a;

    public RequestDefaultHeaders(Collection<? extends Header> collection) {
        this.a = collection;
    }

    public RequestDefaultHeaders() {
        this(null);
    }

    public void process(HttpRequest httpRequest, HttpContext httpContext) {
        Args.notNull(httpRequest, "HTTP request");
        if (!httpRequest.getRequestLine().getMethod().equalsIgnoreCase("CONNECT")) {
            Collection<? extends Header> collection = (Collection) httpRequest.getParams().getParameter(ClientPNames.DEFAULT_HEADERS);
            if (collection == null) {
                collection = this.a;
            }
            if (collection != null) {
                for (Header addHeader : collection) {
                    httpRequest.addHeader(addHeader);
                }
            }
        }
    }
}
