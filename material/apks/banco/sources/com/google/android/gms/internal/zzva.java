package com.google.android.gms.internal;

public final class zzva {
    private static zzva a;
    private final zzux b = new zzux();
    private final zzuy c = new zzuy();

    static {
        zza(new zzva());
    }

    private zzva() {
    }

    private static zzva a() {
        zzva zzva;
        synchronized (zzva.class) {
            zzva = a;
        }
        return zzva;
    }

    protected static void zza(zzva zzva) {
        synchronized (zzva.class) {
            a = zzva;
        }
    }

    public static zzux zzbhm() {
        return a().b;
    }

    public static zzuy zzbhn() {
        return a().c;
    }
}
