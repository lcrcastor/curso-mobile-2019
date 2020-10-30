package com.google.android.gms.analytics.internal;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import com.google.android.gms.analytics.zzi;
import com.google.android.gms.common.internal.zzac;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class zzb extends zzd {
    /* access modifiers changed from: private */
    public final zzl a;

    public zzb(zzf zzf, zzg zzg) {
        super(zzf);
        zzac.zzy(zzg);
        this.a = zzg.a(zzf);
    }

    /* access modifiers changed from: 0000 */
    public void a() {
        zzyl();
        this.a.d();
    }

    /* access modifiers changed from: 0000 */
    public void b() {
        zzyl();
        this.a.c();
    }

    public void setLocalDispatchPeriod(final int i) {
        zzaax();
        zzb("setLocalDispatchPeriod (sec)", Integer.valueOf(i));
        zzaaq().zzg(new Runnable() {
            public void run() {
                zzb.this.a.a(((long) i) * 1000);
            }
        });
    }

    public void start() {
        this.a.a();
    }

    public long zza(zzh zzh) {
        zzaax();
        zzac.zzy(zzh);
        zzyl();
        long a2 = this.a.a(zzh, true);
        if (a2 == 0) {
            this.a.a(zzh);
        }
        return a2;
    }

    public void zza(final zzab zzab) {
        zzac.zzy(zzab);
        zzaax();
        zzb("Hit delivery requested", zzab);
        zzaaq().zzg(new Runnable() {
            public void run() {
                zzb.this.a.a(zzab);
            }
        });
    }

    public void zza(final zzw zzw) {
        zzaax();
        zzaaq().zzg(new Runnable() {
            public void run() {
                zzb.this.a.a(zzw);
            }
        });
    }

    public void zza(final String str, final Runnable runnable) {
        zzac.zzh(str, "campaign param can't be empty");
        zzaaq().zzg(new Runnable() {
            public void run() {
                zzb.this.a.a(str);
                if (runnable != null) {
                    runnable.run();
                }
            }
        });
    }

    public void zzaaf() {
        zzaax();
        zzaam();
        zzaaq().zzg(new Runnable() {
            public void run() {
                zzb.this.a.g();
            }
        });
    }

    public void zzaag() {
        zzaax();
        Context context = getContext();
        if (!zzaj.zzaw(context) || !zzak.zzax(context)) {
            zza((zzw) null);
            return;
        }
        Intent intent = new Intent("com.google.android.gms.analytics.ANALYTICS_DISPATCH");
        intent.setComponent(new ComponentName(context, "com.google.android.gms.analytics.AnalyticsService"));
        context.startService(intent);
    }

    public boolean zzaah() {
        zzaax();
        try {
            zzaaq().zzc(new Callable<Void>() {
                /* renamed from: a */
                public Void call() {
                    zzb.this.a.j();
                    return null;
                }
            }).get(4, TimeUnit.SECONDS);
            return true;
        } catch (InterruptedException e) {
            zzd("syncDispatchLocalHits interrupted", e);
            return false;
        } catch (ExecutionException e2) {
            zze("syncDispatchLocalHits failed", e2);
            return false;
        } catch (TimeoutException e3) {
            zzd("syncDispatchLocalHits timed out", e3);
            return false;
        }
    }

    public void zzaai() {
        zzaax();
        zzi.zzyl();
        this.a.e();
    }

    public void zzaaj() {
        zzep("Radio powered up");
        zzaag();
    }

    public void zzav(final boolean z) {
        zza("Network connectivity status changed", Boolean.valueOf(z));
        zzaaq().zzg(new Runnable() {
            public void run() {
                zzb.this.a.a(z);
            }
        });
    }

    /* access modifiers changed from: protected */
    public void zzym() {
        this.a.initialize();
    }
}
