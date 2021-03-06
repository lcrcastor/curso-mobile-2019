package cz.msebera.android.httpclient.impl.client;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpHost;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.annotation.Immutable;
import cz.msebera.android.httpclient.auth.AuthOption;
import cz.msebera.android.httpclient.auth.AuthScheme;
import cz.msebera.android.httpclient.auth.AuthScope;
import cz.msebera.android.httpclient.auth.AuthenticationException;
import cz.msebera.android.httpclient.auth.Credentials;
import cz.msebera.android.httpclient.client.AuthCache;
import cz.msebera.android.httpclient.client.AuthenticationHandler;
import cz.msebera.android.httpclient.client.AuthenticationStrategy;
import cz.msebera.android.httpclient.client.CredentialsProvider;
import cz.msebera.android.httpclient.extras.HttpClientAndroidLog;
import cz.msebera.android.httpclient.protocol.HttpContext;
import cz.msebera.android.httpclient.util.Args;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Map;
import java.util.Queue;

@Immutable
@Deprecated
class AuthenticationStrategyAdaptor implements AuthenticationStrategy {
    public HttpClientAndroidLog a = new HttpClientAndroidLog(getClass());
    private final AuthenticationHandler b;

    public AuthenticationStrategyAdaptor(AuthenticationHandler authenticationHandler) {
        this.b = authenticationHandler;
    }

    public boolean isAuthenticationRequested(HttpHost httpHost, HttpResponse httpResponse, HttpContext httpContext) {
        return this.b.isAuthenticationRequested(httpResponse, httpContext);
    }

    public Map<String, Header> getChallenges(HttpHost httpHost, HttpResponse httpResponse, HttpContext httpContext) {
        return this.b.getChallenges(httpResponse, httpContext);
    }

    public Queue<AuthOption> select(Map<String, Header> map, HttpHost httpHost, HttpResponse httpResponse, HttpContext httpContext) {
        Args.notNull(map, "Map of auth challenges");
        Args.notNull(httpHost, "Host");
        Args.notNull(httpResponse, "HTTP response");
        Args.notNull(httpContext, "HTTP context");
        LinkedList linkedList = new LinkedList();
        CredentialsProvider credentialsProvider = (CredentialsProvider) httpContext.getAttribute("http.auth.credentials-provider");
        if (credentialsProvider == null) {
            this.a.debug("Credentials provider not set in the context");
            return linkedList;
        }
        try {
            AuthScheme selectScheme = this.b.selectScheme(map, httpResponse, httpContext);
            selectScheme.processChallenge((Header) map.get(selectScheme.getSchemeName().toLowerCase(Locale.ROOT)));
            Credentials credentials = credentialsProvider.getCredentials(new AuthScope(httpHost.getHostName(), httpHost.getPort(), selectScheme.getRealm(), selectScheme.getSchemeName()));
            if (credentials != null) {
                linkedList.add(new AuthOption(selectScheme, credentials));
            }
            return linkedList;
        } catch (AuthenticationException e) {
            if (this.a.isWarnEnabled()) {
                this.a.warn(e.getMessage(), e);
            }
            return linkedList;
        }
    }

    public void authSucceeded(HttpHost httpHost, AuthScheme authScheme, HttpContext httpContext) {
        AuthCache authCache = (AuthCache) httpContext.getAttribute("http.auth.auth-cache");
        if (a(authScheme)) {
            if (authCache == null) {
                authCache = new BasicAuthCache();
                httpContext.setAttribute("http.auth.auth-cache", authCache);
            }
            if (this.a.isDebugEnabled()) {
                HttpClientAndroidLog httpClientAndroidLog = this.a;
                StringBuilder sb = new StringBuilder();
                sb.append("Caching '");
                sb.append(authScheme.getSchemeName());
                sb.append("' auth scheme for ");
                sb.append(httpHost);
                httpClientAndroidLog.debug(sb.toString());
            }
            authCache.put(httpHost, authScheme);
        }
    }

    public void authFailed(HttpHost httpHost, AuthScheme authScheme, HttpContext httpContext) {
        AuthCache authCache = (AuthCache) httpContext.getAttribute("http.auth.auth-cache");
        if (authCache != null) {
            if (this.a.isDebugEnabled()) {
                HttpClientAndroidLog httpClientAndroidLog = this.a;
                StringBuilder sb = new StringBuilder();
                sb.append("Removing from cache '");
                sb.append(authScheme.getSchemeName());
                sb.append("' auth scheme for ");
                sb.append(httpHost);
                httpClientAndroidLog.debug(sb.toString());
            }
            authCache.remove(httpHost);
        }
    }

    private boolean a(AuthScheme authScheme) {
        boolean z = false;
        if (authScheme == null || !authScheme.isComplete()) {
            return false;
        }
        String schemeName = authScheme.getSchemeName();
        if (schemeName.equalsIgnoreCase("Basic") || schemeName.equalsIgnoreCase("Digest")) {
            z = true;
        }
        return z;
    }

    public AuthenticationHandler a() {
        return this.b;
    }
}
