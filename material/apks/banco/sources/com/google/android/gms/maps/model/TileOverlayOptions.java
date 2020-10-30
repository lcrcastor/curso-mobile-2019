package com.google.android.gms.maps.model;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.zzac;
import com.google.android.gms.maps.model.internal.zzi;
import com.google.android.gms.maps.model.internal.zzi.zza;

public final class TileOverlayOptions extends AbstractSafeParcelable {
    public static final zzp CREATOR = new zzp();
    private final int a;
    /* access modifiers changed from: private */
    public zzi b;
    private TileProvider c;
    private boolean d;
    private float e;
    private boolean f;
    private float g;

    public TileOverlayOptions() {
        this.d = true;
        this.f = true;
        this.g = BitmapDescriptorFactory.HUE_RED;
        this.a = 1;
    }

    TileOverlayOptions(int i, IBinder iBinder, boolean z, float f2, boolean z2, float f3) {
        this.d = true;
        this.f = true;
        this.g = BitmapDescriptorFactory.HUE_RED;
        this.a = i;
        this.b = zza.zzjk(iBinder);
        this.c = this.b == null ? null : new TileProvider() {
            private final zzi b = TileOverlayOptions.this.b;

            public Tile getTile(int i, int i2, int i3) {
                try {
                    return this.b.getTile(i, i2, i3);
                } catch (RemoteException unused) {
                    return null;
                }
            }
        };
        this.d = z;
        this.e = f2;
        this.f = z2;
        this.g = f3;
    }

    /* access modifiers changed from: 0000 */
    public int a() {
        return this.a;
    }

    /* access modifiers changed from: 0000 */
    public IBinder b() {
        return this.b.asBinder();
    }

    public TileOverlayOptions fadeIn(boolean z) {
        this.f = z;
        return this;
    }

    public boolean getFadeIn() {
        return this.f;
    }

    public TileProvider getTileProvider() {
        return this.c;
    }

    public float getTransparency() {
        return this.g;
    }

    public float getZIndex() {
        return this.e;
    }

    public boolean isVisible() {
        return this.d;
    }

    public TileOverlayOptions tileProvider(final TileProvider tileProvider) {
        this.c = tileProvider;
        this.b = this.c == null ? null : new zza() {
            public Tile getTile(int i, int i2, int i3) {
                return tileProvider.getTile(i, i2, i3);
            }
        };
        return this;
    }

    public TileOverlayOptions transparency(float f2) {
        zzac.zzb(f2 >= BitmapDescriptorFactory.HUE_RED && f2 <= 1.0f, (Object) "Transparency must be in the range [0..1]");
        this.g = f2;
        return this;
    }

    public TileOverlayOptions visible(boolean z) {
        this.d = z;
        return this;
    }

    public void writeToParcel(Parcel parcel, int i) {
        zzp.a(this, parcel, i);
    }

    public TileOverlayOptions zIndex(float f2) {
        this.e = f2;
        return this;
    }
}
