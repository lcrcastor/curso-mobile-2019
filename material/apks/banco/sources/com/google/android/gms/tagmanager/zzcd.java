package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.zzai.zza;
import java.util.Map;

abstract class zzcd extends zzci {
    public zzcd(String str) {
        super(str);
    }

    /* access modifiers changed from: protected */
    public abstract boolean a(zzdl zzdl, zzdl zzdl2, Map<String, zza> map);

    /* access modifiers changed from: protected */
    public boolean zza(zza zza, zza zza2, Map<String, zza> map) {
        zzdl zzh = zzdm.zzh(zza);
        zzdl zzh2 = zzdm.zzh(zza2);
        if (zzh == zzdm.zzchj() || zzh2 == zzdm.zzchj()) {
            return false;
        }
        return a(zzh, zzh2, map);
    }
}
