package com.nineoldandroids.animation;

import android.view.animation.Interpolator;

public abstract class Keyframe implements Cloneable {
    float a;
    Class b;
    boolean c = false;
    private Interpolator d = null;

    static class FloatKeyframe extends Keyframe {
        float d;

        FloatKeyframe(float f, float f2) {
            this.a = f;
            this.d = f2;
            this.b = Float.TYPE;
            this.c = true;
        }

        FloatKeyframe(float f) {
            this.a = f;
            this.b = Float.TYPE;
        }

        public float a() {
            return this.d;
        }

        public Object getValue() {
            return Float.valueOf(this.d);
        }

        public void setValue(Object obj) {
            if (obj != null && obj.getClass() == Float.class) {
                this.d = ((Float) obj).floatValue();
                this.c = true;
            }
        }

        /* renamed from: b */
        public FloatKeyframe clone() {
            FloatKeyframe floatKeyframe = new FloatKeyframe(getFraction(), this.d);
            floatKeyframe.setInterpolator(getInterpolator());
            return floatKeyframe;
        }
    }

    static class IntKeyframe extends Keyframe {
        int d;

        IntKeyframe(float f, int i) {
            this.a = f;
            this.d = i;
            this.b = Integer.TYPE;
            this.c = true;
        }

        IntKeyframe(float f) {
            this.a = f;
            this.b = Integer.TYPE;
        }

        public int a() {
            return this.d;
        }

        public Object getValue() {
            return Integer.valueOf(this.d);
        }

        public void setValue(Object obj) {
            if (obj != null && obj.getClass() == Integer.class) {
                this.d = ((Integer) obj).intValue();
                this.c = true;
            }
        }

        /* renamed from: b */
        public IntKeyframe clone() {
            IntKeyframe intKeyframe = new IntKeyframe(getFraction(), this.d);
            intKeyframe.setInterpolator(getInterpolator());
            return intKeyframe;
        }
    }

    static class ObjectKeyframe extends Keyframe {
        Object d;

        ObjectKeyframe(float f, Object obj) {
            this.a = f;
            this.d = obj;
            this.c = obj != null;
            this.b = this.c ? obj.getClass() : Object.class;
        }

        public Object getValue() {
            return this.d;
        }

        public void setValue(Object obj) {
            this.d = obj;
            this.c = obj != null;
        }

        /* renamed from: a */
        public ObjectKeyframe clone() {
            ObjectKeyframe objectKeyframe = new ObjectKeyframe(getFraction(), this.d);
            objectKeyframe.setInterpolator(getInterpolator());
            return objectKeyframe;
        }
    }

    public abstract Keyframe clone();

    public abstract Object getValue();

    public abstract void setValue(Object obj);

    public static Keyframe ofInt(float f, int i) {
        return new IntKeyframe(f, i);
    }

    public static Keyframe ofInt(float f) {
        return new IntKeyframe(f);
    }

    public static Keyframe ofFloat(float f, float f2) {
        return new FloatKeyframe(f, f2);
    }

    public static Keyframe ofFloat(float f) {
        return new FloatKeyframe(f);
    }

    public static Keyframe ofObject(float f, Object obj) {
        return new ObjectKeyframe(f, obj);
    }

    public static Keyframe ofObject(float f) {
        return new ObjectKeyframe(f, null);
    }

    public boolean hasValue() {
        return this.c;
    }

    public float getFraction() {
        return this.a;
    }

    public void setFraction(float f) {
        this.a = f;
    }

    public Interpolator getInterpolator() {
        return this.d;
    }

    public void setInterpolator(Interpolator interpolator) {
        this.d = interpolator;
    }

    public Class getType() {
        return this.b;
    }
}
