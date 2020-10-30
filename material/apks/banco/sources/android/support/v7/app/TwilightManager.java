package android.support.v7.app;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresPermission;
import android.support.annotation.VisibleForTesting;
import android.support.v4.content.PermissionChecker;
import android.util.Log;
import java.util.Calendar;

class TwilightManager {
    private static TwilightManager a;
    private final Context b;
    private final LocationManager c;
    private final TwilightState d = new TwilightState();

    static class TwilightState {
        boolean a;
        long b;
        long c;
        long d;
        long e;
        long f;

        TwilightState() {
        }
    }

    static TwilightManager a(@NonNull Context context) {
        if (a == null) {
            Context applicationContext = context.getApplicationContext();
            a = new TwilightManager(applicationContext, (LocationManager) applicationContext.getSystemService("location"));
        }
        return a;
    }

    @VisibleForTesting
    TwilightManager(@NonNull Context context, @NonNull LocationManager locationManager) {
        this.b = context;
        this.c = locationManager;
    }

    /* access modifiers changed from: 0000 */
    public boolean a() {
        TwilightState twilightState = this.d;
        if (c()) {
            return twilightState.a;
        }
        Location b2 = b();
        if (b2 != null) {
            a(b2);
            return twilightState.a;
        }
        Log.i("TwilightManager", "Could not get last known location. This is probably because the app does not have any location permissions. Falling back to hardcoded sunrise/sunset values.");
        int i = Calendar.getInstance().get(11);
        return i < 6 || i >= 22;
    }

    @SuppressLint({"MissingPermission"})
    private Location b() {
        Location location = null;
        Location a2 = PermissionChecker.checkSelfPermission(this.b, "android.permission.ACCESS_COARSE_LOCATION") == 0 ? a("network") : null;
        if (PermissionChecker.checkSelfPermission(this.b, "android.permission.ACCESS_FINE_LOCATION") == 0) {
            location = a("gps");
        }
        if (location == null || a2 == null) {
            if (location != null) {
                a2 = location;
            }
            return a2;
        }
        if (location.getTime() > a2.getTime()) {
            a2 = location;
        }
        return a2;
    }

    @RequiresPermission(anyOf = {"android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"})
    private Location a(String str) {
        try {
            if (this.c.isProviderEnabled(str)) {
                return this.c.getLastKnownLocation(str);
            }
        } catch (Exception e) {
            Log.d("TwilightManager", "Failed to get last known location", e);
        }
        return null;
    }

    private boolean c() {
        return this.d.f > System.currentTimeMillis();
    }

    private void a(@NonNull Location location) {
        long j;
        TwilightState twilightState = this.d;
        long currentTimeMillis = System.currentTimeMillis();
        TwilightCalculator a2 = TwilightCalculator.a();
        TwilightCalculator twilightCalculator = a2;
        twilightCalculator.a(currentTimeMillis - 86400000, location.getLatitude(), location.getLongitude());
        long j2 = a2.a;
        twilightCalculator.a(currentTimeMillis, location.getLatitude(), location.getLongitude());
        boolean z = a2.c == 1;
        long j3 = a2.b;
        long j4 = j2;
        long j5 = a2.a;
        TwilightState twilightState2 = twilightState;
        long j6 = j3;
        boolean z2 = z;
        a2.a(currentTimeMillis + 86400000, location.getLatitude(), location.getLongitude());
        long j7 = a2.b;
        if (j6 == -1 || j5 == -1) {
            j = currentTimeMillis + 43200000;
        } else {
            long j8 = currentTimeMillis > j5 ? j7 + 0 : currentTimeMillis > j6 ? j5 + 0 : j6 + 0;
            j = j8 + 60000;
        }
        TwilightState twilightState3 = twilightState2;
        twilightState3.a = z2;
        twilightState3.b = j4;
        twilightState3.c = j6;
        twilightState3.d = j5;
        twilightState3.e = j7;
        twilightState3.f = j;
    }
}
