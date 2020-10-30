package com.google.android.gms.analytics;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.RequiresPermission;
import com.google.android.gms.analytics.internal.zzaj;

public final class AnalyticsReceiver extends BroadcastReceiver {
    private zzaj a;

    private zzaj a() {
        if (this.a == null) {
            this.a = new zzaj();
        }
        return this.a;
    }

    @RequiresPermission(allOf = {"android.permission.INTERNET", "android.permission.ACCESS_NETWORK_STATE"})
    public void onReceive(Context context, Intent intent) {
        a().onReceive(context, intent);
    }
}
