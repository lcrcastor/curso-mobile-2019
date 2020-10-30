package cz.msebera.android.httpclient.impl.client;

import java.util.concurrent.atomic.AtomicLong;

public final class FutureRequestExecutionMetrics {
    private final AtomicLong a = new AtomicLong();
    private final AtomicLong b = new AtomicLong();
    private final DurationCounter c = new DurationCounter();
    private final DurationCounter d = new DurationCounter();
    private final DurationCounter e = new DurationCounter();
    private final DurationCounter f = new DurationCounter();

    static class DurationCounter {
        private final AtomicLong a = new AtomicLong(0);
        private final AtomicLong b = new AtomicLong(0);

        DurationCounter() {
        }

        public void a(long j) {
            this.a.incrementAndGet();
            this.b.addAndGet(System.currentTimeMillis() - j);
        }

        public long a() {
            return this.a.get();
        }

        public long b() {
            long j = this.a.get();
            if (j > 0) {
                return this.b.get() / j;
            }
            return 0;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("[count=");
            sb.append(a());
            sb.append(", averageDuration=");
            sb.append(b());
            sb.append("]");
            return sb.toString();
        }
    }

    FutureRequestExecutionMetrics() {
    }

    /* access modifiers changed from: 0000 */
    public AtomicLong a() {
        return this.a;
    }

    /* access modifiers changed from: 0000 */
    public AtomicLong b() {
        return this.b;
    }

    /* access modifiers changed from: 0000 */
    public DurationCounter c() {
        return this.c;
    }

    /* access modifiers changed from: 0000 */
    public DurationCounter d() {
        return this.d;
    }

    /* access modifiers changed from: 0000 */
    public DurationCounter e() {
        return this.e;
    }

    /* access modifiers changed from: 0000 */
    public DurationCounter f() {
        return this.f;
    }

    public long getActiveConnectionCount() {
        return this.a.get();
    }

    public long getScheduledConnectionCount() {
        return this.b.get();
    }

    public long getSuccessfulConnectionCount() {
        return this.c.a();
    }

    public long getSuccessfulConnectionAverageDuration() {
        return this.c.b();
    }

    public long getFailedConnectionCount() {
        return this.d.a();
    }

    public long getFailedConnectionAverageDuration() {
        return this.d.b();
    }

    public long getRequestCount() {
        return this.e.a();
    }

    public long getRequestAverageDuration() {
        return this.e.b();
    }

    public long getTaskCount() {
        return this.f.a();
    }

    public long getTaskAverageDuration() {
        return this.f.b();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[activeConnections=");
        sb.append(this.a);
        sb.append(", scheduledConnections=");
        sb.append(this.b);
        sb.append(", successfulConnections=");
        sb.append(this.c);
        sb.append(", failedConnections=");
        sb.append(this.d);
        sb.append(", requests=");
        sb.append(this.e);
        sb.append(", tasks=");
        sb.append(this.f);
        sb.append("]");
        return sb.toString();
    }
}
