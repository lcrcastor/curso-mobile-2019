package com.google.android.gms.location.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;

public interface zzg extends IInterface {

    public static abstract class zza extends Binder implements zzg {

        /* renamed from: com.google.android.gms.location.internal.zzg$zza$zza reason: collision with other inner class name */
        static class C0034zza implements zzg {
            private IBinder a;

            C0034zza(IBinder iBinder) {
                this.a = iBinder;
            }

            public IBinder asBinder() {
                return this.a;
            }

            public void zza(FusedLocationProviderResult fusedLocationProviderResult) {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.location.internal.IFusedLocationProviderCallback");
                    if (fusedLocationProviderResult != null) {
                        obtain.writeInt(1);
                        fusedLocationProviderResult.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.a.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        public zza() {
            attachInterface(this, "com.google.android.gms.location.internal.IFusedLocationProviderCallback");
        }

        public static zzg zzha(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.location.internal.IFusedLocationProviderCallback");
            return (queryLocalInterface == null || !(queryLocalInterface instanceof zzg)) ? new C0034zza(iBinder) : (zzg) queryLocalInterface;
        }

        public IBinder asBinder() {
            return this;
        }

        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            if (i == 1) {
                parcel.enforceInterface("com.google.android.gms.location.internal.IFusedLocationProviderCallback");
                zza(parcel.readInt() != 0 ? (FusedLocationProviderResult) FusedLocationProviderResult.CREATOR.createFromParcel(parcel) : null);
                return true;
            } else if (i != 1598968902) {
                return super.onTransact(i, parcel, parcel2, i2);
            } else {
                parcel2.writeString("com.google.android.gms.location.internal.IFusedLocationProviderCallback");
                return true;
            }
        }
    }

    void zza(FusedLocationProviderResult fusedLocationProviderResult);
}
