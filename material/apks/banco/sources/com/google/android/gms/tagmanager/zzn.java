package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.zzaf;
import com.google.android.gms.internal.zzag;
import com.google.android.gms.internal.zzai.zza;
import java.util.Map;

class zzn extends zzam {
    private static final String a = zzaf.CONSTANT.toString();
    private static final String b = zzag.VALUE.toString();

    public zzn() {
        super(a, b);
    }

    public static String a() {
        return a;
    }

    public static String b() {
        return b;
    }

    public zza zzaw(Map<String, zza> map) {
        return (zza) map.get(b);
    }

    public boolean zzcds() {
        return true;
    }
}
