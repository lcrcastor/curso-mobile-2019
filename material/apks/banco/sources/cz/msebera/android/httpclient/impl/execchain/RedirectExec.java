package cz.msebera.android.httpclient.impl.execchain;

import cz.msebera.android.httpclient.HttpEntityEnclosingRequest;
import cz.msebera.android.httpclient.HttpException;
import cz.msebera.android.httpclient.HttpHost;
import cz.msebera.android.httpclient.ProtocolException;
import cz.msebera.android.httpclient.annotation.ThreadSafe;
import cz.msebera.android.httpclient.auth.AuthScheme;
import cz.msebera.android.httpclient.auth.AuthState;
import cz.msebera.android.httpclient.client.RedirectException;
import cz.msebera.android.httpclient.client.RedirectStrategy;
import cz.msebera.android.httpclient.client.config.RequestConfig;
import cz.msebera.android.httpclient.client.methods.CloseableHttpResponse;
import cz.msebera.android.httpclient.client.methods.HttpExecutionAware;
import cz.msebera.android.httpclient.client.methods.HttpRequestWrapper;
import cz.msebera.android.httpclient.client.methods.HttpUriRequest;
import cz.msebera.android.httpclient.client.protocol.HttpClientContext;
import cz.msebera.android.httpclient.client.utils.URIUtils;
import cz.msebera.android.httpclient.conn.routing.HttpRoute;
import cz.msebera.android.httpclient.conn.routing.HttpRoutePlanner;
import cz.msebera.android.httpclient.extras.HttpClientAndroidLog;
import cz.msebera.android.httpclient.util.Args;
import cz.msebera.android.httpclient.util.EntityUtils;
import java.io.IOException;
import java.net.URI;
import java.util.List;

@ThreadSafe
public class RedirectExec implements ClientExecChain {
    private final ClientExecChain a;
    private final RedirectStrategy b;
    private final HttpRoutePlanner c;
    public HttpClientAndroidLog log = new HttpClientAndroidLog(getClass());

    public RedirectExec(ClientExecChain clientExecChain, HttpRoutePlanner httpRoutePlanner, RedirectStrategy redirectStrategy) {
        Args.notNull(clientExecChain, "HTTP client request executor");
        Args.notNull(httpRoutePlanner, "HTTP route planner");
        Args.notNull(redirectStrategy, "HTTP redirect strategy");
        this.a = clientExecChain;
        this.c = httpRoutePlanner;
        this.b = redirectStrategy;
    }

    public CloseableHttpResponse execute(HttpRoute httpRoute, HttpRequestWrapper httpRequestWrapper, HttpClientContext httpClientContext, HttpExecutionAware httpExecutionAware) {
        CloseableHttpResponse execute;
        Args.notNull(httpRoute, "HTTP route");
        Args.notNull(httpRequestWrapper, "HTTP request");
        Args.notNull(httpClientContext, "HTTP context");
        List redirectLocations = httpClientContext.getRedirectLocations();
        if (redirectLocations != null) {
            redirectLocations.clear();
        }
        RequestConfig requestConfig = httpClientContext.getRequestConfig();
        int maxRedirects = requestConfig.getMaxRedirects() > 0 ? requestConfig.getMaxRedirects() : 50;
        HttpRequestWrapper httpRequestWrapper2 = httpRequestWrapper;
        int i = 0;
        while (true) {
            execute = this.a.execute(httpRoute, httpRequestWrapper2, httpClientContext, httpExecutionAware);
            try {
                if (!requestConfig.isRedirectsEnabled() || !this.b.isRedirected(httpRequestWrapper2, execute, httpClientContext)) {
                    return execute;
                }
                if (i >= maxRedirects) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("Maximum redirects (");
                    sb.append(maxRedirects);
                    sb.append(") exceeded");
                    throw new RedirectException(sb.toString());
                }
                i++;
                HttpUriRequest redirect = this.b.getRedirect(httpRequestWrapper2, execute, httpClientContext);
                if (!redirect.headerIterator().hasNext()) {
                    redirect.setHeaders(httpRequestWrapper.getOriginal().getAllHeaders());
                }
                httpRequestWrapper2 = HttpRequestWrapper.wrap(redirect);
                if (httpRequestWrapper2 instanceof HttpEntityEnclosingRequest) {
                    RequestEntityProxy.a((HttpEntityEnclosingRequest) httpRequestWrapper2);
                }
                URI uri = httpRequestWrapper2.getURI();
                HttpHost extractHost = URIUtils.extractHost(uri);
                if (extractHost == null) {
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("Redirect URI does not specify a valid host name: ");
                    sb2.append(uri);
                    throw new ProtocolException(sb2.toString());
                }
                if (!httpRoute.getTargetHost().equals(extractHost)) {
                    AuthState targetAuthState = httpClientContext.getTargetAuthState();
                    if (targetAuthState != null) {
                        this.log.debug("Resetting target auth state");
                        targetAuthState.reset();
                    }
                    AuthState proxyAuthState = httpClientContext.getProxyAuthState();
                    if (proxyAuthState != null) {
                        AuthScheme authScheme = proxyAuthState.getAuthScheme();
                        if (authScheme != null && authScheme.isConnectionBased()) {
                            this.log.debug("Resetting proxy auth state");
                            proxyAuthState.reset();
                        }
                    }
                }
                httpRoute = this.c.determineRoute(extractHost, httpRequestWrapper2, httpClientContext);
                if (this.log.isDebugEnabled()) {
                    HttpClientAndroidLog httpClientAndroidLog = this.log;
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append("Redirecting to '");
                    sb3.append(uri);
                    sb3.append("' via ");
                    sb3.append(httpRoute);
                    httpClientAndroidLog.debug(sb3.toString());
                }
                EntityUtils.consume(execute.getEntity());
                execute.close();
            } catch (RuntimeException e) {
                execute.close();
                throw e;
            } catch (IOException e2) {
                execute.close();
                throw e2;
            } catch (HttpException e3) {
                try {
                    EntityUtils.consume(execute.getEntity());
                } catch (IOException e4) {
                    this.log.debug("I/O error while releasing connection", e4);
                } catch (Throwable th) {
                    execute.close();
                    throw th;
                }
                execute.close();
                throw e3;
            }
        }
        return execute;
    }
}
