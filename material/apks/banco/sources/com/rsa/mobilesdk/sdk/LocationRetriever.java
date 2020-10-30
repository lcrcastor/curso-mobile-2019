package com.rsa.mobilesdk.sdk;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import java.lang.ref.WeakReference;

class LocationRetriever {
    private final int a = 1200;
    private final int b = 1201;
    private final int c = 30000;
    private final int d = 10;
    private LocationManager e = null;
    private LocationProvider f = null;
    private LocationProvider g = null;
    private Context h = null;
    private long i = 0;
    private long j = 0;
    private long k = 0;
    private int l = 0;
    private long m = 0;
    /* access modifiers changed from: private */
    public Location n = null;
    private LocationUpdateListener o = null;
    /* access modifiers changed from: private */
    public int p = 0;
    private OnLocationDataChangedListener q = null;
    private boolean r;
    private boolean s;
    private GeoLocationInfo t;
    private TimeoutHandler u = new TimeoutHandler(this);
    private SilenceExpiredHandler v = new SilenceExpiredHandler(this);

    class LocationUpdateListener implements LocationListener {
        public void onProviderDisabled(String str) {
        }

        public void onProviderEnabled(String str) {
        }

        public void onStatusChanged(String str, int i, Bundle bundle) {
        }

        private LocationUpdateListener() {
        }

        public void onLocationChanged(Location location) {
            Log.i("LocationRetriever", "onLocationChanged");
            LocationRetriever.this.a(location);
            if (LocationRetriever.this.c(LocationRetriever.this.n)) {
                LocationRetriever.this.e();
                LocationRetriever.this.i();
                LocationRetriever.this.j();
                LocationRetriever.this.p = 0;
            }
            LocationRetriever.this.d();
        }
    }

    interface OnLocationDataChangedListener {
        void a();
    }

    static class SilenceExpiredHandler extends Handler {
        private WeakReference<LocationRetriever> a;

        public SilenceExpiredHandler(LocationRetriever locationRetriever) {
            this.a = new WeakReference<>(locationRetriever);
        }

        public void handleMessage(Message message) {
            try {
                ((LocationRetriever) this.a.get()).g();
                super.handleMessage(message);
            } catch (Exception unused) {
                Log.d("LocationRetriever", "release mode, SEH skipping.");
            }
        }
    }

    static class TimeoutHandler extends Handler {
        private WeakReference<LocationRetriever> a;

        public TimeoutHandler(LocationRetriever locationRetriever) {
            this.a = new WeakReference<>(locationRetriever);
        }

        public void handleMessage(Message message) {
            try {
                ((LocationRetriever) this.a.get()).f();
                super.handleMessage(message);
            } catch (Exception unused) {
                Log.d("LocationRetriever", "release mode, TOH skipping.");
            }
        }
    }

    LocationRetriever() {
    }

    /* access modifiers changed from: 0000 */
    public void a(Context context, long j2, long j3, long j4, long j5, int i2, GeoLocationInfo geoLocationInfo, OnLocationDataChangedListener onLocationDataChangedListener) {
        Log.i("LocationRetriever", "queryLocation");
        if (geoLocationInfo == null) {
            Log.e("LocationRetriever", "mobile SDK: internal problem, queryLocation, null locationInfoBuffer");
            return;
        }
        this.p = 0;
        this.s = false;
        this.r = false;
        this.h = context;
        this.t = geoLocationInfo;
        this.q = onLocationDataChangedListener;
        this.e = (LocationManager) this.h.getSystemService("location");
        this.i = j2 * 60 * 1000;
        this.m = j3 * 60 * 1000;
        this.j = j4 * 60 * 1000;
        this.k = j5 * 24 * 3600 * 1000;
        this.l = i2;
        try {
            this.f = this.e.getProvider("gps");
        } catch (SecurityException unused) {
            this.s = true;
        }
        if (this.f != null) {
            if (!this.e.isProviderEnabled("gps")) {
                this.s = true;
            }
            try {
                a(this.e.getLastKnownLocation("gps"));
            } catch (SecurityException unused2) {
                this.s = true;
            }
        } else {
            this.p = 4;
        }
        try {
            this.g = this.e.getProvider("network");
            if (!this.e.isProviderEnabled("network")) {
                this.r = true;
            }
            a(this.e.getLastKnownLocation("network"));
        } catch (SecurityException unused3) {
            this.r = true;
        }
        if (this.s && this.r) {
            this.p = 1;
        }
        d();
        b();
    }

    private void b() {
        Log.i("LocationRetriever", "startLocationUpdate");
        boolean z = false;
        this.r = false;
        this.s = false;
        try {
            this.f = this.e.getProvider("gps");
        } catch (SecurityException unused) {
            this.s = true;
        }
        if (this.f != null) {
            if (this.e.isProviderEnabled("gps")) {
                this.e.requestLocationUpdates("gps", 30000, 10.0f, c(), Looper.getMainLooper());
                z = true;
            } else {
                this.s = true;
            }
        }
        try {
            this.g = this.e.getProvider("network");
        } catch (SecurityException unused2) {
            this.r = true;
        }
        if (this.g == null || !this.e.isProviderEnabled("network")) {
            this.r = true;
        } else {
            this.e.requestLocationUpdates("network", 30000, 10.0f, c(), Looper.getMainLooper());
            z = true;
        }
        if (this.s && this.r) {
            this.p = 1;
        }
        if (z) {
            h();
        }
    }

    private LocationUpdateListener c() {
        if (this.o == null) {
            this.o = new LocationUpdateListener();
        }
        return this.o;
    }

    /* access modifiers changed from: private */
    public void d() {
        Log.i("LocationRetriever", "putResult");
        this.t.a(this.n, this.p);
        if (this.q != null) {
            this.q.a();
        }
    }

    /* access modifiers changed from: private */
    public void e() {
        Log.i("LocationRetriever", "cancelAllRequests");
        if (this.e != null && this.o != null) {
            this.e.removeUpdates(this.o);
        }
    }

    /* access modifiers changed from: private */
    public synchronized void a(Location location) {
        this.n = a(this.n, location);
    }

    private boolean b(Location location) {
        boolean z = false;
        if (location == null) {
            return false;
        }
        if (System.currentTimeMillis() - location.getTime() < this.k) {
            z = true;
        }
        return z;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0052, code lost:
        if (r1 != false) goto L_0x0054;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private android.location.Location a(android.location.Location r9, android.location.Location r10) {
        /*
            r8 = this;
            boolean r0 = r8.b(r9)
            boolean r1 = r8.b(r10)
            r2 = 0
            if (r0 != 0) goto L_0x000f
            if (r1 != 0) goto L_0x000f
        L_0x000d:
            r9 = r2
            goto L_0x0055
        L_0x000f:
            if (r0 == 0) goto L_0x004f
            if (r1 == 0) goto L_0x004f
            long r0 = java.lang.System.currentTimeMillis()
            long r2 = r9.getTime()
            long r4 = r0 - r2
            long r2 = r10.getTime()
            long r6 = r0 - r2
            long r0 = r8.j
            int r2 = (r4 > r0 ? 1 : (r4 == r0 ? 0 : -1))
            if (r2 >= 0) goto L_0x003c
            long r0 = r8.j
            int r2 = (r6 > r0 ? 1 : (r6 == r0 ? 0 : -1))
            if (r2 >= 0) goto L_0x003c
            float r0 = r9.getAccuracy()
            float r1 = r10.getAccuracy()
            int r0 = (r0 > r1 ? 1 : (r0 == r1 ? 0 : -1))
            if (r0 >= 0) goto L_0x0054
            goto L_0x0055
        L_0x003c:
            long r0 = r8.j
            int r2 = (r4 > r0 ? 1 : (r4 == r0 ? 0 : -1))
            if (r2 >= 0) goto L_0x0043
            goto L_0x0055
        L_0x0043:
            long r0 = r8.j
            int r2 = (r6 > r0 ? 1 : (r6 == r0 ? 0 : -1))
            if (r2 >= 0) goto L_0x004a
            goto L_0x0054
        L_0x004a:
            int r0 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r0 >= 0) goto L_0x0054
            goto L_0x0055
        L_0x004f:
            if (r0 == 0) goto L_0x0052
            goto L_0x0055
        L_0x0052:
            if (r1 == 0) goto L_0x000d
        L_0x0054:
            r9 = r10
        L_0x0055:
            return r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.rsa.mobilesdk.sdk.LocationRetriever.a(android.location.Location, android.location.Location):android.location.Location");
    }

    /* access modifiers changed from: private */
    public boolean c(Location location) {
        boolean z = false;
        if (location == null || System.currentTimeMillis() - location.getTime() >= this.j) {
            return false;
        }
        if (location.getAccuracy() <= ((float) this.l)) {
            z = true;
        }
        return z;
    }

    /* access modifiers changed from: private */
    public void f() {
        Log.i("LocationRetriever", "handleTimeout");
        i();
        k();
        this.p = 3;
        e();
        d();
        j();
    }

    /* access modifiers changed from: private */
    public void g() {
        Log.i("LocationRetriever", "handleSilenceExpired");
        i();
        k();
        this.p = 0;
        b();
    }

    private void h() {
        i();
        this.u.sendEmptyMessageDelayed(1200, this.i);
    }

    /* access modifiers changed from: private */
    public void i() {
        if (this.u.hasMessages(1200)) {
            this.u.removeMessages(1200);
        }
    }

    /* access modifiers changed from: private */
    public void j() {
        k();
        this.v.sendEmptyMessageDelayed(1201, this.m);
    }

    private void k() {
        if (this.v.hasMessages(1201)) {
            this.v.removeMessages(1201);
        }
    }

    /* access modifiers changed from: 0000 */
    public void a() {
        i();
        k();
        e();
        this.e = null;
        this.f = null;
        this.g = null;
        this.q = null;
        this.o = null;
    }
}
