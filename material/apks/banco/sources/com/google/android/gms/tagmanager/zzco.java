package com.google.android.gms.tagmanager;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import com.google.android.gms.internal.zzaf;
import com.google.android.gms.internal.zzai.zza;
import java.util.Map;

class zzco extends zzam {
    private static final String a = zzaf.RESOLUTION.toString();
    private final Context b;

    public zzco(Context context) {
        super(a, new String[0]);
        this.b = context;
    }

    public zza zzaw(Map<String, zza> map) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager) this.b.getSystemService("window")).getDefaultDisplay().getMetrics(displayMetrics);
        int i = displayMetrics.widthPixels;
        int i2 = displayMetrics.heightPixels;
        StringBuilder sb = new StringBuilder(23);
        sb.append(i);
        sb.append("x");
        sb.append(i2);
        return zzdm.zzat(sb.toString());
    }

    public boolean zzcds() {
        return true;
    }
}
