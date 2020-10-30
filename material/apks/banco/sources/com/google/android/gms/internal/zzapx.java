package com.google.android.gms.internal;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class zzapx<T> {
    final Class<? super T> a;
    final Type b;
    final int c;

    protected zzapx() {
        this.b = a(getClass());
        this.a = zzapa.zzf(this.b);
        this.c = this.b.hashCode();
    }

    zzapx(Type type) {
        this.b = zzapa.zze((Type) zzaoz.zzy(type));
        this.a = zzapa.zzf(this.b);
        this.c = this.b.hashCode();
    }

    static Type a(Class<?> cls) {
        Type genericSuperclass = cls.getGenericSuperclass();
        if (!(genericSuperclass instanceof Class)) {
            return zzapa.zze(((ParameterizedType) genericSuperclass).getActualTypeArguments()[0]);
        }
        throw new RuntimeException("Missing type parameter.");
    }

    public static zzapx<?> zzl(Type type) {
        return new zzapx<>(type);
    }

    public static <T> zzapx<T> zzr(Class<T> cls) {
        return new zzapx<>(cls);
    }

    public final Class<? super T> by() {
        return this.a;
    }

    public final Type bz() {
        return this.b;
    }

    public final boolean equals(Object obj) {
        return (obj instanceof zzapx) && zzapa.zza(this.b, ((zzapx) obj).b);
    }

    public final int hashCode() {
        return this.c;
    }

    public final String toString() {
        return zzapa.zzg(this.b);
    }
}
