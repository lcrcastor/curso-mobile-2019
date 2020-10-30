package com.google.android.gms.maps.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;

public interface IUiSettingsDelegate extends IInterface {

    public static abstract class zza extends Binder implements IUiSettingsDelegate {

        /* renamed from: com.google.android.gms.maps.internal.IUiSettingsDelegate$zza$zza reason: collision with other inner class name */
        static class C0049zza implements IUiSettingsDelegate {
            private IBinder a;

            C0049zza(IBinder iBinder) {
                this.a = iBinder;
            }

            public IBinder asBinder() {
                return this.a;
            }

            public boolean isCompassEnabled() {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IUiSettingsDelegate");
                    boolean z = false;
                    this.a.transact(10, obtain, obtain2, 0);
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

            public boolean isIndoorLevelPickerEnabled() {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IUiSettingsDelegate");
                    boolean z = false;
                    this.a.transact(17, obtain, obtain2, 0);
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

            public boolean isMapToolbarEnabled() {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IUiSettingsDelegate");
                    boolean z = false;
                    this.a.transact(19, obtain, obtain2, 0);
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

            public boolean isMyLocationButtonEnabled() {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IUiSettingsDelegate");
                    boolean z = false;
                    this.a.transact(11, obtain, obtain2, 0);
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

            public boolean isRotateGesturesEnabled() {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IUiSettingsDelegate");
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

            public boolean isScrollGesturesEnabled() {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IUiSettingsDelegate");
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

            public boolean isTiltGesturesEnabled() {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IUiSettingsDelegate");
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

            public boolean isZoomControlsEnabled() {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IUiSettingsDelegate");
                    boolean z = false;
                    this.a.transact(9, obtain, obtain2, 0);
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

            public boolean isZoomGesturesEnabled() {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IUiSettingsDelegate");
                    boolean z = false;
                    this.a.transact(13, obtain, obtain2, 0);
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

            public void setAllGesturesEnabled(boolean z) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IUiSettingsDelegate");
                    obtain.writeInt(z ? 1 : 0);
                    this.a.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void setCompassEnabled(boolean z) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IUiSettingsDelegate");
                    obtain.writeInt(z ? 1 : 0);
                    this.a.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void setIndoorLevelPickerEnabled(boolean z) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IUiSettingsDelegate");
                    obtain.writeInt(z ? 1 : 0);
                    this.a.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void setMapToolbarEnabled(boolean z) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IUiSettingsDelegate");
                    obtain.writeInt(z ? 1 : 0);
                    this.a.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void setMyLocationButtonEnabled(boolean z) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IUiSettingsDelegate");
                    obtain.writeInt(z ? 1 : 0);
                    this.a.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void setRotateGesturesEnabled(boolean z) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IUiSettingsDelegate");
                    obtain.writeInt(z ? 1 : 0);
                    this.a.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void setScrollGesturesEnabled(boolean z) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IUiSettingsDelegate");
                    obtain.writeInt(z ? 1 : 0);
                    this.a.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void setTiltGesturesEnabled(boolean z) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IUiSettingsDelegate");
                    obtain.writeInt(z ? 1 : 0);
                    this.a.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void setZoomControlsEnabled(boolean z) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IUiSettingsDelegate");
                    obtain.writeInt(z ? 1 : 0);
                    this.a.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void setZoomGesturesEnabled(boolean z) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IUiSettingsDelegate");
                    obtain.writeInt(z ? 1 : 0);
                    this.a.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public static IUiSettingsDelegate zzja(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.maps.internal.IUiSettingsDelegate");
            return (queryLocalInterface == null || !(queryLocalInterface instanceof IUiSettingsDelegate)) ? new C0049zza(iBinder) : (IUiSettingsDelegate) queryLocalInterface;
        }

        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            if (i != 1598968902) {
                boolean z = false;
                switch (i) {
                    case 1:
                        parcel.enforceInterface("com.google.android.gms.maps.internal.IUiSettingsDelegate");
                        if (parcel.readInt() != 0) {
                            z = true;
                        }
                        setZoomControlsEnabled(z);
                        parcel2.writeNoException();
                        return true;
                    case 2:
                        parcel.enforceInterface("com.google.android.gms.maps.internal.IUiSettingsDelegate");
                        if (parcel.readInt() != 0) {
                            z = true;
                        }
                        setCompassEnabled(z);
                        parcel2.writeNoException();
                        return true;
                    case 3:
                        parcel.enforceInterface("com.google.android.gms.maps.internal.IUiSettingsDelegate");
                        if (parcel.readInt() != 0) {
                            z = true;
                        }
                        setMyLocationButtonEnabled(z);
                        parcel2.writeNoException();
                        return true;
                    case 4:
                        parcel.enforceInterface("com.google.android.gms.maps.internal.IUiSettingsDelegate");
                        if (parcel.readInt() != 0) {
                            z = true;
                        }
                        setScrollGesturesEnabled(z);
                        parcel2.writeNoException();
                        return true;
                    case 5:
                        parcel.enforceInterface("com.google.android.gms.maps.internal.IUiSettingsDelegate");
                        if (parcel.readInt() != 0) {
                            z = true;
                        }
                        setZoomGesturesEnabled(z);
                        parcel2.writeNoException();
                        return true;
                    case 6:
                        parcel.enforceInterface("com.google.android.gms.maps.internal.IUiSettingsDelegate");
                        if (parcel.readInt() != 0) {
                            z = true;
                        }
                        setTiltGesturesEnabled(z);
                        parcel2.writeNoException();
                        return true;
                    case 7:
                        parcel.enforceInterface("com.google.android.gms.maps.internal.IUiSettingsDelegate");
                        if (parcel.readInt() != 0) {
                            z = true;
                        }
                        setRotateGesturesEnabled(z);
                        parcel2.writeNoException();
                        return true;
                    case 8:
                        parcel.enforceInterface("com.google.android.gms.maps.internal.IUiSettingsDelegate");
                        if (parcel.readInt() != 0) {
                            z = true;
                        }
                        setAllGesturesEnabled(z);
                        parcel2.writeNoException();
                        return true;
                    case 9:
                        parcel.enforceInterface("com.google.android.gms.maps.internal.IUiSettingsDelegate");
                        boolean isZoomControlsEnabled = isZoomControlsEnabled();
                        parcel2.writeNoException();
                        parcel2.writeInt(isZoomControlsEnabled ? 1 : 0);
                        return true;
                    case 10:
                        parcel.enforceInterface("com.google.android.gms.maps.internal.IUiSettingsDelegate");
                        boolean isCompassEnabled = isCompassEnabled();
                        parcel2.writeNoException();
                        parcel2.writeInt(isCompassEnabled ? 1 : 0);
                        return true;
                    case 11:
                        parcel.enforceInterface("com.google.android.gms.maps.internal.IUiSettingsDelegate");
                        boolean isMyLocationButtonEnabled = isMyLocationButtonEnabled();
                        parcel2.writeNoException();
                        parcel2.writeInt(isMyLocationButtonEnabled ? 1 : 0);
                        return true;
                    case 12:
                        parcel.enforceInterface("com.google.android.gms.maps.internal.IUiSettingsDelegate");
                        boolean isScrollGesturesEnabled = isScrollGesturesEnabled();
                        parcel2.writeNoException();
                        parcel2.writeInt(isScrollGesturesEnabled ? 1 : 0);
                        return true;
                    case 13:
                        parcel.enforceInterface("com.google.android.gms.maps.internal.IUiSettingsDelegate");
                        boolean isZoomGesturesEnabled = isZoomGesturesEnabled();
                        parcel2.writeNoException();
                        parcel2.writeInt(isZoomGesturesEnabled ? 1 : 0);
                        return true;
                    case 14:
                        parcel.enforceInterface("com.google.android.gms.maps.internal.IUiSettingsDelegate");
                        boolean isTiltGesturesEnabled = isTiltGesturesEnabled();
                        parcel2.writeNoException();
                        parcel2.writeInt(isTiltGesturesEnabled ? 1 : 0);
                        return true;
                    case 15:
                        parcel.enforceInterface("com.google.android.gms.maps.internal.IUiSettingsDelegate");
                        boolean isRotateGesturesEnabled = isRotateGesturesEnabled();
                        parcel2.writeNoException();
                        parcel2.writeInt(isRotateGesturesEnabled ? 1 : 0);
                        return true;
                    case 16:
                        parcel.enforceInterface("com.google.android.gms.maps.internal.IUiSettingsDelegate");
                        if (parcel.readInt() != 0) {
                            z = true;
                        }
                        setIndoorLevelPickerEnabled(z);
                        parcel2.writeNoException();
                        return true;
                    case 17:
                        parcel.enforceInterface("com.google.android.gms.maps.internal.IUiSettingsDelegate");
                        boolean isIndoorLevelPickerEnabled = isIndoorLevelPickerEnabled();
                        parcel2.writeNoException();
                        parcel2.writeInt(isIndoorLevelPickerEnabled ? 1 : 0);
                        return true;
                    case 18:
                        parcel.enforceInterface("com.google.android.gms.maps.internal.IUiSettingsDelegate");
                        if (parcel.readInt() != 0) {
                            z = true;
                        }
                        setMapToolbarEnabled(z);
                        parcel2.writeNoException();
                        return true;
                    case 19:
                        parcel.enforceInterface("com.google.android.gms.maps.internal.IUiSettingsDelegate");
                        boolean isMapToolbarEnabled = isMapToolbarEnabled();
                        parcel2.writeNoException();
                        parcel2.writeInt(isMapToolbarEnabled ? 1 : 0);
                        return true;
                    default:
                        return super.onTransact(i, parcel, parcel2, i2);
                }
            } else {
                parcel2.writeString("com.google.android.gms.maps.internal.IUiSettingsDelegate");
                return true;
            }
        }
    }

    boolean isCompassEnabled();

    boolean isIndoorLevelPickerEnabled();

    boolean isMapToolbarEnabled();

    boolean isMyLocationButtonEnabled();

    boolean isRotateGesturesEnabled();

    boolean isScrollGesturesEnabled();

    boolean isTiltGesturesEnabled();

    boolean isZoomControlsEnabled();

    boolean isZoomGesturesEnabled();

    void setAllGesturesEnabled(boolean z);

    void setCompassEnabled(boolean z);

    void setIndoorLevelPickerEnabled(boolean z);

    void setMapToolbarEnabled(boolean z);

    void setMyLocationButtonEnabled(boolean z);

    void setRotateGesturesEnabled(boolean z);

    void setScrollGesturesEnabled(boolean z);

    void setTiltGesturesEnabled(boolean z);

    void setZoomControlsEnabled(boolean z);

    void setZoomGesturesEnabled(boolean z);
}
