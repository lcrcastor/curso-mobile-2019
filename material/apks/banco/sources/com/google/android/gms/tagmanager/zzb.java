package com.google.android.gms.tagmanager;

import android.content.Context;
import com.google.android.gms.internal.zzaf;
import com.google.android.gms.internal.zzai.zza;
import java.util.Map;

class zzb extends zzam {
    private static final String a = zzaf.ADVERTISER_ID.toString();
    private final zza b;

    public zzb(Context context) {
        this(zza.zzdz(context));
    }

    zzb(zza zza) {
        super(a, new String[0]);
        this.b = zza;
        this.b.zzcdm();
    }

    public zza zzaw(Map<String, zza> map) {
        String zzcdm = this.b.zzcdm();
        return zzcdm == null ? zzdm.zzchl() : zzdm.zzat(zzcdm);
    }

    public boolean zzcds() {
        return false;
    }
}
