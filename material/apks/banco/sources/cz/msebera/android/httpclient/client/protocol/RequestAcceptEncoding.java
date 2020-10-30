package cz.msebera.android.httpclient.client.protocol;

import cz.msebera.android.httpclient.HttpRequest;
import cz.msebera.android.httpclient.HttpRequestInterceptor;
import cz.msebera.android.httpclient.annotation.Immutable;
import cz.msebera.android.httpclient.protocol.HttpContext;
import java.util.List;

@Immutable
public class RequestAcceptEncoding implements HttpRequestInterceptor {
    private final String a;

    public RequestAcceptEncoding(List<String> list) {
        if (list == null || list.isEmpty()) {
            this.a = "gzip,deflate";
            return;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            if (i > 0) {
                sb.append(",");
            }
            sb.append((String) list.get(i));
        }
        this.a = sb.toString();
    }

    public RequestAcceptEncoding() {
        this(null);
    }

    public void process(HttpRequest httpRequest, HttpContext httpContext) {
        if (!httpRequest.containsHeader("Accept-Encoding")) {
            httpRequest.addHeader("Accept-Encoding", this.a);
        }
    }
}
