package ar.com.santander.rio.mbanking.components;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.VelocityTrackerCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ScrollerCompat;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import io.fabric.sdk.android.services.settings.SettingsJsonConstants;
import java.util.Arrays;

public class ViewDragHelper {
    public static final int DIRECTION_ALL = 3;
    public static final int DIRECTION_HORIZONTAL = 1;
    public static final int DIRECTION_VERTICAL = 2;
    public static final int EDGE_ALL = 15;
    public static final int EDGE_BOTTOM = 8;
    public static final int EDGE_LEFT = 1;
    public static final int EDGE_RIGHT = 2;
    public static final int EDGE_TOP = 4;
    public static final int INVALID_POINTER = -1;
    public static final int STATE_DRAGGING = 1;
    public static final int STATE_IDLE = 0;
    public static final int STATE_SETTLING = 2;
    private static final Interpolator v = new Interpolator() {
        public float getInterpolation(float f) {
            float f2 = f - 1.0f;
            return (f2 * f2 * f2 * f2 * f2) + 1.0f;
        }
    };
    private int a;
    private int b;
    private int c = -1;
    private float[] d;
    private float[] e;
    private float[] f;
    private float[] g;
    private int[] h;
    private int[] i;
    private int[] j;
    private int k;
    private VelocityTracker l;
    private float m;
    private float n;
    private int o;
    private int p;
    private ScrollerCompat q;
    private final Callback r;
    private View s;
    private boolean t;
    private final ViewGroup u;
    private final Runnable w = new Runnable() {
        public void run() {
            ViewDragHelper.this.a(0);
        }
    };

    public static abstract class Callback {
        public int clampViewPositionHorizontal(View view, int i, int i2) {
            return 0;
        }

        public int clampViewPositionVertical(View view, int i, int i2) {
            return 0;
        }

        public int getOrderedChildIndex(int i) {
            return i;
        }

        public int getViewHorizontalDragRange(View view) {
            return 0;
        }

        public int getViewVerticalDragRange(View view) {
            return 0;
        }

        public void onEdgeDragStarted(int i, int i2) {
        }

        public boolean onEdgeLock(int i) {
            return false;
        }

        public void onEdgeTouched(int i, int i2) {
        }

        public void onViewCaptured(View view, int i) {
        }

        public void onViewDragStateChanged(int i) {
        }

        public void onViewPositionChanged(View view, int i, int i2, int i3, int i4) {
        }

        public void onViewReleased(View view, float f, float f2) {
        }

        public abstract boolean tryCaptureView(View view, int i);
    }

    public static ViewDragHelper create(ViewGroup viewGroup, Callback callback) {
        return new ViewDragHelper(viewGroup.getContext(), viewGroup, callback);
    }

    public static ViewDragHelper create(ViewGroup viewGroup, float f2, Callback callback) {
        ViewDragHelper create = create(viewGroup, callback);
        create.b = (int) (((float) create.b) * (1.0f / f2));
        return create;
    }

    private ViewDragHelper(Context context, ViewGroup viewGroup, Callback callback) {
        if (viewGroup == null) {
            throw new IllegalArgumentException("Parent view may not be null");
        } else if (callback == null) {
            throw new IllegalArgumentException("Callback may not be null");
        } else {
            this.u = viewGroup;
            this.r = callback;
            ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
            this.o = (int) ((context.getResources().getDisplayMetrics().density * 20.0f) + 0.5f);
            this.b = viewConfiguration.getScaledTouchSlop();
            this.m = (float) viewConfiguration.getScaledMaximumFlingVelocity();
            this.n = (float) viewConfiguration.getScaledMinimumFlingVelocity();
            this.q = ScrollerCompat.create(context, v);
        }
    }

    public void setMinVelocity(float f2) {
        this.n = f2;
    }

    public float getMinVelocity() {
        return this.n;
    }

    public int getViewDragState() {
        return this.a;
    }

    public void setEdgeTrackingEnabled(int i2) {
        this.p = i2;
    }

    public int getEdgeSize() {
        return this.o;
    }

    public void captureChildView(View view, int i2) {
        if (view.getParent() != this.u) {
            StringBuilder sb = new StringBuilder();
            sb.append("captureChildView: parameter must be a descendant of the ViewDragHelper's tracked parent view (");
            sb.append(this.u);
            sb.append(")");
            throw new IllegalArgumentException(sb.toString());
        }
        this.s = view;
        this.c = i2;
        this.r.onViewCaptured(view, i2);
        a(1);
    }

    public View getCapturedView() {
        return this.s;
    }

    public int getActivePointerId() {
        return this.c;
    }

    public int getTouchSlop() {
        return this.b;
    }

    public void cancel() {
        this.c = -1;
        a();
        if (this.l != null) {
            this.l.recycle();
            this.l = null;
        }
    }

    public void abort() {
        cancel();
        if (this.a == 2) {
            int currX = this.q.getCurrX();
            int currY = this.q.getCurrY();
            this.q.abortAnimation();
            int currX2 = this.q.getCurrX();
            int currY2 = this.q.getCurrY();
            this.r.onViewPositionChanged(this.s, currX2, currY2, currX2 - currX, currY2 - currY);
        }
        a(0);
    }

    public boolean smoothSlideViewTo(View view, int i2, int i3) {
        this.s = view;
        this.c = -1;
        return a(i2, i3, 0, 0);
    }

    public boolean settleCapturedViewAt(int i2, int i3) {
        if (this.t) {
            return a(i2, i3, (int) VelocityTrackerCompat.getXVelocity(this.l, this.c), (int) VelocityTrackerCompat.getYVelocity(this.l, this.c));
        }
        throw new IllegalStateException("Cannot settleCapturedViewAt outside of a call to Callback#onViewReleased");
    }

    private boolean a(int i2, int i3, int i4, int i5) {
        int left = this.s.getLeft();
        int top = this.s.getTop();
        int i6 = i2 - left;
        int i7 = i3 - top;
        if (i6 == 0 && i7 == 0) {
            this.q.abortAnimation();
            a(0);
            return false;
        }
        this.q.startScroll(left, top, i6, i7, a(this.s, i6, i7, i4, i5));
        a(2);
        return true;
    }

    private int a(View view, int i2, int i3, int i4, int i5) {
        float f2;
        float f3;
        float f4;
        float f5;
        int b2 = b(i4, (int) this.n, (int) this.m);
        int b3 = b(i5, (int) this.n, (int) this.m);
        int abs = Math.abs(i2);
        int abs2 = Math.abs(i3);
        int abs3 = Math.abs(b2);
        int abs4 = Math.abs(b3);
        int i6 = abs3 + abs4;
        int i7 = abs + abs2;
        if (b2 != 0) {
            f2 = (float) abs3;
            f3 = (float) i6;
        } else {
            f2 = (float) abs;
            f3 = (float) i7;
        }
        float f6 = f2 / f3;
        if (b3 != 0) {
            f4 = (float) abs4;
            f5 = (float) i6;
        } else {
            f4 = (float) abs2;
            f5 = (float) i7;
        }
        float f7 = f4 / f5;
        return (int) ((((float) a(i2, b2, this.r.getViewHorizontalDragRange(view))) * f6) + (((float) a(i3, b3, this.r.getViewVerticalDragRange(view))) * f7));
    }

    private int a(int i2, int i3, int i4) {
        int i5;
        if (i2 == 0) {
            return 0;
        }
        int width = this.u.getWidth();
        float f2 = (float) (width / 2);
        float a2 = f2 + (a(Math.min(1.0f, ((float) Math.abs(i2)) / ((float) width))) * f2);
        int abs = Math.abs(i3);
        if (abs > 0) {
            i5 = Math.round(Math.abs(a2 / ((float) abs)) * 1000.0f) * 4;
        } else {
            i5 = (int) (((((float) Math.abs(i2)) / ((float) i4)) + 1.0f) * 256.0f);
        }
        return Math.min(i5, SettingsJsonConstants.ANALYTICS_FLUSH_INTERVAL_SECS_DEFAULT);
    }

    private int b(int i2, int i3, int i4) {
        int abs = Math.abs(i2);
        if (abs < i3) {
            return 0;
        }
        if (abs <= i4) {
            return i2;
        }
        if (i2 <= 0) {
            i4 = -i4;
        }
        return i4;
    }

    private float a(float f2, float f3, float f4) {
        float abs = Math.abs(f2);
        if (abs < f3) {
            return BitmapDescriptorFactory.HUE_RED;
        }
        if (abs <= f4) {
            return f2;
        }
        if (f2 <= BitmapDescriptorFactory.HUE_RED) {
            f4 = -f4;
        }
        return f4;
    }

    private float a(float f2) {
        return (float) Math.sin((double) ((float) (((double) (f2 - 0.5f)) * 0.4712389167638204d)));
    }

    public void flingCapturedView(int i2, int i3, int i4, int i5) {
        if (!this.t) {
            throw new IllegalStateException("Cannot flingCapturedView outside of a call to Callback#onViewReleased");
        }
        this.q.fling(this.s.getLeft(), this.s.getTop(), (int) VelocityTrackerCompat.getXVelocity(this.l, this.c), (int) VelocityTrackerCompat.getYVelocity(this.l, this.c), i2, i4, i3, i5);
        a(2);
    }

    public boolean continueSettling(boolean z) {
        boolean z2 = false;
        if (this.s == null) {
            return false;
        }
        if (this.a == 2) {
            boolean computeScrollOffset = this.q.computeScrollOffset();
            int currX = this.q.getCurrX();
            int currY = this.q.getCurrY();
            int left = currX - this.s.getLeft();
            int top = currY - this.s.getTop();
            if (left != 0) {
                this.s.offsetLeftAndRight(left);
            }
            if (top != 0) {
                this.s.offsetTopAndBottom(top);
            }
            if (!(left == 0 && top == 0)) {
                this.r.onViewPositionChanged(this.s, currX, currY, left, top);
            }
            if (computeScrollOffset && currX == this.q.getFinalX() && currY == this.q.getFinalY()) {
                this.q.abortAnimation();
                computeScrollOffset = this.q.isFinished();
            }
            if (!computeScrollOffset) {
                if (z) {
                    this.u.post(this.w);
                } else {
                    a(0);
                }
            }
        }
        if (this.a == 2) {
            z2 = true;
        }
        return z2;
    }

    private void a(float f2, float f3) {
        this.t = true;
        this.r.onViewReleased(this.s, f2, f3);
        this.t = false;
        if (this.a == 1) {
            a(0);
        }
    }

    private void a() {
        if (this.d != null) {
            Arrays.fill(this.d, BitmapDescriptorFactory.HUE_RED);
            Arrays.fill(this.e, BitmapDescriptorFactory.HUE_RED);
            Arrays.fill(this.f, BitmapDescriptorFactory.HUE_RED);
            Arrays.fill(this.g, BitmapDescriptorFactory.HUE_RED);
            Arrays.fill(this.h, 0);
            Arrays.fill(this.i, 0);
            Arrays.fill(this.j, 0);
            this.k = 0;
        }
    }

    private void b(int i2) {
        if (this.d != null) {
            this.d[i2] = 0.0f;
            this.e[i2] = 0.0f;
            this.f[i2] = 0.0f;
            this.g[i2] = 0.0f;
            this.h[i2] = 0;
            this.i[i2] = 0;
            this.j[i2] = 0;
            this.k = ((1 << i2) ^ -1) & this.k;
        }
    }

    private void c(int i2) {
        if (this.d == null || this.d.length <= i2) {
            int i3 = i2 + 1;
            float[] fArr = new float[i3];
            float[] fArr2 = new float[i3];
            float[] fArr3 = new float[i3];
            float[] fArr4 = new float[i3];
            int[] iArr = new int[i3];
            int[] iArr2 = new int[i3];
            int[] iArr3 = new int[i3];
            if (this.d != null) {
                System.arraycopy(this.d, 0, fArr, 0, this.d.length);
                System.arraycopy(this.e, 0, fArr2, 0, this.e.length);
                System.arraycopy(this.f, 0, fArr3, 0, this.f.length);
                System.arraycopy(this.g, 0, fArr4, 0, this.g.length);
                System.arraycopy(this.h, 0, iArr, 0, this.h.length);
                System.arraycopy(this.i, 0, iArr2, 0, this.i.length);
                System.arraycopy(this.j, 0, iArr3, 0, this.j.length);
            }
            this.d = fArr;
            this.e = fArr2;
            this.f = fArr3;
            this.g = fArr4;
            this.h = iArr;
            this.i = iArr2;
            this.j = iArr3;
        }
    }

    private void a(float f2, float f3, int i2) {
        c(i2);
        float[] fArr = this.d;
        this.f[i2] = f2;
        fArr[i2] = f2;
        float[] fArr2 = this.e;
        this.g[i2] = f3;
        fArr2[i2] = f3;
        this.h[i2] = a((int) f2, (int) f3);
        this.k |= 1 << i2;
    }

    private void a(MotionEvent motionEvent) {
        int pointerCount = MotionEventCompat.getPointerCount(motionEvent);
        for (int i2 = 0; i2 < pointerCount; i2++) {
            int pointerId = MotionEventCompat.getPointerId(motionEvent, i2);
            float x = MotionEventCompat.getX(motionEvent, i2);
            float y = MotionEventCompat.getY(motionEvent, i2);
            if (!(this.f == null || this.g == null)) {
                this.f[pointerId] = x;
                this.g[pointerId] = y;
            }
        }
    }

    public boolean isPointerDown(int i2) {
        return ((1 << i2) & this.k) != 0;
    }

    /* access modifiers changed from: 0000 */
    public void a(int i2) {
        if (this.a != i2) {
            this.a = i2;
            this.r.onViewDragStateChanged(i2);
            if (i2 == 0) {
                this.s = null;
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public boolean a(View view, int i2) {
        if (view == this.s && this.c == i2) {
            return true;
        }
        if (view == null || !this.r.tryCaptureView(view, i2)) {
            return false;
        }
        this.c = i2;
        captureChildView(view, i2);
        return true;
    }

    /* access modifiers changed from: protected */
    public boolean canScroll(View view, boolean z, int i2, int i3, int i4, int i5) {
        View view2 = view;
        boolean z2 = true;
        if (view2 instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view2;
            int scrollX = view2.getScrollX();
            int scrollY = view2.getScrollY();
            for (int childCount = viewGroup.getChildCount() - 1; childCount >= 0; childCount--) {
                View childAt = viewGroup.getChildAt(childCount);
                int i6 = i4 + scrollX;
                if (i6 >= childAt.getLeft() && i6 < childAt.getRight()) {
                    int i7 = i5 + scrollY;
                    if (i7 >= childAt.getTop() && i7 < childAt.getBottom()) {
                        if (canScroll(childAt, true, i2, i3, i6 - childAt.getLeft(), i7 - childAt.getTop())) {
                            return true;
                        }
                    }
                }
            }
        }
        if (!z || (!ViewCompat.canScrollHorizontally(view2, -i2) && !ViewCompat.canScrollVertically(view2, -i3))) {
            z2 = false;
        }
        return z2;
    }

    public boolean shouldInterceptTouchEvent(MotionEvent motionEvent) {
        int actionMasked = MotionEventCompat.getActionMasked(motionEvent);
        int actionIndex = MotionEventCompat.getActionIndex(motionEvent);
        if (actionMasked == 0) {
            cancel();
        }
        if (this.l == null) {
            this.l = VelocityTracker.obtain();
        }
        this.l.addMovement(motionEvent);
        switch (actionMasked) {
            case 0:
                float x = motionEvent.getX();
                float y = motionEvent.getY();
                int pointerId = MotionEventCompat.getPointerId(motionEvent, 0);
                a(x, y, pointerId);
                View findTopChildUnder = findTopChildUnder((int) x, (int) y);
                if (findTopChildUnder == this.s && this.a == 2) {
                    a(findTopChildUnder, pointerId);
                }
                int i2 = this.h[pointerId];
                if ((this.p & i2) != 0) {
                    this.r.onEdgeTouched(i2 & this.p, pointerId);
                    break;
                }
                break;
            case 1:
            case 3:
                cancel();
                break;
            case 2:
                int pointerCount = MotionEventCompat.getPointerCount(motionEvent);
                for (int i3 = 0; i3 < pointerCount && this.d != null && this.e != null; i3++) {
                    int pointerId2 = MotionEventCompat.getPointerId(motionEvent, i3);
                    if (pointerId2 < this.d.length && pointerId2 < this.e.length) {
                        float x2 = MotionEventCompat.getX(motionEvent, i3);
                        float f2 = x2 - this.d[pointerId2];
                        float y2 = MotionEventCompat.getY(motionEvent, i3) - this.e[pointerId2];
                        b(f2, y2, pointerId2);
                        if (this.a != 1) {
                            View findTopChildUnder2 = findTopChildUnder((int) this.d[pointerId2], (int) this.e[pointerId2]);
                            if (findTopChildUnder2 != null && a(findTopChildUnder2, f2, y2) && a(findTopChildUnder2, pointerId2)) {
                            }
                        }
                    }
                }
                a(motionEvent);
                break;
            case 5:
                int pointerId3 = MotionEventCompat.getPointerId(motionEvent, actionIndex);
                float x3 = MotionEventCompat.getX(motionEvent, actionIndex);
                float y3 = MotionEventCompat.getY(motionEvent, actionIndex);
                a(x3, y3, pointerId3);
                if (this.a != 0) {
                    if (this.a == 2) {
                        View findTopChildUnder3 = findTopChildUnder((int) x3, (int) y3);
                        if (findTopChildUnder3 == this.s) {
                            a(findTopChildUnder3, pointerId3);
                            break;
                        }
                    }
                } else {
                    int i4 = this.h[pointerId3];
                    if ((this.p & i4) != 0) {
                        this.r.onEdgeTouched(i4 & this.p, pointerId3);
                        break;
                    }
                }
                break;
            case 6:
                b(MotionEventCompat.getPointerId(motionEvent, actionIndex));
                break;
        }
        if (this.a == 1) {
            return true;
        }
        return false;
    }

    public void processTouchEvent(MotionEvent motionEvent) {
        int i2;
        int actionMasked = MotionEventCompat.getActionMasked(motionEvent);
        int actionIndex = MotionEventCompat.getActionIndex(motionEvent);
        if (actionMasked == 0) {
            cancel();
        }
        if (this.l == null) {
            this.l = VelocityTracker.obtain();
        }
        this.l.addMovement(motionEvent);
        int i3 = 0;
        switch (actionMasked) {
            case 0:
                float x = motionEvent.getX();
                float y = motionEvent.getY();
                int pointerId = MotionEventCompat.getPointerId(motionEvent, 0);
                View findTopChildUnder = findTopChildUnder((int) x, (int) y);
                a(x, y, pointerId);
                a(findTopChildUnder, pointerId);
                int i4 = this.h[pointerId];
                if ((this.p & i4) != 0) {
                    this.r.onEdgeTouched(i4 & this.p, pointerId);
                    return;
                }
                return;
            case 1:
                if (this.a == 1) {
                    b();
                }
                cancel();
                return;
            case 2:
                if (this.a == 1) {
                    int findPointerIndex = MotionEventCompat.findPointerIndex(motionEvent, this.c);
                    if (findPointerIndex != -1) {
                        float x2 = MotionEventCompat.getX(motionEvent, findPointerIndex);
                        int i5 = (int) (x2 - this.f[this.c]);
                        int y2 = (int) (MotionEventCompat.getY(motionEvent, findPointerIndex) - this.g[this.c]);
                        b(this.s.getLeft() + i5, this.s.getTop() + y2, i5, y2);
                        a(motionEvent);
                        return;
                    }
                    return;
                }
                int pointerCount = MotionEventCompat.getPointerCount(motionEvent);
                while (i3 < pointerCount) {
                    int pointerId2 = MotionEventCompat.getPointerId(motionEvent, i3);
                    float x3 = MotionEventCompat.getX(motionEvent, i3);
                    float y3 = MotionEventCompat.getY(motionEvent, i3);
                    float f2 = x3 - this.d[pointerId2];
                    float f3 = y3 - this.e[pointerId2];
                    b(f2, f3, pointerId2);
                    if (this.a != 1) {
                        View findTopChildUnder2 = findTopChildUnder((int) x3, (int) y3);
                        if (!a(findTopChildUnder2, f2, f3) || !a(findTopChildUnder2, pointerId2)) {
                            i3++;
                        }
                    }
                    a(motionEvent);
                    return;
                }
                a(motionEvent);
                return;
            case 3:
                if (this.a == 1) {
                    a((float) BitmapDescriptorFactory.HUE_RED, (float) BitmapDescriptorFactory.HUE_RED);
                }
                cancel();
                return;
            case 5:
                int pointerId3 = MotionEventCompat.getPointerId(motionEvent, actionIndex);
                float x4 = MotionEventCompat.getX(motionEvent, actionIndex);
                float y4 = MotionEventCompat.getY(motionEvent, actionIndex);
                a(x4, y4, pointerId3);
                if (this.a == 0) {
                    a(findTopChildUnder((int) x4, (int) y4), pointerId3);
                    int i6 = this.h[pointerId3];
                    if ((this.p & i6) != 0) {
                        this.r.onEdgeTouched(i6 & this.p, pointerId3);
                        return;
                    }
                    return;
                } else if (isCapturedViewUnder((int) x4, (int) y4)) {
                    a(this.s, pointerId3);
                    return;
                } else {
                    return;
                }
            case 6:
                int pointerId4 = MotionEventCompat.getPointerId(motionEvent, actionIndex);
                if (this.a == 1 && pointerId4 == this.c) {
                    int pointerCount2 = MotionEventCompat.getPointerCount(motionEvent);
                    while (true) {
                        if (i3 < pointerCount2) {
                            int pointerId5 = MotionEventCompat.getPointerId(motionEvent, i3);
                            if (pointerId5 != this.c) {
                                if (findTopChildUnder((int) MotionEventCompat.getX(motionEvent, i3), (int) MotionEventCompat.getY(motionEvent, i3)) == this.s && a(this.s, pointerId5)) {
                                    i2 = this.c;
                                }
                            }
                            i3++;
                        } else {
                            i2 = -1;
                        }
                    }
                    if (i2 == -1) {
                        b();
                    }
                }
                b(pointerId4);
                return;
            default:
                return;
        }
    }

    private void b(float f2, float f3, int i2) {
        int i3 = 1;
        if (!a(f2, f3, i2, 1)) {
            i3 = 0;
        }
        if (a(f3, f2, i2, 4)) {
            i3 |= 4;
        }
        if (a(f2, f3, i2, 2)) {
            i3 |= 2;
        }
        if (a(f3, f2, i2, 8)) {
            i3 |= 8;
        }
        if (i3 != 0) {
            int[] iArr = this.i;
            iArr[i2] = iArr[i2] | i3;
            this.r.onEdgeDragStarted(i3, i2);
        }
    }

    private boolean a(float f2, float f3, int i2, int i3) {
        float abs = Math.abs(f2);
        float abs2 = Math.abs(f3);
        boolean z = false;
        if ((this.h[i2] & i3) != i3 || (this.p & i3) == 0 || (this.j[i2] & i3) == i3 || (this.i[i2] & i3) == i3 || (abs <= ((float) this.b) && abs2 <= ((float) this.b))) {
            return false;
        }
        if (abs >= abs2 * 0.5f || !this.r.onEdgeLock(i3)) {
            if ((this.i[i2] & i3) == 0 && abs > ((float) this.b)) {
                z = true;
            }
            return z;
        }
        int[] iArr = this.j;
        iArr[i2] = iArr[i2] | i3;
        return false;
    }

    private boolean a(View view, float f2, float f3) {
        boolean z = false;
        if (view == null) {
            return false;
        }
        boolean z2 = this.r.getViewHorizontalDragRange(view) > 0;
        boolean z3 = this.r.getViewVerticalDragRange(view) > 0;
        if (z2 && z3) {
            if ((f2 * f2) + (f3 * f3) > ((float) (this.b * this.b))) {
                z = true;
            }
            return z;
        } else if (z2) {
            if (Math.abs(f2) > ((float) this.b)) {
                z = true;
            }
            return z;
        } else if (!z3) {
            return false;
        } else {
            if (Math.abs(f3) > ((float) this.b)) {
                z = true;
            }
            return z;
        }
    }

    public boolean checkTouchSlop(int i2) {
        int length = this.d.length;
        for (int i3 = 0; i3 < length; i3++) {
            if (checkTouchSlop(i2, i3)) {
                return true;
            }
        }
        return false;
    }

    public boolean checkTouchSlop(int i2, int i3) {
        boolean z = false;
        if (!isPointerDown(i3)) {
            return false;
        }
        boolean z2 = (i2 & 1) == 1;
        boolean z3 = (i2 & 2) == 2;
        float f2 = this.f[i3] - this.d[i3];
        float f3 = this.g[i3] - this.e[i3];
        if (z2 && z3) {
            if ((f2 * f2) + (f3 * f3) > ((float) (this.b * this.b))) {
                z = true;
            }
            return z;
        } else if (z2) {
            if (Math.abs(f2) > ((float) this.b)) {
                z = true;
            }
            return z;
        } else if (!z3) {
            return false;
        } else {
            if (Math.abs(f3) > ((float) this.b)) {
                z = true;
            }
            return z;
        }
    }

    public boolean isEdgeTouched(int i2) {
        int length = this.h.length;
        for (int i3 = 0; i3 < length; i3++) {
            if (isEdgeTouched(i2, i3)) {
                return true;
            }
        }
        return false;
    }

    public boolean isEdgeTouched(int i2, int i3) {
        return isPointerDown(i3) && (i2 & this.h[i3]) != 0;
    }

    private void b() {
        this.l.computeCurrentVelocity(1000, this.m);
        a(a(VelocityTrackerCompat.getXVelocity(this.l, this.c), this.n, this.m), a(VelocityTrackerCompat.getYVelocity(this.l, this.c), this.n, this.m));
    }

    private void b(int i2, int i3, int i4, int i5) {
        int left = this.s.getLeft();
        int top = this.s.getTop();
        if (i4 != 0) {
            i2 = this.r.clampViewPositionHorizontal(this.s, i2, i4);
            this.s.offsetLeftAndRight(i2 - left);
        }
        int i6 = i2;
        if (i5 != 0) {
            i3 = this.r.clampViewPositionVertical(this.s, i3, i5);
            this.s.offsetTopAndBottom(i3 - top);
        }
        int i7 = i3;
        if (i4 != 0 || i5 != 0) {
            this.r.onViewPositionChanged(this.s, i6, i7, i6 - left, i7 - top);
        }
    }

    public boolean isCapturedViewUnder(int i2, int i3) {
        return isViewUnder(this.s, i2, i3);
    }

    public boolean isViewUnder(View view, int i2, int i3) {
        boolean z = false;
        if (view == null) {
            return false;
        }
        if (i2 >= view.getLeft() && i2 < view.getRight() && i3 >= view.getTop() && i3 < view.getBottom()) {
            z = true;
        }
        return z;
    }

    public View findTopChildUnder(int i2, int i3) {
        for (int childCount = this.u.getChildCount() - 1; childCount >= 0; childCount--) {
            View childAt = this.u.getChildAt(this.r.getOrderedChildIndex(childCount));
            if (i2 >= childAt.getLeft() && i2 < childAt.getRight() && i3 >= childAt.getTop() && i3 < childAt.getBottom()) {
                return childAt;
            }
        }
        return null;
    }

    private int a(int i2, int i3) {
        int i4 = i2 < this.u.getLeft() + this.o ? 1 : 0;
        if (i3 < this.u.getTop() + this.o) {
            i4 |= 4;
        }
        if (i2 > this.u.getRight() - this.o) {
            i4 |= 2;
        }
        return i3 > this.u.getBottom() - this.o ? i4 | 8 : i4;
    }
}
