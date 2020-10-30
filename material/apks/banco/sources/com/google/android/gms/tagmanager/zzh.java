package com.google.android.gms.tagmanager;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import com.google.android.gms.internal.zzaf;
import com.google.android.gms.internal.zzai.zza;
import java.util.Map;

class zzh extends zzam {
    private static final String a = zzaf.APP_VERSION.toString();
    private final Context b;

    public zzh(Context context) {
        super(a, new String[0]);
        this.b = context;
    }

    public zza zzaw(Map<String, zza> map) {
        try {
            return zzdm.zzat(Integer.valueOf(this.b.getPackageManager().getPackageInfo(this.b.getPackageName(), 0).versionCode));
        } catch (NameNotFoundException e) {
            String valueOf = String.valueOf(this.b.getPackageName());
            String valueOf2 = String.valueOf(e.getMessage());
            StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 25 + String.valueOf(valueOf2).length());
            sb.append("Package name ");
            sb.append(valueOf);
            sb.append(" not found. ");
            sb.append(valueOf2);
            zzbo.e(sb.toString());
            return zzdm.zzchl();
        }
    }

    public boolean zzcds() {
        return true;
    }
}
