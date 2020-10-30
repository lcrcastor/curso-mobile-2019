package android.support.design.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.ClassLoaderCreator;
import android.os.Parcelable.Creator;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.R;
import android.support.design.internal.BottomNavigationMenu;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.internal.BottomNavigationPresenter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.AbsSavedState;
import android.support.v4.view.ViewCompat;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.view.SupportMenuInflater;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuBuilder.Callback;
import android.support.v7.widget.TintTypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;

public class BottomNavigationView extends FrameLayout {
    private static final int[] a = {16842912};
    private static final int[] b = {-16842910};
    private final MenuBuilder c;
    private final BottomNavigationMenuView d;
    private final BottomNavigationPresenter e;
    private MenuInflater f;
    /* access modifiers changed from: private */
    public OnNavigationItemSelectedListener g;
    /* access modifiers changed from: private */
    public OnNavigationItemReselectedListener h;

    public interface OnNavigationItemReselectedListener {
        void onNavigationItemReselected(@NonNull MenuItem menuItem);
    }

    public interface OnNavigationItemSelectedListener {
        boolean onNavigationItemSelected(@NonNull MenuItem menuItem);
    }

    static class SavedState extends AbsSavedState {
        public static final Creator<SavedState> CREATOR = new ClassLoaderCreator<SavedState>() {
            /* renamed from: a */
            public SavedState createFromParcel(Parcel parcel, ClassLoader classLoader) {
                return new SavedState(parcel, classLoader);
            }

            /* renamed from: a */
            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel, null);
            }

            /* renamed from: a */
            public SavedState[] newArray(int i) {
                return new SavedState[i];
            }
        };
        Bundle a;

        public SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        public SavedState(Parcel parcel, ClassLoader classLoader) {
            super(parcel, classLoader);
            a(parcel, classLoader);
        }

        public void writeToParcel(@NonNull Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeBundle(this.a);
        }

        private void a(Parcel parcel, ClassLoader classLoader) {
            this.a = parcel.readBundle(classLoader);
        }
    }

    public int getMaxItemCount() {
        return 5;
    }

    public BottomNavigationView(Context context) {
        this(context, null);
    }

    public BottomNavigationView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public BottomNavigationView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.e = new BottomNavigationPresenter();
        ThemeUtils.a(context);
        this.c = new BottomNavigationMenu(context);
        this.d = new BottomNavigationMenuView(context);
        LayoutParams layoutParams = new LayoutParams(-2, -2);
        layoutParams.gravity = 17;
        this.d.setLayoutParams(layoutParams);
        this.e.setBottomNavigationMenuView(this.d);
        this.e.setId(1);
        this.d.setPresenter(this.e);
        this.c.addMenuPresenter(this.e);
        this.e.initForMenu(getContext(), this.c);
        TintTypedArray obtainStyledAttributes = TintTypedArray.obtainStyledAttributes(context, attributeSet, R.styleable.BottomNavigationView, i, R.style.Widget_Design_BottomNavigationView);
        if (obtainStyledAttributes.hasValue(R.styleable.BottomNavigationView_itemIconTint)) {
            this.d.setIconTintList(obtainStyledAttributes.getColorStateList(R.styleable.BottomNavigationView_itemIconTint));
        } else {
            this.d.setIconTintList(a(16842808));
        }
        if (obtainStyledAttributes.hasValue(R.styleable.BottomNavigationView_itemTextColor)) {
            this.d.setItemTextColor(obtainStyledAttributes.getColorStateList(R.styleable.BottomNavigationView_itemTextColor));
        } else {
            this.d.setItemTextColor(a(16842808));
        }
        if (obtainStyledAttributes.hasValue(R.styleable.BottomNavigationView_elevation)) {
            ViewCompat.setElevation(this, (float) obtainStyledAttributes.getDimensionPixelSize(R.styleable.BottomNavigationView_elevation, 0));
        }
        this.d.setItemBackgroundRes(obtainStyledAttributes.getResourceId(R.styleable.BottomNavigationView_itemBackground, 0));
        if (obtainStyledAttributes.hasValue(R.styleable.BottomNavigationView_menu)) {
            inflateMenu(obtainStyledAttributes.getResourceId(R.styleable.BottomNavigationView_menu, 0));
        }
        obtainStyledAttributes.recycle();
        addView(this.d, layoutParams);
        if (VERSION.SDK_INT < 21) {
            a(context);
        }
        this.c.setCallback(new Callback() {
            public void onMenuModeChange(MenuBuilder menuBuilder) {
            }

            public boolean onMenuItemSelected(MenuBuilder menuBuilder, MenuItem menuItem) {
                boolean z = true;
                if (BottomNavigationView.this.h == null || menuItem.getItemId() != BottomNavigationView.this.getSelectedItemId()) {
                    if (BottomNavigationView.this.g == null || BottomNavigationView.this.g.onNavigationItemSelected(menuItem)) {
                        z = false;
                    }
                    return z;
                }
                BottomNavigationView.this.h.onNavigationItemReselected(menuItem);
                return true;
            }
        });
    }

    public void setOnNavigationItemSelectedListener(@Nullable OnNavigationItemSelectedListener onNavigationItemSelectedListener) {
        this.g = onNavigationItemSelectedListener;
    }

    public void setOnNavigationItemReselectedListener(@Nullable OnNavigationItemReselectedListener onNavigationItemReselectedListener) {
        this.h = onNavigationItemReselectedListener;
    }

    @NonNull
    public Menu getMenu() {
        return this.c;
    }

    public void inflateMenu(int i) {
        this.e.setUpdateSuspended(true);
        getMenuInflater().inflate(i, this.c);
        this.e.setUpdateSuspended(false);
        this.e.updateMenuView(true);
    }

    @Nullable
    public ColorStateList getItemIconTintList() {
        return this.d.getIconTintList();
    }

    public void setItemIconTintList(@Nullable ColorStateList colorStateList) {
        this.d.setIconTintList(colorStateList);
    }

    @Nullable
    public ColorStateList getItemTextColor() {
        return this.d.getItemTextColor();
    }

    public void setItemTextColor(@Nullable ColorStateList colorStateList) {
        this.d.setItemTextColor(colorStateList);
    }

    @DrawableRes
    public int getItemBackgroundResource() {
        return this.d.getItemBackgroundRes();
    }

    public void setItemBackgroundResource(@DrawableRes int i) {
        this.d.setItemBackgroundRes(i);
    }

    @IdRes
    public int getSelectedItemId() {
        return this.d.getSelectedItemId();
    }

    public void setSelectedItemId(@IdRes int i) {
        MenuItem findItem = this.c.findItem(i);
        if (findItem != null && !this.c.performItemAction(findItem, this.e, 0)) {
            findItem.setChecked(true);
        }
    }

    private void a(Context context) {
        View view = new View(context);
        view.setBackgroundColor(ContextCompat.getColor(context, R.color.design_bottom_navigation_shadow_color));
        view.setLayoutParams(new LayoutParams(-1, getResources().getDimensionPixelSize(R.dimen.design_bottom_navigation_shadow_height)));
        addView(view);
    }

    private MenuInflater getMenuInflater() {
        if (this.f == null) {
            this.f = new SupportMenuInflater(getContext());
        }
        return this.f;
    }

    private ColorStateList a(int i) {
        TypedValue typedValue = new TypedValue();
        if (!getContext().getTheme().resolveAttribute(i, typedValue, true)) {
            return null;
        }
        ColorStateList colorStateList = AppCompatResources.getColorStateList(getContext(), typedValue.resourceId);
        if (!getContext().getTheme().resolveAttribute(android.support.v7.appcompat.R.attr.colorPrimary, typedValue, true)) {
            return null;
        }
        int i2 = typedValue.data;
        int defaultColor = colorStateList.getDefaultColor();
        return new ColorStateList(new int[][]{b, a, EMPTY_STATE_SET}, new int[]{colorStateList.getColorForState(b, defaultColor), i2, defaultColor});
    }

    /* access modifiers changed from: protected */
    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        savedState.a = new Bundle();
        this.c.savePresenterStates(savedState.a);
        return savedState;
    }

    /* access modifiers changed from: protected */
    public void onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable instanceof SavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.getSuperState());
        this.c.restorePresenterStates(savedState.a);
    }
}
