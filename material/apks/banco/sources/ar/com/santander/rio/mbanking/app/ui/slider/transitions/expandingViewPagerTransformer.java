package ar.com.santander.rio.mbanking.app.ui.slider.transitions;

import android.os.Build.VERSION;
import android.support.v4.view.ViewPager.PageTransformer;
import android.view.View;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

public class expandingViewPagerTransformer implements PageTransformer {
    public static final float MAX_SCALE = 1.0f;
    public static final float MIN_SCALE = 0.8f;

    public void transformPage(View view, float f) {
        if (f < -1.0f) {
            f = -1.0f;
        }
        if (f > 1.0f) {
            f = 1.0f;
        }
        float f2 = ((f < BitmapDescriptorFactory.HUE_RED ? f + 1.0f : 1.0f - f) * 0.19999999f) + 0.8f;
        view.setScaleX(f2);
        view.setScaleY(f2);
        if (VERSION.SDK_INT < 19) {
            view.getParent().requestLayout();
        }
    }
}
