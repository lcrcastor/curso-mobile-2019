package com.crashlytics.android.answers;

import android.app.Activity;
import android.os.Bundle;
import io.fabric.sdk.android.ActivityLifecycleManager.Callbacks;

class AnswersLifecycleCallbacks extends Callbacks {
    private final SessionAnalyticsManager a;
    private final BackgroundManager b;

    public void onActivityCreated(Activity activity, Bundle bundle) {
    }

    public void onActivityDestroyed(Activity activity) {
    }

    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
    }

    public AnswersLifecycleCallbacks(SessionAnalyticsManager sessionAnalyticsManager, BackgroundManager backgroundManager) {
        this.a = sessionAnalyticsManager;
        this.b = backgroundManager;
    }

    public void onActivityStarted(Activity activity) {
        this.a.a(activity, Type.START);
    }

    public void onActivityResumed(Activity activity) {
        this.a.a(activity, Type.RESUME);
        this.b.a();
    }

    public void onActivityPaused(Activity activity) {
        this.a.a(activity, Type.PAUSE);
        this.b.b();
    }

    public void onActivityStopped(Activity activity) {
        this.a.a(activity, Type.STOP);
    }
}
