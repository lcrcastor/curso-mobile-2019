package com.google.android.gms.maps.model.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import com.google.android.gms.dynamic.zzd;
import com.google.android.gms.maps.model.LatLng;

public interface zzf extends IInterface {

    public static abstract class zza extends Binder implements zzf {

        /* renamed from: com.google.android.gms.maps.model.internal.zzf$zza$zza reason: collision with other inner class name */
        static class C0089zza implements zzf {
            private IBinder a;

            C0089zza(IBinder iBinder) {
                this.a = iBinder;
            }

            public IBinder asBinder() {
                return this.a;
            }

            public float getAlpha() {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IMarkerDelegate");
                    this.a.transact(26, obtain, obtain2, 0);
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
                    obtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IMarkerDelegate");
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
                    obtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IMarkerDelegate");
                    this.a.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0 ? (LatLng) LatLng.CREATOR.createFromParcel(obtain2) : null;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public float getRotation() {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IMarkerDelegate");
                    this.a.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readFloat();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public String getSnippet() {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IMarkerDelegate");
                    this.a.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public String getTitle() {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IMarkerDelegate");
                    this.a.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public float getZIndex() {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IMarkerDelegate");
                    this.a.transact(28, obtain, obtain2, 0);
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
                    obtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IMarkerDelegate");
                    this.a.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void hideInfoWindow() {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IMarkerDelegate");
                    this.a.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public boolean isDraggable() {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IMarkerDelegate");
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

            public boolean isFlat() {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IMarkerDelegate");
                    boolean z = false;
                    this.a.transact(21, obtain, obtain2, 0);
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

            public boolean isInfoWindowShown() {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IMarkerDelegate");
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

            public boolean isVisible() {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IMarkerDelegate");
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

            public void remove() {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IMarkerDelegate");
                    this.a.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void setAlpha(float f) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IMarkerDelegate");
                    obtain.writeFloat(f);
                    this.a.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void setAnchor(float f, float f2) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IMarkerDelegate");
                    obtain.writeFloat(f);
                    obtain.writeFloat(f2);
                    this.a.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void setDraggable(boolean z) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IMarkerDelegate");
                    obtain.writeInt(z ? 1 : 0);
                    this.a.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void setFlat(boolean z) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IMarkerDelegate");
                    obtain.writeInt(z ? 1 : 0);
                    this.a.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void setInfoWindowAnchor(float f, float f2) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IMarkerDelegate");
                    obtain.writeFloat(f);
                    obtain.writeFloat(f2);
                    this.a.transact(24, obtain, obtain2, 0);
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
                    obtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IMarkerDelegate");
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

            public void setRotation(float f) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IMarkerDelegate");
                    obtain.writeFloat(f);
                    this.a.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void setSnippet(String str) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IMarkerDelegate");
                    obtain.writeString(str);
                    this.a.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void setTitle(String str) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IMarkerDelegate");
                    obtain.writeString(str);
                    this.a.transact(5, obtain, obtain2, 0);
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
                    obtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IMarkerDelegate");
                    obtain.writeInt(z ? 1 : 0);
                    this.a.transact(14, obtain, obtain2, 0);
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
                    obtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IMarkerDelegate");
                    obtain.writeFloat(f);
                    this.a.transact(27, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void showInfoWindow() {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IMarkerDelegate");
                    this.a.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void zzal(zzd zzd) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IMarkerDelegate");
                    obtain.writeStrongBinder(zzd != null ? zzd.asBinder() : null);
                    this.a.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void zzam(zzd zzd) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IMarkerDelegate");
                    obtain.writeStrongBinder(zzd != null ? zzd.asBinder() : null);
                    this.a.transact(29, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public zzd zzbsn() {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IMarkerDelegate");
                    this.a.transact(30, obtain, obtain2, 0);
                    obtain2.readException();
                    return com.google.android.gms.dynamic.zzd.zza.zzfe(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public boolean zzj(zzf zzf) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IMarkerDelegate");
                    obtain.writeStrongBinder(zzf != null ? zzf.asBinder() : null);
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
        }

        public static zzf zzjg(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.maps.model.internal.IMarkerDelegate");
            return (queryLocalInterface == null || !(queryLocalInterface instanceof zzf)) ? new C0089zza(iBinder) : (zzf) queryLocalInterface;
        }

        /* JADX WARNING: type inference failed for: r0v1 */
        /* JADX WARNING: type inference failed for: r0v2, types: [com.google.android.gms.maps.model.LatLng] */
        /* JADX WARNING: type inference failed for: r0v4, types: [com.google.android.gms.maps.model.LatLng] */
        /* JADX WARNING: type inference failed for: r0v5, types: [android.os.IBinder] */
        /* JADX WARNING: type inference failed for: r0v6, types: [android.os.IBinder] */
        /* JADX WARNING: type inference failed for: r0v7 */
        /* JADX WARNING: type inference failed for: r0v8 */
        /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r0v1
          assigns: [?[int, float, boolean, short, byte, char, OBJECT, ARRAY], android.os.IBinder, com.google.android.gms.maps.model.LatLng]
          uses: [com.google.android.gms.maps.model.LatLng, android.os.IBinder]
          mth insns count: 211
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
                if (r4 == r0) goto L_0x021f
                r0 = 0
                r2 = 0
                switch(r4) {
                    case 1: goto L_0x0213;
                    case 2: goto L_0x0203;
                    case 3: goto L_0x01e8;
                    case 4: goto L_0x01cf;
                    case 5: goto L_0x01bf;
                    case 6: goto L_0x01af;
                    case 7: goto L_0x019f;
                    case 8: goto L_0x018f;
                    case 9: goto L_0x017c;
                    case 10: goto L_0x016c;
                    case 11: goto L_0x0160;
                    case 12: goto L_0x0154;
                    case 13: goto L_0x0144;
                    case 14: goto L_0x0131;
                    case 15: goto L_0x0121;
                    case 16: goto L_0x0109;
                    case 17: goto L_0x00f9;
                    case 18: goto L_0x00e5;
                    case 19: goto L_0x00d1;
                    case 20: goto L_0x00be;
                    case 21: goto L_0x00ae;
                    case 22: goto L_0x009e;
                    case 23: goto L_0x008e;
                    case 24: goto L_0x007a;
                    case 25: goto L_0x006a;
                    case 26: goto L_0x005a;
                    case 27: goto L_0x004a;
                    case 28: goto L_0x003a;
                    case 29: goto L_0x0026;
                    case 30: goto L_0x0010;
                    default: goto L_0x000b;
                }
            L_0x000b:
                boolean r4 = super.onTransact(r4, r5, r6, r7)
                return r4
            L_0x0010:
                java.lang.String r4 = "com.google.android.gms.maps.model.internal.IMarkerDelegate"
                r5.enforceInterface(r4)
                com.google.android.gms.dynamic.zzd r4 = r3.zzbsn()
                r6.writeNoException()
                if (r4 == 0) goto L_0x0022
                android.os.IBinder r0 = r4.asBinder()
            L_0x0022:
                r6.writeStrongBinder(r0)
                return r1
            L_0x0026:
                java.lang.String r4 = "com.google.android.gms.maps.model.internal.IMarkerDelegate"
                r5.enforceInterface(r4)
                android.os.IBinder r4 = r5.readStrongBinder()
                com.google.android.gms.dynamic.zzd r4 = com.google.android.gms.dynamic.zzd.zza.zzfe(r4)
                r3.zzam(r4)
                r6.writeNoException()
                return r1
            L_0x003a:
                java.lang.String r4 = "com.google.android.gms.maps.model.internal.IMarkerDelegate"
                r5.enforceInterface(r4)
                float r4 = r3.getZIndex()
                r6.writeNoException()
                r6.writeFloat(r4)
                return r1
            L_0x004a:
                java.lang.String r4 = "com.google.android.gms.maps.model.internal.IMarkerDelegate"
                r5.enforceInterface(r4)
                float r4 = r5.readFloat()
                r3.setZIndex(r4)
                r6.writeNoException()
                return r1
            L_0x005a:
                java.lang.String r4 = "com.google.android.gms.maps.model.internal.IMarkerDelegate"
                r5.enforceInterface(r4)
                float r4 = r3.getAlpha()
                r6.writeNoException()
                r6.writeFloat(r4)
                return r1
            L_0x006a:
                java.lang.String r4 = "com.google.android.gms.maps.model.internal.IMarkerDelegate"
                r5.enforceInterface(r4)
                float r4 = r5.readFloat()
                r3.setAlpha(r4)
                r6.writeNoException()
                return r1
            L_0x007a:
                java.lang.String r4 = "com.google.android.gms.maps.model.internal.IMarkerDelegate"
                r5.enforceInterface(r4)
                float r4 = r5.readFloat()
                float r5 = r5.readFloat()
                r3.setInfoWindowAnchor(r4, r5)
                r6.writeNoException()
                return r1
            L_0x008e:
                java.lang.String r4 = "com.google.android.gms.maps.model.internal.IMarkerDelegate"
                r5.enforceInterface(r4)
                float r4 = r3.getRotation()
                r6.writeNoException()
                r6.writeFloat(r4)
                return r1
            L_0x009e:
                java.lang.String r4 = "com.google.android.gms.maps.model.internal.IMarkerDelegate"
                r5.enforceInterface(r4)
                float r4 = r5.readFloat()
                r3.setRotation(r4)
                r6.writeNoException()
                return r1
            L_0x00ae:
                java.lang.String r4 = "com.google.android.gms.maps.model.internal.IMarkerDelegate"
                r5.enforceInterface(r4)
                boolean r4 = r3.isFlat()
                r6.writeNoException()
                r6.writeInt(r4)
                return r1
            L_0x00be:
                java.lang.String r4 = "com.google.android.gms.maps.model.internal.IMarkerDelegate"
                r5.enforceInterface(r4)
                int r4 = r5.readInt()
                if (r4 == 0) goto L_0x00ca
                r2 = 1
            L_0x00ca:
                r3.setFlat(r2)
                r6.writeNoException()
                return r1
            L_0x00d1:
                java.lang.String r4 = "com.google.android.gms.maps.model.internal.IMarkerDelegate"
                r5.enforceInterface(r4)
                float r4 = r5.readFloat()
                float r5 = r5.readFloat()
                r3.setAnchor(r4, r5)
                r6.writeNoException()
                return r1
            L_0x00e5:
                java.lang.String r4 = "com.google.android.gms.maps.model.internal.IMarkerDelegate"
                r5.enforceInterface(r4)
                android.os.IBinder r4 = r5.readStrongBinder()
                com.google.android.gms.dynamic.zzd r4 = com.google.android.gms.dynamic.zzd.zza.zzfe(r4)
                r3.zzal(r4)
                r6.writeNoException()
                return r1
            L_0x00f9:
                java.lang.String r4 = "com.google.android.gms.maps.model.internal.IMarkerDelegate"
                r5.enforceInterface(r4)
                int r4 = r3.hashCodeRemote()
                r6.writeNoException()
                r6.writeInt(r4)
                return r1
            L_0x0109:
                java.lang.String r4 = "com.google.android.gms.maps.model.internal.IMarkerDelegate"
                r5.enforceInterface(r4)
                android.os.IBinder r4 = r5.readStrongBinder()
                com.google.android.gms.maps.model.internal.zzf r4 = zzjg(r4)
                boolean r4 = r3.zzj(r4)
                r6.writeNoException()
                r6.writeInt(r4)
                return r1
            L_0x0121:
                java.lang.String r4 = "com.google.android.gms.maps.model.internal.IMarkerDelegate"
                r5.enforceInterface(r4)
                boolean r4 = r3.isVisible()
                r6.writeNoException()
                r6.writeInt(r4)
                return r1
            L_0x0131:
                java.lang.String r4 = "com.google.android.gms.maps.model.internal.IMarkerDelegate"
                r5.enforceInterface(r4)
                int r4 = r5.readInt()
                if (r4 == 0) goto L_0x013d
                r2 = 1
            L_0x013d:
                r3.setVisible(r2)
                r6.writeNoException()
                return r1
            L_0x0144:
                java.lang.String r4 = "com.google.android.gms.maps.model.internal.IMarkerDelegate"
                r5.enforceInterface(r4)
                boolean r4 = r3.isInfoWindowShown()
                r6.writeNoException()
                r6.writeInt(r4)
                return r1
            L_0x0154:
                java.lang.String r4 = "com.google.android.gms.maps.model.internal.IMarkerDelegate"
                r5.enforceInterface(r4)
                r3.hideInfoWindow()
                r6.writeNoException()
                return r1
            L_0x0160:
                java.lang.String r4 = "com.google.android.gms.maps.model.internal.IMarkerDelegate"
                r5.enforceInterface(r4)
                r3.showInfoWindow()
                r6.writeNoException()
                return r1
            L_0x016c:
                java.lang.String r4 = "com.google.android.gms.maps.model.internal.IMarkerDelegate"
                r5.enforceInterface(r4)
                boolean r4 = r3.isDraggable()
                r6.writeNoException()
                r6.writeInt(r4)
                return r1
            L_0x017c:
                java.lang.String r4 = "com.google.android.gms.maps.model.internal.IMarkerDelegate"
                r5.enforceInterface(r4)
                int r4 = r5.readInt()
                if (r4 == 0) goto L_0x0188
                r2 = 1
            L_0x0188:
                r3.setDraggable(r2)
                r6.writeNoException()
                return r1
            L_0x018f:
                java.lang.String r4 = "com.google.android.gms.maps.model.internal.IMarkerDelegate"
                r5.enforceInterface(r4)
                java.lang.String r4 = r3.getSnippet()
                r6.writeNoException()
                r6.writeString(r4)
                return r1
            L_0x019f:
                java.lang.String r4 = "com.google.android.gms.maps.model.internal.IMarkerDelegate"
                r5.enforceInterface(r4)
                java.lang.String r4 = r5.readString()
                r3.setSnippet(r4)
                r6.writeNoException()
                return r1
            L_0x01af:
                java.lang.String r4 = "com.google.android.gms.maps.model.internal.IMarkerDelegate"
                r5.enforceInterface(r4)
                java.lang.String r4 = r3.getTitle()
                r6.writeNoException()
                r6.writeString(r4)
                return r1
            L_0x01bf:
                java.lang.String r4 = "com.google.android.gms.maps.model.internal.IMarkerDelegate"
                r5.enforceInterface(r4)
                java.lang.String r4 = r5.readString()
                r3.setTitle(r4)
                r6.writeNoException()
                return r1
            L_0x01cf:
                java.lang.String r4 = "com.google.android.gms.maps.model.internal.IMarkerDelegate"
                r5.enforceInterface(r4)
                com.google.android.gms.maps.model.LatLng r4 = r3.getPosition()
                r6.writeNoException()
                if (r4 == 0) goto L_0x01e4
                r6.writeInt(r1)
                r4.writeToParcel(r6, r1)
                return r1
            L_0x01e4:
                r6.writeInt(r2)
                return r1
            L_0x01e8:
                java.lang.String r4 = "com.google.android.gms.maps.model.internal.IMarkerDelegate"
                r5.enforceInterface(r4)
                int r4 = r5.readInt()
                if (r4 == 0) goto L_0x01fc
                com.google.android.gms.maps.model.zze r4 = com.google.android.gms.maps.model.LatLng.CREATOR
                java.lang.Object r4 = r4.createFromParcel(r5)
                r0 = r4
                com.google.android.gms.maps.model.LatLng r0 = (com.google.android.gms.maps.model.LatLng) r0
            L_0x01fc:
                r3.setPosition(r0)
                r6.writeNoException()
                return r1
            L_0x0203:
                java.lang.String r4 = "com.google.android.gms.maps.model.internal.IMarkerDelegate"
                r5.enforceInterface(r4)
                java.lang.String r4 = r3.getId()
                r6.writeNoException()
                r6.writeString(r4)
                return r1
            L_0x0213:
                java.lang.String r4 = "com.google.android.gms.maps.model.internal.IMarkerDelegate"
                r5.enforceInterface(r4)
                r3.remove()
                r6.writeNoException()
                return r1
            L_0x021f:
                java.lang.String r4 = "com.google.android.gms.maps.model.internal.IMarkerDelegate"
                r6.writeString(r4)
                return r1
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.maps.model.internal.zzf.zza.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }
    }

    float getAlpha();

    String getId();

    LatLng getPosition();

    float getRotation();

    String getSnippet();

    String getTitle();

    float getZIndex();

    int hashCodeRemote();

    void hideInfoWindow();

    boolean isDraggable();

    boolean isFlat();

    boolean isInfoWindowShown();

    boolean isVisible();

    void remove();

    void setAlpha(float f);

    void setAnchor(float f, float f2);

    void setDraggable(boolean z);

    void setFlat(boolean z);

    void setInfoWindowAnchor(float f, float f2);

    void setPosition(LatLng latLng);

    void setRotation(float f);

    void setSnippet(String str);

    void setTitle(String str);

    void setVisible(boolean z);

    void setZIndex(float f);

    void showInfoWindow();

    void zzal(zzd zzd);

    void zzam(zzd zzd);

    zzd zzbsn();

    boolean zzj(zzf zzf);
}
