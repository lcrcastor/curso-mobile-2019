package com.google.common.math;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.common.primitives.Doubles;
import java.util.Iterator;

@GwtIncompatible
@Beta
public final class StatsAccumulator {
    private long a = 0;
    private double b = 0.0d;
    private double c = 0.0d;
    private double d = Double.NaN;
    private double e = Double.NaN;

    public void add(double d2) {
        if (this.a == 0) {
            this.a = 1;
            this.b = d2;
            this.d = d2;
            this.e = d2;
            if (!Doubles.isFinite(d2)) {
                this.c = Double.NaN;
                return;
            }
            return;
        }
        this.a++;
        if (!Doubles.isFinite(d2) || !Doubles.isFinite(this.b)) {
            this.b = a(this.b, d2);
            this.c = Double.NaN;
        } else {
            double d3 = d2 - this.b;
            this.b += d3 / ((double) this.a);
            this.c += d3 * (d2 - this.b);
        }
        this.d = Math.min(this.d, d2);
        this.e = Math.max(this.e, d2);
    }

    public void addAll(Iterable<? extends Number> iterable) {
        for (Number doubleValue : iterable) {
            add(doubleValue.doubleValue());
        }
    }

    public void addAll(Iterator<? extends Number> it) {
        while (it.hasNext()) {
            add(((Number) it.next()).doubleValue());
        }
    }

    public void addAll(double... dArr) {
        for (double add : dArr) {
            add(add);
        }
    }

    public void addAll(int... iArr) {
        for (int i : iArr) {
            add((double) i);
        }
    }

    public void addAll(long... jArr) {
        for (long j : jArr) {
            add((double) j);
        }
    }

    public void addAll(Stats stats) {
        if (stats.count() != 0) {
            if (this.a == 0) {
                this.a = stats.count();
                this.b = stats.mean();
                this.c = stats.a();
                this.d = stats.min();
                this.e = stats.max();
            } else {
                this.a += stats.count();
                if (!Doubles.isFinite(this.b) || !Doubles.isFinite(stats.mean())) {
                    this.b = a(this.b, stats.mean());
                    this.c = Double.NaN;
                } else {
                    double mean = stats.mean() - this.b;
                    this.b += (((double) stats.count()) * mean) / ((double) this.a);
                    this.c += stats.a() + (mean * (stats.mean() - this.b) * ((double) stats.count()));
                }
                this.d = Math.min(this.d, stats.min());
                this.e = Math.max(this.e, stats.max());
            }
        }
    }

    public Stats snapshot() {
        Stats stats = new Stats(this.a, this.b, this.c, this.d, this.e);
        return stats;
    }

    public long count() {
        return this.a;
    }

    public double mean() {
        Preconditions.checkState(this.a != 0);
        return this.b;
    }

    public final double sum() {
        return this.b * ((double) this.a);
    }

    public final double populationVariance() {
        Preconditions.checkState(this.a != 0);
        if (Double.isNaN(this.c)) {
            return Double.NaN;
        }
        if (this.a == 1) {
            return 0.0d;
        }
        return DoubleUtils.e(this.c) / ((double) this.a);
    }

    public final double populationStandardDeviation() {
        return Math.sqrt(populationVariance());
    }

    public final double sampleVariance() {
        Preconditions.checkState(this.a > 1);
        if (Double.isNaN(this.c)) {
            return Double.NaN;
        }
        return DoubleUtils.e(this.c) / ((double) (this.a - 1));
    }

    public final double sampleStandardDeviation() {
        return Math.sqrt(sampleVariance());
    }

    public double min() {
        Preconditions.checkState(this.a != 0);
        return this.d;
    }

    public double max() {
        Preconditions.checkState(this.a != 0);
        return this.e;
    }

    /* access modifiers changed from: 0000 */
    public double a() {
        return this.c;
    }

    static double a(double d2, double d3) {
        if (Doubles.isFinite(d2)) {
            return d3;
        }
        if (Doubles.isFinite(d3) || d2 == d3) {
            return d2;
        }
        return Double.NaN;
    }
}
