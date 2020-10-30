package com.nineoldandroids.animation;

import android.view.animation.Interpolator;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.util.ArrayList;

class IntKeyframeSet extends KeyframeSet {
    private int g;
    private int h;
    private int i;
    private boolean j = true;

    public IntKeyframeSet(IntKeyframe... intKeyframeArr) {
        super(intKeyframeArr);
    }

    public Object a(float f) {
        return Integer.valueOf(b(f));
    }

    /* renamed from: a */
    public IntKeyframeSet clone() {
        ArrayList arrayList = this.e;
        int size = this.e.size();
        IntKeyframe[] intKeyframeArr = new IntKeyframe[size];
        for (int i2 = 0; i2 < size; i2++) {
            intKeyframeArr[i2] = (IntKeyframe) ((Keyframe) arrayList.get(i2)).clone();
        }
        return new IntKeyframeSet(intKeyframeArr);
    }

    public int b(float f) {
        if (this.a == 2) {
            if (this.j) {
                this.j = false;
                this.g = ((IntKeyframe) this.e.get(0)).a();
                this.h = ((IntKeyframe) this.e.get(1)).a();
                this.i = this.h - this.g;
            }
            if (this.d != null) {
                f = this.d.getInterpolation(f);
            }
            if (this.f == null) {
                return this.g + ((int) (f * ((float) this.i)));
            }
            return ((Number) this.f.evaluate(f, Integer.valueOf(this.g), Integer.valueOf(this.h))).intValue();
        } else if (f <= BitmapDescriptorFactory.HUE_RED) {
            IntKeyframe intKeyframe = (IntKeyframe) this.e.get(0);
            IntKeyframe intKeyframe2 = (IntKeyframe) this.e.get(1);
            int a = intKeyframe.a();
            int a2 = intKeyframe2.a();
            float fraction = intKeyframe.getFraction();
            float fraction2 = intKeyframe2.getFraction();
            Interpolator interpolator = intKeyframe2.getInterpolator();
            if (interpolator != null) {
                f = interpolator.getInterpolation(f);
            }
            float f2 = (f - fraction) / (fraction2 - fraction);
            return this.f == null ? a + ((int) (f2 * ((float) (a2 - a)))) : ((Number) this.f.evaluate(f2, Integer.valueOf(a), Integer.valueOf(a2))).intValue();
        } else if (f >= 1.0f) {
            IntKeyframe intKeyframe3 = (IntKeyframe) this.e.get(this.a - 2);
            IntKeyframe intKeyframe4 = (IntKeyframe) this.e.get(this.a - 1);
            int a3 = intKeyframe3.a();
            int a4 = intKeyframe4.a();
            float fraction3 = intKeyframe3.getFraction();
            float fraction4 = intKeyframe4.getFraction();
            Interpolator interpolator2 = intKeyframe4.getInterpolator();
            if (interpolator2 != null) {
                f = interpolator2.getInterpolation(f);
            }
            float f3 = (f - fraction3) / (fraction4 - fraction3);
            return this.f == null ? a3 + ((int) (f3 * ((float) (a4 - a3)))) : ((Number) this.f.evaluate(f3, Integer.valueOf(a3), Integer.valueOf(a4))).intValue();
        } else {
            IntKeyframe intKeyframe5 = (IntKeyframe) this.e.get(0);
            int i2 = 1;
            while (i2 < this.a) {
                IntKeyframe intKeyframe6 = (IntKeyframe) this.e.get(i2);
                if (f < intKeyframe6.getFraction()) {
                    Interpolator interpolator3 = intKeyframe6.getInterpolator();
                    if (interpolator3 != null) {
                        f = interpolator3.getInterpolation(f);
                    }
                    float fraction5 = (f - intKeyframe5.getFraction()) / (intKeyframe6.getFraction() - intKeyframe5.getFraction());
                    int a5 = intKeyframe5.a();
                    int a6 = intKeyframe6.a();
                    return this.f == null ? a5 + ((int) (fraction5 * ((float) (a6 - a5)))) : ((Number) this.f.evaluate(fraction5, Integer.valueOf(a5), Integer.valueOf(a6))).intValue();
                }
                i2++;
                intKeyframe5 = intKeyframe6;
            }
            return ((Number) ((Keyframe) this.e.get(this.a - 1)).getValue()).intValue();
        }
    }
}
