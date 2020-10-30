package cz.msebera.android.httpclient.protocol;

import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.HttpResponseInterceptor;
import cz.msebera.android.httpclient.annotation.Immutable;
import cz.msebera.android.httpclient.util.Args;

@Immutable
public class ResponseServer implements HttpResponseInterceptor {
    private final String a;

    public ResponseServer(String str) {
        this.a = str;
    }

    public ResponseServer() {
        this(null);
    }

    public void process(HttpResponse httpResponse, HttpContext httpContext) {
        Args.notNull(httpResponse, "HTTP response");
        if (!httpResponse.containsHeader("Server") && this.a != null) {
            httpResponse.addHeader("Server", this.a);
        }
    }
}
