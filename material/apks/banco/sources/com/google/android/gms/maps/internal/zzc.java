package com.google.android.gms.maps.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import com.google.android.gms.dynamic.zzd;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.StreetViewPanoramaOptions;
import com.google.android.gms.maps.model.internal.zza.C0083zza;

public interface zzc extends IInterface {

    public static abstract class zza extends Binder implements zzc {

        /* renamed from: com.google.android.gms.maps.internal.zzc$zza$zza reason: collision with other inner class name */
        static class C0058zza implements zzc {
            private IBinder a;

            C0058zza(IBinder iBinder) {
                this.a = iBinder;
            }

            public IBinder asBinder() {
                return this.a;
            }

            public void init(zzd zzd) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.ICreator");
                    obtain.writeStrongBinder(zzd != null ? zzd.asBinder() : null);
                    this.a.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public IMapViewDelegate zza(zzd zzd, GoogleMapOptions googleMapOptions) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.ICreator");
                    obtain.writeStrongBinder(zzd != null ? zzd.asBinder() : null);
                    if (googleMapOptions != null) {
                        obtain.writeInt(1);
                        googleMapOptions.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.a.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return com.google.android.gms.maps.internal.IMapViewDelegate.zza.zzhs(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public IStreetViewPanoramaViewDelegate zza(zzd zzd, StreetViewPanoramaOptions streetViewPanoramaOptions) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.ICreator");
                    obtain.writeStrongBinder(zzd != null ? zzd.asBinder() : null);
                    if (streetViewPanoramaOptions != null) {
                        obtain.writeInt(1);
                        streetViewPanoramaOptions.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.a.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return com.google.android.gms.maps.internal.IStreetViewPanoramaViewDelegate.zza.zziz(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public IMapFragmentDelegate zzah(zzd zzd) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.ICreator");
                    obtain.writeStrongBinder(zzd != null ? zzd.asBinder() : null);
                    this.a.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return com.google.android.gms.maps.internal.IMapFragmentDelegate.zza.zzhr(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public IStreetViewPanoramaFragmentDelegate zzai(zzd zzd) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.ICreator");
                    obtain.writeStrongBinder(zzd != null ? zzd.asBinder() : null);
                    this.a.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return com.google.android.gms.maps.internal.IStreetViewPanoramaFragmentDelegate.zza.zziy(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public ICameraUpdateFactoryDelegate zzbsc() {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.ICreator");
                    this.a.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return com.google.android.gms.maps.internal.ICameraUpdateFactoryDelegate.zza.zzhl(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public com.google.android.gms.maps.model.internal.zza zzbsd() {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.ICreator");
                    this.a.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return C0083zza.zzjb(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void zzh(zzd zzd, int i) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.ICreator");
                    obtain.writeStrongBinder(zzd != null ? zzd.asBinder() : null);
                    obtain.writeInt(i);
                    this.a.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public static zzc zzhn(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.maps.internal.ICreator");
            return (queryLocalInterface == null || !(queryLocalInterface instanceof zzc)) ? new C0058zza(iBinder) : (zzc) queryLocalInterface;
        }

        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            if (i != 1598968902) {
                IBinder iBinder = null;
                switch (i) {
                    case 1:
                        parcel.enforceInterface("com.google.android.gms.maps.internal.ICreator");
                        init(com.google.android.gms.dynamic.zzd.zza.zzfe(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        return true;
                    case 2:
                        parcel.enforceInterface("com.google.android.gms.maps.internal.ICreator");
                        IMapFragmentDelegate zzah = zzah(com.google.android.gms.dynamic.zzd.zza.zzfe(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        if (zzah != null) {
                            iBinder = zzah.asBinder();
                        }
                        parcel2.writeStrongBinder(iBinder);
                        return true;
                    case 3:
                        parcel.enforceInterface("com.google.android.gms.maps.internal.ICreator");
                        IMapViewDelegate zza = zza(com.google.android.gms.dynamic.zzd.zza.zzfe(parcel.readStrongBinder()), parcel.readInt() != 0 ? (GoogleMapOptions) GoogleMapOptions.CREATOR.createFromParcel(parcel) : null);
                        parcel2.writeNoException();
                        if (zza != null) {
                            iBinder = zza.asBinder();
                        }
                        parcel2.writeStrongBinder(iBinder);
                        return true;
                    case 4:
                        parcel.enforceInterface("com.google.android.gms.maps.internal.ICreator");
                        ICameraUpdateFactoryDelegate zzbsc = zzbsc();
                        parcel2.writeNoException();
                        if (zzbsc != null) {
                            iBinder = zzbsc.asBinder();
                        }
                        parcel2.writeStrongBinder(iBinder);
                        return true;
                    case 5:
                        parcel.enforceInterface("com.google.android.gms.maps.internal.ICreator");
                        com.google.android.gms.maps.model.internal.zza zzbsd = zzbsd();
                        parcel2.writeNoException();
                        if (zzbsd != null) {
                            iBinder = zzbsd.asBinder();
                        }
                        parcel2.writeStrongBinder(iBinder);
                        return true;
                    case 6:
                        parcel.enforceInterface("com.google.android.gms.maps.internal.ICreator");
                        zzh(com.google.android.gms.dynamic.zzd.zza.zzfe(parcel.readStrongBinder()), parcel.readInt());
                        parcel2.writeNoException();
                        return true;
                    case 7:
                        parcel.enforceInterface("com.google.android.gms.maps.internal.ICreator");
                        IStreetViewPanoramaViewDelegate zza2 = zza(com.google.android.gms.dynamic.zzd.zza.zzfe(parcel.readStrongBinder()), parcel.readInt() != 0 ? (StreetViewPanoramaOptions) StreetViewPanoramaOptions.CREATOR.createFromParcel(parcel) : null);
                        parcel2.writeNoException();
                        if (zza2 != null) {
                            iBinder = zza2.asBinder();
                        }
                        parcel2.writeStrongBinder(iBinder);
                        return true;
                    case 8:
                        parcel.enforceInterface("com.google.android.gms.maps.internal.ICreator");
                        IStreetViewPanoramaFragmentDelegate zzai = zzai(com.google.android.gms.dynamic.zzd.zza.zzfe(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        if (zzai != null) {
                            iBinder = zzai.asBinder();
                        }
                        parcel2.writeStrongBinder(iBinder);
                        return true;
                    default:
                        return super.onTransact(i, parcel, parcel2, i2);
                }
            } else {
                parcel2.writeString("com.google.android.gms.maps.internal.ICreator");
                return true;
            }
        }
    }

    void init(zzd zzd);

    IMapViewDelegate zza(zzd zzd, GoogleMapOptions googleMapOptions);

    IStreetViewPanoramaViewDelegate zza(zzd zzd, StreetViewPanoramaOptions streetViewPanoramaOptions);

    IMapFragmentDelegate zzah(zzd zzd);

    IStreetViewPanoramaFragmentDelegate zzai(zzd zzd);

    ICameraUpdateFactoryDelegate zzbsc();

    com.google.android.gms.maps.model.internal.zza zzbsd();

    void zzh(zzd zzd, int i);
}
