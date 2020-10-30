package android.support.design.internal;

import android.animation.TimeInterpolator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.design.R;
import android.support.transition.AutoTransition;
import android.support.transition.TransitionManager;
import android.support.transition.TransitionSet;
import android.support.v4.util.Pools.Pool;
import android.support.v4.util.Pools.SynchronizedPool;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuItemImpl;
import android.support.v7.view.menu.MenuView;
import android.util.AttributeSet;
import android.view.MenuItem;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

@RestrictTo({Scope.LIBRARY_GROUP})
public class BottomNavigationMenuView extends ViewGroup implements MenuView {
    private final TransitionSet a;
    private final int b;
    private final int c;
    private final int d;
    private final int e;
    private final OnClickListener f;
    private final Pool<BottomNavigationItemView> g;
    private boolean h;
    private BottomNavigationItemView[] i;
    private int j;
    private int k;
    private ColorStateList l;
    private ColorStateList m;
    private int n;
    private int[] o;
    /* access modifiers changed from: private */
    public BottomNavigationPresenter p;
    /* access modifiers changed from: private */
    public MenuBuilder q;

    public int getWindowAnimations() {
        return 0;
    }

    public BottomNavigationMenuView(Context context) {
        this(context, null);
    }

    public BottomNavigationMenuView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.g = new SynchronizedPool(5);
        this.h = true;
        this.j = 0;
        this.k = 0;
        Resources resources = getResources();
        this.b = resources.getDimensionPixelSize(R.dimen.design_bottom_navigation_item_max_width);
        this.c = resources.getDimensionPixelSize(R.dimen.design_bottom_navigation_item_min_width);
        this.d = resources.getDimensionPixelSize(R.dimen.design_bottom_navigation_active_item_max_width);
        this.e = resources.getDimensionPixelSize(R.dimen.design_bottom_navigation_height);
        this.a = new AutoTransition();
        this.a.setOrdering(0);
        this.a.setDuration(115);
        this.a.setInterpolator((TimeInterpolator) new FastOutSlowInInterpolator());
        this.a.addTransition(new TextScale());
        this.f = new OnClickListener() {
            public void onClick(View view) {
                MenuItemImpl itemData = ((BottomNavigationItemView) view).getItemData();
                if (!BottomNavigationMenuView.this.q.performItemAction(itemData, BottomNavigationMenuView.this.p, 0)) {
                    itemData.setChecked(true);
                }
            }
        };
        this.o = new int[5];
    }

    public void initialize(MenuBuilder menuBuilder) {
        this.q = menuBuilder;
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i2, int i3) {
        int size = MeasureSpec.getSize(i2);
        int childCount = getChildCount();
        int makeMeasureSpec = MeasureSpec.makeMeasureSpec(this.e, 1073741824);
        if (this.h) {
            int i4 = childCount - 1;
            int min = Math.min(size - (this.c * i4), this.d);
            int i5 = size - min;
            int min2 = Math.min(i5 / i4, this.b);
            int i6 = i5 - (i4 * min2);
            int i7 = 0;
            while (i7 < childCount) {
                this.o[i7] = i7 == this.k ? min : min2;
                if (i6 > 0) {
                    int[] iArr = this.o;
                    iArr[i7] = iArr[i7] + 1;
                    i6--;
                }
                i7++;
            }
        } else {
            int min3 = Math.min(size / (childCount == 0 ? 1 : childCount), this.d);
            int i8 = size - (min3 * childCount);
            for (int i9 = 0; i9 < childCount; i9++) {
                this.o[i9] = min3;
                if (i8 > 0) {
                    int[] iArr2 = this.o;
                    iArr2[i9] = iArr2[i9] + 1;
                    i8--;
                }
            }
        }
        int i10 = 0;
        for (int i11 = 0; i11 < childCount; i11++) {
            View childAt = getChildAt(i11);
            if (childAt.getVisibility() != 8) {
                childAt.measure(MeasureSpec.makeMeasureSpec(this.o[i11], 1073741824), makeMeasureSpec);
                childAt.getLayoutParams().width = childAt.getMeasuredWidth();
                i10 += childAt.getMeasuredWidth();
            }
        }
        setMeasuredDimension(View.resolveSizeAndState(i10, MeasureSpec.makeMeasureSpec(i10, 1073741824), 0), View.resolveSizeAndState(this.e, makeMeasureSpec, 0));
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i2, int i3, int i4, int i5) {
        int childCount = getChildCount();
        int i6 = i4 - i2;
        int i7 = i5 - i3;
        int i8 = 0;
        for (int i9 = 0; i9 < childCount; i9++) {
            View childAt = getChildAt(i9);
            if (childAt.getVisibility() != 8) {
                if (ViewCompat.getLayoutDirection(this) == 1) {
                    int i10 = i6 - i8;
                    childAt.layout(i10 - childAt.getMeasuredWidth(), 0, i10, i7);
                } else {
                    childAt.layout(i8, 0, childAt.getMeasuredWidth() + i8, i7);
                }
                i8 += childAt.getMeasuredWidth();
            }
        }
    }

    public void setIconTintList(ColorStateList colorStateList) {
        this.l = colorStateList;
        if (this.i != null) {
            for (BottomNavigationItemView iconTintList : this.i) {
                iconTintList.setIconTintList(colorStateList);
            }
        }
    }

    @Nullable
    public ColorStateList getIconTintList() {
        return this.l;
    }

    public void setItemTextColor(ColorStateList colorStateList) {
        this.m = colorStateList;
        if (this.i != null) {
            for (BottomNavigationItemView textColor : this.i) {
                textColor.setTextColor(colorStateList);
            }
        }
    }

    public ColorStateList getItemTextColor() {
        return this.m;
    }

    public void setItemBackgroundRes(int i2) {
        this.n = i2;
        if (this.i != null) {
            for (BottomNavigationItemView itemBackground : this.i) {
                itemBackground.setItemBackground(i2);
            }
        }
    }

    public int getItemBackgroundRes() {
        return this.n;
    }

    public void setPresenter(BottomNavigationPresenter bottomNavigationPresenter) {
        this.p = bottomNavigationPresenter;
    }

    public void buildMenuView() {
        removeAllViews();
        if (this.i != null) {
            for (BottomNavigationItemView release : this.i) {
                this.g.release(release);
            }
        }
        if (this.q.size() == 0) {
            this.j = 0;
            this.k = 0;
            this.i = null;
            return;
        }
        this.i = new BottomNavigationItemView[this.q.size()];
        this.h = this.q.size() > 3;
        for (int i2 = 0; i2 < this.q.size(); i2++) {
            this.p.setUpdateSuspended(true);
            this.q.getItem(i2).setCheckable(true);
            this.p.setUpdateSuspended(false);
            BottomNavigationItemView newItem = getNewItem();
            this.i[i2] = newItem;
            newItem.setIconTintList(this.l);
            newItem.setTextColor(this.m);
            newItem.setItemBackground(this.n);
            newItem.setShiftingMode(this.h);
            newItem.initialize((MenuItemImpl) this.q.getItem(i2), 0);
            newItem.setItemPosition(i2);
            newItem.setOnClickListener(this.f);
            addView(newItem);
        }
        this.k = Math.min(this.q.size() - 1, this.k);
        this.q.getItem(this.k).setChecked(true);
    }

    public void updateMenuView() {
        int size = this.q.size();
        if (size != this.i.length) {
            buildMenuView();
            return;
        }
        int i2 = this.j;
        for (int i3 = 0; i3 < size; i3++) {
            MenuItem item = this.q.getItem(i3);
            if (item.isChecked()) {
                this.j = item.getItemId();
                this.k = i3;
            }
        }
        if (i2 != this.j) {
            TransitionManager.beginDelayedTransition(this, this.a);
        }
        for (int i4 = 0; i4 < size; i4++) {
            this.p.setUpdateSuspended(true);
            this.i[i4].initialize((MenuItemImpl) this.q.getItem(i4), 0);
            this.p.setUpdateSuspended(false);
        }
    }

    private BottomNavigationItemView getNewItem() {
        BottomNavigationItemView bottomNavigationItemView = (BottomNavigationItemView) this.g.acquire();
        return bottomNavigationItemView == null ? new BottomNavigationItemView(getContext()) : bottomNavigationItemView;
    }

    public int getSelectedItemId() {
        return this.j;
    }

    /* access modifiers changed from: 0000 */
    public void a(int i2) {
        int size = this.q.size();
        for (int i3 = 0; i3 < size; i3++) {
            MenuItem item = this.q.getItem(i3);
            if (i2 == item.getItemId()) {
                this.j = i2;
                this.k = i3;
                item.setChecked(true);
                return;
            }
        }
    }
}
