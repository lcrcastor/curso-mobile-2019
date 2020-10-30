package com.google.android.gms.internal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class zzarh implements Cloneable {
    private zzarf<?, ?> a;
    private Object b;
    private List<zzarm> c = new ArrayList();

    zzarh() {
    }

    private byte[] c() {
        byte[] bArr = new byte[a()];
        a(zzard.zzbe(bArr));
        return bArr;
    }

    /* access modifiers changed from: 0000 */
    public int a() {
        if (this.b != null) {
            return this.a.a(this.b);
        }
        int i = 0;
        for (zzarm a2 : this.c) {
            i += a2.a();
        }
        return i;
    }

    /* access modifiers changed from: 0000 */
    public <T> T a(zzarf<?, T> zzarf) {
        if (this.b == null) {
            this.a = zzarf;
            this.b = zzarf.a(this.c);
            this.c = null;
        } else if (!this.a.equals(zzarf)) {
            throw new IllegalStateException("Tried to getExtension with a different Extension.");
        }
        return this.b;
    }

    /* access modifiers changed from: 0000 */
    public void a(zzard zzard) {
        if (this.b != null) {
            this.a.a(this.b, zzard);
            return;
        }
        for (zzarm a2 : this.c) {
            a2.a(zzard);
        }
    }

    /* access modifiers changed from: 0000 */
    public void a(zzarm zzarm) {
        this.c.add(zzarm);
    }

    /* renamed from: b */
    public final zzarh clone() {
        Object clone;
        zzarh zzarh = new zzarh();
        try {
            zzarh.a = this.a;
            if (this.c == null) {
                zzarh.c = null;
            } else {
                zzarh.c.addAll(this.c);
            }
            if (this.b == null) {
                return zzarh;
            }
            if (this.b instanceof zzark) {
                clone = (zzark) ((zzark) this.b).clone();
            } else if (this.b instanceof byte[]) {
                clone = ((byte[]) this.b).clone();
            } else {
                int i = 0;
                if (this.b instanceof byte[][]) {
                    byte[][] bArr = (byte[][]) this.b;
                    byte[][] bArr2 = new byte[bArr.length][];
                    zzarh.b = bArr2;
                    while (i < bArr.length) {
                        bArr2[i] = (byte[]) bArr[i].clone();
                        i++;
                    }
                } else if (this.b instanceof boolean[]) {
                    clone = ((boolean[]) this.b).clone();
                } else if (this.b instanceof int[]) {
                    clone = ((int[]) this.b).clone();
                } else if (this.b instanceof long[]) {
                    clone = ((long[]) this.b).clone();
                } else if (this.b instanceof float[]) {
                    clone = ((float[]) this.b).clone();
                } else if (this.b instanceof double[]) {
                    clone = ((double[]) this.b).clone();
                } else if (this.b instanceof zzark[]) {
                    zzark[] zzarkArr = (zzark[]) this.b;
                    zzark[] zzarkArr2 = new zzark[zzarkArr.length];
                    zzarh.b = zzarkArr2;
                    while (i < zzarkArr.length) {
                        zzarkArr2[i] = (zzark) zzarkArr[i].clone();
                        i++;
                    }
                }
                return zzarh;
            }
            zzarh.b = clone;
            return zzarh;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzarh)) {
            return false;
        }
        zzarh zzarh = (zzarh) obj;
        if (this.b == null || zzarh.b == null) {
            if (this.c != null && zzarh.c != null) {
                return this.c.equals(zzarh.c);
            }
            try {
                return Arrays.equals(c(), zzarh.c());
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
        } else if (this.a != zzarh.a) {
            return false;
        } else {
            return !this.a.bhd.isArray() ? this.b.equals(zzarh.b) : this.b instanceof byte[] ? Arrays.equals((byte[]) this.b, (byte[]) zzarh.b) : this.b instanceof int[] ? Arrays.equals((int[]) this.b, (int[]) zzarh.b) : this.b instanceof long[] ? Arrays.equals((long[]) this.b, (long[]) zzarh.b) : this.b instanceof float[] ? Arrays.equals((float[]) this.b, (float[]) zzarh.b) : this.b instanceof double[] ? Arrays.equals((double[]) this.b, (double[]) zzarh.b) : this.b instanceof boolean[] ? Arrays.equals((boolean[]) this.b, (boolean[]) zzarh.b) : Arrays.deepEquals((Object[]) this.b, (Object[]) zzarh.b);
        }
    }

    public int hashCode() {
        try {
            return Arrays.hashCode(c()) + 527;
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}
