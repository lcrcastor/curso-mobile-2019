package android.support.v7.view.menu;

import android.content.Context;
import android.content.res.Resources;
import android.os.Parcelable;
import android.support.v7.appcompat.R;
import android.support.v7.view.menu.MenuPresenter.Callback;
import android.support.v7.widget.MenuPopupWindow;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnAttachStateChangeListener;
import android.view.View.OnKeyListener;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;

final class StandardMenuPopup extends MenuPopup implements MenuPresenter, OnKeyListener, OnItemClickListener, OnDismissListener {
    final MenuPopupWindow a;
    View b;
    private final Context c;
    private final MenuBuilder d;
    private final MenuAdapter e;
    private final boolean f;
    private final int g;
    private final int h;
    private final int i;
    /* access modifiers changed from: private */
    public final OnGlobalLayoutListener j = new OnGlobalLayoutListener() {
        public void onGlobalLayout() {
            if (StandardMenuPopup.this.isShowing() && !StandardMenuPopup.this.a.isModal()) {
                View view = StandardMenuPopup.this.b;
                if (view == null || !view.isShown()) {
                    StandardMenuPopup.this.dismiss();
                } else {
                    StandardMenuPopup.this.a.show();
                }
            }
        }
    };
    private final OnAttachStateChangeListener k = new OnAttachStateChangeListener() {
        public void onViewAttachedToWindow(View view) {
        }

        public void onViewDetachedFromWindow(View view) {
            if (StandardMenuPopup.this.o != null) {
                if (!StandardMenuPopup.this.o.isAlive()) {
                    StandardMenuPopup.this.o = view.getViewTreeObserver();
                }
                StandardMenuPopup.this.o.removeGlobalOnLayoutListener(StandardMenuPopup.this.j);
            }
            view.removeOnAttachStateChangeListener(this);
        }
    };
    private OnDismissListener l;
    private View m;
    private Callback n;
    /* access modifiers changed from: private */
    public ViewTreeObserver o;
    private boolean p;
    private boolean q;
    private int r;
    private int s = 0;
    private boolean t;

    public void a(MenuBuilder menuBuilder) {
    }

    public boolean flagActionItems() {
        return false;
    }

    public void onRestoreInstanceState(Parcelable parcelable) {
    }

    public Parcelable onSaveInstanceState() {
        return null;
    }

    public StandardMenuPopup(Context context, MenuBuilder menuBuilder, View view, int i2, int i3, boolean z) {
        this.c = context;
        this.d = menuBuilder;
        this.f = z;
        this.e = new MenuAdapter(menuBuilder, LayoutInflater.from(context), this.f);
        this.h = i2;
        this.i = i3;
        Resources resources = context.getResources();
        this.g = Math.max(resources.getDisplayMetrics().widthPixels / 2, resources.getDimensionPixelSize(R.dimen.abc_config_prefDialogWidth));
        this.m = view;
        this.a = new MenuPopupWindow(this.c, null, this.h, this.i);
        menuBuilder.addMenuPresenter(this, context);
    }

    public void a(boolean z) {
        this.e.setForceShowIcon(z);
    }

    public void a(int i2) {
        this.s = i2;
    }

    private boolean c() {
        if (isShowing()) {
            return true;
        }
        if (this.p || this.m == null) {
            return false;
        }
        this.b = this.m;
        this.a.setOnDismissListener(this);
        this.a.setOnItemClickListener(this);
        this.a.setModal(true);
        View view = this.b;
        boolean z = this.o == null;
        this.o = view.getViewTreeObserver();
        if (z) {
            this.o.addOnGlobalLayoutListener(this.j);
        }
        view.addOnAttachStateChangeListener(this.k);
        this.a.setAnchorView(view);
        this.a.setDropDownGravity(this.s);
        if (!this.q) {
            this.r = a(this.e, null, this.c, this.g);
            this.q = true;
        }
        this.a.setContentWidth(this.r);
        this.a.setInputMethodMode(2);
        this.a.setEpicenterBounds(b());
        this.a.show();
        ListView listView = this.a.getListView();
        listView.setOnKeyListener(this);
        if (this.t && this.d.getHeaderTitle() != null) {
            FrameLayout frameLayout = (FrameLayout) LayoutInflater.from(this.c).inflate(R.layout.abc_popup_menu_header_item_layout, listView, false);
            TextView textView = (TextView) frameLayout.findViewById(16908310);
            if (textView != null) {
                textView.setText(this.d.getHeaderTitle());
            }
            frameLayout.setEnabled(false);
            listView.addHeaderView(frameLayout, null, false);
        }
        this.a.setAdapter(this.e);
        this.a.show();
        return true;
    }

    public void show() {
        if (!c()) {
            throw new IllegalStateException("StandardMenuPopup cannot be used without an anchor");
        }
    }

    public void dismiss() {
        if (isShowing()) {
            this.a.dismiss();
        }
    }

    public boolean isShowing() {
        return !this.p && this.a.isShowing();
    }

    public void onDismiss() {
        this.p = true;
        this.d.close();
        if (this.o != null) {
            if (!this.o.isAlive()) {
                this.o = this.b.getViewTreeObserver();
            }
            this.o.removeGlobalOnLayoutListener(this.j);
            this.o = null;
        }
        this.b.removeOnAttachStateChangeListener(this.k);
        if (this.l != null) {
            this.l.onDismiss();
        }
    }

    public void updateMenuView(boolean z) {
        this.q = false;
        if (this.e != null) {
            this.e.notifyDataSetChanged();
        }
    }

    public void setCallback(Callback callback) {
        this.n = callback;
    }

    public boolean onSubMenuSelected(SubMenuBuilder subMenuBuilder) {
        if (subMenuBuilder.hasVisibleItems()) {
            MenuPopupHelper menuPopupHelper = new MenuPopupHelper(this.c, subMenuBuilder, this.b, this.f, this.h, this.i);
            menuPopupHelper.setPresenterCallback(this.n);
            menuPopupHelper.setForceShowIcon(MenuPopup.b((MenuBuilder) subMenuBuilder));
            menuPopupHelper.setGravity(this.s);
            menuPopupHelper.setOnDismissListener(this.l);
            this.l = null;
            this.d.close(false);
            if (menuPopupHelper.tryShow(this.a.getHorizontalOffset(), this.a.getVerticalOffset())) {
                if (this.n != null) {
                    this.n.onOpenSubMenu(subMenuBuilder);
                }
                return true;
            }
        }
        return false;
    }

    public void onCloseMenu(MenuBuilder menuBuilder, boolean z) {
        if (menuBuilder == this.d) {
            dismiss();
            if (this.n != null) {
                this.n.onCloseMenu(menuBuilder, z);
            }
        }
    }

    public void a(View view) {
        this.m = view;
    }

    public boolean onKey(View view, int i2, KeyEvent keyEvent) {
        if (keyEvent.getAction() != 1 || i2 != 82) {
            return false;
        }
        dismiss();
        return true;
    }

    public void a(OnDismissListener onDismissListener) {
        this.l = onDismissListener;
    }

    public ListView getListView() {
        return this.a.getListView();
    }

    public void b(int i2) {
        this.a.setHorizontalOffset(i2);
    }

    public void c(int i2) {
        this.a.setVerticalOffset(i2);
    }

    public void b(boolean z) {
        this.t = z;
    }
}
