package okhttp3;

import java.net.Proxy;
import java.net.ProxySelector;
import java.net.Socket;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import javax.annotation.Nullable;
import javax.net.SocketFactory;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import okhttp3.Call.Factory;
import okhttp3.internal.Internal;
import okhttp3.internal.Util;
import okhttp3.internal.cache.InternalCache;
import okhttp3.internal.connection.RealConnection;
import okhttp3.internal.connection.RouteDatabase;
import okhttp3.internal.connection.StreamAllocation;
import okhttp3.internal.platform.Platform;
import okhttp3.internal.tls.CertificateChainCleaner;
import okhttp3.internal.tls.OkHostnameVerifier;
import okhttp3.internal.ws.RealWebSocket;

public class OkHttpClient implements Cloneable, Factory, WebSocket.Factory {
    static final List<Protocol> a = Util.immutableList((T[]) new Protocol[]{Protocol.HTTP_2, Protocol.HTTP_1_1});
    static final List<ConnectionSpec> b = Util.immutableList((T[]) new ConnectionSpec[]{ConnectionSpec.MODERN_TLS, ConnectionSpec.CLEARTEXT});
    final int A;
    final int B;
    final int C;
    final Dispatcher c;
    @Nullable
    final Proxy d;
    final List<Protocol> e;
    final List<ConnectionSpec> f;
    final List<Interceptor> g;
    final List<Interceptor> h;
    final EventListener.Factory i;
    final ProxySelector j;
    final CookieJar k;
    @Nullable
    final Cache l;
    @Nullable
    final InternalCache m;
    final SocketFactory n;
    @Nullable
    final SSLSocketFactory o;
    @Nullable
    final CertificateChainCleaner p;
    final HostnameVerifier q;
    final CertificatePinner r;
    final Authenticator s;
    final Authenticator t;
    final ConnectionPool u;
    final Dns v;
    final boolean w;
    final boolean x;
    final boolean y;
    final int z;

    public static final class Builder {
        int A;
        Dispatcher a;
        @Nullable
        Proxy b;
        List<Protocol> c;
        List<ConnectionSpec> d;
        final List<Interceptor> e;
        final List<Interceptor> f;
        EventListener.Factory g;
        ProxySelector h;
        CookieJar i;
        @Nullable
        Cache j;
        @Nullable
        InternalCache k;
        SocketFactory l;
        @Nullable
        SSLSocketFactory m;
        @Nullable
        CertificateChainCleaner n;
        HostnameVerifier o;
        CertificatePinner p;
        Authenticator q;
        Authenticator r;
        ConnectionPool s;
        Dns t;
        boolean u;
        boolean v;
        boolean w;
        int x;
        int y;
        int z;

        public Builder() {
            this.e = new ArrayList();
            this.f = new ArrayList();
            this.a = new Dispatcher();
            this.c = OkHttpClient.a;
            this.d = OkHttpClient.b;
            this.g = EventListener.a(EventListener.NONE);
            this.h = ProxySelector.getDefault();
            this.i = CookieJar.NO_COOKIES;
            this.l = SocketFactory.getDefault();
            this.o = OkHostnameVerifier.INSTANCE;
            this.p = CertificatePinner.DEFAULT;
            this.q = Authenticator.NONE;
            this.r = Authenticator.NONE;
            this.s = new ConnectionPool();
            this.t = Dns.SYSTEM;
            this.u = true;
            this.v = true;
            this.w = true;
            this.x = 10000;
            this.y = 10000;
            this.z = 10000;
            this.A = 0;
        }

        Builder(OkHttpClient okHttpClient) {
            this.e = new ArrayList();
            this.f = new ArrayList();
            this.a = okHttpClient.c;
            this.b = okHttpClient.d;
            this.c = okHttpClient.e;
            this.d = okHttpClient.f;
            this.e.addAll(okHttpClient.g);
            this.f.addAll(okHttpClient.h);
            this.g = okHttpClient.i;
            this.h = okHttpClient.j;
            this.i = okHttpClient.k;
            this.k = okHttpClient.m;
            this.j = okHttpClient.l;
            this.l = okHttpClient.n;
            this.m = okHttpClient.o;
            this.n = okHttpClient.p;
            this.o = okHttpClient.q;
            this.p = okHttpClient.r;
            this.q = okHttpClient.s;
            this.r = okHttpClient.t;
            this.s = okHttpClient.u;
            this.t = okHttpClient.v;
            this.u = okHttpClient.w;
            this.v = okHttpClient.x;
            this.w = okHttpClient.y;
            this.x = okHttpClient.z;
            this.y = okHttpClient.A;
            this.z = okHttpClient.B;
            this.A = okHttpClient.C;
        }

        public Builder connectTimeout(long j2, TimeUnit timeUnit) {
            this.x = Util.checkDuration("timeout", j2, timeUnit);
            return this;
        }

        public Builder readTimeout(long j2, TimeUnit timeUnit) {
            this.y = Util.checkDuration("timeout", j2, timeUnit);
            return this;
        }

        public Builder writeTimeout(long j2, TimeUnit timeUnit) {
            this.z = Util.checkDuration("timeout", j2, timeUnit);
            return this;
        }

        public Builder pingInterval(long j2, TimeUnit timeUnit) {
            this.A = Util.checkDuration("interval", j2, timeUnit);
            return this;
        }

        public Builder proxy(@Nullable Proxy proxy) {
            this.b = proxy;
            return this;
        }

        public Builder proxySelector(ProxySelector proxySelector) {
            this.h = proxySelector;
            return this;
        }

        public Builder cookieJar(CookieJar cookieJar) {
            if (cookieJar == null) {
                throw new NullPointerException("cookieJar == null");
            }
            this.i = cookieJar;
            return this;
        }

        /* access modifiers changed from: 0000 */
        public void a(@Nullable InternalCache internalCache) {
            this.k = internalCache;
            this.j = null;
        }

        public Builder cache(@Nullable Cache cache) {
            this.j = cache;
            this.k = null;
            return this;
        }

        public Builder dns(Dns dns) {
            if (dns == null) {
                throw new NullPointerException("dns == null");
            }
            this.t = dns;
            return this;
        }

        public Builder socketFactory(SocketFactory socketFactory) {
            if (socketFactory == null) {
                throw new NullPointerException("socketFactory == null");
            }
            this.l = socketFactory;
            return this;
        }

        public Builder sslSocketFactory(SSLSocketFactory sSLSocketFactory) {
            if (sSLSocketFactory == null) {
                throw new NullPointerException("sslSocketFactory == null");
            }
            this.m = sSLSocketFactory;
            this.n = Platform.get().buildCertificateChainCleaner(sSLSocketFactory);
            return this;
        }

        public Builder sslSocketFactory(SSLSocketFactory sSLSocketFactory, X509TrustManager x509TrustManager) {
            if (sSLSocketFactory == null) {
                throw new NullPointerException("sslSocketFactory == null");
            } else if (x509TrustManager == null) {
                throw new NullPointerException("trustManager == null");
            } else {
                this.m = sSLSocketFactory;
                this.n = CertificateChainCleaner.get(x509TrustManager);
                return this;
            }
        }

        public Builder hostnameVerifier(HostnameVerifier hostnameVerifier) {
            if (hostnameVerifier == null) {
                throw new NullPointerException("hostnameVerifier == null");
            }
            this.o = hostnameVerifier;
            return this;
        }

        public Builder certificatePinner(CertificatePinner certificatePinner) {
            if (certificatePinner == null) {
                throw new NullPointerException("certificatePinner == null");
            }
            this.p = certificatePinner;
            return this;
        }

        public Builder authenticator(Authenticator authenticator) {
            if (authenticator == null) {
                throw new NullPointerException("authenticator == null");
            }
            this.r = authenticator;
            return this;
        }

        public Builder proxyAuthenticator(Authenticator authenticator) {
            if (authenticator == null) {
                throw new NullPointerException("proxyAuthenticator == null");
            }
            this.q = authenticator;
            return this;
        }

        public Builder connectionPool(ConnectionPool connectionPool) {
            if (connectionPool == null) {
                throw new NullPointerException("connectionPool == null");
            }
            this.s = connectionPool;
            return this;
        }

        public Builder followSslRedirects(boolean z2) {
            this.u = z2;
            return this;
        }

        public Builder followRedirects(boolean z2) {
            this.v = z2;
            return this;
        }

        public Builder retryOnConnectionFailure(boolean z2) {
            this.w = z2;
            return this;
        }

        public Builder dispatcher(Dispatcher dispatcher) {
            if (dispatcher == null) {
                throw new IllegalArgumentException("dispatcher == null");
            }
            this.a = dispatcher;
            return this;
        }

        public Builder protocols(List<Protocol> list) {
            ArrayList arrayList = new ArrayList(list);
            if (!arrayList.contains(Protocol.H2_PRIOR_KNOWLEDGE) && !arrayList.contains(Protocol.HTTP_1_1)) {
                StringBuilder sb = new StringBuilder();
                sb.append("protocols must contain h2_prior_knowledge or http/1.1: ");
                sb.append(arrayList);
                throw new IllegalArgumentException(sb.toString());
            } else if (arrayList.contains(Protocol.H2_PRIOR_KNOWLEDGE) && arrayList.size() > 1) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append("protocols containing h2_prior_knowledge cannot use other protocols: ");
                sb2.append(arrayList);
                throw new IllegalArgumentException(sb2.toString());
            } else if (arrayList.contains(Protocol.HTTP_1_0)) {
                StringBuilder sb3 = new StringBuilder();
                sb3.append("protocols must not contain http/1.0: ");
                sb3.append(arrayList);
                throw new IllegalArgumentException(sb3.toString());
            } else if (arrayList.contains(null)) {
                throw new IllegalArgumentException("protocols must not contain null");
            } else {
                arrayList.remove(Protocol.SPDY_3);
                this.c = Collections.unmodifiableList(arrayList);
                return this;
            }
        }

        public Builder connectionSpecs(List<ConnectionSpec> list) {
            this.d = Util.immutableList(list);
            return this;
        }

        public List<Interceptor> interceptors() {
            return this.e;
        }

        public Builder addInterceptor(Interceptor interceptor) {
            if (interceptor == null) {
                throw new IllegalArgumentException("interceptor == null");
            }
            this.e.add(interceptor);
            return this;
        }

        public List<Interceptor> networkInterceptors() {
            return this.f;
        }

        public Builder addNetworkInterceptor(Interceptor interceptor) {
            if (interceptor == null) {
                throw new IllegalArgumentException("interceptor == null");
            }
            this.f.add(interceptor);
            return this;
        }

        public Builder eventListener(EventListener eventListener) {
            if (eventListener == null) {
                throw new NullPointerException("eventListener == null");
            }
            this.g = EventListener.a(eventListener);
            return this;
        }

        public Builder eventListenerFactory(EventListener.Factory factory) {
            if (factory == null) {
                throw new NullPointerException("eventListenerFactory == null");
            }
            this.g = factory;
            return this;
        }

        public OkHttpClient build() {
            return new OkHttpClient(this);
        }
    }

    static {
        Internal.instance = new Internal() {
            public void addLenient(okhttp3.Headers.Builder builder, String str) {
                builder.a(str);
            }

            public void addLenient(okhttp3.Headers.Builder builder, String str, String str2) {
                builder.a(str, str2);
            }

            public void setCache(Builder builder, InternalCache internalCache) {
                builder.a(internalCache);
            }

            public boolean connectionBecameIdle(ConnectionPool connectionPool, RealConnection realConnection) {
                return connectionPool.b(realConnection);
            }

            public RealConnection get(ConnectionPool connectionPool, Address address, StreamAllocation streamAllocation, Route route) {
                return connectionPool.a(address, streamAllocation, route);
            }

            public boolean equalsNonHost(Address address, Address address2) {
                return address.a(address2);
            }

            public Socket deduplicate(ConnectionPool connectionPool, Address address, StreamAllocation streamAllocation) {
                return connectionPool.a(address, streamAllocation);
            }

            public void put(ConnectionPool connectionPool, RealConnection realConnection) {
                connectionPool.a(realConnection);
            }

            public RouteDatabase routeDatabase(ConnectionPool connectionPool) {
                return connectionPool.a;
            }

            public int code(okhttp3.Response.Builder builder) {
                return builder.c;
            }

            public void apply(ConnectionSpec connectionSpec, SSLSocket sSLSocket, boolean z) {
                connectionSpec.a(sSLSocket, z);
            }

            public boolean isInvalidHttpUrlHost(IllegalArgumentException illegalArgumentException) {
                return illegalArgumentException.getMessage().startsWith("Invalid URL host");
            }

            public StreamAllocation streamAllocation(Call call) {
                return ((RealCall) call).b();
            }

            public Call newWebSocketCall(OkHttpClient okHttpClient, Request request) {
                return RealCall.a(okHttpClient, request, true);
            }
        };
    }

    public OkHttpClient() {
        this(new Builder());
    }

    OkHttpClient(Builder builder) {
        boolean z2;
        this.c = builder.a;
        this.d = builder.b;
        this.e = builder.c;
        this.f = builder.d;
        this.g = Util.immutableList(builder.e);
        this.h = Util.immutableList(builder.f);
        this.i = builder.g;
        this.j = builder.h;
        this.k = builder.i;
        this.l = builder.j;
        this.m = builder.k;
        this.n = builder.l;
        Iterator it = this.f.iterator();
        loop0:
        while (true) {
            z2 = false;
            while (true) {
                if (!it.hasNext()) {
                    break loop0;
                }
                ConnectionSpec connectionSpec = (ConnectionSpec) it.next();
                if (z2 || connectionSpec.isTls()) {
                    z2 = true;
                }
            }
        }
        if (builder.m != null || !z2) {
            this.o = builder.m;
            this.p = builder.n;
        } else {
            X509TrustManager platformTrustManager = Util.platformTrustManager();
            this.o = a(platformTrustManager);
            this.p = CertificateChainCleaner.get(platformTrustManager);
        }
        if (this.o != null) {
            Platform.get().configureSslSocketFactory(this.o);
        }
        this.q = builder.o;
        this.r = builder.p.a(this.p);
        this.s = builder.q;
        this.t = builder.r;
        this.u = builder.s;
        this.v = builder.t;
        this.w = builder.u;
        this.x = builder.v;
        this.y = builder.w;
        this.z = builder.x;
        this.A = builder.y;
        this.B = builder.z;
        this.C = builder.A;
        if (this.g.contains(null)) {
            StringBuilder sb = new StringBuilder();
            sb.append("Null interceptor: ");
            sb.append(this.g);
            throw new IllegalStateException(sb.toString());
        } else if (this.h.contains(null)) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Null network interceptor: ");
            sb2.append(this.h);
            throw new IllegalStateException(sb2.toString());
        }
    }

    private static SSLSocketFactory a(X509TrustManager x509TrustManager) {
        try {
            SSLContext sSLContext = Platform.get().getSSLContext();
            sSLContext.init(null, new TrustManager[]{x509TrustManager}, null);
            return sSLContext.getSocketFactory();
        } catch (GeneralSecurityException e2) {
            throw Util.assertionError("No System TLS", e2);
        }
    }

    public int connectTimeoutMillis() {
        return this.z;
    }

    public int readTimeoutMillis() {
        return this.A;
    }

    public int writeTimeoutMillis() {
        return this.B;
    }

    public int pingIntervalMillis() {
        return this.C;
    }

    public Proxy proxy() {
        return this.d;
    }

    public ProxySelector proxySelector() {
        return this.j;
    }

    public CookieJar cookieJar() {
        return this.k;
    }

    @Nullable
    public Cache cache() {
        return this.l;
    }

    /* access modifiers changed from: 0000 */
    public InternalCache a() {
        return this.l != null ? this.l.a : this.m;
    }

    public Dns dns() {
        return this.v;
    }

    public SocketFactory socketFactory() {
        return this.n;
    }

    public SSLSocketFactory sslSocketFactory() {
        return this.o;
    }

    public HostnameVerifier hostnameVerifier() {
        return this.q;
    }

    public CertificatePinner certificatePinner() {
        return this.r;
    }

    public Authenticator authenticator() {
        return this.t;
    }

    public Authenticator proxyAuthenticator() {
        return this.s;
    }

    public ConnectionPool connectionPool() {
        return this.u;
    }

    public boolean followSslRedirects() {
        return this.w;
    }

    public boolean followRedirects() {
        return this.x;
    }

    public boolean retryOnConnectionFailure() {
        return this.y;
    }

    public Dispatcher dispatcher() {
        return this.c;
    }

    public List<Protocol> protocols() {
        return this.e;
    }

    public List<ConnectionSpec> connectionSpecs() {
        return this.f;
    }

    public List<Interceptor> interceptors() {
        return this.g;
    }

    public List<Interceptor> networkInterceptors() {
        return this.h;
    }

    public EventListener.Factory eventListenerFactory() {
        return this.i;
    }

    public Call newCall(Request request) {
        return RealCall.a(this, request, false);
    }

    public WebSocket newWebSocket(Request request, WebSocketListener webSocketListener) {
        RealWebSocket realWebSocket = new RealWebSocket(request, webSocketListener, new Random(), (long) this.C);
        realWebSocket.connect(this);
        return realWebSocket;
    }

    public Builder newBuilder() {
        return new Builder(this);
    }
}
