package com.facebook;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.Base64;
import android.util.Log;
import com.appsflyer.AppsFlyerLib;
import com.facebook.Request.Callback;
import com.facebook.internal.AttributionIdentifiers;
import com.facebook.internal.Utility;
import com.facebook.internal.Validate;
import com.facebook.model.GraphObject;
import com.facebook.model.GraphObject.Factory;
import io.fabric.sdk.android.services.common.CommonUtils;
import java.net.HttpURLConnection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import org.json.JSONException;
import org.json.JSONObject;

public final class Settings {
    public static final String APPLICATION_ID_PROPERTY = "com.facebook.sdk.ApplicationId";
    public static final String CLIENT_TOKEN_PROPERTY = "com.facebook.sdk.ClientToken";
    private static final String a = Settings.class.getCanonicalName();
    private static final HashSet<LoggingBehavior> b = new HashSet<>(Arrays.asList(new LoggingBehavior[]{LoggingBehavior.DEVELOPER_ERRORS}));
    private static volatile Executor c = null;
    private static volatile boolean d = false;
    private static volatile String e = null;
    private static volatile String f = null;
    private static volatile String g = null;
    private static volatile boolean h = false;
    private static volatile String i = "facebook.com";
    private static AtomicLong j = new AtomicLong(PlaybackStateCompat.ACTION_PREPARE_FROM_SEARCH);
    private static volatile boolean k = false;
    private static volatile boolean l = false;
    private static final Object m = new Object();
    private static final Uri n = Uri.parse(AppsFlyerLib.ATTRIBUTION_ID_CONTENT_URI);
    private static final BlockingQueue<Runnable> o = new LinkedBlockingQueue(10);
    private static final ThreadFactory p = new ThreadFactory() {
        private final AtomicInteger a = new AtomicInteger(0);

        public Thread newThread(Runnable runnable) {
            StringBuilder sb = new StringBuilder();
            sb.append("FacebookSdk #");
            sb.append(this.a.incrementAndGet());
            return new Thread(runnable, sb.toString());
        }
    };
    private static Boolean q = Boolean.valueOf(false);

    public static String getSdkVersion() {
        return "3.21.1";
    }

    public static synchronized void sdkInitialize(Context context) {
        synchronized (Settings.class) {
            if (!q.booleanValue()) {
                a(context);
                Utility.loadAppSettingsAsync(context, getApplicationId());
                BoltsMeasurementEventListener.a(context.getApplicationContext());
                q = Boolean.valueOf(true);
            }
        }
    }

    public static final Set<LoggingBehavior> getLoggingBehaviors() {
        Set<LoggingBehavior> unmodifiableSet;
        synchronized (b) {
            unmodifiableSet = Collections.unmodifiableSet(new HashSet(b));
        }
        return unmodifiableSet;
    }

    public static final void addLoggingBehavior(LoggingBehavior loggingBehavior) {
        synchronized (b) {
            b.add(loggingBehavior);
        }
    }

    public static final void removeLoggingBehavior(LoggingBehavior loggingBehavior) {
        synchronized (b) {
            b.remove(loggingBehavior);
        }
    }

    public static final void clearLoggingBehaviors() {
        synchronized (b) {
            b.clear();
        }
    }

    public static final boolean isLoggingBehaviorEnabled(LoggingBehavior loggingBehavior) {
        boolean z;
        synchronized (b) {
            z = isDebugEnabled() && b.contains(loggingBehavior);
        }
        return z;
    }

    @Deprecated
    public static final boolean isLoggingEnabled() {
        return isDebugEnabled();
    }

    @Deprecated
    public static final void setIsLoggingEnabled(boolean z) {
        setIsDebugEnabled(z);
    }

    public static final boolean isDebugEnabled() {
        return l;
    }

    public static final void setIsDebugEnabled(boolean z) {
        l = z;
    }

    /* JADX WARNING: type inference failed for: r1v2, types: [java.util.concurrent.Executor] */
    /* JADX WARNING: type inference failed for: r1v3, types: [java.util.concurrent.Executor] */
    /* JADX WARNING: type inference failed for: r2v0, types: [java.util.concurrent.ThreadPoolExecutor] */
    /* JADX WARNING: type inference failed for: r2v1, types: [java.util.concurrent.ThreadPoolExecutor] */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r1v2, types: [java.util.concurrent.Executor]
      assigns: [java.util.concurrent.Executor, java.util.concurrent.ThreadPoolExecutor]
      uses: [?[int, boolean, OBJECT, ARRAY, byte, short, char], java.util.concurrent.Executor, java.util.concurrent.ThreadPoolExecutor]
      mth insns count: 18
    	at jadx.core.dex.visitors.typeinference.TypeSearch.fillTypeCandidates(TypeSearch.java:237)
    	at java.util.ArrayList.forEach(ArrayList.java:1249)
    	at jadx.core.dex.visitors.typeinference.TypeSearch.run(TypeSearch.java:53)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runMultiVariableSearch(TypeInferenceVisitor.java:99)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:92)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:27)
    	at jadx.core.dex.visitors.DepthTraversal.lambda$visit$1(DepthTraversal.java:14)
    	at java.util.ArrayList.forEach(ArrayList.java:1249)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
    	at jadx.core.ProcessClass.process(ProcessClass.java:30)
    	at jadx.core.ProcessClass.lambda$processDependencies$0(ProcessClass.java:49)
    	at java.util.ArrayList.forEach(ArrayList.java:1249)
    	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:49)
    	at jadx.core.ProcessClass.process(ProcessClass.java:35)
    	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:311)
    	at jadx.api.JavaClass.decompile(JavaClass.java:62)
    	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:217)
     */
    /* JADX WARNING: Unknown variable types count: 2 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.util.concurrent.Executor getExecutor() {
        /*
            java.lang.Object r0 = m
            monitor-enter(r0)
            java.util.concurrent.Executor r1 = c     // Catch:{ all -> 0x0024 }
            if (r1 != 0) goto L_0x0020
            java.util.concurrent.Executor r1 = a()     // Catch:{ all -> 0x0024 }
            if (r1 != 0) goto L_0x001e
            java.util.concurrent.ThreadPoolExecutor r1 = new java.util.concurrent.ThreadPoolExecutor     // Catch:{ all -> 0x0024 }
            r3 = 5
            r4 = 128(0x80, float:1.794E-43)
            r5 = 1
            java.util.concurrent.TimeUnit r7 = java.util.concurrent.TimeUnit.SECONDS     // Catch:{ all -> 0x0024 }
            java.util.concurrent.BlockingQueue<java.lang.Runnable> r8 = o     // Catch:{ all -> 0x0024 }
            java.util.concurrent.ThreadFactory r9 = p     // Catch:{ all -> 0x0024 }
            r2 = r1
            r2.<init>(r3, r4, r5, r7, r8, r9)     // Catch:{ all -> 0x0024 }
        L_0x001e:
            c = r1     // Catch:{ all -> 0x0024 }
        L_0x0020:
            monitor-exit(r0)     // Catch:{ all -> 0x0024 }
            java.util.concurrent.Executor r0 = c
            return r0
        L_0x0024:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x0024 }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.Settings.getExecutor():java.util.concurrent.Executor");
    }

    public static void setExecutor(Executor executor) {
        Validate.notNull(executor, "executor");
        synchronized (m) {
            c = executor;
        }
    }

    public static String getFacebookDomain() {
        return i;
    }

    public static void setFacebookDomain(String str) {
        Log.w(a, "WARNING: Calling setFacebookDomain from non-DEBUG code.");
        i = str;
    }

    private static Executor a() {
        try {
            try {
                Object obj = AsyncTask.class.getField("THREAD_POOL_EXECUTOR").get(null);
                if (obj != null && (obj instanceof Executor)) {
                    return (Executor) obj;
                }
                return null;
            } catch (IllegalAccessException unused) {
                return null;
            }
        } catch (NoSuchFieldException unused2) {
            return null;
        }
    }

    static void a(Context context, final String str, final Callback callback) {
        final Context applicationContext = context.getApplicationContext();
        getExecutor().execute(new Runnable() {
            public void run() {
                final Response a2 = Settings.a(applicationContext, str, false);
                if (callback != null) {
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        public void run() {
                            callback.onCompleted(a2);
                        }
                    });
                }
            }
        });
    }

    @Deprecated
    public static void setShouldAutoPublishInstall(boolean z) {
        d = z;
    }

    @Deprecated
    public static boolean getShouldAutoPublishInstall() {
        return d;
    }

    static Response a(Context context, String str, boolean z) {
        GraphObject graphObject;
        GraphObject graphObject2;
        Context context2 = context;
        String str2 = str;
        if (context2 == null || str2 == null) {
            throw new IllegalArgumentException("Both context and applicationId must be non-null");
        }
        try {
            AttributionIdentifiers attributionIdentifiers = AttributionIdentifiers.getAttributionIdentifiers(context);
            SharedPreferences sharedPreferences = context2.getSharedPreferences("com.facebook.sdk.attributionTracking", 0);
            StringBuilder sb = new StringBuilder();
            sb.append(str2);
            sb.append("ping");
            String sb2 = sb.toString();
            StringBuilder sb3 = new StringBuilder();
            sb3.append(str2);
            sb3.append("json");
            String sb4 = sb3.toString();
            long j2 = sharedPreferences.getLong(sb2, 0);
            String string = sharedPreferences.getString(sb4, null);
            if (!z) {
                setShouldAutoPublishInstall(false);
            }
            GraphObject create = Factory.create();
            create.setProperty("event", "MOBILE_APP_INSTALL");
            Utility.setAppEventAttributionParameters(create, attributionIdentifiers, Utility.getHashedDeviceAndAppID(context, str), getLimitEventAndDataUsage(context));
            create.setProperty("auto_publish", Boolean.valueOf(z));
            create.setProperty("application_package_name", context.getPackageName());
            Request newPostRequest = Request.newPostRequest(null, String.format("%s/activities", new Object[]{str2}), create, null);
            if (j2 != 0) {
                if (string != null) {
                    try {
                        graphObject2 = Factory.create(new JSONObject(string));
                    } catch (JSONException unused) {
                        graphObject = null;
                    }
                } else {
                    graphObject2 = null;
                }
                graphObject = graphObject2;
                if (graphObject == null) {
                    return (Response) Response.a("true", (HttpURLConnection) null, new RequestBatch(newPostRequest), true).get(0);
                }
                Response response = new Response((Request) null, (HttpURLConnection) null, (String) null, graphObject, true);
                return response;
            }
            if (attributionIdentifiers != null) {
                if (attributionIdentifiers.getAndroidAdvertiserId() != null || attributionIdentifiers.getAttributionId() != null) {
                    if (!Utility.queryAppSettings(str2, false).supportsAttribution()) {
                        throw new FacebookException("Install attribution has been disabled on the server.");
                    }
                    Response executeAndWait = newPostRequest.executeAndWait();
                    Editor edit = sharedPreferences.edit();
                    edit.putLong(sb2, System.currentTimeMillis());
                    if (!(executeAndWait.getGraphObject() == null || executeAndWait.getGraphObject().getInnerJSONObject() == null)) {
                        edit.putString(sb4, executeAndWait.getGraphObject().getInnerJSONObject().toString());
                    }
                    edit.apply();
                    return executeAndWait;
                }
            }
            throw new FacebookException("No attribution id available to send to server.");
        } catch (Exception e2) {
            Exception exc = e2;
            Utility.logd("Facebook-publish", exc);
            return new Response(null, null, new FacebookRequestError(null, exc));
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:23:0x0055  */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x005c  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String getAttributionId(android.content.ContentResolver r8) {
        /*
            r0 = 1
            r1 = 0
            java.lang.String[] r4 = new java.lang.String[r0]     // Catch:{ Exception -> 0x0037, all -> 0x0034 }
            r0 = 0
            java.lang.String r2 = "aid"
            r4[r0] = r2     // Catch:{ Exception -> 0x0037, all -> 0x0034 }
            android.net.Uri r3 = n     // Catch:{ Exception -> 0x0037, all -> 0x0034 }
            r5 = 0
            r6 = 0
            r7 = 0
            r2 = r8
            android.database.Cursor r8 = r2.query(r3, r4, r5, r6, r7)     // Catch:{ Exception -> 0x0037, all -> 0x0034 }
            if (r8 == 0) goto L_0x002e
            boolean r0 = r8.moveToFirst()     // Catch:{ Exception -> 0x002c }
            if (r0 != 0) goto L_0x001c
            goto L_0x002e
        L_0x001c:
            java.lang.String r0 = "aid"
            int r0 = r8.getColumnIndex(r0)     // Catch:{ Exception -> 0x002c }
            java.lang.String r0 = r8.getString(r0)     // Catch:{ Exception -> 0x002c }
            if (r8 == 0) goto L_0x002b
            r8.close()
        L_0x002b:
            return r0
        L_0x002c:
            r0 = move-exception
            goto L_0x0039
        L_0x002e:
            if (r8 == 0) goto L_0x0033
            r8.close()
        L_0x0033:
            return r1
        L_0x0034:
            r0 = move-exception
            r8 = r1
            goto L_0x005a
        L_0x0037:
            r0 = move-exception
            r8 = r1
        L_0x0039:
            java.lang.String r2 = a     // Catch:{ all -> 0x0059 }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x0059 }
            r3.<init>()     // Catch:{ all -> 0x0059 }
            java.lang.String r4 = "Caught unexpected exception in getAttributionId(): "
            r3.append(r4)     // Catch:{ all -> 0x0059 }
            java.lang.String r0 = r0.toString()     // Catch:{ all -> 0x0059 }
            r3.append(r0)     // Catch:{ all -> 0x0059 }
            java.lang.String r0 = r3.toString()     // Catch:{ all -> 0x0059 }
            android.util.Log.d(r2, r0)     // Catch:{ all -> 0x0059 }
            if (r8 == 0) goto L_0x0058
            r8.close()
        L_0x0058:
            return r1
        L_0x0059:
            r0 = move-exception
        L_0x005a:
            if (r8 == 0) goto L_0x005f
            r8.close()
        L_0x005f:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.Settings.getAttributionId(android.content.ContentResolver):java.lang.String");
    }

    public static String getAppVersion() {
        return e;
    }

    public static void setAppVersion(String str) {
        e = str;
    }

    public static boolean getLimitEventAndDataUsage(Context context) {
        return context.getSharedPreferences("com.facebook.sdk.appEventPreferences", 0).getBoolean("limitEventUsage", false);
    }

    public static void setLimitEventAndDataUsage(Context context, boolean z) {
        context.getSharedPreferences("com.facebook.sdk.appEventPreferences", 0).edit().putBoolean("limitEventUsage", z).apply();
    }

    public static long getOnProgressThreshold() {
        return j.get();
    }

    public static void setOnProgressThreshold(long j2) {
        j.set(j2);
    }

    public static boolean getPlatformCompatibilityEnabled() {
        return k;
    }

    public static void setPlatformCompatibilityEnabled(boolean z) {
        k = z;
    }

    public static void loadDefaultsFromMetadata(Context context) {
        h = true;
        if (context != null) {
            try {
                ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), 128);
                if (applicationInfo != null && applicationInfo.metaData != null) {
                    if (f == null) {
                        f = applicationInfo.metaData.getString(APPLICATION_ID_PROPERTY);
                    }
                    if (g == null) {
                        g = applicationInfo.metaData.getString(CLIENT_TOKEN_PROPERTY);
                    }
                }
            } catch (NameNotFoundException unused) {
            }
        }
    }

    static void a(Context context) {
        if (!h) {
            loadDefaultsFromMetadata(context);
        }
    }

    public static String getApplicationSignature(Context context) {
        if (context == null) {
            return null;
        }
        PackageManager packageManager = context.getPackageManager();
        if (packageManager == null) {
            return null;
        }
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 64);
            Signature[] signatureArr = packageInfo.signatures;
            if (signatureArr == null || signatureArr.length == 0) {
                return null;
            }
            try {
                MessageDigest instance = MessageDigest.getInstance(CommonUtils.SHA1_INSTANCE);
                instance.update(packageInfo.signatures[0].toByteArray());
                return Base64.encodeToString(instance.digest(), 9);
            } catch (NoSuchAlgorithmException unused) {
                return null;
            }
        } catch (NameNotFoundException unused2) {
            return null;
        }
    }

    public static String getApplicationId() {
        return f;
    }

    public static void setApplicationId(String str) {
        f = str;
    }

    public static String getClientToken() {
        return g;
    }

    public static void setClientToken(String str) {
        g = str;
    }
}
