package ar.com.santander.rio.mbanking.app.ui.slider.transitions;

import android.support.v4.view.ViewPager.PageTransformer;
import android.view.View;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

public class cubeOut implements PageTransformer {
    public void transformPage(View view, float f) {
        float f2 = BitmapDescriptorFactory.HUE_RED;
        float abs = (f < BitmapDescriptorFactory.HUE_RED ? 90.0f : -90.0f) * Math.abs(f);
        view.setAlpha((abs > 90.0f || abs < -90.0f) ? BitmapDescriptorFactory.HUE_RED : 1.0f);
        if (f < BitmapDescriptorFactory.HUE_RED) {
            f2 = (float) view.getWidth();
        }
        view.setPivotX(f2);
        view.setPivotY(((float) view.getHeight()) * 0.5f);
        view.setRotationY(f * 90.0f);
    }
}
