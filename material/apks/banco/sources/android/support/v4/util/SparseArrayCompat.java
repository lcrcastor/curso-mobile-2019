package android.support.v4.util;

public class SparseArrayCompat<E> implements Cloneable {
    private static final Object a = new Object();
    private boolean b;
    private int[] c;
    private Object[] d;
    private int e;

    public SparseArrayCompat() {
        this(10);
    }

    public SparseArrayCompat(int i) {
        this.b = false;
        if (i == 0) {
            this.c = ContainerHelpers.a;
            this.d = ContainerHelpers.c;
        } else {
            int a2 = ContainerHelpers.a(i);
            this.c = new int[a2];
            this.d = new Object[a2];
        }
        this.e = 0;
    }

    public SparseArrayCompat<E> clone() {
        try {
            SparseArrayCompat<E> sparseArrayCompat = (SparseArrayCompat) super.clone();
            try {
                sparseArrayCompat.c = (int[]) this.c.clone();
                sparseArrayCompat.d = (Object[]) this.d.clone();
                return sparseArrayCompat;
            } catch (CloneNotSupportedException unused) {
                return sparseArrayCompat;
            }
        } catch (CloneNotSupportedException unused2) {
            return null;
        }
    }

    public E get(int i) {
        return get(i, null);
    }

    public E get(int i, E e2) {
        int a2 = ContainerHelpers.a(this.c, this.e, i);
        return (a2 < 0 || this.d[a2] == a) ? e2 : this.d[a2];
    }

    public void delete(int i) {
        int a2 = ContainerHelpers.a(this.c, this.e, i);
        if (a2 >= 0 && this.d[a2] != a) {
            this.d[a2] = a;
            this.b = true;
        }
    }

    public void remove(int i) {
        delete(i);
    }

    public void removeAt(int i) {
        if (this.d[i] != a) {
            this.d[i] = a;
            this.b = true;
        }
    }

    public void removeAtRange(int i, int i2) {
        int min = Math.min(this.e, i2 + i);
        while (i < min) {
            removeAt(i);
            i++;
        }
    }

    private void a() {
        int i = this.e;
        int[] iArr = this.c;
        Object[] objArr = this.d;
        int i2 = 0;
        for (int i3 = 0; i3 < i; i3++) {
            Object obj = objArr[i3];
            if (obj != a) {
                if (i3 != i2) {
                    iArr[i2] = iArr[i3];
                    objArr[i2] = obj;
                    objArr[i3] = null;
                }
                i2++;
            }
        }
        this.b = false;
        this.e = i2;
    }

    public void put(int i, E e2) {
        int a2 = ContainerHelpers.a(this.c, this.e, i);
        if (a2 >= 0) {
            this.d[a2] = e2;
        } else {
            int i2 = a2 ^ -1;
            if (i2 >= this.e || this.d[i2] != a) {
                if (this.b && this.e >= this.c.length) {
                    a();
                    i2 = ContainerHelpers.a(this.c, this.e, i) ^ -1;
                }
                if (this.e >= this.c.length) {
                    int a3 = ContainerHelpers.a(this.e + 1);
                    int[] iArr = new int[a3];
                    Object[] objArr = new Object[a3];
                    System.arraycopy(this.c, 0, iArr, 0, this.c.length);
                    System.arraycopy(this.d, 0, objArr, 0, this.d.length);
                    this.c = iArr;
                    this.d = objArr;
                }
                if (this.e - i2 != 0) {
                    int i3 = i2 + 1;
                    System.arraycopy(this.c, i2, this.c, i3, this.e - i2);
                    System.arraycopy(this.d, i2, this.d, i3, this.e - i2);
                }
                this.c[i2] = i;
                this.d[i2] = e2;
                this.e++;
            } else {
                this.c[i2] = i;
                this.d[i2] = e2;
            }
        }
    }

    public int size() {
        if (this.b) {
            a();
        }
        return this.e;
    }

    public int keyAt(int i) {
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

    public int indexOfKey(int i) {
        if (this.b) {
            a();
        }
        return ContainerHelpers.a(this.c, this.e, i);
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

    public void append(int i, E e2) {
        if (this.e == 0 || i > this.c[this.e - 1]) {
            if (this.b && this.e >= this.c.length) {
                a();
            }
            int i2 = this.e;
            if (i2 >= this.c.length) {
                int a2 = ContainerHelpers.a(i2 + 1);
                int[] iArr = new int[a2];
                Object[] objArr = new Object[a2];
                System.arraycopy(this.c, 0, iArr, 0, this.c.length);
                System.arraycopy(this.d, 0, objArr, 0, this.d.length);
                this.c = iArr;
                this.d = objArr;
            }
            this.c[i2] = i;
            this.d[i2] = e2;
            this.e = i2 + 1;
            return;
        }
        put(i, e2);
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
