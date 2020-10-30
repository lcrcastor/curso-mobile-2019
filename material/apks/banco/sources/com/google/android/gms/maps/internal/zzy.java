package com.google.android.gms.maps.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import com.google.android.gms.maps.model.PointOfInterest;

public interface zzy extends IInterface {

    public static abstract class zza extends Binder implements zzy {

        /* renamed from: com.google.android.gms.maps.internal.zzy$zza$zza reason: collision with other inner class name */
        static class C0080zza implements zzy {
            private IBinder a;

            C0080zza(IBinder iBinder) {
                this.a = iBinder;
            }

            public IBinder asBinder() {
                return this.a;
            }

            public void zza(PointOfInterest pointOfInterest) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IOnPoiClickListener");
                    if (pointOfInterest != null) {
                        obtain.writeInt(1);
                        pointOfInterest.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.a.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public zza() {
            attachInterface(this, "com.google.android.gms.maps.internal.IOnPoiClickListener");
        }

        public static zzy zzin(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.maps.internal.IOnPoiClickListener");
            return (queryLocalInterface == null || !(queryLocalInterface instanceof zzy)) ? new C0080zza(iBinder) : (zzy) queryLocalInterface;
        }

        public IBinder asBinder() {
            return this;
        }

        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            if (i == 1) {
                parcel.enforceInterface("com.google.android.gms.maps.internal.IOnPoiClickListener");
                zza(parcel.readInt() != 0 ? (PointOfInterest) PointOfInterest.CREATOR.createFromParcel(parcel) : null);
                parcel2.writeNoException();
                return true;
            } else if (i != 1598968902) {
                return super.onTransact(i, parcel, parcel2, i2);
            } else {
                parcel2.writeString("com.google.android.gms.maps.internal.IOnPoiClickListener");
                return true;
            }
        }
    }

    void zza(PointOfInterest pointOfInterest);
}
