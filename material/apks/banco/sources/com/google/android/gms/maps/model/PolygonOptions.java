package com.google.android.gms.maps.model;

import android.os.Parcel;
import android.support.v4.view.ViewCompat;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class PolygonOptions extends AbstractSafeParcelable {
    public static final zzi CREATOR = new zzi();
    private final int a;
    private final List<LatLng> b;
    private final List<List<LatLng>> c;
    private float d;
    private int e;
    private int f;
    private float g;
    private boolean h;
    private boolean i;
    private boolean j;

    public PolygonOptions() {
        this.d = 10.0f;
        this.e = ViewCompat.MEASURED_STATE_MASK;
        this.f = 0;
        this.g = BitmapDescriptorFactory.HUE_RED;
        this.h = true;
        this.i = false;
        this.j = false;
        this.a = 1;
        this.b = new ArrayList();
        this.c = new ArrayList();
    }

    PolygonOptions(int i2, List<LatLng> list, List list2, float f2, int i3, int i4, float f3, boolean z, boolean z2, boolean z3) {
        this.d = 10.0f;
        this.e = ViewCompat.MEASURED_STATE_MASK;
        this.f = 0;
        this.g = BitmapDescriptorFactory.HUE_RED;
        this.h = true;
        this.i = false;
        this.j = false;
        this.a = i2;
        this.b = list;
        this.c = list2;
        this.d = f2;
        this.e = i3;
        this.f = i4;
        this.g = f3;
        this.h = z;
        this.i = z2;
        this.j = z3;
    }

    /* access modifiers changed from: 0000 */
    public int a() {
        return this.a;
    }

    public PolygonOptions add(LatLng latLng) {
        this.b.add(latLng);
        return this;
    }

    public PolygonOptions add(LatLng... latLngArr) {
        this.b.addAll(Arrays.asList(latLngArr));
        return this;
    }

    public PolygonOptions addAll(Iterable<LatLng> iterable) {
        for (LatLng add : iterable) {
            this.b.add(add);
        }
        return this;
    }

    public PolygonOptions addHole(Iterable<LatLng> iterable) {
        ArrayList arrayList = new ArrayList();
        for (LatLng add : iterable) {
            arrayList.add(add);
        }
        this.c.add(arrayList);
        return this;
    }

    /* access modifiers changed from: 0000 */
    public List b() {
        return this.c;
    }

    public PolygonOptions clickable(boolean z) {
        this.j = z;
        return this;
    }

    public PolygonOptions fillColor(int i2) {
        this.f = i2;
        return this;
    }

    public PolygonOptions geodesic(boolean z) {
        this.i = z;
        return this;
    }

    public int getFillColor() {
        return this.f;
    }

    public List<List<LatLng>> getHoles() {
        return this.c;
    }

    public List<LatLng> getPoints() {
        return this.b;
    }

    public int getStrokeColor() {
        return this.e;
    }

    public float getStrokeWidth() {
        return this.d;
    }

    public float getZIndex() {
        return this.g;
    }

    public boolean isClickable() {
        return this.j;
    }

    public boolean isGeodesic() {
        return this.i;
    }

    public boolean isVisible() {
        return this.h;
    }

    public PolygonOptions strokeColor(int i2) {
        this.e = i2;
        return this;
    }

    public PolygonOptions strokeWidth(float f2) {
        this.d = f2;
        return this;
    }

    public PolygonOptions visible(boolean z) {
        this.h = z;
        return this;
    }

    public void writeToParcel(Parcel parcel, int i2) {
        zzi.a(this, parcel, i2);
    }

    public PolygonOptions zIndex(float f2) {
        this.g = f2;
        return this;
    }
}
