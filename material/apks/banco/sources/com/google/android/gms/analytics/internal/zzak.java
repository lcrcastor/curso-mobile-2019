package com.google.android.gms.analytics.internal;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.RequiresPermission;
import com.google.android.gms.common.internal.zzac;
import com.google.android.gms.internal.zzxb;

public final class zzak {
    private static Boolean d;
    /* access modifiers changed from: private */
    public final Handler a = new Handler();
    /* access modifiers changed from: private */
    public final zza b;
    private final Context c;

    public interface zza {
        boolean callServiceStopSelfResult(int i);

        Context getContext();
    }

    public zzak(zza zza2) {
        this.c = zza2.getContext();
        zzac.zzy(this.c);
        this.b = zza2;
    }

    private void a() {
        try {
            synchronized (zzaj.a) {
                zzxb zzxb = zzaj.b;
                if (zzxb != null && zzxb.isHeld()) {
                    zzxb.release();
                }
            }
        } catch (SecurityException unused) {
        }
    }

    public static boolean zzax(Context context) {
        zzac.zzy(context);
        if (d != null) {
            return d.booleanValue();
        }
        boolean zzq = zzao.zzq(context, "com.google.android.gms.analytics.AnalyticsService");
        d = Boolean.valueOf(zzq);
        return zzq;
    }

    public IBinder onBind(Intent intent) {
        return null;
    }

    @RequiresPermission(allOf = {"android.permission.INTERNET", "android.permission.ACCESS_NETWORK_STATE"})
    public void onCreate() {
        zzf zzaz = zzf.zzaz(this.c);
        zzaz.zzaao().zzep(zzaz.zzaap().zzact() ? "Device AnalyticsService is starting up" : "Local AnalyticsService is starting up");
    }

    @RequiresPermission(allOf = {"android.permission.INTERNET", "android.permission.ACCESS_NETWORK_STATE"})
    public void onDestroy() {
        zzf zzaz = zzf.zzaz(this.c);
        zzaz.zzaao().zzep(zzaz.zzaap().zzact() ? "Device AnalyticsService is shutting down" : "Local AnalyticsService is shutting down");
    }

    @RequiresPermission(allOf = {"android.permission.INTERNET", "android.permission.ACCESS_NETWORK_STATE"})
    public int onStartCommand(Intent intent, int i, final int i2) {
        a();
        final zzf zzaz = zzf.zzaz(this.c);
        final zzaf zzaao = zzaz.zzaao();
        if (intent == null) {
            zzaao.zzes("AnalyticsService started with null intent");
            return 2;
        }
        String action = intent.getAction();
        zzaao.zza(zzaz.zzaap().zzact() ? "Device AnalyticsService called. startId, action" : "Local AnalyticsService called. startId, action", Integer.valueOf(i2), action);
        if ("com.google.android.gms.analytics.ANALYTICS_DISPATCH".equals(action)) {
            zzaz.zzxu().zza((zzw) new zzw() {
                public void zzf(Throwable th) {
                    zzak.this.a.post(new Runnable() {
                        public void run() {
                            zzaf zzaf;
                            String str;
                            if (zzak.this.b.callServiceStopSelfResult(i2)) {
                                if (zzaz.zzaap().zzact()) {
                                    zzaf = zzaao;
                                    str = "Device AnalyticsService processed last dispatch request";
                                } else {
                                    zzaf = zzaao;
                                    str = "Local AnalyticsService processed last dispatch request";
                                }
                                zzaf.zzep(str);
                            }
                        }
                    });
                }
            });
        }
        return 2;
    }
}
