package com.google.android.gms.maps.internal;

import android.location.Location;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import com.google.android.gms.dynamic.zzd;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.TileOverlayOptions;
import com.google.android.gms.maps.model.internal.IPolylineDelegate;
import com.google.android.gms.maps.model.internal.zzb;
import com.google.android.gms.maps.model.internal.zzc;
import com.google.android.gms.maps.model.internal.zzf;
import com.google.android.gms.maps.model.internal.zzg;
import com.google.android.gms.maps.model.internal.zzh;

public interface IGoogleMapDelegate extends IInterface {

    public static abstract class zza extends Binder implements IGoogleMapDelegate {

        /* renamed from: com.google.android.gms.maps.internal.IGoogleMapDelegate$zza$zza reason: collision with other inner class name */
        static class C0041zza implements IGoogleMapDelegate {
            private IBinder a;

            C0041zza(IBinder iBinder) {
                this.a = iBinder;
            }

            public zzb addCircle(CircleOptions circleOptions) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    if (circleOptions != null) {
                        obtain.writeInt(1);
                        circleOptions.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.a.transact(35, obtain, obtain2, 0);
                    obtain2.readException();
                    return com.google.android.gms.maps.model.internal.zzb.zza.zzjc(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public zzc addGroundOverlay(GroundOverlayOptions groundOverlayOptions) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    if (groundOverlayOptions != null) {
                        obtain.writeInt(1);
                        groundOverlayOptions.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.a.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return com.google.android.gms.maps.model.internal.zzc.zza.zzjd(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public zzf addMarker(MarkerOptions markerOptions) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    if (markerOptions != null) {
                        obtain.writeInt(1);
                        markerOptions.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.a.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return com.google.android.gms.maps.model.internal.zzf.zza.zzjg(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public zzg addPolygon(PolygonOptions polygonOptions) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    if (polygonOptions != null) {
                        obtain.writeInt(1);
                        polygonOptions.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.a.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return com.google.android.gms.maps.model.internal.zzg.zza.zzjh(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public IPolylineDelegate addPolyline(PolylineOptions polylineOptions) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    if (polylineOptions != null) {
                        obtain.writeInt(1);
                        polylineOptions.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.a.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return com.google.android.gms.maps.model.internal.IPolylineDelegate.zza.zzji(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public zzh addTileOverlay(TileOverlayOptions tileOverlayOptions) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    if (tileOverlayOptions != null) {
                        obtain.writeInt(1);
                        tileOverlayOptions.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.a.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    return com.google.android.gms.maps.model.internal.zzh.zza.zzjj(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void animateCamera(zzd zzd) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    obtain.writeStrongBinder(zzd != null ? zzd.asBinder() : null);
                    this.a.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void animateCameraWithCallback(zzd zzd, zzb zzb) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    IBinder iBinder = null;
                    obtain.writeStrongBinder(zzd != null ? zzd.asBinder() : null);
                    if (zzb != null) {
                        iBinder = zzb.asBinder();
                    }
                    obtain.writeStrongBinder(iBinder);
                    this.a.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void animateCameraWithDurationAndCallback(zzd zzd, int i, zzb zzb) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    IBinder iBinder = null;
                    obtain.writeStrongBinder(zzd != null ? zzd.asBinder() : null);
                    obtain.writeInt(i);
                    if (zzb != null) {
                        iBinder = zzb.asBinder();
                    }
                    obtain.writeStrongBinder(iBinder);
                    this.a.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public IBinder asBinder() {
                return this.a;
            }

            public void clear() {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    this.a.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public CameraPosition getCameraPosition() {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    this.a.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0 ? (CameraPosition) CameraPosition.CREATOR.createFromParcel(obtain2) : null;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public com.google.android.gms.maps.model.internal.zzd getFocusedBuilding() {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    this.a.transact(44, obtain, obtain2, 0);
                    obtain2.readException();
                    return com.google.android.gms.maps.model.internal.zzd.zza.zzje(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void getMapAsync(zzt zzt) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    obtain.writeStrongBinder(zzt != null ? zzt.asBinder() : null);
                    this.a.transact(53, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public int getMapType() {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    this.a.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public float getMaxZoomLevel() {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    this.a.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readFloat();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public float getMinZoomLevel() {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    this.a.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readFloat();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public Location getMyLocation() {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    this.a.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0 ? (Location) Location.CREATOR.createFromParcel(obtain2) : null;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public IProjectionDelegate getProjection() {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    this.a.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                    return com.google.android.gms.maps.internal.IProjectionDelegate.zza.zziv(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public IUiSettingsDelegate getUiSettings() {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    this.a.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                    return com.google.android.gms.maps.internal.IUiSettingsDelegate.zza.zzja(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public boolean isBuildingsEnabled() {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    boolean z = false;
                    this.a.transact(40, obtain, obtain2, 0);
                    obtain2.readException();
                    if (obtain2.readInt() != 0) {
                        z = true;
                    }
                    return z;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public boolean isIndoorEnabled() {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    boolean z = false;
                    this.a.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                    if (obtain2.readInt() != 0) {
                        z = true;
                    }
                    return z;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public boolean isMyLocationEnabled() {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    boolean z = false;
                    this.a.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                    if (obtain2.readInt() != 0) {
                        z = true;
                    }
                    return z;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public boolean isTrafficEnabled() {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    boolean z = false;
                    this.a.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                    if (obtain2.readInt() != 0) {
                        z = true;
                    }
                    return z;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void moveCamera(zzd zzd) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    obtain.writeStrongBinder(zzd != null ? zzd.asBinder() : null);
                    this.a.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void onCreate(Bundle bundle) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    if (bundle != null) {
                        obtain.writeInt(1);
                        bundle.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.a.transact(54, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void onDestroy() {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    this.a.transact(57, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void onEnterAmbient(Bundle bundle) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    if (bundle != null) {
                        obtain.writeInt(1);
                        bundle.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.a.transact(81, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void onExitAmbient() {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    this.a.transact(82, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void onLowMemory() {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    this.a.transact(58, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void onPause() {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    this.a.transact(56, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void onResume() {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    this.a.transact(55, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void onSaveInstanceState(Bundle bundle) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    if (bundle != null) {
                        obtain.writeInt(1);
                        bundle.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.a.transact(60, obtain, obtain2, 0);
                    obtain2.readException();
                    if (obtain2.readInt() != 0) {
                        bundle.readFromParcel(obtain2);
                    }
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void onStart() {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    this.a.transact(101, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void onStop() {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    this.a.transact(102, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void resetMinMaxZoomPreference() {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    this.a.transact(94, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void setBuildingsEnabled(boolean z) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    obtain.writeInt(z ? 1 : 0);
                    this.a.transact(41, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void setContentDescription(String str) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    obtain.writeString(str);
                    this.a.transact(61, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public boolean setIndoorEnabled(boolean z) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    obtain.writeInt(z ? 1 : 0);
                    boolean z2 = false;
                    this.a.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                    if (obtain2.readInt() != 0) {
                        z2 = true;
                    }
                    return z2;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void setInfoWindowAdapter(zzd zzd) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    obtain.writeStrongBinder(zzd != null ? zzd.asBinder() : null);
                    this.a.transact(33, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void setLatLngBoundsForCameraTarget(LatLngBounds latLngBounds) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    if (latLngBounds != null) {
                        obtain.writeInt(1);
                        latLngBounds.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.a.transact(95, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void setLocationSource(ILocationSourceDelegate iLocationSourceDelegate) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    obtain.writeStrongBinder(iLocationSourceDelegate != null ? iLocationSourceDelegate.asBinder() : null);
                    this.a.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public boolean setMapStyle(MapStyleOptions mapStyleOptions) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    boolean z = true;
                    if (mapStyleOptions != null) {
                        obtain.writeInt(1);
                        mapStyleOptions.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.a.transact(91, obtain, obtain2, 0);
                    obtain2.readException();
                    if (obtain2.readInt() == 0) {
                        z = false;
                    }
                    return z;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void setMapType(int i) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    obtain.writeInt(i);
                    this.a.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void setMaxZoomPreference(float f) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    obtain.writeFloat(f);
                    this.a.transact(93, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void setMinZoomPreference(float f) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    obtain.writeFloat(f);
                    this.a.transact(92, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void setMyLocationEnabled(boolean z) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    obtain.writeInt(z ? 1 : 0);
                    this.a.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void setOnCameraChangeListener(zze zze) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    obtain.writeStrongBinder(zze != null ? zze.asBinder() : null);
                    this.a.transact(27, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void setOnCameraIdleListener(zzf zzf) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    obtain.writeStrongBinder(zzf != null ? zzf.asBinder() : null);
                    this.a.transact(99, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void setOnCameraMoveCanceledListener(zzg zzg) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    obtain.writeStrongBinder(zzg != null ? zzg.asBinder() : null);
                    this.a.transact(98, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void setOnCameraMoveListener(zzh zzh) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    obtain.writeStrongBinder(zzh != null ? zzh.asBinder() : null);
                    this.a.transact(97, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void setOnCameraMoveStartedListener(zzi zzi) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    obtain.writeStrongBinder(zzi != null ? zzi.asBinder() : null);
                    this.a.transact(96, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void setOnCircleClickListener(zzj zzj) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    obtain.writeStrongBinder(zzj != null ? zzj.asBinder() : null);
                    this.a.transact(89, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void setOnGroundOverlayClickListener(zzk zzk) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    obtain.writeStrongBinder(zzk != null ? zzk.asBinder() : null);
                    this.a.transact(83, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void setOnIndoorStateChangeListener(zzl zzl) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    obtain.writeStrongBinder(zzl != null ? zzl.asBinder() : null);
                    this.a.transact(45, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void setOnInfoWindowClickListener(zzm zzm) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    obtain.writeStrongBinder(zzm != null ? zzm.asBinder() : null);
                    this.a.transact(32, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void setOnInfoWindowCloseListener(zzn zzn) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    obtain.writeStrongBinder(zzn != null ? zzn.asBinder() : null);
                    this.a.transact(86, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void setOnInfoWindowLongClickListener(zzo zzo) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    obtain.writeStrongBinder(zzo != null ? zzo.asBinder() : null);
                    this.a.transact(84, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void setOnMapClickListener(zzq zzq) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    obtain.writeStrongBinder(zzq != null ? zzq.asBinder() : null);
                    this.a.transact(28, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void setOnMapLoadedCallback(zzr zzr) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    obtain.writeStrongBinder(zzr != null ? zzr.asBinder() : null);
                    this.a.transact(42, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void setOnMapLongClickListener(zzs zzs) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    obtain.writeStrongBinder(zzs != null ? zzs.asBinder() : null);
                    this.a.transact(29, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void setOnMarkerClickListener(zzu zzu) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    obtain.writeStrongBinder(zzu != null ? zzu.asBinder() : null);
                    this.a.transact(30, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void setOnMarkerDragListener(zzv zzv) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    obtain.writeStrongBinder(zzv != null ? zzv.asBinder() : null);
                    this.a.transact(31, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void setOnMyLocationButtonClickListener(zzw zzw) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    obtain.writeStrongBinder(zzw != null ? zzw.asBinder() : null);
                    this.a.transact(37, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void setOnMyLocationChangeListener(zzx zzx) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    obtain.writeStrongBinder(zzx != null ? zzx.asBinder() : null);
                    this.a.transact(36, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void setOnPoiClickListener(zzy zzy) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    obtain.writeStrongBinder(zzy != null ? zzy.asBinder() : null);
                    this.a.transact(80, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void setOnPolygonClickListener(zzz zzz) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    obtain.writeStrongBinder(zzz != null ? zzz.asBinder() : null);
                    this.a.transact(85, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void setOnPolylineClickListener(zzaa zzaa) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    obtain.writeStrongBinder(zzaa != null ? zzaa.asBinder() : null);
                    this.a.transact(87, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void setPadding(int i, int i2, int i3, int i4) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    this.a.transact(39, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void setSpotlightLayer(byte[] bArr) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    obtain.writeByteArray(bArr);
                    this.a.transact(90, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void setTrafficEnabled(boolean z) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    obtain.writeInt(z ? 1 : 0);
                    this.a.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void setWatermarkEnabled(boolean z) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    obtain.writeInt(z ? 1 : 0);
                    this.a.transact(51, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void snapshot(zzag zzag, zzd zzd) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    IBinder iBinder = null;
                    obtain.writeStrongBinder(zzag != null ? zzag.asBinder() : null);
                    if (zzd != null) {
                        iBinder = zzd.asBinder();
                    }
                    obtain.writeStrongBinder(iBinder);
                    this.a.transact(38, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void snapshotForTest(zzag zzag) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    obtain.writeStrongBinder(zzag != null ? zzag.asBinder() : null);
                    this.a.transact(71, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void stopAnimation() {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    this.a.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public boolean useViewLifecycleWhenInFragment() {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    boolean z = false;
                    this.a.transact(59, obtain, obtain2, 0);
                    obtain2.readException();
                    if (obtain2.readInt() != 0) {
                        z = true;
                    }
                    return z;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public static IGoogleMapDelegate zzho(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
            return (queryLocalInterface == null || !(queryLocalInterface instanceof IGoogleMapDelegate)) ? new C0041zza(iBinder) : (IGoogleMapDelegate) queryLocalInterface;
        }

        /* JADX WARNING: type inference failed for: r0v3 */
        /* JADX WARNING: type inference failed for: r0v4, types: [android.os.IBinder] */
        /* JADX WARNING: type inference failed for: r0v5, types: [android.os.IBinder] */
        /* JADX WARNING: type inference failed for: r0v6, types: [android.os.IBinder] */
        /* JADX WARNING: type inference failed for: r0v7, types: [android.os.IBinder] */
        /* JADX WARNING: type inference failed for: r0v8, types: [android.os.IBinder] */
        /* JADX WARNING: type inference failed for: r0v9, types: [android.os.IBinder] */
        /* JADX WARNING: type inference failed for: r0v10, types: [android.os.IBinder] */
        /* JADX WARNING: type inference failed for: r0v11, types: [android.os.IBinder] */
        /* JADX WARNING: type inference failed for: r0v12, types: [android.os.IBinder] */
        /* JADX WARNING: type inference failed for: r0v13, types: [android.os.IBinder] */
        /* JADX WARNING: type inference failed for: r0v14, types: [android.os.IBinder] */
        /* JADX WARNING: type inference failed for: r0v15, types: [android.os.IBinder] */
        /* JADX WARNING: type inference failed for: r0v16, types: [android.os.IBinder] */
        /* JADX WARNING: type inference failed for: r0v17, types: [android.os.IBinder] */
        /* JADX WARNING: type inference failed for: r0v18, types: [android.os.IBinder] */
        /* JADX WARNING: type inference failed for: r0v19, types: [android.os.IBinder] */
        /* JADX WARNING: type inference failed for: r0v21, types: [android.os.IBinder] */
        /* JADX WARNING: type inference failed for: r0v22, types: [android.os.IBinder] */
        /* JADX WARNING: type inference failed for: r0v23, types: [android.os.Bundle] */
        /* JADX WARNING: type inference failed for: r0v25, types: [android.os.Bundle] */
        /* JADX WARNING: type inference failed for: r0v26, types: [android.os.Bundle] */
        /* JADX WARNING: type inference failed for: r0v28, types: [android.os.Bundle] */
        /* JADX WARNING: type inference failed for: r0v29, types: [android.os.Bundle] */
        /* JADX WARNING: type inference failed for: r0v31, types: [android.os.Bundle] */
        /* JADX WARNING: type inference failed for: r0v32, types: [com.google.android.gms.maps.model.MapStyleOptions] */
        /* JADX WARNING: type inference failed for: r0v34, types: [com.google.android.gms.maps.model.MapStyleOptions] */
        /* JADX WARNING: type inference failed for: r0v35, types: [com.google.android.gms.maps.model.LatLngBounds] */
        /* JADX WARNING: type inference failed for: r0v37, types: [com.google.android.gms.maps.model.LatLngBounds] */
        /* JADX WARNING: type inference failed for: r0v38 */
        /* JADX WARNING: type inference failed for: r0v39 */
        /* JADX WARNING: type inference failed for: r0v40 */
        /* JADX WARNING: type inference failed for: r0v41 */
        /* JADX WARNING: type inference failed for: r0v42 */
        /* JADX WARNING: type inference failed for: r0v43 */
        /* JADX WARNING: type inference failed for: r0v44 */
        /* JADX WARNING: type inference failed for: r0v45 */
        /* JADX WARNING: type inference failed for: r0v46 */
        /* JADX WARNING: type inference failed for: r0v47 */
        /* JADX WARNING: type inference failed for: r0v48 */
        /* JADX WARNING: type inference failed for: r0v49 */
        /* JADX WARNING: type inference failed for: r0v50 */
        /* JADX WARNING: type inference failed for: r0v51 */
        /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r0v3
          assigns: [?[int, float, boolean, short, byte, char, OBJECT, ARRAY], android.os.IBinder, android.os.Bundle, com.google.android.gms.maps.model.MapStyleOptions, com.google.android.gms.maps.model.LatLngBounds]
          uses: [android.os.IBinder, android.os.Bundle, ?[int, boolean, OBJECT, ARRAY, byte, short, char], com.google.android.gms.maps.model.MapStyleOptions, com.google.android.gms.maps.model.LatLngBounds]
          mth insns count: 615
        	at jadx.core.dex.visitors.typeinference.TypeSearch.fillTypeCandidates(TypeSearch.java:237)
        	at java.util.ArrayList.forEach(ArrayList.java:1249)
        	at jadx.core.dex.visitors.typeinference.TypeSearch.run(TypeSearch.java:53)
        	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runMultiVariableSearch(TypeInferenceVisitor.java:99)
        	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:92)
        	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:27)
        	at jadx.core.dex.visitors.DepthTraversal.lambda$visit$1(DepthTraversal.java:14)
        	at java.util.ArrayList.forEach(ArrayList.java:1249)
        	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
        	at jadx.core.dex.visitors.DepthTraversal.lambda$visit$0(DepthTraversal.java:13)
        	at java.util.ArrayList.forEach(ArrayList.java:1249)
        	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:13)
        	at jadx.core.ProcessClass.process(ProcessClass.java:30)
        	at jadx.core.ProcessClass.lambda$processDependencies$0(ProcessClass.java:49)
        	at java.util.ArrayList.forEach(ArrayList.java:1249)
        	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:49)
        	at jadx.core.ProcessClass.process(ProcessClass.java:35)
        	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:311)
        	at jadx.api.JavaClass.decompile(JavaClass.java:62)
        	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:217)
         */
        /* JADX WARNING: Unknown variable types count: 15 */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTransact(int r4, android.os.Parcel r5, android.os.Parcel r6, int r7) {
            /*
                r3 = this;
                r0 = 51
                r1 = 0
                r2 = 1
                if (r4 == r0) goto L_0x0647
                r0 = 71
                if (r4 == r0) goto L_0x0633
                r0 = 1598968902(0x5f4e5446, float:1.4867585E19)
                if (r4 == r0) goto L_0x062d
                r0 = 0
                switch(r4) {
                    case 1: goto L_0x0614;
                    case 2: goto L_0x0604;
                    case 3: goto L_0x05f4;
                    case 4: goto L_0x05e0;
                    case 5: goto L_0x05cc;
                    case 6: goto L_0x05b0;
                    case 7: goto L_0x0590;
                    case 8: goto L_0x0584;
                    case 9: goto L_0x055e;
                    case 10: goto L_0x0538;
                    case 11: goto L_0x0512;
                    case 12: goto L_0x04ec;
                    case 13: goto L_0x04c6;
                    case 14: goto L_0x04ba;
                    case 15: goto L_0x04aa;
                    case 16: goto L_0x049a;
                    case 17: goto L_0x048a;
                    case 18: goto L_0x0477;
                    case 19: goto L_0x0467;
                    case 20: goto L_0x0450;
                    case 21: goto L_0x0440;
                    case 22: goto L_0x042d;
                    case 23: goto L_0x0414;
                    case 24: goto L_0x0400;
                    case 25: goto L_0x03ea;
                    case 26: goto L_0x03d4;
                    case 27: goto L_0x03c0;
                    case 28: goto L_0x03ac;
                    case 29: goto L_0x0398;
                    case 30: goto L_0x0384;
                    case 31: goto L_0x0370;
                    case 32: goto L_0x035c;
                    case 33: goto L_0x0348;
                    default: goto L_0x0013;
                }
            L_0x0013:
                switch(r4) {
                    case 35: goto L_0x0322;
                    case 36: goto L_0x030e;
                    case 37: goto L_0x02fa;
                    case 38: goto L_0x02de;
                    case 39: goto L_0x02c2;
                    case 40: goto L_0x02b2;
                    case 41: goto L_0x029f;
                    case 42: goto L_0x028b;
                    default: goto L_0x0016;
                }
            L_0x0016:
                switch(r4) {
                    case 44: goto L_0x0275;
                    case 45: goto L_0x0261;
                    default: goto L_0x0019;
                }
            L_0x0019:
                switch(r4) {
                    case 53: goto L_0x024d;
                    case 54: goto L_0x0232;
                    case 55: goto L_0x0226;
                    case 56: goto L_0x021a;
                    case 57: goto L_0x020e;
                    case 58: goto L_0x0202;
                    case 59: goto L_0x01f2;
                    case 60: goto L_0x01cb;
                    case 61: goto L_0x01bb;
                    default: goto L_0x001c;
                }
            L_0x001c:
                switch(r4) {
                    case 80: goto L_0x01a7;
                    case 81: goto L_0x018c;
                    case 82: goto L_0x0180;
                    case 83: goto L_0x016c;
                    case 84: goto L_0x0158;
                    case 85: goto L_0x0144;
                    case 86: goto L_0x0130;
                    case 87: goto L_0x011c;
                    default: goto L_0x001f;
                }
            L_0x001f:
                switch(r4) {
                    case 89: goto L_0x0108;
                    case 90: goto L_0x00f8;
                    case 91: goto L_0x00d9;
                    case 92: goto L_0x00c9;
                    case 93: goto L_0x00b9;
                    case 94: goto L_0x00ad;
                    case 95: goto L_0x0092;
                    case 96: goto L_0x007e;
                    case 97: goto L_0x006a;
                    case 98: goto L_0x0056;
                    case 99: goto L_0x0042;
                    default: goto L_0x0022;
                }
            L_0x0022:
                switch(r4) {
                    case 101: goto L_0x0036;
                    case 102: goto L_0x002a;
                    default: goto L_0x0025;
                }
            L_0x0025:
                boolean r4 = super.onTransact(r4, r5, r6, r7)
                return r4
            L_0x002a:
                java.lang.String r4 = "com.google.android.gms.maps.internal.IGoogleMapDelegate"
                r5.enforceInterface(r4)
                r3.onStop()
                r6.writeNoException()
                return r2
            L_0x0036:
                java.lang.String r4 = "com.google.android.gms.maps.internal.IGoogleMapDelegate"
                r5.enforceInterface(r4)
                r3.onStart()
                r6.writeNoException()
                return r2
            L_0x0042:
                java.lang.String r4 = "com.google.android.gms.maps.internal.IGoogleMapDelegate"
                r5.enforceInterface(r4)
                android.os.IBinder r4 = r5.readStrongBinder()
                com.google.android.gms.maps.internal.zzf r4 = com.google.android.gms.maps.internal.zzf.zza.zzhu(r4)
                r3.setOnCameraIdleListener(r4)
                r6.writeNoException()
                return r2
            L_0x0056:
                java.lang.String r4 = "com.google.android.gms.maps.internal.IGoogleMapDelegate"
                r5.enforceInterface(r4)
                android.os.IBinder r4 = r5.readStrongBinder()
                com.google.android.gms.maps.internal.zzg r4 = com.google.android.gms.maps.internal.zzg.zza.zzhv(r4)
                r3.setOnCameraMoveCanceledListener(r4)
                r6.writeNoException()
                return r2
            L_0x006a:
                java.lang.String r4 = "com.google.android.gms.maps.internal.IGoogleMapDelegate"
                r5.enforceInterface(r4)
                android.os.IBinder r4 = r5.readStrongBinder()
                com.google.android.gms.maps.internal.zzh r4 = com.google.android.gms.maps.internal.zzh.zza.zzhw(r4)
                r3.setOnCameraMoveListener(r4)
                r6.writeNoException()
                return r2
            L_0x007e:
                java.lang.String r4 = "com.google.android.gms.maps.internal.IGoogleMapDelegate"
                r5.enforceInterface(r4)
                android.os.IBinder r4 = r5.readStrongBinder()
                com.google.android.gms.maps.internal.zzi r4 = com.google.android.gms.maps.internal.zzi.zza.zzhx(r4)
                r3.setOnCameraMoveStartedListener(r4)
                r6.writeNoException()
                return r2
            L_0x0092:
                java.lang.String r4 = "com.google.android.gms.maps.internal.IGoogleMapDelegate"
                r5.enforceInterface(r4)
                int r4 = r5.readInt()
                if (r4 == 0) goto L_0x00a6
                com.google.android.gms.maps.model.zzd r4 = com.google.android.gms.maps.model.LatLngBounds.CREATOR
                java.lang.Object r4 = r4.createFromParcel(r5)
                r0 = r4
                com.google.android.gms.maps.model.LatLngBounds r0 = (com.google.android.gms.maps.model.LatLngBounds) r0
            L_0x00a6:
                r3.setLatLngBoundsForCameraTarget(r0)
                r6.writeNoException()
                return r2
            L_0x00ad:
                java.lang.String r4 = "com.google.android.gms.maps.internal.IGoogleMapDelegate"
                r5.enforceInterface(r4)
                r3.resetMinMaxZoomPreference()
                r6.writeNoException()
                return r2
            L_0x00b9:
                java.lang.String r4 = "com.google.android.gms.maps.internal.IGoogleMapDelegate"
                r5.enforceInterface(r4)
                float r4 = r5.readFloat()
                r3.setMaxZoomPreference(r4)
                r6.writeNoException()
                return r2
            L_0x00c9:
                java.lang.String r4 = "com.google.android.gms.maps.internal.IGoogleMapDelegate"
                r5.enforceInterface(r4)
                float r4 = r5.readFloat()
                r3.setMinZoomPreference(r4)
                r6.writeNoException()
                return r2
            L_0x00d9:
                java.lang.String r4 = "com.google.android.gms.maps.internal.IGoogleMapDelegate"
                r5.enforceInterface(r4)
                int r4 = r5.readInt()
                if (r4 == 0) goto L_0x00ed
                com.google.android.gms.maps.model.zzf r4 = com.google.android.gms.maps.model.MapStyleOptions.CREATOR
                java.lang.Object r4 = r4.createFromParcel(r5)
                r0 = r4
                com.google.android.gms.maps.model.MapStyleOptions r0 = (com.google.android.gms.maps.model.MapStyleOptions) r0
            L_0x00ed:
                boolean r4 = r3.setMapStyle(r0)
                r6.writeNoException()
                r6.writeInt(r4)
                return r2
            L_0x00f8:
                java.lang.String r4 = "com.google.android.gms.maps.internal.IGoogleMapDelegate"
                r5.enforceInterface(r4)
                byte[] r4 = r5.createByteArray()
                r3.setSpotlightLayer(r4)
                r6.writeNoException()
                return r2
            L_0x0108:
                java.lang.String r4 = "com.google.android.gms.maps.internal.IGoogleMapDelegate"
                r5.enforceInterface(r4)
                android.os.IBinder r4 = r5.readStrongBinder()
                com.google.android.gms.maps.internal.zzj r4 = com.google.android.gms.maps.internal.zzj.zza.zzhy(r4)
                r3.setOnCircleClickListener(r4)
                r6.writeNoException()
                return r2
            L_0x011c:
                java.lang.String r4 = "com.google.android.gms.maps.internal.IGoogleMapDelegate"
                r5.enforceInterface(r4)
                android.os.IBinder r4 = r5.readStrongBinder()
                com.google.android.gms.maps.internal.zzaa r4 = com.google.android.gms.maps.internal.zzaa.zza.zzip(r4)
                r3.setOnPolylineClickListener(r4)
                r6.writeNoException()
                return r2
            L_0x0130:
                java.lang.String r4 = "com.google.android.gms.maps.internal.IGoogleMapDelegate"
                r5.enforceInterface(r4)
                android.os.IBinder r4 = r5.readStrongBinder()
                com.google.android.gms.maps.internal.zzn r4 = com.google.android.gms.maps.internal.zzn.zza.zzic(r4)
                r3.setOnInfoWindowCloseListener(r4)
                r6.writeNoException()
                return r2
            L_0x0144:
                java.lang.String r4 = "com.google.android.gms.maps.internal.IGoogleMapDelegate"
                r5.enforceInterface(r4)
                android.os.IBinder r4 = r5.readStrongBinder()
                com.google.android.gms.maps.internal.zzz r4 = com.google.android.gms.maps.internal.zzz.zza.zzio(r4)
                r3.setOnPolygonClickListener(r4)
                r6.writeNoException()
                return r2
            L_0x0158:
                java.lang.String r4 = "com.google.android.gms.maps.internal.IGoogleMapDelegate"
                r5.enforceInterface(r4)
                android.os.IBinder r4 = r5.readStrongBinder()
                com.google.android.gms.maps.internal.zzo r4 = com.google.android.gms.maps.internal.zzo.zza.zzid(r4)
                r3.setOnInfoWindowLongClickListener(r4)
                r6.writeNoException()
                return r2
            L_0x016c:
                java.lang.String r4 = "com.google.android.gms.maps.internal.IGoogleMapDelegate"
                r5.enforceInterface(r4)
                android.os.IBinder r4 = r5.readStrongBinder()
                com.google.android.gms.maps.internal.zzk r4 = com.google.android.gms.maps.internal.zzk.zza.zzhz(r4)
                r3.setOnGroundOverlayClickListener(r4)
                r6.writeNoException()
                return r2
            L_0x0180:
                java.lang.String r4 = "com.google.android.gms.maps.internal.IGoogleMapDelegate"
                r5.enforceInterface(r4)
                r3.onExitAmbient()
                r6.writeNoException()
                return r2
            L_0x018c:
                java.lang.String r4 = "com.google.android.gms.maps.internal.IGoogleMapDelegate"
                r5.enforceInterface(r4)
                int r4 = r5.readInt()
                if (r4 == 0) goto L_0x01a0
                android.os.Parcelable$Creator r4 = android.os.Bundle.CREATOR
                java.lang.Object r4 = r4.createFromParcel(r5)
                r0 = r4
                android.os.Bundle r0 = (android.os.Bundle) r0
            L_0x01a0:
                r3.onEnterAmbient(r0)
                r6.writeNoException()
                return r2
            L_0x01a7:
                java.lang.String r4 = "com.google.android.gms.maps.internal.IGoogleMapDelegate"
                r5.enforceInterface(r4)
                android.os.IBinder r4 = r5.readStrongBinder()
                com.google.android.gms.maps.internal.zzy r4 = com.google.android.gms.maps.internal.zzy.zza.zzin(r4)
                r3.setOnPoiClickListener(r4)
                r6.writeNoException()
                return r2
            L_0x01bb:
                java.lang.String r4 = "com.google.android.gms.maps.internal.IGoogleMapDelegate"
                r5.enforceInterface(r4)
                java.lang.String r4 = r5.readString()
                r3.setContentDescription(r4)
                r6.writeNoException()
                return r2
            L_0x01cb:
                java.lang.String r4 = "com.google.android.gms.maps.internal.IGoogleMapDelegate"
                r5.enforceInterface(r4)
                int r4 = r5.readInt()
                if (r4 == 0) goto L_0x01df
                android.os.Parcelable$Creator r4 = android.os.Bundle.CREATOR
                java.lang.Object r4 = r4.createFromParcel(r5)
                r0 = r4
                android.os.Bundle r0 = (android.os.Bundle) r0
            L_0x01df:
                r3.onSaveInstanceState(r0)
                r6.writeNoException()
                if (r0 == 0) goto L_0x01ee
                r6.writeInt(r2)
                r0.writeToParcel(r6, r2)
                return r2
            L_0x01ee:
                r6.writeInt(r1)
                return r2
            L_0x01f2:
                java.lang.String r4 = "com.google.android.gms.maps.internal.IGoogleMapDelegate"
                r5.enforceInterface(r4)
                boolean r4 = r3.useViewLifecycleWhenInFragment()
                r6.writeNoException()
                r6.writeInt(r4)
                return r2
            L_0x0202:
                java.lang.String r4 = "com.google.android.gms.maps.internal.IGoogleMapDelegate"
                r5.enforceInterface(r4)
                r3.onLowMemory()
                r6.writeNoException()
                return r2
            L_0x020e:
                java.lang.String r4 = "com.google.android.gms.maps.internal.IGoogleMapDelegate"
                r5.enforceInterface(r4)
                r3.onDestroy()
                r6.writeNoException()
                return r2
            L_0x021a:
                java.lang.String r4 = "com.google.android.gms.maps.internal.IGoogleMapDelegate"
                r5.enforceInterface(r4)
                r3.onPause()
                r6.writeNoException()
                return r2
            L_0x0226:
                java.lang.String r4 = "com.google.android.gms.maps.internal.IGoogleMapDelegate"
                r5.enforceInterface(r4)
                r3.onResume()
                r6.writeNoException()
                return r2
            L_0x0232:
                java.lang.String r4 = "com.google.android.gms.maps.internal.IGoogleMapDelegate"
                r5.enforceInterface(r4)
                int r4 = r5.readInt()
                if (r4 == 0) goto L_0x0246
                android.os.Parcelable$Creator r4 = android.os.Bundle.CREATOR
                java.lang.Object r4 = r4.createFromParcel(r5)
                r0 = r4
                android.os.Bundle r0 = (android.os.Bundle) r0
            L_0x0246:
                r3.onCreate(r0)
                r6.writeNoException()
                return r2
            L_0x024d:
                java.lang.String r4 = "com.google.android.gms.maps.internal.IGoogleMapDelegate"
                r5.enforceInterface(r4)
                android.os.IBinder r4 = r5.readStrongBinder()
                com.google.android.gms.maps.internal.zzt r4 = com.google.android.gms.maps.internal.zzt.zza.zzii(r4)
                r3.getMapAsync(r4)
                r6.writeNoException()
                return r2
            L_0x0261:
                java.lang.String r4 = "com.google.android.gms.maps.internal.IGoogleMapDelegate"
                r5.enforceInterface(r4)
                android.os.IBinder r4 = r5.readStrongBinder()
                com.google.android.gms.maps.internal.zzl r4 = com.google.android.gms.maps.internal.zzl.zza.zzia(r4)
                r3.setOnIndoorStateChangeListener(r4)
                r6.writeNoException()
                return r2
            L_0x0275:
                java.lang.String r4 = "com.google.android.gms.maps.internal.IGoogleMapDelegate"
                r5.enforceInterface(r4)
                com.google.android.gms.maps.model.internal.zzd r4 = r3.getFocusedBuilding()
                r6.writeNoException()
                if (r4 == 0) goto L_0x0287
                android.os.IBinder r0 = r4.asBinder()
            L_0x0287:
                r6.writeStrongBinder(r0)
                return r2
            L_0x028b:
                java.lang.String r4 = "com.google.android.gms.maps.internal.IGoogleMapDelegate"
                r5.enforceInterface(r4)
                android.os.IBinder r4 = r5.readStrongBinder()
                com.google.android.gms.maps.internal.zzr r4 = com.google.android.gms.maps.internal.zzr.zza.zzig(r4)
                r3.setOnMapLoadedCallback(r4)
                r6.writeNoException()
                return r2
            L_0x029f:
                java.lang.String r4 = "com.google.android.gms.maps.internal.IGoogleMapDelegate"
                r5.enforceInterface(r4)
                int r4 = r5.readInt()
                if (r4 == 0) goto L_0x02ab
                r1 = 1
            L_0x02ab:
                r3.setBuildingsEnabled(r1)
                r6.writeNoException()
                return r2
            L_0x02b2:
                java.lang.String r4 = "com.google.android.gms.maps.internal.IGoogleMapDelegate"
                r5.enforceInterface(r4)
                boolean r4 = r3.isBuildingsEnabled()
                r6.writeNoException()
                r6.writeInt(r4)
                return r2
            L_0x02c2:
                java.lang.String r4 = "com.google.android.gms.maps.internal.IGoogleMapDelegate"
                r5.enforceInterface(r4)
                int r4 = r5.readInt()
                int r7 = r5.readInt()
                int r0 = r5.readInt()
                int r5 = r5.readInt()
                r3.setPadding(r4, r7, r0, r5)
                r6.writeNoException()
                return r2
            L_0x02de:
                java.lang.String r4 = "com.google.android.gms.maps.internal.IGoogleMapDelegate"
                r5.enforceInterface(r4)
                android.os.IBinder r4 = r5.readStrongBinder()
                com.google.android.gms.maps.internal.zzag r4 = com.google.android.gms.maps.internal.zzag.zza.zziw(r4)
                android.os.IBinder r5 = r5.readStrongBinder()
                com.google.android.gms.dynamic.zzd r5 = com.google.android.gms.dynamic.zzd.zza.zzfe(r5)
                r3.snapshot(r4, r5)
                r6.writeNoException()
                return r2
            L_0x02fa:
                java.lang.String r4 = "com.google.android.gms.maps.internal.IGoogleMapDelegate"
                r5.enforceInterface(r4)
                android.os.IBinder r4 = r5.readStrongBinder()
                com.google.android.gms.maps.internal.zzw r4 = com.google.android.gms.maps.internal.zzw.zza.zzil(r4)
                r3.setOnMyLocationButtonClickListener(r4)
                r6.writeNoException()
                return r2
            L_0x030e:
                java.lang.String r4 = "com.google.android.gms.maps.internal.IGoogleMapDelegate"
                r5.enforceInterface(r4)
                android.os.IBinder r4 = r5.readStrongBinder()
                com.google.android.gms.maps.internal.zzx r4 = com.google.android.gms.maps.internal.zzx.zza.zzim(r4)
                r3.setOnMyLocationChangeListener(r4)
                r6.writeNoException()
                return r2
            L_0x0322:
                java.lang.String r4 = "com.google.android.gms.maps.internal.IGoogleMapDelegate"
                r5.enforceInterface(r4)
                int r4 = r5.readInt()
                if (r4 == 0) goto L_0x0336
                com.google.android.gms.maps.model.zzb r4 = com.google.android.gms.maps.model.CircleOptions.CREATOR
                java.lang.Object r4 = r4.createFromParcel(r5)
                com.google.android.gms.maps.model.CircleOptions r4 = (com.google.android.gms.maps.model.CircleOptions) r4
                goto L_0x0337
            L_0x0336:
                r4 = r0
            L_0x0337:
                com.google.android.gms.maps.model.internal.zzb r4 = r3.addCircle(r4)
                r6.writeNoException()
                if (r4 == 0) goto L_0x0344
                android.os.IBinder r0 = r4.asBinder()
            L_0x0344:
                r6.writeStrongBinder(r0)
                return r2
            L_0x0348:
                java.lang.String r4 = "com.google.android.gms.maps.internal.IGoogleMapDelegate"
                r5.enforceInterface(r4)
                android.os.IBinder r4 = r5.readStrongBinder()
                com.google.android.gms.maps.internal.zzd r4 = com.google.android.gms.maps.internal.zzd.zza.zzhp(r4)
                r3.setInfoWindowAdapter(r4)
                r6.writeNoException()
                return r2
            L_0x035c:
                java.lang.String r4 = "com.google.android.gms.maps.internal.IGoogleMapDelegate"
                r5.enforceInterface(r4)
                android.os.IBinder r4 = r5.readStrongBinder()
                com.google.android.gms.maps.internal.zzm r4 = com.google.android.gms.maps.internal.zzm.zza.zzib(r4)
                r3.setOnInfoWindowClickListener(r4)
                r6.writeNoException()
                return r2
            L_0x0370:
                java.lang.String r4 = "com.google.android.gms.maps.internal.IGoogleMapDelegate"
                r5.enforceInterface(r4)
                android.os.IBinder r4 = r5.readStrongBinder()
                com.google.android.gms.maps.internal.zzv r4 = com.google.android.gms.maps.internal.zzv.zza.zzik(r4)
                r3.setOnMarkerDragListener(r4)
                r6.writeNoException()
                return r2
            L_0x0384:
                java.lang.String r4 = "com.google.android.gms.maps.internal.IGoogleMapDelegate"
                r5.enforceInterface(r4)
                android.os.IBinder r4 = r5.readStrongBinder()
                com.google.android.gms.maps.internal.zzu r4 = com.google.android.gms.maps.internal.zzu.zza.zzij(r4)
                r3.setOnMarkerClickListener(r4)
                r6.writeNoException()
                return r2
            L_0x0398:
                java.lang.String r4 = "com.google.android.gms.maps.internal.IGoogleMapDelegate"
                r5.enforceInterface(r4)
                android.os.IBinder r4 = r5.readStrongBinder()
                com.google.android.gms.maps.internal.zzs r4 = com.google.android.gms.maps.internal.zzs.zza.zzih(r4)
                r3.setOnMapLongClickListener(r4)
                r6.writeNoException()
                return r2
            L_0x03ac:
                java.lang.String r4 = "com.google.android.gms.maps.internal.IGoogleMapDelegate"
                r5.enforceInterface(r4)
                android.os.IBinder r4 = r5.readStrongBinder()
                com.google.android.gms.maps.internal.zzq r4 = com.google.android.gms.maps.internal.zzq.zza.zzif(r4)
                r3.setOnMapClickListener(r4)
                r6.writeNoException()
                return r2
            L_0x03c0:
                java.lang.String r4 = "com.google.android.gms.maps.internal.IGoogleMapDelegate"
                r5.enforceInterface(r4)
                android.os.IBinder r4 = r5.readStrongBinder()
                com.google.android.gms.maps.internal.zze r4 = com.google.android.gms.maps.internal.zze.zza.zzht(r4)
                r3.setOnCameraChangeListener(r4)
                r6.writeNoException()
                return r2
            L_0x03d4:
                java.lang.String r4 = "com.google.android.gms.maps.internal.IGoogleMapDelegate"
                r5.enforceInterface(r4)
                com.google.android.gms.maps.internal.IProjectionDelegate r4 = r3.getProjection()
                r6.writeNoException()
                if (r4 == 0) goto L_0x03e6
                android.os.IBinder r0 = r4.asBinder()
            L_0x03e6:
                r6.writeStrongBinder(r0)
                return r2
            L_0x03ea:
                java.lang.String r4 = "com.google.android.gms.maps.internal.IGoogleMapDelegate"
                r5.enforceInterface(r4)
                com.google.android.gms.maps.internal.IUiSettingsDelegate r4 = r3.getUiSettings()
                r6.writeNoException()
                if (r4 == 0) goto L_0x03fc
                android.os.IBinder r0 = r4.asBinder()
            L_0x03fc:
                r6.writeStrongBinder(r0)
                return r2
            L_0x0400:
                java.lang.String r4 = "com.google.android.gms.maps.internal.IGoogleMapDelegate"
                r5.enforceInterface(r4)
                android.os.IBinder r4 = r5.readStrongBinder()
                com.google.android.gms.maps.internal.ILocationSourceDelegate r4 = com.google.android.gms.maps.internal.ILocationSourceDelegate.zza.zzhq(r4)
                r3.setLocationSource(r4)
                r6.writeNoException()
                return r2
            L_0x0414:
                java.lang.String r4 = "com.google.android.gms.maps.internal.IGoogleMapDelegate"
                r5.enforceInterface(r4)
                android.location.Location r4 = r3.getMyLocation()
                r6.writeNoException()
                if (r4 == 0) goto L_0x0429
                r6.writeInt(r2)
                r4.writeToParcel(r6, r2)
                return r2
            L_0x0429:
                r6.writeInt(r1)
                return r2
            L_0x042d:
                java.lang.String r4 = "com.google.android.gms.maps.internal.IGoogleMapDelegate"
                r5.enforceInterface(r4)
                int r4 = r5.readInt()
                if (r4 == 0) goto L_0x0439
                r1 = 1
            L_0x0439:
                r3.setMyLocationEnabled(r1)
                r6.writeNoException()
                return r2
            L_0x0440:
                java.lang.String r4 = "com.google.android.gms.maps.internal.IGoogleMapDelegate"
                r5.enforceInterface(r4)
                boolean r4 = r3.isMyLocationEnabled()
                r6.writeNoException()
                r6.writeInt(r4)
                return r2
            L_0x0450:
                java.lang.String r4 = "com.google.android.gms.maps.internal.IGoogleMapDelegate"
                r5.enforceInterface(r4)
                int r4 = r5.readInt()
                if (r4 == 0) goto L_0x045c
                r1 = 1
            L_0x045c:
                boolean r4 = r3.setIndoorEnabled(r1)
                r6.writeNoException()
                r6.writeInt(r4)
                return r2
            L_0x0467:
                java.lang.String r4 = "com.google.android.gms.maps.internal.IGoogleMapDelegate"
                r5.enforceInterface(r4)
                boolean r4 = r3.isIndoorEnabled()
                r6.writeNoException()
                r6.writeInt(r4)
                return r2
            L_0x0477:
                java.lang.String r4 = "com.google.android.gms.maps.internal.IGoogleMapDelegate"
                r5.enforceInterface(r4)
                int r4 = r5.readInt()
                if (r4 == 0) goto L_0x0483
                r1 = 1
            L_0x0483:
                r3.setTrafficEnabled(r1)
                r6.writeNoException()
                return r2
            L_0x048a:
                java.lang.String r4 = "com.google.android.gms.maps.internal.IGoogleMapDelegate"
                r5.enforceInterface(r4)
                boolean r4 = r3.isTrafficEnabled()
                r6.writeNoException()
                r6.writeInt(r4)
                return r2
            L_0x049a:
                java.lang.String r4 = "com.google.android.gms.maps.internal.IGoogleMapDelegate"
                r5.enforceInterface(r4)
                int r4 = r5.readInt()
                r3.setMapType(r4)
                r6.writeNoException()
                return r2
            L_0x04aa:
                java.lang.String r4 = "com.google.android.gms.maps.internal.IGoogleMapDelegate"
                r5.enforceInterface(r4)
                int r4 = r3.getMapType()
                r6.writeNoException()
                r6.writeInt(r4)
                return r2
            L_0x04ba:
                java.lang.String r4 = "com.google.android.gms.maps.internal.IGoogleMapDelegate"
                r5.enforceInterface(r4)
                r3.clear()
                r6.writeNoException()
                return r2
            L_0x04c6:
                java.lang.String r4 = "com.google.android.gms.maps.internal.IGoogleMapDelegate"
                r5.enforceInterface(r4)
                int r4 = r5.readInt()
                if (r4 == 0) goto L_0x04da
                com.google.android.gms.maps.model.zzp r4 = com.google.android.gms.maps.model.TileOverlayOptions.CREATOR
                java.lang.Object r4 = r4.createFromParcel(r5)
                com.google.android.gms.maps.model.TileOverlayOptions r4 = (com.google.android.gms.maps.model.TileOverlayOptions) r4
                goto L_0x04db
            L_0x04da:
                r4 = r0
            L_0x04db:
                com.google.android.gms.maps.model.internal.zzh r4 = r3.addTileOverlay(r4)
                r6.writeNoException()
                if (r4 == 0) goto L_0x04e8
                android.os.IBinder r0 = r4.asBinder()
            L_0x04e8:
                r6.writeStrongBinder(r0)
                return r2
            L_0x04ec:
                java.lang.String r4 = "com.google.android.gms.maps.internal.IGoogleMapDelegate"
                r5.enforceInterface(r4)
                int r4 = r5.readInt()
                if (r4 == 0) goto L_0x0500
                com.google.android.gms.maps.model.zzc r4 = com.google.android.gms.maps.model.GroundOverlayOptions.CREATOR
                java.lang.Object r4 = r4.createFromParcel(r5)
                com.google.android.gms.maps.model.GroundOverlayOptions r4 = (com.google.android.gms.maps.model.GroundOverlayOptions) r4
                goto L_0x0501
            L_0x0500:
                r4 = r0
            L_0x0501:
                com.google.android.gms.maps.model.internal.zzc r4 = r3.addGroundOverlay(r4)
                r6.writeNoException()
                if (r4 == 0) goto L_0x050e
                android.os.IBinder r0 = r4.asBinder()
            L_0x050e:
                r6.writeStrongBinder(r0)
                return r2
            L_0x0512:
                java.lang.String r4 = "com.google.android.gms.maps.internal.IGoogleMapDelegate"
                r5.enforceInterface(r4)
                int r4 = r5.readInt()
                if (r4 == 0) goto L_0x0526
                com.google.android.gms.maps.model.zzg r4 = com.google.android.gms.maps.model.MarkerOptions.CREATOR
                java.lang.Object r4 = r4.createFromParcel(r5)
                com.google.android.gms.maps.model.MarkerOptions r4 = (com.google.android.gms.maps.model.MarkerOptions) r4
                goto L_0x0527
            L_0x0526:
                r4 = r0
            L_0x0527:
                com.google.android.gms.maps.model.internal.zzf r4 = r3.addMarker(r4)
                r6.writeNoException()
                if (r4 == 0) goto L_0x0534
                android.os.IBinder r0 = r4.asBinder()
            L_0x0534:
                r6.writeStrongBinder(r0)
                return r2
            L_0x0538:
                java.lang.String r4 = "com.google.android.gms.maps.internal.IGoogleMapDelegate"
                r5.enforceInterface(r4)
                int r4 = r5.readInt()
                if (r4 == 0) goto L_0x054c
                com.google.android.gms.maps.model.zzi r4 = com.google.android.gms.maps.model.PolygonOptions.CREATOR
                java.lang.Object r4 = r4.createFromParcel(r5)
                com.google.android.gms.maps.model.PolygonOptions r4 = (com.google.android.gms.maps.model.PolygonOptions) r4
                goto L_0x054d
            L_0x054c:
                r4 = r0
            L_0x054d:
                com.google.android.gms.maps.model.internal.zzg r4 = r3.addPolygon(r4)
                r6.writeNoException()
                if (r4 == 0) goto L_0x055a
                android.os.IBinder r0 = r4.asBinder()
            L_0x055a:
                r6.writeStrongBinder(r0)
                return r2
            L_0x055e:
                java.lang.String r4 = "com.google.android.gms.maps.internal.IGoogleMapDelegate"
                r5.enforceInterface(r4)
                int r4 = r5.readInt()
                if (r4 == 0) goto L_0x0572
                com.google.android.gms.maps.model.zzj r4 = com.google.android.gms.maps.model.PolylineOptions.CREATOR
                java.lang.Object r4 = r4.createFromParcel(r5)
                com.google.android.gms.maps.model.PolylineOptions r4 = (com.google.android.gms.maps.model.PolylineOptions) r4
                goto L_0x0573
            L_0x0572:
                r4 = r0
            L_0x0573:
                com.google.android.gms.maps.model.internal.IPolylineDelegate r4 = r3.addPolyline(r4)
                r6.writeNoException()
                if (r4 == 0) goto L_0x0580
                android.os.IBinder r0 = r4.asBinder()
            L_0x0580:
                r6.writeStrongBinder(r0)
                return r2
            L_0x0584:
                java.lang.String r4 = "com.google.android.gms.maps.internal.IGoogleMapDelegate"
                r5.enforceInterface(r4)
                r3.stopAnimation()
                r6.writeNoException()
                return r2
            L_0x0590:
                java.lang.String r4 = "com.google.android.gms.maps.internal.IGoogleMapDelegate"
                r5.enforceInterface(r4)
                android.os.IBinder r4 = r5.readStrongBinder()
                com.google.android.gms.dynamic.zzd r4 = com.google.android.gms.dynamic.zzd.zza.zzfe(r4)
                int r7 = r5.readInt()
                android.os.IBinder r5 = r5.readStrongBinder()
                com.google.android.gms.maps.internal.zzb r5 = com.google.android.gms.maps.internal.zzb.zza.zzhm(r5)
                r3.animateCameraWithDurationAndCallback(r4, r7, r5)
                r6.writeNoException()
                return r2
            L_0x05b0:
                java.lang.String r4 = "com.google.android.gms.maps.internal.IGoogleMapDelegate"
                r5.enforceInterface(r4)
                android.os.IBinder r4 = r5.readStrongBinder()
                com.google.android.gms.dynamic.zzd r4 = com.google.android.gms.dynamic.zzd.zza.zzfe(r4)
                android.os.IBinder r5 = r5.readStrongBinder()
                com.google.android.gms.maps.internal.zzb r5 = com.google.android.gms.maps.internal.zzb.zza.zzhm(r5)
                r3.animateCameraWithCallback(r4, r5)
                r6.writeNoException()
                return r2
            L_0x05cc:
                java.lang.String r4 = "com.google.android.gms.maps.internal.IGoogleMapDelegate"
                r5.enforceInterface(r4)
                android.os.IBinder r4 = r5.readStrongBinder()
                com.google.android.gms.dynamic.zzd r4 = com.google.android.gms.dynamic.zzd.zza.zzfe(r4)
                r3.animateCamera(r4)
                r6.writeNoException()
                return r2
            L_0x05e0:
                java.lang.String r4 = "com.google.android.gms.maps.internal.IGoogleMapDelegate"
                r5.enforceInterface(r4)
                android.os.IBinder r4 = r5.readStrongBinder()
                com.google.android.gms.dynamic.zzd r4 = com.google.android.gms.dynamic.zzd.zza.zzfe(r4)
                r3.moveCamera(r4)
                r6.writeNoException()
                return r2
            L_0x05f4:
                java.lang.String r4 = "com.google.android.gms.maps.internal.IGoogleMapDelegate"
                r5.enforceInterface(r4)
                float r4 = r3.getMinZoomLevel()
                r6.writeNoException()
                r6.writeFloat(r4)
                return r2
            L_0x0604:
                java.lang.String r4 = "com.google.android.gms.maps.internal.IGoogleMapDelegate"
                r5.enforceInterface(r4)
                float r4 = r3.getMaxZoomLevel()
                r6.writeNoException()
                r6.writeFloat(r4)
                return r2
            L_0x0614:
                java.lang.String r4 = "com.google.android.gms.maps.internal.IGoogleMapDelegate"
                r5.enforceInterface(r4)
                com.google.android.gms.maps.model.CameraPosition r4 = r3.getCameraPosition()
                r6.writeNoException()
                if (r4 == 0) goto L_0x0629
                r6.writeInt(r2)
                r4.writeToParcel(r6, r2)
                return r2
            L_0x0629:
                r6.writeInt(r1)
                return r2
            L_0x062d:
                java.lang.String r4 = "com.google.android.gms.maps.internal.IGoogleMapDelegate"
                r6.writeString(r4)
                return r2
            L_0x0633:
                java.lang.String r4 = "com.google.android.gms.maps.internal.IGoogleMapDelegate"
                r5.enforceInterface(r4)
                android.os.IBinder r4 = r5.readStrongBinder()
                com.google.android.gms.maps.internal.zzag r4 = com.google.android.gms.maps.internal.zzag.zza.zziw(r4)
                r3.snapshotForTest(r4)
                r6.writeNoException()
                return r2
            L_0x0647:
                java.lang.String r4 = "com.google.android.gms.maps.internal.IGoogleMapDelegate"
                r5.enforceInterface(r4)
                int r4 = r5.readInt()
                if (r4 == 0) goto L_0x0653
                r1 = 1
            L_0x0653:
                r3.setWatermarkEnabled(r1)
                r6.writeNoException()
                return r2
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.maps.internal.IGoogleMapDelegate.zza.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }
    }

    zzb addCircle(CircleOptions circleOptions);

    zzc addGroundOverlay(GroundOverlayOptions groundOverlayOptions);

    zzf addMarker(MarkerOptions markerOptions);

    zzg addPolygon(PolygonOptions polygonOptions);

    IPolylineDelegate addPolyline(PolylineOptions polylineOptions);

    zzh addTileOverlay(TileOverlayOptions tileOverlayOptions);

    void animateCamera(zzd zzd);

    void animateCameraWithCallback(zzd zzd, zzb zzb);

    void animateCameraWithDurationAndCallback(zzd zzd, int i, zzb zzb);

    void clear();

    CameraPosition getCameraPosition();

    com.google.android.gms.maps.model.internal.zzd getFocusedBuilding();

    void getMapAsync(zzt zzt);

    int getMapType();

    float getMaxZoomLevel();

    float getMinZoomLevel();

    Location getMyLocation();

    IProjectionDelegate getProjection();

    IUiSettingsDelegate getUiSettings();

    boolean isBuildingsEnabled();

    boolean isIndoorEnabled();

    boolean isMyLocationEnabled();

    boolean isTrafficEnabled();

    void moveCamera(zzd zzd);

    void onCreate(Bundle bundle);

    void onDestroy();

    void onEnterAmbient(Bundle bundle);

    void onExitAmbient();

    void onLowMemory();

    void onPause();

    void onResume();

    void onSaveInstanceState(Bundle bundle);

    void onStart();

    void onStop();

    void resetMinMaxZoomPreference();

    void setBuildingsEnabled(boolean z);

    void setContentDescription(String str);

    boolean setIndoorEnabled(boolean z);

    void setInfoWindowAdapter(zzd zzd);

    void setLatLngBoundsForCameraTarget(LatLngBounds latLngBounds);

    void setLocationSource(ILocationSourceDelegate iLocationSourceDelegate);

    boolean setMapStyle(MapStyleOptions mapStyleOptions);

    void setMapType(int i);

    void setMaxZoomPreference(float f);

    void setMinZoomPreference(float f);

    void setMyLocationEnabled(boolean z);

    void setOnCameraChangeListener(zze zze);

    void setOnCameraIdleListener(zzf zzf);

    void setOnCameraMoveCanceledListener(zzg zzg);

    void setOnCameraMoveListener(zzh zzh);

    void setOnCameraMoveStartedListener(zzi zzi);

    void setOnCircleClickListener(zzj zzj);

    void setOnGroundOverlayClickListener(zzk zzk);

    void setOnIndoorStateChangeListener(zzl zzl);

    void setOnInfoWindowClickListener(zzm zzm);

    void setOnInfoWindowCloseListener(zzn zzn);

    void setOnInfoWindowLongClickListener(zzo zzo);

    void setOnMapClickListener(zzq zzq);

    void setOnMapLoadedCallback(zzr zzr);

    void setOnMapLongClickListener(zzs zzs);

    void setOnMarkerClickListener(zzu zzu);

    void setOnMarkerDragListener(zzv zzv);

    void setOnMyLocationButtonClickListener(zzw zzw);

    void setOnMyLocationChangeListener(zzx zzx);

    void setOnPoiClickListener(zzy zzy);

    void setOnPolygonClickListener(zzz zzz);

    void setOnPolylineClickListener(zzaa zzaa);

    void setPadding(int i, int i2, int i3, int i4);

    void setSpotlightLayer(byte[] bArr);

    void setTrafficEnabled(boolean z);

    void setWatermarkEnabled(boolean z);

    void snapshot(zzag zzag, zzd zzd);

    void snapshotForTest(zzag zzag);

    void stopAnimation();

    boolean useViewLifecycleWhenInFragment();
}
