package com.google.common.primitives;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.io.Serializable;
import java.math.BigInteger;
import javax.annotation.Nullable;

@GwtCompatible(serializable = true)
public final class UnsignedLong extends Number implements Serializable, Comparable<UnsignedLong> {
    public static final UnsignedLong MAX_VALUE = new UnsignedLong(-1);
    public static final UnsignedLong ONE = new UnsignedLong(1);
    public static final UnsignedLong ZERO = new UnsignedLong(0);
    private final long a;

    private UnsignedLong(long j) {
        this.a = j;
    }

    public static UnsignedLong fromLongBits(long j) {
        return new UnsignedLong(j);
    }

    @CanIgnoreReturnValue
    public static UnsignedLong valueOf(long j) {
        Preconditions.checkArgument(j >= 0, "value (%s) is outside the range for an unsigned long value", j);
        return fromLongBits(j);
    }

    @CanIgnoreReturnValue
    public static UnsignedLong valueOf(BigInteger bigInteger) {
        Preconditions.checkNotNull(bigInteger);
        Preconditions.checkArgument(bigInteger.signum() >= 0 && bigInteger.bitLength() <= 64, "value (%s) is outside the range for an unsigned long value", (Object) bigInteger);
        return fromLongBits(bigInteger.longValue());
    }

    @CanIgnoreReturnValue
    public static UnsignedLong valueOf(String str) {
        return valueOf(str, 10);
    }

    @CanIgnoreReturnValue
    public static UnsignedLong valueOf(String str, int i) {
        return fromLongBits(UnsignedLongs.parseUnsignedLong(str, i));
    }

    public UnsignedLong plus(UnsignedLong unsignedLong) {
        return fromLongBits(this.a + ((UnsignedLong) Preconditions.checkNotNull(unsignedLong)).a);
    }

    public UnsignedLong minus(UnsignedLong unsignedLong) {
        return fromLongBits(this.a - ((UnsignedLong) Preconditions.checkNotNull(unsignedLong)).a);
    }

    public UnsignedLong times(UnsignedLong unsignedLong) {
        return fromLongBits(this.a * ((UnsignedLong) Preconditions.checkNotNull(unsignedLong)).a);
    }

    public UnsignedLong dividedBy(UnsignedLong unsignedLong) {
        return fromLongBits(UnsignedLongs.divide(this.a, ((UnsignedLong) Preconditions.checkNotNull(unsignedLong)).a));
    }

    public UnsignedLong mod(UnsignedLong unsignedLong) {
        return fromLongBits(UnsignedLongs.remainder(this.a, ((UnsignedLong) Preconditions.checkNotNull(unsignedLong)).a));
    }

    public int intValue() {
        return (int) this.a;
    }

    public long longValue() {
        return this.a;
    }

    public float floatValue() {
        float f = (float) (this.a & Long.MAX_VALUE);
        return this.a < 0 ? f + 9.223372E18f : f;
    }

    public double doubleValue() {
        double d = (double) (this.a & Long.MAX_VALUE);
        return this.a < 0 ? d + 9.223372036854776E18d : d;
    }

    public BigInteger bigIntegerValue() {
        BigInteger valueOf = BigInteger.valueOf(this.a & Long.MAX_VALUE);
        return this.a < 0 ? valueOf.setBit(63) : valueOf;
    }

    public int compareTo(UnsignedLong unsignedLong) {
        Preconditions.checkNotNull(unsignedLong);
        return UnsignedLongs.compare(this.a, unsignedLong.a);
    }

    public int hashCode() {
        return Longs.hashCode(this.a);
    }

    public boolean equals(@Nullable Object obj) {
        boolean z = false;
        if (!(obj instanceof UnsignedLong)) {
            return false;
        }
        if (this.a == ((UnsignedLong) obj).a) {
            z = true;
        }
        return z;
    }

    public String toString() {
        return UnsignedLongs.toString(this.a);
    }

    public String toString(int i) {
        return UnsignedLongs.toString(this.a, i);
    }
}
