package com.google.android.gms.location;

import android.os.Parcel;
import android.os.SystemClock;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.zzab;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

public final class LocationRequest extends AbstractSafeParcelable implements ReflectedParcelable {
    public static final LocationRequestCreator CREATOR = new LocationRequestCreator();
    public static final int PRIORITY_BALANCED_POWER_ACCURACY = 102;
    public static final int PRIORITY_HIGH_ACCURACY = 100;
    public static final int PRIORITY_LOW_POWER = 104;
    public static final int PRIORITY_NO_POWER = 105;
    int a;
    long b;
    long c;
    boolean d;
    long e;
    int f;
    float g;
    long h;
    private final int i;

    public LocationRequest() {
        this.i = 1;
        this.a = 102;
        this.b = 3600000;
        this.c = 600000;
        this.d = false;
        this.e = Long.MAX_VALUE;
        this.f = SubsamplingScaleImageView.TILE_SIZE_AUTO;
        this.g = BitmapDescriptorFactory.HUE_RED;
        this.h = 0;
    }

    LocationRequest(int i2, int i3, long j, long j2, boolean z, long j3, int i4, float f2, long j4) {
        this.i = i2;
        this.a = i3;
        this.b = j;
        this.c = j2;
        this.d = z;
        this.e = j3;
        this.f = i4;
        this.g = f2;
        this.h = j4;
    }

    private static void a(float f2) {
        if (f2 < BitmapDescriptorFactory.HUE_RED) {
            StringBuilder sb = new StringBuilder(37);
            sb.append("invalid displacement: ");
            sb.append(f2);
            throw new IllegalArgumentException(sb.toString());
        }
    }

    private static void a(int i2) {
        switch (i2) {
            case 100:
            case 102:
            case 104:
            case 105:
                return;
            default:
                StringBuilder sb = new StringBuilder(28);
                sb.append("invalid quality: ");
                sb.append(i2);
                throw new IllegalArgumentException(sb.toString());
        }
    }

    private static void a(long j) {
        if (j < 0) {
            StringBuilder sb = new StringBuilder(38);
            sb.append("invalid interval: ");
            sb.append(j);
            throw new IllegalArgumentException(sb.toString());
        }
    }

    public static LocationRequest create() {
        return new LocationRequest();
    }

    public static String zzue(int i2) {
        switch (i2) {
            case 100:
                return "PRIORITY_HIGH_ACCURACY";
            case 102:
                return "PRIORITY_BALANCED_POWER_ACCURACY";
            case 104:
                return "PRIORITY_LOW_POWER";
            case 105:
                return "PRIORITY_NO_POWER";
            default:
                return "???";
        }
    }

    /* access modifiers changed from: 0000 */
    public int a() {
        return this.i;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof LocationRequest)) {
            return false;
        }
        LocationRequest locationRequest = (LocationRequest) obj;
        return this.a == locationRequest.a && this.b == locationRequest.b && this.c == locationRequest.c && this.d == locationRequest.d && this.e == locationRequest.e && this.f == locationRequest.f && this.g == locationRequest.g;
    }

    public long getExpirationTime() {
        return this.e;
    }

    public long getFastestInterval() {
        return this.c;
    }

    public long getInterval() {
        return this.b;
    }

    public long getMaxWaitTime() {
        long j = this.h;
        return j < this.b ? this.b : j;
    }

    public int getNumUpdates() {
        return this.f;
    }

    public int getPriority() {
        return this.a;
    }

    public float getSmallestDisplacement() {
        return this.g;
    }

    public int hashCode() {
        return zzab.hashCode(Integer.valueOf(this.a), Long.valueOf(this.b), Long.valueOf(this.c), Boolean.valueOf(this.d), Long.valueOf(this.e), Integer.valueOf(this.f), Float.valueOf(this.g));
    }

    public LocationRequest setExpirationDuration(long j) {
        long elapsedRealtime = SystemClock.elapsedRealtime();
        long j2 = Long.MAX_VALUE;
        if (j <= Long.MAX_VALUE - elapsedRealtime) {
            j2 = j + elapsedRealtime;
        }
        this.e = j2;
        if (this.e < 0) {
            this.e = 0;
        }
        return this;
    }

    public LocationRequest setExpirationTime(long j) {
        this.e = j;
        if (this.e < 0) {
            this.e = 0;
        }
        return this;
    }

    public LocationRequest setFastestInterval(long j) {
        a(j);
        this.d = true;
        this.c = j;
        return this;
    }

    public LocationRequest setInterval(long j) {
        a(j);
        this.b = j;
        if (!this.d) {
            this.c = (long) (((double) this.b) / 6.0d);
        }
        return this;
    }

    public LocationRequest setMaxWaitTime(long j) {
        a(j);
        this.h = j;
        return this;
    }

    public LocationRequest setNumUpdates(int i2) {
        if (i2 <= 0) {
            StringBuilder sb = new StringBuilder(31);
            sb.append("invalid numUpdates: ");
            sb.append(i2);
            throw new IllegalArgumentException(sb.toString());
        }
        this.f = i2;
        return this;
    }

    public LocationRequest setPriority(int i2) {
        a(i2);
        this.a = i2;
        return this;
    }

    public LocationRequest setSmallestDisplacement(float f2) {
        a(f2);
        this.g = f2;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Request[");
        sb.append(zzue(this.a));
        if (this.a != 105) {
            sb.append(" requested=");
            sb.append(this.b);
            sb.append("ms");
        }
        sb.append(" fastest=");
        sb.append(this.c);
        sb.append("ms");
        if (this.h > this.b) {
            sb.append(" maxWait=");
            sb.append(this.h);
            sb.append("ms");
        }
        if (this.e != Long.MAX_VALUE) {
            long elapsedRealtime = this.e - SystemClock.elapsedRealtime();
            sb.append(" expireIn=");
            sb.append(elapsedRealtime);
            sb.append("ms");
        }
        if (this.f != Integer.MAX_VALUE) {
            sb.append(" num=");
            sb.append(this.f);
        }
        sb.append(']');
        return sb.toString();
    }

    public void writeToParcel(Parcel parcel, int i2) {
        LocationRequestCreator.a(this, parcel, i2);
    }
}
