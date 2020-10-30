package com.google.android.gms.location.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;

public final class FusedLocationProviderResult extends AbstractSafeParcelable implements Result {
    public static final Creator<FusedLocationProviderResult> CREATOR = new zze();
    public static final FusedLocationProviderResult ahT = new FusedLocationProviderResult(Status.vY);
    private final int a;
    private final Status b;

    FusedLocationProviderResult(int i, Status status) {
        this.a = i;
        this.b = status;
    }

    public FusedLocationProviderResult(Status status) {
        this(1, status);
    }

    public Status getStatus() {
        return this.b;
    }

    public int getVersionCode() {
        return this.a;
    }

    public void writeToParcel(Parcel parcel, int i) {
        zze.a(this, parcel, i);
    }
}
