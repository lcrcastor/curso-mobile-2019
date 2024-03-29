package com.google.common.math;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.common.primitives.Doubles;
import com.google.common.primitives.Ints;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@GwtIncompatible
@Beta
public final class Quantiles {

    public static final class Scale {
        private final int a;

        private Scale(int i) {
            Preconditions.checkArgument(i > 0, "Quantile scale must be positive");
            this.a = i;
        }

        public ScaleAndIndex index(int i) {
            return new ScaleAndIndex(this.a, i);
        }

        public ScaleAndIndexes indexes(int... iArr) {
            return new ScaleAndIndexes(this.a, (int[]) iArr.clone());
        }

        public ScaleAndIndexes indexes(Collection<Integer> collection) {
            return new ScaleAndIndexes(this.a, Ints.toArray(collection));
        }
    }

    public static final class ScaleAndIndex {
        private final int a;
        private final int b;

        private ScaleAndIndex(int i, int i2) {
            Quantiles.b(i2, i);
            this.a = i;
            this.b = i2;
        }

        public double compute(Collection<? extends Number> collection) {
            return computeInPlace(Doubles.toArray(collection));
        }

        public double compute(double... dArr) {
            return computeInPlace((double[]) dArr.clone());
        }

        public double compute(long... jArr) {
            return computeInPlace(Quantiles.b(jArr));
        }

        public double compute(int... iArr) {
            return computeInPlace(Quantiles.b(iArr));
        }

        public double computeInPlace(double... dArr) {
            Preconditions.checkArgument(dArr.length > 0, "Cannot calculate quantiles of an empty dataset");
            if (Quantiles.b(dArr)) {
                return Double.NaN;
            }
            long length = ((long) this.b) * ((long) (dArr.length - 1));
            int divide = (int) LongMath.divide(length, (long) this.a, RoundingMode.DOWN);
            int i = (int) (length - (((long) divide) * ((long) this.a)));
            Quantiles.b(divide, dArr, 0, dArr.length - 1);
            if (i == 0) {
                return dArr[divide];
            }
            int i2 = divide + 1;
            Quantiles.b(i2, dArr, i2, dArr.length - 1);
            return Quantiles.b(dArr[divide], dArr[i2], (double) i, (double) this.a);
        }
    }

    public static final class ScaleAndIndexes {
        private final int a;
        private final int[] b;

        private ScaleAndIndexes(int i, int[] iArr) {
            for (int a2 : iArr) {
                Quantiles.b(a2, i);
            }
            this.a = i;
            this.b = iArr;
        }

        public Map<Integer, Double> compute(Collection<? extends Number> collection) {
            return computeInPlace(Doubles.toArray(collection));
        }

        public Map<Integer, Double> compute(double... dArr) {
            return computeInPlace((double[]) dArr.clone());
        }

        public Map<Integer, Double> compute(long... jArr) {
            return computeInPlace(Quantiles.b(jArr));
        }

        public Map<Integer, Double> compute(int... iArr) {
            return computeInPlace(Quantiles.b(iArr));
        }

        public Map<Integer, Double> computeInPlace(double... dArr) {
            double[] dArr2 = dArr;
            int i = 0;
            Preconditions.checkArgument(dArr2.length > 0, "Cannot calculate quantiles of an empty dataset");
            if (Quantiles.b(dArr)) {
                HashMap hashMap = new HashMap();
                int[] iArr = this.b;
                int length = iArr.length;
                while (i < length) {
                    hashMap.put(Integer.valueOf(iArr[i]), Double.valueOf(Double.NaN));
                    i++;
                }
                return Collections.unmodifiableMap(hashMap);
            }
            int[] iArr2 = new int[this.b.length];
            int[] iArr3 = new int[this.b.length];
            int[] iArr4 = new int[(this.b.length * 2)];
            int i2 = 0;
            for (int i3 = 0; i3 < this.b.length; i3++) {
                long length2 = ((long) this.b[i3]) * ((long) (dArr2.length - 1));
                int divide = (int) LongMath.divide(length2, (long) this.a, RoundingMode.DOWN);
                int i4 = (int) (length2 - (((long) divide) * ((long) this.a)));
                iArr2[i3] = divide;
                iArr3[i3] = i4;
                iArr4[i2] = divide;
                i2++;
                if (i4 != 0) {
                    iArr4[i2] = divide + 1;
                    i2++;
                }
            }
            Arrays.sort(iArr4, 0, i2);
            Quantiles.b(iArr4, 0, i2 - 1, dArr2, 0, dArr2.length - 1);
            HashMap hashMap2 = new HashMap();
            while (i < this.b.length) {
                int i5 = iArr2[i];
                int i6 = iArr3[i];
                if (i6 == 0) {
                    hashMap2.put(Integer.valueOf(this.b[i]), Double.valueOf(dArr2[i5]));
                } else {
                    hashMap2.put(Integer.valueOf(this.b[i]), Double.valueOf(Quantiles.b(dArr2[i5], dArr2[i5 + 1], (double) i6, (double) this.a)));
                }
                i++;
            }
            return Collections.unmodifiableMap(hashMap2);
        }
    }

    /* access modifiers changed from: private */
    public static double b(double d, double d2, double d3, double d4) {
        if (d == Double.NEGATIVE_INFINITY) {
            return d2 == Double.POSITIVE_INFINITY ? Double.NaN : Double.NEGATIVE_INFINITY;
        }
        if (d2 == Double.POSITIVE_INFINITY) {
            return Double.POSITIVE_INFINITY;
        }
        return d + (((d2 - d) * d3) / d4);
    }

    public static ScaleAndIndex median() {
        return scale(2).index(1);
    }

    public static Scale quartiles() {
        return scale(4);
    }

    public static Scale percentiles() {
        return scale(100);
    }

    public static Scale scale(int i) {
        return new Scale(i);
    }

    /* access modifiers changed from: private */
    public static boolean b(double... dArr) {
        for (double isNaN : dArr) {
            if (Double.isNaN(isNaN)) {
                return true;
            }
        }
        return false;
    }

    /* access modifiers changed from: private */
    public static void b(int i, int i2) {
        if (i < 0 || i > i2) {
            StringBuilder sb = new StringBuilder();
            sb.append("Quantile indexes must be between 0 and the scale, which is ");
            sb.append(i2);
            throw new IllegalArgumentException(sb.toString());
        }
    }

    /* access modifiers changed from: private */
    public static double[] b(long[] jArr) {
        int length = jArr.length;
        double[] dArr = new double[length];
        for (int i = 0; i < length; i++) {
            dArr[i] = (double) jArr[i];
        }
        return dArr;
    }

    /* access modifiers changed from: private */
    public static double[] b(int[] iArr) {
        int length = iArr.length;
        double[] dArr = new double[length];
        for (int i = 0; i < length; i++) {
            dArr[i] = (double) iArr[i];
        }
        return dArr;
    }

    /* access modifiers changed from: private */
    public static void b(int i, double[] dArr, int i2, int i3) {
        if (i == i2) {
            int i4 = i2;
            for (int i5 = i2 + 1; i5 <= i3; i5++) {
                if (dArr[i4] > dArr[i5]) {
                    i4 = i5;
                }
            }
            if (i4 != i2) {
                c(dArr, i4, i2);
            }
            return;
        }
        while (i3 > i2) {
            int a = a(dArr, i2, i3);
            if (a >= i) {
                i3 = a - 1;
            }
            if (a <= i) {
                i2 = a + 1;
            }
        }
    }

    private static int a(double[] dArr, int i, int i2) {
        b(dArr, i, i2);
        double d = dArr[i];
        int i3 = i2;
        while (i2 > i) {
            if (dArr[i2] > d) {
                c(dArr, i3, i2);
                i3--;
            }
            i2--;
        }
        c(dArr, i, i3);
        return i3;
    }

    private static void b(double[] dArr, int i, int i2) {
        boolean z = true;
        int i3 = (i + i2) >>> 1;
        boolean z2 = dArr[i2] < dArr[i3];
        boolean z3 = dArr[i3] < dArr[i];
        if (dArr[i2] >= dArr[i]) {
            z = false;
        }
        if (z2 == z3) {
            c(dArr, i3, i);
        } else if (z2 != z) {
            c(dArr, i, i2);
        }
    }

    /* access modifiers changed from: private */
    public static void b(int[] iArr, int i, int i2, double[] dArr, int i3, int i4) {
        int a = a(iArr, i, i2, i3, i4);
        int i5 = iArr[a];
        b(i5, dArr, i3, i4);
        int i6 = a - 1;
        while (i6 >= i && iArr[i6] == i5) {
            i6--;
        }
        if (i6 >= i) {
            b(iArr, i, i6, dArr, i3, i5 - 1);
        }
        int i7 = a + 1;
        while (i7 <= i2 && iArr[i7] == i5) {
            i7++;
        }
        if (i7 <= i2) {
            b(iArr, i7, i2, dArr, i5 + 1, i4);
        }
    }

    private static int a(int[] iArr, int i, int i2, int i3, int i4) {
        if (i == i2) {
            return i;
        }
        int i5 = i3 + i4;
        int i6 = i5 >>> 1;
        while (i2 > i + 1) {
            int i7 = (i + i2) >>> 1;
            if (iArr[i7] > i6) {
                i2 = i7;
            } else if (iArr[i7] >= i6) {
                return i7;
            } else {
                i = i7;
            }
        }
        return (i5 - iArr[i]) - iArr[i2] > 0 ? i2 : i;
    }

    private static void c(double[] dArr, int i, int i2) {
        double d = dArr[i];
        dArr[i] = dArr[i2];
        dArr[i2] = d;
    }
}
