package cz.msebera.android.httpclient;

import cz.msebera.android.httpclient.protocol.HttpContext;

public interface HttpRequestInterceptor {
    void process(HttpRequest httpRequest, HttpContext httpContext);
}
