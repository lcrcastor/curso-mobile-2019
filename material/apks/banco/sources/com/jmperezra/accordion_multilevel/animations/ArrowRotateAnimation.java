package com.jmperezra.accordion_multilevel.animations;

import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;

public class ArrowRotateAnimation {
    public static RotateAnimation turnArrow(int i, int i2, int i3) {
        RotateAnimation rotateAnimation = new RotateAnimation((float) i2, (float) i3, 1, 0.5f, 1, 0.5f);
        rotateAnimation.setInterpolator(new LinearInterpolator());
        rotateAnimation.setDuration((long) i);
        rotateAnimation.setFillEnabled(true);
        rotateAnimation.setFillAfter(true);
        return rotateAnimation;
    }
}
