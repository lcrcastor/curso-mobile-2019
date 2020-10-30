package com.appsflyer;

import android.content.Context;
import com.android.installreferrer.api.InstallReferrerClient;
import com.android.installreferrer.api.InstallReferrerStateListener;

final class e implements InstallReferrerStateListener {
    private InstallReferrerClient a;
    private b b;

    e() {
    }

    /* access modifiers changed from: protected */
    public final void a(Context context, b bVar) {
        this.b = bVar;
        this.a = InstallReferrerClient.newBuilder(context).build();
        try {
            this.a.startConnection(this);
        } catch (Exception e) {
            AFLogger.afErrorLog("referrerClient -> startConnection", e);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:29:0x00bf  */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x00ec  */
    /* JADX WARNING: Removed duplicated region for block: B:37:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void onInstallReferrerSetupFinished(int r6) {
        /*
            r5 = this;
            java.util.HashMap r0 = new java.util.HashMap
            r0.<init>()
            java.lang.String r1 = "code"
            java.lang.String r2 = java.lang.String.valueOf(r6)
            r0.put(r1, r2)
            r1 = 0
            switch(r6) {
                case 0: goto L_0x0027;
                case 1: goto L_0x0020;
                case 2: goto L_0x0019;
                default: goto L_0x0012;
            }
        L_0x0012:
            java.lang.String r6 = "responseCode not found."
            com.appsflyer.AFLogger.afWarnLog(r6)
            goto L_0x00bd
        L_0x0019:
            java.lang.String r6 = "InstallReferrer not supported"
            com.appsflyer.AFLogger.afWarnLog(r6)
            goto L_0x00bd
        L_0x0020:
            java.lang.String r6 = "InstallReferrer not supported"
            com.appsflyer.AFLogger.afWarnLog(r6)
            goto L_0x00bd
        L_0x0027:
            java.lang.String r6 = "InstallReferrer connected"
            com.appsflyer.AFLogger.afDebugLog(r6)     // Catch:{ RemoteException -> 0x009e, IllegalStateException -> 0x007e, Throwable -> 0x005e }
            com.android.installreferrer.api.InstallReferrerClient r6 = r5.a     // Catch:{ RemoteException -> 0x009e, IllegalStateException -> 0x007e, Throwable -> 0x005e }
            boolean r6 = r6.isReady()     // Catch:{ RemoteException -> 0x009e, IllegalStateException -> 0x007e, Throwable -> 0x005e }
            if (r6 == 0) goto L_0x0051
            com.android.installreferrer.api.InstallReferrerClient r6 = r5.a     // Catch:{ RemoteException -> 0x009e, IllegalStateException -> 0x007e, Throwable -> 0x005e }
            com.android.installreferrer.api.ReferrerDetails r6 = r6.getInstallReferrer()     // Catch:{ RemoteException -> 0x009e, IllegalStateException -> 0x007e, Throwable -> 0x005e }
            com.android.installreferrer.api.InstallReferrerClient r1 = r5.a     // Catch:{ RemoteException -> 0x004c, IllegalStateException -> 0x0047, Throwable -> 0x0042 }
            r1.endConnection()     // Catch:{ RemoteException -> 0x004c, IllegalStateException -> 0x0047, Throwable -> 0x0042 }
            r1 = r6
            goto L_0x00bd
        L_0x0042:
            r1 = move-exception
            r4 = r1
            r1 = r6
            r6 = r4
            goto L_0x005f
        L_0x0047:
            r1 = move-exception
            r4 = r1
            r1 = r6
            r6 = r4
            goto L_0x007f
        L_0x004c:
            r1 = move-exception
            r4 = r1
            r1 = r6
            r6 = r4
            goto L_0x009f
        L_0x0051:
            java.lang.String r6 = "ReferrerClient: InstallReferrer is not ready"
            com.appsflyer.AFLogger.afWarnLog(r6)     // Catch:{ RemoteException -> 0x009e, IllegalStateException -> 0x007e, Throwable -> 0x005e }
            java.lang.String r6 = "err"
            java.lang.String r2 = "ReferrerClient: InstallReferrer is not ready"
            r0.put(r6, r2)     // Catch:{ RemoteException -> 0x009e, IllegalStateException -> 0x007e, Throwable -> 0x005e }
            goto L_0x00bd
        L_0x005e:
            r6 = move-exception
        L_0x005f:
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            java.lang.String r3 = "Failed to get install referrer: "
            r2.<init>(r3)
            java.lang.String r3 = r6.getMessage()
            r2.append(r3)
            java.lang.String r2 = r2.toString()
            com.appsflyer.AFLogger.afWarnLog(r2)
            java.lang.String r2 = "err"
            java.lang.String r6 = r6.getMessage()
            r0.put(r2, r6)
            goto L_0x00bd
        L_0x007e:
            r6 = move-exception
        L_0x007f:
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            java.lang.String r3 = "Failed to get install referrer: "
            r2.<init>(r3)
            java.lang.String r3 = r6.getMessage()
            r2.append(r3)
            java.lang.String r2 = r2.toString()
            com.appsflyer.AFLogger.afWarnLog(r2)
            java.lang.String r2 = "err"
            java.lang.String r6 = r6.getMessage()
            r0.put(r2, r6)
            goto L_0x00bd
        L_0x009e:
            r6 = move-exception
        L_0x009f:
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            java.lang.String r3 = "Failed to get install referrer: "
            r2.<init>(r3)
            java.lang.String r3 = r6.getMessage()
            r2.append(r3)
            java.lang.String r2 = r2.toString()
            com.appsflyer.AFLogger.afWarnLog(r2)
            java.lang.String r2 = "err"
            java.lang.String r6 = r6.getMessage()
            r0.put(r2, r6)
        L_0x00bd:
            if (r1 == 0) goto L_0x00e8
            java.lang.String r6 = r1.getInstallReferrer()
            if (r6 == 0) goto L_0x00ce
            java.lang.String r6 = "val"
            java.lang.String r2 = r1.getInstallReferrer()
            r0.put(r6, r2)
        L_0x00ce:
            java.lang.String r6 = "clk"
            long r2 = r1.getReferrerClickTimestampSeconds()
            java.lang.String r2 = java.lang.Long.toString(r2)
            r0.put(r6, r2)
            java.lang.String r6 = "install"
            long r1 = r1.getInstallBeginTimestampSeconds()
            java.lang.String r1 = java.lang.Long.toString(r1)
            r0.put(r6, r1)
        L_0x00e8:
            com.appsflyer.b r6 = r5.b
            if (r6 == 0) goto L_0x00f1
            com.appsflyer.b r6 = r5.b
            r6.onHandleReferrer(r0)
        L_0x00f1:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.appsflyer.e.onInstallReferrerSetupFinished(int):void");
    }

    public final void onInstallReferrerServiceDisconnected() {
        AFLogger.afDebugLog("Install Referrer service disconnected");
    }
}
