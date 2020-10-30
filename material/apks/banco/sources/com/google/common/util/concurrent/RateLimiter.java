package com.google.common.util.concurrent;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.google.common.base.Stopwatch;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import javax.annotation.concurrent.ThreadSafe;

@GwtIncompatible
@ThreadSafe
@Beta
public abstract class RateLimiter {
    private final SleepingStopwatch a;
    private volatile Object b;

    static abstract class SleepingStopwatch {
        /* access modifiers changed from: protected */
        public abstract long a();

        /* access modifiers changed from: protected */
        public abstract void a(long j);

        protected SleepingStopwatch() {
        }

        public static final SleepingStopwatch b() {
            return new SleepingStopwatch() {
                final Stopwatch a = Stopwatch.createStarted();

                /* access modifiers changed from: protected */
                public long a() {
                    return this.a.elapsed(TimeUnit.MICROSECONDS);
                }

                /* access modifiers changed from: protected */
                public void a(long j) {
                    if (j > 0) {
                        Uninterruptibles.sleepUninterruptibly(j, TimeUnit.MICROSECONDS);
                    }
                }
            };
        }
    }

    /* access modifiers changed from: 0000 */
    public abstract double a();

    /* access modifiers changed from: 0000 */
    public abstract long a(long j);

    /* access modifiers changed from: 0000 */
    public abstract void a(double d, long j);

    /* access modifiers changed from: 0000 */
    public abstract long b(int i, long j);

    public static RateLimiter create(double d) {
        return a(SleepingStopwatch.b(), d);
    }

    @VisibleForTesting
    static RateLimiter a(SleepingStopwatch sleepingStopwatch, double d) {
        SmoothBursty smoothBursty = new SmoothBursty(sleepingStopwatch, 1.0d);
        smoothBursty.setRate(d);
        return smoothBursty;
    }

    public static RateLimiter create(double d, long j, TimeUnit timeUnit) {
        Preconditions.checkArgument(j >= 0, "warmupPeriod must not be negative: %s", j);
        return a(SleepingStopwatch.b(), d, j, timeUnit, 3.0d);
    }

    @VisibleForTesting
    static RateLimiter a(SleepingStopwatch sleepingStopwatch, double d, long j, TimeUnit timeUnit, double d2) {
        SmoothWarmingUp smoothWarmingUp = new SmoothWarmingUp(sleepingStopwatch, j, timeUnit, d2);
        smoothWarmingUp.setRate(d);
        return smoothWarmingUp;
    }

    private Object b() {
        Object obj = this.b;
        if (obj == null) {
            synchronized (this) {
                obj = this.b;
                if (obj == null) {
                    obj = new Object();
                    this.b = obj;
                }
            }
        }
        return obj;
    }

    RateLimiter(SleepingStopwatch sleepingStopwatch) {
        this.a = (SleepingStopwatch) Preconditions.checkNotNull(sleepingStopwatch);
    }

    public final void setRate(double d) {
        Preconditions.checkArgument(d > 0.0d && !Double.isNaN(d), "rate must be positive");
        synchronized (b()) {
            a(d, this.a.a());
        }
    }

    public final double getRate() {
        double a2;
        synchronized (b()) {
            a2 = a();
        }
        return a2;
    }

    @CanIgnoreReturnValue
    public double acquire() {
        return acquire(1);
    }

    @CanIgnoreReturnValue
    public double acquire(int i) {
        long a2 = a(i);
        this.a.a(a2);
        return (((double) a2) * 1.0d) / ((double) TimeUnit.SECONDS.toMicros(1));
    }

    /* access modifiers changed from: 0000 */
    public final long a(int i) {
        long a2;
        b(i);
        synchronized (b()) {
            a2 = a(i, this.a.a());
        }
        return a2;
    }

    public boolean tryAcquire(long j, TimeUnit timeUnit) {
        return tryAcquire(1, j, timeUnit);
    }

    public boolean tryAcquire(int i) {
        return tryAcquire(i, 0, TimeUnit.MICROSECONDS);
    }

    public boolean tryAcquire() {
        return tryAcquire(1, 0, TimeUnit.MICROSECONDS);
    }

    public boolean tryAcquire(int i, long j, TimeUnit timeUnit) {
        long max = Math.max(timeUnit.toMicros(j), 0);
        b(i);
        synchronized (b()) {
            long a2 = this.a.a();
            if (!a(a2, max)) {
                return false;
            }
            long a3 = a(i, a2);
            this.a.a(a3);
            return true;
        }
    }

    private boolean a(long j, long j2) {
        return a(j) - j2 <= j;
    }

    /* access modifiers changed from: 0000 */
    public final long a(int i, long j) {
        return Math.max(b(i, j) - j, 0);
    }

    public String toString() {
        return String.format(Locale.ROOT, "RateLimiter[stableRate=%3.1fqps]", new Object[]{Double.valueOf(getRate())});
    }

    private static void b(int i) {
        Preconditions.checkArgument(i > 0, "Requested permits (%s) must be positive", i);
    }
}
