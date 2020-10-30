package com.crashlytics.android.beta;

import android.annotation.TargetApi;
import android.app.Activity;
import io.fabric.sdk.android.ActivityLifecycleManager;
import io.fabric.sdk.android.ActivityLifecycleManager.Callbacks;
import java.util.concurrent.ExecutorService;

@TargetApi(14)
class ActivityLifecycleCheckForUpdatesController extends AbstractCheckForUpdatesController {
    private final Callbacks a = new Callbacks() {
        public void onActivityStarted(Activity activity) {
            if (ActivityLifecycleCheckForUpdatesController.this.a()) {
                ActivityLifecycleCheckForUpdatesController.this.b.submit(new Runnable() {
                    public void run() {
                        ActivityLifecycleCheckForUpdatesController.this.c();
                    }
                });
            }
        }
    };
    /* access modifiers changed from: private */
    public final ExecutorService b;

    public ActivityLifecycleCheckForUpdatesController(ActivityLifecycleManager activityLifecycleManager, ExecutorService executorService) {
        this.b = executorService;
        activityLifecycleManager.registerCallbacks(this.a);
    }
}
