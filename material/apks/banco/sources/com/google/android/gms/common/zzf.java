package com.google.android.gms.common;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.Log;
import com.google.android.gms.common.internal.zzac;
import com.google.android.gms.common.internal.zzt;
import com.google.android.gms.internal.zzsi;

public class zzf {
    private static zzf a;
    private final Context b;

    private zzf(Context context) {
        this.b = context.getApplicationContext();
    }

    private boolean a(PackageInfo packageInfo, boolean z) {
        if (packageInfo.signatures.length != 1) {
            Log.w("GoogleSignatureVerifier", "Package has more than one signature.");
            return false;
        }
        zzb zzb = new zzb(packageInfo.signatures[0].toByteArray());
        for (zzt equals : z ? zzd.a() : zzd.b()) {
            if (zzb.equals(equals)) {
                return true;
            }
        }
        return false;
    }

    public static zzf zzbz(Context context) {
        zzac.zzy(context);
        synchronized (zzf.class) {
            if (a == null) {
                zzd.a(context);
                a = new zzf(context);
            }
        }
        return a;
    }

    /* access modifiers changed from: 0000 */
    public zza a(PackageInfo packageInfo, zza... zzaArr) {
        if (packageInfo.signatures == null) {
            return null;
        }
        if (packageInfo.signatures.length != 1) {
            Log.w("GoogleSignatureVerifier", "Package has more than one signature.");
            return null;
        }
        zzb zzb = new zzb(packageInfo.signatures[0].toByteArray());
        for (int i = 0; i < zzaArr.length; i++) {
            if (zzaArr[i].equals(zzb)) {
                return zzaArr[i];
            }
        }
        return null;
    }

    public boolean zza(PackageInfo packageInfo, boolean z) {
        if (!(packageInfo == null || packageInfo.signatures == null)) {
            if (a(packageInfo, z ? C0014zzd.a : new zza[]{C0014zzd.a[0]}) != null) {
                return true;
            }
        }
        return false;
    }

    public boolean zza(PackageManager packageManager, PackageInfo packageInfo) {
        if (packageInfo == null) {
            return false;
        }
        if (zze.zzbv(this.b)) {
            return a(packageInfo, true);
        }
        boolean a2 = a(packageInfo, false);
        if (!a2 && a(packageInfo, true)) {
            Log.w("GoogleSignatureVerifier", "Test-keys aren't accepted on this build.");
        }
        return a2;
    }

    public boolean zzb(PackageManager packageManager, PackageInfo packageInfo) {
        if (packageInfo == null) {
            return false;
        }
        if (zza(packageInfo, false)) {
            return true;
        }
        if (zza(packageInfo, true)) {
            if (zze.zzbv(this.b)) {
                return true;
            }
            Log.w("GoogleSignatureVerifier", "Test-keys aren't accepted on this build.");
        }
        return false;
    }

    public boolean zzb(PackageManager packageManager, String str) {
        try {
            return zza(packageManager, zzsi.zzcr(this.b).getPackageInfo(str, 64));
        } catch (NameNotFoundException unused) {
            return false;
        }
    }
}
