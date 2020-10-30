package com.google.android.gms.internal;

import java.util.Arrays;

final class zzarm {
    final int a;
    final byte[] b;

    zzarm(int i, byte[] bArr) {
        this.a = i;
        this.b = bArr;
    }

    /* access modifiers changed from: 0000 */
    public int a() {
        return zzard.zzahn(this.a) + 0 + this.b.length;
    }

    /* access modifiers changed from: 0000 */
    public void a(zzard zzard) {
        zzard.zzahm(this.a);
        zzard.zzbh(this.b);
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzarm)) {
            return false;
        }
        zzarm zzarm = (zzarm) obj;
        return this.a == zzarm.a && Arrays.equals(this.b, zzarm.b);
    }

    public int hashCode() {
        return ((this.a + 527) * 31) + Arrays.hashCode(this.b);
    }
}
