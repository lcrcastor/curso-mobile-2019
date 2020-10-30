package com.google.android.gms.maps.model.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import com.google.android.gms.dynamic.zzd;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

public interface zzc extends IInterface {

    public static abstract class zza extends Binder implements zzc {

        /* renamed from: com.google.android.gms.maps.model.internal.zzc$zza$zza reason: collision with other inner class name */
        static class C0086zza implements zzc {
            private IBinder a;

            C0086zza(IBinder iBinder) {
                this.a = iBinder;
            }

            public IBinder asBinder() {
                return this.a;
            }

            public float getBearing() {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IGroundOverlayDelegate");
                    this.a.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readFloat();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public LatLngBounds getBounds() {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IGroundOverlayDelegate");
                    this.a.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0 ? (LatLngBounds) LatLngBounds.CREATOR.createFromParcel(obtain2) : null;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public float getHeight() {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IGroundOverlayDelegate");
                    this.a.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readFloat();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public String getId() {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IGroundOverlayDelegate");
                    this.a.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public LatLng getPosition() {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IGroundOverlayDelegate");
                    this.a.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0 ? (LatLng) LatLng.CREATOR.createFromParcel(obtain2) : null;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public float getTransparency() {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IGroundOverlayDelegate");
                    this.a.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readFloat();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public float getWidth() {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IGroundOverlayDelegate");
                    this.a.transact(7, obtain, obtain2, 0);
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
                    obtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IGroundOverlayDelegate");
                    this.a.transact(14, obtain, obtain2, 0);
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
                    obtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IGroundOverlayDelegate");
                    this.a.transact(20, obtain, obtain2, 0);
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
                    obtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IGroundOverlayDelegate");
                    boolean z = false;
                    this.a.transact(23, obtain, obtain2, 0);
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
                    obtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IGroundOverlayDelegate");
                    boolean z = false;
                    this.a.transact(16, obtain, obtain2, 0);
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
                    obtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IGroundOverlayDelegate");
                    this.a.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void setBearing(float f) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IGroundOverlayDelegate");
                    obtain.writeFloat(f);
                    this.a.transact(11, obtain, obtain2, 0);
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
                    obtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IGroundOverlayDelegate");
                    obtain.writeInt(z ? 1 : 0);
                    this.a.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void setDimensions(float f) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IGroundOverlayDelegate");
                    obtain.writeFloat(f);
                    this.a.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void setPosition(LatLng latLng) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IGroundOverlayDelegate");
                    if (latLng != null) {
                        obtain.writeInt(1);
                        latLng.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.a.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void setPositionFromBounds(LatLngBounds latLngBounds) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IGroundOverlayDelegate");
                    if (latLngBounds != null) {
                        obtain.writeInt(1);
                        latLngBounds.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.a.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void setTransparency(float f) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IGroundOverlayDelegate");
                    obtain.writeFloat(f);
                    this.a.transact(17, obtain, obtain2, 0);
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
                    obtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IGroundOverlayDelegate");
                    obtain.writeInt(z ? 1 : 0);
                    this.a.transact(15, obtain, obtain2, 0);
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
                    obtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IGroundOverlayDelegate");
                    obtain.writeFloat(f);
                    this.a.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void zzak(zzd zzd) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IGroundOverlayDelegate");
                    obtain.writeStrongBinder(zzd != null ? zzd.asBinder() : null);
                    this.a.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public boolean zzb(zzc zzc) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IGroundOverlayDelegate");
                    obtain.writeStrongBinder(zzc != null ? zzc.asBinder() : null);
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

            public void zzg(float f, float f2) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IGroundOverlayDelegate");
                    obtain.writeFloat(f);
                    obtain.writeFloat(f2);
                    this.a.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public static zzc zzjd(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.maps.model.internal.IGroundOverlayDelegate");
            return (queryLocalInterface == null || !(queryLocalInterface instanceof zzc)) ? new C0086zza(iBinder) : (zzc) queryLocalInterface;
        }

        /* JADX WARNING: type inference failed for: r0v1 */
        /* JADX WARNING: type inference failed for: r0v2, types: [com.google.android.gms.maps.model.LatLng] */
        /* JADX WARNING: type inference failed for: r0v4, types: [com.google.android.gms.maps.model.LatLng] */
        /* JADX WARNING: type inference failed for: r0v5, types: [com.google.android.gms.maps.model.LatLngBounds] */
        /* JADX WARNING: type inference failed for: r0v7, types: [com.google.android.gms.maps.model.LatLngBounds] */
        /* JADX WARNING: type inference failed for: r0v8 */
        /* JADX WARNING: type inference failed for: r0v9 */
        /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r0v1
          assigns: [?[int, float, boolean, short, byte, char, OBJECT, ARRAY], com.google.android.gms.maps.model.LatLngBounds, com.google.android.gms.maps.model.LatLng]
          uses: [com.google.android.gms.maps.model.LatLng, com.google.android.gms.maps.model.LatLngBounds]
          mth insns count: 174
        	at jadx.core.dex.visitors.typeinference.TypeSearch.fillTypeCandidates(TypeSearch.java:237)
        	at java.util.ArrayList.forEach(ArrayList.java:1249)
        	at jadx.core.dex.visitors.typeinference.TypeSearch.run(TypeSearch.java:53)
        	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runMultiVariableSearch(TypeInferenceVisitor.java:99)
        	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:92)
        	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:27)
        	at jadx.core.dex.visitors.DepthTraversal.lambda$visit$1(DepthTraversal.java:14)
        	at java.util.ArrayList.forEach(ArrayList.java:1249)
        	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
        	at jadx.core.dex.visitors.DepthTraversal.lambda$visit$0(DepthTraversal.java:13)
        	at java.util.ArrayList.forEach(ArrayList.java:1249)
        	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:13)
        	at jadx.core.ProcessClass.process(ProcessClass.java:30)
        	at jadx.core.ProcessClass.lambda$processDependencies$0(ProcessClass.java:49)
        	at java.util.ArrayList.forEach(ArrayList.java:1249)
        	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:49)
        	at jadx.core.ProcessClass.process(ProcessClass.java:35)
        	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:311)
        	at jadx.api.JavaClass.decompile(JavaClass.java:62)
        	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:217)
         */
        /* JADX WARNING: Unknown variable types count: 3 */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTransact(int r4, android.os.Parcel r5, android.os.Parcel r6, int r7) {
            /*
                r3 = this;
                r0 = 1598968902(0x5f4e5446, float:1.4867585E19)
                r1 = 1
                if (r4 == r0) goto L_0x01ba
                r0 = 0
                r2 = 0
                switch(r4) {
                    case 1: goto L_0x01ae;
                    case 2: goto L_0x019e;
                    case 3: goto L_0x0183;
                    case 4: goto L_0x016a;
                    case 5: goto L_0x015a;
                    case 6: goto L_0x0146;
                    case 7: goto L_0x0136;
                    case 8: goto L_0x0126;
                    case 9: goto L_0x010b;
                    case 10: goto L_0x00f2;
                    case 11: goto L_0x00e2;
                    case 12: goto L_0x00d2;
                    case 13: goto L_0x00c2;
                    case 14: goto L_0x00b2;
                    case 15: goto L_0x009f;
                    case 16: goto L_0x008f;
                    case 17: goto L_0x007f;
                    case 18: goto L_0x006f;
                    case 19: goto L_0x0057;
                    case 20: goto L_0x0047;
                    case 21: goto L_0x0033;
                    case 22: goto L_0x0020;
                    case 23: goto L_0x0010;
                    default: goto L_0x000b;
                }
            L_0x000b:
                boolean r4 = super.onTransact(r4, r5, r6, r7)
                return r4
            L_0x0010:
                java.lang.String r4 = "com.google.android.gms.maps.model.internal.IGroundOverlayDelegate"
                r5.enforceInterface(r4)
                boolean r4 = r3.isClickable()
                r6.writeNoException()
                r6.writeInt(r4)
                return r1
            L_0x0020:
                java.lang.String r4 = "com.google.android.gms.maps.model.internal.IGroundOverlayDelegate"
                r5.enforceInterface(r4)
                int r4 = r5.readInt()
                if (r4 == 0) goto L_0x002c
                r2 = 1
            L_0x002c:
                r3.setClickable(r2)
                r6.writeNoException()
                return r1
            L_0x0033:
                java.lang.String r4 = "com.google.android.gms.maps.model.internal.IGroundOverlayDelegate"
                r5.enforceInterface(r4)
                android.os.IBinder r4 = r5.readStrongBinder()
                com.google.android.gms.dynamic.zzd r4 = com.google.android.gms.dynamic.zzd.zza.zzfe(r4)
                r3.zzak(r4)
                r6.writeNoException()
                return r1
            L_0x0047:
                java.lang.String r4 = "com.google.android.gms.maps.model.internal.IGroundOverlayDelegate"
                r5.enforceInterface(r4)
                int r4 = r3.hashCodeRemote()
                r6.writeNoException()
                r6.writeInt(r4)
                return r1
            L_0x0057:
                java.lang.String r4 = "com.google.android.gms.maps.model.internal.IGroundOverlayDelegate"
                r5.enforceInterface(r4)
                android.os.IBinder r4 = r5.readStrongBinder()
                com.google.android.gms.maps.model.internal.zzc r4 = zzjd(r4)
                boolean r4 = r3.zzb(r4)
                r6.writeNoException()
                r6.writeInt(r4)
                return r1
            L_0x006f:
                java.lang.String r4 = "com.google.android.gms.maps.model.internal.IGroundOverlayDelegate"
                r5.enforceInterface(r4)
                float r4 = r3.getTransparency()
                r6.writeNoException()
                r6.writeFloat(r4)
                return r1
            L_0x007f:
                java.lang.String r4 = "com.google.android.gms.maps.model.internal.IGroundOverlayDelegate"
                r5.enforceInterface(r4)
                float r4 = r5.readFloat()
                r3.setTransparency(r4)
                r6.writeNoException()
                return r1
            L_0x008f:
                java.lang.String r4 = "com.google.android.gms.maps.model.internal.IGroundOverlayDelegate"
                r5.enforceInterface(r4)
                boolean r4 = r3.isVisible()
                r6.writeNoException()
                r6.writeInt(r4)
                return r1
            L_0x009f:
                java.lang.String r4 = "com.google.android.gms.maps.model.internal.IGroundOverlayDelegate"
                r5.enforceInterface(r4)
                int r4 = r5.readInt()
                if (r4 == 0) goto L_0x00ab
                r2 = 1
            L_0x00ab:
                r3.setVisible(r2)
                r6.writeNoException()
                return r1
            L_0x00b2:
                java.lang.String r4 = "com.google.android.gms.maps.model.internal.IGroundOverlayDelegate"
                r5.enforceInterface(r4)
                float r4 = r3.getZIndex()
                r6.writeNoException()
                r6.writeFloat(r4)
                return r1
            L_0x00c2:
                java.lang.String r4 = "com.google.android.gms.maps.model.internal.IGroundOverlayDelegate"
                r5.enforceInterface(r4)
                float r4 = r5.readFloat()
                r3.setZIndex(r4)
                r6.writeNoException()
                return r1
            L_0x00d2:
                java.lang.String r4 = "com.google.android.gms.maps.model.internal.IGroundOverlayDelegate"
                r5.enforceInterface(r4)
                float r4 = r3.getBearing()
                r6.writeNoException()
                r6.writeFloat(r4)
                return r1
            L_0x00e2:
                java.lang.String r4 = "com.google.android.gms.maps.model.internal.IGroundOverlayDelegate"
                r5.enforceInterface(r4)
                float r4 = r5.readFloat()
                r3.setBearing(r4)
                r6.writeNoException()
                return r1
            L_0x00f2:
                java.lang.String r4 = "com.google.android.gms.maps.model.internal.IGroundOverlayDelegate"
                r5.enforceInterface(r4)
                com.google.android.gms.maps.model.LatLngBounds r4 = r3.getBounds()
                r6.writeNoException()
                if (r4 == 0) goto L_0x0107
                r6.writeInt(r1)
                r4.writeToParcel(r6, r1)
                return r1
            L_0x0107:
                r6.writeInt(r2)
                return r1
            L_0x010b:
                java.lang.String r4 = "com.google.android.gms.maps.model.internal.IGroundOverlayDelegate"
                r5.enforceInterface(r4)
                int r4 = r5.readInt()
                if (r4 == 0) goto L_0x011f
                com.google.android.gms.maps.model.zzd r4 = com.google.android.gms.maps.model.LatLngBounds.CREATOR
                java.lang.Object r4 = r4.createFromParcel(r5)
                r0 = r4
                com.google.android.gms.maps.model.LatLngBounds r0 = (com.google.android.gms.maps.model.LatLngBounds) r0
            L_0x011f:
                r3.setPositionFromBounds(r0)
                r6.writeNoException()
                return r1
            L_0x0126:
                java.lang.String r4 = "com.google.android.gms.maps.model.internal.IGroundOverlayDelegate"
                r5.enforceInterface(r4)
                float r4 = r3.getHeight()
                r6.writeNoException()
                r6.writeFloat(r4)
                return r1
            L_0x0136:
                java.lang.String r4 = "com.google.android.gms.maps.model.internal.IGroundOverlayDelegate"
                r5.enforceInterface(r4)
                float r4 = r3.getWidth()
                r6.writeNoException()
                r6.writeFloat(r4)
                return r1
            L_0x0146:
                java.lang.String r4 = "com.google.android.gms.maps.model.internal.IGroundOverlayDelegate"
                r5.enforceInterface(r4)
                float r4 = r5.readFloat()
                float r5 = r5.readFloat()
                r3.zzg(r4, r5)
                r6.writeNoException()
                return r1
            L_0x015a:
                java.lang.String r4 = "com.google.android.gms.maps.model.internal.IGroundOverlayDelegate"
                r5.enforceInterface(r4)
                float r4 = r5.readFloat()
                r3.setDimensions(r4)
                r6.writeNoException()
                return r1
            L_0x016a:
                java.lang.String r4 = "com.google.android.gms.maps.model.internal.IGroundOverlayDelegate"
                r5.enforceInterface(r4)
                com.google.android.gms.maps.model.LatLng r4 = r3.getPosition()
                r6.writeNoException()
                if (r4 == 0) goto L_0x017f
                r6.writeInt(r1)
                r4.writeToParcel(r6, r1)
                return r1
            L_0x017f:
                r6.writeInt(r2)
                return r1
            L_0x0183:
                java.lang.String r4 = "com.google.android.gms.maps.model.internal.IGroundOverlayDelegate"
                r5.enforceInterface(r4)
                int r4 = r5.readInt()
                if (r4 == 0) goto L_0x0197
                com.google.android.gms.maps.model.zze r4 = com.google.android.gms.maps.model.LatLng.CREATOR
                java.lang.Object r4 = r4.createFromParcel(r5)
                r0 = r4
                com.google.android.gms.maps.model.LatLng r0 = (com.google.android.gms.maps.model.LatLng) r0
            L_0x0197:
                r3.setPosition(r0)
                r6.writeNoException()
                return r1
            L_0x019e:
                java.lang.String r4 = "com.google.android.gms.maps.model.internal.IGroundOverlayDelegate"
                r5.enforceInterface(r4)
                java.lang.String r4 = r3.getId()
                r6.writeNoException()
                r6.writeString(r4)
                return r1
            L_0x01ae:
                java.lang.String r4 = "com.google.android.gms.maps.model.internal.IGroundOverlayDelegate"
                r5.enforceInterface(r4)
                r3.remove()
                r6.writeNoException()
                return r1
            L_0x01ba:
                java.lang.String r4 = "com.google.android.gms.maps.model.internal.IGroundOverlayDelegate"
                r6.writeString(r4)
                return r1
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.maps.model.internal.zzc.zza.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }
    }

    float getBearing();

    LatLngBounds getBounds();

    float getHeight();

    String getId();

    LatLng getPosition();

    float getTransparency();

    float getWidth();

    float getZIndex();

    int hashCodeRemote();

    boolean isClickable();

    boolean isVisible();

    void remove();

    void setBearing(float f);

    void setClickable(boolean z);

    void setDimensions(float f);

    void setPosition(LatLng latLng);

    void setPositionFromBounds(LatLngBounds latLngBounds);

    void setTransparency(float f);

    void setVisible(boolean z);

    void setZIndex(float f);

    void zzak(zzd zzd);

    boolean zzb(zzc zzc);

    void zzg(float f, float f2);
}
