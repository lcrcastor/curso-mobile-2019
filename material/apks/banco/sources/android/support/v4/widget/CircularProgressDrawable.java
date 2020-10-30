package android.support.v4.widget;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Path.FillType;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.v4.util.Preconditions;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class CircularProgressDrawable extends Drawable implements Animatable {
    public static final int DEFAULT = 1;
    public static final int LARGE = 0;
    private static final Interpolator a = new LinearInterpolator();
    private static final Interpolator b = new FastOutSlowInInterpolator();
    private static final int[] c = {-16777216};
    private final Ring d = new Ring();
    private float e;
    private Resources f;
    private Animator g;
    /* access modifiers changed from: private */
    public float h;
    /* access modifiers changed from: private */
    public boolean i;

    @RestrictTo({Scope.LIBRARY_GROUP})
    @Retention(RetentionPolicy.SOURCE)
    public @interface ProgressDrawableSize {
    }

    static class Ring {
        final RectF a = new RectF();
        final Paint b = new Paint();
        final Paint c = new Paint();
        final Paint d = new Paint();
        float e = BitmapDescriptorFactory.HUE_RED;
        float f = BitmapDescriptorFactory.HUE_RED;
        float g = BitmapDescriptorFactory.HUE_RED;
        float h = 5.0f;
        int[] i;
        int j;
        float k;
        float l;
        float m;
        boolean n;
        Path o;
        float p = 1.0f;
        float q;
        int r;
        int s;
        int t = 255;
        int u;

        Ring() {
            this.b.setStrokeCap(Cap.SQUARE);
            this.b.setAntiAlias(true);
            this.b.setStyle(Style.STROKE);
            this.c.setStyle(Style.FILL);
            this.c.setAntiAlias(true);
            this.d.setColor(0);
        }

        /* access modifiers changed from: 0000 */
        public void a(float f2, float f3) {
            this.r = (int) f2;
            this.s = (int) f3;
        }

        /* access modifiers changed from: 0000 */
        public void a(Cap cap) {
            this.b.setStrokeCap(cap);
        }

        /* access modifiers changed from: 0000 */
        public Cap a() {
            return this.b.getStrokeCap();
        }

        /* access modifiers changed from: 0000 */
        public float b() {
            return (float) this.r;
        }

        /* access modifiers changed from: 0000 */
        public float c() {
            return (float) this.s;
        }

        /* access modifiers changed from: 0000 */
        public void a(Canvas canvas, Rect rect) {
            RectF rectF = this.a;
            float f2 = this.q + (this.h / 2.0f);
            if (this.q <= BitmapDescriptorFactory.HUE_RED) {
                f2 = (((float) Math.min(rect.width(), rect.height())) / 2.0f) - Math.max((((float) this.r) * this.p) / 2.0f, this.h / 2.0f);
            }
            rectF.set(((float) rect.centerX()) - f2, ((float) rect.centerY()) - f2, ((float) rect.centerX()) + f2, ((float) rect.centerY()) + f2);
            float f3 = (this.e + this.g) * 360.0f;
            float f4 = ((this.f + this.g) * 360.0f) - f3;
            this.b.setColor(this.u);
            this.b.setAlpha(this.t);
            float f5 = this.h / 2.0f;
            rectF.inset(f5, f5);
            canvas.drawCircle(rectF.centerX(), rectF.centerY(), rectF.width() / 2.0f, this.d);
            float f6 = -f5;
            rectF.inset(f6, f6);
            canvas.drawArc(rectF, f3, f4, false, this.b);
            a(canvas, f3, f4, rectF);
        }

        /* access modifiers changed from: 0000 */
        public void a(Canvas canvas, float f2, float f3, RectF rectF) {
            if (this.n) {
                if (this.o == null) {
                    this.o = new Path();
                    this.o.setFillType(FillType.EVEN_ODD);
                } else {
                    this.o.reset();
                }
                float min = Math.min(rectF.width(), rectF.height()) / 2.0f;
                float f4 = (((float) this.r) * this.p) / 2.0f;
                this.o.moveTo(BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED);
                this.o.lineTo(((float) this.r) * this.p, BitmapDescriptorFactory.HUE_RED);
                this.o.lineTo((((float) this.r) * this.p) / 2.0f, ((float) this.s) * this.p);
                this.o.offset((min + rectF.centerX()) - f4, rectF.centerY() + (this.h / 2.0f));
                this.o.close();
                this.c.setColor(this.u);
                this.c.setAlpha(this.t);
                canvas.save();
                canvas.rotate(f2 + f3, rectF.centerX(), rectF.centerY());
                canvas.drawPath(this.o, this.c);
                canvas.restore();
            }
        }

        /* access modifiers changed from: 0000 */
        public void a(@NonNull int[] iArr) {
            this.i = iArr;
            c(0);
        }

        /* access modifiers changed from: 0000 */
        public int[] d() {
            return this.i;
        }

        /* access modifiers changed from: 0000 */
        public void a(int i2) {
            this.u = i2;
        }

        /* access modifiers changed from: 0000 */
        public void b(int i2) {
            this.d.setColor(i2);
        }

        /* access modifiers changed from: 0000 */
        public int e() {
            return this.d.getColor();
        }

        /* access modifiers changed from: 0000 */
        public void c(int i2) {
            this.j = i2;
            this.u = this.i[this.j];
        }

        /* access modifiers changed from: 0000 */
        public int f() {
            return this.i[g()];
        }

        /* access modifiers changed from: 0000 */
        public int g() {
            return (this.j + 1) % this.i.length;
        }

        /* access modifiers changed from: 0000 */
        public void h() {
            c(g());
        }

        /* access modifiers changed from: 0000 */
        public void a(ColorFilter colorFilter) {
            this.b.setColorFilter(colorFilter);
        }

        /* access modifiers changed from: 0000 */
        public void d(int i2) {
            this.t = i2;
        }

        /* access modifiers changed from: 0000 */
        public int i() {
            return this.t;
        }

        /* access modifiers changed from: 0000 */
        public void a(float f2) {
            this.h = f2;
            this.b.setStrokeWidth(f2);
        }

        /* access modifiers changed from: 0000 */
        public float j() {
            return this.h;
        }

        /* access modifiers changed from: 0000 */
        public void b(float f2) {
            this.e = f2;
        }

        /* access modifiers changed from: 0000 */
        public float k() {
            return this.e;
        }

        /* access modifiers changed from: 0000 */
        public float l() {
            return this.k;
        }

        /* access modifiers changed from: 0000 */
        public float m() {
            return this.l;
        }

        /* access modifiers changed from: 0000 */
        public int n() {
            return this.i[this.j];
        }

        /* access modifiers changed from: 0000 */
        public void c(float f2) {
            this.f = f2;
        }

        /* access modifiers changed from: 0000 */
        public float o() {
            return this.f;
        }

        /* access modifiers changed from: 0000 */
        public void d(float f2) {
            this.g = f2;
        }

        /* access modifiers changed from: 0000 */
        public float p() {
            return this.g;
        }

        /* access modifiers changed from: 0000 */
        public void e(float f2) {
            this.q = f2;
        }

        /* access modifiers changed from: 0000 */
        public float q() {
            return this.q;
        }

        /* access modifiers changed from: 0000 */
        public void a(boolean z) {
            if (this.n != z) {
                this.n = z;
            }
        }

        /* access modifiers changed from: 0000 */
        public boolean r() {
            return this.n;
        }

        /* access modifiers changed from: 0000 */
        public void f(float f2) {
            if (f2 != this.p) {
                this.p = f2;
            }
        }

        /* access modifiers changed from: 0000 */
        public float s() {
            return this.p;
        }

        /* access modifiers changed from: 0000 */
        public float t() {
            return this.m;
        }

        /* access modifiers changed from: 0000 */
        public void u() {
            this.k = this.e;
            this.l = this.f;
            this.m = this.g;
        }

        /* access modifiers changed from: 0000 */
        public void v() {
            this.k = BitmapDescriptorFactory.HUE_RED;
            this.l = BitmapDescriptorFactory.HUE_RED;
            this.m = BitmapDescriptorFactory.HUE_RED;
            b((float) BitmapDescriptorFactory.HUE_RED);
            c((float) BitmapDescriptorFactory.HUE_RED);
            d((float) BitmapDescriptorFactory.HUE_RED);
        }
    }

    private int a(float f2, int i2, int i3) {
        int i4 = (i2 >> 24) & 255;
        int i5 = (i2 >> 16) & 255;
        int i6 = (i2 >> 8) & 255;
        int i7 = i2 & 255;
        return ((i4 + ((int) (((float) (((i3 >> 24) & 255) - i4)) * f2))) << 24) | ((i5 + ((int) (((float) (((i3 >> 16) & 255) - i5)) * f2))) << 16) | ((i6 + ((int) (((float) (((i3 >> 8) & 255) - i6)) * f2))) << 8) | (i7 + ((int) (f2 * ((float) ((i3 & 255) - i7)))));
    }

    public int getOpacity() {
        return -3;
    }

    public CircularProgressDrawable(@NonNull Context context) {
        this.f = ((Context) Preconditions.checkNotNull(context)).getResources();
        this.d.a(c);
        setStrokeWidth(2.5f);
        a();
    }

    private void a(float f2, float f3, float f4, float f5) {
        Ring ring = this.d;
        float f6 = this.f.getDisplayMetrics().density;
        ring.a(f3 * f6);
        ring.e(f2 * f6);
        ring.c(0);
        ring.a(f4 * f6, f5 * f6);
    }

    public void setStyle(int i2) {
        if (i2 == 0) {
            a(11.0f, 3.0f, 12.0f, 6.0f);
        } else {
            a(7.5f, 2.5f, 10.0f, 5.0f);
        }
        invalidateSelf();
    }

    public float getStrokeWidth() {
        return this.d.j();
    }

    public void setStrokeWidth(float f2) {
        this.d.a(f2);
        invalidateSelf();
    }

    public float getCenterRadius() {
        return this.d.q();
    }

    public void setCenterRadius(float f2) {
        this.d.e(f2);
        invalidateSelf();
    }

    public void setStrokeCap(@NonNull Cap cap) {
        this.d.a(cap);
        invalidateSelf();
    }

    @NonNull
    public Cap getStrokeCap() {
        return this.d.a();
    }

    public float getArrowWidth() {
        return this.d.b();
    }

    public float getArrowHeight() {
        return this.d.c();
    }

    public void setArrowDimensions(float f2, float f3) {
        this.d.a(f2, f3);
        invalidateSelf();
    }

    public boolean getArrowEnabled() {
        return this.d.r();
    }

    public void setArrowEnabled(boolean z) {
        this.d.a(z);
        invalidateSelf();
    }

    public float getArrowScale() {
        return this.d.s();
    }

    public void setArrowScale(float f2) {
        this.d.f(f2);
        invalidateSelf();
    }

    public float getStartTrim() {
        return this.d.k();
    }

    public float getEndTrim() {
        return this.d.o();
    }

    public void setStartEndTrim(float f2, float f3) {
        this.d.b(f2);
        this.d.c(f3);
        invalidateSelf();
    }

    public float getProgressRotation() {
        return this.d.p();
    }

    public void setProgressRotation(float f2) {
        this.d.d(f2);
        invalidateSelf();
    }

    public int getBackgroundColor() {
        return this.d.e();
    }

    public void setBackgroundColor(int i2) {
        this.d.b(i2);
        invalidateSelf();
    }

    @NonNull
    public int[] getColorSchemeColors() {
        return this.d.d();
    }

    public void setColorSchemeColors(@NonNull int... iArr) {
        this.d.a(iArr);
        this.d.c(0);
        invalidateSelf();
    }

    public void draw(Canvas canvas) {
        Rect bounds = getBounds();
        canvas.save();
        canvas.rotate(this.e, bounds.exactCenterX(), bounds.exactCenterY());
        this.d.a(canvas, bounds);
        canvas.restore();
    }

    public void setAlpha(int i2) {
        this.d.d(i2);
        invalidateSelf();
    }

    public int getAlpha() {
        return this.d.i();
    }

    public void setColorFilter(ColorFilter colorFilter) {
        this.d.a(colorFilter);
        invalidateSelf();
    }

    private void a(float f2) {
        this.e = f2;
    }

    public boolean isRunning() {
        return this.g.isRunning();
    }

    public void start() {
        this.g.cancel();
        this.d.u();
        if (this.d.o() != this.d.k()) {
            this.i = true;
            this.g.setDuration(666);
            this.g.start();
            return;
        }
        this.d.c(0);
        this.d.v();
        this.g.setDuration(1332);
        this.g.start();
    }

    public void stop() {
        this.g.cancel();
        a((float) BitmapDescriptorFactory.HUE_RED);
        this.d.a(false);
        this.d.c(0);
        this.d.v();
        invalidateSelf();
    }

    /* access modifiers changed from: private */
    public void a(float f2, Ring ring) {
        if (f2 > 0.75f) {
            ring.a(a((f2 - 0.75f) / 0.25f, ring.n(), ring.f()));
        } else {
            ring.a(ring.n());
        }
    }

    private void b(float f2, Ring ring) {
        a(f2, ring);
        float floor = (float) (Math.floor((double) (ring.t() / 0.8f)) + 1.0d);
        ring.b(ring.l() + (((ring.m() - 0.01f) - ring.l()) * f2));
        ring.c(ring.m());
        ring.d(ring.t() + ((floor - ring.t()) * f2));
    }

    /* access modifiers changed from: private */
    public void a(float f2, Ring ring, boolean z) {
        float f3;
        float f4;
        if (this.i) {
            b(f2, ring);
        } else if (f2 != 1.0f || z) {
            float t = ring.t();
            if (f2 < 0.5f) {
                float f5 = f2 / 0.5f;
                float l = ring.l();
                float f6 = l;
                f3 = (b.getInterpolation(f5) * 0.79f) + 0.01f + l;
                f4 = f6;
            } else {
                f3 = ring.l() + 0.79f;
                f4 = f3 - (((1.0f - b.getInterpolation((f2 - 0.5f) / 0.5f)) * 0.79f) + 0.01f);
            }
            float f7 = t + (0.20999998f * f2);
            float f8 = (f2 + this.h) * 216.0f;
            ring.b(f4);
            ring.c(f3);
            ring.d(f7);
            a(f8);
        }
    }

    private void a() {
        final Ring ring = this.d;
        ValueAnimator ofFloat = ValueAnimator.ofFloat(new float[]{BitmapDescriptorFactory.HUE_RED, 1.0f});
        ofFloat.addUpdateListener(new AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                CircularProgressDrawable.this.a(floatValue, ring);
                CircularProgressDrawable.this.a(floatValue, ring, false);
                CircularProgressDrawable.this.invalidateSelf();
            }
        });
        ofFloat.setRepeatCount(-1);
        ofFloat.setRepeatMode(1);
        ofFloat.setInterpolator(a);
        ofFloat.addListener(new AnimatorListener() {
            public void onAnimationCancel(Animator animator) {
            }

            public void onAnimationEnd(Animator animator) {
            }

            public void onAnimationStart(Animator animator) {
                CircularProgressDrawable.this.h = BitmapDescriptorFactory.HUE_RED;
            }

            public void onAnimationRepeat(Animator animator) {
                CircularProgressDrawable.this.a(1.0f, ring, true);
                ring.u();
                ring.h();
                if (CircularProgressDrawable.this.i) {
                    CircularProgressDrawable.this.i = false;
                    animator.cancel();
                    animator.setDuration(1332);
                    animator.start();
                    ring.a(false);
                    return;
                }
                CircularProgressDrawable.this.h = CircularProgressDrawable.this.h + 1.0f;
            }
        });
        this.g = ofFloat;
    }
}
