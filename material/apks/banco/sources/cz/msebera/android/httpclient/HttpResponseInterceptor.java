package cz.msebera.android.httpclient;

import cz.msebera.android.httpclient.protocol.HttpContext;

public interface HttpResponseInterceptor {
    void process(HttpResponse httpResponse, HttpContext httpContext);
}
