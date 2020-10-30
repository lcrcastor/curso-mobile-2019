package com.google.android.gms.tagmanager;

import android.os.Build;
import android.support.v4.os.EnvironmentCompat;
import ar.com.santander.rio.mbanking.utils.UtilsCuentas;
import com.google.android.gms.internal.zzaf;
import com.google.android.gms.internal.zzai.zza;
import java.util.Map;

class zzac extends zzam {
    private static final String a = zzaf.DEVICE_NAME.toString();

    public zzac() {
        super(a, new String[0]);
    }

    public zza zzaw(Map<String, zza> map) {
        String str = Build.MANUFACTURER;
        String str2 = Build.MODEL;
        if (!str2.startsWith(str) && !str.equals(EnvironmentCompat.MEDIA_UNKNOWN)) {
            StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 1 + String.valueOf(str2).length());
            sb.append(str);
            sb.append(UtilsCuentas.SEPARAOR2);
            sb.append(str2);
            str2 = sb.toString();
        }
        return zzdm.zzat(str2);
    }

    public boolean zzcds() {
        return true;
    }
}
