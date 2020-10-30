package com.google.android.gms.common.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import com.google.android.gms.dynamic.zzd;

public interface zzt extends IInterface {

    public static abstract class zza extends Binder implements zzt {

        /* renamed from: com.google.android.gms.common.internal.zzt$zza$zza reason: collision with other inner class name */
        static class C0008zza implements zzt {
            private IBinder a;

            C0008zza(IBinder iBinder) {
                this.a = iBinder;
            }

            public IBinder asBinder() {
                return this.a;
            }

            public zzd zzaph() {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.common.internal.ICertData");
                    this.a.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return com.google.android.gms.dynamic.zzd.zza.zzfe(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public int zzapi() {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.common.internal.ICertData");
                    this.a.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public zza() {
            attachInterface(this, "com.google.android.gms.common.internal.ICertData");
        }

        public static zzt zzdt(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.common.internal.ICertData");
            return (queryLocalInterface == null || !(queryLocalInterface instanceof zzt)) ? new C0008zza(iBinder) : (zzt) queryLocalInterface;
        }

        public IBinder asBinder() {
            return this;
        }

        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            if (i != 1598968902) {
                switch (i) {
                    case 1:
                        parcel.enforceInterface("com.google.android.gms.common.internal.ICertData");
                        zzd zzaph = zzaph();
                        parcel2.writeNoException();
                        parcel2.writeStrongBinder(zzaph != null ? zzaph.asBinder() : null);
                        return true;
                    case 2:
                        parcel.enforceInterface("com.google.android.gms.common.internal.ICertData");
                        int zzapi = zzapi();
                        parcel2.writeNoException();
                        parcel2.writeInt(zzapi);
                        return true;
                    default:
                        return super.onTransact(i, parcel, parcel2, i2);
                }
            } else {
                parcel2.writeString("com.google.android.gms.common.internal.ICertData");
                return true;
            }
        }
    }

    zzd zzaph();

    int zzapi();
}
