package android.support.design.widget;

import android.content.Context;
import android.support.v4.math.MathUtils;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.OverScroller;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;

abstract class HeaderBehavior<V extends View> extends ViewOffsetBehavior<V> {
    OverScroller a;
    private Runnable b;
    private boolean c;
    private int d = -1;
    private int e;
    private int f = -1;
    private VelocityTracker g;

    class FlingRunnable implements Runnable {
        private final CoordinatorLayout b;
        private final V c;

        FlingRunnable(CoordinatorLayout coordinatorLayout, V v) {
            this.b = coordinatorLayout;
            this.c = v;
        }

        public void run() {
            if (this.c != null && HeaderBehavior.this.a != null) {
                if (HeaderBehavior.this.a.computeScrollOffset()) {
                    HeaderBehavior.this.a(this.b, this.c, HeaderBehavior.this.a.getCurrY());
                    ViewCompat.postOnAnimation(this.c, this);
                    return;
                }
                HeaderBehavior.this.a(this.b, this.c);
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void a(CoordinatorLayout coordinatorLayout, V v) {
    }

    /* access modifiers changed from: 0000 */
    public boolean c(V v) {
        return false;
    }

    public HeaderBehavior() {
    }

    public HeaderBehavior(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public boolean onInterceptTouchEvent(CoordinatorLayout coordinatorLayout, V v, MotionEvent motionEvent) {
        if (this.f < 0) {
            this.f = ViewConfiguration.get(coordinatorLayout.getContext()).getScaledTouchSlop();
        }
        if (motionEvent.getAction() == 2 && this.c) {
            return true;
        }
        switch (motionEvent.getActionMasked()) {
            case 0:
                this.c = false;
                int x = (int) motionEvent.getX();
                int y = (int) motionEvent.getY();
                if (c(v) && coordinatorLayout.isPointInChildBounds(v, x, y)) {
                    this.e = y;
                    this.d = motionEvent.getPointerId(0);
                    b();
                    break;
                }
            case 1:
            case 3:
                this.c = false;
                this.d = -1;
                if (this.g != null) {
                    this.g.recycle();
                    this.g = null;
                    break;
                }
                break;
            case 2:
                int i = this.d;
                if (i != -1) {
                    int findPointerIndex = motionEvent.findPointerIndex(i);
                    if (findPointerIndex != -1) {
                        int y2 = (int) motionEvent.getY(findPointerIndex);
                        if (Math.abs(y2 - this.e) > this.f) {
                            this.c = true;
                            this.e = y2;
                            break;
                        }
                    }
                }
                break;
        }
        if (this.g != null) {
            this.g.addMovement(motionEvent);
        }
        return this.c;
    }

    public boolean onTouchEvent(CoordinatorLayout coordinatorLayout, V v, MotionEvent motionEvent) {
        if (this.f < 0) {
            this.f = ViewConfiguration.get(coordinatorLayout.getContext()).getScaledTouchSlop();
        }
        switch (motionEvent.getActionMasked()) {
            case 0:
                int y = (int) motionEvent.getY();
                if (coordinatorLayout.isPointInChildBounds(v, (int) motionEvent.getX(), y) && c(v)) {
                    this.e = y;
                    this.d = motionEvent.getPointerId(0);
                    b();
                    break;
                } else {
                    return false;
                }
            case 1:
                if (this.g != null) {
                    this.g.addMovement(motionEvent);
                    this.g.computeCurrentVelocity(1000);
                    a(coordinatorLayout, v, -a(v), 0, this.g.getYVelocity(this.d));
                    break;
                }
                break;
            case 2:
                int findPointerIndex = motionEvent.findPointerIndex(this.d);
                if (findPointerIndex != -1) {
                    int y2 = (int) motionEvent.getY(findPointerIndex);
                    int i = this.e - y2;
                    if (!this.c && Math.abs(i) > this.f) {
                        this.c = true;
                        i = i > 0 ? i - this.f : i + this.f;
                    }
                    int i2 = i;
                    if (this.c) {
                        this.e = y2;
                        b(coordinatorLayout, v, i2, b(v), 0);
                        break;
                    }
                } else {
                    return false;
                }
                break;
            case 3:
                break;
        }
        this.c = false;
        this.d = -1;
        if (this.g != null) {
            this.g.recycle();
            this.g = null;
        }
        if (this.g != null) {
            this.g.addMovement(motionEvent);
        }
        return true;
    }

    /* access modifiers changed from: 0000 */
    public int a(CoordinatorLayout coordinatorLayout, V v, int i) {
        return a(coordinatorLayout, v, i, Integer.MIN_VALUE, (int) SubsamplingScaleImageView.TILE_SIZE_AUTO);
    }

    /* access modifiers changed from: 0000 */
    public int a(CoordinatorLayout coordinatorLayout, V v, int i, int i2, int i3) {
        int topAndBottomOffset = getTopAndBottomOffset();
        if (i2 != 0 && topAndBottomOffset >= i2 && topAndBottomOffset <= i3) {
            int clamp = MathUtils.clamp(i, i2, i3);
            if (topAndBottomOffset != clamp) {
                setTopAndBottomOffset(clamp);
                return topAndBottomOffset - clamp;
            }
        }
        return 0;
    }

    /* access modifiers changed from: 0000 */
    public int a() {
        return getTopAndBottomOffset();
    }

    /* access modifiers changed from: 0000 */
    public final int b(CoordinatorLayout coordinatorLayout, V v, int i, int i2, int i3) {
        return a(coordinatorLayout, v, a() - i, i2, i3);
    }

    /* access modifiers changed from: 0000 */
    public final boolean a(CoordinatorLayout coordinatorLayout, V v, int i, int i2, float f2) {
        V v2 = v;
        if (this.b != null) {
            v2.removeCallbacks(this.b);
            this.b = null;
        }
        if (this.a == null) {
            this.a = new OverScroller(v2.getContext());
        }
        this.a.fling(0, getTopAndBottomOffset(), 0, Math.round(f2), 0, 0, i, i2);
        if (this.a.computeScrollOffset()) {
            this.b = new FlingRunnable(coordinatorLayout, v2);
            ViewCompat.postOnAnimation(v2, this.b);
            return true;
        }
        a(coordinatorLayout, v2);
        return false;
    }

    /* access modifiers changed from: 0000 */
    public int b(V v) {
        return -v.getHeight();
    }

    /* access modifiers changed from: 0000 */
    public int a(V v) {
        return v.getHeight();
    }

    private void b() {
        if (this.g == null) {
            this.g = VelocityTracker.obtain();
        }
    }
}
