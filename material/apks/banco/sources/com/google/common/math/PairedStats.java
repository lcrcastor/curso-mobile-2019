package com.google.common.math;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import javax.annotation.Nullable;

@GwtIncompatible
@Beta
public final class PairedStats implements Serializable {
    private static final long serialVersionUID = 0;
    private final Stats a;
    private final Stats b;
    private final double c;

    private static double a(double d) {
        if (d > 0.0d) {
            return d;
        }
        return Double.MIN_VALUE;
    }

    private static double b(double d) {
        if (d >= 1.0d) {
            return 1.0d;
        }
        if (d <= -1.0d) {
            return -1.0d;
        }
        return d;
    }

    PairedStats(Stats stats, Stats stats2, double d) {
        this.a = stats;
        this.b = stats2;
        this.c = d;
    }

    public long count() {
        return this.a.count();
    }

    public Stats xStats() {
        return this.a;
    }

    public Stats yStats() {
        return this.b;
    }

    public double populationCovariance() {
        Preconditions.checkState(count() != 0);
        return this.c / ((double) count());
    }

    public double sampleCovariance() {
        Preconditions.checkState(count() > 1);
        return this.c / ((double) (count() - 1));
    }

    public double pearsonsCorrelationCoefficient() {
        boolean z = false;
        Preconditions.checkState(count() > 1);
        if (Double.isNaN(this.c)) {
            return Double.NaN;
        }
        double a2 = xStats().a();
        double a3 = yStats().a();
        Preconditions.checkState(a2 > 0.0d);
        if (a3 > 0.0d) {
            z = true;
        }
        Preconditions.checkState(z);
        return b(this.c / Math.sqrt(a(a2 * a3)));
    }

    public LinearTransformation leastSquaresFit() {
        boolean z = false;
        Preconditions.checkState(count() > 1);
        if (Double.isNaN(this.c)) {
            return LinearTransformation.forNaN();
        }
        double a2 = this.a.a();
        if (a2 <= 0.0d) {
            if (this.b.a() > 0.0d) {
                z = true;
            }
            Preconditions.checkState(z);
            return LinearTransformation.vertical(this.a.mean());
        } else if (this.b.a() > 0.0d) {
            return LinearTransformation.mapping(this.a.mean(), this.b.mean()).withSlope(this.c / a2);
        } else {
            return LinearTransformation.horizontal(this.b.mean());
        }
    }

    public boolean equals(@Nullable Object obj) {
        boolean z = false;
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        PairedStats pairedStats = (PairedStats) obj;
        if (this.a.equals(pairedStats.a) && this.b.equals(pairedStats.b) && Double.doubleToLongBits(this.c) == Double.doubleToLongBits(pairedStats.c)) {
            z = true;
        }
        return z;
    }

    public int hashCode() {
        return Objects.hashCode(this.a, this.b, Double.valueOf(this.c));
    }

    public String toString() {
        if (count() > 0) {
            return MoreObjects.toStringHelper((Object) this).add("xStats", (Object) this.a).add("yStats", (Object) this.b).add("populationCovariance", populationCovariance()).toString();
        }
        return MoreObjects.toStringHelper((Object) this).add("xStats", (Object) this.a).add("yStats", (Object) this.b).toString();
    }

    /* access modifiers changed from: 0000 */
    public double a() {
        return this.c;
    }

    public byte[] toByteArray() {
        ByteBuffer order = ByteBuffer.allocate(88).order(ByteOrder.LITTLE_ENDIAN);
        this.a.a(order);
        this.b.a(order);
        order.putDouble(this.c);
        return order.array();
    }

    public static PairedStats fromByteArray(byte[] bArr) {
        Preconditions.checkNotNull(bArr);
        Preconditions.checkArgument(bArr.length == 88, "Expected PairedStats.BYTES = %s, got %s", 88, bArr.length);
        ByteBuffer order = ByteBuffer.wrap(bArr).order(ByteOrder.LITTLE_ENDIAN);
        return new PairedStats(Stats.b(order), Stats.b(order), order.getDouble());
    }
}
