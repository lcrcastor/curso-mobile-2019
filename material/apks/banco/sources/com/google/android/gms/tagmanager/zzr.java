package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.zzaf;
import com.google.android.gms.internal.zzai.zza;
import java.util.Map;

class zzr extends zzam {
    private static final String a = zzaf.CONTAINER_VERSION.toString();
    private final String b;

    public zzr(String str) {
        super(a, new String[0]);
        this.b = str;
    }

    public zza zzaw(Map<String, zza> map) {
        return this.b == null ? zzdm.zzchl() : zzdm.zzat(this.b);
    }

    public boolean zzcds() {
        return true;
    }
}
