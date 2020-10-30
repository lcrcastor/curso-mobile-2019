package android.support.transition;

import android.animation.Animator;
import android.animation.TimeInterpolator;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.v4.content.res.TypedArrayUtils;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import org.xmlpull.v1.XmlPullParser;

public class Slide extends Visibility {
    private static final TimeInterpolator g = new DecelerateInterpolator();
    private static final TimeInterpolator h = new AccelerateInterpolator();
    private static final CalculateSlide k = new CalculateSlideHorizontal() {
        public float a(ViewGroup viewGroup, View view) {
            return view.getTranslationX() - ((float) viewGroup.getWidth());
        }
    };
    private static final CalculateSlide l = new CalculateSlideHorizontal() {
        public float a(ViewGroup viewGroup, View view) {
            boolean z = true;
            if (ViewCompat.getLayoutDirection(viewGroup) != 1) {
                z = false;
            }
            if (z) {
                return view.getTranslationX() + ((float) viewGroup.getWidth());
            }
            return view.getTranslationX() - ((float) viewGroup.getWidth());
        }
    };
    private static final CalculateSlide m = new CalculateSlideVertical() {
        public float b(ViewGroup viewGroup, View view) {
            return view.getTranslationY() - ((float) viewGroup.getHeight());
        }
    };
    private static final CalculateSlide n = new CalculateSlideHorizontal() {
        public float a(ViewGroup viewGroup, View view) {
            return view.getTranslationX() + ((float) viewGroup.getWidth());
        }
    };
    private static final CalculateSlide o = new CalculateSlideHorizontal() {
        public float a(ViewGroup viewGroup, View view) {
            boolean z = true;
            if (ViewCompat.getLayoutDirection(viewGroup) != 1) {
                z = false;
            }
            if (z) {
                return view.getTranslationX() - ((float) viewGroup.getWidth());
            }
            return view.getTranslationX() + ((float) viewGroup.getWidth());
        }
    };
    private static final CalculateSlide p = new CalculateSlideVertical() {
        public float b(ViewGroup viewGroup, View view) {
            return view.getTranslationY() + ((float) viewGroup.getHeight());
        }
    };
    private CalculateSlide i = p;
    private int j = 80;

    interface CalculateSlide {
        float a(ViewGroup viewGroup, View view);

        float b(ViewGroup viewGroup, View view);
    }

    static abstract class CalculateSlideHorizontal implements CalculateSlide {
        private CalculateSlideHorizontal() {
        }

        public float b(ViewGroup viewGroup, View view) {
            return view.getTranslationY();
        }
    }

    static abstract class CalculateSlideVertical implements CalculateSlide {
        private CalculateSlideVertical() {
        }

        public float a(ViewGroup viewGroup, View view) {
            return view.getTranslationX();
        }
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    @Retention(RetentionPolicy.SOURCE)
    public @interface GravityFlag {
    }

    public Slide() {
        setSlideEdge(80);
    }

    public Slide(int i2) {
        setSlideEdge(i2);
    }

    public Slide(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, Styleable.h);
        int namedInt = TypedArrayUtils.getNamedInt(obtainStyledAttributes, (XmlPullParser) attributeSet, "slideEdge", 0, 80);
        obtainStyledAttributes.recycle();
        setSlideEdge(namedInt);
    }

    private void b(TransitionValues transitionValues) {
        int[] iArr = new int[2];
        transitionValues.view.getLocationOnScreen(iArr);
        transitionValues.values.put("android:slide:screenPosition", iArr);
    }

    public void captureStartValues(@NonNull TransitionValues transitionValues) {
        super.captureStartValues(transitionValues);
        b(transitionValues);
    }

    public void captureEndValues(@NonNull TransitionValues transitionValues) {
        super.captureEndValues(transitionValues);
        b(transitionValues);
    }

    public void setSlideEdge(int i2) {
        if (i2 == 3) {
            this.i = k;
        } else if (i2 == 5) {
            this.i = n;
        } else if (i2 == 48) {
            this.i = m;
        } else if (i2 == 80) {
            this.i = p;
        } else if (i2 == 8388611) {
            this.i = l;
        } else if (i2 != 8388613) {
            throw new IllegalArgumentException("Invalid slide direction");
        } else {
            this.i = o;
        }
        this.j = i2;
        SidePropagation sidePropagation = new SidePropagation();
        sidePropagation.setSide(i2);
        setPropagation(sidePropagation);
    }

    public int getSlideEdge() {
        return this.j;
    }

    public Animator onAppear(ViewGroup viewGroup, View view, TransitionValues transitionValues, TransitionValues transitionValues2) {
        if (transitionValues2 == null) {
            return null;
        }
        int[] iArr = (int[]) transitionValues2.values.get("android:slide:screenPosition");
        float translationX = view.getTranslationX();
        float translationY = view.getTranslationY();
        return TranslationAnimationCreator.a(view, transitionValues2, iArr[0], iArr[1], this.i.a(viewGroup, view), this.i.b(viewGroup, view), translationX, translationY, g);
    }

    public Animator onDisappear(ViewGroup viewGroup, View view, TransitionValues transitionValues, TransitionValues transitionValues2) {
        if (transitionValues == null) {
            return null;
        }
        int[] iArr = (int[]) transitionValues.values.get("android:slide:screenPosition");
        return TranslationAnimationCreator.a(view, transitionValues, iArr[0], iArr[1], view.getTranslationX(), view.getTranslationY(), this.i.a(viewGroup, view), this.i.b(viewGroup, view), h);
    }
}
