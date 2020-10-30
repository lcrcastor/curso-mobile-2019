package com.google.android.gms.common.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;

public interface zzs extends IInterface {

    public static abstract class zza extends Binder implements zzs {

        /* renamed from: com.google.android.gms.common.internal.zzs$zza$zza reason: collision with other inner class name */
        static class C0007zza implements zzs {
            private IBinder a;

            C0007zza(IBinder iBinder) {
                this.a = iBinder;
            }

            public IBinder asBinder() {
                return this.a;
            }

            public void cancel() {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.common.internal.ICancelToken");
                    this.a.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        public static zzs zzds(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.common.internal.ICancelToken");
            return (queryLocalInterface == null || !(queryLocalInterface instanceof zzs)) ? new C0007zza(iBinder) : (zzs) queryLocalInterface;
        }

        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            if (i == 2) {
                parcel.enforceInterface("com.google.android.gms.common.internal.ICancelToken");
                cancel();
                return true;
            } else if (i != 1598968902) {
                return super.onTransact(i, parcel, parcel2, i2);
            } else {
                parcel2.writeString("com.google.android.gms.common.internal.ICancelToken");
                return true;
            }
        }
    }

    void cancel();
}
