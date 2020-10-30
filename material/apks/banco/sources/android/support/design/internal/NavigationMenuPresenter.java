package android.support.design.internal;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.annotation.StyleRes;
import android.support.design.R;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.WindowInsetsCompat;
import android.support.v7.view.menu.ListMenuPresenter;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuItemImpl;
import android.support.v7.view.menu.MenuPresenter;
import android.support.v7.view.menu.MenuPresenter.Callback;
import android.support.v7.view.menu.MenuView;
import android.support.v7.view.menu.SubMenuBuilder;
import android.support.v7.widget.RecyclerView.Adapter;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.SubMenu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.ArrayList;

@RestrictTo({Scope.LIBRARY_GROUP})
public class NavigationMenuPresenter implements MenuPresenter {
    LinearLayout a;
    MenuBuilder b;
    NavigationMenuAdapter c;
    LayoutInflater d;
    int e;
    boolean f;
    ColorStateList g;
    ColorStateList h;
    Drawable i;
    int j;
    final OnClickListener k = new OnClickListener() {
        public void onClick(View view) {
            NavigationMenuItemView navigationMenuItemView = (NavigationMenuItemView) view;
            NavigationMenuPresenter.this.setUpdateSuspended(true);
            MenuItemImpl itemData = navigationMenuItemView.getItemData();
            boolean performItemAction = NavigationMenuPresenter.this.b.performItemAction(itemData, NavigationMenuPresenter.this, 0);
            if (itemData != null && itemData.isCheckable() && performItemAction) {
                NavigationMenuPresenter.this.c.a(itemData);
            }
            NavigationMenuPresenter.this.setUpdateSuspended(false);
            NavigationMenuPresenter.this.updateMenuView(false);
        }
    };
    private NavigationMenuView l;
    private Callback m;
    private int n;
    private int o;

    static class HeaderViewHolder extends ViewHolder {
        public HeaderViewHolder(View view) {
            super(view);
        }
    }

    class NavigationMenuAdapter extends Adapter<ViewHolder> {
        private final ArrayList<NavigationMenuItem> b = new ArrayList<>();
        private MenuItemImpl c;
        private boolean d;

        public long getItemId(int i) {
            return (long) i;
        }

        NavigationMenuAdapter() {
            c();
        }

        public int getItemCount() {
            return this.b.size();
        }

        public int getItemViewType(int i) {
            NavigationMenuItem navigationMenuItem = (NavigationMenuItem) this.b.get(i);
            if (navigationMenuItem instanceof NavigationMenuSeparatorItem) {
                return 2;
            }
            if (navigationMenuItem instanceof NavigationMenuHeaderItem) {
                return 3;
            }
            if (navigationMenuItem instanceof NavigationMenuTextItem) {
                return ((NavigationMenuTextItem) navigationMenuItem).a().hasSubMenu() ? 1 : 0;
            }
            throw new RuntimeException("Unknown item type.");
        }

        /* renamed from: a */
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            switch (i) {
                case 0:
                    return new NormalViewHolder(NavigationMenuPresenter.this.d, viewGroup, NavigationMenuPresenter.this.k);
                case 1:
                    return new SubheaderViewHolder(NavigationMenuPresenter.this.d, viewGroup);
                case 2:
                    return new SeparatorViewHolder(NavigationMenuPresenter.this.d, viewGroup);
                case 3:
                    return new HeaderViewHolder(NavigationMenuPresenter.this.a);
                default:
                    return null;
            }
        }

        /* renamed from: a */
        public void onBindViewHolder(ViewHolder viewHolder, int i) {
            switch (getItemViewType(i)) {
                case 0:
                    NavigationMenuItemView navigationMenuItemView = (NavigationMenuItemView) viewHolder.itemView;
                    navigationMenuItemView.setIconTintList(NavigationMenuPresenter.this.h);
                    if (NavigationMenuPresenter.this.f) {
                        navigationMenuItemView.setTextAppearance(NavigationMenuPresenter.this.e);
                    }
                    if (NavigationMenuPresenter.this.g != null) {
                        navigationMenuItemView.setTextColor(NavigationMenuPresenter.this.g);
                    }
                    ViewCompat.setBackground(navigationMenuItemView, NavigationMenuPresenter.this.i != null ? NavigationMenuPresenter.this.i.getConstantState().newDrawable() : null);
                    NavigationMenuTextItem navigationMenuTextItem = (NavigationMenuTextItem) this.b.get(i);
                    navigationMenuItemView.setNeedsEmptyIcon(navigationMenuTextItem.a);
                    navigationMenuItemView.initialize(navigationMenuTextItem.a(), 0);
                    return;
                case 1:
                    ((TextView) viewHolder.itemView).setText(((NavigationMenuTextItem) this.b.get(i)).a().getTitle());
                    return;
                case 2:
                    NavigationMenuSeparatorItem navigationMenuSeparatorItem = (NavigationMenuSeparatorItem) this.b.get(i);
                    viewHolder.itemView.setPadding(0, navigationMenuSeparatorItem.a(), 0, navigationMenuSeparatorItem.b());
                    return;
                default:
                    return;
            }
        }

        /* renamed from: a */
        public void onViewRecycled(ViewHolder viewHolder) {
            if (viewHolder instanceof NormalViewHolder) {
                ((NavigationMenuItemView) viewHolder.itemView).recycle();
            }
        }

        public void a() {
            c();
            notifyDataSetChanged();
        }

        private void c() {
            if (!this.d) {
                this.d = true;
                this.b.clear();
                this.b.add(new NavigationMenuHeaderItem());
                int size = NavigationMenuPresenter.this.b.getVisibleItems().size();
                int i = -1;
                boolean z = false;
                int i2 = 0;
                for (int i3 = 0; i3 < size; i3++) {
                    MenuItemImpl menuItemImpl = (MenuItemImpl) NavigationMenuPresenter.this.b.getVisibleItems().get(i3);
                    if (menuItemImpl.isChecked()) {
                        a(menuItemImpl);
                    }
                    if (menuItemImpl.isCheckable()) {
                        menuItemImpl.setExclusiveCheckable(false);
                    }
                    if (menuItemImpl.hasSubMenu()) {
                        SubMenu subMenu = menuItemImpl.getSubMenu();
                        if (subMenu.hasVisibleItems()) {
                            if (i3 != 0) {
                                this.b.add(new NavigationMenuSeparatorItem(NavigationMenuPresenter.this.j, 0));
                            }
                            this.b.add(new NavigationMenuTextItem(menuItemImpl));
                            int size2 = this.b.size();
                            int size3 = subMenu.size();
                            boolean z2 = false;
                            for (int i4 = 0; i4 < size3; i4++) {
                                MenuItemImpl menuItemImpl2 = (MenuItemImpl) subMenu.getItem(i4);
                                if (menuItemImpl2.isVisible()) {
                                    if (!z2 && menuItemImpl2.getIcon() != null) {
                                        z2 = true;
                                    }
                                    if (menuItemImpl2.isCheckable()) {
                                        menuItemImpl2.setExclusiveCheckable(false);
                                    }
                                    if (menuItemImpl.isChecked()) {
                                        a(menuItemImpl);
                                    }
                                    this.b.add(new NavigationMenuTextItem(menuItemImpl2));
                                }
                            }
                            if (z2) {
                                a(size2, this.b.size());
                            }
                        }
                    } else {
                        int groupId = menuItemImpl.getGroupId();
                        if (groupId != i) {
                            i2 = this.b.size();
                            boolean z3 = menuItemImpl.getIcon() != null;
                            if (i3 != 0) {
                                i2++;
                                this.b.add(new NavigationMenuSeparatorItem(NavigationMenuPresenter.this.j, NavigationMenuPresenter.this.j));
                            }
                            z = z3;
                        } else if (!z && menuItemImpl.getIcon() != null) {
                            a(i2, this.b.size());
                            z = true;
                        }
                        NavigationMenuTextItem navigationMenuTextItem = new NavigationMenuTextItem(menuItemImpl);
                        navigationMenuTextItem.a = z;
                        this.b.add(navigationMenuTextItem);
                        i = groupId;
                    }
                }
                this.d = false;
            }
        }

        private void a(int i, int i2) {
            while (i < i2) {
                ((NavigationMenuTextItem) this.b.get(i)).a = true;
                i++;
            }
        }

        public void a(MenuItemImpl menuItemImpl) {
            if (this.c != menuItemImpl && menuItemImpl.isCheckable()) {
                if (this.c != null) {
                    this.c.setChecked(false);
                }
                this.c = menuItemImpl;
                menuItemImpl.setChecked(true);
            }
        }

        public Bundle b() {
            Bundle bundle = new Bundle();
            if (this.c != null) {
                bundle.putInt("android:menu:checked", this.c.getItemId());
            }
            SparseArray sparseArray = new SparseArray();
            int size = this.b.size();
            for (int i = 0; i < size; i++) {
                NavigationMenuItem navigationMenuItem = (NavigationMenuItem) this.b.get(i);
                if (navigationMenuItem instanceof NavigationMenuTextItem) {
                    MenuItemImpl a2 = ((NavigationMenuTextItem) navigationMenuItem).a();
                    View actionView = a2 != null ? a2.getActionView() : null;
                    if (actionView != null) {
                        ParcelableSparseArray parcelableSparseArray = new ParcelableSparseArray();
                        actionView.saveHierarchyState(parcelableSparseArray);
                        sparseArray.put(a2.getItemId(), parcelableSparseArray);
                    }
                }
            }
            bundle.putSparseParcelableArray("android:menu:action_views", sparseArray);
            return bundle;
        }

        public void a(Bundle bundle) {
            int i = bundle.getInt("android:menu:checked", 0);
            if (i != 0) {
                this.d = true;
                int size = this.b.size();
                int i2 = 0;
                while (true) {
                    if (i2 >= size) {
                        break;
                    }
                    NavigationMenuItem navigationMenuItem = (NavigationMenuItem) this.b.get(i2);
                    if (navigationMenuItem instanceof NavigationMenuTextItem) {
                        MenuItemImpl a2 = ((NavigationMenuTextItem) navigationMenuItem).a();
                        if (a2 != null && a2.getItemId() == i) {
                            a(a2);
                            break;
                        }
                    }
                    i2++;
                }
                this.d = false;
                c();
            }
            SparseArray sparseParcelableArray = bundle.getSparseParcelableArray("android:menu:action_views");
            if (sparseParcelableArray != null) {
                int size2 = this.b.size();
                for (int i3 = 0; i3 < size2; i3++) {
                    NavigationMenuItem navigationMenuItem2 = (NavigationMenuItem) this.b.get(i3);
                    if (navigationMenuItem2 instanceof NavigationMenuTextItem) {
                        MenuItemImpl a3 = ((NavigationMenuTextItem) navigationMenuItem2).a();
                        if (a3 != null) {
                            View actionView = a3.getActionView();
                            if (actionView != null) {
                                ParcelableSparseArray parcelableSparseArray = (ParcelableSparseArray) sparseParcelableArray.get(a3.getItemId());
                                if (parcelableSparseArray != null) {
                                    actionView.restoreHierarchyState(parcelableSparseArray);
                                }
                            }
                        }
                    }
                }
            }
        }

        public void a(boolean z) {
            this.d = z;
        }
    }

    static class NavigationMenuHeaderItem implements NavigationMenuItem {
        NavigationMenuHeaderItem() {
        }
    }

    interface NavigationMenuItem {
    }

    static class NavigationMenuSeparatorItem implements NavigationMenuItem {
        private final int a;
        private final int b;

        public NavigationMenuSeparatorItem(int i, int i2) {
            this.a = i;
            this.b = i2;
        }

        public int a() {
            return this.a;
        }

        public int b() {
            return this.b;
        }
    }

    static class NavigationMenuTextItem implements NavigationMenuItem {
        boolean a;
        private final MenuItemImpl b;

        NavigationMenuTextItem(MenuItemImpl menuItemImpl) {
            this.b = menuItemImpl;
        }

        public MenuItemImpl a() {
            return this.b;
        }
    }

    static class NormalViewHolder extends ViewHolder {
        public NormalViewHolder(LayoutInflater layoutInflater, ViewGroup viewGroup, OnClickListener onClickListener) {
            super(layoutInflater.inflate(R.layout.design_navigation_item, viewGroup, false));
            this.itemView.setOnClickListener(onClickListener);
        }
    }

    static class SeparatorViewHolder extends ViewHolder {
        public SeparatorViewHolder(LayoutInflater layoutInflater, ViewGroup viewGroup) {
            super(layoutInflater.inflate(R.layout.design_navigation_item_separator, viewGroup, false));
        }
    }

    static class SubheaderViewHolder extends ViewHolder {
        public SubheaderViewHolder(LayoutInflater layoutInflater, ViewGroup viewGroup) {
            super(layoutInflater.inflate(R.layout.design_navigation_item_subheader, viewGroup, false));
        }
    }

    static abstract class ViewHolder extends android.support.v7.widget.RecyclerView.ViewHolder {
        public ViewHolder(View view) {
            super(view);
        }
    }

    public boolean collapseItemActionView(MenuBuilder menuBuilder, MenuItemImpl menuItemImpl) {
        return false;
    }

    public boolean expandItemActionView(MenuBuilder menuBuilder, MenuItemImpl menuItemImpl) {
        return false;
    }

    public boolean flagActionItems() {
        return false;
    }

    public boolean onSubMenuSelected(SubMenuBuilder subMenuBuilder) {
        return false;
    }

    public void initForMenu(Context context, MenuBuilder menuBuilder) {
        this.d = LayoutInflater.from(context);
        this.b = menuBuilder;
        this.j = context.getResources().getDimensionPixelOffset(R.dimen.design_navigation_separator_vertical_padding);
    }

    public MenuView getMenuView(ViewGroup viewGroup) {
        if (this.l == null) {
            this.l = (NavigationMenuView) this.d.inflate(R.layout.design_navigation_menu, viewGroup, false);
            if (this.c == null) {
                this.c = new NavigationMenuAdapter();
            }
            this.a = (LinearLayout) this.d.inflate(R.layout.design_navigation_item_header, this.l, false);
            this.l.setAdapter(this.c);
        }
        return this.l;
    }

    public void updateMenuView(boolean z) {
        if (this.c != null) {
            this.c.a();
        }
    }

    public void setCallback(Callback callback) {
        this.m = callback;
    }

    public void onCloseMenu(MenuBuilder menuBuilder, boolean z) {
        if (this.m != null) {
            this.m.onCloseMenu(menuBuilder, z);
        }
    }

    public int getId() {
        return this.n;
    }

    public void setId(int i2) {
        this.n = i2;
    }

    public Parcelable onSaveInstanceState() {
        if (VERSION.SDK_INT < 11) {
            return null;
        }
        Bundle bundle = new Bundle();
        if (this.l != null) {
            SparseArray sparseArray = new SparseArray();
            this.l.saveHierarchyState(sparseArray);
            bundle.putSparseParcelableArray(ListMenuPresenter.VIEWS_TAG, sparseArray);
        }
        if (this.c != null) {
            bundle.putBundle("android:menu:adapter", this.c.b());
        }
        if (this.a != null) {
            SparseArray sparseArray2 = new SparseArray();
            this.a.saveHierarchyState(sparseArray2);
            bundle.putSparseParcelableArray("android:menu:header", sparseArray2);
        }
        return bundle;
    }

    public void onRestoreInstanceState(Parcelable parcelable) {
        if (parcelable instanceof Bundle) {
            Bundle bundle = (Bundle) parcelable;
            SparseArray sparseParcelableArray = bundle.getSparseParcelableArray(ListMenuPresenter.VIEWS_TAG);
            if (sparseParcelableArray != null) {
                this.l.restoreHierarchyState(sparseParcelableArray);
            }
            Bundle bundle2 = bundle.getBundle("android:menu:adapter");
            if (bundle2 != null) {
                this.c.a(bundle2);
            }
            SparseArray sparseParcelableArray2 = bundle.getSparseParcelableArray("android:menu:header");
            if (sparseParcelableArray2 != null) {
                this.a.restoreHierarchyState(sparseParcelableArray2);
            }
        }
    }

    public void setCheckedItem(MenuItemImpl menuItemImpl) {
        this.c.a(menuItemImpl);
    }

    public View inflateHeaderView(@LayoutRes int i2) {
        View inflate = this.d.inflate(i2, this.a, false);
        addHeaderView(inflate);
        return inflate;
    }

    public void addHeaderView(@NonNull View view) {
        this.a.addView(view);
        this.l.setPadding(0, 0, 0, this.l.getPaddingBottom());
    }

    public void removeHeaderView(@NonNull View view) {
        this.a.removeView(view);
        if (this.a.getChildCount() == 0) {
            this.l.setPadding(0, this.o, 0, this.l.getPaddingBottom());
        }
    }

    public int getHeaderCount() {
        return this.a.getChildCount();
    }

    public View getHeaderView(int i2) {
        return this.a.getChildAt(i2);
    }

    @Nullable
    public ColorStateList getItemTintList() {
        return this.h;
    }

    public void setItemIconTintList(@Nullable ColorStateList colorStateList) {
        this.h = colorStateList;
        updateMenuView(false);
    }

    @Nullable
    public ColorStateList getItemTextColor() {
        return this.g;
    }

    public void setItemTextColor(@Nullable ColorStateList colorStateList) {
        this.g = colorStateList;
        updateMenuView(false);
    }

    public void setItemTextAppearance(@StyleRes int i2) {
        this.e = i2;
        this.f = true;
        updateMenuView(false);
    }

    @Nullable
    public Drawable getItemBackground() {
        return this.i;
    }

    public void setItemBackground(@Nullable Drawable drawable) {
        this.i = drawable;
        updateMenuView(false);
    }

    public void setUpdateSuspended(boolean z) {
        if (this.c != null) {
            this.c.a(z);
        }
    }

    public void dispatchApplyWindowInsets(WindowInsetsCompat windowInsetsCompat) {
        int systemWindowInsetTop = windowInsetsCompat.getSystemWindowInsetTop();
        if (this.o != systemWindowInsetTop) {
            this.o = systemWindowInsetTop;
            if (this.a.getChildCount() == 0) {
                this.l.setPadding(0, this.o, 0, this.l.getPaddingBottom());
            }
        }
        ViewCompat.dispatchApplyWindowInsets(this.a, windowInsetsCompat);
    }
}
