package com.google.android.gms.internal;

import com.google.android.gms.analytics.zzg;
import com.google.android.gms.common.internal.zzac;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class zzmm extends zzg<zzmm> {
    private final Map<String, Object> a = new HashMap();

    private String a(String str) {
        zzac.zzhz(str);
        if (str != null && str.startsWith("&")) {
            str = str.substring(1);
        }
        zzac.zzh(str, "Name can not be empty or \"&\"");
        return str;
    }

    public void set(String str, String str2) {
        this.a.put(a(str), str2);
    }

    public String toString() {
        return zzj(this.a);
    }

    /* renamed from: zza */
    public void zzb(zzmm zzmm) {
        zzac.zzy(zzmm);
        zzmm.a.putAll(this.a);
    }

    public Map<String, Object> zzzd() {
        return Collections.unmodifiableMap(this.a);
    }
}
