package android.support.design.widget;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.StateListAnimator;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff.Mode;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build.VERSION;
import android.support.annotation.RequiresApi;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.view.View;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.util.ArrayList;

@RequiresApi(21)
class FloatingActionButtonLollipop extends FloatingActionButtonImpl {
    private InsetDrawable p;

    static class AlwaysStatefulGradientDrawable extends GradientDrawable {
        public boolean isStateful() {
            return true;
        }

        AlwaysStatefulGradientDrawable() {
        }
    }

    /* access modifiers changed from: 0000 */
    public void a(int[] iArr) {
    }

    /* access modifiers changed from: 0000 */
    public void b() {
    }

    /* access modifiers changed from: 0000 */
    public boolean h() {
        return false;
    }

    FloatingActionButtonLollipop(VisibilityAwareImageButton visibilityAwareImageButton, ShadowViewDelegate shadowViewDelegate) {
        super(visibilityAwareImageButton, shadowViewDelegate);
    }

    /* access modifiers changed from: 0000 */
    public void a(ColorStateList colorStateList, Mode mode, int i, int i2) {
        Drawable drawable;
        this.d = DrawableCompat.wrap(k());
        DrawableCompat.setTintList(this.d, colorStateList);
        if (mode != null) {
            DrawableCompat.setTintMode(this.d, mode);
        }
        if (i2 > 0) {
            this.f = a(i2, colorStateList);
            drawable = new LayerDrawable(new Drawable[]{this.f, this.d});
        } else {
            this.f = null;
            drawable = this.d;
        }
        this.e = new RippleDrawable(ColorStateList.valueOf(i), drawable, null);
        this.g = this.e;
        this.o.a(this.e);
    }

    /* access modifiers changed from: 0000 */
    public void a(int i) {
        if (this.e instanceof RippleDrawable) {
            ((RippleDrawable) this.e).setColor(ColorStateList.valueOf(i));
        } else {
            super.a(i);
        }
    }

    /* access modifiers changed from: 0000 */
    public void a(float f, float f2) {
        if (VERSION.SDK_INT != 21) {
            StateListAnimator stateListAnimator = new StateListAnimator();
            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.play(ObjectAnimator.ofFloat(this.n, "elevation", new float[]{f}).setDuration(0)).with(ObjectAnimator.ofFloat(this.n, View.TRANSLATION_Z, new float[]{f2}).setDuration(100));
            animatorSet.setInterpolator(a);
            stateListAnimator.addState(j, animatorSet);
            AnimatorSet animatorSet2 = new AnimatorSet();
            animatorSet2.play(ObjectAnimator.ofFloat(this.n, "elevation", new float[]{f}).setDuration(0)).with(ObjectAnimator.ofFloat(this.n, View.TRANSLATION_Z, new float[]{f2}).setDuration(100));
            animatorSet2.setInterpolator(a);
            stateListAnimator.addState(k, animatorSet2);
            AnimatorSet animatorSet3 = new AnimatorSet();
            ArrayList arrayList = new ArrayList();
            arrayList.add(ObjectAnimator.ofFloat(this.n, "elevation", new float[]{f}).setDuration(0));
            if (VERSION.SDK_INT >= 22 && VERSION.SDK_INT <= 24) {
                arrayList.add(ObjectAnimator.ofFloat(this.n, View.TRANSLATION_Z, new float[]{this.n.getTranslationZ()}).setDuration(100));
            }
            arrayList.add(ObjectAnimator.ofFloat(this.n, View.TRANSLATION_Z, new float[]{0.0f}).setDuration(100));
            animatorSet3.playSequentially((Animator[]) arrayList.toArray(new ObjectAnimator[0]));
            animatorSet3.setInterpolator(a);
            stateListAnimator.addState(l, animatorSet3);
            AnimatorSet animatorSet4 = new AnimatorSet();
            animatorSet4.play(ObjectAnimator.ofFloat(this.n, "elevation", new float[]{0.0f}).setDuration(0)).with(ObjectAnimator.ofFloat(this.n, View.TRANSLATION_Z, new float[]{0.0f}).setDuration(0));
            animatorSet4.setInterpolator(a);
            stateListAnimator.addState(m, animatorSet4);
            this.n.setStateListAnimator(stateListAnimator);
        } else if (this.n.isEnabled()) {
            this.n.setElevation(f);
            if (this.n.isFocused() || this.n.isPressed()) {
                this.n.setTranslationZ(f2);
            } else {
                this.n.setTranslationZ(BitmapDescriptorFactory.HUE_RED);
            }
        } else {
            this.n.setElevation(BitmapDescriptorFactory.HUE_RED);
            this.n.setTranslationZ(BitmapDescriptorFactory.HUE_RED);
        }
        if (this.o.b()) {
            e();
        }
    }

    public float a() {
        return this.n.getElevation();
    }

    /* access modifiers changed from: 0000 */
    public void d() {
        e();
    }

    /* access modifiers changed from: 0000 */
    public void b(Rect rect) {
        if (this.o.b()) {
            InsetDrawable insetDrawable = new InsetDrawable(this.e, rect.left, rect.top, rect.right, rect.bottom);
            this.p = insetDrawable;
            this.o.a(this.p);
            return;
        }
        this.o.a(this.e);
    }

    /* access modifiers changed from: 0000 */
    public CircularBorderDrawable i() {
        return new CircularBorderDrawableLollipop();
    }

    /* access modifiers changed from: 0000 */
    public GradientDrawable l() {
        return new AlwaysStatefulGradientDrawable();
    }

    /* access modifiers changed from: 0000 */
    public void a(Rect rect) {
        if (this.o.b()) {
            float a = this.o.a();
            float a2 = a() + this.i;
            int ceil = (int) Math.ceil((double) ShadowDrawableWrapper.b(a2, a, false));
            int ceil2 = (int) Math.ceil((double) ShadowDrawableWrapper.a(a2, a, false));
            rect.set(ceil, ceil2, ceil, ceil2);
            return;
        }
        rect.set(0, 0, 0, 0);
    }
}
