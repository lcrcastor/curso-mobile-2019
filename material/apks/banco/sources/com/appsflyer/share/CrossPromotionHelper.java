package com.appsflyer.share;

import android.content.Context;
import android.os.AsyncTask;
import com.appsflyer.AFLogger;
import com.appsflyer.AppsFlyerLib;
import com.appsflyer.AppsFlyerProperties;
import com.appsflyer.ServerConfigHandler;
import com.appsflyer.ServerParameters;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

public class CrossPromotionHelper {

    static class b extends AsyncTask<String, Void, Void> {
        private c a;
        private WeakReference<Context> b;
        private boolean c;

        b(c cVar, Context context, boolean z) {
            this.a = cVar;
            this.b = new WeakReference<>(context);
            this.c = z;
        }

        /* access modifiers changed from: private */
        /* JADX WARNING: Code restructure failed: missing block: B:22:0x0086, code lost:
            if (r2 != null) goto L_0x009a;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:30:0x0098, code lost:
            if (r2 != null) goto L_0x009a;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:31:0x009a, code lost:
            r2.disconnect();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:32:0x009d, code lost:
            return null;
         */
        /* JADX WARNING: Removed duplicated region for block: B:35:0x00a1  */
        /* renamed from: a */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public java.lang.Void doInBackground(java.lang.String... r6) {
            /*
                r5 = this;
                boolean r0 = r5.c
                r1 = 0
                if (r0 == 0) goto L_0x0006
                return r1
            L_0x0006:
                r0 = 0
                r6 = r6[r0]     // Catch:{ Throwable -> 0x008e, all -> 0x008b }
                java.net.URL r2 = new java.net.URL     // Catch:{ Throwable -> 0x008e, all -> 0x008b }
                r2.<init>(r6)     // Catch:{ Throwable -> 0x008e, all -> 0x008b }
                java.net.URLConnection r2 = r2.openConnection()     // Catch:{ Throwable -> 0x008e, all -> 0x008b }
                java.net.HttpURLConnection r2 = (java.net.HttpURLConnection) r2     // Catch:{ Throwable -> 0x008e, all -> 0x008b }
                r3 = 10000(0x2710, float:1.4013E-41)
                r2.setConnectTimeout(r3)     // Catch:{ Throwable -> 0x0089 }
                r2.setInstanceFollowRedirects(r0)     // Catch:{ Throwable -> 0x0089 }
                int r3 = r2.getResponseCode()     // Catch:{ Throwable -> 0x0089 }
                r4 = 200(0xc8, float:2.8E-43)
                if (r3 != r4) goto L_0x0032
                java.lang.String r3 = "Cross promotion impressions success: "
                java.lang.String r6 = java.lang.String.valueOf(r6)     // Catch:{ Throwable -> 0x0089 }
                java.lang.String r6 = r3.concat(r6)     // Catch:{ Throwable -> 0x0089 }
                com.appsflyer.AFLogger.afInfoLog(r6, r0)     // Catch:{ Throwable -> 0x0089 }
                goto L_0x0086
            L_0x0032:
                r4 = 301(0x12d, float:4.22E-43)
                if (r3 == r4) goto L_0x0055
                r4 = 302(0x12e, float:4.23E-43)
                if (r3 != r4) goto L_0x003b
                goto L_0x0055
            L_0x003b:
                java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0089 }
                java.lang.String r4 = "call to "
                r0.<init>(r4)     // Catch:{ Throwable -> 0x0089 }
                r0.append(r6)     // Catch:{ Throwable -> 0x0089 }
                java.lang.String r6 = " failed: "
                r0.append(r6)     // Catch:{ Throwable -> 0x0089 }
                r0.append(r3)     // Catch:{ Throwable -> 0x0089 }
                java.lang.String r6 = r0.toString()     // Catch:{ Throwable -> 0x0089 }
                com.appsflyer.AFLogger.afInfoLog(r6)     // Catch:{ Throwable -> 0x0089 }
                goto L_0x0086
            L_0x0055:
                java.lang.String r3 = "Cross promotion redirection success: "
                java.lang.String r6 = java.lang.String.valueOf(r6)     // Catch:{ Throwable -> 0x0089 }
                java.lang.String r6 = r3.concat(r6)     // Catch:{ Throwable -> 0x0089 }
                com.appsflyer.AFLogger.afInfoLog(r6, r0)     // Catch:{ Throwable -> 0x0089 }
                com.appsflyer.share.c r6 = r5.a     // Catch:{ Throwable -> 0x0089 }
                if (r6 == 0) goto L_0x0086
                java.lang.ref.WeakReference<android.content.Context> r6 = r5.b     // Catch:{ Throwable -> 0x0089 }
                java.lang.Object r6 = r6.get()     // Catch:{ Throwable -> 0x0089 }
                if (r6 == 0) goto L_0x0086
                java.lang.String r6 = "Location"
                java.lang.String r6 = r2.getHeaderField(r6)     // Catch:{ Throwable -> 0x0089 }
                com.appsflyer.share.c r0 = r5.a     // Catch:{ Throwable -> 0x0089 }
                r0.a(r6)     // Catch:{ Throwable -> 0x0089 }
                com.appsflyer.share.c r6 = r5.a     // Catch:{ Throwable -> 0x0089 }
                java.lang.ref.WeakReference<android.content.Context> r0 = r5.b     // Catch:{ Throwable -> 0x0089 }
                java.lang.Object r0 = r0.get()     // Catch:{ Throwable -> 0x0089 }
                android.content.Context r0 = (android.content.Context) r0     // Catch:{ Throwable -> 0x0089 }
                r6.a(r0)     // Catch:{ Throwable -> 0x0089 }
            L_0x0086:
                if (r2 == 0) goto L_0x009d
                goto L_0x009a
            L_0x0089:
                r6 = move-exception
                goto L_0x0090
            L_0x008b:
                r6 = move-exception
                r2 = r1
                goto L_0x009f
            L_0x008e:
                r6 = move-exception
                r2 = r1
            L_0x0090:
                java.lang.String r0 = r6.getMessage()     // Catch:{ all -> 0x009e }
                r3 = 1
                com.appsflyer.AFLogger.afErrorLog(r0, r6, r3)     // Catch:{ all -> 0x009e }
                if (r2 == 0) goto L_0x009d
            L_0x009a:
                r2.disconnect()
            L_0x009d:
                return r1
            L_0x009e:
                r6 = move-exception
            L_0x009f:
                if (r2 == 0) goto L_0x00a4
                r2.disconnect()
            L_0x00a4:
                throw r6
            */
            throw new UnsupportedOperationException("Method not decompiled: com.appsflyer.share.CrossPromotionHelper.b.doInBackground(java.lang.String[]):java.lang.Void");
        }
    }

    public static void trackAndOpenStore(Context context, String str, String str2) {
        trackAndOpenStore(context, str, str2, null);
    }

    public static void trackAndOpenStore(Context context, String str, String str2, Map<String, String> map) {
        LinkGenerator a = a(context, str, str2, map, ServerConfigHandler.getUrl(Constants.BASE_URL_APP_APPSFLYER_COM));
        if (AppsFlyerProperties.getInstance().getBoolean(AppsFlyerProperties.AF_WAITFOR_CUSTOMERID, false)) {
            AFLogger.afInfoLog("CustomerUserId not set, track And Open Store is disabled", true);
            return;
        }
        HashMap hashMap = new HashMap();
        if (map != null) {
            hashMap.putAll(map);
        }
        hashMap.put("af_campaign", str2);
        AppsFlyerLib.getInstance().trackEvent(context, "af_cross_promotion", hashMap);
        new b(new c(), context, AppsFlyerLib.getInstance().isTrackingStopped()).execute(new String[]{a.generateLink()});
    }

    public static void trackCrossPromoteImpression(Context context, String str, String str2) {
        if (AppsFlyerProperties.getInstance().getBoolean(AppsFlyerProperties.AF_WAITFOR_CUSTOMERID, false)) {
            AFLogger.afInfoLog("CustomerUserId not set, Promote Impression is disabled", true);
            return;
        }
        LinkGenerator a = a(context, str, str2, null, ServerConfigHandler.getUrl("https://impression.%s"));
        new b(null, null, AppsFlyerLib.getInstance().isTrackingStopped()).execute(new String[]{a.generateLink()});
    }

    private static LinkGenerator a(Context context, String str, String str2, Map<String, String> map, String str3) {
        LinkGenerator linkGenerator = new LinkGenerator("af_cross_promotion");
        linkGenerator.b(str3).a(str).addParameter(Constants.URL_SITE_ID, context.getPackageName());
        if (str2 != null) {
            linkGenerator.setCampaign(str2);
        }
        if (map != null) {
            linkGenerator.addParameters(map);
        }
        String string = AppsFlyerProperties.getInstance().getString(ServerParameters.ADVERTISING_ID_PARAM);
        if (string != null) {
            linkGenerator.addParameter(Constants.URL_ADVERTISING_ID, string);
        }
        return linkGenerator;
    }
}
