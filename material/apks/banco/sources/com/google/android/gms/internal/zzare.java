package com.google.android.gms.internal;

import com.google.android.gms.internal.zzare;

public abstract class zzare<M extends zzare<M>> extends zzark {
    protected zzarg bqv;

    /* renamed from: cP */
    public M clone() {
        M m = (zzare) super.clone();
        zzari.zza(this, (zzare) m);
        return m;
    }

    public /* synthetic */ zzark cQ() {
        return (zzare) clone();
    }

    public final <T> T zza(zzarf<M, T> zzarf) {
        if (this.bqv == null) {
            return null;
        }
        zzarh a = this.bqv.a(zzarn.zzahu(zzarf.tag));
        if (a == null) {
            return null;
        }
        return a.a(zzarf);
    }

    public void zza(zzard zzard) {
        if (this.bqv != null) {
            for (int i = 0; i < this.bqv.a(); i++) {
                this.bqv.b(i).a(zzard);
            }
        }
    }

    /* access modifiers changed from: protected */
    public final boolean zza(zzarc zzarc, int i) {
        int position = zzarc.getPosition();
        if (!zzarc.zzaha(i)) {
            return false;
        }
        int zzahu = zzarn.zzahu(i);
        zzarm zzarm = new zzarm(i, zzarc.zzad(position, zzarc.getPosition() - position));
        zzarh zzarh = null;
        if (this.bqv == null) {
            this.bqv = new zzarg();
        } else {
            zzarh = this.bqv.a(zzahu);
        }
        if (zzarh == null) {
            zzarh = new zzarh();
            this.bqv.a(zzahu, zzarh);
        }
        zzarh.a(zzarm);
        return true;
    }

    /* access modifiers changed from: protected */
    public int zzx() {
        if (this.bqv == null) {
            return 0;
        }
        int i = 0;
        for (int i2 = 0; i2 < this.bqv.a(); i2++) {
            i += this.bqv.b(i2).a();
        }
        return i;
    }
}
