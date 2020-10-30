package com.appsflyer;

import android.content.pm.PackageManager;
import ar.com.santander.rio.mbanking.app.ui.Constants;
import ar.com.santander.rio.mbanking.managers.checkversion.CheckVersionManager.RESPONSE_CODES;
import java.text.SimpleDateFormat;
import java.util.Locale;
import org.json.JSONArray;
import org.json.JSONObject;

final class y {
    private static y a;
    private final String A;
    private JSONObject B;
    private JSONArray C;
    private int D;
    private boolean E;
    private String F;
    private boolean b;
    private boolean c;
    private final String d;
    private final String e;
    private final String f;
    private final String g;
    private final String h;
    private final String i;
    private final String j;
    private final String k;
    private final String l;
    private final String m;
    private final String n;
    private final String o;
    private final String p;
    private final String q;
    private final String r;
    private final String s;
    private final String t;
    private final String u;
    private final String v;
    private final String w;
    private final String x;
    private final String y;
    private final String z;

    private y() {
        this.b = true;
        this.c = true;
        this.d = "brand";
        this.e = "model";
        this.f = "platform";
        this.g = "platform_version";
        this.h = ServerParameters.ADVERTISING_ID_PARAM;
        this.i = "imei";
        this.j = "android_id";
        this.k = "sdk_version";
        this.l = "devkey";
        this.m = "originalAppsFlyerId";
        this.n = "uid";
        this.o = "app_id";
        this.p = "app_version";
        this.q = AppsFlyerProperties.CHANNEL;
        this.r = "preInstall";
        this.s = "data";
        this.t = "r_debugging_off";
        this.u = "r_debugging_on";
        this.v = "public_api_call";
        this.w = "exception";
        this.x = "server_request";
        this.y = "server_response";
        this.z = "yyyy-MM-dd HH:mm:ssZ";
        this.A = "MM-dd HH:mm:ss.SSS";
        this.D = 0;
        this.F = RESPONSE_CODES.ERROR;
        this.C = new JSONArray();
        this.D = 0;
        this.E = false;
    }

    static y a() {
        if (a == null) {
            a = new y();
        }
        return a;
    }

    /* access modifiers changed from: 0000 */
    public final synchronized void a(String str) {
        this.F = str;
    }

    /* access modifiers changed from: 0000 */
    public final synchronized void b() {
        this.E = true;
        a("r_debugging_on", new SimpleDateFormat("yyyy-MM-dd HH:mm:ssZ", Locale.ENGLISH).format(Long.valueOf(System.currentTimeMillis())), new String[0]);
    }

    /* access modifiers changed from: 0000 */
    public final synchronized void c() {
        a("r_debugging_off", new SimpleDateFormat("yyyy-MM-dd HH:mm:ssZ", Locale.ENGLISH).format(Long.valueOf(System.currentTimeMillis())), new String[0]);
        this.E = false;
        this.b = false;
    }

    /* access modifiers changed from: 0000 */
    public final synchronized void d() {
        this.B = null;
        this.C = null;
        a = null;
    }

    private synchronized void a(String str, String str2, String str3, String str4, String str5, String str6) {
        try {
            this.B.put("brand", str);
            this.B.put("model", str2);
            this.B.put("platform", Constants.CURRENT_SO);
            this.B.put("platform_version", str3);
            if (str4 != null && str4.length() > 0) {
                this.B.put(ServerParameters.ADVERTISING_ID_PARAM, str4);
            }
            if (str5 != null && str5.length() > 0) {
                this.B.put("imei", str5);
            }
            if (str6 != null && str6.length() > 0) {
                this.B.put("android_id", str6);
            }
        } catch (Throwable unused) {
        }
    }

    private synchronized void a(String str, String str2, String str3, String str4) {
        try {
            this.B.put("sdk_version", str);
            if (str2 != null && str2.length() > 0) {
                this.B.put("devkey", str2);
            }
            if (str3 != null && str3.length() > 0) {
                this.B.put("originalAppsFlyerId", str3);
            }
            if (str4 != null && str4.length() > 0) {
                this.B.put("uid", str4);
            }
        } catch (Throwable unused) {
        }
    }

    private synchronized void b(String str, String str2, String str3, String str4) {
        if (str != null) {
            try {
                if (str.length() > 0) {
                    this.B.put("app_id", str);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        if (str2 != null && str2.length() > 0) {
            this.B.put("app_version", str2);
        }
        if (str3 != null && str3.length() > 0) {
            this.B.put(AppsFlyerProperties.CHANNEL, str3);
        }
        if (str4 != null && str4.length() > 0) {
            this.B.put("preInstall", str4);
        }
    }

    /* access modifiers changed from: 0000 */
    public final void a(String str, String... strArr) {
        a("public_api_call", str, strArr);
    }

    /* access modifiers changed from: 0000 */
    public final void a(Throwable th) {
        String[] strArr;
        Throwable cause = th.getCause();
        String str = "exception";
        String simpleName = th.getClass().getSimpleName();
        String message = cause == null ? th.getMessage() : cause.getMessage();
        StackTraceElement[] stackTrace = cause == null ? th.getStackTrace() : cause.getStackTrace();
        if (stackTrace == null) {
            strArr = new String[]{message};
        } else {
            String[] strArr2 = new String[(stackTrace.length + 1)];
            strArr2[0] = message;
            for (int i2 = 1; i2 < stackTrace.length; i2++) {
                strArr2[i2] = stackTrace[i2].toString();
            }
            strArr = strArr2;
        }
        a(str, simpleName, strArr);
    }

    /* access modifiers changed from: 0000 */
    public final void a(String str, String str2) {
        a("server_request", str, str2);
    }

    /* access modifiers changed from: 0000 */
    public final void a(String str, int i2, String str2) {
        a("server_response", str, String.valueOf(i2), str2);
    }

    /* access modifiers changed from: 0000 */
    public final void b(String str, String str2) {
        a((String) null, str, str2);
    }

    /* JADX WARNING: Failed to process nested try/catch */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private synchronized java.lang.String h() {
        /*
            r4 = this;
            monitor-enter(r4)
            r0 = 0
            org.json.JSONObject r1 = r4.B     // Catch:{ JSONException -> 0x0018, all -> 0x0015 }
            java.lang.String r2 = "data"
            org.json.JSONArray r3 = r4.C     // Catch:{ JSONException -> 0x0018, all -> 0x0015 }
            r1.put(r2, r3)     // Catch:{ JSONException -> 0x0018, all -> 0x0015 }
            org.json.JSONObject r1 = r4.B     // Catch:{ JSONException -> 0x0018, all -> 0x0015 }
            java.lang.String r1 = r1.toString()     // Catch:{ JSONException -> 0x0018, all -> 0x0015 }
            r4.i()     // Catch:{ JSONException -> 0x0019, all -> 0x0015 }
            goto L_0x0019
        L_0x0015:
            r0 = move-exception
            monitor-exit(r4)
            throw r0
        L_0x0018:
            r1 = r0
        L_0x0019:
            monitor-exit(r4)
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.appsflyer.y.h():java.lang.String");
    }

    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:15:0x0070 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private synchronized void b(java.lang.String r11, android.content.pm.PackageManager r12) {
        /*
            r10 = this;
            monitor-enter(r10)
            com.appsflyer.AppsFlyerProperties r0 = com.appsflyer.AppsFlyerProperties.getInstance()     // Catch:{ all -> 0x0081 }
            com.appsflyer.AppsFlyerLib r1 = com.appsflyer.AppsFlyerLib.getInstance()     // Catch:{ all -> 0x0081 }
            java.lang.String r2 = "remote_debug_static_data"
            java.lang.String r2 = r0.getString(r2)     // Catch:{ all -> 0x0081 }
            if (r2 == 0) goto L_0x0019
            org.json.JSONObject r11 = new org.json.JSONObject     // Catch:{ Throwable -> 0x0070 }
            r11.<init>(r2)     // Catch:{ Throwable -> 0x0070 }
            r10.B = r11     // Catch:{ Throwable -> 0x0070 }
            goto L_0x0070
        L_0x0019:
            org.json.JSONObject r2 = new org.json.JSONObject     // Catch:{ all -> 0x0081 }
            r2.<init>()     // Catch:{ all -> 0x0081 }
            r10.B = r2     // Catch:{ all -> 0x0081 }
            java.lang.String r4 = android.os.Build.BRAND     // Catch:{ all -> 0x0081 }
            java.lang.String r5 = android.os.Build.MODEL     // Catch:{ all -> 0x0081 }
            java.lang.String r6 = android.os.Build.VERSION.RELEASE     // Catch:{ all -> 0x0081 }
            java.lang.String r2 = "advertiserId"
            java.lang.String r7 = r0.getString(r2)     // Catch:{ all -> 0x0081 }
            java.lang.String r8 = r1.d     // Catch:{ all -> 0x0081 }
            java.lang.String r9 = r1.e     // Catch:{ all -> 0x0081 }
            r3 = r10
            r3.a(r4, r5, r6, r7, r8, r9)     // Catch:{ all -> 0x0081 }
            java.lang.String r1 = "4.8.11.383"
            java.lang.String r2 = "AppsFlyerKey"
            java.lang.String r2 = r0.getString(r2)     // Catch:{ all -> 0x0081 }
            java.lang.String r3 = "KSAppsFlyerId"
            java.lang.String r3 = r0.getString(r3)     // Catch:{ all -> 0x0081 }
            java.lang.String r4 = "uid"
            java.lang.String r4 = r0.getString(r4)     // Catch:{ all -> 0x0081 }
            r10.a(r1, r2, r3, r4)     // Catch:{ all -> 0x0081 }
            r1 = 0
            android.content.pm.PackageInfo r12 = r12.getPackageInfo(r11, r1)     // Catch:{ Throwable -> 0x0065 }
            int r12 = r12.versionCode     // Catch:{ Throwable -> 0x0065 }
            java.lang.String r1 = "channel"
            java.lang.String r1 = r0.getString(r1)     // Catch:{ Throwable -> 0x0065 }
            java.lang.String r2 = "preInstallName"
            java.lang.String r2 = r0.getString(r2)     // Catch:{ Throwable -> 0x0065 }
            java.lang.String r12 = java.lang.String.valueOf(r12)     // Catch:{ Throwable -> 0x0065 }
            r10.b(r11, r12, r1, r2)     // Catch:{ Throwable -> 0x0065 }
        L_0x0065:
            java.lang.String r11 = "remote_debug_static_data"
            org.json.JSONObject r12 = r10.B     // Catch:{ all -> 0x0081 }
            java.lang.String r12 = r12.toString()     // Catch:{ all -> 0x0081 }
            r0.set(r11, r12)     // Catch:{ all -> 0x0081 }
        L_0x0070:
            org.json.JSONObject r11 = r10.B     // Catch:{ JSONException -> 0x007b }
            java.lang.String r12 = "launch_counter"
            java.lang.String r0 = r10.F     // Catch:{ JSONException -> 0x007b }
            r11.put(r12, r0)     // Catch:{ JSONException -> 0x007b }
            monitor-exit(r10)
            return
        L_0x007b:
            r11 = move-exception
            r11.printStackTrace()     // Catch:{ all -> 0x0081 }
            monitor-exit(r10)
            return
        L_0x0081:
            r11 = move-exception
            monitor-exit(r10)
            throw r11
        */
        throw new UnsupportedOperationException("Method not decompiled: com.appsflyer.y.b(java.lang.String, android.content.pm.PackageManager):void");
    }

    private synchronized void i() {
        this.C = null;
        this.C = new JSONArray();
        this.D = 0;
    }

    /* access modifiers changed from: 0000 */
    public final synchronized void e() {
        this.b = false;
        i();
    }

    /* access modifiers changed from: 0000 */
    public final void f() {
        this.c = false;
    }

    /* access modifiers changed from: 0000 */
    public final boolean g() {
        return this.E;
    }

    static void a(String str, PackageManager packageManager) {
        try {
            if (a == null) {
                a = new y();
            }
            a.b(str, packageManager);
            if (a == null) {
                a = new y();
            }
            String h2 = a.h();
            l lVar = new l(null, AppsFlyerLib.getInstance().isTrackingStopped());
            lVar.b = h2;
            lVar.a();
            StringBuilder sb = new StringBuilder();
            sb.append(ServerConfigHandler.getUrl("https://monitorsdk.%s/remote-debug?app_id="));
            sb.append(str);
            lVar.execute(new String[]{sb.toString()});
        } catch (Throwable unused) {
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:32:0x00b4, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private synchronized void a(java.lang.String r12, java.lang.String r13, java.lang.String... r14) {
        /*
            r11 = this;
            monitor-enter(r11)
            boolean r0 = r11.c     // Catch:{ all -> 0x00b5 }
            r1 = 0
            r2 = 1
            if (r0 == 0) goto L_0x0011
            boolean r0 = r11.b     // Catch:{ all -> 0x00b5 }
            if (r0 != 0) goto L_0x000f
            boolean r0 = r11.E     // Catch:{ all -> 0x00b5 }
            if (r0 == 0) goto L_0x0011
        L_0x000f:
            r0 = 1
            goto L_0x0012
        L_0x0011:
            r0 = 0
        L_0x0012:
            if (r0 == 0) goto L_0x00b3
            int r0 = r11.D     // Catch:{ all -> 0x00b5 }
            r3 = 98304(0x18000, float:1.37753E-40)
            if (r0 < r3) goto L_0x001d
            goto L_0x00b3
        L_0x001d:
            long r3 = java.lang.System.currentTimeMillis()     // Catch:{ Throwable -> 0x00b1 }
            java.lang.String r0 = ""
            int r5 = r14.length     // Catch:{ Throwable -> 0x00b1 }
            if (r5 <= 0) goto L_0x0045
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x00b1 }
            r0.<init>()     // Catch:{ Throwable -> 0x00b1 }
            int r5 = r14.length     // Catch:{ Throwable -> 0x00b1 }
            int r5 = r5 - r2
        L_0x002d:
            if (r5 <= 0) goto L_0x003c
            r6 = r14[r5]     // Catch:{ Throwable -> 0x00b1 }
            r0.append(r6)     // Catch:{ Throwable -> 0x00b1 }
            java.lang.String r6 = ", "
            r0.append(r6)     // Catch:{ Throwable -> 0x00b1 }
            int r5 = r5 + -1
            goto L_0x002d
        L_0x003c:
            r14 = r14[r1]     // Catch:{ Throwable -> 0x00b1 }
            r0.append(r14)     // Catch:{ Throwable -> 0x00b1 }
            java.lang.String r0 = r0.toString()     // Catch:{ Throwable -> 0x00b1 }
        L_0x0045:
            java.text.SimpleDateFormat r14 = new java.text.SimpleDateFormat     // Catch:{ Throwable -> 0x00b1 }
            java.lang.String r5 = "MM-dd HH:mm:ss.SSS"
            java.util.Locale r6 = java.util.Locale.ENGLISH     // Catch:{ Throwable -> 0x00b1 }
            r14.<init>(r5, r6)     // Catch:{ Throwable -> 0x00b1 }
            java.lang.Long r3 = java.lang.Long.valueOf(r3)     // Catch:{ Throwable -> 0x00b1 }
            java.lang.String r14 = r14.format(r3)     // Catch:{ Throwable -> 0x00b1 }
            r3 = 5
            r4 = 4
            r5 = 3
            r6 = 2
            if (r12 == 0) goto L_0x0080
            java.lang.String r7 = "%18s %5s _/%s [%s] %s %s"
            r8 = 6
            java.lang.Object[] r8 = new java.lang.Object[r8]     // Catch:{ Throwable -> 0x00b1 }
            r8[r1] = r14     // Catch:{ Throwable -> 0x00b1 }
            java.lang.Thread r14 = java.lang.Thread.currentThread()     // Catch:{ Throwable -> 0x00b1 }
            long r9 = r14.getId()     // Catch:{ Throwable -> 0x00b1 }
            java.lang.Long r14 = java.lang.Long.valueOf(r9)     // Catch:{ Throwable -> 0x00b1 }
            r8[r2] = r14     // Catch:{ Throwable -> 0x00b1 }
            java.lang.String r14 = "AppsFlyer_4.8.11"
            r8[r6] = r14     // Catch:{ Throwable -> 0x00b1 }
            r8[r5] = r12     // Catch:{ Throwable -> 0x00b1 }
            r8[r4] = r13     // Catch:{ Throwable -> 0x00b1 }
            r8[r3] = r0     // Catch:{ Throwable -> 0x00b1 }
            java.lang.String r12 = java.lang.String.format(r7, r8)     // Catch:{ Throwable -> 0x00b1 }
            goto L_0x00a0
        L_0x0080:
            java.lang.String r12 = "%18s %5s %s/%s %s"
            java.lang.Object[] r3 = new java.lang.Object[r3]     // Catch:{ Throwable -> 0x00b1 }
            r3[r1] = r14     // Catch:{ Throwable -> 0x00b1 }
            java.lang.Thread r14 = java.lang.Thread.currentThread()     // Catch:{ Throwable -> 0x00b1 }
            long r7 = r14.getId()     // Catch:{ Throwable -> 0x00b1 }
            java.lang.Long r14 = java.lang.Long.valueOf(r7)     // Catch:{ Throwable -> 0x00b1 }
            r3[r2] = r14     // Catch:{ Throwable -> 0x00b1 }
            r3[r6] = r13     // Catch:{ Throwable -> 0x00b1 }
            java.lang.String r13 = "AppsFlyer_4.8.11"
            r3[r5] = r13     // Catch:{ Throwable -> 0x00b1 }
            r3[r4] = r0     // Catch:{ Throwable -> 0x00b1 }
            java.lang.String r12 = java.lang.String.format(r12, r3)     // Catch:{ Throwable -> 0x00b1 }
        L_0x00a0:
            org.json.JSONArray r13 = r11.C     // Catch:{ Throwable -> 0x00b1 }
            r13.put(r12)     // Catch:{ Throwable -> 0x00b1 }
            int r13 = r11.D     // Catch:{ Throwable -> 0x00b1 }
            byte[] r12 = r12.getBytes()     // Catch:{ Throwable -> 0x00b1 }
            int r12 = r12.length     // Catch:{ Throwable -> 0x00b1 }
            int r13 = r13 + r12
            r11.D = r13     // Catch:{ Throwable -> 0x00b1 }
            monitor-exit(r11)
            return
        L_0x00b1:
            monitor-exit(r11)
            return
        L_0x00b3:
            monitor-exit(r11)
            return
        L_0x00b5:
            r12 = move-exception
            monitor-exit(r11)
            throw r12
        */
        throw new UnsupportedOperationException("Method not decompiled: com.appsflyer.y.a(java.lang.String, java.lang.String, java.lang.String[]):void");
    }
}
