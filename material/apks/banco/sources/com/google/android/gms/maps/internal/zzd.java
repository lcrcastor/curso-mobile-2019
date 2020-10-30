package com.google.android.gms.maps.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import com.google.android.gms.maps.model.internal.zzf;

public interface zzd extends IInterface {

    public static abstract class zza extends Binder implements zzd {

        /* renamed from: com.google.android.gms.maps.internal.zzd$zza$zza reason: collision with other inner class name */
        static class C0059zza implements zzd {
            private IBinder a;

            C0059zza(IBinder iBinder) {
                this.a = iBinder;
            }

            public IBinder asBinder() {
                return this.a;
            }

            public com.google.android.gms.dynamic.zzd zzh(zzf zzf) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IInfoWindowAdapter");
                    obtain.writeStrongBinder(zzf != null ? zzf.asBinder() : null);
                    this.a.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return com.google.android.gms.dynamic.zzd.zza.zzfe(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public com.google.android.gms.dynamic.zzd zzi(zzf zzf) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IInfoWindowAdapter");
                    obtain.writeStrongBinder(zzf != null ? zzf.asBinder() : null);
                    this.a.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return com.google.android.gms.dynamic.zzd.zza.zzfe(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public zza() {
            attachInterface(this, "com.google.android.gms.maps.internal.IInfoWindowAdapter");
        }

        public static zzd zzhp(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.maps.internal.IInfoWindowAdapter");
            return (queryLocalInterface == null || !(queryLocalInterface instanceof zzd)) ? new C0059zza(iBinder) : (zzd) queryLocalInterface;
        }

        public IBinder asBinder() {
            return this;
        }

        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            if (i != 1598968902) {
                IBinder iBinder = null;
                switch (i) {
                    case 1:
                        parcel.enforceInterface("com.google.android.gms.maps.internal.IInfoWindowAdapter");
                        com.google.android.gms.dynamic.zzd zzh = zzh(com.google.android.gms.maps.model.internal.zzf.zza.zzjg(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        if (zzh != null) {
                            iBinder = zzh.asBinder();
                        }
                        parcel2.writeStrongBinder(iBinder);
                        return true;
                    case 2:
                        parcel.enforceInterface("com.google.android.gms.maps.internal.IInfoWindowAdapter");
                        com.google.android.gms.dynamic.zzd zzi = zzi(com.google.android.gms.maps.model.internal.zzf.zza.zzjg(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        if (zzi != null) {
                            iBinder = zzi.asBinder();
                        }
                        parcel2.writeStrongBinder(iBinder);
                        return true;
                    default:
                        return super.onTransact(i, parcel, parcel2, i2);
                }
            } else {
                parcel2.writeString("com.google.android.gms.maps.internal.IInfoWindowAdapter");
                return true;
            }
        }
    }

    com.google.android.gms.dynamic.zzd zzh(zzf zzf);

    com.google.android.gms.dynamic.zzd zzi(zzf zzf);
}
