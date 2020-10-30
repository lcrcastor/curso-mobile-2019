package android.support.design.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff.Mode;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Build.VERSION;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.R;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.ViewCompat;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.view.animation.Interpolator;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

@RequiresApi(14)
class FloatingActionButtonImpl {
    static final Interpolator a = AnimationUtils.c;
    static final int[] j = {16842919, 16842910};
    static final int[] k = {16842908, 16842910};
    static final int[] l = {16842910};
    static final int[] m = new int[0];
    int b = 0;
    ShadowDrawableWrapper c;
    Drawable d;
    Drawable e;
    CircularBorderDrawable f;
    Drawable g;
    float h;
    float i;
    final VisibilityAwareImageButton n;
    final ShadowViewDelegate o;
    private final StateListAnimator p;
    private float q;
    private final Rect r = new Rect();
    private OnPreDrawListener s;

    class DisabledElevationAnimation extends ShadowAnimatorImpl {
        /* access modifiers changed from: protected */
        public float a() {
            return BitmapDescriptorFactory.HUE_RED;
        }

        DisabledElevationAnimation() {
            super();
        }
    }

    class ElevateToTranslationZAnimation extends ShadowAnimatorImpl {
        ElevateToTranslationZAnimation() {
            super();
        }

        /* access modifiers changed from: protected */
        public float a() {
            return FloatingActionButtonImpl.this.h + FloatingActionButtonImpl.this.i;
        }
    }

    interface InternalVisibilityChangedListener {
        void a();

        void b();
    }

    class ResetElevationAnimation extends ShadowAnimatorImpl {
        ResetElevationAnimation() {
            super();
        }

        /* access modifiers changed from: protected */
        public float a() {
            return FloatingActionButtonImpl.this.h;
        }
    }

    abstract class ShadowAnimatorImpl extends AnimatorListenerAdapter implements AnimatorUpdateListener {
        private boolean a;
        private float c;
        private float d;

        /* access modifiers changed from: protected */
        public abstract float a();

        private ShadowAnimatorImpl() {
        }

        public void onAnimationUpdate(ValueAnimator valueAnimator) {
            if (!this.a) {
                this.c = FloatingActionButtonImpl.this.c.a();
                this.d = a();
                this.a = true;
            }
            FloatingActionButtonImpl.this.c.b(this.c + ((this.d - this.c) * valueAnimator.getAnimatedFraction()));
        }

        public void onAnimationEnd(Animator animator) {
            FloatingActionButtonImpl.this.c.b(this.d);
            this.a = false;
        }
    }

    /* access modifiers changed from: 0000 */
    public void b(Rect rect) {
    }

    /* access modifiers changed from: 0000 */
    public void d() {
    }

    /* access modifiers changed from: 0000 */
    public boolean h() {
        return true;
    }

    FloatingActionButtonImpl(VisibilityAwareImageButton visibilityAwareImageButton, ShadowViewDelegate shadowViewDelegate) {
        this.n = visibilityAwareImageButton;
        this.o = shadowViewDelegate;
        this.p = new StateListAnimator();
        this.p.a(j, a((ShadowAnimatorImpl) new ElevateToTranslationZAnimation()));
        this.p.a(k, a((ShadowAnimatorImpl) new ElevateToTranslationZAnimation()));
        this.p.a(l, a((ShadowAnimatorImpl) new ResetElevationAnimation()));
        this.p.a(m, a((ShadowAnimatorImpl) new DisabledElevationAnimation()));
        this.q = this.n.getRotation();
    }

    /* access modifiers changed from: 0000 */
    public void a(ColorStateList colorStateList, Mode mode, int i2, int i3) {
        Drawable[] drawableArr;
        this.d = DrawableCompat.wrap(k());
        DrawableCompat.setTintList(this.d, colorStateList);
        if (mode != null) {
            DrawableCompat.setTintMode(this.d, mode);
        }
        this.e = DrawableCompat.wrap(k());
        DrawableCompat.setTintList(this.e, b(i2));
        if (i3 > 0) {
            this.f = a(i3, colorStateList);
            drawableArr = new Drawable[]{this.f, this.d, this.e};
        } else {
            this.f = null;
            drawableArr = new Drawable[]{this.d, this.e};
        }
        this.g = new LayerDrawable(drawableArr);
        ShadowDrawableWrapper shadowDrawableWrapper = new ShadowDrawableWrapper(this.n.getContext(), this.g, this.o.a(), this.h, this.h + this.i);
        this.c = shadowDrawableWrapper;
        this.c.a(false);
        this.o.a(this.c);
    }

    /* access modifiers changed from: 0000 */
    public void a(ColorStateList colorStateList) {
        if (this.d != null) {
            DrawableCompat.setTintList(this.d, colorStateList);
        }
        if (this.f != null) {
            this.f.a(colorStateList);
        }
    }

    /* access modifiers changed from: 0000 */
    public void a(Mode mode) {
        if (this.d != null) {
            DrawableCompat.setTintMode(this.d, mode);
        }
    }

    /* access modifiers changed from: 0000 */
    public void a(int i2) {
        if (this.e != null) {
            DrawableCompat.setTintList(this.e, b(i2));
        }
    }

    /* access modifiers changed from: 0000 */
    public final void a(float f2) {
        if (this.h != f2) {
            this.h = f2;
            a(f2, this.i);
        }
    }

    /* access modifiers changed from: 0000 */
    public float a() {
        return this.h;
    }

    /* access modifiers changed from: 0000 */
    public final void b(float f2) {
        if (this.i != f2) {
            this.i = f2;
            a(this.h, f2);
        }
    }

    /* access modifiers changed from: 0000 */
    public void a(float f2, float f3) {
        if (this.c != null) {
            this.c.a(f2, this.i + f2);
            e();
        }
    }

    /* access modifiers changed from: 0000 */
    public void a(int[] iArr) {
        this.p.a(iArr);
    }

    /* access modifiers changed from: 0000 */
    public void b() {
        this.p.a();
    }

    /* access modifiers changed from: 0000 */
    public void a(@Nullable final InternalVisibilityChangedListener internalVisibilityChangedListener, final boolean z) {
        if (!n()) {
            this.n.animate().cancel();
            if (p()) {
                this.b = 1;
                this.n.animate().scaleX(BitmapDescriptorFactory.HUE_RED).scaleY(BitmapDescriptorFactory.HUE_RED).alpha(BitmapDescriptorFactory.HUE_RED).setDuration(200).setInterpolator(AnimationUtils.c).setListener(new AnimatorListenerAdapter() {
                    private boolean d;

                    public void onAnimationStart(Animator animator) {
                        FloatingActionButtonImpl.this.n.a(0, z);
                        this.d = false;
                    }

                    public void onAnimationCancel(Animator animator) {
                        this.d = true;
                    }

                    public void onAnimationEnd(Animator animator) {
                        FloatingActionButtonImpl.this.b = 0;
                        if (!this.d) {
                            FloatingActionButtonImpl.this.n.a(z ? 8 : 4, z);
                            if (internalVisibilityChangedListener != null) {
                                internalVisibilityChangedListener.b();
                            }
                        }
                    }
                });
            } else {
                this.n.a(z ? 8 : 4, z);
                if (internalVisibilityChangedListener != null) {
                    internalVisibilityChangedListener.b();
                }
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void b(@Nullable final InternalVisibilityChangedListener internalVisibilityChangedListener, final boolean z) {
        if (!m()) {
            this.n.animate().cancel();
            if (p()) {
                this.b = 2;
                if (this.n.getVisibility() != 0) {
                    this.n.setAlpha(BitmapDescriptorFactory.HUE_RED);
                    this.n.setScaleY(BitmapDescriptorFactory.HUE_RED);
                    this.n.setScaleX(BitmapDescriptorFactory.HUE_RED);
                }
                this.n.animate().scaleX(1.0f).scaleY(1.0f).alpha(1.0f).setDuration(200).setInterpolator(AnimationUtils.d).setListener(new AnimatorListenerAdapter() {
                    public void onAnimationStart(Animator animator) {
                        FloatingActionButtonImpl.this.n.a(0, z);
                    }

                    public void onAnimationEnd(Animator animator) {
                        FloatingActionButtonImpl.this.b = 0;
                        if (internalVisibilityChangedListener != null) {
                            internalVisibilityChangedListener.a();
                        }
                    }
                });
            } else {
                this.n.a(0, z);
                this.n.setAlpha(1.0f);
                this.n.setScaleY(1.0f);
                this.n.setScaleX(1.0f);
                if (internalVisibilityChangedListener != null) {
                    internalVisibilityChangedListener.a();
                }
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public final Drawable c() {
        return this.g;
    }

    /* access modifiers changed from: 0000 */
    public final void e() {
        Rect rect = this.r;
        a(rect);
        b(rect);
        this.o.a(rect.left, rect.top, rect.right, rect.bottom);
    }

    /* access modifiers changed from: 0000 */
    public void a(Rect rect) {
        this.c.getPadding(rect);
    }

    /* access modifiers changed from: 0000 */
    public void f() {
        if (h()) {
            o();
            this.n.getViewTreeObserver().addOnPreDrawListener(this.s);
        }
    }

    /* access modifiers changed from: 0000 */
    public void g() {
        if (this.s != null) {
            this.n.getViewTreeObserver().removeOnPreDrawListener(this.s);
            this.s = null;
        }
    }

    /* access modifiers changed from: 0000 */
    public CircularBorderDrawable a(int i2, ColorStateList colorStateList) {
        Context context = this.n.getContext();
        CircularBorderDrawable i3 = i();
        i3.a(ContextCompat.getColor(context, R.color.design_fab_stroke_top_outer_color), ContextCompat.getColor(context, R.color.design_fab_stroke_top_inner_color), ContextCompat.getColor(context, R.color.design_fab_stroke_end_inner_color), ContextCompat.getColor(context, R.color.design_fab_stroke_end_outer_color));
        i3.a((float) i2);
        i3.a(colorStateList);
        return i3;
    }

    /* access modifiers changed from: 0000 */
    public CircularBorderDrawable i() {
        return new CircularBorderDrawable();
    }

    /* access modifiers changed from: 0000 */
    public void j() {
        float rotation = this.n.getRotation();
        if (this.q != rotation) {
            this.q = rotation;
            q();
        }
    }

    private void o() {
        if (this.s == null) {
            this.s = new OnPreDrawListener() {
                public boolean onPreDraw() {
                    FloatingActionButtonImpl.this.j();
                    return true;
                }
            };
        }
    }

    /* access modifiers changed from: 0000 */
    public GradientDrawable k() {
        GradientDrawable l2 = l();
        l2.setShape(1);
        l2.setColor(-1);
        return l2;
    }

    /* access modifiers changed from: 0000 */
    public GradientDrawable l() {
        return new GradientDrawable();
    }

    /* access modifiers changed from: 0000 */
    public boolean m() {
        boolean z = false;
        if (this.n.getVisibility() != 0) {
            if (this.b == 2) {
                z = true;
            }
            return z;
        }
        if (this.b != 1) {
            z = true;
        }
        return z;
    }

    /* access modifiers changed from: 0000 */
    public boolean n() {
        boolean z = false;
        if (this.n.getVisibility() == 0) {
            if (this.b == 1) {
                z = true;
            }
            return z;
        }
        if (this.b != 2) {
            z = true;
        }
        return z;
    }

    private ValueAnimator a(@NonNull ShadowAnimatorImpl shadowAnimatorImpl) {
        ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.setInterpolator(a);
        valueAnimator.setDuration(100);
        valueAnimator.addListener(shadowAnimatorImpl);
        valueAnimator.addUpdateListener(shadowAnimatorImpl);
        valueAnimator.setFloatValues(new float[]{BitmapDescriptorFactory.HUE_RED, 1.0f});
        return valueAnimator;
    }

    private static ColorStateList b(int i2) {
        return new ColorStateList(new int[][]{k, j, new int[0]}, new int[]{i2, i2, 0});
    }

    private boolean p() {
        return ViewCompat.isLaidOut(this.n) && !this.n.isInEditMode();
    }

    private void q() {
        if (VERSION.SDK_INT == 19) {
            if (this.q % 90.0f != BitmapDescriptorFactory.HUE_RED) {
                if (this.n.getLayerType() != 1) {
                    this.n.setLayerType(1, null);
                }
            } else if (this.n.getLayerType() != 0) {
                this.n.setLayerType(0, null);
            }
        }
        if (this.c != null) {
            this.c.a(-this.q);
        }
        if (this.f != null) {
            this.f.b(-this.q);
        }
    }
}
