package cz.msebera.android.httpclient.client.protocol;

import cz.msebera.android.httpclient.HttpHost;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.HttpResponseInterceptor;
import cz.msebera.android.httpclient.annotation.Immutable;
import cz.msebera.android.httpclient.auth.AuthScheme;
import cz.msebera.android.httpclient.auth.AuthState;
import cz.msebera.android.httpclient.client.AuthCache;
import cz.msebera.android.httpclient.conn.scheme.SchemeRegistry;
import cz.msebera.android.httpclient.extras.HttpClientAndroidLog;
import cz.msebera.android.httpclient.impl.client.BasicAuthCache;
import cz.msebera.android.httpclient.protocol.ExecutionContext;
import cz.msebera.android.httpclient.protocol.HttpContext;
import cz.msebera.android.httpclient.util.Args;

@Immutable
@Deprecated
public class ResponseAuthCache implements HttpResponseInterceptor {
    public HttpClientAndroidLog log = new HttpClientAndroidLog(getClass());

    public void process(HttpResponse httpResponse, HttpContext httpContext) {
        Args.notNull(httpResponse, "HTTP request");
        Args.notNull(httpContext, "HTTP context");
        AuthCache authCache = (AuthCache) httpContext.getAttribute("http.auth.auth-cache");
        HttpHost httpHost = (HttpHost) httpContext.getAttribute("http.target_host");
        AuthState authState = (AuthState) httpContext.getAttribute("http.auth.target-scope");
        if (!(httpHost == null || authState == null)) {
            if (this.log.isDebugEnabled()) {
                HttpClientAndroidLog httpClientAndroidLog = this.log;
                StringBuilder sb = new StringBuilder();
                sb.append("Target auth state: ");
                sb.append(authState.getState());
                httpClientAndroidLog.debug(sb.toString());
            }
            if (a(authState)) {
                SchemeRegistry schemeRegistry = (SchemeRegistry) httpContext.getAttribute(ClientContext.SCHEME_REGISTRY);
                if (httpHost.getPort() < 0) {
                    httpHost = new HttpHost(httpHost.getHostName(), schemeRegistry.getScheme(httpHost).resolvePort(httpHost.getPort()), httpHost.getSchemeName());
                }
                if (authCache == null) {
                    authCache = new BasicAuthCache();
                    httpContext.setAttribute("http.auth.auth-cache", authCache);
                }
                switch (authState.getState()) {
                    case CHALLENGED:
                        a(authCache, httpHost, authState.getAuthScheme());
                        break;
                    case FAILURE:
                        b(authCache, httpHost, authState.getAuthScheme());
                        break;
                }
            }
        }
        HttpHost httpHost2 = (HttpHost) httpContext.getAttribute(ExecutionContext.HTTP_PROXY_HOST);
        AuthState authState2 = (AuthState) httpContext.getAttribute("http.auth.proxy-scope");
        if (httpHost2 != null && authState2 != null) {
            if (this.log.isDebugEnabled()) {
                HttpClientAndroidLog httpClientAndroidLog2 = this.log;
                StringBuilder sb2 = new StringBuilder();
                sb2.append("Proxy auth state: ");
                sb2.append(authState2.getState());
                httpClientAndroidLog2.debug(sb2.toString());
            }
            if (a(authState2)) {
                if (authCache == null) {
                    authCache = new BasicAuthCache();
                    httpContext.setAttribute("http.auth.auth-cache", authCache);
                }
                switch (authState2.getState()) {
                    case CHALLENGED:
                        a(authCache, httpHost2, authState2.getAuthScheme());
                        return;
                    case FAILURE:
                        b(authCache, httpHost2, authState2.getAuthScheme());
                        return;
                    default:
                        return;
                }
            }
        }
    }

    private boolean a(AuthState authState) {
        AuthScheme authScheme = authState.getAuthScheme();
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

    private void a(AuthCache authCache, HttpHost httpHost, AuthScheme authScheme) {
        if (this.log.isDebugEnabled()) {
            HttpClientAndroidLog httpClientAndroidLog = this.log;
            StringBuilder sb = new StringBuilder();
            sb.append("Caching '");
            sb.append(authScheme.getSchemeName());
            sb.append("' auth scheme for ");
            sb.append(httpHost);
            httpClientAndroidLog.debug(sb.toString());
        }
        authCache.put(httpHost, authScheme);
    }

    private void b(AuthCache authCache, HttpHost httpHost, AuthScheme authScheme) {
        if (this.log.isDebugEnabled()) {
            HttpClientAndroidLog httpClientAndroidLog = this.log;
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
