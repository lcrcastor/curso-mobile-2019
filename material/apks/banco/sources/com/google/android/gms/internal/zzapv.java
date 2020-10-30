package com.google.android.gms.internal;

import com.google.android.gms.internal.zzaps.zza;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

final class zzapv<T> extends zzaot<T> {
    private final zzaob a;
    private final zzaot<T> b;
    private final Type c;

    zzapv(zzaob zzaob, zzaot<T> zzaot, Type type) {
        this.a = zzaob;
        this.b = zzaot;
        this.c = type;
    }

    private Type a(Type type, Object obj) {
        return obj != null ? (type == Object.class || (type instanceof TypeVariable) || (type instanceof Class)) ? obj.getClass() : type : type;
    }

    public void zza(zzaqa zzaqa, T t) {
        zzaot<T> zzaot = this.b;
        Type a2 = a(this.c, t);
        if (a2 != this.c) {
            zzaot = this.a.zza(zzapx.zzl(a2));
            if ((zzaot instanceof zza) && !(this.b instanceof zza)) {
                zzaot = this.b;
            }
        }
        zzaot.zza(zzaqa, t);
    }

    public T zzb(zzapy zzapy) {
        return this.b.zzb(zzapy);
    }
}
