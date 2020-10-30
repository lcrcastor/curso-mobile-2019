package com.google.android.gms.analytics.ecommerce;

import com.google.android.gms.analytics.zzg;
import com.google.android.gms.common.internal.zzac;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Promotion {
    public static final String ACTION_CLICK = "click";
    public static final String ACTION_VIEW = "view";
    Map<String, String> a = new HashMap();

    /* access modifiers changed from: 0000 */
    public void a(String str, String str2) {
        zzac.zzb(str, (Object) "Name should be non-null");
        this.a.put(str, str2);
    }

    public Promotion setCreative(String str) {
        a("cr", str);
        return this;
    }

    public Promotion setId(String str) {
        a("id", str);
        return this;
    }

    public Promotion setName(String str) {
        a("nm", str);
        return this;
    }

    public Promotion setPosition(String str) {
        a("ps", str);
        return this;
    }

    public String toString() {
        return zzg.zzap(this.a);
    }

    public Map<String, String> zzem(String str) {
        HashMap hashMap = new HashMap();
        for (Entry entry : this.a.entrySet()) {
            String valueOf = String.valueOf(str);
            String valueOf2 = String.valueOf((String) entry.getKey());
            hashMap.put(valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf), (String) entry.getValue());
        }
        return hashMap;
    }
}
