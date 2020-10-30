package android.support.transition;

import android.animation.Animator;
import android.graphics.Matrix;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.widget.ImageView;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@RequiresApi(21)
class ImageViewUtilsApi21 implements ImageViewUtilsImpl {
    private static Method a;
    private static boolean b;

    public void a(ImageView imageView) {
    }

    public void a(ImageView imageView, Animator animator) {
    }

    ImageViewUtilsApi21() {
    }

    public void a(ImageView imageView, Matrix matrix) {
        a();
        if (a != null) {
            try {
                a.invoke(imageView, new Object[]{matrix});
            } catch (IllegalAccessException unused) {
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e.getCause());
            }
        }
    }

    private void a() {
        if (!b) {
            try {
                a = ImageView.class.getDeclaredMethod("animateTransform", new Class[]{Matrix.class});
                a.setAccessible(true);
            } catch (NoSuchMethodException e) {
                Log.i("ImageViewUtilsApi21", "Failed to retrieve animateTransform method", e);
            }
            b = true;
        }
    }
}
