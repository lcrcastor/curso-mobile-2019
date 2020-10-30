package com.crashlytics.android.core;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.pm.PackageInfo;
import android.widget.ScrollView;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.utils.UtilsCuentas;
import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.core.internal.CrashEventDataProvider;
import com.crashlytics.android.core.internal.models.SessionEventData;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Kit;
import io.fabric.sdk.android.Logger;
import io.fabric.sdk.android.services.common.ApiKey;
import io.fabric.sdk.android.services.common.CommonUtils;
import io.fabric.sdk.android.services.common.Crash.FatalException;
import io.fabric.sdk.android.services.common.Crash.LoggedException;
import io.fabric.sdk.android.services.common.ExecutorUtils;
import io.fabric.sdk.android.services.common.IdManager;
import io.fabric.sdk.android.services.concurrency.DependsOn;
import io.fabric.sdk.android.services.concurrency.Priority;
import io.fabric.sdk.android.services.concurrency.PriorityCallable;
import io.fabric.sdk.android.services.concurrency.Task;
import io.fabric.sdk.android.services.concurrency.UnmetDependencyException;
import io.fabric.sdk.android.services.network.DefaultHttpRequestFactory;
import io.fabric.sdk.android.services.network.HttpMethod;
import io.fabric.sdk.android.services.network.HttpRequest;
import io.fabric.sdk.android.services.network.HttpRequestFactory;
import io.fabric.sdk.android.services.persistence.FileStore;
import io.fabric.sdk.android.services.persistence.FileStoreImpl;
import io.fabric.sdk.android.services.persistence.PreferenceStoreImpl;
import io.fabric.sdk.android.services.settings.PromptSettingsData;
import io.fabric.sdk.android.services.settings.SessionSettingsData;
import io.fabric.sdk.android.services.settings.Settings;
import io.fabric.sdk.android.services.settings.Settings.SettingsAccess;
import io.fabric.sdk.android.services.settings.SettingsData;
import java.io.File;
import java.net.URL;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import javax.net.ssl.HttpsURLConnection;

@DependsOn({CrashEventDataProvider.class})
public class CrashlyticsCore extends Kit<Void> {
    public static final String TAG = "CrashlyticsCore";
    private HttpRequestFactory A;
    private CrashlyticsExecutorServiceWrapper B;
    private CrashEventDataProvider C;
    private final long a;
    private final ConcurrentHashMap<String, String> h;
    private File i;
    private FileStore j;
    /* access modifiers changed from: private */
    public CrashlyticsFileMarker k;
    private CrashlyticsFileMarker l;
    private CrashlyticsListener m;
    private CrashlyticsUncaughtExceptionHandler n;
    private String o;
    private String p;
    private String q;
    private String r;
    private String s;
    private String t;
    private String u;
    private String v;
    private String w;
    private float x;
    private boolean y;
    private final PinningInfoProvider z;

    public static class Builder {
        private float a = -1.0f;
        private CrashlyticsListener b;
        private PinningInfoProvider c;
        private boolean d = false;

        public Builder delay(float f) {
            if (f <= BitmapDescriptorFactory.HUE_RED) {
                throw new IllegalArgumentException("delay must be greater than 0");
            } else if (this.a > BitmapDescriptorFactory.HUE_RED) {
                throw new IllegalStateException("delay already set.");
            } else {
                this.a = f;
                return this;
            }
        }

        public Builder listener(CrashlyticsListener crashlyticsListener) {
            if (crashlyticsListener == null) {
                throw new IllegalArgumentException("listener must not be null.");
            } else if (this.b != null) {
                throw new IllegalStateException("listener already set.");
            } else {
                this.b = crashlyticsListener;
                return this;
            }
        }

        @Deprecated
        public Builder pinningInfo(PinningInfoProvider pinningInfoProvider) {
            if (pinningInfoProvider == null) {
                throw new IllegalArgumentException("pinningInfoProvider must not be null.");
            } else if (this.c != null) {
                throw new IllegalStateException("pinningInfoProvider already set.");
            } else {
                this.c = pinningInfoProvider;
                return this;
            }
        }

        public Builder disabled(boolean z) {
            this.d = z;
            return this;
        }

        public CrashlyticsCore build() {
            if (this.a < BitmapDescriptorFactory.HUE_RED) {
                this.a = 1.0f;
            }
            return new CrashlyticsCore(this.a, this.b, this.c, this.d);
        }
    }

    static final class CrashMarkerCheck implements Callable<Boolean> {
        private final CrashlyticsFileMarker a;

        public CrashMarkerCheck(CrashlyticsFileMarker crashlyticsFileMarker) {
            this.a = crashlyticsFileMarker;
        }

        /* renamed from: a */
        public Boolean call() {
            if (!this.a.b()) {
                return Boolean.FALSE;
            }
            Fabric.getLogger().d(CrashlyticsCore.TAG, "Found previous crash marker.");
            this.a.c();
            return Boolean.TRUE;
        }
    }

    static final class NoOpListener implements CrashlyticsListener {
        public void crashlyticsDidDetectCrashDuringPreviousExecution() {
        }

        private NoOpListener() {
        }
    }

    static class OptInLatch {
        private boolean a;
        private final CountDownLatch b;

        private OptInLatch() {
            this.a = false;
            this.b = new CountDownLatch(1);
        }

        /* access modifiers changed from: 0000 */
        public void a(boolean z) {
            this.a = z;
            this.b.countDown();
        }

        /* access modifiers changed from: 0000 */
        public boolean a() {
            return this.a;
        }

        /* access modifiers changed from: 0000 */
        public void b() {
            try {
                this.b.await();
            } catch (InterruptedException unused) {
            }
        }
    }

    /* access modifiers changed from: private */
    public static int b(float f, int i2) {
        return (int) (f * ((float) i2));
    }

    public String getIdentifier() {
        return "com.crashlytics.sdk.android.crashlytics-core";
    }

    public String getVersion() {
        return "2.3.12.143";
    }

    public CrashlyticsCore() {
        this(1.0f, null, null, false);
    }

    CrashlyticsCore(float f, CrashlyticsListener crashlyticsListener, PinningInfoProvider pinningInfoProvider, boolean z2) {
        this(f, crashlyticsListener, pinningInfoProvider, z2, ExecutorUtils.buildSingleThreadExecutorService("Crashlytics Exception Handler"));
    }

    CrashlyticsCore(float f, CrashlyticsListener crashlyticsListener, PinningInfoProvider pinningInfoProvider, boolean z2, ExecutorService executorService) {
        this.o = null;
        this.p = null;
        this.q = null;
        this.x = f;
        if (crashlyticsListener == null) {
            crashlyticsListener = new NoOpListener();
        }
        this.m = crashlyticsListener;
        this.z = pinningInfoProvider;
        this.y = z2;
        this.B = new CrashlyticsExecutorServiceWrapper(executorService);
        this.h = new ConcurrentHashMap<>();
        this.a = System.currentTimeMillis();
    }

    /* access modifiers changed from: protected */
    public boolean onPreExecute() {
        return a(super.getContext());
    }

    /* access modifiers changed from: 0000 */
    public boolean a(Context context) {
        if (this.y) {
            return false;
        }
        this.t = new ApiKey().getValue(context);
        if (this.t == null) {
            return false;
        }
        Logger logger = Fabric.getLogger();
        String str = TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("Initializing Crashlytics ");
        sb.append(getVersion());
        logger.i(str, sb.toString());
        this.j = new FileStoreImpl(this);
        this.l = new CrashlyticsFileMarker("crash_marker", this.j);
        this.k = new CrashlyticsFileMarker("initialization_marker", this.j);
        try {
            a(context, this.t);
            ManifestUnityVersionProvider manifestUnityVersionProvider = new ManifestUnityVersionProvider(context, b());
            boolean o2 = o();
            z();
            a((UnityVersionProvider) manifestUnityVersionProvider);
            if (!o2 || !CommonUtils.canTryConnection(context)) {
                return true;
            }
            y();
            return false;
        } catch (CrashlyticsMissingDependencyException e) {
            throw new UnmetDependencyException((Throwable) e);
        } catch (Exception e2) {
            Fabric.getLogger().e(TAG, "Crashlytics was not started due to an exception during initialization", e2);
            return false;
        }
    }

    private void a(Context context, String str) {
        CrashlyticsPinningInfoProvider crashlyticsPinningInfoProvider = this.z != null ? new CrashlyticsPinningInfoProvider(this.z) : null;
        this.A = new DefaultHttpRequestFactory(Fabric.getLogger());
        this.A.setPinningInfoProvider(crashlyticsPinningInfoProvider);
        this.s = context.getPackageName();
        this.u = getIdManager().getInstallerPackageName();
        Logger logger = Fabric.getLogger();
        String str2 = TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("Installer package name is: ");
        sb.append(this.u);
        logger.d(str2, sb.toString());
        PackageInfo packageInfo = context.getPackageManager().getPackageInfo(this.s, 0);
        this.v = Integer.toString(packageInfo.versionCode);
        this.w = packageInfo.versionName == null ? IdManager.DEFAULT_VERSION_NAME : packageInfo.versionName;
        this.r = CommonUtils.resolveBuildId(context);
        a(this.r, b(context)).a(str, this.s);
    }

    private void a(UnityVersionProvider unityVersionProvider) {
        try {
            Fabric.getLogger().d(TAG, "Installing exception handler...");
            CrashlyticsUncaughtExceptionHandler crashlyticsUncaughtExceptionHandler = new CrashlyticsUncaughtExceptionHandler(Thread.getDefaultUncaughtExceptionHandler(), this.B, getIdManager(), unityVersionProvider, this.j, this);
            this.n = crashlyticsUncaughtExceptionHandler;
            this.n.c();
            Thread.setDefaultUncaughtExceptionHandler(this.n);
            Fabric.getLogger().d(TAG, "Successfully installed exception handler.");
        } catch (Exception e) {
            Fabric.getLogger().e(TAG, "There was a problem installing the exception handler.", e);
        }
    }

    /* access modifiers changed from: protected */
    public Void doInBackground() {
        m();
        this.n.h();
        try {
            SettingsData awaitSettingsData = Settings.getInstance().awaitSettingsData();
            if (awaitSettingsData == null) {
                Fabric.getLogger().w(TAG, "Received null settings, skipping initialization!");
                n();
                return null;
            } else if (!awaitSettingsData.featuresData.collectReports) {
                Fabric.getLogger().d(TAG, "Collection of crash reports disabled in Crashlytics settings.");
                n();
                return null;
            } else {
                this.n.d();
                CreateReportSpiCall a2 = a(awaitSettingsData);
                if (a2 == null) {
                    Fabric.getLogger().w(TAG, "Unable to create a call to upload reports.");
                    n();
                    return null;
                }
                new ReportUploader(this.t, a2).a(this.x);
                n();
                return null;
            }
        } catch (Exception e) {
            Fabric.getLogger().e(TAG, "Crashlytics encountered a problem during asynchronous initialization.", e);
        } catch (Throwable th) {
            n();
            throw th;
        }
    }

    public static CrashlyticsCore getInstance() {
        return (CrashlyticsCore) Fabric.getKit(CrashlyticsCore.class);
    }

    public PinningInfoProvider getPinningInfoProvider() {
        if (!this.y) {
            return this.z;
        }
        return null;
    }

    public void logException(Throwable th) {
        if (this.y || !a("prior to logging exceptions.")) {
            return;
        }
        if (th == null) {
            Fabric.getLogger().log(5, TAG, "Crashlytics is ignoring a request to log a null exception.");
        } else {
            this.n.a(Thread.currentThread(), th);
        }
    }

    public void log(String str) {
        a(3, TAG, str);
    }

    private void a(int i2, String str, String str2) {
        if (!this.y && a("prior to logging messages.")) {
            this.n.a(System.currentTimeMillis() - this.a, b(i2, str, str2));
        }
    }

    public void log(int i2, String str, String str2) {
        a(i2, str, str2);
        Logger logger = Fabric.getLogger();
        StringBuilder sb = new StringBuilder();
        sb.append("");
        sb.append(str);
        String sb2 = sb.toString();
        StringBuilder sb3 = new StringBuilder();
        sb3.append("");
        sb3.append(str2);
        logger.log(i2, sb2, sb3.toString(), true);
    }

    public void setUserIdentifier(String str) {
        if (!this.y) {
            this.o = b(str);
            this.n.a(this.o, this.q, this.p);
        }
    }

    public void setUserName(String str) {
        if (!this.y) {
            this.q = b(str);
            this.n.a(this.o, this.q, this.p);
        }
    }

    public void setUserEmail(String str) {
        if (!this.y) {
            this.p = b(str);
            this.n.a(this.o, this.q, this.p);
        }
    }

    public void setString(String str, String str2) {
        String str3;
        if (!this.y) {
            if (str == null) {
                Context context = getContext();
                if (context == null || !CommonUtils.isAppDebuggable(context)) {
                    Fabric.getLogger().e(TAG, "Attempting to set custom attribute with null key, ignoring.", null);
                    return;
                }
                throw new IllegalArgumentException("Custom attribute key must not be null.");
            }
            String b = b(str);
            if (this.h.size() < 64 || this.h.containsKey(b)) {
                if (str2 == null) {
                    str3 = "";
                } else {
                    str3 = b(str2);
                }
                this.h.put(b, str3);
                this.n.a((Map<String, String>) this.h);
                return;
            }
            Fabric.getLogger().d(TAG, "Exceeded maximum number of custom attributes (64)");
        }
    }

    public void setBool(String str, boolean z2) {
        setString(str, Boolean.toString(z2));
    }

    public void setDouble(String str, double d) {
        setString(str, Double.toString(d));
    }

    public void setFloat(String str, float f) {
        setString(str, Float.toString(f));
    }

    public void setInt(String str, int i2) {
        setString(str, Integer.toString(i2));
    }

    public void setLong(String str, long j2) {
        setString(str, Long.toString(j2));
    }

    public void crash() {
        new CrashTest().indexOutOfBounds();
    }

    public boolean verifyPinning(URL url) {
        try {
            return a(url);
        } catch (Exception e) {
            Fabric.getLogger().e(TAG, "Could not verify SSL pinning", e);
            return false;
        }
    }

    @Deprecated
    public synchronized void setListener(CrashlyticsListener crashlyticsListener) {
        Fabric.getLogger().w(TAG, "Use of setListener is deprecated.");
        if (crashlyticsListener == null) {
            throw new IllegalArgumentException("listener must not be null.");
        }
        this.m = crashlyticsListener;
    }

    static void a(String str, String str2) {
        Answers answers = (Answers) Fabric.getKit(Answers.class);
        if (answers != null) {
            answers.onException(new LoggedException(str, str2));
        }
    }

    static void b(String str, String str2) {
        Answers answers = (Answers) Fabric.getKit(Answers.class);
        if (answers != null) {
            answers.onException(new FatalException(str, str2));
        }
    }

    /* access modifiers changed from: 0000 */
    public Map<String, String> a() {
        return Collections.unmodifiableMap(this.h);
    }

    /* access modifiers changed from: 0000 */
    public BuildIdValidator a(String str, boolean z2) {
        return new BuildIdValidator(str, z2);
    }

    /* access modifiers changed from: 0000 */
    public String b() {
        return this.s;
    }

    /* access modifiers changed from: 0000 */
    public String c() {
        return this.t;
    }

    /* access modifiers changed from: 0000 */
    public String d() {
        return this.u;
    }

    /* access modifiers changed from: 0000 */
    public String e() {
        return this.w;
    }

    /* access modifiers changed from: 0000 */
    public String f() {
        return this.v;
    }

    /* access modifiers changed from: 0000 */
    public String g() {
        return CommonUtils.getStringsFileValue(getContext(), "com.crashlytics.ApiEndpoint");
    }

    /* access modifiers changed from: 0000 */
    public String h() {
        return this.r;
    }

    /* access modifiers changed from: 0000 */
    public CrashlyticsUncaughtExceptionHandler i() {
        return this.n;
    }

    /* access modifiers changed from: 0000 */
    public String j() {
        if (getIdManager().canCollectUserIds()) {
            return this.o;
        }
        return null;
    }

    /* access modifiers changed from: 0000 */
    public String k() {
        if (getIdManager().canCollectUserIds()) {
            return this.p;
        }
        return null;
    }

    /* access modifiers changed from: 0000 */
    public String l() {
        if (getIdManager().canCollectUserIds()) {
            return this.q;
        }
        return null;
    }

    private void y() {
        AnonymousClass1 r0 = new PriorityCallable<Void>() {
            /* renamed from: a */
            public Void call() {
                return CrashlyticsCore.this.doInBackground();
            }

            public Priority getPriority() {
                return Priority.IMMEDIATE;
            }
        };
        for (Task addDependency : getDependencies()) {
            r0.addDependency(addDependency);
        }
        Future submit = getFabric().getExecutorService().submit(r0);
        Fabric.getLogger().d(TAG, "Crashlytics detected incomplete initialization on previous app launch. Will initialize synchronously.");
        try {
            submit.get(4, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Fabric.getLogger().e(TAG, "Crashlytics was interrupted during initialization.", e);
        } catch (ExecutionException e2) {
            Fabric.getLogger().e(TAG, "Problem encountered during Crashlytics initialization.", e2);
        } catch (TimeoutException e3) {
            Fabric.getLogger().e(TAG, "Crashlytics timed out during initialization.", e3);
        }
    }

    /* access modifiers changed from: 0000 */
    public void m() {
        this.B.a((Callable<T>) new Callable<Void>() {
            /* renamed from: a */
            public Void call() {
                CrashlyticsCore.this.k.a();
                Fabric.getLogger().d(CrashlyticsCore.TAG, "Initialization marker file created.");
                return null;
            }
        });
    }

    /* access modifiers changed from: 0000 */
    public void n() {
        this.B.b(new Callable<Boolean>() {
            /* renamed from: a */
            public Boolean call() {
                try {
                    boolean c = CrashlyticsCore.this.k.c();
                    Logger logger = Fabric.getLogger();
                    String str = CrashlyticsCore.TAG;
                    StringBuilder sb = new StringBuilder();
                    sb.append("Initialization marker file removed: ");
                    sb.append(c);
                    logger.d(str, sb.toString());
                    return Boolean.valueOf(c);
                } catch (Exception e) {
                    Fabric.getLogger().e(CrashlyticsCore.TAG, "Problem encountered deleting Crashlytics initialization marker.", e);
                    return Boolean.valueOf(false);
                }
            }
        });
    }

    /* access modifiers changed from: 0000 */
    public boolean o() {
        return ((Boolean) this.B.a((Callable<T>) new Callable<Boolean>() {
            /* renamed from: a */
            public Boolean call() {
                return Boolean.valueOf(CrashlyticsCore.this.k.b());
            }
        })).booleanValue();
    }

    /* access modifiers changed from: 0000 */
    public SessionEventData p() {
        if (this.C != null) {
            return this.C.getCrashEventData();
        }
        return null;
    }

    /* access modifiers changed from: 0000 */
    public boolean a(URL url) {
        if (getPinningInfoProvider() == null) {
            return false;
        }
        HttpRequest buildHttpRequest = this.A.buildHttpRequest(HttpMethod.GET, url.toString());
        ((HttpsURLConnection) buildHttpRequest.getConnection()).setInstanceFollowRedirects(false);
        buildHttpRequest.code();
        return true;
    }

    /* access modifiers changed from: 0000 */
    public File q() {
        if (this.i == null) {
            this.i = new FileStoreImpl(this).getFilesDir();
        }
        return this.i;
    }

    /* access modifiers changed from: 0000 */
    public boolean r() {
        return ((Boolean) Settings.getInstance().withSettings(new SettingsAccess<Boolean>() {
            /* renamed from: a */
            public Boolean usingSettings(SettingsData settingsData) {
                if (settingsData.featuresData.promptEnabled) {
                    return Boolean.valueOf(!CrashlyticsCore.this.s());
                }
                return Boolean.valueOf(false);
            }
        }, Boolean.valueOf(false))).booleanValue();
    }

    /* access modifiers changed from: 0000 */
    public boolean s() {
        return new PreferenceStoreImpl(this).get().getBoolean("always_send_reports_opt_in", false);
    }

    /* access modifiers changed from: 0000 */
    @SuppressLint({"CommitPrefEdits"})
    public void a(boolean z2) {
        PreferenceStoreImpl preferenceStoreImpl = new PreferenceStoreImpl(this);
        preferenceStoreImpl.save(preferenceStoreImpl.edit().putBoolean("always_send_reports_opt_in", z2));
    }

    /* access modifiers changed from: 0000 */
    public boolean t() {
        return ((Boolean) Settings.getInstance().withSettings(new SettingsAccess<Boolean>() {
            /* renamed from: a */
            public Boolean usingSettings(SettingsData settingsData) {
                Activity currentActivity = CrashlyticsCore.this.getFabric().getCurrentActivity();
                return Boolean.valueOf((currentActivity == null || currentActivity.isFinishing() || !CrashlyticsCore.this.r()) ? true : CrashlyticsCore.this.a(currentActivity, settingsData.promptData));
            }
        }, Boolean.valueOf(true))).booleanValue();
    }

    /* access modifiers changed from: 0000 */
    public CreateReportSpiCall a(SettingsData settingsData) {
        if (settingsData != null) {
            return new DefaultCreateReportSpiCall(this, g(), settingsData.appData.reportsUrl, this.A);
        }
        return null;
    }

    private void z() {
        if (Boolean.TRUE.equals((Boolean) this.B.a((Callable<T>) new CrashMarkerCheck<T>(this.l)))) {
            try {
                this.m.crashlyticsDidDetectCrashDuringPreviousExecution();
            } catch (Exception e) {
                Fabric.getLogger().e(TAG, "Exception thrown by CrashlyticsListener while notifying of previous crash.", e);
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void u() {
        this.l.a();
    }

    /* access modifiers changed from: private */
    public boolean a(Activity activity, PromptSettingsData promptSettingsData) {
        final DialogStringResolver dialogStringResolver = new DialogStringResolver(activity, promptSettingsData);
        OptInLatch optInLatch = new OptInLatch();
        final Activity activity2 = activity;
        final OptInLatch optInLatch2 = optInLatch;
        final PromptSettingsData promptSettingsData2 = promptSettingsData;
        AnonymousClass7 r0 = new Runnable() {
            public void run() {
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(activity2);
                AnonymousClass1 r1 = new OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        optInLatch2.a(true);
                        dialogInterface.dismiss();
                    }
                };
                float f = activity2.getResources().getDisplayMetrics().density;
                int a2 = CrashlyticsCore.b(f, 5);
                TextView textView = new TextView(activity2);
                textView.setAutoLinkMask(15);
                textView.setText(dialogStringResolver.b());
                textView.setTextAppearance(activity2, 16973892);
                textView.setPadding(a2, a2, a2, a2);
                textView.setFocusable(false);
                ScrollView scrollView = new ScrollView(activity2);
                scrollView.setPadding(CrashlyticsCore.b(f, 14), CrashlyticsCore.b(f, 2), CrashlyticsCore.b(f, 10), CrashlyticsCore.b(f, 12));
                scrollView.addView(textView);
                builder.setView(scrollView).setTitle(dialogStringResolver.a()).setCancelable(false).setNeutralButton(dialogStringResolver.c(), r1);
                if (promptSettingsData2.showCancelButton) {
                    builder.setNegativeButton(dialogStringResolver.e(), new OnClickListener() {
                        public void onClick(DialogInterface dialogInterface, int i) {
                            optInLatch2.a(false);
                            dialogInterface.dismiss();
                        }
                    });
                }
                if (promptSettingsData2.showAlwaysSendButton) {
                    builder.setPositiveButton(dialogStringResolver.d(), new OnClickListener() {
                        public void onClick(DialogInterface dialogInterface, int i) {
                            CrashlyticsCore.this.a(true);
                            optInLatch2.a(true);
                            dialogInterface.dismiss();
                        }
                    });
                }
                builder.show();
            }
        };
        activity.runOnUiThread(r0);
        Fabric.getLogger().d(TAG, "Waiting for user opt-in.");
        optInLatch.b();
        return optInLatch.a();
    }

    static SessionSettingsData v() {
        SettingsData awaitSettingsData = Settings.getInstance().awaitSettingsData();
        if (awaitSettingsData == null) {
            return null;
        }
        return awaitSettingsData.sessionData;
    }

    private static boolean b(Context context) {
        return CommonUtils.getBooleanResourceValue(context, "com.crashlytics.RequireBuildId", true);
    }

    private static String b(int i2, String str, String str2) {
        StringBuilder sb = new StringBuilder();
        sb.append(CommonUtils.logPriorityToString(i2));
        sb.append("/");
        sb.append(str);
        sb.append(UtilsCuentas.SEPARAOR2);
        sb.append(str2);
        return sb.toString();
    }

    private static boolean a(String str) {
        CrashlyticsCore instance = getInstance();
        if (instance != null && instance.n != null) {
            return true;
        }
        Logger logger = Fabric.getLogger();
        String str2 = TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("Crashlytics must be initialized by calling Fabric.with(Context) ");
        sb.append(str);
        logger.e(str2, sb.toString(), null);
        return false;
    }

    private static String b(String str) {
        if (str == null) {
            return str;
        }
        String trim = str.trim();
        return trim.length() > 1024 ? trim.substring(0, 1024) : trim;
    }
}
