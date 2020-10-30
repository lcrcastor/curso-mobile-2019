package com.twincoders.twinpush.sdk.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import com.twincoders.twinpush.sdk.TwinPushSDK;
import com.twincoders.twinpush.sdk.logging.Ln;
import com.twincoders.twinpush.sdk.util.LastLocationFinder;

public class LocationChangeReceiver extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent) {
        Location location;
        Ln.d("Location update received", new Object[0]);
        TwinPushSDK instance = TwinPushSDK.getInstance(context);
        String str = "location";
        if (intent.hasExtra(str)) {
            location = (Location) intent.getExtras().get(str);
        } else {
            location = new LastLocationFinder(context).getLastBestLocation(instance.getLocationMinUpdateDistance(), System.currentTimeMillis() - instance.getLocationMinUpdateTime());
        }
        if (location != null) {
            instance.setLocation(location);
        }
    }
}
