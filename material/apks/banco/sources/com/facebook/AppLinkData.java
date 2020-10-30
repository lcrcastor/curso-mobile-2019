package com.facebook;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import com.facebook.internal.ServerProtocol;
import com.facebook.internal.Utility;
import com.facebook.internal.Validate;
import java.util.Iterator;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AppLinkData {
    public static final String ARGUMENTS_NATIVE_CLASS_KEY = "com.facebook.platform.APPLINK_NATIVE_CLASS";
    public static final String ARGUMENTS_NATIVE_URL = "com.facebook.platform.APPLINK_NATIVE_URL";
    public static final String ARGUMENTS_REFERER_DATA_KEY = "referer_data";
    public static final String ARGUMENTS_TAPTIME_KEY = "com.facebook.platform.APPLINK_TAP_TIME_UTC";
    private static final String a = AppLinkData.class.getCanonicalName();
    private String b;
    private Uri c;
    private JSONObject d;
    private Bundle e;

    public interface CompletionHandler {
        void onDeferredAppLinkDataFetched(AppLinkData appLinkData);
    }

    public static void fetchDeferredAppLinkData(Context context, CompletionHandler completionHandler) {
        fetchDeferredAppLinkData(context, null, completionHandler);
    }

    public static void fetchDeferredAppLinkData(Context context, final String str, final CompletionHandler completionHandler) {
        Validate.notNull(context, "context");
        Validate.notNull(completionHandler, "completionHandler");
        if (str == null) {
            str = Utility.getMetadataApplicationId(context);
        }
        Validate.notNull(str, "applicationId");
        final Context applicationContext = context.getApplicationContext();
        Settings.getExecutor().execute(new Runnable() {
            public void run() {
                AppLinkData.b(applicationContext, str, completionHandler);
            }
        });
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Can't wrap try/catch for region: R(2:21|22) */
    /* JADX WARNING: Code restructure failed: missing block: B:22:?, code lost:
        android.util.Log.d(a, "Unable to put tap time in AppLinkData.arguments");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:?, code lost:
        android.util.Log.d(a, "Unable to put tap time in AppLinkData.arguments");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:?, code lost:
        android.util.Log.d(a, "Unable to put tap time in AppLinkData.arguments");
     */
    /* JADX WARNING: Exception block dominator not found, dom blocks: [] */
    /* JADX WARNING: Missing exception handler attribute for start block: B:21:0x008b */
    /* JADX WARNING: Missing exception handler attribute for start block: B:31:0x00ab */
    /* JADX WARNING: Missing exception handler attribute for start block: B:41:0x00cb */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void b(android.content.Context r7, java.lang.String r8, com.facebook.AppLinkData.CompletionHandler r9) {
        /*
            com.facebook.model.GraphObject r0 = com.facebook.model.GraphObject.Factory.create()
            java.lang.String r1 = "event"
            java.lang.String r2 = "DEFERRED_APP_LINK"
            r0.setProperty(r1, r2)
            com.facebook.internal.AttributionIdentifiers r1 = com.facebook.internal.AttributionIdentifiers.getAttributionIdentifiers(r7)
            java.lang.String r2 = com.facebook.internal.Utility.getHashedDeviceAndAppID(r7, r8)
            boolean r3 = com.facebook.Settings.getLimitEventAndDataUsage(r7)
            com.facebook.internal.Utility.setAppEventAttributionParameters(r0, r1, r2, r3)
            java.lang.String r1 = "application_package_name"
            java.lang.String r7 = r7.getPackageName()
            r0.setProperty(r1, r7)
            java.lang.String r7 = "%s/activities"
            r1 = 1
            java.lang.Object[] r1 = new java.lang.Object[r1]
            r2 = 0
            r1[r2] = r8
            java.lang.String r7 = java.lang.String.format(r7, r1)
            r8 = 0
            com.facebook.Request r7 = com.facebook.Request.newPostRequest(r8, r7, r0, r8)     // Catch:{ Exception -> 0x00d4 }
            com.facebook.Response r7 = r7.executeAndWait()     // Catch:{ Exception -> 0x00d4 }
            com.facebook.model.GraphObject r7 = r7.getGraphObject()     // Catch:{ Exception -> 0x00d4 }
            if (r7 == 0) goto L_0x0043
            org.json.JSONObject r7 = r7.getInnerJSONObject()     // Catch:{ Exception -> 0x00d4 }
            goto L_0x0044
        L_0x0043:
            r7 = r8
        L_0x0044:
            if (r7 == 0) goto L_0x00db
            java.lang.String r0 = "applink_args"
            java.lang.String r0 = r7.optString(r0)     // Catch:{ Exception -> 0x00d4 }
            java.lang.String r1 = "click_time"
            r2 = -1
            long r4 = r7.optLong(r1, r2)     // Catch:{ Exception -> 0x00d4 }
            java.lang.String r1 = "applink_class"
            java.lang.String r1 = r7.optString(r1)     // Catch:{ Exception -> 0x00d4 }
            java.lang.String r6 = "applink_url"
            java.lang.String r7 = r7.optString(r6)     // Catch:{ Exception -> 0x00d4 }
            boolean r6 = android.text.TextUtils.isEmpty(r0)     // Catch:{ Exception -> 0x00d4 }
            if (r6 != 0) goto L_0x00db
            com.facebook.AppLinkData r0 = a(r0)     // Catch:{ Exception -> 0x00d4 }
            int r8 = (r4 > r2 ? 1 : (r4 == r2 ? 0 : -1))
            if (r8 == 0) goto L_0x0092
            org.json.JSONObject r8 = r0.d     // Catch:{ JSONException -> 0x008b }
            if (r8 == 0) goto L_0x0079
            org.json.JSONObject r8 = r0.d     // Catch:{ JSONException -> 0x008b }
            java.lang.String r2 = "com.facebook.platform.APPLINK_TAP_TIME_UTC"
            r8.put(r2, r4)     // Catch:{ JSONException -> 0x008b }
        L_0x0079:
            android.os.Bundle r8 = r0.e     // Catch:{ JSONException -> 0x008b }
            if (r8 == 0) goto L_0x0092
            android.os.Bundle r8 = r0.e     // Catch:{ JSONException -> 0x008b }
            java.lang.String r2 = "com.facebook.platform.APPLINK_TAP_TIME_UTC"
            java.lang.String r3 = java.lang.Long.toString(r4)     // Catch:{ JSONException -> 0x008b }
            r8.putString(r2, r3)     // Catch:{ JSONException -> 0x008b }
            goto L_0x0092
        L_0x0089:
            r8 = r0
            goto L_0x00d4
        L_0x008b:
            java.lang.String r8 = a     // Catch:{ Exception -> 0x0089 }
            java.lang.String r2 = "Unable to put tap time in AppLinkData.arguments"
            android.util.Log.d(r8, r2)     // Catch:{ Exception -> 0x0089 }
        L_0x0092:
            if (r1 == 0) goto L_0x00b2
            org.json.JSONObject r8 = r0.d     // Catch:{ JSONException -> 0x00ab }
            if (r8 == 0) goto L_0x009f
            org.json.JSONObject r8 = r0.d     // Catch:{ JSONException -> 0x00ab }
            java.lang.String r2 = "com.facebook.platform.APPLINK_NATIVE_CLASS"
            r8.put(r2, r1)     // Catch:{ JSONException -> 0x00ab }
        L_0x009f:
            android.os.Bundle r8 = r0.e     // Catch:{ JSONException -> 0x00ab }
            if (r8 == 0) goto L_0x00b2
            android.os.Bundle r8 = r0.e     // Catch:{ JSONException -> 0x00ab }
            java.lang.String r2 = "com.facebook.platform.APPLINK_NATIVE_CLASS"
            r8.putString(r2, r1)     // Catch:{ JSONException -> 0x00ab }
            goto L_0x00b2
        L_0x00ab:
            java.lang.String r8 = a     // Catch:{ Exception -> 0x0089 }
            java.lang.String r1 = "Unable to put tap time in AppLinkData.arguments"
            android.util.Log.d(r8, r1)     // Catch:{ Exception -> 0x0089 }
        L_0x00b2:
            if (r7 == 0) goto L_0x00d2
            org.json.JSONObject r8 = r0.d     // Catch:{ JSONException -> 0x00cb }
            if (r8 == 0) goto L_0x00bf
            org.json.JSONObject r8 = r0.d     // Catch:{ JSONException -> 0x00cb }
            java.lang.String r1 = "com.facebook.platform.APPLINK_NATIVE_URL"
            r8.put(r1, r7)     // Catch:{ JSONException -> 0x00cb }
        L_0x00bf:
            android.os.Bundle r8 = r0.e     // Catch:{ JSONException -> 0x00cb }
            if (r8 == 0) goto L_0x00d2
            android.os.Bundle r8 = r0.e     // Catch:{ JSONException -> 0x00cb }
            java.lang.String r1 = "com.facebook.platform.APPLINK_NATIVE_URL"
            r8.putString(r1, r7)     // Catch:{ JSONException -> 0x00cb }
            goto L_0x00d2
        L_0x00cb:
            java.lang.String r7 = a     // Catch:{ Exception -> 0x0089 }
            java.lang.String r8 = "Unable to put tap time in AppLinkData.arguments"
            android.util.Log.d(r7, r8)     // Catch:{ Exception -> 0x0089 }
        L_0x00d2:
            r8 = r0
            goto L_0x00db
        L_0x00d4:
            java.lang.String r7 = a
            java.lang.String r0 = "Unable to fetch deferred applink from server"
            com.facebook.internal.Utility.logd(r7, r0)
        L_0x00db:
            r9.onDeferredAppLinkDataFetched(r8)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.AppLinkData.b(android.content.Context, java.lang.String, com.facebook.AppLinkData$CompletionHandler):void");
    }

    public static AppLinkData createFromActivity(Activity activity) {
        Validate.notNull(activity, "activity");
        Intent intent = activity.getIntent();
        if (intent == null) {
            return null;
        }
        AppLinkData a2 = a(intent);
        if (a2 == null) {
            a2 = a(intent.getStringExtra("com.facebook.platform.APPLINK_ARGS"));
        }
        if (a2 == null) {
            a2 = a(intent.getData());
        }
        return a2;
    }

    private static AppLinkData a(Intent intent) {
        Bundle bundleExtra = intent.getBundleExtra("al_applink_data");
        if (bundleExtra == null) {
            return null;
        }
        AppLinkData appLinkData = new AppLinkData();
        appLinkData.c = intent.getData();
        if (appLinkData.c == null) {
            String string = bundleExtra.getString("target_url");
            if (string != null) {
                appLinkData.c = Uri.parse(string);
            }
        }
        appLinkData.e = bundleExtra;
        appLinkData.d = null;
        Bundle bundle = bundleExtra.getBundle(ARGUMENTS_REFERER_DATA_KEY);
        if (bundle != null) {
            appLinkData.b = bundle.getString("fb_ref");
        }
        return appLinkData;
    }

    private static AppLinkData a(String str) {
        if (str == null) {
            return null;
        }
        try {
            JSONObject jSONObject = new JSONObject(str);
            String string = jSONObject.getString("version");
            if (jSONObject.getJSONObject(ServerProtocol.FALLBACK_DIALOG_PARAM_BRIDGE_ARGS).getString("method").equals("applink") && string.equals("2")) {
                AppLinkData appLinkData = new AppLinkData();
                appLinkData.d = jSONObject.getJSONObject(ServerProtocol.FALLBACK_DIALOG_PARAM_METHOD_ARGS);
                if (appLinkData.d.has("ref")) {
                    appLinkData.b = appLinkData.d.getString("ref");
                } else if (appLinkData.d.has(ARGUMENTS_REFERER_DATA_KEY)) {
                    JSONObject jSONObject2 = appLinkData.d.getJSONObject(ARGUMENTS_REFERER_DATA_KEY);
                    if (jSONObject2.has("fb_ref")) {
                        appLinkData.b = jSONObject2.getString("fb_ref");
                    }
                }
                if (appLinkData.d.has("target_url")) {
                    appLinkData.c = Uri.parse(appLinkData.d.getString("target_url"));
                }
                appLinkData.e = a(appLinkData.d);
                return appLinkData;
            }
        } catch (JSONException e2) {
            Log.d(a, "Unable to parse AppLink JSON", e2);
        } catch (FacebookException e3) {
            Log.d(a, "Unable to parse AppLink JSON", e3);
        }
        return null;
    }

    private static AppLinkData a(Uri uri) {
        if (uri == null) {
            return null;
        }
        AppLinkData appLinkData = new AppLinkData();
        appLinkData.c = uri;
        return appLinkData;
    }

    private static Bundle a(JSONObject jSONObject) {
        Bundle bundle = new Bundle();
        Iterator keys = jSONObject.keys();
        while (keys.hasNext()) {
            String str = (String) keys.next();
            Object obj = jSONObject.get(str);
            if (obj instanceof JSONObject) {
                bundle.putBundle(str, a((JSONObject) obj));
            } else if (obj instanceof JSONArray) {
                JSONArray jSONArray = (JSONArray) obj;
                int i = 0;
                if (jSONArray.length() == 0) {
                    bundle.putStringArray(str, new String[0]);
                } else {
                    Object obj2 = jSONArray.get(0);
                    if (obj2 instanceof JSONObject) {
                        Bundle[] bundleArr = new Bundle[jSONArray.length()];
                        while (i < jSONArray.length()) {
                            bundleArr[i] = a(jSONArray.getJSONObject(i));
                            i++;
                        }
                        bundle.putParcelableArray(str, bundleArr);
                    } else if (obj2 instanceof JSONArray) {
                        throw new FacebookException("Nested arrays are not supported.");
                    } else {
                        String[] strArr = new String[jSONArray.length()];
                        while (i < jSONArray.length()) {
                            strArr[i] = jSONArray.get(i).toString();
                            i++;
                        }
                        bundle.putStringArray(str, strArr);
                    }
                }
            } else {
                bundle.putString(str, obj.toString());
            }
        }
        return bundle;
    }

    private AppLinkData() {
    }

    public Uri getTargetUri() {
        return this.c;
    }

    public String getRef() {
        return this.b;
    }

    @Deprecated
    public JSONObject getArguments() {
        return this.d;
    }

    public Bundle getArgumentBundle() {
        return this.e;
    }

    public Bundle getRefererData() {
        if (this.e != null) {
            return this.e.getBundle(ARGUMENTS_REFERER_DATA_KEY);
        }
        return null;
    }
}
