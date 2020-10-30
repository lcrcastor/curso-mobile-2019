package com.google.android.gms.maps.model;

import android.os.Parcel;
import android.support.v4.view.ViewCompat;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class PolylineOptions extends AbstractSafeParcelable {
    public static final zzj CREATOR = new zzj();
    private final int a;
    private final List<LatLng> b;
    private float c;
    private int d;
    private float e;
    private boolean f;
    private boolean g;
    private boolean h;

    public PolylineOptions() {
        this.c = 10.0f;
        this.d = ViewCompat.MEASURED_STATE_MASK;
        this.e = BitmapDescriptorFactory.HUE_RED;
        this.f = true;
        this.g = false;
        this.h = false;
        this.a = 1;
        this.b = new ArrayList();
    }

    PolylineOptions(int i, List list, float f2, int i2, float f3, boolean z, boolean z2, boolean z3) {
        this.c = 10.0f;
        this.d = ViewCompat.MEASURED_STATE_MASK;
        this.e = BitmapDescriptorFactory.HUE_RED;
        this.f = true;
        this.g = false;
        this.h = false;
        this.a = i;
        this.b = list;
        this.c = f2;
        this.d = i2;
        this.e = f3;
        this.f = z;
        this.g = z2;
        this.h = z3;
    }

    /* access modifiers changed from: 0000 */
    public int a() {
        return this.a;
    }

    public PolylineOptions add(LatLng latLng) {
        this.b.add(latLng);
        return this;
    }

    public PolylineOptions add(LatLng... latLngArr) {
        this.b.addAll(Arrays.asList(latLngArr));
        return this;
    }

    public PolylineOptions addAll(Iterable<LatLng> iterable) {
        for (LatLng add : iterable) {
            this.b.add(add);
        }
        return this;
    }

    public PolylineOptions clickable(boolean z) {
        this.h = z;
        return this;
    }

    public PolylineOptions color(int i) {
        this.d = i;
        return this;
    }

    public PolylineOptions geodesic(boolean z) {
        this.g = z;
        return this;
    }

    public int getColor() {
        return this.d;
    }

    public List<LatLng> getPoints() {
        return this.b;
    }

    public float getWidth() {
        return this.c;
    }

    public float getZIndex() {
        return this.e;
    }

    public boolean isClickable() {
        return this.h;
    }

    public boolean isGeodesic() {
        return this.g;
    }

    public boolean isVisible() {
        return this.f;
    }

    public PolylineOptions visible(boolean z) {
        this.f = z;
        return this;
    }

    public PolylineOptions width(float f2) {
        this.c = f2;
        return this;
    }

    public void writeToParcel(Parcel parcel, int i) {
        zzj.a(this, parcel, i);
    }

    public PolylineOptions zIndex(float f2) {
        this.e = f2;
        return this;
    }
}
