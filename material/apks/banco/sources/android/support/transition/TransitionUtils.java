package android.support.transition;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.TypeEvaluator;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

class TransitionUtils {

    static class MatrixEvaluator implements TypeEvaluator<Matrix> {
        final float[] a = new float[9];
        final float[] b = new float[9];
        final Matrix c = new Matrix();

        MatrixEvaluator() {
        }

        /* renamed from: a */
        public Matrix evaluate(float f, Matrix matrix, Matrix matrix2) {
            matrix.getValues(this.a);
            matrix2.getValues(this.b);
            for (int i = 0; i < 9; i++) {
                this.b[i] = this.a[i] + ((this.b[i] - this.a[i]) * f);
            }
            this.c.setValues(this.b);
            return this.c;
        }
    }

    TransitionUtils() {
    }

    static View a(ViewGroup viewGroup, View view, View view2) {
        Matrix matrix = new Matrix();
        matrix.setTranslate((float) (-view2.getScrollX()), (float) (-view2.getScrollY()));
        ViewUtils.a(view, matrix);
        ViewUtils.b(viewGroup, matrix);
        RectF rectF = new RectF(BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, (float) view.getWidth(), (float) view.getHeight());
        matrix.mapRect(rectF);
        int round = Math.round(rectF.left);
        int round2 = Math.round(rectF.top);
        int round3 = Math.round(rectF.right);
        int round4 = Math.round(rectF.bottom);
        ImageView imageView = new ImageView(view.getContext());
        imageView.setScaleType(ScaleType.CENTER_CROP);
        Bitmap a = a(view, matrix, rectF);
        if (a != null) {
            imageView.setImageBitmap(a);
        }
        imageView.measure(MeasureSpec.makeMeasureSpec(round3 - round, 1073741824), MeasureSpec.makeMeasureSpec(round4 - round2, 1073741824));
        imageView.layout(round, round2, round3, round4);
        return imageView;
    }

    private static Bitmap a(View view, Matrix matrix, RectF rectF) {
        int round = Math.round(rectF.width());
        int round2 = Math.round(rectF.height());
        if (round <= 0 || round2 <= 0) {
            return null;
        }
        float min = Math.min(1.0f, 1048576.0f / ((float) (round * round2)));
        int i = (int) (((float) round) * min);
        int i2 = (int) (((float) round2) * min);
        matrix.postTranslate(-rectF.left, -rectF.top);
        matrix.postScale(min, min);
        Bitmap createBitmap = Bitmap.createBitmap(i, i2, Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        canvas.concat(matrix);
        view.draw(canvas);
        return createBitmap;
    }

    static Animator a(Animator animator, Animator animator2) {
        if (animator == null) {
            return animator2;
        }
        if (animator2 == null) {
            return animator;
        }
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(new Animator[]{animator, animator2});
        return animatorSet;
    }
}
