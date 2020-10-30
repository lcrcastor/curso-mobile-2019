package android.support.design.widget;

import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.Drawable;
import android.support.v4.graphics.ColorUtils;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

class CircularBorderDrawable extends Drawable {
    final Paint a = new Paint(1);
    final Rect b = new Rect();
    final RectF c = new RectF();
    float d;
    private int e;
    private int f;
    private int g;
    private int h;
    private ColorStateList i;
    private int j;
    private boolean k = true;
    private float l;

    public CircularBorderDrawable() {
        this.a.setStyle(Style.STROKE);
    }

    /* access modifiers changed from: 0000 */
    public void a(int i2, int i3, int i4, int i5) {
        this.e = i2;
        this.f = i3;
        this.g = i4;
        this.h = i5;
    }

    /* access modifiers changed from: 0000 */
    public void a(float f2) {
        if (this.d != f2) {
            this.d = f2;
            this.a.setStrokeWidth(f2 * 1.3333f);
            this.k = true;
            invalidateSelf();
        }
    }

    public void draw(Canvas canvas) {
        if (this.k) {
            this.a.setShader(a());
            this.k = false;
        }
        float strokeWidth = this.a.getStrokeWidth() / 2.0f;
        RectF rectF = this.c;
        copyBounds(this.b);
        rectF.set(this.b);
        rectF.left += strokeWidth;
        rectF.top += strokeWidth;
        rectF.right -= strokeWidth;
        rectF.bottom -= strokeWidth;
        canvas.save();
        canvas.rotate(this.l, rectF.centerX(), rectF.centerY());
        canvas.drawOval(rectF, this.a);
        canvas.restore();
    }

    public boolean getPadding(Rect rect) {
        int round = Math.round(this.d);
        rect.set(round, round, round, round);
        return true;
    }

    public void setAlpha(int i2) {
        this.a.setAlpha(i2);
        invalidateSelf();
    }

    /* access modifiers changed from: 0000 */
    public void a(ColorStateList colorStateList) {
        if (colorStateList != null) {
            this.j = colorStateList.getColorForState(getState(), this.j);
        }
        this.i = colorStateList;
        this.k = true;
        invalidateSelf();
    }

    public void setColorFilter(ColorFilter colorFilter) {
        this.a.setColorFilter(colorFilter);
        invalidateSelf();
    }

    public int getOpacity() {
        return this.d > BitmapDescriptorFactory.HUE_RED ? -3 : -2;
    }

    /* access modifiers changed from: 0000 */
    public final void b(float f2) {
        if (f2 != this.l) {
            this.l = f2;
            invalidateSelf();
        }
    }

    /* access modifiers changed from: protected */
    public void onBoundsChange(Rect rect) {
        this.k = true;
    }

    public boolean isStateful() {
        return (this.i != null && this.i.isStateful()) || super.isStateful();
    }

    /* access modifiers changed from: protected */
    public boolean onStateChange(int[] iArr) {
        if (this.i != null) {
            int colorForState = this.i.getColorForState(iArr, this.j);
            if (colorForState != this.j) {
                this.k = true;
                this.j = colorForState;
            }
        }
        if (this.k) {
            invalidateSelf();
        }
        return this.k;
    }

    private Shader a() {
        Rect rect = this.b;
        copyBounds(rect);
        float height = this.d / ((float) rect.height());
        LinearGradient linearGradient = new LinearGradient(BitmapDescriptorFactory.HUE_RED, (float) rect.top, BitmapDescriptorFactory.HUE_RED, (float) rect.bottom, new int[]{ColorUtils.compositeColors(this.e, this.j), ColorUtils.compositeColors(this.f, this.j), ColorUtils.compositeColors(ColorUtils.setAlphaComponent(this.f, 0), this.j), ColorUtils.compositeColors(ColorUtils.setAlphaComponent(this.h, 0), this.j), ColorUtils.compositeColors(this.h, this.j), ColorUtils.compositeColors(this.g, this.j)}, new float[]{0.0f, height, 0.5f, 0.5f, 1.0f - height, 1.0f}, TileMode.CLAMP);
        return linearGradient;
    }
}
