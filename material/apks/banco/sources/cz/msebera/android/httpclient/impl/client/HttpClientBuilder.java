package cz.msebera.android.httpclient.impl.client;

import cz.msebera.android.httpclient.ConnectionReuseStrategy;
import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpHost;
import cz.msebera.android.httpclient.HttpRequestInterceptor;
import cz.msebera.android.httpclient.HttpResponseInterceptor;
import cz.msebera.android.httpclient.annotation.NotThreadSafe;
import cz.msebera.android.httpclient.auth.AuthSchemeProvider;
import cz.msebera.android.httpclient.client.AuthenticationStrategy;
import cz.msebera.android.httpclient.client.BackoffManager;
import cz.msebera.android.httpclient.client.ConnectionBackoffStrategy;
import cz.msebera.android.httpclient.client.CookieStore;
import cz.msebera.android.httpclient.client.CredentialsProvider;
import cz.msebera.android.httpclient.client.HttpRequestRetryHandler;
import cz.msebera.android.httpclient.client.RedirectStrategy;
import cz.msebera.android.httpclient.client.ServiceUnavailableRetryStrategy;
import cz.msebera.android.httpclient.client.UserTokenHandler;
import cz.msebera.android.httpclient.client.config.CookieSpecs;
import cz.msebera.android.httpclient.client.config.RequestConfig;
import cz.msebera.android.httpclient.client.entity.InputStreamFactory;
import cz.msebera.android.httpclient.client.protocol.RequestAcceptEncoding;
import cz.msebera.android.httpclient.client.protocol.RequestAddCookies;
import cz.msebera.android.httpclient.client.protocol.RequestAuthCache;
import cz.msebera.android.httpclient.client.protocol.RequestClientConnControl;
import cz.msebera.android.httpclient.client.protocol.RequestDefaultHeaders;
import cz.msebera.android.httpclient.client.protocol.RequestExpectContinue;
import cz.msebera.android.httpclient.client.protocol.ResponseContentEncoding;
import cz.msebera.android.httpclient.client.protocol.ResponseProcessCookies;
import cz.msebera.android.httpclient.config.ConnectionConfig;
import cz.msebera.android.httpclient.config.Lookup;
import cz.msebera.android.httpclient.config.RegistryBuilder;
import cz.msebera.android.httpclient.config.SocketConfig;
import cz.msebera.android.httpclient.conn.ConnectionKeepAliveStrategy;
import cz.msebera.android.httpclient.conn.HttpClientConnectionManager;
import cz.msebera.android.httpclient.conn.SchemePortResolver;
import cz.msebera.android.httpclient.conn.routing.HttpRoutePlanner;
import cz.msebera.android.httpclient.conn.socket.LayeredConnectionSocketFactory;
import cz.msebera.android.httpclient.conn.socket.PlainConnectionSocketFactory;
import cz.msebera.android.httpclient.conn.ssl.DefaultHostnameVerifier;
import cz.msebera.android.httpclient.conn.ssl.SSLConnectionSocketFactory;
import cz.msebera.android.httpclient.conn.ssl.X509HostnameVerifier;
import cz.msebera.android.httpclient.conn.util.PublicSuffixMatcher;
import cz.msebera.android.httpclient.conn.util.PublicSuffixMatcherLoader;
import cz.msebera.android.httpclient.cookie.CookieSpecProvider;
import cz.msebera.android.httpclient.impl.DefaultConnectionReuseStrategy;
import cz.msebera.android.httpclient.impl.NoConnectionReuseStrategy;
import cz.msebera.android.httpclient.impl.auth.BasicSchemeFactory;
import cz.msebera.android.httpclient.impl.auth.DigestSchemeFactory;
import cz.msebera.android.httpclient.impl.auth.NTLMSchemeFactory;
import cz.msebera.android.httpclient.impl.conn.DefaultProxyRoutePlanner;
import cz.msebera.android.httpclient.impl.conn.DefaultRoutePlanner;
import cz.msebera.android.httpclient.impl.conn.DefaultSchemePortResolver;
import cz.msebera.android.httpclient.impl.conn.PoolingHttpClientConnectionManager;
import cz.msebera.android.httpclient.impl.conn.SystemDefaultRoutePlanner;
import cz.msebera.android.httpclient.impl.cookie.DefaultCookieSpecProvider;
import cz.msebera.android.httpclient.impl.cookie.IgnoreSpecProvider;
import cz.msebera.android.httpclient.impl.cookie.NetscapeDraftSpecProvider;
import cz.msebera.android.httpclient.impl.cookie.RFC6265CookieSpecProvider;
import cz.msebera.android.httpclient.impl.cookie.RFC6265CookieSpecProvider.CompatibilityLevel;
import cz.msebera.android.httpclient.impl.execchain.BackoffStrategyExec;
import cz.msebera.android.httpclient.impl.execchain.ClientExecChain;
import cz.msebera.android.httpclient.impl.execchain.MainClientExec;
import cz.msebera.android.httpclient.impl.execchain.ProtocolExec;
import cz.msebera.android.httpclient.impl.execchain.RedirectExec;
import cz.msebera.android.httpclient.impl.execchain.RetryExec;
import cz.msebera.android.httpclient.impl.execchain.ServiceUnavailableRetryExec;
import cz.msebera.android.httpclient.protocol.HttpProcessor;
import cz.msebera.android.httpclient.protocol.HttpProcessorBuilder;
import cz.msebera.android.httpclient.protocol.HttpRequestExecutor;
import cz.msebera.android.httpclient.protocol.ImmutableHttpProcessor;
import cz.msebera.android.httpclient.protocol.RequestContent;
import cz.msebera.android.httpclient.protocol.RequestTargetHost;
import cz.msebera.android.httpclient.protocol.RequestUserAgent;
import cz.msebera.android.httpclient.ssl.SSLContexts;
import cz.msebera.android.httpclient.util.TextUtils;
import cz.msebera.android.httpclient.util.VersionInfo;
import java.io.Closeable;
import java.net.ProxySelector;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;

@NotThreadSafe
public class HttpClientBuilder {
    private CookieStore A;
    private CredentialsProvider B;
    private String C;
    private HttpHost D;
    private Collection<? extends Header> E;
    private SocketConfig F;
    private ConnectionConfig G;
    private RequestConfig H;
    private boolean I;
    private boolean J;
    private long K;
    private TimeUnit L;
    private boolean M;
    private boolean N;
    private boolean O;
    private boolean P;
    private boolean Q;
    private boolean R;
    private boolean S;
    private int T = 0;
    private int U = 0;
    private long V = -1;
    private TimeUnit W = TimeUnit.MILLISECONDS;
    private List<Closeable> X;
    private PublicSuffixMatcher Y;
    private HttpRequestExecutor a;
    private HostnameVerifier b;
    private LayeredConnectionSocketFactory c;
    private SSLContext d;
    private HttpClientConnectionManager e;
    private boolean f;
    private SchemePortResolver g;
    private ConnectionReuseStrategy h;
    private ConnectionKeepAliveStrategy i;
    private AuthenticationStrategy j;
    private AuthenticationStrategy k;
    private UserTokenHandler l;
    private HttpProcessor m;
    private LinkedList<HttpRequestInterceptor> n;
    private LinkedList<HttpRequestInterceptor> o;
    private LinkedList<HttpResponseInterceptor> p;
    private LinkedList<HttpResponseInterceptor> q;
    private HttpRequestRetryHandler r;
    private HttpRoutePlanner s;
    private RedirectStrategy t;
    private ConnectionBackoffStrategy u;
    private BackoffManager v;
    private ServiceUnavailableRetryStrategy w;
    private Lookup<AuthSchemeProvider> x;
    private Lookup<CookieSpecProvider> y;
    private Map<String, InputStreamFactory> z;

    public ClientExecChain decorateMainExec(ClientExecChain clientExecChain) {
        return clientExecChain;
    }

    /* access modifiers changed from: protected */
    public ClientExecChain decorateProtocolExec(ClientExecChain clientExecChain) {
        return clientExecChain;
    }

    public static HttpClientBuilder create() {
        return new HttpClientBuilder();
    }

    protected HttpClientBuilder() {
    }

    public final HttpClientBuilder setRequestExecutor(HttpRequestExecutor httpRequestExecutor) {
        this.a = httpRequestExecutor;
        return this;
    }

    @Deprecated
    public final HttpClientBuilder setHostnameVerifier(X509HostnameVerifier x509HostnameVerifier) {
        this.b = x509HostnameVerifier;
        return this;
    }

    public final HttpClientBuilder setSSLHostnameVerifier(HostnameVerifier hostnameVerifier) {
        this.b = hostnameVerifier;
        return this;
    }

    public final HttpClientBuilder setPublicSuffixMatcher(PublicSuffixMatcher publicSuffixMatcher) {
        this.Y = publicSuffixMatcher;
        return this;
    }

    public final HttpClientBuilder setSslcontext(SSLContext sSLContext) {
        this.d = sSLContext;
        return this;
    }

    public final HttpClientBuilder setSSLSocketFactory(LayeredConnectionSocketFactory layeredConnectionSocketFactory) {
        this.c = layeredConnectionSocketFactory;
        return this;
    }

    public final HttpClientBuilder setMaxConnTotal(int i2) {
        this.T = i2;
        return this;
    }

    public final HttpClientBuilder setMaxConnPerRoute(int i2) {
        this.U = i2;
        return this;
    }

    public final HttpClientBuilder setDefaultSocketConfig(SocketConfig socketConfig) {
        this.F = socketConfig;
        return this;
    }

    public final HttpClientBuilder setDefaultConnectionConfig(ConnectionConfig connectionConfig) {
        this.G = connectionConfig;
        return this;
    }

    public final HttpClientBuilder setConnectionTimeToLive(long j2, TimeUnit timeUnit) {
        this.V = j2;
        this.W = timeUnit;
        return this;
    }

    public final HttpClientBuilder setConnectionManager(HttpClientConnectionManager httpClientConnectionManager) {
        this.e = httpClientConnectionManager;
        return this;
    }

    public final HttpClientBuilder setConnectionManagerShared(boolean z2) {
        this.f = z2;
        return this;
    }

    public final HttpClientBuilder setConnectionReuseStrategy(ConnectionReuseStrategy connectionReuseStrategy) {
        this.h = connectionReuseStrategy;
        return this;
    }

    public final HttpClientBuilder setKeepAliveStrategy(ConnectionKeepAliveStrategy connectionKeepAliveStrategy) {
        this.i = connectionKeepAliveStrategy;
        return this;
    }

    public final HttpClientBuilder setTargetAuthenticationStrategy(AuthenticationStrategy authenticationStrategy) {
        this.j = authenticationStrategy;
        return this;
    }

    public final HttpClientBuilder setProxyAuthenticationStrategy(AuthenticationStrategy authenticationStrategy) {
        this.k = authenticationStrategy;
        return this;
    }

    public final HttpClientBuilder setUserTokenHandler(UserTokenHandler userTokenHandler) {
        this.l = userTokenHandler;
        return this;
    }

    public final HttpClientBuilder disableConnectionState() {
        this.S = true;
        return this;
    }

    public final HttpClientBuilder setSchemePortResolver(SchemePortResolver schemePortResolver) {
        this.g = schemePortResolver;
        return this;
    }

    public final HttpClientBuilder setUserAgent(String str) {
        this.C = str;
        return this;
    }

    public final HttpClientBuilder setDefaultHeaders(Collection<? extends Header> collection) {
        this.E = collection;
        return this;
    }

    public final HttpClientBuilder addInterceptorFirst(HttpResponseInterceptor httpResponseInterceptor) {
        if (httpResponseInterceptor == null) {
            return this;
        }
        if (this.p == null) {
            this.p = new LinkedList<>();
        }
        this.p.addFirst(httpResponseInterceptor);
        return this;
    }

    public final HttpClientBuilder addInterceptorLast(HttpResponseInterceptor httpResponseInterceptor) {
        if (httpResponseInterceptor == null) {
            return this;
        }
        if (this.q == null) {
            this.q = new LinkedList<>();
        }
        this.q.addLast(httpResponseInterceptor);
        return this;
    }

    public final HttpClientBuilder addInterceptorFirst(HttpRequestInterceptor httpRequestInterceptor) {
        if (httpRequestInterceptor == null) {
            return this;
        }
        if (this.n == null) {
            this.n = new LinkedList<>();
        }
        this.n.addFirst(httpRequestInterceptor);
        return this;
    }

    public final HttpClientBuilder addInterceptorLast(HttpRequestInterceptor httpRequestInterceptor) {
        if (httpRequestInterceptor == null) {
            return this;
        }
        if (this.o == null) {
            this.o = new LinkedList<>();
        }
        this.o.addLast(httpRequestInterceptor);
        return this;
    }

    public final HttpClientBuilder disableCookieManagement() {
        this.Q = true;
        return this;
    }

    public final HttpClientBuilder disableContentCompression() {
        this.P = true;
        return this;
    }

    public final HttpClientBuilder disableAuthCaching() {
        this.R = true;
        return this;
    }

    public final HttpClientBuilder setHttpProcessor(HttpProcessor httpProcessor) {
        this.m = httpProcessor;
        return this;
    }

    public final HttpClientBuilder setRetryHandler(HttpRequestRetryHandler httpRequestRetryHandler) {
        this.r = httpRequestRetryHandler;
        return this;
    }

    public final HttpClientBuilder disableAutomaticRetries() {
        this.O = true;
        return this;
    }

    public final HttpClientBuilder setProxy(HttpHost httpHost) {
        this.D = httpHost;
        return this;
    }

    public final HttpClientBuilder setRoutePlanner(HttpRoutePlanner httpRoutePlanner) {
        this.s = httpRoutePlanner;
        return this;
    }

    public final HttpClientBuilder setRedirectStrategy(RedirectStrategy redirectStrategy) {
        this.t = redirectStrategy;
        return this;
    }

    public final HttpClientBuilder disableRedirectHandling() {
        this.N = true;
        return this;
    }

    public final HttpClientBuilder setConnectionBackoffStrategy(ConnectionBackoffStrategy connectionBackoffStrategy) {
        this.u = connectionBackoffStrategy;
        return this;
    }

    public final HttpClientBuilder setBackoffManager(BackoffManager backoffManager) {
        this.v = backoffManager;
        return this;
    }

    public final HttpClientBuilder setServiceUnavailableRetryStrategy(ServiceUnavailableRetryStrategy serviceUnavailableRetryStrategy) {
        this.w = serviceUnavailableRetryStrategy;
        return this;
    }

    public final HttpClientBuilder setDefaultCookieStore(CookieStore cookieStore) {
        this.A = cookieStore;
        return this;
    }

    public final HttpClientBuilder setDefaultCredentialsProvider(CredentialsProvider credentialsProvider) {
        this.B = credentialsProvider;
        return this;
    }

    public final HttpClientBuilder setDefaultAuthSchemeRegistry(Lookup<AuthSchemeProvider> lookup) {
        this.x = lookup;
        return this;
    }

    public final HttpClientBuilder setDefaultCookieSpecRegistry(Lookup<CookieSpecProvider> lookup) {
        this.y = lookup;
        return this;
    }

    public final HttpClientBuilder setContentDecoderRegistry(Map<String, InputStreamFactory> map) {
        this.z = map;
        return this;
    }

    public final HttpClientBuilder setDefaultRequestConfig(RequestConfig requestConfig) {
        this.H = requestConfig;
        return this;
    }

    public final HttpClientBuilder useSystemProperties() {
        this.M = true;
        return this;
    }

    public final HttpClientBuilder evictExpiredConnections() {
        this.I = true;
        return this;
    }

    public final HttpClientBuilder evictIdleConnections(Long l2, TimeUnit timeUnit) {
        this.J = true;
        this.K = l2.longValue();
        this.L = timeUnit;
        return this;
    }

    /* access modifiers changed from: protected */
    public ClientExecChain createMainExec(HttpRequestExecutor httpRequestExecutor, HttpClientConnectionManager httpClientConnectionManager, ConnectionReuseStrategy connectionReuseStrategy, ConnectionKeepAliveStrategy connectionKeepAliveStrategy, HttpProcessor httpProcessor, AuthenticationStrategy authenticationStrategy, AuthenticationStrategy authenticationStrategy2, UserTokenHandler userTokenHandler) {
        MainClientExec mainClientExec = new MainClientExec(httpRequestExecutor, httpClientConnectionManager, connectionReuseStrategy, connectionKeepAliveStrategy, httpProcessor, authenticationStrategy, authenticationStrategy2, userTokenHandler);
        return mainClientExec;
    }

    /* access modifiers changed from: protected */
    public void addCloseable(Closeable closeable) {
        if (closeable != null) {
            if (this.X == null) {
                this.X = new ArrayList();
            }
            this.X.add(closeable);
        }
    }

    private static String[] a(String str) {
        if (TextUtils.isBlank(str)) {
            return null;
        }
        return str.split(" *, *");
    }

    public CloseableHttpClient build() {
        final HttpClientConnectionManager httpClientConnectionManager;
        HttpRoutePlanner httpRoutePlanner;
        Object sSLConnectionSocketFactory;
        PublicSuffixMatcher publicSuffixMatcher = this.Y;
        if (publicSuffixMatcher == null) {
            publicSuffixMatcher = PublicSuffixMatcherLoader.getDefault();
        }
        PublicSuffixMatcher publicSuffixMatcher2 = publicSuffixMatcher;
        HttpRequestExecutor httpRequestExecutor = this.a;
        if (httpRequestExecutor == null) {
            httpRequestExecutor = new HttpRequestExecutor();
        }
        HttpRequestExecutor httpRequestExecutor2 = httpRequestExecutor;
        HttpClientConnectionManager httpClientConnectionManager2 = this.e;
        if (httpClientConnectionManager2 == 0) {
            Object obj = this.c;
            if (obj == null) {
                String[] a2 = this.M ? a(System.getProperty("https.protocols")) : null;
                String[] a3 = this.M ? a(System.getProperty("https.cipherSuites")) : null;
                HostnameVerifier hostnameVerifier = this.b;
                if (hostnameVerifier == null) {
                    hostnameVerifier = new DefaultHostnameVerifier(publicSuffixMatcher2);
                }
                if (this.d != null) {
                    sSLConnectionSocketFactory = new SSLConnectionSocketFactory(this.d, a2, a3, hostnameVerifier);
                } else if (this.M) {
                    sSLConnectionSocketFactory = new SSLConnectionSocketFactory((SSLSocketFactory) SSLSocketFactory.getDefault(), a2, a3, hostnameVerifier);
                } else {
                    obj = new SSLConnectionSocketFactory(SSLContexts.createDefault(), hostnameVerifier);
                }
                obj = sSLConnectionSocketFactory;
            }
            PoolingHttpClientConnectionManager poolingHttpClientConnectionManager = new PoolingHttpClientConnectionManager(RegistryBuilder.create().register(HttpHost.DEFAULT_SCHEME_NAME, PlainConnectionSocketFactory.getSocketFactory()).register("https", obj).build(), null, null, null, this.V, this.W != null ? this.W : TimeUnit.MILLISECONDS);
            if (this.F != null) {
                poolingHttpClientConnectionManager.setDefaultSocketConfig(this.F);
            }
            if (this.G != null) {
                poolingHttpClientConnectionManager.setDefaultConnectionConfig(this.G);
            }
            if (this.M) {
                if ("true".equalsIgnoreCase(System.getProperty("http.keepAlive", "true"))) {
                    int parseInt = Integer.parseInt(System.getProperty("http.maxConnections", "5"));
                    poolingHttpClientConnectionManager.setDefaultMaxPerRoute(parseInt);
                    poolingHttpClientConnectionManager.setMaxTotal(parseInt * 2);
                }
            }
            if (this.T > 0) {
                poolingHttpClientConnectionManager.setMaxTotal(this.T);
            }
            if (this.U > 0) {
                poolingHttpClientConnectionManager.setDefaultMaxPerRoute(this.U);
            }
            httpClientConnectionManager = poolingHttpClientConnectionManager;
        } else {
            httpClientConnectionManager = httpClientConnectionManager2;
        }
        ConnectionReuseStrategy connectionReuseStrategy = this.h;
        if (connectionReuseStrategy == null) {
            if (this.M) {
                if ("true".equalsIgnoreCase(System.getProperty("http.keepAlive", "true"))) {
                    connectionReuseStrategy = DefaultConnectionReuseStrategy.INSTANCE;
                } else {
                    connectionReuseStrategy = NoConnectionReuseStrategy.INSTANCE;
                }
            } else {
                connectionReuseStrategy = DefaultConnectionReuseStrategy.INSTANCE;
            }
        }
        ConnectionReuseStrategy connectionReuseStrategy2 = connectionReuseStrategy;
        ConnectionKeepAliveStrategy connectionKeepAliveStrategy = this.i;
        if (connectionKeepAliveStrategy == null) {
            connectionKeepAliveStrategy = DefaultConnectionKeepAliveStrategy.INSTANCE;
        }
        ConnectionKeepAliveStrategy connectionKeepAliveStrategy2 = connectionKeepAliveStrategy;
        AuthenticationStrategy authenticationStrategy = this.j;
        if (authenticationStrategy == null) {
            authenticationStrategy = TargetAuthenticationStrategy.INSTANCE;
        }
        AuthenticationStrategy authenticationStrategy2 = authenticationStrategy;
        AuthenticationStrategy authenticationStrategy3 = this.k;
        if (authenticationStrategy3 == null) {
            authenticationStrategy3 = ProxyAuthenticationStrategy.INSTANCE;
        }
        AuthenticationStrategy authenticationStrategy4 = authenticationStrategy3;
        UserTokenHandler userTokenHandler = this.l;
        if (userTokenHandler == null) {
            if (!this.S) {
                userTokenHandler = DefaultUserTokenHandler.INSTANCE;
            } else {
                userTokenHandler = NoopUserTokenHandler.INSTANCE;
            }
        }
        UserTokenHandler userTokenHandler2 = userTokenHandler;
        String str = this.C;
        if (str == null) {
            if (this.M) {
                str = System.getProperty("http.agent");
            }
            if (str == null) {
                str = VersionInfo.getUserAgent("Apache-HttpClient", "cz.msebera.android.httpclient.client", getClass());
            }
        }
        String str2 = str;
        ClientExecChain decorateMainExec = decorateMainExec(createMainExec(httpRequestExecutor2, httpClientConnectionManager, connectionReuseStrategy2, connectionKeepAliveStrategy2, new ImmutableHttpProcessor(new RequestTargetHost(), new RequestUserAgent(str2)), authenticationStrategy2, authenticationStrategy4, userTokenHandler2));
        HttpProcessor httpProcessor = this.m;
        if (httpProcessor == null) {
            HttpProcessorBuilder create = HttpProcessorBuilder.create();
            if (this.n != null) {
                Iterator it = this.n.iterator();
                while (it.hasNext()) {
                    create.addFirst((HttpRequestInterceptor) it.next());
                }
            }
            if (this.p != null) {
                Iterator it2 = this.p.iterator();
                while (it2.hasNext()) {
                    create.addFirst((HttpResponseInterceptor) it2.next());
                }
            }
            create.addAll(new RequestDefaultHeaders(this.E), new RequestContent(), new RequestTargetHost(), new RequestClientConnControl(), new RequestUserAgent(str2), new RequestExpectContinue());
            if (!this.Q) {
                create.add((HttpRequestInterceptor) new RequestAddCookies());
            }
            if (!this.P) {
                if (this.z != null) {
                    ArrayList arrayList = new ArrayList(this.z.keySet());
                    Collections.sort(arrayList);
                    create.add((HttpRequestInterceptor) new RequestAcceptEncoding(arrayList));
                } else {
                    create.add((HttpRequestInterceptor) new RequestAcceptEncoding());
                }
            }
            if (!this.R) {
                create.add((HttpRequestInterceptor) new RequestAuthCache());
            }
            if (!this.Q) {
                create.add((HttpResponseInterceptor) new ResponseProcessCookies());
            }
            if (!this.P) {
                if (this.z != null) {
                    RegistryBuilder create2 = RegistryBuilder.create();
                    for (Entry entry : this.z.entrySet()) {
                        create2.register((String) entry.getKey(), entry.getValue());
                    }
                    create.add((HttpResponseInterceptor) new ResponseContentEncoding(create2.build()));
                } else {
                    create.add((HttpResponseInterceptor) new ResponseContentEncoding());
                }
            }
            if (this.o != null) {
                Iterator it3 = this.o.iterator();
                while (it3.hasNext()) {
                    create.addLast((HttpRequestInterceptor) it3.next());
                }
            }
            if (this.q != null) {
                Iterator it4 = this.q.iterator();
                while (it4.hasNext()) {
                    create.addLast((HttpResponseInterceptor) it4.next());
                }
            }
            httpProcessor = create.build();
        }
        ClientExecChain decorateProtocolExec = decorateProtocolExec(new ProtocolExec(decorateMainExec, httpProcessor));
        if (!this.O) {
            HttpRequestRetryHandler httpRequestRetryHandler = this.r;
            if (httpRequestRetryHandler == null) {
                httpRequestRetryHandler = DefaultHttpRequestRetryHandler.INSTANCE;
            }
            decorateProtocolExec = new RetryExec(decorateProtocolExec, httpRequestRetryHandler);
        }
        HttpRoutePlanner httpRoutePlanner2 = this.s;
        if (httpRoutePlanner2 == null) {
            SchemePortResolver schemePortResolver = this.g;
            if (schemePortResolver == null) {
                schemePortResolver = DefaultSchemePortResolver.INSTANCE;
            }
            httpRoutePlanner = this.D != null ? new DefaultProxyRoutePlanner(this.D, schemePortResolver) : this.M ? new SystemDefaultRoutePlanner(schemePortResolver, ProxySelector.getDefault()) : new DefaultRoutePlanner(schemePortResolver);
        } else {
            httpRoutePlanner = httpRoutePlanner2;
        }
        if (!this.N) {
            RedirectStrategy redirectStrategy = this.t;
            if (redirectStrategy == null) {
                redirectStrategy = DefaultRedirectStrategy.INSTANCE;
            }
            decorateProtocolExec = new RedirectExec(decorateProtocolExec, httpRoutePlanner, redirectStrategy);
        }
        ServiceUnavailableRetryStrategy serviceUnavailableRetryStrategy = this.w;
        if (serviceUnavailableRetryStrategy != null) {
            decorateProtocolExec = new ServiceUnavailableRetryExec(decorateProtocolExec, serviceUnavailableRetryStrategy);
        }
        ClientExecChain backoffStrategyExec = (this.v == null || this.u == null) ? decorateProtocolExec : new BackoffStrategyExec(decorateProtocolExec, this.u, this.v);
        Lookup lookup = this.x;
        if (lookup == null) {
            lookup = RegistryBuilder.create().register("Basic", new BasicSchemeFactory()).register("Digest", new DigestSchemeFactory()).register("NTLM", new NTLMSchemeFactory()).build();
        }
        Lookup lookup2 = lookup;
        Lookup lookup3 = this.y;
        if (lookup3 == null) {
            DefaultCookieSpecProvider defaultCookieSpecProvider = new DefaultCookieSpecProvider(publicSuffixMatcher2);
            lookup3 = RegistryBuilder.create().register("default", defaultCookieSpecProvider).register("best-match", defaultCookieSpecProvider).register("compatibility", defaultCookieSpecProvider).register(CookieSpecs.STANDARD, new RFC6265CookieSpecProvider(CompatibilityLevel.RELAXED, publicSuffixMatcher2)).register(CookieSpecs.STANDARD_STRICT, new RFC6265CookieSpecProvider(CompatibilityLevel.STRICT, publicSuffixMatcher2)).register("netscape", new NetscapeDraftSpecProvider()).register("ignoreCookies", new IgnoreSpecProvider()).build();
        }
        Lookup lookup4 = lookup3;
        CookieStore cookieStore = this.A;
        if (cookieStore == null) {
            cookieStore = new BasicCookieStore();
        }
        CookieStore cookieStore2 = cookieStore;
        CredentialsProvider credentialsProvider = this.B;
        if (credentialsProvider == null) {
            if (this.M) {
                credentialsProvider = new SystemDefaultCredentialsProvider();
            } else {
                credentialsProvider = new BasicCredentialsProvider();
            }
        }
        CredentialsProvider credentialsProvider2 = credentialsProvider;
        ArrayList arrayList2 = this.X != null ? new ArrayList(this.X) : null;
        if (!this.f) {
            if (arrayList2 == null) {
                arrayList2 = new ArrayList(1);
            }
            if (this.I || this.J) {
                final IdleConnectionEvictor idleConnectionEvictor = new IdleConnectionEvictor(httpClientConnectionManager, this.K > 0 ? this.K : 10, this.L != null ? this.L : TimeUnit.SECONDS);
                arrayList2.add(new Closeable() {
                    public void close() {
                        idleConnectionEvictor.shutdown();
                    }
                });
                idleConnectionEvictor.start();
            }
            arrayList2.add(new Closeable() {
                public void close() {
                    httpClientConnectionManager.shutdown();
                }
            });
        }
        InternalHttpClient internalHttpClient = new InternalHttpClient(backoffStrategyExec, httpClientConnectionManager, httpRoutePlanner, lookup4, lookup2, cookieStore2, credentialsProvider2, this.H != null ? this.H : RequestConfig.DEFAULT, arrayList2);
        return internalHttpClient;
    }
}
