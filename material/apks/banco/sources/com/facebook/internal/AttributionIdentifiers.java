package com.facebook.internal;

import android.content.Context;
import android.net.Uri;
import android.os.Looper;
import com.appsflyer.AppsFlyerLib;
import com.facebook.FacebookException;
import java.lang.reflect.Method;

public class AttributionIdentifiers {
    private static final String a = AttributionIdentifiers.class.getCanonicalName();
    private static final Uri b = Uri.parse(AppsFlyerLib.ATTRIBUTION_ID_CONTENT_URI);
    private static AttributionIdentifiers g;
    private String c;
    private String d;
    private boolean e;
    private long f;

    private static AttributionIdentifiers a(Context context) {
        AttributionIdentifiers attributionIdentifiers = new AttributionIdentifiers();
        try {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                throw new FacebookException("getAndroidId cannot be called on the main thread.");
            }
            Method methodQuietly = Utility.getMethodQuietly("com.google.android.gms.common.GooglePlayServicesUtil", "isGooglePlayServicesAvailable", (Class<?>[]) new Class[]{Context.class});
            if (methodQuietly == null) {
                return attributionIdentifiers;
            }
            Object invokeMethodQuietly = Utility.invokeMethodQuietly(null, methodQuietly, context);
            if (invokeMethodQuietly instanceof Integer) {
                if (((Integer) invokeMethodQuietly).intValue() == 0) {
                    Method methodQuietly2 = Utility.getMethodQuietly("com.google.android.gms.ads.identifier.AdvertisingIdClient", "getAdvertisingIdInfo", (Class<?>[]) new Class[]{Context.class});
                    if (methodQuietly2 == null) {
                        return attributionIdentifiers;
                    }
                    Object invokeMethodQuietly2 = Utility.invokeMethodQuietly(null, methodQuietly2, context);
                    if (invokeMethodQuietly2 == null) {
                        return attributionIdentifiers;
                    }
                    Method methodQuietly3 = Utility.getMethodQuietly(invokeMethodQuietly2.getClass(), "getId", (Class<?>[]) new Class[0]);
                    Method methodQuietly4 = Utility.getMethodQuietly(invokeMethodQuietly2.getClass(), "isLimitAdTrackingEnabled", (Class<?>[]) new Class[0]);
                    if (methodQuietly3 != null) {
                        if (methodQuietly4 != null) {
                            attributionIdentifiers.d = (String) Utility.invokeMethodQuietly(invokeMethodQuietly2, methodQuietly3, new Object[0]);
                            attributionIdentifiers.e = ((Boolean) Utility.invokeMethodQuietly(invokeMethodQuietly2, methodQuietly4, new Object[0])).booleanValue();
                            return attributionIdentifiers;
                        }
                    }
                    return attributionIdentifiers;
                }
            }
            return attributionIdentifiers;
        } catch (Exception e2) {
            Utility.logd("android_id", e2);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:35:0x00ae  */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x00b5  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.facebook.internal.AttributionIdentifiers getAttributionIdentifiers(android.content.Context r9) {
        /*
            com.facebook.internal.AttributionIdentifiers r0 = g
            if (r0 == 0) goto L_0x0018
            long r0 = java.lang.System.currentTimeMillis()
            com.facebook.internal.AttributionIdentifiers r2 = g
            long r2 = r2.f
            long r4 = r0 - r2
            r0 = 3600000(0x36ee80, double:1.7786363E-317)
            int r2 = (r4 > r0 ? 1 : (r4 == r0 ? 0 : -1))
            if (r2 >= 0) goto L_0x0018
            com.facebook.internal.AttributionIdentifiers r9 = g
            return r9
        L_0x0018:
            com.facebook.internal.AttributionIdentifiers r0 = a(r9)
            r1 = 3
            r2 = 0
            java.lang.String[] r5 = new java.lang.String[r1]     // Catch:{ Exception -> 0x0090, all -> 0x008d }
            r1 = 0
            java.lang.String r3 = "aid"
            r5[r1] = r3     // Catch:{ Exception -> 0x0090, all -> 0x008d }
            r1 = 1
            java.lang.String r3 = "androidid"
            r5[r1] = r3     // Catch:{ Exception -> 0x0090, all -> 0x008d }
            r1 = 2
            java.lang.String r3 = "limit_tracking"
            r5[r1] = r3     // Catch:{ Exception -> 0x0090, all -> 0x008d }
            android.content.ContentResolver r3 = r9.getContentResolver()     // Catch:{ Exception -> 0x0090, all -> 0x008d }
            android.net.Uri r4 = b     // Catch:{ Exception -> 0x0090, all -> 0x008d }
            r6 = 0
            r7 = 0
            r8 = 0
            android.database.Cursor r9 = r3.query(r4, r5, r6, r7, r8)     // Catch:{ Exception -> 0x0090, all -> 0x008d }
            if (r9 == 0) goto L_0x0087
            boolean r1 = r9.moveToFirst()     // Catch:{ Exception -> 0x0085 }
            if (r1 != 0) goto L_0x0045
            goto L_0x0087
        L_0x0045:
            java.lang.String r1 = "aid"
            int r1 = r9.getColumnIndex(r1)     // Catch:{ Exception -> 0x0085 }
            java.lang.String r3 = "androidid"
            int r3 = r9.getColumnIndex(r3)     // Catch:{ Exception -> 0x0085 }
            java.lang.String r4 = "limit_tracking"
            int r4 = r9.getColumnIndex(r4)     // Catch:{ Exception -> 0x0085 }
            java.lang.String r1 = r9.getString(r1)     // Catch:{ Exception -> 0x0085 }
            r0.c = r1     // Catch:{ Exception -> 0x0085 }
            if (r3 <= 0) goto L_0x0077
            if (r4 <= 0) goto L_0x0077
            java.lang.String r1 = r0.getAndroidAdvertiserId()     // Catch:{ Exception -> 0x0085 }
            if (r1 != 0) goto L_0x0077
            java.lang.String r1 = r9.getString(r3)     // Catch:{ Exception -> 0x0085 }
            r0.d = r1     // Catch:{ Exception -> 0x0085 }
            java.lang.String r1 = r9.getString(r4)     // Catch:{ Exception -> 0x0085 }
            boolean r1 = java.lang.Boolean.parseBoolean(r1)     // Catch:{ Exception -> 0x0085 }
            r0.e = r1     // Catch:{ Exception -> 0x0085 }
        L_0x0077:
            if (r9 == 0) goto L_0x007c
            r9.close()
        L_0x007c:
            long r1 = java.lang.System.currentTimeMillis()
            r0.f = r1
            g = r0
            return r0
        L_0x0085:
            r0 = move-exception
            goto L_0x0092
        L_0x0087:
            if (r9 == 0) goto L_0x008c
            r9.close()
        L_0x008c:
            return r0
        L_0x008d:
            r0 = move-exception
            r9 = r2
            goto L_0x00b3
        L_0x0090:
            r0 = move-exception
            r9 = r2
        L_0x0092:
            java.lang.String r1 = a     // Catch:{ all -> 0x00b2 }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x00b2 }
            r3.<init>()     // Catch:{ all -> 0x00b2 }
            java.lang.String r4 = "Caught unexpected exception in getAttributionId(): "
            r3.append(r4)     // Catch:{ all -> 0x00b2 }
            java.lang.String r0 = r0.toString()     // Catch:{ all -> 0x00b2 }
            r3.append(r0)     // Catch:{ all -> 0x00b2 }
            java.lang.String r0 = r3.toString()     // Catch:{ all -> 0x00b2 }
            android.util.Log.d(r1, r0)     // Catch:{ all -> 0x00b2 }
            if (r9 == 0) goto L_0x00b1
            r9.close()
        L_0x00b1:
            return r2
        L_0x00b2:
            r0 = move-exception
        L_0x00b3:
            if (r9 == 0) goto L_0x00b8
            r9.close()
        L_0x00b8:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.internal.AttributionIdentifiers.getAttributionIdentifiers(android.content.Context):com.facebook.internal.AttributionIdentifiers");
    }

    public String getAttributionId() {
        return this.c;
    }

    public String getAndroidAdvertiserId() {
        return this.d;
    }

    public boolean isTrackingLimited() {
        return this.e;
    }
}
