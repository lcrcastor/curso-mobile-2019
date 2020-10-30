package cz.msebera.android.httpclient.impl.conn;

import cz.msebera.android.httpclient.conn.ClientConnectionOperator;
import cz.msebera.android.httpclient.conn.OperatedClientConnection;
import cz.msebera.android.httpclient.conn.routing.HttpRoute;
import cz.msebera.android.httpclient.extras.HttpClientAndroidLog;
import cz.msebera.android.httpclient.pool.AbstractConnPool;
import cz.msebera.android.httpclient.pool.ConnFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

@Deprecated
class HttpConnPool extends AbstractConnPool<HttpRoute, OperatedClientConnection, HttpPoolEntry> {
    private static final AtomicLong b = new AtomicLong();
    public HttpClientAndroidLog a;
    private final long c;
    private final TimeUnit d;

    static class InternalConnFactory implements ConnFactory<HttpRoute, OperatedClientConnection> {
        private final ClientConnectionOperator a;

        InternalConnFactory(ClientConnectionOperator clientConnectionOperator) {
            this.a = clientConnectionOperator;
        }

        /* renamed from: a */
        public OperatedClientConnection create(HttpRoute httpRoute) {
            return this.a.createConnection();
        }
    }

    public HttpConnPool(HttpClientAndroidLog httpClientAndroidLog, ClientConnectionOperator clientConnectionOperator, int i, int i2, long j, TimeUnit timeUnit) {
        super(new InternalConnFactory(clientConnectionOperator), i, i2);
        this.a = httpClientAndroidLog;
        this.c = j;
        this.d = timeUnit;
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public HttpPoolEntry createEntry(HttpRoute httpRoute, OperatedClientConnection operatedClientConnection) {
        HttpPoolEntry httpPoolEntry = new HttpPoolEntry(this.a, Long.toString(b.getAndIncrement()), httpRoute, operatedClientConnection, this.c, this.d);
        return httpPoolEntry;
    }
}
