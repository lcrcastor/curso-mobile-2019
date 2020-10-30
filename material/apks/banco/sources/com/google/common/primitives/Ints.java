package com.google.common.primitives;

import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Ascii;
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
public final class Ints {
    public static final int BYTES = 4;
    public static final int MAX_POWER_OF_TWO = 1073741824;

    @GwtCompatible
    static class IntArrayAsList extends AbstractList<Integer> implements Serializable, RandomAccess {
        private static final long serialVersionUID = 0;
        final int[] a;
        final int b;
        final int c;

        public boolean isEmpty() {
            return false;
        }

        IntArrayAsList(int[] iArr) {
            this(iArr, 0, iArr.length);
        }

        IntArrayAsList(int[] iArr, int i, int i2) {
            this.a = iArr;
            this.b = i;
            this.c = i2;
        }

        public int size() {
            return this.c - this.b;
        }

        /* renamed from: a */
        public Integer get(int i) {
            Preconditions.checkElementIndex(i, size());
            return Integer.valueOf(this.a[this.b + i]);
        }

        public boolean contains(Object obj) {
            return (obj instanceof Integer) && Ints.c(this.a, ((Integer) obj).intValue(), this.b, this.c) != -1;
        }

        public int indexOf(Object obj) {
            if (obj instanceof Integer) {
                int a2 = Ints.c(this.a, ((Integer) obj).intValue(), this.b, this.c);
                if (a2 >= 0) {
                    return a2 - this.b;
                }
            }
            return -1;
        }

        public int lastIndexOf(Object obj) {
            if (obj instanceof Integer) {
                int b2 = Ints.d(this.a, ((Integer) obj).intValue(), this.b, this.c);
                if (b2 >= 0) {
                    return b2 - this.b;
                }
            }
            return -1;
        }

        /* renamed from: a */
        public Integer set(int i, Integer num) {
            Preconditions.checkElementIndex(i, size());
            int i2 = this.a[this.b + i];
            this.a[this.b + i] = ((Integer) Preconditions.checkNotNull(num)).intValue();
            return Integer.valueOf(i2);
        }

        public List<Integer> subList(int i, int i2) {
            Preconditions.checkPositionIndexes(i, i2, size());
            if (i == i2) {
                return Collections.emptyList();
            }
            return new IntArrayAsList(this.a, this.b + i, this.b + i2);
        }

        public boolean equals(@Nullable Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof IntArrayAsList)) {
                return super.equals(obj);
            }
            IntArrayAsList intArrayAsList = (IntArrayAsList) obj;
            int size = size();
            if (intArrayAsList.size() != size) {
                return false;
            }
            for (int i = 0; i < size; i++) {
                if (this.a[this.b + i] != intArrayAsList.a[intArrayAsList.b + i]) {
                    return false;
                }
            }
            return true;
        }

        public int hashCode() {
            int i = 1;
            for (int i2 = this.b; i2 < this.c; i2++) {
                i = (i * 31) + Ints.hashCode(this.a[i2]);
            }
            return i;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder(size() * 5);
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
        public int[] a() {
            int size = size();
            int[] iArr = new int[size];
            System.arraycopy(this.a, this.b, iArr, 0, size);
            return iArr;
        }
    }

    static final class IntConverter extends Converter<String, Integer> implements Serializable {
        static final IntConverter a = new IntConverter();
        private static final long serialVersionUID = 1;

        public String toString() {
            return "Ints.stringConverter()";
        }

        private IntConverter() {
        }

        /* access modifiers changed from: protected */
        /* renamed from: a */
        public Integer doForward(String str) {
            return Integer.decode(str);
        }

        /* access modifiers changed from: protected */
        /* renamed from: a */
        public String doBackward(Integer num) {
            return num.toString();
        }

        private Object readResolve() {
            return a;
        }
    }

    enum LexicographicalComparator implements Comparator<int[]> {
        INSTANCE;

        public String toString() {
            return "Ints.lexicographicalComparator()";
        }

        /* renamed from: a */
        public int compare(int[] iArr, int[] iArr2) {
            int min = Math.min(iArr.length, iArr2.length);
            for (int i = 0; i < min; i++) {
                int compare = Ints.compare(iArr[i], iArr2[i]);
                if (compare != 0) {
                    return compare;
                }
            }
            return iArr.length - iArr2.length;
        }
    }

    public static int compare(int i, int i2) {
        if (i < i2) {
            return -1;
        }
        return i > i2 ? 1 : 0;
    }

    @GwtIncompatible
    public static int fromBytes(byte b, byte b2, byte b3, byte b4) {
        return (b << Ascii.CAN) | ((b2 & UnsignedBytes.MAX_VALUE) << Ascii.DLE) | ((b3 & UnsignedBytes.MAX_VALUE) << 8) | (b4 & UnsignedBytes.MAX_VALUE);
    }

    public static int hashCode(int i) {
        return i;
    }

    public static int saturatedCast(long j) {
        if (j > 2147483647L) {
            return SubsamplingScaleImageView.TILE_SIZE_AUTO;
        }
        if (j < -2147483648L) {
            return Integer.MIN_VALUE;
        }
        return (int) j;
    }

    private Ints() {
    }

    public static int checkedCast(long j) {
        int i = (int) j;
        if (((long) i) == j) {
            return i;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Out of range: ");
        sb.append(j);
        throw new IllegalArgumentException(sb.toString());
    }

    public static boolean contains(int[] iArr, int i) {
        for (int i2 : iArr) {
            if (i2 == i) {
                return true;
            }
        }
        return false;
    }

    public static int indexOf(int[] iArr, int i) {
        return c(iArr, i, 0, iArr.length);
    }

    /* access modifiers changed from: private */
    public static int c(int[] iArr, int i, int i2, int i3) {
        while (i2 < i3) {
            if (iArr[i2] == i) {
                return i2;
            }
            i2++;
        }
        return -1;
    }

    public static int indexOf(int[] iArr, int[] iArr2) {
        Preconditions.checkNotNull(iArr, "array");
        Preconditions.checkNotNull(iArr2, "target");
        if (iArr2.length == 0) {
            return 0;
        }
        int i = 0;
        while (i < (iArr.length - iArr2.length) + 1) {
            int i2 = 0;
            while (i2 < iArr2.length) {
                if (iArr[i + i2] != iArr2[i2]) {
                    i++;
                } else {
                    i2++;
                }
            }
            return i;
        }
        return -1;
    }

    public static int lastIndexOf(int[] iArr, int i) {
        return d(iArr, i, 0, iArr.length);
    }

    /* access modifiers changed from: private */
    public static int d(int[] iArr, int i, int i2, int i3) {
        for (int i4 = i3 - 1; i4 >= i2; i4--) {
            if (iArr[i4] == i) {
                return i4;
            }
        }
        return -1;
    }

    public static int min(int... iArr) {
        Preconditions.checkArgument(iArr.length > 0);
        int i = iArr[0];
        for (int i2 = 1; i2 < iArr.length; i2++) {
            if (iArr[i2] < i) {
                i = iArr[i2];
            }
        }
        return i;
    }

    public static int max(int... iArr) {
        Preconditions.checkArgument(iArr.length > 0);
        int i = iArr[0];
        for (int i2 = 1; i2 < iArr.length; i2++) {
            if (iArr[i2] > i) {
                i = iArr[i2];
            }
        }
        return i;
    }

    public static int[] concat(int[]... iArr) {
        int i = 0;
        for (int[] length : iArr) {
            i += length.length;
        }
        int[] iArr2 = new int[i];
        int i2 = 0;
        for (int[] iArr3 : iArr) {
            System.arraycopy(iArr3, 0, iArr2, i2, iArr3.length);
            i2 += iArr3.length;
        }
        return iArr2;
    }

    @GwtIncompatible
    public static byte[] toByteArray(int i) {
        return new byte[]{(byte) (i >> 24), (byte) (i >> 16), (byte) (i >> 8), (byte) i};
    }

    @GwtIncompatible
    public static int fromByteArray(byte[] bArr) {
        Preconditions.checkArgument(bArr.length >= 4, "array too small: %s < %s", bArr.length, 4);
        return fromBytes(bArr[0], bArr[1], bArr[2], bArr[3]);
    }

    @Beta
    public static Converter<String, Integer> stringConverter() {
        return IntConverter.a;
    }

    public static int[] ensureCapacity(int[] iArr, int i, int i2) {
        boolean z = false;
        Preconditions.checkArgument(i >= 0, "Invalid minLength: %s", i);
        if (i2 >= 0) {
            z = true;
        }
        Preconditions.checkArgument(z, "Invalid padding: %s", i2);
        return iArr.length < i ? Arrays.copyOf(iArr, i + i2) : iArr;
    }

    public static String join(String str, int... iArr) {
        Preconditions.checkNotNull(str);
        if (iArr.length == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder(iArr.length * 5);
        sb.append(iArr[0]);
        for (int i = 1; i < iArr.length; i++) {
            sb.append(str);
            sb.append(iArr[i]);
        }
        return sb.toString();
    }

    public static Comparator<int[]> lexicographicalComparator() {
        return LexicographicalComparator.INSTANCE;
    }

    public static int[] toArray(Collection<? extends Number> collection) {
        if (collection instanceof IntArrayAsList) {
            return ((IntArrayAsList) collection).a();
        }
        Object[] array = collection.toArray();
        int length = array.length;
        int[] iArr = new int[length];
        for (int i = 0; i < length; i++) {
            iArr[i] = ((Number) Preconditions.checkNotNull(array[i])).intValue();
        }
        return iArr;
    }

    public static List<Integer> asList(int... iArr) {
        if (iArr.length == 0) {
            return Collections.emptyList();
        }
        return new IntArrayAsList(iArr);
    }

    @CheckForNull
    @Nullable
    @Beta
    public static Integer tryParse(String str) {
        return tryParse(str, 10);
    }

    @CheckForNull
    @Nullable
    @Beta
    public static Integer tryParse(String str, int i) {
        Long tryParse = Longs.tryParse(str, i);
        if (tryParse == null || tryParse.longValue() != ((long) tryParse.intValue())) {
            return null;
        }
        return Integer.valueOf(tryParse.intValue());
    }
}
