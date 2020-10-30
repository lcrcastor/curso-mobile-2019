package com.google.common.primitives;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import java.math.BigInteger;
import javax.annotation.Nullable;

@GwtCompatible(emulated = true)
public final class UnsignedInteger extends Number implements Comparable<UnsignedInteger> {
    public static final UnsignedInteger MAX_VALUE = fromIntBits(-1);
    public static final UnsignedInteger ONE = fromIntBits(1);
    public static final UnsignedInteger ZERO = fromIntBits(0);
    private final int a;

    private UnsignedInteger(int i) {
        this.a = i & -1;
    }

    public static UnsignedInteger fromIntBits(int i) {
        return new UnsignedInteger(i);
    }

    public static UnsignedInteger valueOf(long j) {
        Preconditions.checkArgument((j & 4294967295L) == j, "value (%s) is outside the range for an unsigned integer value", j);
        return fromIntBits((int) j);
    }

    public static UnsignedInteger valueOf(BigInteger bigInteger) {
        Preconditions.checkNotNull(bigInteger);
        Preconditions.checkArgument(bigInteger.signum() >= 0 && bigInteger.bitLength() <= 32, "value (%s) is outside the range for an unsigned integer value", (Object) bigInteger);
        return fromIntBits(bigInteger.intValue());
    }

    public static UnsignedInteger valueOf(String str) {
        return valueOf(str, 10);
    }

    public static UnsignedInteger valueOf(String str, int i) {
        return fromIntBits(UnsignedInts.parseUnsignedInt(str, i));
    }

    public UnsignedInteger plus(UnsignedInteger unsignedInteger) {
        return fromIntBits(this.a + ((UnsignedInteger) Preconditions.checkNotNull(unsignedInteger)).a);
    }

    public UnsignedInteger minus(UnsignedInteger unsignedInteger) {
        return fromIntBits(this.a - ((UnsignedInteger) Preconditions.checkNotNull(unsignedInteger)).a);
    }

    @GwtIncompatible
    public UnsignedInteger times(UnsignedInteger unsignedInteger) {
        return fromIntBits(this.a * ((UnsignedInteger) Preconditions.checkNotNull(unsignedInteger)).a);
    }

    public UnsignedInteger dividedBy(UnsignedInteger unsignedInteger) {
        return fromIntBits(UnsignedInts.divide(this.a, ((UnsignedInteger) Preconditions.checkNotNull(unsignedInteger)).a));
    }

    public UnsignedInteger mod(UnsignedInteger unsignedInteger) {
        return fromIntBits(UnsignedInts.remainder(this.a, ((UnsignedInteger) Preconditions.checkNotNull(unsignedInteger)).a));
    }

    public int intValue() {
        return this.a;
    }

    public long longValue() {
        return UnsignedInts.toLong(this.a);
    }

    public float floatValue() {
        return (float) longValue();
    }

    public double doubleValue() {
        return (double) longValue();
    }

    public BigInteger bigIntegerValue() {
        return BigInteger.valueOf(longValue());
    }

    public int compareTo(UnsignedInteger unsignedInteger) {
        Preconditions.checkNotNull(unsignedInteger);
        return UnsignedInts.compare(this.a, unsignedInteger.a);
    }

    public int hashCode() {
        return this.a;
    }

    public boolean equals(@Nullable Object obj) {
        boolean z = false;
        if (!(obj instanceof UnsignedInteger)) {
            return false;
        }
        if (this.a == ((UnsignedInteger) obj).a) {
            z = true;
        }
        return z;
    }

    public String toString() {
        return toString(10);
    }

    public String toString(int i) {
        return UnsignedInts.toString(this.a, i);
    }
}
