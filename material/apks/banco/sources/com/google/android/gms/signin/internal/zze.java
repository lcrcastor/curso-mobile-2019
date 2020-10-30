package com.google.android.gms.signin.internal;

import android.accounts.Account;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import com.google.android.gms.common.internal.AuthAccountRequest;
import com.google.android.gms.common.internal.ResolveAccountRequest;
import com.google.android.gms.common.internal.zzr;
import com.google.android.gms.common.internal.zzx;

public interface zze extends IInterface {

    public static abstract class zza extends Binder implements zze {

        /* renamed from: com.google.android.gms.signin.internal.zze$zza$zza reason: collision with other inner class name */
        static class C0094zza implements zze {
            private IBinder a;

            C0094zza(IBinder iBinder) {
                this.a = iBinder;
            }

            public IBinder asBinder() {
                return this.a;
            }

            public void zza(int i, Account account, zzd zzd) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.signin.internal.ISignInService");
                    obtain.writeInt(i);
                    if (account != null) {
                        obtain.writeInt(1);
                        account.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeStrongBinder(zzd != null ? zzd.asBinder() : null);
                    this.a.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void zza(AuthAccountRequest authAccountRequest, zzd zzd) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.signin.internal.ISignInService");
                    if (authAccountRequest != null) {
                        obtain.writeInt(1);
                        authAccountRequest.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeStrongBinder(zzd != null ? zzd.asBinder() : null);
                    this.a.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void zza(ResolveAccountRequest resolveAccountRequest, zzx zzx) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.signin.internal.ISignInService");
                    if (resolveAccountRequest != null) {
                        obtain.writeInt(1);
                        resolveAccountRequest.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeStrongBinder(zzx != null ? zzx.asBinder() : null);
                    this.a.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void zza(zzr zzr, int i, boolean z) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.signin.internal.ISignInService");
                    obtain.writeStrongBinder(zzr != null ? zzr.asBinder() : null);
                    obtain.writeInt(i);
                    obtain.writeInt(z ? 1 : 0);
                    this.a.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void zza(CheckServerAuthResult checkServerAuthResult) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.signin.internal.ISignInService");
                    if (checkServerAuthResult != null) {
                        obtain.writeInt(1);
                        checkServerAuthResult.writeToParcel(obtain, 0);
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

            public void zza(RecordConsentRequest recordConsentRequest, zzd zzd) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.signin.internal.ISignInService");
                    if (recordConsentRequest != null) {
                        obtain.writeInt(1);
                        recordConsentRequest.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeStrongBinder(zzd != null ? zzd.asBinder() : null);
                    this.a.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void zza(SignInRequest signInRequest, zzd zzd) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.signin.internal.ISignInService");
                    if (signInRequest != null) {
                        obtain.writeInt(1);
                        signInRequest.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeStrongBinder(zzd != null ? zzd.asBinder() : null);
                    this.a.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void zzaaf(int i) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.signin.internal.ISignInService");
                    obtain.writeInt(i);
                    this.a.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void zzb(zzd zzd) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.signin.internal.ISignInService");
                    obtain.writeStrongBinder(zzd != null ? zzd.asBinder() : null);
                    this.a.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void zzcj(boolean z) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.signin.internal.ISignInService");
                    obtain.writeInt(z ? 1 : 0);
                    this.a.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public static zze zzlb(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.signin.internal.ISignInService");
            return (queryLocalInterface == null || !(queryLocalInterface instanceof zze)) ? new C0094zza(iBinder) : (zze) queryLocalInterface;
        }

        /* JADX WARNING: type inference failed for: r2v0 */
        /* JADX WARNING: type inference failed for: r2v1, types: [com.google.android.gms.common.internal.AuthAccountRequest] */
        /* JADX WARNING: type inference failed for: r2v3, types: [com.google.android.gms.common.internal.AuthAccountRequest] */
        /* JADX WARNING: type inference failed for: r2v4, types: [com.google.android.gms.signin.internal.CheckServerAuthResult] */
        /* JADX WARNING: type inference failed for: r2v6, types: [com.google.android.gms.signin.internal.CheckServerAuthResult] */
        /* JADX WARNING: type inference failed for: r2v7, types: [com.google.android.gms.common.internal.ResolveAccountRequest] */
        /* JADX WARNING: type inference failed for: r2v9, types: [com.google.android.gms.common.internal.ResolveAccountRequest] */
        /* JADX WARNING: type inference failed for: r2v10, types: [android.accounts.Account] */
        /* JADX WARNING: type inference failed for: r2v12, types: [android.accounts.Account] */
        /* JADX WARNING: type inference failed for: r2v13, types: [com.google.android.gms.signin.internal.RecordConsentRequest] */
        /* JADX WARNING: type inference failed for: r2v15, types: [com.google.android.gms.signin.internal.RecordConsentRequest] */
        /* JADX WARNING: type inference failed for: r2v16, types: [com.google.android.gms.signin.internal.SignInRequest] */
        /* JADX WARNING: type inference failed for: r2v18, types: [com.google.android.gms.signin.internal.SignInRequest] */
        /* JADX WARNING: type inference failed for: r2v19 */
        /* JADX WARNING: type inference failed for: r2v20 */
        /* JADX WARNING: type inference failed for: r2v21 */
        /* JADX WARNING: type inference failed for: r2v22 */
        /* JADX WARNING: type inference failed for: r2v23 */
        /* JADX WARNING: type inference failed for: r2v24 */
        /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r2v0
          assigns: [?[int, float, boolean, short, byte, char, OBJECT, ARRAY], com.google.android.gms.signin.internal.CheckServerAuthResult, com.google.android.gms.common.internal.AuthAccountRequest, com.google.android.gms.common.internal.ResolveAccountRequest, android.accounts.Account, com.google.android.gms.signin.internal.RecordConsentRequest, com.google.android.gms.signin.internal.SignInRequest]
          uses: [com.google.android.gms.common.internal.AuthAccountRequest, com.google.android.gms.signin.internal.CheckServerAuthResult, com.google.android.gms.common.internal.ResolveAccountRequest, android.accounts.Account, com.google.android.gms.signin.internal.RecordConsentRequest, com.google.android.gms.signin.internal.SignInRequest]
          mth insns count: 125
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
        	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:311)
        	at jadx.api.JavaClass.decompile(JavaClass.java:62)
        	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:217)
         */
        /* JADX WARNING: Unknown variable types count: 7 */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTransact(int r4, android.os.Parcel r5, android.os.Parcel r6, int r7) {
            /*
                r3 = this;
                r0 = 1598968902(0x5f4e5446, float:1.4867585E19)
                r1 = 1
                if (r4 == r0) goto L_0x0137
                r0 = 0
                r2 = 0
                switch(r4) {
                    case 2: goto L_0x0114;
                    case 3: goto L_0x00f9;
                    case 4: goto L_0x00e6;
                    case 5: goto L_0x00c3;
                    default: goto L_0x000b;
                }
            L_0x000b:
                switch(r4) {
                    case 7: goto L_0x00b3;
                    case 8: goto L_0x008c;
                    case 9: goto L_0x006d;
                    case 10: goto L_0x004a;
                    case 11: goto L_0x0036;
                    case 12: goto L_0x0013;
                    default: goto L_0x000e;
                }
            L_0x000e:
                boolean r4 = super.onTransact(r4, r5, r6, r7)
                return r4
            L_0x0013:
                java.lang.String r4 = "com.google.android.gms.signin.internal.ISignInService"
                r5.enforceInterface(r4)
                int r4 = r5.readInt()
                if (r4 == 0) goto L_0x0027
                android.os.Parcelable$Creator<com.google.android.gms.signin.internal.SignInRequest> r4 = com.google.android.gms.signin.internal.SignInRequest.CREATOR
                java.lang.Object r4 = r4.createFromParcel(r5)
                r2 = r4
                com.google.android.gms.signin.internal.SignInRequest r2 = (com.google.android.gms.signin.internal.SignInRequest) r2
            L_0x0027:
                android.os.IBinder r4 = r5.readStrongBinder()
                com.google.android.gms.signin.internal.zzd r4 = com.google.android.gms.signin.internal.zzd.zza.zzla(r4)
                r3.zza(r2, r4)
                r6.writeNoException()
                return r1
            L_0x0036:
                java.lang.String r4 = "com.google.android.gms.signin.internal.ISignInService"
                r5.enforceInterface(r4)
                android.os.IBinder r4 = r5.readStrongBinder()
                com.google.android.gms.signin.internal.zzd r4 = com.google.android.gms.signin.internal.zzd.zza.zzla(r4)
                r3.zzb(r4)
                r6.writeNoException()
                return r1
            L_0x004a:
                java.lang.String r4 = "com.google.android.gms.signin.internal.ISignInService"
                r5.enforceInterface(r4)
                int r4 = r5.readInt()
                if (r4 == 0) goto L_0x005e
                android.os.Parcelable$Creator<com.google.android.gms.signin.internal.RecordConsentRequest> r4 = com.google.android.gms.signin.internal.RecordConsentRequest.CREATOR
                java.lang.Object r4 = r4.createFromParcel(r5)
                r2 = r4
                com.google.android.gms.signin.internal.RecordConsentRequest r2 = (com.google.android.gms.signin.internal.RecordConsentRequest) r2
            L_0x005e:
                android.os.IBinder r4 = r5.readStrongBinder()
                com.google.android.gms.signin.internal.zzd r4 = com.google.android.gms.signin.internal.zzd.zza.zzla(r4)
                r3.zza(r2, r4)
                r6.writeNoException()
                return r1
            L_0x006d:
                java.lang.String r4 = "com.google.android.gms.signin.internal.ISignInService"
                r5.enforceInterface(r4)
                android.os.IBinder r4 = r5.readStrongBinder()
                com.google.android.gms.common.internal.zzr r4 = com.google.android.gms.common.internal.zzr.zza.zzdr(r4)
                int r7 = r5.readInt()
                int r5 = r5.readInt()
                if (r5 == 0) goto L_0x0085
                r0 = 1
            L_0x0085:
                r3.zza(r4, r7, r0)
                r6.writeNoException()
                return r1
            L_0x008c:
                java.lang.String r4 = "com.google.android.gms.signin.internal.ISignInService"
                r5.enforceInterface(r4)
                int r4 = r5.readInt()
                int r7 = r5.readInt()
                if (r7 == 0) goto L_0x00a4
                android.os.Parcelable$Creator r7 = android.accounts.Account.CREATOR
                java.lang.Object r7 = r7.createFromParcel(r5)
                r2 = r7
                android.accounts.Account r2 = (android.accounts.Account) r2
            L_0x00a4:
                android.os.IBinder r5 = r5.readStrongBinder()
                com.google.android.gms.signin.internal.zzd r5 = com.google.android.gms.signin.internal.zzd.zza.zzla(r5)
                r3.zza(r4, r2, r5)
                r6.writeNoException()
                return r1
            L_0x00b3:
                java.lang.String r4 = "com.google.android.gms.signin.internal.ISignInService"
                r5.enforceInterface(r4)
                int r4 = r5.readInt()
                r3.zzaaf(r4)
                r6.writeNoException()
                return r1
            L_0x00c3:
                java.lang.String r4 = "com.google.android.gms.signin.internal.ISignInService"
                r5.enforceInterface(r4)
                int r4 = r5.readInt()
                if (r4 == 0) goto L_0x00d7
                android.os.Parcelable$Creator<com.google.android.gms.common.internal.ResolveAccountRequest> r4 = com.google.android.gms.common.internal.ResolveAccountRequest.CREATOR
                java.lang.Object r4 = r4.createFromParcel(r5)
                r2 = r4
                com.google.android.gms.common.internal.ResolveAccountRequest r2 = (com.google.android.gms.common.internal.ResolveAccountRequest) r2
            L_0x00d7:
                android.os.IBinder r4 = r5.readStrongBinder()
                com.google.android.gms.common.internal.zzx r4 = com.google.android.gms.common.internal.zzx.zza.zzdx(r4)
                r3.zza(r2, r4)
                r6.writeNoException()
                return r1
            L_0x00e6:
                java.lang.String r4 = "com.google.android.gms.signin.internal.ISignInService"
                r5.enforceInterface(r4)
                int r4 = r5.readInt()
                if (r4 == 0) goto L_0x00f2
                r0 = 1
            L_0x00f2:
                r3.zzcj(r0)
                r6.writeNoException()
                return r1
            L_0x00f9:
                java.lang.String r4 = "com.google.android.gms.signin.internal.ISignInService"
                r5.enforceInterface(r4)
                int r4 = r5.readInt()
                if (r4 == 0) goto L_0x010d
                android.os.Parcelable$Creator<com.google.android.gms.signin.internal.CheckServerAuthResult> r4 = com.google.android.gms.signin.internal.CheckServerAuthResult.CREATOR
                java.lang.Object r4 = r4.createFromParcel(r5)
                r2 = r4
                com.google.android.gms.signin.internal.CheckServerAuthResult r2 = (com.google.android.gms.signin.internal.CheckServerAuthResult) r2
            L_0x010d:
                r3.zza(r2)
                r6.writeNoException()
                return r1
            L_0x0114:
                java.lang.String r4 = "com.google.android.gms.signin.internal.ISignInService"
                r5.enforceInterface(r4)
                int r4 = r5.readInt()
                if (r4 == 0) goto L_0x0128
                android.os.Parcelable$Creator<com.google.android.gms.common.internal.AuthAccountRequest> r4 = com.google.android.gms.common.internal.AuthAccountRequest.CREATOR
                java.lang.Object r4 = r4.createFromParcel(r5)
                r2 = r4
                com.google.android.gms.common.internal.AuthAccountRequest r2 = (com.google.android.gms.common.internal.AuthAccountRequest) r2
            L_0x0128:
                android.os.IBinder r4 = r5.readStrongBinder()
                com.google.android.gms.signin.internal.zzd r4 = com.google.android.gms.signin.internal.zzd.zza.zzla(r4)
                r3.zza(r2, r4)
                r6.writeNoException()
                return r1
            L_0x0137:
                java.lang.String r4 = "com.google.android.gms.signin.internal.ISignInService"
                r6.writeString(r4)
                return r1
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.signin.internal.zze.zza.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }
    }

    void zza(int i, Account account, zzd zzd);

    void zza(AuthAccountRequest authAccountRequest, zzd zzd);

    void zza(ResolveAccountRequest resolveAccountRequest, zzx zzx);

    void zza(zzr zzr, int i, boolean z);

    void zza(CheckServerAuthResult checkServerAuthResult);

    void zza(RecordConsentRequest recordConsentRequest, zzd zzd);

    void zza(SignInRequest signInRequest, zzd zzd);

    void zzaaf(int i);

    void zzb(zzd zzd);

    void zzcj(boolean z);
}
