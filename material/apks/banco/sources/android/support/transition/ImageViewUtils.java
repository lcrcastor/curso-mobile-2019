package android.support.transition;

import android.animation.Animator;
import android.graphics.Matrix;
import android.os.Build.VERSION;
import android.widget.ImageView;

class ImageViewUtils {
    private static final ImageViewUtilsImpl a;

    ImageViewUtils() {
    }

    static {
        if (VERSION.SDK_INT >= 21) {
            a = new ImageViewUtilsApi21();
        } else {
            a = new ImageViewUtilsApi14();
        }
    }

    static void a(ImageView imageView) {
        a.a(imageView);
    }

    static void a(ImageView imageView, Matrix matrix) {
        a.a(imageView, matrix);
    }

    static void a(ImageView imageView, Animator animator) {
        a.a(imageView, animator);
    }
}
