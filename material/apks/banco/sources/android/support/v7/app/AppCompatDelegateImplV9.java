package android.support.v7.app;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources.Theme;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.ClassLoaderCreator;
import android.os.Parcelable.Creator;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NavUtils;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v4.view.OnApplyWindowInsetsListener;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.support.v4.view.ViewPropertyAnimatorListenerAdapter;
import android.support.v4.view.WindowInsetsCompat;
import android.support.v7.appcompat.R;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.view.ActionMode;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.view.menu.ListMenuPresenter;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuBuilder.Callback;
import android.support.v7.view.menu.MenuPresenter;
import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.ActionBarContextView;
import android.support.v7.widget.AppCompatDrawableManager;
import android.support.v7.widget.ContentFrameLayout;
import android.support.v7.widget.ContentFrameLayout.OnAttachListener;
import android.support.v7.widget.DecorContentParent;
import android.support.v7.widget.FitWindowsViewGroup;
import android.support.v7.widget.FitWindowsViewGroup.OnFitSystemWindowsListener;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.VectorEnabledTintResources;
import android.support.v7.widget.ViewUtils;
import android.text.TextUtils;
import android.util.AndroidRuntimeException;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.LayoutInflater.Factory;
import android.view.LayoutInflater.Factory2;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.ViewParent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import org.xmlpull.v1.XmlPullParser;

@RequiresApi(14)
class AppCompatDelegateImplV9 extends AppCompatDelegateImplBase implements Callback, Factory2 {
    private static final boolean t = (VERSION.SDK_INT < 21);
    private View A;
    private boolean B;
    private boolean C;
    private boolean D;
    private PanelFeatureState[] E;
    private PanelFeatureState F;
    private boolean G;
    private final Runnable H = new Runnable() {
        public void run() {
            if ((AppCompatDelegateImplV9.this.s & 1) != 0) {
                AppCompatDelegateImplV9.this.c(0);
            }
            if ((AppCompatDelegateImplV9.this.s & 4096) != 0) {
                AppCompatDelegateImplV9.this.c(108);
            }
            AppCompatDelegateImplV9.this.r = false;
            AppCompatDelegateImplV9.this.s = 0;
        }
    };
    private boolean I;
    private Rect J;
    private Rect K;
    private AppCompatViewInflater L;
    ActionMode m;
    ActionBarContextView n;
    PopupWindow o;
    Runnable p;
    ViewPropertyAnimatorCompat q = null;
    boolean r;
    int s;
    private DecorContentParent u;
    private ActionMenuPresenterCallback v;
    private PanelMenuPresenterCallback w;
    private boolean x;
    private ViewGroup y;
    private TextView z;

    final class ActionMenuPresenterCallback implements MenuPresenter.Callback {
        ActionMenuPresenterCallback() {
        }

        public boolean onOpenSubMenu(MenuBuilder menuBuilder) {
            Window.Callback e = AppCompatDelegateImplV9.this.e();
            if (e != null) {
                e.onMenuOpened(108, menuBuilder);
            }
            return true;
        }

        public void onCloseMenu(MenuBuilder menuBuilder, boolean z) {
            AppCompatDelegateImplV9.this.a(menuBuilder);
        }
    }

    class ActionModeCallbackWrapperV9 implements ActionMode.Callback {
        private ActionMode.Callback b;

        public ActionModeCallbackWrapperV9(ActionMode.Callback callback) {
            this.b = callback;
        }

        public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
            return this.b.onCreateActionMode(actionMode, menu);
        }

        public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
            return this.b.onPrepareActionMode(actionMode, menu);
        }

        public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
            return this.b.onActionItemClicked(actionMode, menuItem);
        }

        public void onDestroyActionMode(ActionMode actionMode) {
            this.b.onDestroyActionMode(actionMode);
            if (AppCompatDelegateImplV9.this.o != null) {
                AppCompatDelegateImplV9.this.b.getDecorView().removeCallbacks(AppCompatDelegateImplV9.this.p);
            }
            if (AppCompatDelegateImplV9.this.n != null) {
                AppCompatDelegateImplV9.this.h();
                AppCompatDelegateImplV9.this.q = ViewCompat.animate(AppCompatDelegateImplV9.this.n).alpha(BitmapDescriptorFactory.HUE_RED);
                AppCompatDelegateImplV9.this.q.setListener(new ViewPropertyAnimatorListenerAdapter() {
                    public void onAnimationEnd(View view) {
                        AppCompatDelegateImplV9.this.n.setVisibility(8);
                        if (AppCompatDelegateImplV9.this.o != null) {
                            AppCompatDelegateImplV9.this.o.dismiss();
                        } else if (AppCompatDelegateImplV9.this.n.getParent() instanceof View) {
                            ViewCompat.requestApplyInsets((View) AppCompatDelegateImplV9.this.n.getParent());
                        }
                        AppCompatDelegateImplV9.this.n.removeAllViews();
                        AppCompatDelegateImplV9.this.q.setListener(null);
                        AppCompatDelegateImplV9.this.q = null;
                    }
                });
            }
            if (AppCompatDelegateImplV9.this.e != null) {
                AppCompatDelegateImplV9.this.e.onSupportActionModeFinished(AppCompatDelegateImplV9.this.m);
            }
            AppCompatDelegateImplV9.this.m = null;
        }
    }

    class ListMenuDecorView extends ContentFrameLayout {
        public ListMenuDecorView(Context context) {
            super(context);
        }

        public boolean dispatchKeyEvent(KeyEvent keyEvent) {
            return AppCompatDelegateImplV9.this.a(keyEvent) || super.dispatchKeyEvent(keyEvent);
        }

        public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
            if (motionEvent.getAction() != 0 || !a((int) motionEvent.getX(), (int) motionEvent.getY())) {
                return super.onInterceptTouchEvent(motionEvent);
            }
            AppCompatDelegateImplV9.this.b(0);
            return true;
        }

        public void setBackgroundResource(int i) {
            setBackgroundDrawable(AppCompatResources.getDrawable(getContext(), i));
        }

        private boolean a(int i, int i2) {
            return i < -5 || i2 < -5 || i > getWidth() + 5 || i2 > getHeight() + 5;
        }
    }

    public static final class PanelFeatureState {
        int a;
        int b;
        int c;
        int d;
        int e;
        int f;
        ViewGroup g;
        View h;
        View i;
        MenuBuilder j;
        ListMenuPresenter k;
        Context l;
        boolean m;
        boolean n;
        boolean o;
        boolean p = false;
        boolean q;
        public boolean qwertyMode;
        Bundle r;

        static class SavedState implements Parcelable {
            public static final Creator<SavedState> CREATOR = new ClassLoaderCreator<SavedState>() {
                /* renamed from: a */
                public SavedState createFromParcel(Parcel parcel, ClassLoader classLoader) {
                    return SavedState.a(parcel, classLoader);
                }

                /* renamed from: a */
                public SavedState createFromParcel(Parcel parcel) {
                    return SavedState.a(parcel, null);
                }

                /* renamed from: a */
                public SavedState[] newArray(int i) {
                    return new SavedState[i];
                }
            };
            int a;
            boolean b;
            Bundle c;

            public int describeContents() {
                return 0;
            }

            SavedState() {
            }

            public void writeToParcel(Parcel parcel, int i) {
                parcel.writeInt(this.a);
                parcel.writeInt(this.b ? 1 : 0);
                if (this.b) {
                    parcel.writeBundle(this.c);
                }
            }

            static SavedState a(Parcel parcel, ClassLoader classLoader) {
                SavedState savedState = new SavedState();
                savedState.a = parcel.readInt();
                boolean z = true;
                if (parcel.readInt() != 1) {
                    z = false;
                }
                savedState.b = z;
                if (savedState.b) {
                    savedState.c = parcel.readBundle(classLoader);
                }
                return savedState;
            }
        }

        PanelFeatureState(int i2) {
            this.a = i2;
        }

        public boolean hasPanelItems() {
            boolean z = false;
            if (this.h == null) {
                return false;
            }
            if (this.i != null) {
                return true;
            }
            if (this.k.getAdapter().getCount() > 0) {
                z = true;
            }
            return z;
        }

        public void clearMenuPresenters() {
            if (this.j != null) {
                this.j.removeMenuPresenter(this.k);
            }
            this.k = null;
        }

        /* access modifiers changed from: 0000 */
        public void a(Context context) {
            TypedValue typedValue = new TypedValue();
            Theme newTheme = context.getResources().newTheme();
            newTheme.setTo(context.getTheme());
            newTheme.resolveAttribute(R.attr.actionBarPopupTheme, typedValue, true);
            if (typedValue.resourceId != 0) {
                newTheme.applyStyle(typedValue.resourceId, true);
            }
            newTheme.resolveAttribute(R.attr.panelMenuListTheme, typedValue, true);
            if (typedValue.resourceId != 0) {
                newTheme.applyStyle(typedValue.resourceId, true);
            } else {
                newTheme.applyStyle(R.style.Theme_AppCompat_CompactMenu, true);
            }
            ContextThemeWrapper contextThemeWrapper = new ContextThemeWrapper(context, 0);
            contextThemeWrapper.getTheme().setTo(newTheme);
            this.l = contextThemeWrapper;
            TypedArray obtainStyledAttributes = contextThemeWrapper.obtainStyledAttributes(R.styleable.AppCompatTheme);
            this.b = obtainStyledAttributes.getResourceId(R.styleable.AppCompatTheme_panelBackground, 0);
            this.f = obtainStyledAttributes.getResourceId(R.styleable.AppCompatTheme_android_windowAnimationStyle, 0);
            obtainStyledAttributes.recycle();
        }

        /* access modifiers changed from: 0000 */
        public void a(MenuBuilder menuBuilder) {
            if (menuBuilder != this.j) {
                if (this.j != null) {
                    this.j.removeMenuPresenter(this.k);
                }
                this.j = menuBuilder;
                if (!(menuBuilder == null || this.k == null)) {
                    menuBuilder.addMenuPresenter(this.k);
                }
            }
        }

        /* access modifiers changed from: 0000 */
        public MenuView a(MenuPresenter.Callback callback) {
            if (this.j == null) {
                return null;
            }
            if (this.k == null) {
                this.k = new ListMenuPresenter(this.l, R.layout.abc_list_menu_item_layout);
                this.k.setCallback(callback);
                this.j.addMenuPresenter(this.k);
            }
            return this.k.getMenuView(this.g);
        }
    }

    final class PanelMenuPresenterCallback implements MenuPresenter.Callback {
        PanelMenuPresenterCallback() {
        }

        public void onCloseMenu(MenuBuilder menuBuilder, boolean z) {
            MenuBuilder rootMenu = menuBuilder.getRootMenu();
            boolean z2 = rootMenu != menuBuilder;
            AppCompatDelegateImplV9 appCompatDelegateImplV9 = AppCompatDelegateImplV9.this;
            if (z2) {
                menuBuilder = rootMenu;
            }
            PanelFeatureState a2 = appCompatDelegateImplV9.a((Menu) menuBuilder);
            if (a2 == null) {
                return;
            }
            if (z2) {
                AppCompatDelegateImplV9.this.a(a2.a, a2, rootMenu);
                AppCompatDelegateImplV9.this.a(a2, true);
                return;
            }
            AppCompatDelegateImplV9.this.a(a2, z);
        }

        public boolean onOpenSubMenu(MenuBuilder menuBuilder) {
            if (menuBuilder == null && AppCompatDelegateImplV9.this.h) {
                Window.Callback e = AppCompatDelegateImplV9.this.e();
                if (e != null && !AppCompatDelegateImplV9.this.d()) {
                    e.onMenuOpened(108, menuBuilder);
                }
            }
            return true;
        }
    }

    /* access modifiers changed from: 0000 */
    public void a(ViewGroup viewGroup) {
    }

    AppCompatDelegateImplV9(Context context, Window window, AppCompatCallback appCompatCallback) {
        super(context, window, appCompatCallback);
    }

    public void onCreate(Bundle bundle) {
        if ((this.c instanceof Activity) && NavUtils.getParentActivityName((Activity) this.c) != null) {
            ActionBar b = b();
            if (b == null) {
                this.I = true;
            } else {
                b.setDefaultDisplayHomeAsUpEnabled(true);
            }
        }
    }

    public void onPostCreate(Bundle bundle) {
        k();
    }

    public void a() {
        k();
        if (this.h && this.f == null) {
            if (this.c instanceof Activity) {
                this.f = new WindowDecorActionBar((Activity) this.c, this.i);
            } else if (this.c instanceof Dialog) {
                this.f = new WindowDecorActionBar((Dialog) this.c);
            }
            if (this.f != null) {
                this.f.setDefaultDisplayHomeAsUpEnabled(this.I);
            }
        }
    }

    public void setSupportActionBar(Toolbar toolbar) {
        if (this.c instanceof Activity) {
            ActionBar supportActionBar = getSupportActionBar();
            if (supportActionBar instanceof WindowDecorActionBar) {
                throw new IllegalStateException("This Activity already has an action bar supplied by the window decor. Do not request Window.FEATURE_SUPPORT_ACTION_BAR and set windowActionBar to false in your theme to use a Toolbar instead.");
            }
            this.g = null;
            if (supportActionBar != null) {
                supportActionBar.a();
            }
            if (toolbar != null) {
                ToolbarActionBar toolbarActionBar = new ToolbarActionBar(toolbar, ((Activity) this.c).getTitle(), this.d);
                this.f = toolbarActionBar;
                this.b.setCallback(toolbarActionBar.b());
            } else {
                this.f = null;
                this.b.setCallback(this.d);
            }
            invalidateOptionsMenu();
        }
    }

    @Nullable
    public <T extends View> T findViewById(@IdRes int i) {
        k();
        return this.b.findViewById(i);
    }

    public void onConfigurationChanged(Configuration configuration) {
        if (this.h && this.x) {
            ActionBar supportActionBar = getSupportActionBar();
            if (supportActionBar != null) {
                supportActionBar.onConfigurationChanged(configuration);
            }
        }
        AppCompatDrawableManager.get().onConfigurationChanged(this.a);
        applyDayNight();
    }

    public void onStop() {
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setShowHideAnimationEnabled(false);
        }
    }

    public void onPostResume() {
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setShowHideAnimationEnabled(true);
        }
    }

    public void setContentView(View view) {
        k();
        ViewGroup viewGroup = (ViewGroup) this.y.findViewById(16908290);
        viewGroup.removeAllViews();
        viewGroup.addView(view);
        this.c.onContentChanged();
    }

    public void setContentView(int i) {
        k();
        ViewGroup viewGroup = (ViewGroup) this.y.findViewById(16908290);
        viewGroup.removeAllViews();
        LayoutInflater.from(this.a).inflate(i, viewGroup);
        this.c.onContentChanged();
    }

    public void setContentView(View view, LayoutParams layoutParams) {
        k();
        ViewGroup viewGroup = (ViewGroup) this.y.findViewById(16908290);
        viewGroup.removeAllViews();
        viewGroup.addView(view, layoutParams);
        this.c.onContentChanged();
    }

    public void addContentView(View view, LayoutParams layoutParams) {
        k();
        ((ViewGroup) this.y.findViewById(16908290)).addView(view, layoutParams);
        this.c.onContentChanged();
    }

    public void onDestroy() {
        if (this.r) {
            this.b.getDecorView().removeCallbacks(this.H);
        }
        super.onDestroy();
        if (this.f != null) {
            this.f.a();
        }
    }

    private void k() {
        if (!this.x) {
            this.y = l();
            CharSequence f = f();
            if (!TextUtils.isEmpty(f)) {
                a(f);
            }
            m();
            a(this.y);
            this.x = true;
            PanelFeatureState a = a(0, false);
            if (d()) {
                return;
            }
            if (a == null || a.j == null) {
                a(108);
            }
        }
    }

    private ViewGroup l() {
        ViewGroup viewGroup;
        Context context;
        TypedArray obtainStyledAttributes = this.a.obtainStyledAttributes(R.styleable.AppCompatTheme);
        if (!obtainStyledAttributes.hasValue(R.styleable.AppCompatTheme_windowActionBar)) {
            obtainStyledAttributes.recycle();
            throw new IllegalStateException("You need to use a Theme.AppCompat theme (or descendant) with this activity.");
        }
        if (obtainStyledAttributes.getBoolean(R.styleable.AppCompatTheme_windowNoTitle, false)) {
            requestWindowFeature(1);
        } else if (obtainStyledAttributes.getBoolean(R.styleable.AppCompatTheme_windowActionBar, false)) {
            requestWindowFeature(108);
        }
        if (obtainStyledAttributes.getBoolean(R.styleable.AppCompatTheme_windowActionBarOverlay, false)) {
            requestWindowFeature(109);
        }
        if (obtainStyledAttributes.getBoolean(R.styleable.AppCompatTheme_windowActionModeOverlay, false)) {
            requestWindowFeature(10);
        }
        this.k = obtainStyledAttributes.getBoolean(R.styleable.AppCompatTheme_android_windowIsFloating, false);
        obtainStyledAttributes.recycle();
        this.b.getDecorView();
        LayoutInflater from = LayoutInflater.from(this.a);
        if (this.l) {
            if (this.j) {
                viewGroup = (ViewGroup) from.inflate(R.layout.abc_screen_simple_overlay_action_mode, null);
            } else {
                viewGroup = (ViewGroup) from.inflate(R.layout.abc_screen_simple, null);
            }
            if (VERSION.SDK_INT >= 21) {
                ViewCompat.setOnApplyWindowInsetsListener(viewGroup, new OnApplyWindowInsetsListener() {
                    public WindowInsetsCompat onApplyWindowInsets(View view, WindowInsetsCompat windowInsetsCompat) {
                        int systemWindowInsetTop = windowInsetsCompat.getSystemWindowInsetTop();
                        int d = AppCompatDelegateImplV9.this.d(systemWindowInsetTop);
                        if (systemWindowInsetTop != d) {
                            windowInsetsCompat = windowInsetsCompat.replaceSystemWindowInsets(windowInsetsCompat.getSystemWindowInsetLeft(), d, windowInsetsCompat.getSystemWindowInsetRight(), windowInsetsCompat.getSystemWindowInsetBottom());
                        }
                        return ViewCompat.onApplyWindowInsets(view, windowInsetsCompat);
                    }
                });
            } else {
                ((FitWindowsViewGroup) viewGroup).setOnFitSystemWindowsListener(new OnFitSystemWindowsListener() {
                    public void onFitSystemWindows(Rect rect) {
                        rect.top = AppCompatDelegateImplV9.this.d(rect.top);
                    }
                });
            }
        } else if (this.k) {
            viewGroup = (ViewGroup) from.inflate(R.layout.abc_dialog_title_material, null);
            this.i = false;
            this.h = false;
        } else if (this.h) {
            TypedValue typedValue = new TypedValue();
            this.a.getTheme().resolveAttribute(R.attr.actionBarTheme, typedValue, true);
            if (typedValue.resourceId != 0) {
                context = new ContextThemeWrapper(this.a, typedValue.resourceId);
            } else {
                context = this.a;
            }
            viewGroup = (ViewGroup) LayoutInflater.from(context).inflate(R.layout.abc_screen_toolbar, null);
            this.u = (DecorContentParent) viewGroup.findViewById(R.id.decor_content_parent);
            this.u.setWindowCallback(e());
            if (this.i) {
                this.u.initFeature(109);
            }
            if (this.B) {
                this.u.initFeature(2);
            }
            if (this.C) {
                this.u.initFeature(5);
            }
        } else {
            viewGroup = null;
        }
        if (viewGroup == null) {
            StringBuilder sb = new StringBuilder();
            sb.append("AppCompat does not support the current theme features: { windowActionBar: ");
            sb.append(this.h);
            sb.append(", windowActionBarOverlay: ");
            sb.append(this.i);
            sb.append(", android:windowIsFloating: ");
            sb.append(this.k);
            sb.append(", windowActionModeOverlay: ");
            sb.append(this.j);
            sb.append(", windowNoTitle: ");
            sb.append(this.l);
            sb.append(" }");
            throw new IllegalArgumentException(sb.toString());
        }
        if (this.u == null) {
            this.z = (TextView) viewGroup.findViewById(R.id.title);
        }
        ViewUtils.makeOptionalFitsSystemWindows(viewGroup);
        ContentFrameLayout contentFrameLayout = (ContentFrameLayout) viewGroup.findViewById(R.id.action_bar_activity_content);
        ViewGroup viewGroup2 = (ViewGroup) this.b.findViewById(16908290);
        if (viewGroup2 != null) {
            while (viewGroup2.getChildCount() > 0) {
                View childAt = viewGroup2.getChildAt(0);
                viewGroup2.removeViewAt(0);
                contentFrameLayout.addView(childAt);
            }
            viewGroup2.setId(-1);
            contentFrameLayout.setId(16908290);
            if (viewGroup2 instanceof FrameLayout) {
                ((FrameLayout) viewGroup2).setForeground(null);
            }
        }
        this.b.setContentView(viewGroup);
        contentFrameLayout.setAttachListener(new OnAttachListener() {
            public void onAttachedFromWindow() {
            }

            public void onDetachedFromWindow() {
                AppCompatDelegateImplV9.this.j();
            }
        });
        return viewGroup;
    }

    private void m() {
        ContentFrameLayout contentFrameLayout = (ContentFrameLayout) this.y.findViewById(16908290);
        View decorView = this.b.getDecorView();
        contentFrameLayout.setDecorPadding(decorView.getPaddingLeft(), decorView.getPaddingTop(), decorView.getPaddingRight(), decorView.getPaddingBottom());
        TypedArray obtainStyledAttributes = this.a.obtainStyledAttributes(R.styleable.AppCompatTheme);
        obtainStyledAttributes.getValue(R.styleable.AppCompatTheme_windowMinWidthMajor, contentFrameLayout.getMinWidthMajor());
        obtainStyledAttributes.getValue(R.styleable.AppCompatTheme_windowMinWidthMinor, contentFrameLayout.getMinWidthMinor());
        if (obtainStyledAttributes.hasValue(R.styleable.AppCompatTheme_windowFixedWidthMajor)) {
            obtainStyledAttributes.getValue(R.styleable.AppCompatTheme_windowFixedWidthMajor, contentFrameLayout.getFixedWidthMajor());
        }
        if (obtainStyledAttributes.hasValue(R.styleable.AppCompatTheme_windowFixedWidthMinor)) {
            obtainStyledAttributes.getValue(R.styleable.AppCompatTheme_windowFixedWidthMinor, contentFrameLayout.getFixedWidthMinor());
        }
        if (obtainStyledAttributes.hasValue(R.styleable.AppCompatTheme_windowFixedHeightMajor)) {
            obtainStyledAttributes.getValue(R.styleable.AppCompatTheme_windowFixedHeightMajor, contentFrameLayout.getFixedHeightMajor());
        }
        if (obtainStyledAttributes.hasValue(R.styleable.AppCompatTheme_windowFixedHeightMinor)) {
            obtainStyledAttributes.getValue(R.styleable.AppCompatTheme_windowFixedHeightMinor, contentFrameLayout.getFixedHeightMinor());
        }
        obtainStyledAttributes.recycle();
        contentFrameLayout.requestLayout();
    }

    public boolean requestWindowFeature(int i) {
        int e = e(i);
        if (this.l && e == 108) {
            return false;
        }
        if (this.h && e == 1) {
            this.h = false;
        }
        switch (e) {
            case 1:
                n();
                this.l = true;
                return true;
            case 2:
                n();
                this.B = true;
                return true;
            case 5:
                n();
                this.C = true;
                return true;
            case 10:
                n();
                this.j = true;
                return true;
            case 108:
                n();
                this.h = true;
                return true;
            case 109:
                n();
                this.i = true;
                return true;
            default:
                return this.b.requestFeature(e);
        }
    }

    public boolean hasWindowFeature(int i) {
        switch (e(i)) {
            case 1:
                return this.l;
            case 2:
                return this.B;
            case 5:
                return this.C;
            case 10:
                return this.j;
            case 108:
                return this.h;
            case 109:
                return this.i;
            default:
                return false;
        }
    }

    /* access modifiers changed from: 0000 */
    public void a(CharSequence charSequence) {
        if (this.u != null) {
            this.u.setWindowTitle(charSequence);
        } else if (b() != null) {
            b().setWindowTitle(charSequence);
        } else if (this.z != null) {
            this.z.setText(charSequence);
        }
    }

    /* access modifiers changed from: 0000 */
    public void a(int i, Menu menu) {
        if (i == 108) {
            ActionBar supportActionBar = getSupportActionBar();
            if (supportActionBar != null) {
                supportActionBar.dispatchMenuVisibilityChanged(false);
            }
        } else if (i == 0) {
            PanelFeatureState a = a(i, true);
            if (a.o) {
                a(a, false);
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public boolean b(int i, Menu menu) {
        if (i != 108) {
            return false;
        }
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.dispatchMenuVisibilityChanged(true);
        }
        return true;
    }

    public boolean onMenuItemSelected(MenuBuilder menuBuilder, MenuItem menuItem) {
        Window.Callback e = e();
        if (e != null && !d()) {
            PanelFeatureState a = a((Menu) menuBuilder.getRootMenu());
            if (a != null) {
                return e.onMenuItemSelected(a.a, menuItem);
            }
        }
        return false;
    }

    public void onMenuModeChange(MenuBuilder menuBuilder) {
        a(menuBuilder, true);
    }

    public ActionMode startSupportActionMode(@NonNull ActionMode.Callback callback) {
        if (callback == null) {
            throw new IllegalArgumentException("ActionMode callback can not be null.");
        }
        if (this.m != null) {
            this.m.finish();
        }
        ActionModeCallbackWrapperV9 actionModeCallbackWrapperV9 = new ActionModeCallbackWrapperV9(callback);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            this.m = supportActionBar.startActionMode(actionModeCallbackWrapperV9);
            if (!(this.m == null || this.e == null)) {
                this.e.onSupportActionModeStarted(this.m);
            }
        }
        if (this.m == null) {
            this.m = a((ActionMode.Callback) actionModeCallbackWrapperV9);
        }
        return this.m;
    }

    public void invalidateOptionsMenu() {
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar == null || !supportActionBar.invalidateOptionsMenu()) {
            a(0);
        }
    }

    /* access modifiers changed from: 0000 */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x002b  */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x002f  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public android.support.v7.view.ActionMode a(@android.support.annotation.NonNull android.support.v7.view.ActionMode.Callback r8) {
        /*
            r7 = this;
            r7.h()
            android.support.v7.view.ActionMode r0 = r7.m
            if (r0 == 0) goto L_0x000c
            android.support.v7.view.ActionMode r0 = r7.m
            r0.finish()
        L_0x000c:
            boolean r0 = r8 instanceof android.support.v7.app.AppCompatDelegateImplV9.ActionModeCallbackWrapperV9
            if (r0 != 0) goto L_0x0016
            android.support.v7.app.AppCompatDelegateImplV9$ActionModeCallbackWrapperV9 r0 = new android.support.v7.app.AppCompatDelegateImplV9$ActionModeCallbackWrapperV9
            r0.<init>(r8)
            r8 = r0
        L_0x0016:
            android.support.v7.app.AppCompatCallback r0 = r7.e
            r1 = 0
            if (r0 == 0) goto L_0x0028
            boolean r0 = r7.d()
            if (r0 != 0) goto L_0x0028
            android.support.v7.app.AppCompatCallback r0 = r7.e     // Catch:{ AbstractMethodError -> 0x0028 }
            android.support.v7.view.ActionMode r0 = r0.onWindowStartingSupportActionMode(r8)     // Catch:{ AbstractMethodError -> 0x0028 }
            goto L_0x0029
        L_0x0028:
            r0 = r1
        L_0x0029:
            if (r0 == 0) goto L_0x002f
            r7.m = r0
            goto L_0x016c
        L_0x002f:
            android.support.v7.widget.ActionBarContextView r0 = r7.n
            r2 = 0
            r3 = 1
            if (r0 != 0) goto L_0x00dc
            boolean r0 = r7.k
            if (r0 == 0) goto L_0x00bd
            android.util.TypedValue r0 = new android.util.TypedValue
            r0.<init>()
            android.content.Context r4 = r7.a
            android.content.res.Resources$Theme r4 = r4.getTheme()
            int r5 = android.support.v7.appcompat.R.attr.actionBarTheme
            r4.resolveAttribute(r5, r0, r3)
            int r5 = r0.resourceId
            if (r5 == 0) goto L_0x006e
            android.content.Context r5 = r7.a
            android.content.res.Resources r5 = r5.getResources()
            android.content.res.Resources$Theme r5 = r5.newTheme()
            r5.setTo(r4)
            int r4 = r0.resourceId
            r5.applyStyle(r4, r3)
            android.support.v7.view.ContextThemeWrapper r4 = new android.support.v7.view.ContextThemeWrapper
            android.content.Context r6 = r7.a
            r4.<init>(r6, r2)
            android.content.res.Resources$Theme r6 = r4.getTheme()
            r6.setTo(r5)
            goto L_0x0070
        L_0x006e:
            android.content.Context r4 = r7.a
        L_0x0070:
            android.support.v7.widget.ActionBarContextView r5 = new android.support.v7.widget.ActionBarContextView
            r5.<init>(r4)
            r7.n = r5
            android.widget.PopupWindow r5 = new android.widget.PopupWindow
            int r6 = android.support.v7.appcompat.R.attr.actionModePopupWindowStyle
            r5.<init>(r4, r1, r6)
            r7.o = r5
            android.widget.PopupWindow r5 = r7.o
            r6 = 2
            android.support.v4.widget.PopupWindowCompat.setWindowLayoutType(r5, r6)
            android.widget.PopupWindow r5 = r7.o
            android.support.v7.widget.ActionBarContextView r6 = r7.n
            r5.setContentView(r6)
            android.widget.PopupWindow r5 = r7.o
            r6 = -1
            r5.setWidth(r6)
            android.content.res.Resources$Theme r5 = r4.getTheme()
            int r6 = android.support.v7.appcompat.R.attr.actionBarSize
            r5.resolveAttribute(r6, r0, r3)
            int r0 = r0.data
            android.content.res.Resources r4 = r4.getResources()
            android.util.DisplayMetrics r4 = r4.getDisplayMetrics()
            int r0 = android.util.TypedValue.complexToDimensionPixelSize(r0, r4)
            android.support.v7.widget.ActionBarContextView r4 = r7.n
            r4.setContentHeight(r0)
            android.widget.PopupWindow r0 = r7.o
            r4 = -2
            r0.setHeight(r4)
            android.support.v7.app.AppCompatDelegateImplV9$5 r0 = new android.support.v7.app.AppCompatDelegateImplV9$5
            r0.<init>()
            r7.p = r0
            goto L_0x00dc
        L_0x00bd:
            android.view.ViewGroup r0 = r7.y
            int r4 = android.support.v7.appcompat.R.id.action_mode_bar_stub
            android.view.View r0 = r0.findViewById(r4)
            android.support.v7.widget.ViewStubCompat r0 = (android.support.v7.widget.ViewStubCompat) r0
            if (r0 == 0) goto L_0x00dc
            android.content.Context r4 = r7.c()
            android.view.LayoutInflater r4 = android.view.LayoutInflater.from(r4)
            r0.setLayoutInflater(r4)
            android.view.View r0 = r0.inflate()
            android.support.v7.widget.ActionBarContextView r0 = (android.support.v7.widget.ActionBarContextView) r0
            r7.n = r0
        L_0x00dc:
            android.support.v7.widget.ActionBarContextView r0 = r7.n
            if (r0 == 0) goto L_0x016c
            r7.h()
            android.support.v7.widget.ActionBarContextView r0 = r7.n
            r0.killMode()
            android.support.v7.view.StandaloneActionMode r0 = new android.support.v7.view.StandaloneActionMode
            android.support.v7.widget.ActionBarContextView r4 = r7.n
            android.content.Context r4 = r4.getContext()
            android.support.v7.widget.ActionBarContextView r5 = r7.n
            android.widget.PopupWindow r6 = r7.o
            if (r6 != 0) goto L_0x00f7
            goto L_0x00f8
        L_0x00f7:
            r3 = 0
        L_0x00f8:
            r0.<init>(r4, r5, r8, r3)
            android.view.Menu r3 = r0.getMenu()
            boolean r8 = r8.onCreateActionMode(r0, r3)
            if (r8 == 0) goto L_0x016a
            r0.invalidate()
            android.support.v7.widget.ActionBarContextView r8 = r7.n
            r8.initForMode(r0)
            r7.m = r0
            boolean r8 = r7.g()
            r0 = 1065353216(0x3f800000, float:1.0)
            if (r8 == 0) goto L_0x0134
            android.support.v7.widget.ActionBarContextView r8 = r7.n
            r1 = 0
            r8.setAlpha(r1)
            android.support.v7.widget.ActionBarContextView r8 = r7.n
            android.support.v4.view.ViewPropertyAnimatorCompat r8 = android.support.v4.view.ViewCompat.animate(r8)
            android.support.v4.view.ViewPropertyAnimatorCompat r8 = r8.alpha(r0)
            r7.q = r8
            android.support.v4.view.ViewPropertyAnimatorCompat r8 = r7.q
            android.support.v7.app.AppCompatDelegateImplV9$6 r0 = new android.support.v7.app.AppCompatDelegateImplV9$6
            r0.<init>()
            r8.setListener(r0)
            goto L_0x015a
        L_0x0134:
            android.support.v7.widget.ActionBarContextView r8 = r7.n
            r8.setAlpha(r0)
            android.support.v7.widget.ActionBarContextView r8 = r7.n
            r8.setVisibility(r2)
            android.support.v7.widget.ActionBarContextView r8 = r7.n
            r0 = 32
            r8.sendAccessibilityEvent(r0)
            android.support.v7.widget.ActionBarContextView r8 = r7.n
            android.view.ViewParent r8 = r8.getParent()
            boolean r8 = r8 instanceof android.view.View
            if (r8 == 0) goto L_0x015a
            android.support.v7.widget.ActionBarContextView r8 = r7.n
            android.view.ViewParent r8 = r8.getParent()
            android.view.View r8 = (android.view.View) r8
            android.support.v4.view.ViewCompat.requestApplyInsets(r8)
        L_0x015a:
            android.widget.PopupWindow r8 = r7.o
            if (r8 == 0) goto L_0x016c
            android.view.Window r8 = r7.b
            android.view.View r8 = r8.getDecorView()
            java.lang.Runnable r0 = r7.p
            r8.post(r0)
            goto L_0x016c
        L_0x016a:
            r7.m = r1
        L_0x016c:
            android.support.v7.view.ActionMode r8 = r7.m
            if (r8 == 0) goto L_0x017b
            android.support.v7.app.AppCompatCallback r8 = r7.e
            if (r8 == 0) goto L_0x017b
            android.support.v7.app.AppCompatCallback r8 = r7.e
            android.support.v7.view.ActionMode r0 = r7.m
            r8.onSupportActionModeStarted(r0)
        L_0x017b:
            android.support.v7.view.ActionMode r8 = r7.m
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v7.app.AppCompatDelegateImplV9.a(android.support.v7.view.ActionMode$Callback):android.support.v7.view.ActionMode");
    }

    /* access modifiers changed from: 0000 */
    public final boolean g() {
        return this.x && this.y != null && ViewCompat.isLaidOut(this.y);
    }

    /* access modifiers changed from: 0000 */
    public void h() {
        if (this.q != null) {
            this.q.cancel();
        }
    }

    /* access modifiers changed from: 0000 */
    public boolean i() {
        if (this.m != null) {
            this.m.finish();
            return true;
        }
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar == null || !supportActionBar.collapseActionView()) {
            return false;
        }
        return true;
    }

    /* access modifiers changed from: 0000 */
    public boolean a(int i, KeyEvent keyEvent) {
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null && supportActionBar.onKeyShortcut(i, keyEvent)) {
            return true;
        }
        if (this.F == null || !a(this.F, keyEvent.getKeyCode(), keyEvent, 1)) {
            if (this.F == null) {
                PanelFeatureState a = a(0, true);
                b(a, keyEvent);
                boolean a2 = a(a, keyEvent.getKeyCode(), keyEvent, 1);
                a.m = false;
                if (a2) {
                    return true;
                }
            }
            return false;
        }
        if (this.F != null) {
            this.F.n = true;
        }
        return true;
    }

    /* access modifiers changed from: 0000 */
    public boolean a(KeyEvent keyEvent) {
        boolean z2 = true;
        if (keyEvent.getKeyCode() == 82 && this.c.dispatchKeyEvent(keyEvent)) {
            return true;
        }
        int keyCode = keyEvent.getKeyCode();
        if (keyEvent.getAction() != 0) {
            z2 = false;
        }
        return z2 ? c(keyCode, keyEvent) : b(keyCode, keyEvent);
    }

    /* access modifiers changed from: 0000 */
    public boolean b(int i, KeyEvent keyEvent) {
        if (i == 4) {
            boolean z2 = this.G;
            this.G = false;
            PanelFeatureState a = a(0, false);
            if (a != null && a.o) {
                if (!z2) {
                    a(a, true);
                }
                return true;
            } else if (i()) {
                return true;
            }
        } else if (i == 82) {
            e(0, keyEvent);
            return true;
        }
        return false;
    }

    /* access modifiers changed from: 0000 */
    public boolean c(int i, KeyEvent keyEvent) {
        boolean z2 = true;
        if (i == 4) {
            if ((keyEvent.getFlags() & 128) == 0) {
                z2 = false;
            }
            this.G = z2;
        } else if (i == 82) {
            d(0, keyEvent);
            return true;
        }
        return false;
    }

    public View createView(View view, String str, @NonNull Context context, @NonNull AttributeSet attributeSet) {
        boolean z2;
        boolean z3 = false;
        if (this.L == null) {
            String string = this.a.obtainStyledAttributes(R.styleable.AppCompatTheme).getString(R.styleable.AppCompatTheme_viewInflaterClass);
            if (string == null || AppCompatViewInflater.class.getName().equals(string)) {
                this.L = new AppCompatViewInflater();
            } else {
                try {
                    this.L = (AppCompatViewInflater) Class.forName(string).getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
                } catch (Throwable th) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("Failed to instantiate custom view inflater ");
                    sb.append(string);
                    sb.append(". Falling back to default.");
                    Log.i("AppCompatDelegate", sb.toString(), th);
                    this.L = new AppCompatViewInflater();
                }
            }
        }
        if (t) {
            if (!(attributeSet instanceof XmlPullParser)) {
                z3 = a((ViewParent) view);
            } else if (((XmlPullParser) attributeSet).getDepth() > 1) {
                z3 = true;
            }
            z2 = z3;
        } else {
            z2 = false;
        }
        return this.L.a(view, str, context, attributeSet, z2, t, true, VectorEnabledTintResources.shouldBeUsed());
    }

    private boolean a(ViewParent viewParent) {
        if (viewParent == null) {
            return false;
        }
        View decorView = this.b.getDecorView();
        while (viewParent != null) {
            if (viewParent == decorView || !(viewParent instanceof View) || ViewCompat.isAttachedToWindow((View) viewParent)) {
                return false;
            }
            viewParent = viewParent.getParent();
        }
        return true;
    }

    public void installViewFactory() {
        LayoutInflater from = LayoutInflater.from(this.a);
        if (from.getFactory() == null) {
            LayoutInflaterCompat.setFactory2(from, this);
        } else if (!(from.getFactory2() instanceof AppCompatDelegateImplV9)) {
            Log.i("AppCompatDelegate", "The Activity's LayoutInflater already has a Factory installed so we can not install AppCompat's");
        }
    }

    public final View onCreateView(View view, String str, Context context, AttributeSet attributeSet) {
        View a = a(view, str, context, attributeSet);
        if (a != null) {
            return a;
        }
        return createView(view, str, context, attributeSet);
    }

    public View onCreateView(String str, Context context, AttributeSet attributeSet) {
        return onCreateView(null, str, context, attributeSet);
    }

    /* access modifiers changed from: 0000 */
    public View a(View view, String str, Context context, AttributeSet attributeSet) {
        if (this.c instanceof Factory) {
            View onCreateView = ((Factory) this.c).onCreateView(str, context, attributeSet);
            if (onCreateView != null) {
                return onCreateView;
            }
        }
        return null;
    }

    private void a(PanelFeatureState panelFeatureState, KeyEvent keyEvent) {
        int i;
        if (!panelFeatureState.o && !d()) {
            if (panelFeatureState.a == 0) {
                if ((this.a.getResources().getConfiguration().screenLayout & 15) == 4) {
                    return;
                }
            }
            Window.Callback e = e();
            if (e == null || e.onMenuOpened(panelFeatureState.a, panelFeatureState.j)) {
                WindowManager windowManager = (WindowManager) this.a.getSystemService("window");
                if (windowManager != null && b(panelFeatureState, keyEvent)) {
                    if (panelFeatureState.g == null || panelFeatureState.p) {
                        if (panelFeatureState.g == null) {
                            if (!a(panelFeatureState) || panelFeatureState.g == null) {
                                return;
                            }
                        } else if (panelFeatureState.p && panelFeatureState.g.getChildCount() > 0) {
                            panelFeatureState.g.removeAllViews();
                        }
                        if (c(panelFeatureState) && panelFeatureState.hasPanelItems()) {
                            LayoutParams layoutParams = panelFeatureState.h.getLayoutParams();
                            if (layoutParams == null) {
                                layoutParams = new LayoutParams(-2, -2);
                            }
                            panelFeatureState.g.setBackgroundResource(panelFeatureState.b);
                            ViewParent parent = panelFeatureState.h.getParent();
                            if (parent != null && (parent instanceof ViewGroup)) {
                                ((ViewGroup) parent).removeView(panelFeatureState.h);
                            }
                            panelFeatureState.g.addView(panelFeatureState.h, layoutParams);
                            if (!panelFeatureState.h.hasFocus()) {
                                panelFeatureState.h.requestFocus();
                            }
                        } else {
                            return;
                        }
                    } else if (panelFeatureState.i != null) {
                        LayoutParams layoutParams2 = panelFeatureState.i.getLayoutParams();
                        if (layoutParams2 != null && layoutParams2.width == -1) {
                            i = -1;
                            panelFeatureState.n = false;
                            WindowManager.LayoutParams layoutParams3 = new WindowManager.LayoutParams(i, -2, panelFeatureState.d, panelFeatureState.e, 1002, 8519680, -3);
                            layoutParams3.gravity = panelFeatureState.c;
                            layoutParams3.windowAnimations = panelFeatureState.f;
                            windowManager.addView(panelFeatureState.g, layoutParams3);
                            panelFeatureState.o = true;
                            return;
                        }
                    }
                    i = -2;
                    panelFeatureState.n = false;
                    WindowManager.LayoutParams layoutParams32 = new WindowManager.LayoutParams(i, -2, panelFeatureState.d, panelFeatureState.e, 1002, 8519680, -3);
                    layoutParams32.gravity = panelFeatureState.c;
                    layoutParams32.windowAnimations = panelFeatureState.f;
                    windowManager.addView(panelFeatureState.g, layoutParams32);
                    panelFeatureState.o = true;
                    return;
                }
                return;
            }
            a(panelFeatureState, true);
        }
    }

    private boolean a(PanelFeatureState panelFeatureState) {
        panelFeatureState.a(c());
        panelFeatureState.g = new ListMenuDecorView(panelFeatureState.l);
        panelFeatureState.c = 81;
        return true;
    }

    private void a(MenuBuilder menuBuilder, boolean z2) {
        if (this.u == null || !this.u.canShowOverflowMenu() || (ViewConfiguration.get(this.a).hasPermanentMenuKey() && !this.u.isOverflowMenuShowPending())) {
            PanelFeatureState a = a(0, true);
            a.p = true;
            a(a, false);
            a(a, (KeyEvent) null);
            return;
        }
        Window.Callback e = e();
        if (this.u.isOverflowMenuShowing() && z2) {
            this.u.hideOverflowMenu();
            if (!d()) {
                e.onPanelClosed(108, a(0, true).j);
            }
        } else if (e != null && !d()) {
            if (this.r && (this.s & 1) != 0) {
                this.b.getDecorView().removeCallbacks(this.H);
                this.H.run();
            }
            PanelFeatureState a2 = a(0, true);
            if (a2.j != null && !a2.q && e.onPreparePanel(0, a2.i, a2.j)) {
                e.onMenuOpened(108, a2.j);
                this.u.showOverflowMenu();
            }
        }
    }

    private boolean b(PanelFeatureState panelFeatureState) {
        Context context = this.a;
        if ((panelFeatureState.a == 0 || panelFeatureState.a == 108) && this.u != null) {
            TypedValue typedValue = new TypedValue();
            Theme theme = context.getTheme();
            theme.resolveAttribute(R.attr.actionBarTheme, typedValue, true);
            Theme theme2 = null;
            if (typedValue.resourceId != 0) {
                theme2 = context.getResources().newTheme();
                theme2.setTo(theme);
                theme2.applyStyle(typedValue.resourceId, true);
                theme2.resolveAttribute(R.attr.actionBarWidgetTheme, typedValue, true);
            } else {
                theme.resolveAttribute(R.attr.actionBarWidgetTheme, typedValue, true);
            }
            if (typedValue.resourceId != 0) {
                if (theme2 == null) {
                    theme2 = context.getResources().newTheme();
                    theme2.setTo(theme);
                }
                theme2.applyStyle(typedValue.resourceId, true);
            }
            if (theme2 != null) {
                Context contextThemeWrapper = new ContextThemeWrapper(context, 0);
                contextThemeWrapper.getTheme().setTo(theme2);
                context = contextThemeWrapper;
            }
        }
        MenuBuilder menuBuilder = new MenuBuilder(context);
        menuBuilder.setCallback(this);
        panelFeatureState.a(menuBuilder);
        return true;
    }

    private boolean c(PanelFeatureState panelFeatureState) {
        boolean z2 = true;
        if (panelFeatureState.i != null) {
            panelFeatureState.h = panelFeatureState.i;
            return true;
        } else if (panelFeatureState.j == null) {
            return false;
        } else {
            if (this.w == null) {
                this.w = new PanelMenuPresenterCallback();
            }
            panelFeatureState.h = (View) panelFeatureState.a((MenuPresenter.Callback) this.w);
            if (panelFeatureState.h == null) {
                z2 = false;
            }
            return z2;
        }
    }

    private boolean b(PanelFeatureState panelFeatureState, KeyEvent keyEvent) {
        if (d()) {
            return false;
        }
        if (panelFeatureState.m) {
            return true;
        }
        if (!(this.F == null || this.F == panelFeatureState)) {
            a(this.F, false);
        }
        Window.Callback e = e();
        if (e != null) {
            panelFeatureState.i = e.onCreatePanelView(panelFeatureState.a);
        }
        boolean z2 = panelFeatureState.a == 0 || panelFeatureState.a == 108;
        if (z2 && this.u != null) {
            this.u.setMenuPrepared();
        }
        if (panelFeatureState.i == null && (!z2 || !(b() instanceof ToolbarActionBar))) {
            if (panelFeatureState.j == null || panelFeatureState.q) {
                if (panelFeatureState.j == null && (!b(panelFeatureState) || panelFeatureState.j == null)) {
                    return false;
                }
                if (z2 && this.u != null) {
                    if (this.v == null) {
                        this.v = new ActionMenuPresenterCallback();
                    }
                    this.u.setMenu(panelFeatureState.j, this.v);
                }
                panelFeatureState.j.stopDispatchingItemsChanged();
                if (!e.onCreatePanelMenu(panelFeatureState.a, panelFeatureState.j)) {
                    panelFeatureState.a((MenuBuilder) null);
                    if (z2 && this.u != null) {
                        this.u.setMenu(null, this.v);
                    }
                    return false;
                }
                panelFeatureState.q = false;
            }
            panelFeatureState.j.stopDispatchingItemsChanged();
            if (panelFeatureState.r != null) {
                panelFeatureState.j.restoreActionViewStates(panelFeatureState.r);
                panelFeatureState.r = null;
            }
            if (!e.onPreparePanel(0, panelFeatureState.i, panelFeatureState.j)) {
                if (z2 && this.u != null) {
                    this.u.setMenu(null, this.v);
                }
                panelFeatureState.j.startDispatchingItemsChanged();
                return false;
            }
            panelFeatureState.qwertyMode = KeyCharacterMap.load(keyEvent != null ? keyEvent.getDeviceId() : -1).getKeyboardType() != 1;
            panelFeatureState.j.setQwertyMode(panelFeatureState.qwertyMode);
            panelFeatureState.j.startDispatchingItemsChanged();
        }
        panelFeatureState.m = true;
        panelFeatureState.n = false;
        this.F = panelFeatureState;
        return true;
    }

    /* access modifiers changed from: 0000 */
    public void a(MenuBuilder menuBuilder) {
        if (!this.D) {
            this.D = true;
            this.u.dismissPopups();
            Window.Callback e = e();
            if (e != null && !d()) {
                e.onPanelClosed(108, menuBuilder);
            }
            this.D = false;
        }
    }

    /* access modifiers changed from: 0000 */
    public void b(int i) {
        a(a(i, true), true);
    }

    /* access modifiers changed from: 0000 */
    public void a(PanelFeatureState panelFeatureState, boolean z2) {
        if (!z2 || panelFeatureState.a != 0 || this.u == null || !this.u.isOverflowMenuShowing()) {
            WindowManager windowManager = (WindowManager) this.a.getSystemService("window");
            if (!(windowManager == null || !panelFeatureState.o || panelFeatureState.g == null)) {
                windowManager.removeView(panelFeatureState.g);
                if (z2) {
                    a(panelFeatureState.a, panelFeatureState, null);
                }
            }
            panelFeatureState.m = false;
            panelFeatureState.n = false;
            panelFeatureState.o = false;
            panelFeatureState.h = null;
            panelFeatureState.p = true;
            if (this.F == panelFeatureState) {
                this.F = null;
            }
            return;
        }
        a(panelFeatureState.j);
    }

    private boolean d(int i, KeyEvent keyEvent) {
        if (keyEvent.getRepeatCount() == 0) {
            PanelFeatureState a = a(i, true);
            if (!a.o) {
                return b(a, keyEvent);
            }
        }
        return false;
    }

    /* JADX WARNING: Removed duplicated region for block: B:34:0x0070  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean e(int r4, android.view.KeyEvent r5) {
        /*
            r3 = this;
            android.support.v7.view.ActionMode r0 = r3.m
            r1 = 0
            if (r0 == 0) goto L_0x0006
            return r1
        L_0x0006:
            r0 = 1
            android.support.v7.app.AppCompatDelegateImplV9$PanelFeatureState r2 = r3.a(r4, r0)
            if (r4 != 0) goto L_0x0047
            android.support.v7.widget.DecorContentParent r4 = r3.u
            if (r4 == 0) goto L_0x0047
            android.support.v7.widget.DecorContentParent r4 = r3.u
            boolean r4 = r4.canShowOverflowMenu()
            if (r4 == 0) goto L_0x0047
            android.content.Context r4 = r3.a
            android.view.ViewConfiguration r4 = android.view.ViewConfiguration.get(r4)
            boolean r4 = r4.hasPermanentMenuKey()
            if (r4 != 0) goto L_0x0047
            android.support.v7.widget.DecorContentParent r4 = r3.u
            boolean r4 = r4.isOverflowMenuShowing()
            if (r4 != 0) goto L_0x0040
            boolean r4 = r3.d()
            if (r4 != 0) goto L_0x0067
            boolean r4 = r3.b(r2, r5)
            if (r4 == 0) goto L_0x0067
            android.support.v7.widget.DecorContentParent r4 = r3.u
            boolean r4 = r4.showOverflowMenu()
            goto L_0x006e
        L_0x0040:
            android.support.v7.widget.DecorContentParent r4 = r3.u
            boolean r4 = r4.hideOverflowMenu()
            goto L_0x006e
        L_0x0047:
            boolean r4 = r2.o
            if (r4 != 0) goto L_0x0069
            boolean r4 = r2.n
            if (r4 == 0) goto L_0x0050
            goto L_0x0069
        L_0x0050:
            boolean r4 = r2.m
            if (r4 == 0) goto L_0x0067
            boolean r4 = r2.q
            if (r4 == 0) goto L_0x005f
            r2.m = r1
            boolean r4 = r3.b(r2, r5)
            goto L_0x0060
        L_0x005f:
            r4 = 1
        L_0x0060:
            if (r4 == 0) goto L_0x0067
            r3.a(r2, r5)
            r4 = 1
            goto L_0x006e
        L_0x0067:
            r4 = 0
            goto L_0x006e
        L_0x0069:
            boolean r4 = r2.o
            r3.a(r2, r0)
        L_0x006e:
            if (r4 == 0) goto L_0x0087
            android.content.Context r5 = r3.a
            java.lang.String r0 = "audio"
            java.lang.Object r5 = r5.getSystemService(r0)
            android.media.AudioManager r5 = (android.media.AudioManager) r5
            if (r5 == 0) goto L_0x0080
            r5.playSoundEffect(r1)
            goto L_0x0087
        L_0x0080:
            java.lang.String r5 = "AppCompatDelegate"
            java.lang.String r0 = "Couldn't get audio manager"
            android.util.Log.w(r5, r0)
        L_0x0087:
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v7.app.AppCompatDelegateImplV9.e(int, android.view.KeyEvent):boolean");
    }

    /* access modifiers changed from: 0000 */
    public void a(int i, PanelFeatureState panelFeatureState, Menu menu) {
        if (menu == null) {
            if (panelFeatureState == null && i >= 0 && i < this.E.length) {
                panelFeatureState = this.E[i];
            }
            if (panelFeatureState != null) {
                menu = panelFeatureState.j;
            }
        }
        if ((panelFeatureState == null || panelFeatureState.o) && !d()) {
            this.c.onPanelClosed(i, menu);
        }
    }

    /* access modifiers changed from: 0000 */
    public PanelFeatureState a(Menu menu) {
        PanelFeatureState[] panelFeatureStateArr = this.E;
        int length = panelFeatureStateArr != null ? panelFeatureStateArr.length : 0;
        for (int i = 0; i < length; i++) {
            PanelFeatureState panelFeatureState = panelFeatureStateArr[i];
            if (panelFeatureState != null && panelFeatureState.j == menu) {
                return panelFeatureState;
            }
        }
        return null;
    }

    /* access modifiers changed from: protected */
    public PanelFeatureState a(int i, boolean z2) {
        PanelFeatureState[] panelFeatureStateArr = this.E;
        if (panelFeatureStateArr == null || panelFeatureStateArr.length <= i) {
            PanelFeatureState[] panelFeatureStateArr2 = new PanelFeatureState[(i + 1)];
            if (panelFeatureStateArr != null) {
                System.arraycopy(panelFeatureStateArr, 0, panelFeatureStateArr2, 0, panelFeatureStateArr.length);
            }
            this.E = panelFeatureStateArr2;
            panelFeatureStateArr = panelFeatureStateArr2;
        }
        PanelFeatureState panelFeatureState = panelFeatureStateArr[i];
        if (panelFeatureState != null) {
            return panelFeatureState;
        }
        PanelFeatureState panelFeatureState2 = new PanelFeatureState(i);
        panelFeatureStateArr[i] = panelFeatureState2;
        return panelFeatureState2;
    }

    private boolean a(PanelFeatureState panelFeatureState, int i, KeyEvent keyEvent, int i2) {
        boolean z2 = false;
        if (keyEvent.isSystem()) {
            return false;
        }
        if ((panelFeatureState.m || b(panelFeatureState, keyEvent)) && panelFeatureState.j != null) {
            z2 = panelFeatureState.j.performShortcut(i, keyEvent, i2);
        }
        if (z2 && (i2 & 1) == 0 && this.u == null) {
            a(panelFeatureState, true);
        }
        return z2;
    }

    private void a(int i) {
        this.s = (1 << i) | this.s;
        if (!this.r) {
            ViewCompat.postOnAnimation(this.b.getDecorView(), this.H);
            this.r = true;
        }
    }

    /* access modifiers changed from: 0000 */
    public void c(int i) {
        PanelFeatureState a = a(i, true);
        if (a.j != null) {
            Bundle bundle = new Bundle();
            a.j.saveActionViewStates(bundle);
            if (bundle.size() > 0) {
                a.r = bundle;
            }
            a.j.stopDispatchingItemsChanged();
            a.j.clear();
        }
        a.q = true;
        a.p = true;
        if ((i == 108 || i == 0) && this.u != null) {
            PanelFeatureState a2 = a(0, false);
            if (a2 != null) {
                a2.m = false;
                b(a2, (KeyEvent) null);
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public int d(int i) {
        boolean z2;
        boolean z3;
        boolean z4;
        int i2 = 0;
        if (this.n == null || !(this.n.getLayoutParams() instanceof MarginLayoutParams)) {
            z2 = false;
        } else {
            MarginLayoutParams marginLayoutParams = (MarginLayoutParams) this.n.getLayoutParams();
            z2 = true;
            if (this.n.isShown()) {
                if (this.J == null) {
                    this.J = new Rect();
                    this.K = new Rect();
                }
                Rect rect = this.J;
                Rect rect2 = this.K;
                rect.set(0, i, 0, 0);
                ViewUtils.computeFitSystemWindows(this.y, rect, rect2);
                if (marginLayoutParams.topMargin != (rect2.top == 0 ? i : 0)) {
                    marginLayoutParams.topMargin = i;
                    if (this.A == null) {
                        this.A = new View(this.a);
                        this.A.setBackgroundColor(this.a.getResources().getColor(R.color.abc_input_method_navigation_guard));
                        this.y.addView(this.A, -1, new LayoutParams(-1, i));
                    } else {
                        LayoutParams layoutParams = this.A.getLayoutParams();
                        if (layoutParams.height != i) {
                            layoutParams.height = i;
                            this.A.setLayoutParams(layoutParams);
                        }
                    }
                    z3 = true;
                } else {
                    z3 = false;
                }
                if (this.A == null) {
                    z2 = false;
                }
                if (!this.j && z2) {
                    i = 0;
                }
            } else {
                if (marginLayoutParams.topMargin != 0) {
                    marginLayoutParams.topMargin = 0;
                    z4 = true;
                } else {
                    z4 = false;
                }
                z2 = false;
            }
            if (z3) {
                this.n.setLayoutParams(marginLayoutParams);
            }
        }
        if (this.A != null) {
            View view = this.A;
            if (!z2) {
                i2 = 8;
            }
            view.setVisibility(i2);
        }
        return i;
    }

    private void n() {
        if (this.x) {
            throw new AndroidRuntimeException("Window feature must be requested before adding content");
        }
    }

    private int e(int i) {
        if (i == 8) {
            Log.i("AppCompatDelegate", "You should now use the AppCompatDelegate.FEATURE_SUPPORT_ACTION_BAR id when requesting this feature.");
            return 108;
        } else if (i != 9) {
            return i;
        } else {
            Log.i("AppCompatDelegate", "You should now use the AppCompatDelegate.FEATURE_SUPPORT_ACTION_BAR_OVERLAY id when requesting this feature.");
            return 109;
        }
    }

    /* access modifiers changed from: 0000 */
    public void j() {
        if (this.u != null) {
            this.u.dismissPopups();
        }
        if (this.o != null) {
            this.b.getDecorView().removeCallbacks(this.p);
            if (this.o.isShowing()) {
                try {
                    this.o.dismiss();
                } catch (IllegalArgumentException unused) {
                }
            }
            this.o = null;
        }
        h();
        PanelFeatureState a = a(0, false);
        if (a != null && a.j != null) {
            a.j.close();
        }
    }
}
