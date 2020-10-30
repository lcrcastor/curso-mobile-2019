package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.zzaf;
import com.google.android.gms.internal.zzai.zza;
import java.util.Map;

class zzap extends zzcd {
    private static final String a = zzaf.GREATER_THAN.toString();

    public zzap() {
        super(a);
    }

    /* access modifiers changed from: protected */
    public boolean a(zzdl zzdl, zzdl zzdl2, Map<String, zza> map) {
        return zzdl.compareTo(zzdl2) > 0;
    }
}
