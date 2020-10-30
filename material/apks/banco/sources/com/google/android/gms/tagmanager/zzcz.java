package com.google.android.gms.tagmanager;

import android.os.Build.VERSION;
import com.google.android.gms.internal.zzaf;
import com.google.android.gms.internal.zzai.zza;
import java.util.Map;

class zzcz extends zzam {
    private static final String a = zzaf.SDK_VERSION.toString();

    public zzcz() {
        super(a, new String[0]);
    }

    public zza zzaw(Map<String, zza> map) {
        return zzdm.zzat(Integer.valueOf(VERSION.SDK_INT));
    }

    public boolean zzcds() {
        return true;
    }
}
