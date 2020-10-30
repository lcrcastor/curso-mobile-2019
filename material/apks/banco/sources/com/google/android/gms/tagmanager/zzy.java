package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.zzaf;
import com.google.android.gms.internal.zzag;
import com.google.android.gms.internal.zzai.zza;
import java.util.List;
import java.util.Map;

class zzy extends zzdk {
    private static final String a = zzaf.DATA_LAYER_WRITE.toString();
    private static final String b = zzag.VALUE.toString();
    private static final String c = zzag.CLEAR_PERSISTENT_DATA_LAYER_PREFIX.toString();
    private final DataLayer d;

    public zzy(DataLayer dataLayer) {
        super(a, b);
        this.d = dataLayer;
    }

    private void a(zza zza) {
        if (zza != null && zza != zzdm.zzchf()) {
            String zzg = zzdm.zzg(zza);
            if (zzg != zzdm.zzchk()) {
                this.d.a(zzg);
            }
        }
    }

    private void b(zza zza) {
        if (zza != null && zza != zzdm.zzchf()) {
            Object zzl = zzdm.zzl(zza);
            if (zzl instanceof List) {
                for (Object next : (List) zzl) {
                    if (next instanceof Map) {
                        this.d.push((Map) next);
                    }
                }
            }
        }
    }

    public void zzay(Map<String, zza> map) {
        b((zza) map.get(b));
        a((zza) map.get(c));
    }
}
