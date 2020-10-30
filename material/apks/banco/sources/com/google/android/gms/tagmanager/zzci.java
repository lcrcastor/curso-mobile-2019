package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.zzag;
import com.google.android.gms.internal.zzai.zza;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public abstract class zzci extends zzam {
    private static final String a = zzag.ARG0.toString();
    private static final String b = zzag.ARG1.toString();

    public zzci(String str) {
        super(str, a, b);
    }

    /* access modifiers changed from: protected */
    public abstract boolean zza(zza zza, zza zza2, Map<String, zza> map);

    public zza zzaw(Map<String, zza> map) {
        boolean z;
        Iterator it = map.values().iterator();
        while (true) {
            z = false;
            if (it.hasNext()) {
                if (((zza) it.next()) == zzdm.zzchl()) {
                    break;
                }
            } else {
                zza zza = (zza) map.get(a);
                zza zza2 = (zza) map.get(b);
                if (zza != null && zza2 != null) {
                    z = zza(zza, zza2, map);
                }
            }
        }
        return zzdm.zzat(Boolean.valueOf(z));
    }

    public boolean zzcds() {
        return true;
    }

    public /* bridge */ /* synthetic */ String zzcff() {
        return super.zzcff();
    }

    public /* bridge */ /* synthetic */ Set zzcfg() {
        return super.zzcfg();
    }
}
