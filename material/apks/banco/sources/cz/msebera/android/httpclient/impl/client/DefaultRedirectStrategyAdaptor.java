package cz.msebera.android.httpclient.impl.client;

import cz.msebera.android.httpclient.HttpRequest;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.annotation.Immutable;
import cz.msebera.android.httpclient.client.RedirectHandler;
import cz.msebera.android.httpclient.client.RedirectStrategy;
import cz.msebera.android.httpclient.client.methods.HttpGet;
import cz.msebera.android.httpclient.client.methods.HttpHead;
import cz.msebera.android.httpclient.client.methods.HttpUriRequest;
import cz.msebera.android.httpclient.protocol.HttpContext;
import java.net.URI;

@Immutable
@Deprecated
class DefaultRedirectStrategyAdaptor implements RedirectStrategy {
    private final RedirectHandler a;

    public DefaultRedirectStrategyAdaptor(RedirectHandler redirectHandler) {
        this.a = redirectHandler;
    }

    public boolean isRedirected(HttpRequest httpRequest, HttpResponse httpResponse, HttpContext httpContext) {
        return this.a.isRedirectRequested(httpResponse, httpContext);
    }

    public HttpUriRequest getRedirect(HttpRequest httpRequest, HttpResponse httpResponse, HttpContext httpContext) {
        URI locationURI = this.a.getLocationURI(httpResponse, httpContext);
        if (httpRequest.getRequestLine().getMethod().equalsIgnoreCase("HEAD")) {
            return new HttpHead(locationURI);
        }
        return new HttpGet(locationURI);
    }

    public RedirectHandler a() {
        return this.a;
    }
}
