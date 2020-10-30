package com.google.android.gms.tagmanager;

import android.content.Context;
import android.os.Process;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.ads.identifier.AdvertisingIdClient.Info;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.util.zze;
import com.google.android.gms.common.util.zzh;
import java.io.IOException;

public class zza {
    private static Object l = new Object();
    private static zza m;
    private volatile long a;
    private volatile long b;
    private volatile boolean c;
    private volatile Info d;
    private volatile long e;
    private volatile long f;
    /* access modifiers changed from: private */
    public final Context g;
    private final zze h;
    private final Thread i;
    private final Object j;
    private C0095zza k;

    /* renamed from: com.google.android.gms.tagmanager.zza$zza reason: collision with other inner class name */
    public interface C0095zza {
        Info zzcdr();
    }

    private zza(Context context) {
        this(context, null, zzh.zzaxj());
    }

    public zza(Context context, C0095zza zza, zze zze) {
        this.a = 900000;
        this.b = 30000;
        this.c = false;
        this.j = new Object();
        this.k = new C0095zza() {
            public Info zzcdr() {
                String str;
                try {
                    return AdvertisingIdClient.getAdvertisingIdInfo(zza.this.g);
                } catch (IllegalStateException e) {
                    e = e;
                    str = "IllegalStateException getting Advertising Id Info";
                    zzbo.zzd(str, e);
                    return null;
                } catch (GooglePlayServicesRepairableException e2) {
                    e = e2;
                    str = "GooglePlayServicesRepairableException getting Advertising Id Info";
                    zzbo.zzd(str, e);
                    return null;
                } catch (IOException e3) {
                    e = e3;
                    str = "IOException getting Ad Id Info";
                    zzbo.zzd(str, e);
                    return null;
                } catch (GooglePlayServicesNotAvailableException e4) {
                    e = e4;
                    str = "GooglePlayServicesNotAvailableException getting Advertising Id Info";
                    zzbo.zzd(str, e);
                    return null;
                } catch (Exception e5) {
                    e = e5;
                    str = "Unknown exception. Could not get the Advertising Id Info.";
                    zzbo.zzd(str, e);
                    return null;
                }
            }
        };
        this.h = zze;
        if (context != null) {
            context = context.getApplicationContext();
        }
        this.g = context;
        if (zza != null) {
            this.k = zza;
        }
        this.e = this.h.currentTimeMillis();
        this.i = new Thread(new Runnable() {
            public void run() {
                zza.this.d();
            }
        });
    }

    /* JADX WARNING: Exception block dominator not found, dom blocks: [] */
    /* JADX WARNING: Missing exception handler attribute for start block: B:4:0x000c */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void a() {
        /*
            r2 = this;
            monitor-enter(r2)
            r2.b()     // Catch:{ InterruptedException -> 0x000c }
            r0 = 500(0x1f4, double:2.47E-321)
            r2.wait(r0)     // Catch:{ InterruptedException -> 0x000c }
            goto L_0x000c
        L_0x000a:
            r0 = move-exception
            goto L_0x000e
        L_0x000c:
            monitor-exit(r2)     // Catch:{ all -> 0x000a }
            return
        L_0x000e:
            monitor-exit(r2)     // Catch:{ all -> 0x000a }
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.tagmanager.zza.a():void");
    }

    private void b() {
        if (this.h.currentTimeMillis() - this.e > this.b) {
            synchronized (this.j) {
                this.j.notify();
            }
            this.e = this.h.currentTimeMillis();
        }
    }

    private void c() {
        if (this.h.currentTimeMillis() - this.f > 3600000) {
            this.d = null;
        }
    }

    /* access modifiers changed from: private */
    public void d() {
        Process.setThreadPriority(10);
        while (!this.c) {
            Info zzcdr = this.k.zzcdr();
            if (zzcdr != null) {
                this.d = zzcdr;
                this.f = this.h.currentTimeMillis();
                zzbo.zzde("Obtained fresh AdvertisingId info from GmsCore.");
            }
            synchronized (this) {
                notifyAll();
            }
            try {
                synchronized (this.j) {
                    this.j.wait(this.a);
                }
            } catch (InterruptedException unused) {
                zzbo.zzde("sleep interrupted in AdvertiserDataPoller thread; continuing");
            }
        }
    }

    public static zza zzdz(Context context) {
        if (m == null) {
            synchronized (l) {
                if (m == null) {
                    m = new zza(context);
                    m.start();
                }
            }
        }
        return m;
    }

    public boolean isLimitAdTrackingEnabled() {
        if (this.d == null) {
            a();
        } else {
            b();
        }
        c();
        if (this.d == null) {
            return true;
        }
        return this.d.isLimitAdTrackingEnabled();
    }

    public void start() {
        this.i.start();
    }

    public String zzcdm() {
        if (this.d == null) {
            a();
        } else {
            b();
        }
        c();
        if (this.d == null) {
            return null;
        }
        return this.d.getId();
    }
}
