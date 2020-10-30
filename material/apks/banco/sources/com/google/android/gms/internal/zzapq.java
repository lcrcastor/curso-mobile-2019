package com.google.android.gms.internal;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;

public final class zzapq implements zzaou {
    private final zzapb a;
    /* access modifiers changed from: private */
    public final boolean b;

    final class zza<K, V> extends zzaot<Map<K, V>> {
        private final zzaot<K> b;
        private final zzaot<V> c;
        private final zzapg<? extends Map<K, V>> d;

        public zza(zzaob zzaob, Type type, zzaot<K> zzaot, Type type2, zzaot<V> zzaot2, zzapg<? extends Map<K, V>> zzapg) {
            this.b = new zzapv(zzaob, zzaot, type);
            this.c = new zzapv(zzaob, zzaot2, type2);
            this.d = zzapg;
        }

        private String a(zzaoh zzaoh) {
            if (zzaoh.aU()) {
                zzaon aY = zzaoh.aY();
                if (aY.bb()) {
                    return String.valueOf(aY.aQ());
                }
                if (aY.ba()) {
                    return Boolean.toString(aY.getAsBoolean());
                }
                if (aY.bc()) {
                    return aY.aR();
                }
                throw new AssertionError();
            } else if (zzaoh.aV()) {
                return "null";
            } else {
                throw new AssertionError();
            }
        }

        /* renamed from: a */
        public Map<K, V> zzb(zzapy zzapy) {
            zzapz bn = zzapy.bn();
            if (bn == zzapz.NULL) {
                zzapy.nextNull();
                return null;
            }
            Map<K, V> map = (Map) this.d.bg();
            if (bn == zzapz.BEGIN_ARRAY) {
                zzapy.beginArray();
                while (zzapy.hasNext()) {
                    zzapy.beginArray();
                    Object zzb = this.b.zzb(zzapy);
                    if (map.put(zzb, this.c.zzb(zzapy)) != null) {
                        String valueOf = String.valueOf(zzb);
                        StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 15);
                        sb.append("duplicate key: ");
                        sb.append(valueOf);
                        throw new zzaoq(sb.toString());
                    }
                    zzapy.endArray();
                }
                zzapy.endArray();
                return map;
            }
            zzapy.beginObject();
            while (zzapy.hasNext()) {
                zzapd.blQ.zzi(zzapy);
                Object zzb2 = this.b.zzb(zzapy);
                if (map.put(zzb2, this.c.zzb(zzapy)) != null) {
                    String valueOf2 = String.valueOf(zzb2);
                    StringBuilder sb2 = new StringBuilder(String.valueOf(valueOf2).length() + 15);
                    sb2.append("duplicate key: ");
                    sb2.append(valueOf2);
                    throw new zzaoq(sb2.toString());
                }
            }
            zzapy.endObject();
            return map;
        }

        /* renamed from: a */
        public void zza(zzaqa zzaqa, Map<K, V> map) {
            if (map == null) {
                zzaqa.bx();
            } else if (!zzapq.this.b) {
                zzaqa.bv();
                for (Entry entry : map.entrySet()) {
                    zzaqa.zzus(String.valueOf(entry.getKey()));
                    this.c.zza(zzaqa, entry.getValue());
                }
                zzaqa.bw();
            } else {
                ArrayList arrayList = new ArrayList(map.size());
                ArrayList arrayList2 = new ArrayList(map.size());
                int i = 0;
                boolean z = false;
                for (Entry entry2 : map.entrySet()) {
                    zzaoh zzco = this.b.zzco(entry2.getKey());
                    arrayList.add(zzco);
                    arrayList2.add(entry2.getValue());
                    z |= zzco.aS() || zzco.aT();
                }
                if (z) {
                    zzaqa.bt();
                    while (i < arrayList.size()) {
                        zzaqa.bt();
                        zzapi.zzb((zzaoh) arrayList.get(i), zzaqa);
                        this.c.zza(zzaqa, arrayList2.get(i));
                        zzaqa.bu();
                        i++;
                    }
                    zzaqa.bu();
                    return;
                }
                zzaqa.bv();
                while (i < arrayList.size()) {
                    zzaqa.zzus(a((zzaoh) arrayList.get(i)));
                    this.c.zza(zzaqa, arrayList2.get(i));
                    i++;
                }
                zzaqa.bw();
            }
        }
    }

    public zzapq(zzapb zzapb, boolean z) {
        this.a = zzapb;
        this.b = z;
    }

    private zzaot<?> a(zzaob zzaob, Type type) {
        return (type == Boolean.TYPE || type == Boolean.class) ? zzapw.bmX : zzaob.zza(zzapx.zzl(type));
    }

    public <T> zzaot<T> zza(zzaob zzaob, zzapx<T> zzapx) {
        Type bz = zzapx.bz();
        if (!Map.class.isAssignableFrom(zzapx.by())) {
            return null;
        }
        Type[] zzb = zzapa.zzb(bz, zzapa.zzf(bz));
        zzaob zzaob2 = zzaob;
        zza zza2 = new zza(zzaob2, zzb[0], a(zzaob, zzb[0]), zzb[1], zzaob.zza(zzapx.zzl(zzb[1])), this.a.zzb(zzapx));
        return zza2;
    }
}
