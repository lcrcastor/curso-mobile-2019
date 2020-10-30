package com.google.common.primitives;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import java.io.Serializable;
import java.util.AbstractList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.RandomAccess;
import javax.annotation.Nullable;

@GwtCompatible
public final class Bytes {

    @GwtCompatible
    static class ByteArrayAsList extends AbstractList<Byte> implements Serializable, RandomAccess {
        private static final long serialVersionUID = 0;
        final byte[] a;
        final int b;
        final int c;

        public boolean isEmpty() {
            return false;
        }

        ByteArrayAsList(byte[] bArr) {
            this(bArr, 0, bArr.length);
        }

        ByteArrayAsList(byte[] bArr, int i, int i2) {
            this.a = bArr;
            this.b = i;
            this.c = i2;
        }

        public int size() {
            return this.c - this.b;
        }

        /* renamed from: a */
        public Byte get(int i) {
            Preconditions.checkElementIndex(i, size());
            return Byte.valueOf(this.a[this.b + i]);
        }

        public boolean contains(Object obj) {
            return (obj instanceof Byte) && Bytes.c(this.a, ((Byte) obj).byteValue(), this.b, this.c) != -1;
        }

        public int indexOf(Object obj) {
            if (obj instanceof Byte) {
                int a2 = Bytes.c(this.a, ((Byte) obj).byteValue(), this.b, this.c);
                if (a2 >= 0) {
                    return a2 - this.b;
                }
            }
            return -1;
        }

        public int lastIndexOf(Object obj) {
            if (obj instanceof Byte) {
                int b2 = Bytes.d(this.a, ((Byte) obj).byteValue(), this.b, this.c);
                if (b2 >= 0) {
                    return b2 - this.b;
                }
            }
            return -1;
        }

        /* renamed from: a */
        public Byte set(int i, Byte b2) {
            Preconditions.checkElementIndex(i, size());
            byte b3 = this.a[this.b + i];
            this.a[this.b + i] = ((Byte) Preconditions.checkNotNull(b2)).byteValue();
            return Byte.valueOf(b3);
        }

        public List<Byte> subList(int i, int i2) {
            Preconditions.checkPositionIndexes(i, i2, size());
            if (i == i2) {
                return Collections.emptyList();
            }
            return new ByteArrayAsList(this.a, this.b + i, this.b + i2);
        }

        public boolean equals(@Nullable Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof ByteArrayAsList)) {
                return super.equals(obj);
            }
            ByteArrayAsList byteArrayAsList = (ByteArrayAsList) obj;
            int size = size();
            if (byteArrayAsList.size() != size) {
                return false;
            }
            for (int i = 0; i < size; i++) {
                if (this.a[this.b + i] != byteArrayAsList.a[byteArrayAsList.b + i]) {
                    return false;
                }
            }
            return true;
        }

        public int hashCode() {
            int i = 1;
            for (int i2 = this.b; i2 < this.c; i2++) {
                i = (i * 31) + Bytes.hashCode(this.a[i2]);
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
        public byte[] a() {
            int size = size();
            byte[] bArr = new byte[size];
            System.arraycopy(this.a, this.b, bArr, 0, size);
            return bArr;
        }
    }

    public static int hashCode(byte b) {
        return b;
    }

    private Bytes() {
    }

    public static boolean contains(byte[] bArr, byte b) {
        for (byte b2 : bArr) {
            if (b2 == b) {
                return true;
            }
        }
        return false;
    }

    public static int indexOf(byte[] bArr, byte b) {
        return c(bArr, b, 0, bArr.length);
    }

    /* access modifiers changed from: private */
    public static int c(byte[] bArr, byte b, int i, int i2) {
        while (i < i2) {
            if (bArr[i] == b) {
                return i;
            }
            i++;
        }
        return -1;
    }

    public static int indexOf(byte[] bArr, byte[] bArr2) {
        Preconditions.checkNotNull(bArr, "array");
        Preconditions.checkNotNull(bArr2, "target");
        if (bArr2.length == 0) {
            return 0;
        }
        int i = 0;
        while (i < (bArr.length - bArr2.length) + 1) {
            int i2 = 0;
            while (i2 < bArr2.length) {
                if (bArr[i + i2] != bArr2[i2]) {
                    i++;
                } else {
                    i2++;
                }
            }
            return i;
        }
        return -1;
    }

    public static int lastIndexOf(byte[] bArr, byte b) {
        return d(bArr, b, 0, bArr.length);
    }

    /* access modifiers changed from: private */
    public static int d(byte[] bArr, byte b, int i, int i2) {
        for (int i3 = i2 - 1; i3 >= i; i3--) {
            if (bArr[i3] == b) {
                return i3;
            }
        }
        return -1;
    }

    public static byte[] concat(byte[]... bArr) {
        int i = 0;
        for (byte[] length : bArr) {
            i += length.length;
        }
        byte[] bArr2 = new byte[i];
        int i2 = 0;
        for (byte[] bArr3 : bArr) {
            System.arraycopy(bArr3, 0, bArr2, i2, bArr3.length);
            i2 += bArr3.length;
        }
        return bArr2;
    }

    public static byte[] ensureCapacity(byte[] bArr, int i, int i2) {
        boolean z = false;
        Preconditions.checkArgument(i >= 0, "Invalid minLength: %s", i);
        if (i2 >= 0) {
            z = true;
        }
        Preconditions.checkArgument(z, "Invalid padding: %s", i2);
        return bArr.length < i ? Arrays.copyOf(bArr, i + i2) : bArr;
    }

    public static byte[] toArray(Collection<? extends Number> collection) {
        if (collection instanceof ByteArrayAsList) {
            return ((ByteArrayAsList) collection).a();
        }
        Object[] array = collection.toArray();
        int length = array.length;
        byte[] bArr = new byte[length];
        for (int i = 0; i < length; i++) {
            bArr[i] = ((Number) Preconditions.checkNotNull(array[i])).byteValue();
        }
        return bArr;
    }

    public static List<Byte> asList(byte... bArr) {
        if (bArr.length == 0) {
            return Collections.emptyList();
        }
        return new ByteArrayAsList(bArr);
    }
}
