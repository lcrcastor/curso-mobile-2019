package com.google.android.gms.analytics;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.RequiresPermission;
import android.text.TextUtils;
import com.google.android.gms.analytics.internal.zzaf;
import com.google.android.gms.analytics.internal.zzao;
import com.google.android.gms.analytics.internal.zzf;
import com.google.android.gms.common.internal.zzac;
import com.google.android.gms.internal.zzxb;

public class CampaignTrackingReceiver extends BroadcastReceiver {
    static Object a = new Object();
    static zzxb b;
    static Boolean c;

    public static boolean zzaw(Context context) {
        zzac.zzy(context);
        if (c != null) {
            return c.booleanValue();
        }
        boolean zza = zzao.zza(context, "com.google.android.gms.analytics.CampaignTrackingReceiver", true);
        c = Boolean.valueOf(zza);
        return zza;
    }

    @RequiresPermission(allOf = {"android.permission.INTERNET", "android.permission.ACCESS_NETWORK_STATE"})
    public void onReceive(Context context, Intent intent) {
        zzf zzaz = zzf.zzaz(context);
        zzaf zzaao = zzaz.zzaao();
        if (intent == null) {
            zzaao.zzes("CampaignTrackingReceiver received null intent");
            return;
        }
        String stringExtra = intent.getStringExtra("referrer");
        String action = intent.getAction();
        zzaao.zza("CampaignTrackingReceiver received", action);
        if (!"com.android.vending.INSTALL_REFERRER".equals(action) || TextUtils.isEmpty(stringExtra)) {
            zzaao.zzes("CampaignTrackingReceiver received unexpected intent without referrer extra");
            return;
        }
        boolean zzax = CampaignTrackingService.zzax(context);
        if (!zzax) {
            zzaao.zzes("CampaignTrackingService not registered or disabled. Installation tracking not possible. See http://goo.gl/8Rd3yj for instructions.");
        }
        zzo(context, stringExtra);
        if (zzaz.zzaap().zzact()) {
            zzaao.zzet("Received unexpected installation campaign on package side");
            return;
        }
        Class zzxm = zzxm();
        zzac.zzy(zzxm);
        Intent intent2 = new Intent(context, zzxm);
        intent2.putExtra("referrer", stringExtra);
        synchronized (a) {
            context.startService(intent2);
            if (zzax) {
                try {
                    if (b == null) {
                        b = new zzxb(context, 1, "Analytics campaign WakeLock");
                        b.setReferenceCounted(false);
                    }
                    b.acquire(1000);
                } catch (SecurityException unused) {
                    zzaao.zzes("CampaignTrackingService service at risk of not starting. For more reliable installation campaign reports, add the WAKE_LOCK permission to your manifest. See http://goo.gl/8Rd3yj for instructions.");
                }
            }
        }
    }

    public void zzo(Context context, String str) {
    }

    public Class<? extends CampaignTrackingService> zzxm() {
        return CampaignTrackingService.class;
    }
}
