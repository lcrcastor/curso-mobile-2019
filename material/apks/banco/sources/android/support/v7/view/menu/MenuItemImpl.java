package android.support.v7.view.menu;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.internal.view.SupportMenuItem;
import android.support.v4.view.ActionProvider;
import android.support.v4.view.ActionProvider.VisibilityListener;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.view.menu.MenuView.ItemView;
import android.util.Log;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MenuItem.OnActionExpandListener;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewDebug.CapturedViewProperty;
import android.widget.LinearLayout;

@RestrictTo({Scope.LIBRARY_GROUP})
public final class MenuItemImpl implements SupportMenuItem {
    private static String F;
    private static String G;
    private static String H;
    private static String I;
    private View A;
    private ActionProvider B;
    private OnActionExpandListener C;
    private boolean D = false;
    private ContextMenuInfo E;
    MenuBuilder a;
    private final int b;
    private final int c;
    private final int d;
    private final int e;
    private CharSequence f;
    private CharSequence g;
    private Intent h;
    private char i;
    private int j = 4096;
    private char k;
    private int l = 4096;
    private Drawable m;
    private int n = 0;
    private SubMenuBuilder o;
    private Runnable p;
    private OnMenuItemClickListener q;
    private CharSequence r;
    private CharSequence s;
    private ColorStateList t = null;
    private Mode u = null;
    private boolean v = false;
    private boolean w = false;
    private boolean x = false;
    private int y = 16;
    private int z = 0;

    MenuItemImpl(MenuBuilder menuBuilder, int i2, int i3, int i4, int i5, CharSequence charSequence, int i6) {
        this.a = menuBuilder;
        this.b = i3;
        this.c = i2;
        this.d = i4;
        this.e = i5;
        this.f = charSequence;
        this.z = i6;
    }

    public boolean invoke() {
        if ((this.q != null && this.q.onMenuItemClick(this)) || this.a.a(this.a, (MenuItem) this)) {
            return true;
        }
        if (this.p != null) {
            this.p.run();
            return true;
        }
        if (this.h != null) {
            try {
                this.a.getContext().startActivity(this.h);
                return true;
            } catch (ActivityNotFoundException e2) {
                Log.e("MenuItemImpl", "Can't find activity to handle intent; ignoring", e2);
            }
        }
        if (this.B == null || !this.B.onPerformDefaultAction()) {
            return false;
        }
        return true;
    }

    public boolean isEnabled() {
        return (this.y & 16) != 0;
    }

    public MenuItem setEnabled(boolean z2) {
        if (z2) {
            this.y |= 16;
        } else {
            this.y &= -17;
        }
        this.a.onItemsChanged(false);
        return this;
    }

    public int getGroupId() {
        return this.c;
    }

    @CapturedViewProperty
    public int getItemId() {
        return this.b;
    }

    public int getOrder() {
        return this.d;
    }

    public int getOrdering() {
        return this.e;
    }

    public Intent getIntent() {
        return this.h;
    }

    public MenuItem setIntent(Intent intent) {
        this.h = intent;
        return this;
    }

    public MenuItem setCallback(Runnable runnable) {
        this.p = runnable;
        return this;
    }

    public char getAlphabeticShortcut() {
        return this.k;
    }

    public MenuItem setAlphabeticShortcut(char c2) {
        if (this.k == c2) {
            return this;
        }
        this.k = Character.toLowerCase(c2);
        this.a.onItemsChanged(false);
        return this;
    }

    public MenuItem setAlphabeticShortcut(char c2, int i2) {
        if (this.k == c2 && this.l == i2) {
            return this;
        }
        this.k = Character.toLowerCase(c2);
        this.l = KeyEvent.normalizeMetaState(i2);
        this.a.onItemsChanged(false);
        return this;
    }

    public int getAlphabeticModifiers() {
        return this.l;
    }

    public char getNumericShortcut() {
        return this.i;
    }

    public int getNumericModifiers() {
        return this.j;
    }

    public MenuItem setNumericShortcut(char c2) {
        if (this.i == c2) {
            return this;
        }
        this.i = c2;
        this.a.onItemsChanged(false);
        return this;
    }

    public MenuItem setNumericShortcut(char c2, int i2) {
        if (this.i == c2 && this.j == i2) {
            return this;
        }
        this.i = c2;
        this.j = KeyEvent.normalizeMetaState(i2);
        this.a.onItemsChanged(false);
        return this;
    }

    public MenuItem setShortcut(char c2, char c3) {
        this.i = c2;
        this.k = Character.toLowerCase(c3);
        this.a.onItemsChanged(false);
        return this;
    }

    public MenuItem setShortcut(char c2, char c3, int i2, int i3) {
        this.i = c2;
        this.j = KeyEvent.normalizeMetaState(i2);
        this.k = Character.toLowerCase(c3);
        this.l = KeyEvent.normalizeMetaState(i3);
        this.a.onItemsChanged(false);
        return this;
    }

    /* access modifiers changed from: 0000 */
    public char a() {
        return this.a.isQwertyMode() ? this.k : this.i;
    }

    /* access modifiers changed from: 0000 */
    public String b() {
        char a2 = a();
        if (a2 == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder(F);
        if (a2 == 8) {
            sb.append(H);
        } else if (a2 == 10) {
            sb.append(G);
        } else if (a2 != ' ') {
            sb.append(a2);
        } else {
            sb.append(I);
        }
        return sb.toString();
    }

    /* access modifiers changed from: 0000 */
    public boolean c() {
        return this.a.isShortcutsVisible() && a() != 0;
    }

    public SubMenu getSubMenu() {
        return this.o;
    }

    public boolean hasSubMenu() {
        return this.o != null;
    }

    public void setSubMenu(SubMenuBuilder subMenuBuilder) {
        this.o = subMenuBuilder;
        subMenuBuilder.setHeaderTitle(getTitle());
    }

    @CapturedViewProperty
    public CharSequence getTitle() {
        return this.f;
    }

    /* access modifiers changed from: 0000 */
    public CharSequence a(ItemView itemView) {
        if (itemView == null || !itemView.prefersCondensedTitle()) {
            return getTitle();
        }
        return getTitleCondensed();
    }

    public MenuItem setTitle(CharSequence charSequence) {
        this.f = charSequence;
        this.a.onItemsChanged(false);
        if (this.o != null) {
            this.o.setHeaderTitle(charSequence);
        }
        return this;
    }

    public MenuItem setTitle(int i2) {
        return setTitle((CharSequence) this.a.getContext().getString(i2));
    }

    public CharSequence getTitleCondensed() {
        CharSequence charSequence = this.g != null ? this.g : this.f;
        return (VERSION.SDK_INT >= 18 || charSequence == null || (charSequence instanceof String)) ? charSequence : charSequence.toString();
    }

    public MenuItem setTitleCondensed(CharSequence charSequence) {
        this.g = charSequence;
        if (charSequence == null) {
            CharSequence charSequence2 = this.f;
        }
        this.a.onItemsChanged(false);
        return this;
    }

    public Drawable getIcon() {
        if (this.m != null) {
            return a(this.m);
        }
        if (this.n == 0) {
            return null;
        }
        Drawable drawable = AppCompatResources.getDrawable(this.a.getContext(), this.n);
        this.n = 0;
        this.m = drawable;
        return a(drawable);
    }

    public MenuItem setIcon(Drawable drawable) {
        this.n = 0;
        this.m = drawable;
        this.x = true;
        this.a.onItemsChanged(false);
        return this;
    }

    public MenuItem setIcon(int i2) {
        this.m = null;
        this.n = i2;
        this.x = true;
        this.a.onItemsChanged(false);
        return this;
    }

    public MenuItem setIconTintList(@Nullable ColorStateList colorStateList) {
        this.t = colorStateList;
        this.v = true;
        this.x = true;
        this.a.onItemsChanged(false);
        return this;
    }

    public ColorStateList getIconTintList() {
        return this.t;
    }

    public MenuItem setIconTintMode(Mode mode) {
        this.u = mode;
        this.w = true;
        this.x = true;
        this.a.onItemsChanged(false);
        return this;
    }

    public Mode getIconTintMode() {
        return this.u;
    }

    private Drawable a(Drawable drawable) {
        if (drawable != null && this.x && (this.v || this.w)) {
            drawable = DrawableCompat.wrap(drawable).mutate();
            if (this.v) {
                DrawableCompat.setTintList(drawable, this.t);
            }
            if (this.w) {
                DrawableCompat.setTintMode(drawable, this.u);
            }
            this.x = false;
        }
        return drawable;
    }

    public boolean isCheckable() {
        return (this.y & 1) == 1;
    }

    public MenuItem setCheckable(boolean z2) {
        int i2 = this.y;
        this.y = z2 | (this.y & true) ? 1 : 0;
        if (i2 != this.y) {
            this.a.onItemsChanged(false);
        }
        return this;
    }

    public void setExclusiveCheckable(boolean z2) {
        this.y = (z2 ? 4 : 0) | (this.y & -5);
    }

    public boolean isExclusiveCheckable() {
        return (this.y & 4) != 0;
    }

    public boolean isChecked() {
        return (this.y & 2) == 2;
    }

    public MenuItem setChecked(boolean z2) {
        if ((this.y & 4) != 0) {
            this.a.a((MenuItem) this);
        } else {
            a(z2);
        }
        return this;
    }

    /* access modifiers changed from: 0000 */
    public void a(boolean z2) {
        int i2 = this.y;
        this.y = (z2 ? 2 : 0) | (this.y & -3);
        if (i2 != this.y) {
            this.a.onItemsChanged(false);
        }
    }

    public boolean isVisible() {
        boolean z2 = false;
        if (this.B == null || !this.B.overridesItemVisibility()) {
            if ((this.y & 8) == 0) {
                z2 = true;
            }
            return z2;
        }
        if ((this.y & 8) == 0 && this.B.isVisible()) {
            z2 = true;
        }
        return z2;
    }

    /* access modifiers changed from: 0000 */
    public boolean b(boolean z2) {
        int i2 = this.y;
        this.y = (z2 ? 0 : 8) | (this.y & -9);
        if (i2 != this.y) {
            return true;
        }
        return false;
    }

    public MenuItem setVisible(boolean z2) {
        if (b(z2)) {
            this.a.a(this);
        }
        return this;
    }

    public MenuItem setOnMenuItemClickListener(OnMenuItemClickListener onMenuItemClickListener) {
        this.q = onMenuItemClickListener;
        return this;
    }

    public String toString() {
        if (this.f != null) {
            return this.f.toString();
        }
        return null;
    }

    /* access modifiers changed from: 0000 */
    public void a(ContextMenuInfo contextMenuInfo) {
        this.E = contextMenuInfo;
    }

    public ContextMenuInfo getMenuInfo() {
        return this.E;
    }

    public void actionFormatChanged() {
        this.a.b(this);
    }

    public boolean shouldShowIcon() {
        return this.a.b();
    }

    public boolean isActionButton() {
        return (this.y & 32) == 32;
    }

    public boolean requestsActionButton() {
        return (this.z & 1) == 1;
    }

    public boolean requiresActionButton() {
        return (this.z & 2) == 2;
    }

    public void setIsActionButton(boolean z2) {
        if (z2) {
            this.y |= 32;
        } else {
            this.y &= -33;
        }
    }

    public boolean showsTextAsAction() {
        return (this.z & 4) == 4;
    }

    public void setShowAsAction(int i2) {
        switch (i2 & 3) {
            case 0:
            case 1:
            case 2:
                this.z = i2;
                this.a.b(this);
                return;
            default:
                throw new IllegalArgumentException("SHOW_AS_ACTION_ALWAYS, SHOW_AS_ACTION_IF_ROOM, and SHOW_AS_ACTION_NEVER are mutually exclusive.");
        }
    }

    public SupportMenuItem setActionView(View view) {
        this.A = view;
        this.B = null;
        if (view != null && view.getId() == -1 && this.b > 0) {
            view.setId(this.b);
        }
        this.a.b(this);
        return this;
    }

    public SupportMenuItem setActionView(int i2) {
        Context context = this.a.getContext();
        setActionView(LayoutInflater.from(context).inflate(i2, new LinearLayout(context), false));
        return this;
    }

    public View getActionView() {
        if (this.A != null) {
            return this.A;
        }
        if (this.B == null) {
            return null;
        }
        this.A = this.B.onCreateActionView(this);
        return this.A;
    }

    public MenuItem setActionProvider(android.view.ActionProvider actionProvider) {
        throw new UnsupportedOperationException("This is not supported, use MenuItemCompat.setActionProvider()");
    }

    public android.view.ActionProvider getActionProvider() {
        throw new UnsupportedOperationException("This is not supported, use MenuItemCompat.getActionProvider()");
    }

    public ActionProvider getSupportActionProvider() {
        return this.B;
    }

    public SupportMenuItem setSupportActionProvider(ActionProvider actionProvider) {
        if (this.B != null) {
            this.B.reset();
        }
        this.A = null;
        this.B = actionProvider;
        this.a.onItemsChanged(true);
        if (this.B != null) {
            this.B.setVisibilityListener(new VisibilityListener() {
                public void onActionProviderVisibilityChanged(boolean z) {
                    MenuItemImpl.this.a.a(MenuItemImpl.this);
                }
            });
        }
        return this;
    }

    public SupportMenuItem setShowAsActionFlags(int i2) {
        setShowAsAction(i2);
        return this;
    }

    public boolean expandActionView() {
        if (!hasCollapsibleActionView()) {
            return false;
        }
        if (this.C == null || this.C.onMenuItemActionExpand(this)) {
            return this.a.expandItemActionView(this);
        }
        return false;
    }

    public boolean collapseActionView() {
        if ((this.z & 8) == 0) {
            return false;
        }
        if (this.A == null) {
            return true;
        }
        if (this.C == null || this.C.onMenuItemActionCollapse(this)) {
            return this.a.collapseItemActionView(this);
        }
        return false;
    }

    public boolean hasCollapsibleActionView() {
        boolean z2 = false;
        if ((this.z & 8) == 0) {
            return false;
        }
        if (this.A == null && this.B != null) {
            this.A = this.B.onCreateActionView(this);
        }
        if (this.A != null) {
            z2 = true;
        }
        return z2;
    }

    public void setActionViewExpanded(boolean z2) {
        this.D = z2;
        this.a.onItemsChanged(false);
    }

    public boolean isActionViewExpanded() {
        return this.D;
    }

    public MenuItem setOnActionExpandListener(OnActionExpandListener onActionExpandListener) {
        this.C = onActionExpandListener;
        return this;
    }

    public SupportMenuItem setContentDescription(CharSequence charSequence) {
        this.r = charSequence;
        this.a.onItemsChanged(false);
        return this;
    }

    public CharSequence getContentDescription() {
        return this.r;
    }

    public SupportMenuItem setTooltipText(CharSequence charSequence) {
        this.s = charSequence;
        this.a.onItemsChanged(false);
        return this;
    }

    public CharSequence getTooltipText() {
        return this.s;
    }
}
