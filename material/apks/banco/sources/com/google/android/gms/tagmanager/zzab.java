package com.google.android.gms.tagmanager;

import android.content.Context;
import android.provider.Settings.Secure;
import com.google.android.gms.internal.zzaf;
import com.google.android.gms.internal.zzai.zza;
import java.util.Map;

class zzab extends zzam {
    private static final String a = zzaf.DEVICE_ID.toString();
    private final Context b;

    public zzab(Context context) {
        super(a, new String[0]);
        this.b = context;
    }

    /* access modifiers changed from: protected */
    public String a(Context context) {
        return Secure.getString(context.getContentResolver(), "android_id");
    }

    public zza zzaw(Map<String, zza> map) {
        String a2 = a(this.b);
        return a2 == null ? zzdm.zzchl() : zzdm.zzat(a2);
    }

    public boolean zzcds() {
        return true;
    }
}
