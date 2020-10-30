package android.support.v7.app;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.ActionBar.LayoutParams;
import android.support.v7.app.ActionBar.OnMenuVisibilityListener;
import android.support.v7.app.ActionBar.OnNavigationListener;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.view.WindowCallbackWrapper;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuPresenter;
import android.support.v7.widget.DecorToolbar;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.Toolbar.OnMenuItemClickListener;
import android.support.v7.widget.ToolbarWidgetWrapper;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window.Callback;
import android.widget.SpinnerAdapter;
import java.util.ArrayList;

class ToolbarActionBar extends ActionBar {
    DecorToolbar a;
    boolean b;
    Callback c;
    private boolean d;
    private boolean e;
    private ArrayList<OnMenuVisibilityListener> f = new ArrayList<>();
    private final Runnable g = new Runnable() {
        public void run() {
            ToolbarActionBar.this.c();
        }
    };
    private final OnMenuItemClickListener h = new OnMenuItemClickListener() {
        public boolean onMenuItemClick(MenuItem menuItem) {
            return ToolbarActionBar.this.c.onMenuItemSelected(0, menuItem);
        }
    };

    final class ActionMenuPresenterCallback implements MenuPresenter.Callback {
        private boolean b;

        ActionMenuPresenterCallback() {
        }

        public boolean onOpenSubMenu(MenuBuilder menuBuilder) {
            if (ToolbarActionBar.this.c == null) {
                return false;
            }
            ToolbarActionBar.this.c.onMenuOpened(108, menuBuilder);
            return true;
        }

        public void onCloseMenu(MenuBuilder menuBuilder, boolean z) {
            if (!this.b) {
                this.b = true;
                ToolbarActionBar.this.a.dismissPopupMenus();
                if (ToolbarActionBar.this.c != null) {
                    ToolbarActionBar.this.c.onPanelClosed(108, menuBuilder);
                }
                this.b = false;
            }
        }
    }

    final class MenuBuilderCallback implements MenuBuilder.Callback {
        public boolean onMenuItemSelected(MenuBuilder menuBuilder, MenuItem menuItem) {
            return false;
        }

        MenuBuilderCallback() {
        }

        public void onMenuModeChange(MenuBuilder menuBuilder) {
            if (ToolbarActionBar.this.c == null) {
                return;
            }
            if (ToolbarActionBar.this.a.isOverflowMenuShowing()) {
                ToolbarActionBar.this.c.onPanelClosed(108, menuBuilder);
            } else if (ToolbarActionBar.this.c.onPreparePanel(0, null, menuBuilder)) {
                ToolbarActionBar.this.c.onMenuOpened(108, menuBuilder);
            }
        }
    }

    class ToolbarCallbackWrapper extends WindowCallbackWrapper {
        public ToolbarCallbackWrapper(Callback callback) {
            super(callback);
        }

        public boolean onPreparePanel(int i, View view, Menu menu) {
            boolean onPreparePanel = super.onPreparePanel(i, view, menu);
            if (onPreparePanel && !ToolbarActionBar.this.b) {
                ToolbarActionBar.this.a.setMenuPrepared();
                ToolbarActionBar.this.b = true;
            }
            return onPreparePanel;
        }

        public View onCreatePanelView(int i) {
            if (i == 0) {
                return new View(ToolbarActionBar.this.a.getContext());
            }
            return super.onCreatePanelView(i);
        }
    }

    public int getNavigationItemCount() {
        return 0;
    }

    public int getNavigationMode() {
        return 0;
    }

    public int getSelectedNavigationIndex() {
        return -1;
    }

    public int getTabCount() {
        return 0;
    }

    public void setDefaultDisplayHomeAsUpEnabled(boolean z) {
    }

    public void setHomeButtonEnabled(boolean z) {
    }

    public void setShowHideAnimationEnabled(boolean z) {
    }

    public void setSplitBackgroundDrawable(Drawable drawable) {
    }

    public void setStackedBackgroundDrawable(Drawable drawable) {
    }

    ToolbarActionBar(Toolbar toolbar, CharSequence charSequence, Callback callback) {
        this.a = new ToolbarWidgetWrapper(toolbar, false);
        this.c = new ToolbarCallbackWrapper(callback);
        this.a.setWindowCallback(this.c);
        toolbar.setOnMenuItemClickListener(this.h);
        this.a.setWindowTitle(charSequence);
    }

    public Callback b() {
        return this.c;
    }

    public void setCustomView(View view) {
        setCustomView(view, new LayoutParams(-2, -2));
    }

    public void setCustomView(View view, LayoutParams layoutParams) {
        if (view != null) {
            view.setLayoutParams(layoutParams);
        }
        this.a.setCustomView(view);
    }

    public void setCustomView(int i) {
        setCustomView(LayoutInflater.from(this.a.getContext()).inflate(i, this.a.getViewGroup(), false));
    }

    public void setIcon(int i) {
        this.a.setIcon(i);
    }

    public void setIcon(Drawable drawable) {
        this.a.setIcon(drawable);
    }

    public void setLogo(int i) {
        this.a.setLogo(i);
    }

    public void setLogo(Drawable drawable) {
        this.a.setLogo(drawable);
    }

    public void setElevation(float f2) {
        ViewCompat.setElevation(this.a.getViewGroup(), f2);
    }

    public float getElevation() {
        return ViewCompat.getElevation(this.a.getViewGroup());
    }

    public Context getThemedContext() {
        return this.a.getContext();
    }

    public boolean isTitleTruncated() {
        return super.isTitleTruncated();
    }

    public void setHomeAsUpIndicator(Drawable drawable) {
        this.a.setNavigationIcon(drawable);
    }

    public void setHomeAsUpIndicator(int i) {
        this.a.setNavigationIcon(i);
    }

    public void setHomeActionContentDescription(CharSequence charSequence) {
        this.a.setNavigationContentDescription(charSequence);
    }

    public void setHomeActionContentDescription(int i) {
        this.a.setNavigationContentDescription(i);
    }

    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
    }

    public void setListNavigationCallbacks(SpinnerAdapter spinnerAdapter, OnNavigationListener onNavigationListener) {
        this.a.setDropdownParams(spinnerAdapter, new NavItemSelectedListener(onNavigationListener));
    }

    public void setSelectedNavigationItem(int i) {
        if (this.a.getNavigationMode() != 1) {
            throw new IllegalStateException("setSelectedNavigationIndex not valid for current navigation mode");
        }
        this.a.setDropdownSelectedPosition(i);
    }

    public void setTitle(CharSequence charSequence) {
        this.a.setTitle(charSequence);
    }

    public void setTitle(int i) {
        this.a.setTitle(i != 0 ? this.a.getContext().getText(i) : null);
    }

    public void setWindowTitle(CharSequence charSequence) {
        this.a.setWindowTitle(charSequence);
    }

    public boolean requestFocus() {
        ViewGroup viewGroup = this.a.getViewGroup();
        if (viewGroup == null || viewGroup.hasFocus()) {
            return false;
        }
        viewGroup.requestFocus();
        return true;
    }

    public void setSubtitle(CharSequence charSequence) {
        this.a.setSubtitle(charSequence);
    }

    public void setSubtitle(int i) {
        this.a.setSubtitle(i != 0 ? this.a.getContext().getText(i) : null);
    }

    @SuppressLint({"WrongConstant"})
    public void setDisplayOptions(int i) {
        setDisplayOptions(i, -1);
    }

    public void setDisplayOptions(int i, int i2) {
        this.a.setDisplayOptions((i & i2) | ((i2 ^ -1) & this.a.getDisplayOptions()));
    }

    public void setDisplayUseLogoEnabled(boolean z) {
        setDisplayOptions(z ? 1 : 0, 1);
    }

    public void setDisplayShowHomeEnabled(boolean z) {
        setDisplayOptions(z ? 2 : 0, 2);
    }

    public void setDisplayHomeAsUpEnabled(boolean z) {
        setDisplayOptions(z ? 4 : 0, 4);
    }

    public void setDisplayShowTitleEnabled(boolean z) {
        setDisplayOptions(z ? 8 : 0, 8);
    }

    public void setDisplayShowCustomEnabled(boolean z) {
        setDisplayOptions(z ? 16 : 0, 16);
    }

    public void setBackgroundDrawable(@Nullable Drawable drawable) {
        this.a.setBackgroundDrawable(drawable);
    }

    public View getCustomView() {
        return this.a.getCustomView();
    }

    public CharSequence getTitle() {
        return this.a.getTitle();
    }

    public CharSequence getSubtitle() {
        return this.a.getSubtitle();
    }

    public void setNavigationMode(int i) {
        if (i == 2) {
            throw new IllegalArgumentException("Tabs not supported in this configuration");
        }
        this.a.setNavigationMode(i);
    }

    public int getDisplayOptions() {
        return this.a.getDisplayOptions();
    }

    public Tab newTab() {
        throw new UnsupportedOperationException("Tabs are not supported in toolbar action bars");
    }

    public void addTab(Tab tab) {
        throw new UnsupportedOperationException("Tabs are not supported in toolbar action bars");
    }

    public void addTab(Tab tab, boolean z) {
        throw new UnsupportedOperationException("Tabs are not supported in toolbar action bars");
    }

    public void addTab(Tab tab, int i) {
        throw new UnsupportedOperationException("Tabs are not supported in toolbar action bars");
    }

    public void addTab(Tab tab, int i, boolean z) {
        throw new UnsupportedOperationException("Tabs are not supported in toolbar action bars");
    }

    public void removeTab(Tab tab) {
        throw new UnsupportedOperationException("Tabs are not supported in toolbar action bars");
    }

    public void removeTabAt(int i) {
        throw new UnsupportedOperationException("Tabs are not supported in toolbar action bars");
    }

    public void removeAllTabs() {
        throw new UnsupportedOperationException("Tabs are not supported in toolbar action bars");
    }

    public void selectTab(Tab tab) {
        throw new UnsupportedOperationException("Tabs are not supported in toolbar action bars");
    }

    public Tab getSelectedTab() {
        throw new UnsupportedOperationException("Tabs are not supported in toolbar action bars");
    }

    public Tab getTabAt(int i) {
        throw new UnsupportedOperationException("Tabs are not supported in toolbar action bars");
    }

    public int getHeight() {
        return this.a.getHeight();
    }

    public void show() {
        this.a.setVisibility(0);
    }

    public void hide() {
        this.a.setVisibility(8);
    }

    public boolean isShowing() {
        return this.a.getVisibility() == 0;
    }

    public boolean openOptionsMenu() {
        return this.a.showOverflowMenu();
    }

    public boolean closeOptionsMenu() {
        return this.a.hideOverflowMenu();
    }

    public boolean invalidateOptionsMenu() {
        this.a.getViewGroup().removeCallbacks(this.g);
        ViewCompat.postOnAnimation(this.a.getViewGroup(), this.g);
        return true;
    }

    public boolean collapseActionView() {
        if (!this.a.hasExpandedActionView()) {
            return false;
        }
        this.a.collapseActionView();
        return true;
    }

    /* JADX INFO: finally extract failed */
    /* access modifiers changed from: 0000 */
    public void c() {
        Menu d2 = d();
        MenuBuilder menuBuilder = d2 instanceof MenuBuilder ? (MenuBuilder) d2 : null;
        if (menuBuilder != null) {
            menuBuilder.stopDispatchingItemsChanged();
        }
        try {
            d2.clear();
            if (!this.c.onCreatePanelMenu(0, d2) || !this.c.onPreparePanel(0, null, d2)) {
                d2.clear();
            }
            if (menuBuilder != null) {
                menuBuilder.startDispatchingItemsChanged();
            }
        } catch (Throwable th) {
            if (menuBuilder != null) {
                menuBuilder.startDispatchingItemsChanged();
            }
            throw th;
        }
    }

    public boolean onMenuKeyEvent(KeyEvent keyEvent) {
        if (keyEvent.getAction() == 1) {
            openOptionsMenu();
        }
        return true;
    }

    public boolean onKeyShortcut(int i, KeyEvent keyEvent) {
        Menu d2 = d();
        if (d2 == null) {
            return false;
        }
        boolean z = true;
        if (KeyCharacterMap.load(keyEvent != null ? keyEvent.getDeviceId() : -1).getKeyboardType() == 1) {
            z = false;
        }
        d2.setQwertyMode(z);
        return d2.performShortcut(i, keyEvent, 0);
    }

    /* access modifiers changed from: 0000 */
    public void a() {
        this.a.getViewGroup().removeCallbacks(this.g);
    }

    public void addOnMenuVisibilityListener(OnMenuVisibilityListener onMenuVisibilityListener) {
        this.f.add(onMenuVisibilityListener);
    }

    public void removeOnMenuVisibilityListener(OnMenuVisibilityListener onMenuVisibilityListener) {
        this.f.remove(onMenuVisibilityListener);
    }

    public void dispatchMenuVisibilityChanged(boolean z) {
        if (z != this.e) {
            this.e = z;
            int size = this.f.size();
            for (int i = 0; i < size; i++) {
                ((OnMenuVisibilityListener) this.f.get(i)).onMenuVisibilityChanged(z);
            }
        }
    }

    private Menu d() {
        if (!this.d) {
            this.a.setMenuCallbacks(new ActionMenuPresenterCallback(), new MenuBuilderCallback());
            this.d = true;
        }
        return this.a.getMenu();
    }
}
