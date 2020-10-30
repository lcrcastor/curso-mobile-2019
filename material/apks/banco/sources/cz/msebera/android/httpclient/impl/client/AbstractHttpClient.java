package cz.msebera.android.httpclient.impl.client;

import cz.msebera.android.httpclient.ConnectionReuseStrategy;
import cz.msebera.android.httpclient.HttpException;
import cz.msebera.android.httpclient.HttpHost;
import cz.msebera.android.httpclient.HttpRequest;
import cz.msebera.android.httpclient.HttpRequestInterceptor;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.HttpResponseInterceptor;
import cz.msebera.android.httpclient.annotation.GuardedBy;
import cz.msebera.android.httpclient.annotation.ThreadSafe;
import cz.msebera.android.httpclient.auth.AuthSchemeRegistry;
import cz.msebera.android.httpclient.client.AuthenticationHandler;
import cz.msebera.android.httpclient.client.AuthenticationStrategy;
import cz.msebera.android.httpclient.client.BackoffManager;
import cz.msebera.android.httpclient.client.ClientProtocolException;
import cz.msebera.android.httpclient.client.ConnectionBackoffStrategy;
import cz.msebera.android.httpclient.client.CookieStore;
import cz.msebera.android.httpclient.client.CredentialsProvider;
import cz.msebera.android.httpclient.client.HttpRequestRetryHandler;
import cz.msebera.android.httpclient.client.RedirectHandler;
import cz.msebera.android.httpclient.client.RedirectStrategy;
import cz.msebera.android.httpclient.client.RequestDirector;
import cz.msebera.android.httpclient.client.UserTokenHandler;
import cz.msebera.android.httpclient.client.methods.CloseableHttpResponse;
import cz.msebera.android.httpclient.client.params.ClientPNames;
import cz.msebera.android.httpclient.client.params.CookiePolicy;
import cz.msebera.android.httpclient.client.params.HttpClientParamConfig;
import cz.msebera.android.httpclient.client.protocol.ClientContext;
import cz.msebera.android.httpclient.conn.ClientConnectionManager;
import cz.msebera.android.httpclient.conn.ClientConnectionManagerFactory;
import cz.msebera.android.httpclient.conn.ConnectionKeepAliveStrategy;
import cz.msebera.android.httpclient.conn.routing.HttpRoute;
import cz.msebera.android.httpclient.conn.routing.HttpRoutePlanner;
import cz.msebera.android.httpclient.conn.scheme.SchemeRegistry;
import cz.msebera.android.httpclient.cookie.CookieSpecRegistry;
import cz.msebera.android.httpclient.extras.HttpClientAndroidLog;
import cz.msebera.android.httpclient.impl.DefaultConnectionReuseStrategy;
import cz.msebera.android.httpclient.impl.auth.BasicSchemeFactory;
import cz.msebera.android.httpclient.impl.auth.DigestSchemeFactory;
import cz.msebera.android.httpclient.impl.auth.NTLMSchemeFactory;
import cz.msebera.android.httpclient.impl.conn.BasicClientConnectionManager;
import cz.msebera.android.httpclient.impl.conn.DefaultHttpRoutePlanner;
import cz.msebera.android.httpclient.impl.conn.SchemeRegistryFactory;
import cz.msebera.android.httpclient.impl.cookie.BestMatchSpecFactory;
import cz.msebera.android.httpclient.impl.cookie.BrowserCompatSpecFactory;
import cz.msebera.android.httpclient.impl.cookie.IgnoreSpecFactory;
import cz.msebera.android.httpclient.impl.cookie.NetscapeDraftSpecFactory;
import cz.msebera.android.httpclient.impl.cookie.RFC2109SpecFactory;
import cz.msebera.android.httpclient.impl.cookie.RFC2965SpecFactory;
import cz.msebera.android.httpclient.params.HttpParams;
import cz.msebera.android.httpclient.protocol.BasicHttpContext;
import cz.msebera.android.httpclient.protocol.BasicHttpProcessor;
import cz.msebera.android.httpclient.protocol.DefaultedHttpContext;
import cz.msebera.android.httpclient.protocol.HttpContext;
import cz.msebera.android.httpclient.protocol.HttpProcessor;
import cz.msebera.android.httpclient.protocol.HttpRequestExecutor;
import cz.msebera.android.httpclient.protocol.ImmutableHttpProcessor;
import cz.msebera.android.httpclient.util.Args;
import java.io.IOException;
import java.lang.reflect.UndeclaredThrowableException;

@ThreadSafe
@Deprecated
public abstract class AbstractHttpClient extends CloseableHttpClient {
    @GuardedBy("this")
    private HttpParams a;
    @GuardedBy("this")
    private HttpRequestExecutor b;
    @GuardedBy("this")
    private ClientConnectionManager c;
    @GuardedBy("this")
    private ConnectionReuseStrategy d;
    @GuardedBy("this")
    private ConnectionKeepAliveStrategy e;
    @GuardedBy("this")
    private CookieSpecRegistry f;
    @GuardedBy("this")
    private AuthSchemeRegistry g;
    @GuardedBy("this")
    private BasicHttpProcessor h;
    @GuardedBy("this")
    private ImmutableHttpProcessor i;
    @GuardedBy("this")
    private HttpRequestRetryHandler j;
    @GuardedBy("this")
    private RedirectStrategy k;
    @GuardedBy("this")
    private AuthenticationStrategy l;
    public HttpClientAndroidLog log = new HttpClientAndroidLog(getClass());
    @GuardedBy("this")
    private AuthenticationStrategy m;
    @GuardedBy("this")
    private CookieStore n;
    @GuardedBy("this")
    private CredentialsProvider o;
    @GuardedBy("this")
    private HttpRoutePlanner p;
    @GuardedBy("this")
    private UserTokenHandler q;
    @GuardedBy("this")
    private ConnectionBackoffStrategy r;
    @GuardedBy("this")
    private BackoffManager s;

    /* access modifiers changed from: protected */
    public abstract HttpParams createHttpParams();

    /* access modifiers changed from: protected */
    public abstract BasicHttpProcessor createHttpProcessor();

    protected AbstractHttpClient(ClientConnectionManager clientConnectionManager, HttpParams httpParams) {
        this.a = httpParams;
        this.c = clientConnectionManager;
    }

    /* access modifiers changed from: protected */
    public HttpContext createHttpContext() {
        BasicHttpContext basicHttpContext = new BasicHttpContext();
        basicHttpContext.setAttribute(ClientContext.SCHEME_REGISTRY, getConnectionManager().getSchemeRegistry());
        basicHttpContext.setAttribute("http.authscheme-registry", getAuthSchemes());
        basicHttpContext.setAttribute("http.cookiespec-registry", getCookieSpecs());
        basicHttpContext.setAttribute("http.cookie-store", getCookieStore());
        basicHttpContext.setAttribute("http.auth.credentials-provider", getCredentialsProvider());
        return basicHttpContext;
    }

    /* access modifiers changed from: protected */
    public ClientConnectionManager createClientConnectionManager() {
        ClientConnectionManagerFactory clientConnectionManagerFactory;
        SchemeRegistry createDefault = SchemeRegistryFactory.createDefault();
        HttpParams params = getParams();
        String str = (String) params.getParameter(ClientPNames.CONNECTION_MANAGER_FACTORY_CLASS_NAME);
        if (str != null) {
            try {
                clientConnectionManagerFactory = (ClientConnectionManagerFactory) Class.forName(str).newInstance();
            } catch (ClassNotFoundException unused) {
                StringBuilder sb = new StringBuilder();
                sb.append("Invalid class name: ");
                sb.append(str);
                throw new IllegalStateException(sb.toString());
            } catch (IllegalAccessException e2) {
                throw new IllegalAccessError(e2.getMessage());
            } catch (InstantiationException e3) {
                throw new InstantiationError(e3.getMessage());
            }
        } else {
            clientConnectionManagerFactory = null;
        }
        if (clientConnectionManagerFactory != null) {
            return clientConnectionManagerFactory.newInstance(params, createDefault);
        }
        return new BasicClientConnectionManager(createDefault);
    }

    /* access modifiers changed from: protected */
    public AuthSchemeRegistry createAuthSchemeRegistry() {
        AuthSchemeRegistry authSchemeRegistry = new AuthSchemeRegistry();
        authSchemeRegistry.register("Basic", new BasicSchemeFactory());
        authSchemeRegistry.register("Digest", new DigestSchemeFactory());
        authSchemeRegistry.register("NTLM", new NTLMSchemeFactory());
        return authSchemeRegistry;
    }

    /* access modifiers changed from: protected */
    public CookieSpecRegistry createCookieSpecRegistry() {
        CookieSpecRegistry cookieSpecRegistry = new CookieSpecRegistry();
        cookieSpecRegistry.register("default", new BestMatchSpecFactory());
        cookieSpecRegistry.register("best-match", new BestMatchSpecFactory());
        cookieSpecRegistry.register("compatibility", new BrowserCompatSpecFactory());
        cookieSpecRegistry.register("netscape", new NetscapeDraftSpecFactory());
        cookieSpecRegistry.register(CookiePolicy.RFC_2109, new RFC2109SpecFactory());
        cookieSpecRegistry.register(CookiePolicy.RFC_2965, new RFC2965SpecFactory());
        cookieSpecRegistry.register("ignoreCookies", new IgnoreSpecFactory());
        return cookieSpecRegistry;
    }

    /* access modifiers changed from: protected */
    public HttpRequestExecutor createRequestExecutor() {
        return new HttpRequestExecutor();
    }

    /* access modifiers changed from: protected */
    public ConnectionReuseStrategy createConnectionReuseStrategy() {
        return new DefaultConnectionReuseStrategy();
    }

    /* access modifiers changed from: protected */
    public ConnectionKeepAliveStrategy createConnectionKeepAliveStrategy() {
        return new DefaultConnectionKeepAliveStrategy();
    }

    /* access modifiers changed from: protected */
    public HttpRequestRetryHandler createHttpRequestRetryHandler() {
        return new DefaultHttpRequestRetryHandler();
    }

    /* access modifiers changed from: protected */
    @Deprecated
    public RedirectHandler createRedirectHandler() {
        return new DefaultRedirectHandler();
    }

    /* access modifiers changed from: protected */
    public AuthenticationStrategy createTargetAuthenticationStrategy() {
        return new TargetAuthenticationStrategy();
    }

    /* access modifiers changed from: protected */
    @Deprecated
    public AuthenticationHandler createTargetAuthenticationHandler() {
        return new DefaultTargetAuthenticationHandler();
    }

    /* access modifiers changed from: protected */
    public AuthenticationStrategy createProxyAuthenticationStrategy() {
        return new ProxyAuthenticationStrategy();
    }

    /* access modifiers changed from: protected */
    @Deprecated
    public AuthenticationHandler createProxyAuthenticationHandler() {
        return new DefaultProxyAuthenticationHandler();
    }

    /* access modifiers changed from: protected */
    public CookieStore createCookieStore() {
        return new BasicCookieStore();
    }

    /* access modifiers changed from: protected */
    public CredentialsProvider createCredentialsProvider() {
        return new BasicCredentialsProvider();
    }

    /* access modifiers changed from: protected */
    public HttpRoutePlanner createHttpRoutePlanner() {
        return new DefaultHttpRoutePlanner(getConnectionManager().getSchemeRegistry());
    }

    /* access modifiers changed from: protected */
    public UserTokenHandler createUserTokenHandler() {
        return new DefaultUserTokenHandler();
    }

    public final synchronized HttpParams getParams() {
        if (this.a == null) {
            this.a = createHttpParams();
        }
        return this.a;
    }

    public synchronized void setParams(HttpParams httpParams) {
        this.a = httpParams;
    }

    public final synchronized ClientConnectionManager getConnectionManager() {
        if (this.c == null) {
            this.c = createClientConnectionManager();
        }
        return this.c;
    }

    public final synchronized HttpRequestExecutor getRequestExecutor() {
        if (this.b == null) {
            this.b = createRequestExecutor();
        }
        return this.b;
    }

    public final synchronized AuthSchemeRegistry getAuthSchemes() {
        if (this.g == null) {
            this.g = createAuthSchemeRegistry();
        }
        return this.g;
    }

    public synchronized void setAuthSchemes(AuthSchemeRegistry authSchemeRegistry) {
        this.g = authSchemeRegistry;
    }

    public final synchronized ConnectionBackoffStrategy getConnectionBackoffStrategy() {
        return this.r;
    }

    public synchronized void setConnectionBackoffStrategy(ConnectionBackoffStrategy connectionBackoffStrategy) {
        this.r = connectionBackoffStrategy;
    }

    public final synchronized CookieSpecRegistry getCookieSpecs() {
        if (this.f == null) {
            this.f = createCookieSpecRegistry();
        }
        return this.f;
    }

    public final synchronized BackoffManager getBackoffManager() {
        return this.s;
    }

    public synchronized void setBackoffManager(BackoffManager backoffManager) {
        this.s = backoffManager;
    }

    public synchronized void setCookieSpecs(CookieSpecRegistry cookieSpecRegistry) {
        this.f = cookieSpecRegistry;
    }

    public final synchronized ConnectionReuseStrategy getConnectionReuseStrategy() {
        if (this.d == null) {
            this.d = createConnectionReuseStrategy();
        }
        return this.d;
    }

    public synchronized void setReuseStrategy(ConnectionReuseStrategy connectionReuseStrategy) {
        this.d = connectionReuseStrategy;
    }

    public final synchronized ConnectionKeepAliveStrategy getConnectionKeepAliveStrategy() {
        if (this.e == null) {
            this.e = createConnectionKeepAliveStrategy();
        }
        return this.e;
    }

    public synchronized void setKeepAliveStrategy(ConnectionKeepAliveStrategy connectionKeepAliveStrategy) {
        this.e = connectionKeepAliveStrategy;
    }

    public final synchronized HttpRequestRetryHandler getHttpRequestRetryHandler() {
        if (this.j == null) {
            this.j = createHttpRequestRetryHandler();
        }
        return this.j;
    }

    public synchronized void setHttpRequestRetryHandler(HttpRequestRetryHandler httpRequestRetryHandler) {
        this.j = httpRequestRetryHandler;
    }

    @Deprecated
    public final synchronized RedirectHandler getRedirectHandler() {
        return createRedirectHandler();
    }

    @Deprecated
    public synchronized void setRedirectHandler(RedirectHandler redirectHandler) {
        this.k = new DefaultRedirectStrategyAdaptor(redirectHandler);
    }

    public final synchronized RedirectStrategy getRedirectStrategy() {
        if (this.k == null) {
            this.k = new DefaultRedirectStrategy();
        }
        return this.k;
    }

    public synchronized void setRedirectStrategy(RedirectStrategy redirectStrategy) {
        this.k = redirectStrategy;
    }

    @Deprecated
    public final synchronized AuthenticationHandler getTargetAuthenticationHandler() {
        return createTargetAuthenticationHandler();
    }

    @Deprecated
    public synchronized void setTargetAuthenticationHandler(AuthenticationHandler authenticationHandler) {
        this.l = new AuthenticationStrategyAdaptor(authenticationHandler);
    }

    public final synchronized AuthenticationStrategy getTargetAuthenticationStrategy() {
        if (this.l == null) {
            this.l = createTargetAuthenticationStrategy();
        }
        return this.l;
    }

    public synchronized void setTargetAuthenticationStrategy(AuthenticationStrategy authenticationStrategy) {
        this.l = authenticationStrategy;
    }

    @Deprecated
    public final synchronized AuthenticationHandler getProxyAuthenticationHandler() {
        return createProxyAuthenticationHandler();
    }

    @Deprecated
    public synchronized void setProxyAuthenticationHandler(AuthenticationHandler authenticationHandler) {
        this.m = new AuthenticationStrategyAdaptor(authenticationHandler);
    }

    public final synchronized AuthenticationStrategy getProxyAuthenticationStrategy() {
        if (this.m == null) {
            this.m = createProxyAuthenticationStrategy();
        }
        return this.m;
    }

    public synchronized void setProxyAuthenticationStrategy(AuthenticationStrategy authenticationStrategy) {
        this.m = authenticationStrategy;
    }

    public final synchronized CookieStore getCookieStore() {
        if (this.n == null) {
            this.n = createCookieStore();
        }
        return this.n;
    }

    public synchronized void setCookieStore(CookieStore cookieStore) {
        this.n = cookieStore;
    }

    public final synchronized CredentialsProvider getCredentialsProvider() {
        if (this.o == null) {
            this.o = createCredentialsProvider();
        }
        return this.o;
    }

    public synchronized void setCredentialsProvider(CredentialsProvider credentialsProvider) {
        this.o = credentialsProvider;
    }

    public final synchronized HttpRoutePlanner getRoutePlanner() {
        if (this.p == null) {
            this.p = createHttpRoutePlanner();
        }
        return this.p;
    }

    public synchronized void setRoutePlanner(HttpRoutePlanner httpRoutePlanner) {
        this.p = httpRoutePlanner;
    }

    public final synchronized UserTokenHandler getUserTokenHandler() {
        if (this.q == null) {
            this.q = createUserTokenHandler();
        }
        return this.q;
    }

    public synchronized void setUserTokenHandler(UserTokenHandler userTokenHandler) {
        this.q = userTokenHandler;
    }

    /* access modifiers changed from: protected */
    public final synchronized BasicHttpProcessor getHttpProcessor() {
        if (this.h == null) {
            this.h = createHttpProcessor();
        }
        return this.h;
    }

    private synchronized HttpProcessor a() {
        if (this.i == null) {
            BasicHttpProcessor httpProcessor = getHttpProcessor();
            int requestInterceptorCount = httpProcessor.getRequestInterceptorCount();
            HttpRequestInterceptor[] httpRequestInterceptorArr = new HttpRequestInterceptor[requestInterceptorCount];
            for (int i2 = 0; i2 < requestInterceptorCount; i2++) {
                httpRequestInterceptorArr[i2] = httpProcessor.getRequestInterceptor(i2);
            }
            int responseInterceptorCount = httpProcessor.getResponseInterceptorCount();
            HttpResponseInterceptor[] httpResponseInterceptorArr = new HttpResponseInterceptor[responseInterceptorCount];
            for (int i3 = 0; i3 < responseInterceptorCount; i3++) {
                httpResponseInterceptorArr[i3] = httpProcessor.getResponseInterceptor(i3);
            }
            this.i = new ImmutableHttpProcessor(httpRequestInterceptorArr, httpResponseInterceptorArr);
        }
        return this.i;
    }

    public synchronized int getResponseInterceptorCount() {
        return getHttpProcessor().getResponseInterceptorCount();
    }

    public synchronized HttpResponseInterceptor getResponseInterceptor(int i2) {
        return getHttpProcessor().getResponseInterceptor(i2);
    }

    public synchronized HttpRequestInterceptor getRequestInterceptor(int i2) {
        return getHttpProcessor().getRequestInterceptor(i2);
    }

    public synchronized int getRequestInterceptorCount() {
        return getHttpProcessor().getRequestInterceptorCount();
    }

    public synchronized void addResponseInterceptor(HttpResponseInterceptor httpResponseInterceptor) {
        getHttpProcessor().addInterceptor(httpResponseInterceptor);
        this.i = null;
    }

    public synchronized void addResponseInterceptor(HttpResponseInterceptor httpResponseInterceptor, int i2) {
        getHttpProcessor().addInterceptor(httpResponseInterceptor, i2);
        this.i = null;
    }

    public synchronized void clearResponseInterceptors() {
        getHttpProcessor().clearResponseInterceptors();
        this.i = null;
    }

    public synchronized void removeResponseInterceptorByClass(Class<? extends HttpResponseInterceptor> cls) {
        getHttpProcessor().removeResponseInterceptorByClass(cls);
        this.i = null;
    }

    public synchronized void addRequestInterceptor(HttpRequestInterceptor httpRequestInterceptor) {
        getHttpProcessor().addInterceptor(httpRequestInterceptor);
        this.i = null;
    }

    public synchronized void addRequestInterceptor(HttpRequestInterceptor httpRequestInterceptor, int i2) {
        getHttpProcessor().addInterceptor(httpRequestInterceptor, i2);
        this.i = null;
    }

    public synchronized void clearRequestInterceptors() {
        getHttpProcessor().clearRequestInterceptors();
        this.i = null;
    }

    public synchronized void removeRequestInterceptorByClass(Class<? extends HttpRequestInterceptor> cls) {
        getHttpProcessor().removeRequestInterceptorByClass(cls);
        this.i = null;
    }

    /* JADX INFO: finally extract failed */
    /* access modifiers changed from: protected */
    public final CloseableHttpResponse doExecute(HttpHost httpHost, HttpRequest httpRequest, HttpContext httpContext) {
        HttpContext httpContext2;
        HttpContext httpContext3;
        RequestDirector createClientRequestDirector;
        HttpRoutePlanner routePlanner;
        ConnectionBackoffStrategy connectionBackoffStrategy;
        BackoffManager backoffManager;
        HttpHost httpHost2;
        HttpContext httpContext4;
        HttpRequest httpRequest2;
        HttpRoute determineRoute;
        HttpHost httpHost3 = httpHost;
        HttpRequest httpRequest3 = httpRequest;
        HttpContext httpContext5 = httpContext;
        Args.notNull(httpRequest3, "HTTP request");
        synchronized (this) {
            try {
                HttpContext createHttpContext = createHttpContext();
                if (httpContext5 == null) {
                    httpContext2 = createHttpContext;
                } else {
                    httpContext2 = new DefaultedHttpContext(httpContext5, createHttpContext);
                }
                HttpParams determineParams = determineParams(httpRequest3);
                httpContext2.setAttribute("http.request-config", HttpClientParamConfig.getRequestConfig(determineParams));
                httpContext3 = httpContext2;
                createClientRequestDirector = createClientRequestDirector(getRequestExecutor(), getConnectionManager(), getConnectionReuseStrategy(), getConnectionKeepAliveStrategy(), getRoutePlanner(), a(), getHttpRequestRetryHandler(), getRedirectStrategy(), getTargetAuthenticationStrategy(), getProxyAuthenticationStrategy(), getUserTokenHandler(), determineParams);
                routePlanner = getRoutePlanner();
                connectionBackoffStrategy = getConnectionBackoffStrategy();
                backoffManager = getBackoffManager();
            } catch (Throwable th) {
                while (true) {
                    throw th;
                }
            }
        }
        if (connectionBackoffStrategy == null || backoffManager == null) {
            return CloseableHttpResponseProxy.a(createClientRequestDirector.execute(httpHost3, httpRequest, httpContext3));
        }
        if (httpHost3 != null) {
            httpHost2 = httpHost3;
            httpContext4 = httpContext3;
            httpRequest2 = httpRequest;
        } else {
            httpRequest2 = httpRequest;
            try {
                httpHost2 = (HttpHost) determineParams(httpRequest2).getParameter(ClientPNames.DEFAULT_HOST);
                httpContext4 = httpContext3;
            } catch (RuntimeException e2) {
                RuntimeException runtimeException = e2;
                if (connectionBackoffStrategy.shouldBackoff((Throwable) runtimeException)) {
                    backoffManager.backOff(determineRoute);
                }
                throw runtimeException;
            } catch (Exception e3) {
                Exception exc = e3;
                if (connectionBackoffStrategy.shouldBackoff((Throwable) exc)) {
                    backoffManager.backOff(determineRoute);
                }
                if (exc instanceof HttpException) {
                    throw ((HttpException) exc);
                } else if (exc instanceof IOException) {
                    throw ((IOException) exc);
                } else {
                    throw new UndeclaredThrowableException(exc);
                }
            } catch (HttpException e4) {
                throw new ClientProtocolException((Throwable) e4);
            }
        }
        determineRoute = routePlanner.determineRoute(httpHost2, httpRequest2, httpContext4);
        CloseableHttpResponse a2 = CloseableHttpResponseProxy.a(createClientRequestDirector.execute(httpHost3, httpRequest2, httpContext4));
        if (connectionBackoffStrategy.shouldBackoff((HttpResponse) a2)) {
            backoffManager.backOff(determineRoute);
        } else {
            backoffManager.probe(determineRoute);
        }
        return a2;
    }

    /* access modifiers changed from: protected */
    @Deprecated
    public RequestDirector createClientRequestDirector(HttpRequestExecutor httpRequestExecutor, ClientConnectionManager clientConnectionManager, ConnectionReuseStrategy connectionReuseStrategy, ConnectionKeepAliveStrategy connectionKeepAliveStrategy, HttpRoutePlanner httpRoutePlanner, HttpProcessor httpProcessor, HttpRequestRetryHandler httpRequestRetryHandler, RedirectHandler redirectHandler, AuthenticationHandler authenticationHandler, AuthenticationHandler authenticationHandler2, UserTokenHandler userTokenHandler, HttpParams httpParams) {
        DefaultRequestDirector defaultRequestDirector = new DefaultRequestDirector(httpRequestExecutor, clientConnectionManager, connectionReuseStrategy, connectionKeepAliveStrategy, httpRoutePlanner, httpProcessor, httpRequestRetryHandler, redirectHandler, authenticationHandler, authenticationHandler2, userTokenHandler, httpParams);
        return defaultRequestDirector;
    }

    /* access modifiers changed from: protected */
    @Deprecated
    public RequestDirector createClientRequestDirector(HttpRequestExecutor httpRequestExecutor, ClientConnectionManager clientConnectionManager, ConnectionReuseStrategy connectionReuseStrategy, ConnectionKeepAliveStrategy connectionKeepAliveStrategy, HttpRoutePlanner httpRoutePlanner, HttpProcessor httpProcessor, HttpRequestRetryHandler httpRequestRetryHandler, RedirectStrategy redirectStrategy, AuthenticationHandler authenticationHandler, AuthenticationHandler authenticationHandler2, UserTokenHandler userTokenHandler, HttpParams httpParams) {
        DefaultRequestDirector defaultRequestDirector = new DefaultRequestDirector(this.log, httpRequestExecutor, clientConnectionManager, connectionReuseStrategy, connectionKeepAliveStrategy, httpRoutePlanner, httpProcessor, httpRequestRetryHandler, redirectStrategy, authenticationHandler, authenticationHandler2, userTokenHandler, httpParams);
        return defaultRequestDirector;
    }

    /* access modifiers changed from: protected */
    public RequestDirector createClientRequestDirector(HttpRequestExecutor httpRequestExecutor, ClientConnectionManager clientConnectionManager, ConnectionReuseStrategy connectionReuseStrategy, ConnectionKeepAliveStrategy connectionKeepAliveStrategy, HttpRoutePlanner httpRoutePlanner, HttpProcessor httpProcessor, HttpRequestRetryHandler httpRequestRetryHandler, RedirectStrategy redirectStrategy, AuthenticationStrategy authenticationStrategy, AuthenticationStrategy authenticationStrategy2, UserTokenHandler userTokenHandler, HttpParams httpParams) {
        DefaultRequestDirector defaultRequestDirector = new DefaultRequestDirector(this.log, httpRequestExecutor, clientConnectionManager, connectionReuseStrategy, connectionKeepAliveStrategy, httpRoutePlanner, httpProcessor, httpRequestRetryHandler, redirectStrategy, authenticationStrategy, authenticationStrategy2, userTokenHandler, httpParams);
        return defaultRequestDirector;
    }

    /* access modifiers changed from: protected */
    public HttpParams determineParams(HttpRequest httpRequest) {
        return new ClientParamsStack(null, getParams(), httpRequest.getParams(), null);
    }

    public void close() {
        getConnectionManager().shutdown();
    }
}
