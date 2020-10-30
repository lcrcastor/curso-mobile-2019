package android.support.v7.graphics.drawable;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Join;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.FloatRange;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.v7.appcompat.R;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class DrawerArrowDrawable extends Drawable {
    public static final int ARROW_DIRECTION_END = 3;
    public static final int ARROW_DIRECTION_LEFT = 0;
    public static final int ARROW_DIRECTION_RIGHT = 1;
    public static final int ARROW_DIRECTION_START = 2;
    private static final float b = ((float) Math.toRadians(45.0d));
    private final Paint a = new Paint();
    private float c;
    private float d;
    private float e;
    private float f;
    private boolean g;
    private final Path h = new Path();
    private final int i;
    private boolean j = false;
    private float k;
    private float l;
    private int m = 2;

    @RestrictTo({Scope.LIBRARY_GROUP})
    @Retention(RetentionPolicy.SOURCE)
    public @interface ArrowDirection {
    }

    private static float a(float f2, float f3, float f4) {
        return f2 + ((f3 - f2) * f4);
    }

    public int getOpacity() {
        return -3;
    }

    public DrawerArrowDrawable(Context context) {
        this.a.setStyle(Style.STROKE);
        this.a.setStrokeJoin(Join.MITER);
        this.a.setStrokeCap(Cap.BUTT);
        this.a.setAntiAlias(true);
        TypedArray obtainStyledAttributes = context.getTheme().obtainStyledAttributes(null, R.styleable.DrawerArrowToggle, R.attr.drawerArrowStyle, R.style.Base_Widget_AppCompat_DrawerArrowToggle);
        setColor(obtainStyledAttributes.getColor(R.styleable.DrawerArrowToggle_color, 0));
        setBarThickness(obtainStyledAttributes.getDimension(R.styleable.DrawerArrowToggle_thickness, BitmapDescriptorFactory.HUE_RED));
        setSpinEnabled(obtainStyledAttributes.getBoolean(R.styleable.DrawerArrowToggle_spinBars, true));
        setGapSize((float) Math.round(obtainStyledAttributes.getDimension(R.styleable.DrawerArrowToggle_gapBetweenBars, BitmapDescriptorFactory.HUE_RED)));
        this.i = obtainStyledAttributes.getDimensionPixelSize(R.styleable.DrawerArrowToggle_drawableSize, 0);
        this.d = (float) Math.round(obtainStyledAttributes.getDimension(R.styleable.DrawerArrowToggle_barLength, BitmapDescriptorFactory.HUE_RED));
        this.c = (float) Math.round(obtainStyledAttributes.getDimension(R.styleable.DrawerArrowToggle_arrowHeadLength, BitmapDescriptorFactory.HUE_RED));
        this.e = obtainStyledAttributes.getDimension(R.styleable.DrawerArrowToggle_arrowShaftLength, BitmapDescriptorFactory.HUE_RED);
        obtainStyledAttributes.recycle();
    }

    public void setArrowHeadLength(float f2) {
        if (this.c != f2) {
            this.c = f2;
            invalidateSelf();
        }
    }

    public float getArrowHeadLength() {
        return this.c;
    }

    public void setArrowShaftLength(float f2) {
        if (this.e != f2) {
            this.e = f2;
            invalidateSelf();
        }
    }

    public float getArrowShaftLength() {
        return this.e;
    }

    public float getBarLength() {
        return this.d;
    }

    public void setBarLength(float f2) {
        if (this.d != f2) {
            this.d = f2;
            invalidateSelf();
        }
    }

    public void setColor(@ColorInt int i2) {
        if (i2 != this.a.getColor()) {
            this.a.setColor(i2);
            invalidateSelf();
        }
    }

    @ColorInt
    public int getColor() {
        return this.a.getColor();
    }

    public void setBarThickness(float f2) {
        if (this.a.getStrokeWidth() != f2) {
            this.a.setStrokeWidth(f2);
            this.l = (float) (((double) (f2 / 2.0f)) * Math.cos((double) b));
            invalidateSelf();
        }
    }

    public float getBarThickness() {
        return this.a.getStrokeWidth();
    }

    public float getGapSize() {
        return this.f;
    }

    public void setGapSize(float f2) {
        if (f2 != this.f) {
            this.f = f2;
            invalidateSelf();
        }
    }

    public void setDirection(int i2) {
        if (i2 != this.m) {
            this.m = i2;
            invalidateSelf();
        }
    }

    public boolean isSpinEnabled() {
        return this.g;
    }

    public void setSpinEnabled(boolean z) {
        if (this.g != z) {
            this.g = z;
            invalidateSelf();
        }
    }

    public int getDirection() {
        return this.m;
    }

    public void setVerticalMirror(boolean z) {
        if (this.j != z) {
            this.j = z;
            invalidateSelf();
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:4:0x0016, code lost:
        if (android.support.v4.graphics.drawable.DrawableCompat.getLayoutDirection(r20) == 1) goto L_0x0018;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:7:0x001e, code lost:
        if (android.support.v4.graphics.drawable.DrawableCompat.getLayoutDirection(r20) == 0) goto L_0x0018;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void draw(android.graphics.Canvas r21) {
        /*
            r20 = this;
            r0 = r20
            r1 = r21
            android.graphics.Rect r2 = r20.getBounds()
            int r3 = r0.m
            r4 = 3
            r5 = 0
            r6 = 1
            if (r3 == r4) goto L_0x001a
            switch(r3) {
                case 0: goto L_0x0021;
                case 1: goto L_0x0018;
                default: goto L_0x0012;
            }
        L_0x0012:
            int r3 = android.support.v4.graphics.drawable.DrawableCompat.getLayoutDirection(r20)
            if (r3 != r6) goto L_0x0021
        L_0x0018:
            r5 = 1
            goto L_0x0021
        L_0x001a:
            int r3 = android.support.v4.graphics.drawable.DrawableCompat.getLayoutDirection(r20)
            if (r3 != 0) goto L_0x0021
            goto L_0x0018
        L_0x0021:
            float r3 = r0.c
            float r4 = r0.c
            float r3 = r3 * r4
            r4 = 1073741824(0x40000000, float:2.0)
            float r3 = r3 * r4
            double r7 = (double) r3
            double r7 = java.lang.Math.sqrt(r7)
            float r3 = (float) r7
            float r7 = r0.d
            float r8 = r0.k
            float r3 = a(r7, r3, r8)
            float r7 = r0.d
            float r8 = r0.e
            float r9 = r0.k
            float r7 = a(r7, r8, r9)
            float r8 = r0.l
            float r9 = r0.k
            r10 = 0
            float r8 = a(r10, r8, r9)
            int r8 = java.lang.Math.round(r8)
            float r8 = (float) r8
            float r9 = b
            float r11 = r0.k
            float r9 = a(r10, r9, r11)
            if (r5 == 0) goto L_0x005d
            r11 = 0
            goto L_0x005f
        L_0x005d:
            r11 = -1020002304(0xffffffffc3340000, float:-180.0)
        L_0x005f:
            r12 = 1127481344(0x43340000, float:180.0)
            if (r5 == 0) goto L_0x0066
            r13 = 1127481344(0x43340000, float:180.0)
            goto L_0x0067
        L_0x0066:
            r13 = 0
        L_0x0067:
            float r14 = r0.k
            float r11 = a(r11, r13, r14)
            double r13 = (double) r3
            r15 = r11
            double r10 = (double) r9
            double r16 = java.lang.Math.cos(r10)
            r18 = r5
            double r4 = r13 * r16
            long r3 = java.lang.Math.round(r4)
            float r3 = (float) r3
            double r4 = java.lang.Math.sin(r10)
            double r13 = r13 * r4
            long r4 = java.lang.Math.round(r13)
            float r4 = (float) r4
            android.graphics.Path r5 = r0.h
            r5.rewind()
            float r5 = r0.f
            android.graphics.Paint r9 = r0.a
            float r9 = r9.getStrokeWidth()
            float r5 = r5 + r9
            float r9 = r0.l
            float r9 = -r9
            float r10 = r0.k
            float r5 = a(r5, r9, r10)
            float r9 = -r7
            r10 = 1073741824(0x40000000, float:2.0)
            float r9 = r9 / r10
            android.graphics.Path r11 = r0.h
            float r13 = r9 + r8
            r14 = 0
            r11.moveTo(r13, r14)
            android.graphics.Path r11 = r0.h
            float r8 = r8 * r10
            float r7 = r7 - r8
            r11.rLineTo(r7, r14)
            android.graphics.Path r7 = r0.h
            r7.moveTo(r9, r5)
            android.graphics.Path r7 = r0.h
            r7.rLineTo(r3, r4)
            android.graphics.Path r7 = r0.h
            float r5 = -r5
            r7.moveTo(r9, r5)
            android.graphics.Path r5 = r0.h
            float r4 = -r4
            r5.rLineTo(r3, r4)
            android.graphics.Path r3 = r0.h
            r3.close()
            r21.save()
            android.graphics.Paint r3 = r0.a
            float r3 = r3.getStrokeWidth()
            int r4 = r2.height()
            float r4 = (float) r4
            r5 = 1077936128(0x40400000, float:3.0)
            float r5 = r5 * r3
            float r4 = r4 - r5
            float r5 = r0.f
            r7 = 1073741824(0x40000000, float:2.0)
            float r5 = r5 * r7
            float r4 = r4 - r5
            int r4 = (int) r4
            int r4 = r4 / 4
            int r4 = r4 * 2
            float r4 = (float) r4
            r5 = 1069547520(0x3fc00000, float:1.5)
            float r3 = r3 * r5
            float r5 = r0.f
            float r3 = r3 + r5
            float r4 = r4 + r3
            int r2 = r2.centerX()
            float r2 = (float) r2
            r1.translate(r2, r4)
            boolean r2 = r0.g
            if (r2 == 0) goto L_0x0110
            boolean r2 = r0.j
            r2 = r2 ^ r18
            if (r2 == 0) goto L_0x0109
            r6 = -1
        L_0x0109:
            float r2 = (float) r6
            float r11 = r15 * r2
            r1.rotate(r11)
            goto L_0x0115
        L_0x0110:
            if (r18 == 0) goto L_0x0115
            r1.rotate(r12)
        L_0x0115:
            android.graphics.Path r2 = r0.h
            android.graphics.Paint r3 = r0.a
            r1.drawPath(r2, r3)
            r21.restore()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v7.graphics.drawable.DrawerArrowDrawable.draw(android.graphics.Canvas):void");
    }

    public void setAlpha(int i2) {
        if (i2 != this.a.getAlpha()) {
            this.a.setAlpha(i2);
            invalidateSelf();
        }
    }

    public void setColorFilter(ColorFilter colorFilter) {
        this.a.setColorFilter(colorFilter);
        invalidateSelf();
    }

    public int getIntrinsicHeight() {
        return this.i;
    }

    public int getIntrinsicWidth() {
        return this.i;
    }

    @FloatRange(from = 0.0d, to = 1.0d)
    public float getProgress() {
        return this.k;
    }

    public void setProgress(@FloatRange(from = 0.0d, to = 1.0d) float f2) {
        if (this.k != f2) {
            this.k = f2;
            invalidateSelf();
        }
    }

    public final Paint getPaint() {
        return this.a;
    }
}
