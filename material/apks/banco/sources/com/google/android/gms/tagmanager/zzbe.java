package com.google.android.gms.tagmanager;

import android.content.Context;
import com.google.android.gms.internal.zzaf;
import com.google.android.gms.internal.zzag;
import com.google.android.gms.internal.zzai.zza;
import java.util.Map;

class zzbe extends zzam {
    private static final String a = zzaf.INSTALL_REFERRER.toString();
    private static final String b = zzag.COMPONENT.toString();
    private final Context c;

    public zzbe(Context context) {
        super(a, new String[0]);
        this.c = context;
    }

    public zza zzaw(Map<String, zza> map) {
        String zzae = zzbf.zzae(this.c, ((zza) map.get(b)) != null ? zzdm.zzg((zza) map.get(b)) : null);
        return zzae != null ? zzdm.zzat(zzae) : zzdm.zzchl();
    }

    public boolean zzcds() {
        return true;
    }
}
