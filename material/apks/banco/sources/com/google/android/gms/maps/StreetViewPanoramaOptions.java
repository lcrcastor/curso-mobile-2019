package com.google.android.gms.maps;

import android.os.Parcel;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.maps.internal.zza;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.StreetViewPanoramaCamera;

public final class StreetViewPanoramaOptions extends AbstractSafeParcelable implements ReflectedParcelable {
    public static final zzb CREATOR = new zzb();
    private final int a;
    private StreetViewPanoramaCamera b;
    private String c;
    private LatLng d;
    private Integer e;
    private Boolean f;
    private Boolean g;
    private Boolean h;
    private Boolean i;
    private Boolean j;

    public StreetViewPanoramaOptions() {
        this.f = Boolean.valueOf(true);
        this.g = Boolean.valueOf(true);
        this.h = Boolean.valueOf(true);
        this.i = Boolean.valueOf(true);
        this.a = 1;
    }

    StreetViewPanoramaOptions(int i2, StreetViewPanoramaCamera streetViewPanoramaCamera, String str, LatLng latLng, Integer num, byte b2, byte b3, byte b4, byte b5, byte b6) {
        this.f = Boolean.valueOf(true);
        this.g = Boolean.valueOf(true);
        this.h = Boolean.valueOf(true);
        this.i = Boolean.valueOf(true);
        this.a = i2;
        this.b = streetViewPanoramaCamera;
        this.d = latLng;
        this.e = num;
        this.c = str;
        this.f = zza.zza(b2);
        this.g = zza.zza(b3);
        this.h = zza.zza(b4);
        this.i = zza.zza(b5);
        this.j = zza.zza(b6);
    }

    /* access modifiers changed from: 0000 */
    public int a() {
        return this.a;
    }

    /* access modifiers changed from: 0000 */
    public byte b() {
        return zza.zze(this.f);
    }

    /* access modifiers changed from: 0000 */
    public byte c() {
        return zza.zze(this.g);
    }

    /* access modifiers changed from: 0000 */
    public byte d() {
        return zza.zze(this.h);
    }

    /* access modifiers changed from: 0000 */
    public byte e() {
        return zza.zze(this.i);
    }

    /* access modifiers changed from: 0000 */
    public byte f() {
        return zza.zze(this.j);
    }

    public Boolean getPanningGesturesEnabled() {
        return this.h;
    }

    public String getPanoramaId() {
        return this.c;
    }

    public LatLng getPosition() {
        return this.d;
    }

    public Integer getRadius() {
        return this.e;
    }

    public Boolean getStreetNamesEnabled() {
        return this.i;
    }

    public StreetViewPanoramaCamera getStreetViewPanoramaCamera() {
        return this.b;
    }

    public Boolean getUseViewLifecycleInFragment() {
        return this.j;
    }

    public Boolean getUserNavigationEnabled() {
        return this.f;
    }

    public Boolean getZoomGesturesEnabled() {
        return this.g;
    }

    public StreetViewPanoramaOptions panningGesturesEnabled(boolean z) {
        this.h = Boolean.valueOf(z);
        return this;
    }

    public StreetViewPanoramaOptions panoramaCamera(StreetViewPanoramaCamera streetViewPanoramaCamera) {
        this.b = streetViewPanoramaCamera;
        return this;
    }

    public StreetViewPanoramaOptions panoramaId(String str) {
        this.c = str;
        return this;
    }

    public StreetViewPanoramaOptions position(LatLng latLng) {
        this.d = latLng;
        return this;
    }

    public StreetViewPanoramaOptions position(LatLng latLng, Integer num) {
        this.d = latLng;
        this.e = num;
        return this;
    }

    public StreetViewPanoramaOptions streetNamesEnabled(boolean z) {
        this.i = Boolean.valueOf(z);
        return this;
    }

    public StreetViewPanoramaOptions useViewLifecycleInFragment(boolean z) {
        this.j = Boolean.valueOf(z);
        return this;
    }

    public StreetViewPanoramaOptions userNavigationEnabled(boolean z) {
        this.f = Boolean.valueOf(z);
        return this;
    }

    public void writeToParcel(Parcel parcel, int i2) {
        zzb.a(this, parcel, i2);
    }

    public StreetViewPanoramaOptions zoomGesturesEnabled(boolean z) {
        this.g = Boolean.valueOf(z);
        return this;
    }
}
