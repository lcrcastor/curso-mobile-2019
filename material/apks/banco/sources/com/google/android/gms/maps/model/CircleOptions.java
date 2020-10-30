package com.google.android.gms.maps.model;

import android.os.Parcel;
import android.support.v4.view.ViewCompat;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;

public final class CircleOptions extends AbstractSafeParcelable {
    public static final zzb CREATOR = new zzb();
    private final int a;
    private LatLng b;
    private double c;
    private float d;
    private int e;
    private int f;
    private float g;
    private boolean h;
    private boolean i;

    public CircleOptions() {
        this.b = null;
        this.c = 0.0d;
        this.d = 10.0f;
        this.e = ViewCompat.MEASURED_STATE_MASK;
        this.f = 0;
        this.g = BitmapDescriptorFactory.HUE_RED;
        this.h = true;
        this.i = false;
        this.a = 1;
    }

    CircleOptions(int i2, LatLng latLng, double d2, float f2, int i3, int i4, float f3, boolean z, boolean z2) {
        this.b = null;
        this.c = 0.0d;
        this.d = 10.0f;
        this.e = ViewCompat.MEASURED_STATE_MASK;
        this.f = 0;
        this.g = BitmapDescriptorFactory.HUE_RED;
        this.h = true;
        this.i = false;
        this.a = i2;
        this.b = latLng;
        this.c = d2;
        this.d = f2;
        this.e = i3;
        this.f = i4;
        this.g = f3;
        this.h = z;
        this.i = z2;
    }

    /* access modifiers changed from: 0000 */
    public int a() {
        return this.a;
    }

    public CircleOptions center(LatLng latLng) {
        this.b = latLng;
        return this;
    }

    public CircleOptions clickable(boolean z) {
        this.i = z;
        return this;
    }

    public CircleOptions fillColor(int i2) {
        this.f = i2;
        return this;
    }

    public LatLng getCenter() {
        return this.b;
    }

    public int getFillColor() {
        return this.f;
    }

    public double getRadius() {
        return this.c;
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
        return this.i;
    }

    public boolean isVisible() {
        return this.h;
    }

    public CircleOptions radius(double d2) {
        this.c = d2;
        return this;
    }

    public CircleOptions strokeColor(int i2) {
        this.e = i2;
        return this;
    }

    public CircleOptions strokeWidth(float f2) {
        this.d = f2;
        return this;
    }

    public CircleOptions visible(boolean z) {
        this.h = z;
        return this;
    }

    public void writeToParcel(Parcel parcel, int i2) {
        zzb.a(this, parcel, i2);
    }

    public CircleOptions zIndex(float f2) {
        this.g = f2;
        return this;
    }
}
