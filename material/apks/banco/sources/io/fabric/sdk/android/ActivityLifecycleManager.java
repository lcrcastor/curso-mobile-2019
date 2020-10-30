package io.fabric.sdk.android;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.app.Application.ActivityLifecycleCallbacks;
import android.content.Context;
import android.os.Build.VERSION;
import android.os.Bundle;
import java.util.HashSet;
import java.util.Set;

public class ActivityLifecycleManager {
    private final Application a;
    private ActivityLifecycleCallbacksWrapper b;

    static class ActivityLifecycleCallbacksWrapper {
        private final Set<ActivityLifecycleCallbacks> a = new HashSet();
        private final Application b;

        ActivityLifecycleCallbacksWrapper(Application application) {
            this.b = application;
        }

        /* access modifiers changed from: private */
        @TargetApi(14)
        public void a() {
            for (ActivityLifecycleCallbacks unregisterActivityLifecycleCallbacks : this.a) {
                this.b.unregisterActivityLifecycleCallbacks(unregisterActivityLifecycleCallbacks);
            }
        }

        /* access modifiers changed from: private */
        @TargetApi(14)
        public boolean a(final Callbacks callbacks) {
            if (this.b == null) {
                return false;
            }
            AnonymousClass1 r0 = new ActivityLifecycleCallbacks() {
                public void onActivityCreated(Activity activity, Bundle bundle) {
                    callbacks.onActivityCreated(activity, bundle);
                }

                public void onActivityStarted(Activity activity) {
                    callbacks.onActivityStarted(activity);
                }

                public void onActivityResumed(Activity activity) {
                    callbacks.onActivityResumed(activity);
                }

                public void onActivityPaused(Activity activity) {
                    callbacks.onActivityPaused(activity);
                }

                public void onActivityStopped(Activity activity) {
                    callbacks.onActivityStopped(activity);
                }

                public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
                    callbacks.onActivitySaveInstanceState(activity, bundle);
                }

                public void onActivityDestroyed(Activity activity) {
                    callbacks.onActivityDestroyed(activity);
                }
            };
            this.b.registerActivityLifecycleCallbacks(r0);
            this.a.add(r0);
            return true;
        }
    }

    public static abstract class Callbacks {
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
        }

        public void onActivityStopped(Activity activity) {
        }
    }

    public ActivityLifecycleManager(Context context) {
        this.a = (Application) context.getApplicationContext();
        if (VERSION.SDK_INT >= 14) {
            this.b = new ActivityLifecycleCallbacksWrapper(this.a);
        }
    }

    public boolean registerCallbacks(Callbacks callbacks) {
        return this.b != null && this.b.a(callbacks);
    }

    public void resetCallbacks() {
        if (this.b != null) {
            this.b.a();
        }
    }
}
