package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.zzaf;
import com.google.android.gms.internal.zzag;
import com.google.android.gms.internal.zzai.zza;
import java.util.Map;

class zzw extends zzam {
    private static final String a = zzaf.CUSTOM_VAR.toString();
    private static final String b = zzag.NAME.toString();
    private static final String c = zzag.DEFAULT_VALUE.toString();
    private final DataLayer d;

    public zzw(DataLayer dataLayer) {
        super(a, b);
        this.d = dataLayer;
    }

    public zza zzaw(Map<String, zza> map) {
        Object obj = this.d.get(zzdm.zzg((zza) map.get(b)));
        if (obj != null) {
            return zzdm.zzat(obj);
        }
        zza zza = (zza) map.get(c);
        return zza != null ? zza : zzdm.zzchl();
    }

    public boolean zzcds() {
        return false;
    }
}
