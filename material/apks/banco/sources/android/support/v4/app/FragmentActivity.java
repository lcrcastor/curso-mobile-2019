package android.support.v4.app;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.Lifecycle.State;
import android.arch.lifecycle.ViewModelStore;
import android.arch.lifecycle.ViewModelStoreOwner;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.res.Configuration;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.v4.app.ActivityCompat.OnRequestPermissionsResultCallback;
import android.support.v4.app.ActivityCompat.PermissionCompatDelegate;
import android.support.v4.app.ActivityCompat.RequestPermissionsRequestCodeValidator;
import android.support.v4.util.SparseArrayCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import java.io.FileDescriptor;
import java.io.PrintWriter;

public class FragmentActivity extends BaseFragmentActivityApi16 implements ViewModelStoreOwner, OnRequestPermissionsResultCallback, RequestPermissionsRequestCodeValidator {
    final Handler c = new Handler() {
        public void handleMessage(Message message) {
            switch (message.what) {
                case 1:
                    if (FragmentActivity.this.h) {
                        FragmentActivity.this.a(false);
                        return;
                    }
                    return;
                case 2:
                    FragmentActivity.this.onResumeFragments();
                    FragmentActivity.this.d.execPendingActions();
                    return;
                default:
                    super.handleMessage(message);
                    return;
            }
        }
    };
    final FragmentController d = FragmentController.createController(new HostCallbacks());
    LoaderManager e;
    boolean f;
    boolean g;
    boolean h = true;
    boolean i = true;
    boolean j;
    boolean k;
    int l;
    SparseArrayCompat<String> m;
    private ViewModelStore n;

    class HostCallbacks extends FragmentHostCallback<FragmentActivity> {
        public HostCallbacks() {
            super(FragmentActivity.this);
        }

        public void onDump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
            FragmentActivity.this.dump(str, fileDescriptor, printWriter, strArr);
        }

        public boolean onShouldSaveFragmentState(Fragment fragment) {
            return !FragmentActivity.this.isFinishing();
        }

        public LayoutInflater onGetLayoutInflater() {
            return FragmentActivity.this.getLayoutInflater().cloneInContext(FragmentActivity.this);
        }

        /* renamed from: a */
        public FragmentActivity onGetHost() {
            return FragmentActivity.this;
        }

        public void onSupportInvalidateOptionsMenu() {
            FragmentActivity.this.supportInvalidateOptionsMenu();
        }

        public void onStartActivityFromFragment(Fragment fragment, Intent intent, int i) {
            FragmentActivity.this.startActivityFromFragment(fragment, intent, i);
        }

        public void onStartActivityFromFragment(Fragment fragment, Intent intent, int i, @Nullable Bundle bundle) {
            FragmentActivity.this.startActivityFromFragment(fragment, intent, i, bundle);
        }

        public void onStartIntentSenderFromFragment(Fragment fragment, IntentSender intentSender, int i, @Nullable Intent intent, int i2, int i3, int i4, Bundle bundle) {
            FragmentActivity.this.startIntentSenderFromFragment(fragment, intentSender, i, intent, i2, i3, i4, bundle);
        }

        public void onRequestPermissionsFromFragment(@NonNull Fragment fragment, @NonNull String[] strArr, int i) {
            FragmentActivity.this.a(fragment, strArr, i);
        }

        public boolean onShouldShowRequestPermissionRationale(@NonNull String str) {
            return ActivityCompat.shouldShowRequestPermissionRationale(FragmentActivity.this, str);
        }

        public boolean onHasWindowAnimations() {
            return FragmentActivity.this.getWindow() != null;
        }

        public int onGetWindowAnimations() {
            Window window = FragmentActivity.this.getWindow();
            if (window == null) {
                return 0;
            }
            return window.getAttributes().windowAnimations;
        }

        public void a(Fragment fragment) {
            FragmentActivity.this.onAttachFragment(fragment);
        }

        @Nullable
        public View onFindViewById(int i) {
            return FragmentActivity.this.findViewById(i);
        }

        public boolean onHasView() {
            Window window = FragmentActivity.this.getWindow();
            return (window == null || window.peekDecorView() == null) ? false : true;
        }
    }

    static final class NonConfigurationInstances {
        Object a;
        ViewModelStore b;
        FragmentManagerNonConfig c;

        NonConfigurationInstances() {
        }
    }

    public void onAttachFragment(Fragment fragment) {
    }

    public Object onRetainCustomNonConfigurationInstance() {
        return null;
    }

    public /* bridge */ /* synthetic */ View onCreateView(View view, String str, Context context, AttributeSet attributeSet) {
        return super.onCreateView(view, str, context, attributeSet);
    }

    public /* bridge */ /* synthetic */ View onCreateView(String str, Context context, AttributeSet attributeSet) {
        return super.onCreateView(str, context, attributeSet);
    }

    @RequiresApi(16)
    public /* bridge */ /* synthetic */ void startActivityForResult(Intent intent, int i2, @Nullable Bundle bundle) {
        super.startActivityForResult(intent, i2, bundle);
    }

    public /* bridge */ /* synthetic */ void startIntentSenderForResult(IntentSender intentSender, int i2, @Nullable Intent intent, int i3, int i4, int i5) {
        super.startIntentSenderForResult(intentSender, i2, intent, i3, i4, i5);
    }

    @RequiresApi(16)
    public /* bridge */ /* synthetic */ void startIntentSenderForResult(IntentSender intentSender, int i2, @Nullable Intent intent, int i3, int i4, int i5, Bundle bundle) {
        super.startIntentSenderForResult(intentSender, i2, intent, i3, i4, i5, bundle);
    }

    public void onActivityResult(int i2, int i3, Intent intent) {
        this.d.noteStateNotSaved();
        int i4 = i2 >> 16;
        if (i4 != 0) {
            int i5 = i4 - 1;
            String str = (String) this.m.get(i5);
            this.m.remove(i5);
            if (str == null) {
                Log.w("FragmentActivity", "Activity result delivered for unknown Fragment.");
                return;
            }
            Fragment findFragmentByWho = this.d.findFragmentByWho(str);
            if (findFragmentByWho == null) {
                StringBuilder sb = new StringBuilder();
                sb.append("Activity result no fragment exists for who: ");
                sb.append(str);
                Log.w("FragmentActivity", sb.toString());
            } else {
                findFragmentByWho.onActivityResult(i2 & 65535, i3, intent);
            }
            return;
        }
        PermissionCompatDelegate permissionCompatDelegate = ActivityCompat.getPermissionCompatDelegate();
        if (permissionCompatDelegate == null || !permissionCompatDelegate.onActivityResult(this, i2, i3, intent)) {
            super.onActivityResult(i2, i3, intent);
        }
    }

    public void onBackPressed() {
        FragmentManager supportFragmentManager = this.d.getSupportFragmentManager();
        boolean isStateSaved = supportFragmentManager.isStateSaved();
        if (!isStateSaved || VERSION.SDK_INT > 25) {
            if (isStateSaved || !supportFragmentManager.popBackStackImmediate()) {
                super.onBackPressed();
            }
        }
    }

    public void supportFinishAfterTransition() {
        ActivityCompat.finishAfterTransition(this);
    }

    public void setEnterSharedElementCallback(SharedElementCallback sharedElementCallback) {
        ActivityCompat.setEnterSharedElementCallback(this, sharedElementCallback);
    }

    public void setExitSharedElementCallback(SharedElementCallback sharedElementCallback) {
        ActivityCompat.setExitSharedElementCallback(this, sharedElementCallback);
    }

    public void supportPostponeEnterTransition() {
        ActivityCompat.postponeEnterTransition(this);
    }

    public void supportStartPostponedEnterTransition() {
        ActivityCompat.startPostponedEnterTransition(this);
    }

    @CallSuper
    public void onMultiWindowModeChanged(boolean z) {
        this.d.dispatchMultiWindowModeChanged(z);
    }

    @CallSuper
    public void onPictureInPictureModeChanged(boolean z) {
        this.d.dispatchPictureInPictureModeChanged(z);
    }

    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        this.d.noteStateNotSaved();
        this.d.dispatchConfigurationChanged(configuration);
    }

    @NonNull
    public ViewModelStore getViewModelStore() {
        if (getApplication() == null) {
            throw new IllegalStateException("Your activity is not yet attached to the Application instance. You can't request ViewModel before onCreate call.");
        }
        if (this.n == null) {
            this.n = new ViewModelStore();
        }
        return this.n;
    }

    public Lifecycle getLifecycle() {
        return super.getLifecycle();
    }

    public void onCreate(@Nullable Bundle bundle) {
        FragmentManagerNonConfig fragmentManagerNonConfig = null;
        this.d.attachHost(null);
        super.onCreate(bundle);
        NonConfigurationInstances nonConfigurationInstances = (NonConfigurationInstances) getLastNonConfigurationInstance();
        if (nonConfigurationInstances != null) {
            this.n = nonConfigurationInstances.b;
        }
        if (bundle != null) {
            Parcelable parcelable = bundle.getParcelable("android:support:fragments");
            FragmentController fragmentController = this.d;
            if (nonConfigurationInstances != null) {
                fragmentManagerNonConfig = nonConfigurationInstances.c;
            }
            fragmentController.restoreAllState(parcelable, fragmentManagerNonConfig);
            if (bundle.containsKey("android:support:next_request_index")) {
                this.l = bundle.getInt("android:support:next_request_index");
                int[] intArray = bundle.getIntArray("android:support:request_indicies");
                String[] stringArray = bundle.getStringArray("android:support:request_fragment_who");
                if (intArray == null || stringArray == null || intArray.length != stringArray.length) {
                    Log.w("FragmentActivity", "Invalid requestCode mapping in savedInstanceState.");
                } else {
                    this.m = new SparseArrayCompat<>(intArray.length);
                    for (int i2 = 0; i2 < intArray.length; i2++) {
                        this.m.put(intArray[i2], stringArray[i2]);
                    }
                }
            }
        }
        if (this.m == null) {
            this.m = new SparseArrayCompat<>();
            this.l = 0;
        }
        this.d.dispatchCreate();
    }

    public boolean onCreatePanelMenu(int i2, Menu menu) {
        if (i2 == 0) {
            return super.onCreatePanelMenu(i2, menu) | this.d.dispatchCreateOptionsMenu(menu, getMenuInflater());
        }
        return super.onCreatePanelMenu(i2, menu);
    }

    /* access modifiers changed from: 0000 */
    public final View a(View view, String str, Context context, AttributeSet attributeSet) {
        return this.d.onCreateView(view, str, context, attributeSet);
    }

    public void onDestroy() {
        super.onDestroy();
        a(false);
        if (this.n != null && !this.j) {
            this.n.clear();
        }
        this.d.dispatchDestroy();
    }

    public void onLowMemory() {
        super.onLowMemory();
        this.d.dispatchLowMemory();
    }

    public boolean onMenuItemSelected(int i2, MenuItem menuItem) {
        if (super.onMenuItemSelected(i2, menuItem)) {
            return true;
        }
        if (i2 == 0) {
            return this.d.dispatchOptionsItemSelected(menuItem);
        }
        if (i2 != 6) {
            return false;
        }
        return this.d.dispatchContextItemSelected(menuItem);
    }

    public void onPanelClosed(int i2, Menu menu) {
        if (i2 == 0) {
            this.d.dispatchOptionsMenuClosed(menu);
        }
        super.onPanelClosed(i2, menu);
    }

    public void onPause() {
        super.onPause();
        this.g = false;
        if (this.c.hasMessages(2)) {
            this.c.removeMessages(2);
            onResumeFragments();
        }
        this.d.dispatchPause();
    }

    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        this.d.noteStateNotSaved();
    }

    public void onStateNotSaved() {
        this.d.noteStateNotSaved();
    }

    public void onResume() {
        super.onResume();
        this.c.sendEmptyMessage(2);
        this.g = true;
        this.d.execPendingActions();
    }

    public void onPostResume() {
        super.onPostResume();
        this.c.removeMessages(2);
        onResumeFragments();
        this.d.execPendingActions();
    }

    /* access modifiers changed from: protected */
    public void onResumeFragments() {
        this.d.dispatchResume();
    }

    public boolean onPreparePanel(int i2, View view, Menu menu) {
        if (i2 != 0 || menu == null) {
            return super.onPreparePanel(i2, view, menu);
        }
        return onPrepareOptionsPanel(view, menu) | this.d.dispatchPrepareOptionsMenu(menu);
    }

    /* access modifiers changed from: protected */
    @RestrictTo({Scope.LIBRARY_GROUP})
    public boolean onPrepareOptionsPanel(View view, Menu menu) {
        return super.onPreparePanel(0, view, menu);
    }

    public final Object onRetainNonConfigurationInstance() {
        if (this.h) {
            a(true);
        }
        Object onRetainCustomNonConfigurationInstance = onRetainCustomNonConfigurationInstance();
        FragmentManagerNonConfig retainNestedNonConfig = this.d.retainNestedNonConfig();
        if (retainNestedNonConfig == null && this.n == null && onRetainCustomNonConfigurationInstance == null) {
            return null;
        }
        NonConfigurationInstances nonConfigurationInstances = new NonConfigurationInstances();
        nonConfigurationInstances.a = onRetainCustomNonConfigurationInstance;
        nonConfigurationInstances.b = this.n;
        nonConfigurationInstances.c = retainNestedNonConfig;
        return nonConfigurationInstances;
    }

    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        b();
        Parcelable saveAllState = this.d.saveAllState();
        if (saveAllState != null) {
            bundle.putParcelable("android:support:fragments", saveAllState);
        }
        if (this.m.size() > 0) {
            bundle.putInt("android:support:next_request_index", this.l);
            int[] iArr = new int[this.m.size()];
            String[] strArr = new String[this.m.size()];
            for (int i2 = 0; i2 < this.m.size(); i2++) {
                iArr[i2] = this.m.keyAt(i2);
                strArr[i2] = (String) this.m.valueAt(i2);
            }
            bundle.putIntArray("android:support:request_indicies", iArr);
            bundle.putStringArray("android:support:request_fragment_who", strArr);
        }
    }

    /* access modifiers changed from: protected */
    public void onStart() {
        super.onStart();
        this.h = false;
        this.i = false;
        this.c.removeMessages(1);
        if (!this.f) {
            this.f = true;
            this.d.dispatchActivityCreated();
        }
        this.d.noteStateNotSaved();
        this.d.execPendingActions();
        this.d.dispatchStart();
    }

    public void onStop() {
        super.onStop();
        this.h = true;
        b();
        this.c.sendEmptyMessage(1);
        this.d.dispatchStop();
    }

    public Object getLastCustomNonConfigurationInstance() {
        NonConfigurationInstances nonConfigurationInstances = (NonConfigurationInstances) getLastNonConfigurationInstance();
        if (nonConfigurationInstances != null) {
            return nonConfigurationInstances.a;
        }
        return null;
    }

    @Deprecated
    public void supportInvalidateOptionsMenu() {
        invalidateOptionsMenu();
    }

    public void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        super.dump(str, fileDescriptor, printWriter, strArr);
        printWriter.print(str);
        printWriter.print("Local FragmentActivity ");
        printWriter.print(Integer.toHexString(System.identityHashCode(this)));
        printWriter.println(" State:");
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append("  ");
        String sb2 = sb.toString();
        printWriter.print(sb2);
        printWriter.print("mCreated=");
        printWriter.print(this.f);
        printWriter.print("mResumed=");
        printWriter.print(this.g);
        printWriter.print(" mStopped=");
        printWriter.print(this.h);
        printWriter.print(" mReallyStopped=");
        printWriter.println(this.i);
        if (this.e != null) {
            this.e.dump(sb2, fileDescriptor, printWriter, strArr);
        }
        this.d.getSupportFragmentManager().dump(str, fileDescriptor, printWriter, strArr);
    }

    /* access modifiers changed from: 0000 */
    public void a(boolean z) {
        if (!this.i) {
            this.i = true;
            this.j = z;
            this.c.removeMessages(1);
            a();
        }
    }

    /* access modifiers changed from: 0000 */
    public void a() {
        this.d.dispatchReallyStop();
    }

    public FragmentManager getSupportFragmentManager() {
        return this.d.getSupportFragmentManager();
    }

    public LoaderManager getSupportLoaderManager() {
        if (this.e != null) {
            return this.e;
        }
        this.e = new LoaderManagerImpl(this, getViewModelStore());
        return this.e;
    }

    public void startActivityForResult(Intent intent, int i2) {
        if (!this.b && i2 != -1) {
            a(i2);
        }
        super.startActivityForResult(intent, i2);
    }

    public final void validateRequestPermissionsRequestCode(int i2) {
        if (!this.k && i2 != -1) {
            a(i2);
        }
    }

    public void onRequestPermissionsResult(int i2, @NonNull String[] strArr, @NonNull int[] iArr) {
        this.d.noteStateNotSaved();
        int i3 = (i2 >> 16) & 65535;
        if (i3 != 0) {
            int i4 = i3 - 1;
            String str = (String) this.m.get(i4);
            this.m.remove(i4);
            if (str == null) {
                Log.w("FragmentActivity", "Activity result delivered for unknown Fragment.");
                return;
            }
            Fragment findFragmentByWho = this.d.findFragmentByWho(str);
            if (findFragmentByWho == null) {
                StringBuilder sb = new StringBuilder();
                sb.append("Activity result no fragment exists for who: ");
                sb.append(str);
                Log.w("FragmentActivity", sb.toString());
            } else {
                findFragmentByWho.onRequestPermissionsResult(i2 & 65535, strArr, iArr);
            }
        }
    }

    public void startActivityFromFragment(Fragment fragment, Intent intent, int i2) {
        startActivityFromFragment(fragment, intent, i2, null);
    }

    public void startActivityFromFragment(Fragment fragment, Intent intent, int i2, @Nullable Bundle bundle) {
        this.b = true;
        if (i2 == -1) {
            try {
                ActivityCompat.startActivityForResult(this, intent, -1, bundle);
            } finally {
                this.b = false;
            }
        } else {
            a(i2);
            ActivityCompat.startActivityForResult(this, intent, ((a(fragment) + 1) << 16) + (i2 & 65535), bundle);
            this.b = false;
        }
    }

    public void startIntentSenderFromFragment(Fragment fragment, IntentSender intentSender, int i2, @Nullable Intent intent, int i3, int i4, int i5, Bundle bundle) {
        int i6 = i2;
        this.a = true;
        if (i6 == -1) {
            try {
                ActivityCompat.startIntentSenderForResult(this, intentSender, i6, intent, i3, i4, i5, bundle);
                this.a = false;
            } catch (Throwable th) {
                Throwable th2 = th;
                this.a = false;
                throw th2;
            }
        } else {
            a(i6);
            ActivityCompat.startIntentSenderForResult(this, intentSender, ((a(fragment) + 1) << 16) + (65535 & i6), intent, i3, i4, i5, bundle);
            this.a = false;
        }
    }

    private int a(Fragment fragment) {
        if (this.m.size() >= 65534) {
            throw new IllegalStateException("Too many pending Fragment activity results.");
        }
        while (this.m.indexOfKey(this.l) >= 0) {
            this.l = (this.l + 1) % 65534;
        }
        int i2 = this.l;
        this.m.put(i2, fragment.o);
        this.l = (this.l + 1) % 65534;
        return i2;
    }

    /* access modifiers changed from: 0000 */
    public void a(Fragment fragment, String[] strArr, int i2) {
        if (i2 == -1) {
            ActivityCompat.requestPermissions(this, strArr, i2);
            return;
        }
        a(i2);
        try {
            this.k = true;
            ActivityCompat.requestPermissions(this, strArr, ((a(fragment) + 1) << 16) + (i2 & 65535));
        } finally {
            this.k = false;
        }
    }

    private void b() {
        do {
        } while (a(getSupportFragmentManager(), State.CREATED));
    }

    private static boolean a(FragmentManager fragmentManager, State state) {
        boolean z = false;
        for (Fragment fragment : fragmentManager.getFragments()) {
            if (fragment != null) {
                if (fragment.getLifecycle().getCurrentState().isAtLeast(State.STARTED)) {
                    fragment.ac.markState(state);
                    z = true;
                }
                FragmentManager b = fragment.b();
                if (b != null) {
                    z |= a(b, state);
                }
            }
        }
        return z;
    }
}
