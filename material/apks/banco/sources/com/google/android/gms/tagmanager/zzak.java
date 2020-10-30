package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.zzaf;
import com.google.android.gms.internal.zzai.zza;
import java.util.Map;

class zzak extends zzam {
    private static final String a = zzaf.EVENT.toString();
    private final zzcx b;

    public zzak(zzcx zzcx) {
        super(a, new String[0]);
        this.b = zzcx;
    }

    public zza zzaw(Map<String, zza> map) {
        String a2 = this.b.a();
        return a2 == null ? zzdm.zzchl() : zzdm.zzat(a2);
    }

    public boolean zzcds() {
        return false;
    }
}
