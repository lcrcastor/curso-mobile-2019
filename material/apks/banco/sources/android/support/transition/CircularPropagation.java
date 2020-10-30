package android.support.transition;

import android.graphics.Rect;
import android.view.ViewGroup;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

public class CircularPropagation extends VisibilityPropagation {
    private float a = 3.0f;

    public void setPropagationSpeed(float f) {
        if (f == BitmapDescriptorFactory.HUE_RED) {
            throw new IllegalArgumentException("propagationSpeed may not be 0");
        }
        this.a = f;
    }

    public long getStartDelay(ViewGroup viewGroup, Transition transition, TransitionValues transitionValues, TransitionValues transitionValues2) {
        int i;
        int i2;
        int i3;
        if (transitionValues == null && transitionValues2 == null) {
            return 0;
        }
        if (transitionValues2 == null || getViewVisibility(transitionValues) == 0) {
            i = -1;
        } else {
            transitionValues = transitionValues2;
            i = 1;
        }
        int viewX = getViewX(transitionValues);
        int viewY = getViewY(transitionValues);
        Rect epicenter = transition.getEpicenter();
        if (epicenter != null) {
            i3 = epicenter.centerX();
            i2 = epicenter.centerY();
        } else {
            int[] iArr = new int[2];
            viewGroup.getLocationOnScreen(iArr);
            int round = Math.round(((float) (iArr[0] + (viewGroup.getWidth() / 2))) + viewGroup.getTranslationX());
            i2 = Math.round(((float) (iArr[1] + (viewGroup.getHeight() / 2))) + viewGroup.getTranslationY());
            i3 = round;
        }
        float a2 = a((float) viewX, (float) viewY, (float) i3, (float) i2) / a(BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, (float) viewGroup.getWidth(), (float) viewGroup.getHeight());
        long duration = transition.getDuration();
        if (duration < 0) {
            duration = 300;
        }
        return (long) Math.round((((float) (duration * ((long) i))) / this.a) * a2);
    }

    private static float a(float f, float f2, float f3, float f4) {
        float f5 = f3 - f;
        float f6 = f4 - f2;
        return (float) Math.sqrt((double) ((f5 * f5) + (f6 * f6)));
    }
}
