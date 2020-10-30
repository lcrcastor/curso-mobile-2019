package android.support.v4.widget;

import android.content.res.Resources;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewConfiguration;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import cz.msebera.android.httpclient.HttpStatus;

public abstract class AutoScrollHelper implements OnTouchListener {
    public static final int EDGE_TYPE_INSIDE = 0;
    public static final int EDGE_TYPE_INSIDE_EXTEND = 1;
    public static final int EDGE_TYPE_OUTSIDE = 2;
    public static final float NO_MAX = Float.MAX_VALUE;
    public static final float NO_MIN = 0.0f;
    public static final float RELATIVE_UNSPECIFIED = 0.0f;
    private static final int r = ViewConfiguration.getTapTimeout();
    final ClampedScroller a = new ClampedScroller();
    final View b;
    boolean c;
    boolean d;
    boolean e;
    private final Interpolator f = new AccelerateInterpolator();
    private Runnable g;
    private float[] h = {BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED};
    private float[] i = {Float.MAX_VALUE, Float.MAX_VALUE};
    private int j;
    private int k;
    private float[] l = {BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED};
    private float[] m = {BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED};
    private float[] n = {Float.MAX_VALUE, Float.MAX_VALUE};
    private boolean o;
    private boolean p;
    private boolean q;

    static class ClampedScroller {
        private int a;
        private int b;
        private float c;
        private float d;
        private long e = Long.MIN_VALUE;
        private long f = 0;
        private int g = 0;
        private int h = 0;
        private long i = -1;
        private float j;
        private int k;

        private float a(float f2) {
            return (-4.0f * f2 * f2) + (f2 * 4.0f);
        }

        ClampedScroller() {
        }

        public void a(int i2) {
            this.a = i2;
        }

        public void b(int i2) {
            this.b = i2;
        }

        public void a() {
            this.e = AnimationUtils.currentAnimationTimeMillis();
            this.i = -1;
            this.f = this.e;
            this.j = 0.5f;
            this.g = 0;
            this.h = 0;
        }

        public void b() {
            long currentAnimationTimeMillis = AnimationUtils.currentAnimationTimeMillis();
            this.k = AutoScrollHelper.a((int) (currentAnimationTimeMillis - this.e), 0, this.b);
            this.j = a(currentAnimationTimeMillis);
            this.i = currentAnimationTimeMillis;
        }

        public boolean c() {
            return this.i > 0 && AnimationUtils.currentAnimationTimeMillis() > this.i + ((long) this.k);
        }

        private float a(long j2) {
            if (j2 < this.e) {
                return BitmapDescriptorFactory.HUE_RED;
            }
            if (this.i < 0 || j2 < this.i) {
                return AutoScrollHelper.a(((float) (j2 - this.e)) / ((float) this.a), (float) BitmapDescriptorFactory.HUE_RED, 1.0f) * 0.5f;
            }
            return (1.0f - this.j) + (this.j * AutoScrollHelper.a(((float) (j2 - this.i)) / ((float) this.k), (float) BitmapDescriptorFactory.HUE_RED, 1.0f));
        }

        public void d() {
            if (this.f == 0) {
                throw new RuntimeException("Cannot compute scroll delta before calling start()");
            }
            long currentAnimationTimeMillis = AnimationUtils.currentAnimationTimeMillis();
            float a2 = a(a(currentAnimationTimeMillis));
            long j2 = currentAnimationTimeMillis - this.f;
            this.f = currentAnimationTimeMillis;
            float f2 = ((float) j2) * a2;
            this.g = (int) (this.c * f2);
            this.h = (int) (f2 * this.d);
        }

        public void a(float f2, float f3) {
            this.c = f2;
            this.d = f3;
        }

        public int e() {
            return (int) (this.c / Math.abs(this.c));
        }

        public int f() {
            return (int) (this.d / Math.abs(this.d));
        }

        public int g() {
            return this.g;
        }

        public int h() {
            return this.h;
        }
    }

    class ScrollAnimationRunnable implements Runnable {
        ScrollAnimationRunnable() {
        }

        public void run() {
            if (AutoScrollHelper.this.e) {
                if (AutoScrollHelper.this.c) {
                    AutoScrollHelper.this.c = false;
                    AutoScrollHelper.this.a.a();
                }
                ClampedScroller clampedScroller = AutoScrollHelper.this.a;
                if (clampedScroller.c() || !AutoScrollHelper.this.a()) {
                    AutoScrollHelper.this.e = false;
                    return;
                }
                if (AutoScrollHelper.this.d) {
                    AutoScrollHelper.this.d = false;
                    AutoScrollHelper.this.b();
                }
                clampedScroller.d();
                AutoScrollHelper.this.scrollTargetBy(clampedScroller.g(), clampedScroller.h());
                ViewCompat.postOnAnimation(AutoScrollHelper.this.b, this);
            }
        }
    }

    static float a(float f2, float f3, float f4) {
        return f2 > f4 ? f4 : f2 < f3 ? f3 : f2;
    }

    static int a(int i2, int i3, int i4) {
        return i2 > i4 ? i4 : i2 < i3 ? i3 : i2;
    }

    public abstract boolean canTargetScrollHorizontally(int i2);

    public abstract boolean canTargetScrollVertically(int i2);

    public abstract void scrollTargetBy(int i2, int i3);

    public AutoScrollHelper(@NonNull View view) {
        this.b = view;
        DisplayMetrics displayMetrics = Resources.getSystem().getDisplayMetrics();
        int i2 = (int) ((displayMetrics.density * 1575.0f) + 0.5f);
        int i3 = (int) ((displayMetrics.density * 315.0f) + 0.5f);
        float f2 = (float) i2;
        setMaximumVelocity(f2, f2);
        float f3 = (float) i3;
        setMinimumVelocity(f3, f3);
        setEdgeType(1);
        setMaximumEdges(Float.MAX_VALUE, Float.MAX_VALUE);
        setRelativeEdges(0.2f, 0.2f);
        setRelativeVelocity(1.0f, 1.0f);
        setActivationDelay(r);
        setRampUpDuration(HttpStatus.SC_INTERNAL_SERVER_ERROR);
        setRampDownDuration(HttpStatus.SC_INTERNAL_SERVER_ERROR);
    }

    public AutoScrollHelper setEnabled(boolean z) {
        if (this.p && !z) {
            d();
        }
        this.p = z;
        return this;
    }

    public boolean isEnabled() {
        return this.p;
    }

    public AutoScrollHelper setExclusive(boolean z) {
        this.q = z;
        return this;
    }

    public boolean isExclusive() {
        return this.q;
    }

    @NonNull
    public AutoScrollHelper setMaximumVelocity(float f2, float f3) {
        this.n[0] = f2 / 1000.0f;
        this.n[1] = f3 / 1000.0f;
        return this;
    }

    @NonNull
    public AutoScrollHelper setMinimumVelocity(float f2, float f3) {
        this.m[0] = f2 / 1000.0f;
        this.m[1] = f3 / 1000.0f;
        return this;
    }

    @NonNull
    public AutoScrollHelper setRelativeVelocity(float f2, float f3) {
        this.l[0] = f2 / 1000.0f;
        this.l[1] = f3 / 1000.0f;
        return this;
    }

    @NonNull
    public AutoScrollHelper setEdgeType(int i2) {
        this.j = i2;
        return this;
    }

    @NonNull
    public AutoScrollHelper setRelativeEdges(float f2, float f3) {
        this.h[0] = f2;
        this.h[1] = f3;
        return this;
    }

    @NonNull
    public AutoScrollHelper setMaximumEdges(float f2, float f3) {
        this.i[0] = f2;
        this.i[1] = f3;
        return this;
    }

    @NonNull
    public AutoScrollHelper setActivationDelay(int i2) {
        this.k = i2;
        return this;
    }

    @NonNull
    public AutoScrollHelper setRampUpDuration(int i2) {
        this.a.a(i2);
        return this;
    }

    @NonNull
    public AutoScrollHelper setRampDownDuration(int i2) {
        this.a.b(i2);
        return this;
    }

    public boolean onTouch(View view, MotionEvent motionEvent) {
        boolean z = false;
        if (!this.p) {
            return false;
        }
        switch (motionEvent.getActionMasked()) {
            case 0:
                this.d = true;
                this.o = false;
                break;
            case 1:
            case 3:
                d();
                break;
            case 2:
                break;
        }
        this.a.a(a(0, motionEvent.getX(), (float) view.getWidth(), (float) this.b.getWidth()), a(1, motionEvent.getY(), (float) view.getHeight(), (float) this.b.getHeight()));
        if (!this.e && a()) {
            c();
        }
        if (this.q && this.e) {
            z = true;
        }
        return z;
    }

    /* access modifiers changed from: 0000 */
    public boolean a() {
        ClampedScroller clampedScroller = this.a;
        int f2 = clampedScroller.f();
        int e2 = clampedScroller.e();
        return (f2 != 0 && canTargetScrollVertically(f2)) || (e2 != 0 && canTargetScrollHorizontally(e2));
    }

    private void c() {
        if (this.g == null) {
            this.g = new ScrollAnimationRunnable();
        }
        this.e = true;
        this.c = true;
        if (this.o || this.k <= 0) {
            this.g.run();
        } else {
            ViewCompat.postOnAnimationDelayed(this.b, this.g, (long) this.k);
        }
        this.o = true;
    }

    private void d() {
        if (this.c) {
            this.e = false;
        } else {
            this.a.b();
        }
    }

    private float a(int i2, float f2, float f3, float f4) {
        float a2 = a(this.h[i2], f3, this.i[i2], f2);
        if (a2 == BitmapDescriptorFactory.HUE_RED) {
            return BitmapDescriptorFactory.HUE_RED;
        }
        float f5 = this.l[i2];
        float f6 = this.m[i2];
        float f7 = this.n[i2];
        float f8 = f5 * f4;
        if (a2 > BitmapDescriptorFactory.HUE_RED) {
            return a(a2 * f8, f6, f7);
        }
        return -a((-a2) * f8, f6, f7);
    }

    private float a(float f2, float f3, float f4, float f5) {
        float f6;
        float a2 = a(f2 * f3, (float) BitmapDescriptorFactory.HUE_RED, f4);
        float a3 = a(f3 - f5, a2) - a(f5, a2);
        if (a3 < BitmapDescriptorFactory.HUE_RED) {
            f6 = -this.f.getInterpolation(-a3);
        } else if (a3 <= BitmapDescriptorFactory.HUE_RED) {
            return BitmapDescriptorFactory.HUE_RED;
        } else {
            f6 = this.f.getInterpolation(a3);
        }
        return a(f6, -1.0f, 1.0f);
    }

    private float a(float f2, float f3) {
        if (f3 == BitmapDescriptorFactory.HUE_RED) {
            return BitmapDescriptorFactory.HUE_RED;
        }
        switch (this.j) {
            case 0:
            case 1:
                if (f2 < f3) {
                    if (f2 >= BitmapDescriptorFactory.HUE_RED) {
                        return 1.0f - (f2 / f3);
                    }
                    if (!this.e || this.j != 1) {
                        return BitmapDescriptorFactory.HUE_RED;
                    }
                    return 1.0f;
                }
                break;
            case 2:
                if (f2 < BitmapDescriptorFactory.HUE_RED) {
                    return f2 / (-f3);
                }
                break;
        }
        return BitmapDescriptorFactory.HUE_RED;
    }

    /* access modifiers changed from: 0000 */
    public void b() {
        long uptimeMillis = SystemClock.uptimeMillis();
        MotionEvent obtain = MotionEvent.obtain(uptimeMillis, uptimeMillis, 3, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, 0);
        this.b.onTouchEvent(obtain);
        obtain.recycle();
    }
}
