package com.google.android.gms.tagmanager;

import android.content.Context;
import com.google.android.gms.internal.zzaf;
import com.google.android.gms.internal.zzag;
import com.google.android.gms.internal.zzai.zza;
import java.util.Map;

class zze extends zzam {
    private static final String a = zzaf.ADWORDS_CLICK_REFERRER.toString();
    private static final String b = zzag.COMPONENT.toString();
    private static final String c = zzag.CONVERSION_ID.toString();
    private final Context d;

    public zze(Context context) {
        super(a, c);
        this.d = context;
    }

    public zza zzaw(Map<String, zza> map) {
        zza zza = (zza) map.get(c);
        if (zza == null) {
            return zzdm.zzchl();
        }
        zza zza2 = (zza) map.get(b);
        String zzj = zzbf.zzj(this.d, zzdm.zzg(zza), zza2 != null ? zzdm.zzg(zza2) : null);
        return zzj != null ? zzdm.zzat(zzj) : zzdm.zzchl();
    }

    public boolean zzcds() {
        return true;
    }
}
