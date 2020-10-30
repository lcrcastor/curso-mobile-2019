package com.google.android.gms.location.internal;

import android.annotation.SuppressLint;
import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.util.Locale;

public class ParcelableGeofence extends AbstractSafeParcelable implements Geofence {
    public static final zzo CREATOR = new zzo();
    private final int a;
    private final String b;
    private final long c;
    private final short d;
    private final double e;
    private final double f;
    private final float g;
    private final int h;
    private final int i;
    private final int j;

    public ParcelableGeofence(int i2, String str, int i3, short s, double d2, double d3, float f2, long j2, int i4, int i5) {
        a(str);
        a(f2);
        a(d2, d3);
        int a2 = a(i3);
        this.a = i2;
        this.d = s;
        this.b = str;
        this.e = d2;
        this.f = d3;
        this.g = f2;
        this.c = j2;
        this.h = a2;
        this.i = i4;
        this.j = i5;
    }

    public ParcelableGeofence(String str, int i2, short s, double d2, double d3, float f2, long j2, int i3, int i4) {
        this(1, str, i2, s, d2, d3, f2, j2, i3, i4);
    }

    private static int a(int i2) {
        int i3 = i2 & 7;
        if (i3 != 0) {
            return i3;
        }
        StringBuilder sb = new StringBuilder(46);
        sb.append("No supported transition specified: ");
        sb.append(i2);
        throw new IllegalArgumentException(sb.toString());
    }

    private static void a(double d2, double d3) {
        if (d2 > 90.0d || d2 < -90.0d) {
            StringBuilder sb = new StringBuilder(42);
            sb.append("invalid latitude: ");
            sb.append(d2);
            throw new IllegalArgumentException(sb.toString());
        } else if (d3 > 180.0d || d3 < -180.0d) {
            StringBuilder sb2 = new StringBuilder(43);
            sb2.append("invalid longitude: ");
            sb2.append(d3);
            throw new IllegalArgumentException(sb2.toString());
        }
    }

    private static void a(float f2) {
        if (f2 <= BitmapDescriptorFactory.HUE_RED) {
            StringBuilder sb = new StringBuilder(31);
            sb.append("invalid radius: ");
            sb.append(f2);
            throw new IllegalArgumentException(sb.toString());
        }
    }

    private static void a(String str) {
        if (str == null || str.length() > 100) {
            String str2 = "requestId is null or too long: ";
            String valueOf = String.valueOf(str);
            throw new IllegalArgumentException(valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2));
        }
    }

    @SuppressLint({"DefaultLocale"})
    private static String b(int i2) {
        if (i2 != 1) {
            return null;
        }
        return "CIRCLE";
    }

    public static ParcelableGeofence zzv(byte[] bArr) {
        Parcel obtain = Parcel.obtain();
        obtain.unmarshall(bArr, 0, bArr.length);
        obtain.setDataPosition(0);
        ParcelableGeofence parcelableGeofence = (ParcelableGeofence) CREATOR.createFromParcel(obtain);
        obtain.recycle();
        return parcelableGeofence;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof ParcelableGeofence)) {
            return false;
        }
        ParcelableGeofence parcelableGeofence = (ParcelableGeofence) obj;
        return this.g == parcelableGeofence.g && this.e == parcelableGeofence.e && this.f == parcelableGeofence.f && this.d == parcelableGeofence.d;
    }

    public long getExpirationTime() {
        return this.c;
    }

    public double getLatitude() {
        return this.e;
    }

    public double getLongitude() {
        return this.f;
    }

    public float getRadius() {
        return this.g;
    }

    public String getRequestId() {
        return this.b;
    }

    public int getVersionCode() {
        return this.a;
    }

    public int hashCode() {
        long doubleToLongBits = Double.doubleToLongBits(this.e);
        int i2 = ((int) (doubleToLongBits ^ (doubleToLongBits >>> 32))) + 31;
        long doubleToLongBits2 = Double.doubleToLongBits(this.f);
        return (((((((i2 * 31) + ((int) (doubleToLongBits2 ^ (doubleToLongBits2 >>> 32)))) * 31) + Float.floatToIntBits(this.g)) * 31) + this.d) * 31) + this.h;
    }

    public String toString() {
        return String.format(Locale.US, "Geofence[%s id:%s transitions:%d %.6f, %.6f %.0fm, resp=%ds, dwell=%dms, @%d]", new Object[]{b(this.d), this.b, Integer.valueOf(this.h), Double.valueOf(this.e), Double.valueOf(this.f), Float.valueOf(this.g), Integer.valueOf(this.i / 1000), Integer.valueOf(this.j), Long.valueOf(this.c)});
    }

    public void writeToParcel(Parcel parcel, int i2) {
        zzo zzo = CREATOR;
        zzo.a(this, parcel, i2);
    }

    public short zzbpq() {
        return this.d;
    }

    public int zzbpr() {
        return this.h;
    }

    public int zzbps() {
        return this.i;
    }

    public int zzbpt() {
        return this.j;
    }
}
