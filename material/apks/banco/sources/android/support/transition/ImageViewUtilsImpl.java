package android.support.transition;

import android.animation.Animator;
import android.graphics.Matrix;
import android.support.annotation.RequiresApi;
import android.widget.ImageView;

@RequiresApi(14)
interface ImageViewUtilsImpl {
    void a(ImageView imageView);

    void a(ImageView imageView, Animator animator);

    void a(ImageView imageView, Matrix matrix);
}
