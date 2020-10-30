package android.support.design.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.ClassLoaderCreator;
import android.os.Parcelable.Creator;
import android.support.annotation.NonNull;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.annotation.VisibleForTesting;
import android.support.design.R;
import android.support.design.widget.CoordinatorLayout.Behavior;
import android.support.v4.math.MathUtils;
import android.support.v4.view.AbsSavedState;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.support.v4.widget.ViewDragHelper.Callback;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewParent;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;

public class BottomSheetBehavior<V extends View> extends Behavior<V> {
    public static final int PEEK_HEIGHT_AUTO = -1;
    public static final int STATE_COLLAPSED = 4;
    public static final int STATE_DRAGGING = 1;
    public static final int STATE_EXPANDED = 3;
    public static final int STATE_HIDDEN = 5;
    public static final int STATE_SETTLING = 2;
    int a;
    int b;
    boolean c;
    int d = 4;
    ViewDragHelper e;
    int f;
    WeakReference<V> g;
    WeakReference<View> h;
    int i;
    boolean j;
    private float k;
    private int l;
    private boolean m;
    private int n;
    private boolean o;
    private boolean p;
    private int q;
    private boolean r;
    private BottomSheetCallback s;
    private VelocityTracker t;
    private int u;
    private final Callback v = new Callback() {
        public boolean tryCaptureView(View view, int i) {
            boolean z = true;
            if (BottomSheetBehavior.this.d == 1 || BottomSheetBehavior.this.j) {
                return false;
            }
            if (BottomSheetBehavior.this.d == 3 && BottomSheetBehavior.this.i == i) {
                View view2 = (View) BottomSheetBehavior.this.h.get();
                if (view2 != null && view2.canScrollVertically(-1)) {
                    return false;
                }
            }
            if (BottomSheetBehavior.this.g == null || BottomSheetBehavior.this.g.get() != view) {
                z = false;
            }
            return z;
        }

        public void onViewPositionChanged(View view, int i, int i2, int i3, int i4) {
            BottomSheetBehavior.this.b(i2);
        }

        public void onViewDragStateChanged(int i) {
            if (i == 1) {
                BottomSheetBehavior.this.a(1);
            }
        }

        /* JADX WARNING: Removed duplicated region for block: B:18:0x005a  */
        /* JADX WARNING: Removed duplicated region for block: B:19:0x006b  */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void onViewReleased(android.view.View r4, float r5, float r6) {
            /*
                r3 = this;
                r5 = 0
                int r0 = (r6 > r5 ? 1 : (r6 == r5 ? 0 : -1))
                r1 = 4
                r2 = 3
                if (r0 >= 0) goto L_0x000d
                android.support.design.widget.BottomSheetBehavior r5 = android.support.design.widget.BottomSheetBehavior.this
                int r5 = r5.a
            L_0x000b:
                r1 = 3
                goto L_0x004c
            L_0x000d:
                android.support.design.widget.BottomSheetBehavior r0 = android.support.design.widget.BottomSheetBehavior.this
                boolean r0 = r0.c
                if (r0 == 0) goto L_0x0021
                android.support.design.widget.BottomSheetBehavior r0 = android.support.design.widget.BottomSheetBehavior.this
                boolean r0 = r0.a(r4, r6)
                if (r0 == 0) goto L_0x0021
                android.support.design.widget.BottomSheetBehavior r5 = android.support.design.widget.BottomSheetBehavior.this
                int r5 = r5.f
                r1 = 5
                goto L_0x004c
            L_0x0021:
                int r5 = (r6 > r5 ? 1 : (r6 == r5 ? 0 : -1))
                if (r5 != 0) goto L_0x0048
                int r5 = r4.getTop()
                android.support.design.widget.BottomSheetBehavior r6 = android.support.design.widget.BottomSheetBehavior.this
                int r6 = r6.a
                int r6 = r5 - r6
                int r6 = java.lang.Math.abs(r6)
                android.support.design.widget.BottomSheetBehavior r0 = android.support.design.widget.BottomSheetBehavior.this
                int r0 = r0.b
                int r5 = r5 - r0
                int r5 = java.lang.Math.abs(r5)
                if (r6 >= r5) goto L_0x0043
                android.support.design.widget.BottomSheetBehavior r5 = android.support.design.widget.BottomSheetBehavior.this
                int r5 = r5.a
                goto L_0x000b
            L_0x0043:
                android.support.design.widget.BottomSheetBehavior r5 = android.support.design.widget.BottomSheetBehavior.this
                int r5 = r5.b
                goto L_0x004c
            L_0x0048:
                android.support.design.widget.BottomSheetBehavior r5 = android.support.design.widget.BottomSheetBehavior.this
                int r5 = r5.b
            L_0x004c:
                android.support.design.widget.BottomSheetBehavior r6 = android.support.design.widget.BottomSheetBehavior.this
                android.support.v4.widget.ViewDragHelper r6 = r6.e
                int r0 = r4.getLeft()
                boolean r5 = r6.settleCapturedViewAt(r0, r5)
                if (r5 == 0) goto L_0x006b
                android.support.design.widget.BottomSheetBehavior r5 = android.support.design.widget.BottomSheetBehavior.this
                r6 = 2
                r5.a(r6)
                android.support.design.widget.BottomSheetBehavior$SettleRunnable r5 = new android.support.design.widget.BottomSheetBehavior$SettleRunnable
                android.support.design.widget.BottomSheetBehavior r6 = android.support.design.widget.BottomSheetBehavior.this
                r5.<init>(r4, r1)
                android.support.v4.view.ViewCompat.postOnAnimation(r4, r5)
                goto L_0x0070
            L_0x006b:
                android.support.design.widget.BottomSheetBehavior r4 = android.support.design.widget.BottomSheetBehavior.this
                r4.a(r1)
            L_0x0070:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: android.support.design.widget.BottomSheetBehavior.AnonymousClass2.onViewReleased(android.view.View, float, float):void");
        }

        public int clampViewPositionVertical(View view, int i, int i2) {
            return MathUtils.clamp(i, BottomSheetBehavior.this.a, BottomSheetBehavior.this.c ? BottomSheetBehavior.this.f : BottomSheetBehavior.this.b);
        }

        public int clampViewPositionHorizontal(View view, int i, int i2) {
            return view.getLeft();
        }

        public int getViewVerticalDragRange(View view) {
            if (BottomSheetBehavior.this.c) {
                return BottomSheetBehavior.this.f - BottomSheetBehavior.this.a;
            }
            return BottomSheetBehavior.this.b - BottomSheetBehavior.this.a;
        }
    };

    public static abstract class BottomSheetCallback {
        public abstract void onSlide(@NonNull View view, float f);

        public abstract void onStateChanged(@NonNull View view, int i);
    }

    public static class SavedState extends AbsSavedState {
        public static final Creator<SavedState> CREATOR = new ClassLoaderCreator<SavedState>() {
            /* renamed from: a */
            public SavedState createFromParcel(Parcel parcel, ClassLoader classLoader) {
                return new SavedState(parcel, classLoader);
            }

            /* renamed from: a */
            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel, (ClassLoader) null);
            }

            /* renamed from: a */
            public SavedState[] newArray(int i) {
                return new SavedState[i];
            }
        };
        final int a;

        public SavedState(Parcel parcel) {
            this(parcel, (ClassLoader) null);
        }

        public SavedState(Parcel parcel, ClassLoader classLoader) {
            super(parcel, classLoader);
            this.a = parcel.readInt();
        }

        public SavedState(Parcelable parcelable, int i) {
            super(parcelable);
            this.a = i;
        }

        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeInt(this.a);
        }
    }

    class SettleRunnable implements Runnable {
        private final View b;
        private final int c;

        SettleRunnable(View view, int i) {
            this.b = view;
            this.c = i;
        }

        public void run() {
            if (BottomSheetBehavior.this.e == null || !BottomSheetBehavior.this.e.continueSettling(true)) {
                BottomSheetBehavior.this.a(this.c);
            } else {
                ViewCompat.postOnAnimation(this.b, this);
            }
        }
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    @Retention(RetentionPolicy.SOURCE)
    public @interface State {
    }

    public BottomSheetBehavior() {
    }

    public BottomSheetBehavior(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.BottomSheetBehavior_Layout);
        TypedValue peekValue = obtainStyledAttributes.peekValue(R.styleable.BottomSheetBehavior_Layout_behavior_peekHeight);
        if (peekValue == null || peekValue.data != -1) {
            setPeekHeight(obtainStyledAttributes.getDimensionPixelSize(R.styleable.BottomSheetBehavior_Layout_behavior_peekHeight, -1));
        } else {
            setPeekHeight(peekValue.data);
        }
        setHideable(obtainStyledAttributes.getBoolean(R.styleable.BottomSheetBehavior_Layout_behavior_hideable, false));
        setSkipCollapsed(obtainStyledAttributes.getBoolean(R.styleable.BottomSheetBehavior_Layout_behavior_skipCollapsed, false));
        obtainStyledAttributes.recycle();
        this.k = (float) ViewConfiguration.get(context).getScaledMaximumFlingVelocity();
    }

    public Parcelable onSaveInstanceState(CoordinatorLayout coordinatorLayout, V v2) {
        return new SavedState(super.onSaveInstanceState(coordinatorLayout, v2), this.d);
    }

    public void onRestoreInstanceState(CoordinatorLayout coordinatorLayout, V v2, Parcelable parcelable) {
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(coordinatorLayout, v2, savedState.getSuperState());
        if (savedState.a == 1 || savedState.a == 2) {
            this.d = 4;
        } else {
            this.d = savedState.a;
        }
    }

    public boolean onLayoutChild(CoordinatorLayout coordinatorLayout, V v2, int i2) {
        int i3;
        if (ViewCompat.getFitsSystemWindows(coordinatorLayout) && !ViewCompat.getFitsSystemWindows(v2)) {
            ViewCompat.setFitsSystemWindows(v2, true);
        }
        int top = v2.getTop();
        coordinatorLayout.onLayoutChild(v2, i2);
        this.f = coordinatorLayout.getHeight();
        if (this.m) {
            if (this.n == 0) {
                this.n = coordinatorLayout.getResources().getDimensionPixelSize(R.dimen.design_bottom_sheet_peek_height_min);
            }
            i3 = Math.max(this.n, this.f - ((coordinatorLayout.getWidth() * 9) / 16));
        } else {
            i3 = this.l;
        }
        this.a = Math.max(0, this.f - v2.getHeight());
        this.b = Math.max(this.f - i3, this.a);
        if (this.d == 3) {
            ViewCompat.offsetTopAndBottom(v2, this.a);
        } else if (this.c && this.d == 5) {
            ViewCompat.offsetTopAndBottom(v2, this.f);
        } else if (this.d == 4) {
            ViewCompat.offsetTopAndBottom(v2, this.b);
        } else if (this.d == 1 || this.d == 2) {
            ViewCompat.offsetTopAndBottom(v2, top - v2.getTop());
        }
        if (this.e == null) {
            this.e = ViewDragHelper.create(coordinatorLayout, this.v);
        }
        this.g = new WeakReference<>(v2);
        this.h = new WeakReference<>(a((View) v2));
        return true;
    }

    public boolean onInterceptTouchEvent(CoordinatorLayout coordinatorLayout, V v2, MotionEvent motionEvent) {
        boolean z = false;
        if (!v2.isShown()) {
            this.p = true;
            return false;
        }
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 0) {
            a();
        }
        if (this.t == null) {
            this.t = VelocityTracker.obtain();
        }
        this.t.addMovement(motionEvent);
        if (actionMasked != 3) {
            switch (actionMasked) {
                case 0:
                    int x = (int) motionEvent.getX();
                    this.u = (int) motionEvent.getY();
                    View view = this.h != null ? (View) this.h.get() : null;
                    if (view != null && coordinatorLayout.isPointInChildBounds(view, x, this.u)) {
                        this.i = motionEvent.getPointerId(motionEvent.getActionIndex());
                        this.j = true;
                    }
                    this.p = this.i == -1 && !coordinatorLayout.isPointInChildBounds(v2, x, this.u);
                    break;
                case 1:
                    break;
            }
        }
        this.j = false;
        this.i = -1;
        if (this.p) {
            this.p = false;
            return false;
        }
        if (!this.p && this.e.shouldInterceptTouchEvent(motionEvent)) {
            return true;
        }
        View view2 = (View) this.h.get();
        if (actionMasked == 2 && view2 != null && !this.p && this.d != 1 && !coordinatorLayout.isPointInChildBounds(view2, (int) motionEvent.getX(), (int) motionEvent.getY()) && Math.abs(((float) this.u) - motionEvent.getY()) > ((float) this.e.getTouchSlop())) {
            z = true;
        }
        return z;
    }

    public boolean onTouchEvent(CoordinatorLayout coordinatorLayout, V v2, MotionEvent motionEvent) {
        if (!v2.isShown()) {
            return false;
        }
        int actionMasked = motionEvent.getActionMasked();
        if (this.d == 1 && actionMasked == 0) {
            return true;
        }
        if (this.e != null) {
            this.e.processTouchEvent(motionEvent);
        }
        if (actionMasked == 0) {
            a();
        }
        if (this.t == null) {
            this.t = VelocityTracker.obtain();
        }
        this.t.addMovement(motionEvent);
        if (actionMasked == 2 && !this.p && Math.abs(((float) this.u) - motionEvent.getY()) > ((float) this.e.getTouchSlop())) {
            this.e.captureChildView(v2, motionEvent.getPointerId(motionEvent.getActionIndex()));
        }
        return !this.p;
    }

    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, V v2, View view, View view2, int i2) {
        this.q = 0;
        this.r = false;
        if ((i2 & 2) != 0) {
            return true;
        }
        return false;
    }

    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, V v2, View view, int i2, int i3, int[] iArr) {
        if (view == ((View) this.h.get())) {
            int top = v2.getTop();
            int i4 = top - i3;
            if (i3 > 0) {
                if (i4 < this.a) {
                    iArr[1] = top - this.a;
                    ViewCompat.offsetTopAndBottom(v2, -iArr[1]);
                    a(3);
                } else {
                    iArr[1] = i3;
                    ViewCompat.offsetTopAndBottom(v2, -i3);
                    a(1);
                }
            } else if (i3 < 0 && !view.canScrollVertically(-1)) {
                if (i4 <= this.b || this.c) {
                    iArr[1] = i3;
                    ViewCompat.offsetTopAndBottom(v2, -i3);
                    a(1);
                } else {
                    iArr[1] = top - this.b;
                    ViewCompat.offsetTopAndBottom(v2, -iArr[1]);
                    a(4);
                }
            }
            b(v2.getTop());
            this.q = i3;
            this.r = true;
        }
    }

    public void onStopNestedScroll(CoordinatorLayout coordinatorLayout, V v2, View view) {
        int i2;
        int i3 = 3;
        if (v2.getTop() == this.a) {
            a(3);
        } else if (this.h != null && view == this.h.get() && this.r) {
            if (this.q > 0) {
                i2 = this.a;
            } else if (!this.c || !a((View) v2, b())) {
                if (this.q == 0) {
                    int top = v2.getTop();
                    if (Math.abs(top - this.a) < Math.abs(top - this.b)) {
                        i2 = this.a;
                    } else {
                        i2 = this.b;
                    }
                } else {
                    i2 = this.b;
                }
                i3 = 4;
            } else {
                i2 = this.f;
                i3 = 5;
            }
            if (this.e.smoothSlideViewTo(v2, v2.getLeft(), i2)) {
                a(2);
                ViewCompat.postOnAnimation(v2, new SettleRunnable(v2, i3));
            } else {
                a(i3);
            }
            this.r = false;
        }
    }

    public boolean onNestedPreFling(CoordinatorLayout coordinatorLayout, V v2, View view, float f2, float f3) {
        return view == this.h.get() && (this.d != 3 || super.onNestedPreFling(coordinatorLayout, v2, view, f2, f3));
    }

    /* JADX WARNING: Removed duplicated region for block: B:12:0x0026  */
    /* JADX WARNING: Removed duplicated region for block: B:20:? A[ADDED_TO_REGION, RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void setPeekHeight(int r4) {
        /*
            r3 = this;
            r0 = 1
            r1 = 0
            r2 = -1
            if (r4 != r2) goto L_0x000c
            boolean r4 = r3.m
            if (r4 != 0) goto L_0x0015
            r3.m = r0
            goto L_0x0024
        L_0x000c:
            boolean r2 = r3.m
            if (r2 != 0) goto L_0x0017
            int r2 = r3.l
            if (r2 == r4) goto L_0x0015
            goto L_0x0017
        L_0x0015:
            r0 = 0
            goto L_0x0024
        L_0x0017:
            r3.m = r1
            int r1 = java.lang.Math.max(r1, r4)
            r3.l = r1
            int r1 = r3.f
            int r1 = r1 - r4
            r3.b = r1
        L_0x0024:
            if (r0 == 0) goto L_0x003c
            int r4 = r3.d
            r0 = 4
            if (r4 != r0) goto L_0x003c
            java.lang.ref.WeakReference<V> r4 = r3.g
            if (r4 == 0) goto L_0x003c
            java.lang.ref.WeakReference<V> r4 = r3.g
            java.lang.Object r4 = r4.get()
            android.view.View r4 = (android.view.View) r4
            if (r4 == 0) goto L_0x003c
            r4.requestLayout()
        L_0x003c:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.design.widget.BottomSheetBehavior.setPeekHeight(int):void");
    }

    public final int getPeekHeight() {
        if (this.m) {
            return -1;
        }
        return this.l;
    }

    public void setHideable(boolean z) {
        this.c = z;
    }

    public boolean isHideable() {
        return this.c;
    }

    public void setSkipCollapsed(boolean z) {
        this.o = z;
    }

    public boolean getSkipCollapsed() {
        return this.o;
    }

    public void setBottomSheetCallback(BottomSheetCallback bottomSheetCallback) {
        this.s = bottomSheetCallback;
    }

    public final void setState(final int i2) {
        if (i2 != this.d) {
            if (this.g == null) {
                if (i2 == 4 || i2 == 3 || (this.c && i2 == 5)) {
                    this.d = i2;
                }
                return;
            }
            final View view = (View) this.g.get();
            if (view != null) {
                ViewParent parent = view.getParent();
                if (parent == null || !parent.isLayoutRequested() || !ViewCompat.isAttachedToWindow(view)) {
                    a(view, i2);
                } else {
                    view.post(new Runnable() {
                        public void run() {
                            BottomSheetBehavior.this.a(view, i2);
                        }
                    });
                }
            }
        }
    }

    public final int getState() {
        return this.d;
    }

    /* access modifiers changed from: 0000 */
    public void a(int i2) {
        if (this.d != i2) {
            this.d = i2;
            View view = (View) this.g.get();
            if (!(view == null || this.s == null)) {
                this.s.onStateChanged(view, i2);
            }
        }
    }

    private void a() {
        this.i = -1;
        if (this.t != null) {
            this.t.recycle();
            this.t = null;
        }
    }

    /* access modifiers changed from: 0000 */
    public boolean a(View view, float f2) {
        boolean z = true;
        if (this.o) {
            return true;
        }
        if (view.getTop() < this.b) {
            return false;
        }
        if (Math.abs((((float) view.getTop()) + (f2 * 0.1f)) - ((float) this.b)) / ((float) this.l) <= 0.5f) {
            z = false;
        }
        return z;
    }

    /* access modifiers changed from: 0000 */
    @VisibleForTesting
    public View a(View view) {
        if (ViewCompat.isNestedScrollingEnabled(view)) {
            return view;
        }
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            int childCount = viewGroup.getChildCount();
            for (int i2 = 0; i2 < childCount; i2++) {
                View a2 = a(viewGroup.getChildAt(i2));
                if (a2 != null) {
                    return a2;
                }
            }
        }
        return null;
    }

    private float b() {
        this.t.computeCurrentVelocity(1000, this.k);
        return this.t.getYVelocity(this.i);
    }

    /* access modifiers changed from: 0000 */
    public void a(View view, int i2) {
        int i3;
        if (i2 == 4) {
            i3 = this.b;
        } else if (i2 == 3) {
            i3 = this.a;
        } else if (!this.c || i2 != 5) {
            StringBuilder sb = new StringBuilder();
            sb.append("Illegal state argument: ");
            sb.append(i2);
            throw new IllegalArgumentException(sb.toString());
        } else {
            i3 = this.f;
        }
        if (this.e.smoothSlideViewTo(view, view.getLeft(), i3)) {
            a(2);
            ViewCompat.postOnAnimation(view, new SettleRunnable(view, i2));
            return;
        }
        a(i2);
    }

    /* access modifiers changed from: 0000 */
    public void b(int i2) {
        View view = (View) this.g.get();
        if (view != null && this.s != null) {
            if (i2 > this.b) {
                this.s.onSlide(view, ((float) (this.b - i2)) / ((float) (this.f - this.b)));
            } else {
                this.s.onSlide(view, ((float) (this.b - i2)) / ((float) (this.b - this.a)));
            }
        }
    }

    public static <V extends View> BottomSheetBehavior<V> from(V v2) {
        LayoutParams layoutParams = v2.getLayoutParams();
        if (!(layoutParams instanceof CoordinatorLayout.LayoutParams)) {
            throw new IllegalArgumentException("The view is not a child of CoordinatorLayout");
        }
        Behavior behavior = ((CoordinatorLayout.LayoutParams) layoutParams).getBehavior();
        if (behavior instanceof BottomSheetBehavior) {
            return (BottomSheetBehavior) behavior;
        }
        throw new IllegalArgumentException("The view is not associated with BottomSheetBehavior");
    }
}
