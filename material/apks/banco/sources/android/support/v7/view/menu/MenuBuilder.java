package android.support.v7.view.menu;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.v4.content.ContextCompat;
import android.support.v4.internal.view.SupportMenu;
import android.support.v4.view.ActionProvider;
import android.support.v7.appcompat.R;
import android.util.SparseArray;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.KeyCharacterMap.KeyData;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@RestrictTo({Scope.LIBRARY_GROUP})
public class MenuBuilder implements SupportMenu {
    private static final int[] d = {1, 4, 5, 3, 2, 0};
    CharSequence a;
    Drawable b;
    View c;
    private final Context e;
    private final Resources f;
    private boolean g;
    private boolean h;
    private Callback i;
    private ArrayList<MenuItemImpl> j;
    private ArrayList<MenuItemImpl> k;
    private boolean l;
    private ArrayList<MenuItemImpl> m;
    private ArrayList<MenuItemImpl> n;
    private boolean o;
    private int p = 0;
    private ContextMenuInfo q;
    private boolean r = false;
    private boolean s = false;
    private boolean t = false;
    private boolean u = false;
    private boolean v = false;
    private ArrayList<MenuItemImpl> w = new ArrayList<>();
    private CopyOnWriteArrayList<WeakReference<MenuPresenter>> x = new CopyOnWriteArrayList<>();
    private MenuItemImpl y;
    private boolean z;

    @RestrictTo({Scope.LIBRARY_GROUP})
    public interface Callback {
        boolean onMenuItemSelected(MenuBuilder menuBuilder, MenuItem menuItem);

        void onMenuModeChange(MenuBuilder menuBuilder);
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    public interface ItemInvoker {
        boolean invokeItem(MenuItemImpl menuItemImpl);
    }

    /* access modifiers changed from: protected */
    public String getActionViewStatesKey() {
        return "android:menu:actionviewstates";
    }

    public MenuBuilder getRootMenu() {
        return this;
    }

    public MenuBuilder(Context context) {
        this.e = context;
        this.f = context.getResources();
        this.j = new ArrayList<>();
        this.k = new ArrayList<>();
        this.l = true;
        this.m = new ArrayList<>();
        this.n = new ArrayList<>();
        this.o = true;
        b(true);
    }

    public MenuBuilder setDefaultShowAsAction(int i2) {
        this.p = i2;
        return this;
    }

    public void addMenuPresenter(MenuPresenter menuPresenter) {
        addMenuPresenter(menuPresenter, this.e);
    }

    public void addMenuPresenter(MenuPresenter menuPresenter, Context context) {
        this.x.add(new WeakReference(menuPresenter));
        menuPresenter.initForMenu(context, this);
        this.o = true;
    }

    public void removeMenuPresenter(MenuPresenter menuPresenter) {
        Iterator it = this.x.iterator();
        while (it.hasNext()) {
            WeakReference weakReference = (WeakReference) it.next();
            MenuPresenter menuPresenter2 = (MenuPresenter) weakReference.get();
            if (menuPresenter2 == null || menuPresenter2 == menuPresenter) {
                this.x.remove(weakReference);
            }
        }
    }

    private void a(boolean z2) {
        if (!this.x.isEmpty()) {
            stopDispatchingItemsChanged();
            Iterator it = this.x.iterator();
            while (it.hasNext()) {
                WeakReference weakReference = (WeakReference) it.next();
                MenuPresenter menuPresenter = (MenuPresenter) weakReference.get();
                if (menuPresenter == null) {
                    this.x.remove(weakReference);
                } else {
                    menuPresenter.updateMenuView(z2);
                }
            }
            startDispatchingItemsChanged();
        }
    }

    private boolean a(SubMenuBuilder subMenuBuilder, MenuPresenter menuPresenter) {
        boolean z2 = false;
        if (this.x.isEmpty()) {
            return false;
        }
        if (menuPresenter != null) {
            z2 = menuPresenter.onSubMenuSelected(subMenuBuilder);
        }
        Iterator it = this.x.iterator();
        while (it.hasNext()) {
            WeakReference weakReference = (WeakReference) it.next();
            MenuPresenter menuPresenter2 = (MenuPresenter) weakReference.get();
            if (menuPresenter2 == null) {
                this.x.remove(weakReference);
            } else if (!z2) {
                z2 = menuPresenter2.onSubMenuSelected(subMenuBuilder);
            }
        }
        return z2;
    }

    private void a(Bundle bundle) {
        if (!this.x.isEmpty()) {
            SparseArray sparseArray = new SparseArray();
            Iterator it = this.x.iterator();
            while (it.hasNext()) {
                WeakReference weakReference = (WeakReference) it.next();
                MenuPresenter menuPresenter = (MenuPresenter) weakReference.get();
                if (menuPresenter == null) {
                    this.x.remove(weakReference);
                } else {
                    int id2 = menuPresenter.getId();
                    if (id2 > 0) {
                        Parcelable onSaveInstanceState = menuPresenter.onSaveInstanceState();
                        if (onSaveInstanceState != null) {
                            sparseArray.put(id2, onSaveInstanceState);
                        }
                    }
                }
            }
            bundle.putSparseParcelableArray("android:menu:presenters", sparseArray);
        }
    }

    private void b(Bundle bundle) {
        SparseArray sparseParcelableArray = bundle.getSparseParcelableArray("android:menu:presenters");
        if (sparseParcelableArray != null && !this.x.isEmpty()) {
            Iterator it = this.x.iterator();
            while (it.hasNext()) {
                WeakReference weakReference = (WeakReference) it.next();
                MenuPresenter menuPresenter = (MenuPresenter) weakReference.get();
                if (menuPresenter == null) {
                    this.x.remove(weakReference);
                } else {
                    int id2 = menuPresenter.getId();
                    if (id2 > 0) {
                        Parcelable parcelable = (Parcelable) sparseParcelableArray.get(id2);
                        if (parcelable != null) {
                            menuPresenter.onRestoreInstanceState(parcelable);
                        }
                    }
                }
            }
        }
    }

    public void savePresenterStates(Bundle bundle) {
        a(bundle);
    }

    public void restorePresenterStates(Bundle bundle) {
        b(bundle);
    }

    public void saveActionViewStates(Bundle bundle) {
        int size = size();
        SparseArray sparseArray = null;
        for (int i2 = 0; i2 < size; i2++) {
            MenuItem item = getItem(i2);
            View actionView = item.getActionView();
            if (!(actionView == null || actionView.getId() == -1)) {
                if (sparseArray == null) {
                    sparseArray = new SparseArray();
                }
                actionView.saveHierarchyState(sparseArray);
                if (item.isActionViewExpanded()) {
                    bundle.putInt("android:menu:expandedactionview", item.getItemId());
                }
            }
            if (item.hasSubMenu()) {
                ((SubMenuBuilder) item.getSubMenu()).saveActionViewStates(bundle);
            }
        }
        if (sparseArray != null) {
            bundle.putSparseParcelableArray(getActionViewStatesKey(), sparseArray);
        }
    }

    public void restoreActionViewStates(Bundle bundle) {
        if (bundle != null) {
            SparseArray sparseParcelableArray = bundle.getSparseParcelableArray(getActionViewStatesKey());
            int size = size();
            for (int i2 = 0; i2 < size; i2++) {
                MenuItem item = getItem(i2);
                View actionView = item.getActionView();
                if (!(actionView == null || actionView.getId() == -1)) {
                    actionView.restoreHierarchyState(sparseParcelableArray);
                }
                if (item.hasSubMenu()) {
                    ((SubMenuBuilder) item.getSubMenu()).restoreActionViewStates(bundle);
                }
            }
            int i3 = bundle.getInt("android:menu:expandedactionview");
            if (i3 > 0) {
                MenuItem findItem = findItem(i3);
                if (findItem != null) {
                    findItem.expandActionView();
                }
            }
        }
    }

    public void setCallback(Callback callback) {
        this.i = callback;
    }

    /* access modifiers changed from: protected */
    public MenuItem addInternal(int i2, int i3, int i4, CharSequence charSequence) {
        int a2 = a(i4);
        MenuItemImpl a3 = a(i2, i3, i4, a2, charSequence, this.p);
        if (this.q != null) {
            a3.a(this.q);
        }
        this.j.add(a(this.j, a2), a3);
        onItemsChanged(true);
        return a3;
    }

    private MenuItemImpl a(int i2, int i3, int i4, int i5, CharSequence charSequence, int i6) {
        MenuItemImpl menuItemImpl = new MenuItemImpl(this, i2, i3, i4, i5, charSequence, i6);
        return menuItemImpl;
    }

    public MenuItem add(CharSequence charSequence) {
        return addInternal(0, 0, 0, charSequence);
    }

    public MenuItem add(int i2) {
        return addInternal(0, 0, 0, this.f.getString(i2));
    }

    public MenuItem add(int i2, int i3, int i4, CharSequence charSequence) {
        return addInternal(i2, i3, i4, charSequence);
    }

    public MenuItem add(int i2, int i3, int i4, int i5) {
        return addInternal(i2, i3, i4, this.f.getString(i5));
    }

    public SubMenu addSubMenu(CharSequence charSequence) {
        return addSubMenu(0, 0, 0, charSequence);
    }

    public SubMenu addSubMenu(int i2) {
        return addSubMenu(0, 0, 0, (CharSequence) this.f.getString(i2));
    }

    public SubMenu addSubMenu(int i2, int i3, int i4, CharSequence charSequence) {
        MenuItemImpl menuItemImpl = (MenuItemImpl) addInternal(i2, i3, i4, charSequence);
        SubMenuBuilder subMenuBuilder = new SubMenuBuilder(this.e, this, menuItemImpl);
        menuItemImpl.setSubMenu(subMenuBuilder);
        return subMenuBuilder;
    }

    public SubMenu addSubMenu(int i2, int i3, int i4, int i5) {
        return addSubMenu(i2, i3, i4, (CharSequence) this.f.getString(i5));
    }

    public int addIntentOptions(int i2, int i3, int i4, ComponentName componentName, Intent[] intentArr, Intent intent, int i5, MenuItem[] menuItemArr) {
        PackageManager packageManager = this.e.getPackageManager();
        List queryIntentActivityOptions = packageManager.queryIntentActivityOptions(componentName, intentArr, intent, 0);
        int size = queryIntentActivityOptions != null ? queryIntentActivityOptions.size() : 0;
        if ((i5 & 1) == 0) {
            removeGroup(i2);
        }
        for (int i6 = 0; i6 < size; i6++) {
            ResolveInfo resolveInfo = (ResolveInfo) queryIntentActivityOptions.get(i6);
            Intent intent2 = new Intent(resolveInfo.specificIndex < 0 ? intent : intentArr[resolveInfo.specificIndex]);
            intent2.setComponent(new ComponentName(resolveInfo.activityInfo.applicationInfo.packageName, resolveInfo.activityInfo.name));
            MenuItem intent3 = add(i2, i3, i4, resolveInfo.loadLabel(packageManager)).setIcon(resolveInfo.loadIcon(packageManager)).setIntent(intent2);
            if (menuItemArr != null && resolveInfo.specificIndex >= 0) {
                menuItemArr[resolveInfo.specificIndex] = intent3;
            }
        }
        return size;
    }

    public void removeItem(int i2) {
        a(findItemIndex(i2), true);
    }

    public void removeGroup(int i2) {
        int findGroupIndex = findGroupIndex(i2);
        if (findGroupIndex >= 0) {
            int size = this.j.size() - findGroupIndex;
            int i3 = 0;
            while (true) {
                int i4 = i3 + 1;
                if (i3 >= size || ((MenuItemImpl) this.j.get(findGroupIndex)).getGroupId() != i2) {
                    onItemsChanged(true);
                } else {
                    a(findGroupIndex, false);
                    i3 = i4;
                }
            }
            onItemsChanged(true);
        }
    }

    private void a(int i2, boolean z2) {
        if (i2 >= 0 && i2 < this.j.size()) {
            this.j.remove(i2);
            if (z2) {
                onItemsChanged(true);
            }
        }
    }

    public void removeItemAt(int i2) {
        a(i2, true);
    }

    public void clearAll() {
        this.r = true;
        clear();
        clearHeader();
        this.r = false;
        this.s = false;
        this.t = false;
        onItemsChanged(true);
    }

    public void clear() {
        if (this.y != null) {
            collapseItemActionView(this.y);
        }
        this.j.clear();
        onItemsChanged(true);
    }

    /* access modifiers changed from: 0000 */
    public void a(MenuItem menuItem) {
        int groupId = menuItem.getGroupId();
        int size = this.j.size();
        stopDispatchingItemsChanged();
        for (int i2 = 0; i2 < size; i2++) {
            MenuItemImpl menuItemImpl = (MenuItemImpl) this.j.get(i2);
            if (menuItemImpl.getGroupId() == groupId && menuItemImpl.isExclusiveCheckable() && menuItemImpl.isCheckable()) {
                menuItemImpl.a(menuItemImpl == menuItem);
            }
        }
        startDispatchingItemsChanged();
    }

    public void setGroupCheckable(int i2, boolean z2, boolean z3) {
        int size = this.j.size();
        for (int i3 = 0; i3 < size; i3++) {
            MenuItemImpl menuItemImpl = (MenuItemImpl) this.j.get(i3);
            if (menuItemImpl.getGroupId() == i2) {
                menuItemImpl.setExclusiveCheckable(z3);
                menuItemImpl.setCheckable(z2);
            }
        }
    }

    public void setGroupVisible(int i2, boolean z2) {
        int size = this.j.size();
        boolean z3 = false;
        for (int i3 = 0; i3 < size; i3++) {
            MenuItemImpl menuItemImpl = (MenuItemImpl) this.j.get(i3);
            if (menuItemImpl.getGroupId() == i2 && menuItemImpl.b(z2)) {
                z3 = true;
            }
        }
        if (z3) {
            onItemsChanged(true);
        }
    }

    public void setGroupEnabled(int i2, boolean z2) {
        int size = this.j.size();
        for (int i3 = 0; i3 < size; i3++) {
            MenuItemImpl menuItemImpl = (MenuItemImpl) this.j.get(i3);
            if (menuItemImpl.getGroupId() == i2) {
                menuItemImpl.setEnabled(z2);
            }
        }
    }

    public boolean hasVisibleItems() {
        if (this.z) {
            return true;
        }
        int size = size();
        for (int i2 = 0; i2 < size; i2++) {
            if (((MenuItemImpl) this.j.get(i2)).isVisible()) {
                return true;
            }
        }
        return false;
    }

    public MenuItem findItem(int i2) {
        int size = size();
        for (int i3 = 0; i3 < size; i3++) {
            MenuItemImpl menuItemImpl = (MenuItemImpl) this.j.get(i3);
            if (menuItemImpl.getItemId() == i2) {
                return menuItemImpl;
            }
            if (menuItemImpl.hasSubMenu()) {
                MenuItem findItem = menuItemImpl.getSubMenu().findItem(i2);
                if (findItem != null) {
                    return findItem;
                }
            }
        }
        return null;
    }

    public int findItemIndex(int i2) {
        int size = size();
        for (int i3 = 0; i3 < size; i3++) {
            if (((MenuItemImpl) this.j.get(i3)).getItemId() == i2) {
                return i3;
            }
        }
        return -1;
    }

    public int findGroupIndex(int i2) {
        return findGroupIndex(i2, 0);
    }

    public int findGroupIndex(int i2, int i3) {
        int size = size();
        if (i3 < 0) {
            i3 = 0;
        }
        while (i3 < size) {
            if (((MenuItemImpl) this.j.get(i3)).getGroupId() == i2) {
                return i3;
            }
            i3++;
        }
        return -1;
    }

    public int size() {
        return this.j.size();
    }

    public MenuItem getItem(int i2) {
        return (MenuItem) this.j.get(i2);
    }

    public boolean isShortcutKey(int i2, KeyEvent keyEvent) {
        return a(i2, keyEvent) != null;
    }

    public void setQwertyMode(boolean z2) {
        this.g = z2;
        onItemsChanged(false);
    }

    private static int a(int i2) {
        int i3 = (-65536 & i2) >> 16;
        if (i3 < 0 || i3 >= d.length) {
            throw new IllegalArgumentException("order does not contain a valid category.");
        }
        return (i2 & 65535) | (d[i3] << 16);
    }

    /* access modifiers changed from: 0000 */
    public boolean isQwertyMode() {
        return this.g;
    }

    public void setShortcutsVisible(boolean z2) {
        if (this.h != z2) {
            b(z2);
            onItemsChanged(false);
        }
    }

    private void b(boolean z2) {
        boolean z3 = true;
        if (!z2 || this.f.getConfiguration().keyboard == 1 || !this.f.getBoolean(R.bool.abc_config_showMenuShortcutsWhenKeyboardPresent)) {
            z3 = false;
        }
        this.h = z3;
    }

    public boolean isShortcutsVisible() {
        return this.h;
    }

    /* access modifiers changed from: 0000 */
    public Resources a() {
        return this.f;
    }

    public Context getContext() {
        return this.e;
    }

    /* access modifiers changed from: 0000 */
    public boolean a(MenuBuilder menuBuilder, MenuItem menuItem) {
        return this.i != null && this.i.onMenuItemSelected(menuBuilder, menuItem);
    }

    public void changeMenuMode() {
        if (this.i != null) {
            this.i.onMenuModeChange(this);
        }
    }

    private static int a(ArrayList<MenuItemImpl> arrayList, int i2) {
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            if (((MenuItemImpl) arrayList.get(size)).getOrdering() <= i2) {
                return size + 1;
            }
        }
        return 0;
    }

    public boolean performShortcut(int i2, KeyEvent keyEvent, int i3) {
        MenuItemImpl a2 = a(i2, keyEvent);
        boolean performItemAction = a2 != null ? performItemAction(a2, i3) : false;
        if ((i3 & 2) != 0) {
            close(true);
        }
        return performItemAction;
    }

    /* access modifiers changed from: 0000 */
    public void a(List<MenuItemImpl> list, int i2, KeyEvent keyEvent) {
        boolean isQwertyMode = isQwertyMode();
        int modifiers = keyEvent.getModifiers();
        KeyData keyData = new KeyData();
        if (keyEvent.getKeyData(keyData) || i2 == 67) {
            int size = this.j.size();
            for (int i3 = 0; i3 < size; i3++) {
                MenuItemImpl menuItemImpl = (MenuItemImpl) this.j.get(i3);
                if (menuItemImpl.hasSubMenu()) {
                    ((MenuBuilder) menuItemImpl.getSubMenu()).a(list, i2, keyEvent);
                }
                char alphabeticShortcut = isQwertyMode ? menuItemImpl.getAlphabeticShortcut() : menuItemImpl.getNumericShortcut();
                if (((modifiers & SupportMenu.SUPPORTED_MODIFIERS_MASK) == ((isQwertyMode ? menuItemImpl.getAlphabeticModifiers() : menuItemImpl.getNumericModifiers()) & SupportMenu.SUPPORTED_MODIFIERS_MASK)) && alphabeticShortcut != 0 && ((alphabeticShortcut == keyData.meta[0] || alphabeticShortcut == keyData.meta[2] || (isQwertyMode && alphabeticShortcut == 8 && i2 == 67)) && menuItemImpl.isEnabled())) {
                    list.add(menuItemImpl);
                }
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public MenuItemImpl a(int i2, KeyEvent keyEvent) {
        char c2;
        ArrayList<MenuItemImpl> arrayList = this.w;
        arrayList.clear();
        a(arrayList, i2, keyEvent);
        if (arrayList.isEmpty()) {
            return null;
        }
        int metaState = keyEvent.getMetaState();
        KeyData keyData = new KeyData();
        keyEvent.getKeyData(keyData);
        int size = arrayList.size();
        if (size == 1) {
            return (MenuItemImpl) arrayList.get(0);
        }
        boolean isQwertyMode = isQwertyMode();
        for (int i3 = 0; i3 < size; i3++) {
            MenuItemImpl menuItemImpl = (MenuItemImpl) arrayList.get(i3);
            if (isQwertyMode) {
                c2 = menuItemImpl.getAlphabeticShortcut();
            } else {
                c2 = menuItemImpl.getNumericShortcut();
            }
            if ((c2 == keyData.meta[0] && (metaState & 2) == 0) || ((c2 == keyData.meta[2] && (metaState & 2) != 0) || (isQwertyMode && c2 == 8 && i2 == 67))) {
                return menuItemImpl;
            }
        }
        return null;
    }

    public boolean performIdentifierAction(int i2, int i3) {
        return performItemAction(findItem(i2), i3);
    }

    public boolean performItemAction(MenuItem menuItem, int i2) {
        return performItemAction(menuItem, null, i2);
    }

    public boolean performItemAction(MenuItem menuItem, MenuPresenter menuPresenter, int i2) {
        MenuItemImpl menuItemImpl = (MenuItemImpl) menuItem;
        if (menuItemImpl == null || !menuItemImpl.isEnabled()) {
            return false;
        }
        boolean invoke = menuItemImpl.invoke();
        ActionProvider supportActionProvider = menuItemImpl.getSupportActionProvider();
        boolean z2 = supportActionProvider != null && supportActionProvider.hasSubMenu();
        if (menuItemImpl.hasCollapsibleActionView()) {
            invoke |= menuItemImpl.expandActionView();
            if (invoke) {
                close(true);
            }
        } else if (menuItemImpl.hasSubMenu() || z2) {
            if ((i2 & 4) == 0) {
                close(false);
            }
            if (!menuItemImpl.hasSubMenu()) {
                menuItemImpl.setSubMenu(new SubMenuBuilder(getContext(), this, menuItemImpl));
            }
            SubMenuBuilder subMenuBuilder = (SubMenuBuilder) menuItemImpl.getSubMenu();
            if (z2) {
                supportActionProvider.onPrepareSubMenu(subMenuBuilder);
            }
            invoke |= a(subMenuBuilder, menuPresenter);
            if (!invoke) {
                close(true);
            }
        } else if ((i2 & 1) == 0) {
            close(true);
        }
        return invoke;
    }

    public final void close(boolean z2) {
        if (!this.v) {
            this.v = true;
            Iterator it = this.x.iterator();
            while (it.hasNext()) {
                WeakReference weakReference = (WeakReference) it.next();
                MenuPresenter menuPresenter = (MenuPresenter) weakReference.get();
                if (menuPresenter == null) {
                    this.x.remove(weakReference);
                } else {
                    menuPresenter.onCloseMenu(this, z2);
                }
            }
            this.v = false;
        }
    }

    public void close() {
        close(true);
    }

    public void onItemsChanged(boolean z2) {
        if (!this.r) {
            if (z2) {
                this.l = true;
                this.o = true;
            }
            a(z2);
            return;
        }
        this.s = true;
        if (z2) {
            this.t = true;
        }
    }

    public void stopDispatchingItemsChanged() {
        if (!this.r) {
            this.r = true;
            this.s = false;
            this.t = false;
        }
    }

    public void startDispatchingItemsChanged() {
        this.r = false;
        if (this.s) {
            this.s = false;
            onItemsChanged(this.t);
        }
    }

    /* access modifiers changed from: 0000 */
    public void a(MenuItemImpl menuItemImpl) {
        this.l = true;
        onItemsChanged(true);
    }

    /* access modifiers changed from: 0000 */
    public void b(MenuItemImpl menuItemImpl) {
        this.o = true;
        onItemsChanged(true);
    }

    @NonNull
    public ArrayList<MenuItemImpl> getVisibleItems() {
        if (!this.l) {
            return this.k;
        }
        this.k.clear();
        int size = this.j.size();
        for (int i2 = 0; i2 < size; i2++) {
            MenuItemImpl menuItemImpl = (MenuItemImpl) this.j.get(i2);
            if (menuItemImpl.isVisible()) {
                this.k.add(menuItemImpl);
            }
        }
        this.l = false;
        this.o = true;
        return this.k;
    }

    public void flagActionItems() {
        ArrayList visibleItems = getVisibleItems();
        if (this.o) {
            Iterator it = this.x.iterator();
            boolean z2 = false;
            while (it.hasNext()) {
                WeakReference weakReference = (WeakReference) it.next();
                MenuPresenter menuPresenter = (MenuPresenter) weakReference.get();
                if (menuPresenter == null) {
                    this.x.remove(weakReference);
                } else {
                    z2 |= menuPresenter.flagActionItems();
                }
            }
            if (z2) {
                this.m.clear();
                this.n.clear();
                int size = visibleItems.size();
                for (int i2 = 0; i2 < size; i2++) {
                    MenuItemImpl menuItemImpl = (MenuItemImpl) visibleItems.get(i2);
                    if (menuItemImpl.isActionButton()) {
                        this.m.add(menuItemImpl);
                    } else {
                        this.n.add(menuItemImpl);
                    }
                }
            } else {
                this.m.clear();
                this.n.clear();
                this.n.addAll(getVisibleItems());
            }
            this.o = false;
        }
    }

    public ArrayList<MenuItemImpl> getActionItems() {
        flagActionItems();
        return this.m;
    }

    public ArrayList<MenuItemImpl> getNonActionItems() {
        flagActionItems();
        return this.n;
    }

    public void clearHeader() {
        this.b = null;
        this.a = null;
        this.c = null;
        onItemsChanged(false);
    }

    private void a(int i2, CharSequence charSequence, int i3, Drawable drawable, View view) {
        Resources a2 = a();
        if (view != null) {
            this.c = view;
            this.a = null;
            this.b = null;
        } else {
            if (i2 > 0) {
                this.a = a2.getText(i2);
            } else if (charSequence != null) {
                this.a = charSequence;
            }
            if (i3 > 0) {
                this.b = ContextCompat.getDrawable(getContext(), i3);
            } else if (drawable != null) {
                this.b = drawable;
            }
            this.c = null;
        }
        onItemsChanged(false);
    }

    /* access modifiers changed from: protected */
    public MenuBuilder setHeaderTitleInt(CharSequence charSequence) {
        a(0, charSequence, 0, null, null);
        return this;
    }

    /* access modifiers changed from: protected */
    public MenuBuilder setHeaderTitleInt(int i2) {
        a(i2, null, 0, null, null);
        return this;
    }

    /* access modifiers changed from: protected */
    public MenuBuilder setHeaderIconInt(Drawable drawable) {
        a(0, null, 0, drawable, null);
        return this;
    }

    /* access modifiers changed from: protected */
    public MenuBuilder setHeaderIconInt(int i2) {
        a(0, null, i2, null, null);
        return this;
    }

    /* access modifiers changed from: protected */
    public MenuBuilder setHeaderViewInt(View view) {
        a(0, null, 0, null, view);
        return this;
    }

    public CharSequence getHeaderTitle() {
        return this.a;
    }

    public Drawable getHeaderIcon() {
        return this.b;
    }

    public View getHeaderView() {
        return this.c;
    }

    public void setCurrentMenuInfo(ContextMenuInfo contextMenuInfo) {
        this.q = contextMenuInfo;
    }

    public void setOptionalIconsVisible(boolean z2) {
        this.u = z2;
    }

    /* access modifiers changed from: 0000 */
    public boolean b() {
        return this.u;
    }

    public boolean expandItemActionView(MenuItemImpl menuItemImpl) {
        boolean z2 = false;
        if (this.x.isEmpty()) {
            return false;
        }
        stopDispatchingItemsChanged();
        Iterator it = this.x.iterator();
        while (it.hasNext()) {
            WeakReference weakReference = (WeakReference) it.next();
            MenuPresenter menuPresenter = (MenuPresenter) weakReference.get();
            if (menuPresenter == null) {
                this.x.remove(weakReference);
            } else {
                z2 = menuPresenter.expandItemActionView(this, menuItemImpl);
                if (z2) {
                    break;
                }
            }
        }
        startDispatchingItemsChanged();
        if (z2) {
            this.y = menuItemImpl;
        }
        return z2;
    }

    public boolean collapseItemActionView(MenuItemImpl menuItemImpl) {
        boolean z2 = false;
        if (this.x.isEmpty() || this.y != menuItemImpl) {
            return false;
        }
        stopDispatchingItemsChanged();
        Iterator it = this.x.iterator();
        while (it.hasNext()) {
            WeakReference weakReference = (WeakReference) it.next();
            MenuPresenter menuPresenter = (MenuPresenter) weakReference.get();
            if (menuPresenter == null) {
                this.x.remove(weakReference);
            } else {
                z2 = menuPresenter.collapseItemActionView(this, menuItemImpl);
                if (z2) {
                    break;
                }
            }
        }
        startDispatchingItemsChanged();
        if (z2) {
            this.y = null;
        }
        return z2;
    }

    public MenuItemImpl getExpandedItem() {
        return this.y;
    }

    public void setOverrideVisibleItems(boolean z2) {
        this.z = z2;
    }
}
