package cz.msebera.android.httpclient.impl.conn;

import cz.msebera.android.httpclient.annotation.ThreadSafe;
import cz.msebera.android.httpclient.conn.ManagedHttpClientConnection;
import cz.msebera.android.httpclient.conn.routing.HttpRoute;
import cz.msebera.android.httpclient.extras.HttpClientAndroidLog;
import cz.msebera.android.httpclient.pool.AbstractConnPool;
import cz.msebera.android.httpclient.pool.ConnFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

@ThreadSafe
class CPool extends AbstractConnPool<HttpRoute, ManagedHttpClientConnection, CPoolEntry> {
    private static final AtomicLong b = new AtomicLong();
    public HttpClientAndroidLog a = new HttpClientAndroidLog(CPool.class);
    private final long c;
    private final TimeUnit d;

    public CPool(ConnFactory<HttpRoute, ManagedHttpClientConnection> connFactory, int i, int i2, long j, TimeUnit timeUnit) {
        super(connFactory, i, i2);
        this.c = j;
        this.d = timeUnit;
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public CPoolEntry createEntry(HttpRoute httpRoute, ManagedHttpClientConnection managedHttpClientConnection) {
        CPoolEntry cPoolEntry = new CPoolEntry(this.a, Long.toString(b.getAndIncrement()), httpRoute, managedHttpClientConnection, this.c, this.d);
        return cPoolEntry;
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public boolean validate(CPoolEntry cPoolEntry) {
        return !((ManagedHttpClientConnection) cPoolEntry.getConnection()).isStale();
    }
}
