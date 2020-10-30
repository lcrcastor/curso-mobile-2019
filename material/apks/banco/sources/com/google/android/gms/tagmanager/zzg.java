package com.google.android.gms.tagmanager;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import com.google.android.gms.internal.zzaf;
import com.google.android.gms.internal.zzai.zza;
import java.util.Map;

class zzg extends zzam {
    private static final String a = zzaf.APP_NAME.toString();
    private final Context b;

    public zzg(Context context) {
        super(a, new String[0]);
        this.b = context;
    }

    public zza zzaw(Map<String, zza> map) {
        try {
            PackageManager packageManager = this.b.getPackageManager();
            return zzdm.zzat(packageManager.getApplicationLabel(packageManager.getApplicationInfo(this.b.getPackageName(), 0)).toString());
        } catch (NameNotFoundException e) {
            zzbo.zzb("App name is not found.", e);
            return zzdm.zzchl();
        }
    }

    public boolean zzcds() {
        return true;
    }
}
