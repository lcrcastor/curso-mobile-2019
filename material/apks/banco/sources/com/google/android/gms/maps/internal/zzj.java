package com.google.android.gms.maps.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import com.google.android.gms.maps.model.internal.zzb;

public interface zzj extends IInterface {

    public static abstract class zza extends Binder implements zzj {

        /* renamed from: com.google.android.gms.maps.internal.zzj$zza$zza reason: collision with other inner class name */
        static class C0065zza implements zzj {
            private IBinder a;

            C0065zza(IBinder iBinder) {
                this.a = iBinder;
            }

            public IBinder asBinder() {
                return this.a;
            }

            public void zza(zzb zzb) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IOnCircleClickListener");
                    obtain.writeStrongBinder(zzb != null ? zzb.asBinder() : null);
                    this.a.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public zza() {
            attachInterface(this, "com.google.android.gms.maps.internal.IOnCircleClickListener");
        }

        public static zzj zzhy(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.maps.internal.IOnCircleClickListener");
            return (queryLocalInterface == null || !(queryLocalInterface instanceof zzj)) ? new C0065zza(iBinder) : (zzj) queryLocalInterface;
        }

        public IBinder asBinder() {
            return this;
        }

        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            if (i == 1) {
                parcel.enforceInterface("com.google.android.gms.maps.internal.IOnCircleClickListener");
                zza(com.google.android.gms.maps.model.internal.zzb.zza.zzjc(parcel.readStrongBinder()));
                parcel2.writeNoException();
                return true;
            } else if (i != 1598968902) {
                return super.onTransact(i, parcel, parcel2, i2);
            } else {
                parcel2.writeString("com.google.android.gms.maps.internal.IOnCircleClickListener");
                return true;
            }
        }
    }

    void zza(zzb zzb);
}
