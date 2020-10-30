package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.zzai.zza;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

abstract class zzam {
    private final Set<String> a;
    private final String b;

    public zzam(String str, String... strArr) {
        this.b = str;
        this.a = new HashSet(strArr.length);
        for (String add : strArr) {
            this.a.add(add);
        }
    }

    /* access modifiers changed from: 0000 */
    public boolean a(Set<String> set) {
        return set.containsAll(this.a);
    }

    public abstract zza zzaw(Map<String, zza> map);

    public abstract boolean zzcds();

    public String zzcff() {
        return this.b;
    }

    public Set<String> zzcfg() {
        return this.a;
    }
}
