package com.google.android.gms.maps.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import com.google.android.gms.dynamic.zzd;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.StreetViewPanoramaCamera;
import com.google.android.gms.maps.model.StreetViewPanoramaLocation;
import com.google.android.gms.maps.model.StreetViewPanoramaOrientation;

public interface IStreetViewPanoramaDelegate extends IInterface {

    public static abstract class zza extends Binder implements IStreetViewPanoramaDelegate {

        /* renamed from: com.google.android.gms.maps.internal.IStreetViewPanoramaDelegate$zza$zza reason: collision with other inner class name */
        static class C0046zza implements IStreetViewPanoramaDelegate {
            private IBinder a;

            C0046zza(IBinder iBinder) {
                this.a = iBinder;
            }

            public void animateTo(StreetViewPanoramaCamera streetViewPanoramaCamera, long j) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IStreetViewPanoramaDelegate");
                    if (streetViewPanoramaCamera != null) {
                        obtain.writeInt(1);
                        streetViewPanoramaCamera.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeLong(j);
                    this.a.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public IBinder asBinder() {
                return this.a;
            }

            public void enablePanning(boolean z) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IStreetViewPanoramaDelegate");
                    obtain.writeInt(z ? 1 : 0);
                    this.a.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void enableStreetNames(boolean z) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IStreetViewPanoramaDelegate");
                    obtain.writeInt(z ? 1 : 0);
                    this.a.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void enableUserNavigation(boolean z) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IStreetViewPanoramaDelegate");
                    obtain.writeInt(z ? 1 : 0);
                    this.a.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void enableZoom(boolean z) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IStreetViewPanoramaDelegate");
                    obtain.writeInt(z ? 1 : 0);
                    this.a.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public StreetViewPanoramaCamera getPanoramaCamera() {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IStreetViewPanoramaDelegate");
                    this.a.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0 ? (StreetViewPanoramaCamera) StreetViewPanoramaCamera.CREATOR.createFromParcel(obtain2) : null;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public StreetViewPanoramaLocation getStreetViewPanoramaLocation() {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IStreetViewPanoramaDelegate");
                    this.a.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0 ? (StreetViewPanoramaLocation) StreetViewPanoramaLocation.CREATOR.createFromParcel(obtain2) : null;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public boolean isPanningGesturesEnabled() {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IStreetViewPanoramaDelegate");
                    boolean z = false;
                    this.a.transact(6, obtain, obtain2, 0);
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

            public boolean isStreetNamesEnabled() {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IStreetViewPanoramaDelegate");
                    boolean z = false;
                    this.a.transact(8, obtain, obtain2, 0);
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

            public boolean isUserNavigationEnabled() {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IStreetViewPanoramaDelegate");
                    boolean z = false;
                    this.a.transact(7, obtain, obtain2, 0);
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
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IStreetViewPanoramaDelegate");
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

            public zzd orientationToPoint(StreetViewPanoramaOrientation streetViewPanoramaOrientation) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IStreetViewPanoramaDelegate");
                    if (streetViewPanoramaOrientation != null) {
                        obtain.writeInt(1);
                        streetViewPanoramaOrientation.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.a.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                    return com.google.android.gms.dynamic.zzd.zza.zzfe(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public StreetViewPanoramaOrientation pointToOrientation(zzd zzd) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IStreetViewPanoramaDelegate");
                    StreetViewPanoramaOrientation streetViewPanoramaOrientation = null;
                    obtain.writeStrongBinder(zzd != null ? zzd.asBinder() : null);
                    this.a.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                    if (obtain2.readInt() != 0) {
                        streetViewPanoramaOrientation = (StreetViewPanoramaOrientation) StreetViewPanoramaOrientation.CREATOR.createFromParcel(obtain2);
                    }
                    return streetViewPanoramaOrientation;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void setOnStreetViewPanoramaCameraChangeListener(zzab zzab) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IStreetViewPanoramaDelegate");
                    obtain.writeStrongBinder(zzab != null ? zzab.asBinder() : null);
                    this.a.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void setOnStreetViewPanoramaChangeListener(zzac zzac) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IStreetViewPanoramaDelegate");
                    obtain.writeStrongBinder(zzac != null ? zzac.asBinder() : null);
                    this.a.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void setOnStreetViewPanoramaClickListener(zzad zzad) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IStreetViewPanoramaDelegate");
                    obtain.writeStrongBinder(zzad != null ? zzad.asBinder() : null);
                    this.a.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void setOnStreetViewPanoramaLongClickListener(zzae zzae) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IStreetViewPanoramaDelegate");
                    obtain.writeStrongBinder(zzae != null ? zzae.asBinder() : null);
                    this.a.transact(20, obtain, obtain2, 0);
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
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IStreetViewPanoramaDelegate");
                    if (latLng != null) {
                        obtain.writeInt(1);
                        latLng.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.a.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void setPositionWithID(String str) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IStreetViewPanoramaDelegate");
                    obtain.writeString(str);
                    this.a.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void setPositionWithRadius(LatLng latLng, int i) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IStreetViewPanoramaDelegate");
                    if (latLng != null) {
                        obtain.writeInt(1);
                        latLng.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeInt(i);
                    this.a.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public static IStreetViewPanoramaDelegate zzix(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.maps.internal.IStreetViewPanoramaDelegate");
            return (queryLocalInterface == null || !(queryLocalInterface instanceof IStreetViewPanoramaDelegate)) ? new C0046zza(iBinder) : (IStreetViewPanoramaDelegate) queryLocalInterface;
        }

        /* JADX WARNING: type inference failed for: r0v1 */
        /* JADX WARNING: type inference failed for: r0v2, types: [com.google.android.gms.maps.model.StreetViewPanoramaCamera] */
        /* JADX WARNING: type inference failed for: r0v4, types: [com.google.android.gms.maps.model.StreetViewPanoramaCamera] */
        /* JADX WARNING: type inference failed for: r0v5, types: [com.google.android.gms.maps.model.LatLng] */
        /* JADX WARNING: type inference failed for: r0v7, types: [com.google.android.gms.maps.model.LatLng] */
        /* JADX WARNING: type inference failed for: r0v8, types: [com.google.android.gms.maps.model.LatLng] */
        /* JADX WARNING: type inference failed for: r0v10, types: [com.google.android.gms.maps.model.LatLng] */
        /* JADX WARNING: type inference failed for: r0v11, types: [android.os.IBinder] */
        /* JADX WARNING: type inference failed for: r0v12, types: [android.os.IBinder] */
        /* JADX WARNING: type inference failed for: r0v13 */
        /* JADX WARNING: type inference failed for: r0v14 */
        /* JADX WARNING: type inference failed for: r0v15 */
        /* JADX WARNING: type inference failed for: r0v16 */
        /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r0v1
          assigns: [?[int, float, boolean, short, byte, char, OBJECT, ARRAY], com.google.android.gms.maps.model.LatLng, com.google.android.gms.maps.model.StreetViewPanoramaCamera, android.os.IBinder]
          uses: [com.google.android.gms.maps.model.StreetViewPanoramaCamera, com.google.android.gms.maps.model.LatLng, android.os.IBinder]
          mth insns count: 184
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
        /* JADX WARNING: Unknown variable types count: 5 */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTransact(int r4, android.os.Parcel r5, android.os.Parcel r6, int r7) {
            /*
                r3 = this;
                r0 = 1598968902(0x5f4e5446, float:1.4867585E19)
                r1 = 1
                if (r4 == r0) goto L_0x01ce
                r0 = 0
                r2 = 0
                switch(r4) {
                    case 1: goto L_0x01bb;
                    case 2: goto L_0x01a8;
                    case 3: goto L_0x0195;
                    case 4: goto L_0x0182;
                    case 5: goto L_0x0172;
                    case 6: goto L_0x0162;
                    case 7: goto L_0x0152;
                    case 8: goto L_0x0142;
                    case 9: goto L_0x0123;
                    case 10: goto L_0x010a;
                    case 11: goto L_0x00fa;
                    case 12: goto L_0x00df;
                    case 13: goto L_0x00c0;
                    case 14: goto L_0x00a7;
                    case 15: goto L_0x0093;
                    case 16: goto L_0x007f;
                    case 17: goto L_0x006b;
                    case 18: goto L_0x004a;
                    case 19: goto L_0x0024;
                    case 20: goto L_0x0010;
                    default: goto L_0x000b;
                }
            L_0x000b:
                boolean r4 = super.onTransact(r4, r5, r6, r7)
                return r4
            L_0x0010:
                java.lang.String r4 = "com.google.android.gms.maps.internal.IStreetViewPanoramaDelegate"
                r5.enforceInterface(r4)
                android.os.IBinder r4 = r5.readStrongBinder()
                com.google.android.gms.maps.internal.zzae r4 = com.google.android.gms.maps.internal.zzae.zza.zzit(r4)
                r3.setOnStreetViewPanoramaLongClickListener(r4)
                r6.writeNoException()
                return r1
            L_0x0024:
                java.lang.String r4 = "com.google.android.gms.maps.internal.IStreetViewPanoramaDelegate"
                r5.enforceInterface(r4)
                int r4 = r5.readInt()
                if (r4 == 0) goto L_0x0038
                com.google.android.gms.maps.model.zzn r4 = com.google.android.gms.maps.model.StreetViewPanoramaOrientation.CREATOR
                java.lang.Object r4 = r4.createFromParcel(r5)
                com.google.android.gms.maps.model.StreetViewPanoramaOrientation r4 = (com.google.android.gms.maps.model.StreetViewPanoramaOrientation) r4
                goto L_0x0039
            L_0x0038:
                r4 = r0
            L_0x0039:
                com.google.android.gms.dynamic.zzd r4 = r3.orientationToPoint(r4)
                r6.writeNoException()
                if (r4 == 0) goto L_0x0046
                android.os.IBinder r0 = r4.asBinder()
            L_0x0046:
                r6.writeStrongBinder(r0)
                return r1
            L_0x004a:
                java.lang.String r4 = "com.google.android.gms.maps.internal.IStreetViewPanoramaDelegate"
                r5.enforceInterface(r4)
                android.os.IBinder r4 = r5.readStrongBinder()
                com.google.android.gms.dynamic.zzd r4 = com.google.android.gms.dynamic.zzd.zza.zzfe(r4)
                com.google.android.gms.maps.model.StreetViewPanoramaOrientation r4 = r3.pointToOrientation(r4)
                r6.writeNoException()
                if (r4 == 0) goto L_0x0067
                r6.writeInt(r1)
                r4.writeToParcel(r6, r1)
                return r1
            L_0x0067:
                r6.writeInt(r2)
                return r1
            L_0x006b:
                java.lang.String r4 = "com.google.android.gms.maps.internal.IStreetViewPanoramaDelegate"
                r5.enforceInterface(r4)
                android.os.IBinder r4 = r5.readStrongBinder()
                com.google.android.gms.maps.internal.zzad r4 = com.google.android.gms.maps.internal.zzad.zza.zzis(r4)
                r3.setOnStreetViewPanoramaClickListener(r4)
                r6.writeNoException()
                return r1
            L_0x007f:
                java.lang.String r4 = "com.google.android.gms.maps.internal.IStreetViewPanoramaDelegate"
                r5.enforceInterface(r4)
                android.os.IBinder r4 = r5.readStrongBinder()
                com.google.android.gms.maps.internal.zzab r4 = com.google.android.gms.maps.internal.zzab.zza.zziq(r4)
                r3.setOnStreetViewPanoramaCameraChangeListener(r4)
                r6.writeNoException()
                return r1
            L_0x0093:
                java.lang.String r4 = "com.google.android.gms.maps.internal.IStreetViewPanoramaDelegate"
                r5.enforceInterface(r4)
                android.os.IBinder r4 = r5.readStrongBinder()
                com.google.android.gms.maps.internal.zzac r4 = com.google.android.gms.maps.internal.zzac.zza.zzir(r4)
                r3.setOnStreetViewPanoramaChangeListener(r4)
                r6.writeNoException()
                return r1
            L_0x00a7:
                java.lang.String r4 = "com.google.android.gms.maps.internal.IStreetViewPanoramaDelegate"
                r5.enforceInterface(r4)
                com.google.android.gms.maps.model.StreetViewPanoramaLocation r4 = r3.getStreetViewPanoramaLocation()
                r6.writeNoException()
                if (r4 == 0) goto L_0x00bc
                r6.writeInt(r1)
                r4.writeToParcel(r6, r1)
                return r1
            L_0x00bc:
                r6.writeInt(r2)
                return r1
            L_0x00c0:
                java.lang.String r4 = "com.google.android.gms.maps.internal.IStreetViewPanoramaDelegate"
                r5.enforceInterface(r4)
                int r4 = r5.readInt()
                if (r4 == 0) goto L_0x00d4
                com.google.android.gms.maps.model.zze r4 = com.google.android.gms.maps.model.LatLng.CREATOR
                java.lang.Object r4 = r4.createFromParcel(r5)
                r0 = r4
                com.google.android.gms.maps.model.LatLng r0 = (com.google.android.gms.maps.model.LatLng) r0
            L_0x00d4:
                int r4 = r5.readInt()
                r3.setPositionWithRadius(r0, r4)
                r6.writeNoException()
                return r1
            L_0x00df:
                java.lang.String r4 = "com.google.android.gms.maps.internal.IStreetViewPanoramaDelegate"
                r5.enforceInterface(r4)
                int r4 = r5.readInt()
                if (r4 == 0) goto L_0x00f3
                com.google.android.gms.maps.model.zze r4 = com.google.android.gms.maps.model.LatLng.CREATOR
                java.lang.Object r4 = r4.createFromParcel(r5)
                r0 = r4
                com.google.android.gms.maps.model.LatLng r0 = (com.google.android.gms.maps.model.LatLng) r0
            L_0x00f3:
                r3.setPosition(r0)
                r6.writeNoException()
                return r1
            L_0x00fa:
                java.lang.String r4 = "com.google.android.gms.maps.internal.IStreetViewPanoramaDelegate"
                r5.enforceInterface(r4)
                java.lang.String r4 = r5.readString()
                r3.setPositionWithID(r4)
                r6.writeNoException()
                return r1
            L_0x010a:
                java.lang.String r4 = "com.google.android.gms.maps.internal.IStreetViewPanoramaDelegate"
                r5.enforceInterface(r4)
                com.google.android.gms.maps.model.StreetViewPanoramaCamera r4 = r3.getPanoramaCamera()
                r6.writeNoException()
                if (r4 == 0) goto L_0x011f
                r6.writeInt(r1)
                r4.writeToParcel(r6, r1)
                return r1
            L_0x011f:
                r6.writeInt(r2)
                return r1
            L_0x0123:
                java.lang.String r4 = "com.google.android.gms.maps.internal.IStreetViewPanoramaDelegate"
                r5.enforceInterface(r4)
                int r4 = r5.readInt()
                if (r4 == 0) goto L_0x0137
                com.google.android.gms.maps.model.zzk r4 = com.google.android.gms.maps.model.StreetViewPanoramaCamera.CREATOR
                java.lang.Object r4 = r4.createFromParcel(r5)
                r0 = r4
                com.google.android.gms.maps.model.StreetViewPanoramaCamera r0 = (com.google.android.gms.maps.model.StreetViewPanoramaCamera) r0
            L_0x0137:
                long r4 = r5.readLong()
                r3.animateTo(r0, r4)
                r6.writeNoException()
                return r1
            L_0x0142:
                java.lang.String r4 = "com.google.android.gms.maps.internal.IStreetViewPanoramaDelegate"
                r5.enforceInterface(r4)
                boolean r4 = r3.isStreetNamesEnabled()
                r6.writeNoException()
                r6.writeInt(r4)
                return r1
            L_0x0152:
                java.lang.String r4 = "com.google.android.gms.maps.internal.IStreetViewPanoramaDelegate"
                r5.enforceInterface(r4)
                boolean r4 = r3.isUserNavigationEnabled()
                r6.writeNoException()
                r6.writeInt(r4)
                return r1
            L_0x0162:
                java.lang.String r4 = "com.google.android.gms.maps.internal.IStreetViewPanoramaDelegate"
                r5.enforceInterface(r4)
                boolean r4 = r3.isPanningGesturesEnabled()
                r6.writeNoException()
                r6.writeInt(r4)
                return r1
            L_0x0172:
                java.lang.String r4 = "com.google.android.gms.maps.internal.IStreetViewPanoramaDelegate"
                r5.enforceInterface(r4)
                boolean r4 = r3.isZoomGesturesEnabled()
                r6.writeNoException()
                r6.writeInt(r4)
                return r1
            L_0x0182:
                java.lang.String r4 = "com.google.android.gms.maps.internal.IStreetViewPanoramaDelegate"
                r5.enforceInterface(r4)
                int r4 = r5.readInt()
                if (r4 == 0) goto L_0x018e
                r2 = 1
            L_0x018e:
                r3.enableStreetNames(r2)
                r6.writeNoException()
                return r1
            L_0x0195:
                java.lang.String r4 = "com.google.android.gms.maps.internal.IStreetViewPanoramaDelegate"
                r5.enforceInterface(r4)
                int r4 = r5.readInt()
                if (r4 == 0) goto L_0x01a1
                r2 = 1
            L_0x01a1:
                r3.enableUserNavigation(r2)
                r6.writeNoException()
                return r1
            L_0x01a8:
                java.lang.String r4 = "com.google.android.gms.maps.internal.IStreetViewPanoramaDelegate"
                r5.enforceInterface(r4)
                int r4 = r5.readInt()
                if (r4 == 0) goto L_0x01b4
                r2 = 1
            L_0x01b4:
                r3.enablePanning(r2)
                r6.writeNoException()
                return r1
            L_0x01bb:
                java.lang.String r4 = "com.google.android.gms.maps.internal.IStreetViewPanoramaDelegate"
                r5.enforceInterface(r4)
                int r4 = r5.readInt()
                if (r4 == 0) goto L_0x01c7
                r2 = 1
            L_0x01c7:
                r3.enableZoom(r2)
                r6.writeNoException()
                return r1
            L_0x01ce:
                java.lang.String r4 = "com.google.android.gms.maps.internal.IStreetViewPanoramaDelegate"
                r6.writeString(r4)
                return r1
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.maps.internal.IStreetViewPanoramaDelegate.zza.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }
    }

    void animateTo(StreetViewPanoramaCamera streetViewPanoramaCamera, long j);

    void enablePanning(boolean z);

    void enableStreetNames(boolean z);

    void enableUserNavigation(boolean z);

    void enableZoom(boolean z);

    StreetViewPanoramaCamera getPanoramaCamera();

    StreetViewPanoramaLocation getStreetViewPanoramaLocation();

    boolean isPanningGesturesEnabled();

    boolean isStreetNamesEnabled();

    boolean isUserNavigationEnabled();

    boolean isZoomGesturesEnabled();

    zzd orientationToPoint(StreetViewPanoramaOrientation streetViewPanoramaOrientation);

    StreetViewPanoramaOrientation pointToOrientation(zzd zzd);

    void setOnStreetViewPanoramaCameraChangeListener(zzab zzab);

    void setOnStreetViewPanoramaChangeListener(zzac zzac);

    void setOnStreetViewPanoramaClickListener(zzad zzad);

    void setOnStreetViewPanoramaLongClickListener(zzae zzae);

    void setPosition(LatLng latLng);

    void setPositionWithID(String str);

    void setPositionWithRadius(LatLng latLng, int i);
}
