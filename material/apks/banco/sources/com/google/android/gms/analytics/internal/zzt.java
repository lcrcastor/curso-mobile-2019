package com.google.android.gms.analytics.internal;

import android.os.Handler;
import android.os.Looper;
import com.google.android.gms.common.internal.zzac;

abstract class zzt {
    private static volatile Handler b;
    /* access modifiers changed from: private */
    public final zzf a;
    private final Runnable c = new Runnable() {
        public void run() {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                zzt.this.a.zzaaq().zzg(this);
                return;
            }
            boolean c = zzt.this.c();
            zzt.this.d = 0;
            if (c && !zzt.b(zzt.this)) {
                zzt.this.a();
            }
        }
    };
    /* access modifiers changed from: private */
    public volatile long d;

    zzt(zzf zzf) {
        zzac.zzy(zzf);
        this.a = zzf;
    }

    static /* synthetic */ boolean b(zzt zzt) {
        return false;
    }

    private Handler e() {
        Handler handler;
        if (b != null) {
            return b;
        }
        synchronized (zzt.class) {
            if (b == null) {
                b = new Handler(this.a.getContext().getMainLooper());
            }
            handler = b;
        }
        return handler;
    }

    public abstract void a();

    public void a(long j) {
        d();
        if (j >= 0) {
            this.d = this.a.zzaan().currentTimeMillis();
            if (!e().postDelayed(this.c, j)) {
                this.a.zzaao().zze("Failed to schedule delayed post. time", Long.valueOf(j));
            }
        }
    }

    public long b() {
        if (this.d == 0) {
            return 0;
        }
        return Math.abs(this.a.zzaan().currentTimeMillis() - this.d);
    }

    public void b(long j) {
        if (c()) {
            long j2 = 0;
            if (j < 0) {
                d();
                return;
            }
            long abs = j - Math.abs(this.a.zzaan().currentTimeMillis() - this.d);
            if (abs >= 0) {
                j2 = abs;
            }
            e().removeCallbacks(this.c);
            if (!e().postDelayed(this.c, j2)) {
                this.a.zzaao().zze("Failed to adjust delayed post. time", Long.valueOf(j2));
            }
        }
    }

    public boolean c() {
        return this.d != 0;
    }

    public void d() {
        this.d = 0;
        e().removeCallbacks(this.c);
    }
}
