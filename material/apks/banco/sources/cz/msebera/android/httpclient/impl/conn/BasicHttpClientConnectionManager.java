package cz.msebera.android.httpclient.impl.conn;

import ar.com.santander.rio.mbanking.utils.UtilsCuentas;
import cz.msebera.android.httpclient.HttpClientConnection;
import cz.msebera.android.httpclient.HttpHost;
import cz.msebera.android.httpclient.annotation.GuardedBy;
import cz.msebera.android.httpclient.annotation.ThreadSafe;
import cz.msebera.android.httpclient.config.ConnectionConfig;
import cz.msebera.android.httpclient.config.Lookup;
import cz.msebera.android.httpclient.config.Registry;
import cz.msebera.android.httpclient.config.RegistryBuilder;
import cz.msebera.android.httpclient.config.SocketConfig;
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
import cz.msebera.android.httpclient.protocol.HttpContext;
import cz.msebera.android.httpclient.util.Args;
import cz.msebera.android.httpclient.util.Asserts;
import cz.msebera.android.httpclient.util.LangUtils;
import java.io.Closeable;
import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

@ThreadSafe
public class BasicHttpClientConnectionManager implements HttpClientConnectionManager, Closeable {
    private final HttpClientConnectionOperator a;
    private final HttpConnectionFactory<HttpRoute, ManagedHttpClientConnection> b;
    @GuardedBy("this")
    private ManagedHttpClientConnection c;
    @GuardedBy("this")
    private HttpRoute d;
    @GuardedBy("this")
    private Object e;
    @GuardedBy("this")
    private long f;
    @GuardedBy("this")
    private long g;
    @GuardedBy("this")
    private boolean h;
    @GuardedBy("this")
    private SocketConfig i;
    @GuardedBy("this")
    private ConnectionConfig j;
    private final AtomicBoolean k;
    public HttpClientAndroidLog log;

    public void routeComplete(HttpClientConnection httpClientConnection, HttpRoute httpRoute, HttpContext httpContext) {
    }

    private static Registry<ConnectionSocketFactory> a() {
        return RegistryBuilder.create().register(HttpHost.DEFAULT_SCHEME_NAME, PlainConnectionSocketFactory.getSocketFactory()).register("https", SSLConnectionSocketFactory.getSocketFactory()).build();
    }

    public BasicHttpClientConnectionManager(Lookup<ConnectionSocketFactory> lookup, HttpConnectionFactory<HttpRoute, ManagedHttpClientConnection> httpConnectionFactory, SchemePortResolver schemePortResolver, DnsResolver dnsResolver) {
        this((HttpClientConnectionOperator) new DefaultHttpClientConnectionOperator(lookup, schemePortResolver, dnsResolver), httpConnectionFactory);
    }

    public BasicHttpClientConnectionManager(HttpClientConnectionOperator httpClientConnectionOperator, HttpConnectionFactory<HttpRoute, ManagedHttpClientConnection> httpConnectionFactory) {
        this.log = new HttpClientAndroidLog(getClass());
        this.a = (HttpClientConnectionOperator) Args.notNull(httpClientConnectionOperator, "Connection operator");
        if (httpConnectionFactory == null) {
            httpConnectionFactory = ManagedHttpClientConnectionFactory.INSTANCE;
        }
        this.b = httpConnectionFactory;
        this.g = Long.MAX_VALUE;
        this.i = SocketConfig.DEFAULT;
        this.j = ConnectionConfig.DEFAULT;
        this.k = new AtomicBoolean(false);
    }

    public BasicHttpClientConnectionManager(Lookup<ConnectionSocketFactory> lookup, HttpConnectionFactory<HttpRoute, ManagedHttpClientConnection> httpConnectionFactory) {
        this(lookup, httpConnectionFactory, null, null);
    }

    public BasicHttpClientConnectionManager(Lookup<ConnectionSocketFactory> lookup) {
        this(lookup, null, null, null);
    }

    public BasicHttpClientConnectionManager() {
        this(a(), null, null, null);
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

    public synchronized SocketConfig getSocketConfig() {
        return this.i;
    }

    public synchronized void setSocketConfig(SocketConfig socketConfig) {
        if (socketConfig == null) {
            socketConfig = SocketConfig.DEFAULT;
        }
        this.i = socketConfig;
    }

    public synchronized ConnectionConfig getConnectionConfig() {
        return this.j;
    }

    public synchronized void setConnectionConfig(ConnectionConfig connectionConfig) {
        if (connectionConfig == null) {
            connectionConfig = ConnectionConfig.DEFAULT;
        }
        this.j = connectionConfig;
    }

    public final ConnectionRequest requestConnection(final HttpRoute httpRoute, final Object obj) {
        Args.notNull(httpRoute, "Route");
        return new ConnectionRequest() {
            public boolean cancel() {
                return false;
            }

            public HttpClientConnection get(long j, TimeUnit timeUnit) {
                return BasicHttpClientConnectionManager.this.a(httpRoute, obj);
            }
        };
    }

    private void b() {
        if (this.c != null) {
            this.log.debug("Closing connection");
            try {
                this.c.close();
            } catch (IOException e2) {
                if (this.log.isDebugEnabled()) {
                    this.log.debug("I/O exception closing connection", e2);
                }
            }
            this.c = null;
        }
    }

    private void c() {
        if (this.c != null) {
            this.log.debug("Shutting down connection");
            try {
                this.c.shutdown();
            } catch (IOException e2) {
                if (this.log.isDebugEnabled()) {
                    this.log.debug("I/O exception shutting down connection", e2);
                }
            }
            this.c = null;
        }
    }

    private void d() {
        if (this.c != null && System.currentTimeMillis() >= this.g) {
            if (this.log.isDebugEnabled()) {
                HttpClientAndroidLog httpClientAndroidLog = this.log;
                StringBuilder sb = new StringBuilder();
                sb.append("Connection expired @ ");
                sb.append(new Date(this.g));
                httpClientAndroidLog.debug(sb.toString());
            }
            b();
        }
    }

    /* access modifiers changed from: 0000 */
    public synchronized HttpClientConnection a(HttpRoute httpRoute, Object obj) {
        Asserts.check(!this.k.get(), "Connection manager has been shut down");
        if (this.log.isDebugEnabled()) {
            HttpClientAndroidLog httpClientAndroidLog = this.log;
            StringBuilder sb = new StringBuilder();
            sb.append("Get connection for route ");
            sb.append(httpRoute);
            httpClientAndroidLog.debug(sb.toString());
        }
        Asserts.check(!this.h, "Connection is still allocated");
        if (!LangUtils.equals((Object) this.d, (Object) httpRoute) || !LangUtils.equals(this.e, obj)) {
            b();
        }
        this.d = httpRoute;
        this.e = obj;
        d();
        if (this.c == null) {
            this.c = (ManagedHttpClientConnection) this.b.create(httpRoute, this.j);
        }
        this.h = true;
        return this.c;
    }

    public synchronized void releaseConnection(HttpClientConnection httpClientConnection, Object obj, long j2, TimeUnit timeUnit) {
        String str;
        Args.notNull(httpClientConnection, "Connection");
        Asserts.check(httpClientConnection == this.c, "Connection not obtained from this manager");
        if (this.log.isDebugEnabled()) {
            HttpClientAndroidLog httpClientAndroidLog = this.log;
            StringBuilder sb = new StringBuilder();
            sb.append("Releasing connection ");
            sb.append(httpClientConnection);
            httpClientAndroidLog.debug(sb.toString());
        }
        if (!this.k.get()) {
            try {
                this.f = System.currentTimeMillis();
                if (!this.c.isOpen()) {
                    this.c = null;
                    this.d = null;
                    this.c = null;
                    this.g = Long.MAX_VALUE;
                } else {
                    this.e = obj;
                    if (this.log.isDebugEnabled()) {
                        if (j2 > 0) {
                            StringBuilder sb2 = new StringBuilder();
                            sb2.append("for ");
                            sb2.append(j2);
                            sb2.append(UtilsCuentas.SEPARAOR2);
                            sb2.append(timeUnit);
                            str = sb2.toString();
                        } else {
                            str = "indefinitely";
                        }
                        HttpClientAndroidLog httpClientAndroidLog2 = this.log;
                        StringBuilder sb3 = new StringBuilder();
                        sb3.append("Connection can be kept alive ");
                        sb3.append(str);
                        httpClientAndroidLog2.debug(sb3.toString());
                    }
                    if (j2 > 0) {
                        this.g = this.f + timeUnit.toMillis(j2);
                    } else {
                        this.g = Long.MAX_VALUE;
                    }
                }
            } finally {
                this.h = false;
            }
        }
    }

    public void connect(HttpClientConnection httpClientConnection, HttpRoute httpRoute, int i2, HttpContext httpContext) {
        HttpHost targetHost;
        Args.notNull(httpClientConnection, "Connection");
        Args.notNull(httpRoute, "HTTP route");
        Asserts.check(httpClientConnection == this.c, "Connection not obtained from this manager");
        if (httpRoute.getProxyHost() != null) {
            targetHost = httpRoute.getProxyHost();
        } else {
            targetHost = httpRoute.getTargetHost();
        }
        this.a.connect(this.c, targetHost, httpRoute.getLocalSocketAddress(), i2, this.i, httpContext);
    }

    public void upgrade(HttpClientConnection httpClientConnection, HttpRoute httpRoute, HttpContext httpContext) {
        Args.notNull(httpClientConnection, "Connection");
        Args.notNull(httpRoute, "HTTP route");
        Asserts.check(httpClientConnection == this.c, "Connection not obtained from this manager");
        this.a.upgrade(this.c, httpRoute.getTargetHost(), httpContext);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0013, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void closeExpiredConnections() {
        /*
            r1 = this;
            monitor-enter(r1)
            java.util.concurrent.atomic.AtomicBoolean r0 = r1.k     // Catch:{ all -> 0x0014 }
            boolean r0 = r0.get()     // Catch:{ all -> 0x0014 }
            if (r0 == 0) goto L_0x000b
            monitor-exit(r1)
            return
        L_0x000b:
            boolean r0 = r1.h     // Catch:{ all -> 0x0014 }
            if (r0 != 0) goto L_0x0012
            r1.d()     // Catch:{ all -> 0x0014 }
        L_0x0012:
            monitor-exit(r1)
            return
        L_0x0014:
            r0 = move-exception
            monitor-exit(r1)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: cz.msebera.android.httpclient.impl.conn.BasicHttpClientConnectionManager.closeExpiredConnections():void");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0030, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void closeIdleConnections(long r5, java.util.concurrent.TimeUnit r7) {
        /*
            r4 = this;
            monitor-enter(r4)
            java.lang.String r0 = "Time unit"
            cz.msebera.android.httpclient.util.Args.notNull(r7, r0)     // Catch:{ all -> 0x0031 }
            java.util.concurrent.atomic.AtomicBoolean r0 = r4.k     // Catch:{ all -> 0x0031 }
            boolean r0 = r0.get()     // Catch:{ all -> 0x0031 }
            if (r0 == 0) goto L_0x0010
            monitor-exit(r4)
            return
        L_0x0010:
            boolean r0 = r4.h     // Catch:{ all -> 0x0031 }
            if (r0 != 0) goto L_0x002f
            long r5 = r7.toMillis(r5)     // Catch:{ all -> 0x0031 }
            r0 = 0
            int r7 = (r5 > r0 ? 1 : (r5 == r0 ? 0 : -1))
            if (r7 >= 0) goto L_0x001f
            r5 = r0
        L_0x001f:
            long r0 = java.lang.System.currentTimeMillis()     // Catch:{ all -> 0x0031 }
            r7 = 0
            long r2 = r0 - r5
            long r5 = r4.f     // Catch:{ all -> 0x0031 }
            int r7 = (r5 > r2 ? 1 : (r5 == r2 ? 0 : -1))
            if (r7 > 0) goto L_0x002f
            r4.b()     // Catch:{ all -> 0x0031 }
        L_0x002f:
            monitor-exit(r4)
            return
        L_0x0031:
            r5 = move-exception
            monitor-exit(r4)
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: cz.msebera.android.httpclient.impl.conn.BasicHttpClientConnectionManager.closeIdleConnections(long, java.util.concurrent.TimeUnit):void");
    }

    public synchronized void shutdown() {
        if (this.k.compareAndSet(false, true)) {
            c();
        }
    }
}
