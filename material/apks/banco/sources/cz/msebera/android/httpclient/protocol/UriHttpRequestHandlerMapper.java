package cz.msebera.android.httpclient.protocol;

import cz.msebera.android.httpclient.HttpRequest;
import cz.msebera.android.httpclient.annotation.ThreadSafe;
import cz.msebera.android.httpclient.util.Args;

@ThreadSafe
public class UriHttpRequestHandlerMapper implements HttpRequestHandlerMapper {
    private final UriPatternMatcher<HttpRequestHandler> a;

    protected UriHttpRequestHandlerMapper(UriPatternMatcher<HttpRequestHandler> uriPatternMatcher) {
        this.a = (UriPatternMatcher) Args.notNull(uriPatternMatcher, "Pattern matcher");
    }

    public UriHttpRequestHandlerMapper() {
        this(new UriPatternMatcher());
    }

    public void register(String str, HttpRequestHandler httpRequestHandler) {
        Args.notNull(str, "Pattern");
        Args.notNull(httpRequestHandler, "Handler");
        this.a.register(str, httpRequestHandler);
    }

    public void unregister(String str) {
        this.a.unregister(str);
    }

    /* access modifiers changed from: protected */
    public String getRequestPath(HttpRequest httpRequest) {
        String uri = httpRequest.getRequestLine().getUri();
        int indexOf = uri.indexOf("?");
        if (indexOf != -1) {
            return uri.substring(0, indexOf);
        }
        int indexOf2 = uri.indexOf("#");
        return indexOf2 != -1 ? uri.substring(0, indexOf2) : uri;
    }

    public HttpRequestHandler lookup(HttpRequest httpRequest) {
        Args.notNull(httpRequest, "HTTP request");
        return (HttpRequestHandler) this.a.lookup(getRequestPath(httpRequest));
    }
}
