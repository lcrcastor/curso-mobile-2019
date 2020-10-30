package com.squareup.okhttp;

import com.squareup.okhttp.Headers.Builder;
import com.squareup.okhttp.internal.Internal;
import com.squareup.okhttp.internal.InternalCache;
import com.squareup.okhttp.internal.Network;
import com.squareup.okhttp.internal.RouteDatabase;
import com.squareup.okhttp.internal.Util;
import com.squareup.okhttp.internal.http.AuthenticatorAdapter;
import com.squareup.okhttp.internal.http.HttpEngine;
import com.squareup.okhttp.internal.http.Transport;
import com.squareup.okhttp.internal.tls.OkHostnameVerifier;
import java.net.CookieHandler;
import java.net.Proxy;
import java.net.ProxySelector;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.net.SocketFactory;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import okio.BufferedSink;
import okio.BufferedSource;

public class OkHttpClient implements Cloneable {
    private static final List<Protocol> a = Util.immutableList((T[]) new Protocol[]{Protocol.HTTP_2, Protocol.SPDY_3, Protocol.HTTP_1_1});
    private static final List<ConnectionSpec> b = Util.immutableList((T[]) new ConnectionSpec[]{ConnectionSpec.MODERN_TLS, ConnectionSpec.COMPATIBLE_TLS, ConnectionSpec.CLEARTEXT});
    private static SSLSocketFactory c;
    private int A;
    private final RouteDatabase d;
    private Dispatcher e;
    private Proxy f;
    private List<Protocol> g;
    private List<ConnectionSpec> h;
    private final List<Interceptor> i;
    private final List<Interceptor> j;
    private ProxySelector k;
    private CookieHandler l;
    private InternalCache m;
    private Cache n;
    private SocketFactory o;
    private SSLSocketFactory p;
    private HostnameVerifier q;
    private CertificatePinner r;
    private Authenticator s;
    private ConnectionPool t;
    /* access modifiers changed from: private */
    public Network u;
    private boolean v;
    private boolean w;
    private boolean x;
    private int y;
    private int z;

    static {
        Internal.instance = new Internal() {
            public Transport newTransport(Connection connection, HttpEngine httpEngine) {
                return connection.a(httpEngine);
            }

            public boolean clearOwner(Connection connection) {
                return connection.a();
            }

            public void closeIfOwnedBy(Connection connection, Object obj) {
                connection.b(obj);
            }

            public int recycleCount(Connection connection) {
                return connection.l();
            }

            public void setProtocol(Connection connection, Protocol protocol) {
                connection.a(protocol);
            }

            public void setOwner(Connection connection, HttpEngine httpEngine) {
                connection.a((Object) httpEngine);
            }

            public boolean isReadable(Connection connection) {
                return connection.f();
            }

            public void addLenient(Builder builder, String str) {
                builder.a(str);
            }

            public void addLenient(Builder builder, String str, String str2) {
                builder.a(str, str2);
            }

            public void setCache(OkHttpClient okHttpClient, InternalCache internalCache) {
                okHttpClient.a(internalCache);
            }

            public InternalCache internalCache(OkHttpClient okHttpClient) {
                return okHttpClient.a();
            }

            public void recycle(ConnectionPool connectionPool, Connection connection) {
                connectionPool.a(connection);
            }

            public RouteDatabase routeDatabase(OkHttpClient okHttpClient) {
                return okHttpClient.b();
            }

            public Network network(OkHttpClient okHttpClient) {
                return okHttpClient.u;
            }

            public void setNetwork(OkHttpClient okHttpClient, Network network) {
                okHttpClient.u = network;
            }

            public void connectAndSetOwner(OkHttpClient okHttpClient, Connection connection, HttpEngine httpEngine, Request request) {
                connection.a(okHttpClient, httpEngine, request);
            }

            public void callEnqueue(Call call, Callback callback, boolean z) {
                call.a(callback, z);
            }

            public void callEngineReleaseConnection(Call call) {
                call.c.releaseConnection();
            }

            public Connection callEngineGetConnection(Call call) {
                return call.c.getConnection();
            }

            public BufferedSource connectionRawSource(Connection connection) {
                return connection.c();
            }

            public BufferedSink connectionRawSink(Connection connection) {
                return connection.d();
            }

            public void connectionSetOwner(Connection connection, Object obj) {
                connection.a(obj);
            }

            public void apply(ConnectionSpec connectionSpec, SSLSocket sSLSocket, boolean z) {
                connectionSpec.a(sSLSocket, z);
            }
        };
    }

    public OkHttpClient() {
        this.i = new ArrayList();
        this.j = new ArrayList();
        this.v = true;
        this.w = true;
        this.x = true;
        this.d = new RouteDatabase();
        this.e = new Dispatcher();
    }

    private OkHttpClient(OkHttpClient okHttpClient) {
        this.i = new ArrayList();
        this.j = new ArrayList();
        this.v = true;
        this.w = true;
        this.x = true;
        this.d = okHttpClient.d;
        this.e = okHttpClient.e;
        this.f = okHttpClient.f;
        this.g = okHttpClient.g;
        this.h = okHttpClient.h;
        this.i.addAll(okHttpClient.i);
        this.j.addAll(okHttpClient.j);
        this.k = okHttpClient.k;
        this.l = okHttpClient.l;
        this.n = okHttpClient.n;
        this.m = this.n != null ? this.n.a : okHttpClient.m;
        this.o = okHttpClient.o;
        this.p = okHttpClient.p;
        this.q = okHttpClient.q;
        this.r = okHttpClient.r;
        this.s = okHttpClient.s;
        this.t = okHttpClient.t;
        this.u = okHttpClient.u;
        this.v = okHttpClient.v;
        this.w = okHttpClient.w;
        this.x = okHttpClient.x;
        this.y = okHttpClient.y;
        this.z = okHttpClient.z;
        this.A = okHttpClient.A;
    }

    public void setConnectTimeout(long j2, TimeUnit timeUnit) {
        if (j2 < 0) {
            throw new IllegalArgumentException("timeout < 0");
        } else if (timeUnit == null) {
            throw new IllegalArgumentException("unit == null");
        } else {
            long millis = timeUnit.toMillis(j2);
            if (millis > 2147483647L) {
                throw new IllegalArgumentException("Timeout too large.");
            } else if (millis != 0 || j2 <= 0) {
                this.y = (int) millis;
            } else {
                throw new IllegalArgumentException("Timeout too small.");
            }
        }
    }

    public int getConnectTimeout() {
        return this.y;
    }

    public void setReadTimeout(long j2, TimeUnit timeUnit) {
        if (j2 < 0) {
            throw new IllegalArgumentException("timeout < 0");
        } else if (timeUnit == null) {
            throw new IllegalArgumentException("unit == null");
        } else {
            long millis = timeUnit.toMillis(j2);
            if (millis > 2147483647L) {
                throw new IllegalArgumentException("Timeout too large.");
            } else if (millis != 0 || j2 <= 0) {
                this.z = (int) millis;
            } else {
                throw new IllegalArgumentException("Timeout too small.");
            }
        }
    }

    public int getReadTimeout() {
        return this.z;
    }

    public void setWriteTimeout(long j2, TimeUnit timeUnit) {
        if (j2 < 0) {
            throw new IllegalArgumentException("timeout < 0");
        } else if (timeUnit == null) {
            throw new IllegalArgumentException("unit == null");
        } else {
            long millis = timeUnit.toMillis(j2);
            if (millis > 2147483647L) {
                throw new IllegalArgumentException("Timeout too large.");
            } else if (millis != 0 || j2 <= 0) {
                this.A = (int) millis;
            } else {
                throw new IllegalArgumentException("Timeout too small.");
            }
        }
    }

    public int getWriteTimeout() {
        return this.A;
    }

    public OkHttpClient setProxy(Proxy proxy) {
        this.f = proxy;
        return this;
    }

    public Proxy getProxy() {
        return this.f;
    }

    public OkHttpClient setProxySelector(ProxySelector proxySelector) {
        this.k = proxySelector;
        return this;
    }

    public ProxySelector getProxySelector() {
        return this.k;
    }

    public OkHttpClient setCookieHandler(CookieHandler cookieHandler) {
        this.l = cookieHandler;
        return this;
    }

    public CookieHandler getCookieHandler() {
        return this.l;
    }

    /* access modifiers changed from: 0000 */
    public void a(InternalCache internalCache) {
        this.m = internalCache;
        this.n = null;
    }

    /* access modifiers changed from: 0000 */
    public InternalCache a() {
        return this.m;
    }

    public OkHttpClient setCache(Cache cache) {
        this.n = cache;
        this.m = null;
        return this;
    }

    public Cache getCache() {
        return this.n;
    }

    public OkHttpClient setSocketFactory(SocketFactory socketFactory) {
        this.o = socketFactory;
        return this;
    }

    public SocketFactory getSocketFactory() {
        return this.o;
    }

    public OkHttpClient setSslSocketFactory(SSLSocketFactory sSLSocketFactory) {
        this.p = sSLSocketFactory;
        return this;
    }

    public SSLSocketFactory getSslSocketFactory() {
        return this.p;
    }

    public OkHttpClient setHostnameVerifier(HostnameVerifier hostnameVerifier) {
        this.q = hostnameVerifier;
        return this;
    }

    public HostnameVerifier getHostnameVerifier() {
        return this.q;
    }

    public OkHttpClient setCertificatePinner(CertificatePinner certificatePinner) {
        this.r = certificatePinner;
        return this;
    }

    public CertificatePinner getCertificatePinner() {
        return this.r;
    }

    public OkHttpClient setAuthenticator(Authenticator authenticator) {
        this.s = authenticator;
        return this;
    }

    public Authenticator getAuthenticator() {
        return this.s;
    }

    public OkHttpClient setConnectionPool(ConnectionPool connectionPool) {
        this.t = connectionPool;
        return this;
    }

    public ConnectionPool getConnectionPool() {
        return this.t;
    }

    public OkHttpClient setFollowSslRedirects(boolean z2) {
        this.v = z2;
        return this;
    }

    public boolean getFollowSslRedirects() {
        return this.v;
    }

    public void setFollowRedirects(boolean z2) {
        this.w = z2;
    }

    public boolean getFollowRedirects() {
        return this.w;
    }

    public void setRetryOnConnectionFailure(boolean z2) {
        this.x = z2;
    }

    public boolean getRetryOnConnectionFailure() {
        return this.x;
    }

    /* access modifiers changed from: 0000 */
    public RouteDatabase b() {
        return this.d;
    }

    public OkHttpClient setDispatcher(Dispatcher dispatcher) {
        if (dispatcher == null) {
            throw new IllegalArgumentException("dispatcher == null");
        }
        this.e = dispatcher;
        return this;
    }

    public Dispatcher getDispatcher() {
        return this.e;
    }

    public OkHttpClient setProtocols(List<Protocol> list) {
        List immutableList = Util.immutableList(list);
        if (!immutableList.contains(Protocol.HTTP_1_1)) {
            StringBuilder sb = new StringBuilder();
            sb.append("protocols doesn't contain http/1.1: ");
            sb.append(immutableList);
            throw new IllegalArgumentException(sb.toString());
        } else if (immutableList.contains(Protocol.HTTP_1_0)) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("protocols must not contain http/1.0: ");
            sb2.append(immutableList);
            throw new IllegalArgumentException(sb2.toString());
        } else if (immutableList.contains(null)) {
            throw new IllegalArgumentException("protocols must not contain null");
        } else {
            this.g = Util.immutableList(immutableList);
            return this;
        }
    }

    public List<Protocol> getProtocols() {
        return this.g;
    }

    public OkHttpClient setConnectionSpecs(List<ConnectionSpec> list) {
        this.h = Util.immutableList(list);
        return this;
    }

    public List<ConnectionSpec> getConnectionSpecs() {
        return this.h;
    }

    public List<Interceptor> interceptors() {
        return this.i;
    }

    public List<Interceptor> networkInterceptors() {
        return this.j;
    }

    public Call newCall(Request request) {
        return new Call(this, request);
    }

    public OkHttpClient cancel(Object obj) {
        getDispatcher().cancel(obj);
        return this;
    }

    /* access modifiers changed from: 0000 */
    public OkHttpClient c() {
        OkHttpClient okHttpClient = new OkHttpClient(this);
        if (okHttpClient.k == null) {
            okHttpClient.k = ProxySelector.getDefault();
        }
        if (okHttpClient.l == null) {
            okHttpClient.l = CookieHandler.getDefault();
        }
        if (okHttpClient.o == null) {
            okHttpClient.o = SocketFactory.getDefault();
        }
        if (okHttpClient.p == null) {
            okHttpClient.p = d();
        }
        if (okHttpClient.q == null) {
            okHttpClient.q = OkHostnameVerifier.INSTANCE;
        }
        if (okHttpClient.r == null) {
            okHttpClient.r = CertificatePinner.DEFAULT;
        }
        if (okHttpClient.s == null) {
            okHttpClient.s = AuthenticatorAdapter.INSTANCE;
        }
        if (okHttpClient.t == null) {
            okHttpClient.t = ConnectionPool.getDefault();
        }
        if (okHttpClient.g == null) {
            okHttpClient.g = a;
        }
        if (okHttpClient.h == null) {
            okHttpClient.h = b;
        }
        if (okHttpClient.u == null) {
            okHttpClient.u = Network.DEFAULT;
        }
        return okHttpClient;
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(6:4|5|6|7|8|9) */
    /* JADX WARNING: Missing exception handler attribute for start block: B:7:0x0016 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private synchronized javax.net.ssl.SSLSocketFactory d() {
        /*
            r2 = this;
            monitor-enter(r2)
            javax.net.ssl.SSLSocketFactory r0 = c     // Catch:{ all -> 0x0020 }
            if (r0 != 0) goto L_0x001c
            java.lang.String r0 = "TLS"
            javax.net.ssl.SSLContext r0 = javax.net.ssl.SSLContext.getInstance(r0)     // Catch:{ GeneralSecurityException -> 0x0016 }
            r1 = 0
            r0.init(r1, r1, r1)     // Catch:{ GeneralSecurityException -> 0x0016 }
            javax.net.ssl.SSLSocketFactory r0 = r0.getSocketFactory()     // Catch:{ GeneralSecurityException -> 0x0016 }
            c = r0     // Catch:{ GeneralSecurityException -> 0x0016 }
            goto L_0x001c
        L_0x0016:
            java.lang.AssertionError r0 = new java.lang.AssertionError     // Catch:{ all -> 0x0020 }
            r0.<init>()     // Catch:{ all -> 0x0020 }
            throw r0     // Catch:{ all -> 0x0020 }
        L_0x001c:
            javax.net.ssl.SSLSocketFactory r0 = c     // Catch:{ all -> 0x0020 }
            monitor-exit(r2)
            return r0
        L_0x0020:
            r0 = move-exception
            monitor-exit(r2)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.squareup.okhttp.OkHttpClient.d():javax.net.ssl.SSLSocketFactory");
    }

    public OkHttpClient clone() {
        return new OkHttpClient(this);
    }
}
