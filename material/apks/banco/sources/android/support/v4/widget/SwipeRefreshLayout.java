package android.support.v4.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build.VERSION;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.NestedScrollingChild;
import android.support.v4.view.NestedScrollingChildHelper;
import android.support.v4.view.NestedScrollingParent;
import android.support.v4.view.NestedScrollingParentHelper;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Transformation;
import android.widget.AbsListView;
import android.widget.ListView;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

public class SwipeRefreshLayout extends ViewGroup implements NestedScrollingChild, NestedScrollingParent {
    private static final int[] B = {16842766};
    public static final int DEFAULT = 1;
    public static final int LARGE = 0;
    private static final String k = "SwipeRefreshLayout";
    private final DecelerateInterpolator A;
    private int C;
    private Animation D;
    private Animation E;
    private Animation F;
    private Animation G;
    private Animation H;
    private int I;
    private OnChildScrollUpCallback J;
    private AnimationListener K;
    private final Animation L;
    private final Animation M;
    OnRefreshListener a;
    boolean b;
    int c;
    boolean d;
    CircleImageView e;
    float f;
    int g;
    CircularProgressDrawable h;
    boolean i;
    boolean j;
    private View l;
    private int m;
    protected int mFrom;
    protected int mOriginalOffsetTop;
    private float n;
    private float o;
    private final NestedScrollingParentHelper p;
    private final NestedScrollingChildHelper q;
    private final int[] r;
    private final int[] s;
    private boolean t;
    private int u;
    private float v;
    private float w;
    private boolean x;
    private int y;
    private boolean z;

    public interface OnChildScrollUpCallback {
        boolean canChildScrollUp(@NonNull SwipeRefreshLayout swipeRefreshLayout, @Nullable View view);
    }

    public interface OnRefreshListener {
        void onRefresh();
    }

    /* access modifiers changed from: 0000 */
    public void a() {
        this.e.clearAnimation();
        this.h.stop();
        this.e.setVisibility(8);
        setColorViewAlpha(255);
        if (this.d) {
            setAnimationProgress(BitmapDescriptorFactory.HUE_RED);
        } else {
            setTargetOffsetTopAndBottom(this.mOriginalOffsetTop - this.c);
        }
        this.c = this.e.getTop();
    }

    public void setEnabled(boolean z2) {
        super.setEnabled(z2);
        if (!z2) {
            a();
        }
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        a();
    }

    private void setColorViewAlpha(int i2) {
        this.e.getBackground().setAlpha(i2);
        this.h.setAlpha(i2);
    }

    public void setProgressViewOffset(boolean z2, int i2, int i3) {
        this.d = z2;
        this.mOriginalOffsetTop = i2;
        this.g = i3;
        this.j = true;
        a();
        this.b = false;
    }

    public int getProgressViewStartOffset() {
        return this.mOriginalOffsetTop;
    }

    public int getProgressViewEndOffset() {
        return this.g;
    }

    public void setProgressViewEndTarget(boolean z2, int i2) {
        this.g = i2;
        this.d = z2;
        this.e.invalidate();
    }

    public void setSize(int i2) {
        if (i2 == 0 || i2 == 1) {
            DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
            if (i2 == 0) {
                this.I = (int) (displayMetrics.density * 56.0f);
            } else {
                this.I = (int) (displayMetrics.density * 40.0f);
            }
            this.e.setImageDrawable(null);
            this.h.setStyle(i2);
            this.e.setImageDrawable(this.h);
        }
    }

    public SwipeRefreshLayout(@NonNull Context context) {
        this(context, null);
    }

    public SwipeRefreshLayout(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        this.b = false;
        this.n = -1.0f;
        this.r = new int[2];
        this.s = new int[2];
        this.y = -1;
        this.C = -1;
        this.K = new AnimationListener() {
            public void onAnimationRepeat(Animation animation) {
            }

            public void onAnimationStart(Animation animation) {
            }

            public void onAnimationEnd(Animation animation) {
                if (SwipeRefreshLayout.this.b) {
                    SwipeRefreshLayout.this.h.setAlpha(255);
                    SwipeRefreshLayout.this.h.start();
                    if (SwipeRefreshLayout.this.i && SwipeRefreshLayout.this.a != null) {
                        SwipeRefreshLayout.this.a.onRefresh();
                    }
                    SwipeRefreshLayout.this.c = SwipeRefreshLayout.this.e.getTop();
                    return;
                }
                SwipeRefreshLayout.this.a();
            }
        };
        this.L = new Animation() {
            public void applyTransformation(float f, Transformation transformation) {
                int i;
                if (!SwipeRefreshLayout.this.j) {
                    i = SwipeRefreshLayout.this.g - Math.abs(SwipeRefreshLayout.this.mOriginalOffsetTop);
                } else {
                    i = SwipeRefreshLayout.this.g;
                }
                SwipeRefreshLayout.this.setTargetOffsetTopAndBottom((SwipeRefreshLayout.this.mFrom + ((int) (((float) (i - SwipeRefreshLayout.this.mFrom)) * f))) - SwipeRefreshLayout.this.e.getTop());
                SwipeRefreshLayout.this.h.setArrowScale(1.0f - f);
            }
        };
        this.M = new Animation() {
            public void applyTransformation(float f, Transformation transformation) {
                SwipeRefreshLayout.this.a(f);
            }
        };
        this.m = ViewConfiguration.get(context).getScaledTouchSlop();
        this.u = getResources().getInteger(17694721);
        setWillNotDraw(false);
        this.A = new DecelerateInterpolator(2.0f);
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        this.I = (int) (displayMetrics.density * 40.0f);
        b();
        setChildrenDrawingOrderEnabled(true);
        this.g = (int) (displayMetrics.density * 64.0f);
        this.n = (float) this.g;
        this.p = new NestedScrollingParentHelper(this);
        this.q = new NestedScrollingChildHelper(this);
        setNestedScrollingEnabled(true);
        int i2 = -this.I;
        this.c = i2;
        this.mOriginalOffsetTop = i2;
        a(1.0f);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, B);
        setEnabled(obtainStyledAttributes.getBoolean(0, true));
        obtainStyledAttributes.recycle();
    }

    /* access modifiers changed from: protected */
    public int getChildDrawingOrder(int i2, int i3) {
        if (this.C < 0) {
            return i3;
        }
        if (i3 == i2 - 1) {
            return this.C;
        }
        return i3 >= this.C ? i3 + 1 : i3;
    }

    private void b() {
        this.e = new CircleImageView(getContext(), -328966);
        this.h = new CircularProgressDrawable(getContext());
        this.h.setStyle(1);
        this.e.setImageDrawable(this.h);
        this.e.setVisibility(8);
        addView(this.e);
    }

    public void setOnRefreshListener(@Nullable OnRefreshListener onRefreshListener) {
        this.a = onRefreshListener;
    }

    public void setRefreshing(boolean z2) {
        int i2;
        if (!z2 || this.b == z2) {
            a(z2, false);
            return;
        }
        this.b = z2;
        if (!this.j) {
            i2 = this.g + this.mOriginalOffsetTop;
        } else {
            i2 = this.g;
        }
        setTargetOffsetTopAndBottom(i2 - this.c);
        this.i = false;
        b(this.K);
    }

    private void b(AnimationListener animationListener) {
        this.e.setVisibility(0);
        this.h.setAlpha(255);
        this.D = new Animation() {
            public void applyTransformation(float f, Transformation transformation) {
                SwipeRefreshLayout.this.setAnimationProgress(f);
            }
        };
        this.D.setDuration((long) this.u);
        if (animationListener != null) {
            this.e.a(animationListener);
        }
        this.e.clearAnimation();
        this.e.startAnimation(this.D);
    }

    /* access modifiers changed from: 0000 */
    public void setAnimationProgress(float f2) {
        this.e.setScaleX(f2);
        this.e.setScaleY(f2);
    }

    private void a(boolean z2, boolean z3) {
        if (this.b != z2) {
            this.i = z3;
            e();
            this.b = z2;
            if (this.b) {
                a(this.c, this.K);
            } else {
                a(this.K);
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void a(AnimationListener animationListener) {
        this.E = new Animation() {
            public void applyTransformation(float f, Transformation transformation) {
                SwipeRefreshLayout.this.setAnimationProgress(1.0f - f);
            }
        };
        this.E.setDuration(150);
        this.e.a(animationListener);
        this.e.clearAnimation();
        this.e.startAnimation(this.E);
    }

    private void c() {
        this.F = a(this.h.getAlpha(), 76);
    }

    private void d() {
        this.G = a(this.h.getAlpha(), 255);
    }

    private Animation a(final int i2, final int i3) {
        AnonymousClass4 r0 = new Animation() {
            public void applyTransformation(float f, Transformation transformation) {
                SwipeRefreshLayout.this.h.setAlpha((int) (((float) i2) + (((float) (i3 - i2)) * f)));
            }
        };
        r0.setDuration(300);
        this.e.a(null);
        this.e.clearAnimation();
        this.e.startAnimation(r0);
        return r0;
    }

    @Deprecated
    public void setProgressBackgroundColor(int i2) {
        setProgressBackgroundColorSchemeResource(i2);
    }

    public void setProgressBackgroundColorSchemeResource(@ColorRes int i2) {
        setProgressBackgroundColorSchemeColor(ContextCompat.getColor(getContext(), i2));
    }

    public void setProgressBackgroundColorSchemeColor(@ColorInt int i2) {
        this.e.setBackgroundColor(i2);
    }

    @Deprecated
    public void setColorScheme(@ColorRes int... iArr) {
        setColorSchemeResources(iArr);
    }

    public void setColorSchemeResources(@ColorRes int... iArr) {
        Context context = getContext();
        int[] iArr2 = new int[iArr.length];
        for (int i2 = 0; i2 < iArr.length; i2++) {
            iArr2[i2] = ContextCompat.getColor(context, iArr[i2]);
        }
        setColorSchemeColors(iArr2);
    }

    public void setColorSchemeColors(@ColorInt int... iArr) {
        e();
        this.h.setColorSchemeColors(iArr);
    }

    public boolean isRefreshing() {
        return this.b;
    }

    private void e() {
        if (this.l == null) {
            for (int i2 = 0; i2 < getChildCount(); i2++) {
                View childAt = getChildAt(i2);
                if (!childAt.equals(this.e)) {
                    this.l = childAt;
                    return;
                }
            }
        }
    }

    public void setDistanceToTriggerSync(int i2) {
        this.n = (float) i2;
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z2, int i2, int i3, int i4, int i5) {
        int measuredWidth = getMeasuredWidth();
        int measuredHeight = getMeasuredHeight();
        if (getChildCount() != 0) {
            if (this.l == null) {
                e();
            }
            if (this.l != null) {
                View view = this.l;
                int paddingLeft = getPaddingLeft();
                int paddingTop = getPaddingTop();
                view.layout(paddingLeft, paddingTop, ((measuredWidth - getPaddingLeft()) - getPaddingRight()) + paddingLeft, ((measuredHeight - getPaddingTop()) - getPaddingBottom()) + paddingTop);
                int measuredWidth2 = this.e.getMeasuredWidth();
                int i6 = measuredWidth / 2;
                int i7 = measuredWidth2 / 2;
                this.e.layout(i6 - i7, this.c, i6 + i7, this.c + this.e.getMeasuredHeight());
            }
        }
    }

    public void onMeasure(int i2, int i3) {
        super.onMeasure(i2, i3);
        if (this.l == null) {
            e();
        }
        if (this.l != null) {
            this.l.measure(MeasureSpec.makeMeasureSpec((getMeasuredWidth() - getPaddingLeft()) - getPaddingRight(), 1073741824), MeasureSpec.makeMeasureSpec((getMeasuredHeight() - getPaddingTop()) - getPaddingBottom(), 1073741824));
            this.e.measure(MeasureSpec.makeMeasureSpec(this.I, 1073741824), MeasureSpec.makeMeasureSpec(this.I, 1073741824));
            this.C = -1;
            int i4 = 0;
            while (true) {
                if (i4 >= getChildCount()) {
                    break;
                } else if (getChildAt(i4) == this.e) {
                    this.C = i4;
                    break;
                } else {
                    i4++;
                }
            }
        }
    }

    public int getProgressCircleDiameter() {
        return this.I;
    }

    public boolean canChildScrollUp() {
        if (this.J != null) {
            return this.J.canChildScrollUp(this, this.l);
        }
        if (this.l instanceof ListView) {
            return ListViewCompat.canScrollList((ListView) this.l, -1);
        }
        return this.l.canScrollVertically(-1);
    }

    public void setOnChildScrollUpCallback(@Nullable OnChildScrollUpCallback onChildScrollUpCallback) {
        this.J = onChildScrollUpCallback;
    }

    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        e();
        int actionMasked = motionEvent.getActionMasked();
        if (this.z && actionMasked == 0) {
            this.z = false;
        }
        if (!isEnabled() || this.z || canChildScrollUp() || this.b || this.t) {
            return false;
        }
        if (actionMasked != 6) {
            switch (actionMasked) {
                case 0:
                    setTargetOffsetTopAndBottom(this.mOriginalOffsetTop - this.e.getTop());
                    this.y = motionEvent.getPointerId(0);
                    this.x = false;
                    int findPointerIndex = motionEvent.findPointerIndex(this.y);
                    if (findPointerIndex >= 0) {
                        this.w = motionEvent.getY(findPointerIndex);
                        break;
                    } else {
                        return false;
                    }
                case 1:
                case 3:
                    this.x = false;
                    this.y = -1;
                    break;
                case 2:
                    if (this.y != -1) {
                        int findPointerIndex2 = motionEvent.findPointerIndex(this.y);
                        if (findPointerIndex2 >= 0) {
                            d(motionEvent.getY(findPointerIndex2));
                            break;
                        } else {
                            return false;
                        }
                    } else {
                        Log.e(k, "Got ACTION_MOVE event but don't have an active pointer id.");
                        return false;
                    }
            }
        } else {
            a(motionEvent);
        }
        return this.x;
    }

    public void requestDisallowInterceptTouchEvent(boolean z2) {
        if (VERSION.SDK_INT < 21 && (this.l instanceof AbsListView)) {
            return;
        }
        if (this.l == null || ViewCompat.isNestedScrollingEnabled(this.l)) {
            super.requestDisallowInterceptTouchEvent(z2);
        }
    }

    public boolean onStartNestedScroll(View view, View view2, int i2) {
        return isEnabled() && !this.z && !this.b && (i2 & 2) != 0;
    }

    public void onNestedScrollAccepted(View view, View view2, int i2) {
        this.p.onNestedScrollAccepted(view, view2, i2);
        startNestedScroll(i2 & 2);
        this.o = BitmapDescriptorFactory.HUE_RED;
        this.t = true;
    }

    public void onNestedPreScroll(View view, int i2, int i3, int[] iArr) {
        if (i3 > 0 && this.o > BitmapDescriptorFactory.HUE_RED) {
            float f2 = (float) i3;
            if (f2 > this.o) {
                iArr[1] = i3 - ((int) this.o);
                this.o = BitmapDescriptorFactory.HUE_RED;
            } else {
                this.o -= f2;
                iArr[1] = i3;
            }
            b(this.o);
        }
        if (this.j && i3 > 0 && this.o == BitmapDescriptorFactory.HUE_RED && Math.abs(i3 - iArr[1]) > 0) {
            this.e.setVisibility(8);
        }
        int[] iArr2 = this.r;
        if (dispatchNestedPreScroll(i2 - iArr[0], i3 - iArr[1], iArr2, null)) {
            iArr[0] = iArr[0] + iArr2[0];
            iArr[1] = iArr[1] + iArr2[1];
        }
    }

    public int getNestedScrollAxes() {
        return this.p.getNestedScrollAxes();
    }

    public void onStopNestedScroll(View view) {
        this.p.onStopNestedScroll(view);
        this.t = false;
        if (this.o > BitmapDescriptorFactory.HUE_RED) {
            c(this.o);
            this.o = BitmapDescriptorFactory.HUE_RED;
        }
        stopNestedScroll();
    }

    public void onNestedScroll(View view, int i2, int i3, int i4, int i5) {
        dispatchNestedScroll(i2, i3, i4, i5, this.s);
        int i6 = i5 + this.s[1];
        if (i6 < 0 && !canChildScrollUp()) {
            this.o += (float) Math.abs(i6);
            b(this.o);
        }
    }

    public void setNestedScrollingEnabled(boolean z2) {
        this.q.setNestedScrollingEnabled(z2);
    }

    public boolean isNestedScrollingEnabled() {
        return this.q.isNestedScrollingEnabled();
    }

    public boolean startNestedScroll(int i2) {
        return this.q.startNestedScroll(i2);
    }

    public void stopNestedScroll() {
        this.q.stopNestedScroll();
    }

    public boolean hasNestedScrollingParent() {
        return this.q.hasNestedScrollingParent();
    }

    public boolean dispatchNestedScroll(int i2, int i3, int i4, int i5, int[] iArr) {
        return this.q.dispatchNestedScroll(i2, i3, i4, i5, iArr);
    }

    public boolean dispatchNestedPreScroll(int i2, int i3, int[] iArr, int[] iArr2) {
        return this.q.dispatchNestedPreScroll(i2, i3, iArr, iArr2);
    }

    public boolean onNestedPreFling(View view, float f2, float f3) {
        return dispatchNestedPreFling(f2, f3);
    }

    public boolean onNestedFling(View view, float f2, float f3, boolean z2) {
        return dispatchNestedFling(f2, f3, z2);
    }

    public boolean dispatchNestedFling(float f2, float f3, boolean z2) {
        return this.q.dispatchNestedFling(f2, f3, z2);
    }

    public boolean dispatchNestedPreFling(float f2, float f3) {
        return this.q.dispatchNestedPreFling(f2, f3);
    }

    private boolean a(Animation animation) {
        return animation != null && animation.hasStarted() && !animation.hasEnded();
    }

    private void b(float f2) {
        this.h.setArrowEnabled(true);
        float min = Math.min(1.0f, Math.abs(f2 / this.n));
        float max = (((float) Math.max(((double) min) - 0.4d, 0.0d)) * 5.0f) / 3.0f;
        float f3 = (float) (this.j ? this.g - this.mOriginalOffsetTop : this.g);
        double max2 = (double) (Math.max(BitmapDescriptorFactory.HUE_RED, Math.min(Math.abs(f2) - this.n, f3 * 2.0f) / f3) / 4.0f);
        float pow = ((float) (max2 - Math.pow(max2, 2.0d))) * 2.0f;
        int i2 = this.mOriginalOffsetTop + ((int) ((f3 * min) + (f3 * pow * 2.0f)));
        if (this.e.getVisibility() != 0) {
            this.e.setVisibility(0);
        }
        if (!this.d) {
            this.e.setScaleX(1.0f);
            this.e.setScaleY(1.0f);
        }
        if (this.d) {
            setAnimationProgress(Math.min(1.0f, f2 / this.n));
        }
        if (f2 < this.n) {
            if (this.h.getAlpha() > 76 && !a(this.F)) {
                c();
            }
        } else if (this.h.getAlpha() < 255 && !a(this.G)) {
            d();
        }
        this.h.setStartEndTrim(BitmapDescriptorFactory.HUE_RED, Math.min(0.8f, max * 0.8f));
        this.h.setArrowScale(Math.min(1.0f, max));
        this.h.setProgressRotation((((max * 0.4f) - 16.0f) + (pow * 2.0f)) * 0.5f);
        setTargetOffsetTopAndBottom(i2 - this.c);
    }

    private void c(float f2) {
        if (f2 > this.n) {
            a(true, true);
            return;
        }
        this.b = false;
        this.h.setStartEndTrim(BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED);
        AnonymousClass5 r0 = null;
        if (!this.d) {
            r0 = new AnimationListener() {
                public void onAnimationRepeat(Animation animation) {
                }

                public void onAnimationStart(Animation animation) {
                }

                public void onAnimationEnd(Animation animation) {
                    if (!SwipeRefreshLayout.this.d) {
                        SwipeRefreshLayout.this.a((AnimationListener) null);
                    }
                }
            };
        }
        b(this.c, r0);
        this.h.setArrowEnabled(false);
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        int actionMasked = motionEvent.getActionMasked();
        if (this.z && actionMasked == 0) {
            this.z = false;
        }
        if (!isEnabled() || this.z || canChildScrollUp() || this.b || this.t) {
            return false;
        }
        switch (actionMasked) {
            case 0:
                this.y = motionEvent.getPointerId(0);
                this.x = false;
                break;
            case 1:
                int findPointerIndex = motionEvent.findPointerIndex(this.y);
                if (findPointerIndex < 0) {
                    Log.e(k, "Got ACTION_UP event but don't have an active pointer id.");
                    return false;
                }
                if (this.x) {
                    float y2 = (motionEvent.getY(findPointerIndex) - this.v) * 0.5f;
                    this.x = false;
                    c(y2);
                }
                this.y = -1;
                return false;
            case 2:
                int findPointerIndex2 = motionEvent.findPointerIndex(this.y);
                if (findPointerIndex2 < 0) {
                    Log.e(k, "Got ACTION_MOVE event but have an invalid active pointer id.");
                    return false;
                }
                float y3 = motionEvent.getY(findPointerIndex2);
                d(y3);
                if (this.x) {
                    float f2 = (y3 - this.v) * 0.5f;
                    if (f2 > BitmapDescriptorFactory.HUE_RED) {
                        b(f2);
                        break;
                    } else {
                        return false;
                    }
                }
                break;
            case 3:
                return false;
            case 5:
                int actionIndex = motionEvent.getActionIndex();
                if (actionIndex >= 0) {
                    this.y = motionEvent.getPointerId(actionIndex);
                    break;
                } else {
                    Log.e(k, "Got ACTION_POINTER_DOWN event but have an invalid action index.");
                    return false;
                }
            case 6:
                a(motionEvent);
                break;
        }
        return true;
    }

    private void d(float f2) {
        if (f2 - this.w > ((float) this.m) && !this.x) {
            this.v = this.w + ((float) this.m);
            this.x = true;
            this.h.setAlpha(76);
        }
    }

    private void a(int i2, AnimationListener animationListener) {
        this.mFrom = i2;
        this.L.reset();
        this.L.setDuration(200);
        this.L.setInterpolator(this.A);
        if (animationListener != null) {
            this.e.a(animationListener);
        }
        this.e.clearAnimation();
        this.e.startAnimation(this.L);
    }

    private void b(int i2, AnimationListener animationListener) {
        if (this.d) {
            c(i2, animationListener);
            return;
        }
        this.mFrom = i2;
        this.M.reset();
        this.M.setDuration(200);
        this.M.setInterpolator(this.A);
        if (animationListener != null) {
            this.e.a(animationListener);
        }
        this.e.clearAnimation();
        this.e.startAnimation(this.M);
    }

    /* access modifiers changed from: 0000 */
    public void a(float f2) {
        setTargetOffsetTopAndBottom((this.mFrom + ((int) (((float) (this.mOriginalOffsetTop - this.mFrom)) * f2))) - this.e.getTop());
    }

    private void c(int i2, AnimationListener animationListener) {
        this.mFrom = i2;
        this.f = this.e.getScaleX();
        this.H = new Animation() {
            public void applyTransformation(float f, Transformation transformation) {
                SwipeRefreshLayout.this.setAnimationProgress(SwipeRefreshLayout.this.f + ((-SwipeRefreshLayout.this.f) * f));
                SwipeRefreshLayout.this.a(f);
            }
        };
        this.H.setDuration(150);
        if (animationListener != null) {
            this.e.a(animationListener);
        }
        this.e.clearAnimation();
        this.e.startAnimation(this.H);
    }

    /* access modifiers changed from: 0000 */
    public void setTargetOffsetTopAndBottom(int i2) {
        this.e.bringToFront();
        ViewCompat.offsetTopAndBottom(this.e, i2);
        this.c = this.e.getTop();
    }

    private void a(MotionEvent motionEvent) {
        int actionIndex = motionEvent.getActionIndex();
        if (motionEvent.getPointerId(actionIndex) == this.y) {
            this.y = motionEvent.getPointerId(actionIndex == 0 ? 1 : 0);
        }
    }
}
