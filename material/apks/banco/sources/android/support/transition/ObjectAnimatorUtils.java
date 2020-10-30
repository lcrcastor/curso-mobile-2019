package android.support.transition;

import android.animation.ObjectAnimator;
import android.graphics.Path;
import android.graphics.PointF;
import android.os.Build.VERSION;
import android.util.Property;

class ObjectAnimatorUtils {
    private static final ObjectAnimatorUtilsImpl a;

    ObjectAnimatorUtils() {
    }

    static {
        if (VERSION.SDK_INT >= 21) {
            a = new ObjectAnimatorUtilsApi21();
        } else {
            a = new ObjectAnimatorUtilsApi14();
        }
    }

    static <T> ObjectAnimator a(T t, Property<T, PointF> property, Path path) {
        return a.a(t, property, path);
    }
}
