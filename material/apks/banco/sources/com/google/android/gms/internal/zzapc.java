package com.google.android.gms.internal;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.bouncycastle.crypto.tls.CipherSuite;

public final class zzapc implements zzaou, Cloneable {
    public static final zzapc blF = new zzapc();
    private double a = -1.0d;
    private int b = CipherSuite.TLS_DHE_RSA_WITH_CAMELLIA_256_CBC_SHA;
    private boolean c = true;
    private List<zzanx> d = Collections.emptyList();
    private List<zzanx> e = Collections.emptyList();

    private boolean a(zzaox zzaox) {
        return zzaox == null || zzaox.bf() <= this.a;
    }

    private boolean a(zzaox zzaox, zzaoy zzaoy) {
        return a(zzaox) && a(zzaoy);
    }

    private boolean a(zzaoy zzaoy) {
        return zzaoy == null || zzaoy.bf() > this.a;
    }

    private boolean a(Class<?> cls) {
        return !Enum.class.isAssignableFrom(cls) && (cls.isAnonymousClass() || cls.isLocalClass());
    }

    private boolean b(Class<?> cls) {
        return cls.isMemberClass() && !c(cls);
    }

    private boolean c(Class<?> cls) {
        return (cls.getModifiers() & 8) != 0;
    }

    /* access modifiers changed from: protected */
    /* renamed from: bh */
    public zzapc clone() {
        try {
            return (zzapc) super.clone();
        } catch (CloneNotSupportedException unused) {
            throw new AssertionError();
        }
    }

    public <T> zzaot<T> zza(zzaob zzaob, zzapx<T> zzapx) {
        Class by = zzapx.by();
        final boolean zza = zza(by, true);
        final boolean zza2 = zza(by, false);
        if (!zza && !zza2) {
            return null;
        }
        final zzaob zzaob2 = zzaob;
        final zzapx<T> zzapx2 = zzapx;
        AnonymousClass1 r2 = new zzaot<T>() {
            private zzaot<T> f;

            private zzaot<T> a() {
                zzaot<T> zzaot = this.f;
                if (zzaot != null) {
                    return zzaot;
                }
                zzaot<T> zza = zzaob2.zza((zzaou) zzapc.this, zzapx2);
                this.f = zza;
                return zza;
            }

            public void zza(zzaqa zzaqa, T t) {
                if (zza) {
                    zzaqa.bx();
                } else {
                    a().zza(zzaqa, t);
                }
            }

            public T zzb(zzapy zzapy) {
                if (!zza2) {
                    return a().zzb(zzapy);
                }
                zzapy.skipValue();
                return null;
            }
        };
        return r2;
    }

    public zzapc zza(zzanx zzanx, boolean z, boolean z2) {
        zzapc bh = clone();
        if (z) {
            bh.d = new ArrayList(this.d);
            bh.d.add(zzanx);
        }
        if (z2) {
            bh.e = new ArrayList(this.e);
            bh.e.add(zzanx);
        }
        return bh;
    }

    public boolean zza(Class<?> cls, boolean z) {
        if (this.a != -1.0d && !a((zzaox) cls.getAnnotation(zzaox.class), (zzaoy) cls.getAnnotation(zzaoy.class))) {
            return true;
        }
        if ((!this.c && b(cls)) || a(cls)) {
            return true;
        }
        for (zzanx zzh : z ? this.d : this.e) {
            if (zzh.zzh(cls)) {
                return true;
            }
        }
        return false;
    }

    public boolean zza(Field field, boolean z) {
        if ((this.b & field.getModifiers()) != 0) {
            return true;
        }
        if ((this.a != -1.0d && !a((zzaox) field.getAnnotation(zzaox.class), (zzaoy) field.getAnnotation(zzaoy.class))) || field.isSynthetic()) {
            return true;
        }
        if ((!this.c && b(field.getType())) || a(field.getType())) {
            return true;
        }
        List<zzanx> list = z ? this.d : this.e;
        if (!list.isEmpty()) {
            zzany zzany = new zzany(field);
            for (zzanx zza : list) {
                if (zza.zza(zzany)) {
                    return true;
                }
            }
        }
        return false;
    }

    public zzapc zzg(int... iArr) {
        zzapc bh = clone();
        bh.b = 0;
        for (int i : iArr) {
            bh.b = i | bh.b;
        }
        return bh;
    }
}
