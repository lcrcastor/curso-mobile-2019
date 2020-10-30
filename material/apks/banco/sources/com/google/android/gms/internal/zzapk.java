package com.google.android.gms.internal;

import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Type;
import java.util.ArrayList;

public final class zzapk<E> extends zzaot<Object> {
    public static final zzaou bmp = new zzaou() {
        public <T> zzaot<T> zza(zzaob zzaob, zzapx<T> zzapx) {
            Type bz = zzapx.bz();
            if (!(bz instanceof GenericArrayType) && (!(bz instanceof Class) || !((Class) bz).isArray())) {
                return null;
            }
            Type zzh = zzapa.zzh(bz);
            return new zzapk(zzaob, zzaob.zza(zzapx.zzl(zzh)), zzapa.zzf(zzh));
        }
    };
    private final Class<E> a;
    private final zzaot<E> b;

    public zzapk(zzaob zzaob, zzaot<E> zzaot, Class<E> cls) {
        this.b = new zzapv(zzaob, zzaot, cls);
        this.a = cls;
    }

    public void zza(zzaqa zzaqa, Object obj) {
        if (obj == null) {
            zzaqa.bx();
            return;
        }
        zzaqa.bt();
        int length = Array.getLength(obj);
        for (int i = 0; i < length; i++) {
            this.b.zza(zzaqa, Array.get(obj, i));
        }
        zzaqa.bu();
    }

    public Object zzb(zzapy zzapy) {
        if (zzapy.bn() == zzapz.NULL) {
            zzapy.nextNull();
            return null;
        }
        ArrayList arrayList = new ArrayList();
        zzapy.beginArray();
        while (zzapy.hasNext()) {
            arrayList.add(this.b.zzb(zzapy));
        }
        zzapy.endArray();
        Object newInstance = Array.newInstance(this.a, arrayList.size());
        for (int i = 0; i < arrayList.size(); i++) {
            Array.set(newInstance, i, arrayList.get(i));
        }
        return newInstance;
    }
}
