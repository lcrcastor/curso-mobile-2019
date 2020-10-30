package com.google.android.gms.maps.model.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;

public interface zze extends IInterface {

    public static abstract class zza extends Binder implements zze {

        /* renamed from: com.google.android.gms.maps.model.internal.zze$zza$zza reason: collision with other inner class name */
        static class C0088zza implements zze {
            private IBinder a;

            C0088zza(IBinder iBinder) {
                this.a = iBinder;
            }

            public void activate() {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IIndoorLevelDelegate");
                    this.a.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public IBinder asBinder() {
                return this.a;
            }

            public String getName() {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IIndoorLevelDelegate");
                    this.a.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public String getShortName() {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IIndoorLevelDelegate");
                    this.a.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public int hashCodeRemote() {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IIndoorLevelDelegate");
                    this.a.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public boolean zza(zze zze) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IIndoorLevelDelegate");
                    obtain.writeStrongBinder(zze != null ? zze.asBinder() : null);
                    boolean z = false;
                    this.a.transact(4, obtain, obtain2, 0);
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

        public static zze zzjf(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.maps.model.internal.IIndoorLevelDelegate");
            return (queryLocalInterface == null || !(queryLocalInterface instanceof zze)) ? new C0088zza(iBinder) : (zze) queryLocalInterface;
        }

        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            if (i != 1598968902) {
                switch (i) {
                    case 1:
                        parcel.enforceInterface("com.google.android.gms.maps.model.internal.IIndoorLevelDelegate");
                        String name = getName();
                        parcel2.writeNoException();
                        parcel2.writeString(name);
                        return true;
                    case 2:
                        parcel.enforceInterface("com.google.android.gms.maps.model.internal.IIndoorLevelDelegate");
                        String shortName = getShortName();
                        parcel2.writeNoException();
                        parcel2.writeString(shortName);
                        return true;
                    case 3:
                        parcel.enforceInterface("com.google.android.gms.maps.model.internal.IIndoorLevelDelegate");
                        activate();
                        parcel2.writeNoException();
                        return true;
                    case 4:
                        parcel.enforceInterface("com.google.android.gms.maps.model.internal.IIndoorLevelDelegate");
                        boolean zza = zza(zzjf(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        parcel2.writeInt(zza ? 1 : 0);
                        return true;
                    case 5:
                        parcel.enforceInterface("com.google.android.gms.maps.model.internal.IIndoorLevelDelegate");
                        int hashCodeRemote = hashCodeRemote();
                        parcel2.writeNoException();
                        parcel2.writeInt(hashCodeRemote);
                        return true;
                    default:
                        return super.onTransact(i, parcel, parcel2, i2);
                }
            } else {
                parcel2.writeString("com.google.android.gms.maps.model.internal.IIndoorLevelDelegate");
                return true;
            }
        }
    }

    void activate();

    String getName();

    String getShortName();

    int hashCodeRemote();

    boolean zza(zze zze);
}
