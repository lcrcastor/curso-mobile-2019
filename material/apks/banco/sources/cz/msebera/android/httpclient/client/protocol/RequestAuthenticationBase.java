package cz.msebera.android.httpclient.client.protocol;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpRequest;
import cz.msebera.android.httpclient.HttpRequestInterceptor;
import cz.msebera.android.httpclient.auth.AuthOption;
import cz.msebera.android.httpclient.auth.AuthScheme;
import cz.msebera.android.httpclient.auth.AuthState;
import cz.msebera.android.httpclient.auth.AuthenticationException;
import cz.msebera.android.httpclient.auth.ContextAwareAuthScheme;
import cz.msebera.android.httpclient.auth.Credentials;
import cz.msebera.android.httpclient.extras.HttpClientAndroidLog;
import cz.msebera.android.httpclient.protocol.HttpContext;
import cz.msebera.android.httpclient.util.Asserts;
import java.util.Queue;

@Deprecated
abstract class RequestAuthenticationBase implements HttpRequestInterceptor {
    final HttpClientAndroidLog a = new HttpClientAndroidLog(getClass());

    /* access modifiers changed from: 0000 */
    public void a(AuthState authState, HttpRequest httpRequest, HttpContext httpContext) {
        AuthScheme authScheme = authState.getAuthScheme();
        Credentials credentials = authState.getCredentials();
        switch (authState.getState()) {
            case FAILURE:
                return;
            case SUCCESS:
                a(authScheme);
                if (authScheme.isConnectionBased()) {
                    return;
                }
                break;
            case CHALLENGED:
                Queue authOptions = authState.getAuthOptions();
                if (authOptions == null) {
                    a(authScheme);
                    break;
                } else {
                    while (!authOptions.isEmpty()) {
                        AuthOption authOption = (AuthOption) authOptions.remove();
                        AuthScheme authScheme2 = authOption.getAuthScheme();
                        Credentials credentials2 = authOption.getCredentials();
                        authState.update(authScheme2, credentials2);
                        if (this.a.isDebugEnabled()) {
                            HttpClientAndroidLog httpClientAndroidLog = this.a;
                            StringBuilder sb = new StringBuilder();
                            sb.append("Generating response to an authentication challenge using ");
                            sb.append(authScheme2.getSchemeName());
                            sb.append(" scheme");
                            httpClientAndroidLog.debug(sb.toString());
                        }
                        try {
                            httpRequest.addHeader(a(authScheme2, credentials2, httpRequest, httpContext));
                            return;
                        } catch (AuthenticationException e) {
                            if (this.a.isWarnEnabled()) {
                                HttpClientAndroidLog httpClientAndroidLog2 = this.a;
                                StringBuilder sb2 = new StringBuilder();
                                sb2.append(authScheme2);
                                sb2.append(" authentication error: ");
                                sb2.append(e.getMessage());
                                httpClientAndroidLog2.warn(sb2.toString());
                            }
                        }
                    }
                    return;
                }
        }
        if (authScheme != null) {
            try {
                httpRequest.addHeader(a(authScheme, credentials, httpRequest, httpContext));
            } catch (AuthenticationException e2) {
                if (this.a.isErrorEnabled()) {
                    HttpClientAndroidLog httpClientAndroidLog3 = this.a;
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append(authScheme);
                    sb3.append(" authentication error: ");
                    sb3.append(e2.getMessage());
                    httpClientAndroidLog3.error(sb3.toString());
                }
            }
        }
    }

    private void a(AuthScheme authScheme) {
        Asserts.notNull(authScheme, "Auth scheme");
    }

    private Header a(AuthScheme authScheme, Credentials credentials, HttpRequest httpRequest, HttpContext httpContext) {
        Asserts.notNull(authScheme, "Auth scheme");
        if (authScheme instanceof ContextAwareAuthScheme) {
            return ((ContextAwareAuthScheme) authScheme).authenticate(credentials, httpRequest, httpContext);
        }
        return authScheme.authenticate(credentials, httpRequest);
    }
}
