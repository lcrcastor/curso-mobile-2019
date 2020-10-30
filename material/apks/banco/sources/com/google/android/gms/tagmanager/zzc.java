package com.google.android.gms.tagmanager;

import android.content.Context;
import com.google.android.gms.internal.zzaf;
import com.google.android.gms.internal.zzai.zza;
import java.util.Map;

class zzc extends zzam {
    private static final String a = zzaf.ADVERTISING_TRACKING_ENABLED.toString();
    private final zza b;

    public zzc(Context context) {
        this(zza.zzdz(context));
    }

    zzc(zza zza) {
        super(a, new String[0]);
        this.b = zza;
    }

    public zza zzaw(Map<String, zza> map) {
        return zzdm.zzat(Boolean.valueOf(!this.b.isLimitAdTrackingEnabled()));
    }

    public boolean zzcds() {
        return false;
    }
}
