package android.support.v7.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Parcelable;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.support.v4.view.ViewPropertyAnimatorListenerAdapter;
import android.support.v7.appcompat.R;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.view.menu.ActionMenuItem;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuPresenter;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window.Callback;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

@RestrictTo({Scope.LIBRARY_GROUP})
public class ToolbarWidgetWrapper implements DecorToolbar {
    Toolbar a;
    CharSequence b;
    Callback c;
    boolean d;
    private int e;
    private View f;
    private Spinner g;
    private View h;
    private Drawable i;
    private Drawable j;
    private Drawable k;
    private boolean l;
    private CharSequence m;
    private CharSequence n;
    private ActionMenuPresenter o;
    private int p;
    private int q;
    private Drawable r;

    public void setHomeButtonEnabled(boolean z) {
    }

    public ToolbarWidgetWrapper(Toolbar toolbar, boolean z) {
        this(toolbar, z, R.string.abc_action_bar_up_description, R.drawable.abc_ic_ab_back_material);
    }

    public ToolbarWidgetWrapper(Toolbar toolbar, boolean z, int i2, int i3) {
        this.p = 0;
        this.q = 0;
        this.a = toolbar;
        this.b = toolbar.getTitle();
        this.m = toolbar.getSubtitle();
        this.l = this.b != null;
        this.k = toolbar.getNavigationIcon();
        TintTypedArray obtainStyledAttributes = TintTypedArray.obtainStyledAttributes(toolbar.getContext(), null, R.styleable.ActionBar, R.attr.actionBarStyle, 0);
        this.r = obtainStyledAttributes.getDrawable(R.styleable.ActionBar_homeAsUpIndicator);
        if (z) {
            CharSequence text = obtainStyledAttributes.getText(R.styleable.ActionBar_title);
            if (!TextUtils.isEmpty(text)) {
                setTitle(text);
            }
            CharSequence text2 = obtainStyledAttributes.getText(R.styleable.ActionBar_subtitle);
            if (!TextUtils.isEmpty(text2)) {
                setSubtitle(text2);
            }
            Drawable drawable = obtainStyledAttributes.getDrawable(R.styleable.ActionBar_logo);
            if (drawable != null) {
                setLogo(drawable);
            }
            Drawable drawable2 = obtainStyledAttributes.getDrawable(R.styleable.ActionBar_icon);
            if (drawable2 != null) {
                setIcon(drawable2);
            }
            if (this.k == null && this.r != null) {
                setNavigationIcon(this.r);
            }
            setDisplayOptions(obtainStyledAttributes.getInt(R.styleable.ActionBar_displayOptions, 0));
            int resourceId = obtainStyledAttributes.getResourceId(R.styleable.ActionBar_customNavigationLayout, 0);
            if (resourceId != 0) {
                setCustomView(LayoutInflater.from(this.a.getContext()).inflate(resourceId, this.a, false));
                setDisplayOptions(this.e | 16);
            }
            int layoutDimension = obtainStyledAttributes.getLayoutDimension(R.styleable.ActionBar_height, 0);
            if (layoutDimension > 0) {
                LayoutParams layoutParams = this.a.getLayoutParams();
                layoutParams.height = layoutDimension;
                this.a.setLayoutParams(layoutParams);
            }
            int dimensionPixelOffset = obtainStyledAttributes.getDimensionPixelOffset(R.styleable.ActionBar_contentInsetStart, -1);
            int dimensionPixelOffset2 = obtainStyledAttributes.getDimensionPixelOffset(R.styleable.ActionBar_contentInsetEnd, -1);
            if (dimensionPixelOffset >= 0 || dimensionPixelOffset2 >= 0) {
                this.a.setContentInsetsRelative(Math.max(dimensionPixelOffset, 0), Math.max(dimensionPixelOffset2, 0));
            }
            int resourceId2 = obtainStyledAttributes.getResourceId(R.styleable.ActionBar_titleTextStyle, 0);
            if (resourceId2 != 0) {
                this.a.setTitleTextAppearance(this.a.getContext(), resourceId2);
            }
            int resourceId3 = obtainStyledAttributes.getResourceId(R.styleable.ActionBar_subtitleTextStyle, 0);
            if (resourceId3 != 0) {
                this.a.setSubtitleTextAppearance(this.a.getContext(), resourceId3);
            }
            int resourceId4 = obtainStyledAttributes.getResourceId(R.styleable.ActionBar_popupTheme, 0);
            if (resourceId4 != 0) {
                this.a.setPopupTheme(resourceId4);
            }
        } else {
            this.e = a();
        }
        obtainStyledAttributes.recycle();
        setDefaultNavigationContentDescription(i2);
        this.n = this.a.getNavigationContentDescription();
        this.a.setNavigationOnClickListener(new OnClickListener() {
            final ActionMenuItem a;

            {
                ActionMenuItem actionMenuItem = new ActionMenuItem(ToolbarWidgetWrapper.this.a.getContext(), 0, 16908332, 0, 0, ToolbarWidgetWrapper.this.b);
                this.a = actionMenuItem;
            }

            public void onClick(View view) {
                if (ToolbarWidgetWrapper.this.c != null && ToolbarWidgetWrapper.this.d) {
                    ToolbarWidgetWrapper.this.c.onMenuItemSelected(0, this.a);
                }
            }
        });
    }

    public void setDefaultNavigationContentDescription(int i2) {
        if (i2 != this.q) {
            this.q = i2;
            if (TextUtils.isEmpty(this.a.getNavigationContentDescription())) {
                setNavigationContentDescription(this.q);
            }
        }
    }

    private int a() {
        if (this.a.getNavigationIcon() == null) {
            return 11;
        }
        this.r = this.a.getNavigationIcon();
        return 15;
    }

    public ViewGroup getViewGroup() {
        return this.a;
    }

    public Context getContext() {
        return this.a.getContext();
    }

    public boolean hasExpandedActionView() {
        return this.a.hasExpandedActionView();
    }

    public void collapseActionView() {
        this.a.collapseActionView();
    }

    public void setWindowCallback(Callback callback) {
        this.c = callback;
    }

    public void setWindowTitle(CharSequence charSequence) {
        if (!this.l) {
            a(charSequence);
        }
    }

    public CharSequence getTitle() {
        return this.a.getTitle();
    }

    public void setTitle(CharSequence charSequence) {
        this.l = true;
        a(charSequence);
    }

    private void a(CharSequence charSequence) {
        this.b = charSequence;
        if ((this.e & 8) != 0) {
            this.a.setTitle(charSequence);
        }
    }

    public CharSequence getSubtitle() {
        return this.a.getSubtitle();
    }

    public void setSubtitle(CharSequence charSequence) {
        this.m = charSequence;
        if ((this.e & 8) != 0) {
            this.a.setSubtitle(charSequence);
        }
    }

    public void initProgress() {
        Log.i("ToolbarWidgetWrapper", "Progress display unsupported");
    }

    public void initIndeterminateProgress() {
        Log.i("ToolbarWidgetWrapper", "Progress display unsupported");
    }

    public boolean hasIcon() {
        return this.i != null;
    }

    public boolean hasLogo() {
        return this.j != null;
    }

    public void setIcon(int i2) {
        setIcon(i2 != 0 ? AppCompatResources.getDrawable(getContext(), i2) : null);
    }

    public void setIcon(Drawable drawable) {
        this.i = drawable;
        b();
    }

    public void setLogo(int i2) {
        setLogo(i2 != 0 ? AppCompatResources.getDrawable(getContext(), i2) : null);
    }

    public void setLogo(Drawable drawable) {
        this.j = drawable;
        b();
    }

    private void b() {
        Drawable drawable = (this.e & 2) != 0 ? (this.e & 1) != 0 ? this.j != null ? this.j : this.i : this.i : null;
        this.a.setLogo(drawable);
    }

    public boolean canShowOverflowMenu() {
        return this.a.canShowOverflowMenu();
    }

    public boolean isOverflowMenuShowing() {
        return this.a.isOverflowMenuShowing();
    }

    public boolean isOverflowMenuShowPending() {
        return this.a.isOverflowMenuShowPending();
    }

    public boolean showOverflowMenu() {
        return this.a.showOverflowMenu();
    }

    public boolean hideOverflowMenu() {
        return this.a.hideOverflowMenu();
    }

    public void setMenuPrepared() {
        this.d = true;
    }

    public void setMenu(Menu menu, MenuPresenter.Callback callback) {
        if (this.o == null) {
            this.o = new ActionMenuPresenter(this.a.getContext());
            this.o.setId(R.id.action_menu_presenter);
        }
        this.o.setCallback(callback);
        this.a.setMenu((MenuBuilder) menu, this.o);
    }

    public void dismissPopupMenus() {
        this.a.dismissPopupMenus();
    }

    public int getDisplayOptions() {
        return this.e;
    }

    public void setDisplayOptions(int i2) {
        int i3 = this.e ^ i2;
        this.e = i2;
        if (i3 != 0) {
            if ((i3 & 4) != 0) {
                if ((i2 & 4) != 0) {
                    e();
                }
                d();
            }
            if ((i3 & 3) != 0) {
                b();
            }
            if ((i3 & 8) != 0) {
                if ((i2 & 8) != 0) {
                    this.a.setTitle(this.b);
                    this.a.setSubtitle(this.m);
                } else {
                    this.a.setTitle((CharSequence) null);
                    this.a.setSubtitle((CharSequence) null);
                }
            }
            if ((i3 & 16) != 0 && this.h != null) {
                if ((i2 & 16) != 0) {
                    this.a.addView(this.h);
                } else {
                    this.a.removeView(this.h);
                }
            }
        }
    }

    public void setEmbeddedTabView(ScrollingTabContainerView scrollingTabContainerView) {
        if (this.f != null && this.f.getParent() == this.a) {
            this.a.removeView(this.f);
        }
        this.f = scrollingTabContainerView;
        if (scrollingTabContainerView != null && this.p == 2) {
            this.a.addView(this.f, 0);
            Toolbar.LayoutParams layoutParams = (Toolbar.LayoutParams) this.f.getLayoutParams();
            layoutParams.width = -2;
            layoutParams.height = -2;
            layoutParams.gravity = 8388691;
            scrollingTabContainerView.setAllowCollapse(true);
        }
    }

    public boolean hasEmbeddedTabs() {
        return this.f != null;
    }

    public boolean isTitleTruncated() {
        return this.a.isTitleTruncated();
    }

    public void setCollapsible(boolean z) {
        this.a.setCollapsible(z);
    }

    public int getNavigationMode() {
        return this.p;
    }

    public void setNavigationMode(int i2) {
        int i3 = this.p;
        if (i2 != i3) {
            switch (i3) {
                case 1:
                    if (this.g != null && this.g.getParent() == this.a) {
                        this.a.removeView(this.g);
                        break;
                    }
                case 2:
                    if (this.f != null && this.f.getParent() == this.a) {
                        this.a.removeView(this.f);
                        break;
                    }
            }
            this.p = i2;
            switch (i2) {
                case 0:
                    return;
                case 1:
                    c();
                    this.a.addView(this.g, 0);
                    return;
                case 2:
                    if (this.f != null) {
                        this.a.addView(this.f, 0);
                        Toolbar.LayoutParams layoutParams = (Toolbar.LayoutParams) this.f.getLayoutParams();
                        layoutParams.width = -2;
                        layoutParams.height = -2;
                        layoutParams.gravity = 8388691;
                        return;
                    }
                    return;
                default:
                    StringBuilder sb = new StringBuilder();
                    sb.append("Invalid navigation mode ");
                    sb.append(i2);
                    throw new IllegalArgumentException(sb.toString());
            }
        }
    }

    private void c() {
        if (this.g == null) {
            this.g = new AppCompatSpinner(getContext(), null, R.attr.actionDropDownStyle);
            this.g.setLayoutParams(new Toolbar.LayoutParams(-2, -2, 8388627));
        }
    }

    public void setDropdownParams(SpinnerAdapter spinnerAdapter, OnItemSelectedListener onItemSelectedListener) {
        c();
        this.g.setAdapter(spinnerAdapter);
        this.g.setOnItemSelectedListener(onItemSelectedListener);
    }

    public void setDropdownSelectedPosition(int i2) {
        if (this.g == null) {
            throw new IllegalStateException("Can't set dropdown selected position without an adapter");
        }
        this.g.setSelection(i2);
    }

    public int getDropdownSelectedPosition() {
        if (this.g != null) {
            return this.g.getSelectedItemPosition();
        }
        return 0;
    }

    public int getDropdownItemCount() {
        if (this.g != null) {
            return this.g.getCount();
        }
        return 0;
    }

    public void setCustomView(View view) {
        if (!(this.h == null || (this.e & 16) == 0)) {
            this.a.removeView(this.h);
        }
        this.h = view;
        if (view != null && (this.e & 16) != 0) {
            this.a.addView(this.h);
        }
    }

    public View getCustomView() {
        return this.h;
    }

    public void animateToVisibility(int i2) {
        ViewPropertyAnimatorCompat viewPropertyAnimatorCompat = setupAnimatorToVisibility(i2, 200);
        if (viewPropertyAnimatorCompat != null) {
            viewPropertyAnimatorCompat.start();
        }
    }

    public ViewPropertyAnimatorCompat setupAnimatorToVisibility(final int i2, long j2) {
        return ViewCompat.animate(this.a).alpha(i2 == 0 ? 1.0f : BitmapDescriptorFactory.HUE_RED).setDuration(j2).setListener(new ViewPropertyAnimatorListenerAdapter() {
            private boolean c = false;

            public void onAnimationStart(View view) {
                ToolbarWidgetWrapper.this.a.setVisibility(0);
            }

            public void onAnimationEnd(View view) {
                if (!this.c) {
                    ToolbarWidgetWrapper.this.a.setVisibility(i2);
                }
            }

            public void onAnimationCancel(View view) {
                this.c = true;
            }
        });
    }

    public void setNavigationIcon(Drawable drawable) {
        this.k = drawable;
        d();
    }

    public void setNavigationIcon(int i2) {
        setNavigationIcon(i2 != 0 ? AppCompatResources.getDrawable(getContext(), i2) : null);
    }

    public void setDefaultNavigationIcon(Drawable drawable) {
        if (this.r != drawable) {
            this.r = drawable;
            d();
        }
    }

    private void d() {
        if ((this.e & 4) != 0) {
            this.a.setNavigationIcon(this.k != null ? this.k : this.r);
        } else {
            this.a.setNavigationIcon((Drawable) null);
        }
    }

    public void setNavigationContentDescription(CharSequence charSequence) {
        this.n = charSequence;
        e();
    }

    public void setNavigationContentDescription(int i2) {
        setNavigationContentDescription((CharSequence) i2 == 0 ? null : getContext().getString(i2));
    }

    private void e() {
        if ((this.e & 4) == 0) {
            return;
        }
        if (TextUtils.isEmpty(this.n)) {
            this.a.setNavigationContentDescription(this.q);
        } else {
            this.a.setNavigationContentDescription(this.n);
        }
    }

    public void saveHierarchyState(SparseArray<Parcelable> sparseArray) {
        this.a.saveHierarchyState(sparseArray);
    }

    public void restoreHierarchyState(SparseArray<Parcelable> sparseArray) {
        this.a.restoreHierarchyState(sparseArray);
    }

    public void setBackgroundDrawable(Drawable drawable) {
        ViewCompat.setBackground(this.a, drawable);
    }

    public int getHeight() {
        return this.a.getHeight();
    }

    public void setVisibility(int i2) {
        this.a.setVisibility(i2);
    }

    public int getVisibility() {
        return this.a.getVisibility();
    }

    public void setMenuCallbacks(MenuPresenter.Callback callback, MenuBuilder.Callback callback2) {
        this.a.setMenuCallbacks(callback, callback2);
    }

    public Menu getMenu() {
        return this.a.getMenu();
    }
}
