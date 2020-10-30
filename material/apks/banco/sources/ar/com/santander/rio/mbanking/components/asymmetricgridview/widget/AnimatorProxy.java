package ar.com.santander.rio.mbanking.components.asymmetricgridview.widget;

import android.graphics.Matrix;
import android.graphics.RectF;
import android.os.Build.VERSION;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.lang.ref.WeakReference;
import java.util.WeakHashMap;

public final class AnimatorProxy extends Animation {
    public static final boolean NEEDS_PROXY = (VERSION.SDK_INT < 11);
    private static final WeakHashMap<View, AnimatorProxy> a = new WeakHashMap<>();
    private final WeakReference<View> b;
    private final RectF c = new RectF();
    private final RectF d = new RectF();
    private final Matrix e = new Matrix();
    private float f = 1.0f;
    private float g = 1.0f;
    private float h = 1.0f;
    private float i;
    private float j;

    public void reset() {
    }

    private AnimatorProxy(View view) {
        setDuration(0);
        setFillAfter(true);
        view.setAnimation(this);
        this.b = new WeakReference<>(view);
    }

    public static AnimatorProxy wrap(View view) {
        AnimatorProxy animatorProxy = (AnimatorProxy) a.get(view);
        if (animatorProxy != null) {
            return animatorProxy;
        }
        AnimatorProxy animatorProxy2 = new AnimatorProxy(view);
        a.put(view, animatorProxy2);
        return animatorProxy2;
    }

    public float getAlpha() {
        return this.f;
    }

    public void setAlpha(float f2) {
        if (this.f != f2) {
            this.f = f2;
            View view = (View) this.b.get();
            if (view != null) {
                view.invalidate();
            }
        }
    }

    public float getScaleX() {
        return this.g;
    }

    public void setScaleX(float f2) {
        if (this.g != f2) {
            a();
            this.g = f2;
            b();
        }
    }

    public float getScaleY() {
        return this.h;
    }

    public void setScaleY(float f2) {
        if (this.h != f2) {
            a();
            this.h = f2;
            b();
        }
    }

    public int getScrollX() {
        View view = (View) this.b.get();
        if (view == null) {
            return 0;
        }
        return view.getScrollX();
    }

    public void setScrollX(int i2) {
        View view = (View) this.b.get();
        if (view != null) {
            view.scrollTo(i2, view.getScrollY());
        }
    }

    public int getScrollY() {
        View view = (View) this.b.get();
        if (view == null) {
            return 0;
        }
        return view.getScrollY();
    }

    public void setScrollY(int i2) {
        View view = (View) this.b.get();
        if (view != null) {
            view.scrollTo(view.getScrollY(), i2);
        }
    }

    public float getTranslationX() {
        return this.i;
    }

    public void setTranslationX(float f2) {
        if (this.i != f2) {
            a();
            this.i = f2;
            b();
        }
    }

    public float getTranslationY() {
        return this.j;
    }

    public void setTranslationY(float f2) {
        if (this.j != f2) {
            a();
            this.j = f2;
            b();
        }
    }

    private void a() {
        View view = (View) this.b.get();
        if (view != null) {
            a(this.c, view);
        }
    }

    private void b() {
        View view = (View) this.b.get();
        if (view != null) {
            View view2 = (View) view.getParent();
            if (view2 != null) {
                view.setAnimation(this);
                RectF rectF = this.d;
                a(rectF, view);
                rectF.union(this.c);
                view2.invalidate((int) ((float) Math.floor((double) rectF.left)), (int) ((float) Math.floor((double) rectF.top)), (int) ((float) Math.floor((double) rectF.right)), (int) ((float) Math.floor((double) rectF.bottom)));
            }
        }
    }

    private void a(RectF rectF, View view) {
        rectF.set(BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, (float) view.getWidth(), (float) view.getHeight());
        Matrix matrix = this.e;
        matrix.reset();
        a(matrix, view);
        this.e.mapRect(rectF);
        rectF.offset((float) view.getLeft(), (float) view.getTop());
        if (rectF.right < rectF.left) {
            float f2 = rectF.right;
            rectF.right = rectF.left;
            rectF.left = f2;
        }
        if (rectF.bottom < rectF.top) {
            float f3 = rectF.top;
            rectF.top = rectF.bottom;
            rectF.bottom = f3;
        }
    }

    private void a(Matrix matrix, View view) {
        float width = (float) view.getWidth();
        float height = (float) view.getHeight();
        float f2 = this.g;
        float f3 = this.h;
        if (!(f2 == 1.0f && f3 == 1.0f)) {
            float f4 = ((f2 * width) - width) / 2.0f;
            float f5 = ((f3 * height) - height) / 2.0f;
            matrix.postScale(f2, f3);
            matrix.postTranslate(-f4, -f5);
        }
        matrix.postTranslate(this.i, this.j);
    }

    /* access modifiers changed from: protected */
    public void applyTransformation(float f2, Transformation transformation) {
        View view = (View) this.b.get();
        if (view != null) {
            transformation.setAlpha(this.f);
            a(transformation.getMatrix(), view);
        }
    }
}
