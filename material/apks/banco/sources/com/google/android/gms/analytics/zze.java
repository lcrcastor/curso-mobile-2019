package com.google.android.gms.analytics;

import com.google.android.gms.common.internal.zzac;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public final class zze {
    private final zzh a;
    private final com.google.android.gms.common.util.zze b;
    private boolean c;
    private long d;
    private long e;
    private long f;
    private long g;
    private long h;
    private boolean i;
    private final Map<Class<? extends zzg>, zzg> j;
    private final List<zzk> k;

    zze(zze zze) {
        this.a = zze.a;
        this.b = zze.b;
        this.d = zze.d;
        this.e = zze.e;
        this.f = zze.f;
        this.g = zze.g;
        this.h = zze.h;
        this.k = new ArrayList(zze.k);
        this.j = new HashMap(zze.j.size());
        for (Entry entry : zze.j.entrySet()) {
            zzg a2 = a((Class) entry.getKey());
            ((zzg) entry.getValue()).zzb(a2);
            this.j.put((Class) entry.getKey(), a2);
        }
    }

    zze(zzh zzh, com.google.android.gms.common.util.zze zze) {
        zzac.zzy(zzh);
        zzac.zzy(zze);
        this.a = zzh;
        this.b = zze;
        this.g = 1800000;
        this.h = 3024000000L;
        this.j = new HashMap();
        this.k = new ArrayList();
    }

    private static <T extends zzg> T a(Class<T> cls) {
        try {
            return (zzg) cls.newInstance();
        } catch (InstantiationException e2) {
            throw new IllegalArgumentException("dataType doesn't have default constructor", e2);
        } catch (IllegalAccessException e3) {
            throw new IllegalArgumentException("dataType default constructor is not accessible", e3);
        }
    }

    /* access modifiers changed from: 0000 */
    public void a() {
        this.f = this.b.elapsedRealtime();
        this.d = this.e != 0 ? this.e : this.b.currentTimeMillis();
        this.c = true;
    }

    /* access modifiers changed from: 0000 */
    public zzh b() {
        return this.a;
    }

    /* access modifiers changed from: 0000 */
    public zzi c() {
        return this.a.zzye();
    }

    /* access modifiers changed from: 0000 */
    public boolean d() {
        return this.i;
    }

    /* access modifiers changed from: 0000 */
    public void e() {
        this.i = true;
    }

    public <T extends zzg> T zza(Class<T> cls) {
        return (zzg) this.j.get(cls);
    }

    public void zza(zzg zzg) {
        zzac.zzy(zzg);
        Class cls = zzg.getClass();
        if (cls.getSuperclass() != zzg.class) {
            throw new IllegalArgumentException();
        }
        zzg.zzb(zzb(cls));
    }

    public <T extends zzg> T zzb(Class<T> cls) {
        T t = (zzg) this.j.get(cls);
        if (t != null) {
            return t;
        }
        T a2 = a(cls);
        this.j.put(cls, a2);
        return a2;
    }

    public void zzp(long j2) {
        this.e = j2;
    }

    public zze zzxw() {
        return new zze(this);
    }

    public Collection<zzg> zzxx() {
        return this.j.values();
    }

    public List<zzk> zzxy() {
        return this.k;
    }

    public long zzxz() {
        return this.d;
    }

    public void zzya() {
        c().a(this);
    }

    public boolean zzyb() {
        return this.c;
    }
}
