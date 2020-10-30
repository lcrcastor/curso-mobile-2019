package com.google.android.gms.analytics;

import com.google.android.gms.analytics.zzh;
import com.google.android.gms.common.internal.zzac;
import com.google.android.gms.common.util.zze;
import java.util.ArrayList;
import java.util.List;

public abstract class zzh<T extends zzh> {
    private final zzi a;
    private final List<zzf> b = new ArrayList();
    protected final zze zzczx;

    protected zzh(zzi zzi, zze zze) {
        zzac.zzy(zzi);
        this.a = zzi;
        zze zze2 = new zze(this, zze);
        zze2.e();
        this.zzczx = zze2;
    }

    /* access modifiers changed from: protected */
    public void zza(zze zze) {
    }

    /* access modifiers changed from: protected */
    public void zzd(zze zze) {
        for (zzf zza : this.b) {
            zza.zza(this, zze);
        }
    }

    public zze zzxi() {
        zze zzxw = this.zzczx.zzxw();
        zzd(zzxw);
        return zzxw;
    }

    /* access modifiers changed from: protected */
    public zzi zzye() {
        return this.a;
    }

    public zze zzyh() {
        return this.zzczx;
    }

    public List<zzk> zzyi() {
        return this.zzczx.zzxy();
    }
}
