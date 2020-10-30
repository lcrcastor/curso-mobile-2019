package android.support.v7.view.menu;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.Parcelable;
import android.os.SystemClock;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.appcompat.R;
import android.support.v7.view.menu.MenuPresenter.Callback;
import android.support.v7.widget.MenuItemHoverListener;
import android.support.v7.widget.MenuPopupWindow;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnAttachStateChangeListener;
import android.view.View.OnKeyListener;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.FrameLayout;
import android.widget.HeaderViewListAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

final class CascadingMenuPopup extends MenuPopup implements MenuPresenter, OnKeyListener, OnDismissListener {
    final Handler a;
    final List<CascadingMenuInfo> b = new ArrayList();
    View c;
    boolean d;
    private final Context e;
    private final int f;
    private final int g;
    private final int h;
    private final boolean i;
    private final List<MenuBuilder> j = new ArrayList();
    /* access modifiers changed from: private */
    public final OnGlobalLayoutListener k = new OnGlobalLayoutListener() {
        public void onGlobalLayout() {
            if (CascadingMenuPopup.this.isShowing() && CascadingMenuPopup.this.b.size() > 0 && !((CascadingMenuInfo) CascadingMenuPopup.this.b.get(0)).a.isModal()) {
                View view = CascadingMenuPopup.this.c;
                if (view == null || !view.isShown()) {
                    CascadingMenuPopup.this.dismiss();
                    return;
                }
                for (CascadingMenuInfo cascadingMenuInfo : CascadingMenuPopup.this.b) {
                    cascadingMenuInfo.a.show();
                }
            }
        }
    };
    private final OnAttachStateChangeListener l = new OnAttachStateChangeListener() {
        public void onViewAttachedToWindow(View view) {
        }

        public void onViewDetachedFromWindow(View view) {
            if (CascadingMenuPopup.this.y != null) {
                if (!CascadingMenuPopup.this.y.isAlive()) {
                    CascadingMenuPopup.this.y = view.getViewTreeObserver();
                }
                CascadingMenuPopup.this.y.removeGlobalOnLayoutListener(CascadingMenuPopup.this.k);
            }
            view.removeOnAttachStateChangeListener(this);
        }
    };
    private final MenuItemHoverListener m = new MenuItemHoverListener() {
        public void onItemHoverExit(@NonNull MenuBuilder menuBuilder, @NonNull MenuItem menuItem) {
            CascadingMenuPopup.this.a.removeCallbacksAndMessages(menuBuilder);
        }

        public void onItemHoverEnter(@NonNull final MenuBuilder menuBuilder, @NonNull final MenuItem menuItem) {
            final CascadingMenuInfo cascadingMenuInfo = null;
            CascadingMenuPopup.this.a.removeCallbacksAndMessages(null);
            int size = CascadingMenuPopup.this.b.size();
            int i = 0;
            while (true) {
                if (i >= size) {
                    i = -1;
                    break;
                } else if (menuBuilder == ((CascadingMenuInfo) CascadingMenuPopup.this.b.get(i)).b) {
                    break;
                } else {
                    i++;
                }
            }
            if (i != -1) {
                int i2 = i + 1;
                if (i2 < CascadingMenuPopup.this.b.size()) {
                    cascadingMenuInfo = (CascadingMenuInfo) CascadingMenuPopup.this.b.get(i2);
                }
                CascadingMenuPopup.this.a.postAtTime(new Runnable() {
                    public void run() {
                        if (cascadingMenuInfo != null) {
                            CascadingMenuPopup.this.d = true;
                            cascadingMenuInfo.b.close(false);
                            CascadingMenuPopup.this.d = false;
                        }
                        if (menuItem.isEnabled() && menuItem.hasSubMenu()) {
                            menuBuilder.performItemAction(menuItem, 4);
                        }
                    }
                }, menuBuilder, SystemClock.uptimeMillis() + 200);
            }
        }
    };
    private int n = 0;
    private int o = 0;
    private View p;
    private int q;
    private boolean r;
    private boolean s;
    private int t;
    private int u;
    private boolean v;
    private boolean w;
    private Callback x;
    /* access modifiers changed from: private */
    public ViewTreeObserver y;
    private OnDismissListener z;

    static class CascadingMenuInfo {
        public final MenuPopupWindow a;
        public final MenuBuilder b;
        public final int c;

        public CascadingMenuInfo(@NonNull MenuPopupWindow menuPopupWindow, @NonNull MenuBuilder menuBuilder, int i) {
            this.a = menuPopupWindow;
            this.b = menuBuilder;
            this.c = i;
        }

        public ListView a() {
            return this.a.getListView();
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface HorizPosition {
    }

    /* access modifiers changed from: protected */
    public boolean a() {
        return false;
    }

    public boolean flagActionItems() {
        return false;
    }

    public void onRestoreInstanceState(Parcelable parcelable) {
    }

    public Parcelable onSaveInstanceState() {
        return null;
    }

    public CascadingMenuPopup(@NonNull Context context, @NonNull View view, @AttrRes int i2, @StyleRes int i3, boolean z2) {
        this.e = context;
        this.p = view;
        this.g = i2;
        this.h = i3;
        this.i = z2;
        this.v = false;
        this.q = d();
        Resources resources = context.getResources();
        this.f = Math.max(resources.getDisplayMetrics().widthPixels / 2, resources.getDimensionPixelSize(R.dimen.abc_config_prefDialogWidth));
        this.a = new Handler();
    }

    public void a(boolean z2) {
        this.v = z2;
    }

    private MenuPopupWindow c() {
        MenuPopupWindow menuPopupWindow = new MenuPopupWindow(this.e, null, this.g, this.h);
        menuPopupWindow.setHoverListener(this.m);
        menuPopupWindow.setOnItemClickListener(this);
        menuPopupWindow.setOnDismissListener(this);
        menuPopupWindow.setAnchorView(this.p);
        menuPopupWindow.setDropDownGravity(this.o);
        menuPopupWindow.setModal(true);
        menuPopupWindow.setInputMethodMode(2);
        return menuPopupWindow;
    }

    public void show() {
        if (!isShowing()) {
            for (MenuBuilder c2 : this.j) {
                c(c2);
            }
            this.j.clear();
            this.c = this.p;
            if (this.c != null) {
                boolean z2 = this.y == null;
                this.y = this.c.getViewTreeObserver();
                if (z2) {
                    this.y.addOnGlobalLayoutListener(this.k);
                }
                this.c.addOnAttachStateChangeListener(this.l);
            }
        }
    }

    public void dismiss() {
        int size = this.b.size();
        if (size > 0) {
            CascadingMenuInfo[] cascadingMenuInfoArr = (CascadingMenuInfo[]) this.b.toArray(new CascadingMenuInfo[size]);
            for (int i2 = size - 1; i2 >= 0; i2--) {
                CascadingMenuInfo cascadingMenuInfo = cascadingMenuInfoArr[i2];
                if (cascadingMenuInfo.a.isShowing()) {
                    cascadingMenuInfo.a.dismiss();
                }
            }
        }
    }

    public boolean onKey(View view, int i2, KeyEvent keyEvent) {
        if (keyEvent.getAction() != 1 || i2 != 82) {
            return false;
        }
        dismiss();
        return true;
    }

    private int d() {
        return ViewCompat.getLayoutDirection(this.p) == 1 ? 0 : 1;
    }

    private int d(int i2) {
        ListView a2 = ((CascadingMenuInfo) this.b.get(this.b.size() - 1)).a();
        int[] iArr = new int[2];
        a2.getLocationOnScreen(iArr);
        Rect rect = new Rect();
        this.c.getWindowVisibleDisplayFrame(rect);
        if (this.q == 1) {
            if (iArr[0] + a2.getWidth() + i2 > rect.right) {
                return 0;
            }
            return 1;
        } else if (iArr[0] - i2 < 0) {
            return 1;
        } else {
            return 0;
        }
    }

    public void a(MenuBuilder menuBuilder) {
        menuBuilder.addMenuPresenter(this, this.e);
        if (isShowing()) {
            c(menuBuilder);
        } else {
            this.j.add(menuBuilder);
        }
    }

    private void c(@NonNull MenuBuilder menuBuilder) {
        View view;
        CascadingMenuInfo cascadingMenuInfo;
        int i2;
        int i3;
        LayoutInflater from = LayoutInflater.from(this.e);
        MenuAdapter menuAdapter = new MenuAdapter(menuBuilder, from, this.i);
        if (!isShowing() && this.v) {
            menuAdapter.setForceShowIcon(true);
        } else if (isShowing()) {
            menuAdapter.setForceShowIcon(MenuPopup.b(menuBuilder));
        }
        int a2 = a(menuAdapter, null, this.e, this.f);
        MenuPopupWindow c2 = c();
        c2.setAdapter(menuAdapter);
        c2.setContentWidth(a2);
        c2.setDropDownGravity(this.o);
        if (this.b.size() > 0) {
            cascadingMenuInfo = (CascadingMenuInfo) this.b.get(this.b.size() - 1);
            view = a(cascadingMenuInfo, menuBuilder);
        } else {
            cascadingMenuInfo = null;
            view = null;
        }
        if (view != null) {
            c2.setTouchModal(false);
            c2.setEnterTransition(null);
            int d2 = d(a2);
            boolean z2 = d2 == 1;
            this.q = d2;
            if (VERSION.SDK_INT >= 26) {
                c2.setAnchorView(view);
                i3 = 0;
                i2 = 0;
            } else {
                int[] iArr = new int[2];
                this.p.getLocationOnScreen(iArr);
                int[] iArr2 = new int[2];
                view.getLocationOnScreen(iArr2);
                if ((this.o & 7) == 5) {
                    iArr[0] = iArr[0] + this.p.getWidth();
                    iArr2[0] = iArr2[0] + view.getWidth();
                }
                i2 = iArr2[0] - iArr[0];
                i3 = iArr2[1] - iArr[1];
            }
            int i4 = (this.o & 5) == 5 ? z2 ? i2 + a2 : i2 - view.getWidth() : z2 ? i2 + view.getWidth() : i2 - a2;
            c2.setHorizontalOffset(i4);
            c2.setOverlapAnchor(true);
            c2.setVerticalOffset(i3);
        } else {
            if (this.r) {
                c2.setHorizontalOffset(this.t);
            }
            if (this.s) {
                c2.setVerticalOffset(this.u);
            }
            c2.setEpicenterBounds(b());
        }
        this.b.add(new CascadingMenuInfo(c2, menuBuilder, this.q));
        c2.show();
        ListView listView = c2.getListView();
        listView.setOnKeyListener(this);
        if (cascadingMenuInfo == null && this.w && menuBuilder.getHeaderTitle() != null) {
            FrameLayout frameLayout = (FrameLayout) from.inflate(R.layout.abc_popup_menu_header_item_layout, listView, false);
            TextView textView = (TextView) frameLayout.findViewById(16908310);
            frameLayout.setEnabled(false);
            textView.setText(menuBuilder.getHeaderTitle());
            listView.addHeaderView(frameLayout, null, false);
            c2.show();
        }
    }

    private MenuItem a(@NonNull MenuBuilder menuBuilder, @NonNull MenuBuilder menuBuilder2) {
        int size = menuBuilder.size();
        for (int i2 = 0; i2 < size; i2++) {
            MenuItem item = menuBuilder.getItem(i2);
            if (item.hasSubMenu() && menuBuilder2 == item.getSubMenu()) {
                return item;
            }
        }
        return null;
    }

    @Nullable
    private View a(@NonNull CascadingMenuInfo cascadingMenuInfo, @NonNull MenuBuilder menuBuilder) {
        int i2;
        MenuAdapter menuAdapter;
        MenuItem a2 = a(cascadingMenuInfo.b, menuBuilder);
        if (a2 == null) {
            return null;
        }
        ListView a3 = cascadingMenuInfo.a();
        ListAdapter adapter = a3.getAdapter();
        int i3 = 0;
        if (adapter instanceof HeaderViewListAdapter) {
            HeaderViewListAdapter headerViewListAdapter = (HeaderViewListAdapter) adapter;
            i2 = headerViewListAdapter.getHeadersCount();
            menuAdapter = (MenuAdapter) headerViewListAdapter.getWrappedAdapter();
        } else {
            menuAdapter = (MenuAdapter) adapter;
            i2 = 0;
        }
        int count = menuAdapter.getCount();
        while (true) {
            if (i3 >= count) {
                i3 = -1;
                break;
            } else if (a2 == menuAdapter.getItem(i3)) {
                break;
            } else {
                i3++;
            }
        }
        if (i3 == -1) {
            return null;
        }
        int firstVisiblePosition = (i3 + i2) - a3.getFirstVisiblePosition();
        if (firstVisiblePosition < 0 || firstVisiblePosition >= a3.getChildCount()) {
            return null;
        }
        return a3.getChildAt(firstVisiblePosition);
    }

    public boolean isShowing() {
        return this.b.size() > 0 && ((CascadingMenuInfo) this.b.get(0)).a.isShowing();
    }

    public void onDismiss() {
        CascadingMenuInfo cascadingMenuInfo;
        int size = this.b.size();
        int i2 = 0;
        while (true) {
            if (i2 >= size) {
                cascadingMenuInfo = null;
                break;
            }
            cascadingMenuInfo = (CascadingMenuInfo) this.b.get(i2);
            if (!cascadingMenuInfo.a.isShowing()) {
                break;
            }
            i2++;
        }
        if (cascadingMenuInfo != null) {
            cascadingMenuInfo.b.close(false);
        }
    }

    public void updateMenuView(boolean z2) {
        for (CascadingMenuInfo a2 : this.b) {
            a(a2.a().getAdapter()).notifyDataSetChanged();
        }
    }

    public void setCallback(Callback callback) {
        this.x = callback;
    }

    public boolean onSubMenuSelected(SubMenuBuilder subMenuBuilder) {
        for (CascadingMenuInfo cascadingMenuInfo : this.b) {
            if (subMenuBuilder == cascadingMenuInfo.b) {
                cascadingMenuInfo.a().requestFocus();
                return true;
            }
        }
        if (!subMenuBuilder.hasVisibleItems()) {
            return false;
        }
        a((MenuBuilder) subMenuBuilder);
        if (this.x != null) {
            this.x.onOpenSubMenu(subMenuBuilder);
        }
        return true;
    }

    private int d(@NonNull MenuBuilder menuBuilder) {
        int size = this.b.size();
        for (int i2 = 0; i2 < size; i2++) {
            if (menuBuilder == ((CascadingMenuInfo) this.b.get(i2)).b) {
                return i2;
            }
        }
        return -1;
    }

    public void onCloseMenu(MenuBuilder menuBuilder, boolean z2) {
        int d2 = d(menuBuilder);
        if (d2 >= 0) {
            int i2 = d2 + 1;
            if (i2 < this.b.size()) {
                ((CascadingMenuInfo) this.b.get(i2)).b.close(false);
            }
            CascadingMenuInfo cascadingMenuInfo = (CascadingMenuInfo) this.b.remove(d2);
            cascadingMenuInfo.b.removeMenuPresenter(this);
            if (this.d) {
                cascadingMenuInfo.a.setExitTransition(null);
                cascadingMenuInfo.a.setAnimationStyle(0);
            }
            cascadingMenuInfo.a.dismiss();
            int size = this.b.size();
            if (size > 0) {
                this.q = ((CascadingMenuInfo) this.b.get(size - 1)).c;
            } else {
                this.q = d();
            }
            if (size == 0) {
                dismiss();
                if (this.x != null) {
                    this.x.onCloseMenu(menuBuilder, true);
                }
                if (this.y != null) {
                    if (this.y.isAlive()) {
                        this.y.removeGlobalOnLayoutListener(this.k);
                    }
                    this.y = null;
                }
                this.c.removeOnAttachStateChangeListener(this.l);
                this.z.onDismiss();
            } else if (z2) {
                ((CascadingMenuInfo) this.b.get(0)).b.close(false);
            }
        }
    }

    public void a(int i2) {
        if (this.n != i2) {
            this.n = i2;
            this.o = GravityCompat.getAbsoluteGravity(i2, ViewCompat.getLayoutDirection(this.p));
        }
    }

    public void a(@NonNull View view) {
        if (this.p != view) {
            this.p = view;
            this.o = GravityCompat.getAbsoluteGravity(this.n, ViewCompat.getLayoutDirection(this.p));
        }
    }

    public void a(OnDismissListener onDismissListener) {
        this.z = onDismissListener;
    }

    public ListView getListView() {
        if (this.b.isEmpty()) {
            return null;
        }
        return ((CascadingMenuInfo) this.b.get(this.b.size() - 1)).a();
    }

    public void b(int i2) {
        this.r = true;
        this.t = i2;
    }

    public void c(int i2) {
        this.s = true;
        this.u = i2;
    }

    public void b(boolean z2) {
        this.w = z2;
    }
}
