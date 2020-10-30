package android.support.transition;

import android.animation.Animator;
import android.animation.TimeInterpolator;
import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

public class Explode extends Visibility {
    private static final TimeInterpolator g = new DecelerateInterpolator();
    private static final TimeInterpolator h = new AccelerateInterpolator();
    private int[] i = new int[2];

    public Explode() {
        setPropagation(new CircularPropagation());
    }

    public Explode(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        setPropagation(new CircularPropagation());
    }

    private void b(TransitionValues transitionValues) {
        View view = transitionValues.view;
        view.getLocationOnScreen(this.i);
        int i2 = this.i[0];
        int i3 = this.i[1];
        transitionValues.values.put("android:explode:screenBounds", new Rect(i2, i3, view.getWidth() + i2, view.getHeight() + i3));
    }

    public void captureStartValues(@NonNull TransitionValues transitionValues) {
        super.captureStartValues(transitionValues);
        b(transitionValues);
    }

    public void captureEndValues(@NonNull TransitionValues transitionValues) {
        super.captureEndValues(transitionValues);
        b(transitionValues);
    }

    public Animator onAppear(ViewGroup viewGroup, View view, TransitionValues transitionValues, TransitionValues transitionValues2) {
        if (transitionValues2 == null) {
            return null;
        }
        Rect rect = (Rect) transitionValues2.values.get("android:explode:screenBounds");
        float translationX = view.getTranslationX();
        float translationY = view.getTranslationY();
        a((View) viewGroup, rect, this.i);
        return TranslationAnimationCreator.a(view, transitionValues2, rect.left, rect.top, translationX + ((float) this.i[0]), translationY + ((float) this.i[1]), translationX, translationY, g);
    }

    public Animator onDisappear(ViewGroup viewGroup, View view, TransitionValues transitionValues, TransitionValues transitionValues2) {
        float f;
        float f2;
        if (transitionValues == null) {
            return null;
        }
        Rect rect = (Rect) transitionValues.values.get("android:explode:screenBounds");
        int i2 = rect.left;
        int i3 = rect.top;
        float translationX = view.getTranslationX();
        float translationY = view.getTranslationY();
        int[] iArr = (int[]) transitionValues.view.getTag(R.id.transition_position);
        if (iArr != null) {
            f2 = ((float) (iArr[0] - rect.left)) + translationX;
            f = ((float) (iArr[1] - rect.top)) + translationY;
            rect.offsetTo(iArr[0], iArr[1]);
        } else {
            f2 = translationX;
            f = translationY;
        }
        a((View) viewGroup, rect, this.i);
        return TranslationAnimationCreator.a(view, transitionValues, i2, i3, translationX, translationY, f2 + ((float) this.i[0]), f + ((float) this.i[1]), h);
    }

    private void a(View view, Rect rect, int[] iArr) {
        int i2;
        int i3;
        View view2 = view;
        view2.getLocationOnScreen(this.i);
        int i4 = this.i[0];
        int i5 = this.i[1];
        Rect epicenter = getEpicenter();
        if (epicenter == null) {
            i3 = (view.getWidth() / 2) + i4 + Math.round(view.getTranslationX());
            i2 = (view.getHeight() / 2) + i5 + Math.round(view.getTranslationY());
        } else {
            int centerX = epicenter.centerX();
            i2 = epicenter.centerY();
            i3 = centerX;
        }
        float centerX2 = (float) (rect.centerX() - i3);
        float centerY = (float) (rect.centerY() - i2);
        if (centerX2 == BitmapDescriptorFactory.HUE_RED && centerY == BitmapDescriptorFactory.HUE_RED) {
            centerX2 = ((float) (Math.random() * 2.0d)) - 1.0f;
            centerY = ((float) (Math.random() * 2.0d)) - 1.0f;
        }
        float a = a(centerX2, centerY);
        float f = centerX2 / a;
        float f2 = centerY / a;
        float a2 = a(view2, i3 - i4, i2 - i5);
        iArr[0] = Math.round(f * a2);
        iArr[1] = Math.round(a2 * f2);
    }

    private static float a(View view, int i2, int i3) {
        return a((float) Math.max(i2, view.getWidth() - i2), (float) Math.max(i3, view.getHeight() - i3));
    }

    private static float a(float f, float f2) {
        return (float) Math.sqrt((double) ((f * f) + (f2 * f2)));
    }
}
