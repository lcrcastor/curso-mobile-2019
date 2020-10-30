package com.appsflyer;

import android.content.Context;
import android.content.Intent;
import android.os.Looper;
import ar.com.santander.rio.mbanking.utils.UtilsCuentas;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;

final class i implements Runnable {
    private static String j;
    /* access modifiers changed from: private */
    public WeakReference<Context> a = null;
    private String b;
    private String c;
    private String d;
    private String e;
    private String f;
    private String g;
    /* access modifiers changed from: private */
    public Map<String, String> h;
    private ScheduledExecutorService i;
    private final Intent k;

    static {
        StringBuilder sb = new StringBuilder("https://validate.%s/api/v");
        sb.append(AppsFlyerLib.a);
        sb.append("/androidevent?buildnumber=4.8.11&app_id=");
        j = sb.toString();
    }

    i(Context context, String str, String str2, String str3, String str4, String str5, String str6, Map<String, String> map, ScheduledExecutorService scheduledExecutorService, Intent intent) {
        this.a = new WeakReference<>(context);
        this.b = str;
        this.c = str2;
        this.e = str4;
        this.f = str5;
        this.g = str6;
        this.h = map;
        this.d = str3;
        this.i = scheduledExecutorService;
        this.k = intent;
    }

    /* JADX WARNING: Removed duplicated region for block: B:37:0x011e A[Catch:{ all -> 0x0117 }] */
    /* JADX WARNING: Removed duplicated region for block: B:40:0x0139  */
    /* JADX WARNING: Removed duplicated region for block: B:44:0x0140  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void run() {
        /*
            r10 = this;
            java.lang.String r0 = r10.b
            if (r0 == 0) goto L_0x0144
            java.lang.String r0 = r10.b
            int r0 = r0.length()
            if (r0 != 0) goto L_0x000e
            goto L_0x0144
        L_0x000e:
            com.appsflyer.AppsFlyerLib r0 = com.appsflyer.AppsFlyerLib.getInstance()
            boolean r0 = r0.isTrackingStopped()
            if (r0 == 0) goto L_0x0019
            return
        L_0x0019:
            r0 = 0
            r1 = 0
            java.lang.ref.WeakReference<android.content.Context> r2 = r10.a     // Catch:{ Throwable -> 0x0119 }
            java.lang.Object r2 = r2.get()     // Catch:{ Throwable -> 0x0119 }
            android.content.Context r2 = (android.content.Context) r2     // Catch:{ Throwable -> 0x0119 }
            if (r2 != 0) goto L_0x0026
            return
        L_0x0026:
            java.util.HashMap r3 = new java.util.HashMap     // Catch:{ Throwable -> 0x0119 }
            r3.<init>()     // Catch:{ Throwable -> 0x0119 }
            java.lang.String r4 = "public-key"
            java.lang.String r5 = r10.c     // Catch:{ Throwable -> 0x0119 }
            r3.put(r4, r5)     // Catch:{ Throwable -> 0x0119 }
            java.lang.String r4 = "sig-data"
            java.lang.String r5 = r10.e     // Catch:{ Throwable -> 0x0119 }
            r3.put(r4, r5)     // Catch:{ Throwable -> 0x0119 }
            java.lang.String r4 = "signature"
            java.lang.String r5 = r10.d     // Catch:{ Throwable -> 0x0119 }
            r3.put(r4, r5)     // Catch:{ Throwable -> 0x0119 }
            java.util.HashMap r4 = new java.util.HashMap     // Catch:{ Throwable -> 0x0119 }
            r4.<init>()     // Catch:{ Throwable -> 0x0119 }
            r4.putAll(r3)     // Catch:{ Throwable -> 0x0119 }
            java.util.concurrent.ScheduledExecutorService r5 = java.util.concurrent.Executors.newSingleThreadScheduledExecutor()     // Catch:{ Throwable -> 0x0119 }
            com.appsflyer.i$1 r6 = new com.appsflyer.i$1     // Catch:{ Throwable -> 0x0119 }
            r6.<init>(r4)     // Catch:{ Throwable -> 0x0119 }
            r7 = 5
            java.util.concurrent.TimeUnit r4 = java.util.concurrent.TimeUnit.MILLISECONDS     // Catch:{ Throwable -> 0x0119 }
            r5.schedule(r6, r7, r4)     // Catch:{ Throwable -> 0x0119 }
            java.lang.String r4 = "dev_key"
            java.lang.String r5 = r10.b     // Catch:{ Throwable -> 0x0119 }
            r3.put(r4, r5)     // Catch:{ Throwable -> 0x0119 }
            java.lang.String r4 = "app_id"
            java.lang.String r5 = r2.getPackageName()     // Catch:{ Throwable -> 0x0119 }
            r3.put(r4, r5)     // Catch:{ Throwable -> 0x0119 }
            java.lang.String r4 = "uid"
            com.appsflyer.AppsFlyerLib r5 = com.appsflyer.AppsFlyerLib.getInstance()     // Catch:{ Throwable -> 0x0119 }
            java.lang.String r2 = r5.getAppsFlyerUID(r2)     // Catch:{ Throwable -> 0x0119 }
            r3.put(r4, r2)     // Catch:{ Throwable -> 0x0119 }
            java.lang.String r2 = "advertiserId"
            com.appsflyer.AppsFlyerProperties r4 = com.appsflyer.AppsFlyerProperties.getInstance()     // Catch:{ Throwable -> 0x0119 }
            java.lang.String r5 = "advertiserId"
            java.lang.String r4 = r4.getString(r5)     // Catch:{ Throwable -> 0x0119 }
            r3.put(r2, r4)     // Catch:{ Throwable -> 0x0119 }
            org.json.JSONObject r2 = new org.json.JSONObject     // Catch:{ Throwable -> 0x0119 }
            r2.<init>(r3)     // Catch:{ Throwable -> 0x0119 }
            java.lang.String r2 = r2.toString()     // Catch:{ Throwable -> 0x0119 }
            java.lang.String r3 = "https://sdk-services.%s/validate-android-signature"
            java.lang.String r3 = com.appsflyer.ServerConfigHandler.getUrl(r3)     // Catch:{ Throwable -> 0x0119 }
            com.appsflyer.y r4 = com.appsflyer.y.a()     // Catch:{ Throwable -> 0x0119 }
            r4.a(r3, r2)     // Catch:{ Throwable -> 0x0119 }
            java.net.HttpURLConnection r2 = a(r2, r3)     // Catch:{ Throwable -> 0x0119 }
            r0 = -1
            if (r2 == 0) goto L_0x00b0
            int r0 = r2.getResponseCode()     // Catch:{ Throwable -> 0x00ab, all -> 0x00a6 }
            goto L_0x00b0
        L_0x00a6:
            r0 = move-exception
            r1 = r0
            r0 = r2
            goto L_0x013e
        L_0x00ab:
            r0 = move-exception
            r9 = r2
            r2 = r0
            r0 = r9
            goto L_0x011a
        L_0x00b0:
            com.appsflyer.AppsFlyerLib.getInstance()     // Catch:{ Throwable -> 0x00ab, all -> 0x00a6 }
            java.lang.String r4 = com.appsflyer.AppsFlyerLib.a(r2)     // Catch:{ Throwable -> 0x00ab, all -> 0x00a6 }
            com.appsflyer.y r5 = com.appsflyer.y.a()     // Catch:{ Throwable -> 0x00ab, all -> 0x00a6 }
            r5.a(r3, r0, r4)     // Catch:{ Throwable -> 0x00ab, all -> 0x00a6 }
            org.json.JSONObject r3 = new org.json.JSONObject     // Catch:{ Throwable -> 0x00ab, all -> 0x00a6 }
            r3.<init>(r4)     // Catch:{ Throwable -> 0x00ab, all -> 0x00a6 }
            java.lang.String r4 = "code"
            r3.put(r4, r0)     // Catch:{ Throwable -> 0x00ab, all -> 0x00a6 }
            r4 = 200(0xc8, float:2.8E-43)
            if (r0 != r4) goto L_0x00ff
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x00ab, all -> 0x00a6 }
            java.lang.String r4 = "Validate response 200 ok: "
            r0.<init>(r4)     // Catch:{ Throwable -> 0x00ab, all -> 0x00a6 }
            java.lang.String r4 = r3.toString()     // Catch:{ Throwable -> 0x00ab, all -> 0x00a6 }
            r0.append(r4)     // Catch:{ Throwable -> 0x00ab, all -> 0x00a6 }
            java.lang.String r0 = r0.toString()     // Catch:{ Throwable -> 0x00ab, all -> 0x00a6 }
            com.appsflyer.AFLogger.afInfoLog(r0)     // Catch:{ Throwable -> 0x00ab, all -> 0x00a6 }
            java.lang.String r0 = "result"
            boolean r0 = r3.optBoolean(r0)     // Catch:{ Throwable -> 0x00ab, all -> 0x00a6 }
            if (r0 == 0) goto L_0x00f0
            java.lang.String r0 = "result"
            boolean r0 = r3.getBoolean(r0)     // Catch:{ Throwable -> 0x00ab, all -> 0x00a6 }
            goto L_0x00f1
        L_0x00f0:
            r0 = 0
        L_0x00f1:
            java.lang.String r4 = r10.e     // Catch:{ Throwable -> 0x00ab, all -> 0x00a6 }
            java.lang.String r5 = r10.f     // Catch:{ Throwable -> 0x00ab, all -> 0x00a6 }
            java.lang.String r6 = r10.g     // Catch:{ Throwable -> 0x00ab, all -> 0x00a6 }
            java.lang.String r3 = r3.toString()     // Catch:{ Throwable -> 0x00ab, all -> 0x00a6 }
            a(r0, r4, r5, r6, r3)     // Catch:{ Throwable -> 0x00ab, all -> 0x00a6 }
            goto L_0x0111
        L_0x00ff:
            java.lang.String r0 = "Failed Validate request"
            com.appsflyer.AFLogger.afInfoLog(r0)     // Catch:{ Throwable -> 0x00ab, all -> 0x00a6 }
            java.lang.String r0 = r10.e     // Catch:{ Throwable -> 0x00ab, all -> 0x00a6 }
            java.lang.String r4 = r10.f     // Catch:{ Throwable -> 0x00ab, all -> 0x00a6 }
            java.lang.String r5 = r10.g     // Catch:{ Throwable -> 0x00ab, all -> 0x00a6 }
            java.lang.String r3 = r3.toString()     // Catch:{ Throwable -> 0x00ab, all -> 0x00a6 }
            a(r1, r0, r4, r5, r3)     // Catch:{ Throwable -> 0x00ab, all -> 0x00a6 }
        L_0x0111:
            if (r2 == 0) goto L_0x013d
            r2.disconnect()
            return
        L_0x0117:
            r1 = move-exception
            goto L_0x013e
        L_0x0119:
            r2 = move-exception
        L_0x011a:
            com.appsflyer.AppsFlyerInAppPurchaseValidatorListener r3 = com.appsflyer.AppsFlyerLib.c     // Catch:{ all -> 0x0117 }
            if (r3 == 0) goto L_0x0130
            java.lang.String r3 = "Failed Validate request + ex"
            com.appsflyer.AFLogger.afErrorLog(r3, r2)     // Catch:{ all -> 0x0117 }
            java.lang.String r3 = r10.e     // Catch:{ all -> 0x0117 }
            java.lang.String r4 = r10.f     // Catch:{ all -> 0x0117 }
            java.lang.String r5 = r10.g     // Catch:{ all -> 0x0117 }
            java.lang.String r6 = r2.getMessage()     // Catch:{ all -> 0x0117 }
            a(r1, r3, r4, r5, r6)     // Catch:{ all -> 0x0117 }
        L_0x0130:
            java.lang.String r1 = r2.getMessage()     // Catch:{ all -> 0x0117 }
            com.appsflyer.AFLogger.afErrorLog(r1, r2)     // Catch:{ all -> 0x0117 }
            if (r0 == 0) goto L_0x013d
            r0.disconnect()
            return
        L_0x013d:
            return
        L_0x013e:
            if (r0 == 0) goto L_0x0143
            r0.disconnect()
        L_0x0143:
            throw r1
        L_0x0144:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.appsflyer.i.run():void");
    }

    private static HttpURLConnection a(String str, String str2) {
        try {
            l lVar = new l(null, AppsFlyerLib.getInstance().isTrackingStopped());
            lVar.b = str;
            lVar.c();
            if (Thread.currentThread() == Looper.getMainLooper().getThread()) {
                StringBuilder sb = new StringBuilder("Main thread detected. Calling ");
                sb.append(str2);
                sb.append(" in a new thread.");
                AFLogger.afDebugLog(sb.toString());
                lVar.execute(new String[]{str2});
            } else {
                StringBuilder sb2 = new StringBuilder("Calling ");
                sb2.append(str2);
                sb2.append(" (on current thread: ");
                sb2.append(Thread.currentThread().toString());
                sb2.append(" )");
                AFLogger.afDebugLog(sb2.toString());
                lVar.onPreExecute();
                lVar.onPostExecute(lVar.doInBackground(str2));
            }
            return lVar.b();
        } catch (Throwable th) {
            AFLogger.afErrorLog("Could not send callStats request", th);
            return null;
        }
    }

    private static void a(boolean z, String str, String str2, String str3, String str4) {
        if (AppsFlyerLib.c != null) {
            StringBuilder sb = new StringBuilder("Validate callback parameters: ");
            sb.append(str);
            sb.append(UtilsCuentas.SEPARAOR2);
            sb.append(str2);
            sb.append(UtilsCuentas.SEPARAOR2);
            sb.append(str3);
            AFLogger.afDebugLog(sb.toString());
            if (z) {
                AFLogger.afDebugLog("Validate in app purchase success: ".concat(String.valueOf(str4)));
                AppsFlyerLib.c.onValidateInApp();
                return;
            }
            AFLogger.afDebugLog("Validate in app purchase failed: ".concat(String.valueOf(str4)));
            AppsFlyerInAppPurchaseValidatorListener appsFlyerInAppPurchaseValidatorListener = AppsFlyerLib.c;
            if (str4 == null) {
                str4 = "Failed validating";
            }
            appsFlyerInAppPurchaseValidatorListener.onValidateInAppFailure(str4);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:49:0x0151  */
    /* JADX WARNING: Removed duplicated region for block: B:52:0x0157  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static /* synthetic */ void a(com.appsflyer.i r16, java.util.Map r17, java.util.Map r18, java.lang.ref.WeakReference r19) {
        /*
            r1 = r16
            java.lang.Object r2 = r19.get()
            if (r2 == 0) goto L_0x015b
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = j
            java.lang.String r3 = com.appsflyer.ServerConfigHandler.getUrl(r3)
            r2.append(r3)
            java.lang.Object r3 = r19.get()
            android.content.Context r3 = (android.content.Context) r3
            java.lang.String r3 = r3.getPackageName()
            r2.append(r3)
            java.lang.String r2 = r2.toString()
            java.lang.Object r3 = r19.get()
            android.content.Context r3 = (android.content.Context) r3
            java.lang.String r4 = "appsflyer-data"
            r5 = 0
            android.content.SharedPreferences r13 = r3.getSharedPreferences(r4, r5)
            java.lang.String r3 = "referrer"
            r4 = 0
            java.lang.String r3 = r13.getString(r3, r4)
            if (r3 != 0) goto L_0x003f
            java.lang.String r3 = ""
        L_0x003f:
            r11 = r3
            com.appsflyer.AppsFlyerLib r6 = com.appsflyer.AppsFlyerLib.getInstance()
            java.lang.Object r3 = r19.get()
            r7 = r3
            android.content.Context r7 = (android.content.Context) r7
            java.lang.String r8 = r1.b
            java.lang.String r9 = "af_purchase"
            java.lang.String r10 = ""
            r12 = 1
            r14 = 0
            android.content.Intent r15 = r1.k
            java.util.Map r3 = r6.a(r7, r8, r9, r10, r11, r12, r13, r14, r15)
            java.lang.String r5 = "price"
            java.lang.String r6 = r1.f
            r3.put(r5, r6)
            java.lang.String r5 = "currency"
            java.lang.String r1 = r1.g
            r3.put(r5, r1)
            org.json.JSONObject r1 = new org.json.JSONObject
            r1.<init>(r3)
            org.json.JSONObject r3 = new org.json.JSONObject
            r3.<init>()
            java.util.Set r5 = r17.entrySet()     // Catch:{ JSONException -> 0x0099 }
            java.util.Iterator r5 = r5.iterator()     // Catch:{ JSONException -> 0x0099 }
        L_0x0079:
            boolean r6 = r5.hasNext()     // Catch:{ JSONException -> 0x0099 }
            if (r6 == 0) goto L_0x0093
            java.lang.Object r6 = r5.next()     // Catch:{ JSONException -> 0x0099 }
            java.util.Map$Entry r6 = (java.util.Map.Entry) r6     // Catch:{ JSONException -> 0x0099 }
            java.lang.Object r7 = r6.getKey()     // Catch:{ JSONException -> 0x0099 }
            java.lang.String r7 = (java.lang.String) r7     // Catch:{ JSONException -> 0x0099 }
            java.lang.Object r6 = r6.getValue()     // Catch:{ JSONException -> 0x0099 }
            r3.put(r7, r6)     // Catch:{ JSONException -> 0x0099 }
            goto L_0x0079
        L_0x0093:
            java.lang.String r5 = "receipt_data"
            r1.put(r5, r3)     // Catch:{ JSONException -> 0x0099 }
            goto L_0x00a0
        L_0x0099:
            r0 = move-exception
            r3 = r0
            java.lang.String r5 = "Failed to build 'receipt_data'"
            com.appsflyer.AFLogger.afErrorLog(r5, r3)
        L_0x00a0:
            if (r18 == 0) goto L_0x00d6
            org.json.JSONObject r5 = new org.json.JSONObject
            r5.<init>()
            java.util.Set r3 = r18.entrySet()     // Catch:{ JSONException -> 0x00cf }
            java.util.Iterator r3 = r3.iterator()     // Catch:{ JSONException -> 0x00cf }
        L_0x00af:
            boolean r6 = r3.hasNext()     // Catch:{ JSONException -> 0x00cf }
            if (r6 == 0) goto L_0x00c9
            java.lang.Object r6 = r3.next()     // Catch:{ JSONException -> 0x00cf }
            java.util.Map$Entry r6 = (java.util.Map.Entry) r6     // Catch:{ JSONException -> 0x00cf }
            java.lang.Object r7 = r6.getKey()     // Catch:{ JSONException -> 0x00cf }
            java.lang.String r7 = (java.lang.String) r7     // Catch:{ JSONException -> 0x00cf }
            java.lang.Object r6 = r6.getValue()     // Catch:{ JSONException -> 0x00cf }
            r5.put(r7, r6)     // Catch:{ JSONException -> 0x00cf }
            goto L_0x00af
        L_0x00c9:
            java.lang.String r3 = "extra_prms"
            r1.put(r3, r5)     // Catch:{ JSONException -> 0x00cf }
            goto L_0x00d6
        L_0x00cf:
            r0 = move-exception
            r3 = r0
            java.lang.String r5 = "Failed to build 'extra_prms'"
            com.appsflyer.AFLogger.afErrorLog(r5, r3)
        L_0x00d6:
            java.lang.String r1 = r1.toString()
            com.appsflyer.y r3 = com.appsflyer.y.a()
            r3.a(r2, r1)
            java.net.HttpURLConnection r1 = a(r1, r2)     // Catch:{ Throwable -> 0x0146 }
            r3 = -1
            if (r1 == 0) goto L_0x00f3
            int r3 = r1.getResponseCode()     // Catch:{ Throwable -> 0x00f0, all -> 0x00ed }
            goto L_0x00f3
        L_0x00ed:
            r0 = move-exception
            r4 = r1
            goto L_0x0144
        L_0x00f0:
            r0 = move-exception
            r4 = r1
            goto L_0x0147
        L_0x00f3:
            com.appsflyer.AppsFlyerLib.getInstance()     // Catch:{ Throwable -> 0x00f0, all -> 0x00ed }
            java.lang.String r4 = com.appsflyer.AppsFlyerLib.a(r1)     // Catch:{ Throwable -> 0x00f0, all -> 0x00ed }
            com.appsflyer.y r5 = com.appsflyer.y.a()     // Catch:{ Throwable -> 0x00f0, all -> 0x00ed }
            r5.a(r2, r3, r4)     // Catch:{ Throwable -> 0x00f0, all -> 0x00ed }
            org.json.JSONObject r2 = new org.json.JSONObject     // Catch:{ Throwable -> 0x00f0, all -> 0x00ed }
            r2.<init>(r4)     // Catch:{ Throwable -> 0x00f0, all -> 0x00ed }
            r4 = 200(0xc8, float:2.8E-43)
            if (r3 != r4) goto L_0x0120
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x00f0, all -> 0x00ed }
            java.lang.String r4 = "Validate-WH response - 200: "
            r3.<init>(r4)     // Catch:{ Throwable -> 0x00f0, all -> 0x00ed }
            java.lang.String r2 = r2.toString()     // Catch:{ Throwable -> 0x00f0, all -> 0x00ed }
            r3.append(r2)     // Catch:{ Throwable -> 0x00f0, all -> 0x00ed }
            java.lang.String r2 = r3.toString()     // Catch:{ Throwable -> 0x00f0, all -> 0x00ed }
            com.appsflyer.AFLogger.afInfoLog(r2)     // Catch:{ Throwable -> 0x00f0, all -> 0x00ed }
            goto L_0x013d
        L_0x0120:
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x00f0, all -> 0x00ed }
            java.lang.String r5 = "Validate-WH response failed - "
            r4.<init>(r5)     // Catch:{ Throwable -> 0x00f0, all -> 0x00ed }
            r4.append(r3)     // Catch:{ Throwable -> 0x00f0, all -> 0x00ed }
            java.lang.String r3 = ": "
            r4.append(r3)     // Catch:{ Throwable -> 0x00f0, all -> 0x00ed }
            java.lang.String r2 = r2.toString()     // Catch:{ Throwable -> 0x00f0, all -> 0x00ed }
            r4.append(r2)     // Catch:{ Throwable -> 0x00f0, all -> 0x00ed }
            java.lang.String r2 = r4.toString()     // Catch:{ Throwable -> 0x00f0, all -> 0x00ed }
            com.appsflyer.AFLogger.afWarnLog(r2)     // Catch:{ Throwable -> 0x00f0, all -> 0x00ed }
        L_0x013d:
            if (r1 == 0) goto L_0x015b
            r1.disconnect()
            return
        L_0x0143:
            r0 = move-exception
        L_0x0144:
            r1 = r0
            goto L_0x0155
        L_0x0146:
            r0 = move-exception
        L_0x0147:
            r1 = r0
            java.lang.String r2 = r1.getMessage()     // Catch:{ all -> 0x0143 }
            com.appsflyer.AFLogger.afErrorLog(r2, r1)     // Catch:{ all -> 0x0143 }
            if (r4 == 0) goto L_0x015b
            r4.disconnect()
            return
        L_0x0155:
            if (r4 == 0) goto L_0x015a
            r4.disconnect()
        L_0x015a:
            throw r1
        L_0x015b:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.appsflyer.i.a(com.appsflyer.i, java.util.Map, java.util.Map, java.lang.ref.WeakReference):void");
    }
}
