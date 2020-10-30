package android.support.v4.view.animation;

import android.view.animation.Interpolator;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

abstract class LookupTableInterpolator implements Interpolator {
    private final float[] a;
    private final float b = (1.0f / ((float) (this.a.length - 1)));

    protected LookupTableInterpolator(float[] fArr) {
        this.a = fArr;
    }

    public float getInterpolation(float f) {
        if (f >= 1.0f) {
            return 1.0f;
        }
        if (f <= BitmapDescriptorFactory.HUE_RED) {
            return BitmapDescriptorFactory.HUE_RED;
        }
        int min = Math.min((int) (((float) (this.a.length - 1)) * f), this.a.length - 2);
        return this.a[min] + (((f - (((float) min) * this.b)) / this.b) * (this.a[min + 1] - this.a[min]));
    }
}
