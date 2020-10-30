package android.support.v7.app;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.annotation.VisibleForTesting;
import android.support.v7.view.SupportActionModeWrapper.CallbackWrapper;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ActionMode;
import android.view.View;
import android.view.Window;
import android.view.Window.Callback;

@RequiresApi(14)
class AppCompatDelegateImplV14 extends AppCompatDelegateImplV9 {
    private int t = -100;
    private boolean u;
    private boolean v = true;
    private AutoNightModeManager w;

    class AppCompatWindowCallbackV14 extends AppCompatWindowCallbackBase {
        AppCompatWindowCallbackV14(Callback callback) {
            super(callback);
        }

        public ActionMode onWindowStartingActionMode(ActionMode.Callback callback) {
            if (AppCompatDelegateImplV14.this.isHandleNativeActionModesEnabled()) {
                return a(callback);
            }
            return super.onWindowStartingActionMode(callback);
        }

        /* access modifiers changed from: 0000 */
        public final ActionMode a(ActionMode.Callback callback) {
            CallbackWrapper callbackWrapper = new CallbackWrapper(AppCompatDelegateImplV14.this.a, callback);
            android.support.v7.view.ActionMode startSupportActionMode = AppCompatDelegateImplV14.this.startSupportActionMode(callbackWrapper);
            if (startSupportActionMode != null) {
                return callbackWrapper.getActionModeWrapper(startSupportActionMode);
            }
            return null;
        }
    }

    @VisibleForTesting
    final class AutoNightModeManager {
        private TwilightManager b;
        private boolean c;
        private BroadcastReceiver d;
        private IntentFilter e;

        AutoNightModeManager(TwilightManager twilightManager) {
            this.b = twilightManager;
            this.c = twilightManager.a();
        }

        /* access modifiers changed from: 0000 */
        public final int a() {
            this.c = this.b.a();
            return this.c ? 2 : 1;
        }

        /* access modifiers changed from: 0000 */
        public final void b() {
            boolean a2 = this.b.a();
            if (a2 != this.c) {
                this.c = a2;
                AppCompatDelegateImplV14.this.applyDayNight();
            }
        }

        /* access modifiers changed from: 0000 */
        public final void c() {
            d();
            if (this.d == null) {
                this.d = new BroadcastReceiver() {
                    public void onReceive(Context context, Intent intent) {
                        AutoNightModeManager.this.b();
                    }
                };
            }
            if (this.e == null) {
                this.e = new IntentFilter();
                this.e.addAction("android.intent.action.TIME_SET");
                this.e.addAction("android.intent.action.TIMEZONE_CHANGED");
                this.e.addAction("android.intent.action.TIME_TICK");
            }
            AppCompatDelegateImplV14.this.a.registerReceiver(this.d, this.e);
        }

        /* access modifiers changed from: 0000 */
        public final void d() {
            if (this.d != null) {
                AppCompatDelegateImplV14.this.a.unregisterReceiver(this.d);
                this.d = null;
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public View a(View view, String str, Context context, AttributeSet attributeSet) {
        return null;
    }

    AppCompatDelegateImplV14(Context context, Window window, AppCompatCallback appCompatCallback) {
        super(context, window, appCompatCallback);
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (bundle != null && this.t == -100) {
            this.t = bundle.getInt("appcompat:local_night_mode", -100);
        }
    }

    public boolean hasWindowFeature(int i) {
        return super.hasWindowFeature(i) || this.b.hasFeature(i);
    }

    /* access modifiers changed from: 0000 */
    public Callback a(Callback callback) {
        return new AppCompatWindowCallbackV14(callback);
    }

    public void setHandleNativeActionModesEnabled(boolean z) {
        this.v = z;
    }

    public boolean isHandleNativeActionModesEnabled() {
        return this.v;
    }

    public boolean applyDayNight() {
        int k = k();
        int a = a(k);
        boolean e = a != -1 ? e(a) : false;
        if (k == 0) {
            l();
            this.w.c();
        }
        this.u = true;
        return e;
    }

    public void onStart() {
        super.onStart();
        applyDayNight();
    }

    public void onStop() {
        super.onStop();
        if (this.w != null) {
            this.w.d();
        }
    }

    public void setLocalNightMode(int i) {
        switch (i) {
            case -1:
            case 0:
            case 1:
            case 2:
                if (this.t != i) {
                    this.t = i;
                    if (this.u) {
                        applyDayNight();
                        return;
                    }
                    return;
                }
                return;
            default:
                Log.i("AppCompatDelegate", "setLocalNightMode() called with an unknown mode");
                return;
        }
    }

    /* access modifiers changed from: 0000 */
    public int a(int i) {
        if (i == -100) {
            return -1;
        }
        if (i != 0) {
            return i;
        }
        l();
        return this.w.a();
    }

    private int k() {
        return this.t != -100 ? this.t : getDefaultNightMode();
    }

    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        if (this.t != -100) {
            bundle.putInt("appcompat:local_night_mode", this.t);
        }
    }

    public void onDestroy() {
        super.onDestroy();
        if (this.w != null) {
            this.w.d();
        }
    }

    private boolean e(int i) {
        Resources resources = this.a.getResources();
        Configuration configuration = resources.getConfiguration();
        int i2 = configuration.uiMode & 48;
        int i3 = i == 2 ? 32 : 16;
        if (i2 == i3) {
            return false;
        }
        if (m()) {
            ((Activity) this.a).recreate();
        } else {
            Configuration configuration2 = new Configuration(configuration);
            DisplayMetrics displayMetrics = resources.getDisplayMetrics();
            configuration2.uiMode = i3 | (configuration2.uiMode & -49);
            resources.updateConfiguration(configuration2, displayMetrics);
            if (VERSION.SDK_INT < 26) {
                ResourcesFlusher.a(resources);
            }
        }
        return true;
    }

    private void l() {
        if (this.w == null) {
            this.w = new AutoNightModeManager(TwilightManager.a(this.a));
        }
    }

    private boolean m() {
        boolean z = false;
        if (!this.u || !(this.a instanceof Activity)) {
            return false;
        }
        try {
            if ((this.a.getPackageManager().getActivityInfo(new ComponentName(this.a, this.a.getClass()), 0).configChanges & 512) == 0) {
                z = true;
            }
            return z;
        } catch (NameNotFoundException e) {
            Log.d("AppCompatDelegate", "Exception while getting ActivityInfo", e);
            return true;
        }
    }
}
