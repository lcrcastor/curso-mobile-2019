package com.google.common.math;

import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.google.common.primitives.Ints;
import java.math.RoundingMode;
import org.bouncycastle.apache.bzip2.BZip2Constants;
import org.bouncycastle.asn1.eac.EACTags;
import org.bouncycastle.crypto.tls.CipherSuite;

@GwtCompatible(emulated = true)
public final class IntMath {
    @VisibleForTesting
    static final byte[] a = {9, 9, 9, 8, 8, 8, 7, 7, 7, 6, 6, 6, 6, 5, 5, 5, 4, 4, 4, 3, 3, 3, 3, 2, 2, 2, 1, 1, 1, 0, 0, 0, 0};
    @VisibleForTesting
    static final int[] b = {1, 10, 100, 1000, 10000, BZip2Constants.baseBlockSize, 1000000, 10000000, 100000000, 1000000000};
    @VisibleForTesting
    static final int[] c = {3, 31, 316, 3162, 31622, 316227, 3162277, 31622776, 316227766, SubsamplingScaleImageView.TILE_SIZE_AUTO};
    @VisibleForTesting
    static int[] d = {SubsamplingScaleImageView.TILE_SIZE_AUTO, SubsamplingScaleImageView.TILE_SIZE_AUTO, 65536, 2345, 477, CipherSuite.TLS_DH_DSS_WITH_CAMELLIA_256_CBC_SHA256, 110, 75, 58, 49, 43, 39, 37, 35, 34, 34, 33};
    private static final int[] e = {1, 1, 2, 6, 24, EACTags.COMPATIBLE_TAG_ALLOCATION_AUTHORITY, 720, 5040, 40320, 362880, 3628800, 39916800, 479001600};

    /* renamed from: com.google.common.math.IntMath$1 reason: invalid class name */
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
                java.math.RoundingMode r1 = java.math.RoundingMode.DOWN     // Catch:{ NoSuchFieldError -> 0x001f }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001f }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001f }
            L_0x001f:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x002a }
                java.math.RoundingMode r1 = java.math.RoundingMode.FLOOR     // Catch:{ NoSuchFieldError -> 0x002a }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x002a }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x002a }
            L_0x002a:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x0035 }
                java.math.RoundingMode r1 = java.math.RoundingMode.UP     // Catch:{ NoSuchFieldError -> 0x0035 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0035 }
                r2 = 4
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0035 }
            L_0x0035:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x0040 }
                java.math.RoundingMode r1 = java.math.RoundingMode.CEILING     // Catch:{ NoSuchFieldError -> 0x0040 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0040 }
                r2 = 5
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0040 }
            L_0x0040:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x004b }
                java.math.RoundingMode r1 = java.math.RoundingMode.HALF_DOWN     // Catch:{ NoSuchFieldError -> 0x004b }
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
                java.math.RoundingMode r1 = java.math.RoundingMode.HALF_EVEN     // Catch:{ NoSuchFieldError -> 0x0062 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0062 }
                r2 = 8
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0062 }
            L_0x0062:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.common.math.IntMath.AnonymousClass1.<clinit>():void");
        }
    }

    @VisibleForTesting
    static int a(int i, int i2) {
        return (((i - i2) ^ -1) ^ -1) >>> 31;
    }

    public static boolean isPowerOfTwo(int i) {
        boolean z = false;
        boolean z2 = i > 0;
        if ((i & (i - 1)) == 0) {
            z = true;
        }
        return z2 & z;
    }

    public static int mean(int i, int i2) {
        return (i & i2) + ((i ^ i2) >> 1);
    }

    @Beta
    public static int ceilingPowerOfTwo(int i) {
        MathPreconditions.a("x", i);
        if (i <= 1073741824) {
            return 1 << (-Integer.numberOfLeadingZeros(i - 1));
        }
        StringBuilder sb = new StringBuilder();
        sb.append("ceilingPowerOfTwo(");
        sb.append(i);
        sb.append(") not representable as an int");
        throw new ArithmeticException(sb.toString());
    }

    @Beta
    public static int floorPowerOfTwo(int i) {
        MathPreconditions.a("x", i);
        return Integer.highestOneBit(i);
    }

    public static int log2(int i, RoundingMode roundingMode) {
        MathPreconditions.a("x", i);
        switch (AnonymousClass1.a[roundingMode.ordinal()]) {
            case 1:
                MathPreconditions.a(isPowerOfTwo(i));
                break;
            case 2:
            case 3:
                break;
            case 4:
            case 5:
                return 32 - Integer.numberOfLeadingZeros(i - 1);
            case 6:
            case 7:
            case 8:
                int numberOfLeadingZeros = Integer.numberOfLeadingZeros(i);
                return (31 - numberOfLeadingZeros) + a(-1257966797 >>> numberOfLeadingZeros, i);
            default:
                throw new AssertionError();
        }
        return 31 - Integer.numberOfLeadingZeros(i);
    }

    @GwtIncompatible
    public static int log10(int i, RoundingMode roundingMode) {
        MathPreconditions.a("x", i);
        int a2 = a(i);
        int i2 = b[a2];
        switch (AnonymousClass1.a[roundingMode.ordinal()]) {
            case 1:
                MathPreconditions.a(i == i2);
                break;
            case 2:
            case 3:
                break;
            case 4:
            case 5:
                return a2 + a(i2, i);
            case 6:
            case 7:
            case 8:
                return a2 + a(c[a2], i);
            default:
                throw new AssertionError();
        }
        return a2;
    }

    private static int a(int i) {
        byte b2 = a[Integer.numberOfLeadingZeros(i)];
        return b2 - a(i, b[b2]);
    }

    @GwtIncompatible
    public static int pow(int i, int i2) {
        MathPreconditions.b("exponent", i2);
        int i3 = 0;
        int i4 = 1;
        switch (i) {
            case -2:
                if (i2 >= 32) {
                    return 0;
                }
                return (i2 & 1) == 0 ? 1 << i2 : -(1 << i2);
            case -1:
                if ((i2 & 1) != 0) {
                    i4 = -1;
                }
                return i4;
            case 0:
                if (i2 == 0) {
                    i3 = 1;
                }
                return i3;
            case 1:
                return 1;
            case 2:
                if (i2 < 32) {
                    i3 = 1 << i2;
                }
                return i3;
            default:
                int i5 = i;
                int i6 = 1;
                while (true) {
                    switch (i2) {
                        case 0:
                            return i6;
                        case 1:
                            return i5 * i6;
                        default:
                            i6 *= (i2 & 1) == 0 ? 1 : i5;
                            i5 *= i5;
                            i2 >>= 1;
                    }
                }
        }
    }

    @GwtIncompatible
    public static int sqrt(int i, RoundingMode roundingMode) {
        MathPreconditions.b("x", i);
        int b2 = b(i);
        switch (AnonymousClass1.a[roundingMode.ordinal()]) {
            case 1:
                MathPreconditions.a(b2 * b2 == i);
                break;
            case 2:
            case 3:
                break;
            case 4:
            case 5:
                return b2 + a(b2 * b2, i);
            case 6:
            case 7:
            case 8:
                return b2 + a((b2 * b2) + b2, i);
            default:
                throw new AssertionError();
        }
        return b2;
    }

    private static int b(int i) {
        return (int) Math.sqrt((double) i);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:24:0x004c, code lost:
        if (((r7 == java.math.RoundingMode.HALF_EVEN) & ((r0 & 1) != 0)) != false) goto L_0x0060;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x004f, code lost:
        if (r1 > 0) goto L_0x0060;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0052, code lost:
        if (r5 > 0) goto L_0x0060;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0055, code lost:
        if (r5 < 0) goto L_0x0060;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static int divide(int r5, int r6, java.math.RoundingMode r7) {
        /*
            com.google.common.base.Preconditions.checkNotNull(r7)
            if (r6 != 0) goto L_0x000d
            java.lang.ArithmeticException r5 = new java.lang.ArithmeticException
            java.lang.String r6 = "/ by zero"
            r5.<init>(r6)
            throw r5
        L_0x000d:
            int r0 = r5 / r6
            int r1 = r6 * r0
            int r1 = r5 - r1
            if (r1 != 0) goto L_0x0016
            return r0
        L_0x0016:
            r5 = r5 ^ r6
            int r5 = r5 >> 31
            r2 = 1
            r5 = r5 | r2
            int[] r3 = com.google.common.math.IntMath.AnonymousClass1.a
            int r4 = r7.ordinal()
            r3 = r3[r4]
            r4 = 0
            switch(r3) {
                case 1: goto L_0x0058;
                case 2: goto L_0x005f;
                case 3: goto L_0x0055;
                case 4: goto L_0x0060;
                case 5: goto L_0x0052;
                case 6: goto L_0x002d;
                case 7: goto L_0x002d;
                case 8: goto L_0x002d;
                default: goto L_0x0027;
            }
        L_0x0027:
            java.lang.AssertionError r5 = new java.lang.AssertionError
            r5.<init>()
            throw r5
        L_0x002d:
            int r1 = java.lang.Math.abs(r1)
            int r6 = java.lang.Math.abs(r6)
            int r6 = r6 - r1
            int r1 = r1 - r6
            if (r1 != 0) goto L_0x004f
            java.math.RoundingMode r6 = java.math.RoundingMode.HALF_UP
            if (r7 == r6) goto L_0x0060
            java.math.RoundingMode r6 = java.math.RoundingMode.HALF_EVEN
            if (r7 != r6) goto L_0x0043
            r6 = 1
            goto L_0x0044
        L_0x0043:
            r6 = 0
        L_0x0044:
            r7 = r0 & 1
            if (r7 == 0) goto L_0x004a
            r7 = 1
            goto L_0x004b
        L_0x004a:
            r7 = 0
        L_0x004b:
            r6 = r6 & r7
            if (r6 == 0) goto L_0x005f
            goto L_0x0060
        L_0x004f:
            if (r1 <= 0) goto L_0x005f
            goto L_0x0060
        L_0x0052:
            if (r5 <= 0) goto L_0x005f
            goto L_0x0060
        L_0x0055:
            if (r5 >= 0) goto L_0x005f
            goto L_0x0060
        L_0x0058:
            if (r1 != 0) goto L_0x005b
            goto L_0x005c
        L_0x005b:
            r2 = 0
        L_0x005c:
            com.google.common.math.MathPreconditions.a(r2)
        L_0x005f:
            r2 = 0
        L_0x0060:
            if (r2 == 0) goto L_0x0063
            int r0 = r0 + r5
        L_0x0063:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.common.math.IntMath.divide(int, int, java.math.RoundingMode):int");
    }

    public static int mod(int i, int i2) {
        if (i2 <= 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("Modulus ");
            sb.append(i2);
            sb.append(" must be > 0");
            throw new ArithmeticException(sb.toString());
        }
        int i3 = i % i2;
        return i3 >= 0 ? i3 : i3 + i2;
    }

    public static int gcd(int i, int i2) {
        MathPreconditions.b("a", i);
        MathPreconditions.b("b", i2);
        if (i == 0) {
            return i2;
        }
        if (i2 == 0) {
            return i;
        }
        int numberOfTrailingZeros = Integer.numberOfTrailingZeros(i);
        int i3 = i >> numberOfTrailingZeros;
        int numberOfTrailingZeros2 = Integer.numberOfTrailingZeros(i2);
        int i4 = i2 >> numberOfTrailingZeros2;
        while (i3 != i4) {
            int i5 = i3 - i4;
            int i6 = (i5 >> 31) & i5;
            int i7 = (i5 - i6) - i6;
            i4 += i6;
            i3 = i7 >> Integer.numberOfTrailingZeros(i7);
        }
        return i3 << Math.min(numberOfTrailingZeros, numberOfTrailingZeros2);
    }

    public static int checkedAdd(int i, int i2) {
        long j = ((long) i) + ((long) i2);
        int i3 = (int) j;
        MathPreconditions.c(j == ((long) i3));
        return i3;
    }

    public static int checkedSubtract(int i, int i2) {
        long j = ((long) i) - ((long) i2);
        int i3 = (int) j;
        MathPreconditions.c(j == ((long) i3));
        return i3;
    }

    public static int checkedMultiply(int i, int i2) {
        long j = ((long) i) * ((long) i2);
        int i3 = (int) j;
        MathPreconditions.c(j == ((long) i3));
        return i3;
    }

    public static int checkedPow(int i, int i2) {
        MathPreconditions.b("exponent", i2);
        int i3 = -1;
        boolean z = false;
        switch (i) {
            case -2:
                if (i2 < 32) {
                    z = true;
                }
                MathPreconditions.c(z);
                return (i2 & 1) == 0 ? 1 << i2 : -1 << i2;
            case -1:
                if ((i2 & 1) == 0) {
                    i3 = 1;
                }
                return i3;
            case 0:
                if (i2 == 0) {
                    z = true;
                }
                return z ? 1 : 0;
            case 1:
                return 1;
            case 2:
                if (i2 < 31) {
                    z = true;
                }
                MathPreconditions.c(z);
                return 1 << i2;
            default:
                int i4 = i;
                int i5 = 1;
                while (true) {
                    switch (i2) {
                        case 0:
                            return i5;
                        case 1:
                            return checkedMultiply(i5, i4);
                        default:
                            if ((i2 & 1) != 0) {
                                i5 = checkedMultiply(i5, i4);
                            }
                            i2 >>= 1;
                            if (i2 > 0) {
                                MathPreconditions.c((-46340 <= i4) & (i4 <= 46340));
                                i4 *= i4;
                            }
                    }
                }
        }
    }

    @Beta
    public static int saturatedAdd(int i, int i2) {
        return Ints.saturatedCast(((long) i) + ((long) i2));
    }

    @Beta
    public static int saturatedSubtract(int i, int i2) {
        return Ints.saturatedCast(((long) i) - ((long) i2));
    }

    @Beta
    public static int saturatedMultiply(int i, int i2) {
        return Ints.saturatedCast(((long) i) * ((long) i2));
    }

    @Beta
    public static int saturatedPow(int i, int i2) {
        MathPreconditions.b("exponent", i2);
        int i3 = -1;
        int i4 = 0;
        switch (i) {
            case -2:
                if (i2 >= 32) {
                    return (i2 & 1) + SubsamplingScaleImageView.TILE_SIZE_AUTO;
                }
                return (i2 & 1) == 0 ? 1 << i2 : -1 << i2;
            case -1:
                if ((i2 & 1) == 0) {
                    i3 = 1;
                }
                return i3;
            case 0:
                if (i2 == 0) {
                    i4 = 1;
                }
                return i4;
            case 1:
                return 1;
            case 2:
                return i2 >= 31 ? SubsamplingScaleImageView.TILE_SIZE_AUTO : 1 << i2;
            default:
                int i5 = ((i >>> 31) & i2 & 1) + SubsamplingScaleImageView.TILE_SIZE_AUTO;
                int i6 = i;
                int i7 = 1;
                while (true) {
                    switch (i2) {
                        case 0:
                            return i7;
                        case 1:
                            return saturatedMultiply(i7, i6);
                        default:
                            if ((i2 & 1) != 0) {
                                i7 = saturatedMultiply(i7, i6);
                            }
                            i2 >>= 1;
                            if (i2 > 0) {
                                if ((-46340 > i6) || (i6 > 46340)) {
                                    return i5;
                                }
                                i6 *= i6;
                            }
                    }
                }
        }
    }

    public static int factorial(int i) {
        MathPreconditions.b("n", i);
        return i < e.length ? e[i] : SubsamplingScaleImageView.TILE_SIZE_AUTO;
    }

    @GwtIncompatible
    public static int binomial(int i, int i2) {
        MathPreconditions.b("n", i);
        MathPreconditions.b("k", i2);
        int i3 = 0;
        Preconditions.checkArgument(i2 <= i, "k (%s) > n (%s)", i2, i);
        if (i2 > (i >> 1)) {
            i2 = i - i2;
        }
        if (i2 >= d.length || i > d[i2]) {
            return SubsamplingScaleImageView.TILE_SIZE_AUTO;
        }
        switch (i2) {
            case 0:
                return 1;
            case 1:
                return i;
            default:
                long j = 1;
                while (i3 < i2) {
                    i3++;
                    j = (j * ((long) (i - i3))) / ((long) i3);
                }
                return (int) j;
        }
    }

    @GwtIncompatible
    @Beta
    public static boolean isPrime(int i) {
        return LongMath.isPrime((long) i);
    }

    private IntMath() {
    }
}
