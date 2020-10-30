package android.support.transition;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.support.annotation.NonNull;
import android.support.v4.content.res.TypedArrayUtils;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

public class Fade extends Visibility {
    public static final int IN = 1;
    public static final int OUT = 2;

    static class FadeAnimatorListener extends AnimatorListenerAdapter {
        private final View a;
        private boolean b = false;

        FadeAnimatorListener(View view) {
            this.a = view;
        }

        public void onAnimationStart(Animator animator) {
            if (ViewCompat.hasOverlappingRendering(this.a) && this.a.getLayerType() == 0) {
                this.b = true;
                this.a.setLayerType(2, null);
            }
        }

        public void onAnimationEnd(Animator animator) {
            ViewUtils.a(this.a, 1.0f);
            if (this.b) {
                this.a.setLayerType(0, null);
            }
        }
    }

    public Fade(int i) {
        setMode(i);
    }

    public Fade() {
    }

    public Fade(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, Styleable.f);
        setMode(TypedArrayUtils.getNamedInt(obtainStyledAttributes, (XmlResourceParser) attributeSet, "fadingMode", 0, getMode()));
        obtainStyledAttributes.recycle();
    }

    public void captureStartValues(@NonNull TransitionValues transitionValues) {
        super.captureStartValues(transitionValues);
        transitionValues.values.put("android:fade:transitionAlpha", Float.valueOf(ViewUtils.c(transitionValues.view)));
    }

    private Animator a(final View view, float f, float f2) {
        if (f == f2) {
            return null;
        }
        ViewUtils.a(view, f);
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(view, ViewUtils.a, new float[]{f2});
        ofFloat.addListener(new FadeAnimatorListener(view));
        addListener(new TransitionListenerAdapter() {
            public void onTransitionEnd(@NonNull Transition transition) {
                ViewUtils.a(view, 1.0f);
                ViewUtils.e(view);
                transition.removeListener(this);
            }
        });
        return ofFloat;
    }

    public Animator onAppear(ViewGroup viewGroup, View view, TransitionValues transitionValues, TransitionValues transitionValues2) {
        float f = BitmapDescriptorFactory.HUE_RED;
        float a = a(transitionValues, BitmapDescriptorFactory.HUE_RED);
        if (a != 1.0f) {
            f = a;
        }
        return a(view, f, 1.0f);
    }

    public Animator onDisappear(ViewGroup viewGroup, View view, TransitionValues transitionValues, TransitionValues transitionValues2) {
        ViewUtils.d(view);
        return a(view, a(transitionValues, 1.0f), BitmapDescriptorFactory.HUE_RED);
    }

    private static float a(TransitionValues transitionValues, float f) {
        if (transitionValues == null) {
            return f;
        }
        Float f2 = (Float) transitionValues.values.get("android:fade:transitionAlpha");
        return f2 != null ? f2.floatValue() : f;
    }
}
