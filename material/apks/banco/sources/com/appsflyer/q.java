package com.appsflyer;

import android.app.Activity;
import android.app.Application;
import android.app.Application.ActivityLifecycleCallbacks;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import java.lang.ref.WeakReference;
import java.util.concurrent.RejectedExecutionException;

@RequiresApi(api = 14)
final class q implements ActivityLifecycleCallbacks {
    private static q a;
    /* access modifiers changed from: private */
    public boolean b = false;
    /* access modifiers changed from: private */
    public boolean c = true;
    /* access modifiers changed from: private */
    public a d = null;

    interface a {
        void a(Activity activity);

        void a(WeakReference<Context> weakReference);
    }

    class c extends AsyncTask<Void, Void, Void> {
        private WeakReference<Context> a;

        /* access modifiers changed from: protected */
        public final /* synthetic */ Object doInBackground(Object[] objArr) {
            return a();
        }

        public c(WeakReference<Context> weakReference) {
            this.a = weakReference;
        }

        private Void a() {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                AFLogger.afErrorLog("Sleeping attempt failed (essential for background state verification)\n", e);
            }
            if (q.this.b && q.this.c) {
                q.this.b = false;
                try {
                    q.this.d.a(this.a);
                } catch (Exception e2) {
                    AFLogger.afErrorLog("Listener threw exception! ", e2);
                    cancel(true);
                }
            }
            this.a.clear();
            return null;
        }
    }

    public final void onActivityCreated(Activity activity, Bundle bundle) {
    }

    public final void onActivityDestroyed(Activity activity) {
    }

    public final void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
    }

    public final void onActivityStarted(Activity activity) {
    }

    public final void onActivityStopped(Activity activity) {
    }

    q() {
    }

    static q a() {
        if (a == null) {
            a = new q();
        }
        return a;
    }

    public static q b() {
        if (a != null) {
            return a;
        }
        throw new IllegalStateException("Foreground is not initialised - invoke at least once with parameter init/get");
    }

    public final void a(Application application, a aVar) {
        this.d = aVar;
        if (VERSION.SDK_INT >= 14) {
            application.registerActivityLifecycleCallbacks(a);
        }
    }

    public final void onActivityResumed(Activity activity) {
        this.c = false;
        boolean z = !this.b;
        this.b = true;
        if (z) {
            try {
                this.d.a(activity);
            } catch (Exception e) {
                AFLogger.afErrorLog("Listener threw exception! ", e);
            }
        }
    }

    public final void onActivityPaused(Activity activity) {
        this.c = true;
        try {
            new c(new WeakReference(activity.getApplicationContext())).executeOnExecutor(AFExecutor.getInstance().getThreadPoolExecutor(), new Void[0]);
        } catch (RejectedExecutionException e) {
            AFLogger.afErrorLog("backgroundTask.executeOnExecutor failed with RejectedExecutionException Exception", e);
        } catch (Throwable th) {
            AFLogger.afErrorLog("backgroundTask.executeOnExecutor failed with Exception", th);
        }
    }
}
