package com.google.android.gms.internal;

import java.lang.reflect.Type;
import java.util.Collection;

public final class zzapl implements zzaou {
    private final zzapb a;

    static final class zza<E> extends zzaot<Collection<E>> {
        private final zzaot<E> a;
        private final zzapg<? extends Collection<E>> b;

        public zza(zzaob zzaob, Type type, zzaot<E> zzaot, zzapg<? extends Collection<E>> zzapg) {
            this.a = new zzapv(zzaob, zzaot, type);
            this.b = zzapg;
        }

        /* renamed from: a */
        public Collection<E> zzb(zzapy zzapy) {
            if (zzapy.bn() == zzapz.NULL) {
                zzapy.nextNull();
                return null;
            }
            Collection<E> collection = (Collection) this.b.bg();
            zzapy.beginArray();
            while (zzapy.hasNext()) {
                collection.add(this.a.zzb(zzapy));
            }
            zzapy.endArray();
            return collection;
        }

        /* renamed from: a */
        public void zza(zzaqa zzaqa, Collection<E> collection) {
            if (collection == null) {
                zzaqa.bx();
                return;
            }
            zzaqa.bt();
            for (E zza : collection) {
                this.a.zza(zzaqa, zza);
            }
            zzaqa.bu();
        }
    }

    public zzapl(zzapb zzapb) {
        this.a = zzapb;
    }

    public <T> zzaot<T> zza(zzaob zzaob, zzapx<T> zzapx) {
        Type bz = zzapx.bz();
        Class by = zzapx.by();
        if (!Collection.class.isAssignableFrom(by)) {
            return null;
        }
        Type zza2 = zzapa.zza(bz, by);
        return new zza(zzaob, zza2, zzaob.zza(zzapx.zzl(zza2)), this.a.zzb(zzapx));
    }
}
