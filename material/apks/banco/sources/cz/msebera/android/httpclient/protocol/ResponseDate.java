package cz.msebera.android.httpclient.protocol;

import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.HttpResponseInterceptor;
import cz.msebera.android.httpclient.annotation.ThreadSafe;
import cz.msebera.android.httpclient.util.Args;

@ThreadSafe
public class ResponseDate implements HttpResponseInterceptor {
    private static final HttpDateGenerator a = new HttpDateGenerator();

    public void process(HttpResponse httpResponse, HttpContext httpContext) {
        Args.notNull(httpResponse, "HTTP response");
        if (httpResponse.getStatusLine().getStatusCode() >= 200 && !httpResponse.containsHeader("Date")) {
            httpResponse.setHeader("Date", a.getCurrentDate());
        }
    }
}
