package com.google.android.gms.tagmanager;

import android.os.Build.VERSION;
import com.google.android.gms.internal.zzaf;
import com.google.android.gms.internal.zzai.zza;
import java.util.Map;

class zzcf extends zzam {
    private static final String a = zzaf.OS_VERSION.toString();

    public zzcf() {
        super(a, new String[0]);
    }

    public zza zzaw(Map<String, zza> map) {
        return zzdm.zzat(VERSION.RELEASE);
    }

    public boolean zzcds() {
        return true;
    }
}
