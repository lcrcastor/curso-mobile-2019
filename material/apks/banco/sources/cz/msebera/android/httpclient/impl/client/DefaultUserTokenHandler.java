package cz.msebera.android.httpclient.impl.client;

import cz.msebera.android.httpclient.HttpConnection;
import cz.msebera.android.httpclient.annotation.Immutable;
import cz.msebera.android.httpclient.auth.AuthScheme;
import cz.msebera.android.httpclient.auth.AuthState;
import cz.msebera.android.httpclient.auth.Credentials;
import cz.msebera.android.httpclient.client.UserTokenHandler;
import cz.msebera.android.httpclient.client.protocol.HttpClientContext;
import cz.msebera.android.httpclient.conn.ManagedHttpClientConnection;
import cz.msebera.android.httpclient.protocol.HttpContext;
import java.security.Principal;
import javax.net.ssl.SSLSession;

@Immutable
public class DefaultUserTokenHandler implements UserTokenHandler {
    public static final DefaultUserTokenHandler INSTANCE = new DefaultUserTokenHandler();

    public Object getUserToken(HttpContext httpContext) {
        Principal principal;
        HttpClientContext adapt = HttpClientContext.adapt(httpContext);
        AuthState targetAuthState = adapt.getTargetAuthState();
        if (targetAuthState != null) {
            principal = a(targetAuthState);
            if (principal == null) {
                principal = a(adapt.getProxyAuthState());
            }
        } else {
            principal = null;
        }
        if (principal != null) {
            return principal;
        }
        HttpConnection connection = adapt.getConnection();
        if (!connection.isOpen() || !(connection instanceof ManagedHttpClientConnection)) {
            return principal;
        }
        SSLSession sSLSession = ((ManagedHttpClientConnection) connection).getSSLSession();
        return sSLSession != null ? sSLSession.getLocalPrincipal() : principal;
    }

    private static Principal a(AuthState authState) {
        AuthScheme authScheme = authState.getAuthScheme();
        if (authScheme != null && authScheme.isComplete() && authScheme.isConnectionBased()) {
            Credentials credentials = authState.getCredentials();
            if (credentials != null) {
                return credentials.getUserPrincipal();
            }
        }
        return null;
    }
}
