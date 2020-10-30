package com.google.android.gms.internal;

public final class zzarg implements Cloneable {
    private static final zzarh a = new zzarh();
    private boolean b;
    private int[] c;
    private zzarh[] d;
    private int e;

    zzarg() {
        this(10);
    }

    zzarg(int i) {
        this.b = false;
        int c2 = c(i);
        this.c = new int[c2];
        this.d = new zzarh[c2];
        this.e = 0;
    }

    private boolean a(int[] iArr, int[] iArr2, int i) {
        for (int i2 = 0; i2 < i; i2++) {
            if (iArr[i2] != iArr2[i2]) {
                return false;
            }
        }
        return true;
    }

    private boolean a(zzarh[] zzarhArr, zzarh[] zzarhArr2, int i) {
        for (int i2 = 0; i2 < i; i2++) {
            if (!zzarhArr[i2].equals(zzarhArr2[i2])) {
                return false;
            }
        }
        return true;
    }

    private int c(int i) {
        return d(i * 4) / 4;
    }

    private int d(int i) {
        for (int i2 = 4; i2 < 32; i2++) {
            int i3 = (1 << i2) - 12;
            if (i <= i3) {
                return i3;
            }
        }
        return i;
    }

    private int e(int i) {
        int i2 = this.e - 1;
        int i3 = 0;
        while (i3 <= i2) {
            int i4 = (i3 + i2) >>> 1;
            int i5 = this.c[i4];
            if (i5 < i) {
                i3 = i4 + 1;
            } else if (i5 <= i) {
                return i4;
            } else {
                i2 = i4 - 1;
            }
        }
        return i3 ^ -1;
    }

    /* access modifiers changed from: 0000 */
    public int a() {
        return this.e;
    }

    /* access modifiers changed from: 0000 */
    public zzarh a(int i) {
        int e2 = e(i);
        if (e2 < 0 || this.d[e2] == a) {
            return null;
        }
        return this.d[e2];
    }

    /* access modifiers changed from: 0000 */
    public void a(int i, zzarh zzarh) {
        int e2 = e(i);
        if (e2 >= 0) {
            this.d[e2] = zzarh;
            return;
        }
        int i2 = e2 ^ -1;
        if (i2 >= this.e || this.d[i2] != a) {
            if (this.e >= this.c.length) {
                int c2 = c(this.e + 1);
                int[] iArr = new int[c2];
                zzarh[] zzarhArr = new zzarh[c2];
                System.arraycopy(this.c, 0, iArr, 0, this.c.length);
                System.arraycopy(this.d, 0, zzarhArr, 0, this.d.length);
                this.c = iArr;
                this.d = zzarhArr;
            }
            if (this.e - i2 != 0) {
                int i3 = i2 + 1;
                System.arraycopy(this.c, i2, this.c, i3, this.e - i2);
                System.arraycopy(this.d, i2, this.d, i3, this.e - i2);
            }
            this.c[i2] = i;
            this.d[i2] = zzarh;
            this.e++;
            return;
        }
        this.c[i2] = i;
        this.d[i2] = zzarh;
    }

    /* access modifiers changed from: 0000 */
    public zzarh b(int i) {
        return this.d[i];
    }

    /* renamed from: cR */
    public final zzarg clone() {
        int a2 = a();
        zzarg zzarg = new zzarg(a2);
        System.arraycopy(this.c, 0, zzarg.c, 0, a2);
        for (int i = 0; i < a2; i++) {
            if (this.d[i] != null) {
                zzarg.d[i] = (zzarh) this.d[i].clone();
            }
        }
        zzarg.e = a2;
        return zzarg;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzarg)) {
            return false;
        }
        zzarg zzarg = (zzarg) obj;
        if (a() != zzarg.a()) {
            return false;
        }
        return a(this.c, zzarg.c, this.e) && a(this.d, zzarg.d, this.e);
    }

    public int hashCode() {
        int i = 17;
        for (int i2 = 0; i2 < this.e; i2++) {
            i = (((i * 31) + this.c[i2]) * 31) + this.d[i2].hashCode();
        }
        return i;
    }

    public boolean isEmpty() {
        return a() == 0;
    }
}
