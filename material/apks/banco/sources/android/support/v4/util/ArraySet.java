package android.support.v4.util;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public final class ArraySet<E> implements Collection<E>, Set<E> {
    private static final int[] a = new int[0];
    private static final Object[] b = new Object[0];
    private static Object[] c;
    private static int d;
    private static Object[] e;
    private static int f;
    private int[] g;
    /* access modifiers changed from: private */
    public Object[] h;
    /* access modifiers changed from: private */
    public int i;
    private MapCollections<E, E> j;

    private int a(Object obj, int i2) {
        int i3 = this.i;
        if (i3 == 0) {
            return -1;
        }
        int a2 = ContainerHelpers.a(this.g, i3, i2);
        if (a2 < 0 || obj.equals(this.h[a2])) {
            return a2;
        }
        int i4 = a2 + 1;
        while (i4 < i3 && this.g[i4] == i2) {
            if (obj.equals(this.h[i4])) {
                return i4;
            }
            i4++;
        }
        int i5 = a2 - 1;
        while (i5 >= 0 && this.g[i5] == i2) {
            if (obj.equals(this.h[i5])) {
                return i5;
            }
            i5--;
        }
        return i4 ^ -1;
    }

    private int a() {
        int i2 = this.i;
        if (i2 == 0) {
            return -1;
        }
        int a2 = ContainerHelpers.a(this.g, i2, 0);
        if (a2 < 0 || this.h[a2] == null) {
            return a2;
        }
        int i3 = a2 + 1;
        while (i3 < i2 && this.g[i3] == 0) {
            if (this.h[i3] == null) {
                return i3;
            }
            i3++;
        }
        int i4 = a2 - 1;
        while (i4 >= 0 && this.g[i4] == 0) {
            if (this.h[i4] == null) {
                return i4;
            }
            i4--;
        }
        return i3 ^ -1;
    }

    private void a(int i2) {
        if (i2 == 8) {
            synchronized (ArraySet.class) {
                if (e != null) {
                    Object[] objArr = e;
                    this.h = objArr;
                    e = (Object[]) objArr[0];
                    this.g = (int[]) objArr[1];
                    objArr[1] = null;
                    objArr[0] = null;
                    f--;
                    return;
                }
            }
        } else if (i2 == 4) {
            synchronized (ArraySet.class) {
                if (c != null) {
                    Object[] objArr2 = c;
                    this.h = objArr2;
                    c = (Object[]) objArr2[0];
                    this.g = (int[]) objArr2[1];
                    objArr2[1] = null;
                    objArr2[0] = null;
                    d--;
                    return;
                }
            }
        }
        this.g = new int[i2];
        this.h = new Object[i2];
    }

    private static void a(int[] iArr, Object[] objArr, int i2) {
        if (iArr.length == 8) {
            synchronized (ArraySet.class) {
                if (f < 10) {
                    objArr[0] = e;
                    objArr[1] = iArr;
                    for (int i3 = i2 - 1; i3 >= 2; i3--) {
                        objArr[i3] = null;
                    }
                    e = objArr;
                    f++;
                }
            }
        } else if (iArr.length == 4) {
            synchronized (ArraySet.class) {
                if (d < 10) {
                    objArr[0] = c;
                    objArr[1] = iArr;
                    for (int i4 = i2 - 1; i4 >= 2; i4--) {
                        objArr[i4] = null;
                    }
                    c = objArr;
                    d++;
                }
            }
        }
    }

    public ArraySet() {
        this(0);
    }

    public ArraySet(int i2) {
        if (i2 == 0) {
            this.g = a;
            this.h = b;
        } else {
            a(i2);
        }
        this.i = 0;
    }

    public ArraySet(@Nullable ArraySet<E> arraySet) {
        this();
        if (arraySet != null) {
            addAll(arraySet);
        }
    }

    public ArraySet(@Nullable Collection<E> collection) {
        this();
        if (collection != null) {
            addAll(collection);
        }
    }

    public void clear() {
        if (this.i != 0) {
            a(this.g, this.h, this.i);
            this.g = a;
            this.h = b;
            this.i = 0;
        }
    }

    public void ensureCapacity(int i2) {
        if (this.g.length < i2) {
            int[] iArr = this.g;
            Object[] objArr = this.h;
            a(i2);
            if (this.i > 0) {
                System.arraycopy(iArr, 0, this.g, 0, this.i);
                System.arraycopy(objArr, 0, this.h, 0, this.i);
            }
            a(iArr, objArr, this.i);
        }
    }

    public boolean contains(Object obj) {
        return indexOf(obj) >= 0;
    }

    public int indexOf(Object obj) {
        return obj == null ? a() : a(obj, obj.hashCode());
    }

    @Nullable
    public E valueAt(int i2) {
        return this.h[i2];
    }

    public boolean isEmpty() {
        return this.i <= 0;
    }

    public boolean add(@Nullable E e2) {
        int i2;
        int i3;
        if (e2 == null) {
            i3 = a();
            i2 = 0;
        } else {
            int hashCode = e2.hashCode();
            i2 = hashCode;
            i3 = a(e2, hashCode);
        }
        if (i3 >= 0) {
            return false;
        }
        int i4 = i3 ^ -1;
        if (this.i >= this.g.length) {
            int i5 = 4;
            if (this.i >= 8) {
                i5 = (this.i >> 1) + this.i;
            } else if (this.i >= 4) {
                i5 = 8;
            }
            int[] iArr = this.g;
            Object[] objArr = this.h;
            a(i5);
            if (this.g.length > 0) {
                System.arraycopy(iArr, 0, this.g, 0, iArr.length);
                System.arraycopy(objArr, 0, this.h, 0, objArr.length);
            }
            a(iArr, objArr, this.i);
        }
        if (i4 < this.i) {
            int i6 = i4 + 1;
            System.arraycopy(this.g, i4, this.g, i6, this.i - i4);
            System.arraycopy(this.h, i4, this.h, i6, this.i - i4);
        }
        this.g[i4] = i2;
        this.h[i4] = e2;
        this.i++;
        return true;
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    public void append(E e2) {
        int i2;
        int i3 = this.i;
        if (e2 == null) {
            i2 = 0;
        } else {
            i2 = e2.hashCode();
        }
        if (i3 >= this.g.length) {
            throw new IllegalStateException("Array is full");
        } else if (i3 <= 0 || this.g[i3 - 1] <= i2) {
            this.i = i3 + 1;
            this.g[i3] = i2;
            this.h[i3] = e2;
        } else {
            add(e2);
        }
    }

    public void addAll(@NonNull ArraySet<? extends E> arraySet) {
        int i2 = arraySet.i;
        ensureCapacity(this.i + i2);
        if (this.i != 0) {
            for (int i3 = 0; i3 < i2; i3++) {
                add(arraySet.valueAt(i3));
            }
        } else if (i2 > 0) {
            System.arraycopy(arraySet.g, 0, this.g, 0, i2);
            System.arraycopy(arraySet.h, 0, this.h, 0, i2);
            this.i = i2;
        }
    }

    public boolean remove(Object obj) {
        int indexOf = indexOf(obj);
        if (indexOf < 0) {
            return false;
        }
        removeAt(indexOf);
        return true;
    }

    public E removeAt(int i2) {
        E e2 = this.h[i2];
        if (this.i <= 1) {
            a(this.g, this.h, this.i);
            this.g = a;
            this.h = b;
            this.i = 0;
        } else {
            int i3 = 8;
            if (this.g.length <= 8 || this.i >= this.g.length / 3) {
                this.i--;
                if (i2 < this.i) {
                    int i4 = i2 + 1;
                    System.arraycopy(this.g, i4, this.g, i2, this.i - i2);
                    System.arraycopy(this.h, i4, this.h, i2, this.i - i2);
                }
                this.h[this.i] = null;
            } else {
                if (this.i > 8) {
                    i3 = (this.i >> 1) + this.i;
                }
                int[] iArr = this.g;
                Object[] objArr = this.h;
                a(i3);
                this.i--;
                if (i2 > 0) {
                    System.arraycopy(iArr, 0, this.g, 0, i2);
                    System.arraycopy(objArr, 0, this.h, 0, i2);
                }
                if (i2 < this.i) {
                    int i5 = i2 + 1;
                    System.arraycopy(iArr, i5, this.g, i2, this.i - i2);
                    System.arraycopy(objArr, i5, this.h, i2, this.i - i2);
                }
            }
        }
        return e2;
    }

    public boolean removeAll(ArraySet<? extends E> arraySet) {
        int i2 = arraySet.i;
        int i3 = this.i;
        for (int i4 = 0; i4 < i2; i4++) {
            remove(arraySet.valueAt(i4));
        }
        if (i3 != this.i) {
            return true;
        }
        return false;
    }

    public int size() {
        return this.i;
    }

    @NonNull
    public Object[] toArray() {
        Object[] objArr = new Object[this.i];
        System.arraycopy(this.h, 0, objArr, 0, this.i);
        return objArr;
    }

    @NonNull
    public <T> T[] toArray(@NonNull T[] tArr) {
        if (tArr.length < this.i) {
            tArr = (Object[]) Array.newInstance(tArr.getClass().getComponentType(), this.i);
        }
        System.arraycopy(this.h, 0, tArr, 0, this.i);
        if (tArr.length > this.i) {
            tArr[this.i] = null;
        }
        return tArr;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Set)) {
            return false;
        }
        Set set = (Set) obj;
        if (size() != set.size()) {
            return false;
        }
        int i2 = 0;
        while (i2 < this.i) {
            try {
                if (!set.contains(valueAt(i2))) {
                    return false;
                }
                i2++;
            } catch (NullPointerException unused) {
                return false;
            } catch (ClassCastException unused2) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        int[] iArr = this.g;
        int i2 = 0;
        for (int i3 = 0; i3 < this.i; i3++) {
            i2 += iArr[i3];
        }
        return i2;
    }

    public String toString() {
        if (isEmpty()) {
            return "{}";
        }
        StringBuilder sb = new StringBuilder(this.i * 14);
        sb.append('{');
        for (int i2 = 0; i2 < this.i; i2++) {
            if (i2 > 0) {
                sb.append(", ");
            }
            Object valueAt = valueAt(i2);
            if (valueAt != this) {
                sb.append(valueAt);
            } else {
                sb.append("(this Set)");
            }
        }
        sb.append('}');
        return sb.toString();
    }

    private MapCollections<E, E> b() {
        if (this.j == null) {
            this.j = new MapCollections<E, E>() {
                /* access modifiers changed from: protected */
                public int a() {
                    return ArraySet.this.i;
                }

                /* access modifiers changed from: protected */
                public Object a(int i, int i2) {
                    return ArraySet.this.h[i];
                }

                /* access modifiers changed from: protected */
                public int a(Object obj) {
                    return ArraySet.this.indexOf(obj);
                }

                /* access modifiers changed from: protected */
                public int b(Object obj) {
                    return ArraySet.this.indexOf(obj);
                }

                /* access modifiers changed from: protected */
                public Map<E, E> b() {
                    throw new UnsupportedOperationException("not a map");
                }

                /* access modifiers changed from: protected */
                public void a(E e, E e2) {
                    ArraySet.this.add(e);
                }

                /* access modifiers changed from: protected */
                public E a(int i, E e) {
                    throw new UnsupportedOperationException("not a map");
                }

                /* access modifiers changed from: protected */
                public void a(int i) {
                    ArraySet.this.removeAt(i);
                }

                /* access modifiers changed from: protected */
                public void c() {
                    ArraySet.this.clear();
                }
            };
        }
        return this.j;
    }

    public Iterator<E> iterator() {
        return b().e().iterator();
    }

    public boolean containsAll(@NonNull Collection<?> collection) {
        for (Object contains : collection) {
            if (!contains(contains)) {
                return false;
            }
        }
        return true;
    }

    public boolean addAll(@NonNull Collection<? extends E> collection) {
        ensureCapacity(this.i + collection.size());
        boolean z = false;
        for (Object add : collection) {
            z |= add(add);
        }
        return z;
    }

    public boolean removeAll(@NonNull Collection<?> collection) {
        boolean z = false;
        for (Object remove : collection) {
            z |= remove(remove);
        }
        return z;
    }

    public boolean retainAll(@NonNull Collection<?> collection) {
        boolean z = false;
        for (int i2 = this.i - 1; i2 >= 0; i2--) {
            if (!collection.contains(this.h[i2])) {
                removeAt(i2);
                z = true;
            }
        }
        return z;
    }
}
