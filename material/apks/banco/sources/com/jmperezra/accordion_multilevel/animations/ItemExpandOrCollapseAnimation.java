package com.jmperezra.accordion_multilevel.animations;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;

public class ItemExpandOrCollapseAnimation {
    public static void expand(int i, final View view) {
        try {
            view.measure(-1, -2);
            final int measuredHeight = view.getMeasuredHeight();
            view.getLayoutParams().height = 0;
            view.setVisibility(0);
            AnonymousClass1 r1 = new Animation() {
                public boolean willChangeBounds() {
                    return true;
                }

                /* access modifiers changed from: protected */
                public void applyTransformation(float f, Transformation transformation) {
                    view.getLayoutParams().height = f == 1.0f ? -2 : (int) (((float) measuredHeight) * f);
                    view.requestLayout();
                }
            };
            r1.setDuration((long) i);
            view.startAnimation(r1);
        } catch (Throwable unused) {
        }
    }

    public static void collapse(int i, final View view) {
        try {
            final int measuredHeight = view.getMeasuredHeight();
            AnonymousClass2 r1 = new Animation() {
                public boolean willChangeBounds() {
                    return true;
                }

                /* access modifiers changed from: protected */
                public void applyTransformation(float f, Transformation transformation) {
                    if (f == 1.0f) {
                        view.setVisibility(8);
                        return;
                    }
                    view.getLayoutParams().height = measuredHeight - ((int) (((float) measuredHeight) * f));
                    view.requestLayout();
                }
            };
            r1.setDuration((long) i);
            view.startAnimation(r1);
        } catch (Throwable unused) {
        }
    }
}
