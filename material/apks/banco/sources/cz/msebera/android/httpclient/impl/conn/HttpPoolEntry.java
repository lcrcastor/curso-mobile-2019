package cz.msebera.android.httpclient.impl.conn;

import cz.msebera.android.httpclient.conn.OperatedClientConnection;
import cz.msebera.android.httpclient.conn.routing.HttpRoute;
import cz.msebera.android.httpclient.conn.routing.RouteTracker;
import cz.msebera.android.httpclient.extras.HttpClientAndroidLog;
import cz.msebera.android.httpclient.pool.PoolEntry;
import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Deprecated
class HttpPoolEntry extends PoolEntry<HttpRoute, OperatedClientConnection> {
    public HttpClientAndroidLog a;
    private final RouteTracker b;

    public HttpPoolEntry(HttpClientAndroidLog httpClientAndroidLog, String str, HttpRoute httpRoute, OperatedClientConnection operatedClientConnection, long j, TimeUnit timeUnit) {
        super(str, httpRoute, operatedClientConnection, j, timeUnit);
        this.a = httpClientAndroidLog;
        this.b = new RouteTracker(httpRoute);
    }

    public boolean isExpired(long j) {
        boolean isExpired = super.isExpired(j);
        if (isExpired && this.a.isDebugEnabled()) {
            HttpClientAndroidLog httpClientAndroidLog = this.a;
            StringBuilder sb = new StringBuilder();
            sb.append("Connection ");
            sb.append(this);
            sb.append(" expired @ ");
            sb.append(new Date(getExpiry()));
            httpClientAndroidLog.debug(sb.toString());
        }
        return isExpired;
    }

    /* access modifiers changed from: 0000 */
    public RouteTracker a() {
        return this.b;
    }

    /* access modifiers changed from: 0000 */
    public HttpRoute b() {
        return (HttpRoute) getRoute();
    }

    /* access modifiers changed from: 0000 */
    public HttpRoute c() {
        return this.b.toRoute();
    }

    public boolean isClosed() {
        return !((OperatedClientConnection) getConnection()).isOpen();
    }

    public void close() {
        try {
            ((OperatedClientConnection) getConnection()).close();
        } catch (IOException e) {
            this.a.debug("I/O error closing connection", e);
        }
    }
}
