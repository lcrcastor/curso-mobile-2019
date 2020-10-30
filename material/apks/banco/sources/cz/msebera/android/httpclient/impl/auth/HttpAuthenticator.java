package cz.msebera.android.httpclient.impl.auth;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpHost;
import cz.msebera.android.httpclient.HttpRequest;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.auth.AuthOption;
import cz.msebera.android.httpclient.auth.AuthProtocolState;
import cz.msebera.android.httpclient.auth.AuthScheme;
import cz.msebera.android.httpclient.auth.AuthState;
import cz.msebera.android.httpclient.auth.AuthenticationException;
import cz.msebera.android.httpclient.auth.ContextAwareAuthScheme;
import cz.msebera.android.httpclient.auth.Credentials;
import cz.msebera.android.httpclient.auth.MalformedChallengeException;
import cz.msebera.android.httpclient.client.AuthenticationStrategy;
import cz.msebera.android.httpclient.extras.HttpClientAndroidLog;
import cz.msebera.android.httpclient.protocol.HttpContext;
import cz.msebera.android.httpclient.util.Asserts;
import java.util.Locale;
import java.util.Map;
import java.util.Queue;

public class HttpAuthenticator {
    public HttpClientAndroidLog log;

    public HttpAuthenticator(HttpClientAndroidLog httpClientAndroidLog) {
        if (httpClientAndroidLog == null) {
            httpClientAndroidLog = new HttpClientAndroidLog(getClass());
        }
        this.log = httpClientAndroidLog;
    }

    public HttpAuthenticator() {
        this(null);
    }

    public boolean isAuthenticationRequested(HttpHost httpHost, HttpResponse httpResponse, AuthenticationStrategy authenticationStrategy, AuthState authState, HttpContext httpContext) {
        if (authenticationStrategy.isAuthenticationRequested(httpHost, httpResponse, httpContext)) {
            this.log.debug("Authentication required");
            if (authState.getState() == AuthProtocolState.SUCCESS) {
                authenticationStrategy.authFailed(httpHost, authState.getAuthScheme(), httpContext);
            }
            return true;
        }
        switch (authState.getState()) {
            case CHALLENGED:
            case HANDSHAKE:
                this.log.debug("Authentication succeeded");
                authState.setState(AuthProtocolState.SUCCESS);
                authenticationStrategy.authSucceeded(httpHost, authState.getAuthScheme(), httpContext);
                break;
            case SUCCESS:
                break;
            default:
                authState.setState(AuthProtocolState.UNCHALLENGED);
                break;
        }
        return false;
    }

    public boolean handleAuthChallenge(HttpHost httpHost, HttpResponse httpResponse, AuthenticationStrategy authenticationStrategy, AuthState authState, HttpContext httpContext) {
        try {
            if (this.log.isDebugEnabled()) {
                HttpClientAndroidLog httpClientAndroidLog = this.log;
                StringBuilder sb = new StringBuilder();
                sb.append(httpHost.toHostString());
                sb.append(" requested authentication");
                httpClientAndroidLog.debug(sb.toString());
            }
            Map challenges = authenticationStrategy.getChallenges(httpHost, httpResponse, httpContext);
            if (challenges.isEmpty()) {
                this.log.debug("Response contains no authentication challenges");
                return false;
            }
            AuthScheme authScheme = authState.getAuthScheme();
            switch (authState.getState()) {
                case CHALLENGED:
                case HANDSHAKE:
                    if (authScheme == null) {
                        this.log.debug("Auth scheme is null");
                        authenticationStrategy.authFailed(httpHost, null, httpContext);
                        authState.reset();
                        authState.setState(AuthProtocolState.FAILURE);
                        return false;
                    }
                    break;
                case SUCCESS:
                    authState.reset();
                    break;
                case FAILURE:
                    return false;
                case UNCHALLENGED:
                    break;
            }
            if (authScheme != null) {
                Header header = (Header) challenges.get(authScheme.getSchemeName().toLowerCase(Locale.ROOT));
                if (header != null) {
                    this.log.debug("Authorization challenge processed");
                    authScheme.processChallenge(header);
                    if (authScheme.isComplete()) {
                        this.log.debug("Authentication failed");
                        authenticationStrategy.authFailed(httpHost, authState.getAuthScheme(), httpContext);
                        authState.reset();
                        authState.setState(AuthProtocolState.FAILURE);
                        return false;
                    }
                    authState.setState(AuthProtocolState.HANDSHAKE);
                    return true;
                }
                authState.reset();
            }
            Queue select = authenticationStrategy.select(challenges, httpHost, httpResponse, httpContext);
            if (select == null || select.isEmpty()) {
                return false;
            }
            if (this.log.isDebugEnabled()) {
                HttpClientAndroidLog httpClientAndroidLog2 = this.log;
                StringBuilder sb2 = new StringBuilder();
                sb2.append("Selected authentication options: ");
                sb2.append(select);
                httpClientAndroidLog2.debug(sb2.toString());
            }
            authState.setState(AuthProtocolState.CHALLENGED);
            authState.update(select);
            return true;
        } catch (MalformedChallengeException e) {
            if (this.log.isWarnEnabled()) {
                HttpClientAndroidLog httpClientAndroidLog3 = this.log;
                StringBuilder sb3 = new StringBuilder();
                sb3.append("Malformed challenge: ");
                sb3.append(e.getMessage());
                httpClientAndroidLog3.warn(sb3.toString());
            }
            authState.reset();
            return false;
        }
    }

    public void generateAuthResponse(HttpRequest httpRequest, AuthState authState, HttpContext httpContext) {
        AuthScheme authScheme = authState.getAuthScheme();
        Credentials credentials = authState.getCredentials();
        int i = AnonymousClass1.a[authState.getState().ordinal()];
        if (i != 1) {
            switch (i) {
                case 3:
                    a(authScheme);
                    if (authScheme.isConnectionBased()) {
                        return;
                    }
                    break;
                case 4:
                    return;
            }
        } else {
            Queue authOptions = authState.getAuthOptions();
            if (authOptions != null) {
                while (!authOptions.isEmpty()) {
                    AuthOption authOption = (AuthOption) authOptions.remove();
                    AuthScheme authScheme2 = authOption.getAuthScheme();
                    Credentials credentials2 = authOption.getCredentials();
                    authState.update(authScheme2, credentials2);
                    if (this.log.isDebugEnabled()) {
                        HttpClientAndroidLog httpClientAndroidLog = this.log;
                        StringBuilder sb = new StringBuilder();
                        sb.append("Generating response to an authentication challenge using ");
                        sb.append(authScheme2.getSchemeName());
                        sb.append(" scheme");
                        httpClientAndroidLog.debug(sb.toString());
                    }
                    try {
                        httpRequest.addHeader(a(authScheme2, credentials2, httpRequest, httpContext));
                        break;
                    } catch (AuthenticationException e) {
                        if (this.log.isWarnEnabled()) {
                            HttpClientAndroidLog httpClientAndroidLog2 = this.log;
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
            a(authScheme);
        }
        if (authScheme != null) {
            try {
                httpRequest.addHeader(a(authScheme, credentials, httpRequest, httpContext));
            } catch (AuthenticationException e2) {
                if (this.log.isErrorEnabled()) {
                    HttpClientAndroidLog httpClientAndroidLog3 = this.log;
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
        if (authScheme instanceof ContextAwareAuthScheme) {
            return ((ContextAwareAuthScheme) authScheme).authenticate(credentials, httpRequest, httpContext);
        }
        return authScheme.authenticate(credentials, httpRequest);
    }
}
