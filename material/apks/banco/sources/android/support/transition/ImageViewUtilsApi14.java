package android.support.transition;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.graphics.Matrix;
import android.support.annotation.RequiresApi;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

@RequiresApi(14)
class ImageViewUtilsApi14 implements ImageViewUtilsImpl {
    ImageViewUtilsApi14() {
    }

    public void a(ImageView imageView) {
        ScaleType scaleType = imageView.getScaleType();
        imageView.setTag(R.id.save_scale_type, scaleType);
        if (scaleType == ScaleType.MATRIX) {
            imageView.setTag(R.id.save_image_matrix, imageView.getImageMatrix());
        } else {
            imageView.setScaleType(ScaleType.MATRIX);
        }
        imageView.setImageMatrix(MatrixUtils.a);
    }

    public void a(ImageView imageView, Matrix matrix) {
        imageView.setImageMatrix(matrix);
    }

    public void a(final ImageView imageView, Animator animator) {
        animator.addListener(new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator animator) {
                ScaleType scaleType = (ScaleType) imageView.getTag(R.id.save_scale_type);
                imageView.setScaleType(scaleType);
                imageView.setTag(R.id.save_scale_type, null);
                if (scaleType == ScaleType.MATRIX) {
                    imageView.setImageMatrix((Matrix) imageView.getTag(R.id.save_image_matrix));
                    imageView.setTag(R.id.save_image_matrix, null);
                }
                animator.removeListener(this);
            }
        });
    }
}
