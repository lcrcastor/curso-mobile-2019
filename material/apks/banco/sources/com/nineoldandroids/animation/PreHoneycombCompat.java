package com.nineoldandroids.animation;

import android.view.View;
import com.nineoldandroids.util.FloatProperty;
import com.nineoldandroids.util.IntProperty;
import com.nineoldandroids.util.Property;
import com.nineoldandroids.view.animation.AnimatorProxy;

final class PreHoneycombCompat {
    static Property<View, Float> a = new FloatProperty<View>("alpha") {
        /* renamed from: a */
        public void setValue(View view, float f) {
            AnimatorProxy.wrap(view).setAlpha(f);
        }

        /* renamed from: a */
        public Float get(View view) {
            return Float.valueOf(AnimatorProxy.wrap(view).getAlpha());
        }
    };
    static Property<View, Float> b = new FloatProperty<View>("pivotX") {
        /* renamed from: a */
        public void setValue(View view, float f) {
            AnimatorProxy.wrap(view).setPivotX(f);
        }

        /* renamed from: a */
        public Float get(View view) {
            return Float.valueOf(AnimatorProxy.wrap(view).getPivotX());
        }
    };
    static Property<View, Float> c = new FloatProperty<View>("pivotY") {
        /* renamed from: a */
        public void setValue(View view, float f) {
            AnimatorProxy.wrap(view).setPivotY(f);
        }

        /* renamed from: a */
        public Float get(View view) {
            return Float.valueOf(AnimatorProxy.wrap(view).getPivotY());
        }
    };
    static Property<View, Float> d = new FloatProperty<View>("translationX") {
        /* renamed from: a */
        public void setValue(View view, float f) {
            AnimatorProxy.wrap(view).setTranslationX(f);
        }

        /* renamed from: a */
        public Float get(View view) {
            return Float.valueOf(AnimatorProxy.wrap(view).getTranslationX());
        }
    };
    static Property<View, Float> e = new FloatProperty<View>("translationY") {
        /* renamed from: a */
        public void setValue(View view, float f) {
            AnimatorProxy.wrap(view).setTranslationY(f);
        }

        /* renamed from: a */
        public Float get(View view) {
            return Float.valueOf(AnimatorProxy.wrap(view).getTranslationY());
        }
    };
    static Property<View, Float> f = new FloatProperty<View>("rotation") {
        /* renamed from: a */
        public void setValue(View view, float f) {
            AnimatorProxy.wrap(view).setRotation(f);
        }

        /* renamed from: a */
        public Float get(View view) {
            return Float.valueOf(AnimatorProxy.wrap(view).getRotation());
        }
    };
    static Property<View, Float> g = new FloatProperty<View>("rotationX") {
        /* renamed from: a */
        public void setValue(View view, float f) {
            AnimatorProxy.wrap(view).setRotationX(f);
        }

        /* renamed from: a */
        public Float get(View view) {
            return Float.valueOf(AnimatorProxy.wrap(view).getRotationX());
        }
    };
    static Property<View, Float> h = new FloatProperty<View>("rotationY") {
        /* renamed from: a */
        public void setValue(View view, float f) {
            AnimatorProxy.wrap(view).setRotationY(f);
        }

        /* renamed from: a */
        public Float get(View view) {
            return Float.valueOf(AnimatorProxy.wrap(view).getRotationY());
        }
    };
    static Property<View, Float> i = new FloatProperty<View>("scaleX") {
        /* renamed from: a */
        public void setValue(View view, float f) {
            AnimatorProxy.wrap(view).setScaleX(f);
        }

        /* renamed from: a */
        public Float get(View view) {
            return Float.valueOf(AnimatorProxy.wrap(view).getScaleX());
        }
    };
    static Property<View, Float> j = new FloatProperty<View>("scaleY") {
        /* renamed from: a */
        public void setValue(View view, float f) {
            AnimatorProxy.wrap(view).setScaleY(f);
        }

        /* renamed from: a */
        public Float get(View view) {
            return Float.valueOf(AnimatorProxy.wrap(view).getScaleY());
        }
    };
    static Property<View, Integer> k = new IntProperty<View>("scrollX") {
        /* renamed from: a */
        public void setValue(View view, int i) {
            AnimatorProxy.wrap(view).setScrollX(i);
        }

        /* renamed from: a */
        public Integer get(View view) {
            return Integer.valueOf(AnimatorProxy.wrap(view).getScrollX());
        }
    };
    static Property<View, Integer> l = new IntProperty<View>("scrollY") {
        /* renamed from: a */
        public void setValue(View view, int i) {
            AnimatorProxy.wrap(view).setScrollY(i);
        }

        /* renamed from: a */
        public Integer get(View view) {
            return Integer.valueOf(AnimatorProxy.wrap(view).getScrollY());
        }
    };
    static Property<View, Float> m = new FloatProperty<View>("x") {
        /* renamed from: a */
        public void setValue(View view, float f) {
            AnimatorProxy.wrap(view).setX(f);
        }

        /* renamed from: a */
        public Float get(View view) {
            return Float.valueOf(AnimatorProxy.wrap(view).getX());
        }
    };
    static Property<View, Float> n = new FloatProperty<View>("y") {
        /* renamed from: a */
        public void setValue(View view, float f) {
            AnimatorProxy.wrap(view).setY(f);
        }

        /* renamed from: a */
        public Float get(View view) {
            return Float.valueOf(AnimatorProxy.wrap(view).getY());
        }
    };

    private PreHoneycombCompat() {
    }
}
