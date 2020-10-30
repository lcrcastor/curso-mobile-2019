package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.zzaf;
import com.google.android.gms.internal.zzai.zza;
import java.util.Map;

class zzag extends zzdh {
    private static final String a = zzaf.ENDS_WITH.toString();

    public zzag() {
        super(a);
    }

    /* access modifiers changed from: protected */
    public boolean zza(String str, String str2, Map<String, zza> map) {
        return str.endsWith(str2);
    }
}
