package android.support.transition;

import android.animation.PropertyValuesHolder;
import android.graphics.Path;
import android.graphics.PointF;
import android.support.annotation.RequiresApi;
import android.util.Property;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

@RequiresApi(14)
class PropertyValuesHolderUtilsApi14 implements PropertyValuesHolderUtilsImpl {
    PropertyValuesHolderUtilsApi14() {
    }

    public PropertyValuesHolder a(Property<?, PointF> property, Path path) {
        return PropertyValuesHolder.ofFloat(new PathProperty(property, path), new float[]{BitmapDescriptorFactory.HUE_RED, 1.0f});
    }
}
