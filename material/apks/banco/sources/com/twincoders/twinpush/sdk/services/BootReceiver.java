package com.twincoders.twinpush.sdk.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.twincoders.twinpush.sdk.DefaultTwinPushSDK;
import com.twincoders.twinpush.sdk.TwinPushSDK;
import com.twincoders.twinpush.sdk.logging.Ln;

public class BootReceiver extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent) {
        Ln.i("Received boot intent", new Object[0]);
        TwinPushSDK instance = TwinPushSDK.getInstance(context);
        if (instance.isMonitoringLocationChanges()) {
            ((DefaultTwinPushSDK) instance).registerForLocationUpdates();
        }
    }
}
