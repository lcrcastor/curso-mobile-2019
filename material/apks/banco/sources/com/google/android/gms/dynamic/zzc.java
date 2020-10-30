package com.google.android.gms.dynamic;

import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;

public interface zzc extends IInterface {

    public static abstract class zza extends Binder implements zzc {

        /* renamed from: com.google.android.gms.dynamic.zzc$zza$zza reason: collision with other inner class name */
        static class C0016zza implements zzc {
            private IBinder a;

            C0016zza(IBinder iBinder) {
                this.a = iBinder;
            }

            public IBinder asBinder() {
                return this.a;
            }

            public Bundle getArguments() {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.dynamic.IFragmentWrapper");
                    this.a.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(obtain2) : null;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public int getId() {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.dynamic.IFragmentWrapper");
                    this.a.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public boolean getRetainInstance() {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.dynamic.IFragmentWrapper");
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

            public String getTag() {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.dynamic.IFragmentWrapper");
                    this.a.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public int getTargetRequestCode() {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.dynamic.IFragmentWrapper");
                    this.a.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public boolean getUserVisibleHint() {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.dynamic.IFragmentWrapper");
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

            public zzd getView() {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.dynamic.IFragmentWrapper");
                    this.a.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return com.google.android.gms.dynamic.zzd.zza.zzfe(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public boolean isAdded() {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.dynamic.IFragmentWrapper");
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

            public boolean isDetached() {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.dynamic.IFragmentWrapper");
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

            public boolean isHidden() {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.dynamic.IFragmentWrapper");
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

            public boolean isInLayout() {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.dynamic.IFragmentWrapper");
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

            public boolean isRemoving() {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.dynamic.IFragmentWrapper");
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

            public boolean isResumed() {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.dynamic.IFragmentWrapper");
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

            public boolean isVisible() {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.dynamic.IFragmentWrapper");
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

            public void setHasOptionsMenu(boolean z) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.dynamic.IFragmentWrapper");
                    obtain.writeInt(z ? 1 : 0);
                    this.a.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void setMenuVisibility(boolean z) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.dynamic.IFragmentWrapper");
                    obtain.writeInt(z ? 1 : 0);
                    this.a.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void setRetainInstance(boolean z) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.dynamic.IFragmentWrapper");
                    obtain.writeInt(z ? 1 : 0);
                    this.a.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void setUserVisibleHint(boolean z) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.dynamic.IFragmentWrapper");
                    obtain.writeInt(z ? 1 : 0);
                    this.a.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void startActivity(Intent intent) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.dynamic.IFragmentWrapper");
                    if (intent != null) {
                        obtain.writeInt(1);
                        intent.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.a.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void startActivityForResult(Intent intent, int i) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.dynamic.IFragmentWrapper");
                    if (intent != null) {
                        obtain.writeInt(1);
                        intent.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeInt(i);
                    this.a.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void zzac(zzd zzd) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.dynamic.IFragmentWrapper");
                    obtain.writeStrongBinder(zzd != null ? zzd.asBinder() : null);
                    this.a.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void zzad(zzd zzd) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.dynamic.IFragmentWrapper");
                    obtain.writeStrongBinder(zzd != null ? zzd.asBinder() : null);
                    this.a.transact(27, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public zzd zzbdu() {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.dynamic.IFragmentWrapper");
                    this.a.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return com.google.android.gms.dynamic.zzd.zza.zzfe(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public zzc zzbdv() {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.dynamic.IFragmentWrapper");
                    this.a.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return zza.zzfd(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public zzd zzbdw() {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.dynamic.IFragmentWrapper");
                    this.a.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return com.google.android.gms.dynamic.zzd.zza.zzfe(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public zzc zzbdx() {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.dynamic.IFragmentWrapper");
                    this.a.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return zza.zzfd(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public zza() {
            attachInterface(this, "com.google.android.gms.dynamic.IFragmentWrapper");
        }

        public static zzc zzfd(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.dynamic.IFragmentWrapper");
            return (queryLocalInterface == null || !(queryLocalInterface instanceof zzc)) ? new C0016zza(iBinder) : (zzc) queryLocalInterface;
        }

        public IBinder asBinder() {
            return this;
        }

        /* JADX WARNING: type inference failed for: r2v0 */
        /* JADX WARNING: type inference failed for: r2v1, types: [android.os.IBinder] */
        /* JADX WARNING: type inference failed for: r2v2, types: [android.os.IBinder] */
        /* JADX WARNING: type inference failed for: r2v3, types: [android.os.IBinder] */
        /* JADX WARNING: type inference failed for: r2v4, types: [android.os.IBinder] */
        /* JADX WARNING: type inference failed for: r2v5, types: [android.os.IBinder] */
        /* JADX WARNING: type inference failed for: r2v6, types: [android.os.IBinder] */
        /* JADX WARNING: type inference failed for: r2v7, types: [android.os.IBinder] */
        /* JADX WARNING: type inference failed for: r2v8, types: [android.os.IBinder] */
        /* JADX WARNING: type inference failed for: r2v9, types: [android.os.IBinder] */
        /* JADX WARNING: type inference failed for: r2v10, types: [android.os.IBinder] */
        /* JADX WARNING: type inference failed for: r2v11, types: [android.content.Intent] */
        /* JADX WARNING: type inference failed for: r2v13, types: [android.content.Intent] */
        /* JADX WARNING: type inference failed for: r2v14, types: [android.content.Intent] */
        /* JADX WARNING: type inference failed for: r2v16, types: [android.content.Intent] */
        /* JADX WARNING: type inference failed for: r2v17 */
        /* JADX WARNING: type inference failed for: r2v18 */
        /* JADX WARNING: type inference failed for: r2v19 */
        /* JADX WARNING: type inference failed for: r2v20 */
        /* JADX WARNING: type inference failed for: r2v21 */
        /* JADX WARNING: type inference failed for: r2v22 */
        /* JADX WARNING: type inference failed for: r2v23 */
        /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r2v0
          assigns: [?[int, float, boolean, short, byte, char, OBJECT, ARRAY], android.os.IBinder, android.content.Intent]
          uses: [android.os.IBinder, android.content.Intent]
          mth insns count: 207
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
        /* JADX WARNING: Unknown variable types count: 8 */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTransact(int r4, android.os.Parcel r5, android.os.Parcel r6, int r7) {
            /*
                r3 = this;
                r0 = 1598968902(0x5f4e5446, float:1.4867585E19)
                r1 = 1
                if (r4 == r0) goto L_0x0205
                r0 = 0
                r2 = 0
                switch(r4) {
                    case 2: goto L_0x01ef;
                    case 3: goto L_0x01d6;
                    case 4: goto L_0x01c6;
                    case 5: goto L_0x01b0;
                    case 6: goto L_0x019a;
                    case 7: goto L_0x018a;
                    case 8: goto L_0x017a;
                    case 9: goto L_0x0164;
                    case 10: goto L_0x0154;
                    case 11: goto L_0x0144;
                    case 12: goto L_0x012e;
                    case 13: goto L_0x011e;
                    case 14: goto L_0x010e;
                    case 15: goto L_0x00fe;
                    case 16: goto L_0x00ee;
                    case 17: goto L_0x00de;
                    case 18: goto L_0x00ce;
                    case 19: goto L_0x00be;
                    case 20: goto L_0x00aa;
                    case 21: goto L_0x0097;
                    case 22: goto L_0x0084;
                    case 23: goto L_0x0071;
                    case 24: goto L_0x005e;
                    case 25: goto L_0x0043;
                    case 26: goto L_0x0024;
                    case 27: goto L_0x0010;
                    default: goto L_0x000b;
                }
            L_0x000b:
                boolean r4 = super.onTransact(r4, r5, r6, r7)
                return r4
            L_0x0010:
                java.lang.String r4 = "com.google.android.gms.dynamic.IFragmentWrapper"
                r5.enforceInterface(r4)
                android.os.IBinder r4 = r5.readStrongBinder()
                com.google.android.gms.dynamic.zzd r4 = com.google.android.gms.dynamic.zzd.zza.zzfe(r4)
                r3.zzad(r4)
                r6.writeNoException()
                return r1
            L_0x0024:
                java.lang.String r4 = "com.google.android.gms.dynamic.IFragmentWrapper"
                r5.enforceInterface(r4)
                int r4 = r5.readInt()
                if (r4 == 0) goto L_0x0038
                android.os.Parcelable$Creator r4 = android.content.Intent.CREATOR
                java.lang.Object r4 = r4.createFromParcel(r5)
                r2 = r4
                android.content.Intent r2 = (android.content.Intent) r2
            L_0x0038:
                int r4 = r5.readInt()
                r3.startActivityForResult(r2, r4)
                r6.writeNoException()
                return r1
            L_0x0043:
                java.lang.String r4 = "com.google.android.gms.dynamic.IFragmentWrapper"
                r5.enforceInterface(r4)
                int r4 = r5.readInt()
                if (r4 == 0) goto L_0x0057
                android.os.Parcelable$Creator r4 = android.content.Intent.CREATOR
                java.lang.Object r4 = r4.createFromParcel(r5)
                r2 = r4
                android.content.Intent r2 = (android.content.Intent) r2
            L_0x0057:
                r3.startActivity(r2)
                r6.writeNoException()
                return r1
            L_0x005e:
                java.lang.String r4 = "com.google.android.gms.dynamic.IFragmentWrapper"
                r5.enforceInterface(r4)
                int r4 = r5.readInt()
                if (r4 == 0) goto L_0x006a
                r0 = 1
            L_0x006a:
                r3.setUserVisibleHint(r0)
                r6.writeNoException()
                return r1
            L_0x0071:
                java.lang.String r4 = "com.google.android.gms.dynamic.IFragmentWrapper"
                r5.enforceInterface(r4)
                int r4 = r5.readInt()
                if (r4 == 0) goto L_0x007d
                r0 = 1
            L_0x007d:
                r3.setRetainInstance(r0)
                r6.writeNoException()
                return r1
            L_0x0084:
                java.lang.String r4 = "com.google.android.gms.dynamic.IFragmentWrapper"
                r5.enforceInterface(r4)
                int r4 = r5.readInt()
                if (r4 == 0) goto L_0x0090
                r0 = 1
            L_0x0090:
                r3.setMenuVisibility(r0)
                r6.writeNoException()
                return r1
            L_0x0097:
                java.lang.String r4 = "com.google.android.gms.dynamic.IFragmentWrapper"
                r5.enforceInterface(r4)
                int r4 = r5.readInt()
                if (r4 == 0) goto L_0x00a3
                r0 = 1
            L_0x00a3:
                r3.setHasOptionsMenu(r0)
                r6.writeNoException()
                return r1
            L_0x00aa:
                java.lang.String r4 = "com.google.android.gms.dynamic.IFragmentWrapper"
                r5.enforceInterface(r4)
                android.os.IBinder r4 = r5.readStrongBinder()
                com.google.android.gms.dynamic.zzd r4 = com.google.android.gms.dynamic.zzd.zza.zzfe(r4)
                r3.zzac(r4)
                r6.writeNoException()
                return r1
            L_0x00be:
                java.lang.String r4 = "com.google.android.gms.dynamic.IFragmentWrapper"
                r5.enforceInterface(r4)
                boolean r4 = r3.isVisible()
                r6.writeNoException()
                r6.writeInt(r4)
                return r1
            L_0x00ce:
                java.lang.String r4 = "com.google.android.gms.dynamic.IFragmentWrapper"
                r5.enforceInterface(r4)
                boolean r4 = r3.isResumed()
                r6.writeNoException()
                r6.writeInt(r4)
                return r1
            L_0x00de:
                java.lang.String r4 = "com.google.android.gms.dynamic.IFragmentWrapper"
                r5.enforceInterface(r4)
                boolean r4 = r3.isRemoving()
                r6.writeNoException()
                r6.writeInt(r4)
                return r1
            L_0x00ee:
                java.lang.String r4 = "com.google.android.gms.dynamic.IFragmentWrapper"
                r5.enforceInterface(r4)
                boolean r4 = r3.isInLayout()
                r6.writeNoException()
                r6.writeInt(r4)
                return r1
            L_0x00fe:
                java.lang.String r4 = "com.google.android.gms.dynamic.IFragmentWrapper"
                r5.enforceInterface(r4)
                boolean r4 = r3.isHidden()
                r6.writeNoException()
                r6.writeInt(r4)
                return r1
            L_0x010e:
                java.lang.String r4 = "com.google.android.gms.dynamic.IFragmentWrapper"
                r5.enforceInterface(r4)
                boolean r4 = r3.isDetached()
                r6.writeNoException()
                r6.writeInt(r4)
                return r1
            L_0x011e:
                java.lang.String r4 = "com.google.android.gms.dynamic.IFragmentWrapper"
                r5.enforceInterface(r4)
                boolean r4 = r3.isAdded()
                r6.writeNoException()
                r6.writeInt(r4)
                return r1
            L_0x012e:
                java.lang.String r4 = "com.google.android.gms.dynamic.IFragmentWrapper"
                r5.enforceInterface(r4)
                com.google.android.gms.dynamic.zzd r4 = r3.getView()
                r6.writeNoException()
                if (r4 == 0) goto L_0x0140
                android.os.IBinder r2 = r4.asBinder()
            L_0x0140:
                r6.writeStrongBinder(r2)
                return r1
            L_0x0144:
                java.lang.String r4 = "com.google.android.gms.dynamic.IFragmentWrapper"
                r5.enforceInterface(r4)
                boolean r4 = r3.getUserVisibleHint()
                r6.writeNoException()
                r6.writeInt(r4)
                return r1
            L_0x0154:
                java.lang.String r4 = "com.google.android.gms.dynamic.IFragmentWrapper"
                r5.enforceInterface(r4)
                int r4 = r3.getTargetRequestCode()
                r6.writeNoException()
                r6.writeInt(r4)
                return r1
            L_0x0164:
                java.lang.String r4 = "com.google.android.gms.dynamic.IFragmentWrapper"
                r5.enforceInterface(r4)
                com.google.android.gms.dynamic.zzc r4 = r3.zzbdx()
                r6.writeNoException()
                if (r4 == 0) goto L_0x0176
                android.os.IBinder r2 = r4.asBinder()
            L_0x0176:
                r6.writeStrongBinder(r2)
                return r1
            L_0x017a:
                java.lang.String r4 = "com.google.android.gms.dynamic.IFragmentWrapper"
                r5.enforceInterface(r4)
                java.lang.String r4 = r3.getTag()
                r6.writeNoException()
                r6.writeString(r4)
                return r1
            L_0x018a:
                java.lang.String r4 = "com.google.android.gms.dynamic.IFragmentWrapper"
                r5.enforceInterface(r4)
                boolean r4 = r3.getRetainInstance()
                r6.writeNoException()
                r6.writeInt(r4)
                return r1
            L_0x019a:
                java.lang.String r4 = "com.google.android.gms.dynamic.IFragmentWrapper"
                r5.enforceInterface(r4)
                com.google.android.gms.dynamic.zzd r4 = r3.zzbdw()
                r6.writeNoException()
                if (r4 == 0) goto L_0x01ac
                android.os.IBinder r2 = r4.asBinder()
            L_0x01ac:
                r6.writeStrongBinder(r2)
                return r1
            L_0x01b0:
                java.lang.String r4 = "com.google.android.gms.dynamic.IFragmentWrapper"
                r5.enforceInterface(r4)
                com.google.android.gms.dynamic.zzc r4 = r3.zzbdv()
                r6.writeNoException()
                if (r4 == 0) goto L_0x01c2
                android.os.IBinder r2 = r4.asBinder()
            L_0x01c2:
                r6.writeStrongBinder(r2)
                return r1
            L_0x01c6:
                java.lang.String r4 = "com.google.android.gms.dynamic.IFragmentWrapper"
                r5.enforceInterface(r4)
                int r4 = r3.getId()
                r6.writeNoException()
                r6.writeInt(r4)
                return r1
            L_0x01d6:
                java.lang.String r4 = "com.google.android.gms.dynamic.IFragmentWrapper"
                r5.enforceInterface(r4)
                android.os.Bundle r4 = r3.getArguments()
                r6.writeNoException()
                if (r4 == 0) goto L_0x01eb
                r6.writeInt(r1)
                r4.writeToParcel(r6, r1)
                return r1
            L_0x01eb:
                r6.writeInt(r0)
                return r1
            L_0x01ef:
                java.lang.String r4 = "com.google.android.gms.dynamic.IFragmentWrapper"
                r5.enforceInterface(r4)
                com.google.android.gms.dynamic.zzd r4 = r3.zzbdu()
                r6.writeNoException()
                if (r4 == 0) goto L_0x0201
                android.os.IBinder r2 = r4.asBinder()
            L_0x0201:
                r6.writeStrongBinder(r2)
                return r1
            L_0x0205:
                java.lang.String r4 = "com.google.android.gms.dynamic.IFragmentWrapper"
                r6.writeString(r4)
                return r1
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.dynamic.zzc.zza.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }
    }

    Bundle getArguments();

    int getId();

    boolean getRetainInstance();

    String getTag();

    int getTargetRequestCode();

    boolean getUserVisibleHint();

    zzd getView();

    boolean isAdded();

    boolean isDetached();

    boolean isHidden();

    boolean isInLayout();

    boolean isRemoving();

    boolean isResumed();

    boolean isVisible();

    void setHasOptionsMenu(boolean z);

    void setMenuVisibility(boolean z);

    void setRetainInstance(boolean z);

    void setUserVisibleHint(boolean z);

    void startActivity(Intent intent);

    void startActivityForResult(Intent intent, int i);

    void zzac(zzd zzd);

    void zzad(zzd zzd);

    zzd zzbdu();

    zzc zzbdv();

    zzd zzbdw();

    zzc zzbdx();
}
