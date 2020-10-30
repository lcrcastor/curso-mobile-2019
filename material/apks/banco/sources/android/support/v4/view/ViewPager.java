package android.support.v4.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.ClassLoaderCreator;
import android.os.Parcelable.Creator;
import android.os.SystemClock;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.FocusFinder;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SoundEffectConstants;
import android.view.VelocityTracker;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;
import android.view.animation.Interpolator;
import android.widget.EdgeEffect;
import android.widget.Scroller;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import io.fabric.sdk.android.services.settings.SettingsJsonConstants;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ViewPager extends ViewGroup {
    public static final int SCROLL_STATE_DRAGGING = 1;
    public static final int SCROLL_STATE_IDLE = 0;
    public static final int SCROLL_STATE_SETTLING = 2;
    static final int[] a = {16842931};
    private static final ViewPositionComparator aj = new ViewPositionComparator();
    private static final Comparator<ItemInfo> e = new Comparator<ItemInfo>() {
        /* renamed from: a */
        public int compare(ItemInfo itemInfo, ItemInfo itemInfo2) {
            return itemInfo.b - itemInfo2.b;
        }
    };
    private static final Interpolator f = new Interpolator() {
        public float getInterpolation(float f) {
            float f2 = f - 1.0f;
            return (f2 * f2 * f2 * f2 * f2) + 1.0f;
        }
    };
    private int A = 1;
    private boolean B;
    private boolean C;
    private int D;
    private int E;
    private int F;
    private float G;
    private float H;
    private float I;
    private float J;
    private int K = -1;
    private VelocityTracker L;
    private int M;
    private int N;
    private int O;
    private int P;
    private boolean Q;
    private long R;
    private EdgeEffect S;
    private EdgeEffect T;
    private boolean U = true;
    private boolean V = false;
    private boolean W;
    private int aa;
    private List<OnPageChangeListener> ab;
    private OnPageChangeListener ac;
    private OnPageChangeListener ad;
    private List<OnAdapterChangeListener> ae;
    private PageTransformer af;
    private int ag;
    private int ah;
    private ArrayList<View> ai;
    private final Runnable ak = new Runnable() {
        public void run() {
            ViewPager.this.setScrollState(0);
            ViewPager.this.c();
        }
    };
    private int al = 0;
    PagerAdapter b;
    int c;
    private int d;
    private final ArrayList<ItemInfo> g = new ArrayList<>();
    private final ItemInfo h = new ItemInfo();
    private final Rect i = new Rect();
    private int j = -1;
    private Parcelable k = null;
    private ClassLoader l = null;
    private Scroller m;
    private boolean n;
    private PagerObserver o;
    private int p;
    private Drawable q;
    private int r;
    private int s;
    private float t = -3.4028235E38f;
    private float u = Float.MAX_VALUE;
    private int v;
    private int w;
    private boolean x;
    private boolean y;
    private boolean z;

    @Inherited
    @Target({ElementType.TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface DecorView {
    }

    static class ItemInfo {
        Object a;
        int b;
        boolean c;
        float d;
        float e;

        ItemInfo() {
        }
    }

    public static class LayoutParams extends android.view.ViewGroup.LayoutParams {
        float a = BitmapDescriptorFactory.HUE_RED;
        boolean b;
        int c;
        int d;
        public int gravity;
        public boolean isDecor;

        public LayoutParams() {
            super(-1, -1);
        }

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, ViewPager.a);
            this.gravity = obtainStyledAttributes.getInteger(0, 48);
            obtainStyledAttributes.recycle();
        }
    }

    class MyAccessibilityDelegate extends AccessibilityDelegateCompat {
        MyAccessibilityDelegate() {
        }

        public void onInitializeAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
            super.onInitializeAccessibilityEvent(view, accessibilityEvent);
            accessibilityEvent.setClassName(ViewPager.class.getName());
            accessibilityEvent.setScrollable(b());
            if (accessibilityEvent.getEventType() == 4096 && ViewPager.this.b != null) {
                accessibilityEvent.setItemCount(ViewPager.this.b.getCount());
                accessibilityEvent.setFromIndex(ViewPager.this.c);
                accessibilityEvent.setToIndex(ViewPager.this.c);
            }
        }

        public void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfoCompat);
            accessibilityNodeInfoCompat.setClassName(ViewPager.class.getName());
            accessibilityNodeInfoCompat.setScrollable(b());
            if (ViewPager.this.canScrollHorizontally(1)) {
                accessibilityNodeInfoCompat.addAction(4096);
            }
            if (ViewPager.this.canScrollHorizontally(-1)) {
                accessibilityNodeInfoCompat.addAction(8192);
            }
        }

        public boolean performAccessibilityAction(View view, int i, Bundle bundle) {
            if (super.performAccessibilityAction(view, i, bundle)) {
                return true;
            }
            if (i != 4096) {
                if (i != 8192 || !ViewPager.this.canScrollHorizontally(-1)) {
                    return false;
                }
                ViewPager.this.setCurrentItem(ViewPager.this.c - 1);
                return true;
            } else if (!ViewPager.this.canScrollHorizontally(1)) {
                return false;
            } else {
                ViewPager.this.setCurrentItem(ViewPager.this.c + 1);
                return true;
            }
        }

        private boolean b() {
            return ViewPager.this.b != null && ViewPager.this.b.getCount() > 1;
        }
    }

    public interface OnAdapterChangeListener {
        void onAdapterChanged(@NonNull ViewPager viewPager, @Nullable PagerAdapter pagerAdapter, @Nullable PagerAdapter pagerAdapter2);
    }

    public interface OnPageChangeListener {
        void onPageScrollStateChanged(int i);

        void onPageScrolled(int i, float f, int i2);

        void onPageSelected(int i);
    }

    public interface PageTransformer {
        void transformPage(@NonNull View view, float f);
    }

    class PagerObserver extends DataSetObserver {
        PagerObserver() {
        }

        public void onChanged() {
            ViewPager.this.b();
        }

        public void onInvalidated() {
            ViewPager.this.b();
        }
    }

    public static class SavedState extends AbsSavedState {
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
        int a;
        Parcelable b;
        ClassLoader c;

        public SavedState(@NonNull Parcelable parcelable) {
            super(parcelable);
        }

        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeInt(this.a);
            parcel.writeParcelable(this.b, i);
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("FragmentPager.SavedState{");
            sb.append(Integer.toHexString(System.identityHashCode(this)));
            sb.append(" position=");
            sb.append(this.a);
            sb.append("}");
            return sb.toString();
        }

        SavedState(Parcel parcel, ClassLoader classLoader) {
            super(parcel, classLoader);
            if (classLoader == null) {
                classLoader = getClass().getClassLoader();
            }
            this.a = parcel.readInt();
            this.b = parcel.readParcelable(classLoader);
            this.c = classLoader;
        }
    }

    public static class SimpleOnPageChangeListener implements OnPageChangeListener {
        public void onPageScrollStateChanged(int i) {
        }

        public void onPageScrolled(int i, float f, int i2) {
        }

        public void onPageSelected(int i) {
        }
    }

    static class ViewPositionComparator implements Comparator<View> {
        ViewPositionComparator() {
        }

        /* renamed from: a */
        public int compare(View view, View view2) {
            LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
            LayoutParams layoutParams2 = (LayoutParams) view2.getLayoutParams();
            if (layoutParams.isDecor == layoutParams2.isDecor) {
                return layoutParams.c - layoutParams2.c;
            }
            return layoutParams.isDecor ? 1 : -1;
        }
    }

    public ViewPager(@NonNull Context context) {
        super(context);
        a();
    }

    public ViewPager(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        a();
    }

    /* access modifiers changed from: 0000 */
    public void a() {
        setWillNotDraw(false);
        setDescendantFocusability(262144);
        setFocusable(true);
        Context context = getContext();
        this.m = new Scroller(context, f);
        ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
        float f2 = context.getResources().getDisplayMetrics().density;
        this.F = viewConfiguration.getScaledPagingTouchSlop();
        this.M = (int) (400.0f * f2);
        this.N = viewConfiguration.getScaledMaximumFlingVelocity();
        this.S = new EdgeEffect(context);
        this.T = new EdgeEffect(context);
        this.O = (int) (25.0f * f2);
        this.P = (int) (2.0f * f2);
        this.D = (int) (f2 * 16.0f);
        ViewCompat.setAccessibilityDelegate(this, new MyAccessibilityDelegate());
        if (ViewCompat.getImportantForAccessibility(this) == 0) {
            ViewCompat.setImportantForAccessibility(this, 1);
        }
        ViewCompat.setOnApplyWindowInsetsListener(this, new OnApplyWindowInsetsListener() {
            private final Rect b = new Rect();

            public WindowInsetsCompat onApplyWindowInsets(View view, WindowInsetsCompat windowInsetsCompat) {
                WindowInsetsCompat onApplyWindowInsets = ViewCompat.onApplyWindowInsets(view, windowInsetsCompat);
                if (onApplyWindowInsets.isConsumed()) {
                    return onApplyWindowInsets;
                }
                Rect rect = this.b;
                rect.left = onApplyWindowInsets.getSystemWindowInsetLeft();
                rect.top = onApplyWindowInsets.getSystemWindowInsetTop();
                rect.right = onApplyWindowInsets.getSystemWindowInsetRight();
                rect.bottom = onApplyWindowInsets.getSystemWindowInsetBottom();
                int childCount = ViewPager.this.getChildCount();
                for (int i = 0; i < childCount; i++) {
                    WindowInsetsCompat dispatchApplyWindowInsets = ViewCompat.dispatchApplyWindowInsets(ViewPager.this.getChildAt(i), onApplyWindowInsets);
                    rect.left = Math.min(dispatchApplyWindowInsets.getSystemWindowInsetLeft(), rect.left);
                    rect.top = Math.min(dispatchApplyWindowInsets.getSystemWindowInsetTop(), rect.top);
                    rect.right = Math.min(dispatchApplyWindowInsets.getSystemWindowInsetRight(), rect.right);
                    rect.bottom = Math.min(dispatchApplyWindowInsets.getSystemWindowInsetBottom(), rect.bottom);
                }
                return onApplyWindowInsets.replaceSystemWindowInsets(rect.left, rect.top, rect.right, rect.bottom);
            }
        });
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        removeCallbacks(this.ak);
        if (this.m != null && !this.m.isFinished()) {
            this.m.abortAnimation();
        }
        super.onDetachedFromWindow();
    }

    /* access modifiers changed from: 0000 */
    public void setScrollState(int i2) {
        if (this.al != i2) {
            this.al = i2;
            if (this.af != null) {
                b(i2 != 0);
            }
            e(i2);
        }
    }

    public void setAdapter(@Nullable PagerAdapter pagerAdapter) {
        if (this.b != null) {
            this.b.a(null);
            this.b.startUpdate((ViewGroup) this);
            for (int i2 = 0; i2 < this.g.size(); i2++) {
                ItemInfo itemInfo = (ItemInfo) this.g.get(i2);
                this.b.destroyItem((ViewGroup) this, itemInfo.b, itemInfo.a);
            }
            this.b.finishUpdate((ViewGroup) this);
            this.g.clear();
            f();
            this.c = 0;
            scrollTo(0, 0);
        }
        PagerAdapter pagerAdapter2 = this.b;
        this.b = pagerAdapter;
        this.d = 0;
        if (this.b != null) {
            if (this.o == null) {
                this.o = new PagerObserver();
            }
            this.b.a(this.o);
            this.z = false;
            boolean z2 = this.U;
            this.U = true;
            this.d = this.b.getCount();
            if (this.j >= 0) {
                this.b.restoreState(this.k, this.l);
                a(this.j, false, true);
                this.j = -1;
                this.k = null;
                this.l = null;
            } else if (!z2) {
                c();
            } else {
                requestLayout();
            }
        }
        if (this.ae != null && !this.ae.isEmpty()) {
            int size = this.ae.size();
            for (int i3 = 0; i3 < size; i3++) {
                ((OnAdapterChangeListener) this.ae.get(i3)).onAdapterChanged(this, pagerAdapter2, pagerAdapter);
            }
        }
    }

    private void f() {
        int i2 = 0;
        while (i2 < getChildCount()) {
            if (!((LayoutParams) getChildAt(i2).getLayoutParams()).isDecor) {
                removeViewAt(i2);
                i2--;
            }
            i2++;
        }
    }

    @Nullable
    public PagerAdapter getAdapter() {
        return this.b;
    }

    public void addOnAdapterChangeListener(@NonNull OnAdapterChangeListener onAdapterChangeListener) {
        if (this.ae == null) {
            this.ae = new ArrayList();
        }
        this.ae.add(onAdapterChangeListener);
    }

    public void removeOnAdapterChangeListener(@NonNull OnAdapterChangeListener onAdapterChangeListener) {
        if (this.ae != null) {
            this.ae.remove(onAdapterChangeListener);
        }
    }

    private int getClientWidth() {
        return (getMeasuredWidth() - getPaddingLeft()) - getPaddingRight();
    }

    public void setCurrentItem(int i2) {
        this.z = false;
        a(i2, !this.U, false);
    }

    public void setCurrentItem(int i2, boolean z2) {
        this.z = false;
        a(i2, z2, false);
    }

    public int getCurrentItem() {
        return this.c;
    }

    /* access modifiers changed from: 0000 */
    public void a(int i2, boolean z2, boolean z3) {
        a(i2, z2, z3, 0);
    }

    /* access modifiers changed from: 0000 */
    public void a(int i2, boolean z2, boolean z3, int i3) {
        if (this.b == null || this.b.getCount() <= 0) {
            setScrollingCacheEnabled(false);
        } else if (z3 || this.c != i2 || this.g.size() == 0) {
            boolean z4 = true;
            if (i2 < 0) {
                i2 = 0;
            } else if (i2 >= this.b.getCount()) {
                i2 = this.b.getCount() - 1;
            }
            int i4 = this.A;
            if (i2 > this.c + i4 || i2 < this.c - i4) {
                for (int i5 = 0; i5 < this.g.size(); i5++) {
                    ((ItemInfo) this.g.get(i5)).c = true;
                }
            }
            if (this.c == i2) {
                z4 = false;
            }
            if (this.U) {
                this.c = i2;
                if (z4) {
                    d(i2);
                }
                requestLayout();
            } else {
                a(i2);
                a(i2, z2, i3, z4);
            }
        } else {
            setScrollingCacheEnabled(false);
        }
    }

    private void a(int i2, boolean z2, int i3, boolean z3) {
        ItemInfo b2 = b(i2);
        int clientWidth = b2 != null ? (int) (((float) getClientWidth()) * Math.max(this.t, Math.min(b2.e, this.u))) : 0;
        if (z2) {
            a(clientWidth, 0, i3);
            if (z3) {
                d(i2);
                return;
            }
            return;
        }
        if (z3) {
            d(i2);
        }
        a(false);
        scrollTo(clientWidth, 0);
        c(clientWidth);
    }

    @Deprecated
    public void setOnPageChangeListener(OnPageChangeListener onPageChangeListener) {
        this.ac = onPageChangeListener;
    }

    public void addOnPageChangeListener(@NonNull OnPageChangeListener onPageChangeListener) {
        if (this.ab == null) {
            this.ab = new ArrayList();
        }
        this.ab.add(onPageChangeListener);
    }

    public void removeOnPageChangeListener(@NonNull OnPageChangeListener onPageChangeListener) {
        if (this.ab != null) {
            this.ab.remove(onPageChangeListener);
        }
    }

    public void clearOnPageChangeListeners() {
        if (this.ab != null) {
            this.ab.clear();
        }
    }

    public void setPageTransformer(boolean z2, @Nullable PageTransformer pageTransformer) {
        setPageTransformer(z2, pageTransformer, 2);
    }

    public void setPageTransformer(boolean z2, @Nullable PageTransformer pageTransformer, int i2) {
        int i3 = 1;
        boolean z3 = pageTransformer != null;
        boolean z4 = z3 != (this.af != null);
        this.af = pageTransformer;
        setChildrenDrawingOrderEnabled(z3);
        if (z3) {
            if (z2) {
                i3 = 2;
            }
            this.ah = i3;
            this.ag = i2;
        } else {
            this.ah = 0;
        }
        if (z4) {
            c();
        }
    }

    /* access modifiers changed from: protected */
    public int getChildDrawingOrder(int i2, int i3) {
        if (this.ah == 2) {
            i3 = (i2 - 1) - i3;
        }
        return ((LayoutParams) ((View) this.ai.get(i3)).getLayoutParams()).d;
    }

    /* access modifiers changed from: 0000 */
    public OnPageChangeListener a(OnPageChangeListener onPageChangeListener) {
        OnPageChangeListener onPageChangeListener2 = this.ad;
        this.ad = onPageChangeListener;
        return onPageChangeListener2;
    }

    public int getOffscreenPageLimit() {
        return this.A;
    }

    public void setOffscreenPageLimit(int i2) {
        if (i2 < 1) {
            StringBuilder sb = new StringBuilder();
            sb.append("Requested offscreen page limit ");
            sb.append(i2);
            sb.append(" too small; defaulting to ");
            sb.append(1);
            Log.w("ViewPager", sb.toString());
            i2 = 1;
        }
        if (i2 != this.A) {
            this.A = i2;
            c();
        }
    }

    public void setPageMargin(int i2) {
        int i3 = this.p;
        this.p = i2;
        int width = getWidth();
        a(width, width, i2, i3);
        requestLayout();
    }

    public int getPageMargin() {
        return this.p;
    }

    public void setPageMarginDrawable(@Nullable Drawable drawable) {
        this.q = drawable;
        if (drawable != null) {
            refreshDrawableState();
        }
        setWillNotDraw(drawable == null);
        invalidate();
    }

    public void setPageMarginDrawable(@DrawableRes int i2) {
        setPageMarginDrawable(ContextCompat.getDrawable(getContext(), i2));
    }

    /* access modifiers changed from: protected */
    public boolean verifyDrawable(Drawable drawable) {
        return super.verifyDrawable(drawable) || drawable == this.q;
    }

    /* access modifiers changed from: protected */
    public void drawableStateChanged() {
        super.drawableStateChanged();
        Drawable drawable = this.q;
        if (drawable != null && drawable.isStateful()) {
            drawable.setState(getDrawableState());
        }
    }

    /* access modifiers changed from: 0000 */
    public float a(float f2) {
        return (float) Math.sin((double) ((f2 - 0.5f) * 0.47123894f));
    }

    /* access modifiers changed from: 0000 */
    public void a(int i2, int i3, int i4) {
        int i5;
        int i6;
        if (getChildCount() == 0) {
            setScrollingCacheEnabled(false);
            return;
        }
        if (this.m != null && !this.m.isFinished()) {
            i5 = this.n ? this.m.getCurrX() : this.m.getStartX();
            this.m.abortAnimation();
            setScrollingCacheEnabled(false);
        } else {
            i5 = getScrollX();
        }
        int i7 = i5;
        int scrollY = getScrollY();
        int i8 = i2 - i7;
        int i9 = i3 - scrollY;
        if (i8 == 0 && i9 == 0) {
            a(false);
            c();
            setScrollState(0);
            return;
        }
        setScrollingCacheEnabled(true);
        setScrollState(2);
        int clientWidth = getClientWidth();
        int i10 = clientWidth / 2;
        float f2 = (float) clientWidth;
        float f3 = (float) i10;
        float a2 = f3 + (a(Math.min(1.0f, (((float) Math.abs(i8)) * 1.0f) / f2)) * f3);
        int abs = Math.abs(i4);
        if (abs > 0) {
            i6 = Math.round(Math.abs(a2 / ((float) abs)) * 1000.0f) * 4;
        } else {
            i6 = (int) (((((float) Math.abs(i8)) / ((f2 * this.b.getPageWidth(this.c)) + ((float) this.p))) + 1.0f) * 100.0f);
        }
        int min = Math.min(i6, SettingsJsonConstants.ANALYTICS_FLUSH_INTERVAL_SECS_DEFAULT);
        this.n = false;
        this.m.startScroll(i7, scrollY, i8, i9, min);
        ViewCompat.postInvalidateOnAnimation(this);
    }

    /* access modifiers changed from: 0000 */
    public ItemInfo a(int i2, int i3) {
        ItemInfo itemInfo = new ItemInfo();
        itemInfo.b = i2;
        itemInfo.a = this.b.instantiateItem((ViewGroup) this, i2);
        itemInfo.d = this.b.getPageWidth(i2);
        if (i3 < 0 || i3 >= this.g.size()) {
            this.g.add(itemInfo);
        } else {
            this.g.add(i3, itemInfo);
        }
        return itemInfo;
    }

    /* access modifiers changed from: 0000 */
    public void b() {
        int count = this.b.getCount();
        this.d = count;
        boolean z2 = this.g.size() < (this.A * 2) + 1 && this.g.size() < count;
        int i2 = this.c;
        int i3 = 0;
        boolean z3 = false;
        while (i3 < this.g.size()) {
            ItemInfo itemInfo = (ItemInfo) this.g.get(i3);
            int itemPosition = this.b.getItemPosition(itemInfo.a);
            if (itemPosition != -1) {
                if (itemPosition == -2) {
                    this.g.remove(i3);
                    i3--;
                    if (!z3) {
                        this.b.startUpdate((ViewGroup) this);
                        z3 = true;
                    }
                    this.b.destroyItem((ViewGroup) this, itemInfo.b, itemInfo.a);
                    if (this.c == itemInfo.b) {
                        i2 = Math.max(0, Math.min(this.c, count - 1));
                    }
                } else if (itemInfo.b != itemPosition) {
                    if (itemInfo.b == this.c) {
                        i2 = itemPosition;
                    }
                    itemInfo.b = itemPosition;
                }
                z2 = true;
            }
            i3++;
        }
        if (z3) {
            this.b.finishUpdate((ViewGroup) this);
        }
        Collections.sort(this.g, e);
        if (z2) {
            int childCount = getChildCount();
            for (int i4 = 0; i4 < childCount; i4++) {
                LayoutParams layoutParams = (LayoutParams) getChildAt(i4).getLayoutParams();
                if (!layoutParams.isDecor) {
                    layoutParams.a = BitmapDescriptorFactory.HUE_RED;
                }
            }
            a(i2, false, true);
            requestLayout();
        }
    }

    /* access modifiers changed from: 0000 */
    public void c() {
        a(this.c);
    }

    /* access modifiers changed from: 0000 */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x00be, code lost:
        if (r8.b == r0.c) goto L_0x00c5;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void a(int r19) {
        /*
            r18 = this;
            r0 = r18
            r1 = r19
            int r2 = r0.c
            if (r2 == r1) goto L_0x0011
            int r2 = r0.c
            android.support.v4.view.ViewPager$ItemInfo r2 = r0.b(r2)
            r0.c = r1
            goto L_0x0012
        L_0x0011:
            r2 = 0
        L_0x0012:
            android.support.v4.view.PagerAdapter r1 = r0.b
            if (r1 != 0) goto L_0x001a
            r18.g()
            return
        L_0x001a:
            boolean r1 = r0.z
            if (r1 == 0) goto L_0x0022
            r18.g()
            return
        L_0x0022:
            android.os.IBinder r1 = r18.getWindowToken()
            if (r1 != 0) goto L_0x0029
            return
        L_0x0029:
            android.support.v4.view.PagerAdapter r1 = r0.b
            r1.startUpdate(r0)
            int r1 = r0.A
            int r4 = r0.c
            int r4 = r4 - r1
            r5 = 0
            int r4 = java.lang.Math.max(r5, r4)
            android.support.v4.view.PagerAdapter r6 = r0.b
            int r6 = r6.getCount()
            int r7 = r6 + -1
            int r8 = r0.c
            int r8 = r8 + r1
            int r1 = java.lang.Math.min(r7, r8)
            int r7 = r0.d
            if (r6 == r7) goto L_0x00a3
            android.content.res.Resources r1 = r18.getResources()     // Catch:{ NotFoundException -> 0x0058 }
            int r2 = r18.getId()     // Catch:{ NotFoundException -> 0x0058 }
            java.lang.String r1 = r1.getResourceName(r2)     // Catch:{ NotFoundException -> 0x0058 }
            goto L_0x0060
        L_0x0058:
            int r1 = r18.getId()
            java.lang.String r1 = java.lang.Integer.toHexString(r1)
        L_0x0060:
            java.lang.IllegalStateException r2 = new java.lang.IllegalStateException
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "The application's PagerAdapter changed the adapter's contents without calling PagerAdapter#notifyDataSetChanged! Expected adapter item count: "
            r3.append(r4)
            int r4 = r0.d
            r3.append(r4)
            java.lang.String r4 = ", found: "
            r3.append(r4)
            r3.append(r6)
            java.lang.String r4 = " Pager id: "
            r3.append(r4)
            r3.append(r1)
            java.lang.String r1 = " Pager class: "
            r3.append(r1)
            java.lang.Class r1 = r18.getClass()
            r3.append(r1)
            java.lang.String r1 = " Problematic adapter: "
            r3.append(r1)
            android.support.v4.view.PagerAdapter r1 = r0.b
            java.lang.Class r1 = r1.getClass()
            r3.append(r1)
            java.lang.String r1 = r3.toString()
            r2.<init>(r1)
            throw r2
        L_0x00a3:
            r7 = 0
        L_0x00a4:
            java.util.ArrayList<android.support.v4.view.ViewPager$ItemInfo> r8 = r0.g
            int r8 = r8.size()
            if (r7 >= r8) goto L_0x00c4
            java.util.ArrayList<android.support.v4.view.ViewPager$ItemInfo> r8 = r0.g
            java.lang.Object r8 = r8.get(r7)
            android.support.v4.view.ViewPager$ItemInfo r8 = (android.support.v4.view.ViewPager.ItemInfo) r8
            int r9 = r8.b
            int r10 = r0.c
            if (r9 < r10) goto L_0x00c1
            int r9 = r8.b
            int r10 = r0.c
            if (r9 != r10) goto L_0x00c4
            goto L_0x00c5
        L_0x00c1:
            int r7 = r7 + 1
            goto L_0x00a4
        L_0x00c4:
            r8 = 0
        L_0x00c5:
            if (r8 != 0) goto L_0x00cf
            if (r6 <= 0) goto L_0x00cf
            int r8 = r0.c
            android.support.v4.view.ViewPager$ItemInfo r8 = r0.a(r8, r7)
        L_0x00cf:
            r9 = 0
            if (r8 == 0) goto L_0x01fb
            int r10 = r7 + -1
            if (r10 < 0) goto L_0x00df
            java.util.ArrayList<android.support.v4.view.ViewPager$ItemInfo> r11 = r0.g
            java.lang.Object r11 = r11.get(r10)
            android.support.v4.view.ViewPager$ItemInfo r11 = (android.support.v4.view.ViewPager.ItemInfo) r11
            goto L_0x00e0
        L_0x00df:
            r11 = 0
        L_0x00e0:
            int r12 = r18.getClientWidth()
            r13 = 1073741824(0x40000000, float:2.0)
            if (r12 > 0) goto L_0x00ea
            r3 = 0
            goto L_0x00f7
        L_0x00ea:
            float r14 = r8.d
            float r14 = r13 - r14
            int r15 = r18.getPaddingLeft()
            float r15 = (float) r15
            float r3 = (float) r12
            float r15 = r15 / r3
            float r3 = r14 + r15
        L_0x00f7:
            int r14 = r0.c
            int r14 = r14 + -1
            r15 = r7
            r7 = 0
        L_0x00fd:
            if (r14 < 0) goto L_0x015d
            int r16 = (r7 > r3 ? 1 : (r7 == r3 ? 0 : -1))
            if (r16 < 0) goto L_0x012b
            if (r14 >= r4) goto L_0x012b
            if (r11 != 0) goto L_0x0108
            goto L_0x015d
        L_0x0108:
            int r5 = r11.b
            if (r14 != r5) goto L_0x0159
            boolean r5 = r11.c
            if (r5 != 0) goto L_0x0159
            java.util.ArrayList<android.support.v4.view.ViewPager$ItemInfo> r5 = r0.g
            r5.remove(r10)
            android.support.v4.view.PagerAdapter r5 = r0.b
            java.lang.Object r11 = r11.a
            r5.destroyItem(r0, r14, r11)
            int r10 = r10 + -1
            int r15 = r15 + -1
            if (r10 < 0) goto L_0x0157
            java.util.ArrayList<android.support.v4.view.ViewPager$ItemInfo> r5 = r0.g
            java.lang.Object r5 = r5.get(r10)
            android.support.v4.view.ViewPager$ItemInfo r5 = (android.support.v4.view.ViewPager.ItemInfo) r5
            goto L_0x0158
        L_0x012b:
            if (r11 == 0) goto L_0x0141
            int r5 = r11.b
            if (r14 != r5) goto L_0x0141
            float r5 = r11.d
            float r7 = r7 + r5
            int r10 = r10 + -1
            if (r10 < 0) goto L_0x0157
            java.util.ArrayList<android.support.v4.view.ViewPager$ItemInfo> r5 = r0.g
            java.lang.Object r5 = r5.get(r10)
            android.support.v4.view.ViewPager$ItemInfo r5 = (android.support.v4.view.ViewPager.ItemInfo) r5
            goto L_0x0158
        L_0x0141:
            int r5 = r10 + 1
            android.support.v4.view.ViewPager$ItemInfo r5 = r0.a(r14, r5)
            float r5 = r5.d
            float r7 = r7 + r5
            int r15 = r15 + 1
            if (r10 < 0) goto L_0x0157
            java.util.ArrayList<android.support.v4.view.ViewPager$ItemInfo> r5 = r0.g
            java.lang.Object r5 = r5.get(r10)
            android.support.v4.view.ViewPager$ItemInfo r5 = (android.support.v4.view.ViewPager.ItemInfo) r5
            goto L_0x0158
        L_0x0157:
            r5 = 0
        L_0x0158:
            r11 = r5
        L_0x0159:
            int r14 = r14 + -1
            r5 = 0
            goto L_0x00fd
        L_0x015d:
            float r3 = r8.d
            int r4 = r15 + 1
            int r5 = (r3 > r13 ? 1 : (r3 == r13 ? 0 : -1))
            if (r5 >= 0) goto L_0x01ef
            java.util.ArrayList<android.support.v4.view.ViewPager$ItemInfo> r5 = r0.g
            int r5 = r5.size()
            if (r4 >= r5) goto L_0x0176
            java.util.ArrayList<android.support.v4.view.ViewPager$ItemInfo> r5 = r0.g
            java.lang.Object r5 = r5.get(r4)
            android.support.v4.view.ViewPager$ItemInfo r5 = (android.support.v4.view.ViewPager.ItemInfo) r5
            goto L_0x0177
        L_0x0176:
            r5 = 0
        L_0x0177:
            if (r12 > 0) goto L_0x017b
            r7 = 0
            goto L_0x0183
        L_0x017b:
            int r7 = r18.getPaddingRight()
            float r7 = (float) r7
            float r10 = (float) r12
            float r7 = r7 / r10
            float r7 = r7 + r13
        L_0x0183:
            int r10 = r0.c
        L_0x0185:
            int r10 = r10 + 1
            if (r10 >= r6) goto L_0x01ef
            int r11 = (r3 > r7 ? 1 : (r3 == r7 ? 0 : -1))
            if (r11 < 0) goto L_0x01b9
            if (r10 <= r1) goto L_0x01b9
            if (r5 != 0) goto L_0x0192
            goto L_0x01ef
        L_0x0192:
            int r11 = r5.b
            if (r10 != r11) goto L_0x01ee
            boolean r11 = r5.c
            if (r11 != 0) goto L_0x01ee
            java.util.ArrayList<android.support.v4.view.ViewPager$ItemInfo> r11 = r0.g
            r11.remove(r4)
            android.support.v4.view.PagerAdapter r11 = r0.b
            java.lang.Object r5 = r5.a
            r11.destroyItem(r0, r10, r5)
            java.util.ArrayList<android.support.v4.view.ViewPager$ItemInfo> r5 = r0.g
            int r5 = r5.size()
            if (r4 >= r5) goto L_0x01b7
            java.util.ArrayList<android.support.v4.view.ViewPager$ItemInfo> r5 = r0.g
            java.lang.Object r5 = r5.get(r4)
            android.support.v4.view.ViewPager$ItemInfo r5 = (android.support.v4.view.ViewPager.ItemInfo) r5
            goto L_0x01ee
        L_0x01b7:
            r5 = 0
            goto L_0x01ee
        L_0x01b9:
            if (r5 == 0) goto L_0x01d5
            int r11 = r5.b
            if (r10 != r11) goto L_0x01d5
            float r5 = r5.d
            float r3 = r3 + r5
            int r4 = r4 + 1
            java.util.ArrayList<android.support.v4.view.ViewPager$ItemInfo> r5 = r0.g
            int r5 = r5.size()
            if (r4 >= r5) goto L_0x01b7
            java.util.ArrayList<android.support.v4.view.ViewPager$ItemInfo> r5 = r0.g
            java.lang.Object r5 = r5.get(r4)
            android.support.v4.view.ViewPager$ItemInfo r5 = (android.support.v4.view.ViewPager.ItemInfo) r5
            goto L_0x01ee
        L_0x01d5:
            android.support.v4.view.ViewPager$ItemInfo r5 = r0.a(r10, r4)
            int r4 = r4 + 1
            float r5 = r5.d
            float r3 = r3 + r5
            java.util.ArrayList<android.support.v4.view.ViewPager$ItemInfo> r5 = r0.g
            int r5 = r5.size()
            if (r4 >= r5) goto L_0x01b7
            java.util.ArrayList<android.support.v4.view.ViewPager$ItemInfo> r5 = r0.g
            java.lang.Object r5 = r5.get(r4)
            android.support.v4.view.ViewPager$ItemInfo r5 = (android.support.v4.view.ViewPager.ItemInfo) r5
        L_0x01ee:
            goto L_0x0185
        L_0x01ef:
            r0.a(r8, r15, r2)
            android.support.v4.view.PagerAdapter r1 = r0.b
            int r2 = r0.c
            java.lang.Object r3 = r8.a
            r1.setPrimaryItem(r0, r2, r3)
        L_0x01fb:
            android.support.v4.view.PagerAdapter r1 = r0.b
            r1.finishUpdate(r0)
            int r1 = r18.getChildCount()
            r2 = 0
        L_0x0205:
            if (r2 >= r1) goto L_0x022e
            android.view.View r3 = r0.getChildAt(r2)
            android.view.ViewGroup$LayoutParams r4 = r3.getLayoutParams()
            android.support.v4.view.ViewPager$LayoutParams r4 = (android.support.v4.view.ViewPager.LayoutParams) r4
            r4.d = r2
            boolean r5 = r4.isDecor
            if (r5 != 0) goto L_0x022b
            float r5 = r4.a
            int r5 = (r5 > r9 ? 1 : (r5 == r9 ? 0 : -1))
            if (r5 != 0) goto L_0x022b
            android.support.v4.view.ViewPager$ItemInfo r3 = r0.a(r3)
            if (r3 == 0) goto L_0x022b
            float r5 = r3.d
            r4.a = r5
            int r3 = r3.b
            r4.c = r3
        L_0x022b:
            int r2 = r2 + 1
            goto L_0x0205
        L_0x022e:
            r18.g()
            boolean r1 = r18.hasFocus()
            if (r1 == 0) goto L_0x026d
            android.view.View r1 = r18.findFocus()
            if (r1 == 0) goto L_0x0242
            android.support.v4.view.ViewPager$ItemInfo r3 = r0.b(r1)
            goto L_0x0243
        L_0x0242:
            r3 = 0
        L_0x0243:
            if (r3 == 0) goto L_0x024b
            int r1 = r3.b
            int r2 = r0.c
            if (r1 == r2) goto L_0x026d
        L_0x024b:
            r1 = 0
        L_0x024c:
            int r2 = r18.getChildCount()
            if (r1 >= r2) goto L_0x026d
            android.view.View r2 = r0.getChildAt(r1)
            android.support.v4.view.ViewPager$ItemInfo r3 = r0.a(r2)
            if (r3 == 0) goto L_0x026a
            int r3 = r3.b
            int r4 = r0.c
            if (r3 != r4) goto L_0x026a
            r3 = 2
            boolean r2 = r2.requestFocus(r3)
            if (r2 == 0) goto L_0x026a
            goto L_0x026d
        L_0x026a:
            int r1 = r1 + 1
            goto L_0x024c
        L_0x026d:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.view.ViewPager.a(int):void");
    }

    private void g() {
        if (this.ah != 0) {
            if (this.ai == null) {
                this.ai = new ArrayList<>();
            } else {
                this.ai.clear();
            }
            int childCount = getChildCount();
            for (int i2 = 0; i2 < childCount; i2++) {
                this.ai.add(getChildAt(i2));
            }
            Collections.sort(this.ai, aj);
        }
    }

    private void a(ItemInfo itemInfo, int i2, ItemInfo itemInfo2) {
        ItemInfo itemInfo3;
        ItemInfo itemInfo4;
        int count = this.b.getCount();
        int clientWidth = getClientWidth();
        float f2 = clientWidth > 0 ? ((float) this.p) / ((float) clientWidth) : BitmapDescriptorFactory.HUE_RED;
        if (itemInfo2 != null) {
            int i3 = itemInfo2.b;
            if (i3 < itemInfo.b) {
                float f3 = itemInfo2.e + itemInfo2.d + f2;
                int i4 = i3 + 1;
                int i5 = 0;
                while (i4 <= itemInfo.b && i5 < this.g.size()) {
                    Object obj = this.g.get(i5);
                    while (true) {
                        itemInfo4 = (ItemInfo) obj;
                        if (i4 > itemInfo4.b && i5 < this.g.size() - 1) {
                            i5++;
                            obj = this.g.get(i5);
                        }
                    }
                    while (i4 < itemInfo4.b) {
                        f3 += this.b.getPageWidth(i4) + f2;
                        i4++;
                    }
                    itemInfo4.e = f3;
                    f3 += itemInfo4.d + f2;
                    i4++;
                }
            } else if (i3 > itemInfo.b) {
                int size = this.g.size() - 1;
                float f4 = itemInfo2.e;
                while (true) {
                    i3--;
                    if (i3 < itemInfo.b || size < 0) {
                        break;
                    }
                    Object obj2 = this.g.get(size);
                    while (true) {
                        itemInfo3 = (ItemInfo) obj2;
                        if (i3 < itemInfo3.b && size > 0) {
                            size--;
                            obj2 = this.g.get(size);
                        }
                    }
                    while (i3 > itemInfo3.b) {
                        f4 -= this.b.getPageWidth(i3) + f2;
                        i3--;
                    }
                    f4 -= itemInfo3.d + f2;
                    itemInfo3.e = f4;
                }
            }
        }
        int size2 = this.g.size();
        float f5 = itemInfo.e;
        int i6 = itemInfo.b - 1;
        this.t = itemInfo.b == 0 ? itemInfo.e : -3.4028235E38f;
        int i7 = count - 1;
        this.u = itemInfo.b == i7 ? (itemInfo.e + itemInfo.d) - 1.0f : Float.MAX_VALUE;
        int i8 = i2 - 1;
        while (i8 >= 0) {
            ItemInfo itemInfo5 = (ItemInfo) this.g.get(i8);
            while (i6 > itemInfo5.b) {
                f5 -= this.b.getPageWidth(i6) + f2;
                i6--;
            }
            f5 -= itemInfo5.d + f2;
            itemInfo5.e = f5;
            if (itemInfo5.b == 0) {
                this.t = f5;
            }
            i8--;
            i6--;
        }
        float f6 = itemInfo.e + itemInfo.d + f2;
        int i9 = itemInfo.b + 1;
        int i10 = i2 + 1;
        while (i10 < size2) {
            ItemInfo itemInfo6 = (ItemInfo) this.g.get(i10);
            while (i9 < itemInfo6.b) {
                f6 += this.b.getPageWidth(i9) + f2;
                i9++;
            }
            if (itemInfo6.b == i7) {
                this.u = (itemInfo6.d + f6) - 1.0f;
            }
            itemInfo6.e = f6;
            f6 += itemInfo6.d + f2;
            i10++;
            i9++;
        }
        this.V = false;
    }

    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        savedState.a = this.c;
        if (this.b != null) {
            savedState.b = this.b.saveState();
        }
        return savedState;
    }

    public void onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable instanceof SavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.getSuperState());
        if (this.b != null) {
            this.b.restoreState(savedState.b, savedState.c);
            a(savedState.a, false, true);
        } else {
            this.j = savedState.a;
            this.k = savedState.b;
            this.l = savedState.c;
        }
    }

    public void addView(View view, int i2, android.view.ViewGroup.LayoutParams layoutParams) {
        if (!checkLayoutParams(layoutParams)) {
            layoutParams = generateLayoutParams(layoutParams);
        }
        LayoutParams layoutParams2 = (LayoutParams) layoutParams;
        layoutParams2.isDecor |= c(view);
        if (!this.x) {
            super.addView(view, i2, layoutParams);
        } else if (layoutParams2 == null || !layoutParams2.isDecor) {
            layoutParams2.b = true;
            addViewInLayout(view, i2, layoutParams);
        } else {
            throw new IllegalStateException("Cannot add pager decor view during layout");
        }
    }

    private static boolean c(@NonNull View view) {
        return view.getClass().getAnnotation(DecorView.class) != null;
    }

    public void removeView(View view) {
        if (this.x) {
            removeViewInLayout(view);
        } else {
            super.removeView(view);
        }
    }

    /* access modifiers changed from: 0000 */
    public ItemInfo a(View view) {
        for (int i2 = 0; i2 < this.g.size(); i2++) {
            ItemInfo itemInfo = (ItemInfo) this.g.get(i2);
            if (this.b.isViewFromObject(view, itemInfo.a)) {
                return itemInfo;
            }
        }
        return null;
    }

    /* access modifiers changed from: 0000 */
    public ItemInfo b(View view) {
        while (true) {
            ViewParent parent = view.getParent();
            if (parent == this) {
                return a(view);
            }
            if (parent != null && (parent instanceof View)) {
                view = (View) parent;
            }
        }
        return null;
    }

    /* access modifiers changed from: 0000 */
    public ItemInfo b(int i2) {
        for (int i3 = 0; i3 < this.g.size(); i3++) {
            ItemInfo itemInfo = (ItemInfo) this.g.get(i3);
            if (itemInfo.b == i2) {
                return itemInfo;
            }
        }
        return null;
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.U = true;
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x008b  */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x0097  */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x009c  */
    /* JADX WARNING: Removed duplicated region for block: B:40:0x00a5  */
    /* JADX WARNING: Removed duplicated region for block: B:43:0x00b4  */
    /* JADX WARNING: Removed duplicated region for block: B:44:0x00ba  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onMeasure(int r18, int r19) {
        /*
            r17 = this;
            r0 = r17
            r1 = 0
            r2 = r18
            int r2 = getDefaultSize(r1, r2)
            r3 = r19
            int r3 = getDefaultSize(r1, r3)
            r0.setMeasuredDimension(r2, r3)
            int r2 = r17.getMeasuredWidth()
            int r3 = r2 / 10
            int r4 = r0.D
            int r3 = java.lang.Math.min(r3, r4)
            r0.E = r3
            int r3 = r17.getPaddingLeft()
            int r2 = r2 - r3
            int r3 = r17.getPaddingRight()
            int r2 = r2 - r3
            int r3 = r17.getMeasuredHeight()
            int r4 = r17.getPaddingTop()
            int r3 = r3 - r4
            int r4 = r17.getPaddingBottom()
            int r3 = r3 - r4
            int r4 = r17.getChildCount()
            r5 = r3
            r3 = r2
            r2 = 0
        L_0x003f:
            r6 = 8
            r7 = 1
            r8 = 1073741824(0x40000000, float:2.0)
            if (r2 >= r4) goto L_0x00c6
            android.view.View r9 = r0.getChildAt(r2)
            int r10 = r9.getVisibility()
            if (r10 == r6) goto L_0x00c1
            android.view.ViewGroup$LayoutParams r6 = r9.getLayoutParams()
            android.support.v4.view.ViewPager$LayoutParams r6 = (android.support.v4.view.ViewPager.LayoutParams) r6
            if (r6 == 0) goto L_0x00c1
            boolean r10 = r6.isDecor
            if (r10 == 0) goto L_0x00c1
            int r10 = r6.gravity
            r10 = r10 & 7
            int r11 = r6.gravity
            r11 = r11 & 112(0x70, float:1.57E-43)
            r12 = 48
            if (r11 == r12) goto L_0x006f
            r12 = 80
            if (r11 != r12) goto L_0x006d
            goto L_0x006f
        L_0x006d:
            r11 = 0
            goto L_0x0070
        L_0x006f:
            r11 = 1
        L_0x0070:
            r12 = 3
            if (r10 == r12) goto L_0x0078
            r12 = 5
            if (r10 != r12) goto L_0x0077
            goto L_0x0078
        L_0x0077:
            r7 = 0
        L_0x0078:
            r10 = -2147483648(0xffffffff80000000, float:-0.0)
            if (r11 == 0) goto L_0x0081
            r10 = 1073741824(0x40000000, float:2.0)
        L_0x007e:
            r12 = -2147483648(0xffffffff80000000, float:-0.0)
            goto L_0x0085
        L_0x0081:
            if (r7 == 0) goto L_0x007e
            r12 = 1073741824(0x40000000, float:2.0)
        L_0x0085:
            int r13 = r6.width
            r14 = -1
            r15 = -2
            if (r13 == r15) goto L_0x0097
            int r10 = r6.width
            if (r10 == r14) goto L_0x0093
            int r10 = r6.width
            r13 = r10
            goto L_0x0094
        L_0x0093:
            r13 = r3
        L_0x0094:
            r10 = 1073741824(0x40000000, float:2.0)
            goto L_0x0098
        L_0x0097:
            r13 = r3
        L_0x0098:
            int r1 = r6.height
            if (r1 == r15) goto L_0x00a5
            int r1 = r6.height
            if (r1 == r14) goto L_0x00a3
            int r1 = r6.height
            goto L_0x00a7
        L_0x00a3:
            r1 = r5
            goto L_0x00a7
        L_0x00a5:
            r1 = r5
            r8 = r12
        L_0x00a7:
            int r6 = android.view.View.MeasureSpec.makeMeasureSpec(r13, r10)
            int r1 = android.view.View.MeasureSpec.makeMeasureSpec(r1, r8)
            r9.measure(r6, r1)
            if (r11 == 0) goto L_0x00ba
            int r1 = r9.getMeasuredHeight()
            int r5 = r5 - r1
            goto L_0x00c1
        L_0x00ba:
            if (r7 == 0) goto L_0x00c1
            int r1 = r9.getMeasuredWidth()
            int r3 = r3 - r1
        L_0x00c1:
            int r2 = r2 + 1
            r1 = 0
            goto L_0x003f
        L_0x00c6:
            int r1 = android.view.View.MeasureSpec.makeMeasureSpec(r3, r8)
            r0.v = r1
            int r1 = android.view.View.MeasureSpec.makeMeasureSpec(r5, r8)
            r0.w = r1
            r0.x = r7
            r17.c()
            r1 = 0
            r0.x = r1
            int r2 = r17.getChildCount()
        L_0x00de:
            if (r1 >= r2) goto L_0x0108
            android.view.View r4 = r0.getChildAt(r1)
            int r5 = r4.getVisibility()
            if (r5 == r6) goto L_0x0105
            android.view.ViewGroup$LayoutParams r5 = r4.getLayoutParams()
            android.support.v4.view.ViewPager$LayoutParams r5 = (android.support.v4.view.ViewPager.LayoutParams) r5
            if (r5 == 0) goto L_0x00f6
            boolean r7 = r5.isDecor
            if (r7 != 0) goto L_0x0105
        L_0x00f6:
            float r7 = (float) r3
            float r5 = r5.a
            float r7 = r7 * r5
            int r5 = (int) r7
            int r5 = android.view.View.MeasureSpec.makeMeasureSpec(r5, r8)
            int r7 = r0.w
            r4.measure(r5, r7)
        L_0x0105:
            int r1 = r1 + 1
            goto L_0x00de
        L_0x0108:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.view.ViewPager.onMeasure(int, int):void");
    }

    /* access modifiers changed from: protected */
    public void onSizeChanged(int i2, int i3, int i4, int i5) {
        super.onSizeChanged(i2, i3, i4, i5);
        if (i2 != i4) {
            a(i2, i4, this.p, this.p);
        }
    }

    private void a(int i2, int i3, int i4, int i5) {
        if (i3 <= 0 || this.g.isEmpty()) {
            ItemInfo b2 = b(this.c);
            int min = (int) ((b2 != null ? Math.min(b2.e, this.u) : BitmapDescriptorFactory.HUE_RED) * ((float) ((i2 - getPaddingLeft()) - getPaddingRight())));
            if (min != getScrollX()) {
                a(false);
                scrollTo(min, getScrollY());
            }
        } else if (!this.m.isFinished()) {
            this.m.setFinalX(getCurrentItem() * getClientWidth());
        } else {
            scrollTo((int) ((((float) getScrollX()) / ((float) (((i3 - getPaddingLeft()) - getPaddingRight()) + i5))) * ((float) (((i2 - getPaddingLeft()) - getPaddingRight()) + i4))), getScrollY());
        }
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z2, int i2, int i3, int i4, int i5) {
        boolean z3;
        int i6;
        int i7;
        int childCount = getChildCount();
        int i8 = i4 - i2;
        int i9 = i5 - i3;
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();
        int scrollX = getScrollX();
        int i10 = paddingBottom;
        int i11 = 0;
        int i12 = paddingTop;
        int i13 = paddingLeft;
        for (int i14 = 0; i14 < childCount; i14++) {
            View childAt = getChildAt(i14);
            if (childAt.getVisibility() != 8) {
                LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                if (layoutParams.isDecor) {
                    int i15 = layoutParams.gravity & 7;
                    int i16 = layoutParams.gravity & 112;
                    if (i15 == 1) {
                        i6 = Math.max((i8 - childAt.getMeasuredWidth()) / 2, i13);
                    } else if (i15 == 3) {
                        i6 = i13;
                        i13 = childAt.getMeasuredWidth() + i13;
                    } else if (i15 != 5) {
                        i6 = i13;
                    } else {
                        i6 = (i8 - paddingRight) - childAt.getMeasuredWidth();
                        paddingRight += childAt.getMeasuredWidth();
                    }
                    if (i16 == 16) {
                        i7 = Math.max((i9 - childAt.getMeasuredHeight()) / 2, i12);
                    } else if (i16 == 48) {
                        i7 = i12;
                        i12 = childAt.getMeasuredHeight() + i12;
                    } else if (i16 != 80) {
                        i7 = i12;
                    } else {
                        i7 = (i9 - i10) - childAt.getMeasuredHeight();
                        i10 += childAt.getMeasuredHeight();
                    }
                    int i17 = i6 + scrollX;
                    childAt.layout(i17, i7, childAt.getMeasuredWidth() + i17, i7 + childAt.getMeasuredHeight());
                    i11++;
                }
            }
        }
        int i18 = (i8 - i13) - paddingRight;
        for (int i19 = 0; i19 < childCount; i19++) {
            View childAt2 = getChildAt(i19);
            if (childAt2.getVisibility() != 8) {
                LayoutParams layoutParams2 = (LayoutParams) childAt2.getLayoutParams();
                if (!layoutParams2.isDecor) {
                    ItemInfo a2 = a(childAt2);
                    if (a2 != null) {
                        float f2 = (float) i18;
                        int i20 = ((int) (a2.e * f2)) + i13;
                        if (layoutParams2.b) {
                            layoutParams2.b = false;
                            childAt2.measure(MeasureSpec.makeMeasureSpec((int) (f2 * layoutParams2.a), 1073741824), MeasureSpec.makeMeasureSpec((i9 - i12) - i10, 1073741824));
                        }
                        childAt2.layout(i20, i12, childAt2.getMeasuredWidth() + i20, childAt2.getMeasuredHeight() + i12);
                    }
                }
            }
        }
        this.r = i12;
        this.s = i9 - i10;
        this.aa = i11;
        if (this.U) {
            z3 = false;
            a(this.c, false, 0, false);
        } else {
            z3 = false;
        }
        this.U = z3;
    }

    public void computeScroll() {
        this.n = true;
        if (this.m.isFinished() || !this.m.computeScrollOffset()) {
            a(true);
            return;
        }
        int scrollX = getScrollX();
        int scrollY = getScrollY();
        int currX = this.m.getCurrX();
        int currY = this.m.getCurrY();
        if (!(scrollX == currX && scrollY == currY)) {
            scrollTo(currX, currY);
            if (!c(currX)) {
                this.m.abortAnimation();
                scrollTo(0, currY);
            }
        }
        ViewCompat.postInvalidateOnAnimation(this);
    }

    private boolean c(int i2) {
        if (this.g.size() != 0) {
            ItemInfo i3 = i();
            int clientWidth = getClientWidth();
            int i4 = this.p + clientWidth;
            float f2 = (float) clientWidth;
            float f3 = ((float) this.p) / f2;
            int i5 = i3.b;
            float f4 = ((((float) i2) / f2) - i3.e) / (i3.d + f3);
            int i6 = (int) (((float) i4) * f4);
            this.W = false;
            onPageScrolled(i5, f4, i6);
            if (this.W) {
                return true;
            }
            throw new IllegalStateException("onPageScrolled did not call superclass implementation");
        } else if (this.U) {
            return false;
        } else {
            this.W = false;
            onPageScrolled(0, BitmapDescriptorFactory.HUE_RED, 0);
            if (this.W) {
                return false;
            }
            throw new IllegalStateException("onPageScrolled did not call superclass implementation");
        }
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Removed duplicated region for block: B:19:0x0066  */
    @android.support.annotation.CallSuper
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onPageScrolled(int r13, float r14, int r15) {
        /*
            r12 = this;
            int r0 = r12.aa
            r1 = 0
            r2 = 1
            if (r0 <= 0) goto L_0x006d
            int r0 = r12.getScrollX()
            int r3 = r12.getPaddingLeft()
            int r4 = r12.getPaddingRight()
            int r5 = r12.getWidth()
            int r6 = r12.getChildCount()
            r7 = r4
            r4 = r3
            r3 = 0
        L_0x001d:
            if (r3 >= r6) goto L_0x006d
            android.view.View r8 = r12.getChildAt(r3)
            android.view.ViewGroup$LayoutParams r9 = r8.getLayoutParams()
            android.support.v4.view.ViewPager$LayoutParams r9 = (android.support.v4.view.ViewPager.LayoutParams) r9
            boolean r10 = r9.isDecor
            if (r10 != 0) goto L_0x002e
            goto L_0x006a
        L_0x002e:
            int r9 = r9.gravity
            r9 = r9 & 7
            if (r9 == r2) goto L_0x004f
            r10 = 3
            if (r9 == r10) goto L_0x0049
            r10 = 5
            if (r9 == r10) goto L_0x003c
            r9 = r4
            goto L_0x005e
        L_0x003c:
            int r9 = r5 - r7
            int r10 = r8.getMeasuredWidth()
            int r9 = r9 - r10
            int r10 = r8.getMeasuredWidth()
            int r7 = r7 + r10
            goto L_0x005b
        L_0x0049:
            int r9 = r8.getWidth()
            int r9 = r9 + r4
            goto L_0x005e
        L_0x004f:
            int r9 = r8.getMeasuredWidth()
            int r9 = r5 - r9
            int r9 = r9 / 2
            int r9 = java.lang.Math.max(r9, r4)
        L_0x005b:
            r11 = r9
            r9 = r4
            r4 = r11
        L_0x005e:
            int r4 = r4 + r0
            int r10 = r8.getLeft()
            int r4 = r4 - r10
            if (r4 == 0) goto L_0x0069
            r8.offsetLeftAndRight(r4)
        L_0x0069:
            r4 = r9
        L_0x006a:
            int r3 = r3 + 1
            goto L_0x001d
        L_0x006d:
            r12.a(r13, r14, r15)
            android.support.v4.view.ViewPager$PageTransformer r13 = r12.af
            if (r13 == 0) goto L_0x00a1
            int r13 = r12.getScrollX()
            int r14 = r12.getChildCount()
        L_0x007c:
            if (r1 >= r14) goto L_0x00a1
            android.view.View r15 = r12.getChildAt(r1)
            android.view.ViewGroup$LayoutParams r0 = r15.getLayoutParams()
            android.support.v4.view.ViewPager$LayoutParams r0 = (android.support.v4.view.ViewPager.LayoutParams) r0
            boolean r0 = r0.isDecor
            if (r0 == 0) goto L_0x008d
            goto L_0x009e
        L_0x008d:
            int r0 = r15.getLeft()
            int r0 = r0 - r13
            float r0 = (float) r0
            int r3 = r12.getClientWidth()
            float r3 = (float) r3
            float r0 = r0 / r3
            android.support.v4.view.ViewPager$PageTransformer r3 = r12.af
            r3.transformPage(r15, r0)
        L_0x009e:
            int r1 = r1 + 1
            goto L_0x007c
        L_0x00a1:
            r12.W = r2
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.view.ViewPager.onPageScrolled(int, float, int):void");
    }

    private void a(int i2, float f2, int i3) {
        if (this.ac != null) {
            this.ac.onPageScrolled(i2, f2, i3);
        }
        if (this.ab != null) {
            int size = this.ab.size();
            for (int i4 = 0; i4 < size; i4++) {
                OnPageChangeListener onPageChangeListener = (OnPageChangeListener) this.ab.get(i4);
                if (onPageChangeListener != null) {
                    onPageChangeListener.onPageScrolled(i2, f2, i3);
                }
            }
        }
        if (this.ad != null) {
            this.ad.onPageScrolled(i2, f2, i3);
        }
    }

    private void d(int i2) {
        if (this.ac != null) {
            this.ac.onPageSelected(i2);
        }
        if (this.ab != null) {
            int size = this.ab.size();
            for (int i3 = 0; i3 < size; i3++) {
                OnPageChangeListener onPageChangeListener = (OnPageChangeListener) this.ab.get(i3);
                if (onPageChangeListener != null) {
                    onPageChangeListener.onPageSelected(i2);
                }
            }
        }
        if (this.ad != null) {
            this.ad.onPageSelected(i2);
        }
    }

    private void e(int i2) {
        if (this.ac != null) {
            this.ac.onPageScrollStateChanged(i2);
        }
        if (this.ab != null) {
            int size = this.ab.size();
            for (int i3 = 0; i3 < size; i3++) {
                OnPageChangeListener onPageChangeListener = (OnPageChangeListener) this.ab.get(i3);
                if (onPageChangeListener != null) {
                    onPageChangeListener.onPageScrollStateChanged(i2);
                }
            }
        }
        if (this.ad != null) {
            this.ad.onPageScrollStateChanged(i2);
        }
    }

    private void a(boolean z2) {
        boolean z3 = this.al == 2;
        if (z3) {
            setScrollingCacheEnabled(false);
            if (!this.m.isFinished()) {
                this.m.abortAnimation();
                int scrollX = getScrollX();
                int scrollY = getScrollY();
                int currX = this.m.getCurrX();
                int currY = this.m.getCurrY();
                if (!(scrollX == currX && scrollY == currY)) {
                    scrollTo(currX, currY);
                    if (currX != scrollX) {
                        c(currX);
                    }
                }
            }
        }
        this.z = false;
        boolean z4 = z3;
        for (int i2 = 0; i2 < this.g.size(); i2++) {
            ItemInfo itemInfo = (ItemInfo) this.g.get(i2);
            if (itemInfo.c) {
                itemInfo.c = false;
                z4 = true;
            }
        }
        if (!z4) {
            return;
        }
        if (z2) {
            ViewCompat.postOnAnimation(this, this.ak);
        } else {
            this.ak.run();
        }
    }

    private boolean a(float f2, float f3) {
        return (f2 < ((float) this.E) && f3 > BitmapDescriptorFactory.HUE_RED) || (f2 > ((float) (getWidth() - this.E)) && f3 < BitmapDescriptorFactory.HUE_RED);
    }

    private void b(boolean z2) {
        int childCount = getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            getChildAt(i2).setLayerType(z2 ? this.ag : 0, null);
        }
    }

    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        MotionEvent motionEvent2 = motionEvent;
        int action = motionEvent.getAction() & 255;
        if (action == 3 || action == 1) {
            h();
            return false;
        }
        if (action != 0) {
            if (this.B) {
                return true;
            }
            if (this.C) {
                return false;
            }
        }
        if (action == 0) {
            float x2 = motionEvent.getX();
            this.I = x2;
            this.G = x2;
            float y2 = motionEvent.getY();
            this.J = y2;
            this.H = y2;
            this.K = motionEvent2.getPointerId(0);
            this.C = false;
            this.n = true;
            this.m.computeScrollOffset();
            if (this.al != 2 || Math.abs(this.m.getFinalX() - this.m.getCurrX()) <= this.P) {
                a(false);
                this.B = false;
            } else {
                this.m.abortAnimation();
                this.z = false;
                c();
                this.B = true;
                c(true);
                setScrollState(1);
            }
        } else if (action == 2) {
            int i2 = this.K;
            if (i2 != -1) {
                int findPointerIndex = motionEvent2.findPointerIndex(i2);
                float x3 = motionEvent2.getX(findPointerIndex);
                float f2 = x3 - this.G;
                float abs = Math.abs(f2);
                float y3 = motionEvent2.getY(findPointerIndex);
                float abs2 = Math.abs(y3 - this.J);
                if (f2 != BitmapDescriptorFactory.HUE_RED && !a(this.G, f2)) {
                    if (canScroll(this, false, (int) f2, (int) x3, (int) y3)) {
                        this.G = x3;
                        this.H = y3;
                        this.C = true;
                        return false;
                    }
                }
                if (abs > ((float) this.F) && abs * 0.5f > abs2) {
                    this.B = true;
                    c(true);
                    setScrollState(1);
                    this.G = f2 > BitmapDescriptorFactory.HUE_RED ? this.I + ((float) this.F) : this.I - ((float) this.F);
                    this.H = y3;
                    setScrollingCacheEnabled(true);
                } else if (abs2 > ((float) this.F)) {
                    this.C = true;
                }
                if (this.B && b(x3)) {
                    ViewCompat.postInvalidateOnAnimation(this);
                }
            }
        } else if (action == 6) {
            a(motionEvent);
        }
        if (this.L == null) {
            this.L = VelocityTracker.obtain();
        }
        this.L.addMovement(motionEvent2);
        return this.B;
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (this.Q) {
            return true;
        }
        boolean z2 = false;
        if ((motionEvent.getAction() == 0 && motionEvent.getEdgeFlags() != 0) || this.b == null || this.b.getCount() == 0) {
            return false;
        }
        if (this.L == null) {
            this.L = VelocityTracker.obtain();
        }
        this.L.addMovement(motionEvent);
        switch (motionEvent.getAction() & 255) {
            case 0:
                this.m.abortAnimation();
                this.z = false;
                c();
                float x2 = motionEvent.getX();
                this.I = x2;
                this.G = x2;
                float y2 = motionEvent.getY();
                this.J = y2;
                this.H = y2;
                this.K = motionEvent.getPointerId(0);
                break;
            case 1:
                if (this.B) {
                    VelocityTracker velocityTracker = this.L;
                    velocityTracker.computeCurrentVelocity(1000, (float) this.N);
                    int xVelocity = (int) velocityTracker.getXVelocity(this.K);
                    this.z = true;
                    int clientWidth = getClientWidth();
                    int scrollX = getScrollX();
                    ItemInfo i2 = i();
                    float f2 = (float) clientWidth;
                    a(a(i2.b, ((((float) scrollX) / f2) - i2.e) / (i2.d + (((float) this.p) / f2)), xVelocity, (int) (motionEvent.getX(motionEvent.findPointerIndex(this.K)) - this.I)), true, true, xVelocity);
                    z2 = h();
                    break;
                }
                break;
            case 2:
                if (!this.B) {
                    int findPointerIndex = motionEvent.findPointerIndex(this.K);
                    if (findPointerIndex == -1) {
                        z2 = h();
                        break;
                    } else {
                        float x3 = motionEvent.getX(findPointerIndex);
                        float abs = Math.abs(x3 - this.G);
                        float y3 = motionEvent.getY(findPointerIndex);
                        float abs2 = Math.abs(y3 - this.H);
                        if (abs > ((float) this.F) && abs > abs2) {
                            this.B = true;
                            c(true);
                            this.G = x3 - this.I > BitmapDescriptorFactory.HUE_RED ? this.I + ((float) this.F) : this.I - ((float) this.F);
                            this.H = y3;
                            setScrollState(1);
                            setScrollingCacheEnabled(true);
                            ViewParent parent = getParent();
                            if (parent != null) {
                                parent.requestDisallowInterceptTouchEvent(true);
                            }
                        }
                    }
                }
                if (this.B) {
                    z2 = false | b(motionEvent.getX(motionEvent.findPointerIndex(this.K)));
                    break;
                }
                break;
            case 3:
                if (this.B) {
                    a(this.c, true, 0, false);
                    z2 = h();
                    break;
                }
                break;
            case 5:
                int actionIndex = motionEvent.getActionIndex();
                this.G = motionEvent.getX(actionIndex);
                this.K = motionEvent.getPointerId(actionIndex);
                break;
            case 6:
                a(motionEvent);
                this.G = motionEvent.getX(motionEvent.findPointerIndex(this.K));
                break;
        }
        if (z2) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
        return true;
    }

    private boolean h() {
        this.K = -1;
        j();
        this.S.onRelease();
        this.T.onRelease();
        return this.S.isFinished() || this.T.isFinished();
    }

    private void c(boolean z2) {
        ViewParent parent = getParent();
        if (parent != null) {
            parent.requestDisallowInterceptTouchEvent(z2);
        }
    }

    private boolean b(float f2) {
        boolean z2;
        boolean z3;
        float f3 = this.G - f2;
        this.G = f2;
        float scrollX = ((float) getScrollX()) + f3;
        float clientWidth = (float) getClientWidth();
        float f4 = this.t * clientWidth;
        float f5 = this.u * clientWidth;
        boolean z4 = false;
        ItemInfo itemInfo = (ItemInfo) this.g.get(0);
        ItemInfo itemInfo2 = (ItemInfo) this.g.get(this.g.size() - 1);
        if (itemInfo.b != 0) {
            f4 = itemInfo.e * clientWidth;
            z2 = false;
        } else {
            z2 = true;
        }
        if (itemInfo2.b != this.b.getCount() - 1) {
            f5 = itemInfo2.e * clientWidth;
            z3 = false;
        } else {
            z3 = true;
        }
        if (scrollX < f4) {
            if (z2) {
                this.S.onPull(Math.abs(f4 - scrollX) / clientWidth);
                z4 = true;
            }
            scrollX = f4;
        } else if (scrollX > f5) {
            if (z3) {
                this.T.onPull(Math.abs(scrollX - f5) / clientWidth);
                z4 = true;
            }
            scrollX = f5;
        }
        int i2 = (int) scrollX;
        this.G += scrollX - ((float) i2);
        scrollTo(i2, getScrollY());
        c(i2);
        return z4;
    }

    private ItemInfo i() {
        int clientWidth = getClientWidth();
        float scrollX = clientWidth > 0 ? ((float) getScrollX()) / ((float) clientWidth) : BitmapDescriptorFactory.HUE_RED;
        float f2 = clientWidth > 0 ? ((float) this.p) / ((float) clientWidth) : BitmapDescriptorFactory.HUE_RED;
        ItemInfo itemInfo = null;
        int i2 = 0;
        boolean z2 = true;
        int i3 = -1;
        float f3 = BitmapDescriptorFactory.HUE_RED;
        float f4 = BitmapDescriptorFactory.HUE_RED;
        while (i2 < this.g.size()) {
            ItemInfo itemInfo2 = (ItemInfo) this.g.get(i2);
            if (!z2) {
                int i4 = i3 + 1;
                if (itemInfo2.b != i4) {
                    itemInfo2 = this.h;
                    itemInfo2.e = f3 + f4 + f2;
                    itemInfo2.b = i4;
                    itemInfo2.d = this.b.getPageWidth(itemInfo2.b);
                    i2--;
                }
            }
            f3 = itemInfo2.e;
            float f5 = itemInfo2.d + f3 + f2;
            if (!z2 && scrollX < f3) {
                return itemInfo;
            }
            if (scrollX < f5 || i2 == this.g.size() - 1) {
                return itemInfo2;
            }
            i3 = itemInfo2.b;
            f4 = itemInfo2.d;
            i2++;
            itemInfo = itemInfo2;
            z2 = false;
        }
        return itemInfo;
    }

    private int a(int i2, float f2, int i3, int i4) {
        if (Math.abs(i4) <= this.O || Math.abs(i3) <= this.M) {
            i2 += (int) (f2 + (i2 >= this.c ? 0.4f : 0.6f));
        } else if (i3 <= 0) {
            i2++;
        }
        if (this.g.size() <= 0) {
            return i2;
        }
        return Math.max(((ItemInfo) this.g.get(0)).b, Math.min(i2, ((ItemInfo) this.g.get(this.g.size() - 1)).b));
    }

    public void draw(Canvas canvas) {
        super.draw(canvas);
        int overScrollMode = getOverScrollMode();
        boolean z2 = false;
        if (overScrollMode == 0 || (overScrollMode == 1 && this.b != null && this.b.getCount() > 1)) {
            if (!this.S.isFinished()) {
                int save = canvas.save();
                int height = (getHeight() - getPaddingTop()) - getPaddingBottom();
                int width = getWidth();
                canvas.rotate(270.0f);
                canvas.translate((float) ((-height) + getPaddingTop()), this.t * ((float) width));
                this.S.setSize(height, width);
                z2 = false | this.S.draw(canvas);
                canvas.restoreToCount(save);
            }
            if (!this.T.isFinished()) {
                int save2 = canvas.save();
                int width2 = getWidth();
                int height2 = (getHeight() - getPaddingTop()) - getPaddingBottom();
                canvas.rotate(90.0f);
                canvas.translate((float) (-getPaddingTop()), (-(this.u + 1.0f)) * ((float) width2));
                this.T.setSize(height2, width2);
                z2 |= this.T.draw(canvas);
                canvas.restoreToCount(save2);
            }
        } else {
            this.S.finish();
            this.T.finish();
        }
        if (z2) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        float f2;
        float f3;
        super.onDraw(canvas);
        if (this.p > 0 && this.q != null && this.g.size() > 0 && this.b != null) {
            int scrollX = getScrollX();
            int width = getWidth();
            float f4 = (float) width;
            float f5 = ((float) this.p) / f4;
            int i2 = 0;
            ItemInfo itemInfo = (ItemInfo) this.g.get(0);
            float f6 = itemInfo.e;
            int size = this.g.size();
            int i3 = itemInfo.b;
            int i4 = ((ItemInfo) this.g.get(size - 1)).b;
            while (i3 < i4) {
                while (i3 > itemInfo.b && i2 < size) {
                    i2++;
                    itemInfo = (ItemInfo) this.g.get(i2);
                }
                if (i3 == itemInfo.b) {
                    f2 = (itemInfo.e + itemInfo.d) * f4;
                    f6 = itemInfo.e + itemInfo.d + f5;
                } else {
                    float pageWidth = this.b.getPageWidth(i3);
                    f2 = (f6 + pageWidth) * f4;
                    f6 += pageWidth + f5;
                }
                if (((float) this.p) + f2 > ((float) scrollX)) {
                    f3 = f5;
                    this.q.setBounds(Math.round(f2), this.r, Math.round(((float) this.p) + f2), this.s);
                    this.q.draw(canvas);
                } else {
                    Canvas canvas2 = canvas;
                    f3 = f5;
                }
                if (f2 <= ((float) (scrollX + width))) {
                    i3++;
                    f5 = f3;
                } else {
                    return;
                }
            }
        }
    }

    public boolean beginFakeDrag() {
        if (this.B) {
            return false;
        }
        this.Q = true;
        setScrollState(1);
        this.G = BitmapDescriptorFactory.HUE_RED;
        this.I = BitmapDescriptorFactory.HUE_RED;
        if (this.L == null) {
            this.L = VelocityTracker.obtain();
        } else {
            this.L.clear();
        }
        long uptimeMillis = SystemClock.uptimeMillis();
        MotionEvent obtain = MotionEvent.obtain(uptimeMillis, uptimeMillis, 0, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, 0);
        this.L.addMovement(obtain);
        obtain.recycle();
        this.R = uptimeMillis;
        return true;
    }

    public void endFakeDrag() {
        if (!this.Q) {
            throw new IllegalStateException("No fake drag in progress. Call beginFakeDrag first.");
        }
        if (this.b != null) {
            VelocityTracker velocityTracker = this.L;
            velocityTracker.computeCurrentVelocity(1000, (float) this.N);
            int xVelocity = (int) velocityTracker.getXVelocity(this.K);
            this.z = true;
            int clientWidth = getClientWidth();
            int scrollX = getScrollX();
            ItemInfo i2 = i();
            a(a(i2.b, ((((float) scrollX) / ((float) clientWidth)) - i2.e) / i2.d, xVelocity, (int) (this.G - this.I)), true, true, xVelocity);
        }
        j();
        this.Q = false;
    }

    public void fakeDragBy(float f2) {
        if (!this.Q) {
            throw new IllegalStateException("No fake drag in progress. Call beginFakeDrag first.");
        } else if (this.b != null) {
            this.G += f2;
            float scrollX = ((float) getScrollX()) - f2;
            float clientWidth = (float) getClientWidth();
            float f3 = this.t * clientWidth;
            float f4 = this.u * clientWidth;
            ItemInfo itemInfo = (ItemInfo) this.g.get(0);
            ItemInfo itemInfo2 = (ItemInfo) this.g.get(this.g.size() - 1);
            if (itemInfo.b != 0) {
                f3 = itemInfo.e * clientWidth;
            }
            if (itemInfo2.b != this.b.getCount() - 1) {
                f4 = itemInfo2.e * clientWidth;
            }
            if (scrollX < f3) {
                scrollX = f3;
            } else if (scrollX > f4) {
                scrollX = f4;
            }
            int i2 = (int) scrollX;
            this.G += scrollX - ((float) i2);
            scrollTo(i2, getScrollY());
            c(i2);
            MotionEvent obtain = MotionEvent.obtain(this.R, SystemClock.uptimeMillis(), 2, this.G, BitmapDescriptorFactory.HUE_RED, 0);
            this.L.addMovement(obtain);
            obtain.recycle();
        }
    }

    public boolean isFakeDragging() {
        return this.Q;
    }

    private void a(MotionEvent motionEvent) {
        int actionIndex = motionEvent.getActionIndex();
        if (motionEvent.getPointerId(actionIndex) == this.K) {
            int i2 = actionIndex == 0 ? 1 : 0;
            this.G = motionEvent.getX(i2);
            this.K = motionEvent.getPointerId(i2);
            if (this.L != null) {
                this.L.clear();
            }
        }
    }

    private void j() {
        this.B = false;
        this.C = false;
        if (this.L != null) {
            this.L.recycle();
            this.L = null;
        }
    }

    private void setScrollingCacheEnabled(boolean z2) {
        if (this.y != z2) {
            this.y = z2;
        }
    }

    public boolean canScrollHorizontally(int i2) {
        boolean z2 = false;
        if (this.b == null) {
            return false;
        }
        int clientWidth = getClientWidth();
        int scrollX = getScrollX();
        if (i2 < 0) {
            if (scrollX > ((int) (((float) clientWidth) * this.t))) {
                z2 = true;
            }
            return z2;
        } else if (i2 <= 0) {
            return false;
        } else {
            if (scrollX < ((int) (((float) clientWidth) * this.u))) {
                z2 = true;
            }
            return z2;
        }
    }

    /* access modifiers changed from: protected */
    public boolean canScroll(View view, boolean z2, int i2, int i3, int i4) {
        View view2 = view;
        boolean z3 = true;
        if (view2 instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view2;
            int scrollX = view2.getScrollX();
            int scrollY = view2.getScrollY();
            for (int childCount = viewGroup.getChildCount() - 1; childCount >= 0; childCount--) {
                View childAt = viewGroup.getChildAt(childCount);
                int i5 = i3 + scrollX;
                if (i5 >= childAt.getLeft() && i5 < childAt.getRight()) {
                    int i6 = i4 + scrollY;
                    if (i6 >= childAt.getTop() && i6 < childAt.getBottom()) {
                        if (canScroll(childAt, true, i2, i5 - childAt.getLeft(), i6 - childAt.getTop())) {
                            return true;
                        }
                    }
                }
            }
        }
        if (!z2 || !view2.canScrollHorizontally(-i2)) {
            z3 = false;
        }
        return z3;
    }

    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        return super.dispatchKeyEvent(keyEvent) || executeKeyEvent(keyEvent);
    }

    public boolean executeKeyEvent(@NonNull KeyEvent keyEvent) {
        if (keyEvent.getAction() == 0) {
            int keyCode = keyEvent.getKeyCode();
            if (keyCode != 61) {
                switch (keyCode) {
                    case 21:
                        if (keyEvent.hasModifiers(2)) {
                            return d();
                        }
                        return arrowScroll(17);
                    case 22:
                        if (keyEvent.hasModifiers(2)) {
                            return e();
                        }
                        return arrowScroll(66);
                }
            } else if (keyEvent.hasNoModifiers()) {
                return arrowScroll(2);
            } else {
                if (keyEvent.hasModifiers(1)) {
                    return arrowScroll(1);
                }
            }
        }
        return false;
    }

    public boolean arrowScroll(int i2) {
        boolean requestFocus;
        boolean z2;
        View findFocus = findFocus();
        boolean z3 = false;
        View view = null;
        if (findFocus != this) {
            if (findFocus != null) {
                ViewParent parent = findFocus.getParent();
                while (true) {
                    if (!(parent instanceof ViewGroup)) {
                        z2 = false;
                        break;
                    } else if (parent == this) {
                        z2 = true;
                        break;
                    } else {
                        parent = parent.getParent();
                    }
                }
                if (!z2) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(findFocus.getClass().getSimpleName());
                    for (ViewParent parent2 = findFocus.getParent(); parent2 instanceof ViewGroup; parent2 = parent2.getParent()) {
                        sb.append(" => ");
                        sb.append(parent2.getClass().getSimpleName());
                    }
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("arrowScroll tried to find focus based on non-child current focused view ");
                    sb2.append(sb.toString());
                    Log.e("ViewPager", sb2.toString());
                }
            }
            view = findFocus;
        }
        View findNextFocus = FocusFinder.getInstance().findNextFocus(this, view, i2);
        if (findNextFocus != null && findNextFocus != view) {
            if (i2 == 17) {
                int i3 = a(this.i, findNextFocus).left;
                int i4 = a(this.i, view).left;
                if (view == null || i3 < i4) {
                    requestFocus = findNextFocus.requestFocus();
                } else {
                    requestFocus = d();
                }
            } else if (i2 == 66) {
                int i5 = a(this.i, findNextFocus).left;
                int i6 = a(this.i, view).left;
                if (view == null || i5 > i6) {
                    requestFocus = findNextFocus.requestFocus();
                } else {
                    requestFocus = e();
                }
            }
            z3 = requestFocus;
        } else if (i2 == 17 || i2 == 1) {
            z3 = d();
        } else if (i2 == 66 || i2 == 2) {
            z3 = e();
        }
        if (z3) {
            playSoundEffect(SoundEffectConstants.getContantForFocusDirection(i2));
        }
        return z3;
    }

    private Rect a(Rect rect, View view) {
        if (rect == null) {
            rect = new Rect();
        }
        if (view == null) {
            rect.set(0, 0, 0, 0);
            return rect;
        }
        rect.left = view.getLeft();
        rect.right = view.getRight();
        rect.top = view.getTop();
        rect.bottom = view.getBottom();
        ViewParent parent = view.getParent();
        while ((parent instanceof ViewGroup) && parent != this) {
            ViewGroup viewGroup = (ViewGroup) parent;
            rect.left += viewGroup.getLeft();
            rect.right += viewGroup.getRight();
            rect.top += viewGroup.getTop();
            rect.bottom += viewGroup.getBottom();
            parent = viewGroup.getParent();
        }
        return rect;
    }

    /* access modifiers changed from: 0000 */
    public boolean d() {
        if (this.c <= 0) {
            return false;
        }
        setCurrentItem(this.c - 1, true);
        return true;
    }

    /* access modifiers changed from: 0000 */
    public boolean e() {
        if (this.b == null || this.c >= this.b.getCount() - 1) {
            return false;
        }
        setCurrentItem(this.c + 1, true);
        return true;
    }

    public void addFocusables(ArrayList<View> arrayList, int i2, int i3) {
        int size = arrayList.size();
        int descendantFocusability = getDescendantFocusability();
        if (descendantFocusability != 393216) {
            for (int i4 = 0; i4 < getChildCount(); i4++) {
                View childAt = getChildAt(i4);
                if (childAt.getVisibility() == 0) {
                    ItemInfo a2 = a(childAt);
                    if (a2 != null && a2.b == this.c) {
                        childAt.addFocusables(arrayList, i2, i3);
                    }
                }
            }
        }
        if ((descendantFocusability == 262144 && size != arrayList.size()) || !isFocusable()) {
            return;
        }
        if (((i3 & 1) != 1 || !isInTouchMode() || isFocusableInTouchMode()) && arrayList != null) {
            arrayList.add(this);
        }
    }

    public void addTouchables(ArrayList<View> arrayList) {
        for (int i2 = 0; i2 < getChildCount(); i2++) {
            View childAt = getChildAt(i2);
            if (childAt.getVisibility() == 0) {
                ItemInfo a2 = a(childAt);
                if (a2 != null && a2.b == this.c) {
                    childAt.addTouchables(arrayList);
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public boolean onRequestFocusInDescendants(int i2, Rect rect) {
        int i3;
        int i4;
        int childCount = getChildCount();
        int i5 = -1;
        if ((i2 & 2) != 0) {
            i5 = childCount;
            i4 = 0;
            i3 = 1;
        } else {
            i4 = childCount - 1;
            i3 = -1;
        }
        while (i4 != i5) {
            View childAt = getChildAt(i4);
            if (childAt.getVisibility() == 0) {
                ItemInfo a2 = a(childAt);
                if (a2 != null && a2.b == this.c && childAt.requestFocus(i2, rect)) {
                    return true;
                }
            }
            i4 += i3;
        }
        return false;
    }

    public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        if (accessibilityEvent.getEventType() == 4096) {
            return super.dispatchPopulateAccessibilityEvent(accessibilityEvent);
        }
        int childCount = getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            View childAt = getChildAt(i2);
            if (childAt.getVisibility() == 0) {
                ItemInfo a2 = a(childAt);
                if (a2 != null && a2.b == this.c && childAt.dispatchPopulateAccessibilityEvent(accessibilityEvent)) {
                    return true;
                }
            }
        }
        return false;
    }

    /* access modifiers changed from: protected */
    public android.view.ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams();
    }

    /* access modifiers changed from: protected */
    public android.view.ViewGroup.LayoutParams generateLayoutParams(android.view.ViewGroup.LayoutParams layoutParams) {
        return generateDefaultLayoutParams();
    }

    /* access modifiers changed from: protected */
    public boolean checkLayoutParams(android.view.ViewGroup.LayoutParams layoutParams) {
        return (layoutParams instanceof LayoutParams) && super.checkLayoutParams(layoutParams);
    }

    public android.view.ViewGroup.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(getContext(), attributeSet);
    }
}
