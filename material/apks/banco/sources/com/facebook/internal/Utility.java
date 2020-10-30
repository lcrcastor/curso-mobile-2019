package com.facebook.internal;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.net.Uri.Builder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.Settings.Secure;
import android.support.v4.os.EnvironmentCompat;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import com.facebook.FacebookException;
import com.facebook.Request;
import com.facebook.Settings;
import com.facebook.model.GraphObject;
import io.fabric.sdk.android.services.common.CommonUtils;
import java.io.BufferedInputStream;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public final class Utility {
    public static final int DEFAULT_STREAM_BUFFER_SIZE = 8192;
    private static final String[] a = {"supports_attribution", "supports_implicit_sdk_logging", "gdpv4_nux_content", "gdpv4_nux_enabled", "android_dialog_configs"};
    private static Map<String, FetchedAppSettings> b = new ConcurrentHashMap();
    /* access modifiers changed from: private */
    public static AsyncTask<Void, Void, GraphObject> c;

    public static class DialogFeatureConfig {
        private String a;
        private String b;
        private Uri c;
        private int[] d;

        /* access modifiers changed from: private */
        public static DialogFeatureConfig b(JSONObject jSONObject) {
            String optString = jSONObject.optString("name");
            Uri uri = null;
            if (Utility.isNullOrEmpty(optString)) {
                return null;
            }
            String[] split = optString.split("\\|");
            if (split.length != 2) {
                return null;
            }
            String str = split[0];
            String str2 = split[1];
            if (Utility.isNullOrEmpty(str) || Utility.isNullOrEmpty(str2)) {
                return null;
            }
            String optString2 = jSONObject.optString("url");
            if (!Utility.isNullOrEmpty(optString2)) {
                uri = Uri.parse(optString2);
            }
            return new DialogFeatureConfig(str, str2, uri, a(jSONObject.optJSONArray("versions")));
        }

        private static int[] a(JSONArray jSONArray) {
            if (jSONArray == null) {
                return null;
            }
            int length = jSONArray.length();
            int[] iArr = new int[length];
            for (int i = 0; i < length; i++) {
                int optInt = jSONArray.optInt(i, -1);
                if (optInt == -1) {
                    String optString = jSONArray.optString(i);
                    if (!Utility.isNullOrEmpty(optString)) {
                        try {
                            optInt = Integer.parseInt(optString);
                        } catch (NumberFormatException e) {
                            Utility.logd("FacebookSDK", (Exception) e);
                            optInt = -1;
                        }
                    }
                }
                iArr[i] = optInt;
            }
            return iArr;
        }

        private DialogFeatureConfig(String str, String str2, Uri uri, int[] iArr) {
            this.a = str;
            this.b = str2;
            this.c = uri;
            this.d = iArr;
        }

        public String getDialogName() {
            return this.a;
        }

        public String getFeatureName() {
            return this.b;
        }

        public Uri getFallbackUrl() {
            return this.c;
        }

        public int[] getVersionSpec() {
            return this.d;
        }
    }

    public static class FetchedAppSettings {
        private boolean a;
        private boolean b;
        private String c;
        private boolean d;
        private Map<String, Map<String, DialogFeatureConfig>> e;

        private FetchedAppSettings(boolean z, boolean z2, String str, boolean z3, Map<String, Map<String, DialogFeatureConfig>> map) {
            this.a = z;
            this.b = z2;
            this.c = str;
            this.d = z3;
            this.e = map;
        }

        public boolean supportsAttribution() {
            return this.a;
        }

        public boolean supportsImplicitLogging() {
            return this.b;
        }

        public String getNuxContent() {
            return this.c;
        }

        public boolean getNuxEnabled() {
            return this.d;
        }

        public Map<String, Map<String, DialogFeatureConfig>> getDialogConfigurations() {
            return this.e;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:32:0x005d  */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x000e A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static int[] intersectRanges(int[] r10, int[] r11) {
        /*
            if (r10 != 0) goto L_0x0003
            return r11
        L_0x0003:
            if (r11 != 0) goto L_0x0006
            return r10
        L_0x0006:
            int r0 = r10.length
            int r1 = r11.length
            int r0 = r0 + r1
            int[] r0 = new int[r0]
            r1 = 0
            r2 = 0
            r3 = 0
        L_0x000e:
            int r4 = r10.length
            if (r1 >= r4) goto L_0x0069
            int r4 = r11.length
            if (r2 >= r4) goto L_0x0069
            r4 = r10[r1]
            r5 = r11[r2]
            int r6 = r10.length
            int r6 = r6 + -1
            r7 = 2147483647(0x7fffffff, float:NaN)
            if (r1 >= r6) goto L_0x0025
            int r6 = r1 + 1
            r6 = r10[r6]
            goto L_0x0028
        L_0x0025:
            r6 = 2147483647(0x7fffffff, float:NaN)
        L_0x0028:
            int r8 = r11.length
            int r8 = r8 + -1
            if (r2 >= r8) goto L_0x0032
            int r8 = r2 + 1
            r8 = r11[r8]
            goto L_0x0035
        L_0x0032:
            r8 = 2147483647(0x7fffffff, float:NaN)
        L_0x0035:
            r9 = -2147483648(0xffffffff80000000, float:-0.0)
            if (r4 >= r5) goto L_0x004e
            if (r6 <= r5) goto L_0x0046
            if (r6 <= r8) goto L_0x0042
            int r2 = r2 + 2
            r4 = r5
        L_0x0040:
            r6 = r8
            goto L_0x005b
        L_0x0042:
            int r1 = r1 + 2
            r4 = r5
            goto L_0x005b
        L_0x0046:
            int r1 = r1 + 2
        L_0x0048:
            r4 = -2147483648(0xffffffff80000000, float:-0.0)
            r6 = 2147483647(0x7fffffff, float:NaN)
            goto L_0x005b
        L_0x004e:
            if (r8 <= r4) goto L_0x0058
            if (r8 <= r6) goto L_0x0055
            int r1 = r1 + 2
            goto L_0x005b
        L_0x0055:
            int r2 = r2 + 2
            goto L_0x0040
        L_0x0058:
            int r2 = r2 + 2
            goto L_0x0048
        L_0x005b:
            if (r4 == r9) goto L_0x000e
            int r5 = r3 + 1
            r0[r3] = r4
            if (r6 == r7) goto L_0x0068
            int r3 = r5 + 1
            r0[r5] = r6
            goto L_0x000e
        L_0x0068:
            r3 = r5
        L_0x0069:
            int[] r10 = java.util.Arrays.copyOf(r0, r3)
            return r10
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.internal.Utility.intersectRanges(int[], int[]):int[]");
    }

    public static <T> boolean isSubset(Collection<T> collection, Collection<T> collection2) {
        boolean z = true;
        if (collection2 == null || collection2.size() == 0) {
            if (!(collection == null || collection.size() == 0)) {
                z = false;
            }
            return z;
        }
        HashSet hashSet = new HashSet(collection2);
        for (T contains : collection) {
            if (!hashSet.contains(contains)) {
                return false;
            }
        }
        return true;
    }

    public static <T> boolean isNullOrEmpty(Collection<T> collection) {
        return collection == null || collection.size() == 0;
    }

    public static boolean isNullOrEmpty(String str) {
        return str == null || str.length() == 0;
    }

    public static String coerceValueIfNullOrEmpty(String str, String str2) {
        return isNullOrEmpty(str) ? str2 : str;
    }

    public static <T> Collection<T> unmodifiableCollection(T... tArr) {
        return Collections.unmodifiableCollection(Arrays.asList(tArr));
    }

    public static <T> ArrayList<T> arrayList(T... tArr) {
        ArrayList<T> arrayList = new ArrayList<>(tArr.length);
        for (T add : tArr) {
            arrayList.add(add);
        }
        return arrayList;
    }

    static String a(String str) {
        return a(CommonUtils.MD5_INSTANCE, str);
    }

    static String b(String str) {
        return a(CommonUtils.SHA1_INSTANCE, str);
    }

    static String a(byte[] bArr) {
        return a(CommonUtils.SHA1_INSTANCE, bArr);
    }

    private static String a(String str, String str2) {
        return a(str, str2.getBytes());
    }

    private static String a(String str, byte[] bArr) {
        try {
            return a(MessageDigest.getInstance(str), bArr);
        } catch (NoSuchAlgorithmException unused) {
            return null;
        }
    }

    private static String a(MessageDigest messageDigest, byte[] bArr) {
        messageDigest.update(bArr);
        byte[] digest = messageDigest.digest();
        StringBuilder sb = new StringBuilder();
        for (byte b2 : digest) {
            sb.append(Integer.toHexString((b2 >> 4) & 15));
            sb.append(Integer.toHexString((b2 >> 0) & 15));
        }
        return sb.toString();
    }

    public static Uri buildUri(String str, String str2, Bundle bundle) {
        Builder builder = new Builder();
        builder.scheme("https");
        builder.authority(str);
        builder.path(str2);
        for (String str3 : bundle.keySet()) {
            Object obj = bundle.get(str3);
            if (obj instanceof String) {
                builder.appendQueryParameter(str3, (String) obj);
            }
        }
        return builder.build();
    }

    public static Bundle parseUrlQueryString(String str) {
        Bundle bundle = new Bundle();
        if (!isNullOrEmpty(str)) {
            for (String split : str.split("&")) {
                String[] split2 = split.split("=");
                try {
                    if (split2.length == 2) {
                        bundle.putString(URLDecoder.decode(split2[0], "UTF-8"), URLDecoder.decode(split2[1], "UTF-8"));
                    } else if (split2.length == 1) {
                        bundle.putString(URLDecoder.decode(split2[0], "UTF-8"), "");
                    }
                } catch (UnsupportedEncodingException e) {
                    logd("FacebookSDK", (Exception) e);
                }
            }
        }
        return bundle;
    }

    public static void putObjectInBundle(Bundle bundle, String str, Object obj) {
        if (obj instanceof String) {
            bundle.putString(str, (String) obj);
        } else if (obj instanceof Parcelable) {
            bundle.putParcelable(str, (Parcelable) obj);
        } else if (obj instanceof byte[]) {
            bundle.putByteArray(str, (byte[]) obj);
        } else {
            throw new FacebookException("attempted to add unsupported type to Bundle");
        }
    }

    public static void closeQuietly(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException unused) {
            }
        }
    }

    public static void disconnectQuietly(URLConnection uRLConnection) {
        if (uRLConnection instanceof HttpURLConnection) {
            ((HttpURLConnection) uRLConnection).disconnect();
        }
    }

    public static String getMetadataApplicationId(Context context) {
        Validate.notNull(context, "context");
        Settings.loadDefaultsFromMetadata(context);
        return Settings.getApplicationId();
    }

    public static Object getStringPropertyAsJSON(JSONObject jSONObject, String str, String str2) {
        Object opt = jSONObject.opt(str);
        if (opt != null && (opt instanceof String)) {
            opt = new JSONTokener((String) opt).nextValue();
        }
        if (opt == null || (opt instanceof JSONObject) || (opt instanceof JSONArray)) {
            return opt;
        }
        if (str2 != null) {
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.putOpt(str2, opt);
            return jSONObject2;
        }
        throw new FacebookException("Got an unexpected non-JSON object.");
    }

    public static String readStreamToString(InputStream inputStream) {
        InputStreamReader inputStreamReader;
        BufferedInputStream bufferedInputStream;
        Throwable th;
        try {
            bufferedInputStream = new BufferedInputStream(inputStream);
            try {
                inputStreamReader = new InputStreamReader(bufferedInputStream);
                try {
                    StringBuilder sb = new StringBuilder();
                    char[] cArr = new char[2048];
                    while (true) {
                        int read = inputStreamReader.read(cArr);
                        if (read != -1) {
                            sb.append(cArr, 0, read);
                        } else {
                            String sb2 = sb.toString();
                            closeQuietly(bufferedInputStream);
                            closeQuietly(inputStreamReader);
                            return sb2;
                        }
                    }
                } catch (Throwable th2) {
                    th = th2;
                    closeQuietly(bufferedInputStream);
                    closeQuietly(inputStreamReader);
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
                inputStreamReader = null;
                closeQuietly(bufferedInputStream);
                closeQuietly(inputStreamReader);
                throw th;
            }
        } catch (Throwable th4) {
            bufferedInputStream = null;
            th = th4;
            inputStreamReader = null;
            closeQuietly(bufferedInputStream);
            closeQuietly(inputStreamReader);
            throw th;
        }
    }

    public static boolean stringsEqualOrEmpty(String str, String str2) {
        boolean isEmpty = TextUtils.isEmpty(str);
        boolean isEmpty2 = TextUtils.isEmpty(str2);
        if (isEmpty && isEmpty2) {
            return true;
        }
        if (isEmpty || isEmpty2) {
            return false;
        }
        return str.equals(str2);
    }

    private static void a(Context context, String str) {
        CookieSyncManager.createInstance(context).sync();
        CookieManager instance = CookieManager.getInstance();
        String cookie = instance.getCookie(str);
        if (cookie != null) {
            for (String split : cookie.split(";")) {
                String[] split2 = split.split("=");
                if (split2.length > 0) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(split2[0].trim());
                    sb.append("=;expires=Sat, 1 Jan 2000 00:00:01 UTC;");
                    instance.setCookie(str, sb.toString());
                }
            }
            instance.removeExpiredCookie();
        }
    }

    public static void clearFacebookCookies(Context context) {
        a(context, "facebook.com");
        a(context, ".facebook.com");
        a(context, "https://facebook.com");
        a(context, "https://.facebook.com");
    }

    public static void logd(String str, Exception exc) {
        if (Settings.isDebugEnabled() && str != null && exc != null) {
            StringBuilder sb = new StringBuilder();
            sb.append(exc.getClass().getSimpleName());
            sb.append(": ");
            sb.append(exc.getMessage());
            Log.d(str, sb.toString());
        }
    }

    public static void logd(String str, String str2) {
        if (Settings.isDebugEnabled() && str != null && str2 != null) {
            Log.d(str, str2);
        }
    }

    public static void logd(String str, String str2, Throwable th) {
        if (Settings.isDebugEnabled() && !isNullOrEmpty(str)) {
            Log.d(str, str2, th);
        }
    }

    public static <T> boolean areObjectsEqual(T t, T t2) {
        if (t != null) {
            return t.equals(t2);
        }
        return t2 == null;
    }

    public static void loadAppSettingsAsync(final Context context, final String str) {
        JSONObject jSONObject;
        if (!isNullOrEmpty(str) && !b.containsKey(str) && c == null) {
            final String format = String.format("com.facebook.internal.APP_SETTINGS.%s", new Object[]{str});
            c = new AsyncTask<Void, Void, GraphObject>() {
                /* access modifiers changed from: protected */
                /* renamed from: a */
                public GraphObject doInBackground(Void... voidArr) {
                    return Utility.d(str);
                }

                /* access modifiers changed from: protected */
                /* renamed from: a */
                public void onPostExecute(GraphObject graphObject) {
                    if (graphObject != null) {
                        JSONObject innerJSONObject = graphObject.getInnerJSONObject();
                        Utility.b(str, innerJSONObject);
                        context.getSharedPreferences("com.facebook.internal.preferences.APP_SETTINGS", 0).edit().putString(format, innerJSONObject.toString()).apply();
                    }
                    Utility.c = null;
                }
            };
            c.execute(null);
            String string = context.getSharedPreferences("com.facebook.internal.preferences.APP_SETTINGS", 0).getString(format, null);
            if (!isNullOrEmpty(string)) {
                try {
                    jSONObject = new JSONObject(string);
                } catch (JSONException e) {
                    logd("FacebookSDK", (Exception) e);
                    jSONObject = null;
                }
                if (jSONObject != null) {
                    b(str, jSONObject);
                }
            }
        }
    }

    public static FetchedAppSettings queryAppSettings(String str, boolean z) {
        if (!z && b.containsKey(str)) {
            return (FetchedAppSettings) b.get(str);
        }
        GraphObject d = d(str);
        if (d == null) {
            return null;
        }
        return b(str, d.getInnerJSONObject());
    }

    /* access modifiers changed from: private */
    public static FetchedAppSettings b(String str, JSONObject jSONObject) {
        FetchedAppSettings fetchedAppSettings = new FetchedAppSettings(jSONObject.optBoolean("supports_attribution", false), jSONObject.optBoolean("supports_implicit_sdk_logging", false), jSONObject.optString("gdpv4_nux_content", ""), jSONObject.optBoolean("gdpv4_nux_enabled", false), a(jSONObject.optJSONObject("android_dialog_configs")));
        b.put(str, fetchedAppSettings);
        return fetchedAppSettings;
    }

    /* access modifiers changed from: private */
    public static GraphObject d(String str) {
        Bundle bundle = new Bundle();
        bundle.putString("fields", TextUtils.join(",", a));
        Request newGraphPathRequest = Request.newGraphPathRequest(null, str, null);
        newGraphPathRequest.setSkipClientToken(true);
        newGraphPathRequest.setParameters(bundle);
        return newGraphPathRequest.executeAndWait().getGraphObject();
    }

    public static DialogFeatureConfig getDialogFeatureConfig(String str, String str2, String str3) {
        if (isNullOrEmpty(str2) || isNullOrEmpty(str3)) {
            return null;
        }
        FetchedAppSettings fetchedAppSettings = (FetchedAppSettings) b.get(str);
        if (fetchedAppSettings != null) {
            Map map = (Map) fetchedAppSettings.getDialogConfigurations().get(str2);
            if (map != null) {
                return (DialogFeatureConfig) map.get(str3);
            }
        }
        return null;
    }

    private static Map<String, Map<String, DialogFeatureConfig>> a(JSONObject jSONObject) {
        HashMap hashMap = new HashMap();
        if (jSONObject != null) {
            JSONArray optJSONArray = jSONObject.optJSONArray("data");
            if (optJSONArray != null) {
                for (int i = 0; i < optJSONArray.length(); i++) {
                    DialogFeatureConfig a2 = DialogFeatureConfig.b(optJSONArray.optJSONObject(i));
                    if (a2 != null) {
                        String dialogName = a2.getDialogName();
                        Map map = (Map) hashMap.get(dialogName);
                        if (map == null) {
                            map = new HashMap();
                            hashMap.put(dialogName, map);
                        }
                        map.put(a2.getFeatureName(), a2);
                    }
                }
            }
        }
        return hashMap;
    }

    public static boolean safeGetBooleanFromResponse(GraphObject graphObject, String str) {
        Object valueOf = Boolean.valueOf(false);
        if (graphObject != null) {
            valueOf = graphObject.getProperty(str);
        }
        if (!(valueOf instanceof Boolean)) {
            valueOf = Boolean.valueOf(false);
        }
        return ((Boolean) valueOf).booleanValue();
    }

    public static String safeGetStringFromResponse(GraphObject graphObject, String str) {
        Object obj = "";
        if (graphObject != null) {
            obj = graphObject.getProperty(str);
        }
        if (!(obj instanceof String)) {
            obj = "";
        }
        return (String) obj;
    }

    public static JSONObject tryGetJSONObjectFromResponse(GraphObject graphObject, String str) {
        if (graphObject == null) {
            return null;
        }
        Object property = graphObject.getProperty(str);
        if (!(property instanceof JSONObject)) {
            return null;
        }
        return (JSONObject) property;
    }

    public static JSONArray tryGetJSONArrayFromResponse(GraphObject graphObject, String str) {
        if (graphObject == null) {
            return null;
        }
        Object property = graphObject.getProperty(str);
        if (!(property instanceof JSONArray)) {
            return null;
        }
        return (JSONArray) property;
    }

    public static void clearCaches(Context context) {
        ImageDownloader.clearCache(context);
    }

    public static void deleteDirectory(File file) {
        if (file.exists()) {
            if (file.isDirectory()) {
                for (File deleteDirectory : file.listFiles()) {
                    deleteDirectory(deleteDirectory);
                }
            }
            file.delete();
        }
    }

    public static <T> List<T> asListNoNulls(T... tArr) {
        ArrayList arrayList = new ArrayList();
        for (T t : tArr) {
            if (t != null) {
                arrayList.add(t);
            }
        }
        return arrayList;
    }

    public static String getHashedDeviceAndAppID(Context context, String str) {
        String string = Secure.getString(context.getContentResolver(), "android_id");
        if (string == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(string);
        sb.append(str);
        return b(sb.toString());
    }

    public static void setAppEventAttributionParameters(GraphObject graphObject, AttributionIdentifiers attributionIdentifiers, String str, boolean z) {
        if (!(attributionIdentifiers == null || attributionIdentifiers.getAttributionId() == null)) {
            graphObject.setProperty("attribution", attributionIdentifiers.getAttributionId());
        }
        if (attributionIdentifiers != null && attributionIdentifiers.getAndroidAdvertiserId() != null) {
            graphObject.setProperty("advertiser_id", attributionIdentifiers.getAndroidAdvertiserId());
            graphObject.setProperty("advertiser_tracking_enabled", Boolean.valueOf(!attributionIdentifiers.isTrackingLimited()));
        } else if (str != null) {
            graphObject.setProperty("advertiser_id", str);
        }
        graphObject.setProperty("application_tracking_enabled", Boolean.valueOf(!z));
    }

    public static void setAppEventExtendedDeviceInfoParameters(GraphObject graphObject, Context context) {
        String str;
        int i;
        JSONArray jSONArray = new JSONArray();
        jSONArray.put("a1");
        String packageName = context.getPackageName();
        String str2 = "";
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(packageName, 0);
            i = packageInfo.versionCode;
            try {
                str = packageInfo.versionName;
            } catch (NameNotFoundException unused) {
            }
        } catch (NameNotFoundException unused2) {
            i = -1;
            str = str2;
            jSONArray.put(packageName);
            jSONArray.put(i);
            jSONArray.put(str);
            graphObject.setProperty("extinfo", jSONArray.toString());
        }
        jSONArray.put(packageName);
        jSONArray.put(i);
        jSONArray.put(str);
        graphObject.setProperty("extinfo", jSONArray.toString());
    }

    public static Method getMethodQuietly(Class<?> cls, String str, Class<?>... clsArr) {
        try {
            return cls.getMethod(str, clsArr);
        } catch (NoSuchMethodException unused) {
            return null;
        }
    }

    public static Method getMethodQuietly(String str, String str2, Class<?>... clsArr) {
        try {
            return getMethodQuietly(Class.forName(str), str2, clsArr);
        } catch (ClassNotFoundException unused) {
            return null;
        }
    }

    public static Object invokeMethodQuietly(Object obj, Method method, Object... objArr) {
        try {
            return method.invoke(obj, objArr);
        } catch (IllegalAccessException unused) {
            return null;
        } catch (InvocationTargetException unused2) {
            return null;
        }
    }

    public static String getActivityName(Context context) {
        if (context == null) {
            return "null";
        }
        if (context == context.getApplicationContext()) {
            return EnvironmentCompat.MEDIA_UNKNOWN;
        }
        return context.getClass().getSimpleName();
    }
}
