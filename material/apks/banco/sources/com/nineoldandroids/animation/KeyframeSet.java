package com.nineoldandroids.animation;

import android.view.animation.Interpolator;
import ar.com.santander.rio.mbanking.utils.UtilsCuentas;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.util.ArrayList;
import java.util.Arrays;

class KeyframeSet {
    int a;
    Keyframe b;
    Keyframe c;
    Interpolator d;
    ArrayList<Keyframe> e = new ArrayList<>();
    TypeEvaluator f;

    public KeyframeSet(Keyframe... keyframeArr) {
        this.a = keyframeArr.length;
        this.e.addAll(Arrays.asList(keyframeArr));
        this.b = (Keyframe) this.e.get(0);
        this.c = (Keyframe) this.e.get(this.a - 1);
        this.d = this.c.getInterpolator();
    }

    public static KeyframeSet a(int... iArr) {
        int length = iArr.length;
        IntKeyframe[] intKeyframeArr = new IntKeyframe[Math.max(length, 2)];
        if (length == 1) {
            intKeyframeArr[0] = (IntKeyframe) Keyframe.ofInt(BitmapDescriptorFactory.HUE_RED);
            intKeyframeArr[1] = (IntKeyframe) Keyframe.ofInt(1.0f, iArr[0]);
        } else {
            intKeyframeArr[0] = (IntKeyframe) Keyframe.ofInt(BitmapDescriptorFactory.HUE_RED, iArr[0]);
            for (int i = 1; i < length; i++) {
                intKeyframeArr[i] = (IntKeyframe) Keyframe.ofInt(((float) i) / ((float) (length - 1)), iArr[i]);
            }
        }
        return new IntKeyframeSet(intKeyframeArr);
    }

    public static KeyframeSet a(float... fArr) {
        int length = fArr.length;
        FloatKeyframe[] floatKeyframeArr = new FloatKeyframe[Math.max(length, 2)];
        if (length == 1) {
            floatKeyframeArr[0] = (FloatKeyframe) Keyframe.ofFloat(BitmapDescriptorFactory.HUE_RED);
            floatKeyframeArr[1] = (FloatKeyframe) Keyframe.ofFloat(1.0f, fArr[0]);
        } else {
            floatKeyframeArr[0] = (FloatKeyframe) Keyframe.ofFloat(BitmapDescriptorFactory.HUE_RED, fArr[0]);
            for (int i = 1; i < length; i++) {
                floatKeyframeArr[i] = (FloatKeyframe) Keyframe.ofFloat(((float) i) / ((float) (length - 1)), fArr[i]);
            }
        }
        return new FloatKeyframeSet(floatKeyframeArr);
    }

    public static KeyframeSet a(Keyframe... keyframeArr) {
        int length = keyframeArr.length;
        int i = 0;
        boolean z = false;
        boolean z2 = false;
        boolean z3 = false;
        for (int i2 = 0; i2 < length; i2++) {
            if (keyframeArr[i2] instanceof FloatKeyframe) {
                z = true;
            } else if (keyframeArr[i2] instanceof IntKeyframe) {
                z2 = true;
            } else {
                z3 = true;
            }
        }
        if (z && !z2 && !z3) {
            FloatKeyframe[] floatKeyframeArr = new FloatKeyframe[length];
            while (i < length) {
                floatKeyframeArr[i] = keyframeArr[i];
                i++;
            }
            return new FloatKeyframeSet(floatKeyframeArr);
        } else if (!z2 || z || z3) {
            return new KeyframeSet(keyframeArr);
        } else {
            IntKeyframe[] intKeyframeArr = new IntKeyframe[length];
            while (i < length) {
                intKeyframeArr[i] = keyframeArr[i];
                i++;
            }
            return new IntKeyframeSet(intKeyframeArr);
        }
    }

    public static KeyframeSet a(Object... objArr) {
        int length = objArr.length;
        ObjectKeyframe[] objectKeyframeArr = new ObjectKeyframe[Math.max(length, 2)];
        if (length == 1) {
            objectKeyframeArr[0] = (ObjectKeyframe) Keyframe.ofObject(BitmapDescriptorFactory.HUE_RED);
            objectKeyframeArr[1] = (ObjectKeyframe) Keyframe.ofObject(1.0f, objArr[0]);
        } else {
            objectKeyframeArr[0] = (ObjectKeyframe) Keyframe.ofObject(BitmapDescriptorFactory.HUE_RED, objArr[0]);
            for (int i = 1; i < length; i++) {
                objectKeyframeArr[i] = (ObjectKeyframe) Keyframe.ofObject(((float) i) / ((float) (length - 1)), objArr[i]);
            }
        }
        return new KeyframeSet(objectKeyframeArr);
    }

    public void a(TypeEvaluator typeEvaluator) {
        this.f = typeEvaluator;
    }

    /* renamed from: b */
    public KeyframeSet clone() {
        ArrayList<Keyframe> arrayList = this.e;
        int size = this.e.size();
        Keyframe[] keyframeArr = new Keyframe[size];
        for (int i = 0; i < size; i++) {
            keyframeArr[i] = ((Keyframe) arrayList.get(i)).clone();
        }
        return new KeyframeSet(keyframeArr);
    }

    public Object a(float f2) {
        if (this.a == 2) {
            if (this.d != null) {
                f2 = this.d.getInterpolation(f2);
            }
            return this.f.evaluate(f2, this.b.getValue(), this.c.getValue());
        }
        int i = 1;
        if (f2 <= BitmapDescriptorFactory.HUE_RED) {
            Keyframe keyframe = (Keyframe) this.e.get(1);
            Interpolator interpolator = keyframe.getInterpolator();
            if (interpolator != null) {
                f2 = interpolator.getInterpolation(f2);
            }
            float fraction = this.b.getFraction();
            return this.f.evaluate((f2 - fraction) / (keyframe.getFraction() - fraction), this.b.getValue(), keyframe.getValue());
        } else if (f2 >= 1.0f) {
            Keyframe keyframe2 = (Keyframe) this.e.get(this.a - 2);
            Interpolator interpolator2 = this.c.getInterpolator();
            if (interpolator2 != null) {
                f2 = interpolator2.getInterpolation(f2);
            }
            float fraction2 = keyframe2.getFraction();
            return this.f.evaluate((f2 - fraction2) / (this.c.getFraction() - fraction2), keyframe2.getValue(), this.c.getValue());
        } else {
            Keyframe keyframe3 = this.b;
            while (i < this.a) {
                Keyframe keyframe4 = (Keyframe) this.e.get(i);
                if (f2 < keyframe4.getFraction()) {
                    Interpolator interpolator3 = keyframe4.getInterpolator();
                    if (interpolator3 != null) {
                        f2 = interpolator3.getInterpolation(f2);
                    }
                    float fraction3 = keyframe3.getFraction();
                    return this.f.evaluate((f2 - fraction3) / (keyframe4.getFraction() - fraction3), keyframe3.getValue(), keyframe4.getValue());
                }
                i++;
                keyframe3 = keyframe4;
            }
            return this.c.getValue();
        }
    }

    public String toString() {
        String str = UtilsCuentas.SEPARAOR2;
        for (int i = 0; i < this.a; i++) {
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append(((Keyframe) this.e.get(i)).getValue());
            sb.append("  ");
            str = sb.toString();
        }
        return str;
    }
}
