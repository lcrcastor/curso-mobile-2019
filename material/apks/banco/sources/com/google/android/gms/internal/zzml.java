package com.google.android.gms.internal;

import com.google.android.gms.analytics.zzg;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public final class zzml extends zzg<zzml> {
    private Map<Integer, Double> a = new HashMap(4);

    public String toString() {
        HashMap hashMap = new HashMap();
        for (Entry entry : this.a.entrySet()) {
            String valueOf = String.valueOf(entry.getKey());
            StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 6);
            sb.append("metric");
            sb.append(valueOf);
            hashMap.put(sb.toString(), entry.getValue());
        }
        return zzj(hashMap);
    }

    /* renamed from: zza */
    public void zzb(zzml zzml) {
        zzml.a.putAll(this.a);
    }

    public Map<Integer, Double> zzzc() {
        return Collections.unmodifiableMap(this.a);
    }
}
