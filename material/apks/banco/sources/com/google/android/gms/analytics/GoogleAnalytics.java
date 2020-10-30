package com.google.android.gms.analytics;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.app.Application.ActivityLifecycleCallbacks;
import android.content.Context;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.annotation.RequiresPermission;
import android.util.Log;
import com.google.android.gms.analytics.internal.zzae;
import com.google.android.gms.analytics.internal.zzam;
import com.google.android.gms.analytics.internal.zzan;
import com.google.android.gms.analytics.internal.zzap;
import com.google.android.gms.analytics.internal.zzf;
import com.google.android.gms.analytics.internal.zzy;
import com.google.android.gms.common.internal.zzac;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class GoogleAnalytics extends zza {
    private static List<Runnable> a = new ArrayList();
    private boolean b;
    private Set<zza> c = new HashSet();
    private boolean d;
    private boolean e;
    private volatile boolean f;
    private boolean g;

    interface zza {
        void a(Activity activity);

        void b(Activity activity);
    }

    @TargetApi(14)
    class zzb implements ActivityLifecycleCallbacks {
        zzb() {
        }

        public void onActivityCreated(Activity activity, Bundle bundle) {
        }

        public void onActivityDestroyed(Activity activity) {
        }

        public void onActivityPaused(Activity activity) {
        }

        public void onActivityResumed(Activity activity) {
        }

        public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
        }

        public void onActivityStarted(Activity activity) {
            GoogleAnalytics.this.a(activity);
        }

        public void onActivityStopped(Activity activity) {
            GoogleAnalytics.this.b(activity);
        }
    }

    public GoogleAnalytics(zzf zzf) {
        super(zzf);
    }

    private com.google.android.gms.analytics.internal.zzb d() {
        return c().zzxu();
    }

    private zzap e() {
        return c().zzxv();
    }

    @RequiresPermission(allOf = {"android.permission.INTERNET", "android.permission.ACCESS_NETWORK_STATE"})
    public static GoogleAnalytics getInstance(Context context) {
        return zzf.zzaz(context).zzabb();
    }

    public static void zzxr() {
        synchronized (GoogleAnalytics.class) {
            if (a != null) {
                for (Runnable run : a) {
                    run.run();
                }
                a = null;
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void a() {
        zzap e2 = e();
        if (e2.zzaei()) {
            getLogger().setLogLevel(e2.getLogLevel());
        }
        if (e2.zzaem()) {
            setDryRun(e2.zzaen());
        }
        if (e2.zzaei()) {
            Logger logger = zzae.getLogger();
            if (logger != null) {
                logger.setLogLevel(e2.getLogLevel());
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void a(Activity activity) {
        for (zza a2 : this.c) {
            a2.a(activity);
        }
    }

    /* access modifiers changed from: 0000 */
    public void a(zza zza2) {
        this.c.add(zza2);
        Context context = c().getContext();
        if (context instanceof Application) {
            enableAutoActivityReports((Application) context);
        }
    }

    /* access modifiers changed from: 0000 */
    public void b() {
        d().zzaah();
    }

    /* access modifiers changed from: 0000 */
    public void b(Activity activity) {
        for (zza b2 : this.c) {
            b2.b(activity);
        }
    }

    /* access modifiers changed from: 0000 */
    public void b(zza zza2) {
        this.c.remove(zza2);
    }

    public void dispatchLocalHits() {
        d().zzaag();
    }

    @TargetApi(14)
    public void enableAutoActivityReports(Application application) {
        if (VERSION.SDK_INT >= 14 && !this.d) {
            application.registerActivityLifecycleCallbacks(new zzb());
            this.d = true;
        }
    }

    public boolean getAppOptOut() {
        return this.f;
    }

    @Deprecated
    public Logger getLogger() {
        return zzae.getLogger();
    }

    public void initialize() {
        a();
        this.b = true;
    }

    public boolean isDryRunEnabled() {
        return this.e;
    }

    public boolean isInitialized() {
        return this.b;
    }

    public Tracker newTracker(int i) {
        Tracker tracker;
        synchronized (this) {
            tracker = new Tracker(c(), null, null);
            if (i > 0) {
                zzan zzan = (zzan) new zzam(c()).zzcd(i);
                if (zzan != null) {
                    tracker.a(zzan);
                }
            }
            tracker.initialize();
        }
        return tracker;
    }

    public Tracker newTracker(String str) {
        Tracker tracker;
        synchronized (this) {
            tracker = new Tracker(c(), str, null);
            tracker.initialize();
        }
        return tracker;
    }

    public void reportActivityStart(Activity activity) {
        if (!this.d) {
            a(activity);
        }
    }

    public void reportActivityStop(Activity activity) {
        if (!this.d) {
            b(activity);
        }
    }

    public void setAppOptOut(boolean z) {
        this.f = z;
        if (this.f) {
            d().zzaaf();
        }
    }

    public void setDryRun(boolean z) {
        this.e = z;
    }

    public void setLocalDispatchPeriod(int i) {
        d().setLocalDispatchPeriod(i);
    }

    @Deprecated
    public void setLogger(Logger logger) {
        zzae.setLogger(logger);
        if (!this.g) {
            String str = (String) zzy.cg.get();
            String str2 = (String) zzy.cg.get();
            StringBuilder sb = new StringBuilder(String.valueOf(str2).length() + 112);
            sb.append("GoogleAnalytics.setLogger() is deprecated. To enable debug logging, please run:\nadb shell setprop log.tag.");
            sb.append(str2);
            sb.append(" DEBUG");
            Log.i(str, sb.toString());
            this.g = true;
        }
    }

    public String zzxs() {
        zzac.zzhr("getClientId can not be called from the main thread");
        return c().zzabe().zzacm();
    }
}
