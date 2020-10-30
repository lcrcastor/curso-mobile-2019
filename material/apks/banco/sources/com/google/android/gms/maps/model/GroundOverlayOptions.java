package com.google.android.gms.maps.model;

import android.os.IBinder;
import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.zzac;
import com.google.android.gms.dynamic.zzd.zza;

public final class GroundOverlayOptions extends AbstractSafeParcelable {
    public static final zzc CREATOR = new zzc();
    public static final float NO_DIMENSION = -1.0f;
    private final int a;
    private BitmapDescriptor b;
    private LatLng c;
    private float d;
    private float e;
    private LatLngBounds f;
    private float g;
    private float h;
    private boolean i;
    private float j;
    private float k;
    private float l;
    private boolean m;

    public GroundOverlayOptions() {
        this.i = true;
        this.j = BitmapDescriptorFactory.HUE_RED;
        this.k = 0.5f;
        this.l = 0.5f;
        this.m = false;
        this.a = 1;
    }

    GroundOverlayOptions(int i2, IBinder iBinder, LatLng latLng, float f2, float f3, LatLngBounds latLngBounds, float f4, float f5, boolean z, float f6, float f7, float f8, boolean z2) {
        this.i = true;
        this.j = BitmapDescriptorFactory.HUE_RED;
        this.k = 0.5f;
        this.l = 0.5f;
        this.m = false;
        this.a = i2;
        this.b = new BitmapDescriptor(zza.zzfe(iBinder));
        this.c = latLng;
        this.d = f2;
        this.e = f3;
        this.f = latLngBounds;
        this.g = f4;
        this.h = f5;
        this.i = z;
        this.j = f6;
        this.k = f7;
        this.l = f8;
        this.m = z2;
    }

    private GroundOverlayOptions a(LatLng latLng, float f2, float f3) {
        this.c = latLng;
        this.d = f2;
        this.e = f3;
        return this;
    }

    /* access modifiers changed from: 0000 */
    public IBinder a() {
        return this.b.zzbrh().asBinder();
    }

    public GroundOverlayOptions anchor(float f2, float f3) {
        this.k = f2;
        this.l = f3;
        return this;
    }

    /* access modifiers changed from: 0000 */
    public int b() {
        return this.a;
    }

    public GroundOverlayOptions bearing(float f2) {
        this.g = ((f2 % 360.0f) + 360.0f) % 360.0f;
        return this;
    }

    public GroundOverlayOptions clickable(boolean z) {
        this.m = z;
        return this;
    }

    public float getAnchorU() {
        return this.k;
    }

    public float getAnchorV() {
        return this.l;
    }

    public float getBearing() {
        return this.g;
    }

    public LatLngBounds getBounds() {
        return this.f;
    }

    public float getHeight() {
        return this.e;
    }

    public BitmapDescriptor getImage() {
        return this.b;
    }

    public LatLng getLocation() {
        return this.c;
    }

    public float getTransparency() {
        return this.j;
    }

    public float getWidth() {
        return this.d;
    }

    public float getZIndex() {
        return this.h;
    }

    public GroundOverlayOptions image(BitmapDescriptor bitmapDescriptor) {
        this.b = bitmapDescriptor;
        return this;
    }

    public boolean isClickable() {
        return this.m;
    }

    public boolean isVisible() {
        return this.i;
    }

    public GroundOverlayOptions position(LatLng latLng, float f2) {
        boolean z = false;
        zzac.zza(this.f == null, (Object) "Position has already been set using positionFromBounds");
        zzac.zzb(latLng != null, (Object) "Location must be specified");
        if (f2 >= BitmapDescriptorFactory.HUE_RED) {
            z = true;
        }
        zzac.zzb(z, (Object) "Width must be non-negative");
        return a(latLng, f2, -1.0f);
    }

    public GroundOverlayOptions position(LatLng latLng, float f2, float f3) {
        boolean z = false;
        zzac.zza(this.f == null, (Object) "Position has already been set using positionFromBounds");
        zzac.zzb(latLng != null, (Object) "Location must be specified");
        zzac.zzb(f2 >= BitmapDescriptorFactory.HUE_RED, (Object) "Width must be non-negative");
        if (f3 >= BitmapDescriptorFactory.HUE_RED) {
            z = true;
        }
        zzac.zzb(z, (Object) "Height must be non-negative");
        return a(latLng, f2, f3);
    }

    public GroundOverlayOptions positionFromBounds(LatLngBounds latLngBounds) {
        boolean z = this.c == null;
        String valueOf = String.valueOf(this.c);
        StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 46);
        sb.append("Position has already been set using position: ");
        sb.append(valueOf);
        zzac.zza(z, (Object) sb.toString());
        this.f = latLngBounds;
        return this;
    }

    public GroundOverlayOptions transparency(float f2) {
        zzac.zzb(f2 >= BitmapDescriptorFactory.HUE_RED && f2 <= 1.0f, (Object) "Transparency must be in the range [0..1]");
        this.j = f2;
        return this;
    }

    public GroundOverlayOptions visible(boolean z) {
        this.i = z;
        return this;
    }

    public void writeToParcel(Parcel parcel, int i2) {
        zzc.a(this, parcel, i2);
    }

    public GroundOverlayOptions zIndex(float f2) {
        this.h = f2;
        return this;
    }
}
