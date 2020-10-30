package android.support.v4.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.v4.view.AccessibilityDelegateCompat;
import android.support.v4.view.NestedScrollingChild2;
import android.support.v4.view.NestedScrollingChildHelper;
import android.support.v4.view.NestedScrollingParent;
import android.support.v4.view.NestedScrollingParentHelper;
import android.support.v4.view.ScrollingView;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.support.v4.view.accessibility.AccessibilityRecordCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.FocusFinder;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.View.BaseSavedState;
import android.view.View.MeasureSpec;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;
import android.view.animation.AnimationUtils;
import android.widget.EdgeEffect;
import android.widget.FrameLayout;
import android.widget.OverScroller;
import android.widget.ScrollView;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.util.ArrayList;

public class NestedScrollView extends FrameLayout implements NestedScrollingChild2, NestedScrollingParent, ScrollingView {
    private static final AccessibilityDelegate w = new AccessibilityDelegate();
    private static final int[] x = {16843130};
    private float A;
    private OnScrollChangeListener B;
    private long a;
    private final Rect b;
    private OverScroller c;
    private EdgeEffect d;
    private EdgeEffect e;
    private int f;
    private boolean g;
    private boolean h;
    private View i;
    private boolean j;
    private VelocityTracker k;
    private boolean l;
    private boolean m;
    private int n;
    private int o;
    private int p;
    private int q;
    private final int[] r;
    private final int[] s;
    private int t;
    private int u;
    private SavedState v;
    private final NestedScrollingParentHelper y;
    private final NestedScrollingChildHelper z;

    static class AccessibilityDelegate extends AccessibilityDelegateCompat {
        AccessibilityDelegate() {
        }

        public boolean performAccessibilityAction(View view, int i, Bundle bundle) {
            if (super.performAccessibilityAction(view, i, bundle)) {
                return true;
            }
            NestedScrollView nestedScrollView = (NestedScrollView) view;
            if (!nestedScrollView.isEnabled()) {
                return false;
            }
            if (i == 4096) {
                int min = Math.min(nestedScrollView.getScrollY() + ((nestedScrollView.getHeight() - nestedScrollView.getPaddingBottom()) - nestedScrollView.getPaddingTop()), nestedScrollView.getScrollRange());
                if (min == nestedScrollView.getScrollY()) {
                    return false;
                }
                nestedScrollView.smoothScrollTo(0, min);
                return true;
            } else if (i != 8192) {
                return false;
            } else {
                int max = Math.max(nestedScrollView.getScrollY() - ((nestedScrollView.getHeight() - nestedScrollView.getPaddingBottom()) - nestedScrollView.getPaddingTop()), 0);
                if (max == nestedScrollView.getScrollY()) {
                    return false;
                }
                nestedScrollView.smoothScrollTo(0, max);
                return true;
            }
        }

        public void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfoCompat);
            NestedScrollView nestedScrollView = (NestedScrollView) view;
            accessibilityNodeInfoCompat.setClassName(ScrollView.class.getName());
            if (nestedScrollView.isEnabled()) {
                int scrollRange = nestedScrollView.getScrollRange();
                if (scrollRange > 0) {
                    accessibilityNodeInfoCompat.setScrollable(true);
                    if (nestedScrollView.getScrollY() > 0) {
                        accessibilityNodeInfoCompat.addAction(8192);
                    }
                    if (nestedScrollView.getScrollY() < scrollRange) {
                        accessibilityNodeInfoCompat.addAction(4096);
                    }
                }
            }
        }

        public void onInitializeAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
            super.onInitializeAccessibilityEvent(view, accessibilityEvent);
            NestedScrollView nestedScrollView = (NestedScrollView) view;
            accessibilityEvent.setClassName(ScrollView.class.getName());
            accessibilityEvent.setScrollable(nestedScrollView.getScrollRange() > 0);
            accessibilityEvent.setScrollX(nestedScrollView.getScrollX());
            accessibilityEvent.setScrollY(nestedScrollView.getScrollY());
            AccessibilityRecordCompat.setMaxScrollX(accessibilityEvent, nestedScrollView.getScrollX());
            AccessibilityRecordCompat.setMaxScrollY(accessibilityEvent, nestedScrollView.getScrollRange());
        }
    }

    public interface OnScrollChangeListener {
        void onScrollChange(NestedScrollView nestedScrollView, int i, int i2, int i3, int i4);
    }

    static class SavedState extends BaseSavedState {
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

        SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        SavedState(Parcel parcel) {
            super(parcel);
            this.a = parcel.readInt();
        }

        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeInt(this.a);
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("HorizontalScrollView.SavedState{");
            sb.append(Integer.toHexString(System.identityHashCode(this)));
            sb.append(" scrollPosition=");
            sb.append(this.a);
            sb.append("}");
            return sb.toString();
        }
    }

    private static int b(int i2, int i3, int i4) {
        if (i3 >= i4 || i2 < 0) {
            return 0;
        }
        return i3 + i2 > i4 ? i4 - i3 : i2;
    }

    public boolean onStartNestedScroll(View view, View view2, int i2) {
        return (i2 & 2) != 0;
    }

    public boolean shouldDelayChildPressedState() {
        return true;
    }

    public NestedScrollView(@NonNull Context context) {
        this(context, null);
    }

    public NestedScrollView(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public NestedScrollView(@NonNull Context context, @Nullable AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.b = new Rect();
        this.g = true;
        this.h = false;
        this.i = null;
        this.j = false;
        this.m = true;
        this.q = -1;
        this.r = new int[2];
        this.s = new int[2];
        a();
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, x, i2, 0);
        setFillViewport(obtainStyledAttributes.getBoolean(0, false));
        obtainStyledAttributes.recycle();
        this.y = new NestedScrollingParentHelper(this);
        this.z = new NestedScrollingChildHelper(this);
        setNestedScrollingEnabled(true);
        ViewCompat.setAccessibilityDelegate(this, w);
    }

    public void setNestedScrollingEnabled(boolean z2) {
        this.z.setNestedScrollingEnabled(z2);
    }

    public boolean isNestedScrollingEnabled() {
        return this.z.isNestedScrollingEnabled();
    }

    public boolean startNestedScroll(int i2) {
        return this.z.startNestedScroll(i2);
    }

    public boolean startNestedScroll(int i2, int i3) {
        return this.z.startNestedScroll(i2, i3);
    }

    public void stopNestedScroll() {
        this.z.stopNestedScroll();
    }

    public void stopNestedScroll(int i2) {
        this.z.stopNestedScroll(i2);
    }

    public boolean hasNestedScrollingParent() {
        return this.z.hasNestedScrollingParent();
    }

    public boolean hasNestedScrollingParent(int i2) {
        return this.z.hasNestedScrollingParent(i2);
    }

    public boolean dispatchNestedScroll(int i2, int i3, int i4, int i5, int[] iArr) {
        return this.z.dispatchNestedScroll(i2, i3, i4, i5, iArr);
    }

    public boolean dispatchNestedScroll(int i2, int i3, int i4, int i5, int[] iArr, int i6) {
        return this.z.dispatchNestedScroll(i2, i3, i4, i5, iArr, i6);
    }

    public boolean dispatchNestedPreScroll(int i2, int i3, int[] iArr, int[] iArr2) {
        return this.z.dispatchNestedPreScroll(i2, i3, iArr, iArr2);
    }

    public boolean dispatchNestedPreScroll(int i2, int i3, int[] iArr, int[] iArr2, int i4) {
        return this.z.dispatchNestedPreScroll(i2, i3, iArr, iArr2, i4);
    }

    public boolean dispatchNestedFling(float f2, float f3, boolean z2) {
        return this.z.dispatchNestedFling(f2, f3, z2);
    }

    public boolean dispatchNestedPreFling(float f2, float f3) {
        return this.z.dispatchNestedPreFling(f2, f3);
    }

    public void onNestedScrollAccepted(View view, View view2, int i2) {
        this.y.onNestedScrollAccepted(view, view2, i2);
        startNestedScroll(2);
    }

    public void onStopNestedScroll(View view) {
        this.y.onStopNestedScroll(view);
        stopNestedScroll();
    }

    public void onNestedScroll(View view, int i2, int i3, int i4, int i5) {
        int scrollY = getScrollY();
        scrollBy(0, i5);
        int scrollY2 = getScrollY() - scrollY;
        dispatchNestedScroll(0, scrollY2, 0, i5 - scrollY2, null);
    }

    public void onNestedPreScroll(View view, int i2, int i3, int[] iArr) {
        dispatchNestedPreScroll(i2, i3, iArr, null);
    }

    public boolean onNestedFling(View view, float f2, float f3, boolean z2) {
        if (z2) {
            return false;
        }
        b((int) f3);
        return true;
    }

    public boolean onNestedPreFling(View view, float f2, float f3) {
        return dispatchNestedPreFling(f2, f3);
    }

    public int getNestedScrollAxes() {
        return this.y.getNestedScrollAxes();
    }

    /* access modifiers changed from: protected */
    public float getTopFadingEdgeStrength() {
        if (getChildCount() == 0) {
            return BitmapDescriptorFactory.HUE_RED;
        }
        int verticalFadingEdgeLength = getVerticalFadingEdgeLength();
        int scrollY = getScrollY();
        if (scrollY < verticalFadingEdgeLength) {
            return ((float) scrollY) / ((float) verticalFadingEdgeLength);
        }
        return 1.0f;
    }

    /* access modifiers changed from: protected */
    public float getBottomFadingEdgeStrength() {
        if (getChildCount() == 0) {
            return BitmapDescriptorFactory.HUE_RED;
        }
        int verticalFadingEdgeLength = getVerticalFadingEdgeLength();
        int bottom = (getChildAt(0).getBottom() - getScrollY()) - (getHeight() - getPaddingBottom());
        if (bottom < verticalFadingEdgeLength) {
            return ((float) bottom) / ((float) verticalFadingEdgeLength);
        }
        return 1.0f;
    }

    public int getMaxScrollAmount() {
        return (int) (((float) getHeight()) * 0.5f);
    }

    private void a() {
        this.c = new OverScroller(getContext());
        setFocusable(true);
        setDescendantFocusability(262144);
        setWillNotDraw(false);
        ViewConfiguration viewConfiguration = ViewConfiguration.get(getContext());
        this.n = viewConfiguration.getScaledTouchSlop();
        this.o = viewConfiguration.getScaledMinimumFlingVelocity();
        this.p = viewConfiguration.getScaledMaximumFlingVelocity();
    }

    public void addView(View view) {
        if (getChildCount() > 0) {
            throw new IllegalStateException("ScrollView can host only one direct child");
        }
        super.addView(view);
    }

    public void addView(View view, int i2) {
        if (getChildCount() > 0) {
            throw new IllegalStateException("ScrollView can host only one direct child");
        }
        super.addView(view, i2);
    }

    public void addView(View view, LayoutParams layoutParams) {
        if (getChildCount() > 0) {
            throw new IllegalStateException("ScrollView can host only one direct child");
        }
        super.addView(view, layoutParams);
    }

    public void addView(View view, int i2, LayoutParams layoutParams) {
        if (getChildCount() > 0) {
            throw new IllegalStateException("ScrollView can host only one direct child");
        }
        super.addView(view, i2, layoutParams);
    }

    public void setOnScrollChangeListener(@Nullable OnScrollChangeListener onScrollChangeListener) {
        this.B = onScrollChangeListener;
    }

    private boolean b() {
        boolean z2 = false;
        View childAt = getChildAt(0);
        if (childAt == null) {
            return false;
        }
        if (getHeight() < childAt.getHeight() + getPaddingTop() + getPaddingBottom()) {
            z2 = true;
        }
        return z2;
    }

    public boolean isFillViewport() {
        return this.l;
    }

    public void setFillViewport(boolean z2) {
        if (z2 != this.l) {
            this.l = z2;
            requestLayout();
        }
    }

    public boolean isSmoothScrollingEnabled() {
        return this.m;
    }

    public void setSmoothScrollingEnabled(boolean z2) {
        this.m = z2;
    }

    /* access modifiers changed from: protected */
    public void onScrollChanged(int i2, int i3, int i4, int i5) {
        super.onScrollChanged(i2, i3, i4, i5);
        if (this.B != null) {
            this.B.onScrollChange(this, i2, i3, i4, i5);
        }
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i2, int i3) {
        super.onMeasure(i2, i3);
        if (this.l && MeasureSpec.getMode(i3) != 0 && getChildCount() > 0) {
            View childAt = getChildAt(0);
            int measuredHeight = getMeasuredHeight();
            if (childAt.getMeasuredHeight() < measuredHeight) {
                childAt.measure(getChildMeasureSpec(i2, getPaddingLeft() + getPaddingRight(), ((FrameLayout.LayoutParams) childAt.getLayoutParams()).width), MeasureSpec.makeMeasureSpec((measuredHeight - getPaddingTop()) - getPaddingBottom(), 1073741824));
            }
        }
    }

    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        return super.dispatchKeyEvent(keyEvent) || executeKeyEvent(keyEvent);
    }

    public boolean executeKeyEvent(@NonNull KeyEvent keyEvent) {
        this.b.setEmpty();
        boolean z2 = false;
        int i2 = 130;
        if (b()) {
            if (keyEvent.getAction() == 0) {
                int keyCode = keyEvent.getKeyCode();
                if (keyCode != 62) {
                    switch (keyCode) {
                        case 19:
                            if (keyEvent.isAltPressed()) {
                                z2 = fullScroll(33);
                                break;
                            } else {
                                z2 = arrowScroll(33);
                                break;
                            }
                        case 20:
                            if (keyEvent.isAltPressed()) {
                                z2 = fullScroll(130);
                                break;
                            } else {
                                z2 = arrowScroll(130);
                                break;
                            }
                    }
                } else {
                    if (keyEvent.isShiftPressed()) {
                        i2 = 33;
                    }
                    pageScroll(i2);
                }
            }
            return z2;
        } else if (!isFocused() || keyEvent.getKeyCode() == 4) {
            return false;
        } else {
            View findFocus = findFocus();
            if (findFocus == this) {
                findFocus = null;
            }
            View findNextFocus = FocusFinder.getInstance().findNextFocus(this, findFocus, 130);
            if (!(findNextFocus == null || findNextFocus == this || !findNextFocus.requestFocus(130))) {
                z2 = true;
            }
            return z2;
        }
    }

    private boolean a(int i2, int i3) {
        boolean z2 = false;
        if (getChildCount() <= 0) {
            return false;
        }
        int scrollY = getScrollY();
        View childAt = getChildAt(0);
        if (i3 >= childAt.getTop() - scrollY && i3 < childAt.getBottom() - scrollY && i2 >= childAt.getLeft() && i2 < childAt.getRight()) {
            z2 = true;
        }
        return z2;
    }

    private void c() {
        if (this.k == null) {
            this.k = VelocityTracker.obtain();
        } else {
            this.k.clear();
        }
    }

    private void d() {
        if (this.k == null) {
            this.k = VelocityTracker.obtain();
        }
    }

    private void e() {
        if (this.k != null) {
            this.k.recycle();
            this.k = null;
        }
    }

    public void requestDisallowInterceptTouchEvent(boolean z2) {
        if (z2) {
            e();
        }
        super.requestDisallowInterceptTouchEvent(z2);
    }

    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        if (action == 2 && this.j) {
            return true;
        }
        int i2 = action & 255;
        if (i2 != 6) {
            switch (i2) {
                case 0:
                    int y2 = (int) motionEvent.getY();
                    if (a((int) motionEvent.getX(), y2)) {
                        this.f = y2;
                        this.q = motionEvent.getPointerId(0);
                        c();
                        this.k.addMovement(motionEvent);
                        this.c.computeScrollOffset();
                        this.j = !this.c.isFinished();
                        startNestedScroll(2, 0);
                        break;
                    } else {
                        this.j = false;
                        e();
                        break;
                    }
                case 1:
                case 3:
                    this.j = false;
                    this.q = -1;
                    e();
                    if (this.c.springBack(getScrollX(), getScrollY(), 0, 0, 0, getScrollRange())) {
                        ViewCompat.postInvalidateOnAnimation(this);
                    }
                    stopNestedScroll(0);
                    break;
                case 2:
                    int i3 = this.q;
                    if (i3 != -1) {
                        int findPointerIndex = motionEvent.findPointerIndex(i3);
                        if (findPointerIndex != -1) {
                            int y3 = (int) motionEvent.getY(findPointerIndex);
                            if (Math.abs(y3 - this.f) > this.n && (2 & getNestedScrollAxes()) == 0) {
                                this.j = true;
                                this.f = y3;
                                d();
                                this.k.addMovement(motionEvent);
                                this.t = 0;
                                ViewParent parent = getParent();
                                if (parent != null) {
                                    parent.requestDisallowInterceptTouchEvent(true);
                                    break;
                                }
                            }
                        } else {
                            StringBuilder sb = new StringBuilder();
                            sb.append("Invalid pointerId=");
                            sb.append(i3);
                            sb.append(" in onInterceptTouchEvent");
                            Log.e("NestedScrollView", sb.toString());
                            break;
                        }
                    }
                    break;
            }
        } else {
            a(motionEvent);
        }
        return this.j;
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        MotionEvent motionEvent2 = motionEvent;
        d();
        MotionEvent obtain = MotionEvent.obtain(motionEvent);
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 0) {
            this.t = 0;
        }
        obtain.offsetLocation(BitmapDescriptorFactory.HUE_RED, (float) this.t);
        switch (actionMasked) {
            case 0:
                if (getChildCount() != 0) {
                    boolean z2 = !this.c.isFinished();
                    this.j = z2;
                    if (z2) {
                        ViewParent parent = getParent();
                        if (parent != null) {
                            parent.requestDisallowInterceptTouchEvent(true);
                        }
                    }
                    if (!this.c.isFinished()) {
                        this.c.abortAnimation();
                    }
                    this.f = (int) motionEvent.getY();
                    this.q = motionEvent2.getPointerId(0);
                    startNestedScroll(2, 0);
                    break;
                } else {
                    return false;
                }
            case 1:
                VelocityTracker velocityTracker = this.k;
                velocityTracker.computeCurrentVelocity(1000, (float) this.p);
                int yVelocity = (int) velocityTracker.getYVelocity(this.q);
                if (Math.abs(yVelocity) > this.o) {
                    b(-yVelocity);
                } else if (this.c.springBack(getScrollX(), getScrollY(), 0, 0, 0, getScrollRange())) {
                    ViewCompat.postInvalidateOnAnimation(this);
                }
                this.q = -1;
                f();
                break;
            case 2:
                int findPointerIndex = motionEvent2.findPointerIndex(this.q);
                if (findPointerIndex != -1) {
                    int y2 = (int) motionEvent2.getY(findPointerIndex);
                    int i2 = this.f - y2;
                    if (dispatchNestedPreScroll(0, i2, this.s, this.r, 0)) {
                        i2 -= this.s[1];
                        obtain.offsetLocation(BitmapDescriptorFactory.HUE_RED, (float) this.r[1]);
                        this.t += this.r[1];
                    }
                    if (!this.j && Math.abs(i2) > this.n) {
                        ViewParent parent2 = getParent();
                        if (parent2 != null) {
                            parent2.requestDisallowInterceptTouchEvent(true);
                        }
                        this.j = true;
                        if (i2 > 0) {
                            i2 -= this.n;
                        } else {
                            i2 += this.n;
                        }
                    }
                    int i3 = i2;
                    if (this.j) {
                        this.f = y2 - this.r[1];
                        int scrollY = getScrollY();
                        int scrollRange = getScrollRange();
                        int overScrollMode = getOverScrollMode();
                        boolean z3 = overScrollMode == 0 || (overScrollMode == 1 && scrollRange > 0);
                        int i4 = scrollRange;
                        int i5 = i3;
                        int i6 = findPointerIndex;
                        if (a(0, i3, 0, getScrollY(), 0, scrollRange, 0, 0, true) && !hasNestedScrollingParent(0)) {
                            this.k.clear();
                        }
                        int scrollY2 = getScrollY() - scrollY;
                        if (!dispatchNestedScroll(0, scrollY2, 0, i5 - scrollY2, this.r, 0)) {
                            if (z3) {
                                g();
                                int i7 = scrollY + i5;
                                if (i7 < 0) {
                                    EdgeEffectCompat.onPull(this.d, ((float) i5) / ((float) getHeight()), motionEvent2.getX(i6) / ((float) getWidth()));
                                    if (!this.e.isFinished()) {
                                        this.e.onRelease();
                                    }
                                } else {
                                    int i8 = i6;
                                    if (i7 > i4) {
                                        EdgeEffectCompat.onPull(this.e, ((float) i5) / ((float) getHeight()), 1.0f - (motionEvent2.getX(i8) / ((float) getWidth())));
                                        if (!this.d.isFinished()) {
                                            this.d.onRelease();
                                        }
                                    }
                                }
                                if (this.d != null && (!this.d.isFinished() || !this.e.isFinished())) {
                                    ViewCompat.postInvalidateOnAnimation(this);
                                    break;
                                }
                            }
                        } else {
                            this.f -= this.r[1];
                            obtain.offsetLocation(BitmapDescriptorFactory.HUE_RED, (float) this.r[1]);
                            this.t += this.r[1];
                            break;
                        }
                    }
                } else {
                    StringBuilder sb = new StringBuilder();
                    sb.append("Invalid pointerId=");
                    sb.append(this.q);
                    sb.append(" in onTouchEvent");
                    Log.e("NestedScrollView", sb.toString());
                    break;
                }
                break;
            case 3:
                if (this.j && getChildCount() > 0 && this.c.springBack(getScrollX(), getScrollY(), 0, 0, 0, getScrollRange())) {
                    ViewCompat.postInvalidateOnAnimation(this);
                }
                this.q = -1;
                f();
                break;
            case 5:
                int actionIndex = motionEvent.getActionIndex();
                this.f = (int) motionEvent2.getY(actionIndex);
                this.q = motionEvent2.getPointerId(actionIndex);
                break;
            case 6:
                a(motionEvent);
                this.f = (int) motionEvent2.getY(motionEvent2.findPointerIndex(this.q));
                break;
        }
        if (this.k != null) {
            this.k.addMovement(obtain);
        }
        obtain.recycle();
        return true;
    }

    private void a(MotionEvent motionEvent) {
        int actionIndex = motionEvent.getActionIndex();
        if (motionEvent.getPointerId(actionIndex) == this.q) {
            int i2 = actionIndex == 0 ? 1 : 0;
            this.f = (int) motionEvent.getY(i2);
            this.q = motionEvent.getPointerId(i2);
            if (this.k != null) {
                this.k.clear();
            }
        }
    }

    public boolean onGenericMotionEvent(MotionEvent motionEvent) {
        if ((motionEvent.getSource() & 2) != 0 && motionEvent.getAction() == 8 && !this.j) {
            float axisValue = motionEvent.getAxisValue(9);
            if (axisValue != BitmapDescriptorFactory.HUE_RED) {
                int verticalScrollFactorCompat = (int) (axisValue * getVerticalScrollFactorCompat());
                int scrollRange = getScrollRange();
                int scrollY = getScrollY();
                int i2 = scrollY - verticalScrollFactorCompat;
                if (i2 < 0) {
                    i2 = 0;
                } else if (i2 > scrollRange) {
                    i2 = scrollRange;
                }
                if (i2 != scrollY) {
                    super.scrollTo(getScrollX(), i2);
                    return true;
                }
            }
        }
        return false;
    }

    private float getVerticalScrollFactorCompat() {
        if (this.A == BitmapDescriptorFactory.HUE_RED) {
            TypedValue typedValue = new TypedValue();
            Context context = getContext();
            if (!context.getTheme().resolveAttribute(16842829, typedValue, true)) {
                throw new IllegalStateException("Expected theme to define listPreferredItemHeight.");
            }
            this.A = typedValue.getDimension(context.getResources().getDisplayMetrics());
        }
        return this.A;
    }

    /* access modifiers changed from: protected */
    public void onOverScrolled(int i2, int i3, boolean z2, boolean z3) {
        super.scrollTo(i2, i3);
    }

    /* access modifiers changed from: 0000 */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x0058  */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x005b  */
    /* JADX WARNING: Removed duplicated region for block: B:43:0x007b A[ADDED_TO_REGION] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean a(int r17, int r18, int r19, int r20, int r21, int r22, int r23, int r24, boolean r25) {
        /*
            r16 = this;
            r0 = r16
            int r1 = r16.getOverScrollMode()
            int r2 = r16.computeHorizontalScrollRange()
            int r3 = r16.computeHorizontalScrollExtent()
            r4 = 0
            r5 = 1
            if (r2 <= r3) goto L_0x0014
            r2 = 1
            goto L_0x0015
        L_0x0014:
            r2 = 0
        L_0x0015:
            int r3 = r16.computeVerticalScrollRange()
            int r6 = r16.computeVerticalScrollExtent()
            if (r3 <= r6) goto L_0x0021
            r3 = 1
            goto L_0x0022
        L_0x0021:
            r3 = 0
        L_0x0022:
            if (r1 == 0) goto L_0x002b
            if (r1 != r5) goto L_0x0029
            if (r2 == 0) goto L_0x0029
            goto L_0x002b
        L_0x0029:
            r2 = 0
            goto L_0x002c
        L_0x002b:
            r2 = 1
        L_0x002c:
            if (r1 == 0) goto L_0x0035
            if (r1 != r5) goto L_0x0033
            if (r3 == 0) goto L_0x0033
            goto L_0x0035
        L_0x0033:
            r6 = 0
            goto L_0x0036
        L_0x0035:
            r6 = 1
        L_0x0036:
            int r1 = r19 + r17
            if (r2 != 0) goto L_0x003c
            r7 = 0
            goto L_0x003e
        L_0x003c:
            r7 = r23
        L_0x003e:
            int r2 = r20 + r18
            if (r6 != 0) goto L_0x0044
            r3 = 0
            goto L_0x0046
        L_0x0044:
            r3 = r24
        L_0x0046:
            int r6 = -r7
            int r7 = r7 + r21
            int r8 = -r3
            int r3 = r3 + r22
            if (r1 <= r7) goto L_0x0051
            r6 = r7
        L_0x004f:
            r1 = 1
            goto L_0x0056
        L_0x0051:
            if (r1 >= r6) goto L_0x0054
            goto L_0x004f
        L_0x0054:
            r6 = r1
            r1 = 0
        L_0x0056:
            if (r2 <= r3) goto L_0x005b
            r8 = r3
        L_0x0059:
            r2 = 1
            goto L_0x0060
        L_0x005b:
            if (r2 >= r8) goto L_0x005e
            goto L_0x0059
        L_0x005e:
            r8 = r2
            r2 = 0
        L_0x0060:
            if (r2 == 0) goto L_0x0076
            boolean r3 = r0.hasNestedScrollingParent(r5)
            if (r3 != 0) goto L_0x0076
            android.widget.OverScroller r9 = r0.c
            r12 = 0
            r13 = 0
            r14 = 0
            int r15 = r16.getScrollRange()
            r10 = r6
            r11 = r8
            r9.springBack(r10, r11, r12, r13, r14, r15)
        L_0x0076:
            r0.onOverScrolled(r6, r8, r1, r2)
            if (r1 != 0) goto L_0x007d
            if (r2 == 0) goto L_0x007e
        L_0x007d:
            r4 = 1
        L_0x007e:
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.widget.NestedScrollView.a(int, int, int, int, int, int, int, int, boolean):boolean");
    }

    /* access modifiers changed from: 0000 */
    public int getScrollRange() {
        if (getChildCount() > 0) {
            return Math.max(0, getChildAt(0).getHeight() - ((getHeight() - getPaddingBottom()) - getPaddingTop()));
        }
        return 0;
    }

    private View a(boolean z2, int i2, int i3) {
        ArrayList focusables = getFocusables(2);
        int size = focusables.size();
        View view = null;
        boolean z3 = false;
        for (int i4 = 0; i4 < size; i4++) {
            View view2 = (View) focusables.get(i4);
            int top = view2.getTop();
            int bottom = view2.getBottom();
            if (i2 < bottom && top < i3) {
                boolean z4 = i2 < top && bottom < i3;
                if (view == null) {
                    view = view2;
                    z3 = z4;
                } else {
                    boolean z5 = (z2 && top < view.getTop()) || (!z2 && bottom > view.getBottom());
                    if (z3) {
                        if (z4) {
                            if (!z5) {
                            }
                        }
                    } else if (z4) {
                        view = view2;
                        z3 = true;
                    } else if (!z5) {
                    }
                    view = view2;
                }
            }
        }
        return view;
    }

    public boolean pageScroll(int i2) {
        boolean z2 = i2 == 130;
        int height = getHeight();
        if (z2) {
            this.b.top = getScrollY() + height;
            int childCount = getChildCount();
            if (childCount > 0) {
                View childAt = getChildAt(childCount - 1);
                if (this.b.top + height > childAt.getBottom()) {
                    this.b.top = childAt.getBottom() - height;
                }
            }
        } else {
            this.b.top = getScrollY() - height;
            if (this.b.top < 0) {
                this.b.top = 0;
            }
        }
        this.b.bottom = this.b.top + height;
        return a(i2, this.b.top, this.b.bottom);
    }

    public boolean fullScroll(int i2) {
        boolean z2 = i2 == 130;
        int height = getHeight();
        this.b.top = 0;
        this.b.bottom = height;
        if (z2) {
            int childCount = getChildCount();
            if (childCount > 0) {
                this.b.bottom = getChildAt(childCount - 1).getBottom() + getPaddingBottom();
                this.b.top = this.b.bottom - height;
            }
        }
        return a(i2, this.b.top, this.b.bottom);
    }

    private boolean a(int i2, int i3, int i4) {
        int height = getHeight();
        int scrollY = getScrollY();
        int i5 = height + scrollY;
        boolean z2 = false;
        boolean z3 = i2 == 33;
        View a2 = a(z3, i3, i4);
        if (a2 == null) {
            a2 = this;
        }
        if (i3 < scrollY || i4 > i5) {
            a(z3 ? i3 - scrollY : i4 - i5);
            z2 = true;
        }
        if (a2 != findFocus()) {
            a2.requestFocus(i2);
        }
        return z2;
    }

    public boolean arrowScroll(int i2) {
        View findFocus = findFocus();
        if (findFocus == this) {
            findFocus = null;
        }
        View findNextFocus = FocusFinder.getInstance().findNextFocus(this, findFocus, i2);
        int maxScrollAmount = getMaxScrollAmount();
        if (findNextFocus == null || !a(findNextFocus, maxScrollAmount, getHeight())) {
            if (i2 == 33 && getScrollY() < maxScrollAmount) {
                maxScrollAmount = getScrollY();
            } else if (i2 == 130 && getChildCount() > 0) {
                int bottom = getChildAt(0).getBottom() - ((getScrollY() + getHeight()) - getPaddingBottom());
                if (bottom < maxScrollAmount) {
                    maxScrollAmount = bottom;
                }
            }
            if (maxScrollAmount == 0) {
                return false;
            }
            if (i2 != 130) {
                maxScrollAmount = -maxScrollAmount;
            }
            a(maxScrollAmount);
        } else {
            findNextFocus.getDrawingRect(this.b);
            offsetDescendantRectToMyCoords(findNextFocus, this.b);
            a(computeScrollDeltaToGetChildRectOnScreen(this.b));
            findNextFocus.requestFocus(i2);
        }
        if (findFocus != null && findFocus.isFocused() && a(findFocus)) {
            int descendantFocusability = getDescendantFocusability();
            setDescendantFocusability(131072);
            requestFocus();
            setDescendantFocusability(descendantFocusability);
        }
        return true;
    }

    private boolean a(View view) {
        return !a(view, 0, getHeight());
    }

    private boolean a(View view, int i2, int i3) {
        view.getDrawingRect(this.b);
        offsetDescendantRectToMyCoords(view, this.b);
        return this.b.bottom + i2 >= getScrollY() && this.b.top - i2 <= getScrollY() + i3;
    }

    private void a(int i2) {
        if (i2 == 0) {
            return;
        }
        if (this.m) {
            smoothScrollBy(0, i2);
        } else {
            scrollBy(0, i2);
        }
    }

    public final void smoothScrollBy(int i2, int i3) {
        if (getChildCount() != 0) {
            if (AnimationUtils.currentAnimationTimeMillis() - this.a > 250) {
                int max = Math.max(0, getChildAt(0).getHeight() - ((getHeight() - getPaddingBottom()) - getPaddingTop()));
                int scrollY = getScrollY();
                this.c.startScroll(getScrollX(), scrollY, 0, Math.max(0, Math.min(i3 + scrollY, max)) - scrollY);
                ViewCompat.postInvalidateOnAnimation(this);
            } else {
                if (!this.c.isFinished()) {
                    this.c.abortAnimation();
                }
                scrollBy(i2, i3);
            }
            this.a = AnimationUtils.currentAnimationTimeMillis();
        }
    }

    public final void smoothScrollTo(int i2, int i3) {
        smoothScrollBy(i2 - getScrollX(), i3 - getScrollY());
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    public int computeVerticalScrollRange() {
        int height = (getHeight() - getPaddingBottom()) - getPaddingTop();
        if (getChildCount() == 0) {
            return height;
        }
        int bottom = getChildAt(0).getBottom();
        int scrollY = getScrollY();
        int max = Math.max(0, bottom - height);
        if (scrollY < 0) {
            bottom -= scrollY;
        } else if (scrollY > max) {
            bottom += scrollY - max;
        }
        return bottom;
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    public int computeVerticalScrollOffset() {
        return Math.max(0, super.computeVerticalScrollOffset());
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    public int computeVerticalScrollExtent() {
        return super.computeVerticalScrollExtent();
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    public int computeHorizontalScrollRange() {
        return super.computeHorizontalScrollRange();
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    public int computeHorizontalScrollOffset() {
        return super.computeHorizontalScrollOffset();
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    public int computeHorizontalScrollExtent() {
        return super.computeHorizontalScrollExtent();
    }

    /* access modifiers changed from: protected */
    public void measureChild(View view, int i2, int i3) {
        view.measure(getChildMeasureSpec(i2, getPaddingLeft() + getPaddingRight(), view.getLayoutParams().width), MeasureSpec.makeMeasureSpec(0, 0));
    }

    /* access modifiers changed from: protected */
    public void measureChildWithMargins(View view, int i2, int i3, int i4, int i5) {
        MarginLayoutParams marginLayoutParams = (MarginLayoutParams) view.getLayoutParams();
        view.measure(getChildMeasureSpec(i2, getPaddingLeft() + getPaddingRight() + marginLayoutParams.leftMargin + marginLayoutParams.rightMargin + i3, marginLayoutParams.width), MeasureSpec.makeMeasureSpec(marginLayoutParams.topMargin + marginLayoutParams.bottomMargin, 0));
    }

    public void computeScroll() {
        if (this.c.computeScrollOffset()) {
            this.c.getCurrX();
            int currY = this.c.getCurrY();
            int i2 = currY - this.u;
            if (dispatchNestedPreScroll(0, i2, this.s, null, 1)) {
                i2 -= this.s[1];
            }
            int i3 = i2;
            if (i3 != 0) {
                int scrollRange = getScrollRange();
                int scrollY = getScrollY();
                int i4 = scrollY;
                a(0, i3, getScrollX(), scrollY, 0, scrollRange, 0, 0, false);
                int scrollY2 = getScrollY() - i4;
                if (!dispatchNestedScroll(0, scrollY2, 0, i3 - scrollY2, null, 1)) {
                    int overScrollMode = getOverScrollMode();
                    if (overScrollMode == 0 || (overScrollMode == 1 && scrollRange > 0)) {
                        g();
                        if (currY <= 0 && i4 > 0) {
                            this.d.onAbsorb((int) this.c.getCurrVelocity());
                        } else if (currY >= scrollRange && i4 < scrollRange) {
                            this.e.onAbsorb((int) this.c.getCurrVelocity());
                        }
                    }
                }
            }
            this.u = currY;
            ViewCompat.postInvalidateOnAnimation(this);
            return;
        }
        if (hasNestedScrollingParent(1)) {
            stopNestedScroll(1);
        }
        this.u = 0;
    }

    private void b(View view) {
        view.getDrawingRect(this.b);
        offsetDescendantRectToMyCoords(view, this.b);
        int computeScrollDeltaToGetChildRectOnScreen = computeScrollDeltaToGetChildRectOnScreen(this.b);
        if (computeScrollDeltaToGetChildRectOnScreen != 0) {
            scrollBy(0, computeScrollDeltaToGetChildRectOnScreen);
        }
    }

    private boolean a(Rect rect, boolean z2) {
        int computeScrollDeltaToGetChildRectOnScreen = computeScrollDeltaToGetChildRectOnScreen(rect);
        boolean z3 = computeScrollDeltaToGetChildRectOnScreen != 0;
        if (z3) {
            if (z2) {
                scrollBy(0, computeScrollDeltaToGetChildRectOnScreen);
            } else {
                smoothScrollBy(0, computeScrollDeltaToGetChildRectOnScreen);
            }
        }
        return z3;
    }

    /* access modifiers changed from: protected */
    public int computeScrollDeltaToGetChildRectOnScreen(Rect rect) {
        int i2;
        int i3;
        int i4 = 0;
        if (getChildCount() == 0) {
            return 0;
        }
        int height = getHeight();
        int scrollY = getScrollY();
        int i5 = scrollY + height;
        int verticalFadingEdgeLength = getVerticalFadingEdgeLength();
        if (rect.top > 0) {
            scrollY += verticalFadingEdgeLength;
        }
        if (rect.bottom < getChildAt(0).getHeight()) {
            i5 -= verticalFadingEdgeLength;
        }
        if (rect.bottom > i5 && rect.top > scrollY) {
            if (rect.height() > height) {
                i3 = (rect.top - scrollY) + 0;
            } else {
                i3 = (rect.bottom - i5) + 0;
            }
            i4 = Math.min(i3, getChildAt(0).getBottom() - i5);
        } else if (rect.top < scrollY && rect.bottom < i5) {
            if (rect.height() > height) {
                i2 = 0 - (i5 - rect.bottom);
            } else {
                i2 = 0 - (scrollY - rect.top);
            }
            i4 = Math.max(i2, -getScrollY());
        }
        return i4;
    }

    public void requestChildFocus(View view, View view2) {
        if (!this.g) {
            b(view2);
        } else {
            this.i = view2;
        }
        super.requestChildFocus(view, view2);
    }

    /* access modifiers changed from: protected */
    public boolean onRequestFocusInDescendants(int i2, Rect rect) {
        View view;
        if (i2 == 2) {
            i2 = 130;
        } else if (i2 == 1) {
            i2 = 33;
        }
        if (rect == null) {
            view = FocusFinder.getInstance().findNextFocus(this, null, i2);
        } else {
            view = FocusFinder.getInstance().findNextFocusFromRect(this, rect, i2);
        }
        if (view != null && !a(view)) {
            return view.requestFocus(i2, rect);
        }
        return false;
    }

    public boolean requestChildRectangleOnScreen(View view, Rect rect, boolean z2) {
        rect.offset(view.getLeft() - view.getScrollX(), view.getTop() - view.getScrollY());
        return a(rect, z2);
    }

    public void requestLayout() {
        this.g = true;
        super.requestLayout();
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z2, int i2, int i3, int i4, int i5) {
        super.onLayout(z2, i2, i3, i4, i5);
        this.g = false;
        if (this.i != null && a(this.i, (View) this)) {
            b(this.i);
        }
        this.i = null;
        if (!this.h) {
            if (this.v != null) {
                scrollTo(getScrollX(), this.v.a);
                this.v = null;
            }
            int max = Math.max(0, (getChildCount() > 0 ? getChildAt(0).getMeasuredHeight() : 0) - (((i5 - i3) - getPaddingBottom()) - getPaddingTop()));
            if (getScrollY() > max) {
                scrollTo(getScrollX(), max);
            } else if (getScrollY() < 0) {
                scrollTo(getScrollX(), 0);
            }
        }
        scrollTo(getScrollX(), getScrollY());
        this.h = true;
    }

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.h = false;
    }

    /* access modifiers changed from: protected */
    public void onSizeChanged(int i2, int i3, int i4, int i5) {
        super.onSizeChanged(i2, i3, i4, i5);
        View findFocus = findFocus();
        if (!(findFocus == null || this == findFocus || !a(findFocus, 0, i5))) {
            findFocus.getDrawingRect(this.b);
            offsetDescendantRectToMyCoords(findFocus, this.b);
            a(computeScrollDeltaToGetChildRectOnScreen(this.b));
        }
    }

    private static boolean a(View view, View view2) {
        boolean z2 = true;
        if (view == view2) {
            return true;
        }
        ViewParent parent = view.getParent();
        if (!(parent instanceof ViewGroup) || !a((View) parent, view2)) {
            z2 = false;
        }
        return z2;
    }

    public void fling(int i2) {
        if (getChildCount() > 0) {
            startNestedScroll(2, 1);
            this.c.fling(getScrollX(), getScrollY(), 0, i2, 0, 0, Integer.MIN_VALUE, SubsamplingScaleImageView.TILE_SIZE_AUTO, 0, 0);
            this.u = getScrollY();
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    private void b(int i2) {
        int scrollY = getScrollY();
        boolean z2 = (scrollY > 0 || i2 > 0) && (scrollY < getScrollRange() || i2 < 0);
        float f2 = (float) i2;
        if (!dispatchNestedPreFling(BitmapDescriptorFactory.HUE_RED, f2)) {
            dispatchNestedFling(BitmapDescriptorFactory.HUE_RED, f2, z2);
            fling(i2);
        }
    }

    private void f() {
        this.j = false;
        e();
        stopNestedScroll(0);
        if (this.d != null) {
            this.d.onRelease();
            this.e.onRelease();
        }
    }

    public void scrollTo(int i2, int i3) {
        if (getChildCount() > 0) {
            View childAt = getChildAt(0);
            int b2 = b(i2, (getWidth() - getPaddingRight()) - getPaddingLeft(), childAt.getWidth());
            int b3 = b(i3, (getHeight() - getPaddingBottom()) - getPaddingTop(), childAt.getHeight());
            if (b2 != getScrollX() || b3 != getScrollY()) {
                super.scrollTo(b2, b3);
            }
        }
    }

    private void g() {
        if (getOverScrollMode() == 2) {
            this.d = null;
            this.e = null;
        } else if (this.d == null) {
            Context context = getContext();
            this.d = new EdgeEffect(context);
            this.e = new EdgeEffect(context);
        }
    }

    public void draw(Canvas canvas) {
        int i2;
        super.draw(canvas);
        if (this.d != null) {
            int scrollY = getScrollY();
            int i3 = 0;
            if (!this.d.isFinished()) {
                int save = canvas.save();
                int width = getWidth();
                int height = getHeight();
                int min = Math.min(0, scrollY);
                if (VERSION.SDK_INT < 21 || getClipToPadding()) {
                    width -= getPaddingLeft() + getPaddingRight();
                    i2 = getPaddingLeft() + 0;
                } else {
                    i2 = 0;
                }
                if (VERSION.SDK_INT >= 21 && getClipToPadding()) {
                    height -= getPaddingTop() + getPaddingBottom();
                    min += getPaddingTop();
                }
                canvas.translate((float) i2, (float) min);
                this.d.setSize(width, height);
                if (this.d.draw(canvas)) {
                    ViewCompat.postInvalidateOnAnimation(this);
                }
                canvas.restoreToCount(save);
            }
            if (!this.e.isFinished()) {
                int save2 = canvas.save();
                int width2 = getWidth();
                int height2 = getHeight();
                int max = Math.max(getScrollRange(), scrollY) + height2;
                if (VERSION.SDK_INT < 21 || getClipToPadding()) {
                    width2 -= getPaddingLeft() + getPaddingRight();
                    i3 = 0 + getPaddingLeft();
                }
                if (VERSION.SDK_INT >= 21 && getClipToPadding()) {
                    height2 -= getPaddingTop() + getPaddingBottom();
                    max -= getPaddingBottom();
                }
                canvas.translate((float) (i3 - width2), (float) max);
                canvas.rotate(180.0f, (float) width2, BitmapDescriptorFactory.HUE_RED);
                this.e.setSize(width2, height2);
                if (this.e.draw(canvas)) {
                    ViewCompat.postInvalidateOnAnimation(this);
                }
                canvas.restoreToCount(save2);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable instanceof SavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.getSuperState());
        this.v = savedState;
        requestLayout();
    }

    /* access modifiers changed from: protected */
    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        savedState.a = getScrollY();
        return savedState;
    }
}
