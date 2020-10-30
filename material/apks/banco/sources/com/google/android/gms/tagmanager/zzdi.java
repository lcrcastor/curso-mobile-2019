package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.zzaf;
import com.google.android.gms.internal.zzai.zza;
import java.util.Map;

class zzdi extends zzam {
    private static final String a = zzaf.TIME.toString();

    public zzdi() {
        super(a, new String[0]);
    }

    public zza zzaw(Map<String, zza> map) {
        return zzdm.zzat(Long.valueOf(System.currentTimeMillis()));
    }

    public boolean zzcds() {
        return false;
    }
}
