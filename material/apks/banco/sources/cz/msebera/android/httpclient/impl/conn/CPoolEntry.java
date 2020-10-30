package cz.msebera.android.httpclient.impl.conn;

import cz.msebera.android.httpclient.HttpClientConnection;
import cz.msebera.android.httpclient.annotation.ThreadSafe;
import cz.msebera.android.httpclient.conn.ManagedHttpClientConnection;
import cz.msebera.android.httpclient.conn.routing.HttpRoute;
import cz.msebera.android.httpclient.extras.HttpClientAndroidLog;
import cz.msebera.android.httpclient.pool.PoolEntry;
import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@ThreadSafe
class CPoolEntry extends PoolEntry<HttpRoute, ManagedHttpClientConnection> {
    public HttpClientAndroidLog a;
    private volatile boolean b;

    public CPoolEntry(HttpClientAndroidLog httpClientAndroidLog, String str, HttpRoute httpRoute, ManagedHttpClientConnection managedHttpClientConnection, long j, TimeUnit timeUnit) {
        super(str, httpRoute, managedHttpClientConnection, j, timeUnit);
        this.a = httpClientAndroidLog;
    }

    public void a() {
        this.b = true;
    }

    public boolean b() {
        return this.b;
    }

    public void c() {
        ((HttpClientConnection) getConnection()).close();
    }

    public void d() {
        ((HttpClientConnection) getConnection()).shutdown();
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

    public boolean isClosed() {
        return !((HttpClientConnection) getConnection()).isOpen();
    }

    public void close() {
        try {
            c();
        } catch (IOException e) {
            this.a.debug("I/O error closing connection", e);
        }
    }
}
