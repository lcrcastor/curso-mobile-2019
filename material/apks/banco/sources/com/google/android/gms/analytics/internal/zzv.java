package com.google.android.gms.analytics.internal;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.support.v4.app.NotificationCompat;
import com.google.android.gms.common.internal.zzac;

public class zzv extends zzd {
    private boolean a;
    private boolean b;
    private AlarmManager c = ((AlarmManager) getContext().getSystemService(NotificationCompat.CATEGORY_ALARM));

    protected zzv(zzf zzf) {
        super(zzf);
    }

    private PendingIntent a() {
        Intent intent = new Intent("com.google.android.gms.analytics.ANALYTICS_DISPATCH");
        intent.setComponent(new ComponentName(getContext(), "com.google.android.gms.analytics.AnalyticsReceiver"));
        return PendingIntent.getBroadcast(getContext(), 0, intent, 0);
    }

    public void cancel() {
        zzaax();
        this.b = false;
        this.c.cancel(a());
    }

    public void schedule() {
        zzaax();
        zzac.zza(zzaed(), (Object) "Receiver not registered");
        long zzadc = zzaap().zzadc();
        if (zzadc > 0) {
            cancel();
            long elapsedRealtime = zzaan().elapsedRealtime() + zzadc;
            this.b = true;
            this.c.setInexactRepeating(2, elapsedRealtime, 0, a());
        }
    }

    public boolean zzaed() {
        return this.a;
    }

    public boolean zzfl() {
        return this.b;
    }

    /* access modifiers changed from: protected */
    public void zzym() {
        try {
            this.c.cancel(a());
            if (zzaap().zzadc() > 0) {
                ActivityInfo receiverInfo = getContext().getPackageManager().getReceiverInfo(new ComponentName(getContext(), "com.google.android.gms.analytics.AnalyticsReceiver"), 2);
                if (receiverInfo != null && receiverInfo.enabled) {
                    zzep("Receiver registered. Using alarm for local dispatch.");
                    this.a = true;
                }
            }
        } catch (NameNotFoundException unused) {
        }
    }
}
