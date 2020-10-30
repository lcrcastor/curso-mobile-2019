package com.nineoldandroids.animation;

import android.view.animation.Interpolator;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.util.ArrayList;

class FloatKeyframeSet extends KeyframeSet {
    private float g;
    private float h;
    private float i;
    private boolean j = true;

    public FloatKeyframeSet(FloatKeyframe... floatKeyframeArr) {
        super(floatKeyframeArr);
    }

    public Object a(float f) {
        return Float.valueOf(b(f));
    }

    /* renamed from: a */
    public FloatKeyframeSet clone() {
        ArrayList arrayList = this.e;
        int size = this.e.size();
        FloatKeyframe[] floatKeyframeArr = new FloatKeyframe[size];
        for (int i2 = 0; i2 < size; i2++) {
            floatKeyframeArr[i2] = (FloatKeyframe) ((Keyframe) arrayList.get(i2)).clone();
        }
        return new FloatKeyframeSet(floatKeyframeArr);
    }

    public float b(float f) {
        if (this.a == 2) {
            if (this.j) {
                this.j = false;
                this.g = ((FloatKeyframe) this.e.get(0)).a();
                this.h = ((FloatKeyframe) this.e.get(1)).a();
                this.i = this.h - this.g;
            }
            if (this.d != null) {
                f = this.d.getInterpolation(f);
            }
            if (this.f == null) {
                return this.g + (f * this.i);
            }
            return ((Number) this.f.evaluate(f, Float.valueOf(this.g), Float.valueOf(this.h))).floatValue();
        } else if (f <= BitmapDescriptorFactory.HUE_RED) {
            FloatKeyframe floatKeyframe = (FloatKeyframe) this.e.get(0);
            FloatKeyframe floatKeyframe2 = (FloatKeyframe) this.e.get(1);
            float a = floatKeyframe.a();
            float a2 = floatKeyframe2.a();
            float fraction = floatKeyframe.getFraction();
            float fraction2 = floatKeyframe2.getFraction();
            Interpolator interpolator = floatKeyframe2.getInterpolator();
            if (interpolator != null) {
                f = interpolator.getInterpolation(f);
            }
            float f2 = (f - fraction) / (fraction2 - fraction);
            return this.f == null ? a + (f2 * (a2 - a)) : ((Number) this.f.evaluate(f2, Float.valueOf(a), Float.valueOf(a2))).floatValue();
        } else if (f >= 1.0f) {
            FloatKeyframe floatKeyframe3 = (FloatKeyframe) this.e.get(this.a - 2);
            FloatKeyframe floatKeyframe4 = (FloatKeyframe) this.e.get(this.a - 1);
            float a3 = floatKeyframe3.a();
            float a4 = floatKeyframe4.a();
            float fraction3 = floatKeyframe3.getFraction();
            float fraction4 = floatKeyframe4.getFraction();
            Interpolator interpolator2 = floatKeyframe4.getInterpolator();
            if (interpolator2 != null) {
                f = interpolator2.getInterpolation(f);
            }
            float f3 = (f - fraction3) / (fraction4 - fraction3);
            return this.f == null ? a3 + (f3 * (a4 - a3)) : ((Number) this.f.evaluate(f3, Float.valueOf(a3), Float.valueOf(a4))).floatValue();
        } else {
            FloatKeyframe floatKeyframe5 = (FloatKeyframe) this.e.get(0);
            int i2 = 1;
            while (i2 < this.a) {
                FloatKeyframe floatKeyframe6 = (FloatKeyframe) this.e.get(i2);
                if (f < floatKeyframe6.getFraction()) {
                    Interpolator interpolator3 = floatKeyframe6.getInterpolator();
                    if (interpolator3 != null) {
                        f = interpolator3.getInterpolation(f);
                    }
                    float fraction5 = (f - floatKeyframe5.getFraction()) / (floatKeyframe6.getFraction() - floatKeyframe5.getFraction());
                    float a5 = floatKeyframe5.a();
                    float a6 = floatKeyframe6.a();
                    return this.f == null ? a5 + (fraction5 * (a6 - a5)) : ((Number) this.f.evaluate(fraction5, Float.valueOf(a5), Float.valueOf(a6))).floatValue();
                }
                i2++;
                floatKeyframe5 = floatKeyframe6;
            }
            return ((Number) ((Keyframe) this.e.get(this.a - 1)).getValue()).floatValue();
        }
    }
}
