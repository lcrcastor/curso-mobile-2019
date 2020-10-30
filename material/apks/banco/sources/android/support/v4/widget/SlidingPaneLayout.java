package android.support.v4.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.ClassLoaderCreator;
import android.os.Parcelable.Creator;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.AbsSavedState;
import android.support.v4.view.AccessibilityDelegateCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.support.v4.widget.ViewDragHelper.Callback;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class SlidingPaneLayout extends ViewGroup {
    static final SlidingPanelLayoutImpl h;
    View a;
    float b;
    int c;
    boolean d;
    final ViewDragHelper e;
    boolean f;
    final ArrayList<DisableLayerRunnable> g;
    private int i;
    private int j;
    private Drawable k;
    private Drawable l;
    private final int m;
    private boolean n;
    private float o;
    private int p;
    private float q;
    private float r;
    private PanelSlideListener s;
    private boolean t;
    private final Rect u;

    class AccessibilityDelegate extends AccessibilityDelegateCompat {
        private final Rect c = new Rect();

        AccessibilityDelegate() {
        }

        public void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            AccessibilityNodeInfoCompat obtain = AccessibilityNodeInfoCompat.obtain(accessibilityNodeInfoCompat);
            super.onInitializeAccessibilityNodeInfo(view, obtain);
            a(accessibilityNodeInfoCompat, obtain);
            obtain.recycle();
            accessibilityNodeInfoCompat.setClassName(SlidingPaneLayout.class.getName());
            accessibilityNodeInfoCompat.setSource(view);
            ViewParent parentForAccessibility = ViewCompat.getParentForAccessibility(view);
            if (parentForAccessibility instanceof View) {
                accessibilityNodeInfoCompat.setParent((View) parentForAccessibility);
            }
            int childCount = SlidingPaneLayout.this.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childAt = SlidingPaneLayout.this.getChildAt(i);
                if (!a(childAt) && childAt.getVisibility() == 0) {
                    ViewCompat.setImportantForAccessibility(childAt, 1);
                    accessibilityNodeInfoCompat.addChild(childAt);
                }
            }
        }

        public void onInitializeAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
            super.onInitializeAccessibilityEvent(view, accessibilityEvent);
            accessibilityEvent.setClassName(SlidingPaneLayout.class.getName());
        }

        public boolean onRequestSendAccessibilityEvent(ViewGroup viewGroup, View view, AccessibilityEvent accessibilityEvent) {
            if (!a(view)) {
                return super.onRequestSendAccessibilityEvent(viewGroup, view, accessibilityEvent);
            }
            return false;
        }

        public boolean a(View view) {
            return SlidingPaneLayout.this.f(view);
        }

        private void a(AccessibilityNodeInfoCompat accessibilityNodeInfoCompat, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat2) {
            Rect rect = this.c;
            accessibilityNodeInfoCompat2.getBoundsInParent(rect);
            accessibilityNodeInfoCompat.setBoundsInParent(rect);
            accessibilityNodeInfoCompat2.getBoundsInScreen(rect);
            accessibilityNodeInfoCompat.setBoundsInScreen(rect);
            accessibilityNodeInfoCompat.setVisibleToUser(accessibilityNodeInfoCompat2.isVisibleToUser());
            accessibilityNodeInfoCompat.setPackageName(accessibilityNodeInfoCompat2.getPackageName());
            accessibilityNodeInfoCompat.setClassName(accessibilityNodeInfoCompat2.getClassName());
            accessibilityNodeInfoCompat.setContentDescription(accessibilityNodeInfoCompat2.getContentDescription());
            accessibilityNodeInfoCompat.setEnabled(accessibilityNodeInfoCompat2.isEnabled());
            accessibilityNodeInfoCompat.setClickable(accessibilityNodeInfoCompat2.isClickable());
            accessibilityNodeInfoCompat.setFocusable(accessibilityNodeInfoCompat2.isFocusable());
            accessibilityNodeInfoCompat.setFocused(accessibilityNodeInfoCompat2.isFocused());
            accessibilityNodeInfoCompat.setAccessibilityFocused(accessibilityNodeInfoCompat2.isAccessibilityFocused());
            accessibilityNodeInfoCompat.setSelected(accessibilityNodeInfoCompat2.isSelected());
            accessibilityNodeInfoCompat.setLongClickable(accessibilityNodeInfoCompat2.isLongClickable());
            accessibilityNodeInfoCompat.addAction(accessibilityNodeInfoCompat2.getActions());
            accessibilityNodeInfoCompat.setMovementGranularities(accessibilityNodeInfoCompat2.getMovementGranularities());
        }
    }

    class DisableLayerRunnable implements Runnable {
        final View a;

        DisableLayerRunnable(View view) {
            this.a = view;
        }

        public void run() {
            if (this.a.getParent() == SlidingPaneLayout.this) {
                this.a.setLayerType(0, null);
                SlidingPaneLayout.this.e(this.a);
            }
            SlidingPaneLayout.this.g.remove(this);
        }
    }

    class DragHelperCallback extends Callback {
        DragHelperCallback() {
        }

        public boolean tryCaptureView(View view, int i) {
            if (SlidingPaneLayout.this.d) {
                return false;
            }
            return ((LayoutParams) view.getLayoutParams()).a;
        }

        public void onViewDragStateChanged(int i) {
            if (SlidingPaneLayout.this.e.getViewDragState() != 0) {
                return;
            }
            if (SlidingPaneLayout.this.b == BitmapDescriptorFactory.HUE_RED) {
                SlidingPaneLayout.this.d(SlidingPaneLayout.this.a);
                SlidingPaneLayout.this.c(SlidingPaneLayout.this.a);
                SlidingPaneLayout.this.f = false;
                return;
            }
            SlidingPaneLayout.this.b(SlidingPaneLayout.this.a);
            SlidingPaneLayout.this.f = true;
        }

        public void onViewCaptured(View view, int i) {
            SlidingPaneLayout.this.a();
        }

        public void onViewPositionChanged(View view, int i, int i2, int i3, int i4) {
            SlidingPaneLayout.this.a(i);
            SlidingPaneLayout.this.invalidate();
        }

        public void onViewReleased(View view, float f, float f2) {
            int i;
            LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
            if (SlidingPaneLayout.this.b()) {
                int paddingRight = SlidingPaneLayout.this.getPaddingRight() + layoutParams.rightMargin;
                if (f < BitmapDescriptorFactory.HUE_RED || (f == BitmapDescriptorFactory.HUE_RED && SlidingPaneLayout.this.b > 0.5f)) {
                    paddingRight += SlidingPaneLayout.this.c;
                }
                i = (SlidingPaneLayout.this.getWidth() - paddingRight) - SlidingPaneLayout.this.a.getWidth();
            } else {
                i = layoutParams.leftMargin + SlidingPaneLayout.this.getPaddingLeft();
                if (f > BitmapDescriptorFactory.HUE_RED || (f == BitmapDescriptorFactory.HUE_RED && SlidingPaneLayout.this.b > 0.5f)) {
                    i += SlidingPaneLayout.this.c;
                }
            }
            SlidingPaneLayout.this.e.settleCapturedViewAt(i, view.getTop());
            SlidingPaneLayout.this.invalidate();
        }

        public int getViewHorizontalDragRange(View view) {
            return SlidingPaneLayout.this.c;
        }

        public int clampViewPositionHorizontal(View view, int i, int i2) {
            LayoutParams layoutParams = (LayoutParams) SlidingPaneLayout.this.a.getLayoutParams();
            if (SlidingPaneLayout.this.b()) {
                int width = SlidingPaneLayout.this.getWidth() - ((SlidingPaneLayout.this.getPaddingRight() + layoutParams.rightMargin) + SlidingPaneLayout.this.a.getWidth());
                return Math.max(Math.min(i, width), width - SlidingPaneLayout.this.c);
            }
            int paddingLeft = SlidingPaneLayout.this.getPaddingLeft() + layoutParams.leftMargin;
            return Math.min(Math.max(i, paddingLeft), SlidingPaneLayout.this.c + paddingLeft);
        }

        public int clampViewPositionVertical(View view, int i, int i2) {
            return view.getTop();
        }

        public void onEdgeDragStarted(int i, int i2) {
            SlidingPaneLayout.this.e.captureChildView(SlidingPaneLayout.this.a, i2);
        }
    }

    public static class LayoutParams extends MarginLayoutParams {
        private static final int[] d = {16843137};
        boolean a;
        boolean b;
        Paint c;
        public float weight = BitmapDescriptorFactory.HUE_RED;

        public LayoutParams() {
            super(-1, -1);
        }

        public LayoutParams(int i, int i2) {
            super(i, i2);
        }

        public LayoutParams(@NonNull android.view.ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
        }

        public LayoutParams(@NonNull MarginLayoutParams marginLayoutParams) {
            super(marginLayoutParams);
        }

        public LayoutParams(@NonNull LayoutParams layoutParams) {
            super(layoutParams);
            this.weight = layoutParams.weight;
        }

        public LayoutParams(@NonNull Context context, @Nullable AttributeSet attributeSet) {
            super(context, attributeSet);
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, d);
            this.weight = obtainStyledAttributes.getFloat(0, BitmapDescriptorFactory.HUE_RED);
            obtainStyledAttributes.recycle();
        }
    }

    public interface PanelSlideListener {
        void onPanelClosed(@NonNull View view);

        void onPanelOpened(@NonNull View view);

        void onPanelSlide(@NonNull View view, float f);
    }

    static class SavedState extends AbsSavedState {
        public static final Creator<SavedState> CREATOR = new ClassLoaderCreator<SavedState>() {
            /* renamed from: a */
            public SavedState createFromParcel(Parcel parcel, ClassLoader classLoader) {
                return new SavedState(parcel, null);
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
        boolean a;

        SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        SavedState(Parcel parcel, ClassLoader classLoader) {
            super(parcel, classLoader);
            this.a = parcel.readInt() != 0;
        }

        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeInt(this.a ? 1 : 0);
        }
    }

    public static class SimplePanelSlideListener implements PanelSlideListener {
        public void onPanelClosed(View view) {
        }

        public void onPanelOpened(View view) {
        }

        public void onPanelSlide(View view, float f) {
        }
    }

    interface SlidingPanelLayoutImpl {
        void a(SlidingPaneLayout slidingPaneLayout, View view);
    }

    static class SlidingPanelLayoutImplBase implements SlidingPanelLayoutImpl {
        SlidingPanelLayoutImplBase() {
        }

        public void a(SlidingPaneLayout slidingPaneLayout, View view) {
            ViewCompat.postInvalidateOnAnimation(slidingPaneLayout, view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
        }
    }

    @RequiresApi(16)
    static class SlidingPanelLayoutImplJB extends SlidingPanelLayoutImplBase {
        private Method a;
        private Field b;

        SlidingPanelLayoutImplJB() {
            try {
                this.a = View.class.getDeclaredMethod("getDisplayList", null);
            } catch (NoSuchMethodException e) {
                Log.e("SlidingPaneLayout", "Couldn't fetch getDisplayList method; dimming won't work right.", e);
            }
            try {
                this.b = View.class.getDeclaredField("mRecreateDisplayList");
                this.b.setAccessible(true);
            } catch (NoSuchFieldException e2) {
                Log.e("SlidingPaneLayout", "Couldn't fetch mRecreateDisplayList field; dimming will be slow.", e2);
            }
        }

        public void a(SlidingPaneLayout slidingPaneLayout, View view) {
            if (this.a == null || this.b == null) {
                view.invalidate();
                return;
            }
            try {
                this.b.setBoolean(view, true);
                this.a.invoke(view, null);
            } catch (Exception e) {
                Log.e("SlidingPaneLayout", "Error refreshing display list state", e);
            }
            super.a(slidingPaneLayout, view);
        }
    }

    @RequiresApi(17)
    static class SlidingPanelLayoutImplJBMR1 extends SlidingPanelLayoutImplBase {
        SlidingPanelLayoutImplJBMR1() {
        }

        public void a(SlidingPaneLayout slidingPaneLayout, View view) {
            ViewCompat.setLayerPaint(view, ((LayoutParams) view.getLayoutParams()).c);
        }
    }

    static {
        if (VERSION.SDK_INT >= 17) {
            h = new SlidingPanelLayoutImplJBMR1();
        } else if (VERSION.SDK_INT >= 16) {
            h = new SlidingPanelLayoutImplJB();
        } else {
            h = new SlidingPanelLayoutImplBase();
        }
    }

    public SlidingPaneLayout(@NonNull Context context) {
        this(context, null);
    }

    public SlidingPaneLayout(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public SlidingPaneLayout(@NonNull Context context, @Nullable AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.i = -858993460;
        this.t = true;
        this.u = new Rect();
        this.g = new ArrayList<>();
        float f2 = context.getResources().getDisplayMetrics().density;
        this.m = (int) ((32.0f * f2) + 0.5f);
        setWillNotDraw(false);
        ViewCompat.setAccessibilityDelegate(this, new AccessibilityDelegate());
        ViewCompat.setImportantForAccessibility(this, 1);
        this.e = ViewDragHelper.create(this, 0.5f, new DragHelperCallback());
        this.e.setMinVelocity(f2 * 400.0f);
    }

    public void setParallaxDistance(int i2) {
        this.p = i2;
        requestLayout();
    }

    public int getParallaxDistance() {
        return this.p;
    }

    public void setSliderFadeColor(@ColorInt int i2) {
        this.i = i2;
    }

    @ColorInt
    public int getSliderFadeColor() {
        return this.i;
    }

    public void setCoveredFadeColor(@ColorInt int i2) {
        this.j = i2;
    }

    @ColorInt
    public int getCoveredFadeColor() {
        return this.j;
    }

    public void setPanelSlideListener(@Nullable PanelSlideListener panelSlideListener) {
        this.s = panelSlideListener;
    }

    /* access modifiers changed from: 0000 */
    public void a(View view) {
        if (this.s != null) {
            this.s.onPanelSlide(view, this.b);
        }
    }

    /* access modifiers changed from: 0000 */
    public void b(View view) {
        if (this.s != null) {
            this.s.onPanelOpened(view);
        }
        sendAccessibilityEvent(32);
    }

    /* access modifiers changed from: 0000 */
    public void c(View view) {
        if (this.s != null) {
            this.s.onPanelClosed(view);
        }
        sendAccessibilityEvent(32);
    }

    /* access modifiers changed from: 0000 */
    public void d(View view) {
        int i2;
        int i3;
        int i4;
        int i5;
        boolean z;
        int i6;
        View view2 = view;
        boolean b2 = b();
        int width = b2 ? getWidth() - getPaddingRight() : getPaddingLeft();
        int paddingLeft = b2 ? getPaddingLeft() : getWidth() - getPaddingRight();
        int paddingTop = getPaddingTop();
        int height = getHeight() - getPaddingBottom();
        if (view2 == null || !g(view)) {
            i5 = 0;
            i4 = 0;
            i3 = 0;
            i2 = 0;
        } else {
            i5 = view.getLeft();
            i4 = view.getRight();
            i3 = view.getTop();
            i2 = view.getBottom();
        }
        int childCount = getChildCount();
        int i7 = 0;
        while (i7 < childCount) {
            View childAt = getChildAt(i7);
            if (childAt != view2) {
                if (childAt.getVisibility() == 8) {
                    z = b2;
                } else {
                    int max = Math.max(b2 ? paddingLeft : width, childAt.getLeft());
                    int max2 = Math.max(paddingTop, childAt.getTop());
                    if (b2) {
                        z = b2;
                        i6 = width;
                    } else {
                        z = b2;
                        i6 = paddingLeft;
                    }
                    childAt.setVisibility((max < i5 || max2 < i3 || Math.min(i6, childAt.getRight()) > i4 || Math.min(height, childAt.getBottom()) > i2) ? 0 : 4);
                }
                i7++;
                b2 = z;
                view2 = view;
            } else {
                return;
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void a() {
        int childCount = getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            View childAt = getChildAt(i2);
            if (childAt.getVisibility() == 4) {
                childAt.setVisibility(0);
            }
        }
    }

    private static boolean g(View view) {
        boolean z = true;
        if (view.isOpaque()) {
            return true;
        }
        if (VERSION.SDK_INT >= 18) {
            return false;
        }
        Drawable background = view.getBackground();
        if (background == null) {
            return false;
        }
        if (background.getOpacity() != -1) {
            z = false;
        }
        return z;
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.t = true;
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.t = true;
        int size = this.g.size();
        for (int i2 = 0; i2 < size; i2++) {
            ((DisableLayerRunnable) this.g.get(i2)).run();
        }
        this.g.clear();
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Removed duplicated region for block: B:114:0x01f7  */
    /* JADX WARNING: Removed duplicated region for block: B:117:0x020d  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onMeasure(int r21, int r22) {
        /*
            r20 = this;
            r0 = r20
            int r1 = android.view.View.MeasureSpec.getMode(r21)
            int r2 = android.view.View.MeasureSpec.getSize(r21)
            int r3 = android.view.View.MeasureSpec.getMode(r22)
            int r4 = android.view.View.MeasureSpec.getSize(r22)
            r5 = 300(0x12c, float:4.2E-43)
            r6 = -2147483648(0xffffffff80000000, float:-0.0)
            r7 = 1073741824(0x40000000, float:2.0)
            if (r1 == r7) goto L_0x0030
            boolean r8 = r20.isInEditMode()
            if (r8 == 0) goto L_0x0028
            if (r1 != r6) goto L_0x0023
            goto L_0x0047
        L_0x0023:
            if (r1 != 0) goto L_0x0047
            r2 = 300(0x12c, float:4.2E-43)
            goto L_0x0047
        L_0x0028:
            java.lang.IllegalStateException r1 = new java.lang.IllegalStateException
            java.lang.String r2 = "Width must have an exact value or MATCH_PARENT"
            r1.<init>(r2)
            throw r1
        L_0x0030:
            if (r3 != 0) goto L_0x0047
            boolean r1 = r20.isInEditMode()
            if (r1 == 0) goto L_0x003f
            if (r3 != 0) goto L_0x0047
            r3 = -2147483648(0xffffffff80000000, float:-0.0)
            r4 = 300(0x12c, float:4.2E-43)
            goto L_0x0047
        L_0x003f:
            java.lang.IllegalStateException r1 = new java.lang.IllegalStateException
            java.lang.String r2 = "Height must not be UNSPECIFIED"
            r1.<init>(r2)
            throw r1
        L_0x0047:
            r1 = 0
            if (r3 == r6) goto L_0x005b
            if (r3 == r7) goto L_0x004f
            r4 = 0
            r5 = 0
            goto L_0x0067
        L_0x004f:
            int r5 = r20.getPaddingTop()
            int r4 = r4 - r5
            int r5 = r20.getPaddingBottom()
            int r4 = r4 - r5
            r5 = r4
            goto L_0x0067
        L_0x005b:
            int r5 = r20.getPaddingTop()
            int r4 = r4 - r5
            int r5 = r20.getPaddingBottom()
            int r4 = r4 - r5
            r5 = r4
            r4 = 0
        L_0x0067:
            int r8 = r20.getPaddingLeft()
            int r8 = r2 - r8
            int r9 = r20.getPaddingRight()
            int r8 = r8 - r9
            int r9 = r20.getChildCount()
            r10 = 2
            if (r9 <= r10) goto L_0x0080
            java.lang.String r10 = "SlidingPaneLayout"
            java.lang.String r11 = "onMeasure: More than two child views are not supported."
            android.util.Log.e(r10, r11)
        L_0x0080:
            r10 = 0
            r0.a = r10
            r13 = r4
            r14 = r8
            r4 = 0
            r11 = 0
            r12 = 0
        L_0x0088:
            r15 = 8
            r16 = 1
            if (r4 >= r9) goto L_0x012d
            android.view.View r6 = r0.getChildAt(r4)
            android.view.ViewGroup$LayoutParams r18 = r6.getLayoutParams()
            r7 = r18
            android.support.v4.widget.SlidingPaneLayout$LayoutParams r7 = (android.support.v4.widget.SlidingPaneLayout.LayoutParams) r7
            int r10 = r6.getVisibility()
            if (r10 != r15) goto L_0x00a4
            r7.b = r1
            goto L_0x0124
        L_0x00a4:
            float r10 = r7.weight
            r15 = 0
            int r10 = (r10 > r15 ? 1 : (r10 == r15 ? 0 : -1))
            if (r10 <= 0) goto L_0x00b4
            float r10 = r7.weight
            float r12 = r12 + r10
            int r10 = r7.width
            if (r10 != 0) goto L_0x00b4
            goto L_0x0124
        L_0x00b4:
            int r10 = r7.leftMargin
            int r15 = r7.rightMargin
            int r10 = r10 + r15
            int r15 = r7.width
            r1 = -2
            if (r15 != r1) goto L_0x00c9
            int r1 = r8 - r10
            r10 = -2147483648(0xffffffff80000000, float:-0.0)
            int r1 = android.view.View.MeasureSpec.makeMeasureSpec(r1, r10)
            r10 = 1073741824(0x40000000, float:2.0)
            goto L_0x00df
        L_0x00c9:
            int r1 = r7.width
            r15 = -1
            if (r1 != r15) goto L_0x00d7
            int r1 = r8 - r10
            r10 = 1073741824(0x40000000, float:2.0)
            int r1 = android.view.View.MeasureSpec.makeMeasureSpec(r1, r10)
            goto L_0x00df
        L_0x00d7:
            r10 = 1073741824(0x40000000, float:2.0)
            int r1 = r7.width
            int r1 = android.view.View.MeasureSpec.makeMeasureSpec(r1, r10)
        L_0x00df:
            int r15 = r7.height
            r10 = -2
            if (r15 != r10) goto L_0x00eb
            r10 = -2147483648(0xffffffff80000000, float:-0.0)
            int r15 = android.view.View.MeasureSpec.makeMeasureSpec(r5, r10)
            goto L_0x00ff
        L_0x00eb:
            int r10 = r7.height
            r15 = -1
            if (r10 != r15) goto L_0x00f7
            r10 = 1073741824(0x40000000, float:2.0)
            int r15 = android.view.View.MeasureSpec.makeMeasureSpec(r5, r10)
            goto L_0x00ff
        L_0x00f7:
            r10 = 1073741824(0x40000000, float:2.0)
            int r15 = r7.height
            int r15 = android.view.View.MeasureSpec.makeMeasureSpec(r15, r10)
        L_0x00ff:
            r6.measure(r1, r15)
            int r1 = r6.getMeasuredWidth()
            int r10 = r6.getMeasuredHeight()
            r15 = -2147483648(0xffffffff80000000, float:-0.0)
            if (r3 != r15) goto L_0x0114
            if (r10 <= r13) goto L_0x0114
            int r13 = java.lang.Math.min(r10, r5)
        L_0x0114:
            int r14 = r14 - r1
            if (r14 >= 0) goto L_0x0119
            r1 = 1
            goto L_0x011a
        L_0x0119:
            r1 = 0
        L_0x011a:
            r7.a = r1
            r1 = r1 | r11
            boolean r7 = r7.a
            if (r7 == 0) goto L_0x0123
            r0.a = r6
        L_0x0123:
            r11 = r1
        L_0x0124:
            int r4 = r4 + 1
            r1 = 0
            r6 = -2147483648(0xffffffff80000000, float:-0.0)
            r7 = 1073741824(0x40000000, float:2.0)
            goto L_0x0088
        L_0x012d:
            if (r11 != 0) goto L_0x0134
            r1 = 0
            int r3 = (r12 > r1 ? 1 : (r12 == r1 ? 0 : -1))
            if (r3 <= 0) goto L_0x022d
        L_0x0134:
            int r1 = r0.m
            int r1 = r8 - r1
            r3 = 0
        L_0x0139:
            if (r3 >= r9) goto L_0x022d
            android.view.View r4 = r0.getChildAt(r3)
            int r6 = r4.getVisibility()
            if (r6 != r15) goto L_0x014c
        L_0x0145:
            r19 = r1
        L_0x0147:
            r1 = 0
            r6 = 1073741824(0x40000000, float:2.0)
            goto L_0x0225
        L_0x014c:
            android.view.ViewGroup$LayoutParams r6 = r4.getLayoutParams()
            android.support.v4.widget.SlidingPaneLayout$LayoutParams r6 = (android.support.v4.widget.SlidingPaneLayout.LayoutParams) r6
            int r7 = r4.getVisibility()
            if (r7 != r15) goto L_0x0159
            goto L_0x0145
        L_0x0159:
            int r7 = r6.width
            if (r7 != 0) goto L_0x0166
            float r7 = r6.weight
            r10 = 0
            int r7 = (r7 > r10 ? 1 : (r7 == r10 ? 0 : -1))
            if (r7 <= 0) goto L_0x0166
            r7 = 1
            goto L_0x0167
        L_0x0166:
            r7 = 0
        L_0x0167:
            if (r7 == 0) goto L_0x016b
            r10 = 0
            goto L_0x016f
        L_0x016b:
            int r10 = r4.getMeasuredWidth()
        L_0x016f:
            if (r11 == 0) goto L_0x01b9
            android.view.View r15 = r0.a
            if (r4 == r15) goto L_0x01b9
            int r15 = r6.width
            if (r15 >= 0) goto L_0x0145
            if (r10 > r1) goto L_0x0182
            float r10 = r6.weight
            r15 = 0
            int r10 = (r10 > r15 ? 1 : (r10 == r15 ? 0 : -1))
            if (r10 <= 0) goto L_0x0145
        L_0x0182:
            if (r7 == 0) goto L_0x01a7
            int r7 = r6.height
            r10 = -2
            if (r7 != r10) goto L_0x0192
            r7 = -2147483648(0xffffffff80000000, float:-0.0)
            int r6 = android.view.View.MeasureSpec.makeMeasureSpec(r5, r7)
            r7 = 1073741824(0x40000000, float:2.0)
            goto L_0x01b1
        L_0x0192:
            int r7 = r6.height
            r10 = -1
            if (r7 != r10) goto L_0x019e
            r7 = 1073741824(0x40000000, float:2.0)
            int r6 = android.view.View.MeasureSpec.makeMeasureSpec(r5, r7)
            goto L_0x01b1
        L_0x019e:
            r7 = 1073741824(0x40000000, float:2.0)
            int r6 = r6.height
            int r6 = android.view.View.MeasureSpec.makeMeasureSpec(r6, r7)
            goto L_0x01b1
        L_0x01a7:
            r7 = 1073741824(0x40000000, float:2.0)
            int r6 = r4.getMeasuredHeight()
            int r6 = android.view.View.MeasureSpec.makeMeasureSpec(r6, r7)
        L_0x01b1:
            int r10 = android.view.View.MeasureSpec.makeMeasureSpec(r1, r7)
            r4.measure(r10, r6)
            goto L_0x0145
        L_0x01b9:
            float r7 = r6.weight
            r15 = 0
            int r7 = (r7 > r15 ? 1 : (r7 == r15 ? 0 : -1))
            if (r7 <= 0) goto L_0x0145
            int r7 = r6.width
            if (r7 != 0) goto L_0x01e9
            int r7 = r6.height
            r15 = -2
            if (r7 != r15) goto L_0x01d4
            r7 = -2147483648(0xffffffff80000000, float:-0.0)
            int r17 = android.view.View.MeasureSpec.makeMeasureSpec(r5, r7)
            r15 = r17
            r7 = 1073741824(0x40000000, float:2.0)
            goto L_0x01f5
        L_0x01d4:
            int r7 = r6.height
            r15 = -1
            if (r7 != r15) goto L_0x01e0
            r7 = 1073741824(0x40000000, float:2.0)
            int r17 = android.view.View.MeasureSpec.makeMeasureSpec(r5, r7)
            goto L_0x01f3
        L_0x01e0:
            r7 = 1073741824(0x40000000, float:2.0)
            int r15 = r6.height
            int r17 = android.view.View.MeasureSpec.makeMeasureSpec(r15, r7)
            goto L_0x01f3
        L_0x01e9:
            r7 = 1073741824(0x40000000, float:2.0)
            int r15 = r4.getMeasuredHeight()
            int r17 = android.view.View.MeasureSpec.makeMeasureSpec(r15, r7)
        L_0x01f3:
            r15 = r17
        L_0x01f5:
            if (r11 == 0) goto L_0x020d
            int r7 = r6.leftMargin
            int r6 = r6.rightMargin
            int r7 = r7 + r6
            int r6 = r8 - r7
            r19 = r1
            r7 = 1073741824(0x40000000, float:2.0)
            int r1 = android.view.View.MeasureSpec.makeMeasureSpec(r6, r7)
            if (r10 == r6) goto L_0x0147
            r4.measure(r1, r15)
            goto L_0x0147
        L_0x020d:
            r19 = r1
            r1 = 0
            int r7 = java.lang.Math.max(r1, r14)
            float r6 = r6.weight
            float r7 = (float) r7
            float r6 = r6 * r7
            float r6 = r6 / r12
            int r6 = (int) r6
            int r10 = r10 + r6
            r6 = 1073741824(0x40000000, float:2.0)
            int r7 = android.view.View.MeasureSpec.makeMeasureSpec(r10, r6)
            r4.measure(r7, r15)
        L_0x0225:
            int r3 = r3 + 1
            r1 = r19
            r15 = 8
            goto L_0x0139
        L_0x022d:
            int r1 = r20.getPaddingTop()
            int r13 = r13 + r1
            int r1 = r20.getPaddingBottom()
            int r13 = r13 + r1
            r0.setMeasuredDimension(r2, r13)
            r0.n = r11
            android.support.v4.widget.ViewDragHelper r1 = r0.e
            int r1 = r1.getViewDragState()
            if (r1 == 0) goto L_0x024b
            if (r11 != 0) goto L_0x024b
            android.support.v4.widget.ViewDragHelper r1 = r0.e
            r1.abort()
        L_0x024b:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.widget.SlidingPaneLayout.onMeasure(int, int):void");
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Removed duplicated region for block: B:44:0x00c2  */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x00c8  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onLayout(boolean r20, int r21, int r22, int r23, int r24) {
        /*
            r19 = this;
            r0 = r19
            boolean r1 = r19.b()
            r2 = 1
            if (r1 == 0) goto L_0x0010
            android.support.v4.widget.ViewDragHelper r3 = r0.e
            r4 = 2
            r3.setEdgeTrackingEnabled(r4)
            goto L_0x0015
        L_0x0010:
            android.support.v4.widget.ViewDragHelper r3 = r0.e
            r3.setEdgeTrackingEnabled(r2)
        L_0x0015:
            int r3 = r23 - r21
            if (r1 == 0) goto L_0x001e
            int r4 = r19.getPaddingRight()
            goto L_0x0022
        L_0x001e:
            int r4 = r19.getPaddingLeft()
        L_0x0022:
            if (r1 == 0) goto L_0x0029
            int r5 = r19.getPaddingLeft()
            goto L_0x002d
        L_0x0029:
            int r5 = r19.getPaddingRight()
        L_0x002d:
            int r6 = r19.getPaddingTop()
            int r7 = r19.getChildCount()
            boolean r8 = r0.t
            if (r8 == 0) goto L_0x0047
            boolean r8 = r0.n
            if (r8 == 0) goto L_0x0044
            boolean r8 = r0.f
            if (r8 == 0) goto L_0x0044
            r8 = 1065353216(0x3f800000, float:1.0)
            goto L_0x0045
        L_0x0044:
            r8 = 0
        L_0x0045:
            r0.b = r8
        L_0x0047:
            r11 = r4
            r12 = r11
            r4 = 0
        L_0x004a:
            if (r4 >= r7) goto L_0x00df
            android.view.View r13 = r0.getChildAt(r4)
            int r14 = r13.getVisibility()
            r15 = 8
            if (r14 != r15) goto L_0x005c
            r8 = 1065353216(0x3f800000, float:1.0)
            goto L_0x00da
        L_0x005c:
            android.view.ViewGroup$LayoutParams r14 = r13.getLayoutParams()
            android.support.v4.widget.SlidingPaneLayout$LayoutParams r14 = (android.support.v4.widget.SlidingPaneLayout.LayoutParams) r14
            int r15 = r13.getMeasuredWidth()
            boolean r2 = r14.a
            if (r2 == 0) goto L_0x00a5
            int r2 = r14.leftMargin
            int r8 = r14.rightMargin
            int r2 = r2 + r8
            int r8 = r3 - r5
            int r9 = r0.m
            int r9 = r8 - r9
            int r9 = java.lang.Math.min(r11, r9)
            int r9 = r9 - r12
            int r9 = r9 - r2
            r0.c = r9
            if (r1 == 0) goto L_0x0082
            int r2 = r14.rightMargin
            goto L_0x0084
        L_0x0082:
            int r2 = r14.leftMargin
        L_0x0084:
            int r16 = r12 + r2
            int r16 = r16 + r9
            int r17 = r15 / 2
            int r10 = r16 + r17
            if (r10 <= r8) goto L_0x0090
            r8 = 1
            goto L_0x0091
        L_0x0090:
            r8 = 0
        L_0x0091:
            r14.b = r8
            float r8 = (float) r9
            float r9 = r0.b
            float r8 = r8 * r9
            int r8 = (int) r8
            int r2 = r2 + r8
            int r2 = r2 + r12
            float r8 = (float) r8
            int r9 = r0.c
            float r9 = (float) r9
            float r8 = r8 / r9
            r0.b = r8
            r8 = 1065353216(0x3f800000, float:1.0)
            goto L_0x00bf
        L_0x00a5:
            boolean r2 = r0.n
            if (r2 == 0) goto L_0x00bc
            int r2 = r0.p
            if (r2 == 0) goto L_0x00bc
            float r2 = r0.b
            r8 = 1065353216(0x3f800000, float:1.0)
            float r10 = r8 - r2
            int r2 = r0.p
            float r2 = (float) r2
            float r10 = r10 * r2
            int r2 = (int) r10
            r9 = r2
            r2 = r11
            goto L_0x00c0
        L_0x00bc:
            r8 = 1065353216(0x3f800000, float:1.0)
            r2 = r11
        L_0x00bf:
            r9 = 0
        L_0x00c0:
            if (r1 == 0) goto L_0x00c8
            int r10 = r3 - r2
            int r10 = r10 + r9
            int r9 = r10 - r15
            goto L_0x00cc
        L_0x00c8:
            int r9 = r2 - r9
            int r10 = r9 + r15
        L_0x00cc:
            int r12 = r13.getMeasuredHeight()
            int r12 = r12 + r6
            r13.layout(r9, r6, r10, r12)
            int r9 = r13.getWidth()
            int r11 = r11 + r9
            r12 = r2
        L_0x00da:
            int r4 = r4 + 1
            r2 = 1
            goto L_0x004a
        L_0x00df:
            boolean r1 = r0.t
            if (r1 == 0) goto L_0x011b
            boolean r1 = r0.n
            if (r1 == 0) goto L_0x0106
            int r1 = r0.p
            if (r1 == 0) goto L_0x00f0
            float r1 = r0.b
            r0.a(r1)
        L_0x00f0:
            android.view.View r1 = r0.a
            android.view.ViewGroup$LayoutParams r1 = r1.getLayoutParams()
            android.support.v4.widget.SlidingPaneLayout$LayoutParams r1 = (android.support.v4.widget.SlidingPaneLayout.LayoutParams) r1
            boolean r1 = r1.b
            if (r1 == 0) goto L_0x0116
            android.view.View r1 = r0.a
            float r2 = r0.b
            int r3 = r0.i
            r0.a(r1, r2, r3)
            goto L_0x0116
        L_0x0106:
            r1 = 0
        L_0x0107:
            if (r1 >= r7) goto L_0x0116
            android.view.View r2 = r0.getChildAt(r1)
            int r3 = r0.i
            r4 = 0
            r0.a(r2, r4, r3)
            int r1 = r1 + 1
            goto L_0x0107
        L_0x0116:
            android.view.View r1 = r0.a
            r0.d(r1)
        L_0x011b:
            r1 = 0
            r0.t = r1
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.widget.SlidingPaneLayout.onLayout(boolean, int, int, int, int):void");
    }

    /* access modifiers changed from: protected */
    public void onSizeChanged(int i2, int i3, int i4, int i5) {
        super.onSizeChanged(i2, i3, i4, i5);
        if (i2 != i4) {
            this.t = true;
        }
    }

    public void requestChildFocus(View view, View view2) {
        super.requestChildFocus(view, view2);
        if (!isInTouchMode() && !this.n) {
            this.f = view == this.a;
        }
    }

    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        boolean z;
        int actionMasked = motionEvent.getActionMasked();
        boolean z2 = true;
        if (!this.n && actionMasked == 0 && getChildCount() > 1) {
            View childAt = getChildAt(1);
            if (childAt != null) {
                this.f = !this.e.isViewUnder(childAt, (int) motionEvent.getX(), (int) motionEvent.getY());
            }
        }
        if (!this.n || (this.d && actionMasked != 0)) {
            this.e.cancel();
            return super.onInterceptTouchEvent(motionEvent);
        } else if (actionMasked == 3 || actionMasked == 1) {
            this.e.cancel();
            return false;
        } else {
            if (actionMasked == 0) {
                this.d = false;
                float x = motionEvent.getX();
                float y = motionEvent.getY();
                this.q = x;
                this.r = y;
                if (this.e.isViewUnder(this.a, (int) x, (int) y) && f(this.a)) {
                    z = true;
                    if (!this.e.shouldInterceptTouchEvent(motionEvent) && !z) {
                        z2 = false;
                    }
                    return z2;
                }
            } else if (actionMasked == 2) {
                float x2 = motionEvent.getX();
                float y2 = motionEvent.getY();
                float abs = Math.abs(x2 - this.q);
                float abs2 = Math.abs(y2 - this.r);
                if (abs > ((float) this.e.getTouchSlop()) && abs2 > abs) {
                    this.e.cancel();
                    this.d = true;
                    return false;
                }
            }
            z = false;
            z2 = false;
            return z2;
        }
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (!this.n) {
            return super.onTouchEvent(motionEvent);
        }
        this.e.processTouchEvent(motionEvent);
        switch (motionEvent.getActionMasked()) {
            case 0:
                float x = motionEvent.getX();
                float y = motionEvent.getY();
                this.q = x;
                this.r = y;
                break;
            case 1:
                if (f(this.a)) {
                    float x2 = motionEvent.getX();
                    float y2 = motionEvent.getY();
                    float f2 = x2 - this.q;
                    float f3 = y2 - this.r;
                    int touchSlop = this.e.getTouchSlop();
                    if ((f2 * f2) + (f3 * f3) < ((float) (touchSlop * touchSlop)) && this.e.isViewUnder(this.a, (int) x2, (int) y2)) {
                        a(this.a, 0);
                        break;
                    }
                }
                break;
        }
        return true;
    }

    private boolean a(View view, int i2) {
        if (!this.t && !a((float) BitmapDescriptorFactory.HUE_RED, i2)) {
            return false;
        }
        this.f = false;
        return true;
    }

    private boolean b(View view, int i2) {
        if (!this.t && !a(1.0f, i2)) {
            return false;
        }
        this.f = true;
        return true;
    }

    @Deprecated
    public void smoothSlideOpen() {
        openPane();
    }

    public boolean openPane() {
        return b(this.a, 0);
    }

    @Deprecated
    public void smoothSlideClosed() {
        closePane();
    }

    public boolean closePane() {
        return a(this.a, 0);
    }

    public boolean isOpen() {
        return !this.n || this.b == 1.0f;
    }

    @Deprecated
    public boolean canSlide() {
        return this.n;
    }

    public boolean isSlideable() {
        return this.n;
    }

    /* access modifiers changed from: 0000 */
    public void a(int i2) {
        if (this.a == null) {
            this.b = BitmapDescriptorFactory.HUE_RED;
            return;
        }
        boolean b2 = b();
        LayoutParams layoutParams = (LayoutParams) this.a.getLayoutParams();
        int width = this.a.getWidth();
        if (b2) {
            i2 = (getWidth() - i2) - width;
        }
        this.b = ((float) (i2 - ((b2 ? getPaddingRight() : getPaddingLeft()) + (b2 ? layoutParams.rightMargin : layoutParams.leftMargin)))) / ((float) this.c);
        if (this.p != 0) {
            a(this.b);
        }
        if (layoutParams.b) {
            a(this.a, this.b, this.i);
        }
        a(this.a);
    }

    private void a(View view, float f2, int i2) {
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
        if (f2 > BitmapDescriptorFactory.HUE_RED && i2 != 0) {
            int i3 = (((int) (((float) ((-16777216 & i2) >>> 24)) * f2)) << 24) | (i2 & ViewCompat.MEASURED_SIZE_MASK);
            if (layoutParams.c == null) {
                layoutParams.c = new Paint();
            }
            layoutParams.c.setColorFilter(new PorterDuffColorFilter(i3, Mode.SRC_OVER));
            if (view.getLayerType() != 2) {
                view.setLayerType(2, layoutParams.c);
            }
            e(view);
        } else if (view.getLayerType() != 0) {
            if (layoutParams.c != null) {
                layoutParams.c.setColorFilter(null);
            }
            DisableLayerRunnable disableLayerRunnable = new DisableLayerRunnable(view);
            this.g.add(disableLayerRunnable);
            ViewCompat.postOnAnimation(this, disableLayerRunnable);
        }
    }

    /* access modifiers changed from: protected */
    public boolean drawChild(Canvas canvas, View view, long j2) {
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
        int save = canvas.save();
        if (this.n && !layoutParams.a && this.a != null) {
            canvas.getClipBounds(this.u);
            if (b()) {
                this.u.left = Math.max(this.u.left, this.a.getRight());
            } else {
                this.u.right = Math.min(this.u.right, this.a.getLeft());
            }
            canvas.clipRect(this.u);
        }
        boolean drawChild = super.drawChild(canvas, view, j2);
        canvas.restoreToCount(save);
        return drawChild;
    }

    /* access modifiers changed from: 0000 */
    public void e(View view) {
        h.a(this, view);
    }

    /* access modifiers changed from: 0000 */
    public boolean a(float f2, int i2) {
        int i3;
        if (!this.n) {
            return false;
        }
        LayoutParams layoutParams = (LayoutParams) this.a.getLayoutParams();
        if (b()) {
            i3 = (int) (((float) getWidth()) - ((((float) (getPaddingRight() + layoutParams.rightMargin)) + (f2 * ((float) this.c))) + ((float) this.a.getWidth())));
        } else {
            i3 = (int) (((float) (getPaddingLeft() + layoutParams.leftMargin)) + (f2 * ((float) this.c)));
        }
        if (!this.e.smoothSlideViewTo(this.a, i3, this.a.getTop())) {
            return false;
        }
        a();
        ViewCompat.postInvalidateOnAnimation(this);
        return true;
    }

    public void computeScroll() {
        if (this.e.continueSettling(true)) {
            if (!this.n) {
                this.e.abort();
                return;
            }
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    @Deprecated
    public void setShadowDrawable(Drawable drawable) {
        setShadowDrawableLeft(drawable);
    }

    public void setShadowDrawableLeft(@Nullable Drawable drawable) {
        this.k = drawable;
    }

    public void setShadowDrawableRight(@Nullable Drawable drawable) {
        this.l = drawable;
    }

    @Deprecated
    public void setShadowResource(@DrawableRes int i2) {
        setShadowDrawable(getResources().getDrawable(i2));
    }

    public void setShadowResourceLeft(int i2) {
        setShadowDrawableLeft(ContextCompat.getDrawable(getContext(), i2));
    }

    public void setShadowResourceRight(int i2) {
        setShadowDrawableRight(ContextCompat.getDrawable(getContext(), i2));
    }

    public void draw(Canvas canvas) {
        Drawable drawable;
        int i2;
        int i3;
        super.draw(canvas);
        if (b()) {
            drawable = this.l;
        } else {
            drawable = this.k;
        }
        View childAt = getChildCount() > 1 ? getChildAt(1) : null;
        if (childAt != null && drawable != null) {
            int top = childAt.getTop();
            int bottom = childAt.getBottom();
            int intrinsicWidth = drawable.getIntrinsicWidth();
            if (b()) {
                i3 = childAt.getRight();
                i2 = intrinsicWidth + i3;
            } else {
                int left = childAt.getLeft();
                int i4 = left - intrinsicWidth;
                i2 = left;
                i3 = i4;
            }
            drawable.setBounds(i3, top, i2, bottom);
            drawable.draw(canvas);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:10:0x0023  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void a(float r10) {
        /*
            r9 = this;
            boolean r0 = r9.b()
            android.view.View r1 = r9.a
            android.view.ViewGroup$LayoutParams r1 = r1.getLayoutParams()
            android.support.v4.widget.SlidingPaneLayout$LayoutParams r1 = (android.support.v4.widget.SlidingPaneLayout.LayoutParams) r1
            boolean r2 = r1.b
            r3 = 0
            if (r2 == 0) goto L_0x001c
            if (r0 == 0) goto L_0x0016
            int r1 = r1.rightMargin
            goto L_0x0018
        L_0x0016:
            int r1 = r1.leftMargin
        L_0x0018:
            if (r1 > 0) goto L_0x001c
            r1 = 1
            goto L_0x001d
        L_0x001c:
            r1 = 0
        L_0x001d:
            int r2 = r9.getChildCount()
        L_0x0021:
            if (r3 >= r2) goto L_0x005d
            android.view.View r4 = r9.getChildAt(r3)
            android.view.View r5 = r9.a
            if (r4 != r5) goto L_0x002c
            goto L_0x005a
        L_0x002c:
            float r5 = r9.o
            r6 = 1065353216(0x3f800000, float:1.0)
            float r5 = r6 - r5
            int r7 = r9.p
            float r7 = (float) r7
            float r5 = r5 * r7
            int r5 = (int) r5
            r9.o = r10
            float r7 = r6 - r10
            int r8 = r9.p
            float r8 = (float) r8
            float r7 = r7 * r8
            int r7 = (int) r7
            int r5 = r5 - r7
            if (r0 == 0) goto L_0x0046
            int r5 = -r5
        L_0x0046:
            r4.offsetLeftAndRight(r5)
            if (r1 == 0) goto L_0x005a
            if (r0 == 0) goto L_0x0051
            float r5 = r9.o
            float r5 = r5 - r6
            goto L_0x0055
        L_0x0051:
            float r5 = r9.o
            float r5 = r6 - r5
        L_0x0055:
            int r6 = r9.j
            r9.a(r4, r5, r6)
        L_0x005a:
            int r3 = r3 + 1
            goto L_0x0021
        L_0x005d:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.widget.SlidingPaneLayout.a(float):void");
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0065, code lost:
        if (r0.canScrollHorizontally(b() ? r16 : -r16) != false) goto L_0x0069;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean canScroll(android.view.View r14, boolean r15, int r16, int r17, int r18) {
        /*
            r13 = this;
            r0 = r14
            boolean r1 = r0 instanceof android.view.ViewGroup
            r2 = 1
            if (r1 == 0) goto L_0x0053
            r1 = r0
            android.view.ViewGroup r1 = (android.view.ViewGroup) r1
            int r3 = r0.getScrollX()
            int r4 = r0.getScrollY()
            int r5 = r1.getChildCount()
            int r5 = r5 - r2
        L_0x0016:
            if (r5 < 0) goto L_0x0053
            android.view.View r7 = r1.getChildAt(r5)
            int r6 = r17 + r3
            int r8 = r7.getLeft()
            if (r6 < r8) goto L_0x0050
            int r8 = r7.getRight()
            if (r6 >= r8) goto L_0x0050
            int r8 = r18 + r4
            int r9 = r7.getTop()
            if (r8 < r9) goto L_0x0050
            int r9 = r7.getBottom()
            if (r8 >= r9) goto L_0x0050
            r9 = 1
            int r10 = r7.getLeft()
            int r10 = r6 - r10
            int r6 = r7.getTop()
            int r11 = r8 - r6
            r6 = r13
            r8 = r9
            r9 = r16
            boolean r6 = r6.canScroll(r7, r8, r9, r10, r11)
            if (r6 == 0) goto L_0x0050
            return r2
        L_0x0050:
            int r5 = r5 + -1
            goto L_0x0016
        L_0x0053:
            if (r15 == 0) goto L_0x0068
            boolean r1 = r13.b()
            if (r1 == 0) goto L_0x005e
            r1 = r16
            goto L_0x0061
        L_0x005e:
            r1 = r16
            int r1 = -r1
        L_0x0061:
            boolean r0 = r0.canScrollHorizontally(r1)
            if (r0 == 0) goto L_0x0068
            goto L_0x0069
        L_0x0068:
            r2 = 0
        L_0x0069:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.widget.SlidingPaneLayout.canScroll(android.view.View, boolean, int, int, int):boolean");
    }

    /* access modifiers changed from: 0000 */
    public boolean f(View view) {
        boolean z = false;
        if (view == null) {
            return false;
        }
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
        if (this.n && layoutParams.b && this.b > BitmapDescriptorFactory.HUE_RED) {
            z = true;
        }
        return z;
    }

    /* access modifiers changed from: protected */
    public android.view.ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams();
    }

    /* access modifiers changed from: protected */
    public android.view.ViewGroup.LayoutParams generateLayoutParams(android.view.ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof MarginLayoutParams ? new LayoutParams((MarginLayoutParams) layoutParams) : new LayoutParams(layoutParams);
    }

    /* access modifiers changed from: protected */
    public boolean checkLayoutParams(android.view.ViewGroup.LayoutParams layoutParams) {
        return (layoutParams instanceof LayoutParams) && super.checkLayoutParams(layoutParams);
    }

    public android.view.ViewGroup.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(getContext(), attributeSet);
    }

    /* access modifiers changed from: protected */
    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        savedState.a = isSlideable() ? isOpen() : this.f;
        return savedState;
    }

    /* access modifiers changed from: protected */
    public void onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable instanceof SavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.getSuperState());
        if (savedState.a) {
            openPane();
        } else {
            closePane();
        }
        this.f = savedState.a;
    }

    /* access modifiers changed from: 0000 */
    public boolean b() {
        return ViewCompat.getLayoutDirection(this) == 1;
    }
}
