package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.zzai.zza;
import java.util.Map;

abstract class zzdh extends zzci {
    public zzdh(String str) {
        super(str);
    }

    /* access modifiers changed from: protected */
    public boolean zza(zza zza, zza zza2, Map<String, zza> map) {
        String zzg = zzdm.zzg(zza);
        String zzg2 = zzdm.zzg(zza2);
        if (zzg == zzdm.zzchk() || zzg2 == zzdm.zzchk()) {
            return false;
        }
        return zza(zzg, zzg2, map);
    }

    /* access modifiers changed from: protected */
    public abstract boolean zza(String str, String str2, Map<String, zza> map);
}
