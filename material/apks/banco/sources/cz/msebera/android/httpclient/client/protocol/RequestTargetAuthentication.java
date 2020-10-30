package cz.msebera.android.httpclient.client.protocol;

import cz.msebera.android.httpclient.HttpRequest;
import cz.msebera.android.httpclient.annotation.Immutable;
import cz.msebera.android.httpclient.auth.AuthState;
import cz.msebera.android.httpclient.extras.HttpClientAndroidLog;
import cz.msebera.android.httpclient.protocol.HttpContext;
import cz.msebera.android.httpclient.util.Args;

@Immutable
@Deprecated
public class RequestTargetAuthentication extends RequestAuthenticationBase {
    public void process(HttpRequest httpRequest, HttpContext httpContext) {
        Args.notNull(httpRequest, "HTTP request");
        Args.notNull(httpContext, "HTTP context");
        if (!httpRequest.getRequestLine().getMethod().equalsIgnoreCase("CONNECT") && !httpRequest.containsHeader("Authorization")) {
            AuthState authState = (AuthState) httpContext.getAttribute("http.auth.target-scope");
            if (authState == null) {
                this.a.debug("Target auth state not set in the context");
                return;
            }
            if (this.a.isDebugEnabled()) {
                HttpClientAndroidLog httpClientAndroidLog = this.a;
                StringBuilder sb = new StringBuilder();
                sb.append("Target auth state: ");
                sb.append(authState.getState());
                httpClientAndroidLog.debug(sb.toString());
            }
            a(authState, httpRequest, httpContext);
        }
    }
}
