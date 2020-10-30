package com.appsflyer;

import android.content.ContentResolver;
import android.os.Build;
import android.provider.Settings.Secure;
import com.appsflyer.share.Constants;

final class o {
    o() {
    }

    static n a(ContentResolver contentResolver) {
        String str;
        n nVar = null;
        if (contentResolver == null) {
            return null;
        }
        if (AppsFlyerProperties.getInstance().getString("amazon_aid") == null && "Amazon".equals(Build.MANUFACTURER)) {
            int i = Secure.getInt(contentResolver, "limit_ad_tracking", 2);
            if (i == 0) {
                nVar = new n(b.AMAZON, Secure.getString(contentResolver, Constants.URL_ADVERTISING_ID), false);
            } else if (i != 2) {
                String str2 = "";
                try {
                    str = Secure.getString(contentResolver, Constants.URL_ADVERTISING_ID);
                } catch (Throwable th) {
                    AFLogger.afErrorLog("Couldn't fetch Amazon Advertising ID (Ad-Tracking is limited!)", th);
                    str = str2;
                }
                nVar = new n(b.AMAZON, str, true);
            }
        }
        return nVar;
    }

    /* JADX WARNING: Removed duplicated region for block: B:29:0x0074 A[SYNTHETIC, Splitter:B:29:0x0074] */
    /* JADX WARNING: Removed duplicated region for block: B:44:0x00e3  */
    /* JADX WARNING: Removed duplicated region for block: B:47:0x00f6  */
    /* JADX WARNING: Removed duplicated region for block: B:49:0x010e  */
    /* JADX WARNING: Removed duplicated region for block: B:51:0x0129 A[ADDED_TO_REGION] */
    /* JADX WARNING: Removed duplicated region for block: B:54:? A[ADDED_TO_REGION, RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static void a(android.content.Context r9, java.util.Map<java.lang.String, java.lang.Object> r10) {
        /*
            java.lang.String r0 = "Trying to fetch GAID.."
            com.appsflyer.AFLogger.afInfoLog(r0)
            r0 = 0
            r1 = 1
            r2 = 0
            r3 = -1
            java.lang.String r4 = "com.google.android.gms.ads.identifier.AdvertisingIdClient"
            java.lang.Class.forName(r4)     // Catch:{ Throwable -> 0x0040 }
            com.google.android.gms.ads.identifier.AdvertisingIdClient$Info r4 = com.google.android.gms.ads.identifier.AdvertisingIdClient.getAdvertisingIdInfo(r9)     // Catch:{ Throwable -> 0x0040 }
            if (r4 == 0) goto L_0x0037
            java.lang.String r5 = r4.getId()     // Catch:{ Throwable -> 0x0040 }
            boolean r4 = r4.isLimitAdTrackingEnabled()     // Catch:{ Throwable -> 0x0035 }
            r4 = r4 ^ r1
            java.lang.String r4 = java.lang.Boolean.toString(r4)     // Catch:{ Throwable -> 0x0035 }
            if (r5 == 0) goto L_0x0030
            int r2 = r5.length()     // Catch:{ Throwable -> 0x002a }
            if (r2 != 0) goto L_0x003d
            goto L_0x0030
        L_0x002a:
            r0 = move-exception
            r2 = 1
            r8 = r4
            r4 = r0
            r0 = r8
            goto L_0x0042
        L_0x0030:
            java.lang.String r0 = "emptyOrNull"
            r2 = 1
            goto L_0x00e6
        L_0x0035:
            r4 = move-exception
            goto L_0x0042
        L_0x0037:
            java.lang.String r4 = "gpsAdInfo-null"
            r5 = r0
            r1 = 0
            r0 = r4
            r4 = r5
        L_0x003d:
            r2 = r1
            goto L_0x00e6
        L_0x0040:
            r4 = move-exception
            r5 = r0
        L_0x0042:
            java.lang.String r6 = r4.getMessage()
            com.appsflyer.AFLogger.afErrorLog(r6, r4)
            com.google.android.gms.common.GoogleApiAvailability r6 = com.google.android.gms.common.GoogleApiAvailability.getInstance()     // Catch:{ Throwable -> 0x0053 }
            int r6 = r6.isGooglePlayServicesAvailable(r9)     // Catch:{ Throwable -> 0x0053 }
            r3 = r6
            goto L_0x005b
        L_0x0053:
            r6 = move-exception
            java.lang.String r7 = r6.getMessage()
            com.appsflyer.AFLogger.afErrorLog(r7, r6)
        L_0x005b:
            java.lang.Class r4 = r4.getClass()
            java.lang.String r4 = r4.getSimpleName()
            java.lang.String r6 = "WARNING: Google Play Services is missing."
            com.appsflyer.AFLogger.afInfoLog(r6)
            com.appsflyer.AppsFlyerProperties r6 = com.appsflyer.AppsFlyerProperties.getInstance()
            java.lang.String r7 = "enableGpsFallback"
            boolean r6 = r6.getBoolean(r7, r1)
            if (r6 == 0) goto L_0x00e3
            com.appsflyer.m$a r0 = com.appsflyer.m.a(r9)     // Catch:{ Throwable -> 0x0094 }
            java.lang.String r5 = r0.a()     // Catch:{ Throwable -> 0x0094 }
            boolean r0 = r0.b()     // Catch:{ Throwable -> 0x0094 }
            r0 = r0 ^ r1
            java.lang.String r0 = java.lang.Boolean.toString(r0)     // Catch:{ Throwable -> 0x0094 }
            if (r5 == 0) goto L_0x0090
            int r1 = r5.length()     // Catch:{ Throwable -> 0x0094 }
            if (r1 != 0) goto L_0x008e
            goto L_0x0090
        L_0x008e:
            r1 = r4
            goto L_0x0092
        L_0x0090:
            java.lang.String r1 = "emptyOrNull (bypass)"
        L_0x0092:
            r4 = r0
            goto L_0x00e1
        L_0x0094:
            r0 = move-exception
            java.lang.String r1 = r0.getMessage()
            com.appsflyer.AFLogger.afErrorLog(r1, r0)
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            r1.append(r4)
            java.lang.String r4 = "/"
            r1.append(r4)
            java.lang.Class r4 = r0.getClass()
            java.lang.String r4 = r4.getSimpleName()
            r1.append(r4)
            java.lang.String r1 = r1.toString()
            com.appsflyer.AppsFlyerProperties r4 = com.appsflyer.AppsFlyerProperties.getInstance()
            java.lang.String r5 = "advertiserId"
            java.lang.String r5 = r4.getString(r5)
            com.appsflyer.AppsFlyerProperties r4 = com.appsflyer.AppsFlyerProperties.getInstance()
            java.lang.String r6 = "advertiserIdEnabled"
            java.lang.String r4 = r4.getString(r6)
            java.lang.String r6 = r0.getLocalizedMessage()
            if (r6 == 0) goto L_0x00da
            java.lang.String r0 = r0.getLocalizedMessage()
            com.appsflyer.AFLogger.afInfoLog(r0)
            goto L_0x00e1
        L_0x00da:
            java.lang.String r0 = r0.toString()
            com.appsflyer.AFLogger.afInfoLog(r0)
        L_0x00e1:
            r0 = r1
            goto L_0x00e6
        L_0x00e3:
            r8 = r4
            r4 = r0
            r0 = r8
        L_0x00e6:
            java.lang.Class r9 = r9.getClass()
            java.lang.String r9 = r9.getName()
            java.lang.String r1 = "android.app.ReceiverRestrictedContext"
            boolean r9 = r9.equals(r1)
            if (r9 == 0) goto L_0x010c
            com.appsflyer.AppsFlyerProperties r9 = com.appsflyer.AppsFlyerProperties.getInstance()
            java.lang.String r0 = "advertiserId"
            java.lang.String r5 = r9.getString(r0)
            com.appsflyer.AppsFlyerProperties r9 = com.appsflyer.AppsFlyerProperties.getInstance()
            java.lang.String r0 = "advertiserIdEnabled"
            java.lang.String r4 = r9.getString(r0)
            java.lang.String r0 = "context = android.app.ReceiverRestrictedContext"
        L_0x010c:
            if (r0 == 0) goto L_0x0127
            java.lang.String r9 = "gaidError"
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            r1.append(r3)
            java.lang.String r3 = ": "
            r1.append(r3)
            r1.append(r0)
            java.lang.String r0 = r1.toString()
            r10.put(r9, r0)
        L_0x0127:
            if (r5 == 0) goto L_0x0150
            if (r4 == 0) goto L_0x0150
            java.lang.String r9 = "advertiserId"
            r10.put(r9, r5)
            java.lang.String r9 = "advertiserIdEnabled"
            r10.put(r9, r4)
            com.appsflyer.AppsFlyerProperties r9 = com.appsflyer.AppsFlyerProperties.getInstance()
            java.lang.String r0 = "advertiserId"
            r9.set(r0, r5)
            com.appsflyer.AppsFlyerProperties r9 = com.appsflyer.AppsFlyerProperties.getInstance()
            java.lang.String r0 = "advertiserIdEnabled"
            r9.set(r0, r4)
            java.lang.String r9 = "isGaidWithGps"
            java.lang.String r0 = java.lang.String.valueOf(r2)
            r10.put(r9, r0)
        L_0x0150:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.appsflyer.o.a(android.content.Context, java.util.Map):void");
    }
}
