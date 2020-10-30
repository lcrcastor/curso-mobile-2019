package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.zzaf;
import com.google.android.gms.internal.zzai.zza;
import java.util.Map;

class zzdg extends zzdh {
    private static final String a = zzaf.STARTS_WITH.toString();

    public zzdg() {
        super(a);
    }

    /* access modifiers changed from: protected */
    public boolean zza(String str, String str2, Map<String, zza> map) {
        return str.startsWith(str2);
    }
}
