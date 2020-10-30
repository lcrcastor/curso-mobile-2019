package com.google.android.gms.internal;

import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.internal.zzrd.zzc;

public abstract class zzqj<L> implements zzc<L> {
    private final DataHolder a;

    protected zzqj(DataHolder dataHolder) {
        this.a = dataHolder;
    }

    /* access modifiers changed from: protected */
    public abstract void zza(L l, DataHolder dataHolder);

    public void zzarg() {
        if (this.a != null) {
            this.a.close();
        }
    }

    public final void zzt(L l) {
        zza(l, this.a);
    }
}
