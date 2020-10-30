package com.google.android.gms.internal;

import java.util.Map.Entry;
import java.util.Set;

public final class zzaok extends zzaoh {
    private final zzapf<String, zzaoh> a = new zzapf<>();

    private zzaoh a(Object obj) {
        return obj == null ? zzaoj.bld : new zzaon(obj);
    }

    public Set<Entry<String, zzaoh>> entrySet() {
        return this.a.entrySet();
    }

    public boolean equals(Object obj) {
        return obj == this || ((obj instanceof zzaok) && ((zzaok) obj).a.equals(this.a));
    }

    public boolean has(String str) {
        return this.a.containsKey(str);
    }

    public int hashCode() {
        return this.a.hashCode();
    }

    public void zza(String str, zzaoh zzaoh) {
        if (zzaoh == null) {
            zzaoh = zzaoj.bld;
        }
        this.a.put(str, zzaoh);
    }

    public void zzb(String str, Boolean bool) {
        zza(str, a(bool));
    }

    public void zzcb(String str, String str2) {
        zza(str, a(str2));
    }

    public zzaoh zzuo(String str) {
        return (zzaoh) this.a.get(str);
    }

    public zzaoe zzup(String str) {
        return (zzaoe) this.a.get(str);
    }
}
