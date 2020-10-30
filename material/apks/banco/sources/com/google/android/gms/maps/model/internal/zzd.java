package com.google.android.gms.maps.model.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import java.util.List;

public interface zzd extends IInterface {

    public static abstract class zza extends Binder implements zzd {

        /* renamed from: com.google.android.gms.maps.model.internal.zzd$zza$zza reason: collision with other inner class name */
        static class C0087zza implements zzd {
            private IBinder a;

            C0087zza(IBinder iBinder) {
                this.a = iBinder;
            }

            public IBinder asBinder() {
                return this.a;
            }

            public int getActiveLevelIndex() {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IIndoorBuildingDelegate");
                    this.a.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public int getDefaultLevelIndex() {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IIndoorBuildingDelegate");
                    this.a.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public List<IBinder> getLevels() {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IIndoorBuildingDelegate");
                    this.a.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createBinderArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public int hashCodeRemote() {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IIndoorBuildingDelegate");
                    this.a.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public boolean isUnderground() {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IIndoorBuildingDelegate");
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

            public boolean zzb(zzd zzd) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IIndoorBuildingDelegate");
                    obtain.writeStrongBinder(zzd != null ? zzd.asBinder() : null);
                    boolean z = false;
                    this.a.transact(5, obtain, obtain2, 0);
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

        public static zzd zzje(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.maps.model.internal.IIndoorBuildingDelegate");
            return (queryLocalInterface == null || !(queryLocalInterface instanceof zzd)) ? new C0087zza(iBinder) : (zzd) queryLocalInterface;
        }

        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            if (i != 1598968902) {
                switch (i) {
                    case 1:
                        parcel.enforceInterface("com.google.android.gms.maps.model.internal.IIndoorBuildingDelegate");
                        int activeLevelIndex = getActiveLevelIndex();
                        parcel2.writeNoException();
                        parcel2.writeInt(activeLevelIndex);
                        return true;
                    case 2:
                        parcel.enforceInterface("com.google.android.gms.maps.model.internal.IIndoorBuildingDelegate");
                        int defaultLevelIndex = getDefaultLevelIndex();
                        parcel2.writeNoException();
                        parcel2.writeInt(defaultLevelIndex);
                        return true;
                    case 3:
                        parcel.enforceInterface("com.google.android.gms.maps.model.internal.IIndoorBuildingDelegate");
                        List levels = getLevels();
                        parcel2.writeNoException();
                        parcel2.writeBinderList(levels);
                        return true;
                    case 4:
                        parcel.enforceInterface("com.google.android.gms.maps.model.internal.IIndoorBuildingDelegate");
                        boolean isUnderground = isUnderground();
                        parcel2.writeNoException();
                        parcel2.writeInt(isUnderground ? 1 : 0);
                        return true;
                    case 5:
                        parcel.enforceInterface("com.google.android.gms.maps.model.internal.IIndoorBuildingDelegate");
                        boolean zzb = zzb(zzje(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        parcel2.writeInt(zzb ? 1 : 0);
                        return true;
                    case 6:
                        parcel.enforceInterface("com.google.android.gms.maps.model.internal.IIndoorBuildingDelegate");
                        int hashCodeRemote = hashCodeRemote();
                        parcel2.writeNoException();
                        parcel2.writeInt(hashCodeRemote);
                        return true;
                    default:
                        return super.onTransact(i, parcel, parcel2, i2);
                }
            } else {
                parcel2.writeString("com.google.android.gms.maps.model.internal.IIndoorBuildingDelegate");
                return true;
            }
        }
    }

    int getActiveLevelIndex();

    int getDefaultLevelIndex();

    List<IBinder> getLevels();

    int hashCodeRemote();

    boolean isUnderground();

    boolean zzb(zzd zzd);
}
