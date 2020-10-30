package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.zzaf;
import com.google.android.gms.internal.zzai.zza;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public class zzbj extends zzam {
    private static final String a = zzaf.LANGUAGE.toString();

    public zzbj() {
        super(a, new String[0]);
    }

    public zza zzaw(Map<String, zza> map) {
        Locale locale = Locale.getDefault();
        if (locale == null) {
            return zzdm.zzchl();
        }
        String language = locale.getLanguage();
        return language == null ? zzdm.zzchl() : zzdm.zzat(language.toLowerCase());
    }

    public boolean zzcds() {
        return false;
    }

    public /* bridge */ /* synthetic */ String zzcff() {
        return super.zzcff();
    }

    public /* bridge */ /* synthetic */ Set zzcfg() {
        return super.zzcfg();
    }
}
