package com.google.android.gms.maps.model.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import com.google.android.gms.maps.model.LatLng;
import java.util.List;

public interface IPolylineDelegate extends IInterface {

    public static abstract class zza extends Binder implements IPolylineDelegate {

        /* renamed from: com.google.android.gms.maps.model.internal.IPolylineDelegate$zza$zza reason: collision with other inner class name */
        static class C0082zza implements IPolylineDelegate {
            private IBinder a;

            C0082zza(IBinder iBinder) {
                this.a = iBinder;
            }

            public IBinder asBinder() {
                return this.a;
            }

            public boolean equalsRemote(IPolylineDelegate iPolylineDelegate) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IPolylineDelegate");
                    obtain.writeStrongBinder(iPolylineDelegate != null ? iPolylineDelegate.asBinder() : null);
                    boolean z = false;
                    this.a.transact(15, obtain, obtain2, 0);
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

            public int getColor() {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IPolylineDelegate");
                    this.a.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public String getId() {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IPolylineDelegate");
                    this.a.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public List<LatLng> getPoints() {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IPolylineDelegate");
                    this.a.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(LatLng.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public float getWidth() {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IPolylineDelegate");
                    this.a.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readFloat();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public float getZIndex() {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IPolylineDelegate");
                    this.a.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readFloat();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public int hashCodeRemote() {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IPolylineDelegate");
                    this.a.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public boolean isClickable() {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IPolylineDelegate");
                    boolean z = false;
                    this.a.transact(18, obtain, obtain2, 0);
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

            public boolean isGeodesic() {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IPolylineDelegate");
                    boolean z = false;
                    this.a.transact(14, obtain, obtain2, 0);
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

            public boolean isVisible() {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IPolylineDelegate");
                    boolean z = false;
                    this.a.transact(12, obtain, obtain2, 0);
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

            public void remove() {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IPolylineDelegate");
                    this.a.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void setClickable(boolean z) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IPolylineDelegate");
                    obtain.writeInt(z ? 1 : 0);
                    this.a.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void setColor(int i) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IPolylineDelegate");
                    obtain.writeInt(i);
                    this.a.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void setGeodesic(boolean z) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IPolylineDelegate");
                    obtain.writeInt(z ? 1 : 0);
                    this.a.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void setPoints(List<LatLng> list) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IPolylineDelegate");
                    obtain.writeTypedList(list);
                    this.a.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void setVisible(boolean z) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IPolylineDelegate");
                    obtain.writeInt(z ? 1 : 0);
                    this.a.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void setWidth(float f) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IPolylineDelegate");
                    obtain.writeFloat(f);
                    this.a.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void setZIndex(float f) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IPolylineDelegate");
                    obtain.writeFloat(f);
                    this.a.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public static IPolylineDelegate zzji(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.maps.model.internal.IPolylineDelegate");
            return (queryLocalInterface == null || !(queryLocalInterface instanceof IPolylineDelegate)) ? new C0082zza(iBinder) : (IPolylineDelegate) queryLocalInterface;
        }

        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            if (i != 1598968902) {
                boolean z = false;
                switch (i) {
                    case 1:
                        parcel.enforceInterface("com.google.android.gms.maps.model.internal.IPolylineDelegate");
                        remove();
                        parcel2.writeNoException();
                        return true;
                    case 2:
                        parcel.enforceInterface("com.google.android.gms.maps.model.internal.IPolylineDelegate");
                        String id2 = getId();
                        parcel2.writeNoException();
                        parcel2.writeString(id2);
                        return true;
                    case 3:
                        parcel.enforceInterface("com.google.android.gms.maps.model.internal.IPolylineDelegate");
                        setPoints(parcel.createTypedArrayList(LatLng.CREATOR));
                        parcel2.writeNoException();
                        return true;
                    case 4:
                        parcel.enforceInterface("com.google.android.gms.maps.model.internal.IPolylineDelegate");
                        List points = getPoints();
                        parcel2.writeNoException();
                        parcel2.writeTypedList(points);
                        return true;
                    case 5:
                        parcel.enforceInterface("com.google.android.gms.maps.model.internal.IPolylineDelegate");
                        setWidth(parcel.readFloat());
                        parcel2.writeNoException();
                        return true;
                    case 6:
                        parcel.enforceInterface("com.google.android.gms.maps.model.internal.IPolylineDelegate");
                        float width = getWidth();
                        parcel2.writeNoException();
                        parcel2.writeFloat(width);
                        return true;
                    case 7:
                        parcel.enforceInterface("com.google.android.gms.maps.model.internal.IPolylineDelegate");
                        setColor(parcel.readInt());
                        parcel2.writeNoException();
                        return true;
                    case 8:
                        parcel.enforceInterface("com.google.android.gms.maps.model.internal.IPolylineDelegate");
                        int color = getColor();
                        parcel2.writeNoException();
                        parcel2.writeInt(color);
                        return true;
                    case 9:
                        parcel.enforceInterface("com.google.android.gms.maps.model.internal.IPolylineDelegate");
                        setZIndex(parcel.readFloat());
                        parcel2.writeNoException();
                        return true;
                    case 10:
                        parcel.enforceInterface("com.google.android.gms.maps.model.internal.IPolylineDelegate");
                        float zIndex = getZIndex();
                        parcel2.writeNoException();
                        parcel2.writeFloat(zIndex);
                        return true;
                    case 11:
                        parcel.enforceInterface("com.google.android.gms.maps.model.internal.IPolylineDelegate");
                        if (parcel.readInt() != 0) {
                            z = true;
                        }
                        setVisible(z);
                        parcel2.writeNoException();
                        return true;
                    case 12:
                        parcel.enforceInterface("com.google.android.gms.maps.model.internal.IPolylineDelegate");
                        boolean isVisible = isVisible();
                        parcel2.writeNoException();
                        parcel2.writeInt(isVisible ? 1 : 0);
                        return true;
                    case 13:
                        parcel.enforceInterface("com.google.android.gms.maps.model.internal.IPolylineDelegate");
                        if (parcel.readInt() != 0) {
                            z = true;
                        }
                        setGeodesic(z);
                        parcel2.writeNoException();
                        return true;
                    case 14:
                        parcel.enforceInterface("com.google.android.gms.maps.model.internal.IPolylineDelegate");
                        boolean isGeodesic = isGeodesic();
                        parcel2.writeNoException();
                        parcel2.writeInt(isGeodesic ? 1 : 0);
                        return true;
                    case 15:
                        parcel.enforceInterface("com.google.android.gms.maps.model.internal.IPolylineDelegate");
                        boolean equalsRemote = equalsRemote(zzji(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        parcel2.writeInt(equalsRemote ? 1 : 0);
                        return true;
                    case 16:
                        parcel.enforceInterface("com.google.android.gms.maps.model.internal.IPolylineDelegate");
                        int hashCodeRemote = hashCodeRemote();
                        parcel2.writeNoException();
                        parcel2.writeInt(hashCodeRemote);
                        return true;
                    case 17:
                        parcel.enforceInterface("com.google.android.gms.maps.model.internal.IPolylineDelegate");
                        if (parcel.readInt() != 0) {
                            z = true;
                        }
                        setClickable(z);
                        parcel2.writeNoException();
                        return true;
                    case 18:
                        parcel.enforceInterface("com.google.android.gms.maps.model.internal.IPolylineDelegate");
                        boolean isClickable = isClickable();
                        parcel2.writeNoException();
                        parcel2.writeInt(isClickable ? 1 : 0);
                        return true;
                    default:
                        return super.onTransact(i, parcel, parcel2, i2);
                }
            } else {
                parcel2.writeString("com.google.android.gms.maps.model.internal.IPolylineDelegate");
                return true;
            }
        }
    }

    boolean equalsRemote(IPolylineDelegate iPolylineDelegate);

    int getColor();

    String getId();

    List<LatLng> getPoints();

    float getWidth();

    float getZIndex();

    int hashCodeRemote();

    boolean isClickable();

    boolean isGeodesic();

    boolean isVisible();

    void remove();

    void setClickable(boolean z);

    void setColor(int i);

    void setGeodesic(boolean z);

    void setPoints(List<LatLng> list);

    void setVisible(boolean z);

    void setWidth(float f);

    void setZIndex(float f);
}
