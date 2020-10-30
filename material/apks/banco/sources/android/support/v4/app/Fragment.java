package android.support.v4.app;

import android.animation.Animator;
import android.app.Activity;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.Lifecycle.Event;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.ViewModelStore;
import android.arch.lifecycle.ViewModelStoreOwner;
import android.content.ComponentCallbacks;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Looper;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.annotation.StringRes;
import android.support.v4.util.DebugUtils;
import android.support.v4.util.SimpleArrayMap;
import android.support.v4.view.LayoutInflaterCompat;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnCreateContextMenuListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import ar.com.santander.rio.mbanking.utils.UtilsCuentas;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;

public class Fragment implements LifecycleOwner, ViewModelStoreOwner, ComponentCallbacks, OnCreateContextMenuListener {
    private static final SimpleArrayMap<String, Class<?>> a = new SimpleArrayMap<>();
    static final Object j = new Object();
    FragmentManagerImpl A;
    FragmentHostCallback B;
    FragmentManagerImpl C;
    FragmentManagerNonConfig D;
    ViewModelStore E;
    Fragment F;
    int G;
    int H;
    String I;
    boolean J;
    boolean K;
    boolean L;
    boolean M;
    boolean N;
    boolean O = true;
    boolean P;
    ViewGroup Q;
    View R;
    View S;
    boolean T;
    boolean U = true;
    LoaderManagerImpl V;
    AnimationInfo W;
    boolean X;
    boolean Y;
    float Z;
    LayoutInflater aa;
    boolean ab;
    LifecycleRegistry ac = new LifecycleRegistry(this);
    int k = 0;
    Bundle l;
    SparseArray<Parcelable> m;
    int n = -1;
    String o;
    Bundle p;
    Fragment q;
    int r = -1;
    int s;
    boolean t;
    boolean u;
    boolean v;
    boolean w;
    boolean x;
    boolean y;
    int z;

    static class AnimationInfo {
        View a;
        Animator b;
        int c;
        int d;
        int e;
        int f;
        SharedElementCallback g = null;
        SharedElementCallback h = null;
        boolean i;
        OnStartEnterTransitionListener j;
        boolean k;
        /* access modifiers changed from: private */
        public Object l = null;
        /* access modifiers changed from: private */
        public Object m = Fragment.j;
        /* access modifiers changed from: private */
        public Object n = null;
        /* access modifiers changed from: private */
        public Object o = Fragment.j;
        /* access modifiers changed from: private */
        public Object p = null;
        /* access modifiers changed from: private */
        public Object q = Fragment.j;
        /* access modifiers changed from: private */
        public Boolean r;
        /* access modifiers changed from: private */
        public Boolean s;

        AnimationInfo() {
        }
    }

    public static class InstantiationException extends RuntimeException {
        public InstantiationException(String str, Exception exc) {
            super(str, exc);
        }
    }

    interface OnStartEnterTransitionListener {
        void a();

        void b();
    }

    public static class SavedState implements Parcelable {
        public static final Creator<SavedState> CREATOR = new Creator<SavedState>() {
            /* renamed from: a */
            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel, null);
            }

            /* renamed from: a */
            public SavedState[] newArray(int i) {
                return new SavedState[i];
            }
        };
        final Bundle a;

        public int describeContents() {
            return 0;
        }

        SavedState(Bundle bundle) {
            this.a = bundle;
        }

        SavedState(Parcel parcel, ClassLoader classLoader) {
            this.a = parcel.readBundle();
            if (classLoader != null && this.a != null) {
                this.a.setClassLoader(classLoader);
            }
        }

        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeBundle(this.a);
        }
    }

    public void onActivityResult(int i, int i2, Intent intent) {
    }

    public void onAttachFragment(Fragment fragment) {
    }

    public boolean onContextItemSelected(MenuItem menuItem) {
        return false;
    }

    public Animation onCreateAnimation(int i, boolean z2, int i2) {
        return null;
    }

    public Animator onCreateAnimator(int i, boolean z2, int i2) {
        return null;
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
    }

    @Nullable
    public View onCreateView(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        return null;
    }

    public void onDestroyOptionsMenu() {
    }

    public void onHiddenChanged(boolean z2) {
    }

    public void onMultiWindowModeChanged(boolean z2) {
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        return false;
    }

    public void onOptionsMenuClosed(Menu menu) {
    }

    public void onPictureInPictureModeChanged(boolean z2) {
    }

    public void onPrepareOptionsMenu(Menu menu) {
    }

    public void onRequestPermissionsResult(int i, @NonNull String[] strArr, @NonNull int[] iArr) {
    }

    public void onSaveInstanceState(@NonNull Bundle bundle) {
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle bundle) {
    }

    public Lifecycle getLifecycle() {
        return this.ac;
    }

    @NonNull
    public ViewModelStore getViewModelStore() {
        if (getContext() == null) {
            throw new IllegalStateException("Can't access ViewModels from detached fragment");
        }
        if (this.E == null) {
            this.E = new ViewModelStore();
        }
        return this.E;
    }

    public static Fragment instantiate(Context context, String str) {
        return instantiate(context, str, null);
    }

    public static Fragment instantiate(Context context, String str, @Nullable Bundle bundle) {
        try {
            Class cls = (Class) a.get(str);
            if (cls == null) {
                cls = context.getClassLoader().loadClass(str);
                a.put(str, cls);
            }
            Fragment fragment = (Fragment) cls.getConstructor(new Class[0]).newInstance(new Object[0]);
            if (bundle != null) {
                bundle.setClassLoader(fragment.getClass().getClassLoader());
                fragment.setArguments(bundle);
            }
            return fragment;
        } catch (ClassNotFoundException e) {
            StringBuilder sb = new StringBuilder();
            sb.append("Unable to instantiate fragment ");
            sb.append(str);
            sb.append(": make sure class name exists, is public, and has an");
            sb.append(" empty constructor that is public");
            throw new InstantiationException(sb.toString(), e);
        } catch (InstantiationException e2) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Unable to instantiate fragment ");
            sb2.append(str);
            sb2.append(": make sure class name exists, is public, and has an");
            sb2.append(" empty constructor that is public");
            throw new InstantiationException(sb2.toString(), e2);
        } catch (IllegalAccessException e3) {
            StringBuilder sb3 = new StringBuilder();
            sb3.append("Unable to instantiate fragment ");
            sb3.append(str);
            sb3.append(": make sure class name exists, is public, and has an");
            sb3.append(" empty constructor that is public");
            throw new InstantiationException(sb3.toString(), e3);
        } catch (NoSuchMethodException e4) {
            StringBuilder sb4 = new StringBuilder();
            sb4.append("Unable to instantiate fragment ");
            sb4.append(str);
            sb4.append(": could not find Fragment constructor");
            throw new InstantiationException(sb4.toString(), e4);
        } catch (InvocationTargetException e5) {
            StringBuilder sb5 = new StringBuilder();
            sb5.append("Unable to instantiate fragment ");
            sb5.append(str);
            sb5.append(": calling Fragment constructor caused an exception");
            throw new InstantiationException(sb5.toString(), e5);
        }
    }

    static boolean a(Context context, String str) {
        try {
            Class cls = (Class) a.get(str);
            if (cls == null) {
                cls = context.getClassLoader().loadClass(str);
                a.put(str, cls);
            }
            return Fragment.class.isAssignableFrom(cls);
        } catch (ClassNotFoundException unused) {
            return false;
        }
    }

    /* access modifiers changed from: 0000 */
    public final void a(Bundle bundle) {
        if (this.m != null) {
            this.S.restoreHierarchyState(this.m);
            this.m = null;
        }
        this.P = false;
        onViewStateRestored(bundle);
        if (!this.P) {
            StringBuilder sb = new StringBuilder();
            sb.append("Fragment ");
            sb.append(this);
            sb.append(" did not call through to super.onViewStateRestored()");
            throw new SuperNotCalledException(sb.toString());
        }
    }

    /* access modifiers changed from: 0000 */
    public final void a(int i, Fragment fragment) {
        this.n = i;
        if (fragment != null) {
            StringBuilder sb = new StringBuilder();
            sb.append(fragment.o);
            sb.append(":");
            sb.append(this.n);
            this.o = sb.toString();
            return;
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append("android:fragment:");
        sb2.append(this.n);
        this.o = sb2.toString();
    }

    /* access modifiers changed from: 0000 */
    public final boolean a() {
        return this.z > 0;
    }

    public final boolean equals(Object obj) {
        return super.equals(obj);
    }

    public final int hashCode() {
        return super.hashCode();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(128);
        DebugUtils.buildShortClassTag(this, sb);
        if (this.n >= 0) {
            sb.append(" #");
            sb.append(this.n);
        }
        if (this.G != 0) {
            sb.append(" id=0x");
            sb.append(Integer.toHexString(this.G));
        }
        if (this.I != null) {
            sb.append(UtilsCuentas.SEPARAOR2);
            sb.append(this.I);
        }
        sb.append('}');
        return sb.toString();
    }

    public final int getId() {
        return this.G;
    }

    @Nullable
    public final String getTag() {
        return this.I;
    }

    public void setArguments(@Nullable Bundle bundle) {
        if (this.n < 0 || !isStateSaved()) {
            this.p = bundle;
            return;
        }
        throw new IllegalStateException("Fragment already active and state has been saved");
    }

    @Nullable
    public final Bundle getArguments() {
        return this.p;
    }

    public final boolean isStateSaved() {
        if (this.A == null) {
            return false;
        }
        return this.A.isStateSaved();
    }

    public void setInitialSavedState(@Nullable SavedState savedState) {
        if (this.n >= 0) {
            throw new IllegalStateException("Fragment already active");
        }
        this.l = (savedState == null || savedState.a == null) ? null : savedState.a;
    }

    public void setTargetFragment(@Nullable Fragment fragment, int i) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentManager fragmentManager2 = fragment != null ? fragment.getFragmentManager() : null;
        if (fragmentManager == null || fragmentManager2 == null || fragmentManager == fragmentManager2) {
            for (Fragment fragment2 = fragment; fragment2 != null; fragment2 = fragment2.getTargetFragment()) {
                if (fragment2 == this) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("Setting ");
                    sb.append(fragment);
                    sb.append(" as the target of ");
                    sb.append(this);
                    sb.append(" would create a target cycle");
                    throw new IllegalArgumentException(sb.toString());
                }
            }
            this.q = fragment;
            this.s = i;
            return;
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append("Fragment ");
        sb2.append(fragment);
        sb2.append(" must share the same FragmentManager to be set as a target fragment");
        throw new IllegalArgumentException(sb2.toString());
    }

    @Nullable
    public final Fragment getTargetFragment() {
        return this.q;
    }

    public final int getTargetRequestCode() {
        return this.s;
    }

    @Nullable
    public Context getContext() {
        if (this.B == null) {
            return null;
        }
        return this.B.c();
    }

    @NonNull
    public final Context requireContext() {
        Context context = getContext();
        if (context != null) {
            return context;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Fragment ");
        sb.append(this);
        sb.append(" not attached to a context.");
        throw new IllegalStateException(sb.toString());
    }

    @Nullable
    public final FragmentActivity getActivity() {
        if (this.B == null) {
            return null;
        }
        return (FragmentActivity) this.B.b();
    }

    @NonNull
    public final FragmentActivity requireActivity() {
        FragmentActivity activity = getActivity();
        if (activity != null) {
            return activity;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Fragment ");
        sb.append(this);
        sb.append(" not attached to an activity.");
        throw new IllegalStateException(sb.toString());
    }

    @Nullable
    public final Object getHost() {
        if (this.B == null) {
            return null;
        }
        return this.B.onGetHost();
    }

    @NonNull
    public final Object requireHost() {
        Object host = getHost();
        if (host != null) {
            return host;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Fragment ");
        sb.append(this);
        sb.append(" not attached to a host.");
        throw new IllegalStateException(sb.toString());
    }

    @NonNull
    public final Resources getResources() {
        return requireContext().getResources();
    }

    @NonNull
    public final CharSequence getText(@StringRes int i) {
        return getResources().getText(i);
    }

    @NonNull
    public final String getString(@StringRes int i) {
        return getResources().getString(i);
    }

    @NonNull
    public final String getString(@StringRes int i, Object... objArr) {
        return getResources().getString(i, objArr);
    }

    @Nullable
    public final FragmentManager getFragmentManager() {
        return this.A;
    }

    @NonNull
    public final FragmentManager requireFragmentManager() {
        FragmentManager fragmentManager = getFragmentManager();
        if (fragmentManager != null) {
            return fragmentManager;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Fragment ");
        sb.append(this);
        sb.append(" not associated with a fragment manager.");
        throw new IllegalStateException(sb.toString());
    }

    @NonNull
    public final FragmentManager getChildFragmentManager() {
        if (this.C == null) {
            d();
            if (this.k >= 5) {
                this.C.n();
            } else if (this.k >= 4) {
                this.C.m();
            } else if (this.k >= 2) {
                this.C.l();
            } else if (this.k >= 1) {
                this.C.k();
            }
        }
        return this.C;
    }

    /* access modifiers changed from: 0000 */
    @Nullable
    public FragmentManager b() {
        return this.C;
    }

    @Nullable
    public final Fragment getParentFragment() {
        return this.F;
    }

    public final boolean isAdded() {
        return this.B != null && this.t;
    }

    public final boolean isDetached() {
        return this.K;
    }

    public final boolean isRemoving() {
        return this.u;
    }

    public final boolean isInLayout() {
        return this.w;
    }

    public final boolean isResumed() {
        return this.k >= 5;
    }

    public final boolean isVisible() {
        return isAdded() && !isHidden() && this.R != null && this.R.getWindowToken() != null && this.R.getVisibility() == 0;
    }

    public final boolean isHidden() {
        return this.J;
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    public final boolean hasOptionsMenu() {
        return this.N;
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    public final boolean isMenuVisible() {
        return this.O;
    }

    public void setRetainInstance(boolean z2) {
        this.L = z2;
    }

    public final boolean getRetainInstance() {
        return this.L;
    }

    public void setHasOptionsMenu(boolean z2) {
        if (this.N != z2) {
            this.N = z2;
            if (isAdded() && !isHidden()) {
                this.B.onSupportInvalidateOptionsMenu();
            }
        }
    }

    public void setMenuVisibility(boolean z2) {
        if (this.O != z2) {
            this.O = z2;
            if (this.N && isAdded() && !isHidden()) {
                this.B.onSupportInvalidateOptionsMenu();
            }
        }
    }

    public void setUserVisibleHint(boolean z2) {
        if (!this.U && z2 && this.k < 4 && this.A != null && isAdded()) {
            this.A.a(this);
        }
        this.U = z2;
        this.T = this.k < 4 && !z2;
        if (this.l != null) {
            this.l.putBoolean("android:user_visible_hint", this.U);
        }
    }

    public boolean getUserVisibleHint() {
        return this.U;
    }

    public LoaderManager getLoaderManager() {
        if (this.V != null) {
            return this.V;
        }
        this.V = new LoaderManagerImpl(this, getViewModelStore());
        return this.V;
    }

    public void startActivity(Intent intent) {
        startActivity(intent, null);
    }

    public void startActivity(Intent intent, @Nullable Bundle bundle) {
        if (this.B == null) {
            StringBuilder sb = new StringBuilder();
            sb.append("Fragment ");
            sb.append(this);
            sb.append(" not attached to Activity");
            throw new IllegalStateException(sb.toString());
        }
        this.B.onStartActivityFromFragment(this, intent, -1, bundle);
    }

    public void startActivityForResult(Intent intent, int i) {
        startActivityForResult(intent, i, null);
    }

    public void startActivityForResult(Intent intent, int i, @Nullable Bundle bundle) {
        if (this.B == null) {
            StringBuilder sb = new StringBuilder();
            sb.append("Fragment ");
            sb.append(this);
            sb.append(" not attached to Activity");
            throw new IllegalStateException(sb.toString());
        }
        this.B.onStartActivityFromFragment(this, intent, i, bundle);
    }

    public void startIntentSenderForResult(IntentSender intentSender, int i, @Nullable Intent intent, int i2, int i3, int i4, Bundle bundle) {
        if (this.B == null) {
            StringBuilder sb = new StringBuilder();
            sb.append("Fragment ");
            sb.append(this);
            sb.append(" not attached to Activity");
            throw new IllegalStateException(sb.toString());
        }
        this.B.onStartIntentSenderFromFragment(this, intentSender, i, intent, i2, i3, i4, bundle);
    }

    public final void requestPermissions(@NonNull String[] strArr, int i) {
        if (this.B == null) {
            StringBuilder sb = new StringBuilder();
            sb.append("Fragment ");
            sb.append(this);
            sb.append(" not attached to Activity");
            throw new IllegalStateException(sb.toString());
        }
        this.B.onRequestPermissionsFromFragment(this, strArr, i);
    }

    public boolean shouldShowRequestPermissionRationale(@NonNull String str) {
        if (this.B != null) {
            return this.B.onShouldShowRequestPermissionRationale(str);
        }
        return false;
    }

    @NonNull
    public LayoutInflater onGetLayoutInflater(@Nullable Bundle bundle) {
        return getLayoutInflater(bundle);
    }

    public final LayoutInflater getLayoutInflater() {
        if (this.aa == null) {
            return b((Bundle) null);
        }
        return this.aa;
    }

    /* access modifiers changed from: 0000 */
    @NonNull
    public LayoutInflater b(@Nullable Bundle bundle) {
        this.aa = onGetLayoutInflater(bundle);
        return this.aa;
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    @Deprecated
    @NonNull
    public LayoutInflater getLayoutInflater(@Nullable Bundle bundle) {
        if (this.B == null) {
            throw new IllegalStateException("onGetLayoutInflater() cannot be executed until the Fragment is attached to the FragmentManager.");
        }
        LayoutInflater onGetLayoutInflater = this.B.onGetLayoutInflater();
        getChildFragmentManager();
        LayoutInflaterCompat.setFactory2(onGetLayoutInflater, this.C.u());
        return onGetLayoutInflater;
    }

    @CallSuper
    public void onInflate(Context context, AttributeSet attributeSet, Bundle bundle) {
        this.P = true;
        Activity b = this.B == null ? null : this.B.b();
        if (b != null) {
            this.P = false;
            onInflate(b, attributeSet, bundle);
        }
    }

    @Deprecated
    @CallSuper
    public void onInflate(Activity activity, AttributeSet attributeSet, Bundle bundle) {
        this.P = true;
    }

    @CallSuper
    public void onAttach(Context context) {
        this.P = true;
        Activity b = this.B == null ? null : this.B.b();
        if (b != null) {
            this.P = false;
            onAttach(b);
        }
    }

    @Deprecated
    @CallSuper
    public void onAttach(Activity activity) {
        this.P = true;
    }

    @CallSuper
    public void onCreate(@Nullable Bundle bundle) {
        this.P = true;
        c(bundle);
        if (this.C != null && !this.C.a(1)) {
            this.C.k();
        }
    }

    /* access modifiers changed from: 0000 */
    public void c(@Nullable Bundle bundle) {
        if (bundle != null) {
            Parcelable parcelable = bundle.getParcelable("android:support:fragments");
            if (parcelable != null) {
                if (this.C == null) {
                    d();
                }
                this.C.a(parcelable, this.D);
                this.D = null;
                this.C.k();
            }
        }
    }

    @Nullable
    public View getView() {
        return this.R;
    }

    @CallSuper
    public void onActivityCreated(@Nullable Bundle bundle) {
        this.P = true;
    }

    @CallSuper
    public void onViewStateRestored(@Nullable Bundle bundle) {
        this.P = true;
    }

    @CallSuper
    public void onStart() {
        this.P = true;
    }

    @CallSuper
    public void onResume() {
        this.P = true;
    }

    @CallSuper
    public void onConfigurationChanged(Configuration configuration) {
        this.P = true;
    }

    @CallSuper
    public void onPause() {
        this.P = true;
    }

    @CallSuper
    public void onStop() {
        this.P = true;
    }

    @CallSuper
    public void onLowMemory() {
        this.P = true;
    }

    @CallSuper
    public void onDestroyView() {
        this.P = true;
    }

    @CallSuper
    public void onDestroy() {
        this.P = true;
        if (this.E != null && !this.B.d.isStateSaved()) {
            this.E.clear();
        }
    }

    /* access modifiers changed from: 0000 */
    public void c() {
        this.n = -1;
        this.o = null;
        this.t = false;
        this.u = false;
        this.v = false;
        this.w = false;
        this.x = false;
        this.z = 0;
        this.A = null;
        this.C = null;
        this.B = null;
        this.G = 0;
        this.H = 0;
        this.I = null;
        this.J = false;
        this.K = false;
        this.M = false;
    }

    @CallSuper
    public void onDetach() {
        this.P = true;
    }

    public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenuInfo contextMenuInfo) {
        getActivity().onCreateContextMenu(contextMenu, view, contextMenuInfo);
    }

    public void registerForContextMenu(View view) {
        view.setOnCreateContextMenuListener(this);
    }

    public void unregisterForContextMenu(View view) {
        view.setOnCreateContextMenuListener(null);
    }

    public void setEnterSharedElementCallback(SharedElementCallback sharedElementCallback) {
        z().g = sharedElementCallback;
    }

    public void setExitSharedElementCallback(SharedElementCallback sharedElementCallback) {
        z().h = sharedElementCallback;
    }

    public void setEnterTransition(@Nullable Object obj) {
        z().l = obj;
    }

    @Nullable
    public Object getEnterTransition() {
        if (this.W == null) {
            return null;
        }
        return this.W.l;
    }

    public void setReturnTransition(@Nullable Object obj) {
        z().m = obj;
    }

    @Nullable
    public Object getReturnTransition() {
        Object obj;
        if (this.W == null) {
            return null;
        }
        if (this.W.m == j) {
            obj = getEnterTransition();
        } else {
            obj = this.W.m;
        }
        return obj;
    }

    public void setExitTransition(@Nullable Object obj) {
        z().n = obj;
    }

    @Nullable
    public Object getExitTransition() {
        if (this.W == null) {
            return null;
        }
        return this.W.n;
    }

    public void setReenterTransition(@Nullable Object obj) {
        z().o = obj;
    }

    public Object getReenterTransition() {
        Object obj;
        if (this.W == null) {
            return null;
        }
        if (this.W.o == j) {
            obj = getExitTransition();
        } else {
            obj = this.W.o;
        }
        return obj;
    }

    public void setSharedElementEnterTransition(@Nullable Object obj) {
        z().p = obj;
    }

    @Nullable
    public Object getSharedElementEnterTransition() {
        if (this.W == null) {
            return null;
        }
        return this.W.p;
    }

    public void setSharedElementReturnTransition(@Nullable Object obj) {
        z().q = obj;
    }

    @Nullable
    public Object getSharedElementReturnTransition() {
        Object obj;
        if (this.W == null) {
            return null;
        }
        if (this.W.q == j) {
            obj = getSharedElementEnterTransition();
        } else {
            obj = this.W.q;
        }
        return obj;
    }

    public void setAllowEnterTransitionOverlap(boolean z2) {
        z().s = Boolean.valueOf(z2);
    }

    public boolean getAllowEnterTransitionOverlap() {
        if (this.W == null || this.W.s == null) {
            return true;
        }
        return this.W.s.booleanValue();
    }

    public void setAllowReturnTransitionOverlap(boolean z2) {
        z().r = Boolean.valueOf(z2);
    }

    public boolean getAllowReturnTransitionOverlap() {
        if (this.W == null || this.W.r == null) {
            return true;
        }
        return this.W.r.booleanValue();
    }

    public void postponeEnterTransition() {
        z().i = true;
    }

    public void startPostponedEnterTransition() {
        if (this.A == null || this.A.m == null) {
            z().i = false;
        } else if (Looper.myLooper() != this.A.m.d().getLooper()) {
            this.A.m.d().postAtFrontOfQueue(new Runnable() {
                public void run() {
                    Fragment.this.y();
                }
            });
        } else {
            y();
        }
    }

    /* access modifiers changed from: private */
    public void y() {
        OnStartEnterTransitionListener onStartEnterTransitionListener;
        if (this.W == null) {
            onStartEnterTransitionListener = null;
        } else {
            this.W.i = false;
            onStartEnterTransitionListener = this.W.j;
            this.W.j = null;
        }
        if (onStartEnterTransitionListener != null) {
            onStartEnterTransitionListener.a();
        }
    }

    public void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        printWriter.print(str);
        printWriter.print("mFragmentId=#");
        printWriter.print(Integer.toHexString(this.G));
        printWriter.print(" mContainerId=#");
        printWriter.print(Integer.toHexString(this.H));
        printWriter.print(" mTag=");
        printWriter.println(this.I);
        printWriter.print(str);
        printWriter.print("mState=");
        printWriter.print(this.k);
        printWriter.print(" mIndex=");
        printWriter.print(this.n);
        printWriter.print(" mWho=");
        printWriter.print(this.o);
        printWriter.print(" mBackStackNesting=");
        printWriter.println(this.z);
        printWriter.print(str);
        printWriter.print("mAdded=");
        printWriter.print(this.t);
        printWriter.print(" mRemoving=");
        printWriter.print(this.u);
        printWriter.print(" mFromLayout=");
        printWriter.print(this.v);
        printWriter.print(" mInLayout=");
        printWriter.println(this.w);
        printWriter.print(str);
        printWriter.print("mHidden=");
        printWriter.print(this.J);
        printWriter.print(" mDetached=");
        printWriter.print(this.K);
        printWriter.print(" mMenuVisible=");
        printWriter.print(this.O);
        printWriter.print(" mHasMenu=");
        printWriter.println(this.N);
        printWriter.print(str);
        printWriter.print("mRetainInstance=");
        printWriter.print(this.L);
        printWriter.print(" mRetaining=");
        printWriter.print(this.M);
        printWriter.print(" mUserVisibleHint=");
        printWriter.println(this.U);
        if (this.A != null) {
            printWriter.print(str);
            printWriter.print("mFragmentManager=");
            printWriter.println(this.A);
        }
        if (this.B != null) {
            printWriter.print(str);
            printWriter.print("mHost=");
            printWriter.println(this.B);
        }
        if (this.F != null) {
            printWriter.print(str);
            printWriter.print("mParentFragment=");
            printWriter.println(this.F);
        }
        if (this.p != null) {
            printWriter.print(str);
            printWriter.print("mArguments=");
            printWriter.println(this.p);
        }
        if (this.l != null) {
            printWriter.print(str);
            printWriter.print("mSavedFragmentState=");
            printWriter.println(this.l);
        }
        if (this.m != null) {
            printWriter.print(str);
            printWriter.print("mSavedViewState=");
            printWriter.println(this.m);
        }
        if (this.q != null) {
            printWriter.print(str);
            printWriter.print("mTarget=");
            printWriter.print(this.q);
            printWriter.print(" mTargetRequestCode=");
            printWriter.println(this.s);
        }
        if (o() != 0) {
            printWriter.print(str);
            printWriter.print("mNextAnim=");
            printWriter.println(o());
        }
        if (this.Q != null) {
            printWriter.print(str);
            printWriter.print("mContainer=");
            printWriter.println(this.Q);
        }
        if (this.R != null) {
            printWriter.print(str);
            printWriter.print("mView=");
            printWriter.println(this.R);
        }
        if (this.S != null) {
            printWriter.print(str);
            printWriter.print("mInnerView=");
            printWriter.println(this.R);
        }
        if (t() != null) {
            printWriter.print(str);
            printWriter.print("mAnimatingAway=");
            printWriter.println(t());
            printWriter.print(str);
            printWriter.print("mStateAfterAnimating=");
            printWriter.println(v());
        }
        if (this.V != null) {
            printWriter.print(str);
            printWriter.println("Loader Manager:");
            LoaderManagerImpl loaderManagerImpl = this.V;
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append("  ");
            loaderManagerImpl.dump(sb.toString(), fileDescriptor, printWriter, strArr);
        }
        if (this.C != null) {
            printWriter.print(str);
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Child ");
            sb2.append(this.C);
            sb2.append(":");
            printWriter.println(sb2.toString());
            FragmentManagerImpl fragmentManagerImpl = this.C;
            StringBuilder sb3 = new StringBuilder();
            sb3.append(str);
            sb3.append("  ");
            fragmentManagerImpl.dump(sb3.toString(), fileDescriptor, printWriter, strArr);
        }
    }

    /* access modifiers changed from: 0000 */
    public Fragment a(String str) {
        if (str.equals(this.o)) {
            return this;
        }
        if (this.C != null) {
            return this.C.a(str);
        }
        return null;
    }

    /* access modifiers changed from: 0000 */
    public void d() {
        if (this.B == null) {
            throw new IllegalStateException("Fragment has not been attached yet.");
        }
        this.C = new FragmentManagerImpl();
        this.C.a(this.B, (FragmentContainer) new FragmentContainer() {
            @Nullable
            public View onFindViewById(int i) {
                if (Fragment.this.R != null) {
                    return Fragment.this.R.findViewById(i);
                }
                throw new IllegalStateException("Fragment does not have a view");
            }

            public boolean onHasView() {
                return Fragment.this.R != null;
            }

            public Fragment instantiate(Context context, String str, Bundle bundle) {
                return Fragment.this.B.instantiate(context, str, bundle);
            }
        }, this);
    }

    /* access modifiers changed from: 0000 */
    public void d(Bundle bundle) {
        if (this.C != null) {
            this.C.j();
        }
        this.k = 1;
        this.P = false;
        onCreate(bundle);
        this.ab = true;
        if (!this.P) {
            StringBuilder sb = new StringBuilder();
            sb.append("Fragment ");
            sb.append(this);
            sb.append(" did not call through to super.onCreate()");
            throw new SuperNotCalledException(sb.toString());
        }
        this.ac.handleLifecycleEvent(Event.ON_CREATE);
    }

    /* access modifiers changed from: 0000 */
    public View a(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        if (this.C != null) {
            this.C.j();
        }
        this.y = true;
        return onCreateView(layoutInflater, viewGroup, bundle);
    }

    /* access modifiers changed from: 0000 */
    public void e(Bundle bundle) {
        if (this.C != null) {
            this.C.j();
        }
        this.k = 2;
        this.P = false;
        onActivityCreated(bundle);
        if (!this.P) {
            StringBuilder sb = new StringBuilder();
            sb.append("Fragment ");
            sb.append(this);
            sb.append(" did not call through to super.onActivityCreated()");
            throw new SuperNotCalledException(sb.toString());
        } else if (this.C != null) {
            this.C.l();
        }
    }

    /* access modifiers changed from: 0000 */
    public void e() {
        if (this.C != null) {
            this.C.j();
            this.C.d();
        }
        this.k = 4;
        this.P = false;
        onStart();
        if (!this.P) {
            StringBuilder sb = new StringBuilder();
            sb.append("Fragment ");
            sb.append(this);
            sb.append(" did not call through to super.onStart()");
            throw new SuperNotCalledException(sb.toString());
        }
        if (this.C != null) {
            this.C.m();
        }
        this.ac.handleLifecycleEvent(Event.ON_START);
    }

    /* access modifiers changed from: 0000 */
    public void f() {
        if (this.C != null) {
            this.C.j();
            this.C.d();
        }
        this.k = 5;
        this.P = false;
        onResume();
        if (!this.P) {
            StringBuilder sb = new StringBuilder();
            sb.append("Fragment ");
            sb.append(this);
            sb.append(" did not call through to super.onResume()");
            throw new SuperNotCalledException(sb.toString());
        }
        if (this.C != null) {
            this.C.n();
            this.C.d();
        }
        this.ac.handleLifecycleEvent(Event.ON_RESUME);
    }

    /* access modifiers changed from: 0000 */
    public void g() {
        if (this.C != null) {
            this.C.j();
        }
    }

    /* access modifiers changed from: 0000 */
    public void b(boolean z2) {
        onMultiWindowModeChanged(z2);
        if (this.C != null) {
            this.C.a(z2);
        }
    }

    /* access modifiers changed from: 0000 */
    public void c(boolean z2) {
        onPictureInPictureModeChanged(z2);
        if (this.C != null) {
            this.C.b(z2);
        }
    }

    /* access modifiers changed from: 0000 */
    public void a(Configuration configuration) {
        onConfigurationChanged(configuration);
        if (this.C != null) {
            this.C.a(configuration);
        }
    }

    /* access modifiers changed from: 0000 */
    public void h() {
        onLowMemory();
        if (this.C != null) {
            this.C.t();
        }
    }

    /* access modifiers changed from: 0000 */
    public boolean a(Menu menu, MenuInflater menuInflater) {
        boolean z2 = false;
        if (this.J) {
            return false;
        }
        if (this.N && this.O) {
            z2 = true;
            onCreateOptionsMenu(menu, menuInflater);
        }
        return this.C != null ? z2 | this.C.a(menu, menuInflater) : z2;
    }

    /* access modifiers changed from: 0000 */
    public boolean a(Menu menu) {
        boolean z2 = false;
        if (this.J) {
            return false;
        }
        if (this.N && this.O) {
            z2 = true;
            onPrepareOptionsMenu(menu);
        }
        return this.C != null ? z2 | this.C.a(menu) : z2;
    }

    /* access modifiers changed from: 0000 */
    public boolean a(MenuItem menuItem) {
        if (!this.J) {
            if (this.N && this.O && onOptionsItemSelected(menuItem)) {
                return true;
            }
            if (this.C != null && this.C.a(menuItem)) {
                return true;
            }
        }
        return false;
    }

    /* access modifiers changed from: 0000 */
    public boolean b(MenuItem menuItem) {
        if (!this.J) {
            if (onContextItemSelected(menuItem)) {
                return true;
            }
            if (this.C != null && this.C.b(menuItem)) {
                return true;
            }
        }
        return false;
    }

    /* access modifiers changed from: 0000 */
    public void b(Menu menu) {
        if (!this.J) {
            if (this.N && this.O) {
                onOptionsMenuClosed(menu);
            }
            if (this.C != null) {
                this.C.b(menu);
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void f(Bundle bundle) {
        onSaveInstanceState(bundle);
        if (this.C != null) {
            Parcelable i = this.C.i();
            if (i != null) {
                bundle.putParcelable("android:support:fragments", i);
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void i() {
        this.ac.handleLifecycleEvent(Event.ON_PAUSE);
        if (this.C != null) {
            this.C.o();
        }
        this.k = 4;
        this.P = false;
        onPause();
        if (!this.P) {
            StringBuilder sb = new StringBuilder();
            sb.append("Fragment ");
            sb.append(this);
            sb.append(" did not call through to super.onPause()");
            throw new SuperNotCalledException(sb.toString());
        }
    }

    /* access modifiers changed from: 0000 */
    public void j() {
        this.ac.handleLifecycleEvent(Event.ON_STOP);
        if (this.C != null) {
            this.C.p();
        }
        this.k = 3;
        this.P = false;
        onStop();
        if (!this.P) {
            StringBuilder sb = new StringBuilder();
            sb.append("Fragment ");
            sb.append(this);
            sb.append(" did not call through to super.onStop()");
            throw new SuperNotCalledException(sb.toString());
        }
    }

    /* access modifiers changed from: 0000 */
    public void k() {
        if (this.C != null) {
            this.C.q();
        }
        this.k = 2;
    }

    /* access modifiers changed from: 0000 */
    public void l() {
        if (this.C != null) {
            this.C.r();
        }
        this.k = 1;
        this.P = false;
        onDestroyView();
        if (!this.P) {
            StringBuilder sb = new StringBuilder();
            sb.append("Fragment ");
            sb.append(this);
            sb.append(" did not call through to super.onDestroyView()");
            throw new SuperNotCalledException(sb.toString());
        }
        if (this.V != null) {
            this.V.a();
        }
        this.y = false;
    }

    /* access modifiers changed from: 0000 */
    public void m() {
        this.ac.handleLifecycleEvent(Event.ON_DESTROY);
        if (this.C != null) {
            this.C.s();
        }
        this.k = 0;
        this.P = false;
        this.ab = false;
        onDestroy();
        if (!this.P) {
            StringBuilder sb = new StringBuilder();
            sb.append("Fragment ");
            sb.append(this);
            sb.append(" did not call through to super.onDestroy()");
            throw new SuperNotCalledException(sb.toString());
        }
        this.C = null;
    }

    /* access modifiers changed from: 0000 */
    public void n() {
        this.P = false;
        onDetach();
        this.aa = null;
        if (!this.P) {
            StringBuilder sb = new StringBuilder();
            sb.append("Fragment ");
            sb.append(this);
            sb.append(" did not call through to super.onDetach()");
            throw new SuperNotCalledException(sb.toString());
        } else if (this.C == null) {
        } else {
            if (!this.M) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append("Child FragmentManager of ");
                sb2.append(this);
                sb2.append(" was not ");
                sb2.append(" destroyed and this fragment is not retaining instance");
                throw new IllegalStateException(sb2.toString());
            }
            this.C.s();
            this.C = null;
        }
    }

    /* access modifiers changed from: 0000 */
    public void a(OnStartEnterTransitionListener onStartEnterTransitionListener) {
        z();
        if (onStartEnterTransitionListener != this.W.j) {
            if (onStartEnterTransitionListener == null || this.W.j == null) {
                if (this.W.i) {
                    this.W.j = onStartEnterTransitionListener;
                }
                if (onStartEnterTransitionListener != null) {
                    onStartEnterTransitionListener.b();
                }
                return;
            }
            StringBuilder sb = new StringBuilder();
            sb.append("Trying to set a replacement startPostponedEnterTransition on ");
            sb.append(this);
            throw new IllegalStateException(sb.toString());
        }
    }

    private AnimationInfo z() {
        if (this.W == null) {
            this.W = new AnimationInfo();
        }
        return this.W;
    }

    /* access modifiers changed from: 0000 */
    public int o() {
        if (this.W == null) {
            return 0;
        }
        return this.W.d;
    }

    /* access modifiers changed from: 0000 */
    public void a(int i) {
        if (this.W != null || i != 0) {
            z().d = i;
        }
    }

    /* access modifiers changed from: 0000 */
    public int p() {
        if (this.W == null) {
            return 0;
        }
        return this.W.e;
    }

    /* access modifiers changed from: 0000 */
    public void a(int i, int i2) {
        if (this.W != null || i != 0 || i2 != 0) {
            z();
            this.W.e = i;
            this.W.f = i2;
        }
    }

    /* access modifiers changed from: 0000 */
    public int q() {
        if (this.W == null) {
            return 0;
        }
        return this.W.f;
    }

    /* access modifiers changed from: 0000 */
    public SharedElementCallback r() {
        if (this.W == null) {
            return null;
        }
        return this.W.g;
    }

    /* access modifiers changed from: 0000 */
    public SharedElementCallback s() {
        if (this.W == null) {
            return null;
        }
        return this.W.h;
    }

    /* access modifiers changed from: 0000 */
    public View t() {
        if (this.W == null) {
            return null;
        }
        return this.W.a;
    }

    /* access modifiers changed from: 0000 */
    public void a(View view) {
        z().a = view;
    }

    /* access modifiers changed from: 0000 */
    public void a(Animator animator) {
        z().b = animator;
    }

    /* access modifiers changed from: 0000 */
    public Animator u() {
        if (this.W == null) {
            return null;
        }
        return this.W.b;
    }

    /* access modifiers changed from: 0000 */
    public int v() {
        if (this.W == null) {
            return 0;
        }
        return this.W.c;
    }

    /* access modifiers changed from: 0000 */
    public void b(int i) {
        z().c = i;
    }

    /* access modifiers changed from: 0000 */
    public boolean w() {
        if (this.W == null) {
            return false;
        }
        return this.W.i;
    }

    /* access modifiers changed from: 0000 */
    public boolean x() {
        if (this.W == null) {
            return false;
        }
        return this.W.k;
    }

    /* access modifiers changed from: 0000 */
    public void d(boolean z2) {
        z().k = z2;
    }
}
