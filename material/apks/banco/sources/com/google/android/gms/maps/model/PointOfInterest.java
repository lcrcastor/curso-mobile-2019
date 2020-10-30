package com.google.android.gms.maps.model;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;

public final class PointOfInterest extends AbstractSafeParcelable {
    public static final zzh CREATOR = new zzh();
    private final int a;
    public final LatLng latLng;
    public final String name;
    public final String placeId;

    PointOfInterest(int i, LatLng latLng2, String str, String str2) {
        this.a = i;
        this.latLng = latLng2;
        this.placeId = str;
        this.name = str2;
    }

    public PointOfInterest(LatLng latLng2, String str, String str2) {
        this(1, latLng2, str, str2);
    }

    /* access modifiers changed from: 0000 */
    public int a() {
        return this.a;
    }

    public void writeToParcel(Parcel parcel, int i) {
        zzh.a(this, parcel, i);
    }
}
