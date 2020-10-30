package com.google.android.gms.tagmanager;

interface zzbn<T> {

    public enum zza {
        NOT_AVAILABLE,
        IO_ERROR,
        SERVER_ERROR,
        SERVER_UNAVAILABLE_ERROR
    }

    void a();

    void a(zza zza2);

    void a(T t);
}
