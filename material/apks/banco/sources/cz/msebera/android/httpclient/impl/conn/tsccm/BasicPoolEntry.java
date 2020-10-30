package cz.msebera.android.httpclient.impl.conn.tsccm;

import cz.msebera.android.httpclient.conn.ClientConnectionOperator;
import cz.msebera.android.httpclient.conn.OperatedClientConnection;
import cz.msebera.android.httpclient.conn.routing.HttpRoute;
import cz.msebera.android.httpclient.impl.conn.AbstractPoolEntry;
import cz.msebera.android.httpclient.util.Args;
import java.lang.ref.ReferenceQueue;
import java.util.concurrent.TimeUnit;

@Deprecated
public class BasicPoolEntry extends AbstractPoolEntry {
    private final long a;
    private long b;
    private final long c;
    private long d;

    /* access modifiers changed from: protected */
    public final BasicPoolEntryRef getWeakRef() {
        return null;
    }

    public BasicPoolEntry(ClientConnectionOperator clientConnectionOperator, HttpRoute httpRoute, ReferenceQueue<Object> referenceQueue) {
        super(clientConnectionOperator, httpRoute);
        Args.notNull(httpRoute, "HTTP route");
        this.a = System.currentTimeMillis();
        this.c = Long.MAX_VALUE;
        this.d = this.c;
    }

    public BasicPoolEntry(ClientConnectionOperator clientConnectionOperator, HttpRoute httpRoute) {
        this(clientConnectionOperator, httpRoute, -1, TimeUnit.MILLISECONDS);
    }

    public BasicPoolEntry(ClientConnectionOperator clientConnectionOperator, HttpRoute httpRoute, long j, TimeUnit timeUnit) {
        super(clientConnectionOperator, httpRoute);
        Args.notNull(httpRoute, "HTTP route");
        this.a = System.currentTimeMillis();
        if (j > 0) {
            this.c = this.a + timeUnit.toMillis(j);
        } else {
            this.c = Long.MAX_VALUE;
        }
        this.d = this.c;
    }

    /* access modifiers changed from: protected */
    public final OperatedClientConnection getConnection() {
        return this.connection;
    }

    /* access modifiers changed from: protected */
    public final HttpRoute getPlannedRoute() {
        return this.route;
    }

    /* access modifiers changed from: protected */
    public void shutdownEntry() {
        super.shutdownEntry();
    }

    public long getCreated() {
        return this.a;
    }

    public long getUpdated() {
        return this.b;
    }

    public long getExpiry() {
        return this.d;
    }

    public long getValidUntil() {
        return this.c;
    }

    public void updateExpiry(long j, TimeUnit timeUnit) {
        this.b = System.currentTimeMillis();
        this.d = Math.min(this.c, j > 0 ? this.b + timeUnit.toMillis(j) : Long.MAX_VALUE);
    }

    public boolean isExpired(long j) {
        return j >= this.d;
    }
}
