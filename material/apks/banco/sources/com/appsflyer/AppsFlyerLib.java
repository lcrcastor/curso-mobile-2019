package com.appsflyer;

import android.app.Activity;
import android.app.Application;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Looper;
import android.os.Process;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import ar.com.santander.rio.mbanking.app.ui.Constants;
import ar.com.santander.rio.mbanking.app.ui.constants.TarjetasConstants;
import ar.com.santander.rio.mbanking.utils.UtilsCuentas;
import ar.com.santander.rio.mbanking.utils.zurich_sdk.Constants.ResultValues;
import com.appsflyer.AFLogger.LogLevel;
import com.appsflyer.AppsFlyerProperties.EmailsCryptType;
import com.appsflyer.OneLinkHttpTask.HttpsUrlConnectionProvider;
import com.appsflyer.cache.CacheManager;
import com.appsflyer.cache.RequestCacheData;
import com.google.android.gms.common.GoogleApiAvailability;
import cz.msebera.android.httpclient.cookie.ClientCookie;
import io.fabric.sdk.android.services.common.CommonUtils;
import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AppsFlyerLib implements b {
    public static final String AF_PRE_INSTALL_PATH = "AF_PRE_INSTALL_PATH";
    public static final String ATTRIBUTION_ID_COLUMN_NAME = "aid";
    public static final String ATTRIBUTION_ID_CONTENT_URI = "content://com.facebook.katana.provider.AttributionIdProvider";
    public static final String IS_STOP_TRACKING_USED = "is_stop_tracking_used";
    public static final String LOG_TAG = "AppsFlyer_4.8.11";
    public static final String PRE_INSTALL_SYSTEM_DEFAULT = "/data/local/tmp/pre_install.appsflyer";
    public static final String PRE_INSTALL_SYSTEM_DEFAULT_ETC = "/etc/pre_install.appsflyer";
    public static final String PRE_INSTALL_SYSTEM_RO_PROP = "ro.appsflyer.preinstall.path";
    static final String a = BuildConfig.AF_SDK_VERSION.substring(0, BuildConfig.AF_SDK_VERSION.indexOf("."));
    static final String b;
    static AppsFlyerInAppPurchaseValidatorListener c;
    private static final String f;
    private static String g;
    private static String h;
    private static String i;
    private static final List<String> l = Arrays.asList(new String[]{"is_cache"});
    /* access modifiers changed from: private */
    public static final List<String> m = Arrays.asList(new String[]{"googleplay", "playstore", "googleplaystore"});
    /* access modifiers changed from: private */
    public static AppsFlyerConversionListener o;
    private static AppsFlyerLib w = new AppsFlyerLib();
    private boolean A = false;
    private boolean B = false;
    private String C;
    private Map<Long, String> D;
    private boolean E;
    private boolean F;
    private t G = new t();
    private boolean H = false;
    private boolean I = false;
    String d;
    String e;
    private long j = -1;
    private long k = -1;
    private long n = TimeUnit.SECONDS.toMillis(5);
    private e p = null;
    /* access modifiers changed from: private */
    public Map<String, String> q;
    /* access modifiers changed from: private */
    public boolean r = false;
    /* access modifiers changed from: private */
    public long s;
    /* access modifiers changed from: private */
    public ScheduledExecutorService t = null;
    private long u;
    private long v;
    private a x;
    private Uri y = null;
    private long z;

    /* renamed from: com.appsflyer.AppsFlyerLib$4 reason: invalid class name */
    static /* synthetic */ class AnonymousClass4 {
        static final /* synthetic */ int[] a = new int[EmailsCryptType.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(10:0|1|2|3|4|5|6|7|8|10) */
        /* JADX WARNING: Can't wrap try/catch for region: R(8:0|1|2|3|4|5|6|(3:7|8|10)) */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0014 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001f */
        /* JADX WARNING: Missing exception handler attribute for start block: B:7:0x002a */
        static {
            /*
                com.appsflyer.AppsFlyerProperties$EmailsCryptType[] r0 = com.appsflyer.AppsFlyerProperties.EmailsCryptType.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                a = r0
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x0014 }
                com.appsflyer.AppsFlyerProperties$EmailsCryptType r1 = com.appsflyer.AppsFlyerProperties.EmailsCryptType.SHA1     // Catch:{ NoSuchFieldError -> 0x0014 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0014 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0014 }
            L_0x0014:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x001f }
                com.appsflyer.AppsFlyerProperties$EmailsCryptType r1 = com.appsflyer.AppsFlyerProperties.EmailsCryptType.MD5     // Catch:{ NoSuchFieldError -> 0x001f }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001f }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001f }
            L_0x001f:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x002a }
                com.appsflyer.AppsFlyerProperties$EmailsCryptType r1 = com.appsflyer.AppsFlyerProperties.EmailsCryptType.SHA256     // Catch:{ NoSuchFieldError -> 0x002a }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x002a }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x002a }
            L_0x002a:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x0035 }
                com.appsflyer.AppsFlyerProperties$EmailsCryptType r1 = com.appsflyer.AppsFlyerProperties.EmailsCryptType.NONE     // Catch:{ NoSuchFieldError -> 0x0035 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0035 }
                r2 = 4
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0035 }
            L_0x0035:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.appsflyer.AppsFlyerLib.AnonymousClass4.<clinit>():void");
        }
    }

    class a extends d {
        public a(Context context, String str, ScheduledExecutorService scheduledExecutorService) {
            super(context, str, scheduledExecutorService);
        }

        public final String a() {
            return ServerConfigHandler.getUrl("https://api.%s/install_data/v3/");
        }

        /* access modifiers changed from: protected */
        public final void a(Map<String, String> map) {
            map.put("is_first_launch", Boolean.toString(true));
            AppsFlyerLib.o.onInstallConversionDataLoaded(map);
            AppsFlyerLib.b((Context) this.a.get(), "appsflyerConversionDataRequestRetries", 0);
        }

        /* access modifiers changed from: protected */
        public final void a(String str, int i) {
            AppsFlyerLib.o.onInstallConversionFailure(str);
            if (i >= 400 && i < 500) {
                AppsFlyerLib.b((Context) this.a.get(), "appsflyerConversionDataRequestRetries", AppsFlyerLib.a((Context) this.a.get()).getInt("appsflyerConversionDataRequestRetries", 0) + 1);
            }
        }
    }

    class b implements Runnable {
        private final Intent a;
        private WeakReference<Context> b;
        private String c;
        private String d;
        private String e;
        private String f;
        private ExecutorService g;
        private boolean h;
        private boolean i;

        /* synthetic */ b(AppsFlyerLib appsFlyerLib, WeakReference weakReference, String str, String str2, String str3, String str4, ExecutorService executorService, boolean z, Intent intent, byte b2) {
            this(weakReference, str, str2, str3, str4, executorService, z, intent);
        }

        /* JADX WARNING: type inference failed for: r7v0, types: [boolean, java.util.concurrent.ExecutorService] */
        /* JADX WARNING: Incorrect type for immutable var: ssa=boolean, code=null, for r7v0, types: [boolean, java.util.concurrent.ExecutorService] */
        /* JADX WARNING: Unknown variable types count: 1 */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        private b(java.lang.ref.WeakReference<android.content.Context> r2, java.lang.String r3, java.lang.String r4, java.lang.String r5, java.lang.String r6, boolean r7, boolean r8, android.content.Intent r9) {
            /*
                r0 = this;
                com.appsflyer.AppsFlyerLib.this = r1
                r0.<init>()
                r0.b = r2
                r0.c = r3
                r0.d = r4
                r0.e = r5
                r0.f = r6
                r1 = 1
                r0.h = r1
                r0.g = r7
                r0.i = r8
                r0.a = r9
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.appsflyer.AppsFlyerLib.b.<init>(com.appsflyer.AppsFlyerLib, java.lang.ref.WeakReference, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.util.concurrent.ExecutorService, boolean, android.content.Intent):void");
        }

        public final void run() {
            AppsFlyerLib.a(AppsFlyerLib.this, (Context) this.b.get(), this.c, this.d, this.e, this.f, this.h, this.i, this.a);
        }
    }

    class c implements Runnable {
        private WeakReference<Context> a = null;

        public c(Context context) {
            this.a = new WeakReference<>(context);
        }

        public final void run() {
            if (!AppsFlyerLib.this.r) {
                AppsFlyerLib.this.s = System.currentTimeMillis();
                if (this.a != null) {
                    AppsFlyerLib.this.r = true;
                    try {
                        String b2 = AppsFlyerProperties.getInstance().getString(AppsFlyerProperties.AF_KEY);
                        synchronized (this.a) {
                            for (RequestCacheData requestCacheData : CacheManager.getInstance().getCachedRequests((Context) this.a.get())) {
                                StringBuilder sb = new StringBuilder("resending request: ");
                                sb.append(requestCacheData.getRequestURL());
                                AFLogger.afInfoLog(sb.toString());
                                try {
                                    long currentTimeMillis = System.currentTimeMillis();
                                    long parseLong = Long.parseLong(requestCacheData.getCacheKey(), 10);
                                    AppsFlyerLib appsFlyerLib = AppsFlyerLib.this;
                                    StringBuilder sb2 = new StringBuilder();
                                    sb2.append(requestCacheData.getRequestURL());
                                    sb2.append("&isCachedRequest=true&timeincache=");
                                    sb2.append(Long.toString((currentTimeMillis - parseLong) / 1000));
                                    AppsFlyerLib.a(appsFlyerLib, sb2.toString(), requestCacheData.getPostData(), b2, this.a, requestCacheData.getCacheKey(), false);
                                } catch (Exception e) {
                                    AFLogger.afErrorLog("Failed to resend cached request", e);
                                }
                            }
                        }
                    } catch (Exception e2) {
                        try {
                            AFLogger.afErrorLog("failed to check cache. ", e2);
                        } catch (Throwable th) {
                            AppsFlyerLib.this.r = false;
                            throw th;
                        }
                    }
                    AppsFlyerLib.this.r = false;
                    AppsFlyerLib.this.t.shutdown();
                    AppsFlyerLib.this.t = null;
                }
            }
        }
    }

    abstract class d implements Runnable {
        WeakReference<Context> a = null;
        private String b;
        private ScheduledExecutorService c;
        private AtomicInteger d = new AtomicInteger(0);

        public abstract String a();

        /* access modifiers changed from: protected */
        public abstract void a(String str, int i);

        /* access modifiers changed from: protected */
        public abstract void a(Map<String, String> map);

        d(Context context, String str, ScheduledExecutorService scheduledExecutorService) {
            this.a = new WeakReference<>(context);
            this.b = str;
            if (scheduledExecutorService == null) {
                this.c = AFExecutor.getInstance().a();
            } else {
                this.c = scheduledExecutorService;
            }
        }

        /* JADX WARNING: Removed duplicated region for block: B:74:0x0233 A[Catch:{ all -> 0x0229 }] */
        /* JADX WARNING: Removed duplicated region for block: B:78:0x0248  */
        /* JADX WARNING: Removed duplicated region for block: B:83:0x0258  */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void run() {
            /*
                r13 = this;
                java.lang.String r0 = r13.b
                if (r0 == 0) goto L_0x025c
                java.lang.String r0 = r13.b
                int r0 = r0.length()
                if (r0 != 0) goto L_0x000e
                goto L_0x025c
            L_0x000e:
                com.appsflyer.AppsFlyerLib r0 = com.appsflyer.AppsFlyerLib.this
                boolean r0 = r0.isTrackingStopped()
                if (r0 == 0) goto L_0x0017
                return
            L_0x0017:
                java.util.concurrent.atomic.AtomicInteger r0 = r13.d
                r0.incrementAndGet()
                r0 = 0
                r1 = 0
                java.lang.ref.WeakReference<android.content.Context> r2 = r13.a     // Catch:{ Throwable -> 0x022c }
                java.lang.Object r2 = r2.get()     // Catch:{ Throwable -> 0x022c }
                android.content.Context r2 = (android.content.Context) r2     // Catch:{ Throwable -> 0x022c }
                if (r2 != 0) goto L_0x002e
                java.util.concurrent.atomic.AtomicInteger r0 = r13.d
                r0.decrementAndGet()
                return
            L_0x002e:
                long r3 = java.lang.System.currentTimeMillis()     // Catch:{ Throwable -> 0x022c }
                java.lang.ref.WeakReference r5 = new java.lang.ref.WeakReference     // Catch:{ Throwable -> 0x022c }
                r5.<init>(r2)     // Catch:{ Throwable -> 0x022c }
                java.lang.String r5 = com.appsflyer.AppsFlyerLib.b(r5)     // Catch:{ Throwable -> 0x022c }
                java.lang.String r5 = com.appsflyer.AppsFlyerLib.e(r2, r5)     // Catch:{ Throwable -> 0x022c }
                java.lang.String r6 = ""
                r7 = 1
                if (r5 == 0) goto L_0x006a
                java.util.List r8 = com.appsflyer.AppsFlyerLib.m     // Catch:{ Throwable -> 0x022c }
                java.lang.String r9 = r5.toLowerCase()     // Catch:{ Throwable -> 0x022c }
                boolean r8 = r8.contains(r9)     // Catch:{ Throwable -> 0x022c }
                if (r8 != 0) goto L_0x005d
                java.lang.String r6 = "-"
                java.lang.String r5 = java.lang.String.valueOf(r5)     // Catch:{ Throwable -> 0x022c }
                java.lang.String r6 = r6.concat(r5)     // Catch:{ Throwable -> 0x022c }
                goto L_0x006a
            L_0x005d:
                java.lang.String r8 = "AF detected using redundant Google-Play channel for attribution - %s. Using without channel postfix."
                java.lang.Object[] r9 = new java.lang.Object[r7]     // Catch:{ Throwable -> 0x022c }
                r9[r1] = r5     // Catch:{ Throwable -> 0x022c }
                java.lang.String r5 = java.lang.String.format(r8, r9)     // Catch:{ Throwable -> 0x022c }
                com.appsflyer.AFLogger.afWarnLog(r5)     // Catch:{ Throwable -> 0x022c }
            L_0x006a:
                java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x022c }
                r5.<init>()     // Catch:{ Throwable -> 0x022c }
                java.lang.String r8 = r13.a()     // Catch:{ Throwable -> 0x022c }
                r5.append(r8)     // Catch:{ Throwable -> 0x022c }
                java.lang.String r8 = r2.getPackageName()     // Catch:{ Throwable -> 0x022c }
                r5.append(r8)     // Catch:{ Throwable -> 0x022c }
                r5.append(r6)     // Catch:{ Throwable -> 0x022c }
                java.lang.String r6 = "?devkey="
                r5.append(r6)     // Catch:{ Throwable -> 0x022c }
                java.lang.String r6 = r13.b     // Catch:{ Throwable -> 0x022c }
                r5.append(r6)     // Catch:{ Throwable -> 0x022c }
                java.lang.String r6 = "&device_id="
                r5.append(r6)     // Catch:{ Throwable -> 0x022c }
                java.lang.ref.WeakReference r6 = new java.lang.ref.WeakReference     // Catch:{ Throwable -> 0x022c }
                r6.<init>(r2)     // Catch:{ Throwable -> 0x022c }
                java.lang.String r6 = com.appsflyer.p.a(r6)     // Catch:{ Throwable -> 0x022c }
                r5.append(r6)     // Catch:{ Throwable -> 0x022c }
                com.appsflyer.y r6 = com.appsflyer.y.a()     // Catch:{ Throwable -> 0x022c }
                java.lang.String r8 = r5.toString()     // Catch:{ Throwable -> 0x022c }
                java.lang.String r9 = ""
                r6.a(r8, r9)     // Catch:{ Throwable -> 0x022c }
                java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x022c }
                java.lang.String r8 = "Calling server for attribution url: "
                r6.<init>(r8)     // Catch:{ Throwable -> 0x022c }
                java.lang.String r8 = r5.toString()     // Catch:{ Throwable -> 0x022c }
                r6.append(r8)     // Catch:{ Throwable -> 0x022c }
                java.lang.String r6 = r6.toString()     // Catch:{ Throwable -> 0x022c }
                com.appsflyer.f.AnonymousClass5.b(r6)     // Catch:{ Throwable -> 0x022c }
                java.net.URL r6 = new java.net.URL     // Catch:{ Throwable -> 0x022c }
                java.lang.String r8 = r5.toString()     // Catch:{ Throwable -> 0x022c }
                r6.<init>(r8)     // Catch:{ Throwable -> 0x022c }
                java.net.URLConnection r6 = r6.openConnection()     // Catch:{ Throwable -> 0x022c }
                java.net.HttpURLConnection r6 = (java.net.HttpURLConnection) r6     // Catch:{ Throwable -> 0x022c }
                java.lang.String r0 = "GET"
                r6.setRequestMethod(r0)     // Catch:{ Throwable -> 0x0226, all -> 0x0224 }
                r0 = 10000(0x2710, float:1.4013E-41)
                r6.setConnectTimeout(r0)     // Catch:{ Throwable -> 0x0226, all -> 0x0224 }
                java.lang.String r0 = "Connection"
                java.lang.String r8 = "close"
                r6.setRequestProperty(r0, r8)     // Catch:{ Throwable -> 0x0226, all -> 0x0224 }
                r6.connect()     // Catch:{ Throwable -> 0x0226, all -> 0x0224 }
                int r0 = r6.getResponseCode()     // Catch:{ Throwable -> 0x0226, all -> 0x0224 }
                java.lang.String r8 = com.appsflyer.AppsFlyerLib.a(r6)     // Catch:{ Throwable -> 0x0226, all -> 0x0224 }
                com.appsflyer.y r9 = com.appsflyer.y.a()     // Catch:{ Throwable -> 0x0226, all -> 0x0224 }
                java.lang.String r10 = r5.toString()     // Catch:{ Throwable -> 0x0226, all -> 0x0224 }
                r9.a(r10, r0, r8)     // Catch:{ Throwable -> 0x0226, all -> 0x0224 }
                r9 = 200(0xc8, float:2.8E-43)
                if (r0 != r9) goto L_0x01ed
                long r9 = java.lang.System.currentTimeMillis()     // Catch:{ Throwable -> 0x0226, all -> 0x0224 }
                java.lang.String r0 = "appsflyerGetConversionDataTiming"
                r5 = 0
                long r11 = r9 - r3
                r3 = 1000(0x3e8, double:4.94E-321)
                long r11 = r11 / r3
                com.appsflyer.AppsFlyerLib.b(r2, r0, r11)     // Catch:{ Throwable -> 0x0226, all -> 0x0224 }
                java.lang.String r0 = "Attribution data: "
                java.lang.String r3 = java.lang.String.valueOf(r8)     // Catch:{ Throwable -> 0x0226, all -> 0x0224 }
                java.lang.String r0 = r0.concat(r3)     // Catch:{ Throwable -> 0x0226, all -> 0x0224 }
                com.appsflyer.f.AnonymousClass5.b(r0)     // Catch:{ Throwable -> 0x0226, all -> 0x0224 }
                int r0 = r8.length()     // Catch:{ Throwable -> 0x0226, all -> 0x0224 }
                if (r0 <= 0) goto L_0x0219
                if (r2 == 0) goto L_0x0219
                java.util.Map r0 = com.appsflyer.AppsFlyerLib.c(r8)     // Catch:{ Throwable -> 0x0226, all -> 0x0224 }
                java.lang.String r3 = "iscache"
                java.lang.Object r3 = r0.get(r3)     // Catch:{ Throwable -> 0x0226, all -> 0x0224 }
                java.lang.String r3 = (java.lang.String) r3     // Catch:{ Throwable -> 0x0226, all -> 0x0224 }
                if (r3 == 0) goto L_0x013c
                java.lang.String r4 = java.lang.Boolean.toString(r1)     // Catch:{ Throwable -> 0x0226, all -> 0x0224 }
                boolean r4 = r4.equals(r3)     // Catch:{ Throwable -> 0x0226, all -> 0x0224 }
                if (r4 == 0) goto L_0x013c
                java.lang.String r4 = "appsflyerConversionDataCacheExpiration"
                long r9 = java.lang.System.currentTimeMillis()     // Catch:{ Throwable -> 0x0226, all -> 0x0224 }
                com.appsflyer.AppsFlyerLib.b(r2, r4, r9)     // Catch:{ Throwable -> 0x0226, all -> 0x0224 }
            L_0x013c:
                java.lang.String r4 = "af_siteid"
                boolean r4 = r0.containsKey(r4)     // Catch:{ Throwable -> 0x0226, all -> 0x0224 }
                if (r4 == 0) goto L_0x0179
                java.lang.String r4 = "af_channel"
                boolean r4 = r0.containsKey(r4)     // Catch:{ Throwable -> 0x0226, all -> 0x0224 }
                if (r4 == 0) goto L_0x0166
                java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0226, all -> 0x0224 }
                java.lang.String r5 = "[Invite] Detected App-Invite via channel: "
                r4.<init>(r5)     // Catch:{ Throwable -> 0x0226, all -> 0x0224 }
                java.lang.String r5 = "af_channel"
                java.lang.Object r5 = r0.get(r5)     // Catch:{ Throwable -> 0x0226, all -> 0x0224 }
                java.lang.String r5 = (java.lang.String) r5     // Catch:{ Throwable -> 0x0226, all -> 0x0224 }
                r4.append(r5)     // Catch:{ Throwable -> 0x0226, all -> 0x0224 }
                java.lang.String r4 = r4.toString()     // Catch:{ Throwable -> 0x0226, all -> 0x0224 }
                com.appsflyer.AFLogger.afDebugLog(r4)     // Catch:{ Throwable -> 0x0226, all -> 0x0224 }
                goto L_0x0179
            L_0x0166:
                java.lang.String r4 = "[CrossPromotion] App was installed via %s's Cross Promotion"
                java.lang.Object[] r5 = new java.lang.Object[r7]     // Catch:{ Throwable -> 0x0226, all -> 0x0224 }
                java.lang.String r9 = "af_siteid"
                java.lang.Object r9 = r0.get(r9)     // Catch:{ Throwable -> 0x0226, all -> 0x0224 }
                r5[r1] = r9     // Catch:{ Throwable -> 0x0226, all -> 0x0224 }
                java.lang.String r4 = java.lang.String.format(r4, r5)     // Catch:{ Throwable -> 0x0226, all -> 0x0224 }
                com.appsflyer.AFLogger.afDebugLog(r4)     // Catch:{ Throwable -> 0x0226, all -> 0x0224 }
            L_0x0179:
                java.lang.String r4 = "af_siteid"
                boolean r4 = r0.containsKey(r4)     // Catch:{ Throwable -> 0x0226, all -> 0x0224 }
                if (r4 == 0) goto L_0x019a
                java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0226, all -> 0x0224 }
                java.lang.String r5 = "[Invite] Detected App-Invite via channel: "
                r4.<init>(r5)     // Catch:{ Throwable -> 0x0226, all -> 0x0224 }
                java.lang.String r5 = "af_channel"
                java.lang.Object r5 = r0.get(r5)     // Catch:{ Throwable -> 0x0226, all -> 0x0224 }
                java.lang.String r5 = (java.lang.String) r5     // Catch:{ Throwable -> 0x0226, all -> 0x0224 }
                r4.append(r5)     // Catch:{ Throwable -> 0x0226, all -> 0x0224 }
                java.lang.String r4 = r4.toString()     // Catch:{ Throwable -> 0x0226, all -> 0x0224 }
                com.appsflyer.AFLogger.afDebugLog(r4)     // Catch:{ Throwable -> 0x0226, all -> 0x0224 }
            L_0x019a:
                java.lang.String r4 = "is_first_launch"
                java.lang.String r5 = java.lang.Boolean.toString(r1)     // Catch:{ Throwable -> 0x0226, all -> 0x0224 }
                r0.put(r4, r5)     // Catch:{ Throwable -> 0x0226, all -> 0x0224 }
                org.json.JSONObject r4 = new org.json.JSONObject     // Catch:{ Throwable -> 0x0226, all -> 0x0224 }
                r4.<init>(r0)     // Catch:{ Throwable -> 0x0226, all -> 0x0224 }
                java.lang.String r4 = r4.toString()     // Catch:{ Throwable -> 0x0226, all -> 0x0224 }
                if (r4 == 0) goto L_0x01b4
                java.lang.String r5 = "attributionId"
                com.appsflyer.AppsFlyerLib.b(r2, r5, r4)     // Catch:{ Throwable -> 0x0226, all -> 0x0224 }
                goto L_0x01b9
            L_0x01b4:
                java.lang.String r4 = "attributionId"
                com.appsflyer.AppsFlyerLib.b(r2, r4, r8)     // Catch:{ Throwable -> 0x0226, all -> 0x0224 }
            L_0x01b9:
                java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0226, all -> 0x0224 }
                java.lang.String r5 = "iscache="
                r4.<init>(r5)     // Catch:{ Throwable -> 0x0226, all -> 0x0224 }
                r4.append(r3)     // Catch:{ Throwable -> 0x0226, all -> 0x0224 }
                java.lang.String r3 = " caching conversion data"
                r4.append(r3)     // Catch:{ Throwable -> 0x0226, all -> 0x0224 }
                java.lang.String r3 = r4.toString()     // Catch:{ Throwable -> 0x0226, all -> 0x0224 }
                com.appsflyer.AFLogger.afDebugLog(r3)     // Catch:{ Throwable -> 0x0226, all -> 0x0224 }
                com.appsflyer.AppsFlyerConversionListener r3 = com.appsflyer.AppsFlyerLib.o     // Catch:{ Throwable -> 0x0226, all -> 0x0224 }
                if (r3 == 0) goto L_0x0219
                java.util.concurrent.atomic.AtomicInteger r3 = r13.d     // Catch:{ Throwable -> 0x0226, all -> 0x0224 }
                int r3 = r3.intValue()     // Catch:{ Throwable -> 0x0226, all -> 0x0224 }
                if (r3 > r7) goto L_0x0219
                java.util.Map r2 = com.appsflyer.AppsFlyerLib.d(r2)     // Catch:{ k -> 0x01e3 }
                r0 = r2
                goto L_0x01e9
            L_0x01e3:
                r2 = move-exception
                java.lang.String r3 = "Exception while trying to fetch attribution data. "
                com.appsflyer.AFLogger.afErrorLog(r3, r2)     // Catch:{ Throwable -> 0x0226, all -> 0x0224 }
            L_0x01e9:
                r13.a(r0)     // Catch:{ Throwable -> 0x0226, all -> 0x0224 }
                goto L_0x0219
            L_0x01ed:
                com.appsflyer.AppsFlyerConversionListener r2 = com.appsflyer.AppsFlyerLib.o     // Catch:{ Throwable -> 0x0226, all -> 0x0224 }
                if (r2 == 0) goto L_0x0200
                java.lang.String r2 = "Error connection to server: "
                java.lang.String r3 = java.lang.String.valueOf(r0)     // Catch:{ Throwable -> 0x0226, all -> 0x0224 }
                java.lang.String r2 = r2.concat(r3)     // Catch:{ Throwable -> 0x0226, all -> 0x0224 }
                r13.a(r2, r0)     // Catch:{ Throwable -> 0x0226, all -> 0x0224 }
            L_0x0200:
                java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0226, all -> 0x0224 }
                java.lang.String r3 = "AttributionIdFetcher response code: "
                r2.<init>(r3)     // Catch:{ Throwable -> 0x0226, all -> 0x0224 }
                r2.append(r0)     // Catch:{ Throwable -> 0x0226, all -> 0x0224 }
                java.lang.String r0 = "  url: "
                r2.append(r0)     // Catch:{ Throwable -> 0x0226, all -> 0x0224 }
                r2.append(r5)     // Catch:{ Throwable -> 0x0226, all -> 0x0224 }
                java.lang.String r0 = r2.toString()     // Catch:{ Throwable -> 0x0226, all -> 0x0224 }
                com.appsflyer.f.AnonymousClass5.b(r0)     // Catch:{ Throwable -> 0x0226, all -> 0x0224 }
            L_0x0219:
                java.util.concurrent.atomic.AtomicInteger r0 = r13.d
                r0.decrementAndGet()
                if (r6 == 0) goto L_0x024b
                r6.disconnect()
                goto L_0x024b
            L_0x0224:
                r1 = move-exception
                goto L_0x0251
            L_0x0226:
                r2 = move-exception
                r0 = r6
                goto L_0x022d
            L_0x0229:
                r1 = move-exception
                r6 = r0
                goto L_0x0251
            L_0x022c:
                r2 = move-exception
            L_0x022d:
                com.appsflyer.AppsFlyerConversionListener r3 = com.appsflyer.AppsFlyerLib.o     // Catch:{ all -> 0x0229 }
                if (r3 == 0) goto L_0x023a
                java.lang.String r3 = r2.getMessage()     // Catch:{ all -> 0x0229 }
                r13.a(r3, r1)     // Catch:{ all -> 0x0229 }
            L_0x023a:
                java.lang.String r1 = r2.getMessage()     // Catch:{ all -> 0x0229 }
                com.appsflyer.AFLogger.afErrorLog(r1, r2)     // Catch:{ all -> 0x0229 }
                java.util.concurrent.atomic.AtomicInteger r1 = r13.d
                r1.decrementAndGet()
                if (r0 == 0) goto L_0x024b
                r0.disconnect()
            L_0x024b:
                java.util.concurrent.ScheduledExecutorService r0 = r13.c
                r0.shutdown()
                return
            L_0x0251:
                java.util.concurrent.atomic.AtomicInteger r0 = r13.d
                r0.decrementAndGet()
                if (r6 == 0) goto L_0x025b
                r6.disconnect()
            L_0x025b:
                throw r1
            L_0x025c:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.appsflyer.AppsFlyerLib.d.run():void");
        }
    }

    class e implements Runnable {
        private String a;
        private WeakReference<Context> b;
        private Map<String, Object> c;
        private boolean d;
        private int e;

        /* synthetic */ e(AppsFlyerLib appsFlyerLib, String str, Map map, Context context, boolean z, int i, byte b2) {
            this(str, map, context, z, i);
        }

        private e(String str, Map<String, Object> map, Context context, boolean z, int i) {
            this.b = null;
            this.a = str;
            this.c = map;
            this.b = new WeakReference<>(context);
            this.d = z;
            this.e = i;
        }

        /* JADX WARNING: Code restructure failed: missing block: B:15:0x004b, code lost:
            r0 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:16:0x004c, code lost:
            r9 = r1;
            r1 = r0;
            r0 = r9;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:17:0x0050, code lost:
            r0 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:18:0x0051, code lost:
            com.appsflyer.AFLogger.afErrorLog(r0.getMessage(), r0);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:19:0x0058, code lost:
            return;
         */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Removed duplicated region for block: B:17:0x0050 A[ExcHandler: Throwable (r0v8 'th' java.lang.Throwable A[CUSTOM_DECLARE]), Splitter:B:10:0x0028] */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final void run() {
            /*
                r10 = this;
                com.appsflyer.AppsFlyerLib r0 = com.appsflyer.AppsFlyerLib.this
                boolean r0 = r0.isTrackingStopped()
                if (r0 == 0) goto L_0x0009
                return
            L_0x0009:
                r0 = 0
                boolean r1 = r10.d
                if (r1 == 0) goto L_0x0028
                int r1 = r10.e
                r2 = 2
                if (r1 > r2) goto L_0x0028
                com.appsflyer.AppsFlyerLib r1 = com.appsflyer.AppsFlyerLib.this
                boolean r1 = com.appsflyer.AppsFlyerLib.a(r1)
                if (r1 == 0) goto L_0x0028
                java.util.Map<java.lang.String, java.lang.Object> r1 = r10.c
                java.lang.String r2 = "rfr"
                com.appsflyer.AppsFlyerLib r3 = com.appsflyer.AppsFlyerLib.this
                java.util.Map r3 = r3.q
                r1.put(r2, r3)
            L_0x0028:
                java.util.Map<java.lang.String, java.lang.Object> r1 = r10.c     // Catch:{ IOException -> 0x0059, Throwable -> 0x0050 }
                java.lang.String r2 = "appsflyerKey"
                java.lang.Object r1 = r1.get(r2)     // Catch:{ IOException -> 0x0059, Throwable -> 0x0050 }
                r5 = r1
                java.lang.String r5 = (java.lang.String) r5     // Catch:{ IOException -> 0x0059, Throwable -> 0x0050 }
                java.util.Map<java.lang.String, java.lang.Object> r1 = r10.c     // Catch:{ IOException -> 0x0059, Throwable -> 0x0050 }
                org.json.JSONObject r1 = com.appsflyer.AFHelper.convertToJsonObject(r1)     // Catch:{ IOException -> 0x0059, Throwable -> 0x0050 }
                java.lang.String r1 = r1.toString()     // Catch:{ IOException -> 0x0059, Throwable -> 0x0050 }
                com.appsflyer.AppsFlyerLib r2 = com.appsflyer.AppsFlyerLib.this     // Catch:{ IOException -> 0x004b, Throwable -> 0x0050 }
                java.lang.String r3 = r10.a     // Catch:{ IOException -> 0x004b, Throwable -> 0x0050 }
                java.lang.ref.WeakReference<android.content.Context> r6 = r10.b     // Catch:{ IOException -> 0x004b, Throwable -> 0x0050 }
                r7 = 0
                boolean r8 = r10.d     // Catch:{ IOException -> 0x004b, Throwable -> 0x0050 }
                r4 = r1
                com.appsflyer.AppsFlyerLib.a(r2, r3, r4, r5, r6, r7, r8)     // Catch:{ IOException -> 0x004b, Throwable -> 0x0050 }
                return
            L_0x004b:
                r0 = move-exception
                r9 = r1
                r1 = r0
                r0 = r9
                goto L_0x005a
            L_0x0050:
                r0 = move-exception
                java.lang.String r1 = r0.getMessage()
                com.appsflyer.AFLogger.afErrorLog(r1, r0)
                return
            L_0x0059:
                r1 = move-exception
            L_0x005a:
                java.lang.String r2 = "Exception while sending request to server. "
                com.appsflyer.AFLogger.afErrorLog(r2, r1)
                if (r0 == 0) goto L_0x008e
                java.lang.ref.WeakReference<android.content.Context> r2 = r10.b
                if (r2 == 0) goto L_0x008e
                java.lang.String r2 = r10.a
                java.lang.String r3 = "&isCachedRequest=true&timeincache="
                boolean r2 = r2.contains(r3)
                if (r2 != 0) goto L_0x008e
                com.appsflyer.cache.CacheManager r2 = com.appsflyer.cache.CacheManager.getInstance()
                com.appsflyer.cache.RequestCacheData r3 = new com.appsflyer.cache.RequestCacheData
                java.lang.String r4 = r10.a
                java.lang.String r5 = "4.8.11"
                r3.<init>(r4, r0, r5)
                java.lang.ref.WeakReference<android.content.Context> r0 = r10.b
                java.lang.Object r0 = r0.get()
                android.content.Context r0 = (android.content.Context) r0
                r2.cacheRequest(r3, r0)
                java.lang.String r0 = r1.getMessage()
                com.appsflyer.AFLogger.afErrorLog(r0, r1)
            L_0x008e:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.appsflyer.AppsFlyerLib.e.run():void");
        }
    }

    static /* synthetic */ void a(Map map) {
        if (o != null) {
            try {
                o.onAppOpenAttribution(map);
            } catch (Throwable th) {
                AFLogger.afErrorLog(th.getLocalizedMessage(), th);
            }
        }
    }

    static /* synthetic */ String b(WeakReference weakReference) {
        String string = AppsFlyerProperties.getInstance().getString(AppsFlyerProperties.CHANNEL);
        return string == null ? a(weakReference, "CHANNEL") : string;
    }

    static /* synthetic */ void a(AppsFlyerLib appsFlyerLib, Context context, String str, String str2, String str3, String str4, boolean z2, boolean z3, Intent intent) {
        AppsFlyerLib appsFlyerLib2 = appsFlyerLib;
        Context context2 = context;
        if (context2 == null) {
            AFLogger.afDebugLog("sendTrackingWithEvent - got null context. skipping event/launch.");
            return;
        }
        boolean z4 = false;
        SharedPreferences sharedPreferences = context2.getSharedPreferences("appsflyer-data", 0);
        AppsFlyerProperties.getInstance().saveProperties(sharedPreferences);
        if (!appsFlyerLib.isTrackingStopped()) {
            StringBuilder sb = new StringBuilder("sendTrackingWithEvent from activity: ");
            sb.append(context.getClass().getName());
            AFLogger.afInfoLog(sb.toString());
        }
        boolean z5 = str2 == null;
        Map a2 = appsFlyerLib2.a(context2, str, str2, str3, str4, z2, sharedPreferences, z5, intent);
        String str5 = (String) a2.get("appsflyerKey");
        if (str5 == null || str5.length() == 0) {
            AFLogger.afDebugLog("Not sending data yet, waiting for dev key");
            return;
        }
        if (!appsFlyerLib.isTrackingStopped()) {
            AFLogger.afInfoLog("AppsFlyerLib.sendTrackingWithEvent");
        }
        String str6 = z5 ? z3 ? g : h : i;
        String url = ServerConfigHandler.getUrl(str6);
        StringBuilder sb2 = new StringBuilder();
        sb2.append(url);
        sb2.append(context.getPackageName());
        e eVar = new e(appsFlyerLib2, sb2.toString(), a2, context.getApplicationContext(), z5, a(sharedPreferences, "appsFlyerCount", false), 0);
        if (z5 && c(context)) {
            if (appsFlyerLib2.q != null && appsFlyerLib2.q.size() > 0) {
                z4 = true;
            }
            if (!z4) {
                AFLogger.afDebugLog("Failed to get new referrer, wait ...");
                a((ScheduledExecutorService) AFExecutor.getInstance().a(), (Runnable) eVar, 500, TimeUnit.MILLISECONDS);
                return;
            }
        }
        eVar.run();
    }

    static /* synthetic */ void a(AppsFlyerLib appsFlyerLib, String str, String str2, String str3, WeakReference weakReference, String str4, boolean z2) {
        URL url = new URL(str);
        StringBuilder sb = new StringBuilder("url: ");
        sb.append(url.toString());
        AFLogger.afInfoLog(sb.toString());
        AnonymousClass5.b("data: ".concat(String.valueOf(str2)));
        a((Context) weakReference.get(), LOG_TAG, "EVENT_DATA", str2);
        try {
            appsFlyerLib.a(url, str2, str3, weakReference, str4, z2);
        } catch (IOException e2) {
            AFLogger.afErrorLog("Exception in sendRequestToServer. ", e2);
            if (AppsFlyerProperties.getInstance().getBoolean(AppsFlyerProperties.USE_HTTP_FALLBACK, false)) {
                appsFlyerLib.a(new URL(str.replace("https:", "http:")), str2, str3, weakReference, str4, z2);
                return;
            }
            StringBuilder sb2 = new StringBuilder("failed to send requeset to server. ");
            sb2.append(e2.getLocalizedMessage());
            AFLogger.afInfoLog(sb2.toString());
            a((Context) weakReference.get(), LOG_TAG, ResultValues.ERROR, e2.getLocalizedMessage());
            throw e2;
        }
    }

    static /* synthetic */ boolean a(AppsFlyerLib appsFlyerLib) {
        return appsFlyerLib.q != null && appsFlyerLib.q.size() > 0;
    }

    static {
        StringBuilder sb = new StringBuilder();
        sb.append(a);
        sb.append("/androidevent?buildnumber=4.8.11&app_id=");
        f = sb.toString();
        StringBuilder sb2 = new StringBuilder("https://attr.%s/api/v");
        sb2.append(f);
        g = sb2.toString();
        StringBuilder sb3 = new StringBuilder("https://t.%s/api/v");
        sb3.append(f);
        h = sb3.toString();
        StringBuilder sb4 = new StringBuilder("https://events.%s/api/v");
        sb4.append(f);
        i = sb4.toString();
        StringBuilder sb5 = new StringBuilder("https://register.%s/api/v");
        sb5.append(f);
        b = sb5.toString();
    }

    /* access modifiers changed from: 0000 */
    public final void a() {
        this.u = System.currentTimeMillis();
    }

    /* access modifiers changed from: 0000 */
    public final void b() {
        this.v = System.currentTimeMillis();
    }

    /* access modifiers changed from: 0000 */
    public final void a(Context context, Intent intent) {
        String stringExtra = intent.getStringExtra(AppsFlyerProperties.IS_MONITOR);
        if (stringExtra != null) {
            AFLogger.afInfoLog("Turning on monitoring.");
            AppsFlyerProperties.getInstance().set(AppsFlyerProperties.IS_MONITOR, stringExtra.equals("true"));
            a(context, (String) null, "START_TRACKING", context.getPackageName());
            return;
        }
        AFLogger.afInfoLog("****** onReceive called *******");
        AppsFlyerProperties.getInstance().setOnReceiveCalled();
        String stringExtra2 = intent.getStringExtra("referrer");
        AFLogger.afInfoLog("Play store referrer: ".concat(String.valueOf(stringExtra2)));
        if (stringExtra2 != null) {
            if ("AppsFlyer_Test".equals(intent.getStringExtra("TestIntegrationMode"))) {
                Editor edit = context.getSharedPreferences("appsflyer-data", 0).edit();
                edit.clear();
                if (VERSION.SDK_INT >= 9) {
                    edit.apply();
                } else {
                    edit.commit();
                }
                AppsFlyerProperties.getInstance().setFirstLaunchCalled(false);
                AFLogger.afInfoLog("Test mode started..");
                this.z = System.currentTimeMillis();
            }
            Editor edit2 = context.getSharedPreferences("appsflyer-data", 0).edit();
            edit2.putString("referrer", stringExtra2);
            if (VERSION.SDK_INT >= 9) {
                edit2.apply();
            } else {
                edit2.commit();
            }
            AppsFlyerProperties.getInstance().setReferrer(stringExtra2);
            if (AppsFlyerProperties.getInstance().isFirstLaunchCalled()) {
                AFLogger.afInfoLog("onReceive: isLaunchCalled");
                if (stringExtra2 != null && stringExtra2.length() > 5) {
                    ScheduledThreadPoolExecutor a2 = AFExecutor.getInstance().a();
                    b bVar = new b(this, new WeakReference(context.getApplicationContext()), null, null, null, stringExtra2, a2, true, intent, 0);
                    a((ScheduledExecutorService) a2, (Runnable) bVar, 5, TimeUnit.MILLISECONDS);
                }
            }
        }
    }

    private static void a(JSONObject jSONObject) {
        String str;
        ArrayList arrayList = new ArrayList();
        Iterator keys = jSONObject.keys();
        while (true) {
            if (!keys.hasNext()) {
                break;
            }
            try {
                JSONArray jSONArray = new JSONArray((String) jSONObject.get((String) keys.next()));
                for (int i2 = 0; i2 < jSONArray.length(); i2++) {
                    arrayList.add(Long.valueOf(jSONArray.getLong(i2)));
                }
            } catch (JSONException unused) {
            }
        }
        Collections.sort(arrayList);
        Iterator keys2 = jSONObject.keys();
        loop2:
        while (true) {
            str = null;
            while (keys2.hasNext() && str == null) {
                String str2 = (String) keys2.next();
                try {
                    JSONArray jSONArray2 = new JSONArray((String) jSONObject.get(str2));
                    String str3 = str;
                    int i3 = 0;
                    while (i3 < jSONArray2.length()) {
                        try {
                            if (jSONArray2.getLong(i3) != ((Long) arrayList.get(0)).longValue() && jSONArray2.getLong(i3) != ((Long) arrayList.get(1)).longValue() && jSONArray2.getLong(i3) != ((Long) arrayList.get(arrayList.size() - 1)).longValue()) {
                                i3++;
                                str3 = str2;
                            }
                        } catch (JSONException unused2) {
                        }
                    }
                    str = str3;
                } catch (JSONException unused3) {
                }
            }
        }
        if (str != null) {
            jSONObject.remove(str);
        }
    }

    static void a(Context context, String str) {
        JSONObject jSONObject;
        JSONArray jSONArray;
        AFLogger.afDebugLog("received a new (extra) referrer: ".concat(String.valueOf(str)));
        try {
            long currentTimeMillis = System.currentTimeMillis();
            String string = context.getSharedPreferences("appsflyer-data", 0).getString("extraReferrers", null);
            if (string == null) {
                JSONObject jSONObject2 = new JSONObject();
                JSONArray jSONArray2 = new JSONArray();
                jSONObject = jSONObject2;
                jSONArray = jSONArray2;
            } else {
                jSONObject = new JSONObject(string);
                if (jSONObject.has(str)) {
                    jSONArray = new JSONArray((String) jSONObject.get(str));
                } else {
                    jSONArray = new JSONArray();
                }
            }
            if (((long) jSONArray.length()) < 5) {
                jSONArray.put(currentTimeMillis);
            }
            if (((long) jSONObject.length()) >= 4) {
                a(jSONObject);
            }
            jSONObject.put(str, jSONArray.toString());
            String jSONObject3 = jSONObject.toString();
            Editor edit = context.getSharedPreferences("appsflyer-data", 0).edit();
            edit.putString("extraReferrers", jSONObject3);
            if (VERSION.SDK_INT >= 9) {
                edit.apply();
            } else {
                edit.commit();
            }
        } catch (JSONException unused) {
        } catch (Throwable th) {
            StringBuilder sb = new StringBuilder("Couldn't save referrer - ");
            sb.append(str);
            sb.append(": ");
            AFLogger.afErrorLog(sb.toString(), th);
        }
    }

    private AppsFlyerLib() {
        AFVersionDeclaration.init();
    }

    public static AppsFlyerLib getInstance() {
        return w;
    }

    public void stopTracking(boolean z2, Context context) {
        this.I = z2;
        CacheManager.getInstance().clearCache(context);
        if (this.I) {
            String str = IS_STOP_TRACKING_USED;
            Editor edit = context.getSharedPreferences("appsflyer-data", 0).edit();
            edit.putBoolean(str, true);
            if (VERSION.SDK_INT >= 9) {
                edit.apply();
                return;
            }
            edit.commit();
        }
    }

    public String getSdkVersion() {
        y.a().a("getSdkVersion", new String[0]);
        return "version: 4.8.11 (build 383)";
    }

    public void onPause(Context context) {
        d.a(context);
        f a2 = f.a(context);
        a2.a.post(a2.f);
    }

    private void a(Application application) {
        AppsFlyerProperties.getInstance().loadProperties(application.getApplicationContext());
        if (VERSION.SDK_INT < 14) {
            AFLogger.afInfoLog("SDK<14 call trackEvent manually");
            AFLogger.afInfoLog("onBecameForeground");
            getInstance().u = System.currentTimeMillis();
            getInstance().a((Context) application, (String) null, null);
            AFLogger.resetDeltaTime();
        } else if (VERSION.SDK_INT >= 14 && this.x == null) {
            q.a();
            this.x = new a() {
                public final void a(Activity activity) {
                    if (2 > AppsFlyerLib.a(AppsFlyerLib.a((Context) activity))) {
                        f a2 = f.a(activity);
                        a2.a.post(a2.f);
                        a2.a.post(a2.e);
                    }
                    AFLogger.afInfoLog("onBecameForeground");
                    AppsFlyerLib.getInstance().a();
                    AppsFlyerLib.getInstance().a((Context) activity, (String) null, null);
                    AFLogger.resetDeltaTime();
                }

                public final void a(WeakReference<Context> weakReference) {
                    d.a((Context) weakReference.get());
                    f a2 = f.a((Context) weakReference.get());
                    a2.a.post(a2.f);
                }
            };
            q.b().a(application, this.x);
        }
    }

    @Deprecated
    public void setGCMProjectID(String str) {
        y.a().a("setGCMProjectID", str);
        AFLogger.afWarnLog("Method 'setGCMProjectNumber' is deprecated. Please follow the documentation.");
        enableUninstallTracking(str);
    }

    @Deprecated
    public void setGCMProjectNumber(String str) {
        y.a().a("setGCMProjectNumber", str);
        AFLogger.afWarnLog("Method 'setGCMProjectNumber' is deprecated. Please follow the documentation.");
        enableUninstallTracking(str);
    }

    @Deprecated
    public void setGCMProjectNumber(Context context, String str) {
        y.a().a("setGCMProjectNumber", str);
        AFLogger.afWarnLog("Method 'setGCMProjectNumber' is deprecated. Please use 'enableUninstallTracking'.");
        enableUninstallTracking(str);
    }

    public void enableUninstallTracking(String str) {
        y.a().a("enableUninstallTracking", str);
        AppsFlyerProperties.getInstance().set("gcmProjectNumber", str);
    }

    public void updateServerUninstallToken(Context context, String str) {
        if (str != null) {
            u.a(context, new d(str));
        }
    }

    public void setDebugLog(boolean z2) {
        y.a().a("setDebugLog", String.valueOf(z2));
        AppsFlyerProperties.getInstance().set("shouldLog", z2);
        AppsFlyerProperties.getInstance().set("logLevel", (z2 ? LogLevel.DEBUG : LogLevel.NONE).getLevel());
    }

    public void setImeiData(String str) {
        y.a().a("setImeiData", str);
        this.d = str;
    }

    public void setAndroidIdData(String str) {
        y.a().a("setAndroidIdData", str);
        this.e = str;
    }

    public AppsFlyerLib enableLocationCollection(boolean z2) {
        this.A = z2;
        return this;
    }

    @Deprecated
    public void setAppUserId(String str) {
        y.a().a("setAppUserId", str);
        setCustomerUserId(str);
    }

    public void setCustomerUserId(String str) {
        y.a().a("setCustomerUserId", str);
        AFLogger.afInfoLog("setCustomerUserId = ".concat(String.valueOf(str)));
        AppsFlyerProperties.getInstance().set(AppsFlyerProperties.APP_USER_ID, str);
    }

    public void waitForCustomerUserId(boolean z2) {
        AFLogger.afInfoLog("initAfterCustomerUserID: ".concat(String.valueOf(z2)), true);
        AppsFlyerProperties.getInstance().set(AppsFlyerProperties.AF_WAITFOR_CUSTOMERID, z2);
    }

    public void setCustomerIdAndTrack(String str, @NonNull Context context) {
        if (context != null) {
            boolean z2 = false;
            if (AppsFlyerProperties.getInstance().getBoolean(AppsFlyerProperties.AF_WAITFOR_CUSTOMERID, false)) {
                if (AppsFlyerProperties.getInstance().getString(AppsFlyerProperties.APP_USER_ID) == null) {
                    z2 = true;
                }
            }
            if (z2) {
                setCustomerUserId(str);
                StringBuilder sb = new StringBuilder("CustomerUserId set: ");
                sb.append(str);
                sb.append(" - Initializing AppsFlyer Tacking");
                AFLogger.afInfoLog(sb.toString(), true);
                String referrer = AppsFlyerProperties.getInstance().getReferrer(context);
                String string = AppsFlyerProperties.getInstance().getString(AppsFlyerProperties.AF_KEY);
                if (referrer == null) {
                    referrer = "";
                }
                a(context, string, (String) null, (String) null, referrer, context instanceof Activity ? ((Activity) context).getIntent() : null);
                if (AppsFlyerProperties.getInstance().getString("afUninstallToken") != null) {
                    b(context, AppsFlyerProperties.getInstance().getString("afUninstallToken"));
                }
                return;
            }
            setCustomerUserId(str);
            AFLogger.afInfoLog("waitForCustomerUserId is false; setting CustomerUserID: ".concat(String.valueOf(str)), true);
        }
    }

    public void setAppInviteOneLink(String str) {
        y.a().a("setAppInviteOneLink", str);
        AFLogger.afInfoLog("setAppInviteOneLink = ".concat(String.valueOf(str)));
        if (str == null || !str.equals(AppsFlyerProperties.getInstance().getString(AppsFlyerProperties.ONELINK_ID))) {
            AppsFlyerProperties.getInstance().remove(AppsFlyerProperties.ONELINK_DOMAIN);
            AppsFlyerProperties.getInstance().remove("onelinkVersion");
            AppsFlyerProperties.getInstance().remove(AppsFlyerProperties.ONELINK_SCHEME);
        }
        AppsFlyerProperties.getInstance().set(AppsFlyerProperties.ONELINK_ID, str);
    }

    public void setAdditionalData(HashMap<String, Object> hashMap) {
        if (hashMap != null) {
            y.a().a("setAdditionalData", hashMap.toString());
            AppsFlyerProperties.getInstance().setCustomData(new JSONObject(hashMap).toString());
        }
    }

    public void sendDeepLinkData(Activity activity) {
        if (activity != null && activity.getIntent() != null) {
            StringBuilder sb = new StringBuilder("activity_intent_");
            sb.append(activity.getIntent().toString());
            y.a().a("sendDeepLinkData", activity.getLocalClassName(), sb.toString());
        } else if (activity != null) {
            y.a().a("sendDeepLinkData", activity.getLocalClassName(), "activity_intent_null");
        } else {
            y.a().a("sendDeepLinkData", "activity_null");
        }
        StringBuilder sb2 = new StringBuilder("getDeepLinkData with activity ");
        sb2.append(activity.getIntent().getDataString());
        AFLogger.afInfoLog(sb2.toString());
        a(activity.getApplication());
    }

    /* JADX WARNING: Removed duplicated region for block: B:20:0x0099  */
    /* JADX WARNING: Removed duplicated region for block: B:46:0x0168  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void sendPushNotificationData(android.app.Activity r15) {
        /*
            r14 = this;
            r0 = 1
            r1 = 0
            r2 = 2
            if (r15 == 0) goto L_0x0035
            android.content.Intent r3 = r15.getIntent()
            if (r3 == 0) goto L_0x0035
            com.appsflyer.y r3 = com.appsflyer.y.a()
            java.lang.String r4 = "sendPushNotificationData"
            java.lang.String[] r5 = new java.lang.String[r2]
            java.lang.String r6 = r15.getLocalClassName()
            r5[r1] = r6
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r6 = "activity_intent_"
            r1.<init>(r6)
            android.content.Intent r6 = r15.getIntent()
            java.lang.String r6 = r6.toString()
            r1.append(r6)
            java.lang.String r1 = r1.toString()
            r5[r0] = r1
            r3.a(r4, r5)
            goto L_0x005c
        L_0x0035:
            if (r15 == 0) goto L_0x004d
            com.appsflyer.y r3 = com.appsflyer.y.a()
            java.lang.String r4 = "sendPushNotificationData"
            java.lang.String[] r5 = new java.lang.String[r2]
            java.lang.String r6 = r15.getLocalClassName()
            r5[r1] = r6
            java.lang.String r1 = "activity_intent_null"
            r5[r0] = r1
            r3.a(r4, r5)
            goto L_0x005c
        L_0x004d:
            com.appsflyer.y r3 = com.appsflyer.y.a()
            java.lang.String r4 = "sendPushNotificationData"
            java.lang.String[] r0 = new java.lang.String[r0]
            java.lang.String r5 = "activity_null"
            r0[r1] = r5
            r3.a(r4, r0)
        L_0x005c:
            boolean r0 = r15 instanceof android.app.Activity
            r1 = 0
            if (r0 == 0) goto L_0x0092
            r0 = r15
            android.app.Activity r0 = (android.app.Activity) r0
            android.content.Intent r3 = r0.getIntent()
            if (r3 == 0) goto L_0x0092
            android.os.Bundle r4 = r3.getExtras()
            if (r4 == 0) goto L_0x0092
            java.lang.String r5 = "af"
            java.lang.String r5 = r4.getString(r5)
            if (r5 == 0) goto L_0x0093
            java.lang.String r6 = "Push Notification received af payload = "
            java.lang.String r7 = java.lang.String.valueOf(r5)
            java.lang.String r6 = r6.concat(r7)
            com.appsflyer.AFLogger.afInfoLog(r6)
            java.lang.String r6 = "af"
            r4.remove(r6)
            android.content.Intent r3 = r3.putExtras(r4)
            r0.setIntent(r3)
            goto L_0x0093
        L_0x0092:
            r5 = r1
        L_0x0093:
            r14.C = r5
            java.lang.String r0 = r14.C
            if (r0 == 0) goto L_0x0199
            long r3 = java.lang.System.currentTimeMillis()
            java.util.Map<java.lang.Long, java.lang.String> r0 = r14.D
            if (r0 != 0) goto L_0x00b0
            java.lang.String r0 = "pushes: initializing pushes history.."
            com.appsflyer.AFLogger.afInfoLog(r0)
            java.util.concurrent.ConcurrentHashMap r0 = new java.util.concurrent.ConcurrentHashMap
            r0.<init>()
            r14.D = r0
            r7 = r3
            goto L_0x0156
        L_0x00b0:
            com.appsflyer.AppsFlyerProperties r0 = com.appsflyer.AppsFlyerProperties.getInstance()     // Catch:{ Throwable -> 0x013b }
            java.lang.String r5 = "pushPayloadMaxAging"
            r6 = 1800000(0x1b7740, double:8.89318E-318)
            long r5 = r0.getLong(r5, r6)     // Catch:{ Throwable -> 0x013b }
            java.util.Map<java.lang.Long, java.lang.String> r0 = r14.D     // Catch:{ Throwable -> 0x013b }
            java.util.Set r0 = r0.keySet()     // Catch:{ Throwable -> 0x013b }
            java.util.Iterator r0 = r0.iterator()     // Catch:{ Throwable -> 0x013b }
            r7 = r3
        L_0x00c8:
            boolean r9 = r0.hasNext()     // Catch:{ Throwable -> 0x0139 }
            if (r9 == 0) goto L_0x0156
            java.lang.Object r9 = r0.next()     // Catch:{ Throwable -> 0x0139 }
            java.lang.Long r9 = (java.lang.Long) r9     // Catch:{ Throwable -> 0x0139 }
            org.json.JSONObject r10 = new org.json.JSONObject     // Catch:{ Throwable -> 0x0139 }
            java.lang.String r11 = r14.C     // Catch:{ Throwable -> 0x0139 }
            r10.<init>(r11)     // Catch:{ Throwable -> 0x0139 }
            org.json.JSONObject r11 = new org.json.JSONObject     // Catch:{ Throwable -> 0x0139 }
            java.util.Map<java.lang.Long, java.lang.String> r12 = r14.D     // Catch:{ Throwable -> 0x0139 }
            java.lang.Object r12 = r12.get(r9)     // Catch:{ Throwable -> 0x0139 }
            java.lang.String r12 = (java.lang.String) r12     // Catch:{ Throwable -> 0x0139 }
            r11.<init>(r12)     // Catch:{ Throwable -> 0x0139 }
            java.lang.String r12 = "pid"
            java.lang.Object r12 = r10.get(r12)     // Catch:{ Throwable -> 0x0139 }
            java.lang.String r13 = "pid"
            java.lang.Object r13 = r11.get(r13)     // Catch:{ Throwable -> 0x0139 }
            boolean r12 = r12.equals(r13)     // Catch:{ Throwable -> 0x0139 }
            if (r12 == 0) goto L_0x011b
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0139 }
            java.lang.String r5 = "PushNotificationMeasurement: A previous payload with same PID was already acknowledged! (old: "
            r0.<init>(r5)     // Catch:{ Throwable -> 0x0139 }
            r0.append(r11)     // Catch:{ Throwable -> 0x0139 }
            java.lang.String r5 = ", new: "
            r0.append(r5)     // Catch:{ Throwable -> 0x0139 }
            r0.append(r10)     // Catch:{ Throwable -> 0x0139 }
            java.lang.String r5 = ")"
            r0.append(r5)     // Catch:{ Throwable -> 0x0139 }
            java.lang.String r0 = r0.toString()     // Catch:{ Throwable -> 0x0139 }
            com.appsflyer.AFLogger.afInfoLog(r0)     // Catch:{ Throwable -> 0x0139 }
            r14.C = r1     // Catch:{ Throwable -> 0x0139 }
            return
        L_0x011b:
            long r10 = r9.longValue()     // Catch:{ Throwable -> 0x0139 }
            r12 = 0
            long r12 = r3 - r10
            int r10 = (r12 > r5 ? 1 : (r12 == r5 ? 0 : -1))
            if (r10 <= 0) goto L_0x012b
            java.util.Map<java.lang.Long, java.lang.String> r10 = r14.D     // Catch:{ Throwable -> 0x0139 }
            r10.remove(r9)     // Catch:{ Throwable -> 0x0139 }
        L_0x012b:
            long r10 = r9.longValue()     // Catch:{ Throwable -> 0x0139 }
            int r12 = (r10 > r7 ? 1 : (r10 == r7 ? 0 : -1))
            if (r12 > 0) goto L_0x00c8
            long r9 = r9.longValue()     // Catch:{ Throwable -> 0x0139 }
            r7 = r9
            goto L_0x00c8
        L_0x0139:
            r0 = move-exception
            goto L_0x013d
        L_0x013b:
            r0 = move-exception
            r7 = r3
        L_0x013d:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r5 = "Error while handling push notification measurement: "
            r1.<init>(r5)
            java.lang.Class r5 = r0.getClass()
            java.lang.String r5 = r5.getSimpleName()
            r1.append(r5)
            java.lang.String r1 = r1.toString()
            com.appsflyer.AFLogger.afErrorLog(r1, r0)
        L_0x0156:
            com.appsflyer.AppsFlyerProperties r0 = com.appsflyer.AppsFlyerProperties.getInstance()
            java.lang.String r1 = "pushPayloadHistorySize"
            int r0 = r0.getInt(r1, r2)
            java.util.Map<java.lang.Long, java.lang.String> r1 = r14.D
            int r1 = r1.size()
            if (r1 != r0) goto L_0x0187
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r1 = "pushes: removing oldest overflowing push (oldest push:"
            r0.<init>(r1)
            r0.append(r7)
            java.lang.String r1 = ")"
            r0.append(r1)
            java.lang.String r0 = r0.toString()
            com.appsflyer.AFLogger.afInfoLog(r0)
            java.util.Map<java.lang.Long, java.lang.String> r0 = r14.D
            java.lang.Long r1 = java.lang.Long.valueOf(r7)
            r0.remove(r1)
        L_0x0187:
            java.util.Map<java.lang.Long, java.lang.String> r0 = r14.D
            java.lang.Long r1 = java.lang.Long.valueOf(r3)
            java.lang.String r2 = r14.C
            r0.put(r1, r2)
            android.app.Application r15 = r15.getApplication()
            r14.a(r15)
        L_0x0199:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.appsflyer.AppsFlyerLib.sendPushNotificationData(android.app.Activity):void");
    }

    @Deprecated
    public void setUserEmail(String str) {
        y.a().a("setUserEmail", str);
        AppsFlyerProperties.getInstance().set(AppsFlyerProperties.USER_EMAIL, str);
    }

    public void setUserEmails(String... strArr) {
        y.a().a("setUserEmails", strArr);
        setUserEmails(EmailsCryptType.NONE, strArr);
    }

    public void setUserEmails(EmailsCryptType emailsCryptType, String... strArr) {
        String str;
        ArrayList arrayList = new ArrayList(strArr.length + 1);
        arrayList.add(emailsCryptType.toString());
        arrayList.addAll(Arrays.asList(strArr));
        y.a().a("setUserEmails", (String[]) arrayList.toArray(new String[(strArr.length + 1)]));
        AppsFlyerProperties.getInstance().set(AppsFlyerProperties.EMAIL_CRYPT_TYPE, emailsCryptType.getValue());
        HashMap hashMap = new HashMap();
        ArrayList arrayList2 = new ArrayList();
        Object obj = null;
        for (String str2 : strArr) {
            switch (AnonymousClass4.a[emailsCryptType.ordinal()]) {
                case 2:
                    str = "md5_el_arr";
                    arrayList2.add(C0096r.b(str2));
                    break;
                case 3:
                    str = "sha256_el_arr";
                    arrayList2.add(C0096r.c(str2));
                    break;
                case 4:
                    str = "plain_el_arr";
                    arrayList2.add(str2);
                    break;
                default:
                    str = "sha1_el_arr";
                    arrayList2.add(C0096r.a(str2));
                    break;
            }
            obj = str;
        }
        hashMap.put(obj, arrayList2);
        AppsFlyerProperties.getInstance().setUserEmails(new JSONObject(hashMap).toString());
    }

    public void setCollectAndroidID(boolean z2) {
        y.a().a("setCollectAndroidID", String.valueOf(z2));
        AppsFlyerProperties.getInstance().set(AppsFlyerProperties.COLLECT_ANDROID_ID, Boolean.toString(z2));
    }

    public void setCollectIMEI(boolean z2) {
        y.a().a("setCollectIMEI", String.valueOf(z2));
        AppsFlyerProperties.getInstance().set(AppsFlyerProperties.COLLECT_IMEI, Boolean.toString(z2));
    }

    @Deprecated
    public void setCollectFingerPrint(boolean z2) {
        y.a().a("setCollectFingerPrint", String.valueOf(z2));
        AppsFlyerProperties.getInstance().set(AppsFlyerProperties.COLLECT_FINGER_PRINT, Boolean.toString(z2));
    }

    public AppsFlyerLib init(String str, AppsFlyerConversionListener appsFlyerConversionListener) {
        y a2 = y.a();
        String str2 = "init";
        String[] strArr = new String[2];
        strArr[0] = str;
        strArr[1] = appsFlyerConversionListener == null ? "null" : "conversionDataListener";
        a2.a(str2, strArr);
        AFLogger.a(String.format("Initializing AppsFlyer SDK: (v%s.%s)", new Object[]{BuildConfig.AF_SDK_VERSION, "383"}));
        this.F = true;
        AppsFlyerProperties.getInstance().set(AppsFlyerProperties.AF_KEY, str);
        AnonymousClass5.a(str);
        o = appsFlyerConversionListener;
        return this;
    }

    public AppsFlyerLib init(String str, AppsFlyerConversionListener appsFlyerConversionListener, Context context) {
        if (context != null && c(context)) {
            if (this.p == null) {
                this.p = new e();
                this.p.a(context, this);
            } else {
                AFLogger.afWarnLog("AFInstallReferrer instance already created");
            }
        }
        return init(str, appsFlyerConversionListener);
    }

    private static boolean c(@NonNull Context context) {
        if (a(context.getSharedPreferences("appsflyer-data", 0), "appsFlyerCount", false) > 2) {
            AFLogger.afRDLog("Install referrer will not load, the counter > 2, ");
            return false;
        }
        try {
            Class.forName("com.android.installreferrer.api.InstallReferrerClient");
            if (a.a(context, "com.google.android.finsky.permission.BIND_GET_INSTALL_REFERRER_SERVICE")) {
                AFLogger.afDebugLog("Install referrer is allowed");
                return true;
            }
            AFLogger.afDebugLog("Install referrer is not allowed");
            return false;
        } catch (ClassNotFoundException unused) {
            AFLogger.afRDLog("Class com.android.installreferrer.api.InstallReferrerClient not found");
            return false;
        } catch (Throwable th) {
            AFLogger.afErrorLog("An error occurred while trying to verify manifest : com.android.installreferrer.api.InstallReferrerClient", th);
            return false;
        }
    }

    public void startTracking(Application application) {
        if (!this.F) {
            AFLogger.afWarnLog("ERROR: AppsFlyer SDK is not initialized! The API call 'startTracking(Application)' must be called after the 'init(String, AppsFlyerConversionListener)' API method, which should be called on the Application's onCreate.");
        } else {
            startTracking(application, null);
        }
    }

    public void startTracking(Application application, String str) {
        y.a().a("startTracking", str);
        AFLogger.afInfoLog(String.format("Starting AppsFlyer Tracking: (v%s.%s)", new Object[]{BuildConfig.AF_SDK_VERSION, "383"}));
        AFLogger.afInfoLog("Build Number: 383");
        AppsFlyerProperties.getInstance().loadProperties(application.getApplicationContext());
        if (!TextUtils.isEmpty(str)) {
            AppsFlyerProperties.getInstance().set(AppsFlyerProperties.AF_KEY, str);
            AnonymousClass5.a(str);
        } else {
            if (TextUtils.isEmpty(AppsFlyerProperties.getInstance().getString(AppsFlyerProperties.AF_KEY))) {
                AFLogger.afWarnLog("ERROR: AppsFlyer SDK is not initialized! You must provide AppsFlyer Dev-Key either in the 'init' API method (should be called on Application's onCreate),or in the startTracking API method (should be called on Activity's onCreate).");
                return;
            }
        }
        a(application);
    }

    public void setAppId(String str) {
        y.a().a("setAppId", str);
        AppsFlyerProperties.getInstance().set(AppsFlyerProperties.APP_ID, str);
    }

    public void setExtension(String str) {
        y.a().a("setExtension", str);
        AppsFlyerProperties.getInstance().set(AppsFlyerProperties.EXTENSION, str);
    }

    public void setIsUpdate(boolean z2) {
        y.a().a("setIsUpdate", String.valueOf(z2));
        AppsFlyerProperties.getInstance().set(AppsFlyerProperties.IS_UPDATE, z2);
    }

    public void setCurrencyCode(String str) {
        y.a().a("setCurrencyCode", str);
        AppsFlyerProperties.getInstance().set(AppsFlyerProperties.CURRENCY_CODE, str);
    }

    public void trackLocation(Context context, double d2, double d3) {
        y.a().a("trackLocation", String.valueOf(d2), String.valueOf(d3));
        HashMap hashMap = new HashMap();
        hashMap.put(AFInAppEventParameterName.LONGTITUDE, Double.toString(d3));
        hashMap.put(AFInAppEventParameterName.LATITUDE, Double.toString(d2));
        a(context, AFInAppEventType.LOCATION_COORDINATES, (Map<String, Object>) hashMap);
    }

    /* access modifiers changed from: 0000 */
    public final void a(WeakReference<Context> weakReference) {
        if (weakReference.get() != null) {
            AFLogger.afInfoLog("app went to background");
            SharedPreferences sharedPreferences = ((Context) weakReference.get()).getSharedPreferences("appsflyer-data", 0);
            AppsFlyerProperties.getInstance().saveProperties(sharedPreferences);
            long j2 = this.v - this.u;
            HashMap hashMap = new HashMap();
            String string = AppsFlyerProperties.getInstance().getString(AppsFlyerProperties.AF_KEY);
            if (string == null) {
                AFLogger.afWarnLog("[callStats] AppsFlyer's SDK cannot send any event without providing DevKey.");
                return;
            }
            String string2 = AppsFlyerProperties.getInstance().getString("KSAppsFlyerId");
            if (AppsFlyerProperties.getInstance().getBoolean(AppsFlyerProperties.DEVICE_TRACKING_DISABLED, false)) {
                hashMap.put(AppsFlyerProperties.DEVICE_TRACKING_DISABLED, "true");
            }
            n a2 = o.a(((Context) weakReference.get()).getContentResolver());
            if (a2 != null) {
                hashMap.put("amazon_aid", a2.a());
                hashMap.put("amazon_aid_limit", String.valueOf(a2.b()));
            }
            String string3 = AppsFlyerProperties.getInstance().getString(ServerParameters.ADVERTISING_ID_PARAM);
            if (string3 != null) {
                hashMap.put(ServerParameters.ADVERTISING_ID_PARAM, string3);
            }
            hashMap.put("app_id", ((Context) weakReference.get()).getPackageName());
            hashMap.put("devkey", string);
            hashMap.put("uid", p.a(weakReference));
            hashMap.put("time_in_app", String.valueOf(j2 / 1000));
            hashMap.put("statType", "user_closed_app");
            hashMap.put("platform", Constants.CURRENT_SO);
            hashMap.put("launch_counter", Integer.toString(a(sharedPreferences, "appsFlyerCount", false)));
            hashMap.put("gcd_conversion_data_timing", Long.toString(sharedPreferences.getLong("appsflyerGetConversionDataTiming", 0)));
            String str = AppsFlyerProperties.CHANNEL;
            String string4 = AppsFlyerProperties.getInstance().getString(AppsFlyerProperties.CHANNEL);
            if (string4 == null) {
                string4 = a(weakReference, "CHANNEL");
            }
            hashMap.put(str, string4);
            String str2 = "originalAppsflyerId";
            if (string2 == null) {
                string2 = "";
            }
            hashMap.put(str2, string2);
            if (this.H) {
                try {
                    l lVar = new l(null, isTrackingStopped());
                    lVar.a = hashMap;
                    if (Thread.currentThread() == Looper.getMainLooper().getThread()) {
                        AFLogger.afDebugLog("Main thread detected. Running callStats task in a new thread.");
                        lVar.execute(new String[]{ServerConfigHandler.getUrl("https://stats.%s/stats")});
                        return;
                    }
                    StringBuilder sb = new StringBuilder("Running callStats task (on current thread: ");
                    sb.append(Thread.currentThread().toString());
                    sb.append(" )");
                    AFLogger.afDebugLog(sb.toString());
                    lVar.onPreExecute();
                    lVar.onPostExecute(lVar.doInBackground(ServerConfigHandler.getUrl("https://stats.%s/stats")));
                } catch (Throwable th) {
                    AFLogger.afErrorLog("Could not send callStats request", th);
                }
            } else {
                AFLogger.afDebugLog("Stats call is disabled, ignore ...");
            }
        }
    }

    public void trackAppLaunch(Context context, String str) {
        if (c(context)) {
            if (this.p == null) {
                this.p = new e();
                this.p.a(context, this);
            } else {
                AFLogger.afWarnLog("AFInstallReferrer instance already created");
            }
        }
        a(context, str, (String) null, (String) null, "", (Intent) null);
    }

    /* access modifiers changed from: protected */
    public void setDeepLinkData(Intent intent) {
        if (intent != null) {
            try {
                if ("android.intent.action.VIEW".equals(intent.getAction())) {
                    this.y = intent.getData();
                    StringBuilder sb = new StringBuilder("Unity setDeepLinkData = ");
                    sb.append(this.y);
                    AFLogger.afDebugLog(sb.toString());
                }
            } catch (Throwable th) {
                AFLogger.afErrorLog("Exception while setting deeplink data (unity). ", th);
            }
        }
    }

    public void reportTrackSession(Context context) {
        y.a().a("reportTrackSession", new String[0]);
        y.a().f();
        a(context, (String) null, null);
    }

    public void trackEvent(Context context, String str, Map<String, Object> map) {
        y.a().a("trackEvent", str, new JSONObject(map == null ? new HashMap<>() : map).toString());
        a(context, str, map);
    }

    /* access modifiers changed from: 0000 */
    public final void a(Context context, String str, Map<String, Object> map) {
        Intent intent = context instanceof Activity ? ((Activity) context).getIntent() : null;
        if (AppsFlyerProperties.getInstance().getString(AppsFlyerProperties.AF_KEY) == null) {
            AFLogger.afWarnLog("[TrackEvent/Launch] AppsFlyer's SDK cannot send any event without providing DevKey.");
            return;
        }
        if (map == null) {
            map = new HashMap<>();
        }
        JSONObject jSONObject = new JSONObject(map);
        String referrer = AppsFlyerProperties.getInstance().getReferrer(context);
        String jSONObject2 = jSONObject.toString();
        if (referrer == null) {
            referrer = "";
        }
        a(context, (String) null, str, jSONObject2, referrer, intent);
    }

    private static void a(Context context, String str, String str2, String str3) {
        if (AppsFlyerProperties.getInstance().getBoolean(AppsFlyerProperties.IS_MONITOR, false)) {
            Intent intent = new Intent("com.appsflyer.MonitorBroadcast");
            intent.setPackage("com.appsflyer.nightvision");
            intent.putExtra("message", str2);
            intent.putExtra(TarjetasConstants.VALUE, str3);
            intent.putExtra("packageName", "true");
            intent.putExtra(com.appsflyer.share.Constants.URL_MEDIA_SOURCE, new Integer(Process.myPid()));
            intent.putExtra("eventIdentifier", str);
            intent.putExtra(CommonUtils.SDK, BuildConfig.AF_SDK_VERSION);
            context.sendBroadcast(intent);
        }
    }

    /* access modifiers changed from: 0000 */
    /* JADX WARNING: Removed duplicated region for block: B:7:0x001f  */
    /* JADX WARNING: Removed duplicated region for block: B:9:0x0025  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void b(android.content.Context r11, java.lang.String r12) {
        /*
            r10 = this;
            java.lang.String r0 = "waitForCustomerId"
            com.appsflyer.AppsFlyerProperties r1 = com.appsflyer.AppsFlyerProperties.getInstance()
            r2 = 0
            boolean r0 = r1.getBoolean(r0, r2)
            r1 = 1
            if (r0 == 0) goto L_0x001c
            java.lang.String r0 = "AppUserId"
            com.appsflyer.AppsFlyerProperties r3 = com.appsflyer.AppsFlyerProperties.getInstance()
            java.lang.String r0 = r3.getString(r0)
            if (r0 != 0) goto L_0x001c
            r0 = 1
            goto L_0x001d
        L_0x001c:
            r0 = 0
        L_0x001d:
            if (r0 == 0) goto L_0x0025
            java.lang.String r11 = "CustomerUserId not set, Tracking is disabled"
            com.appsflyer.AFLogger.afInfoLog(r11, r1)
            return
        L_0x0025:
            java.util.HashMap r0 = new java.util.HashMap
            r0.<init>()
            java.lang.String r3 = "AppsFlyerKey"
            com.appsflyer.AppsFlyerProperties r4 = com.appsflyer.AppsFlyerProperties.getInstance()
            java.lang.String r3 = r4.getString(r3)
            if (r3 != 0) goto L_0x003c
            java.lang.String r11 = "[registerUninstall] AppsFlyer's SDK cannot send any event without providing DevKey."
            com.appsflyer.AFLogger.afWarnLog(r11)
            return
        L_0x003c:
            android.content.pm.PackageManager r4 = r11.getPackageManager()
            java.lang.String r5 = r11.getPackageName()
            android.content.pm.PackageInfo r6 = r4.getPackageInfo(r5, r2)     // Catch:{ Throwable -> 0x008c }
            java.lang.String r7 = "app_version_code"
            int r8 = r6.versionCode     // Catch:{ Throwable -> 0x008c }
            java.lang.String r8 = java.lang.Integer.toString(r8)     // Catch:{ Throwable -> 0x008c }
            r0.put(r7, r8)     // Catch:{ Throwable -> 0x008c }
            java.lang.String r7 = "app_version_name"
            java.lang.String r8 = r6.versionName     // Catch:{ Throwable -> 0x008c }
            r0.put(r7, r8)     // Catch:{ Throwable -> 0x008c }
            android.content.pm.ApplicationInfo r7 = r6.applicationInfo     // Catch:{ Throwable -> 0x008c }
            java.lang.CharSequence r4 = r4.getApplicationLabel(r7)     // Catch:{ Throwable -> 0x008c }
            java.lang.String r4 = r4.toString()     // Catch:{ Throwable -> 0x008c }
            java.lang.String r7 = "app_name"
            r0.put(r7, r4)     // Catch:{ Throwable -> 0x008c }
            long r6 = r6.firstInstallTime     // Catch:{ Throwable -> 0x008c }
            java.lang.String r4 = "yyyy-MM-dd_HHmmssZ"
            java.text.SimpleDateFormat r8 = new java.text.SimpleDateFormat     // Catch:{ Throwable -> 0x008c }
            java.util.Locale r9 = java.util.Locale.US     // Catch:{ Throwable -> 0x008c }
            r8.<init>(r4, r9)     // Catch:{ Throwable -> 0x008c }
            java.lang.String r4 = "installDate"
            java.lang.String r9 = "UTC"
            java.util.TimeZone r9 = java.util.TimeZone.getTimeZone(r9)     // Catch:{ Throwable -> 0x008c }
            r8.setTimeZone(r9)     // Catch:{ Throwable -> 0x008c }
            java.util.Date r9 = new java.util.Date     // Catch:{ Throwable -> 0x008c }
            r9.<init>(r6)     // Catch:{ Throwable -> 0x008c }
            java.lang.String r6 = r8.format(r9)     // Catch:{ Throwable -> 0x008c }
            r0.put(r4, r6)     // Catch:{ Throwable -> 0x008c }
            goto L_0x0092
        L_0x008c:
            r4 = move-exception
            java.lang.String r6 = "Exception while collecting application version info."
            com.appsflyer.AFLogger.afErrorLog(r6, r4)
        L_0x0092:
            a(r11, r0)
            java.lang.String r4 = "AppUserId"
            com.appsflyer.AppsFlyerProperties r6 = com.appsflyer.AppsFlyerProperties.getInstance()
            java.lang.String r4 = r6.getString(r4)
            if (r4 == 0) goto L_0x00a6
            java.lang.String r6 = "appUserId"
            r0.put(r6, r4)
        L_0x00a6:
            java.lang.String r4 = "model"
            java.lang.String r6 = android.os.Build.MODEL     // Catch:{ Throwable -> 0x00b5 }
            r0.put(r4, r6)     // Catch:{ Throwable -> 0x00b5 }
            java.lang.String r4 = "brand"
            java.lang.String r6 = android.os.Build.BRAND     // Catch:{ Throwable -> 0x00b5 }
            r0.put(r4, r6)     // Catch:{ Throwable -> 0x00b5 }
            goto L_0x00bb
        L_0x00b5:
            r4 = move-exception
            java.lang.String r6 = "Exception while collecting device brand and model."
            com.appsflyer.AFLogger.afErrorLog(r6, r4)
        L_0x00bb:
            com.appsflyer.AppsFlyerProperties r4 = com.appsflyer.AppsFlyerProperties.getInstance()
            java.lang.String r6 = "deviceTrackingDisabled"
            boolean r4 = r4.getBoolean(r6, r2)
            if (r4 == 0) goto L_0x00ce
            java.lang.String r4 = "deviceTrackingDisabled"
            java.lang.String r6 = "true"
            r0.put(r4, r6)
        L_0x00ce:
            android.content.ContentResolver r4 = r11.getContentResolver()
            com.appsflyer.n r4 = com.appsflyer.o.a(r4)
            if (r4 == 0) goto L_0x00ee
            java.lang.String r6 = "amazon_aid"
            java.lang.String r7 = r4.a()
            r0.put(r6, r7)
            java.lang.String r6 = "amazon_aid_limit"
            boolean r4 = r4.b()
            java.lang.String r4 = java.lang.String.valueOf(r4)
            r0.put(r6, r4)
        L_0x00ee:
            com.appsflyer.AppsFlyerProperties r4 = com.appsflyer.AppsFlyerProperties.getInstance()
            java.lang.String r6 = "advertiserId"
            java.lang.String r4 = r4.getString(r6)
            if (r4 == 0) goto L_0x00ff
            java.lang.String r6 = "advertiserId"
            r0.put(r6, r4)
        L_0x00ff:
            java.lang.String r4 = "devkey"
            r0.put(r4, r3)
            java.lang.String r3 = "uid"
            java.lang.ref.WeakReference r4 = new java.lang.ref.WeakReference
            r4.<init>(r11)
            java.lang.String r4 = com.appsflyer.p.a(r4)
            r0.put(r3, r4)
            java.lang.String r3 = "af_gcm_token"
            r0.put(r3, r12)
            java.lang.String r12 = "appsflyer-data"
            android.content.SharedPreferences r12 = r11.getSharedPreferences(r12, r2)
            java.lang.String r3 = "appsFlyerCount"
            int r12 = a(r12, r3, r2)
            java.lang.String r3 = "launch_counter"
            java.lang.String r12 = java.lang.Integer.toString(r12)
            r0.put(r3, r12)
            java.lang.String r12 = "sdk"
            int r3 = android.os.Build.VERSION.SDK_INT
            java.lang.String r3 = java.lang.Integer.toString(r3)
            r0.put(r12, r3)
            java.lang.ref.WeakReference r12 = new java.lang.ref.WeakReference
            r12.<init>(r11)
            com.appsflyer.AppsFlyerProperties r3 = com.appsflyer.AppsFlyerProperties.getInstance()
            java.lang.String r4 = "channel"
            java.lang.String r3 = r3.getString(r4)
            if (r3 != 0) goto L_0x014e
            java.lang.String r3 = "CHANNEL"
            java.lang.String r3 = a(r12, r3)
        L_0x014e:
            if (r3 == 0) goto L_0x0155
            java.lang.String r12 = "channel"
            r0.put(r12, r3)
        L_0x0155:
            com.appsflyer.l r12 = new com.appsflyer.l     // Catch:{ Throwable -> 0x017d }
            boolean r3 = r10.isTrackingStopped()     // Catch:{ Throwable -> 0x017d }
            r12.<init>(r11, r3)     // Catch:{ Throwable -> 0x017d }
            r12.a = r0     // Catch:{ Throwable -> 0x017d }
            java.lang.StringBuilder r11 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x017d }
            r11.<init>()     // Catch:{ Throwable -> 0x017d }
            java.lang.String r0 = b     // Catch:{ Throwable -> 0x017d }
            java.lang.String r0 = com.appsflyer.ServerConfigHandler.getUrl(r0)     // Catch:{ Throwable -> 0x017d }
            r11.append(r0)     // Catch:{ Throwable -> 0x017d }
            r11.append(r5)     // Catch:{ Throwable -> 0x017d }
            java.lang.String r11 = r11.toString()     // Catch:{ Throwable -> 0x017d }
            java.lang.String[] r0 = new java.lang.String[r1]     // Catch:{ Throwable -> 0x017d }
            r0[r2] = r11     // Catch:{ Throwable -> 0x017d }
            r12.execute(r0)     // Catch:{ Throwable -> 0x017d }
            return
        L_0x017d:
            r11 = move-exception
            java.lang.String r12 = r11.getMessage()
            com.appsflyer.AFLogger.afErrorLog(r12, r11)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.appsflyer.AppsFlyerLib.b(android.content.Context, java.lang.String):void");
    }

    public void setDeviceTrackingDisabled(boolean z2) {
        y.a().a("setDeviceTrackingDisabled", String.valueOf(z2));
        AppsFlyerProperties.getInstance().set(AppsFlyerProperties.DEVICE_TRACKING_DISABLED, z2);
    }

    /* access modifiers changed from: private */
    public static Map<String, String> d(Context context) {
        String string = context.getSharedPreferences("appsflyer-data", 0).getString("attributionId", null);
        if (string != null && string.length() > 0) {
            return c(string);
        }
        throw new k();
    }

    public void registerConversionListener(Context context, AppsFlyerConversionListener appsFlyerConversionListener) {
        y.a().a("registerConversionListener", new String[0]);
        if (appsFlyerConversionListener != null) {
            o = appsFlyerConversionListener;
        }
    }

    public void unregisterConversionListener() {
        y.a().a("unregisterConversionListener", new String[0]);
        o = null;
    }

    public void registerValidatorListener(Context context, AppsFlyerInAppPurchaseValidatorListener appsFlyerInAppPurchaseValidatorListener) {
        y.a().a("registerValidatorListener", new String[0]);
        AFLogger.afDebugLog("registerValidatorListener called");
        if (appsFlyerInAppPurchaseValidatorListener == null) {
            AFLogger.afDebugLog("registerValidatorListener null listener");
        } else {
            c = appsFlyerInAppPurchaseValidatorListener;
        }
    }

    /* access modifiers changed from: protected */
    public void getConversionData(Context context, final ConversionDataListener conversionDataListener) {
        o = new AppsFlyerConversionListener() {
            public final void onAppOpenAttribution(Map<String, String> map) {
            }

            public final void onAttributionFailure(String str) {
            }

            public final void onInstallConversionDataLoaded(Map<String, String> map) {
                conversionDataListener.onConversionDataLoaded(map);
            }

            public final void onInstallConversionFailure(String str) {
                conversionDataListener.onConversionFailure(str);
            }
        };
    }

    private static Map<String, String> d(Context context, String str) {
        String str2;
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        String[] split = str.split("&");
        int length = split.length;
        boolean z2 = false;
        for (int i2 = 0; i2 < length; i2++) {
            String str3 = split[i2];
            int indexOf = str3.indexOf("=");
            String substring = indexOf > 0 ? str3.substring(0, indexOf) : str3;
            if (!linkedHashMap.containsKey(substring)) {
                if (substring.equals(com.appsflyer.share.Constants.URL_CAMPAIGN)) {
                    substring = "campaign";
                } else if (substring.equals(com.appsflyer.share.Constants.URL_MEDIA_SOURCE)) {
                    substring = "media_source";
                } else if (substring.equals("af_prt")) {
                    substring = "agency";
                    z2 = true;
                }
                linkedHashMap.put(substring, "");
            }
            if (indexOf > 0) {
                int i3 = indexOf + 1;
                if (str3.length() > i3) {
                    str2 = str3.substring(i3);
                    linkedHashMap.put(substring, str2);
                }
            }
            str2 = null;
            linkedHashMap.put(substring, str2);
        }
        try {
            if (!linkedHashMap.containsKey("install_time")) {
                PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
                long j2 = packageInfo.firstInstallTime;
                simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
                linkedHashMap.put("install_time", simpleDateFormat.format(new Date(j2)));
            }
        } catch (Exception e2) {
            AFLogger.afErrorLog("Could not fetch install time. ", e2);
        }
        if (!linkedHashMap.containsKey("af_status")) {
            linkedHashMap.put("af_status", "Non-organic");
        }
        if (z2) {
            linkedHashMap.remove("media_source");
        }
        return linkedHashMap;
    }

    /* access modifiers changed from: private */
    public static Map<String, String> c(String str) {
        HashMap hashMap = new HashMap();
        try {
            JSONObject jSONObject = new JSONObject(str);
            Iterator keys = jSONObject.keys();
            while (keys.hasNext()) {
                String str2 = (String) keys.next();
                if (!l.contains(str2)) {
                    String string = jSONObject.getString(str2);
                    if (!TextUtils.isEmpty(string) && !"null".equals(string)) {
                        hashMap.put(str2, string);
                    }
                }
            }
            return hashMap;
        } catch (JSONException e2) {
            AFLogger.afErrorLog(e2.getMessage(), e2);
            return null;
        }
    }

    private void a(Context context, String str, String str2, String str3, String str4, Intent intent) {
        AppsFlyerLib appsFlyerLib;
        Context applicationContext = context.getApplicationContext();
        boolean z2 = false;
        boolean z3 = str2 == null;
        if (AppsFlyerProperties.getInstance().getBoolean(AppsFlyerProperties.AF_WAITFOR_CUSTOMERID, false)) {
            if (AppsFlyerProperties.getInstance().getString(AppsFlyerProperties.APP_USER_ID) == null) {
                z2 = true;
            }
        }
        if (z2) {
            AFLogger.afInfoLog("CustomerUserId not set, Tracking is disabled", true);
            return;
        }
        if (z3) {
            if (!AppsFlyerProperties.getInstance().getBoolean(AppsFlyerProperties.LAUNCH_PROTECT_ENABLED, true)) {
                AFLogger.afInfoLog("Allowing multiple launches within a 5 second time window.");
            } else if (e()) {
                return;
            }
            appsFlyerLib = this;
            appsFlyerLib.j = System.currentTimeMillis();
        } else {
            appsFlyerLib = this;
        }
        ScheduledThreadPoolExecutor a2 = AFExecutor.getInstance().a();
        b bVar = new b(appsFlyerLib, new WeakReference(applicationContext), str, str2, str3, str4, a2, false, intent, 0);
        a((ScheduledExecutorService) a2, (Runnable) bVar, 150, TimeUnit.MILLISECONDS);
    }

    private boolean e() {
        if (this.j > 0) {
            long currentTimeMillis = System.currentTimeMillis() - this.j;
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS Z", Locale.US);
            long j2 = this.j;
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            String format = simpleDateFormat.format(new Date(j2));
            long j3 = this.k;
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            String format2 = simpleDateFormat.format(new Date(j3));
            if (currentTimeMillis < this.n && !isTrackingStopped()) {
                AFLogger.afInfoLog(String.format(Locale.US, "Last Launch attempt: %s;\nLast successful Launch event: %s;\nThis launch is blocked: %s ms < %s ms", new Object[]{format, format2, Long.valueOf(currentTimeMillis), Long.valueOf(this.n)}));
                return true;
            } else if (!isTrackingStopped()) {
                AFLogger.afInfoLog(String.format(Locale.US, "Last Launch attempt: %s;\nLast successful Launch event: %s;\nSending launch (+%s ms)", new Object[]{format, format2, Long.valueOf(currentTimeMillis)}));
            }
        } else if (!isTrackingStopped()) {
            AFLogger.afInfoLog("Sending first launch for this session!");
        }
        return false;
    }

    /* access modifiers changed from: 0000 */
    /* JADX WARNING: Code restructure failed: missing block: B:249:0x0644, code lost:
        if (r1.d != null) goto L_0x0646;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:281:0x06a9, code lost:
        if (r1.e != null) goto L_0x06ab;
     */
    /* JADX WARNING: Removed duplicated region for block: B:132:0x0426 A[Catch:{ Exception -> 0x00ac, Throwable -> 0x0be1 }] */
    /* JADX WARNING: Removed duplicated region for block: B:134:0x043a A[Catch:{ Exception -> 0x00ac, Throwable -> 0x0be1 }] */
    /* JADX WARNING: Removed duplicated region for block: B:140:0x0446 A[Catch:{ Exception -> 0x00ac, Throwable -> 0x0be1 }] */
    /* JADX WARNING: Removed duplicated region for block: B:142:0x044e A[Catch:{ Exception -> 0x00ac, Throwable -> 0x0be1 }] */
    /* JADX WARNING: Removed duplicated region for block: B:148:0x045a A[Catch:{ Exception -> 0x00ac, Throwable -> 0x0be1 }] */
    /* JADX WARNING: Removed duplicated region for block: B:150:0x0462 A[Catch:{ Exception -> 0x00ac, Throwable -> 0x0be1 }] */
    /* JADX WARNING: Removed duplicated region for block: B:156:0x046e A[Catch:{ Exception -> 0x00ac, Throwable -> 0x0be1 }] */
    /* JADX WARNING: Removed duplicated region for block: B:161:0x047d A[Catch:{ Exception -> 0x00ac, Throwable -> 0x0be1 }] */
    /* JADX WARNING: Removed duplicated region for block: B:162:0x047e A[Catch:{ Exception -> 0x00ac, Throwable -> 0x0be1 }] */
    /* JADX WARNING: Removed duplicated region for block: B:218:0x05bf A[Catch:{ Exception -> 0x00ac, Throwable -> 0x0be1 }] */
    /* JADX WARNING: Removed duplicated region for block: B:221:0x05d1 A[Catch:{ Exception -> 0x00ac, Throwable -> 0x0be1 }] */
    /* JADX WARNING: Removed duplicated region for block: B:222:0x05da A[Catch:{ Exception -> 0x00ac, Throwable -> 0x0be1 }] */
    /* JADX WARNING: Removed duplicated region for block: B:256:0x0651 A[Catch:{ Exception -> 0x00ac, Throwable -> 0x0be1 }] */
    /* JADX WARNING: Removed duplicated region for block: B:257:0x065c A[Catch:{ Exception -> 0x00ac, Throwable -> 0x0be1 }] */
    /* JADX WARNING: Removed duplicated region for block: B:260:0x0675 A[Catch:{ Exception -> 0x00ac, Throwable -> 0x0be1 }] */
    /* JADX WARNING: Removed duplicated region for block: B:283:0x06ae A[Catch:{ Exception -> 0x00ac, Throwable -> 0x0be1 }] */
    /* JADX WARNING: Removed duplicated region for block: B:288:0x06b6 A[Catch:{ Exception -> 0x00ac, Throwable -> 0x0be1 }] */
    /* JADX WARNING: Removed duplicated region for block: B:289:0x06c1 A[Catch:{ Exception -> 0x00ac, Throwable -> 0x0be1 }] */
    /* JADX WARNING: Removed duplicated region for block: B:293:0x06d1 A[Catch:{ Exception -> 0x06d7 }] */
    /* JADX WARNING: Removed duplicated region for block: B:322:0x074a A[SYNTHETIC, Splitter:B:322:0x074a] */
    /* JADX WARNING: Removed duplicated region for block: B:334:0x0791 A[Catch:{ Throwable -> 0x0830 }] */
    /* JADX WARNING: Removed duplicated region for block: B:337:0x07b5 A[Catch:{ Throwable -> 0x0830 }] */
    /* JADX WARNING: Removed duplicated region for block: B:353:0x0841 A[Catch:{ Exception -> 0x00ac, Throwable -> 0x0be1 }] */
    /* JADX WARNING: Removed duplicated region for block: B:356:0x0851 A[Catch:{ Exception -> 0x00ac, Throwable -> 0x0be1 }] */
    /* JADX WARNING: Removed duplicated region for block: B:359:0x0862 A[Catch:{ Exception -> 0x00ac, Throwable -> 0x0be1 }] */
    /* JADX WARNING: Removed duplicated region for block: B:362:0x088c A[Catch:{ Exception -> 0x00ac, Throwable -> 0x0be1 }] */
    /* JADX WARNING: Removed duplicated region for block: B:365:0x0897 A[Catch:{ Exception -> 0x00ac, Throwable -> 0x0be1 }] */
    /* JADX WARNING: Removed duplicated region for block: B:370:0x08b7 A[Catch:{ Exception -> 0x00ac, Throwable -> 0x0be1 }] */
    /* JADX WARNING: Removed duplicated region for block: B:382:0x08de A[Catch:{ Exception -> 0x00ac, Throwable -> 0x0be1 }] */
    /* JADX WARNING: Removed duplicated region for block: B:385:0x0919 A[ADDED_TO_REGION, Catch:{ Exception -> 0x00ac, Throwable -> 0x0be1 }] */
    /* JADX WARNING: Removed duplicated region for block: B:391:0x0928 A[Catch:{ Exception -> 0x00ac, Throwable -> 0x0be1 }] */
    /* JADX WARNING: Removed duplicated region for block: B:394:0x0961 A[Catch:{ Exception -> 0x00ac, Throwable -> 0x0be1 }] */
    /* JADX WARNING: Removed duplicated region for block: B:400:0x0989 A[Catch:{ Exception -> 0x00ac, Throwable -> 0x0be1 }] */
    /* JADX WARNING: Removed duplicated region for block: B:410:0x09ed A[Catch:{ Exception -> 0x00ac, Throwable -> 0x0be1 }] */
    /* JADX WARNING: Removed duplicated region for block: B:411:0x09ef A[Catch:{ Exception -> 0x00ac, Throwable -> 0x0be1 }] */
    /* JADX WARNING: Removed duplicated region for block: B:418:0x0a16 A[Catch:{ Exception -> 0x00ac, Throwable -> 0x0be1 }] */
    /* JADX WARNING: Removed duplicated region for block: B:421:0x0a5e A[Catch:{ Exception -> 0x00ac, Throwable -> 0x0be1 }] */
    /* JADX WARNING: Removed duplicated region for block: B:439:0x0bbd A[Catch:{ Exception -> 0x00ac, Throwable -> 0x0be1 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.util.Map<java.lang.String, java.lang.Object> a(android.content.Context r19, java.lang.String r20, java.lang.String r21, java.lang.String r22, java.lang.String r23, boolean r24, android.content.SharedPreferences r25, boolean r26, android.content.Intent r27) {
        /*
            r18 = this;
            r1 = r18
            r2 = r19
            r3 = r20
            r4 = r21
            r5 = r22
            r6 = r25
            r7 = r26
            java.util.HashMap r8 = new java.util.HashMap
            r8.<init>()
            com.appsflyer.o.a(r2, r8)
            java.util.Date r9 = new java.util.Date
            r9.<init>()
            long r9 = r9.getTime()
            java.lang.String r11 = "af_timestamp"
            java.lang.String r12 = java.lang.Long.toString(r9)
            r8.put(r11, r12)
            java.lang.String r9 = com.appsflyer.a.a(r2, r9)
            if (r9 == 0) goto L_0x0033
            java.lang.String r10 = "cksm_v1"
            r8.put(r10, r9)
        L_0x0033:
            boolean r9 = r18.isTrackingStopped()     // Catch:{ Throwable -> 0x0be1 }
            if (r9 != 0) goto L_0x0051
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0be1 }
            java.lang.String r10 = "******* sendTrackingWithEvent: "
            r9.<init>(r10)     // Catch:{ Throwable -> 0x0be1 }
            if (r7 == 0) goto L_0x0045
            java.lang.String r10 = "Launch"
            goto L_0x0046
        L_0x0045:
            r10 = r4
        L_0x0046:
            r9.append(r10)     // Catch:{ Throwable -> 0x0be1 }
            java.lang.String r9 = r9.toString()     // Catch:{ Throwable -> 0x0be1 }
            com.appsflyer.AFLogger.afInfoLog(r9)     // Catch:{ Throwable -> 0x0be1 }
            goto L_0x0056
        L_0x0051:
            java.lang.String r9 = "SDK tracking has been stopped"
            com.appsflyer.AFLogger.afInfoLog(r9)     // Catch:{ Throwable -> 0x0be1 }
        L_0x0056:
            java.lang.String r9 = "AppsFlyer_4.8.11"
            java.lang.String r10 = "EVENT_CREATED_WITH_NAME"
            if (r7 == 0) goto L_0x005f
            java.lang.String r11 = "Launch"
            goto L_0x0060
        L_0x005f:
            r11 = r4
        L_0x0060:
            a(r2, r9, r10, r11)     // Catch:{ Throwable -> 0x0be1 }
            com.appsflyer.cache.CacheManager r9 = com.appsflyer.cache.CacheManager.getInstance()     // Catch:{ Throwable -> 0x0be1 }
            r9.init(r2)     // Catch:{ Throwable -> 0x0be1 }
            r9 = 0
            android.content.pm.PackageManager r10 = r19.getPackageManager()     // Catch:{ Exception -> 0x00ac }
            java.lang.String r11 = r19.getPackageName()     // Catch:{ Exception -> 0x00ac }
            r12 = 4096(0x1000, float:5.74E-42)
            android.content.pm.PackageInfo r10 = r10.getPackageInfo(r11, r12)     // Catch:{ Exception -> 0x00ac }
            java.lang.String[] r10 = r10.requestedPermissions     // Catch:{ Exception -> 0x00ac }
            java.util.List r10 = java.util.Arrays.asList(r10)     // Catch:{ Exception -> 0x00ac }
            java.lang.String r11 = "android.permission.INTERNET"
            boolean r11 = r10.contains(r11)     // Catch:{ Exception -> 0x00ac }
            if (r11 != 0) goto L_0x0091
            java.lang.String r11 = "Permission android.permission.INTERNET is missing in the AndroidManifest.xml"
            com.appsflyer.AFLogger.afWarnLog(r11)     // Catch:{ Exception -> 0x00ac }
            java.lang.String r11 = "PERMISSION_INTERNET_MISSING"
            a(r2, r9, r11, r9)     // Catch:{ Exception -> 0x00ac }
        L_0x0091:
            java.lang.String r11 = "android.permission.ACCESS_NETWORK_STATE"
            boolean r11 = r10.contains(r11)     // Catch:{ Exception -> 0x00ac }
            if (r11 != 0) goto L_0x009e
            java.lang.String r11 = "Permission android.permission.ACCESS_NETWORK_STATE is missing in the AndroidManifest.xml"
            com.appsflyer.AFLogger.afWarnLog(r11)     // Catch:{ Exception -> 0x00ac }
        L_0x009e:
            java.lang.String r11 = "android.permission.ACCESS_WIFI_STATE"
            boolean r10 = r10.contains(r11)     // Catch:{ Exception -> 0x00ac }
            if (r10 != 0) goto L_0x00b3
            java.lang.String r10 = "Permission android.permission.ACCESS_WIFI_STATE is missing in the AndroidManifest.xml"
            com.appsflyer.AFLogger.afWarnLog(r10)     // Catch:{ Exception -> 0x00ac }
            goto L_0x00b3
        L_0x00ac:
            r0 = move-exception
            r10 = r0
            java.lang.String r11 = "Exception while validation permissions. "
            com.appsflyer.AFLogger.afErrorLog(r11, r10)     // Catch:{ Throwable -> 0x0be1 }
        L_0x00b3:
            if (r24 == 0) goto L_0x00bc
            java.lang.String r10 = "af_events_api"
            java.lang.String r11 = "1"
            r8.put(r10, r11)     // Catch:{ Throwable -> 0x0be1 }
        L_0x00bc:
            java.lang.String r10 = "brand"
            java.lang.String r11 = android.os.Build.BRAND     // Catch:{ Throwable -> 0x0be1 }
            r8.put(r10, r11)     // Catch:{ Throwable -> 0x0be1 }
            java.lang.String r10 = "device"
            java.lang.String r11 = android.os.Build.DEVICE     // Catch:{ Throwable -> 0x0be1 }
            r8.put(r10, r11)     // Catch:{ Throwable -> 0x0be1 }
            java.lang.String r10 = "product"
            java.lang.String r11 = android.os.Build.PRODUCT     // Catch:{ Throwable -> 0x0be1 }
            r8.put(r10, r11)     // Catch:{ Throwable -> 0x0be1 }
            java.lang.String r10 = "sdk"
            int r11 = android.os.Build.VERSION.SDK_INT     // Catch:{ Throwable -> 0x0be1 }
            java.lang.String r11 = java.lang.Integer.toString(r11)     // Catch:{ Throwable -> 0x0be1 }
            r8.put(r10, r11)     // Catch:{ Throwable -> 0x0be1 }
            java.lang.String r10 = "model"
            java.lang.String r11 = android.os.Build.MODEL     // Catch:{ Throwable -> 0x0be1 }
            r8.put(r10, r11)     // Catch:{ Throwable -> 0x0be1 }
            java.lang.String r10 = "deviceType"
            java.lang.String r11 = android.os.Build.TYPE     // Catch:{ Throwable -> 0x0be1 }
            r8.put(r10, r11)     // Catch:{ Throwable -> 0x0be1 }
            r13 = 0
            r15 = 1
            r12 = 0
            if (r7 == 0) goto L_0x027c
            java.lang.String r10 = "appsflyer-data"
            android.content.SharedPreferences r10 = r2.getSharedPreferences(r10, r12)     // Catch:{ Throwable -> 0x0be1 }
            java.lang.String r11 = "appsFlyerCount"
            boolean r10 = r10.contains(r11)     // Catch:{ Throwable -> 0x0be1 }
            r10 = r10 ^ r15
            if (r10 == 0) goto L_0x022e
            com.appsflyer.AppsFlyerProperties r10 = com.appsflyer.AppsFlyerProperties.getInstance()     // Catch:{ Throwable -> 0x0be1 }
            boolean r10 = r10.isOtherSdkStringDisabled()     // Catch:{ Throwable -> 0x0be1 }
            if (r10 != 0) goto L_0x0192
            java.lang.String r10 = "af_sdks"
            java.lang.StringBuilder r11 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0be1 }
            r11.<init>()     // Catch:{ Throwable -> 0x0be1 }
            java.lang.String r15 = "com.tune.Tune"
            com.appsflyer.t r9 = r1.G     // Catch:{ Throwable -> 0x0be1 }
            boolean r9 = r9.a(r15)     // Catch:{ Throwable -> 0x0be1 }
            r11.append(r9)     // Catch:{ Throwable -> 0x0be1 }
            java.lang.String r9 = "com.adjust.sdk.Adjust"
            com.appsflyer.t r15 = r1.G     // Catch:{ Throwable -> 0x0be1 }
            boolean r9 = r15.a(r9)     // Catch:{ Throwable -> 0x0be1 }
            r11.append(r9)     // Catch:{ Throwable -> 0x0be1 }
            java.lang.String r9 = "com.kochava.android.tracker.Feature"
            com.appsflyer.t r15 = r1.G     // Catch:{ Throwable -> 0x0be1 }
            boolean r9 = r15.a(r9)     // Catch:{ Throwable -> 0x0be1 }
            r11.append(r9)     // Catch:{ Throwable -> 0x0be1 }
            java.lang.String r9 = "io.branch.referral.Branch"
            com.appsflyer.t r15 = r1.G     // Catch:{ Throwable -> 0x0be1 }
            boolean r9 = r15.a(r9)     // Catch:{ Throwable -> 0x0be1 }
            r11.append(r9)     // Catch:{ Throwable -> 0x0be1 }
            java.lang.String r9 = "com.apsalar.sdk.Apsalar"
            com.appsflyer.t r15 = r1.G     // Catch:{ Throwable -> 0x0be1 }
            boolean r9 = r15.a(r9)     // Catch:{ Throwable -> 0x0be1 }
            r11.append(r9)     // Catch:{ Throwable -> 0x0be1 }
            java.lang.String r9 = "com.localytics.android.Localytics"
            com.appsflyer.t r15 = r1.G     // Catch:{ Throwable -> 0x0be1 }
            boolean r9 = r15.a(r9)     // Catch:{ Throwable -> 0x0be1 }
            r11.append(r9)     // Catch:{ Throwable -> 0x0be1 }
            java.lang.String r9 = "com.tenjin.android.TenjinSDK"
            com.appsflyer.t r15 = r1.G     // Catch:{ Throwable -> 0x0be1 }
            boolean r9 = r15.a(r9)     // Catch:{ Throwable -> 0x0be1 }
            r11.append(r9)     // Catch:{ Throwable -> 0x0be1 }
            java.lang.String r9 = "place holder for TD"
            com.appsflyer.t r15 = r1.G     // Catch:{ Throwable -> 0x0be1 }
            boolean r9 = r15.a(r9)     // Catch:{ Throwable -> 0x0be1 }
            r11.append(r9)     // Catch:{ Throwable -> 0x0be1 }
            java.lang.String r9 = "it.partytrack.sdk.Track"
            com.appsflyer.t r15 = r1.G     // Catch:{ Throwable -> 0x0be1 }
            boolean r9 = r15.a(r9)     // Catch:{ Throwable -> 0x0be1 }
            r11.append(r9)     // Catch:{ Throwable -> 0x0be1 }
            java.lang.String r9 = "jp.appAdForce.android.LtvManager"
            com.appsflyer.t r15 = r1.G     // Catch:{ Throwable -> 0x0be1 }
            boolean r9 = r15.a(r9)     // Catch:{ Throwable -> 0x0be1 }
            r11.append(r9)     // Catch:{ Throwable -> 0x0be1 }
            java.lang.String r9 = r11.toString()     // Catch:{ Throwable -> 0x0be1 }
            r8.put(r10, r9)     // Catch:{ Throwable -> 0x0be1 }
            float r9 = f(r19)     // Catch:{ Throwable -> 0x0be1 }
            java.lang.String r10 = "batteryLevel"
            java.lang.String r9 = java.lang.String.valueOf(r9)     // Catch:{ Throwable -> 0x0be1 }
            r8.put(r10, r9)     // Catch:{ Throwable -> 0x0be1 }
        L_0x0192:
            r9 = 18
            java.lang.String r10 = "OPPO"
            java.lang.String r11 = android.os.Build.BRAND     // Catch:{ Throwable -> 0x0be1 }
            boolean r10 = r10.equals(r11)     // Catch:{ Throwable -> 0x0be1 }
            if (r10 == 0) goto L_0x01a5
            r9 = 23
            java.lang.String r10 = "OPPO device found"
            com.appsflyer.AFLogger.afRDLog(r10)     // Catch:{ Throwable -> 0x0be1 }
        L_0x01a5:
            int r10 = android.os.Build.VERSION.SDK_INT     // Catch:{ Throwable -> 0x0be1 }
            if (r10 < r9) goto L_0x0216
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0be1 }
            java.lang.String r10 = "OS SDK is="
            r9.<init>(r10)     // Catch:{ Throwable -> 0x0be1 }
            int r10 = android.os.Build.VERSION.SDK_INT     // Catch:{ Throwable -> 0x0be1 }
            r9.append(r10)     // Catch:{ Throwable -> 0x0be1 }
            java.lang.String r10 = "; use KeyStore"
            r9.append(r10)     // Catch:{ Throwable -> 0x0be1 }
            java.lang.String r9 = r9.toString()     // Catch:{ Throwable -> 0x0be1 }
            com.appsflyer.AFLogger.afRDLog(r9)     // Catch:{ Throwable -> 0x0be1 }
            com.appsflyer.AFKeystoreWrapper r9 = new com.appsflyer.AFKeystoreWrapper     // Catch:{ Throwable -> 0x0be1 }
            r9.<init>(r2)     // Catch:{ Throwable -> 0x0be1 }
            boolean r10 = r9.b()     // Catch:{ Throwable -> 0x0be1 }
            if (r10 != 0) goto L_0x01f7
            java.lang.ref.WeakReference r10 = new java.lang.ref.WeakReference     // Catch:{ Throwable -> 0x0be1 }
            r10.<init>(r2)     // Catch:{ Throwable -> 0x0be1 }
            java.lang.String r10 = com.appsflyer.p.a(r10)     // Catch:{ Throwable -> 0x0be1 }
            r9.a(r10)     // Catch:{ Throwable -> 0x0be1 }
            java.lang.String r10 = "KSAppsFlyerId"
            java.lang.String r11 = r9.c()     // Catch:{ Throwable -> 0x0be1 }
            com.appsflyer.AppsFlyerProperties r15 = com.appsflyer.AppsFlyerProperties.getInstance()     // Catch:{ Throwable -> 0x0be1 }
            r15.set(r10, r11)     // Catch:{ Throwable -> 0x0be1 }
            java.lang.String r10 = "KSAppsFlyerRICounter"
            int r9 = r9.d()     // Catch:{ Throwable -> 0x0be1 }
            java.lang.String r9 = java.lang.String.valueOf(r9)     // Catch:{ Throwable -> 0x0be1 }
            com.appsflyer.AppsFlyerProperties r11 = com.appsflyer.AppsFlyerProperties.getInstance()     // Catch:{ Throwable -> 0x0be1 }
        L_0x01f3:
            r11.set(r10, r9)     // Catch:{ Throwable -> 0x0be1 }
            goto L_0x022e
        L_0x01f7:
            r9.a()     // Catch:{ Throwable -> 0x0be1 }
            java.lang.String r10 = "KSAppsFlyerId"
            java.lang.String r11 = r9.c()     // Catch:{ Throwable -> 0x0be1 }
            com.appsflyer.AppsFlyerProperties r15 = com.appsflyer.AppsFlyerProperties.getInstance()     // Catch:{ Throwable -> 0x0be1 }
            r15.set(r10, r11)     // Catch:{ Throwable -> 0x0be1 }
            java.lang.String r10 = "KSAppsFlyerRICounter"
            int r9 = r9.d()     // Catch:{ Throwable -> 0x0be1 }
            java.lang.String r9 = java.lang.String.valueOf(r9)     // Catch:{ Throwable -> 0x0be1 }
            com.appsflyer.AppsFlyerProperties r11 = com.appsflyer.AppsFlyerProperties.getInstance()     // Catch:{ Throwable -> 0x0be1 }
            goto L_0x01f3
        L_0x0216:
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0be1 }
            java.lang.String r10 = "OS SDK is="
            r9.<init>(r10)     // Catch:{ Throwable -> 0x0be1 }
            int r10 = android.os.Build.VERSION.SDK_INT     // Catch:{ Throwable -> 0x0be1 }
            r9.append(r10)     // Catch:{ Throwable -> 0x0be1 }
            java.lang.String r10 = "; no KeyStore usage"
            r9.append(r10)     // Catch:{ Throwable -> 0x0be1 }
            java.lang.String r9 = r9.toString()     // Catch:{ Throwable -> 0x0be1 }
            com.appsflyer.AFLogger.afRDLog(r9)     // Catch:{ Throwable -> 0x0be1 }
        L_0x022e:
            java.lang.String r9 = "timepassedsincelastlaunch"
            java.lang.String r10 = "appsflyer-data"
            android.content.SharedPreferences r10 = r2.getSharedPreferences(r10, r12)     // Catch:{ Throwable -> 0x0be1 }
            java.lang.String r11 = "AppsFlyerTimePassedSincePrevLaunch"
            long r10 = r10.getLong(r11, r13)     // Catch:{ Throwable -> 0x0be1 }
            long r12 = java.lang.System.currentTimeMillis()     // Catch:{ Throwable -> 0x0be1 }
            java.lang.String r14 = "AppsFlyerTimePassedSincePrevLaunch"
            b(r2, r14, r12)     // Catch:{ Throwable -> 0x0be1 }
            r14 = 0
            int r16 = (r10 > r14 ? 1 : (r10 == r14 ? 0 : -1))
            if (r16 <= 0) goto L_0x0252
            long r14 = r12 - r10
            r10 = 1000(0x3e8, double:4.94E-321)
            long r10 = r14 / r10
            goto L_0x0254
        L_0x0252:
            r10 = -1
        L_0x0254:
            java.lang.String r10 = java.lang.Long.toString(r10)     // Catch:{ Throwable -> 0x0be1 }
            r8.put(r9, r10)     // Catch:{ Throwable -> 0x0be1 }
            com.appsflyer.AppsFlyerProperties r9 = com.appsflyer.AppsFlyerProperties.getInstance()     // Catch:{ Throwable -> 0x0be1 }
            java.lang.String r10 = "oneLinkSlug"
            java.lang.String r9 = r9.getString(r10)     // Catch:{ Throwable -> 0x0be1 }
            if (r9 == 0) goto L_0x02f0
            java.lang.String r10 = "onelink_id"
            r8.put(r10, r9)     // Catch:{ Throwable -> 0x0be1 }
            java.lang.String r9 = "ol_ver"
            com.appsflyer.AppsFlyerProperties r10 = com.appsflyer.AppsFlyerProperties.getInstance()     // Catch:{ Throwable -> 0x0be1 }
            java.lang.String r11 = "onelinkVersion"
            java.lang.String r10 = r10.getString(r11)     // Catch:{ Throwable -> 0x0be1 }
            r8.put(r9, r10)     // Catch:{ Throwable -> 0x0be1 }
            goto L_0x02f0
        L_0x027c:
            java.lang.String r9 = "appsflyer-data"
            r10 = 0
            android.content.SharedPreferences r9 = r2.getSharedPreferences(r9, r10)     // Catch:{ Throwable -> 0x0be1 }
            android.content.SharedPreferences$Editor r10 = r9.edit()     // Catch:{ Throwable -> 0x0be1 }
            java.lang.String r11 = "prev_event_name"
            r12 = 0
            java.lang.String r11 = r9.getString(r11, r12)     // Catch:{ Exception -> 0x02e9 }
            if (r11 == 0) goto L_0x02c8
            org.json.JSONObject r12 = new org.json.JSONObject     // Catch:{ Exception -> 0x02e9 }
            r12.<init>()     // Catch:{ Exception -> 0x02e9 }
            java.lang.String r13 = "prev_event_timestamp"
            java.lang.StringBuilder r14 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x02e9 }
            r14.<init>()     // Catch:{ Exception -> 0x02e9 }
            java.lang.String r15 = "prev_event_timestamp"
            r6 = -1
            long r6 = r9.getLong(r15, r6)     // Catch:{ Exception -> 0x02e9 }
            r14.append(r6)     // Catch:{ Exception -> 0x02e9 }
            java.lang.String r6 = r14.toString()     // Catch:{ Exception -> 0x02e9 }
            r12.put(r13, r6)     // Catch:{ Exception -> 0x02e9 }
            java.lang.String r6 = "prev_event_value"
            java.lang.String r7 = "prev_event_value"
            r13 = 0
            java.lang.String r7 = r9.getString(r7, r13)     // Catch:{ Exception -> 0x02e9 }
            r12.put(r6, r7)     // Catch:{ Exception -> 0x02e9 }
            java.lang.String r6 = "prev_event_name"
            r12.put(r6, r11)     // Catch:{ Exception -> 0x02e9 }
            java.lang.String r6 = "prev_event"
            java.lang.String r7 = r12.toString()     // Catch:{ Exception -> 0x02e9 }
            r8.put(r6, r7)     // Catch:{ Exception -> 0x02e9 }
        L_0x02c8:
            java.lang.String r6 = "prev_event_name"
            r10.putString(r6, r4)     // Catch:{ Exception -> 0x02e9 }
            java.lang.String r6 = "prev_event_value"
            r10.putString(r6, r5)     // Catch:{ Exception -> 0x02e9 }
            java.lang.String r6 = "prev_event_timestamp"
            long r11 = java.lang.System.currentTimeMillis()     // Catch:{ Exception -> 0x02e9 }
            r10.putLong(r6, r11)     // Catch:{ Exception -> 0x02e9 }
            int r6 = android.os.Build.VERSION.SDK_INT     // Catch:{ Exception -> 0x02e9 }
            r7 = 9
            if (r6 < r7) goto L_0x02e5
            r10.apply()     // Catch:{ Exception -> 0x02e9 }
            goto L_0x02f0
        L_0x02e5:
            r10.commit()     // Catch:{ Exception -> 0x02e9 }
            goto L_0x02f0
        L_0x02e9:
            r0 = move-exception
            r6 = r0
            java.lang.String r7 = "Error while processing previous event."
            com.appsflyer.AFLogger.afErrorLog(r7, r6)     // Catch:{ Throwable -> 0x0be1 }
        L_0x02f0:
            java.lang.String r6 = "KSAppsFlyerId"
            com.appsflyer.AppsFlyerProperties r7 = com.appsflyer.AppsFlyerProperties.getInstance()     // Catch:{ Throwable -> 0x0be1 }
            java.lang.String r6 = r7.getString(r6)     // Catch:{ Throwable -> 0x0be1 }
            java.lang.String r7 = "KSAppsFlyerRICounter"
            com.appsflyer.AppsFlyerProperties r9 = com.appsflyer.AppsFlyerProperties.getInstance()     // Catch:{ Throwable -> 0x0be1 }
            java.lang.String r7 = r9.getString(r7)     // Catch:{ Throwable -> 0x0be1 }
            if (r6 == 0) goto L_0x031c
            if (r7 == 0) goto L_0x031c
            java.lang.Integer r9 = java.lang.Integer.valueOf(r7)     // Catch:{ Throwable -> 0x0be1 }
            int r9 = r9.intValue()     // Catch:{ Throwable -> 0x0be1 }
            if (r9 <= 0) goto L_0x031c
            java.lang.String r9 = "reinstallCounter"
            r8.put(r9, r7)     // Catch:{ Throwable -> 0x0be1 }
            java.lang.String r7 = "originalAppsflyerId"
            r8.put(r7, r6)     // Catch:{ Throwable -> 0x0be1 }
        L_0x031c:
            java.lang.String r6 = "additionalCustomData"
            com.appsflyer.AppsFlyerProperties r7 = com.appsflyer.AppsFlyerProperties.getInstance()     // Catch:{ Throwable -> 0x0be1 }
            java.lang.String r6 = r7.getString(r6)     // Catch:{ Throwable -> 0x0be1 }
            if (r6 == 0) goto L_0x032d
            java.lang.String r7 = "customData"
            r8.put(r7, r6)     // Catch:{ Throwable -> 0x0be1 }
        L_0x032d:
            android.content.pm.PackageManager r6 = r19.getPackageManager()     // Catch:{ Exception -> 0x0341 }
            java.lang.String r7 = r19.getPackageName()     // Catch:{ Exception -> 0x0341 }
            java.lang.String r6 = r6.getInstallerPackageName(r7)     // Catch:{ Exception -> 0x0341 }
            if (r6 == 0) goto L_0x0348
            java.lang.String r7 = "installer_package"
            r8.put(r7, r6)     // Catch:{ Exception -> 0x0341 }
            goto L_0x0348
        L_0x0341:
            r0 = move-exception
            r6 = r0
            java.lang.String r7 = "Exception while getting the app's installer package. "
            com.appsflyer.AFLogger.afErrorLog(r7, r6)     // Catch:{ Throwable -> 0x0be1 }
        L_0x0348:
            com.appsflyer.AppsFlyerProperties r6 = com.appsflyer.AppsFlyerProperties.getInstance()     // Catch:{ Throwable -> 0x0be1 }
            java.lang.String r7 = "sdkExtension"
            java.lang.String r6 = r6.getString(r7)     // Catch:{ Throwable -> 0x0be1 }
            if (r6 == 0) goto L_0x035f
            int r7 = r6.length()     // Catch:{ Throwable -> 0x0be1 }
            if (r7 <= 0) goto L_0x035f
            java.lang.String r7 = "sdkExtension"
            r8.put(r7, r6)     // Catch:{ Throwable -> 0x0be1 }
        L_0x035f:
            java.lang.ref.WeakReference r6 = new java.lang.ref.WeakReference     // Catch:{ Throwable -> 0x0be1 }
            r6.<init>(r2)     // Catch:{ Throwable -> 0x0be1 }
            com.appsflyer.AppsFlyerProperties r7 = com.appsflyer.AppsFlyerProperties.getInstance()     // Catch:{ Throwable -> 0x0be1 }
            java.lang.String r9 = "channel"
            java.lang.String r7 = r7.getString(r9)     // Catch:{ Throwable -> 0x0be1 }
            if (r7 != 0) goto L_0x0376
            java.lang.String r7 = "CHANNEL"
            java.lang.String r7 = a(r6, r7)     // Catch:{ Throwable -> 0x0be1 }
        L_0x0376:
            java.lang.String r6 = e(r2, r7)     // Catch:{ Throwable -> 0x0be1 }
            if (r6 == 0) goto L_0x0381
            java.lang.String r9 = "channel"
            r8.put(r9, r6)     // Catch:{ Throwable -> 0x0be1 }
        L_0x0381:
            if (r6 == 0) goto L_0x0389
            boolean r9 = r6.equals(r7)     // Catch:{ Throwable -> 0x0be1 }
            if (r9 == 0) goto L_0x038d
        L_0x0389:
            if (r6 != 0) goto L_0x0392
            if (r7 == 0) goto L_0x0392
        L_0x038d:
            java.lang.String r6 = "af_latestchannel"
            r8.put(r6, r7)     // Catch:{ Throwable -> 0x0be1 }
        L_0x0392:
            java.lang.String r6 = "appsflyer-data"
            r7 = 0
            android.content.SharedPreferences r6 = r2.getSharedPreferences(r6, r7)     // Catch:{ Throwable -> 0x0be1 }
            java.lang.String r7 = "INSTALL_STORE"
            boolean r7 = r6.contains(r7)     // Catch:{ Throwable -> 0x0be1 }
            if (r7 == 0) goto L_0x03a9
            java.lang.String r7 = "INSTALL_STORE"
            r9 = 0
            java.lang.String r6 = r6.getString(r7, r9)     // Catch:{ Throwable -> 0x0be1 }
            goto L_0x03cd
        L_0x03a9:
            java.lang.String r6 = "appsflyer-data"
            r7 = 0
            android.content.SharedPreferences r6 = r2.getSharedPreferences(r6, r7)     // Catch:{ Throwable -> 0x0be1 }
            java.lang.String r7 = "appsFlyerCount"
            boolean r6 = r6.contains(r7)     // Catch:{ Throwable -> 0x0be1 }
            r7 = 1
            r6 = r6 ^ r7
            if (r6 == 0) goto L_0x03c7
            java.lang.ref.WeakReference r6 = new java.lang.ref.WeakReference     // Catch:{ Throwable -> 0x0be1 }
            r6.<init>(r2)     // Catch:{ Throwable -> 0x0be1 }
            java.lang.String r7 = "AF_STORE"
            java.lang.String r9 = a(r6, r7)     // Catch:{ Throwable -> 0x0be1 }
            r6 = r9
            goto L_0x03c8
        L_0x03c7:
            r6 = 0
        L_0x03c8:
            java.lang.String r7 = "INSTALL_STORE"
            b(r2, r7, r6)     // Catch:{ Throwable -> 0x0be1 }
        L_0x03cd:
            if (r6 == 0) goto L_0x03d8
            java.lang.String r7 = "af_installstore"
            java.lang.String r6 = r6.toLowerCase()     // Catch:{ Throwable -> 0x0be1 }
            r8.put(r7, r6)     // Catch:{ Throwable -> 0x0be1 }
        L_0x03d8:
            java.lang.String r6 = "appsflyer-data"
            r7 = 0
            android.content.SharedPreferences r6 = r2.getSharedPreferences(r6, r7)     // Catch:{ Throwable -> 0x0be1 }
            java.lang.String r7 = "preInstallName"
            com.appsflyer.AppsFlyerProperties r9 = com.appsflyer.AppsFlyerProperties.getInstance()     // Catch:{ Throwable -> 0x0be1 }
            java.lang.String r7 = r9.getString(r7)     // Catch:{ Throwable -> 0x0be1 }
            if (r7 != 0) goto L_0x049b
            java.lang.String r9 = "preInstallName"
            boolean r9 = r6.contains(r9)     // Catch:{ Throwable -> 0x0be1 }
            if (r9 == 0) goto L_0x03fd
            java.lang.String r7 = "preInstallName"
            r9 = 0
            java.lang.String r6 = r6.getString(r7, r9)     // Catch:{ Throwable -> 0x0be1 }
            r7 = r6
            goto L_0x0490
        L_0x03fd:
            java.lang.String r6 = "appsflyer-data"
            r9 = 0
            android.content.SharedPreferences r6 = r2.getSharedPreferences(r6, r9)     // Catch:{ Throwable -> 0x0be1 }
            java.lang.String r9 = "appsFlyerCount"
            boolean r6 = r6.contains(r9)     // Catch:{ Throwable -> 0x0be1 }
            r9 = 1
            r6 = r6 ^ r9
            if (r6 == 0) goto L_0x0489
            java.lang.String r6 = "ro.appsflyer.preinstall.path"
            java.lang.String r6 = d(r6)     // Catch:{ Throwable -> 0x0be1 }
            java.io.File r6 = e(r6)     // Catch:{ Throwable -> 0x0be1 }
            if (r6 == 0) goto L_0x0423
            boolean r7 = r6.exists()     // Catch:{ Throwable -> 0x0be1 }
            if (r7 != 0) goto L_0x0421
            goto L_0x0423
        L_0x0421:
            r7 = 0
            goto L_0x0424
        L_0x0423:
            r7 = 1
        L_0x0424:
            if (r7 == 0) goto L_0x0438
            java.lang.String r6 = "AF_PRE_INSTALL_PATH"
            android.content.pm.PackageManager r7 = r19.getPackageManager()     // Catch:{ Throwable -> 0x0be1 }
            java.lang.String r9 = r19.getPackageName()     // Catch:{ Throwable -> 0x0be1 }
            java.lang.String r6 = a(r6, r7, r9)     // Catch:{ Throwable -> 0x0be1 }
            java.io.File r6 = e(r6)     // Catch:{ Throwable -> 0x0be1 }
        L_0x0438:
            if (r6 == 0) goto L_0x0443
            boolean r7 = r6.exists()     // Catch:{ Throwable -> 0x0be1 }
            if (r7 != 0) goto L_0x0441
            goto L_0x0443
        L_0x0441:
            r7 = 0
            goto L_0x0444
        L_0x0443:
            r7 = 1
        L_0x0444:
            if (r7 == 0) goto L_0x044c
            java.lang.String r6 = "/data/local/tmp/pre_install.appsflyer"
            java.io.File r6 = e(r6)     // Catch:{ Throwable -> 0x0be1 }
        L_0x044c:
            if (r6 == 0) goto L_0x0457
            boolean r7 = r6.exists()     // Catch:{ Throwable -> 0x0be1 }
            if (r7 != 0) goto L_0x0455
            goto L_0x0457
        L_0x0455:
            r7 = 0
            goto L_0x0458
        L_0x0457:
            r7 = 1
        L_0x0458:
            if (r7 == 0) goto L_0x0460
            java.lang.String r6 = "/etc/pre_install.appsflyer"
            java.io.File r6 = e(r6)     // Catch:{ Throwable -> 0x0be1 }
        L_0x0460:
            if (r6 == 0) goto L_0x046b
            boolean r7 = r6.exists()     // Catch:{ Throwable -> 0x0be1 }
            if (r7 != 0) goto L_0x0469
            goto L_0x046b
        L_0x0469:
            r7 = 0
            goto L_0x046c
        L_0x046b:
            r7 = 1
        L_0x046c:
            if (r7 != 0) goto L_0x047a
            java.lang.String r7 = r19.getPackageName()     // Catch:{ Throwable -> 0x0be1 }
            java.lang.String r9 = a(r6, r7)     // Catch:{ Throwable -> 0x0be1 }
            if (r9 == 0) goto L_0x047a
            r7 = r9
            goto L_0x047b
        L_0x047a:
            r7 = 0
        L_0x047b:
            if (r7 == 0) goto L_0x047e
            goto L_0x0489
        L_0x047e:
            java.lang.ref.WeakReference r6 = new java.lang.ref.WeakReference     // Catch:{ Throwable -> 0x0be1 }
            r6.<init>(r2)     // Catch:{ Throwable -> 0x0be1 }
            java.lang.String r7 = "AF_PRE_INSTALL_NAME"
            java.lang.String r7 = a(r6, r7)     // Catch:{ Throwable -> 0x0be1 }
        L_0x0489:
            if (r7 == 0) goto L_0x0490
            java.lang.String r6 = "preInstallName"
            b(r2, r6, r7)     // Catch:{ Throwable -> 0x0be1 }
        L_0x0490:
            if (r7 == 0) goto L_0x049b
            java.lang.String r6 = "preInstallName"
            com.appsflyer.AppsFlyerProperties r9 = com.appsflyer.AppsFlyerProperties.getInstance()     // Catch:{ Throwable -> 0x0be1 }
            r9.set(r6, r7)     // Catch:{ Throwable -> 0x0be1 }
        L_0x049b:
            if (r7 == 0) goto L_0x04a6
            java.lang.String r6 = "af_preinstall_name"
            java.lang.String r7 = r7.toLowerCase()     // Catch:{ Throwable -> 0x0be1 }
            r8.put(r6, r7)     // Catch:{ Throwable -> 0x0be1 }
        L_0x04a6:
            java.lang.ref.WeakReference r6 = new java.lang.ref.WeakReference     // Catch:{ Throwable -> 0x0be1 }
            r6.<init>(r2)     // Catch:{ Throwable -> 0x0be1 }
            java.lang.String r7 = "AF_STORE"
            java.lang.String r6 = a(r6, r7)     // Catch:{ Throwable -> 0x0be1 }
            if (r6 == 0) goto L_0x04bc
            java.lang.String r7 = "af_currentstore"
            java.lang.String r6 = r6.toLowerCase()     // Catch:{ Throwable -> 0x0be1 }
            r8.put(r7, r6)     // Catch:{ Throwable -> 0x0be1 }
        L_0x04bc:
            if (r3 == 0) goto L_0x04ca
            int r6 = r20.length()     // Catch:{ Throwable -> 0x0be1 }
            if (r6 < 0) goto L_0x04ca
            java.lang.String r6 = "appsflyerKey"
            r8.put(r6, r3)     // Catch:{ Throwable -> 0x0be1 }
            goto L_0x04e1
        L_0x04ca:
            java.lang.String r3 = "AppsFlyerKey"
            com.appsflyer.AppsFlyerProperties r6 = com.appsflyer.AppsFlyerProperties.getInstance()     // Catch:{ Throwable -> 0x0be1 }
            java.lang.String r3 = r6.getString(r3)     // Catch:{ Throwable -> 0x0be1 }
            if (r3 == 0) goto L_0x0bce
            int r6 = r3.length()     // Catch:{ Throwable -> 0x0be1 }
            if (r6 < 0) goto L_0x0bce
            java.lang.String r6 = "appsflyerKey"
            r8.put(r6, r3)     // Catch:{ Throwable -> 0x0be1 }
        L_0x04e1:
            java.lang.String r3 = "AppUserId"
            com.appsflyer.AppsFlyerProperties r6 = com.appsflyer.AppsFlyerProperties.getInstance()     // Catch:{ Throwable -> 0x0be1 }
            java.lang.String r3 = r6.getString(r3)     // Catch:{ Throwable -> 0x0be1 }
            if (r3 == 0) goto L_0x04f2
            java.lang.String r6 = "appUserId"
            r8.put(r6, r3)     // Catch:{ Throwable -> 0x0be1 }
        L_0x04f2:
            com.appsflyer.AppsFlyerProperties r3 = com.appsflyer.AppsFlyerProperties.getInstance()     // Catch:{ Throwable -> 0x0be1 }
            java.lang.String r6 = "userEmails"
            java.lang.String r3 = r3.getString(r6)     // Catch:{ Throwable -> 0x0be1 }
            if (r3 == 0) goto L_0x0504
            java.lang.String r6 = "user_emails"
            r8.put(r6, r3)     // Catch:{ Throwable -> 0x0be1 }
            goto L_0x0519
        L_0x0504:
            java.lang.String r3 = "userEmail"
            com.appsflyer.AppsFlyerProperties r6 = com.appsflyer.AppsFlyerProperties.getInstance()     // Catch:{ Throwable -> 0x0be1 }
            java.lang.String r3 = r6.getString(r3)     // Catch:{ Throwable -> 0x0be1 }
            if (r3 == 0) goto L_0x0519
            java.lang.String r6 = "sha1_el"
            java.lang.String r3 = com.appsflyer.C0096r.a(r3)     // Catch:{ Throwable -> 0x0be1 }
            r8.put(r6, r3)     // Catch:{ Throwable -> 0x0be1 }
        L_0x0519:
            if (r4 == 0) goto L_0x0527
            java.lang.String r3 = "eventName"
            r8.put(r3, r4)     // Catch:{ Throwable -> 0x0be1 }
            if (r5 == 0) goto L_0x0527
            java.lang.String r3 = "eventValue"
            r8.put(r3, r5)     // Catch:{ Throwable -> 0x0be1 }
        L_0x0527:
            java.lang.String r3 = "appid"
            com.appsflyer.AppsFlyerProperties r5 = com.appsflyer.AppsFlyerProperties.getInstance()     // Catch:{ Throwable -> 0x0be1 }
            java.lang.String r3 = r5.getString(r3)     // Catch:{ Throwable -> 0x0be1 }
            if (r3 == 0) goto L_0x0542
            java.lang.String r3 = "appid"
            java.lang.String r5 = "appid"
            com.appsflyer.AppsFlyerProperties r6 = com.appsflyer.AppsFlyerProperties.getInstance()     // Catch:{ Throwable -> 0x0be1 }
            java.lang.String r5 = r6.getString(r5)     // Catch:{ Throwable -> 0x0be1 }
            r8.put(r3, r5)     // Catch:{ Throwable -> 0x0be1 }
        L_0x0542:
            java.lang.String r3 = "currencyCode"
            com.appsflyer.AppsFlyerProperties r5 = com.appsflyer.AppsFlyerProperties.getInstance()     // Catch:{ Throwable -> 0x0be1 }
            java.lang.String r3 = r5.getString(r3)     // Catch:{ Throwable -> 0x0be1 }
            r5 = 3
            if (r3 == 0) goto L_0x0570
            int r6 = r3.length()     // Catch:{ Throwable -> 0x0be1 }
            if (r6 == r5) goto L_0x056b
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0be1 }
            java.lang.String r7 = "WARNING: currency code should be 3 characters!!! '"
            r6.<init>(r7)     // Catch:{ Throwable -> 0x0be1 }
            r6.append(r3)     // Catch:{ Throwable -> 0x0be1 }
            java.lang.String r7 = "' is not a legal value."
            r6.append(r7)     // Catch:{ Throwable -> 0x0be1 }
            java.lang.String r6 = r6.toString()     // Catch:{ Throwable -> 0x0be1 }
            com.appsflyer.AFLogger.afWarnLog(r6)     // Catch:{ Throwable -> 0x0be1 }
        L_0x056b:
            java.lang.String r6 = "currency"
            r8.put(r6, r3)     // Catch:{ Throwable -> 0x0be1 }
        L_0x0570:
            java.lang.String r3 = "IS_UPDATE"
            com.appsflyer.AppsFlyerProperties r6 = com.appsflyer.AppsFlyerProperties.getInstance()     // Catch:{ Throwable -> 0x0be1 }
            java.lang.String r3 = r6.getString(r3)     // Catch:{ Throwable -> 0x0be1 }
            if (r3 == 0) goto L_0x0581
            java.lang.String r6 = "isUpdate"
            r8.put(r6, r3)     // Catch:{ Throwable -> 0x0be1 }
        L_0x0581:
            boolean r3 = r18.isPreInstalledApp(r19)     // Catch:{ Throwable -> 0x0be1 }
            java.lang.String r6 = "af_preinstalled"
            java.lang.String r3 = java.lang.Boolean.toString(r3)     // Catch:{ Throwable -> 0x0be1 }
            r8.put(r6, r3)     // Catch:{ Throwable -> 0x0be1 }
            com.appsflyer.AppsFlyerProperties r3 = com.appsflyer.AppsFlyerProperties.getInstance()     // Catch:{ Throwable -> 0x0be1 }
            java.lang.String r6 = "collectFacebookAttrId"
            r7 = 1
            boolean r3 = r3.getBoolean(r6, r7)     // Catch:{ Throwable -> 0x0be1 }
            if (r3 == 0) goto L_0x05c4
            android.content.pm.PackageManager r3 = r19.getPackageManager()     // Catch:{ NameNotFoundException -> 0x05b7, Throwable -> 0x05ae }
            java.lang.String r6 = "com.facebook.katana"
            r7 = 0
            r3.getApplicationInfo(r6, r7)     // Catch:{ NameNotFoundException -> 0x05b7, Throwable -> 0x05ae }
            android.content.ContentResolver r3 = r19.getContentResolver()     // Catch:{ NameNotFoundException -> 0x05b7, Throwable -> 0x05ae }
            java.lang.String r9 = r1.getAttributionId(r3)     // Catch:{ NameNotFoundException -> 0x05b7, Throwable -> 0x05ae }
            goto L_0x05bd
        L_0x05ae:
            r0 = move-exception
            r3 = r0
            java.lang.String r6 = "Exception while collecting facebook's attribution ID. "
            com.appsflyer.AFLogger.afErrorLog(r6, r3)     // Catch:{ Throwable -> 0x0be1 }
        L_0x05b5:
            r9 = 0
            goto L_0x05bd
        L_0x05b7:
            java.lang.String r3 = "Exception while collecting facebook's attribution ID. "
            com.appsflyer.AFLogger.afWarnLog(r3)     // Catch:{ Throwable -> 0x0be1 }
            goto L_0x05b5
        L_0x05bd:
            if (r9 == 0) goto L_0x05c4
            java.lang.String r3 = "fb"
            r8.put(r3, r9)     // Catch:{ Throwable -> 0x0be1 }
        L_0x05c4:
            com.appsflyer.AppsFlyerProperties r3 = com.appsflyer.AppsFlyerProperties.getInstance()     // Catch:{ Throwable -> 0x0be1 }
            java.lang.String r6 = "deviceTrackingDisabled"
            r7 = 0
            boolean r3 = r3.getBoolean(r6, r7)     // Catch:{ Throwable -> 0x0be1 }
            if (r3 == 0) goto L_0x05da
            java.lang.String r3 = "deviceTrackingDisabled"
            java.lang.String r6 = "true"
            r8.put(r3, r6)     // Catch:{ Throwable -> 0x0be1 }
            goto L_0x06c6
        L_0x05da:
            java.lang.String r3 = "appsflyer-data"
            r6 = 0
            android.content.SharedPreferences r3 = r2.getSharedPreferences(r3, r6)     // Catch:{ Throwable -> 0x0be1 }
            com.appsflyer.AppsFlyerProperties r6 = com.appsflyer.AppsFlyerProperties.getInstance()     // Catch:{ Throwable -> 0x0be1 }
            java.lang.String r7 = "collectIMEI"
            r9 = 1
            boolean r6 = r6.getBoolean(r7, r9)     // Catch:{ Throwable -> 0x0be1 }
            java.lang.String r7 = "imeiCached"
            r9 = 0
            java.lang.String r7 = r3.getString(r7, r9)     // Catch:{ Throwable -> 0x0be1 }
            r9 = 19
            if (r6 == 0) goto L_0x0649
            int r6 = android.os.Build.VERSION.SDK_INT     // Catch:{ Throwable -> 0x0be1 }
            if (r6 < r9) goto L_0x0604
            boolean r6 = e(r19)     // Catch:{ Throwable -> 0x0be1 }
            if (r6 != 0) goto L_0x0602
            goto L_0x0604
        L_0x0602:
            r6 = 0
            goto L_0x0605
        L_0x0604:
            r6 = 1
        L_0x0605:
            if (r6 == 0) goto L_0x0642
            java.lang.String r6 = "phone"
            java.lang.Object r6 = r2.getSystemService(r6)     // Catch:{ InvocationTargetException -> 0x063c, Exception -> 0x0634 }
            android.telephony.TelephonyManager r6 = (android.telephony.TelephonyManager) r6     // Catch:{ InvocationTargetException -> 0x063c, Exception -> 0x0634 }
            java.lang.Class r10 = r6.getClass()     // Catch:{ InvocationTargetException -> 0x063c, Exception -> 0x0634 }
            java.lang.String r11 = "getDeviceId"
            r12 = 0
            java.lang.Class[] r13 = new java.lang.Class[r12]     // Catch:{ InvocationTargetException -> 0x063c, Exception -> 0x0634 }
            java.lang.reflect.Method r10 = r10.getMethod(r11, r13)     // Catch:{ InvocationTargetException -> 0x063c, Exception -> 0x0634 }
            java.lang.Object[] r11 = new java.lang.Object[r12]     // Catch:{ InvocationTargetException -> 0x063c, Exception -> 0x0634 }
            java.lang.Object r6 = r10.invoke(r6, r11)     // Catch:{ InvocationTargetException -> 0x063c, Exception -> 0x0634 }
            java.lang.String r6 = (java.lang.String) r6     // Catch:{ InvocationTargetException -> 0x063c, Exception -> 0x0634 }
            if (r6 == 0) goto L_0x0627
            goto L_0x064f
        L_0x0627:
            java.lang.String r6 = r1.d     // Catch:{ InvocationTargetException -> 0x063c, Exception -> 0x0634 }
            if (r6 == 0) goto L_0x062e
            java.lang.String r6 = r1.d     // Catch:{ InvocationTargetException -> 0x063c, Exception -> 0x0634 }
            goto L_0x064f
        L_0x062e:
            if (r7 == 0) goto L_0x0631
            goto L_0x0632
        L_0x0631:
            r7 = 0
        L_0x0632:
            r6 = r7
            goto L_0x064f
        L_0x0634:
            r0 = move-exception
            r6 = r0
            java.lang.String r7 = "WARNING: READ_PHONE_STATE is missing. "
            com.appsflyer.AFLogger.afErrorLog(r7, r6)     // Catch:{ Throwable -> 0x0be1 }
            goto L_0x064e
        L_0x063c:
            java.lang.String r6 = "WARNING: READ_PHONE_STATE is missing."
            com.appsflyer.AFLogger.afWarnLog(r6)     // Catch:{ Throwable -> 0x0be1 }
            goto L_0x064e
        L_0x0642:
            java.lang.String r6 = r1.d     // Catch:{ Throwable -> 0x0be1 }
            if (r6 == 0) goto L_0x064e
        L_0x0646:
            java.lang.String r6 = r1.d     // Catch:{ Throwable -> 0x0be1 }
            goto L_0x064f
        L_0x0649:
            java.lang.String r6 = r1.d     // Catch:{ Throwable -> 0x0be1 }
            if (r6 == 0) goto L_0x064e
            goto L_0x0646
        L_0x064e:
            r6 = 0
        L_0x064f:
            if (r6 == 0) goto L_0x065c
            java.lang.String r7 = "imeiCached"
            b(r2, r7, r6)     // Catch:{ Throwable -> 0x0be1 }
            java.lang.String r7 = "imei"
            r8.put(r7, r6)     // Catch:{ Throwable -> 0x0be1 }
            goto L_0x0661
        L_0x065c:
            java.lang.String r6 = "IMEI was not collected."
            com.appsflyer.AFLogger.afInfoLog(r6)     // Catch:{ Throwable -> 0x0be1 }
        L_0x0661:
            com.appsflyer.AppsFlyerProperties r6 = com.appsflyer.AppsFlyerProperties.getInstance()     // Catch:{ Throwable -> 0x0be1 }
            java.lang.String r7 = "collectAndroidId"
            r10 = 1
            boolean r6 = r6.getBoolean(r7, r10)     // Catch:{ Throwable -> 0x0be1 }
            java.lang.String r7 = "androidIdCached"
            r10 = 0
            java.lang.String r3 = r3.getString(r7, r10)     // Catch:{ Throwable -> 0x0be1 }
            if (r6 == 0) goto L_0x06ae
            int r6 = android.os.Build.VERSION.SDK_INT     // Catch:{ Throwable -> 0x0be1 }
            if (r6 < r9) goto L_0x0682
            boolean r6 = e(r19)     // Catch:{ Throwable -> 0x0be1 }
            if (r6 != 0) goto L_0x0680
            goto L_0x0682
        L_0x0680:
            r6 = 0
            goto L_0x0683
        L_0x0682:
            r6 = 1
        L_0x0683:
            if (r6 == 0) goto L_0x06a7
            android.content.ContentResolver r6 = r19.getContentResolver()     // Catch:{ Exception -> 0x069d }
            java.lang.String r7 = "android_id"
            java.lang.String r9 = android.provider.Settings.Secure.getString(r6, r7)     // Catch:{ Exception -> 0x069d }
            if (r9 == 0) goto L_0x0692
            goto L_0x06b4
        L_0x0692:
            java.lang.String r6 = r1.e     // Catch:{ Exception -> 0x069d }
            if (r6 == 0) goto L_0x0699
            java.lang.String r9 = r1.e     // Catch:{ Exception -> 0x069d }
            goto L_0x06b4
        L_0x0699:
            if (r3 == 0) goto L_0x06b3
            r9 = r3
            goto L_0x06b4
        L_0x069d:
            r0 = move-exception
            r3 = r0
            java.lang.String r6 = r3.getMessage()     // Catch:{ Throwable -> 0x0be1 }
            com.appsflyer.AFLogger.afErrorLog(r6, r3)     // Catch:{ Throwable -> 0x0be1 }
            goto L_0x06b3
        L_0x06a7:
            java.lang.String r3 = r1.e     // Catch:{ Throwable -> 0x0be1 }
            if (r3 == 0) goto L_0x06b3
        L_0x06ab:
            java.lang.String r9 = r1.e     // Catch:{ Throwable -> 0x0be1 }
            goto L_0x06b4
        L_0x06ae:
            java.lang.String r3 = r1.e     // Catch:{ Throwable -> 0x0be1 }
            if (r3 == 0) goto L_0x06b3
            goto L_0x06ab
        L_0x06b3:
            r9 = 0
        L_0x06b4:
            if (r9 == 0) goto L_0x06c1
            java.lang.String r3 = "androidIdCached"
            b(r2, r3, r9)     // Catch:{ Throwable -> 0x0be1 }
            java.lang.String r3 = "android_id"
            r8.put(r3, r9)     // Catch:{ Throwable -> 0x0be1 }
            goto L_0x06c6
        L_0x06c1:
            java.lang.String r3 = "Android ID was not collected."
            com.appsflyer.AFLogger.afInfoLog(r3)     // Catch:{ Throwable -> 0x0be1 }
        L_0x06c6:
            java.lang.ref.WeakReference r3 = new java.lang.ref.WeakReference     // Catch:{ Exception -> 0x06d7 }
            r3.<init>(r2)     // Catch:{ Exception -> 0x06d7 }
            java.lang.String r3 = com.appsflyer.p.a(r3)     // Catch:{ Exception -> 0x06d7 }
            if (r3 == 0) goto L_0x06ee
            java.lang.String r6 = "uid"
            r8.put(r6, r3)     // Catch:{ Exception -> 0x06d7 }
            goto L_0x06ee
        L_0x06d7:
            r0 = move-exception
            r3 = r0
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0be1 }
            java.lang.String r7 = "ERROR: could not get uid "
            r6.<init>(r7)     // Catch:{ Throwable -> 0x0be1 }
            java.lang.String r7 = r3.getMessage()     // Catch:{ Throwable -> 0x0be1 }
            r6.append(r7)     // Catch:{ Throwable -> 0x0be1 }
            java.lang.String r6 = r6.toString()     // Catch:{ Throwable -> 0x0be1 }
            com.appsflyer.AFLogger.afErrorLog(r6, r3)     // Catch:{ Throwable -> 0x0be1 }
        L_0x06ee:
            java.lang.String r3 = "lang"
            java.util.Locale r6 = java.util.Locale.getDefault()     // Catch:{ Exception -> 0x06fc }
            java.lang.String r6 = r6.getDisplayLanguage()     // Catch:{ Exception -> 0x06fc }
            r8.put(r3, r6)     // Catch:{ Exception -> 0x06fc }
            goto L_0x0703
        L_0x06fc:
            r0 = move-exception
            r3 = r0
            java.lang.String r6 = "Exception while collecting display language name. "
            com.appsflyer.AFLogger.afErrorLog(r6, r3)     // Catch:{ Throwable -> 0x0be1 }
        L_0x0703:
            java.lang.String r3 = "lang_code"
            java.util.Locale r6 = java.util.Locale.getDefault()     // Catch:{ Exception -> 0x0711 }
            java.lang.String r6 = r6.getLanguage()     // Catch:{ Exception -> 0x0711 }
            r8.put(r3, r6)     // Catch:{ Exception -> 0x0711 }
            goto L_0x0718
        L_0x0711:
            r0 = move-exception
            r3 = r0
            java.lang.String r6 = "Exception while collecting display language code. "
            com.appsflyer.AFLogger.afErrorLog(r6, r3)     // Catch:{ Throwable -> 0x0be1 }
        L_0x0718:
            java.lang.String r3 = "country"
            java.util.Locale r6 = java.util.Locale.getDefault()     // Catch:{ Exception -> 0x0726 }
            java.lang.String r6 = r6.getCountry()     // Catch:{ Exception -> 0x0726 }
            r8.put(r3, r6)     // Catch:{ Exception -> 0x0726 }
            goto L_0x072d
        L_0x0726:
            r0 = move-exception
            r3 = r0
            java.lang.String r6 = "Exception while collecting country name. "
            com.appsflyer.AFLogger.afErrorLog(r6, r3)     // Catch:{ Throwable -> 0x0be1 }
        L_0x072d:
            java.lang.String r3 = "platformextension"
            com.appsflyer.t r6 = r1.G     // Catch:{ Throwable -> 0x0be1 }
            java.lang.String r6 = r6.a()     // Catch:{ Throwable -> 0x0be1 }
            r8.put(r3, r6)     // Catch:{ Throwable -> 0x0be1 }
            a(r2, r8)     // Catch:{ Throwable -> 0x0be1 }
            java.lang.String r3 = "yyyy-MM-dd_HHmmssZ"
            java.text.SimpleDateFormat r6 = new java.text.SimpleDateFormat     // Catch:{ Throwable -> 0x0be1 }
            java.util.Locale r7 = java.util.Locale.US     // Catch:{ Throwable -> 0x0be1 }
            r6.<init>(r3, r7)     // Catch:{ Throwable -> 0x0be1 }
            int r3 = android.os.Build.VERSION.SDK_INT     // Catch:{ Throwable -> 0x0be1 }
            r7 = 9
            if (r3 < r7) goto L_0x0778
            android.content.pm.PackageManager r3 = r19.getPackageManager()     // Catch:{ Exception -> 0x0771 }
            java.lang.String r7 = r19.getPackageName()     // Catch:{ Exception -> 0x0771 }
            r9 = 0
            android.content.pm.PackageInfo r3 = r3.getPackageInfo(r7, r9)     // Catch:{ Exception -> 0x0771 }
            long r9 = r3.firstInstallTime     // Catch:{ Exception -> 0x0771 }
            java.lang.String r3 = "installDate"
            java.lang.String r7 = "UTC"
            java.util.TimeZone r7 = java.util.TimeZone.getTimeZone(r7)     // Catch:{ Exception -> 0x0771 }
            r6.setTimeZone(r7)     // Catch:{ Exception -> 0x0771 }
            java.util.Date r7 = new java.util.Date     // Catch:{ Exception -> 0x0771 }
            r7.<init>(r9)     // Catch:{ Exception -> 0x0771 }
            java.lang.String r7 = r6.format(r7)     // Catch:{ Exception -> 0x0771 }
            r8.put(r3, r7)     // Catch:{ Exception -> 0x0771 }
            goto L_0x0778
        L_0x0771:
            r0 = move-exception
            r3 = r0
            java.lang.String r7 = "Exception while collecting install date. "
            com.appsflyer.AFLogger.afErrorLog(r7, r3)     // Catch:{ Throwable -> 0x0be1 }
        L_0x0778:
            android.content.pm.PackageManager r3 = r19.getPackageManager()     // Catch:{ Throwable -> 0x0832 }
            java.lang.String r7 = r19.getPackageName()     // Catch:{ Throwable -> 0x0832 }
            r9 = 0
            android.content.pm.PackageInfo r3 = r3.getPackageInfo(r7, r9)     // Catch:{ Throwable -> 0x0832 }
            java.lang.String r7 = "versionCode"
            r10 = r25
            int r7 = r10.getInt(r7, r9)     // Catch:{ Throwable -> 0x0830 }
            int r11 = r3.versionCode     // Catch:{ Throwable -> 0x0830 }
            if (r11 <= r7) goto L_0x079d
            java.lang.String r7 = "appsflyerConversionDataRequestRetries"
            b(r2, r7, r9)     // Catch:{ Throwable -> 0x0830 }
            java.lang.String r7 = "versionCode"
            int r9 = r3.versionCode     // Catch:{ Throwable -> 0x0830 }
            b(r2, r7, r9)     // Catch:{ Throwable -> 0x0830 }
        L_0x079d:
            java.lang.String r7 = "app_version_code"
            int r9 = r3.versionCode     // Catch:{ Throwable -> 0x0830 }
            java.lang.String r9 = java.lang.Integer.toString(r9)     // Catch:{ Throwable -> 0x0830 }
            r8.put(r7, r9)     // Catch:{ Throwable -> 0x0830 }
            java.lang.String r7 = "app_version_name"
            java.lang.String r9 = r3.versionName     // Catch:{ Throwable -> 0x0830 }
            r8.put(r7, r9)     // Catch:{ Throwable -> 0x0830 }
            int r7 = android.os.Build.VERSION.SDK_INT     // Catch:{ Throwable -> 0x0830 }
            r9 = 9
            if (r7 < r9) goto L_0x083b
            long r11 = r3.firstInstallTime     // Catch:{ Throwable -> 0x0830 }
            long r13 = r3.lastUpdateTime     // Catch:{ Throwable -> 0x0830 }
            java.lang.String r3 = "date1"
            java.lang.String r7 = "UTC"
            java.util.TimeZone r7 = java.util.TimeZone.getTimeZone(r7)     // Catch:{ Throwable -> 0x0830 }
            r6.setTimeZone(r7)     // Catch:{ Throwable -> 0x0830 }
            java.util.Date r7 = new java.util.Date     // Catch:{ Throwable -> 0x0830 }
            r7.<init>(r11)     // Catch:{ Throwable -> 0x0830 }
            java.lang.String r7 = r6.format(r7)     // Catch:{ Throwable -> 0x0830 }
            r8.put(r3, r7)     // Catch:{ Throwable -> 0x0830 }
            java.lang.String r3 = "date2"
            java.lang.String r7 = "UTC"
            java.util.TimeZone r7 = java.util.TimeZone.getTimeZone(r7)     // Catch:{ Throwable -> 0x0830 }
            r6.setTimeZone(r7)     // Catch:{ Throwable -> 0x0830 }
            java.util.Date r7 = new java.util.Date     // Catch:{ Throwable -> 0x0830 }
            r7.<init>(r13)     // Catch:{ Throwable -> 0x0830 }
            java.lang.String r7 = r6.format(r7)     // Catch:{ Throwable -> 0x0830 }
            r8.put(r3, r7)     // Catch:{ Throwable -> 0x0830 }
            java.lang.String r3 = "appsflyer-data"
            r7 = 0
            android.content.SharedPreferences r3 = r2.getSharedPreferences(r3, r7)     // Catch:{ Throwable -> 0x0830 }
            java.lang.String r9 = "appsFlyerFirstInstall"
            r11 = 0
            java.lang.String r3 = r3.getString(r9, r11)     // Catch:{ Throwable -> 0x0830 }
            if (r3 != 0) goto L_0x081d
            java.lang.String r3 = "appsflyer-data"
            android.content.SharedPreferences r3 = r2.getSharedPreferences(r3, r7)     // Catch:{ Throwable -> 0x0830 }
            java.lang.String r7 = "appsFlyerCount"
            boolean r3 = r3.contains(r7)     // Catch:{ Throwable -> 0x0830 }
            r7 = 1
            r3 = r3 ^ r7
            if (r3 == 0) goto L_0x0816
            java.lang.String r3 = "AppsFlyer: first launch detected"
            com.appsflyer.AFLogger.afDebugLog(r3)     // Catch:{ Throwable -> 0x0830 }
            java.util.Date r3 = new java.util.Date     // Catch:{ Throwable -> 0x0830 }
            r3.<init>()     // Catch:{ Throwable -> 0x0830 }
            java.lang.String r3 = r6.format(r3)     // Catch:{ Throwable -> 0x0830 }
            goto L_0x0818
        L_0x0816:
            java.lang.String r3 = ""
        L_0x0818:
            java.lang.String r6 = "appsFlyerFirstInstall"
            b(r2, r6, r3)     // Catch:{ Throwable -> 0x0830 }
        L_0x081d:
            java.lang.String r6 = "AppsFlyer: first launch date: "
            java.lang.String r7 = java.lang.String.valueOf(r3)     // Catch:{ Throwable -> 0x0830 }
            java.lang.String r6 = r6.concat(r7)     // Catch:{ Throwable -> 0x0830 }
            com.appsflyer.AFLogger.afInfoLog(r6)     // Catch:{ Throwable -> 0x0830 }
            java.lang.String r6 = "firstLaunchDate"
            r8.put(r6, r3)     // Catch:{ Throwable -> 0x0830 }
            goto L_0x083b
        L_0x0830:
            r0 = move-exception
            goto L_0x0835
        L_0x0832:
            r0 = move-exception
            r10 = r25
        L_0x0835:
            r3 = r0
            java.lang.String r6 = "Exception while collecting app version data "
            com.appsflyer.AFLogger.afErrorLog(r6, r3)     // Catch:{ Throwable -> 0x0be1 }
        L_0x083b:
            int r3 = r23.length()     // Catch:{ Throwable -> 0x0be1 }
            if (r3 <= 0) goto L_0x0848
            java.lang.String r3 = "referrer"
            r6 = r23
            r8.put(r3, r6)     // Catch:{ Throwable -> 0x0be1 }
        L_0x0848:
            java.lang.String r3 = "extraReferrers"
            r6 = 0
            java.lang.String r3 = r10.getString(r3, r6)     // Catch:{ Throwable -> 0x0be1 }
            if (r3 == 0) goto L_0x0856
            java.lang.String r6 = "extraReferrers"
            r8.put(r6, r3)     // Catch:{ Throwable -> 0x0be1 }
        L_0x0856:
            java.lang.String r3 = "afUninstallToken"
            com.appsflyer.AppsFlyerProperties r6 = com.appsflyer.AppsFlyerProperties.getInstance()     // Catch:{ Throwable -> 0x0be1 }
            java.lang.String r3 = r6.getString(r3)     // Catch:{ Throwable -> 0x0be1 }
            if (r3 == 0) goto L_0x086f
            com.appsflyer.d r3 = com.appsflyer.d.a(r3)     // Catch:{ Throwable -> 0x0be1 }
            java.lang.String r6 = "af_gcm_token"
            java.lang.String r3 = r3.a()     // Catch:{ Throwable -> 0x0be1 }
            r8.put(r6, r3)     // Catch:{ Throwable -> 0x0be1 }
        L_0x086f:
            boolean r3 = com.appsflyer.u.a(r19)     // Catch:{ Throwable -> 0x0be1 }
            r1.E = r3     // Catch:{ Throwable -> 0x0be1 }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0be1 }
            java.lang.String r6 = "didConfigureTokenRefreshService="
            r3.<init>(r6)     // Catch:{ Throwable -> 0x0be1 }
            boolean r6 = r1.E     // Catch:{ Throwable -> 0x0be1 }
            r3.append(r6)     // Catch:{ Throwable -> 0x0be1 }
            java.lang.String r3 = r3.toString()     // Catch:{ Throwable -> 0x0be1 }
            com.appsflyer.AFLogger.afDebugLog(r3)     // Catch:{ Throwable -> 0x0be1 }
            boolean r3 = r1.E     // Catch:{ Throwable -> 0x0be1 }
            if (r3 != 0) goto L_0x0893
            java.lang.String r3 = "tokenRefreshConfigured"
            java.lang.Boolean r6 = java.lang.Boolean.FALSE     // Catch:{ Throwable -> 0x0be1 }
            r8.put(r3, r6)     // Catch:{ Throwable -> 0x0be1 }
        L_0x0893:
            r3 = r26
            if (r3 == 0) goto L_0x08b5
            java.lang.String r6 = r1.C     // Catch:{ Throwable -> 0x0be1 }
            if (r6 == 0) goto L_0x08b2
            org.json.JSONObject r6 = new org.json.JSONObject     // Catch:{ Throwable -> 0x0be1 }
            java.lang.String r7 = r1.C     // Catch:{ Throwable -> 0x0be1 }
            r6.<init>(r7)     // Catch:{ Throwable -> 0x0be1 }
            java.lang.String r7 = "isPush"
            java.lang.String r9 = "true"
            r6.put(r7, r9)     // Catch:{ Throwable -> 0x0be1 }
            java.lang.String r7 = "af_deeplink"
            java.lang.String r6 = r6.toString()     // Catch:{ Throwable -> 0x0be1 }
            r8.put(r7, r6)     // Catch:{ Throwable -> 0x0be1 }
        L_0x08b2:
            r6 = 0
            r1.C = r6     // Catch:{ Throwable -> 0x0be1 }
        L_0x08b5:
            if (r3 == 0) goto L_0x08da
            if (r27 == 0) goto L_0x08ca
            java.lang.String r7 = "android.intent.action.VIEW"
            java.lang.String r9 = r27.getAction()     // Catch:{ Throwable -> 0x0be1 }
            boolean r7 = r7.equals(r9)     // Catch:{ Throwable -> 0x0be1 }
            if (r7 == 0) goto L_0x08ca
            android.net.Uri r9 = r27.getData()     // Catch:{ Throwable -> 0x0be1 }
            goto L_0x08cb
        L_0x08ca:
            r9 = 0
        L_0x08cb:
            if (r9 == 0) goto L_0x08d1
            r1.a(r2, r8, r9)     // Catch:{ Throwable -> 0x0be1 }
            goto L_0x08da
        L_0x08d1:
            android.net.Uri r6 = r1.y     // Catch:{ Throwable -> 0x0be1 }
            if (r6 == 0) goto L_0x08da
            android.net.Uri r6 = r1.y     // Catch:{ Throwable -> 0x0be1 }
            r1.a(r2, r8, r6)     // Catch:{ Throwable -> 0x0be1 }
        L_0x08da:
            boolean r6 = r1.B     // Catch:{ Throwable -> 0x0be1 }
            if (r6 == 0) goto L_0x0902
            java.lang.String r6 = "testAppMode_retargeting"
            java.lang.String r7 = "true"
            r8.put(r6, r7)     // Catch:{ Throwable -> 0x0be1 }
            org.json.JSONObject r6 = new org.json.JSONObject     // Catch:{ Throwable -> 0x0be1 }
            r6.<init>(r8)     // Catch:{ Throwable -> 0x0be1 }
            java.lang.String r6 = r6.toString()     // Catch:{ Throwable -> 0x0be1 }
            android.content.Intent r7 = new android.content.Intent     // Catch:{ Throwable -> 0x0be1 }
            java.lang.String r9 = "com.appsflyer.testIntgrationBroadcast"
            r7.<init>(r9)     // Catch:{ Throwable -> 0x0be1 }
            java.lang.String r9 = "params"
            r7.putExtra(r9, r6)     // Catch:{ Throwable -> 0x0be1 }
            r2.sendBroadcast(r7)     // Catch:{ Throwable -> 0x0be1 }
            java.lang.String r6 = "Sent retargeting params to test app"
            com.appsflyer.AFLogger.afInfoLog(r6)     // Catch:{ Throwable -> 0x0be1 }
        L_0x0902:
            long r6 = java.lang.System.currentTimeMillis()     // Catch:{ Throwable -> 0x0be1 }
            long r11 = r1.z     // Catch:{ Throwable -> 0x0be1 }
            r9 = 0
            long r13 = r6 - r11
            com.appsflyer.AppsFlyerProperties r6 = com.appsflyer.AppsFlyerProperties.getInstance()     // Catch:{ Throwable -> 0x0be1 }
            java.lang.String r6 = r6.getReferrer(r2)     // Catch:{ Throwable -> 0x0be1 }
            r11 = 30000(0x7530, double:1.4822E-319)
            int r7 = (r13 > r11 ? 1 : (r13 == r11 ? 0 : -1))
            if (r7 > 0) goto L_0x0925
            if (r6 == 0) goto L_0x0925
            java.lang.String r7 = "AppsFlyer_Test"
            boolean r6 = r6.contains(r7)     // Catch:{ Throwable -> 0x0be1 }
            if (r6 == 0) goto L_0x0925
            r6 = 1
            goto L_0x0926
        L_0x0925:
            r6 = 0
        L_0x0926:
            if (r6 == 0) goto L_0x0955
            java.lang.String r6 = "testAppMode"
            java.lang.String r7 = "true"
            r8.put(r6, r7)     // Catch:{ Throwable -> 0x0be1 }
            org.json.JSONObject r6 = new org.json.JSONObject     // Catch:{ Throwable -> 0x0be1 }
            r6.<init>(r8)     // Catch:{ Throwable -> 0x0be1 }
            java.lang.String r6 = r6.toString()     // Catch:{ Throwable -> 0x0be1 }
            android.content.Intent r7 = new android.content.Intent     // Catch:{ Throwable -> 0x0be1 }
            java.lang.String r9 = "com.appsflyer.testIntgrationBroadcast"
            r7.<init>(r9)     // Catch:{ Throwable -> 0x0be1 }
            java.lang.String r9 = "params"
            r7.putExtra(r9, r6)     // Catch:{ Throwable -> 0x0be1 }
            r2.sendBroadcast(r7)     // Catch:{ Throwable -> 0x0be1 }
            java.lang.String r6 = "Sent params to test app"
            com.appsflyer.AFLogger.afInfoLog(r6)     // Catch:{ Throwable -> 0x0be1 }
            java.lang.String r6 = "Test mode ended!"
            com.appsflyer.AFLogger.afInfoLog(r6)     // Catch:{ Throwable -> 0x0be1 }
            r6 = 0
            r1.z = r6     // Catch:{ Throwable -> 0x0be1 }
        L_0x0955:
            java.lang.String r6 = "advertiserId"
            com.appsflyer.AppsFlyerProperties r7 = com.appsflyer.AppsFlyerProperties.getInstance()     // Catch:{ Throwable -> 0x0be1 }
            java.lang.String r6 = r7.getString(r6)     // Catch:{ Throwable -> 0x0be1 }
            if (r6 != 0) goto L_0x097f
            com.appsflyer.o.a(r2, r8)     // Catch:{ Throwable -> 0x0be1 }
            java.lang.String r6 = "advertiserId"
            com.appsflyer.AppsFlyerProperties r7 = com.appsflyer.AppsFlyerProperties.getInstance()     // Catch:{ Throwable -> 0x0be1 }
            java.lang.String r6 = r7.getString(r6)     // Catch:{ Throwable -> 0x0be1 }
            if (r6 == 0) goto L_0x0978
            java.lang.String r6 = "GAID_retry"
            java.lang.String r7 = "true"
            r8.put(r6, r7)     // Catch:{ Throwable -> 0x0be1 }
            goto L_0x097f
        L_0x0978:
            java.lang.String r6 = "GAID_retry"
            java.lang.String r7 = "false"
            r8.put(r6, r7)     // Catch:{ Throwable -> 0x0be1 }
        L_0x097f:
            android.content.ContentResolver r6 = r19.getContentResolver()     // Catch:{ Throwable -> 0x0be1 }
            com.appsflyer.n r6 = com.appsflyer.o.a(r6)     // Catch:{ Throwable -> 0x0be1 }
            if (r6 == 0) goto L_0x099f
            java.lang.String r7 = "amazon_aid"
            java.lang.String r9 = r6.a()     // Catch:{ Throwable -> 0x0be1 }
            r8.put(r7, r9)     // Catch:{ Throwable -> 0x0be1 }
            java.lang.String r7 = "amazon_aid_limit"
            boolean r6 = r6.b()     // Catch:{ Throwable -> 0x0be1 }
            java.lang.String r6 = java.lang.String.valueOf(r6)     // Catch:{ Throwable -> 0x0be1 }
            r8.put(r7, r6)     // Catch:{ Throwable -> 0x0be1 }
        L_0x099f:
            com.appsflyer.AppsFlyerProperties r6 = com.appsflyer.AppsFlyerProperties.getInstance()     // Catch:{ Throwable -> 0x0be1 }
            java.lang.String r6 = r6.getReferrer(r2)     // Catch:{ Throwable -> 0x0be1 }
            if (r6 == 0) goto L_0x09bc
            int r7 = r6.length()     // Catch:{ Throwable -> 0x0be1 }
            if (r7 <= 0) goto L_0x09bc
            java.lang.String r7 = "referrer"
            java.lang.Object r7 = r8.get(r7)     // Catch:{ Throwable -> 0x0be1 }
            if (r7 != 0) goto L_0x09bc
            java.lang.String r7 = "referrer"
            r8.put(r7, r6)     // Catch:{ Throwable -> 0x0be1 }
        L_0x09bc:
            java.lang.String r6 = "true"
            java.lang.String r7 = "sentSuccessfully"
            java.lang.String r9 = ""
            java.lang.String r7 = r10.getString(r7, r9)     // Catch:{ Throwable -> 0x0be1 }
            boolean r6 = r6.equals(r7)     // Catch:{ Throwable -> 0x0be1 }
            java.lang.String r7 = "sentRegisterRequestToAF"
            r9 = 0
            boolean r7 = r10.getBoolean(r7, r9)     // Catch:{ Throwable -> 0x0be1 }
            java.lang.String r9 = "registeredUninstall"
            java.lang.Boolean r7 = java.lang.Boolean.valueOf(r7)     // Catch:{ Throwable -> 0x0be1 }
            r8.put(r9, r7)     // Catch:{ Throwable -> 0x0be1 }
            java.lang.String r7 = "appsFlyerCount"
            int r7 = a(r10, r7, r3)     // Catch:{ Throwable -> 0x0be1 }
            java.lang.String r9 = "counter"
            java.lang.String r11 = java.lang.Integer.toString(r7)     // Catch:{ Throwable -> 0x0be1 }
            r8.put(r9, r11)     // Catch:{ Throwable -> 0x0be1 }
            java.lang.String r9 = "iaecounter"
            if (r4 == 0) goto L_0x09ef
            r4 = 1
            goto L_0x09f0
        L_0x09ef:
            r4 = 0
        L_0x09f0:
            java.lang.String r11 = "appsFlyerInAppEventCount"
            int r4 = a(r10, r11, r4)     // Catch:{ Throwable -> 0x0be1 }
            java.lang.String r4 = java.lang.Integer.toString(r4)     // Catch:{ Throwable -> 0x0be1 }
            r8.put(r9, r4)     // Catch:{ Throwable -> 0x0be1 }
            if (r3 == 0) goto L_0x0a20
            r4 = 1
            if (r7 != r4) goto L_0x0a20
            com.appsflyer.AppsFlyerProperties r4 = com.appsflyer.AppsFlyerProperties.getInstance()     // Catch:{ Throwable -> 0x0be1 }
            r4.setFirstLaunchCalled()     // Catch:{ Throwable -> 0x0be1 }
            java.lang.String r4 = "waitForCustomerId"
            com.appsflyer.AppsFlyerProperties r9 = com.appsflyer.AppsFlyerProperties.getInstance()     // Catch:{ Throwable -> 0x0be1 }
            r11 = 0
            boolean r4 = r9.getBoolean(r4, r11)     // Catch:{ Throwable -> 0x0be1 }
            if (r4 == 0) goto L_0x0a20
            java.lang.String r4 = "wait_cid"
            r9 = 1
            java.lang.String r11 = java.lang.Boolean.toString(r9)     // Catch:{ Throwable -> 0x0be1 }
            r8.put(r4, r11)     // Catch:{ Throwable -> 0x0be1 }
        L_0x0a20:
            java.lang.String r4 = "isFirstCall"
            r9 = 1
            r6 = r6 ^ r9
            java.lang.String r6 = java.lang.Boolean.toString(r6)     // Catch:{ Throwable -> 0x0be1 }
            r8.put(r4, r6)     // Catch:{ Throwable -> 0x0be1 }
            java.util.HashMap r4 = new java.util.HashMap     // Catch:{ Throwable -> 0x0be1 }
            r4.<init>()     // Catch:{ Throwable -> 0x0be1 }
            java.lang.String r6 = "cpu_abi"
            java.lang.String r9 = "ro.product.cpu.abi"
            java.lang.String r9 = d(r9)     // Catch:{ Throwable -> 0x0be1 }
            r4.put(r6, r9)     // Catch:{ Throwable -> 0x0be1 }
            java.lang.String r6 = "cpu_abi2"
            java.lang.String r9 = "ro.product.cpu.abi2"
            java.lang.String r9 = d(r9)     // Catch:{ Throwable -> 0x0be1 }
            r4.put(r6, r9)     // Catch:{ Throwable -> 0x0be1 }
            java.lang.String r6 = "arch"
            java.lang.String r9 = "os.arch"
            java.lang.String r9 = d(r9)     // Catch:{ Throwable -> 0x0be1 }
            r4.put(r6, r9)     // Catch:{ Throwable -> 0x0be1 }
            java.lang.String r6 = "build_display_id"
            java.lang.String r9 = "ro.build.display.id"
            java.lang.String r9 = d(r9)     // Catch:{ Throwable -> 0x0be1 }
            r4.put(r6, r9)     // Catch:{ Throwable -> 0x0be1 }
            if (r3 == 0) goto L_0x0ad9
            boolean r3 = r1.A     // Catch:{ Throwable -> 0x0be1 }
            if (r3 == 0) goto L_0x0aa1
            com.appsflyer.j r3 = com.appsflyer.j.d.a     // Catch:{ Throwable -> 0x0be1 }
            android.location.Location r3 = com.appsflyer.j.a(r19)     // Catch:{ Throwable -> 0x0be1 }
            java.util.HashMap r6 = new java.util.HashMap     // Catch:{ Throwable -> 0x0be1 }
            r6.<init>(r5)     // Catch:{ Throwable -> 0x0be1 }
            if (r3 == 0) goto L_0x0a96
            java.lang.String r5 = "lat"
            double r11 = r3.getLatitude()     // Catch:{ Throwable -> 0x0be1 }
            java.lang.String r9 = java.lang.String.valueOf(r11)     // Catch:{ Throwable -> 0x0be1 }
            r6.put(r5, r9)     // Catch:{ Throwable -> 0x0be1 }
            java.lang.String r5 = "lon"
            double r11 = r3.getLongitude()     // Catch:{ Throwable -> 0x0be1 }
            java.lang.String r9 = java.lang.String.valueOf(r11)     // Catch:{ Throwable -> 0x0be1 }
            r6.put(r5, r9)     // Catch:{ Throwable -> 0x0be1 }
            java.lang.String r5 = "ts"
            long r11 = r3.getTime()     // Catch:{ Throwable -> 0x0be1 }
            java.lang.String r3 = java.lang.String.valueOf(r11)     // Catch:{ Throwable -> 0x0be1 }
            r6.put(r5, r3)     // Catch:{ Throwable -> 0x0be1 }
        L_0x0a96:
            boolean r3 = r6.isEmpty()     // Catch:{ Throwable -> 0x0be1 }
            if (r3 != 0) goto L_0x0aa1
            java.lang.String r3 = "loc"
            r4.put(r3, r6)     // Catch:{ Throwable -> 0x0be1 }
        L_0x0aa1:
            com.appsflyer.c r3 = com.appsflyer.c.C0001c.a     // Catch:{ Throwable -> 0x0be1 }
            com.appsflyer.c$e r3 = r3.a(r2)     // Catch:{ Throwable -> 0x0be1 }
            java.lang.String r5 = "btl"
            float r6 = r3.a()     // Catch:{ Throwable -> 0x0be1 }
            java.lang.String r6 = java.lang.Float.toString(r6)     // Catch:{ Throwable -> 0x0be1 }
            r4.put(r5, r6)     // Catch:{ Throwable -> 0x0be1 }
            java.lang.String r5 = r3.b()     // Catch:{ Throwable -> 0x0be1 }
            if (r5 == 0) goto L_0x0ac3
            java.lang.String r5 = "btch"
            java.lang.String r3 = r3.b()     // Catch:{ Throwable -> 0x0be1 }
            r4.put(r5, r3)     // Catch:{ Throwable -> 0x0be1 }
        L_0x0ac3:
            r3 = 2
            if (r3 < r7) goto L_0x0ad9
            com.appsflyer.f r3 = com.appsflyer.f.a(r19)     // Catch:{ Throwable -> 0x0be1 }
            java.util.List r3 = r3.c()     // Catch:{ Throwable -> 0x0be1 }
            boolean r5 = r3.isEmpty()     // Catch:{ Throwable -> 0x0be1 }
            if (r5 != 0) goto L_0x0ad9
            java.lang.String r5 = "sensors"
            r4.put(r5, r3)     // Catch:{ Throwable -> 0x0be1 }
        L_0x0ad9:
            java.util.Map r2 = com.appsflyer.AFScreenManager.getScreenMetrics(r19)     // Catch:{ Throwable -> 0x0be1 }
            java.lang.String r3 = "dim"
            r4.put(r3, r2)     // Catch:{ Throwable -> 0x0be1 }
            java.lang.String r2 = "deviceData"
            r8.put(r2, r4)     // Catch:{ Throwable -> 0x0be1 }
            com.appsflyer.r r2 = new com.appsflyer.r     // Catch:{ Throwable -> 0x0be1 }
            r2.<init>()     // Catch:{ Throwable -> 0x0be1 }
            java.lang.String r2 = "appsflyerKey"
            java.lang.Object r2 = r8.get(r2)     // Catch:{ Throwable -> 0x0be1 }
            java.lang.String r2 = (java.lang.String) r2     // Catch:{ Throwable -> 0x0be1 }
            java.lang.String r3 = "af_timestamp"
            java.lang.Object r3 = r8.get(r3)     // Catch:{ Throwable -> 0x0be1 }
            java.lang.String r3 = (java.lang.String) r3     // Catch:{ Throwable -> 0x0be1 }
            java.lang.String r4 = "uid"
            java.lang.Object r4 = r8.get(r4)     // Catch:{ Throwable -> 0x0be1 }
            java.lang.String r4 = (java.lang.String) r4     // Catch:{ Throwable -> 0x0be1 }
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0be1 }
            r5.<init>()     // Catch:{ Throwable -> 0x0be1 }
            r6 = 7
            r7 = 0
            java.lang.String r2 = r2.substring(r7, r6)     // Catch:{ Throwable -> 0x0be1 }
            r5.append(r2)     // Catch:{ Throwable -> 0x0be1 }
            java.lang.String r2 = r4.substring(r7, r6)     // Catch:{ Throwable -> 0x0be1 }
            r5.append(r2)     // Catch:{ Throwable -> 0x0be1 }
            int r2 = r3.length()     // Catch:{ Throwable -> 0x0be1 }
            int r2 = r2 - r6
            java.lang.String r2 = r3.substring(r2)     // Catch:{ Throwable -> 0x0be1 }
            r5.append(r2)     // Catch:{ Throwable -> 0x0be1 }
            java.lang.String r2 = r5.toString()     // Catch:{ Throwable -> 0x0be1 }
            java.lang.String r2 = com.appsflyer.C0096r.a(r2)     // Catch:{ Throwable -> 0x0be1 }
            java.lang.String r3 = "af_v"
            r8.put(r3, r2)     // Catch:{ Throwable -> 0x0be1 }
            com.appsflyer.r r2 = new com.appsflyer.r     // Catch:{ Throwable -> 0x0be1 }
            r2.<init>()     // Catch:{ Throwable -> 0x0be1 }
            java.lang.String r2 = "appsflyerKey"
            java.lang.Object r2 = r8.get(r2)     // Catch:{ Throwable -> 0x0be1 }
            java.lang.String r2 = (java.lang.String) r2     // Catch:{ Throwable -> 0x0be1 }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0be1 }
            r3.<init>()     // Catch:{ Throwable -> 0x0be1 }
            r3.append(r2)     // Catch:{ Throwable -> 0x0be1 }
            java.lang.String r2 = "af_timestamp"
            java.lang.Object r2 = r8.get(r2)     // Catch:{ Throwable -> 0x0be1 }
            r3.append(r2)     // Catch:{ Throwable -> 0x0be1 }
            java.lang.String r2 = r3.toString()     // Catch:{ Throwable -> 0x0be1 }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0be1 }
            r3.<init>()     // Catch:{ Throwable -> 0x0be1 }
            r3.append(r2)     // Catch:{ Throwable -> 0x0be1 }
            java.lang.String r2 = "uid"
            java.lang.Object r2 = r8.get(r2)     // Catch:{ Throwable -> 0x0be1 }
            r3.append(r2)     // Catch:{ Throwable -> 0x0be1 }
            java.lang.String r2 = r3.toString()     // Catch:{ Throwable -> 0x0be1 }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0be1 }
            r3.<init>()     // Catch:{ Throwable -> 0x0be1 }
            r3.append(r2)     // Catch:{ Throwable -> 0x0be1 }
            java.lang.String r2 = "installDate"
            java.lang.Object r2 = r8.get(r2)     // Catch:{ Throwable -> 0x0be1 }
            r3.append(r2)     // Catch:{ Throwable -> 0x0be1 }
            java.lang.String r2 = r3.toString()     // Catch:{ Throwable -> 0x0be1 }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0be1 }
            r3.<init>()     // Catch:{ Throwable -> 0x0be1 }
            r3.append(r2)     // Catch:{ Throwable -> 0x0be1 }
            java.lang.String r2 = "counter"
            java.lang.Object r2 = r8.get(r2)     // Catch:{ Throwable -> 0x0be1 }
            r3.append(r2)     // Catch:{ Throwable -> 0x0be1 }
            java.lang.String r2 = r3.toString()     // Catch:{ Throwable -> 0x0be1 }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0be1 }
            r3.<init>()     // Catch:{ Throwable -> 0x0be1 }
            r3.append(r2)     // Catch:{ Throwable -> 0x0be1 }
            java.lang.String r2 = "iaecounter"
            java.lang.Object r2 = r8.get(r2)     // Catch:{ Throwable -> 0x0be1 }
            r3.append(r2)     // Catch:{ Throwable -> 0x0be1 }
            java.lang.String r2 = r3.toString()     // Catch:{ Throwable -> 0x0be1 }
            java.lang.String r2 = com.appsflyer.C0096r.b(r2)     // Catch:{ Throwable -> 0x0be1 }
            java.lang.String r2 = com.appsflyer.C0096r.a(r2)     // Catch:{ Throwable -> 0x0be1 }
            java.lang.String r3 = "af_v2"
            r8.put(r3, r2)     // Catch:{ Throwable -> 0x0be1 }
            java.lang.String r2 = "is_stop_tracking_used"
            boolean r2 = r10.contains(r2)     // Catch:{ Throwable -> 0x0be1 }
            if (r2 == 0) goto L_0x0bea
            java.lang.String r2 = "istu"
            java.lang.String r3 = "is_stop_tracking_used"
            r4 = 0
            boolean r3 = r10.getBoolean(r3, r4)     // Catch:{ Throwable -> 0x0be1 }
            java.lang.String r3 = java.lang.String.valueOf(r3)     // Catch:{ Throwable -> 0x0be1 }
            r8.put(r2, r3)     // Catch:{ Throwable -> 0x0be1 }
            goto L_0x0bea
        L_0x0bce:
            java.lang.String r3 = "AppsFlyer dev key is missing!!! Please use  AppsFlyerLib.getInstance().setAppsFlyerKey(...) to set it. "
            com.appsflyer.AFLogger.afInfoLog(r3)     // Catch:{ Throwable -> 0x0be1 }
            java.lang.String r3 = "AppsFlyer_4.8.11"
            java.lang.String r4 = "DEV_KEY_MISSING"
            r5 = 0
            a(r2, r3, r4, r5)     // Catch:{ Throwable -> 0x0be1 }
            java.lang.String r2 = "AppsFlyer will not track this event."
            com.appsflyer.AFLogger.afInfoLog(r2)     // Catch:{ Throwable -> 0x0be1 }
            return r5
        L_0x0be1:
            r0 = move-exception
            r2 = r0
            java.lang.String r3 = r2.getLocalizedMessage()
            com.appsflyer.AFLogger.afErrorLog(r3, r2)
        L_0x0bea:
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.appsflyer.AppsFlyerLib.a(android.content.Context, java.lang.String, java.lang.String, java.lang.String, java.lang.String, boolean, android.content.SharedPreferences, boolean, android.content.Intent):java.util.Map");
    }

    private static void a(Context context, Map<String, ? super String> map) {
        g gVar = c.a;
        a a2 = g.a(context);
        map.put("network", a2.a());
        if (a2.c() != null) {
            map.put("operator", a2.c());
        }
        if (a2.b() != null) {
            map.put("carrier", a2.b());
        }
    }

    private void a(Context context, Map<String, Object> map, Uri uri) {
        final Map map2;
        map.put("af_deeplink", uri.toString());
        if (uri.getQueryParameter("af_deeplink") != null) {
            this.B = "AppsFlyer_Test".equals(uri.getQueryParameter("media_source")) && Boolean.parseBoolean(uri.getQueryParameter("is_retargeting"));
            map2 = d(context, uri.getQuery());
            String str = ClientCookie.PATH_ATTR;
            String path = uri.getPath();
            if (path != null) {
                map2.put(str, path);
            }
            String str2 = "scheme";
            String scheme = uri.getScheme();
            if (scheme != null) {
                map2.put(str2, scheme);
            }
            String str3 = "host";
            String host = uri.getHost();
            if (host != null) {
                map2.put(str3, host);
            }
        } else {
            map2 = new HashMap();
            map2.put("link", uri.toString());
        }
        final WeakReference weakReference = new WeakReference(context);
        s sVar = new s(uri, this);
        sVar.setConnProvider(new HttpsUrlConnectionProvider());
        if (sVar.c()) {
            sVar.a((b) new b() {
                public final void a(String str) {
                    if (AppsFlyerLib.o != null) {
                        b(map2);
                        AppsFlyerLib.o.onAttributionFailure(str);
                    }
                }

                private void b(Map<String, String> map) {
                    if (weakReference.get() != null) {
                        AppsFlyerLib.b((Context) weakReference.get(), "deeplinkAttribution", new JSONObject(map).toString());
                    }
                }

                public final void a(Map<String, String> map) {
                    for (String str : map.keySet()) {
                        map2.put(str, map.get(str));
                    }
                    b(map2);
                    AppsFlyerLib.a(map2);
                }
            });
            AFExecutor.getInstance().getThreadPoolExecutor().execute(sVar);
            return;
        }
        if (o != null) {
            try {
                o.onAppOpenAttribution(map2);
            } catch (Throwable th) {
                AFLogger.afErrorLog(th.getLocalizedMessage(), th);
            }
        }
    }

    private static boolean e(Context context) {
        try {
            if (GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(context) == 0) {
                return true;
            }
        } catch (Throwable th) {
            AFLogger.afErrorLog("WARNING:  Google play services is unavailable. ", th);
        }
        try {
            context.getPackageManager().getPackageInfo("com.google.android.gms", 0);
            return true;
        } catch (NameNotFoundException e2) {
            AFLogger.afErrorLog("WARNING:  Google Play Services is unavailable. ", e2);
            return false;
        }
    }

    private static String d(String str) {
        try {
            return (String) Class.forName("android.os.SystemProperties").getMethod("get", new Class[]{String.class}).invoke(null, new Object[]{str});
        } catch (Throwable th) {
            AFLogger.afErrorLog(th.getMessage(), th);
            return null;
        }
    }

    @Nullable
    private static String a(WeakReference<Context> weakReference, String str) {
        if (weakReference.get() == null) {
            return null;
        }
        return a(str, ((Context) weakReference.get()).getPackageManager(), ((Context) weakReference.get()).getPackageName());
    }

    @Nullable
    private static String a(String str, PackageManager packageManager, String str2) {
        try {
            Bundle bundle = packageManager.getApplicationInfo(str2, 128).metaData;
            if (bundle == null) {
                return null;
            }
            Object obj = bundle.get(str);
            if (obj != null) {
                return obj.toString();
            }
            return null;
        } catch (Throwable th) {
            StringBuilder sb = new StringBuilder("Could not find ");
            sb.append(str);
            sb.append(" value in the manifest");
            AFLogger.afErrorLog(sb.toString(), th);
            return null;
        }
    }

    public void setPreinstallAttribution(String str, String str2, String str3) {
        AFLogger.afDebugLog("setPreinstallAttribution API called");
        JSONObject jSONObject = new JSONObject();
        if (str != null) {
            try {
                jSONObject.put(com.appsflyer.share.Constants.URL_MEDIA_SOURCE, str);
            } catch (JSONException e2) {
                AFLogger.afErrorLog(e2.getMessage(), e2);
            }
        }
        if (str2 != null) {
            jSONObject.put(com.appsflyer.share.Constants.URL_CAMPAIGN, str2);
        }
        if (str3 != null) {
            jSONObject.put(com.appsflyer.share.Constants.URL_SITE_ID, str3);
        }
        if (jSONObject.has(com.appsflyer.share.Constants.URL_MEDIA_SOURCE)) {
            String jSONObject2 = jSONObject.toString();
            AppsFlyerProperties.getInstance().set("preInstallName", jSONObject2);
            return;
        }
        AFLogger.afWarnLog("Cannot set preinstall attribution data without a media source");
    }

    /* JADX WARNING: Missing exception handler attribute for start block: B:24:0x0042 */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x0034 A[SYNTHETIC, Splitter:B:18:0x0034] */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x0060 A[SYNTHETIC, Splitter:B:32:0x0060] */
    /* JADX WARNING: Unknown top exception splitter block from list: {B:24:0x0042=Splitter:B:24:0x0042, B:15:0x002b=Splitter:B:15:0x002b} */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.lang.String a(java.io.File r4, java.lang.String r5) {
        /*
            r0 = 0
            java.util.Properties r1 = new java.util.Properties     // Catch:{ FileNotFoundException -> 0x0041, Throwable -> 0x0029, all -> 0x0026 }
            r1.<init>()     // Catch:{ FileNotFoundException -> 0x0041, Throwable -> 0x0029, all -> 0x0026 }
            java.io.FileReader r2 = new java.io.FileReader     // Catch:{ FileNotFoundException -> 0x0041, Throwable -> 0x0029, all -> 0x0026 }
            r2.<init>(r4)     // Catch:{ FileNotFoundException -> 0x0041, Throwable -> 0x0029, all -> 0x0026 }
            r1.load(r2)     // Catch:{ FileNotFoundException -> 0x0042, Throwable -> 0x0024 }
            java.lang.String r3 = "Found PreInstall property!"
            com.appsflyer.AFLogger.afInfoLog(r3)     // Catch:{ FileNotFoundException -> 0x0042, Throwable -> 0x0024 }
            java.lang.String r5 = r1.getProperty(r5)     // Catch:{ FileNotFoundException -> 0x0042, Throwable -> 0x0024 }
            r2.close()     // Catch:{ Throwable -> 0x001b }
            goto L_0x0023
        L_0x001b:
            r4 = move-exception
            java.lang.String r0 = r4.getMessage()
            com.appsflyer.AFLogger.afErrorLog(r0, r4)
        L_0x0023:
            return r5
        L_0x0024:
            r4 = move-exception
            goto L_0x002b
        L_0x0026:
            r4 = move-exception
            r2 = r0
            goto L_0x005e
        L_0x0029:
            r4 = move-exception
            r2 = r0
        L_0x002b:
            java.lang.String r5 = r4.getMessage()     // Catch:{ all -> 0x005d }
            com.appsflyer.AFLogger.afErrorLog(r5, r4)     // Catch:{ all -> 0x005d }
            if (r2 == 0) goto L_0x005c
            r2.close()     // Catch:{ Throwable -> 0x0038 }
            goto L_0x005c
        L_0x0038:
            r4 = move-exception
            java.lang.String r5 = r4.getMessage()
            com.appsflyer.AFLogger.afErrorLog(r5, r4)
            goto L_0x005c
        L_0x0041:
            r2 = r0
        L_0x0042:
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ all -> 0x005d }
            java.lang.String r1 = "PreInstall file wasn't found: "
            r5.<init>(r1)     // Catch:{ all -> 0x005d }
            java.lang.String r4 = r4.getAbsolutePath()     // Catch:{ all -> 0x005d }
            r5.append(r4)     // Catch:{ all -> 0x005d }
            java.lang.String r4 = r5.toString()     // Catch:{ all -> 0x005d }
            com.appsflyer.AFLogger.afDebugLog(r4)     // Catch:{ all -> 0x005d }
            if (r2 == 0) goto L_0x005c
            r2.close()     // Catch:{ Throwable -> 0x0038 }
        L_0x005c:
            return r0
        L_0x005d:
            r4 = move-exception
        L_0x005e:
            if (r2 == 0) goto L_0x006c
            r2.close()     // Catch:{ Throwable -> 0x0064 }
            goto L_0x006c
        L_0x0064:
            r5 = move-exception
            java.lang.String r0 = r5.getMessage()
            com.appsflyer.AFLogger.afErrorLog(r0, r5)
        L_0x006c:
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.appsflyer.AppsFlyerLib.a(java.io.File, java.lang.String):java.lang.String");
    }

    private static File e(String str) {
        if (str != null) {
            try {
                if (str.trim().length() > 0) {
                    return new File(str.trim());
                }
            } catch (Throwable th) {
                AFLogger.afErrorLog(th.getMessage(), th);
            }
        }
        return null;
    }

    public boolean isPreInstalledApp(Context context) {
        try {
            if ((context.getPackageManager().getApplicationInfo(context.getPackageName(), 0).flags & 1) != 0) {
                return true;
            }
            return false;
        } catch (NameNotFoundException e2) {
            AFLogger.afErrorLog("Could not check if app is pre installed", e2);
        }
    }

    /* access modifiers changed from: private */
    public static String e(Context context, String str) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("appsflyer-data", 0);
        if (sharedPreferences.contains("CACHED_CHANNEL")) {
            return sharedPreferences.getString("CACHED_CHANNEL", null);
        }
        b(context, "CACHED_CHANNEL", str);
        return str;
    }

    public String getAttributionId(ContentResolver contentResolver) {
        ContentResolver contentResolver2 = contentResolver;
        Cursor query = contentResolver2.query(Uri.parse(ATTRIBUTION_ID_CONTENT_URI), new String[]{"aid"}, null, null, null);
        String str = null;
        if (query != null) {
            try {
                if (query.moveToFirst()) {
                    String string = query.getString(query.getColumnIndex("aid"));
                    if (query != null) {
                        try {
                            query.close();
                        } catch (Exception e2) {
                            AFLogger.afErrorLog(e2.getMessage(), e2);
                        }
                    }
                    str = string;
                    return str;
                }
            } catch (Exception e3) {
                AFLogger.afErrorLog("Could not collect cursor attribution. ", e3);
                if (query != null) {
                    try {
                        query.close();
                    } catch (Exception e4) {
                        AFLogger.afErrorLog(e4.getMessage(), e4);
                    }
                }
            } finally {
                if (query != null) {
                    try {
                        query.close();
                    } catch (Exception e5) {
                        AFLogger.afErrorLog(e5.getMessage(), e5);
                    }
                }
            }
        }
        if (query != null) {
            try {
                query.close();
            } catch (Exception e6) {
                AFLogger.afErrorLog(e6.getMessage(), e6);
            }
        }
        return null;
    }

    static SharedPreferences a(Context context) {
        return context.getSharedPreferences("appsflyer-data", 0);
    }

    static int a(SharedPreferences sharedPreferences) {
        return a(sharedPreferences, "appsFlyerCount", false);
    }

    private static int a(SharedPreferences sharedPreferences, String str, boolean z2) {
        int i2 = sharedPreferences.getInt(str, 0);
        if (z2) {
            i2++;
            Editor edit = sharedPreferences.edit();
            edit.putInt(str, i2);
            if (VERSION.SDK_INT >= 9) {
                edit.apply();
            } else {
                edit.commit();
            }
        }
        if (y.a().g()) {
            y.a().a(String.valueOf(i2));
        }
        return i2;
    }

    public String getAppsFlyerUID(Context context) {
        y.a().a("getAppsFlyerUID", new String[0]);
        return p.a(new WeakReference<>(context));
    }

    /* JADX WARNING: Removed duplicated region for block: B:105:0x0213  */
    /* JADX WARNING: Removed duplicated region for block: B:97:0x0207 A[SYNTHETIC, Splitter:B:97:0x0207] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void a(java.net.URL r21, java.lang.String r22, java.lang.String r23, java.lang.ref.WeakReference<android.content.Context> r24, java.lang.String r25, boolean r26) {
        /*
            r20 = this;
            r1 = r20
            r2 = r22
            r3 = r23
            r4 = r25
            java.lang.Object r6 = r24.get()
            android.content.Context r6 = (android.content.Context) r6
            r7 = 1
            r8 = 0
            if (r26 == 0) goto L_0x0018
            com.appsflyer.AppsFlyerConversionListener r9 = o
            if (r9 == 0) goto L_0x0018
            r9 = 1
            goto L_0x0019
        L_0x0018:
            r9 = 0
        L_0x0019:
            r10 = 0
            com.appsflyer.y r11 = com.appsflyer.y.a()     // Catch:{ all -> 0x020e }
            java.lang.String r12 = r21.toString()     // Catch:{ all -> 0x020e }
            r11.a(r12, r2)     // Catch:{ all -> 0x020e }
            java.net.URLConnection r11 = r21.openConnection()     // Catch:{ all -> 0x020e }
            java.net.HttpURLConnection r11 = (java.net.HttpURLConnection) r11     // Catch:{ all -> 0x020e }
            java.lang.String r12 = "POST"
            r11.setRequestMethod(r12)     // Catch:{ all -> 0x020b }
            byte[] r12 = r22.getBytes()     // Catch:{ all -> 0x020b }
            int r12 = r12.length     // Catch:{ all -> 0x020b }
            java.lang.String r13 = "Content-Length"
            java.lang.String r12 = java.lang.String.valueOf(r12)     // Catch:{ all -> 0x020b }
            r11.setRequestProperty(r13, r12)     // Catch:{ all -> 0x020b }
            java.lang.String r12 = "Content-Type"
            java.lang.String r13 = "application/json"
            r11.setRequestProperty(r12, r13)     // Catch:{ all -> 0x020b }
            r12 = 10000(0x2710, float:1.4013E-41)
            r11.setConnectTimeout(r12)     // Catch:{ all -> 0x020b }
            r11.setDoOutput(r7)     // Catch:{ all -> 0x020b }
            java.io.OutputStreamWriter r12 = new java.io.OutputStreamWriter     // Catch:{ all -> 0x0203 }
            java.io.OutputStream r13 = r11.getOutputStream()     // Catch:{ all -> 0x0203 }
            java.lang.String r14 = "UTF-8"
            r12.<init>(r13, r14)     // Catch:{ all -> 0x0203 }
            r12.write(r2)     // Catch:{ all -> 0x01ff }
            r12.close()     // Catch:{ all -> 0x020b }
            int r2 = r11.getResponseCode()     // Catch:{ all -> 0x020b }
            java.lang.String r12 = a(r11)     // Catch:{ all -> 0x020b }
            com.appsflyer.y r13 = com.appsflyer.y.a()     // Catch:{ all -> 0x020b }
            java.lang.String r14 = r21.toString()     // Catch:{ all -> 0x020b }
            r13.a(r14, r2, r12)     // Catch:{ all -> 0x020b }
            java.lang.String r13 = "response code: "
            java.lang.String r14 = java.lang.String.valueOf(r2)     // Catch:{ all -> 0x020b }
            java.lang.String r13 = r13.concat(r14)     // Catch:{ all -> 0x020b }
            com.appsflyer.AFLogger.afInfoLog(r13)     // Catch:{ all -> 0x020b }
            java.lang.String r13 = "AppsFlyer_4.8.11"
            java.lang.String r14 = "SERVER_RESPONSE_CODE"
            java.lang.String r15 = java.lang.Integer.toString(r2)     // Catch:{ all -> 0x020b }
            a(r6, r13, r14, r15)     // Catch:{ all -> 0x020b }
            java.lang.String r13 = "appsflyer-data"
            android.content.SharedPreferences r13 = r6.getSharedPreferences(r13, r8)     // Catch:{ all -> 0x020b }
            r14 = 200(0xc8, float:2.8E-43)
            if (r2 != r14) goto L_0x0152
            java.lang.Object r2 = r24.get()     // Catch:{ all -> 0x020b }
            if (r2 == 0) goto L_0x00a1
            if (r26 == 0) goto L_0x00a1
            long r14 = java.lang.System.currentTimeMillis()     // Catch:{ all -> 0x020b }
            r1.k = r14     // Catch:{ all -> 0x020b }
        L_0x00a1:
            java.lang.String r2 = "afUninstallToken"
            com.appsflyer.AppsFlyerProperties r5 = com.appsflyer.AppsFlyerProperties.getInstance()     // Catch:{ all -> 0x020b }
            java.lang.String r2 = r5.getString(r2)     // Catch:{ all -> 0x020b }
            if (r2 == 0) goto L_0x00d8
            java.lang.String r5 = "Uninstall Token exists: "
            java.lang.String r14 = java.lang.String.valueOf(r2)     // Catch:{ all -> 0x020b }
            java.lang.String r5 = r5.concat(r14)     // Catch:{ all -> 0x020b }
            com.appsflyer.AFLogger.afDebugLog(r5)     // Catch:{ all -> 0x020b }
            java.lang.String r5 = "sentRegisterRequestToAF"
            boolean r5 = r13.getBoolean(r5, r8)     // Catch:{ all -> 0x020b }
            if (r5 != 0) goto L_0x00f8
            java.lang.String r5 = "Resending Uninstall token to AF servers: "
            java.lang.String r14 = java.lang.String.valueOf(r2)     // Catch:{ all -> 0x020b }
            java.lang.String r5 = r5.concat(r14)     // Catch:{ all -> 0x020b }
            com.appsflyer.AFLogger.afDebugLog(r5)     // Catch:{ all -> 0x020b }
            com.appsflyer.d r5 = new com.appsflyer.d     // Catch:{ all -> 0x020b }
            r5.<init>(r2)     // Catch:{ all -> 0x020b }
            com.appsflyer.u.a(r6, r5)     // Catch:{ all -> 0x020b }
            goto L_0x00f8
        L_0x00d8:
            java.lang.String r2 = "gcmProjectNumber"
            com.appsflyer.AppsFlyerProperties r5 = com.appsflyer.AppsFlyerProperties.getInstance()     // Catch:{ all -> 0x020b }
            java.lang.String r2 = r5.getString(r2)     // Catch:{ all -> 0x020b }
            if (r2 == 0) goto L_0x00f8
            java.lang.String r2 = "GCM Project number exists. Fetching token and sending to AF servers"
            com.appsflyer.AFLogger.afDebugLog(r2)     // Catch:{ all -> 0x020b }
            java.lang.ref.WeakReference r2 = new java.lang.ref.WeakReference     // Catch:{ all -> 0x020b }
            r2.<init>(r6)     // Catch:{ all -> 0x020b }
            com.appsflyer.u$c r5 = new com.appsflyer.u$c     // Catch:{ all -> 0x020b }
            r5.<init>(r2)     // Catch:{ all -> 0x020b }
            java.lang.Void[] r2 = new java.lang.Void[r8]     // Catch:{ all -> 0x020b }
            r5.execute(r2)     // Catch:{ all -> 0x020b }
        L_0x00f8:
            android.net.Uri r2 = r1.y     // Catch:{ all -> 0x020b }
            if (r2 == 0) goto L_0x00fe
            r1.y = r10     // Catch:{ all -> 0x020b }
        L_0x00fe:
            if (r4 == 0) goto L_0x0107
            com.appsflyer.cache.CacheManager r2 = com.appsflyer.cache.CacheManager.getInstance()     // Catch:{ all -> 0x020b }
            r2.deleteRequest(r4, r6)     // Catch:{ all -> 0x020b }
        L_0x0107:
            java.lang.Object r2 = r24.get()     // Catch:{ all -> 0x020b }
            if (r2 == 0) goto L_0x0146
            if (r4 != 0) goto L_0x0146
            java.lang.String r2 = "sentSuccessfully"
            java.lang.String r4 = "true"
            b(r6, r2, r4)     // Catch:{ all -> 0x020b }
            boolean r2 = r1.r     // Catch:{ all -> 0x020b }
            if (r2 != 0) goto L_0x0146
            long r4 = java.lang.System.currentTimeMillis()     // Catch:{ all -> 0x020b }
            long r14 = r1.s     // Catch:{ all -> 0x020b }
            r2 = 0
            long r16 = r4 - r14
            r4 = 15000(0x3a98, double:7.411E-320)
            int r2 = (r16 > r4 ? 1 : (r16 == r4 ? 0 : -1))
            if (r2 >= 0) goto L_0x012a
            goto L_0x0146
        L_0x012a:
            java.util.concurrent.ScheduledExecutorService r2 = r1.t     // Catch:{ all -> 0x020b }
            if (r2 != 0) goto L_0x0146
            com.appsflyer.AFExecutor r2 = com.appsflyer.AFExecutor.getInstance()     // Catch:{ all -> 0x020b }
            java.util.concurrent.ScheduledThreadPoolExecutor r2 = r2.a()     // Catch:{ all -> 0x020b }
            r1.t = r2     // Catch:{ all -> 0x020b }
            com.appsflyer.AppsFlyerLib$c r2 = new com.appsflyer.AppsFlyerLib$c     // Catch:{ all -> 0x020b }
            r2.<init>(r6)     // Catch:{ all -> 0x020b }
            java.util.concurrent.ScheduledExecutorService r4 = r1.t     // Catch:{ all -> 0x020b }
            r14 = 1
            java.util.concurrent.TimeUnit r5 = java.util.concurrent.TimeUnit.SECONDS     // Catch:{ all -> 0x020b }
            a(r4, r2, r14, r5)     // Catch:{ all -> 0x020b }
        L_0x0146:
            org.json.JSONObject r2 = com.appsflyer.ServerConfigHandler.a(r12)     // Catch:{ all -> 0x020b }
            java.lang.String r4 = "send_background"
            boolean r2 = r2.optBoolean(r4, r8)     // Catch:{ all -> 0x020b }
            r1.H = r2     // Catch:{ all -> 0x020b }
        L_0x0152:
            java.lang.String r2 = "appsflyerConversionDataRequestRetries"
            int r2 = r13.getInt(r2, r8)     // Catch:{ all -> 0x020b }
            java.lang.String r4 = "appsflyerConversionDataCacheExpiration"
            r14 = 0
            long r4 = r13.getLong(r4, r14)     // Catch:{ all -> 0x020b }
            int r12 = (r4 > r14 ? 1 : (r4 == r14 ? 0 : -1))
            if (r12 == 0) goto L_0x017e
            long r16 = java.lang.System.currentTimeMillis()     // Catch:{ all -> 0x020b }
            r12 = 0
            long r18 = r16 - r4
            r4 = 5184000000(0x134fd9000, double:2.561236308E-314)
            int r12 = (r18 > r4 ? 1 : (r18 == r4 ? 0 : -1))
            if (r12 <= 0) goto L_0x017e
            java.lang.String r4 = "attributionId"
            b(r6, r4, r10)     // Catch:{ all -> 0x020b }
            java.lang.String r4 = "appsflyerConversionDataCacheExpiration"
            b(r6, r4, r14)     // Catch:{ all -> 0x020b }
        L_0x017e:
            java.lang.String r4 = "attributionId"
            java.lang.String r4 = r13.getString(r4, r10)     // Catch:{ all -> 0x020b }
            if (r4 != 0) goto L_0x01aa
            if (r3 == 0) goto L_0x01aa
            if (r9 == 0) goto L_0x01aa
            com.appsflyer.AppsFlyerConversionListener r4 = o     // Catch:{ all -> 0x020b }
            if (r4 == 0) goto L_0x01aa
            r4 = 5
            if (r2 > r4) goto L_0x01aa
            com.appsflyer.AFExecutor r2 = com.appsflyer.AFExecutor.getInstance()     // Catch:{ all -> 0x020b }
            java.util.concurrent.ScheduledThreadPoolExecutor r2 = r2.a()     // Catch:{ all -> 0x020b }
            com.appsflyer.AppsFlyerLib$a r4 = new com.appsflyer.AppsFlyerLib$a     // Catch:{ all -> 0x020b }
            android.content.Context r5 = r6.getApplicationContext()     // Catch:{ all -> 0x020b }
            r4.<init>(r5, r3, r2)     // Catch:{ all -> 0x020b }
            r5 = 10
            java.util.concurrent.TimeUnit r3 = java.util.concurrent.TimeUnit.MILLISECONDS     // Catch:{ all -> 0x020b }
            a(r2, r4, r5, r3)     // Catch:{ all -> 0x020b }
            goto L_0x01f8
        L_0x01aa:
            if (r3 != 0) goto L_0x01b2
            java.lang.String r2 = "AppsFlyer dev key is missing."
            com.appsflyer.AFLogger.afWarnLog(r2)     // Catch:{ all -> 0x020b }
            goto L_0x01f8
        L_0x01b2:
            if (r9 == 0) goto L_0x01f8
            com.appsflyer.AppsFlyerConversionListener r2 = o     // Catch:{ all -> 0x020b }
            if (r2 == 0) goto L_0x01f8
            java.lang.String r2 = "attributionId"
            java.lang.String r2 = r13.getString(r2, r10)     // Catch:{ all -> 0x020b }
            if (r2 == 0) goto L_0x01f8
            java.lang.String r2 = "appsFlyerCount"
            int r2 = a(r13, r2, r8)     // Catch:{ all -> 0x020b }
            if (r2 <= r7) goto L_0x01f8
            java.util.Map r2 = d(r6)     // Catch:{ k -> 0x01ef }
            if (r2 == 0) goto L_0x01f8
            java.lang.String r3 = "is_first_launch"
            boolean r3 = r2.containsKey(r3)     // Catch:{ Throwable -> 0x01e5 }
            if (r3 != 0) goto L_0x01df
            java.lang.String r3 = "is_first_launch"
            java.lang.String r4 = java.lang.Boolean.toString(r8)     // Catch:{ Throwable -> 0x01e5 }
            r2.put(r3, r4)     // Catch:{ Throwable -> 0x01e5 }
        L_0x01df:
            com.appsflyer.AppsFlyerConversionListener r3 = o     // Catch:{ Throwable -> 0x01e5 }
            r3.onInstallConversionDataLoaded(r2)     // Catch:{ Throwable -> 0x01e5 }
            goto L_0x01f8
        L_0x01e5:
            r0 = move-exception
            r2 = r0
            java.lang.String r3 = r2.getLocalizedMessage()     // Catch:{ k -> 0x01ef }
            com.appsflyer.AFLogger.afErrorLog(r3, r2)     // Catch:{ k -> 0x01ef }
            goto L_0x01f8
        L_0x01ef:
            r0 = move-exception
            r2 = r0
            java.lang.String r3 = r2.getMessage()     // Catch:{ all -> 0x020b }
            com.appsflyer.AFLogger.afErrorLog(r3, r2)     // Catch:{ all -> 0x020b }
        L_0x01f8:
            if (r11 == 0) goto L_0x01fe
            r11.disconnect()
            return
        L_0x01fe:
            return
        L_0x01ff:
            r0 = move-exception
            r2 = r0
            r10 = r12
            goto L_0x0205
        L_0x0203:
            r0 = move-exception
            r2 = r0
        L_0x0205:
            if (r10 == 0) goto L_0x020a
            r10.close()     // Catch:{ all -> 0x020b }
        L_0x020a:
            throw r2     // Catch:{ all -> 0x020b }
        L_0x020b:
            r0 = move-exception
            r2 = r0
            goto L_0x0211
        L_0x020e:
            r0 = move-exception
            r2 = r0
            r11 = r10
        L_0x0211:
            if (r11 == 0) goto L_0x0216
            r11.disconnect()
        L_0x0216:
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.appsflyer.AppsFlyerLib.a(java.net.URL, java.lang.String, java.lang.String, java.lang.ref.WeakReference, java.lang.String, boolean):void");
    }

    public void validateAndTrackInAppPurchase(Context context, String str, String str2, String str3, String str4, String str5, Map<String, String> map) {
        Context context2 = context;
        String str6 = str3;
        String str7 = str4;
        String str8 = str5;
        y a2 = y.a();
        String str9 = "validateAndTrackInAppPurchase";
        String[] strArr = new String[6];
        strArr[0] = str;
        strArr[1] = str2;
        strArr[2] = str6;
        strArr[3] = str7;
        strArr[4] = str8;
        strArr[5] = map == null ? "" : map.toString();
        a2.a(str9, strArr);
        if (!isTrackingStopped()) {
            StringBuilder sb = new StringBuilder("Validate in app called with parameters: ");
            sb.append(str6);
            sb.append(UtilsCuentas.SEPARAOR2);
            sb.append(str7);
            sb.append(UtilsCuentas.SEPARAOR2);
            sb.append(str8);
            AFLogger.afInfoLog(sb.toString());
        }
        if (str != null && str7 != null && str2 != null && str8 != null && str6 != null) {
            ScheduledThreadPoolExecutor a3 = AFExecutor.getInstance().a();
            i iVar = new i(context2.getApplicationContext(), AppsFlyerProperties.getInstance().getString(AppsFlyerProperties.AF_KEY), str, str2, str6, str7, str8, map, a3, context2 instanceof Activity ? ((Activity) context2).getIntent() : null);
            a((ScheduledExecutorService) a3, (Runnable) iVar, 10, TimeUnit.MILLISECONDS);
        } else if (c != null) {
            c.onValidateInAppFailure("Please provide purchase parameters");
        }
    }

    private static void a(ScheduledExecutorService scheduledExecutorService, Runnable runnable, long j2, TimeUnit timeUnit) {
        if (scheduledExecutorService != null) {
            try {
                if (!scheduledExecutorService.isShutdown() && !scheduledExecutorService.isTerminated()) {
                    scheduledExecutorService.schedule(runnable, j2, timeUnit);
                    return;
                }
            } catch (RejectedExecutionException e2) {
                AFLogger.afErrorLog("scheduleJob failed with RejectedExecutionException Exception", e2);
                return;
            } catch (Throwable th) {
                AFLogger.afErrorLog("scheduleJob failed with Exception", th);
                return;
            }
        }
        AFLogger.afWarnLog("scheduler is null, shut downed or terminated");
    }

    public void onHandleReferrer(Map<String, String> map) {
        this.q = map;
    }

    public boolean isTrackingStopped() {
        return this.I;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:29:0x005d, code lost:
        if (r3 != null) goto L_0x002c;
     */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x005a A[SYNTHETIC, Splitter:B:27:0x005a] */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x0086 A[SYNTHETIC, Splitter:B:45:0x0086] */
    /* JADX WARNING: Removed duplicated region for block: B:48:0x008b A[Catch:{ Throwable -> 0x008e }] */
    @android.support.annotation.NonNull
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static java.lang.String a(java.net.HttpURLConnection r7) {
        /*
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            r1 = 0
            java.io.InputStream r2 = r7.getErrorStream()     // Catch:{ Throwable -> 0x003d, all -> 0x003a }
            if (r2 != 0) goto L_0x0010
            java.io.InputStream r2 = r7.getInputStream()     // Catch:{ Throwable -> 0x003d, all -> 0x003a }
        L_0x0010:
            java.io.InputStreamReader r3 = new java.io.InputStreamReader     // Catch:{ Throwable -> 0x003d, all -> 0x003a }
            r3.<init>(r2)     // Catch:{ Throwable -> 0x003d, all -> 0x003a }
            java.io.BufferedReader r2 = new java.io.BufferedReader     // Catch:{ Throwable -> 0x0038 }
            r2.<init>(r3)     // Catch:{ Throwable -> 0x0038 }
        L_0x001a:
            java.lang.String r1 = r2.readLine()     // Catch:{ Throwable -> 0x0033, all -> 0x0030 }
            if (r1 == 0) goto L_0x0029
            r0.append(r1)     // Catch:{ Throwable -> 0x0033, all -> 0x0030 }
            r1 = 10
            r0.append(r1)     // Catch:{ Throwable -> 0x0033, all -> 0x0030 }
            goto L_0x001a
        L_0x0029:
            r2.close()     // Catch:{ Throwable -> 0x0060 }
        L_0x002c:
            r3.close()     // Catch:{ Throwable -> 0x0060 }
            goto L_0x0060
        L_0x0030:
            r7 = move-exception
            r1 = r2
            goto L_0x0084
        L_0x0033:
            r1 = move-exception
            r6 = r2
            r2 = r1
            r1 = r6
            goto L_0x003f
        L_0x0038:
            r2 = move-exception
            goto L_0x003f
        L_0x003a:
            r7 = move-exception
            r3 = r1
            goto L_0x0084
        L_0x003d:
            r2 = move-exception
            r3 = r1
        L_0x003f:
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x0083 }
            java.lang.String r5 = "Could not read connection response from: "
            r4.<init>(r5)     // Catch:{ all -> 0x0083 }
            java.net.URL r7 = r7.getURL()     // Catch:{ all -> 0x0083 }
            java.lang.String r7 = r7.toString()     // Catch:{ all -> 0x0083 }
            r4.append(r7)     // Catch:{ all -> 0x0083 }
            java.lang.String r7 = r4.toString()     // Catch:{ all -> 0x0083 }
            com.appsflyer.AFLogger.afErrorLog(r7, r2)     // Catch:{ all -> 0x0083 }
            if (r1 == 0) goto L_0x005d
            r1.close()     // Catch:{ Throwable -> 0x0060 }
        L_0x005d:
            if (r3 == 0) goto L_0x0060
            goto L_0x002c
        L_0x0060:
            java.lang.String r7 = r0.toString()
            org.json.JSONObject r0 = new org.json.JSONObject     // Catch:{ JSONException -> 0x006a }
            r0.<init>(r7)     // Catch:{ JSONException -> 0x006a }
            return r7
        L_0x006a:
            org.json.JSONObject r0 = new org.json.JSONObject
            r0.<init>()
            java.lang.String r1 = "string_response"
            r0.put(r1, r7)     // Catch:{ JSONException -> 0x0079 }
            java.lang.String r7 = r0.toString()     // Catch:{ JSONException -> 0x0079 }
            return r7
        L_0x0079:
            org.json.JSONObject r7 = new org.json.JSONObject
            r7.<init>()
            java.lang.String r7 = r7.toString()
            return r7
        L_0x0083:
            r7 = move-exception
        L_0x0084:
            if (r1 == 0) goto L_0x0089
            r1.close()     // Catch:{ Throwable -> 0x008e }
        L_0x0089:
            if (r3 == 0) goto L_0x008e
            r3.close()     // Catch:{ Throwable -> 0x008e }
        L_0x008e:
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.appsflyer.AppsFlyerLib.a(java.net.HttpURLConnection):java.lang.String");
    }

    private static float f(Context context) {
        float f2;
        try {
            Intent registerReceiver = context.getApplicationContext().registerReceiver(null, new IntentFilter("android.intent.action.BATTERY_CHANGED"));
            int intExtra = registerReceiver.getIntExtra("level", -1);
            int intExtra2 = registerReceiver.getIntExtra("scale", -1);
            if (intExtra == -1 || intExtra2 == -1) {
                return 50.0f;
            }
            f2 = (((float) intExtra) / ((float) intExtra2)) * 100.0f;
            return f2;
        } catch (Throwable th) {
            AFLogger.afErrorLog(th.getMessage(), th);
            f2 = 1.0f;
        }
    }

    public void setLogLevel(LogLevel logLevel) {
        AppsFlyerProperties.getInstance().set("logLevel", logLevel.getLevel());
    }

    public void setHostName(String str) {
        AppsFlyerProperties.getInstance().set("custom_host", str);
    }

    public String getHost() {
        String string = AppsFlyerProperties.getInstance().getString("custom_host");
        return string != null ? string : ServerParameters.DEFAULT_HOST;
    }

    public void setMinTimeBetweenSessions(int i2) {
        this.n = TimeUnit.SECONDS.toMillis((long) i2);
    }

    /* access modifiers changed from: private */
    public static void b(Context context, String str, String str2) {
        Editor edit = context.getSharedPreferences("appsflyer-data", 0).edit();
        edit.putString(str, str2);
        if (VERSION.SDK_INT >= 9) {
            edit.apply();
        } else {
            edit.commit();
        }
    }

    /* access modifiers changed from: private */
    public static void b(Context context, String str, int i2) {
        Editor edit = context.getSharedPreferences("appsflyer-data", 0).edit();
        edit.putInt(str, i2);
        if (VERSION.SDK_INT >= 9) {
            edit.apply();
        } else {
            edit.commit();
        }
    }

    /* access modifiers changed from: private */
    public static void b(Context context, String str, long j2) {
        Editor edit = context.getSharedPreferences("appsflyer-data", 0).edit();
        edit.putLong(str, j2);
        if (VERSION.SDK_INT >= 9) {
            edit.apply();
        } else {
            edit.commit();
        }
    }
}
