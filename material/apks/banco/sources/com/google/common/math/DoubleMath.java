package com.google.common.math;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.google.common.primitives.Booleans;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.Iterator;

@GwtCompatible(emulated = true)
public final class DoubleMath {
    @VisibleForTesting
    static final double[] a = {1.0d, 2.0922789888E13d, 2.631308369336935E35d, 1.2413915592536073E61d, 1.2688693218588417E89d, 7.156945704626381E118d, 9.916779348709496E149d, 1.974506857221074E182d, 3.856204823625804E215d, 5.5502938327393044E249d, 4.7147236359920616E284d};
    private static final double b = Math.log(2.0d);

    /* renamed from: com.google.common.math.DoubleMath$1 reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] a = new int[RoundingMode.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(16:0|1|2|3|4|5|6|7|8|9|10|11|12|13|14|(3:15|16|18)) */
        /* JADX WARNING: Can't wrap try/catch for region: R(18:0|1|2|3|4|5|6|7|8|9|10|11|12|13|14|15|16|18) */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:11:0x0040 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:13:0x004b */
        /* JADX WARNING: Missing exception handler attribute for start block: B:15:0x0056 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0014 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001f */
        /* JADX WARNING: Missing exception handler attribute for start block: B:7:0x002a */
        /* JADX WARNING: Missing exception handler attribute for start block: B:9:0x0035 */
        static {
            /*
                java.math.RoundingMode[] r0 = java.math.RoundingMode.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                a = r0
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x0014 }
                java.math.RoundingMode r1 = java.math.RoundingMode.UNNECESSARY     // Catch:{ NoSuchFieldError -> 0x0014 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0014 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0014 }
            L_0x0014:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x001f }
                java.math.RoundingMode r1 = java.math.RoundingMode.FLOOR     // Catch:{ NoSuchFieldError -> 0x001f }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001f }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001f }
            L_0x001f:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x002a }
                java.math.RoundingMode r1 = java.math.RoundingMode.CEILING     // Catch:{ NoSuchFieldError -> 0x002a }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x002a }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x002a }
            L_0x002a:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x0035 }
                java.math.RoundingMode r1 = java.math.RoundingMode.DOWN     // Catch:{ NoSuchFieldError -> 0x0035 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0035 }
                r2 = 4
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0035 }
            L_0x0035:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x0040 }
                java.math.RoundingMode r1 = java.math.RoundingMode.UP     // Catch:{ NoSuchFieldError -> 0x0040 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0040 }
                r2 = 5
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0040 }
            L_0x0040:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x004b }
                java.math.RoundingMode r1 = java.math.RoundingMode.HALF_EVEN     // Catch:{ NoSuchFieldError -> 0x004b }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x004b }
                r2 = 6
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x004b }
            L_0x004b:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x0056 }
                java.math.RoundingMode r1 = java.math.RoundingMode.HALF_UP     // Catch:{ NoSuchFieldError -> 0x0056 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0056 }
                r2 = 7
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0056 }
            L_0x0056:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x0062 }
                java.math.RoundingMode r1 = java.math.RoundingMode.HALF_DOWN     // Catch:{ NoSuchFieldError -> 0x0062 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0062 }
                r2 = 8
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0062 }
            L_0x0062:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.common.math.DoubleMath.AnonymousClass1.<clinit>():void");
        }
    }

    @GwtIncompatible
    static double a(double d, RoundingMode roundingMode) {
        if (!DoubleUtils.b(d)) {
            throw new ArithmeticException("input is infinite or NaN");
        }
        switch (AnonymousClass1.a[roundingMode.ordinal()]) {
            case 1:
                MathPreconditions.a(isMathematicalInteger(d));
                return d;
            case 2:
                return (d >= 0.0d || isMathematicalInteger(d)) ? d : (double) (((long) d) - 1);
            case 3:
                return (d <= 0.0d || isMathematicalInteger(d)) ? d : (double) (((long) d) + 1);
            case 4:
                return d;
            case 5:
                if (isMathematicalInteger(d)) {
                    return d;
                }
                return (double) (((long) d) + ((long) (d > 0.0d ? 1 : -1)));
            case 6:
                return Math.rint(d);
            case 7:
                double rint = Math.rint(d);
                return Math.abs(d - rint) == 0.5d ? d + Math.copySign(0.5d, d) : rint;
            case 8:
                double rint2 = Math.rint(d);
                return Math.abs(d - rint2) == 0.5d ? d : rint2;
            default:
                throw new AssertionError();
        }
    }

    @GwtIncompatible
    public static int roundToInt(double d, RoundingMode roundingMode) {
        double a2 = a(d, roundingMode);
        boolean z = false;
        boolean z2 = a2 > -2.147483649E9d;
        if (a2 < 2.147483648E9d) {
            z = true;
        }
        MathPreconditions.b(z2 & z);
        return (int) a2;
    }

    @GwtIncompatible
    public static long roundToLong(double d, RoundingMode roundingMode) {
        double a2 = a(d, roundingMode);
        boolean z = false;
        boolean z2 = -9.223372036854776E18d - a2 < 1.0d;
        if (a2 < 9.223372036854776E18d) {
            z = true;
        }
        MathPreconditions.b(z2 & z);
        return (long) a2;
    }

    @GwtIncompatible
    public static BigInteger roundToBigInteger(double d, RoundingMode roundingMode) {
        double a2 = a(d, roundingMode);
        boolean z = false;
        boolean z2 = -9.223372036854776E18d - a2 < 1.0d;
        if (a2 < 9.223372036854776E18d) {
            z = true;
        }
        if (z2 && z) {
            return BigInteger.valueOf((long) a2);
        }
        BigInteger shiftLeft = BigInteger.valueOf(DoubleUtils.a(a2)).shiftLeft(Math.getExponent(a2) - 52);
        if (a2 < 0.0d) {
            shiftLeft = shiftLeft.negate();
        }
        return shiftLeft;
    }

    @GwtIncompatible
    public static boolean isPowerOfTwo(double d) {
        return d > 0.0d && DoubleUtils.b(d) && LongMath.isPowerOfTwo(DoubleUtils.a(d));
    }

    public static double log2(double d) {
        return Math.log(d) / b;
    }

    @GwtIncompatible
    public static int log2(double d, RoundingMode roundingMode) {
        boolean z = false;
        Preconditions.checkArgument(d > 0.0d && DoubleUtils.b(d), "x must be positive and finite");
        int exponent = Math.getExponent(d);
        if (!DoubleUtils.c(d)) {
            return log2(d * 4.503599627370496E15d, roundingMode) - 52;
        }
        switch (AnonymousClass1.a[roundingMode.ordinal()]) {
            case 1:
                MathPreconditions.a(isPowerOfTwo(d));
                break;
            case 2:
                break;
            case 3:
                z = !isPowerOfTwo(d);
                break;
            case 4:
                if (exponent < 0) {
                    z = true;
                }
                z &= !isPowerOfTwo(d);
                break;
            case 5:
                if (exponent >= 0) {
                    z = true;
                }
                z &= !isPowerOfTwo(d);
                break;
            case 6:
            case 7:
            case 8:
                double d2 = DoubleUtils.d(d);
                if (d2 * d2 > 2.0d) {
                    z = true;
                    break;
                }
                break;
            default:
                throw new AssertionError();
        }
        if (z) {
            exponent++;
        }
        return exponent;
    }

    @GwtIncompatible
    public static boolean isMathematicalInteger(double d) {
        return DoubleUtils.b(d) && (d == 0.0d || 52 - Long.numberOfTrailingZeros(DoubleUtils.a(d)) <= Math.getExponent(d));
    }

    public static double factorial(int i) {
        MathPreconditions.b("n", i);
        if (i > 170) {
            return Double.POSITIVE_INFINITY;
        }
        double d = 1.0d;
        int i2 = i & -16;
        while (true) {
            i2++;
            if (i2 > i) {
                return d * a[i >> 4];
            }
            d *= (double) i2;
        }
    }

    public static boolean fuzzyEquals(double d, double d2, double d3) {
        MathPreconditions.a("tolerance", d3);
        return Math.copySign(d - d2, 1.0d) <= d3 || d == d2 || (Double.isNaN(d) && Double.isNaN(d2));
    }

    public static int fuzzyCompare(double d, double d2, double d3) {
        if (fuzzyEquals(d, d2, d3)) {
            return 0;
        }
        if (d < d2) {
            return -1;
        }
        if (d > d2) {
            return 1;
        }
        return Booleans.compare(Double.isNaN(d), Double.isNaN(d2));
    }

    @GwtIncompatible
    @Deprecated
    public static double mean(double... dArr) {
        int i = 1;
        Preconditions.checkArgument(dArr.length > 0, "Cannot take mean of 0 values");
        double a2 = a(dArr[0]);
        long j = 1;
        while (i < dArr.length) {
            a(dArr[i]);
            long j2 = j + 1;
            a2 += (dArr[i] - a2) / ((double) j2);
            i++;
            j = j2;
        }
        return a2;
    }

    @Deprecated
    public static double mean(int... iArr) {
        int i = 0;
        Preconditions.checkArgument(iArr.length > 0, "Cannot take mean of 0 values");
        long j = 0;
        while (i < iArr.length) {
            i++;
            j += (long) iArr[i];
        }
        return ((double) j) / ((double) iArr.length);
    }

    @Deprecated
    public static double mean(long... jArr) {
        int i = 1;
        Preconditions.checkArgument(jArr.length > 0, "Cannot take mean of 0 values");
        double d = (double) jArr[0];
        long j = 1;
        while (i < jArr.length) {
            long j2 = j + 1;
            d += (((double) jArr[i]) - d) / ((double) j2);
            i++;
            j = j2;
        }
        return d;
    }

    @GwtIncompatible
    @Deprecated
    public static double mean(Iterable<? extends Number> iterable) {
        return mean(iterable.iterator());
    }

    @GwtIncompatible
    @Deprecated
    public static double mean(Iterator<? extends Number> it) {
        Preconditions.checkArgument(it.hasNext(), "Cannot take mean of 0 values");
        double a2 = a(((Number) it.next()).doubleValue());
        long j = 1;
        while (it.hasNext()) {
            long j2 = j + 1;
            a2 += (a(((Number) it.next()).doubleValue()) - a2) / ((double) j2);
            j = j2;
        }
        return a2;
    }

    @GwtIncompatible
    @CanIgnoreReturnValue
    private static double a(double d) {
        Preconditions.checkArgument(DoubleUtils.b(d));
        return d;
    }

    private DoubleMath() {
    }
}
