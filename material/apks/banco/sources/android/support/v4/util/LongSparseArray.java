package android.support.v4.util;

public class LongSparseArray<E> implements Cloneable {
    private static final Object a = new Object();
    private boolean b;
    private long[] c;
    private Object[] d;
    private int e;

    public LongSparseArray() {
        this(10);
    }

    public LongSparseArray(int i) {
        this.b = false;
        if (i == 0) {
            this.c = ContainerHelpers.b;
            this.d = ContainerHelpers.c;
        } else {
            int b2 = ContainerHelpers.b(i);
            this.c = new long[b2];
            this.d = new Object[b2];
        }
        this.e = 0;
    }

    public LongSparseArray<E> clone() {
        try {
            LongSparseArray<E> longSparseArray = (LongSparseArray) super.clone();
            try {
                longSparseArray.c = (long[]) this.c.clone();
                longSparseArray.d = (Object[]) this.d.clone();
                return longSparseArray;
            } catch (CloneNotSupportedException unused) {
                return longSparseArray;
            }
        } catch (CloneNotSupportedException unused2) {
            return null;
        }
    }

    public E get(long j) {
        return get(j, null);
    }

    public E get(long j, E e2) {
        int a2 = ContainerHelpers.a(this.c, this.e, j);
        return (a2 < 0 || this.d[a2] == a) ? e2 : this.d[a2];
    }

    public void delete(long j) {
        int a2 = ContainerHelpers.a(this.c, this.e, j);
        if (a2 >= 0 && this.d[a2] != a) {
            this.d[a2] = a;
            this.b = true;
        }
    }

    public void remove(long j) {
        delete(j);
    }

    public void removeAt(int i) {
        if (this.d[i] != a) {
            this.d[i] = a;
            this.b = true;
        }
    }

    private void a() {
        int i = this.e;
        long[] jArr = this.c;
        Object[] objArr = this.d;
        int i2 = 0;
        for (int i3 = 0; i3 < i; i3++) {
            Object obj = objArr[i3];
            if (obj != a) {
                if (i3 != i2) {
                    jArr[i2] = jArr[i3];
                    objArr[i2] = obj;
                    objArr[i3] = null;
                }
                i2++;
            }
        }
        this.b = false;
        this.e = i2;
    }

    public void put(long j, E e2) {
        int a2 = ContainerHelpers.a(this.c, this.e, j);
        if (a2 >= 0) {
            this.d[a2] = e2;
        } else {
            int i = a2 ^ -1;
            if (i >= this.e || this.d[i] != a) {
                if (this.b && this.e >= this.c.length) {
                    a();
                    i = ContainerHelpers.a(this.c, this.e, j) ^ -1;
                }
                if (this.e >= this.c.length) {
                    int b2 = ContainerHelpers.b(this.e + 1);
                    long[] jArr = new long[b2];
                    Object[] objArr = new Object[b2];
                    System.arraycopy(this.c, 0, jArr, 0, this.c.length);
                    System.arraycopy(this.d, 0, objArr, 0, this.d.length);
                    this.c = jArr;
                    this.d = objArr;
                }
                if (this.e - i != 0) {
                    int i2 = i + 1;
                    System.arraycopy(this.c, i, this.c, i2, this.e - i);
                    System.arraycopy(this.d, i, this.d, i2, this.e - i);
                }
                this.c[i] = j;
                this.d[i] = e2;
                this.e++;
            } else {
                this.c[i] = j;
                this.d[i] = e2;
            }
        }
    }

    public int size() {
        if (this.b) {
            a();
        }
        return this.e;
    }

    public long keyAt(int i) {
        if (this.b) {
            a();
        }
        return this.c[i];
    }

    public E valueAt(int i) {
        if (this.b) {
            a();
        }
        return this.d[i];
    }

    public void setValueAt(int i, E e2) {
        if (this.b) {
            a();
        }
        this.d[i] = e2;
    }

    public int indexOfKey(long j) {
        if (this.b) {
            a();
        }
        return ContainerHelpers.a(this.c, this.e, j);
    }

    public int indexOfValue(E e2) {
        if (this.b) {
            a();
        }
        for (int i = 0; i < this.e; i++) {
            if (this.d[i] == e2) {
                return i;
            }
        }
        return -1;
    }

    public void clear() {
        int i = this.e;
        Object[] objArr = this.d;
        for (int i2 = 0; i2 < i; i2++) {
            objArr[i2] = null;
        }
        this.e = 0;
        this.b = false;
    }

    public void append(long j, E e2) {
        if (this.e == 0 || j > this.c[this.e - 1]) {
            if (this.b && this.e >= this.c.length) {
                a();
            }
            int i = this.e;
            if (i >= this.c.length) {
                int b2 = ContainerHelpers.b(i + 1);
                long[] jArr = new long[b2];
                Object[] objArr = new Object[b2];
                System.arraycopy(this.c, 0, jArr, 0, this.c.length);
                System.arraycopy(this.d, 0, objArr, 0, this.d.length);
                this.c = jArr;
                this.d = objArr;
            }
            this.c[i] = j;
            this.d[i] = e2;
            this.e = i + 1;
            return;
        }
        put(j, e2);
    }

    public String toString() {
        if (size() <= 0) {
            return "{}";
        }
        StringBuilder sb = new StringBuilder(this.e * 28);
        sb.append('{');
        for (int i = 0; i < this.e; i++) {
            if (i > 0) {
                sb.append(", ");
            }
            sb.append(keyAt(i));
            sb.append('=');
            Object valueAt = valueAt(i);
            if (valueAt != this) {
                sb.append(valueAt);
            } else {
                sb.append("(this Map)");
            }
        }
        sb.append('}');
        return sb.toString();
    }
}
