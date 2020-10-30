package cz.msebera.android.httpclient.impl.client;

import ar.com.santander.rio.mbanking.utils.localizacion.LocalizacionManager;
import cz.msebera.android.httpclient.client.BackoffManager;
import cz.msebera.android.httpclient.conn.routing.HttpRoute;
import cz.msebera.android.httpclient.pool.ConnPoolControl;
import cz.msebera.android.httpclient.util.Args;
import java.util.HashMap;
import java.util.Map;

public class AIMDBackoffManager implements BackoffManager {
    private final ConnPoolControl<HttpRoute> a;
    private final Clock b;
    private final Map<HttpRoute, Long> c;
    private final Map<HttpRoute, Long> d;
    private long e;
    private double f;
    private int g;

    public AIMDBackoffManager(ConnPoolControl<HttpRoute> connPoolControl) {
        this(connPoolControl, new SystemClock());
    }

    AIMDBackoffManager(ConnPoolControl<HttpRoute> connPoolControl, Clock clock) {
        this.e = LocalizacionManager.FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS;
        this.f = 0.5d;
        this.g = 2;
        this.b = clock;
        this.a = connPoolControl;
        this.c = new HashMap();
        this.d = new HashMap();
    }

    public void backOff(HttpRoute httpRoute) {
        synchronized (this.a) {
            int maxPerRoute = this.a.getMaxPerRoute(httpRoute);
            Long a2 = a(this.d, httpRoute);
            long a3 = this.b.a();
            if (a3 - a2.longValue() >= this.e) {
                this.a.setMaxPerRoute(httpRoute, a(maxPerRoute));
                this.d.put(httpRoute, Long.valueOf(a3));
            }
        }
    }

    private int a(int i) {
        if (i <= 1) {
            return 1;
        }
        return (int) Math.floor(this.f * ((double) i));
    }

    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0050, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void probe(cz.msebera.android.httpclient.conn.routing.HttpRoute r11) {
        /*
            r10 = this;
            cz.msebera.android.httpclient.pool.ConnPoolControl<cz.msebera.android.httpclient.conn.routing.HttpRoute> r0 = r10.a
            monitor-enter(r0)
            cz.msebera.android.httpclient.pool.ConnPoolControl<cz.msebera.android.httpclient.conn.routing.HttpRoute> r1 = r10.a     // Catch:{ all -> 0x0051 }
            int r1 = r1.getMaxPerRoute(r11)     // Catch:{ all -> 0x0051 }
            int r2 = r10.g     // Catch:{ all -> 0x0051 }
            if (r1 < r2) goto L_0x0010
            int r1 = r10.g     // Catch:{ all -> 0x0051 }
            goto L_0x0012
        L_0x0010:
            int r1 = r1 + 1
        L_0x0012:
            java.util.Map<cz.msebera.android.httpclient.conn.routing.HttpRoute, java.lang.Long> r2 = r10.c     // Catch:{ all -> 0x0051 }
            java.lang.Long r2 = r10.a(r2, r11)     // Catch:{ all -> 0x0051 }
            java.util.Map<cz.msebera.android.httpclient.conn.routing.HttpRoute, java.lang.Long> r3 = r10.d     // Catch:{ all -> 0x0051 }
            java.lang.Long r3 = r10.a(r3, r11)     // Catch:{ all -> 0x0051 }
            cz.msebera.android.httpclient.impl.client.Clock r4 = r10.b     // Catch:{ all -> 0x0051 }
            long r4 = r4.a()     // Catch:{ all -> 0x0051 }
            long r6 = r2.longValue()     // Catch:{ all -> 0x0051 }
            r2 = 0
            long r8 = r4 - r6
            long r6 = r10.e     // Catch:{ all -> 0x0051 }
            int r2 = (r8 > r6 ? 1 : (r8 == r6 ? 0 : -1))
            if (r2 < 0) goto L_0x004f
            long r2 = r3.longValue()     // Catch:{ all -> 0x0051 }
            r6 = 0
            long r6 = r4 - r2
            long r2 = r10.e     // Catch:{ all -> 0x0051 }
            int r8 = (r6 > r2 ? 1 : (r6 == r2 ? 0 : -1))
            if (r8 >= 0) goto L_0x003f
            goto L_0x004f
        L_0x003f:
            cz.msebera.android.httpclient.pool.ConnPoolControl<cz.msebera.android.httpclient.conn.routing.HttpRoute> r2 = r10.a     // Catch:{ all -> 0x0051 }
            r2.setMaxPerRoute(r11, r1)     // Catch:{ all -> 0x0051 }
            java.util.Map<cz.msebera.android.httpclient.conn.routing.HttpRoute, java.lang.Long> r1 = r10.c     // Catch:{ all -> 0x0051 }
            java.lang.Long r2 = java.lang.Long.valueOf(r4)     // Catch:{ all -> 0x0051 }
            r1.put(r11, r2)     // Catch:{ all -> 0x0051 }
            monitor-exit(r0)     // Catch:{ all -> 0x0051 }
            return
        L_0x004f:
            monitor-exit(r0)     // Catch:{ all -> 0x0051 }
            return
        L_0x0051:
            r11 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x0051 }
            throw r11
        */
        throw new UnsupportedOperationException("Method not decompiled: cz.msebera.android.httpclient.impl.client.AIMDBackoffManager.probe(cz.msebera.android.httpclient.conn.routing.HttpRoute):void");
    }

    private Long a(Map<HttpRoute, Long> map, HttpRoute httpRoute) {
        Long l = (Long) map.get(httpRoute);
        return l == null ? Long.valueOf(0) : l;
    }

    public void setBackoffFactor(double d2) {
        Args.check(d2 > 0.0d && d2 < 1.0d, "Backoff factor must be 0.0 < f < 1.0");
        this.f = d2;
    }

    public void setCooldownMillis(long j) {
        Args.positive(this.e, "Cool down");
        this.e = j;
    }

    public void setPerHostConnectionCap(int i) {
        Args.positive(i, "Per host connection cap");
        this.g = i;
    }
}
