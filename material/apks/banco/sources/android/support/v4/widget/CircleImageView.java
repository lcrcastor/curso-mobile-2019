package android.support.v4.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Build.VERSION;
import android.support.v4.view.ViewCompat;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

class CircleImageView extends ImageView {
    int a;
    private AnimationListener b;

    class OvalShadow extends OvalShape {
        private RadialGradient b;
        private Paint c = new Paint();

        OvalShadow(int i) {
            CircleImageView.this.a = i;
            a((int) rect().width());
        }

        /* access modifiers changed from: protected */
        public void onResize(float f, float f2) {
            super.onResize(f, f2);
            a((int) f);
        }

        public void draw(Canvas canvas, Paint paint) {
            int width = CircleImageView.this.getWidth() / 2;
            float f = (float) width;
            float height = (float) (CircleImageView.this.getHeight() / 2);
            canvas.drawCircle(f, height, f, this.c);
            canvas.drawCircle(f, height, (float) (width - CircleImageView.this.a), paint);
        }

        private void a(int i) {
            float f = (float) (i / 2);
            RadialGradient radialGradient = new RadialGradient(f, f, (float) CircleImageView.this.a, new int[]{1023410176, 0}, null, TileMode.CLAMP);
            this.b = radialGradient;
            this.c.setShader(this.b);
        }
    }

    CircleImageView(Context context, int i) {
        ShapeDrawable shapeDrawable;
        super(context);
        float f = getContext().getResources().getDisplayMetrics().density;
        int i2 = (int) (1.75f * f);
        int i3 = (int) (BitmapDescriptorFactory.HUE_RED * f);
        this.a = (int) (3.5f * f);
        if (a()) {
            shapeDrawable = new ShapeDrawable(new OvalShape());
            ViewCompat.setElevation(this, f * 4.0f);
        } else {
            ShapeDrawable shapeDrawable2 = new ShapeDrawable(new OvalShadow(this.a));
            setLayerType(1, shapeDrawable2.getPaint());
            shapeDrawable2.getPaint().setShadowLayer((float) this.a, (float) i3, (float) i2, 503316480);
            int i4 = this.a;
            setPadding(i4, i4, i4, i4);
            shapeDrawable = shapeDrawable2;
        }
        shapeDrawable.getPaint().setColor(i);
        ViewCompat.setBackground(this, shapeDrawable);
    }

    private boolean a() {
        return VERSION.SDK_INT >= 21;
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        if (!a()) {
            setMeasuredDimension(getMeasuredWidth() + (this.a * 2), getMeasuredHeight() + (this.a * 2));
        }
    }

    public void a(AnimationListener animationListener) {
        this.b = animationListener;
    }

    public void onAnimationStart() {
        super.onAnimationStart();
        if (this.b != null) {
            this.b.onAnimationStart(getAnimation());
        }
    }

    public void onAnimationEnd() {
        super.onAnimationEnd();
        if (this.b != null) {
            this.b.onAnimationEnd(getAnimation());
        }
    }

    public void setBackgroundColor(int i) {
        if (getBackground() instanceof ShapeDrawable) {
            ((ShapeDrawable) getBackground()).getPaint().setColor(i);
        }
    }
}
