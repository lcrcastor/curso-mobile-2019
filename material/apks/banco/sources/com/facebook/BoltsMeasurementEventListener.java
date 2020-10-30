package com.facebook;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import bolts.MeasurementEvent;

public class BoltsMeasurementEventListener extends BroadcastReceiver {
    private static BoltsMeasurementEventListener a;
    private Context b;

    private BoltsMeasurementEventListener(Context context) {
        this.b = context.getApplicationContext();
    }

    private void a() {
        LocalBroadcastManager.getInstance(this.b).registerReceiver(this, new IntentFilter(MeasurementEvent.MEASUREMENT_EVENT_NOTIFICATION_NAME));
    }

    private void b() {
        LocalBroadcastManager.getInstance(this.b).unregisterReceiver(this);
    }

    static BoltsMeasurementEventListener a(Context context) {
        if (a != null) {
            return a;
        }
        a = new BoltsMeasurementEventListener(context);
        a.a();
        return a;
    }

    /* access modifiers changed from: protected */
    public void finalize() {
        try {
            b();
        } finally {
            super.finalize();
        }
    }

    public void onReceive(Context context, Intent intent) {
        AppEventsLogger newLogger = AppEventsLogger.newLogger(context);
        StringBuilder sb = new StringBuilder();
        sb.append("bf_");
        sb.append(intent.getStringExtra(MeasurementEvent.MEASUREMENT_EVENT_NAME_KEY));
        String sb2 = sb.toString();
        Bundle bundleExtra = intent.getBundleExtra(MeasurementEvent.MEASUREMENT_EVENT_ARGS_KEY);
        Bundle bundle = new Bundle();
        for (String str : bundleExtra.keySet()) {
            bundle.putString(str.replaceAll("[^0-9a-zA-Z _-]", "-").replaceAll("^[ -]*", "").replaceAll("[ -]*$", ""), (String) bundleExtra.get(str));
        }
        newLogger.logEvent(sb2, bundle);
    }
}
