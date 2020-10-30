package com.google.android.gms.analytics;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.RequiresPermission;
import android.text.TextUtils;
import com.google.android.gms.analytics.internal.zzaf;
import com.google.android.gms.analytics.internal.zzao;
import com.google.android.gms.analytics.internal.zzf;
import com.google.android.gms.common.internal.zzac;
import com.google.android.gms.internal.zzxb;

public class CampaignTrackingService extends Service {
    private static Boolean b;
    private Handler a;

    private void a() {
        try {
            synchronized (CampaignTrackingReceiver.a) {
                zzxb zzxb = CampaignTrackingReceiver.b;
                if (zzxb != null && zzxb.isHeld()) {
                    zzxb.release();
                }
            }
        } catch (SecurityException unused) {
        }
    }

    private Handler b() {
        Handler handler = this.a;
        if (handler != null) {
            return handler;
        }
        Handler handler2 = new Handler(getMainLooper());
        this.a = handler2;
        return handler2;
    }

    public static boolean zzax(Context context) {
        zzac.zzy(context);
        if (b != null) {
            return b.booleanValue();
        }
        boolean zzq = zzao.zzq(context, "com.google.android.gms.analytics.CampaignTrackingService");
        b = Boolean.valueOf(zzq);
        return zzq;
    }

    public IBinder onBind(Intent intent) {
        return null;
    }

    @RequiresPermission(allOf = {"android.permission.INTERNET", "android.permission.ACCESS_NETWORK_STATE"})
    public void onCreate() {
        super.onCreate();
        zzf.zzaz(this).zzaao().zzep("CampaignTrackingService is starting up");
    }

    @RequiresPermission(allOf = {"android.permission.INTERNET", "android.permission.ACCESS_NETWORK_STATE"})
    public void onDestroy() {
        zzf.zzaz(this).zzaao().zzep("CampaignTrackingService is shutting down");
        super.onDestroy();
    }

    @RequiresPermission(allOf = {"android.permission.INTERNET", "android.permission.ACCESS_NETWORK_STATE"})
    public int onStartCommand(Intent intent, int i, final int i2) {
        String str;
        a();
        zzf zzaz = zzf.zzaz(this);
        final zzaf zzaao = zzaz.zzaao();
        if (zzaz.zzaap().zzact()) {
            zzaao.zzet("Unexpected installation campaign (package side)");
            str = null;
        } else {
            str = intent.getStringExtra("referrer");
        }
        final Handler b2 = b();
        if (TextUtils.isEmpty(str)) {
            if (!zzaz.zzaap().zzact()) {
                zzaao.zzes("No campaign found on com.android.vending.INSTALL_REFERRER \"referrer\" extra");
            }
            zzaz.zzaaq().zzg(new Runnable() {
                public void run() {
                    CampaignTrackingService.this.zza(zzaao, b2, i2);
                }
            });
            return 2;
        }
        int zzacx = zzaz.zzaap().zzacx();
        if (str.length() > zzacx) {
            zzaao.zzc("Campaign data exceed the maximum supported size and will be clipped. size, limit", Integer.valueOf(str.length()), Integer.valueOf(zzacx));
            str = str.substring(0, zzacx);
        }
        zzaao.zza("CampaignTrackingService called. startId, campaign", Integer.valueOf(i2), str);
        zzaz.zzxu().zza(str, new Runnable() {
            public void run() {
                CampaignTrackingService.this.zza(zzaao, b2, i2);
            }
        });
        return 2;
    }

    /* access modifiers changed from: protected */
    public void zza(final zzaf zzaf, Handler handler, final int i) {
        handler.post(new Runnable() {
            public void run() {
                boolean stopSelfResult = CampaignTrackingService.this.stopSelfResult(i);
                if (stopSelfResult) {
                    zzaf.zza("Install campaign broadcast processed", Boolean.valueOf(stopSelfResult));
                }
            }
        });
    }
}
