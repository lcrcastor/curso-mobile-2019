package com.google.common.primitives;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Converter;
import com.google.common.base.Preconditions;
import java.io.Serializable;
import java.util.AbstractList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.RandomAccess;
import javax.annotation.CheckForNull;
import javax.annotation.Nullable;

@GwtCompatible(emulated = true)
public final class Floats {
    public static final int BYTES = 4;

    @GwtCompatible
    static class FloatArrayAsList extends AbstractList<Float> implements Serializable, RandomAccess {
        private static final long serialVersionUID = 0;
        final float[] a;
        final int b;
        final int c;

        public boolean isEmpty() {
            return false;
        }

        FloatArrayAsList(float[] fArr) {
            this(fArr, 0, fArr.length);
        }

        FloatArrayAsList(float[] fArr, int i, int i2) {
            this.a = fArr;
            this.b = i;
            this.c = i2;
        }

        public int size() {
            return this.c - this.b;
        }

        /* renamed from: a */
        public Float get(int i) {
            Preconditions.checkElementIndex(i, size());
            return Float.valueOf(this.a[this.b + i]);
        }

        public boolean contains(Object obj) {
            return (obj instanceof Float) && Floats.c(this.a, ((Float) obj).floatValue(), this.b, this.c) != -1;
        }

        public int indexOf(Object obj) {
            if (obj instanceof Float) {
                int a2 = Floats.c(this.a, ((Float) obj).floatValue(), this.b, this.c);
                if (a2 >= 0) {
                    return a2 - this.b;
                }
            }
            return -1;
        }

        public int lastIndexOf(Object obj) {
            if (obj instanceof Float) {
                int b2 = Floats.d(this.a, ((Float) obj).floatValue(), this.b, this.c);
                if (b2 >= 0) {
                    return b2 - this.b;
                }
            }
            return -1;
        }

        /* renamed from: a */
        public Float set(int i, Float f) {
            Preconditions.checkElementIndex(i, size());
            float f2 = this.a[this.b + i];
            this.a[this.b + i] = ((Float) Preconditions.checkNotNull(f)).floatValue();
            return Float.valueOf(f2);
        }

        public List<Float> subList(int i, int i2) {
            Preconditions.checkPositionIndexes(i, i2, size());
            if (i == i2) {
                return Collections.emptyList();
            }
            return new FloatArrayAsList(this.a, this.b + i, this.b + i2);
        }

        public boolean equals(@Nullable Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof FloatArrayAsList)) {
                return super.equals(obj);
            }
            FloatArrayAsList floatArrayAsList = (FloatArrayAsList) obj;
            int size = size();
            if (floatArrayAsList.size() != size) {
                return false;
            }
            for (int i = 0; i < size; i++) {
                if (this.a[this.b + i] != floatArrayAsList.a[floatArrayAsList.b + i]) {
                    return false;
                }
            }
            return true;
        }

        public int hashCode() {
            int i = 1;
            for (int i2 = this.b; i2 < this.c; i2++) {
                i = (i * 31) + Floats.hashCode(this.a[i2]);
            }
            return i;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder(size() * 12);
            sb.append('[');
            sb.append(this.a[this.b]);
            int i = this.b;
            while (true) {
                i++;
                if (i < this.c) {
                    sb.append(", ");
                    sb.append(this.a[i]);
                } else {
                    sb.append(']');
                    return sb.toString();
                }
            }
        }

        /* access modifiers changed from: 0000 */
        public float[] a() {
            int size = size();
            float[] fArr = new float[size];
            System.arraycopy(this.a, this.b, fArr, 0, size);
            return fArr;
        }
    }

    static final class FloatConverter extends Converter<String, Float> implements Serializable {
        static final FloatConverter a = new FloatConverter();
        private static final long serialVersionUID = 1;

        public String toString() {
            return "Floats.stringConverter()";
        }

        private FloatConverter() {
        }

        /* access modifiers changed from: protected */
        /* renamed from: a */
        public Float doForward(String str) {
            return Float.valueOf(str);
        }

        /* access modifiers changed from: protected */
        /* renamed from: a */
        public String doBackward(Float f) {
            return f.toString();
        }

        private Object readResolve() {
            return a;
        }
    }

    enum LexicographicalComparator implements Comparator<float[]> {
        INSTANCE;

        public String toString() {
            return "Floats.lexicographicalComparator()";
        }

        /* renamed from: a */
        public int compare(float[] fArr, float[] fArr2) {
            int min = Math.min(fArr.length, fArr2.length);
            for (int i = 0; i < min; i++) {
                int compare = Float.compare(fArr[i], fArr2[i]);
                if (compare != 0) {
                    return compare;
                }
            }
            return fArr.length - fArr2.length;
        }
    }

    public static boolean isFinite(float f) {
        boolean z = false;
        boolean z2 = Float.NEGATIVE_INFINITY < f;
        if (f < Float.POSITIVE_INFINITY) {
            z = true;
        }
        return z2 & z;
    }

    private Floats() {
    }

    public static int hashCode(float f) {
        return Float.valueOf(f).hashCode();
    }

    public static int compare(float f, float f2) {
        return Float.compare(f, f2);
    }

    public static boolean contains(float[] fArr, float f) {
        int length = fArr.length;
        for (int i = 0; i < length; i++) {
            if (fArr[i] == f) {
                return true;
            }
        }
        return false;
    }

    public static int indexOf(float[] fArr, float f) {
        return c(fArr, f, 0, fArr.length);
    }

    /* access modifiers changed from: private */
    public static int c(float[] fArr, float f, int i, int i2) {
        while (i < i2) {
            if (fArr[i] == f) {
                return i;
            }
            i++;
        }
        return -1;
    }

    public static int indexOf(float[] fArr, float[] fArr2) {
        Preconditions.checkNotNull(fArr, "array");
        Preconditions.checkNotNull(fArr2, "target");
        if (fArr2.length == 0) {
            return 0;
        }
        int i = 0;
        while (i < (fArr.length - fArr2.length) + 1) {
            int i2 = 0;
            while (i2 < fArr2.length) {
                if (fArr[i + i2] != fArr2[i2]) {
                    i++;
                } else {
                    i2++;
                }
            }
            return i;
        }
        return -1;
    }

    public static int lastIndexOf(float[] fArr, float f) {
        return d(fArr, f, 0, fArr.length);
    }

    /* access modifiers changed from: private */
    public static int d(float[] fArr, float f, int i, int i2) {
        for (int i3 = i2 - 1; i3 >= i; i3--) {
            if (fArr[i3] == f) {
                return i3;
            }
        }
        return -1;
    }

    public static float min(float... fArr) {
        Preconditions.checkArgument(fArr.length > 0);
        float f = fArr[0];
        for (int i = 1; i < fArr.length; i++) {
            f = Math.min(f, fArr[i]);
        }
        return f;
    }

    public static float max(float... fArr) {
        Preconditions.checkArgument(fArr.length > 0);
        float f = fArr[0];
        for (int i = 1; i < fArr.length; i++) {
            f = Math.max(f, fArr[i]);
        }
        return f;
    }

    public static float[] concat(float[]... fArr) {
        int i = 0;
        for (float[] length : fArr) {
            i += length.length;
        }
        float[] fArr2 = new float[i];
        int i2 = 0;
        for (float[] fArr3 : fArr) {
            System.arraycopy(fArr3, 0, fArr2, i2, fArr3.length);
            i2 += fArr3.length;
        }
        return fArr2;
    }

    @Beta
    public static Converter<String, Float> stringConverter() {
        return FloatConverter.a;
    }

    public static float[] ensureCapacity(float[] fArr, int i, int i2) {
        boolean z = false;
        Preconditions.checkArgument(i >= 0, "Invalid minLength: %s", i);
        if (i2 >= 0) {
            z = true;
        }
        Preconditions.checkArgument(z, "Invalid padding: %s", i2);
        return fArr.length < i ? Arrays.copyOf(fArr, i + i2) : fArr;
    }

    public static String join(String str, float... fArr) {
        Preconditions.checkNotNull(str);
        if (fArr.length == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder(fArr.length * 12);
        sb.append(fArr[0]);
        for (int i = 1; i < fArr.length; i++) {
            sb.append(str);
            sb.append(fArr[i]);
        }
        return sb.toString();
    }

    public static Comparator<float[]> lexicographicalComparator() {
        return LexicographicalComparator.INSTANCE;
    }

    public static float[] toArray(Collection<? extends Number> collection) {
        if (collection instanceof FloatArrayAsList) {
            return ((FloatArrayAsList) collection).a();
        }
        Object[] array = collection.toArray();
        int length = array.length;
        float[] fArr = new float[length];
        for (int i = 0; i < length; i++) {
            fArr[i] = ((Number) Preconditions.checkNotNull(array[i])).floatValue();
        }
        return fArr;
    }

    public static List<Float> asList(float... fArr) {
        if (fArr.length == 0) {
            return Collections.emptyList();
        }
        return new FloatArrayAsList(fArr);
    }

    @GwtIncompatible
    @CheckForNull
    @Nullable
    @Beta
    public static Float tryParse(String str) {
        if (Doubles.a.matcher(str).matches()) {
            try {
                return Float.valueOf(Float.parseFloat(str));
            } catch (NumberFormatException unused) {
            }
        }
        return null;
    }
}
