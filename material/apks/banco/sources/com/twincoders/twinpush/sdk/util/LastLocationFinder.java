package com.twincoders.twinpush.sdk.util;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build.VERSION;
import android.support.v4.app.ActivityCompat;
import com.twincoders.twinpush.sdk.logging.Ln;
import java.util.Iterator;

public class LastLocationFinder {
    protected static String SINGLE_LOCATION_UPDATE_ACTION = "com.twincoders.twinpush.sdk.SINGLE_LOCATION_UPDATE_ACTION";
    protected Context context;
    protected Criteria criteria;
    protected LocationListener locationListener;
    protected LocationManager locationManager;
    protected PendingIntent singleUpatePI;
    protected BroadcastReceiver singleUpdateReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            context.unregisterReceiver(LastLocationFinder.this.singleUpdateReceiver);
            Location location = (Location) intent.getExtras().get("location");
            if (!(LastLocationFinder.this.locationListener == null || location == null)) {
                LastLocationFinder.this.locationListener.onLocationChanged(location);
            }
            LastLocationFinder.this.locationManager.removeUpdates(LastLocationFinder.this.singleUpatePI);
        }
    };

    public LastLocationFinder(Context context2) {
        this.context = context2;
        this.locationManager = (LocationManager) context2.getSystemService("location");
        this.criteria = new Criteria();
        this.criteria.setAccuracy(1);
        this.singleUpatePI = PendingIntent.getBroadcast(context2, 0, new Intent(SINGLE_LOCATION_UPDATE_ACTION), 134217728);
    }

    public Location getLastBestLocation(int i, long j) {
        Iterator it = this.locationManager.getAllProviders().iterator();
        long j2 = Long.MIN_VALUE;
        Location location = null;
        float f = Float.MAX_VALUE;
        while (true) {
            boolean z = true;
            if (it.hasNext()) {
                String str = (String) it.next();
                if (ActivityCompat.checkSelfPermission(this.context, "android.permission.ACCESS_FINE_LOCATION") == 0 || ActivityCompat.checkSelfPermission(this.context, "android.permission.ACCESS_COARSE_LOCATION") == 0) {
                    z = false;
                }
                if (z) {
                    Ln.e("Missing required ACCESS_FINE_LOCATION permission to update location", new Object[0]);
                    return null;
                }
                Location lastKnownLocation = this.locationManager.getLastKnownLocation(str);
                if (lastKnownLocation != null) {
                    float accuracy = lastKnownLocation.getAccuracy();
                    long time = lastKnownLocation.getTime();
                    if (time > j && accuracy < f) {
                        location = lastKnownLocation;
                        f = accuracy;
                    } else if (time < j && f == Float.MAX_VALUE && time > j2) {
                        location = lastKnownLocation;
                    }
                    j2 = time;
                }
            } else {
                if (VERSION.SDK_INT >= 9 && this.locationListener != null && (j2 < j || f > ((float) i))) {
                    this.context.registerReceiver(this.singleUpdateReceiver, new IntentFilter(SINGLE_LOCATION_UPDATE_ACTION));
                    try {
                        this.locationManager.requestSingleUpdate(this.criteria, this.singleUpatePI);
                    } catch (Exception e) {
                        Ln.e("Error while trying to update location: %s", e.getLocalizedMessage());
                    }
                }
                return location;
            }
        }
    }

    public void setChangedLocationListener(LocationListener locationListener2) {
        this.locationListener = locationListener2;
    }

    public void cancel() {
        this.locationManager.removeUpdates(this.singleUpatePI);
    }
}
