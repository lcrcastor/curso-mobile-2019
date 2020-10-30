package com.google.common.math;

import ar.com.santander.rio.mbanking.utils.localizacion.LocalizacionManager;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Ascii;
import com.google.common.base.Preconditions;
import com.google.common.primitives.Longs;
import com.google.common.primitives.UnsignedLongs;
import cz.msebera.android.httpclient.HttpStatus;
import java.math.RoundingMode;
import org.bouncycastle.asn1.eac.EACTags;
import org.bouncycastle.crypto.tls.CipherSuite;

@GwtCompatible(emulated = true)
public final class LongMath {
    @VisibleForTesting
    static final byte[] a = {19, Ascii.DC2, Ascii.DC2, Ascii.DC2, Ascii.DC2, 17, 17, 17, Ascii.DLE, Ascii.DLE, Ascii.DLE, Ascii.SI, Ascii.SI, Ascii.SI, Ascii.SI, Ascii.SO, Ascii.SO, Ascii.SO, Ascii.CR, Ascii.CR, Ascii.CR, Ascii.FF, Ascii.FF, Ascii.FF, Ascii.FF, Ascii.VT, Ascii.VT, Ascii.VT, 10, 10, 10, 9, 9, 9, 9, 8, 8, 8, 7, 7, 7, 6, 6, 6, 6, 5, 5, 5, 4, 4, 4, 3, 3, 3, 3, 2, 2, 2, 1, 1, 1, 0, 0, 0};
    @GwtIncompatible
    @VisibleForTesting
    static final long[] b = {1, 10, 100, 1000, LocalizacionManager.UPDATE_INTERVAL_IN_MILLISECONDS, 100000, 1000000, 10000000, 100000000, 1000000000, 10000000000L, 100000000000L, 1000000000000L, 10000000000000L, 100000000000000L, 1000000000000000L, 10000000000000000L, 100000000000000000L, 1000000000000000000L};
    @GwtIncompatible
    @VisibleForTesting
    static final long[] c = {3, 31, 316, 3162, 31622, 316227, 3162277, 31622776, 316227766, 3162277660L, 31622776601L, 316227766016L, 3162277660168L, 31622776601683L, 316227766016837L, 3162277660168379L, 31622776601683793L, 316227766016837933L, 3162277660168379331L};
    static final long[] d = {1, 1, 2, 6, 24, 120, 720, 5040, 40320, 362880, 3628800, 39916800, 479001600, 6227020800L, 87178291200L, 1307674368000L, 20922789888000L, 355687428096000L, 6402373705728000L, 121645100408832000L, 2432902008176640000L};
    static final int[] e = {SubsamplingScaleImageView.TILE_SIZE_AUTO, SubsamplingScaleImageView.TILE_SIZE_AUTO, SubsamplingScaleImageView.TILE_SIZE_AUTO, 3810779, 121977, 16175, 4337, 1733, 887, 534, 361, 265, HttpStatus.SC_PARTIAL_CONTENT, CipherSuite.TLS_PSK_WITH_AES_256_GCM_SHA384, CipherSuite.TLS_DHE_PSK_WITH_3DES_EDE_CBC_SHA, EACTags.SECURE_MESSAGING_TEMPLATE, 111, 101, 94, 88, 83, 79, 76, 74, 72, 70, 69, 68, 67, 67, 66, 66, 66, 66};
    @VisibleForTesting
    static final int[] f = {SubsamplingScaleImageView.TILE_SIZE_AUTO, SubsamplingScaleImageView.TILE_SIZE_AUTO, SubsamplingScaleImageView.TILE_SIZE_AUTO, 2642246, 86251, 11724, 3218, 1313, 684, HttpStatus.SC_INSUFFICIENT_SPACE_ON_RESOURCE, 287, 214, CipherSuite.TLS_PSK_WITH_AES_256_GCM_SHA384, CipherSuite.TLS_PSK_WITH_3DES_EDE_CBC_SHA, 119, 105, 95, 87, 81, 76, 73, 70, 68, 66, 64, 63, 62, 62, 61, 61, 61};
    private static final long[][] g = {new long[]{291830, 126401071349994536L}, new long[]{885594168, 725270293939359937L, 3569819667048198375L}, new long[]{273919523040L, 15, 7363882082L, 992620450144556L}, new long[]{47636622961200L, 2, 2570940, 211991001, 3749873356L}, new long[]{7999252175582850L, 2, 4130806001517L, 149795463772692060L, 186635894390467037L, 3967304179347715805L}, new long[]{585226005592931976L, 2, 123635709730000L, 9233062284813009L, 43835965440333360L, 761179012939631437L, 1263739024124850375L}, new long[]{Long.MAX_VALUE, 2, 325, 9375, 28178, 450775, 9780504, 1795265022}};

    /* renamed from: com.google.common.math.LongMath$1 reason: invalid class name */
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
            throw new UnsupportedOperationException("Method not decompiled: com.google.common.math.LongMath.AnonymousClass1.<clinit>():void");
        }
    }

    enum MillerRabinTester {
        SMALL {
            /* access modifiers changed from: 0000 */
            public long a(long j, long j2, long j3) {
                return (j * j2) % j3;
            }

            /* access modifiers changed from: 0000 */
            public long b(long j, long j2) {
                return (j * j) % j2;
            }
        },
        LARGE {
            private long b(long j, long j2, long j3) {
                return j >= j3 - j2 ? (j + j2) - j3 : j + j2;
            }

            private long c(long j, long j2) {
                int i = 32;
                do {
                    int min = Math.min(i, Long.numberOfLeadingZeros(j));
                    j = UnsignedLongs.remainder(j << min, j2);
                    i -= min;
                } while (i > 0);
                return j;
            }

            /* access modifiers changed from: 0000 */
            public long a(long j, long j2, long j3) {
                long j4 = j >>> 32;
                long j5 = j2 >>> 32;
                long j6 = j & 4294967295L;
                long j7 = j2 & 4294967295L;
                long c = c(j4 * j5, j3) + (j4 * j7);
                if (c < 0) {
                    c = UnsignedLongs.remainder(c, j3);
                }
                return b(c(c + (j5 * j6), j3), UnsignedLongs.remainder(j6 * j7, j3), j3);
            }

            /* access modifiers changed from: 0000 */
            public long b(long j, long j2) {
                long j3 = j2;
                long j4 = j >>> 32;
                long j5 = j & 4294967295L;
                long c = c(j4 * j4, j3);
                long j6 = j4 * j5 * 2;
                if (j6 < 0) {
                    j6 = UnsignedLongs.remainder(j6, j3);
                }
                return b(c(c + j6, j3), UnsignedLongs.remainder(j5 * j5, j3), j3);
            }
        };

        /* access modifiers changed from: 0000 */
        public abstract long a(long j, long j2, long j3);

        /* access modifiers changed from: 0000 */
        public abstract long b(long j, long j2);

        static boolean a(long j, long j2) {
            return (j2 <= 3037000499L ? SMALL : LARGE).c(j, j2);
        }

        private long b(long j, long j2, long j3) {
            long j4 = 1;
            while (j2 != 0) {
                if ((j2 & 1) != 0) {
                    j4 = a(j4, j, j3);
                }
                j = b(j, j3);
                j2 >>= 1;
            }
            return j4;
        }

        private boolean c(long j, long j2) {
            long j3 = j2;
            long j4 = j3 - 1;
            int numberOfTrailingZeros = Long.numberOfTrailingZeros(j4);
            long j5 = j4 >> numberOfTrailingZeros;
            long j6 = j % j3;
            if (j6 == 0) {
                return true;
            }
            long b = b(j6, j5, j3);
            if (b == 1) {
                return true;
            }
            int i = 0;
            while (b != j4) {
                i++;
                if (i == numberOfTrailingZeros) {
                    return false;
                }
                b = b(b, j3);
            }
            return true;
        }
    }

    @VisibleForTesting
    static int a(long j, long j2) {
        return (int) ((((j - j2) ^ -1) ^ -1) >>> 63);
    }

    static boolean b(long j) {
        return ((long) ((int) j)) == j;
    }

    public static boolean isPowerOfTwo(long j) {
        boolean z = false;
        boolean z2 = j > 0;
        if ((j & (j - 1)) == 0) {
            z = true;
        }
        return z2 & z;
    }

    public static long mean(long j, long j2) {
        return (j & j2) + ((j ^ j2) >> 1);
    }

    @Beta
    public static long saturatedAdd(long j, long j2) {
        long j3 = j + j2;
        boolean z = false;
        boolean z2 = (j ^ j2) < 0;
        if ((j ^ j3) >= 0) {
            z = true;
        }
        return z2 | z ? j3 : ((j3 >>> 63) ^ 1) + Long.MAX_VALUE;
    }

    @Beta
    public static long saturatedSubtract(long j, long j2) {
        long j3 = j - j2;
        boolean z = false;
        boolean z2 = (j ^ j2) >= 0;
        if ((j ^ j3) >= 0) {
            z = true;
        }
        return z2 | z ? j3 : ((j3 >>> 63) ^ 1) + Long.MAX_VALUE;
    }

    @Beta
    public static long ceilingPowerOfTwo(long j) {
        MathPreconditions.a("x", j);
        if (j <= Longs.MAX_POWER_OF_TWO) {
            return 1 << (-Long.numberOfLeadingZeros(j - 1));
        }
        StringBuilder sb = new StringBuilder();
        sb.append("ceilingPowerOfTwo(");
        sb.append(j);
        sb.append(") is not representable as a long");
        throw new ArithmeticException(sb.toString());
    }

    @Beta
    public static long floorPowerOfTwo(long j) {
        MathPreconditions.a("x", j);
        return 1 << (63 - Long.numberOfLeadingZeros(j));
    }

    public static int log2(long j, RoundingMode roundingMode) {
        MathPreconditions.a("x", j);
        switch (AnonymousClass1.a[roundingMode.ordinal()]) {
            case 1:
                MathPreconditions.a(isPowerOfTwo(j));
                break;
            case 2:
            case 3:
                break;
            case 4:
            case 5:
                return 64 - Long.numberOfLeadingZeros(j - 1);
            case 6:
            case 7:
            case 8:
                int numberOfLeadingZeros = Long.numberOfLeadingZeros(j);
                return (63 - numberOfLeadingZeros) + a(-5402926248376769404 >>> numberOfLeadingZeros, j);
            default:
                throw new AssertionError("impossible");
        }
        return 63 - Long.numberOfLeadingZeros(j);
    }

    @GwtIncompatible
    public static int log10(long j, RoundingMode roundingMode) {
        MathPreconditions.a("x", j);
        int a2 = a(j);
        long j2 = b[a2];
        switch (AnonymousClass1.a[roundingMode.ordinal()]) {
            case 1:
                MathPreconditions.a(j == j2);
                break;
            case 2:
            case 3:
                break;
            case 4:
            case 5:
                return a2 + a(j2, j);
            case 6:
            case 7:
            case 8:
                return a2 + a(c[a2], j);
            default:
                throw new AssertionError();
        }
        return a2;
    }

    @GwtIncompatible
    static int a(long j) {
        byte b2 = a[Long.numberOfLeadingZeros(j)];
        return b2 - a(j, b[b2]);
    }

    @GwtIncompatible
    public static long pow(long j, int i) {
        MathPreconditions.b("exponent", i);
        long j2 = 1;
        if (-2 > j || j > 2) {
            long j3 = j;
            long j4 = 1;
            while (true) {
                switch (i) {
                    case 0:
                        return j4;
                    case 1:
                        return j4 * j3;
                    default:
                        j4 *= (i & 1) == 0 ? 1 : j3;
                        j3 *= j3;
                        i >>= 1;
                }
            }
        } else {
            long j5 = 0;
            switch ((int) j) {
                case -2:
                    if (i >= 64) {
                        return 0;
                    }
                    return (i & 1) == 0 ? 1 << i : -(1 << i);
                case -1:
                    if ((i & 1) != 0) {
                        j2 = -1;
                    }
                    return j2;
                case 0:
                    if (i != 0) {
                        j2 = 0;
                    }
                    return j2;
                case 1:
                    return 1;
                case 2:
                    if (i < 64) {
                        j5 = 1 << i;
                    }
                    return j5;
                default:
                    throw new AssertionError();
            }
        }
    }

    @GwtIncompatible
    public static long sqrt(long j, RoundingMode roundingMode) {
        MathPreconditions.b("x", j);
        if (b(j)) {
            return (long) IntMath.sqrt((int) j, roundingMode);
        }
        long sqrt = (long) Math.sqrt((double) j);
        long j2 = sqrt * sqrt;
        boolean z = false;
        switch (AnonymousClass1.a[roundingMode.ordinal()]) {
            case 1:
                if (j2 == j) {
                    z = true;
                }
                MathPreconditions.a(z);
                return sqrt;
            case 2:
            case 3:
                return j < j2 ? sqrt - 1 : sqrt;
            case 4:
            case 5:
                return j > j2 ? sqrt + 1 : sqrt;
            case 6:
            case 7:
            case 8:
                if (j < j2) {
                    z = true;
                }
                long j3 = sqrt - (z ? 1 : 0);
                return j3 + ((long) a((j3 * j3) + j3, j));
            default:
                throw new AssertionError();
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0058, code lost:
        if (r12 > 0) goto L_0x006b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x005b, code lost:
        if (r10 > 0) goto L_0x006b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x005e, code lost:
        if (r10 < 0) goto L_0x006b;
     */
    @com.google.common.annotations.GwtIncompatible
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static long divide(long r10, long r12, java.math.RoundingMode r14) {
        /*
            com.google.common.base.Preconditions.checkNotNull(r14)
            long r0 = r10 / r12
            long r2 = r12 * r0
            long r4 = r10 - r2
            r2 = 0
            int r6 = (r4 > r2 ? 1 : (r4 == r2 ? 0 : -1))
            if (r6 != 0) goto L_0x0010
            return r0
        L_0x0010:
            long r6 = r10 ^ r12
            r10 = 63
            long r10 = r6 >> r10
            int r10 = (int) r10
            r11 = 1
            r10 = r10 | r11
            int[] r6 = com.google.common.math.LongMath.AnonymousClass1.a
            int r7 = r14.ordinal()
            r6 = r6[r7]
            r7 = 0
            switch(r6) {
                case 1: goto L_0x0061;
                case 2: goto L_0x006a;
                case 3: goto L_0x005e;
                case 4: goto L_0x006b;
                case 5: goto L_0x005b;
                case 6: goto L_0x002b;
                case 7: goto L_0x002b;
                case 8: goto L_0x002b;
                default: goto L_0x0025;
            }
        L_0x0025:
            java.lang.AssertionError r10 = new java.lang.AssertionError
            r10.<init>()
            throw r10
        L_0x002b:
            long r4 = java.lang.Math.abs(r4)
            long r12 = java.lang.Math.abs(r12)
            long r8 = r12 - r4
            long r12 = r4 - r8
            int r4 = (r12 > r2 ? 1 : (r12 == r2 ? 0 : -1))
            if (r4 != 0) goto L_0x0056
            java.math.RoundingMode r12 = java.math.RoundingMode.HALF_UP
            if (r14 != r12) goto L_0x0041
            r12 = 1
            goto L_0x0042
        L_0x0041:
            r12 = 0
        L_0x0042:
            java.math.RoundingMode r13 = java.math.RoundingMode.HALF_EVEN
            if (r14 != r13) goto L_0x0048
            r13 = 1
            goto L_0x0049
        L_0x0048:
            r13 = 0
        L_0x0049:
            r4 = 1
            long r8 = r0 & r4
            int r14 = (r8 > r2 ? 1 : (r8 == r2 ? 0 : -1))
            if (r14 == 0) goto L_0x0052
            goto L_0x0053
        L_0x0052:
            r11 = 0
        L_0x0053:
            r11 = r11 & r13
            r11 = r11 | r12
            goto L_0x006b
        L_0x0056:
            int r14 = (r12 > r2 ? 1 : (r12 == r2 ? 0 : -1))
            if (r14 <= 0) goto L_0x006a
            goto L_0x006b
        L_0x005b:
            if (r10 <= 0) goto L_0x006a
            goto L_0x006b
        L_0x005e:
            if (r10 >= 0) goto L_0x006a
            goto L_0x006b
        L_0x0061:
            int r12 = (r4 > r2 ? 1 : (r4 == r2 ? 0 : -1))
            if (r12 != 0) goto L_0x0066
            goto L_0x0067
        L_0x0066:
            r11 = 0
        L_0x0067:
            com.google.common.math.MathPreconditions.a(r11)
        L_0x006a:
            r11 = 0
        L_0x006b:
            if (r11 == 0) goto L_0x0071
            long r10 = (long) r10
            long r12 = r0 + r10
            goto L_0x0072
        L_0x0071:
            r12 = r0
        L_0x0072:
            return r12
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.common.math.LongMath.divide(long, long, java.math.RoundingMode):long");
    }

    @GwtIncompatible
    public static int mod(long j, int i) {
        return (int) mod(j, (long) i);
    }

    @GwtIncompatible
    public static long mod(long j, long j2) {
        if (j2 <= 0) {
            throw new ArithmeticException("Modulus must be positive");
        }
        long j3 = j % j2;
        return j3 >= 0 ? j3 : j3 + j2;
    }

    public static long gcd(long j, long j2) {
        MathPreconditions.b("a", j);
        MathPreconditions.b("b", j2);
        if (j == 0) {
            return j2;
        }
        if (j2 == 0) {
            return j;
        }
        int numberOfTrailingZeros = Long.numberOfTrailingZeros(j);
        long j3 = j >> numberOfTrailingZeros;
        int numberOfTrailingZeros2 = Long.numberOfTrailingZeros(j2);
        long j4 = j2 >> numberOfTrailingZeros2;
        while (j3 != j4) {
            long j5 = j3 - j4;
            long j6 = j5 & (j5 >> 63);
            long j7 = (j5 - j6) - j6;
            long j8 = j4 + j6;
            j3 = j7 >> Long.numberOfTrailingZeros(j7);
            j4 = j8;
        }
        return j3 << Math.min(numberOfTrailingZeros, numberOfTrailingZeros2);
    }

    @GwtIncompatible
    public static long checkedAdd(long j, long j2) {
        long j3 = j + j2;
        boolean z = false;
        boolean z2 = (j ^ j2) < 0;
        if ((j ^ j3) >= 0) {
            z = true;
        }
        MathPreconditions.c(z2 | z);
        return j3;
    }

    @GwtIncompatible
    public static long checkedSubtract(long j, long j2) {
        long j3 = j - j2;
        boolean z = false;
        boolean z2 = (j ^ j2) >= 0;
        if ((j ^ j3) >= 0) {
            z = true;
        }
        MathPreconditions.c(z2 | z);
        return j3;
    }

    @GwtIncompatible
    public static long checkedMultiply(long j, long j2) {
        int numberOfLeadingZeros = Long.numberOfLeadingZeros(j) + Long.numberOfLeadingZeros(j ^ -1) + Long.numberOfLeadingZeros(j2) + Long.numberOfLeadingZeros(j2 ^ -1);
        if (numberOfLeadingZeros > 65) {
            return j * j2;
        }
        boolean z = false;
        MathPreconditions.c(numberOfLeadingZeros >= 64);
        MathPreconditions.c((j >= 0) | (j2 != Long.MIN_VALUE));
        long j3 = j * j2;
        if (j == 0 || j3 / j == j2) {
            z = true;
        }
        MathPreconditions.c(z);
        return j3;
    }

    @GwtIncompatible
    public static long checkedPow(long j, int i) {
        MathPreconditions.b("exponent", i);
        boolean z = false;
        long j2 = 1;
        if (!(j >= -2) || !(j <= 2)) {
            while (true) {
                switch (i) {
                    case 0:
                        return j2;
                    case 1:
                        return checkedMultiply(j2, j);
                    default:
                        if ((i & 1) != 0) {
                            j2 = checkedMultiply(j2, j);
                        }
                        i >>= 1;
                        if (i > 0) {
                            MathPreconditions.c(-3037000499L <= j && j <= 3037000499L);
                            j *= j;
                        }
                        break;
                }
            }
        } else {
            switch ((int) j) {
                case -2:
                    if (i < 64) {
                        z = true;
                    }
                    MathPreconditions.c(z);
                    return (i & 1) == 0 ? 1 << i : -1 << i;
                case -1:
                    if ((i & 1) != 0) {
                        j2 = -1;
                    }
                    return j2;
                case 0:
                    if (i != 0) {
                        j2 = 0;
                    }
                    return j2;
                case 1:
                    return 1;
                case 2:
                    if (i < 63) {
                        z = true;
                    }
                    MathPreconditions.c(z);
                    return 1 << i;
                default:
                    throw new AssertionError();
            }
        }
    }

    @Beta
    public static long saturatedMultiply(long j, long j2) {
        int numberOfLeadingZeros = Long.numberOfLeadingZeros(j) + Long.numberOfLeadingZeros(j ^ -1) + Long.numberOfLeadingZeros(j2) + Long.numberOfLeadingZeros(j2 ^ -1);
        if (numberOfLeadingZeros > 65) {
            return j * j2;
        }
        long j3 = ((j ^ j2) >>> 63) + Long.MAX_VALUE;
        boolean z = false;
        boolean z2 = numberOfLeadingZeros < 64;
        boolean z3 = j < 0;
        if (j2 == Long.MIN_VALUE) {
            z = true;
        }
        if (z2 || (z3 & z)) {
            return j3;
        }
        long j4 = j * j2;
        return (j == 0 || j4 / j == j2) ? j4 : j3;
    }

    @Beta
    public static long saturatedPow(long j, int i) {
        MathPreconditions.b("exponent", i);
        long j2 = 1;
        if ((j >= -2) && (j <= 2)) {
            switch ((int) j) {
                case -2:
                    if (i >= 64) {
                        return ((long) (i & 1)) + Long.MAX_VALUE;
                    }
                    return (i & 1) == 0 ? 1 << i : -1 << i;
                case -1:
                    if ((i & 1) != 0) {
                        j2 = -1;
                    }
                    return j2;
                case 0:
                    if (i != 0) {
                        j2 = 0;
                    }
                    return j2;
                case 1:
                    return 1;
                case 2:
                    if (i >= 63) {
                        return Long.MAX_VALUE;
                    }
                    return 1 << i;
                default:
                    throw new AssertionError();
            }
        } else {
            long j3 = ((j >>> 63) & ((long) (i & 1))) + Long.MAX_VALUE;
            while (true) {
                switch (i) {
                    case 0:
                        return j2;
                    case 1:
                        return saturatedMultiply(j2, j);
                    default:
                        if ((i & 1) != 0) {
                            j2 = saturatedMultiply(j2, j);
                        }
                        i >>= 1;
                        if (i > 0) {
                            if ((-3037000499L > j) || (j > 3037000499L)) {
                                return j3;
                            }
                            j *= j;
                        }
                }
            }
        }
    }

    @GwtIncompatible
    public static long factorial(int i) {
        MathPreconditions.b("n", i);
        if (i < d.length) {
            return d[i];
        }
        return Long.MAX_VALUE;
    }

    public static long binomial(int i, int i2) {
        MathPreconditions.b("n", i);
        MathPreconditions.b("k", i2);
        Preconditions.checkArgument(i2 <= i, "k (%s) > n (%s)", i2, i);
        if (i2 > (i >> 1)) {
            i2 = i - i2;
        }
        switch (i2) {
            case 0:
                return 1;
            case 1:
                return (long) i;
            default:
                if (i < d.length) {
                    return d[i] / (d[i2] * d[i - i2]);
                }
                if (i2 >= e.length || i > e[i2]) {
                    return Long.MAX_VALUE;
                }
                int i3 = 2;
                if (i2 >= f.length || i > f[i2]) {
                    long j = (long) i;
                    int log2 = log2(j, RoundingMode.CEILING);
                    long j2 = 1;
                    long j3 = j;
                    int i4 = i - 1;
                    long j4 = 1;
                    int i5 = log2;
                    while (i3 <= i2) {
                        i5 += log2;
                        if (i5 < 63) {
                            j3 *= (long) i4;
                            j2 *= (long) i3;
                        } else {
                            j4 = a(j4, j3, j2);
                            j3 = (long) i4;
                            j2 = (long) i3;
                            i5 = log2;
                        }
                        i3++;
                        i4--;
                    }
                    return a(j4, j3, j2);
                }
                int i6 = i - 1;
                long j5 = (long) i;
                while (i3 <= i2) {
                    j5 = (j5 * ((long) i6)) / ((long) i3);
                    i6--;
                    i3++;
                }
                return j5;
        }
    }

    static long a(long j, long j2, long j3) {
        if (j == 1) {
            return j2 / j3;
        }
        long gcd = gcd(j, j3);
        return (j / gcd) * (j2 / (j3 / gcd));
    }

    @GwtIncompatible
    @Beta
    public static boolean isPrime(long j) {
        long[][] jArr;
        if (j < 2) {
            MathPreconditions.b("n", j);
            return false;
        } else if (j == 2 || j == 3 || j == 5 || j == 7 || j == 11 || j == 13) {
            return true;
        } else {
            if ((-545925251 & (1 << ((int) (j % 30)))) != 0 || j % 7 == 0 || j % 11 == 0 || j % 13 == 0) {
                return false;
            }
            if (j < 289) {
                return true;
            }
            for (long[] jArr2 : g) {
                if (j <= jArr2[0]) {
                    for (int i = 1; i < jArr2.length; i++) {
                        if (!MillerRabinTester.a(jArr2[i], j)) {
                            return false;
                        }
                    }
                    return true;
                }
            }
            throw new AssertionError();
        }
    }

    private LongMath() {
    }
}
