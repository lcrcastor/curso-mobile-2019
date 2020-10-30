package com.google.android.gms.location;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public final class LocationSettingsRequest extends AbstractSafeParcelable {
    public static final Creator<LocationSettingsRequest> CREATOR = new zzi();
    private final int a;
    private final List<LocationRequest> b;
    private final boolean c;
    private final boolean d;

    public static final class Builder {
        private final ArrayList<LocationRequest> a = new ArrayList<>();
        private boolean b = false;
        private boolean c = false;

        public Builder addAllLocationRequests(Collection<LocationRequest> collection) {
            this.a.addAll(collection);
            return this;
        }

        public Builder addLocationRequest(LocationRequest locationRequest) {
            this.a.add(locationRequest);
            return this;
        }

        public LocationSettingsRequest build() {
            return new LocationSettingsRequest((List) this.a, this.b, this.c);
        }

        public Builder setAlwaysShow(boolean z) {
            this.b = z;
            return this;
        }

        public Builder setNeedBle(boolean z) {
            this.c = z;
            return this;
        }
    }

    LocationSettingsRequest(int i, List<LocationRequest> list, boolean z, boolean z2) {
        this.a = i;
        this.b = list;
        this.c = z;
        this.d = z2;
    }

    private LocationSettingsRequest(List<LocationRequest> list, boolean z, boolean z2) {
        this(3, list, z, z2);
    }

    public int getVersionCode() {
        return this.a;
    }

    public void writeToParcel(Parcel parcel, int i) {
        zzi.a(this, parcel, i);
    }

    public List<LocationRequest> zzbgq() {
        return Collections.unmodifiableList(this.b);
    }

    public boolean zzbph() {
        return this.c;
    }

    public boolean zzbpi() {
        return this.d;
    }
}
