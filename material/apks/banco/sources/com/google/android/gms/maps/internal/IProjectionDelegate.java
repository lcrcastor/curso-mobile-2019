package com.google.android.gms.maps.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import com.google.android.gms.dynamic.zzd;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.VisibleRegion;

public interface IProjectionDelegate extends IInterface {

    public static abstract class zza extends Binder implements IProjectionDelegate {

        /* renamed from: com.google.android.gms.maps.internal.IProjectionDelegate$zza$zza reason: collision with other inner class name */
        static class C0045zza implements IProjectionDelegate {
            private IBinder a;

            C0045zza(IBinder iBinder) {
                this.a = iBinder;
            }

            public IBinder asBinder() {
                return this.a;
            }

            public LatLng fromScreenLocation(zzd zzd) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IProjectionDelegate");
                    LatLng latLng = null;
                    obtain.writeStrongBinder(zzd != null ? zzd.asBinder() : null);
                    this.a.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    if (obtain2.readInt() != 0) {
                        latLng = (LatLng) LatLng.CREATOR.createFromParcel(obtain2);
                    }
                    return latLng;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public VisibleRegion getVisibleRegion() {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IProjectionDelegate");
                    this.a.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0 ? (VisibleRegion) VisibleRegion.CREATOR.createFromParcel(obtain2) : null;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public zzd toScreenLocation(LatLng latLng) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IProjectionDelegate");
                    if (latLng != null) {
                        obtain.writeInt(1);
                        latLng.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.a.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return com.google.android.gms.dynamic.zzd.zza.zzfe(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public static IProjectionDelegate zziv(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.maps.internal.IProjectionDelegate");
            return (queryLocalInterface == null || !(queryLocalInterface instanceof IProjectionDelegate)) ? new C0045zza(iBinder) : (IProjectionDelegate) queryLocalInterface;
        }

        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            if (i != 1598968902) {
                switch (i) {
                    case 1:
                        parcel.enforceInterface("com.google.android.gms.maps.internal.IProjectionDelegate");
                        LatLng fromScreenLocation = fromScreenLocation(com.google.android.gms.dynamic.zzd.zza.zzfe(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        if (fromScreenLocation != null) {
                            parcel2.writeInt(1);
                            fromScreenLocation.writeToParcel(parcel2, 1);
                            return true;
                        }
                        parcel2.writeInt(0);
                        return true;
                    case 2:
                        parcel.enforceInterface("com.google.android.gms.maps.internal.IProjectionDelegate");
                        IBinder iBinder = null;
                        zzd screenLocation = toScreenLocation(parcel.readInt() != 0 ? (LatLng) LatLng.CREATOR.createFromParcel(parcel) : null);
                        parcel2.writeNoException();
                        if (screenLocation != null) {
                            iBinder = screenLocation.asBinder();
                        }
                        parcel2.writeStrongBinder(iBinder);
                        return true;
                    case 3:
                        parcel.enforceInterface("com.google.android.gms.maps.internal.IProjectionDelegate");
                        VisibleRegion visibleRegion = getVisibleRegion();
                        parcel2.writeNoException();
                        if (visibleRegion != null) {
                            parcel2.writeInt(1);
                            visibleRegion.writeToParcel(parcel2, 1);
                            return true;
                        }
                        parcel2.writeInt(0);
                        return true;
                    default:
                        return super.onTransact(i, parcel, parcel2, i2);
                }
            } else {
                parcel2.writeString("com.google.android.gms.maps.internal.IProjectionDelegate");
                return true;
            }
        }
    }

    LatLng fromScreenLocation(zzd zzd);

    VisibleRegion getVisibleRegion();

    zzd toScreenLocation(LatLng latLng);
}
