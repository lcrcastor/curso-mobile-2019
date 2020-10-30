package com.google.android.gms.analytics.internal;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.RequiresPermission;
import com.google.android.gms.common.internal.zzac;
import com.google.android.gms.internal.zzxb;

public final class zzaj {
    static Object a = new Object();
    static zzxb b;
    static Boolean c;

    public static boolean zzaw(Context context) {
        zzac.zzy(context);
        if (c != null) {
            return c.booleanValue();
        }
        boolean zza = zzao.zza(context, "com.google.android.gms.analytics.AnalyticsReceiver", false);
        c = Boolean.valueOf(zza);
        return zza;
    }

    @RequiresPermission(allOf = {"android.permission.INTERNET", "android.permission.ACCESS_NETWORK_STATE"})
    public void onReceive(Context context, Intent intent) {
        zzf zzaz = zzf.zzaz(context);
        zzaf zzaao = zzaz.zzaao();
        if (intent == null) {
            zzaao.zzes("AnalyticsReceiver called with null intent");
            return;
        }
        String action = intent.getAction();
        zzaao.zza(zzaz.zzaap().zzact() ? "Device AnalyticsReceiver got" : "Local AnalyticsReceiver got", action);
        if ("com.google.android.gms.analytics.ANALYTICS_DISPATCH".equals(action)) {
            boolean zzax = zzak.zzax(context);
            Intent intent2 = new Intent("com.google.android.gms.analytics.ANALYTICS_DISPATCH");
            intent2.setComponent(new ComponentName(context, "com.google.android.gms.analytics.AnalyticsService"));
            intent2.setAction("com.google.android.gms.analytics.ANALYTICS_DISPATCH");
            synchronized (a) {
                context.startService(intent2);
                if (zzax) {
                    try {
                        if (b == null) {
                            b = new zzxb(context, 1, "Analytics WakeLock");
                            b.setReferenceCounted(false);
                        }
                        b.acquire(1000);
                    } catch (SecurityException unused) {
                        zzaao.zzes("Analytics service at risk of not starting. For more reliable analytics, add the WAKE_LOCK permission to your manifest. See http://goo.gl/8Rd3yj for instructions.");
                    }
                }
            }
        }
    }
}
