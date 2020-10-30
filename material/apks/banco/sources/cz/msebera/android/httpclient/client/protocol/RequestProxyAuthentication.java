package cz.msebera.android.httpclient.client.protocol;

import cz.msebera.android.httpclient.HttpRequest;
import cz.msebera.android.httpclient.annotation.Immutable;
import cz.msebera.android.httpclient.auth.AuthState;
import cz.msebera.android.httpclient.conn.HttpRoutedConnection;
import cz.msebera.android.httpclient.extras.HttpClientAndroidLog;
import cz.msebera.android.httpclient.protocol.HttpContext;
import cz.msebera.android.httpclient.util.Args;

@Immutable
@Deprecated
public class RequestProxyAuthentication extends RequestAuthenticationBase {
    public void process(HttpRequest httpRequest, HttpContext httpContext) {
        Args.notNull(httpRequest, "HTTP request");
        Args.notNull(httpContext, "HTTP context");
        if (!httpRequest.containsHeader("Proxy-Authorization")) {
            HttpRoutedConnection httpRoutedConnection = (HttpRoutedConnection) httpContext.getAttribute("http.connection");
            if (httpRoutedConnection == null) {
                this.a.debug("HTTP connection not set in the context");
            } else if (!httpRoutedConnection.getRoute().isTunnelled()) {
                AuthState authState = (AuthState) httpContext.getAttribute("http.auth.proxy-scope");
                if (authState == null) {
                    this.a.debug("Proxy auth state not set in the context");
                    return;
                }
                if (this.a.isDebugEnabled()) {
                    HttpClientAndroidLog httpClientAndroidLog = this.a;
                    StringBuilder sb = new StringBuilder();
                    sb.append("Proxy auth state: ");
                    sb.append(authState.getState());
                    httpClientAndroidLog.debug(sb.toString());
                }
                a(authState, httpRequest, httpContext);
            }
        }
    }
}
