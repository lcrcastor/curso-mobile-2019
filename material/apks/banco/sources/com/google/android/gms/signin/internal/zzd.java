package com.google.android.gms.signin.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Status;

public interface zzd extends IInterface {

    public static abstract class zza extends Binder implements zzd {

        /* renamed from: com.google.android.gms.signin.internal.zzd$zza$zza reason: collision with other inner class name */
        static class C0093zza implements zzd {
            private IBinder a;

            C0093zza(IBinder iBinder) {
                this.a = iBinder;
            }

            public IBinder asBinder() {
                return this.a;
            }

            public void zza(ConnectionResult connectionResult, AuthAccountResult authAccountResult) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.signin.internal.ISignInCallbacks");
                    if (connectionResult != null) {
                        obtain.writeInt(1);
                        connectionResult.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    if (authAccountResult != null) {
                        obtain.writeInt(1);
                        authAccountResult.writeToParcel(obtain, 0);
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

            public void zza(Status status, GoogleSignInAccount googleSignInAccount) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.signin.internal.ISignInCallbacks");
                    if (status != null) {
                        obtain.writeInt(1);
                        status.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    if (googleSignInAccount != null) {
                        obtain.writeInt(1);
                        googleSignInAccount.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.a.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void zzb(SignInResponse signInResponse) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.signin.internal.ISignInCallbacks");
                    if (signInResponse != null) {
                        obtain.writeInt(1);
                        signInResponse.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.a.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void zzed(Status status) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.signin.internal.ISignInCallbacks");
                    if (status != null) {
                        obtain.writeInt(1);
                        status.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.a.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void zzee(Status status) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.signin.internal.ISignInCallbacks");
                    if (status != null) {
                        obtain.writeInt(1);
                        status.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.a.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public zza() {
            attachInterface(this, "com.google.android.gms.signin.internal.ISignInCallbacks");
        }

        public static zzd zzla(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.signin.internal.ISignInCallbacks");
            return (queryLocalInterface == null || !(queryLocalInterface instanceof zzd)) ? new C0093zza(iBinder) : (zzd) queryLocalInterface;
        }

        public IBinder asBinder() {
            return this;
        }

        /* JADX WARNING: type inference failed for: r1v0 */
        /* JADX WARNING: type inference failed for: r1v1, types: [com.google.android.gms.signin.internal.AuthAccountResult] */
        /* JADX WARNING: type inference failed for: r1v3, types: [com.google.android.gms.signin.internal.AuthAccountResult] */
        /* JADX WARNING: type inference failed for: r1v4, types: [com.google.android.gms.common.api.Status] */
        /* JADX WARNING: type inference failed for: r1v6, types: [com.google.android.gms.common.api.Status] */
        /* JADX WARNING: type inference failed for: r1v7, types: [com.google.android.gms.common.api.Status] */
        /* JADX WARNING: type inference failed for: r1v9, types: [com.google.android.gms.common.api.Status] */
        /* JADX WARNING: type inference failed for: r1v10, types: [com.google.android.gms.auth.api.signin.GoogleSignInAccount] */
        /* JADX WARNING: type inference failed for: r1v12, types: [com.google.android.gms.auth.api.signin.GoogleSignInAccount] */
        /* JADX WARNING: type inference failed for: r1v13, types: [com.google.android.gms.signin.internal.SignInResponse] */
        /* JADX WARNING: type inference failed for: r1v15, types: [com.google.android.gms.signin.internal.SignInResponse] */
        /* JADX WARNING: type inference failed for: r1v16 */
        /* JADX WARNING: type inference failed for: r1v17 */
        /* JADX WARNING: type inference failed for: r1v18 */
        /* JADX WARNING: type inference failed for: r1v19 */
        /* JADX WARNING: type inference failed for: r1v20 */
        /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r1v0
          assigns: [?[int, float, boolean, short, byte, char, OBJECT, ARRAY], com.google.android.gms.common.api.Status, com.google.android.gms.signin.internal.AuthAccountResult, com.google.android.gms.auth.api.signin.GoogleSignInAccount, com.google.android.gms.signin.internal.SignInResponse]
          uses: [com.google.android.gms.signin.internal.AuthAccountResult, com.google.android.gms.common.api.Status, com.google.android.gms.auth.api.signin.GoogleSignInAccount, com.google.android.gms.signin.internal.SignInResponse]
          mth insns count: 79
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
                r0 = 1
                r1 = 0
                switch(r3) {
                    case 3: goto L_0x008c;
                    case 4: goto L_0x0071;
                    case 6: goto L_0x0056;
                    case 7: goto L_0x002b;
                    case 8: goto L_0x0010;
                    case 1598968902: goto L_0x000a;
                    default: goto L_0x0005;
                }
            L_0x0005:
                boolean r3 = super.onTransact(r3, r4, r5, r6)
                return r3
            L_0x000a:
                java.lang.String r3 = "com.google.android.gms.signin.internal.ISignInCallbacks"
                r5.writeString(r3)
                return r0
            L_0x0010:
                java.lang.String r3 = "com.google.android.gms.signin.internal.ISignInCallbacks"
                r4.enforceInterface(r3)
                int r3 = r4.readInt()
                if (r3 == 0) goto L_0x0024
                android.os.Parcelable$Creator<com.google.android.gms.signin.internal.SignInResponse> r3 = com.google.android.gms.signin.internal.SignInResponse.CREATOR
                java.lang.Object r3 = r3.createFromParcel(r4)
                r1 = r3
                com.google.android.gms.signin.internal.SignInResponse r1 = (com.google.android.gms.signin.internal.SignInResponse) r1
            L_0x0024:
                r2.zzb(r1)
                r5.writeNoException()
                return r0
            L_0x002b:
                java.lang.String r3 = "com.google.android.gms.signin.internal.ISignInCallbacks"
                r4.enforceInterface(r3)
                int r3 = r4.readInt()
                if (r3 == 0) goto L_0x003f
                android.os.Parcelable$Creator<com.google.android.gms.common.api.Status> r3 = com.google.android.gms.common.api.Status.CREATOR
                java.lang.Object r3 = r3.createFromParcel(r4)
                com.google.android.gms.common.api.Status r3 = (com.google.android.gms.common.api.Status) r3
                goto L_0x0040
            L_0x003f:
                r3 = r1
            L_0x0040:
                int r6 = r4.readInt()
                if (r6 == 0) goto L_0x004f
                android.os.Parcelable$Creator<com.google.android.gms.auth.api.signin.GoogleSignInAccount> r6 = com.google.android.gms.auth.api.signin.GoogleSignInAccount.CREATOR
                java.lang.Object r4 = r6.createFromParcel(r4)
                r1 = r4
                com.google.android.gms.auth.api.signin.GoogleSignInAccount r1 = (com.google.android.gms.auth.api.signin.GoogleSignInAccount) r1
            L_0x004f:
                r2.zza(r3, r1)
                r5.writeNoException()
                return r0
            L_0x0056:
                java.lang.String r3 = "com.google.android.gms.signin.internal.ISignInCallbacks"
                r4.enforceInterface(r3)
                int r3 = r4.readInt()
                if (r3 == 0) goto L_0x006a
                android.os.Parcelable$Creator<com.google.android.gms.common.api.Status> r3 = com.google.android.gms.common.api.Status.CREATOR
                java.lang.Object r3 = r3.createFromParcel(r4)
                r1 = r3
                com.google.android.gms.common.api.Status r1 = (com.google.android.gms.common.api.Status) r1
            L_0x006a:
                r2.zzee(r1)
                r5.writeNoException()
                return r0
            L_0x0071:
                java.lang.String r3 = "com.google.android.gms.signin.internal.ISignInCallbacks"
                r4.enforceInterface(r3)
                int r3 = r4.readInt()
                if (r3 == 0) goto L_0x0085
                android.os.Parcelable$Creator<com.google.android.gms.common.api.Status> r3 = com.google.android.gms.common.api.Status.CREATOR
                java.lang.Object r3 = r3.createFromParcel(r4)
                r1 = r3
                com.google.android.gms.common.api.Status r1 = (com.google.android.gms.common.api.Status) r1
            L_0x0085:
                r2.zzed(r1)
                r5.writeNoException()
                return r0
            L_0x008c:
                java.lang.String r3 = "com.google.android.gms.signin.internal.ISignInCallbacks"
                r4.enforceInterface(r3)
                int r3 = r4.readInt()
                if (r3 == 0) goto L_0x00a0
                android.os.Parcelable$Creator<com.google.android.gms.common.ConnectionResult> r3 = com.google.android.gms.common.ConnectionResult.CREATOR
                java.lang.Object r3 = r3.createFromParcel(r4)
                com.google.android.gms.common.ConnectionResult r3 = (com.google.android.gms.common.ConnectionResult) r3
                goto L_0x00a1
            L_0x00a0:
                r3 = r1
            L_0x00a1:
                int r6 = r4.readInt()
                if (r6 == 0) goto L_0x00b0
                android.os.Parcelable$Creator<com.google.android.gms.signin.internal.AuthAccountResult> r6 = com.google.android.gms.signin.internal.AuthAccountResult.CREATOR
                java.lang.Object r4 = r6.createFromParcel(r4)
                r1 = r4
                com.google.android.gms.signin.internal.AuthAccountResult r1 = (com.google.android.gms.signin.internal.AuthAccountResult) r1
            L_0x00b0:
                r2.zza(r3, r1)
                r5.writeNoException()
                return r0
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.signin.internal.zzd.zza.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }
    }

    void zza(ConnectionResult connectionResult, AuthAccountResult authAccountResult);

    void zza(Status status, GoogleSignInAccount googleSignInAccount);

    void zzb(SignInResponse signInResponse);

    void zzed(Status status);

    void zzee(Status status);
}
