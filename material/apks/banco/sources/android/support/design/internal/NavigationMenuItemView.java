package android.support.design.internal;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Drawable.ConstantState;
import android.graphics.drawable.StateListDrawable;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.design.R;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.AccessibilityDelegateCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.view.menu.MenuItemImpl;
import android.support.v7.view.menu.MenuView.ItemView;
import android.support.v7.widget.LinearLayoutCompat.LayoutParams;
import android.support.v7.widget.TooltipCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewStub;
import android.widget.CheckedTextView;
import android.widget.FrameLayout;

@RestrictTo({Scope.LIBRARY_GROUP})
public class NavigationMenuItemView extends ForegroundLinearLayout implements ItemView {
    private static final int[] c = {16842912};
    boolean b;
    private final int d;
    private boolean e;
    private final CheckedTextView f;
    private FrameLayout g;
    private MenuItemImpl h;
    private ColorStateList i;
    private boolean j;
    private Drawable k;
    private final AccessibilityDelegateCompat l;

    public boolean prefersCondensedTitle() {
        return false;
    }

    public void setShortcut(boolean z, char c2) {
    }

    public boolean showsIcon() {
        return true;
    }

    public NavigationMenuItemView(Context context) {
        this(context, null);
    }

    public NavigationMenuItemView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public NavigationMenuItemView(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.l = new AccessibilityDelegateCompat() {
            public void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
                super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfoCompat);
                accessibilityNodeInfoCompat.setCheckable(NavigationMenuItemView.this.b);
            }
        };
        setOrientation(0);
        LayoutInflater.from(context).inflate(R.layout.design_navigation_menu_item, this, true);
        this.d = context.getResources().getDimensionPixelSize(R.dimen.design_navigation_icon_size);
        this.f = (CheckedTextView) findViewById(R.id.design_menu_item_text);
        this.f.setDuplicateParentStateEnabled(true);
        ViewCompat.setAccessibilityDelegate(this.f, this.l);
    }

    public void initialize(MenuItemImpl menuItemImpl, int i2) {
        this.h = menuItemImpl;
        setVisibility(menuItemImpl.isVisible() ? 0 : 8);
        if (getBackground() == null) {
            ViewCompat.setBackground(this, c());
        }
        setCheckable(menuItemImpl.isCheckable());
        setChecked(menuItemImpl.isChecked());
        setEnabled(menuItemImpl.isEnabled());
        setTitle(menuItemImpl.getTitle());
        setIcon(menuItemImpl.getIcon());
        setActionView(menuItemImpl.getActionView());
        setContentDescription(menuItemImpl.getContentDescription());
        TooltipCompat.setTooltipText(this, menuItemImpl.getTooltipText());
        b();
    }

    private boolean a() {
        return this.h.getTitle() == null && this.h.getIcon() == null && this.h.getActionView() != null;
    }

    private void b() {
        if (a()) {
            this.f.setVisibility(8);
            if (this.g != null) {
                LayoutParams layoutParams = (LayoutParams) this.g.getLayoutParams();
                layoutParams.width = -1;
                this.g.setLayoutParams(layoutParams);
                return;
            }
            return;
        }
        this.f.setVisibility(0);
        if (this.g != null) {
            LayoutParams layoutParams2 = (LayoutParams) this.g.getLayoutParams();
            layoutParams2.width = -2;
            this.g.setLayoutParams(layoutParams2);
        }
    }

    public void recycle() {
        if (this.g != null) {
            this.g.removeAllViews();
        }
        this.f.setCompoundDrawables(null, null, null, null);
    }

    private void setActionView(View view) {
        if (view != null) {
            if (this.g == null) {
                this.g = (FrameLayout) ((ViewStub) findViewById(R.id.design_menu_item_action_area_stub)).inflate();
            }
            this.g.removeAllViews();
            this.g.addView(view);
        }
    }

    private StateListDrawable c() {
        TypedValue typedValue = new TypedValue();
        if (!getContext().getTheme().resolveAttribute(android.support.v7.appcompat.R.attr.colorControlHighlight, typedValue, true)) {
            return null;
        }
        StateListDrawable stateListDrawable = new StateListDrawable();
        stateListDrawable.addState(c, new ColorDrawable(typedValue.data));
        stateListDrawable.addState(EMPTY_STATE_SET, new ColorDrawable(0));
        return stateListDrawable;
    }

    public MenuItemImpl getItemData() {
        return this.h;
    }

    public void setTitle(CharSequence charSequence) {
        this.f.setText(charSequence);
    }

    public void setCheckable(boolean z) {
        refreshDrawableState();
        if (this.b != z) {
            this.b = z;
            this.l.sendAccessibilityEvent(this.f, 2048);
        }
    }

    public void setChecked(boolean z) {
        refreshDrawableState();
        this.f.setChecked(z);
    }

    public void setIcon(Drawable drawable) {
        if (drawable != null) {
            if (this.j) {
                ConstantState constantState = drawable.getConstantState();
                if (constantState != null) {
                    drawable = constantState.newDrawable();
                }
                drawable = DrawableCompat.wrap(drawable).mutate();
                DrawableCompat.setTintList(drawable, this.i);
            }
            drawable.setBounds(0, 0, this.d, this.d);
        } else if (this.e) {
            if (this.k == null) {
                this.k = ResourcesCompat.getDrawable(getResources(), R.drawable.navigation_empty_icon, getContext().getTheme());
                if (this.k != null) {
                    this.k.setBounds(0, 0, this.d, this.d);
                }
            }
            drawable = this.k;
        }
        TextViewCompat.setCompoundDrawablesRelative(this.f, drawable, null, null, null);
    }

    /* access modifiers changed from: protected */
    public int[] onCreateDrawableState(int i2) {
        int[] onCreateDrawableState = super.onCreateDrawableState(i2 + 1);
        if (this.h != null && this.h.isCheckable() && this.h.isChecked()) {
            mergeDrawableStates(onCreateDrawableState, c);
        }
        return onCreateDrawableState;
    }

    /* access modifiers changed from: 0000 */
    public void setIconTintList(ColorStateList colorStateList) {
        this.i = colorStateList;
        this.j = this.i != null;
        if (this.h != null) {
            setIcon(this.h.getIcon());
        }
    }

    public void setTextAppearance(int i2) {
        TextViewCompat.setTextAppearance(this.f, i2);
    }

    public void setTextColor(ColorStateList colorStateList) {
        this.f.setTextColor(colorStateList);
    }

    public void setNeedsEmptyIcon(boolean z) {
        this.e = z;
    }
}
