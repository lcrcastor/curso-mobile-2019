package android.support.v7.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView.ItemDecoration;
import android.support.v7.widget.RecyclerView.OnItemTouchListener;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.support.v7.widget.RecyclerView.State;
import android.view.MotionEvent;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import cz.msebera.android.httpclient.HttpStatus;

@VisibleForTesting
class FastScroller extends ItemDecoration implements OnItemTouchListener {
    private static final int[] g = {16842919};
    private static final int[] h = new int[0];
    private final int[] A = new int[2];
    /* access modifiers changed from: private */
    public final ValueAnimator B = ValueAnimator.ofFloat(new float[]{BitmapDescriptorFactory.HUE_RED, 1.0f});
    /* access modifiers changed from: private */
    public int C = 0;
    private final Runnable D = new Runnable() {
        public void run() {
            FastScroller.this.a((int) HttpStatus.SC_INTERNAL_SERVER_ERROR);
        }
    };
    private final OnScrollListener E = new OnScrollListener() {
        public void onScrolled(RecyclerView recyclerView, int i, int i2) {
            FastScroller.this.a(recyclerView.computeHorizontalScrollOffset(), recyclerView.computeVerticalScrollOffset());
        }
    };
    @VisibleForTesting
    int a;
    @VisibleForTesting
    int b;
    @VisibleForTesting
    float c;
    @VisibleForTesting
    int d;
    @VisibleForTesting
    int e;
    @VisibleForTesting
    float f;
    private final int i;
    private final int j;
    /* access modifiers changed from: private */
    public final StateListDrawable k;
    /* access modifiers changed from: private */
    public final Drawable l;
    private final int m;
    private final int n;
    private final StateListDrawable o;
    private final Drawable p;
    private final int q;
    private final int r;
    private int s = 0;
    private int t = 0;
    private RecyclerView u;
    private boolean v = false;
    private boolean w = false;
    private int x = 0;
    private int y = 0;
    private final int[] z = new int[2];

    class AnimatorListener extends AnimatorListenerAdapter {
        private boolean b;

        private AnimatorListener() {
            this.b = false;
        }

        public void onAnimationEnd(Animator animator) {
            if (this.b) {
                this.b = false;
                return;
            }
            if (((Float) FastScroller.this.B.getAnimatedValue()).floatValue() == BitmapDescriptorFactory.HUE_RED) {
                FastScroller.this.C = 0;
                FastScroller.this.b(0);
            } else {
                FastScroller.this.C = 2;
                FastScroller.this.d();
            }
        }

        public void onAnimationCancel(Animator animator) {
            this.b = true;
        }
    }

    class AnimatorUpdater implements AnimatorUpdateListener {
        private AnimatorUpdater() {
        }

        public void onAnimationUpdate(ValueAnimator valueAnimator) {
            int floatValue = (int) (((Float) valueAnimator.getAnimatedValue()).floatValue() * 255.0f);
            FastScroller.this.k.setAlpha(floatValue);
            FastScroller.this.l.setAlpha(floatValue);
            FastScroller.this.d();
        }
    }

    public void onRequestDisallowInterceptTouchEvent(boolean z2) {
    }

    FastScroller(RecyclerView recyclerView, StateListDrawable stateListDrawable, Drawable drawable, StateListDrawable stateListDrawable2, Drawable drawable2, int i2, int i3, int i4) {
        this.k = stateListDrawable;
        this.l = drawable;
        this.o = stateListDrawable2;
        this.p = drawable2;
        this.m = Math.max(i2, stateListDrawable.getIntrinsicWidth());
        this.n = Math.max(i2, drawable.getIntrinsicWidth());
        this.q = Math.max(i2, stateListDrawable2.getIntrinsicWidth());
        this.r = Math.max(i2, drawable2.getIntrinsicWidth());
        this.i = i3;
        this.j = i4;
        this.k.setAlpha(255);
        this.l.setAlpha(255);
        this.B.addListener(new AnimatorListener());
        this.B.addUpdateListener(new AnimatorUpdater());
        a(recyclerView);
    }

    public void a(@Nullable RecyclerView recyclerView) {
        if (this.u != recyclerView) {
            if (this.u != null) {
                c();
            }
            this.u = recyclerView;
            if (this.u != null) {
                b();
            }
        }
    }

    private void b() {
        this.u.addItemDecoration(this);
        this.u.addOnItemTouchListener(this);
        this.u.addOnScrollListener(this.E);
    }

    private void c() {
        this.u.removeItemDecoration(this);
        this.u.removeOnItemTouchListener(this);
        this.u.removeOnScrollListener(this.E);
        f();
    }

    /* access modifiers changed from: private */
    public void d() {
        this.u.invalidate();
    }

    /* access modifiers changed from: private */
    public void b(int i2) {
        if (i2 == 2 && this.x != 2) {
            this.k.setState(g);
            f();
        }
        if (i2 == 0) {
            d();
        } else {
            a();
        }
        if (this.x == 2 && i2 != 2) {
            this.k.setState(h);
            c(1200);
        } else if (i2 == 1) {
            c((int) ConnectionResult.DRIVE_EXTERNAL_STORAGE_REQUIRED);
        }
        this.x = i2;
    }

    private boolean e() {
        return ViewCompat.getLayoutDirection(this.u) == 1;
    }

    public void a() {
        int i2 = this.C;
        if (i2 != 0) {
            if (i2 == 3) {
                this.B.cancel();
            } else {
                return;
            }
        }
        this.C = 1;
        this.B.setFloatValues(new float[]{((Float) this.B.getAnimatedValue()).floatValue(), 1.0f});
        this.B.setDuration(500);
        this.B.setStartDelay(0);
        this.B.start();
    }

    /* access modifiers changed from: 0000 */
    @VisibleForTesting
    public void a(int i2) {
        switch (this.C) {
            case 1:
                this.B.cancel();
                break;
            case 2:
                break;
            default:
                return;
        }
        this.C = 3;
        this.B.setFloatValues(new float[]{((Float) this.B.getAnimatedValue()).floatValue(), 0.0f});
        this.B.setDuration((long) i2);
        this.B.start();
    }

    private void f() {
        this.u.removeCallbacks(this.D);
    }

    private void c(int i2) {
        f();
        this.u.postDelayed(this.D, (long) i2);
    }

    public void onDrawOver(Canvas canvas, RecyclerView recyclerView, State state) {
        if (this.s == this.u.getWidth() && this.t == this.u.getHeight()) {
            if (this.C != 0) {
                if (this.v) {
                    a(canvas);
                }
                if (this.w) {
                    b(canvas);
                }
            }
            return;
        }
        this.s = this.u.getWidth();
        this.t = this.u.getHeight();
        b(0);
    }

    private void a(Canvas canvas) {
        int i2 = this.s - this.m;
        int i3 = this.b - (this.a / 2);
        this.k.setBounds(0, 0, this.m, this.a);
        this.l.setBounds(0, 0, this.n, this.t);
        if (e()) {
            this.l.draw(canvas);
            canvas.translate((float) this.m, (float) i3);
            canvas.scale(-1.0f, 1.0f);
            this.k.draw(canvas);
            canvas.scale(1.0f, 1.0f);
            canvas.translate((float) (-this.m), (float) (-i3));
            return;
        }
        canvas.translate((float) i2, BitmapDescriptorFactory.HUE_RED);
        this.l.draw(canvas);
        canvas.translate(BitmapDescriptorFactory.HUE_RED, (float) i3);
        this.k.draw(canvas);
        canvas.translate((float) (-i2), (float) (-i3));
    }

    private void b(Canvas canvas) {
        int i2 = this.t - this.q;
        int i3 = this.e - (this.d / 2);
        this.o.setBounds(0, 0, this.d, this.q);
        this.p.setBounds(0, 0, this.s, this.r);
        canvas.translate(BitmapDescriptorFactory.HUE_RED, (float) i2);
        this.p.draw(canvas);
        canvas.translate((float) i3, BitmapDescriptorFactory.HUE_RED);
        this.o.draw(canvas);
        canvas.translate((float) (-i3), (float) (-i2));
    }

    /* access modifiers changed from: 0000 */
    public void a(int i2, int i3) {
        int computeVerticalScrollRange = this.u.computeVerticalScrollRange();
        int i4 = this.t;
        this.v = computeVerticalScrollRange - i4 > 0 && this.t >= this.i;
        int computeHorizontalScrollRange = this.u.computeHorizontalScrollRange();
        int i5 = this.s;
        this.w = computeHorizontalScrollRange - i5 > 0 && this.s >= this.i;
        if (this.v || this.w) {
            if (this.v) {
                float f2 = (float) i4;
                this.b = (int) ((f2 * (((float) i3) + (f2 / 2.0f))) / ((float) computeVerticalScrollRange));
                this.a = Math.min(i4, (i4 * i4) / computeVerticalScrollRange);
            }
            if (this.w) {
                float f3 = (float) i5;
                this.e = (int) ((f3 * (((float) i2) + (f3 / 2.0f))) / ((float) computeHorizontalScrollRange));
                this.d = Math.min(i5, (i5 * i5) / computeHorizontalScrollRange);
            }
            if (this.x == 0 || this.x == 1) {
                b(1);
            }
            return;
        }
        if (this.x != 0) {
            b(0);
        }
    }

    public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
        if (this.x == 1) {
            boolean a2 = a(motionEvent.getX(), motionEvent.getY());
            boolean b2 = b(motionEvent.getX(), motionEvent.getY());
            if (motionEvent.getAction() != 0) {
                return false;
            }
            if (!a2 && !b2) {
                return false;
            }
            if (b2) {
                this.y = 1;
                this.f = (float) ((int) motionEvent.getX());
            } else if (a2) {
                this.y = 2;
                this.c = (float) ((int) motionEvent.getY());
            }
            b(2);
        } else if (this.x != 2) {
            return false;
        }
        return true;
    }

    public void onTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
        if (this.x != 0) {
            if (motionEvent.getAction() == 0) {
                boolean a2 = a(motionEvent.getX(), motionEvent.getY());
                boolean b2 = b(motionEvent.getX(), motionEvent.getY());
                if (a2 || b2) {
                    if (b2) {
                        this.y = 1;
                        this.f = (float) ((int) motionEvent.getX());
                    } else if (a2) {
                        this.y = 2;
                        this.c = (float) ((int) motionEvent.getY());
                    }
                    b(2);
                }
            } else if (motionEvent.getAction() == 1 && this.x == 2) {
                this.c = BitmapDescriptorFactory.HUE_RED;
                this.f = BitmapDescriptorFactory.HUE_RED;
                b(1);
                this.y = 0;
            } else if (motionEvent.getAction() == 2 && this.x == 2) {
                a();
                if (this.y == 1) {
                    b(motionEvent.getX());
                }
                if (this.y == 2) {
                    a(motionEvent.getY());
                }
            }
        }
    }

    private void a(float f2) {
        int[] g2 = g();
        float max = Math.max((float) g2[0], Math.min((float) g2[1], f2));
        if (Math.abs(((float) this.b) - max) >= 2.0f) {
            int a2 = a(this.c, max, g2, this.u.computeVerticalScrollRange(), this.u.computeVerticalScrollOffset(), this.t);
            if (a2 != 0) {
                this.u.scrollBy(0, a2);
            }
            this.c = max;
        }
    }

    private void b(float f2) {
        int[] h2 = h();
        float max = Math.max((float) h2[0], Math.min((float) h2[1], f2));
        if (Math.abs(((float) this.e) - max) >= 2.0f) {
            int a2 = a(this.f, max, h2, this.u.computeHorizontalScrollRange(), this.u.computeHorizontalScrollOffset(), this.s);
            if (a2 != 0) {
                this.u.scrollBy(a2, 0);
            }
            this.f = max;
        }
    }

    private int a(float f2, float f3, int[] iArr, int i2, int i3, int i4) {
        int i5 = iArr[1] - iArr[0];
        if (i5 == 0) {
            return 0;
        }
        int i6 = i2 - i4;
        int i7 = (int) (((f3 - f2) / ((float) i5)) * ((float) i6));
        int i8 = i3 + i7;
        if (i8 >= i6 || i8 < 0) {
            return 0;
        }
        return i7;
    }

    /* access modifiers changed from: 0000 */
    @VisibleForTesting
    public boolean a(float f2, float f3) {
        if (!e() ? f2 >= ((float) (this.s - this.m)) : f2 <= ((float) (this.m / 2))) {
            if (f3 >= ((float) (this.b - (this.a / 2))) && f3 <= ((float) (this.b + (this.a / 2)))) {
                return true;
            }
        }
        return false;
    }

    /* access modifiers changed from: 0000 */
    @VisibleForTesting
    public boolean b(float f2, float f3) {
        return f3 >= ((float) (this.t - this.q)) && f2 >= ((float) (this.e - (this.d / 2))) && f2 <= ((float) (this.e + (this.d / 2)));
    }

    private int[] g() {
        this.z[0] = this.j;
        this.z[1] = this.t - this.j;
        return this.z;
    }

    private int[] h() {
        this.A[0] = this.j;
        this.A[1] = this.s - this.j;
        return this.A;
    }
}
