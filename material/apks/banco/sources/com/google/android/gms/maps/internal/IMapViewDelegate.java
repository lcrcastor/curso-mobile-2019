package com.google.android.gms.maps.internal;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import com.google.android.gms.dynamic.zzd;

public interface IMapViewDelegate extends IInterface {

    public static abstract class zza extends Binder implements IMapViewDelegate {

        /* renamed from: com.google.android.gms.maps.internal.IMapViewDelegate$zza$zza reason: collision with other inner class name */
        static class C0044zza implements IMapViewDelegate {
            private IBinder a;

            C0044zza(IBinder iBinder) {
                this.a = iBinder;
            }

            public IBinder asBinder() {
                return this.a;
            }

            public IGoogleMapDelegate getMap() {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IMapViewDelegate");
                    this.a.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return com.google.android.gms.maps.internal.IGoogleMapDelegate.zza.zzho(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void getMapAsync(zzt zzt) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IMapViewDelegate");
                    obtain.writeStrongBinder(zzt != null ? zzt.asBinder() : null);
                    this.a.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public zzd getView() {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IMapViewDelegate");
                    this.a.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return com.google.android.gms.dynamic.zzd.zza.zzfe(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void onCreate(Bundle bundle) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IMapViewDelegate");
                    if (bundle != null) {
                        obtain.writeInt(1);
                        bundle.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.a.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void onDestroy() {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IMapViewDelegate");
                    this.a.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void onEnterAmbient(Bundle bundle) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IMapViewDelegate");
                    if (bundle != null) {
                        obtain.writeInt(1);
                        bundle.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.a.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void onExitAmbient() {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IMapViewDelegate");
                    this.a.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void onLowMemory() {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IMapViewDelegate");
                    this.a.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void onPause() {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IMapViewDelegate");
                    this.a.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void onResume() {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IMapViewDelegate");
                    this.a.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void onSaveInstanceState(Bundle bundle) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IMapViewDelegate");
                    if (bundle != null) {
                        obtain.writeInt(1);
                        bundle.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.a.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    if (obtain2.readInt() != 0) {
                        bundle.readFromParcel(obtain2);
                    }
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void onStart() {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IMapViewDelegate");
                    this.a.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void onStop() {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IMapViewDelegate");
                    this.a.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public static IMapViewDelegate zzhs(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.maps.internal.IMapViewDelegate");
            return (queryLocalInterface == null || !(queryLocalInterface instanceof IMapViewDelegate)) ? new C0044zza(iBinder) : (IMapViewDelegate) queryLocalInterface;
        }

        /* JADX WARNING: type inference failed for: r0v1 */
        /* JADX WARNING: type inference failed for: r0v2, types: [android.os.IBinder] */
        /* JADX WARNING: type inference failed for: r0v3, types: [android.os.IBinder] */
        /* JADX WARNING: type inference failed for: r0v4, types: [android.os.Bundle] */
        /* JADX WARNING: type inference failed for: r0v6, types: [android.os.Bundle] */
        /* JADX WARNING: type inference failed for: r0v7, types: [android.os.Bundle] */
        /* JADX WARNING: type inference failed for: r0v9, types: [android.os.Bundle] */
        /* JADX WARNING: type inference failed for: r0v10, types: [android.os.IBinder] */
        /* JADX WARNING: type inference failed for: r0v11, types: [android.os.IBinder] */
        /* JADX WARNING: type inference failed for: r0v12, types: [android.os.Bundle] */
        /* JADX WARNING: type inference failed for: r0v14, types: [android.os.Bundle] */
        /* JADX WARNING: type inference failed for: r0v15 */
        /* JADX WARNING: type inference failed for: r0v16 */
        /* JADX WARNING: type inference failed for: r0v17 */
        /* JADX WARNING: type inference failed for: r0v18 */
        /* JADX WARNING: type inference failed for: r0v19 */
        /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r0v1
          assigns: [?[int, float, boolean, short, byte, char, OBJECT, ARRAY], android.os.Bundle, android.os.IBinder]
          uses: [android.os.IBinder, android.os.Bundle, ?[int, boolean, OBJECT, ARRAY, byte, short, char]]
          mth insns count: 109
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
        /* JADX WARNING: Unknown variable types count: 6 */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTransact(int r3, android.os.Parcel r4, android.os.Parcel r5, int r6) {
            /*
                r2 = this;
                r0 = 1598968902(0x5f4e5446, float:1.4867585E19)
                r1 = 1
                if (r3 == r0) goto L_0x0101
                r0 = 0
                switch(r3) {
                    case 1: goto L_0x00eb;
                    case 2: goto L_0x00d0;
                    case 3: goto L_0x00c4;
                    case 4: goto L_0x00b8;
                    case 5: goto L_0x00ac;
                    case 6: goto L_0x00a0;
                    case 7: goto L_0x0078;
                    case 8: goto L_0x0062;
                    case 9: goto L_0x004e;
                    case 10: goto L_0x0033;
                    case 11: goto L_0x0027;
                    case 12: goto L_0x001b;
                    case 13: goto L_0x000f;
                    default: goto L_0x000a;
                }
            L_0x000a:
                boolean r3 = super.onTransact(r3, r4, r5, r6)
                return r3
            L_0x000f:
                java.lang.String r3 = "com.google.android.gms.maps.internal.IMapViewDelegate"
                r4.enforceInterface(r3)
                r2.onStop()
                r5.writeNoException()
                return r1
            L_0x001b:
                java.lang.String r3 = "com.google.android.gms.maps.internal.IMapViewDelegate"
                r4.enforceInterface(r3)
                r2.onStart()
                r5.writeNoException()
                return r1
            L_0x0027:
                java.lang.String r3 = "com.google.android.gms.maps.internal.IMapViewDelegate"
                r4.enforceInterface(r3)
                r2.onExitAmbient()
                r5.writeNoException()
                return r1
            L_0x0033:
                java.lang.String r3 = "com.google.android.gms.maps.internal.IMapViewDelegate"
                r4.enforceInterface(r3)
                int r3 = r4.readInt()
                if (r3 == 0) goto L_0x0047
                android.os.Parcelable$Creator r3 = android.os.Bundle.CREATOR
                java.lang.Object r3 = r3.createFromParcel(r4)
                r0 = r3
                android.os.Bundle r0 = (android.os.Bundle) r0
            L_0x0047:
                r2.onEnterAmbient(r0)
                r5.writeNoException()
                return r1
            L_0x004e:
                java.lang.String r3 = "com.google.android.gms.maps.internal.IMapViewDelegate"
                r4.enforceInterface(r3)
                android.os.IBinder r3 = r4.readStrongBinder()
                com.google.android.gms.maps.internal.zzt r3 = com.google.android.gms.maps.internal.zzt.zza.zzii(r3)
                r2.getMapAsync(r3)
                r5.writeNoException()
                return r1
            L_0x0062:
                java.lang.String r3 = "com.google.android.gms.maps.internal.IMapViewDelegate"
                r4.enforceInterface(r3)
                com.google.android.gms.dynamic.zzd r3 = r2.getView()
                r5.writeNoException()
                if (r3 == 0) goto L_0x0074
                android.os.IBinder r0 = r3.asBinder()
            L_0x0074:
                r5.writeStrongBinder(r0)
                return r1
            L_0x0078:
                java.lang.String r3 = "com.google.android.gms.maps.internal.IMapViewDelegate"
                r4.enforceInterface(r3)
                int r3 = r4.readInt()
                if (r3 == 0) goto L_0x008c
                android.os.Parcelable$Creator r3 = android.os.Bundle.CREATOR
                java.lang.Object r3 = r3.createFromParcel(r4)
                r0 = r3
                android.os.Bundle r0 = (android.os.Bundle) r0
            L_0x008c:
                r2.onSaveInstanceState(r0)
                r5.writeNoException()
                if (r0 == 0) goto L_0x009b
                r5.writeInt(r1)
                r0.writeToParcel(r5, r1)
                return r1
            L_0x009b:
                r3 = 0
                r5.writeInt(r3)
                return r1
            L_0x00a0:
                java.lang.String r3 = "com.google.android.gms.maps.internal.IMapViewDelegate"
                r4.enforceInterface(r3)
                r2.onLowMemory()
                r5.writeNoException()
                return r1
            L_0x00ac:
                java.lang.String r3 = "com.google.android.gms.maps.internal.IMapViewDelegate"
                r4.enforceInterface(r3)
                r2.onDestroy()
                r5.writeNoException()
                return r1
            L_0x00b8:
                java.lang.String r3 = "com.google.android.gms.maps.internal.IMapViewDelegate"
                r4.enforceInterface(r3)
                r2.onPause()
                r5.writeNoException()
                return r1
            L_0x00c4:
                java.lang.String r3 = "com.google.android.gms.maps.internal.IMapViewDelegate"
                r4.enforceInterface(r3)
                r2.onResume()
                r5.writeNoException()
                return r1
            L_0x00d0:
                java.lang.String r3 = "com.google.android.gms.maps.internal.IMapViewDelegate"
                r4.enforceInterface(r3)
                int r3 = r4.readInt()
                if (r3 == 0) goto L_0x00e4
                android.os.Parcelable$Creator r3 = android.os.Bundle.CREATOR
                java.lang.Object r3 = r3.createFromParcel(r4)
                r0 = r3
                android.os.Bundle r0 = (android.os.Bundle) r0
            L_0x00e4:
                r2.onCreate(r0)
                r5.writeNoException()
                return r1
            L_0x00eb:
                java.lang.String r3 = "com.google.android.gms.maps.internal.IMapViewDelegate"
                r4.enforceInterface(r3)
                com.google.android.gms.maps.internal.IGoogleMapDelegate r3 = r2.getMap()
                r5.writeNoException()
                if (r3 == 0) goto L_0x00fd
                android.os.IBinder r0 = r3.asBinder()
            L_0x00fd:
                r5.writeStrongBinder(r0)
                return r1
            L_0x0101:
                java.lang.String r3 = "com.google.android.gms.maps.internal.IMapViewDelegate"
                r5.writeString(r3)
                return r1
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.maps.internal.IMapViewDelegate.zza.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }
    }

    IGoogleMapDelegate getMap();

    void getMapAsync(zzt zzt);

    zzd getView();

    void onCreate(Bundle bundle);

    void onDestroy();

    void onEnterAmbient(Bundle bundle);

    void onExitAmbient();

    void onLowMemory();

    void onPause();

    void onResume();

    void onSaveInstanceState(Bundle bundle);

    void onStart();

    void onStop();
}
