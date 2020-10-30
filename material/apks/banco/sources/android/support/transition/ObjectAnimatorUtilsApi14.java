package android.support.transition;

import android.animation.ObjectAnimator;
import android.graphics.Path;
import android.graphics.PointF;
import android.support.annotation.RequiresApi;
import android.util.Property;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

@RequiresApi(14)
class ObjectAnimatorUtilsApi14 implements ObjectAnimatorUtilsImpl {
    ObjectAnimatorUtilsApi14() {
    }

    public <T> ObjectAnimator a(T t, Property<T, PointF> property, Path path) {
        return ObjectAnimator.ofFloat(t, new PathProperty(property, path), new float[]{BitmapDescriptorFactory.HUE_RED, 1.0f});
    }
}
