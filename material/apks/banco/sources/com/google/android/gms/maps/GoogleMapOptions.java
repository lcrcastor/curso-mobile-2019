package com.google.android.gms.maps;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.util.AttributeSet;
import com.google.android.gms.R;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.maps.internal.zza;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLngBounds;

public final class GoogleMapOptions extends AbstractSafeParcelable implements ReflectedParcelable {
    public static final zza CREATOR = new zza();
    private final int a;
    private Boolean b;
    private Boolean c;
    private int d;
    private CameraPosition e;
    private Boolean f;
    private Boolean g;
    private Boolean h;
    private Boolean i;
    private Boolean j;
    private Boolean k;
    private Boolean l;
    private Boolean m;
    private Boolean n;
    private Float o;
    private Float p;
    private LatLngBounds q;

    public GoogleMapOptions() {
        this.d = -1;
        this.o = null;
        this.p = null;
        this.q = null;
        this.a = 1;
    }

    GoogleMapOptions(int i2, byte b2, byte b3, int i3, CameraPosition cameraPosition, byte b4, byte b5, byte b6, byte b7, byte b8, byte b9, byte b10, byte b11, byte b12, Float f2, Float f3, LatLngBounds latLngBounds) {
        this.d = -1;
        this.o = null;
        this.p = null;
        this.q = null;
        this.a = i2;
        this.b = zza.zza(b2);
        this.c = zza.zza(b3);
        this.d = i3;
        this.e = cameraPosition;
        this.f = zza.zza(b4);
        this.g = zza.zza(b5);
        this.h = zza.zza(b6);
        this.i = zza.zza(b7);
        this.j = zza.zza(b8);
        this.k = zza.zza(b9);
        this.l = zza.zza(b10);
        this.m = zza.zza(b11);
        this.n = zza.zza(b12);
        this.o = f2;
        this.p = f3;
        this.q = latLngBounds;
    }

    public static GoogleMapOptions createFromAttributes(Context context, AttributeSet attributeSet) {
        if (attributeSet == null) {
            return null;
        }
        TypedArray obtainAttributes = context.getResources().obtainAttributes(attributeSet, R.styleable.MapAttrs);
        GoogleMapOptions googleMapOptions = new GoogleMapOptions();
        if (obtainAttributes.hasValue(R.styleable.MapAttrs_mapType)) {
            googleMapOptions.mapType(obtainAttributes.getInt(R.styleable.MapAttrs_mapType, -1));
        }
        if (obtainAttributes.hasValue(R.styleable.MapAttrs_zOrderOnTop)) {
            googleMapOptions.zOrderOnTop(obtainAttributes.getBoolean(R.styleable.MapAttrs_zOrderOnTop, false));
        }
        if (obtainAttributes.hasValue(R.styleable.MapAttrs_useViewLifecycle)) {
            googleMapOptions.useViewLifecycleInFragment(obtainAttributes.getBoolean(R.styleable.MapAttrs_useViewLifecycle, false));
        }
        if (obtainAttributes.hasValue(R.styleable.MapAttrs_uiCompass)) {
            googleMapOptions.compassEnabled(obtainAttributes.getBoolean(R.styleable.MapAttrs_uiCompass, true));
        }
        if (obtainAttributes.hasValue(R.styleable.MapAttrs_uiRotateGestures)) {
            googleMapOptions.rotateGesturesEnabled(obtainAttributes.getBoolean(R.styleable.MapAttrs_uiRotateGestures, true));
        }
        if (obtainAttributes.hasValue(R.styleable.MapAttrs_uiScrollGestures)) {
            googleMapOptions.scrollGesturesEnabled(obtainAttributes.getBoolean(R.styleable.MapAttrs_uiScrollGestures, true));
        }
        if (obtainAttributes.hasValue(R.styleable.MapAttrs_uiTiltGestures)) {
            googleMapOptions.tiltGesturesEnabled(obtainAttributes.getBoolean(R.styleable.MapAttrs_uiTiltGestures, true));
        }
        if (obtainAttributes.hasValue(R.styleable.MapAttrs_uiZoomGestures)) {
            googleMapOptions.zoomGesturesEnabled(obtainAttributes.getBoolean(R.styleable.MapAttrs_uiZoomGestures, true));
        }
        if (obtainAttributes.hasValue(R.styleable.MapAttrs_uiZoomControls)) {
            googleMapOptions.zoomControlsEnabled(obtainAttributes.getBoolean(R.styleable.MapAttrs_uiZoomControls, true));
        }
        if (obtainAttributes.hasValue(R.styleable.MapAttrs_liteMode)) {
            googleMapOptions.liteMode(obtainAttributes.getBoolean(R.styleable.MapAttrs_liteMode, false));
        }
        if (obtainAttributes.hasValue(R.styleable.MapAttrs_uiMapToolbar)) {
            googleMapOptions.mapToolbarEnabled(obtainAttributes.getBoolean(R.styleable.MapAttrs_uiMapToolbar, true));
        }
        if (obtainAttributes.hasValue(R.styleable.MapAttrs_ambientEnabled)) {
            googleMapOptions.ambientEnabled(obtainAttributes.getBoolean(R.styleable.MapAttrs_ambientEnabled, false));
        }
        if (obtainAttributes.hasValue(R.styleable.MapAttrs_cameraMinZoomPreference)) {
            googleMapOptions.minZoomPreference(obtainAttributes.getFloat(R.styleable.MapAttrs_cameraMinZoomPreference, Float.NEGATIVE_INFINITY));
        }
        if (obtainAttributes.hasValue(R.styleable.MapAttrs_cameraMinZoomPreference)) {
            googleMapOptions.maxZoomPreference(obtainAttributes.getFloat(R.styleable.MapAttrs_cameraMaxZoomPreference, Float.POSITIVE_INFINITY));
        }
        googleMapOptions.latLngBoundsForCameraTarget(LatLngBounds.createFromAttributes(context, attributeSet));
        googleMapOptions.camera(CameraPosition.createFromAttributes(context, attributeSet));
        obtainAttributes.recycle();
        return googleMapOptions;
    }

    /* access modifiers changed from: 0000 */
    public int a() {
        return this.a;
    }

    public GoogleMapOptions ambientEnabled(boolean z) {
        this.n = Boolean.valueOf(z);
        return this;
    }

    /* access modifiers changed from: 0000 */
    public byte b() {
        return zza.zze(this.b);
    }

    /* access modifiers changed from: 0000 */
    public byte c() {
        return zza.zze(this.c);
    }

    public GoogleMapOptions camera(CameraPosition cameraPosition) {
        this.e = cameraPosition;
        return this;
    }

    public GoogleMapOptions compassEnabled(boolean z) {
        this.g = Boolean.valueOf(z);
        return this;
    }

    /* access modifiers changed from: 0000 */
    public byte d() {
        return zza.zze(this.f);
    }

    /* access modifiers changed from: 0000 */
    public byte e() {
        return zza.zze(this.g);
    }

    /* access modifiers changed from: 0000 */
    public byte f() {
        return zza.zze(this.h);
    }

    /* access modifiers changed from: 0000 */
    public byte g() {
        return zza.zze(this.i);
    }

    public Boolean getAmbientEnabled() {
        return this.n;
    }

    public CameraPosition getCamera() {
        return this.e;
    }

    public Boolean getCompassEnabled() {
        return this.g;
    }

    public LatLngBounds getLatLngBoundsForCameraTarget() {
        return this.q;
    }

    public Boolean getLiteMode() {
        return this.l;
    }

    public Boolean getMapToolbarEnabled() {
        return this.m;
    }

    public int getMapType() {
        return this.d;
    }

    public Float getMaxZoomPreference() {
        return this.p;
    }

    public Float getMinZoomPreference() {
        return this.o;
    }

    public Boolean getRotateGesturesEnabled() {
        return this.k;
    }

    public Boolean getScrollGesturesEnabled() {
        return this.h;
    }

    public Boolean getTiltGesturesEnabled() {
        return this.j;
    }

    public Boolean getUseViewLifecycleInFragment() {
        return this.c;
    }

    public Boolean getZOrderOnTop() {
        return this.b;
    }

    public Boolean getZoomControlsEnabled() {
        return this.f;
    }

    public Boolean getZoomGesturesEnabled() {
        return this.i;
    }

    /* access modifiers changed from: 0000 */
    public byte h() {
        return zza.zze(this.j);
    }

    /* access modifiers changed from: 0000 */
    public byte i() {
        return zza.zze(this.k);
    }

    /* access modifiers changed from: 0000 */
    public byte j() {
        return zza.zze(this.l);
    }

    /* access modifiers changed from: 0000 */
    public byte k() {
        return zza.zze(this.m);
    }

    /* access modifiers changed from: 0000 */
    public byte l() {
        return zza.zze(this.n);
    }

    public GoogleMapOptions latLngBoundsForCameraTarget(LatLngBounds latLngBounds) {
        this.q = latLngBounds;
        return this;
    }

    public GoogleMapOptions liteMode(boolean z) {
        this.l = Boolean.valueOf(z);
        return this;
    }

    public GoogleMapOptions mapToolbarEnabled(boolean z) {
        this.m = Boolean.valueOf(z);
        return this;
    }

    public GoogleMapOptions mapType(int i2) {
        this.d = i2;
        return this;
    }

    public GoogleMapOptions maxZoomPreference(float f2) {
        this.p = Float.valueOf(f2);
        return this;
    }

    public GoogleMapOptions minZoomPreference(float f2) {
        this.o = Float.valueOf(f2);
        return this;
    }

    public GoogleMapOptions rotateGesturesEnabled(boolean z) {
        this.k = Boolean.valueOf(z);
        return this;
    }

    public GoogleMapOptions scrollGesturesEnabled(boolean z) {
        this.h = Boolean.valueOf(z);
        return this;
    }

    public GoogleMapOptions tiltGesturesEnabled(boolean z) {
        this.j = Boolean.valueOf(z);
        return this;
    }

    public GoogleMapOptions useViewLifecycleInFragment(boolean z) {
        this.c = Boolean.valueOf(z);
        return this;
    }

    public void writeToParcel(Parcel parcel, int i2) {
        zza.a(this, parcel, i2);
    }

    public GoogleMapOptions zOrderOnTop(boolean z) {
        this.b = Boolean.valueOf(z);
        return this;
    }

    public GoogleMapOptions zoomControlsEnabled(boolean z) {
        this.f = Boolean.valueOf(z);
        return this;
    }

    public GoogleMapOptions zoomGesturesEnabled(boolean z) {
        this.i = Boolean.valueOf(z);
        return this;
    }
}
