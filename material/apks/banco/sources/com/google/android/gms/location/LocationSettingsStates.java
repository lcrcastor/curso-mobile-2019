package com.google.android.gms.location;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.zzc;

public final class LocationSettingsStates extends AbstractSafeParcelable {
    public static final Creator<LocationSettingsStates> CREATOR = new zzk();
    private final int a;
    private final boolean b;
    private final boolean c;
    private final boolean d;
    private final boolean e;
    private final boolean f;
    private final boolean g;

    LocationSettingsStates(int i, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6) {
        this.a = i;
        this.b = z;
        this.c = z2;
        this.d = z3;
        this.e = z4;
        this.f = z5;
        this.g = z6;
    }

    public static LocationSettingsStates fromIntent(Intent intent) {
        return (LocationSettingsStates) zzc.zza(intent, "com.google.android.gms.location.LOCATION_SETTINGS_STATES", CREATOR);
    }

    public int getVersionCode() {
        return this.a;
    }

    public boolean isBlePresent() {
        return this.g;
    }

    public boolean isBleUsable() {
        return this.d;
    }

    public boolean isGpsPresent() {
        return this.e;
    }

    public boolean isGpsUsable() {
        return this.b;
    }

    public boolean isLocationPresent() {
        return this.e || this.f;
    }

    public boolean isLocationUsable() {
        return this.b || this.c;
    }

    public boolean isNetworkLocationPresent() {
        return this.f;
    }

    public boolean isNetworkLocationUsable() {
        return this.c;
    }

    public void writeToParcel(Parcel parcel, int i) {
        zzk.a(this, parcel, i);
    }
}
