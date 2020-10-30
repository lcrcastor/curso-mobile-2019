package cz.msebera.android.httpclient.impl.conn;

import ar.com.santander.rio.mbanking.utils.UtilsCuentas;
import cz.msebera.android.httpclient.HttpClientConnection;
import cz.msebera.android.httpclient.HttpHost;
import cz.msebera.android.httpclient.annotation.ThreadSafe;
import cz.msebera.android.httpclient.config.ConnectionConfig;
import cz.msebera.android.httpclient.config.Registry;
import cz.msebera.android.httpclient.config.RegistryBuilder;
import cz.msebera.android.httpclient.config.SocketConfig;
import cz.msebera.android.httpclient.conn.ConnectionPoolTimeoutException;
import cz.msebera.android.httpclient.conn.ConnectionRequest;
import cz.msebera.android.httpclient.conn.DnsResolver;
import cz.msebera.android.httpclient.conn.HttpClientConnectionManager;
import cz.msebera.android.httpclient.conn.HttpClientConnectionOperator;
import cz.msebera.android.httpclient.conn.HttpConnectionFactory;
import cz.msebera.android.httpclient.conn.ManagedHttpClientConnection;
import cz.msebera.android.httpclient.conn.SchemePortResolver;
import cz.msebera.android.httpclient.conn.routing.HttpRoute;
import cz.msebera.android.httpclient.conn.socket.ConnectionSocketFactory;
import cz.msebera.android.httpclient.conn.socket.PlainConnectionSocketFactory;
import cz.msebera.android.httpclient.conn.ssl.SSLConnectionSocketFactory;
import cz.msebera.android.httpclient.extras.HttpClientAndroidLog;
import cz.msebera.android.httpclient.pool.ConnFactory;
import cz.msebera.android.httpclient.pool.ConnPoolControl;
import cz.msebera.android.httpclient.pool.PoolStats;
import cz.msebera.android.httpclient.protocol.HttpContext;
import cz.msebera.android.httpclient.util.Args;
import cz.msebera.android.httpclient.util.Asserts;
import java.io.Closeable;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;

@ThreadSafe
public class PoolingHttpClientConnectionManager implements HttpClientConnectionManager, ConnPoolControl<HttpRoute>, Closeable {
    private final ConfigData a;
    private final CPool b;
    private final HttpClientConnectionOperator c;
    private final AtomicBoolean d;
    public HttpClientAndroidLog log;

    static class ConfigData {
        private final Map<HttpHost, SocketConfig> a = new ConcurrentHashMap();
        private final Map<HttpHost, ConnectionConfig> b = new ConcurrentHashMap();
        private volatile SocketConfig c;
        private volatile ConnectionConfig d;

        ConfigData() {
        }

        public SocketConfig a() {
            return this.c;
        }

        public void a(SocketConfig socketConfig) {
            this.c = socketConfig;
        }

        public ConnectionConfig b() {
            return this.d;
        }

        public void a(ConnectionConfig connectionConfig) {
            this.d = connectionConfig;
        }

        public SocketConfig a(HttpHost httpHost) {
            return (SocketConfig) this.a.get(httpHost);
        }

        public void a(HttpHost httpHost, SocketConfig socketConfig) {
            this.a.put(httpHost, socketConfig);
        }

        public ConnectionConfig b(HttpHost httpHost) {
            return (ConnectionConfig) this.b.get(httpHost);
        }

        public void a(HttpHost httpHost, ConnectionConfig connectionConfig) {
            this.b.put(httpHost, connectionConfig);
        }
    }

    static class InternalConnectionFactory implements ConnFactory<HttpRoute, ManagedHttpClientConnection> {
        private final ConfigData a;
        private final HttpConnectionFactory<HttpRoute, ManagedHttpClientConnection> b;

        InternalConnectionFactory(ConfigData configData, HttpConnectionFactory<HttpRoute, ManagedHttpClientConnection> httpConnectionFactory) {
            if (configData == null) {
                configData = new ConfigData();
            }
            this.a = configData;
            if (httpConnectionFactory == null) {
                httpConnectionFactory = ManagedHttpClientConnectionFactory.INSTANCE;
            }
            this.b = httpConnectionFactory;
        }

        /* renamed from: a */
        public ManagedHttpClientConnection create(HttpRoute httpRoute) {
            ConnectionConfig b2 = httpRoute.getProxyHost() != null ? this.a.b(httpRoute.getProxyHost()) : null;
            if (b2 == null) {
                b2 = this.a.b(httpRoute.getTargetHost());
            }
            if (b2 == null) {
                b2 = this.a.b();
            }
            if (b2 == null) {
                b2 = ConnectionConfig.DEFAULT;
            }
            return (ManagedHttpClientConnection) this.b.create(httpRoute, b2);
        }
    }

    private static Registry<ConnectionSocketFactory> a() {
        return RegistryBuilder.create().register(HttpHost.DEFAULT_SCHEME_NAME, PlainConnectionSocketFactory.getSocketFactory()).register("https", SSLConnectionSocketFactory.getSocketFactory()).build();
    }

    public PoolingHttpClientConnectionManager() {
        this(a());
    }

    public PoolingHttpClientConnectionManager(long j, TimeUnit timeUnit) {
        this(a(), null, null, null, j, timeUnit);
    }

    public PoolingHttpClientConnectionManager(Registry<ConnectionSocketFactory> registry) {
        this(registry, null, null);
    }

    public PoolingHttpClientConnectionManager(Registry<ConnectionSocketFactory> registry, DnsResolver dnsResolver) {
        this(registry, null, dnsResolver);
    }

    public PoolingHttpClientConnectionManager(Registry<ConnectionSocketFactory> registry, HttpConnectionFactory<HttpRoute, ManagedHttpClientConnection> httpConnectionFactory) {
        this(registry, httpConnectionFactory, null);
    }

    public PoolingHttpClientConnectionManager(HttpConnectionFactory<HttpRoute, ManagedHttpClientConnection> httpConnectionFactory) {
        this(a(), httpConnectionFactory, null);
    }

    public PoolingHttpClientConnectionManager(Registry<ConnectionSocketFactory> registry, HttpConnectionFactory<HttpRoute, ManagedHttpClientConnection> httpConnectionFactory, DnsResolver dnsResolver) {
        this(registry, httpConnectionFactory, null, dnsResolver, -1, TimeUnit.MILLISECONDS);
    }

    public PoolingHttpClientConnectionManager(Registry<ConnectionSocketFactory> registry, HttpConnectionFactory<HttpRoute, ManagedHttpClientConnection> httpConnectionFactory, SchemePortResolver schemePortResolver, DnsResolver dnsResolver, long j, TimeUnit timeUnit) {
        this(new DefaultHttpClientConnectionOperator(registry, schemePortResolver, dnsResolver), httpConnectionFactory, j, timeUnit);
    }

    public PoolingHttpClientConnectionManager(HttpClientConnectionOperator httpClientConnectionOperator, HttpConnectionFactory<HttpRoute, ManagedHttpClientConnection> httpConnectionFactory, long j, TimeUnit timeUnit) {
        this.log = new HttpClientAndroidLog(getClass());
        this.a = new ConfigData();
        CPool cPool = new CPool(new InternalConnectionFactory(this.a, httpConnectionFactory), 2, 20, j, timeUnit);
        this.b = cPool;
        this.b.setValidateAfterInactivity(5000);
        this.c = (HttpClientConnectionOperator) Args.notNull(httpClientConnectionOperator, "HttpClientConnectionOperator");
        this.d = new AtomicBoolean(false);
    }

    /* access modifiers changed from: protected */
    public void finalize() {
        try {
            shutdown();
        } finally {
            super.finalize();
        }
    }

    public void close() {
        shutdown();
    }

    private String a(HttpRoute httpRoute, Object obj) {
        StringBuilder sb = new StringBuilder();
        sb.append("[route: ");
        sb.append(httpRoute);
        sb.append("]");
        if (obj != null) {
            sb.append("[state: ");
            sb.append(obj);
            sb.append("]");
        }
        return sb.toString();
    }

    private String a(HttpRoute httpRoute) {
        StringBuilder sb = new StringBuilder();
        PoolStats totalStats = this.b.getTotalStats();
        PoolStats stats = this.b.getStats(httpRoute);
        sb.append("[total kept alive: ");
        sb.append(totalStats.getAvailable());
        sb.append("; ");
        sb.append("route allocated: ");
        sb.append(stats.getLeased() + stats.getAvailable());
        sb.append(" of ");
        sb.append(stats.getMax());
        sb.append("; ");
        sb.append("total allocated: ");
        sb.append(totalStats.getLeased() + totalStats.getAvailable());
        sb.append(" of ");
        sb.append(totalStats.getMax());
        sb.append("]");
        return sb.toString();
    }

    private String a(CPoolEntry cPoolEntry) {
        StringBuilder sb = new StringBuilder();
        sb.append("[id: ");
        sb.append(cPoolEntry.getId());
        sb.append("]");
        sb.append("[route: ");
        sb.append(cPoolEntry.getRoute());
        sb.append("]");
        Object state = cPoolEntry.getState();
        if (state != null) {
            sb.append("[state: ");
            sb.append(state);
            sb.append("]");
        }
        return sb.toString();
    }

    public ConnectionRequest requestConnection(HttpRoute httpRoute, Object obj) {
        Args.notNull(httpRoute, "HTTP route");
        if (this.log.isDebugEnabled()) {
            HttpClientAndroidLog httpClientAndroidLog = this.log;
            StringBuilder sb = new StringBuilder();
            sb.append("Connection request: ");
            sb.append(a(httpRoute, obj));
            sb.append(a(httpRoute));
            httpClientAndroidLog.debug(sb.toString());
        }
        final Future lease = this.b.lease(httpRoute, obj, null);
        return new ConnectionRequest() {
            public boolean cancel() {
                return lease.cancel(true);
            }

            public HttpClientConnection get(long j, TimeUnit timeUnit) {
                return PoolingHttpClientConnectionManager.this.leaseConnection(lease, j, timeUnit);
            }
        };
    }

    /* access modifiers changed from: protected */
    public HttpClientConnection leaseConnection(Future<CPoolEntry> future, long j, TimeUnit timeUnit) {
        try {
            CPoolEntry cPoolEntry = (CPoolEntry) future.get(j, timeUnit);
            if (cPoolEntry != null) {
                if (!future.isCancelled()) {
                    Asserts.check(cPoolEntry.getConnection() != null, "Pool entry with no connection");
                    if (this.log.isDebugEnabled()) {
                        HttpClientAndroidLog httpClientAndroidLog = this.log;
                        StringBuilder sb = new StringBuilder();
                        sb.append("Connection leased: ");
                        sb.append(a(cPoolEntry));
                        sb.append(a((HttpRoute) cPoolEntry.getRoute()));
                        httpClientAndroidLog.debug(sb.toString());
                    }
                    return CPoolProxy.a(cPoolEntry);
                }
            }
            throw new InterruptedException();
        } catch (TimeoutException unused) {
            throw new ConnectionPoolTimeoutException("Timeout waiting for connection from pool");
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:32:0x00bd, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void releaseConnection(cz.msebera.android.httpclient.HttpClientConnection r7, java.lang.Object r8, long r9, java.util.concurrent.TimeUnit r11) {
        /*
            r6 = this;
            java.lang.String r0 = "Managed connection"
            cz.msebera.android.httpclient.util.Args.notNull(r7, r0)
            monitor-enter(r7)
            cz.msebera.android.httpclient.impl.conn.CPoolEntry r0 = cz.msebera.android.httpclient.impl.conn.CPoolProxy.b(r7)     // Catch:{ all -> 0x0101 }
            if (r0 != 0) goto L_0x000e
            monitor-exit(r7)     // Catch:{ all -> 0x0101 }
            return
        L_0x000e:
            java.lang.Object r1 = r0.getConnection()     // Catch:{ all -> 0x0101 }
            cz.msebera.android.httpclient.conn.ManagedHttpClientConnection r1 = (cz.msebera.android.httpclient.conn.ManagedHttpClientConnection) r1     // Catch:{ all -> 0x0101 }
            r2 = 0
            r3 = 1
            boolean r4 = r1.isOpen()     // Catch:{ all -> 0x00be }
            if (r4 == 0) goto L_0x007b
            if (r11 == 0) goto L_0x001f
            goto L_0x0021
        L_0x001f:
            java.util.concurrent.TimeUnit r11 = java.util.concurrent.TimeUnit.MILLISECONDS     // Catch:{ all -> 0x00be }
        L_0x0021:
            r0.setState(r8)     // Catch:{ all -> 0x00be }
            r0.updateExpiry(r9, r11)     // Catch:{ all -> 0x00be }
            cz.msebera.android.httpclient.extras.HttpClientAndroidLog r8 = r6.log     // Catch:{ all -> 0x00be }
            boolean r8 = r8.isDebugEnabled()     // Catch:{ all -> 0x00be }
            if (r8 == 0) goto L_0x007b
            r4 = 0
            int r8 = (r9 > r4 ? 1 : (r9 == r4 ? 0 : -1))
            if (r8 <= 0) goto L_0x0057
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ all -> 0x00be }
            r8.<init>()     // Catch:{ all -> 0x00be }
            java.lang.String r4 = "for "
            r8.append(r4)     // Catch:{ all -> 0x00be }
            long r9 = r11.toMillis(r9)     // Catch:{ all -> 0x00be }
            double r9 = (double) r9     // Catch:{ all -> 0x00be }
            r4 = 4652007308841189376(0x408f400000000000, double:1000.0)
            double r9 = r9 / r4
            r8.append(r9)     // Catch:{ all -> 0x00be }
            java.lang.String r9 = " seconds"
            r8.append(r9)     // Catch:{ all -> 0x00be }
            java.lang.String r8 = r8.toString()     // Catch:{ all -> 0x00be }
            goto L_0x0059
        L_0x0057:
            java.lang.String r8 = "indefinitely"
        L_0x0059:
            cz.msebera.android.httpclient.extras.HttpClientAndroidLog r9 = r6.log     // Catch:{ all -> 0x00be }
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch:{ all -> 0x00be }
            r10.<init>()     // Catch:{ all -> 0x00be }
            java.lang.String r11 = "Connection "
            r10.append(r11)     // Catch:{ all -> 0x00be }
            java.lang.String r11 = r6.a(r0)     // Catch:{ all -> 0x00be }
            r10.append(r11)     // Catch:{ all -> 0x00be }
            java.lang.String r11 = " can be kept alive "
            r10.append(r11)     // Catch:{ all -> 0x00be }
            r10.append(r8)     // Catch:{ all -> 0x00be }
            java.lang.String r8 = r10.toString()     // Catch:{ all -> 0x00be }
            r9.debug(r8)     // Catch:{ all -> 0x00be }
        L_0x007b:
            cz.msebera.android.httpclient.impl.conn.CPool r8 = r6.b     // Catch:{ all -> 0x0101 }
            boolean r9 = r1.isOpen()     // Catch:{ all -> 0x0101 }
            if (r9 == 0) goto L_0x008a
            boolean r9 = r0.b()     // Catch:{ all -> 0x0101 }
            if (r9 == 0) goto L_0x008a
            r2 = 1
        L_0x008a:
            r8.release(r0, r2)     // Catch:{ all -> 0x0101 }
            cz.msebera.android.httpclient.extras.HttpClientAndroidLog r8 = r6.log     // Catch:{ all -> 0x0101 }
            boolean r8 = r8.isDebugEnabled()     // Catch:{ all -> 0x0101 }
            if (r8 == 0) goto L_0x00bc
            cz.msebera.android.httpclient.extras.HttpClientAndroidLog r8 = r6.log     // Catch:{ all -> 0x0101 }
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch:{ all -> 0x0101 }
            r9.<init>()     // Catch:{ all -> 0x0101 }
            java.lang.String r10 = "Connection released: "
            r9.append(r10)     // Catch:{ all -> 0x0101 }
            java.lang.String r10 = r6.a(r0)     // Catch:{ all -> 0x0101 }
            r9.append(r10)     // Catch:{ all -> 0x0101 }
            java.lang.Object r10 = r0.getRoute()     // Catch:{ all -> 0x0101 }
            cz.msebera.android.httpclient.conn.routing.HttpRoute r10 = (cz.msebera.android.httpclient.conn.routing.HttpRoute) r10     // Catch:{ all -> 0x0101 }
            java.lang.String r10 = r6.a(r10)     // Catch:{ all -> 0x0101 }
            r9.append(r10)     // Catch:{ all -> 0x0101 }
            java.lang.String r9 = r9.toString()     // Catch:{ all -> 0x0101 }
            r8.debug(r9)     // Catch:{ all -> 0x0101 }
        L_0x00bc:
            monitor-exit(r7)     // Catch:{ all -> 0x0101 }
            return
        L_0x00be:
            r8 = move-exception
            cz.msebera.android.httpclient.impl.conn.CPool r9 = r6.b     // Catch:{ all -> 0x0101 }
            boolean r10 = r1.isOpen()     // Catch:{ all -> 0x0101 }
            if (r10 == 0) goto L_0x00ce
            boolean r10 = r0.b()     // Catch:{ all -> 0x0101 }
            if (r10 == 0) goto L_0x00ce
            r2 = 1
        L_0x00ce:
            r9.release(r0, r2)     // Catch:{ all -> 0x0101 }
            cz.msebera.android.httpclient.extras.HttpClientAndroidLog r9 = r6.log     // Catch:{ all -> 0x0101 }
            boolean r9 = r9.isDebugEnabled()     // Catch:{ all -> 0x0101 }
            if (r9 == 0) goto L_0x0100
            cz.msebera.android.httpclient.extras.HttpClientAndroidLog r9 = r6.log     // Catch:{ all -> 0x0101 }
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch:{ all -> 0x0101 }
            r10.<init>()     // Catch:{ all -> 0x0101 }
            java.lang.String r11 = "Connection released: "
            r10.append(r11)     // Catch:{ all -> 0x0101 }
            java.lang.String r11 = r6.a(r0)     // Catch:{ all -> 0x0101 }
            r10.append(r11)     // Catch:{ all -> 0x0101 }
            java.lang.Object r11 = r0.getRoute()     // Catch:{ all -> 0x0101 }
            cz.msebera.android.httpclient.conn.routing.HttpRoute r11 = (cz.msebera.android.httpclient.conn.routing.HttpRoute) r11     // Catch:{ all -> 0x0101 }
            java.lang.String r11 = r6.a(r11)     // Catch:{ all -> 0x0101 }
            r10.append(r11)     // Catch:{ all -> 0x0101 }
            java.lang.String r10 = r10.toString()     // Catch:{ all -> 0x0101 }
            r9.debug(r10)     // Catch:{ all -> 0x0101 }
        L_0x0100:
            throw r8     // Catch:{ all -> 0x0101 }
        L_0x0101:
            r8 = move-exception
            monitor-exit(r7)     // Catch:{ all -> 0x0101 }
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: cz.msebera.android.httpclient.impl.conn.PoolingHttpClientConnectionManager.releaseConnection(cz.msebera.android.httpclient.HttpClientConnection, java.lang.Object, long, java.util.concurrent.TimeUnit):void");
    }

    public void connect(HttpClientConnection httpClientConnection, HttpRoute httpRoute, int i, HttpContext httpContext) {
        ManagedHttpClientConnection managedHttpClientConnection;
        HttpHost targetHost;
        Args.notNull(httpClientConnection, "Managed Connection");
        Args.notNull(httpRoute, "HTTP route");
        synchronized (httpClientConnection) {
            managedHttpClientConnection = (ManagedHttpClientConnection) CPoolProxy.a(httpClientConnection).getConnection();
        }
        if (httpRoute.getProxyHost() != null) {
            targetHost = httpRoute.getProxyHost();
        } else {
            targetHost = httpRoute.getTargetHost();
        }
        HttpHost httpHost = targetHost;
        InetSocketAddress localSocketAddress = httpRoute.getLocalSocketAddress();
        SocketConfig a2 = this.a.a(httpHost);
        if (a2 == null) {
            a2 = this.a.a();
        }
        if (a2 == null) {
            a2 = SocketConfig.DEFAULT;
        }
        this.c.connect(managedHttpClientConnection, httpHost, localSocketAddress, i, a2, httpContext);
    }

    public void upgrade(HttpClientConnection httpClientConnection, HttpRoute httpRoute, HttpContext httpContext) {
        ManagedHttpClientConnection managedHttpClientConnection;
        Args.notNull(httpClientConnection, "Managed Connection");
        Args.notNull(httpRoute, "HTTP route");
        synchronized (httpClientConnection) {
            managedHttpClientConnection = (ManagedHttpClientConnection) CPoolProxy.a(httpClientConnection).getConnection();
        }
        this.c.upgrade(managedHttpClientConnection, httpRoute.getTargetHost(), httpContext);
    }

    public void routeComplete(HttpClientConnection httpClientConnection, HttpRoute httpRoute, HttpContext httpContext) {
        Args.notNull(httpClientConnection, "Managed Connection");
        Args.notNull(httpRoute, "HTTP route");
        synchronized (httpClientConnection) {
            CPoolProxy.a(httpClientConnection).a();
        }
    }

    public void shutdown() {
        if (this.d.compareAndSet(false, true)) {
            this.log.debug("Connection manager is shutting down");
            try {
                this.b.shutdown();
            } catch (IOException e) {
                this.log.debug("I/O exception shutting down connection manager", e);
            }
            this.log.debug("Connection manager shut down");
        }
    }

    public void closeIdleConnections(long j, TimeUnit timeUnit) {
        if (this.log.isDebugEnabled()) {
            HttpClientAndroidLog httpClientAndroidLog = this.log;
            StringBuilder sb = new StringBuilder();
            sb.append("Closing connections idle longer than ");
            sb.append(j);
            sb.append(UtilsCuentas.SEPARAOR2);
            sb.append(timeUnit);
            httpClientAndroidLog.debug(sb.toString());
        }
        this.b.closeIdle(j, timeUnit);
    }

    public void closeExpiredConnections() {
        this.log.debug("Closing expired connections");
        this.b.closeExpired();
    }

    public int getMaxTotal() {
        return this.b.getMaxTotal();
    }

    public void setMaxTotal(int i) {
        this.b.setMaxTotal(i);
    }

    public int getDefaultMaxPerRoute() {
        return this.b.getDefaultMaxPerRoute();
    }

    public void setDefaultMaxPerRoute(int i) {
        this.b.setDefaultMaxPerRoute(i);
    }

    public int getMaxPerRoute(HttpRoute httpRoute) {
        return this.b.getMaxPerRoute(httpRoute);
    }

    public void setMaxPerRoute(HttpRoute httpRoute, int i) {
        this.b.setMaxPerRoute(httpRoute, i);
    }

    public PoolStats getTotalStats() {
        return this.b.getTotalStats();
    }

    public PoolStats getStats(HttpRoute httpRoute) {
        return this.b.getStats(httpRoute);
    }

    public Set<HttpRoute> getRoutes() {
        return this.b.getRoutes();
    }

    public SocketConfig getDefaultSocketConfig() {
        return this.a.a();
    }

    public void setDefaultSocketConfig(SocketConfig socketConfig) {
        this.a.a(socketConfig);
    }

    public ConnectionConfig getDefaultConnectionConfig() {
        return this.a.b();
    }

    public void setDefaultConnectionConfig(ConnectionConfig connectionConfig) {
        this.a.a(connectionConfig);
    }

    public SocketConfig getSocketConfig(HttpHost httpHost) {
        return this.a.a(httpHost);
    }

    public void setSocketConfig(HttpHost httpHost, SocketConfig socketConfig) {
        this.a.a(httpHost, socketConfig);
    }

    public ConnectionConfig getConnectionConfig(HttpHost httpHost) {
        return this.a.b(httpHost);
    }

    public void setConnectionConfig(HttpHost httpHost, ConnectionConfig connectionConfig) {
        this.a.a(httpHost, connectionConfig);
    }

    public int getValidateAfterInactivity() {
        return this.b.getValidateAfterInactivity();
    }

    public void setValidateAfterInactivity(int i) {
        this.b.setValidateAfterInactivity(i);
    }
}
