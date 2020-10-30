package android.support.design.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Path.FillType;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.Drawable;
import android.support.design.R;
import android.support.v4.content.ContextCompat;
import android.support.v7.graphics.drawable.DrawableWrapper;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

class ShadowDrawableWrapper extends DrawableWrapper {
    static final double a = Math.cos(Math.toRadians(45.0d));
    final Paint b;
    final Paint c;
    final RectF d;
    float e;
    Path f;
    float g;
    float h;
    float i;
    float j;
    private boolean k = true;
    private final int l;
    private final int m;
    private final int n;
    private boolean o = true;
    private float p;
    private boolean q = false;

    public int getOpacity() {
        return -3;
    }

    public ShadowDrawableWrapper(Context context, Drawable drawable, float f2, float f3, float f4) {
        super(drawable);
        this.l = ContextCompat.getColor(context, R.color.design_fab_shadow_start_color);
        this.m = ContextCompat.getColor(context, R.color.design_fab_shadow_mid_color);
        this.n = ContextCompat.getColor(context, R.color.design_fab_shadow_end_color);
        this.b = new Paint(5);
        this.b.setStyle(Style.FILL);
        this.e = (float) Math.round(f2);
        this.d = new RectF();
        this.c = new Paint(this.b);
        this.c.setAntiAlias(false);
        a(f3, f4);
    }

    private static int c(float f2) {
        int round = Math.round(f2);
        return round % 2 == 1 ? round - 1 : round;
    }

    public void a(boolean z) {
        this.o = z;
        invalidateSelf();
    }

    public void setAlpha(int i2) {
        super.setAlpha(i2);
        this.b.setAlpha(i2);
        this.c.setAlpha(i2);
    }

    /* access modifiers changed from: protected */
    public void onBoundsChange(Rect rect) {
        this.k = true;
    }

    /* access modifiers changed from: 0000 */
    public void a(float f2, float f3) {
        if (f2 < BitmapDescriptorFactory.HUE_RED || f3 < BitmapDescriptorFactory.HUE_RED) {
            throw new IllegalArgumentException("invalid shadow size");
        }
        float c2 = (float) c(f2);
        float c3 = (float) c(f3);
        if (c2 > c3) {
            if (!this.q) {
                this.q = true;
            }
            c2 = c3;
        }
        if (this.j != c2 || this.h != c3) {
            this.j = c2;
            this.h = c3;
            this.i = (float) Math.round(c2 * 1.5f);
            this.g = c3;
            this.k = true;
            invalidateSelf();
        }
    }

    public boolean getPadding(Rect rect) {
        int ceil = (int) Math.ceil((double) a(this.h, this.e, this.o));
        int ceil2 = (int) Math.ceil((double) b(this.h, this.e, this.o));
        rect.set(ceil2, ceil, ceil2, ceil);
        return true;
    }

    public static float a(float f2, float f3, boolean z) {
        return z ? (float) (((double) (f2 * 1.5f)) + ((1.0d - a) * ((double) f3))) : f2 * 1.5f;
    }

    public static float b(float f2, float f3, boolean z) {
        return z ? (float) (((double) f2) + ((1.0d - a) * ((double) f3))) : f2;
    }

    public void draw(Canvas canvas) {
        if (this.k) {
            a(getBounds());
            this.k = false;
        }
        a(canvas);
        super.draw(canvas);
    }

    /* access modifiers changed from: 0000 */
    public final void a(float f2) {
        if (this.p != f2) {
            this.p = f2;
            invalidateSelf();
        }
    }

    private void a(Canvas canvas) {
        int i2;
        float f2;
        int i3;
        float f3;
        float f4;
        float f5;
        Canvas canvas2 = canvas;
        int save = canvas.save();
        canvas2.rotate(this.p, this.d.centerX(), this.d.centerY());
        float f6 = (-this.e) - this.i;
        float f7 = this.e;
        float f8 = f7 * 2.0f;
        boolean z = this.d.width() - f8 > BitmapDescriptorFactory.HUE_RED;
        boolean z2 = this.d.height() - f8 > BitmapDescriptorFactory.HUE_RED;
        float f9 = f7 / ((this.j - (this.j * 0.5f)) + f7);
        float f10 = f7 / ((this.j - (this.j * 0.25f)) + f7);
        float f11 = f7 / ((this.j - (this.j * 1.0f)) + f7);
        int save2 = canvas.save();
        canvas2.translate(this.d.left + f7, this.d.top + f7);
        canvas2.scale(f9, f10);
        canvas2.drawPath(this.f, this.b);
        if (z) {
            canvas2.scale(1.0f / f9, 1.0f);
            i3 = save2;
            f2 = f11;
            i2 = save;
            f3 = f10;
            canvas2.drawRect(BitmapDescriptorFactory.HUE_RED, f6, this.d.width() - f8, -this.e, this.c);
        } else {
            i3 = save2;
            f2 = f11;
            i2 = save;
            f3 = f10;
        }
        canvas2.restoreToCount(i3);
        int save3 = canvas.save();
        canvas2.translate(this.d.right - f7, this.d.bottom - f7);
        float f12 = f2;
        canvas2.scale(f9, f12);
        canvas2.rotate(180.0f);
        canvas2.drawPath(this.f, this.b);
        if (z) {
            canvas2.scale(1.0f / f9, 1.0f);
            f4 = f3;
            f5 = f12;
            canvas2.drawRect(BitmapDescriptorFactory.HUE_RED, f6, this.d.width() - f8, (-this.e) + this.i, this.c);
        } else {
            f4 = f3;
            f5 = f12;
        }
        canvas2.restoreToCount(save3);
        int save4 = canvas.save();
        canvas2.translate(this.d.left + f7, this.d.bottom - f7);
        canvas2.scale(f9, f5);
        canvas2.rotate(270.0f);
        canvas2.drawPath(this.f, this.b);
        if (z2) {
            canvas2.scale(1.0f / f5, 1.0f);
            canvas2.drawRect(BitmapDescriptorFactory.HUE_RED, f6, this.d.height() - f8, -this.e, this.c);
        }
        canvas2.restoreToCount(save4);
        int save5 = canvas.save();
        canvas2.translate(this.d.right - f7, this.d.top + f7);
        float f13 = f4;
        canvas2.scale(f9, f13);
        canvas2.rotate(90.0f);
        canvas2.drawPath(this.f, this.b);
        if (z2) {
            canvas2.scale(1.0f / f13, 1.0f);
            canvas2.drawRect(BitmapDescriptorFactory.HUE_RED, f6, this.d.height() - f8, -this.e, this.c);
        }
        canvas2.restoreToCount(save5);
        canvas2.restoreToCount(i2);
    }

    private void b() {
        RectF rectF = new RectF(-this.e, -this.e, this.e, this.e);
        RectF rectF2 = new RectF(rectF);
        rectF2.inset(-this.i, -this.i);
        if (this.f == null) {
            this.f = new Path();
        } else {
            this.f.reset();
        }
        this.f.setFillType(FillType.EVEN_ODD);
        this.f.moveTo(-this.e, BitmapDescriptorFactory.HUE_RED);
        this.f.rLineTo(-this.i, BitmapDescriptorFactory.HUE_RED);
        this.f.arcTo(rectF2, 180.0f, 90.0f, false);
        this.f.arcTo(rectF, 270.0f, -90.0f, false);
        this.f.close();
        float f2 = -rectF2.top;
        if (f2 > BitmapDescriptorFactory.HUE_RED) {
            float f3 = this.e / f2;
            float f4 = ((1.0f - f3) / 2.0f) + f3;
            RadialGradient radialGradient = r8;
            float[] fArr = {0.0f, f3, f4, 1.0f};
            Paint paint = this.b;
            RadialGradient radialGradient2 = new RadialGradient(BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, f2, new int[]{0, this.l, this.m, this.n}, fArr, TileMode.CLAMP);
            paint.setShader(radialGradient);
        }
        Paint paint2 = this.c;
        float f5 = rectF.top;
        LinearGradient linearGradient = new LinearGradient(BitmapDescriptorFactory.HUE_RED, f5, BitmapDescriptorFactory.HUE_RED, rectF2.top, new int[]{this.l, this.m, this.n}, new float[]{BitmapDescriptorFactory.HUE_RED, 0.5f, 1.0f}, TileMode.CLAMP);
        paint2.setShader(linearGradient);
        this.c.setAntiAlias(false);
    }

    private void a(Rect rect) {
        float f2 = this.h * 1.5f;
        this.d.set(((float) rect.left) + this.h, ((float) rect.top) + f2, ((float) rect.right) - this.h, ((float) rect.bottom) - f2);
        getWrappedDrawable().setBounds((int) this.d.left, (int) this.d.top, (int) this.d.right, (int) this.d.bottom);
        b();
    }

    public void b(float f2) {
        a(f2, this.h);
    }

    public float a() {
        return this.j;
    }
}
