package com.google.android.gms.internal;

final class zzaos<T> extends zzaot<T> {
    private final zzaop<T> a;
    private final zzaog<T> b;
    private final zzaob c;
    private final zzapx<T> d;
    private final zzaou e;
    private zzaot<T> f;

    static class zza implements zzaou {
        private final zzapx<?> a;
        private final boolean b;
        private final Class<?> c;
        private final zzaop<?> d;
        private final zzaog<?> e;

        private zza(Object obj, zzapx<?> zzapx, boolean z, Class<?> cls) {
            zzaog<?> zzaog = null;
            this.d = obj instanceof zzaop ? (zzaop) obj : null;
            if (obj instanceof zzaog) {
                zzaog = (zzaog) obj;
            }
            this.e = zzaog;
            zzaoz.zzbs((this.d == null && this.e == null) ? false : true);
            this.a = zzapx;
            this.b = z;
            this.c = cls;
        }

        public <T> zzaot<T> zza(zzaob zzaob, zzapx<T> zzapx) {
            boolean z = this.a != null ? this.a.equals(zzapx) || (this.b && this.a.bz() == zzapx.by()) : this.c.isAssignableFrom(zzapx.by());
            if (!z) {
                return null;
            }
            zzaos zzaos = new zzaos(this.d, this.e, zzaob, zzapx, this);
            return zzaos;
        }
    }

    private zzaos(zzaop<T> zzaop, zzaog<T> zzaog, zzaob zzaob, zzapx<T> zzapx, zzaou zzaou) {
        this.a = zzaop;
        this.b = zzaog;
        this.c = zzaob;
        this.d = zzapx;
        this.e = zzaou;
    }

    private zzaot<T> a() {
        zzaot<T> zzaot = this.f;
        if (zzaot != null) {
            return zzaot;
        }
        zzaot<T> zza2 = this.c.zza(this.e, this.d);
        this.f = zza2;
        return zza2;
    }

    public static zzaou a(zzapx<?> zzapx, Object obj) {
        zza zza2 = new zza(obj, zzapx, false, null);
        return zza2;
    }

    public static zzaou b(zzapx<?> zzapx, Object obj) {
        zza zza2 = new zza(obj, zzapx, zzapx.bz() == zzapx.by(), null);
        return zza2;
    }

    public void zza(zzaqa zzaqa, T t) {
        if (this.a == null) {
            a().zza(zzaqa, t);
        } else if (t == null) {
            zzaqa.bx();
        } else {
            zzapi.zzb(this.a.zza(t, this.d.bz(), this.c.b), zzaqa);
        }
    }

    public T zzb(zzapy zzapy) {
        if (this.b == null) {
            return a().zzb(zzapy);
        }
        zzaoh zzh = zzapi.zzh(zzapy);
        if (zzh.aV()) {
            return null;
        }
        try {
            return this.b.zzb(zzh, this.d.bz(), this.c.a);
        } catch (zzaol e2) {
            throw e2;
        } catch (Exception e3) {
            throw new zzaol((Throwable) e3);
        }
    }
}
