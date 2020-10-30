package android.support.v7.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.os.Parcelable;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.v4.view.NestedScrollingParent;
import android.support.v4.view.NestedScrollingParentHelper;
import android.support.v4.view.ViewCompat;
import android.support.v7.appcompat.R;
import android.support.v7.view.menu.MenuPresenter;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.ViewPropertyAnimator;
import android.view.Window.Callback;
import android.widget.OverScroller;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import io.fabric.sdk.android.services.settings.SettingsJsonConstants;

@RestrictTo({Scope.LIBRARY_GROUP})
public class ActionBarOverlayLayout extends ViewGroup implements NestedScrollingParent, DecorContentParent {
    static final int[] e = {R.attr.actionBarSize, 16842841};
    private final Runnable A;
    private final Runnable B;
    private final NestedScrollingParentHelper C;
    ActionBarContainer a;
    boolean b;
    ViewPropertyAnimator c;
    final AnimatorListenerAdapter d;
    private int f;
    private int g;
    private ContentFrameLayout h;
    private DecorToolbar i;
    private Drawable j;
    private boolean k;
    private boolean l;
    private boolean m;
    private boolean n;
    private int o;
    private int p;
    private final Rect q;
    private final Rect r;
    private final Rect s;
    private final Rect t;
    private final Rect u;
    private final Rect v;
    private final Rect w;
    private ActionBarVisibilityCallback x;
    private final int y;
    private OverScroller z;

    public interface ActionBarVisibilityCallback {
        void enableContentAnimations(boolean z);

        void hideForSystem();

        void onContentScrollStarted();

        void onContentScrollStopped();

        void onWindowVisibilityChanged(int i);

        void showForSystem();
    }

    public static class LayoutParams extends MarginLayoutParams {
        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
        }

        public LayoutParams(int i, int i2) {
            super(i, i2);
        }

        public LayoutParams(android.view.ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
        }

        public LayoutParams(MarginLayoutParams marginLayoutParams) {
            super(marginLayoutParams);
        }
    }

    public boolean onNestedPreFling(View view, float f2, float f3) {
        return false;
    }

    public void onNestedPreScroll(View view, int i2, int i3, int[] iArr) {
    }

    public void setShowingForActionMode(boolean z2) {
    }

    public void setUiOptions(int i2) {
    }

    public boolean shouldDelayChildPressedState() {
        return false;
    }

    public ActionBarOverlayLayout(Context context) {
        this(context, null);
    }

    public ActionBarOverlayLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.g = 0;
        this.q = new Rect();
        this.r = new Rect();
        this.s = new Rect();
        this.t = new Rect();
        this.u = new Rect();
        this.v = new Rect();
        this.w = new Rect();
        this.y = SettingsJsonConstants.ANALYTICS_FLUSH_INTERVAL_SECS_DEFAULT;
        this.d = new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator animator) {
                ActionBarOverlayLayout.this.c = null;
                ActionBarOverlayLayout.this.b = false;
            }

            public void onAnimationCancel(Animator animator) {
                ActionBarOverlayLayout.this.c = null;
                ActionBarOverlayLayout.this.b = false;
            }
        };
        this.A = new Runnable() {
            public void run() {
                ActionBarOverlayLayout.this.b();
                ActionBarOverlayLayout.this.c = ActionBarOverlayLayout.this.a.animate().translationY(BitmapDescriptorFactory.HUE_RED).setListener(ActionBarOverlayLayout.this.d);
            }
        };
        this.B = new Runnable() {
            public void run() {
                ActionBarOverlayLayout.this.b();
                ActionBarOverlayLayout.this.c = ActionBarOverlayLayout.this.a.animate().translationY((float) (-ActionBarOverlayLayout.this.a.getHeight())).setListener(ActionBarOverlayLayout.this.d);
            }
        };
        a(context);
        this.C = new NestedScrollingParentHelper(this);
    }

    private void a(Context context) {
        TypedArray obtainStyledAttributes = getContext().getTheme().obtainStyledAttributes(e);
        boolean z2 = false;
        this.f = obtainStyledAttributes.getDimensionPixelSize(0, 0);
        this.j = obtainStyledAttributes.getDrawable(1);
        setWillNotDraw(this.j == null);
        obtainStyledAttributes.recycle();
        if (context.getApplicationInfo().targetSdkVersion < 19) {
            z2 = true;
        }
        this.k = z2;
        this.z = new OverScroller(context);
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        b();
    }

    public void setActionBarVisibilityCallback(ActionBarVisibilityCallback actionBarVisibilityCallback) {
        this.x = actionBarVisibilityCallback;
        if (getWindowToken() != null) {
            this.x.onWindowVisibilityChanged(this.g);
            if (this.p != 0) {
                onWindowSystemUiVisibilityChanged(this.p);
                ViewCompat.requestApplyInsets(this);
            }
        }
    }

    public void setOverlayMode(boolean z2) {
        this.l = z2;
        this.k = z2 && getContext().getApplicationInfo().targetSdkVersion < 19;
    }

    public boolean isInOverlayMode() {
        return this.l;
    }

    public void setHasNonEmbeddedTabs(boolean z2) {
        this.m = z2;
    }

    /* access modifiers changed from: protected */
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        a(getContext());
        ViewCompat.requestApplyInsets(this);
    }

    public void onWindowSystemUiVisibilityChanged(int i2) {
        if (VERSION.SDK_INT >= 16) {
            super.onWindowSystemUiVisibilityChanged(i2);
        }
        a();
        int i3 = this.p ^ i2;
        this.p = i2;
        boolean z2 = false;
        boolean z3 = (i2 & 4) == 0;
        if ((i2 & 256) != 0) {
            z2 = true;
        }
        if (this.x != null) {
            this.x.enableContentAnimations(!z2);
            if (z3 || !z2) {
                this.x.showForSystem();
            } else {
                this.x.hideForSystem();
            }
        }
        if ((i3 & 256) != 0 && this.x != null) {
            ViewCompat.requestApplyInsets(this);
        }
    }

    /* access modifiers changed from: protected */
    public void onWindowVisibilityChanged(int i2) {
        super.onWindowVisibilityChanged(i2);
        this.g = i2;
        if (this.x != null) {
            this.x.onWindowVisibilityChanged(i2);
        }
    }

    private boolean a(View view, Rect rect, boolean z2, boolean z3, boolean z4, boolean z5) {
        boolean z6;
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
        if (!z2 || layoutParams.leftMargin == rect.left) {
            z6 = false;
        } else {
            layoutParams.leftMargin = rect.left;
            z6 = true;
        }
        if (z3 && layoutParams.topMargin != rect.top) {
            layoutParams.topMargin = rect.top;
            z6 = true;
        }
        if (z5 && layoutParams.rightMargin != rect.right) {
            layoutParams.rightMargin = rect.right;
            z6 = true;
        }
        if (!z4 || layoutParams.bottomMargin == rect.bottom) {
            return z6;
        }
        layoutParams.bottomMargin = rect.bottom;
        return true;
    }

    /* access modifiers changed from: protected */
    public boolean fitSystemWindows(Rect rect) {
        a();
        int windowSystemUiVisibility = ViewCompat.getWindowSystemUiVisibility(this) & 256;
        boolean a2 = a(this.a, rect, true, true, false, true);
        this.t.set(rect);
        ViewUtils.computeFitSystemWindows(this, this.t, this.q);
        if (!this.u.equals(this.t)) {
            this.u.set(this.t);
            a2 = true;
        }
        if (!this.r.equals(this.q)) {
            this.r.set(this.q);
            a2 = true;
        }
        if (a2) {
            requestLayout();
        }
        return true;
    }

    /* access modifiers changed from: protected */
    public LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(-1, -1);
    }

    public LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(getContext(), attributeSet);
    }

    /* access modifiers changed from: protected */
    public android.view.ViewGroup.LayoutParams generateLayoutParams(android.view.ViewGroup.LayoutParams layoutParams) {
        return new LayoutParams(layoutParams);
    }

    /* access modifiers changed from: protected */
    public boolean checkLayoutParams(android.view.ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof LayoutParams;
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i2, int i3) {
        int i4;
        a();
        measureChildWithMargins(this.a, i2, 0, i3, 0);
        LayoutParams layoutParams = (LayoutParams) this.a.getLayoutParams();
        int max = Math.max(0, this.a.getMeasuredWidth() + layoutParams.leftMargin + layoutParams.rightMargin);
        int max2 = Math.max(0, this.a.getMeasuredHeight() + layoutParams.topMargin + layoutParams.bottomMargin);
        int combineMeasuredStates = View.combineMeasuredStates(0, this.a.getMeasuredState());
        boolean z2 = (ViewCompat.getWindowSystemUiVisibility(this) & 256) != 0;
        if (z2) {
            i4 = this.f;
            if (this.m && this.a.getTabContainer() != null) {
                i4 += this.f;
            }
        } else {
            i4 = this.a.getVisibility() != 8 ? this.a.getMeasuredHeight() : 0;
        }
        this.s.set(this.q);
        this.v.set(this.t);
        if (this.l || z2) {
            this.v.top += i4;
            this.v.bottom += 0;
        } else {
            this.s.top += i4;
            this.s.bottom += 0;
        }
        a(this.h, this.s, true, true, true, true);
        if (!this.w.equals(this.v)) {
            this.w.set(this.v);
            this.h.dispatchFitSystemWindows(this.v);
        }
        measureChildWithMargins(this.h, i2, 0, i3, 0);
        LayoutParams layoutParams2 = (LayoutParams) this.h.getLayoutParams();
        int max3 = Math.max(max, this.h.getMeasuredWidth() + layoutParams2.leftMargin + layoutParams2.rightMargin);
        int max4 = Math.max(max2, this.h.getMeasuredHeight() + layoutParams2.topMargin + layoutParams2.bottomMargin);
        int combineMeasuredStates2 = View.combineMeasuredStates(combineMeasuredStates, this.h.getMeasuredState());
        setMeasuredDimension(View.resolveSizeAndState(Math.max(max3 + getPaddingLeft() + getPaddingRight(), getSuggestedMinimumWidth()), i2, combineMeasuredStates2), View.resolveSizeAndState(Math.max(max4 + getPaddingTop() + getPaddingBottom(), getSuggestedMinimumHeight()), i3, combineMeasuredStates2 << 16));
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z2, int i2, int i3, int i4, int i5) {
        int childCount = getChildCount();
        int paddingLeft = getPaddingLeft();
        getPaddingRight();
        int paddingTop = getPaddingTop();
        getPaddingBottom();
        for (int i6 = 0; i6 < childCount; i6++) {
            View childAt = getChildAt(i6);
            if (childAt.getVisibility() != 8) {
                LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                int i7 = layoutParams.leftMargin + paddingLeft;
                int i8 = layoutParams.topMargin + paddingTop;
                childAt.layout(i7, i8, childAt.getMeasuredWidth() + i7, childAt.getMeasuredHeight() + i8);
            }
        }
    }

    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (this.j != null && !this.k) {
            int bottom = this.a.getVisibility() == 0 ? (int) (((float) this.a.getBottom()) + this.a.getTranslationY() + 0.5f) : 0;
            this.j.setBounds(0, bottom, getWidth(), this.j.getIntrinsicHeight() + bottom);
            this.j.draw(canvas);
        }
    }

    public boolean onStartNestedScroll(View view, View view2, int i2) {
        if ((i2 & 2) == 0 || this.a.getVisibility() != 0) {
            return false;
        }
        return this.n;
    }

    public void onNestedScrollAccepted(View view, View view2, int i2) {
        this.C.onNestedScrollAccepted(view, view2, i2);
        this.o = getActionBarHideOffset();
        b();
        if (this.x != null) {
            this.x.onContentScrollStarted();
        }
    }

    public void onNestedScroll(View view, int i2, int i3, int i4, int i5) {
        this.o += i3;
        setActionBarHideOffset(this.o);
    }

    public void onStopNestedScroll(View view) {
        if (this.n && !this.b) {
            if (this.o <= this.a.getHeight()) {
                c();
            } else {
                d();
            }
        }
        if (this.x != null) {
            this.x.onContentScrollStopped();
        }
    }

    public boolean onNestedFling(View view, float f2, float f3, boolean z2) {
        if (!this.n || !z2) {
            return false;
        }
        if (a(f2, f3)) {
            f();
        } else {
            e();
        }
        this.b = true;
        return true;
    }

    public int getNestedScrollAxes() {
        return this.C.getNestedScrollAxes();
    }

    /* access modifiers changed from: 0000 */
    public void a() {
        if (this.h == null) {
            this.h = (ContentFrameLayout) findViewById(R.id.action_bar_activity_content);
            this.a = (ActionBarContainer) findViewById(R.id.action_bar_container);
            this.i = a(findViewById(R.id.action_bar));
        }
    }

    private DecorToolbar a(View view) {
        if (view instanceof DecorToolbar) {
            return (DecorToolbar) view;
        }
        if (view instanceof Toolbar) {
            return ((Toolbar) view).getWrapper();
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Can't make a decor toolbar out of ");
        sb.append(view.getClass().getSimpleName());
        throw new IllegalStateException(sb.toString());
    }

    public void setHideOnContentScrollEnabled(boolean z2) {
        if (z2 != this.n) {
            this.n = z2;
            if (!z2) {
                b();
                setActionBarHideOffset(0);
            }
        }
    }

    public boolean isHideOnContentScrollEnabled() {
        return this.n;
    }

    public int getActionBarHideOffset() {
        if (this.a != null) {
            return -((int) this.a.getTranslationY());
        }
        return 0;
    }

    public void setActionBarHideOffset(int i2) {
        b();
        this.a.setTranslationY((float) (-Math.max(0, Math.min(i2, this.a.getHeight()))));
    }

    /* access modifiers changed from: 0000 */
    public void b() {
        removeCallbacks(this.A);
        removeCallbacks(this.B);
        if (this.c != null) {
            this.c.cancel();
        }
    }

    private void c() {
        b();
        postDelayed(this.A, 600);
    }

    private void d() {
        b();
        postDelayed(this.B, 600);
    }

    private void e() {
        b();
        this.A.run();
    }

    private void f() {
        b();
        this.B.run();
    }

    private boolean a(float f2, float f3) {
        this.z.fling(0, 0, 0, (int) f3, 0, 0, Integer.MIN_VALUE, SubsamplingScaleImageView.TILE_SIZE_AUTO);
        return this.z.getFinalY() > this.a.getHeight();
    }

    public void setWindowCallback(Callback callback) {
        a();
        this.i.setWindowCallback(callback);
    }

    public void setWindowTitle(CharSequence charSequence) {
        a();
        this.i.setWindowTitle(charSequence);
    }

    public CharSequence getTitle() {
        a();
        return this.i.getTitle();
    }

    public void initFeature(int i2) {
        a();
        if (i2 == 2) {
            this.i.initProgress();
        } else if (i2 == 5) {
            this.i.initIndeterminateProgress();
        } else if (i2 == 109) {
            setOverlayMode(true);
        }
    }

    public boolean hasIcon() {
        a();
        return this.i.hasIcon();
    }

    public boolean hasLogo() {
        a();
        return this.i.hasLogo();
    }

    public void setIcon(int i2) {
        a();
        this.i.setIcon(i2);
    }

    public void setIcon(Drawable drawable) {
        a();
        this.i.setIcon(drawable);
    }

    public void setLogo(int i2) {
        a();
        this.i.setLogo(i2);
    }

    public boolean canShowOverflowMenu() {
        a();
        return this.i.canShowOverflowMenu();
    }

    public boolean isOverflowMenuShowing() {
        a();
        return this.i.isOverflowMenuShowing();
    }

    public boolean isOverflowMenuShowPending() {
        a();
        return this.i.isOverflowMenuShowPending();
    }

    public boolean showOverflowMenu() {
        a();
        return this.i.showOverflowMenu();
    }

    public boolean hideOverflowMenu() {
        a();
        return this.i.hideOverflowMenu();
    }

    public void setMenuPrepared() {
        a();
        this.i.setMenuPrepared();
    }

    public void setMenu(Menu menu, MenuPresenter.Callback callback) {
        a();
        this.i.setMenu(menu, callback);
    }

    public void saveToolbarHierarchyState(SparseArray<Parcelable> sparseArray) {
        a();
        this.i.saveHierarchyState(sparseArray);
    }

    public void restoreToolbarHierarchyState(SparseArray<Parcelable> sparseArray) {
        a();
        this.i.restoreHierarchyState(sparseArray);
    }

    public void dismissPopups() {
        a();
        this.i.dismissPopupMenus();
    }
}
