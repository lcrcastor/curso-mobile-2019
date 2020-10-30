package com.google.android.gms.tagmanager;

import android.content.Context;
import com.google.android.gms.internal.zzaf;
import com.google.android.gms.internal.zzai.zza;
import java.util.Map;

class zzf extends zzam {
    private static final String a = zzaf.APP_ID.toString();
    private final Context b;

    public zzf(Context context) {
        super(a, new String[0]);
        this.b = context;
    }

    public zza zzaw(Map<String, zza> map) {
        return zzdm.zzat(this.b.getPackageName());
    }

    public boolean zzcds() {
        return true;
    }
}
