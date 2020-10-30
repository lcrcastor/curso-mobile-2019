package com.google.android.gms.maps.model;

import android.os.IBinder;
import android.os.Parcel;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.dynamic.zzd.zza;

public final class MarkerOptions extends AbstractSafeParcelable {
    public static final zzg CREATOR = new zzg();
    private final int a;
    private LatLng b;
    private String c;
    private String d;
    private BitmapDescriptor e;
    private float f;
    private float g;
    private boolean h;
    private boolean i;
    private boolean j;
    private float k;
    private float l;
    private float m;
    private float n;
    private float o;

    public MarkerOptions() {
        this.f = 0.5f;
        this.g = 1.0f;
        this.i = true;
        this.j = false;
        this.k = BitmapDescriptorFactory.HUE_RED;
        this.l = 0.5f;
        this.m = BitmapDescriptorFactory.HUE_RED;
        this.n = 1.0f;
        this.a = 1;
    }

    MarkerOptions(int i2, LatLng latLng, String str, String str2, IBinder iBinder, float f2, float f3, boolean z, boolean z2, boolean z3, float f4, float f5, float f6, float f7, float f8) {
        this.f = 0.5f;
        this.g = 1.0f;
        this.i = true;
        this.j = false;
        this.k = BitmapDescriptorFactory.HUE_RED;
        this.l = 0.5f;
        this.m = BitmapDescriptorFactory.HUE_RED;
        this.n = 1.0f;
        this.a = i2;
        this.b = latLng;
        this.c = str;
        this.d = str2;
        this.e = iBinder == null ? null : new BitmapDescriptor(zza.zzfe(iBinder));
        this.f = f2;
        this.g = f3;
        this.h = z;
        this.i = z2;
        this.j = z3;
        this.k = f4;
        this.l = f5;
        this.m = f6;
        this.n = f7;
        this.o = f8;
    }

    /* access modifiers changed from: 0000 */
    public int a() {
        return this.a;
    }

    public MarkerOptions alpha(float f2) {
        this.n = f2;
        return this;
    }

    public MarkerOptions anchor(float f2, float f3) {
        this.f = f2;
        this.g = f3;
        return this;
    }

    /* access modifiers changed from: 0000 */
    public IBinder b() {
        if (this.e == null) {
            return null;
        }
        return this.e.zzbrh().asBinder();
    }

    public MarkerOptions draggable(boolean z) {
        this.h = z;
        return this;
    }

    public MarkerOptions flat(boolean z) {
        this.j = z;
        return this;
    }

    public float getAlpha() {
        return this.n;
    }

    public float getAnchorU() {
        return this.f;
    }

    public float getAnchorV() {
        return this.g;
    }

    public BitmapDescriptor getIcon() {
        return this.e;
    }

    public float getInfoWindowAnchorU() {
        return this.l;
    }

    public float getInfoWindowAnchorV() {
        return this.m;
    }

    public LatLng getPosition() {
        return this.b;
    }

    public float getRotation() {
        return this.k;
    }

    public String getSnippet() {
        return this.d;
    }

    public String getTitle() {
        return this.c;
    }

    public float getZIndex() {
        return this.o;
    }

    public MarkerOptions icon(@Nullable BitmapDescriptor bitmapDescriptor) {
        this.e = bitmapDescriptor;
        return this;
    }

    public MarkerOptions infoWindowAnchor(float f2, float f3) {
        this.l = f2;
        this.m = f3;
        return this;
    }

    public boolean isDraggable() {
        return this.h;
    }

    public boolean isFlat() {
        return this.j;
    }

    public boolean isVisible() {
        return this.i;
    }

    public MarkerOptions position(@NonNull LatLng latLng) {
        if (latLng == null) {
            throw new IllegalArgumentException("latlng cannot be null - a position is required.");
        }
        this.b = latLng;
        return this;
    }

    public MarkerOptions rotation(float f2) {
        this.k = f2;
        return this;
    }

    public MarkerOptions snippet(@Nullable String str) {
        this.d = str;
        return this;
    }

    public MarkerOptions title(@Nullable String str) {
        this.c = str;
        return this;
    }

    public MarkerOptions visible(boolean z) {
        this.i = z;
        return this;
    }

    public void writeToParcel(Parcel parcel, int i2) {
        zzg.a(this, parcel, i2);
    }

    public MarkerOptions zIndex(float f2) {
        this.o = f2;
        return this;
    }
}
