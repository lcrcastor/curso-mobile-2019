package com.google.android.gms.maps.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import com.google.android.gms.maps.model.internal.zzf;

public interface zzo extends IInterface {

    public static abstract class zza extends Binder implements zzo {

        /* renamed from: com.google.android.gms.maps.internal.zzo$zza$zza reason: collision with other inner class name */
        static class C0070zza implements zzo {
            private IBinder a;

            C0070zza(IBinder iBinder) {
                this.a = iBinder;
            }

            public IBinder asBinder() {
                return this.a;
            }

            public void zzf(zzf zzf) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IOnInfoWindowLongClickListener");
                    obtain.writeStrongBinder(zzf != null ? zzf.asBinder() : null);
                    this.a.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public zza() {
            attachInterface(this, "com.google.android.gms.maps.internal.IOnInfoWindowLongClickListener");
        }

        public static zzo zzid(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.maps.internal.IOnInfoWindowLongClickListener");
            return (queryLocalInterface == null || !(queryLocalInterface instanceof zzo)) ? new C0070zza(iBinder) : (zzo) queryLocalInterface;
        }

        public IBinder asBinder() {
            return this;
        }

        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            if (i == 1) {
                parcel.enforceInterface("com.google.android.gms.maps.internal.IOnInfoWindowLongClickListener");
                zzf(com.google.android.gms.maps.model.internal.zzf.zza.zzjg(parcel.readStrongBinder()));
                parcel2.writeNoException();
                return true;
            } else if (i != 1598968902) {
                return super.onTransact(i, parcel, parcel2, i2);
            } else {
                parcel2.writeString("com.google.android.gms.maps.internal.IOnInfoWindowLongClickListener");
                return true;
            }
        }
    }

    void zzf(zzf zzf);
}
