package com.google.common.util.concurrent;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.math.LongMath;
import java.util.concurrent.TimeUnit;

@GwtIncompatible
abstract class SmoothRateLimiter extends RateLimiter {
    double a;
    double b;
    double c;
    private long d;

    static final class SmoothBursty extends SmoothRateLimiter {
        final double d;

        /* access modifiers changed from: 0000 */
        public long b(double d2, double d3) {
            return 0;
        }

        SmoothBursty(SleepingStopwatch sleepingStopwatch, double d2) {
            super(sleepingStopwatch);
            this.d = d2;
        }

        /* access modifiers changed from: 0000 */
        public void a(double d2, double d3) {
            double d4 = this.b;
            this.b = this.d * d2;
            if (d4 == Double.POSITIVE_INFINITY) {
                this.a = this.b;
                return;
            }
            double d5 = 0.0d;
            if (d4 != 0.0d) {
                d5 = (this.a * this.b) / d4;
            }
            this.a = d5;
        }

        /* access modifiers changed from: 0000 */
        public double b() {
            return this.c;
        }
    }

    static final class SmoothWarmingUp extends SmoothRateLimiter {
        private final long d;
        private double e;
        private double f;
        private double g;

        SmoothWarmingUp(SleepingStopwatch sleepingStopwatch, long j, TimeUnit timeUnit, double d2) {
            super(sleepingStopwatch);
            this.d = timeUnit.toMicros(j);
            this.g = d2;
        }

        /* access modifiers changed from: 0000 */
        public void a(double d2, double d3) {
            double d4 = this.b;
            double d5 = this.g * d3;
            this.f = (((double) this.d) * 0.5d) / d3;
            this.b = this.f + ((((double) this.d) * 2.0d) / (d3 + d5));
            this.e = (d5 - d3) / (this.b - this.f);
            if (d4 == Double.POSITIVE_INFINITY) {
                this.a = 0.0d;
            } else {
                this.a = d4 == 0.0d ? this.b : (this.a * this.b) / d4;
            }
        }

        /* access modifiers changed from: 0000 */
        public long b(double d2, double d3) {
            long j;
            double d4 = d2 - this.f;
            if (d4 > 0.0d) {
                double min = Math.min(d4, d3);
                j = (long) (((a(d4) + a(d4 - min)) * min) / 2.0d);
                d3 -= min;
            } else {
                j = 0;
            }
            return (long) (((double) j) + (this.c * d3));
        }

        private double a(double d2) {
            return this.c + (d2 * this.e);
        }

        /* access modifiers changed from: 0000 */
        public double b() {
            return ((double) this.d) / this.b;
        }
    }

    /* access modifiers changed from: 0000 */
    public abstract void a(double d2, double d3);

    /* access modifiers changed from: 0000 */
    public abstract double b();

    /* access modifiers changed from: 0000 */
    public abstract long b(double d2, double d3);

    private SmoothRateLimiter(SleepingStopwatch sleepingStopwatch) {
        super(sleepingStopwatch);
        this.d = 0;
    }

    /* access modifiers changed from: 0000 */
    public final void a(double d2, long j) {
        b(j);
        double micros = ((double) TimeUnit.SECONDS.toMicros(1)) / d2;
        this.c = micros;
        a(d2, micros);
    }

    /* access modifiers changed from: 0000 */
    public final double a() {
        return ((double) TimeUnit.SECONDS.toMicros(1)) / this.c;
    }

    /* access modifiers changed from: 0000 */
    public final long a(long j) {
        return this.d;
    }

    /* access modifiers changed from: 0000 */
    public final long b(int i, long j) {
        b(j);
        long j2 = this.d;
        double d2 = (double) i;
        double min = Math.min(d2, this.a);
        this.d = LongMath.saturatedAdd(this.d, b(this.a, min) + ((long) ((d2 - min) * this.c)));
        this.a -= min;
        return j2;
    }

    /* access modifiers changed from: 0000 */
    public void b(long j) {
        if (j > this.d) {
            this.a = Math.min(this.b, this.a + (((double) (j - this.d)) / b()));
            this.d = j;
        }
    }
}
