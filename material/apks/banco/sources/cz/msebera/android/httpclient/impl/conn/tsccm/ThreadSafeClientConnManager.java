package cz.msebera.android.httpclient.impl.conn.tsccm;

import ar.com.santander.rio.mbanking.utils.UtilsCuentas;
import cz.msebera.android.httpclient.annotation.ThreadSafe;
import cz.msebera.android.httpclient.conn.ClientConnectionManager;
import cz.msebera.android.httpclient.conn.ClientConnectionOperator;
import cz.msebera.android.httpclient.conn.ClientConnectionRequest;
import cz.msebera.android.httpclient.conn.ManagedClientConnection;
import cz.msebera.android.httpclient.conn.params.ConnPerRouteBean;
import cz.msebera.android.httpclient.conn.routing.HttpRoute;
import cz.msebera.android.httpclient.conn.scheme.SchemeRegistry;
import cz.msebera.android.httpclient.extras.HttpClientAndroidLog;
import cz.msebera.android.httpclient.impl.conn.DefaultClientConnectionOperator;
import cz.msebera.android.httpclient.impl.conn.SchemeRegistryFactory;
import cz.msebera.android.httpclient.params.HttpParams;
import cz.msebera.android.httpclient.util.Args;
import java.util.concurrent.TimeUnit;

@ThreadSafe
@Deprecated
public class ThreadSafeClientConnManager implements ClientConnectionManager {
    protected final ClientConnectionOperator connOperator;
    protected final ConnPerRouteBean connPerRoute;
    protected final AbstractConnPool connectionPool;
    public HttpClientAndroidLog log;
    protected final ConnPoolByRoute pool;
    protected final SchemeRegistry schemeRegistry;

    public ThreadSafeClientConnManager(SchemeRegistry schemeRegistry2) {
        this(schemeRegistry2, -1, TimeUnit.MILLISECONDS);
    }

    public ThreadSafeClientConnManager() {
        this(SchemeRegistryFactory.createDefault());
    }

    public ThreadSafeClientConnManager(SchemeRegistry schemeRegistry2, long j, TimeUnit timeUnit) {
        this(schemeRegistry2, j, timeUnit, new ConnPerRouteBean());
    }

    public ThreadSafeClientConnManager(SchemeRegistry schemeRegistry2, long j, TimeUnit timeUnit, ConnPerRouteBean connPerRouteBean) {
        Args.notNull(schemeRegistry2, "Scheme registry");
        this.log = new HttpClientAndroidLog(getClass());
        this.schemeRegistry = schemeRegistry2;
        this.connPerRoute = connPerRouteBean;
        this.connOperator = createConnectionOperator(schemeRegistry2);
        this.pool = createConnectionPool(j, timeUnit);
        this.connectionPool = this.pool;
    }

    @Deprecated
    public ThreadSafeClientConnManager(HttpParams httpParams, SchemeRegistry schemeRegistry2) {
        Args.notNull(schemeRegistry2, "Scheme registry");
        this.log = new HttpClientAndroidLog(getClass());
        this.schemeRegistry = schemeRegistry2;
        this.connPerRoute = new ConnPerRouteBean();
        this.connOperator = createConnectionOperator(schemeRegistry2);
        this.pool = (ConnPoolByRoute) createConnectionPool(httpParams);
        this.connectionPool = this.pool;
    }

    /* access modifiers changed from: protected */
    public void finalize() {
        try {
            shutdown();
        } finally {
            super.finalize();
        }
    }

    /* access modifiers changed from: protected */
    @Deprecated
    public AbstractConnPool createConnectionPool(HttpParams httpParams) {
        return new ConnPoolByRoute(this.connOperator, httpParams);
    }

    /* access modifiers changed from: protected */
    public ConnPoolByRoute createConnectionPool(long j, TimeUnit timeUnit) {
        ConnPoolByRoute connPoolByRoute = new ConnPoolByRoute(this.connOperator, this.connPerRoute, 20, j, timeUnit);
        return connPoolByRoute;
    }

    /* access modifiers changed from: protected */
    public ClientConnectionOperator createConnectionOperator(SchemeRegistry schemeRegistry2) {
        return new DefaultClientConnectionOperator(schemeRegistry2);
    }

    public SchemeRegistry getSchemeRegistry() {
        return this.schemeRegistry;
    }

    public ClientConnectionRequest requestConnection(final HttpRoute httpRoute, Object obj) {
        final PoolEntryRequest requestPoolEntry = this.pool.requestPoolEntry(httpRoute, obj);
        return new ClientConnectionRequest() {
            public void abortRequest() {
                requestPoolEntry.abortRequest();
            }

            public ManagedClientConnection getConnection(long j, TimeUnit timeUnit) {
                Args.notNull(httpRoute, "Route");
                if (ThreadSafeClientConnManager.this.log.isDebugEnabled()) {
                    HttpClientAndroidLog httpClientAndroidLog = ThreadSafeClientConnManager.this.log;
                    StringBuilder sb = new StringBuilder();
                    sb.append("Get connection: ");
                    sb.append(httpRoute);
                    sb.append(", timeout = ");
                    sb.append(j);
                    httpClientAndroidLog.debug(sb.toString());
                }
                return new BasicPooledConnAdapter(ThreadSafeClientConnManager.this, requestPoolEntry.getPoolEntry(j, timeUnit));
            }
        };
    }

    /* JADX WARNING: Unknown top exception splitter block from list: {B:33:0x0072=Splitter:B:33:0x0072, B:19:0x0038=Splitter:B:19:0x0038} */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void releaseConnection(cz.msebera.android.httpclient.conn.ManagedClientConnection r8, long r9, java.util.concurrent.TimeUnit r11) {
        /*
            r7 = this;
            boolean r0 = r8 instanceof cz.msebera.android.httpclient.impl.conn.tsccm.BasicPooledConnAdapter
            java.lang.String r1 = "Connection class mismatch, connection not obtained from this manager"
            cz.msebera.android.httpclient.util.Args.check(r0, r1)
            cz.msebera.android.httpclient.impl.conn.tsccm.BasicPooledConnAdapter r8 = (cz.msebera.android.httpclient.impl.conn.tsccm.BasicPooledConnAdapter) r8
            cz.msebera.android.httpclient.impl.conn.AbstractPoolEntry r0 = r8.getPoolEntry()
            if (r0 == 0) goto L_0x001d
            cz.msebera.android.httpclient.conn.ClientConnectionManager r0 = r8.getManager()
            if (r0 != r7) goto L_0x0017
            r0 = 1
            goto L_0x0018
        L_0x0017:
            r0 = 0
        L_0x0018:
            java.lang.String r1 = "Connection not obtained from this manager"
            cz.msebera.android.httpclient.util.Asserts.check(r0, r1)
        L_0x001d:
            monitor-enter(r8)
            cz.msebera.android.httpclient.impl.conn.AbstractPoolEntry r0 = r8.getPoolEntry()     // Catch:{ all -> 0x00bf }
            r2 = r0
            cz.msebera.android.httpclient.impl.conn.tsccm.BasicPoolEntry r2 = (cz.msebera.android.httpclient.impl.conn.tsccm.BasicPoolEntry) r2     // Catch:{ all -> 0x00bf }
            if (r2 != 0) goto L_0x0029
            monitor-exit(r8)     // Catch:{ all -> 0x00bf }
            return
        L_0x0029:
            boolean r0 = r8.isOpen()     // Catch:{ IOException -> 0x0062 }
            if (r0 == 0) goto L_0x0038
            boolean r0 = r8.isMarkedReusable()     // Catch:{ IOException -> 0x0062 }
            if (r0 != 0) goto L_0x0038
            r8.shutdown()     // Catch:{ IOException -> 0x0062 }
        L_0x0038:
            boolean r3 = r8.isMarkedReusable()     // Catch:{ all -> 0x00bf }
            cz.msebera.android.httpclient.extras.HttpClientAndroidLog r0 = r7.log     // Catch:{ all -> 0x00bf }
            boolean r0 = r0.isDebugEnabled()     // Catch:{ all -> 0x00bf }
            if (r0 == 0) goto L_0x0055
            if (r3 == 0) goto L_0x004e
            cz.msebera.android.httpclient.extras.HttpClientAndroidLog r0 = r7.log     // Catch:{ all -> 0x00bf }
            java.lang.String r1 = "Released connection is reusable."
            r0.debug(r1)     // Catch:{ all -> 0x00bf }
            goto L_0x0055
        L_0x004e:
            cz.msebera.android.httpclient.extras.HttpClientAndroidLog r0 = r7.log     // Catch:{ all -> 0x00bf }
            java.lang.String r1 = "Released connection is not reusable."
            r0.debug(r1)     // Catch:{ all -> 0x00bf }
        L_0x0055:
            r8.detach()     // Catch:{ all -> 0x00bf }
            cz.msebera.android.httpclient.impl.conn.tsccm.ConnPoolByRoute r1 = r7.pool     // Catch:{ all -> 0x00bf }
        L_0x005a:
            r4 = r9
            r6 = r11
            r1.freeEntry(r2, r3, r4, r6)     // Catch:{ all -> 0x00bf }
            goto L_0x0095
        L_0x0060:
            r0 = move-exception
            goto L_0x0097
        L_0x0062:
            r0 = move-exception
            cz.msebera.android.httpclient.extras.HttpClientAndroidLog r1 = r7.log     // Catch:{ all -> 0x0060 }
            boolean r1 = r1.isDebugEnabled()     // Catch:{ all -> 0x0060 }
            if (r1 == 0) goto L_0x0072
            cz.msebera.android.httpclient.extras.HttpClientAndroidLog r1 = r7.log     // Catch:{ all -> 0x0060 }
            java.lang.String r3 = "Exception shutting down released connection."
            r1.debug(r3, r0)     // Catch:{ all -> 0x0060 }
        L_0x0072:
            boolean r3 = r8.isMarkedReusable()     // Catch:{ all -> 0x00bf }
            cz.msebera.android.httpclient.extras.HttpClientAndroidLog r0 = r7.log     // Catch:{ all -> 0x00bf }
            boolean r0 = r0.isDebugEnabled()     // Catch:{ all -> 0x00bf }
            if (r0 == 0) goto L_0x008f
            if (r3 == 0) goto L_0x0088
            cz.msebera.android.httpclient.extras.HttpClientAndroidLog r0 = r7.log     // Catch:{ all -> 0x00bf }
            java.lang.String r1 = "Released connection is reusable."
            r0.debug(r1)     // Catch:{ all -> 0x00bf }
            goto L_0x008f
        L_0x0088:
            cz.msebera.android.httpclient.extras.HttpClientAndroidLog r0 = r7.log     // Catch:{ all -> 0x00bf }
            java.lang.String r1 = "Released connection is not reusable."
            r0.debug(r1)     // Catch:{ all -> 0x00bf }
        L_0x008f:
            r8.detach()     // Catch:{ all -> 0x00bf }
            cz.msebera.android.httpclient.impl.conn.tsccm.ConnPoolByRoute r1 = r7.pool     // Catch:{ all -> 0x00bf }
            goto L_0x005a
        L_0x0095:
            monitor-exit(r8)     // Catch:{ all -> 0x00bf }
            return
        L_0x0097:
            boolean r3 = r8.isMarkedReusable()     // Catch:{ all -> 0x00bf }
            cz.msebera.android.httpclient.extras.HttpClientAndroidLog r1 = r7.log     // Catch:{ all -> 0x00bf }
            boolean r1 = r1.isDebugEnabled()     // Catch:{ all -> 0x00bf }
            if (r1 == 0) goto L_0x00b4
            if (r3 == 0) goto L_0x00ad
            cz.msebera.android.httpclient.extras.HttpClientAndroidLog r1 = r7.log     // Catch:{ all -> 0x00bf }
            java.lang.String r4 = "Released connection is reusable."
            r1.debug(r4)     // Catch:{ all -> 0x00bf }
            goto L_0x00b4
        L_0x00ad:
            cz.msebera.android.httpclient.extras.HttpClientAndroidLog r1 = r7.log     // Catch:{ all -> 0x00bf }
            java.lang.String r4 = "Released connection is not reusable."
            r1.debug(r4)     // Catch:{ all -> 0x00bf }
        L_0x00b4:
            r8.detach()     // Catch:{ all -> 0x00bf }
            cz.msebera.android.httpclient.impl.conn.tsccm.ConnPoolByRoute r1 = r7.pool     // Catch:{ all -> 0x00bf }
            r4 = r9
            r6 = r11
            r1.freeEntry(r2, r3, r4, r6)     // Catch:{ all -> 0x00bf }
            throw r0     // Catch:{ all -> 0x00bf }
        L_0x00bf:
            r9 = move-exception
            monitor-exit(r8)     // Catch:{ all -> 0x00bf }
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: cz.msebera.android.httpclient.impl.conn.tsccm.ThreadSafeClientConnManager.releaseConnection(cz.msebera.android.httpclient.conn.ManagedClientConnection, long, java.util.concurrent.TimeUnit):void");
    }

    public void shutdown() {
        this.log.debug("Shutting down");
        this.pool.shutdown();
    }

    public int getConnectionsInPool(HttpRoute httpRoute) {
        return this.pool.getConnectionsInPool(httpRoute);
    }

    public int getConnectionsInPool() {
        return this.pool.getConnectionsInPool();
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
        this.pool.closeIdleConnections(j, timeUnit);
    }

    public void closeExpiredConnections() {
        this.log.debug("Closing expired connections");
        this.pool.closeExpiredConnections();
    }

    public int getMaxTotal() {
        return this.pool.getMaxTotalConnections();
    }

    public void setMaxTotal(int i) {
        this.pool.setMaxTotalConnections(i);
    }

    public int getDefaultMaxPerRoute() {
        return this.connPerRoute.getDefaultMaxPerRoute();
    }

    public void setDefaultMaxPerRoute(int i) {
        this.connPerRoute.setDefaultMaxPerRoute(i);
    }

    public int getMaxForRoute(HttpRoute httpRoute) {
        return this.connPerRoute.getMaxForRoute(httpRoute);
    }

    public void setMaxForRoute(HttpRoute httpRoute, int i) {
        this.connPerRoute.setMaxForRoute(httpRoute, i);
    }
}
