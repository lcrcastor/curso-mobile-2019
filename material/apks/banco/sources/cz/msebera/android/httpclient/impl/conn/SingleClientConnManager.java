package cz.msebera.android.httpclient.impl.conn;

import cz.msebera.android.httpclient.annotation.GuardedBy;
import cz.msebera.android.httpclient.annotation.ThreadSafe;
import cz.msebera.android.httpclient.conn.ClientConnectionManager;
import cz.msebera.android.httpclient.conn.ClientConnectionOperator;
import cz.msebera.android.httpclient.conn.ClientConnectionRequest;
import cz.msebera.android.httpclient.conn.ManagedClientConnection;
import cz.msebera.android.httpclient.conn.routing.HttpRoute;
import cz.msebera.android.httpclient.conn.routing.RouteTracker;
import cz.msebera.android.httpclient.conn.scheme.SchemeRegistry;
import cz.msebera.android.httpclient.extras.HttpClientAndroidLog;
import cz.msebera.android.httpclient.params.HttpParams;
import cz.msebera.android.httpclient.util.Args;
import cz.msebera.android.httpclient.util.Asserts;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

@ThreadSafe
@Deprecated
public class SingleClientConnManager implements ClientConnectionManager {
    public static final String MISUSE_MESSAGE = "Invalid use of SingleClientConnManager: connection still allocated.\nMake sure to release the connection before allocating another one.";
    protected final boolean alwaysShutDown;
    protected final ClientConnectionOperator connOperator;
    @GuardedBy("this")
    protected volatile long connectionExpiresTime;
    protected volatile boolean isShutDown;
    @GuardedBy("this")
    protected volatile long lastReleaseTime;
    public HttpClientAndroidLog log;
    @GuardedBy("this")
    protected volatile ConnAdapter managedConn;
    protected final SchemeRegistry schemeRegistry;
    @GuardedBy("this")
    protected volatile PoolEntry uniquePoolEntry;

    public class ConnAdapter extends AbstractPooledConnAdapter {
        protected ConnAdapter(PoolEntry poolEntry, HttpRoute httpRoute) {
            super(SingleClientConnManager.this, poolEntry);
            markReusable();
            poolEntry.route = httpRoute;
        }
    }

    public class PoolEntry extends AbstractPoolEntry {
        protected PoolEntry() {
            super(SingleClientConnManager.this.connOperator, null);
        }

        /* access modifiers changed from: protected */
        public void close() {
            shutdownEntry();
            if (this.connection.isOpen()) {
                this.connection.close();
            }
        }

        /* access modifiers changed from: protected */
        public void shutdown() {
            shutdownEntry();
            if (this.connection.isOpen()) {
                this.connection.shutdown();
            }
        }
    }

    @Deprecated
    public SingleClientConnManager(HttpParams httpParams, SchemeRegistry schemeRegistry2) {
        this(schemeRegistry2);
    }

    public SingleClientConnManager(SchemeRegistry schemeRegistry2) {
        this.log = new HttpClientAndroidLog(getClass());
        Args.notNull(schemeRegistry2, "Scheme registry");
        this.schemeRegistry = schemeRegistry2;
        this.connOperator = createConnectionOperator(schemeRegistry2);
        this.uniquePoolEntry = new PoolEntry();
        this.managedConn = null;
        this.lastReleaseTime = -1;
        this.alwaysShutDown = false;
        this.isShutDown = false;
    }

    public SingleClientConnManager() {
        this(SchemeRegistryFactory.createDefault());
    }

    /* access modifiers changed from: protected */
    public void finalize() {
        try {
            shutdown();
        } finally {
            super.finalize();
        }
    }

    public SchemeRegistry getSchemeRegistry() {
        return this.schemeRegistry;
    }

    /* access modifiers changed from: protected */
    public ClientConnectionOperator createConnectionOperator(SchemeRegistry schemeRegistry2) {
        return new DefaultClientConnectionOperator(schemeRegistry2);
    }

    /* access modifiers changed from: protected */
    public final void assertStillUp() {
        Asserts.check(!this.isShutDown, "Manager is shut down");
    }

    public final ClientConnectionRequest requestConnection(final HttpRoute httpRoute, final Object obj) {
        return new ClientConnectionRequest() {
            public void abortRequest() {
            }

            public ManagedClientConnection getConnection(long j, TimeUnit timeUnit) {
                return SingleClientConnManager.this.getConnection(httpRoute, obj);
            }
        };
    }

    public ManagedClientConnection getConnection(HttpRoute httpRoute, Object obj) {
        boolean z;
        ConnAdapter connAdapter;
        Args.notNull(httpRoute, "Route");
        assertStillUp();
        if (this.log.isDebugEnabled()) {
            HttpClientAndroidLog httpClientAndroidLog = this.log;
            StringBuilder sb = new StringBuilder();
            sb.append("Get connection for route ");
            sb.append(httpRoute);
            httpClientAndroidLog.debug(sb.toString());
        }
        synchronized (this) {
            boolean z2 = false;
            Asserts.check(this.managedConn == null, MISUSE_MESSAGE);
            closeExpiredConnections();
            if (this.uniquePoolEntry.connection.isOpen()) {
                RouteTracker routeTracker = this.uniquePoolEntry.tracker;
                z = routeTracker == null || !routeTracker.toRoute().equals(httpRoute);
            } else {
                z = false;
                z2 = true;
            }
            if (z) {
                try {
                    this.uniquePoolEntry.shutdown();
                } catch (IOException e) {
                    this.log.debug("Problem shutting down connection.", e);
                }
                z2 = true;
            }
            if (z2) {
                this.uniquePoolEntry = new PoolEntry();
            }
            this.managedConn = new ConnAdapter(this.uniquePoolEntry, httpRoute);
            connAdapter = this.managedConn;
        }
        return connAdapter;
    }

    /* JADX WARNING: Unknown top exception splitter block from list: {B:27:0x0069=Splitter:B:27:0x0069, B:56:0x00bd=Splitter:B:56:0x00bd, B:47:0x009e=Splitter:B:47:0x009e} */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void releaseConnection(cz.msebera.android.httpclient.conn.ManagedClientConnection r9, long r10, java.util.concurrent.TimeUnit r12) {
        /*
            r8 = this;
            boolean r0 = r9 instanceof cz.msebera.android.httpclient.impl.conn.SingleClientConnManager.ConnAdapter
            java.lang.String r1 = "Connection class mismatch, connection not obtained from this manager"
            cz.msebera.android.httpclient.util.Args.check(r0, r1)
            r8.assertStillUp()
            cz.msebera.android.httpclient.extras.HttpClientAndroidLog r0 = r8.log
            boolean r0 = r0.isDebugEnabled()
            if (r0 == 0) goto L_0x0028
            cz.msebera.android.httpclient.extras.HttpClientAndroidLog r0 = r8.log
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "Releasing connection "
            r1.append(r2)
            r1.append(r9)
            java.lang.String r1 = r1.toString()
            r0.debug(r1)
        L_0x0028:
            cz.msebera.android.httpclient.impl.conn.SingleClientConnManager$ConnAdapter r9 = (cz.msebera.android.httpclient.impl.conn.SingleClientConnManager.ConnAdapter) r9
            monitor-enter(r9)
            cz.msebera.android.httpclient.impl.conn.AbstractPoolEntry r0 = r9.poolEntry     // Catch:{ all -> 0x00e5 }
            if (r0 != 0) goto L_0x0031
            monitor-exit(r9)     // Catch:{ all -> 0x00e5 }
            return
        L_0x0031:
            cz.msebera.android.httpclient.conn.ClientConnectionManager r0 = r9.getManager()     // Catch:{ all -> 0x00e5 }
            if (r0 != r8) goto L_0x0039
            r0 = 1
            goto L_0x003a
        L_0x0039:
            r0 = 0
        L_0x003a:
            java.lang.String r1 = "Connection not obtained from this manager"
            cz.msebera.android.httpclient.util.Asserts.check(r0, r1)     // Catch:{ all -> 0x00e5 }
            r0 = 9223372036854775807(0x7fffffffffffffff, double:NaN)
            r2 = 0
            r4 = 0
            boolean r5 = r9.isOpen()     // Catch:{ IOException -> 0x008e }
            if (r5 == 0) goto L_0x0069
            boolean r5 = r8.alwaysShutDown     // Catch:{ IOException -> 0x008e }
            if (r5 != 0) goto L_0x0057
            boolean r5 = r9.isMarkedReusable()     // Catch:{ IOException -> 0x008e }
            if (r5 != 0) goto L_0x0069
        L_0x0057:
            cz.msebera.android.httpclient.extras.HttpClientAndroidLog r5 = r8.log     // Catch:{ IOException -> 0x008e }
            boolean r5 = r5.isDebugEnabled()     // Catch:{ IOException -> 0x008e }
            if (r5 == 0) goto L_0x0066
            cz.msebera.android.httpclient.extras.HttpClientAndroidLog r5 = r8.log     // Catch:{ IOException -> 0x008e }
            java.lang.String r6 = "Released connection open but not reusable."
            r5.debug(r6)     // Catch:{ IOException -> 0x008e }
        L_0x0066:
            r9.shutdown()     // Catch:{ IOException -> 0x008e }
        L_0x0069:
            r9.detach()     // Catch:{ all -> 0x00e5 }
            monitor-enter(r8)     // Catch:{ all -> 0x00e5 }
            r8.managedConn = r4     // Catch:{ all -> 0x0089 }
            long r4 = java.lang.System.currentTimeMillis()     // Catch:{ all -> 0x0089 }
            r8.lastReleaseTime = r4     // Catch:{ all -> 0x0089 }
            int r4 = (r10 > r2 ? 1 : (r10 == r2 ? 0 : -1))
            if (r4 <= 0) goto L_0x0085
            long r10 = r12.toMillis(r10)     // Catch:{ all -> 0x0089 }
            long r0 = r8.lastReleaseTime     // Catch:{ all -> 0x0089 }
            r12 = 0
            long r2 = r10 + r0
            r8.connectionExpiresTime = r2     // Catch:{ all -> 0x0089 }
            goto L_0x0087
        L_0x0085:
            r8.connectionExpiresTime = r0     // Catch:{ all -> 0x0089 }
        L_0x0087:
            monitor-exit(r8)     // Catch:{ all -> 0x0089 }
            goto L_0x00bd
        L_0x0089:
            r10 = move-exception
            monitor-exit(r8)     // Catch:{ all -> 0x0089 }
            throw r10     // Catch:{ all -> 0x00e5 }
        L_0x008c:
            r5 = move-exception
            goto L_0x00c2
        L_0x008e:
            r5 = move-exception
            cz.msebera.android.httpclient.extras.HttpClientAndroidLog r6 = r8.log     // Catch:{ all -> 0x008c }
            boolean r6 = r6.isDebugEnabled()     // Catch:{ all -> 0x008c }
            if (r6 == 0) goto L_0x009e
            cz.msebera.android.httpclient.extras.HttpClientAndroidLog r6 = r8.log     // Catch:{ all -> 0x008c }
            java.lang.String r7 = "Exception shutting down released connection."
            r6.debug(r7, r5)     // Catch:{ all -> 0x008c }
        L_0x009e:
            r9.detach()     // Catch:{ all -> 0x00e5 }
            monitor-enter(r8)     // Catch:{ all -> 0x00e5 }
            r8.managedConn = r4     // Catch:{ all -> 0x00bf }
            long r4 = java.lang.System.currentTimeMillis()     // Catch:{ all -> 0x00bf }
            r8.lastReleaseTime = r4     // Catch:{ all -> 0x00bf }
            int r4 = (r10 > r2 ? 1 : (r10 == r2 ? 0 : -1))
            if (r4 <= 0) goto L_0x00ba
            long r10 = r12.toMillis(r10)     // Catch:{ all -> 0x00bf }
            long r0 = r8.lastReleaseTime     // Catch:{ all -> 0x00bf }
            r12 = 0
            long r2 = r10 + r0
            r8.connectionExpiresTime = r2     // Catch:{ all -> 0x00bf }
            goto L_0x00bc
        L_0x00ba:
            r8.connectionExpiresTime = r0     // Catch:{ all -> 0x00bf }
        L_0x00bc:
            monitor-exit(r8)     // Catch:{ all -> 0x00bf }
        L_0x00bd:
            monitor-exit(r9)     // Catch:{ all -> 0x00e5 }
            return
        L_0x00bf:
            r10 = move-exception
            monitor-exit(r8)     // Catch:{ all -> 0x00bf }
            throw r10     // Catch:{ all -> 0x00e5 }
        L_0x00c2:
            r9.detach()     // Catch:{ all -> 0x00e5 }
            monitor-enter(r8)     // Catch:{ all -> 0x00e5 }
            r8.managedConn = r4     // Catch:{ all -> 0x00e2 }
            long r6 = java.lang.System.currentTimeMillis()     // Catch:{ all -> 0x00e2 }
            r8.lastReleaseTime = r6     // Catch:{ all -> 0x00e2 }
            int r4 = (r10 > r2 ? 1 : (r10 == r2 ? 0 : -1))
            if (r4 <= 0) goto L_0x00de
            long r10 = r12.toMillis(r10)     // Catch:{ all -> 0x00e2 }
            long r0 = r8.lastReleaseTime     // Catch:{ all -> 0x00e2 }
            r12 = 0
            long r2 = r10 + r0
            r8.connectionExpiresTime = r2     // Catch:{ all -> 0x00e2 }
            goto L_0x00e0
        L_0x00de:
            r8.connectionExpiresTime = r0     // Catch:{ all -> 0x00e2 }
        L_0x00e0:
            monitor-exit(r8)     // Catch:{ all -> 0x00e2 }
            throw r5     // Catch:{ all -> 0x00e5 }
        L_0x00e2:
            r10 = move-exception
            monitor-exit(r8)     // Catch:{ all -> 0x00e2 }
            throw r10     // Catch:{ all -> 0x00e5 }
        L_0x00e5:
            r10 = move-exception
            monitor-exit(r9)     // Catch:{ all -> 0x00e5 }
            throw r10
        */
        throw new UnsupportedOperationException("Method not decompiled: cz.msebera.android.httpclient.impl.conn.SingleClientConnManager.releaseConnection(cz.msebera.android.httpclient.conn.ManagedClientConnection, long, java.util.concurrent.TimeUnit):void");
    }

    public void closeExpiredConnections() {
        if (System.currentTimeMillis() >= this.connectionExpiresTime) {
            closeIdleConnections(0, TimeUnit.MILLISECONDS);
        }
    }

    public void closeIdleConnections(long j, TimeUnit timeUnit) {
        assertStillUp();
        Args.notNull(timeUnit, "Time unit");
        synchronized (this) {
            if (this.managedConn == null && this.uniquePoolEntry.connection.isOpen()) {
                if (this.lastReleaseTime <= System.currentTimeMillis() - timeUnit.toMillis(j)) {
                    try {
                        this.uniquePoolEntry.close();
                    } catch (IOException e) {
                        this.log.debug("Problem closing idle connection.", e);
                    }
                }
            }
        }
    }

    public void shutdown() {
        this.isShutDown = true;
        synchronized (this) {
            try {
                if (this.uniquePoolEntry != null) {
                    this.uniquePoolEntry.shutdown();
                }
                this.uniquePoolEntry = null;
            } catch (IOException e) {
                try {
                    this.log.debug("Problem while shutting down manager.", e);
                    this.uniquePoolEntry = null;
                } catch (Throwable th) {
                    this.uniquePoolEntry = null;
                    this.managedConn = null;
                    throw th;
                }
            }
            this.managedConn = null;
        }
    }

    /* access modifiers changed from: protected */
    public void revokeConnection() {
        ConnAdapter connAdapter = this.managedConn;
        if (connAdapter != null) {
            connAdapter.detach();
            synchronized (this) {
                try {
                    this.uniquePoolEntry.shutdown();
                } catch (IOException e) {
                    this.log.debug("Problem while shutting down connection.", e);
                }
            }
        }
    }
}
