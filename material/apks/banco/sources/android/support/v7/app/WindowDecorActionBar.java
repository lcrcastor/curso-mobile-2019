package android.support.v7.app;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v4.view.ViewPropertyAnimatorListenerAdapter;
import android.support.v4.view.ViewPropertyAnimatorUpdateListener;
import android.support.v7.app.ActionBar.LayoutParams;
import android.support.v7.app.ActionBar.OnMenuVisibilityListener;
import android.support.v7.app.ActionBar.OnNavigationListener;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBar.TabListener;
import android.support.v7.appcompat.R;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.view.ActionBarPolicy;
import android.support.v7.view.ActionMode;
import android.support.v7.view.ActionMode.Callback;
import android.support.v7.view.SupportMenuInflater;
import android.support.v7.view.ViewPropertyAnimatorCompatSet;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuPopupHelper;
import android.support.v7.view.menu.SubMenuBuilder;
import android.support.v7.widget.ActionBarContainer;
import android.support.v7.widget.ActionBarContextView;
import android.support.v7.widget.ActionBarOverlayLayout;
import android.support.v7.widget.ActionBarOverlayLayout.ActionBarVisibilityCallback;
import android.support.v7.widget.DecorToolbar;
import android.support.v7.widget.ScrollingTabContainerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.SpinnerAdapter;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

@RestrictTo({Scope.LIBRARY_GROUP})
public class WindowDecorActionBar extends ActionBar implements ActionBarVisibilityCallback {
    static final /* synthetic */ boolean s = true;
    private static final Interpolator t = new AccelerateInterpolator();
    private static final Interpolator u = new DecelerateInterpolator();
    private int A = -1;
    private boolean B;
    private boolean C;
    private ArrayList<OnMenuVisibilityListener> D = new ArrayList<>();
    private boolean E;
    private int F = 0;
    private boolean G;
    private boolean H = true;
    private boolean I;
    Context a;
    ActionBarOverlayLayout b;
    ActionBarContainer c;
    DecorToolbar d;
    ActionBarContextView e;
    View f;
    ScrollingTabContainerView g;
    ActionModeImpl h;
    ActionMode i;
    Callback j;
    boolean k = true;
    boolean l;
    boolean m;
    ViewPropertyAnimatorCompatSet n;
    boolean o;
    final ViewPropertyAnimatorListener p = new ViewPropertyAnimatorListenerAdapter() {
        public void onAnimationEnd(View view) {
            if (WindowDecorActionBar.this.k && WindowDecorActionBar.this.f != null) {
                WindowDecorActionBar.this.f.setTranslationY(BitmapDescriptorFactory.HUE_RED);
                WindowDecorActionBar.this.c.setTranslationY(BitmapDescriptorFactory.HUE_RED);
            }
            WindowDecorActionBar.this.c.setVisibility(8);
            WindowDecorActionBar.this.c.setTransitioning(false);
            WindowDecorActionBar.this.n = null;
            WindowDecorActionBar.this.b();
            if (WindowDecorActionBar.this.b != null) {
                ViewCompat.requestApplyInsets(WindowDecorActionBar.this.b);
            }
        }
    };
    final ViewPropertyAnimatorListener q = new ViewPropertyAnimatorListenerAdapter() {
        public void onAnimationEnd(View view) {
            WindowDecorActionBar.this.n = null;
            WindowDecorActionBar.this.c.requestLayout();
        }
    };
    final ViewPropertyAnimatorUpdateListener r = new ViewPropertyAnimatorUpdateListener() {
        public void onAnimationUpdate(View view) {
            ((View) WindowDecorActionBar.this.c.getParent()).invalidate();
        }
    };
    private Context v;
    private Activity w;
    private Dialog x;
    private ArrayList<TabImpl> y = new ArrayList<>();
    private TabImpl z;

    @RestrictTo({Scope.LIBRARY_GROUP})
    public class ActionModeImpl extends ActionMode implements MenuBuilder.Callback {
        private final Context b;
        private final MenuBuilder c;
        private Callback d;
        private WeakReference<View> e;

        public void onCloseMenu(MenuBuilder menuBuilder, boolean z) {
        }

        public void onCloseSubMenu(SubMenuBuilder subMenuBuilder) {
        }

        public ActionModeImpl(Context context, Callback callback) {
            this.b = context;
            this.d = callback;
            this.c = new MenuBuilder(context).setDefaultShowAsAction(1);
            this.c.setCallback(this);
        }

        public MenuInflater getMenuInflater() {
            return new SupportMenuInflater(this.b);
        }

        public Menu getMenu() {
            return this.c;
        }

        public void finish() {
            if (WindowDecorActionBar.this.h == this) {
                if (!WindowDecorActionBar.a(WindowDecorActionBar.this.l, WindowDecorActionBar.this.m, false)) {
                    WindowDecorActionBar.this.i = this;
                    WindowDecorActionBar.this.j = this.d;
                } else {
                    this.d.onDestroyActionMode(this);
                }
                this.d = null;
                WindowDecorActionBar.this.animateToMode(false);
                WindowDecorActionBar.this.e.closeMode();
                WindowDecorActionBar.this.d.getViewGroup().sendAccessibilityEvent(32);
                WindowDecorActionBar.this.b.setHideOnContentScrollEnabled(WindowDecorActionBar.this.o);
                WindowDecorActionBar.this.h = null;
            }
        }

        public void invalidate() {
            if (WindowDecorActionBar.this.h == this) {
                this.c.stopDispatchingItemsChanged();
                try {
                    this.d.onPrepareActionMode(this, this.c);
                } finally {
                    this.c.startDispatchingItemsChanged();
                }
            }
        }

        public boolean dispatchOnCreate() {
            this.c.stopDispatchingItemsChanged();
            try {
                return this.d.onCreateActionMode(this, this.c);
            } finally {
                this.c.startDispatchingItemsChanged();
            }
        }

        public void setCustomView(View view) {
            WindowDecorActionBar.this.e.setCustomView(view);
            this.e = new WeakReference<>(view);
        }

        public void setSubtitle(CharSequence charSequence) {
            WindowDecorActionBar.this.e.setSubtitle(charSequence);
        }

        public void setTitle(CharSequence charSequence) {
            WindowDecorActionBar.this.e.setTitle(charSequence);
        }

        public void setTitle(int i) {
            setTitle((CharSequence) WindowDecorActionBar.this.a.getResources().getString(i));
        }

        public void setSubtitle(int i) {
            setSubtitle((CharSequence) WindowDecorActionBar.this.a.getResources().getString(i));
        }

        public CharSequence getTitle() {
            return WindowDecorActionBar.this.e.getTitle();
        }

        public CharSequence getSubtitle() {
            return WindowDecorActionBar.this.e.getSubtitle();
        }

        public void setTitleOptionalHint(boolean z) {
            super.setTitleOptionalHint(z);
            WindowDecorActionBar.this.e.setTitleOptional(z);
        }

        public boolean isTitleOptional() {
            return WindowDecorActionBar.this.e.isTitleOptional();
        }

        public View getCustomView() {
            if (this.e != null) {
                return (View) this.e.get();
            }
            return null;
        }

        public boolean onMenuItemSelected(MenuBuilder menuBuilder, MenuItem menuItem) {
            if (this.d != null) {
                return this.d.onActionItemClicked(this, menuItem);
            }
            return false;
        }

        public boolean onSubMenuSelected(SubMenuBuilder subMenuBuilder) {
            if (this.d == null) {
                return false;
            }
            if (!subMenuBuilder.hasVisibleItems()) {
                return true;
            }
            new MenuPopupHelper(WindowDecorActionBar.this.getThemedContext(), subMenuBuilder).show();
            return true;
        }

        public void onMenuModeChange(MenuBuilder menuBuilder) {
            if (this.d != null) {
                invalidate();
                WindowDecorActionBar.this.e.showOverflowMenu();
            }
        }
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    public class TabImpl extends Tab {
        private TabListener b;
        private Object c;
        private Drawable d;
        private CharSequence e;
        private CharSequence f;
        private int g = -1;
        private View h;

        public TabImpl() {
        }

        public Object getTag() {
            return this.c;
        }

        public Tab setTag(Object obj) {
            this.c = obj;
            return this;
        }

        public TabListener getCallback() {
            return this.b;
        }

        public Tab setTabListener(TabListener tabListener) {
            this.b = tabListener;
            return this;
        }

        public View getCustomView() {
            return this.h;
        }

        public Tab setCustomView(View view) {
            this.h = view;
            if (this.g >= 0) {
                WindowDecorActionBar.this.g.updateTab(this.g);
            }
            return this;
        }

        public Tab setCustomView(int i) {
            return setCustomView(LayoutInflater.from(WindowDecorActionBar.this.getThemedContext()).inflate(i, null));
        }

        public Drawable getIcon() {
            return this.d;
        }

        public int getPosition() {
            return this.g;
        }

        public void setPosition(int i) {
            this.g = i;
        }

        public CharSequence getText() {
            return this.e;
        }

        public Tab setIcon(Drawable drawable) {
            this.d = drawable;
            if (this.g >= 0) {
                WindowDecorActionBar.this.g.updateTab(this.g);
            }
            return this;
        }

        public Tab setIcon(int i) {
            return setIcon(AppCompatResources.getDrawable(WindowDecorActionBar.this.a, i));
        }

        public Tab setText(CharSequence charSequence) {
            this.e = charSequence;
            if (this.g >= 0) {
                WindowDecorActionBar.this.g.updateTab(this.g);
            }
            return this;
        }

        public Tab setText(int i) {
            return setText(WindowDecorActionBar.this.a.getResources().getText(i));
        }

        public void select() {
            WindowDecorActionBar.this.selectTab(this);
        }

        public Tab setContentDescription(int i) {
            return setContentDescription(WindowDecorActionBar.this.a.getResources().getText(i));
        }

        public Tab setContentDescription(CharSequence charSequence) {
            this.f = charSequence;
            if (this.g >= 0) {
                WindowDecorActionBar.this.g.updateTab(this.g);
            }
            return this;
        }

        public CharSequence getContentDescription() {
            return this.f;
        }
    }

    static boolean a(boolean z2, boolean z3, boolean z4) {
        if (z4) {
            return true;
        }
        return !z2 && !z3;
    }

    public void onContentScrollStopped() {
    }

    public void setSplitBackgroundDrawable(Drawable drawable) {
    }

    public WindowDecorActionBar(Activity activity, boolean z2) {
        this.w = activity;
        View decorView = activity.getWindow().getDecorView();
        a(decorView);
        if (!z2) {
            this.f = decorView.findViewById(16908290);
        }
    }

    public WindowDecorActionBar(Dialog dialog) {
        this.x = dialog;
        a(dialog.getWindow().getDecorView());
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    public WindowDecorActionBar(View view) {
        if (s || view.isInEditMode()) {
            a(view);
            return;
        }
        throw new AssertionError();
    }

    private void a(View view) {
        this.b = (ActionBarOverlayLayout) view.findViewById(R.id.decor_content_parent);
        if (this.b != null) {
            this.b.setActionBarVisibilityCallback(this);
        }
        this.d = b(view.findViewById(R.id.action_bar));
        this.e = (ActionBarContextView) view.findViewById(R.id.action_context_bar);
        this.c = (ActionBarContainer) view.findViewById(R.id.action_bar_container);
        if (this.d == null || this.e == null || this.c == null) {
            StringBuilder sb = new StringBuilder();
            sb.append(getClass().getSimpleName());
            sb.append(" can only be used ");
            sb.append("with a compatible window decor layout");
            throw new IllegalStateException(sb.toString());
        }
        this.a = this.d.getContext();
        boolean z2 = (this.d.getDisplayOptions() & 4) != 0;
        if (z2) {
            this.B = true;
        }
        ActionBarPolicy actionBarPolicy = ActionBarPolicy.get(this.a);
        setHomeButtonEnabled(actionBarPolicy.enableHomeButtonByDefault() || z2);
        a(actionBarPolicy.hasEmbeddedTabs());
        TypedArray obtainStyledAttributes = this.a.obtainStyledAttributes(null, R.styleable.ActionBar, R.attr.actionBarStyle, 0);
        if (obtainStyledAttributes.getBoolean(R.styleable.ActionBar_hideOnContentScroll, false)) {
            setHideOnContentScrollEnabled(true);
        }
        int dimensionPixelSize = obtainStyledAttributes.getDimensionPixelSize(R.styleable.ActionBar_elevation, 0);
        if (dimensionPixelSize != 0) {
            setElevation((float) dimensionPixelSize);
        }
        obtainStyledAttributes.recycle();
    }

    private DecorToolbar b(View view) {
        if (view instanceof DecorToolbar) {
            return (DecorToolbar) view;
        }
        if (view instanceof Toolbar) {
            return ((Toolbar) view).getWrapper();
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Can't make a decor toolbar out of ");
        sb.append(view);
        throw new IllegalStateException(sb.toString() != null ? view.getClass().getSimpleName() : "null");
    }

    public void setElevation(float f2) {
        ViewCompat.setElevation(this.c, f2);
    }

    public float getElevation() {
        return ViewCompat.getElevation(this.c);
    }

    public void onConfigurationChanged(Configuration configuration) {
        a(ActionBarPolicy.get(this.a).hasEmbeddedTabs());
    }

    private void a(boolean z2) {
        this.E = z2;
        if (!this.E) {
            this.d.setEmbeddedTabView(null);
            this.c.setTabContainer(this.g);
        } else {
            this.c.setTabContainer(null);
            this.d.setEmbeddedTabView(this.g);
        }
        boolean z3 = true;
        boolean z4 = getNavigationMode() == 2;
        if (this.g != null) {
            if (z4) {
                this.g.setVisibility(0);
                if (this.b != null) {
                    ViewCompat.requestApplyInsets(this.b);
                }
            } else {
                this.g.setVisibility(8);
            }
        }
        this.d.setCollapsible(!this.E && z4);
        ActionBarOverlayLayout actionBarOverlayLayout = this.b;
        if (this.E || !z4) {
            z3 = false;
        }
        actionBarOverlayLayout.setHasNonEmbeddedTabs(z3);
    }

    private void c() {
        if (this.g == null) {
            ScrollingTabContainerView scrollingTabContainerView = new ScrollingTabContainerView(this.a);
            if (this.E) {
                scrollingTabContainerView.setVisibility(0);
                this.d.setEmbeddedTabView(scrollingTabContainerView);
            } else {
                if (getNavigationMode() == 2) {
                    scrollingTabContainerView.setVisibility(0);
                    if (this.b != null) {
                        ViewCompat.requestApplyInsets(this.b);
                    }
                } else {
                    scrollingTabContainerView.setVisibility(8);
                }
                this.c.setTabContainer(scrollingTabContainerView);
            }
            this.g = scrollingTabContainerView;
        }
    }

    /* access modifiers changed from: 0000 */
    public void b() {
        if (this.j != null) {
            this.j.onDestroyActionMode(this.i);
            this.i = null;
            this.j = null;
        }
    }

    public void onWindowVisibilityChanged(int i2) {
        this.F = i2;
    }

    public void setShowHideAnimationEnabled(boolean z2) {
        this.I = z2;
        if (!z2 && this.n != null) {
            this.n.cancel();
        }
    }

    public void addOnMenuVisibilityListener(OnMenuVisibilityListener onMenuVisibilityListener) {
        this.D.add(onMenuVisibilityListener);
    }

    public void removeOnMenuVisibilityListener(OnMenuVisibilityListener onMenuVisibilityListener) {
        this.D.remove(onMenuVisibilityListener);
    }

    public void dispatchMenuVisibilityChanged(boolean z2) {
        if (z2 != this.C) {
            this.C = z2;
            int size = this.D.size();
            for (int i2 = 0; i2 < size; i2++) {
                ((OnMenuVisibilityListener) this.D.get(i2)).onMenuVisibilityChanged(z2);
            }
        }
    }

    public void setCustomView(int i2) {
        setCustomView(LayoutInflater.from(getThemedContext()).inflate(i2, this.d.getViewGroup(), false));
    }

    public void setDisplayUseLogoEnabled(boolean z2) {
        setDisplayOptions(z2 ? 1 : 0, 1);
    }

    public void setDisplayShowHomeEnabled(boolean z2) {
        setDisplayOptions(z2 ? 2 : 0, 2);
    }

    public void setDisplayHomeAsUpEnabled(boolean z2) {
        setDisplayOptions(z2 ? 4 : 0, 4);
    }

    public void setDisplayShowTitleEnabled(boolean z2) {
        setDisplayOptions(z2 ? 8 : 0, 8);
    }

    public void setDisplayShowCustomEnabled(boolean z2) {
        setDisplayOptions(z2 ? 16 : 0, 16);
    }

    public void setHomeButtonEnabled(boolean z2) {
        this.d.setHomeButtonEnabled(z2);
    }

    public void setTitle(int i2) {
        setTitle((CharSequence) this.a.getString(i2));
    }

    public void setSubtitle(int i2) {
        setSubtitle((CharSequence) this.a.getString(i2));
    }

    public void setSelectedNavigationItem(int i2) {
        switch (this.d.getNavigationMode()) {
            case 1:
                this.d.setDropdownSelectedPosition(i2);
                return;
            case 2:
                selectTab((Tab) this.y.get(i2));
                return;
            default:
                throw new IllegalStateException("setSelectedNavigationIndex not valid for current navigation mode");
        }
    }

    public void removeAllTabs() {
        d();
    }

    private void d() {
        if (this.z != null) {
            selectTab(null);
        }
        this.y.clear();
        if (this.g != null) {
            this.g.removeAllTabs();
        }
        this.A = -1;
    }

    public void setTitle(CharSequence charSequence) {
        this.d.setTitle(charSequence);
    }

    public void setWindowTitle(CharSequence charSequence) {
        this.d.setWindowTitle(charSequence);
    }

    public boolean requestFocus() {
        ViewGroup viewGroup = this.d.getViewGroup();
        if (viewGroup == null || viewGroup.hasFocus()) {
            return false;
        }
        viewGroup.requestFocus();
        return true;
    }

    public void setSubtitle(CharSequence charSequence) {
        this.d.setSubtitle(charSequence);
    }

    public void setDisplayOptions(int i2) {
        if ((i2 & 4) != 0) {
            this.B = true;
        }
        this.d.setDisplayOptions(i2);
    }

    public void setDisplayOptions(int i2, int i3) {
        int displayOptions = this.d.getDisplayOptions();
        if ((i3 & 4) != 0) {
            this.B = true;
        }
        this.d.setDisplayOptions((i2 & i3) | ((i3 ^ -1) & displayOptions));
    }

    public void setBackgroundDrawable(Drawable drawable) {
        this.c.setPrimaryBackground(drawable);
    }

    public void setStackedBackgroundDrawable(Drawable drawable) {
        this.c.setStackedBackground(drawable);
    }

    public View getCustomView() {
        return this.d.getCustomView();
    }

    public CharSequence getTitle() {
        return this.d.getTitle();
    }

    public CharSequence getSubtitle() {
        return this.d.getSubtitle();
    }

    public int getNavigationMode() {
        return this.d.getNavigationMode();
    }

    public int getDisplayOptions() {
        return this.d.getDisplayOptions();
    }

    public ActionMode startActionMode(Callback callback) {
        if (this.h != null) {
            this.h.finish();
        }
        this.b.setHideOnContentScrollEnabled(false);
        this.e.killMode();
        ActionModeImpl actionModeImpl = new ActionModeImpl(this.e.getContext(), callback);
        if (!actionModeImpl.dispatchOnCreate()) {
            return null;
        }
        this.h = actionModeImpl;
        actionModeImpl.invalidate();
        this.e.initForMode(actionModeImpl);
        animateToMode(true);
        this.e.sendAccessibilityEvent(32);
        return actionModeImpl;
    }

    private void a(Tab tab, int i2) {
        TabImpl tabImpl = (TabImpl) tab;
        if (tabImpl.getCallback() == null) {
            throw new IllegalStateException("Action Bar Tab must have a Callback");
        }
        tabImpl.setPosition(i2);
        this.y.add(i2, tabImpl);
        int size = this.y.size();
        while (true) {
            i2++;
            if (i2 < size) {
                ((TabImpl) this.y.get(i2)).setPosition(i2);
            } else {
                return;
            }
        }
    }

    public void addTab(Tab tab) {
        addTab(tab, this.y.isEmpty());
    }

    public void addTab(Tab tab, int i2) {
        addTab(tab, i2, this.y.isEmpty());
    }

    public void addTab(Tab tab, boolean z2) {
        c();
        this.g.addTab(tab, z2);
        a(tab, this.y.size());
        if (z2) {
            selectTab(tab);
        }
    }

    public void addTab(Tab tab, int i2, boolean z2) {
        c();
        this.g.addTab(tab, i2, z2);
        a(tab, i2);
        if (z2) {
            selectTab(tab);
        }
    }

    public Tab newTab() {
        return new TabImpl();
    }

    public void removeTab(Tab tab) {
        removeTabAt(tab.getPosition());
    }

    public void removeTabAt(int i2) {
        if (this.g != null) {
            int position = this.z != null ? this.z.getPosition() : this.A;
            this.g.removeTabAt(i2);
            TabImpl tabImpl = (TabImpl) this.y.remove(i2);
            if (tabImpl != null) {
                tabImpl.setPosition(-1);
            }
            int size = this.y.size();
            for (int i3 = i2; i3 < size; i3++) {
                ((TabImpl) this.y.get(i3)).setPosition(i3);
            }
            if (position == i2) {
                selectTab(this.y.isEmpty() ? null : (TabImpl) this.y.get(Math.max(0, i2 - 1)));
            }
        }
    }

    public void selectTab(Tab tab) {
        int i2 = -1;
        if (getNavigationMode() != 2) {
            if (tab != null) {
                i2 = tab.getPosition();
            }
            this.A = i2;
            return;
        }
        FragmentTransaction disallowAddToBackStack = (!(this.w instanceof FragmentActivity) || this.d.getViewGroup().isInEditMode()) ? null : ((FragmentActivity) this.w).getSupportFragmentManager().beginTransaction().disallowAddToBackStack();
        if (this.z != tab) {
            ScrollingTabContainerView scrollingTabContainerView = this.g;
            if (tab != null) {
                i2 = tab.getPosition();
            }
            scrollingTabContainerView.setTabSelected(i2);
            if (this.z != null) {
                this.z.getCallback().onTabUnselected(this.z, disallowAddToBackStack);
            }
            this.z = (TabImpl) tab;
            if (this.z != null) {
                this.z.getCallback().onTabSelected(this.z, disallowAddToBackStack);
            }
        } else if (this.z != null) {
            this.z.getCallback().onTabReselected(this.z, disallowAddToBackStack);
            this.g.animateToTab(tab.getPosition());
        }
        if (disallowAddToBackStack != null && !disallowAddToBackStack.isEmpty()) {
            disallowAddToBackStack.commit();
        }
    }

    public Tab getSelectedTab() {
        return this.z;
    }

    public int getHeight() {
        return this.c.getHeight();
    }

    public void enableContentAnimations(boolean z2) {
        this.k = z2;
    }

    public void show() {
        if (this.l) {
            this.l = false;
            b(false);
        }
    }

    private void e() {
        if (!this.G) {
            this.G = true;
            if (this.b != null) {
                this.b.setShowingForActionMode(true);
            }
            b(false);
        }
    }

    public void showForSystem() {
        if (this.m) {
            this.m = false;
            b(true);
        }
    }

    public void hide() {
        if (!this.l) {
            this.l = true;
            b(false);
        }
    }

    private void f() {
        if (this.G) {
            this.G = false;
            if (this.b != null) {
                this.b.setShowingForActionMode(false);
            }
            b(false);
        }
    }

    public void hideForSystem() {
        if (!this.m) {
            this.m = true;
            b(true);
        }
    }

    public void setHideOnContentScrollEnabled(boolean z2) {
        if (!z2 || this.b.isInOverlayMode()) {
            this.o = z2;
            this.b.setHideOnContentScrollEnabled(z2);
            return;
        }
        throw new IllegalStateException("Action bar must be in overlay mode (Window.FEATURE_OVERLAY_ACTION_BAR) to enable hide on content scroll");
    }

    public boolean isHideOnContentScrollEnabled() {
        return this.b.isHideOnContentScrollEnabled();
    }

    public int getHideOffset() {
        return this.b.getActionBarHideOffset();
    }

    public void setHideOffset(int i2) {
        if (i2 == 0 || this.b.isInOverlayMode()) {
            this.b.setActionBarHideOffset(i2);
            return;
        }
        throw new IllegalStateException("Action bar must be in overlay mode (Window.FEATURE_OVERLAY_ACTION_BAR) to set a non-zero hide offset");
    }

    private void b(boolean z2) {
        if (a(this.l, this.m, this.G)) {
            if (!this.H) {
                this.H = true;
                doShow(z2);
            }
        } else if (this.H) {
            this.H = false;
            doHide(z2);
        }
    }

    public void doShow(boolean z2) {
        if (this.n != null) {
            this.n.cancel();
        }
        this.c.setVisibility(0);
        if (this.F != 0 || (!this.I && !z2)) {
            this.c.setAlpha(1.0f);
            this.c.setTranslationY(BitmapDescriptorFactory.HUE_RED);
            if (this.k && this.f != null) {
                this.f.setTranslationY(BitmapDescriptorFactory.HUE_RED);
            }
            this.q.onAnimationEnd(null);
        } else {
            this.c.setTranslationY(BitmapDescriptorFactory.HUE_RED);
            float f2 = (float) (-this.c.getHeight());
            if (z2) {
                int[] iArr = {0, 0};
                this.c.getLocationInWindow(iArr);
                f2 -= (float) iArr[1];
            }
            this.c.setTranslationY(f2);
            ViewPropertyAnimatorCompatSet viewPropertyAnimatorCompatSet = new ViewPropertyAnimatorCompatSet();
            ViewPropertyAnimatorCompat translationY = ViewCompat.animate(this.c).translationY(BitmapDescriptorFactory.HUE_RED);
            translationY.setUpdateListener(this.r);
            viewPropertyAnimatorCompatSet.play(translationY);
            if (this.k && this.f != null) {
                this.f.setTranslationY(f2);
                viewPropertyAnimatorCompatSet.play(ViewCompat.animate(this.f).translationY(BitmapDescriptorFactory.HUE_RED));
            }
            viewPropertyAnimatorCompatSet.setInterpolator(u);
            viewPropertyAnimatorCompatSet.setDuration(250);
            viewPropertyAnimatorCompatSet.setListener(this.q);
            this.n = viewPropertyAnimatorCompatSet;
            viewPropertyAnimatorCompatSet.start();
        }
        if (this.b != null) {
            ViewCompat.requestApplyInsets(this.b);
        }
    }

    public void doHide(boolean z2) {
        if (this.n != null) {
            this.n.cancel();
        }
        if (this.F != 0 || (!this.I && !z2)) {
            this.p.onAnimationEnd(null);
            return;
        }
        this.c.setAlpha(1.0f);
        this.c.setTransitioning(true);
        ViewPropertyAnimatorCompatSet viewPropertyAnimatorCompatSet = new ViewPropertyAnimatorCompatSet();
        float f2 = (float) (-this.c.getHeight());
        if (z2) {
            int[] iArr = {0, 0};
            this.c.getLocationInWindow(iArr);
            f2 -= (float) iArr[1];
        }
        ViewPropertyAnimatorCompat translationY = ViewCompat.animate(this.c).translationY(f2);
        translationY.setUpdateListener(this.r);
        viewPropertyAnimatorCompatSet.play(translationY);
        if (this.k && this.f != null) {
            viewPropertyAnimatorCompatSet.play(ViewCompat.animate(this.f).translationY(f2));
        }
        viewPropertyAnimatorCompatSet.setInterpolator(t);
        viewPropertyAnimatorCompatSet.setDuration(250);
        viewPropertyAnimatorCompatSet.setListener(this.p);
        this.n = viewPropertyAnimatorCompatSet;
        viewPropertyAnimatorCompatSet.start();
    }

    public boolean isShowing() {
        int height = getHeight();
        return this.H && (height == 0 || getHideOffset() < height);
    }

    public void animateToMode(boolean z2) {
        ViewPropertyAnimatorCompat viewPropertyAnimatorCompat;
        ViewPropertyAnimatorCompat viewPropertyAnimatorCompat2;
        if (z2) {
            e();
        } else {
            f();
        }
        if (g()) {
            if (z2) {
                viewPropertyAnimatorCompat = this.d.setupAnimatorToVisibility(4, 100);
                viewPropertyAnimatorCompat2 = this.e.setupAnimatorToVisibility(0, 200);
            } else {
                viewPropertyAnimatorCompat2 = this.d.setupAnimatorToVisibility(0, 200);
                viewPropertyAnimatorCompat = this.e.setupAnimatorToVisibility(8, 100);
            }
            ViewPropertyAnimatorCompatSet viewPropertyAnimatorCompatSet = new ViewPropertyAnimatorCompatSet();
            viewPropertyAnimatorCompatSet.playSequentially(viewPropertyAnimatorCompat, viewPropertyAnimatorCompat2);
            viewPropertyAnimatorCompatSet.start();
        } else if (z2) {
            this.d.setVisibility(4);
            this.e.setVisibility(0);
        } else {
            this.d.setVisibility(0);
            this.e.setVisibility(8);
        }
    }

    private boolean g() {
        return ViewCompat.isLaidOut(this.c);
    }

    public Context getThemedContext() {
        if (this.v == null) {
            TypedValue typedValue = new TypedValue();
            this.a.getTheme().resolveAttribute(R.attr.actionBarWidgetTheme, typedValue, true);
            int i2 = typedValue.resourceId;
            if (i2 != 0) {
                this.v = new ContextThemeWrapper(this.a, i2);
            } else {
                this.v = this.a;
            }
        }
        return this.v;
    }

    public boolean isTitleTruncated() {
        return this.d != null && this.d.isTitleTruncated();
    }

    public void setHomeAsUpIndicator(Drawable drawable) {
        this.d.setNavigationIcon(drawable);
    }

    public void setHomeAsUpIndicator(int i2) {
        this.d.setNavigationIcon(i2);
    }

    public void setHomeActionContentDescription(CharSequence charSequence) {
        this.d.setNavigationContentDescription(charSequence);
    }

    public void setHomeActionContentDescription(int i2) {
        this.d.setNavigationContentDescription(i2);
    }

    public void onContentScrollStarted() {
        if (this.n != null) {
            this.n.cancel();
            this.n = null;
        }
    }

    public boolean collapseActionView() {
        if (this.d == null || !this.d.hasExpandedActionView()) {
            return false;
        }
        this.d.collapseActionView();
        return true;
    }

    public void setCustomView(View view) {
        this.d.setCustomView(view);
    }

    public void setCustomView(View view, LayoutParams layoutParams) {
        view.setLayoutParams(layoutParams);
        this.d.setCustomView(view);
    }

    public void setListNavigationCallbacks(SpinnerAdapter spinnerAdapter, OnNavigationListener onNavigationListener) {
        this.d.setDropdownParams(spinnerAdapter, new NavItemSelectedListener(onNavigationListener));
    }

    public int getSelectedNavigationIndex() {
        int i2 = -1;
        switch (this.d.getNavigationMode()) {
            case 1:
                return this.d.getDropdownSelectedPosition();
            case 2:
                if (this.z != null) {
                    i2 = this.z.getPosition();
                }
                return i2;
            default:
                return -1;
        }
    }

    public int getNavigationItemCount() {
        switch (this.d.getNavigationMode()) {
            case 1:
                return this.d.getDropdownItemCount();
            case 2:
                return this.y.size();
            default:
                return 0;
        }
    }

    public int getTabCount() {
        return this.y.size();
    }

    public void setNavigationMode(int i2) {
        int navigationMode = this.d.getNavigationMode();
        if (navigationMode == 2) {
            this.A = getSelectedNavigationIndex();
            selectTab(null);
            this.g.setVisibility(8);
        }
        if (!(navigationMode == i2 || this.E || this.b == null)) {
            ViewCompat.requestApplyInsets(this.b);
        }
        this.d.setNavigationMode(i2);
        boolean z2 = false;
        if (i2 == 2) {
            c();
            this.g.setVisibility(0);
            if (this.A != -1) {
                setSelectedNavigationItem(this.A);
                this.A = -1;
            }
        }
        this.d.setCollapsible(i2 == 2 && !this.E);
        ActionBarOverlayLayout actionBarOverlayLayout = this.b;
        if (i2 == 2 && !this.E) {
            z2 = true;
        }
        actionBarOverlayLayout.setHasNonEmbeddedTabs(z2);
    }

    public Tab getTabAt(int i2) {
        return (Tab) this.y.get(i2);
    }

    public void setIcon(int i2) {
        this.d.setIcon(i2);
    }

    public void setIcon(Drawable drawable) {
        this.d.setIcon(drawable);
    }

    public boolean hasIcon() {
        return this.d.hasIcon();
    }

    public void setLogo(int i2) {
        this.d.setLogo(i2);
    }

    public void setLogo(Drawable drawable) {
        this.d.setLogo(drawable);
    }

    public boolean hasLogo() {
        return this.d.hasLogo();
    }

    public void setDefaultDisplayHomeAsUpEnabled(boolean z2) {
        if (!this.B) {
            setDisplayHomeAsUpEnabled(z2);
        }
    }

    public boolean onKeyShortcut(int i2, KeyEvent keyEvent) {
        if (this.h == null) {
            return false;
        }
        Menu menu = this.h.getMenu();
        if (menu == null) {
            return false;
        }
        boolean z2 = true;
        if (KeyCharacterMap.load(keyEvent != null ? keyEvent.getDeviceId() : -1).getKeyboardType() == 1) {
            z2 = false;
        }
        menu.setQwertyMode(z2);
        return menu.performShortcut(i2, keyEvent, 0);
    }
}
