package com.nineoldandroids.view.animation;

import android.graphics.Camera;
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
    public static final boolean NEEDS_PROXY = (Integer.valueOf(VERSION.SDK).intValue() < 11);
    private static final WeakHashMap<View, AnimatorProxy> a = new WeakHashMap<>();
    private final WeakReference<View> b;
    private final Camera c = new Camera();
    private boolean d;
    private float e = 1.0f;
    private float f;
    private float g;
    private float h;
    private float i;
    private float j;
    private float k = 1.0f;
    private float l = 1.0f;
    private float m;
    private float n;
    private final RectF o = new RectF();
    private final RectF p = new RectF();
    private final Matrix q = new Matrix();

    public static AnimatorProxy wrap(View view) {
        AnimatorProxy animatorProxy = (AnimatorProxy) a.get(view);
        if (animatorProxy != null && animatorProxy == view.getAnimation()) {
            return animatorProxy;
        }
        AnimatorProxy animatorProxy2 = new AnimatorProxy(view);
        a.put(view, animatorProxy2);
        return animatorProxy2;
    }

    private AnimatorProxy(View view) {
        setDuration(0);
        setFillAfter(true);
        view.setAnimation(this);
        this.b = new WeakReference<>(view);
    }

    public float getAlpha() {
        return this.e;
    }

    public void setAlpha(float f2) {
        if (this.e != f2) {
            this.e = f2;
            View view = (View) this.b.get();
            if (view != null) {
                view.invalidate();
            }
        }
    }

    public float getPivotX() {
        return this.f;
    }

    public void setPivotX(float f2) {
        if (!this.d || this.f != f2) {
            a();
            this.d = true;
            this.f = f2;
            b();
        }
    }

    public float getPivotY() {
        return this.g;
    }

    public void setPivotY(float f2) {
        if (!this.d || this.g != f2) {
            a();
            this.d = true;
            this.g = f2;
            b();
        }
    }

    public float getRotation() {
        return this.j;
    }

    public void setRotation(float f2) {
        if (this.j != f2) {
            a();
            this.j = f2;
            b();
        }
    }

    public float getRotationX() {
        return this.h;
    }

    public void setRotationX(float f2) {
        if (this.h != f2) {
            a();
            this.h = f2;
            b();
        }
    }

    public float getRotationY() {
        return this.i;
    }

    public void setRotationY(float f2) {
        if (this.i != f2) {
            a();
            this.i = f2;
            b();
        }
    }

    public float getScaleX() {
        return this.k;
    }

    public void setScaleX(float f2) {
        if (this.k != f2) {
            a();
            this.k = f2;
            b();
        }
    }

    public float getScaleY() {
        return this.l;
    }

    public void setScaleY(float f2) {
        if (this.l != f2) {
            a();
            this.l = f2;
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
            view.scrollTo(view.getScrollX(), i2);
        }
    }

    public float getTranslationX() {
        return this.m;
    }

    public void setTranslationX(float f2) {
        if (this.m != f2) {
            a();
            this.m = f2;
            b();
        }
    }

    public float getTranslationY() {
        return this.n;
    }

    public void setTranslationY(float f2) {
        if (this.n != f2) {
            a();
            this.n = f2;
            b();
        }
    }

    public float getX() {
        View view = (View) this.b.get();
        if (view == null) {
            return BitmapDescriptorFactory.HUE_RED;
        }
        return ((float) view.getLeft()) + this.m;
    }

    public void setX(float f2) {
        View view = (View) this.b.get();
        if (view != null) {
            setTranslationX(f2 - ((float) view.getLeft()));
        }
    }

    public float getY() {
        View view = (View) this.b.get();
        if (view == null) {
            return BitmapDescriptorFactory.HUE_RED;
        }
        return ((float) view.getTop()) + this.n;
    }

    public void setY(float f2) {
        View view = (View) this.b.get();
        if (view != null) {
            setTranslationY(f2 - ((float) view.getTop()));
        }
    }

    private void a() {
        View view = (View) this.b.get();
        if (view != null) {
            a(this.o, view);
        }
    }

    private void b() {
        View view = (View) this.b.get();
        if (view != null && view.getParent() != null) {
            RectF rectF = this.p;
            a(rectF, view);
            rectF.union(this.o);
            ((View) view.getParent()).invalidate((int) Math.floor((double) rectF.left), (int) Math.floor((double) rectF.top), (int) Math.ceil((double) rectF.right), (int) Math.ceil((double) rectF.bottom));
        }
    }

    private void a(RectF rectF, View view) {
        rectF.set(BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, (float) view.getWidth(), (float) view.getHeight());
        Matrix matrix = this.q;
        matrix.reset();
        a(matrix, view);
        this.q.mapRect(rectF);
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
        boolean z = this.d;
        float f2 = z ? this.f : width / 2.0f;
        float f3 = z ? this.g : height / 2.0f;
        float f4 = this.h;
        float f5 = this.i;
        float f6 = this.j;
        if (!(f4 == BitmapDescriptorFactory.HUE_RED && f5 == BitmapDescriptorFactory.HUE_RED && f6 == BitmapDescriptorFactory.HUE_RED)) {
            Camera camera = this.c;
            camera.save();
            camera.rotateX(f4);
            camera.rotateY(f5);
            camera.rotateZ(-f6);
            camera.getMatrix(matrix);
            camera.restore();
            matrix.preTranslate(-f2, -f3);
            matrix.postTranslate(f2, f3);
        }
        float f7 = this.k;
        float f8 = this.l;
        if (!(f7 == 1.0f && f8 == 1.0f)) {
            matrix.postScale(f7, f8);
            matrix.postTranslate((-(f2 / width)) * ((f7 * width) - width), (-(f3 / height)) * ((f8 * height) - height));
        }
        matrix.postTranslate(this.m, this.n);
    }

    /* access modifiers changed from: protected */
    public void applyTransformation(float f2, Transformation transformation) {
        View view = (View) this.b.get();
        if (view != null) {
            transformation.setAlpha(this.e);
            a(transformation.getMatrix(), view);
        }
    }
}
