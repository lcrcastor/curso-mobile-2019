package android.support.v7.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.ActionProvider;
import android.support.v4.view.ActionProvider.SubUiVisibilityListener;
import android.support.v4.view.GravityCompat;
import android.support.v7.appcompat.R;
import android.support.v7.view.ActionBarPolicy;
import android.support.v7.view.menu.ActionMenuItemView;
import android.support.v7.view.menu.ActionMenuItemView.PopupCallback;
import android.support.v7.view.menu.BaseMenuPresenter;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuItemImpl;
import android.support.v7.view.menu.MenuPopupHelper;
import android.support.v7.view.menu.MenuPresenter.Callback;
import android.support.v7.view.menu.MenuView;
import android.support.v7.view.menu.MenuView.ItemView;
import android.support.v7.view.menu.ShowableListMenu;
import android.support.v7.view.menu.SubMenuBuilder;
import android.support.v7.widget.ActionMenuView.ActionMenuChildView;
import android.util.SparseBooleanArray;
import android.view.MenuItem;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import java.util.ArrayList;

class ActionMenuPresenter extends BaseMenuPresenter implements SubUiVisibilityListener {
    OverflowMenuButton a;
    OverflowPopup b;
    ActionButtonSubmenu c;
    OpenOverflowRunnable d;
    final PopupPresenterCallback e = new PopupPresenterCallback();
    int f;
    private Drawable g;
    private boolean h;
    private boolean i;
    private boolean j;
    private int k;
    private int l;
    private int m;
    private boolean n;
    private boolean o;
    private boolean p;
    private boolean q;
    private int r;
    private final SparseBooleanArray s = new SparseBooleanArray();
    private View t;
    private ActionMenuPopupCallback u;

    class ActionButtonSubmenu extends MenuPopupHelper {
        public ActionButtonSubmenu(Context context, SubMenuBuilder subMenuBuilder, View view) {
            super(context, subMenuBuilder, view, false, R.attr.actionOverflowMenuStyle);
            if (!((MenuItemImpl) subMenuBuilder.getItem()).isActionButton()) {
                setAnchorView(ActionMenuPresenter.this.a == null ? (View) ActionMenuPresenter.this.mMenuView : ActionMenuPresenter.this.a);
            }
            setPresenterCallback(ActionMenuPresenter.this.e);
        }

        /* access modifiers changed from: protected */
        public void onDismiss() {
            ActionMenuPresenter.this.c = null;
            ActionMenuPresenter.this.f = 0;
            super.onDismiss();
        }
    }

    class ActionMenuPopupCallback extends PopupCallback {
        ActionMenuPopupCallback() {
        }

        public ShowableListMenu getPopup() {
            if (ActionMenuPresenter.this.c != null) {
                return ActionMenuPresenter.this.c.getPopup();
            }
            return null;
        }
    }

    class OpenOverflowRunnable implements Runnable {
        private OverflowPopup b;

        public OpenOverflowRunnable(OverflowPopup overflowPopup) {
            this.b = overflowPopup;
        }

        public void run() {
            if (ActionMenuPresenter.this.mMenu != null) {
                ActionMenuPresenter.this.mMenu.changeMenuMode();
            }
            View view = (View) ActionMenuPresenter.this.mMenuView;
            if (!(view == null || view.getWindowToken() == null || !this.b.tryShow())) {
                ActionMenuPresenter.this.b = this.b;
            }
            ActionMenuPresenter.this.d = null;
        }
    }

    class OverflowMenuButton extends AppCompatImageView implements ActionMenuChildView {
        private final float[] b = new float[2];

        public boolean needsDividerAfter() {
            return false;
        }

        public boolean needsDividerBefore() {
            return false;
        }

        public OverflowMenuButton(Context context) {
            super(context, null, R.attr.actionOverflowButtonStyle);
            setClickable(true);
            setFocusable(true);
            setVisibility(0);
            setEnabled(true);
            TooltipCompat.setTooltipText(this, getContentDescription());
            setOnTouchListener(new ForwardingListener(this, ActionMenuPresenter.this) {
                public ShowableListMenu getPopup() {
                    if (ActionMenuPresenter.this.b == null) {
                        return null;
                    }
                    return ActionMenuPresenter.this.b.getPopup();
                }

                public boolean onForwardingStarted() {
                    ActionMenuPresenter.this.b();
                    return true;
                }

                public boolean onForwardingStopped() {
                    if (ActionMenuPresenter.this.d != null) {
                        return false;
                    }
                    ActionMenuPresenter.this.c();
                    return true;
                }
            });
        }

        public boolean performClick() {
            if (super.performClick()) {
                return true;
            }
            playSoundEffect(0);
            ActionMenuPresenter.this.b();
            return true;
        }

        /* access modifiers changed from: protected */
        public boolean setFrame(int i, int i2, int i3, int i4) {
            boolean frame = super.setFrame(i, i2, i3, i4);
            Drawable drawable = getDrawable();
            Drawable background = getBackground();
            if (!(drawable == null || background == null)) {
                int width = getWidth();
                int height = getHeight();
                int max = Math.max(width, height) / 2;
                int paddingLeft = (width + (getPaddingLeft() - getPaddingRight())) / 2;
                int paddingTop = (height + (getPaddingTop() - getPaddingBottom())) / 2;
                DrawableCompat.setHotspotBounds(background, paddingLeft - max, paddingTop - max, paddingLeft + max, paddingTop + max);
            }
            return frame;
        }
    }

    class OverflowPopup extends MenuPopupHelper {
        public OverflowPopup(Context context, MenuBuilder menuBuilder, View view, boolean z) {
            super(context, menuBuilder, view, z, R.attr.actionOverflowMenuStyle);
            setGravity(GravityCompat.END);
            setPresenterCallback(ActionMenuPresenter.this.e);
        }

        /* access modifiers changed from: protected */
        public void onDismiss() {
            if (ActionMenuPresenter.this.mMenu != null) {
                ActionMenuPresenter.this.mMenu.close();
            }
            ActionMenuPresenter.this.b = null;
            super.onDismiss();
        }
    }

    class PopupPresenterCallback implements Callback {
        PopupPresenterCallback() {
        }

        public boolean onOpenSubMenu(MenuBuilder menuBuilder) {
            boolean z = false;
            if (menuBuilder == null) {
                return false;
            }
            ActionMenuPresenter.this.f = ((SubMenuBuilder) menuBuilder).getItem().getItemId();
            Callback callback = ActionMenuPresenter.this.getCallback();
            if (callback != null) {
                z = callback.onOpenSubMenu(menuBuilder);
            }
            return z;
        }

        public void onCloseMenu(MenuBuilder menuBuilder, boolean z) {
            if (menuBuilder instanceof SubMenuBuilder) {
                menuBuilder.getRootMenu().close(false);
            }
            Callback callback = ActionMenuPresenter.this.getCallback();
            if (callback != null) {
                callback.onCloseMenu(menuBuilder, z);
            }
        }
    }

    static class SavedState implements Parcelable {
        public static final Creator<SavedState> CREATOR = new Creator<SavedState>() {
            /* renamed from: a */
            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel);
            }

            /* renamed from: a */
            public SavedState[] newArray(int i) {
                return new SavedState[i];
            }
        };
        public int a;

        public int describeContents() {
            return 0;
        }

        SavedState() {
        }

        SavedState(Parcel parcel) {
            this.a = parcel.readInt();
        }

        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(this.a);
        }
    }

    public ActionMenuPresenter(Context context) {
        super(context, R.layout.abc_action_menu_layout, R.layout.abc_action_menu_item_layout);
    }

    public void initForMenu(@NonNull Context context, @Nullable MenuBuilder menuBuilder) {
        super.initForMenu(context, menuBuilder);
        Resources resources = context.getResources();
        ActionBarPolicy actionBarPolicy = ActionBarPolicy.get(context);
        if (!this.j) {
            this.i = actionBarPolicy.showsOverflowMenuButton();
        }
        if (!this.p) {
            this.k = actionBarPolicy.getEmbeddedMenuWidthLimit();
        }
        if (!this.n) {
            this.m = actionBarPolicy.getMaxActionButtons();
        }
        int i2 = this.k;
        if (this.i) {
            if (this.a == null) {
                this.a = new OverflowMenuButton(this.mSystemContext);
                if (this.h) {
                    this.a.setImageDrawable(this.g);
                    this.g = null;
                    this.h = false;
                }
                int makeMeasureSpec = MeasureSpec.makeMeasureSpec(0, 0);
                this.a.measure(makeMeasureSpec, makeMeasureSpec);
            }
            i2 -= this.a.getMeasuredWidth();
        } else {
            this.a = null;
        }
        this.l = i2;
        this.r = (int) (resources.getDisplayMetrics().density * 56.0f);
        this.t = null;
    }

    public void a(Configuration configuration) {
        if (!this.n) {
            this.m = ActionBarPolicy.get(this.mContext).getMaxActionButtons();
        }
        if (this.mMenu != null) {
            this.mMenu.onItemsChanged(true);
        }
    }

    public void a(boolean z) {
        this.i = z;
        this.j = true;
    }

    public void b(boolean z) {
        this.q = z;
    }

    public void a(Drawable drawable) {
        if (this.a != null) {
            this.a.setImageDrawable(drawable);
            return;
        }
        this.h = true;
        this.g = drawable;
    }

    public Drawable a() {
        if (this.a != null) {
            return this.a.getDrawable();
        }
        if (this.h) {
            return this.g;
        }
        return null;
    }

    public MenuView getMenuView(ViewGroup viewGroup) {
        MenuView menuView = this.mMenuView;
        MenuView menuView2 = super.getMenuView(viewGroup);
        if (menuView != menuView2) {
            ((ActionMenuView) menuView2).setPresenter(this);
        }
        return menuView2;
    }

    public View getItemView(MenuItemImpl menuItemImpl, View view, ViewGroup viewGroup) {
        View actionView = menuItemImpl.getActionView();
        if (actionView == null || menuItemImpl.hasCollapsibleActionView()) {
            actionView = super.getItemView(menuItemImpl, view, viewGroup);
        }
        actionView.setVisibility(menuItemImpl.isActionViewExpanded() ? 8 : 0);
        ActionMenuView actionMenuView = (ActionMenuView) viewGroup;
        LayoutParams layoutParams = actionView.getLayoutParams();
        if (!actionMenuView.checkLayoutParams(layoutParams)) {
            actionView.setLayoutParams(actionMenuView.generateLayoutParams(layoutParams));
        }
        return actionView;
    }

    public void bindItemView(MenuItemImpl menuItemImpl, ItemView itemView) {
        itemView.initialize(menuItemImpl, 0);
        ActionMenuItemView actionMenuItemView = (ActionMenuItemView) itemView;
        actionMenuItemView.setItemInvoker((ActionMenuView) this.mMenuView);
        if (this.u == null) {
            this.u = new ActionMenuPopupCallback();
        }
        actionMenuItemView.setPopupCallback(this.u);
    }

    public boolean shouldIncludeItem(int i2, MenuItemImpl menuItemImpl) {
        return menuItemImpl.isActionButton();
    }

    public void updateMenuView(boolean z) {
        super.updateMenuView(z);
        ((View) this.mMenuView).requestLayout();
        boolean z2 = false;
        if (this.mMenu != null) {
            ArrayList actionItems = this.mMenu.getActionItems();
            int size = actionItems.size();
            for (int i2 = 0; i2 < size; i2++) {
                ActionProvider supportActionProvider = ((MenuItemImpl) actionItems.get(i2)).getSupportActionProvider();
                if (supportActionProvider != null) {
                    supportActionProvider.setSubUiVisibilityListener(this);
                }
            }
        }
        ArrayList nonActionItems = this.mMenu != null ? this.mMenu.getNonActionItems() : null;
        if (this.i && nonActionItems != null) {
            int size2 = nonActionItems.size();
            if (size2 == 1) {
                z2 = !((MenuItemImpl) nonActionItems.get(0)).isActionViewExpanded();
            } else if (size2 > 0) {
                z2 = true;
            }
        }
        if (z2) {
            if (this.a == null) {
                this.a = new OverflowMenuButton(this.mSystemContext);
            }
            ViewGroup viewGroup = (ViewGroup) this.a.getParent();
            if (viewGroup != this.mMenuView) {
                if (viewGroup != null) {
                    viewGroup.removeView(this.a);
                }
                ActionMenuView actionMenuView = (ActionMenuView) this.mMenuView;
                actionMenuView.addView(this.a, actionMenuView.generateOverflowButtonLayoutParams());
            }
        } else if (this.a != null && this.a.getParent() == this.mMenuView) {
            ((ViewGroup) this.mMenuView).removeView(this.a);
        }
        ((ActionMenuView) this.mMenuView).setOverflowReserved(this.i);
    }

    public boolean filterLeftoverView(ViewGroup viewGroup, int i2) {
        if (viewGroup.getChildAt(i2) == this.a) {
            return false;
        }
        return super.filterLeftoverView(viewGroup, i2);
    }

    public boolean onSubMenuSelected(SubMenuBuilder subMenuBuilder) {
        boolean z = false;
        if (!subMenuBuilder.hasVisibleItems()) {
            return false;
        }
        SubMenuBuilder subMenuBuilder2 = subMenuBuilder;
        while (subMenuBuilder2.getParentMenu() != this.mMenu) {
            subMenuBuilder2 = (SubMenuBuilder) subMenuBuilder2.getParentMenu();
        }
        View a2 = a(subMenuBuilder2.getItem());
        if (a2 == null) {
            return false;
        }
        this.f = subMenuBuilder.getItem().getItemId();
        int size = subMenuBuilder.size();
        int i2 = 0;
        while (true) {
            if (i2 >= size) {
                break;
            }
            MenuItem item = subMenuBuilder.getItem(i2);
            if (item.isVisible() && item.getIcon() != null) {
                z = true;
                break;
            }
            i2++;
        }
        this.c = new ActionButtonSubmenu(this.mContext, subMenuBuilder, a2);
        this.c.setForceShowIcon(z);
        this.c.show();
        super.onSubMenuSelected(subMenuBuilder);
        return true;
    }

    private View a(MenuItem menuItem) {
        ViewGroup viewGroup = (ViewGroup) this.mMenuView;
        if (viewGroup == null) {
            return null;
        }
        int childCount = viewGroup.getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            View childAt = viewGroup.getChildAt(i2);
            if ((childAt instanceof ItemView) && ((ItemView) childAt).getItemData() == menuItem) {
                return childAt;
            }
        }
        return null;
    }

    public boolean b() {
        if (!this.i || f() || this.mMenu == null || this.mMenuView == null || this.d != null || this.mMenu.getNonActionItems().isEmpty()) {
            return false;
        }
        OverflowPopup overflowPopup = new OverflowPopup(this.mContext, this.mMenu, this.a, true);
        this.d = new OpenOverflowRunnable(overflowPopup);
        ((View) this.mMenuView).post(this.d);
        super.onSubMenuSelected(null);
        return true;
    }

    public boolean c() {
        if (this.d == null || this.mMenuView == null) {
            OverflowPopup overflowPopup = this.b;
            if (overflowPopup == null) {
                return false;
            }
            overflowPopup.dismiss();
            return true;
        }
        ((View) this.mMenuView).removeCallbacks(this.d);
        this.d = null;
        return true;
    }

    public boolean d() {
        return c() | e();
    }

    public boolean e() {
        if (this.c == null) {
            return false;
        }
        this.c.dismiss();
        return true;
    }

    public boolean f() {
        return this.b != null && this.b.isShowing();
    }

    public boolean g() {
        return this.d != null || f();
    }

    public boolean h() {
        return this.i;
    }

    public boolean flagActionItems() {
        int i2;
        ArrayList arrayList;
        int i3;
        int i4;
        int i5;
        boolean z;
        ActionMenuPresenter actionMenuPresenter = this;
        int i6 = 0;
        if (actionMenuPresenter.mMenu != null) {
            arrayList = actionMenuPresenter.mMenu.getVisibleItems();
            i2 = arrayList.size();
        } else {
            arrayList = null;
            i2 = 0;
        }
        int i7 = actionMenuPresenter.m;
        int i8 = actionMenuPresenter.l;
        int makeMeasureSpec = MeasureSpec.makeMeasureSpec(0, 0);
        ViewGroup viewGroup = (ViewGroup) actionMenuPresenter.mMenuView;
        int i9 = i7;
        int i10 = 0;
        boolean z2 = false;
        int i11 = 0;
        for (int i12 = 0; i12 < i2; i12++) {
            MenuItemImpl menuItemImpl = (MenuItemImpl) arrayList.get(i12);
            if (menuItemImpl.requiresActionButton()) {
                i10++;
            } else if (menuItemImpl.requestsActionButton()) {
                i11++;
            } else {
                z2 = true;
            }
            if (actionMenuPresenter.q && menuItemImpl.isActionViewExpanded()) {
                i9 = 0;
            }
        }
        if (actionMenuPresenter.i && (z2 || i11 + i10 > i9)) {
            i9--;
        }
        int i13 = i9 - i10;
        SparseBooleanArray sparseBooleanArray = actionMenuPresenter.s;
        sparseBooleanArray.clear();
        if (actionMenuPresenter.o) {
            i4 = i8 / actionMenuPresenter.r;
            i3 = ((i8 % actionMenuPresenter.r) / i4) + actionMenuPresenter.r;
        } else {
            i4 = 0;
            i3 = 0;
        }
        int i14 = i8;
        int i15 = 0;
        int i16 = 0;
        while (i15 < i2) {
            MenuItemImpl menuItemImpl2 = (MenuItemImpl) arrayList.get(i15);
            if (menuItemImpl2.requiresActionButton()) {
                View itemView = actionMenuPresenter.getItemView(menuItemImpl2, actionMenuPresenter.t, viewGroup);
                if (actionMenuPresenter.t == null) {
                    actionMenuPresenter.t = itemView;
                }
                if (actionMenuPresenter.o) {
                    i4 -= ActionMenuView.a(itemView, i3, i4, makeMeasureSpec, i6);
                } else {
                    itemView.measure(makeMeasureSpec, makeMeasureSpec);
                }
                int measuredWidth = itemView.getMeasuredWidth();
                i14 -= measuredWidth;
                if (i16 == 0) {
                    i16 = measuredWidth;
                }
                int groupId = menuItemImpl2.getGroupId();
                if (groupId != 0) {
                    z = true;
                    sparseBooleanArray.put(groupId, true);
                } else {
                    z = true;
                }
                menuItemImpl2.setIsActionButton(z);
                i5 = i2;
            } else if (menuItemImpl2.requestsActionButton()) {
                int groupId2 = menuItemImpl2.getGroupId();
                boolean z3 = sparseBooleanArray.get(groupId2);
                boolean z4 = (i13 > 0 || z3) && i14 > 0 && (!actionMenuPresenter.o || i4 > 0);
                if (z4) {
                    boolean z5 = z4;
                    View itemView2 = actionMenuPresenter.getItemView(menuItemImpl2, actionMenuPresenter.t, viewGroup);
                    i5 = i2;
                    if (actionMenuPresenter.t == null) {
                        actionMenuPresenter.t = itemView2;
                    }
                    if (actionMenuPresenter.o) {
                        int a2 = ActionMenuView.a(itemView2, i3, i4, makeMeasureSpec, 0);
                        i4 -= a2;
                        if (a2 == 0) {
                            z5 = false;
                        }
                    } else {
                        itemView2.measure(makeMeasureSpec, makeMeasureSpec);
                    }
                    int measuredWidth2 = itemView2.getMeasuredWidth();
                    i14 -= measuredWidth2;
                    if (i16 == 0) {
                        i16 = measuredWidth2;
                    }
                    if (actionMenuPresenter.o) {
                        z4 = z5 & (i14 >= 0);
                    } else {
                        z4 = z5 & (i14 + i16 > 0);
                    }
                } else {
                    boolean z6 = z4;
                    i5 = i2;
                }
                if (z4 && groupId2 != 0) {
                    sparseBooleanArray.put(groupId2, true);
                } else if (z3) {
                    sparseBooleanArray.put(groupId2, false);
                    int i17 = 0;
                    while (i17 < i15) {
                        MenuItemImpl menuItemImpl3 = (MenuItemImpl) arrayList.get(i17);
                        if (menuItemImpl3.getGroupId() == groupId2) {
                            if (menuItemImpl3.isActionButton()) {
                                i13++;
                            }
                            menuItemImpl3.setIsActionButton(false);
                        }
                        i17++;
                    }
                }
                if (z4) {
                    i13--;
                }
                menuItemImpl2.setIsActionButton(z4);
            } else {
                i5 = i2;
                menuItemImpl2.setIsActionButton(false);
                i15++;
                i2 = i5;
                actionMenuPresenter = this;
                i6 = 0;
            }
            i15++;
            i2 = i5;
            actionMenuPresenter = this;
            i6 = 0;
        }
        return true;
    }

    public void onCloseMenu(MenuBuilder menuBuilder, boolean z) {
        d();
        super.onCloseMenu(menuBuilder, z);
    }

    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState();
        savedState.a = this.f;
        return savedState;
    }

    public void onRestoreInstanceState(Parcelable parcelable) {
        if (parcelable instanceof SavedState) {
            SavedState savedState = (SavedState) parcelable;
            if (savedState.a > 0) {
                MenuItem findItem = this.mMenu.findItem(savedState.a);
                if (findItem != null) {
                    onSubMenuSelected((SubMenuBuilder) findItem.getSubMenu());
                }
            }
        }
    }

    public void onSubUiVisibilityChanged(boolean z) {
        if (z) {
            super.onSubMenuSelected(null);
        } else if (this.mMenu != null) {
            this.mMenu.close(false);
        }
    }

    public void a(ActionMenuView actionMenuView) {
        this.mMenuView = actionMenuView;
        actionMenuView.initialize(this.mMenu);
    }
}
