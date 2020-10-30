package com.google.android.gms.tagmanager;

import android.content.Context;
import com.google.android.gms.internal.zzah.zzj;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

class zzcu implements zze {
    /* access modifiers changed from: private */
    public final String a;
    /* access modifiers changed from: private */
    public final Context b;
    private final ScheduledExecutorService c;
    private final zza d;
    private ScheduledFuture<?> e;
    private boolean f;
    private zzt g;
    private String h;
    private zzbn<zzj> i;

    interface zza {
        zzct a(zzt zzt);
    }

    interface zzb {
        ScheduledExecutorService a();
    }

    public zzcu(Context context, String str, zzt zzt) {
        this(context, str, zzt, null, null);
    }

    zzcu(Context context, String str, zzt zzt, zzb zzb2, zza zza2) {
        this.g = zzt;
        this.b = context;
        this.a = str;
        if (zzb2 == null) {
            zzb2 = new zzb() {
                public ScheduledExecutorService a() {
                    return Executors.newSingleThreadScheduledExecutor();
                }
            };
        }
        this.c = zzb2.a();
        if (zza2 == null) {
            this.d = new zza() {
                public zzct a(zzt zzt) {
                    return new zzct(zzcu.this.b, zzcu.this.a, zzt);
                }
            };
        } else {
            this.d = zza2;
        }
    }

    private synchronized void a() {
        if (this.f) {
            throw new IllegalStateException("called method after closed");
        }
    }

    private zzct b(String str) {
        zzct a2 = this.d.a(this.g);
        a2.a(this.i);
        a2.a(this.h);
        a2.b(str);
        return a2;
    }

    public synchronized void a(long j, String str) {
        String str2 = this.a;
        StringBuilder sb = new StringBuilder(String.valueOf(str2).length() + 55);
        sb.append("loadAfterDelay: containerId=");
        sb.append(str2);
        sb.append(" delay=");
        sb.append(j);
        zzbo.v(sb.toString());
        a();
        if (this.i == null) {
            throw new IllegalStateException("callback must be set before loadAfterDelay() is called.");
        }
        if (this.e != null) {
            this.e.cancel(false);
        }
        this.e = this.c.schedule(b(str), j, TimeUnit.MILLISECONDS);
    }

    public synchronized void a(zzbn<zzj> zzbn) {
        a();
        this.i = zzbn;
    }

    public synchronized void a(String str) {
        a();
        this.h = str;
    }

    public synchronized void release() {
        a();
        if (this.e != null) {
            this.e.cancel(false);
        }
        this.c.shutdown();
        this.f = true;
    }
}
