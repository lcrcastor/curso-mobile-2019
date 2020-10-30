package android.support.design.widget;

import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.os.Build.VERSION;
import android.support.annotation.ColorInt;
import android.support.v4.math.MathUtils;
import android.support.v4.text.TextDirectionHeuristicsCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.appcompat.R;
import android.support.v7.widget.TintTypedArray;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextUtils.TruncateAt;
import android.view.View;
import android.view.animation.Interpolator;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

final class CollapsingTextHelper {
    private static final boolean a = (VERSION.SDK_INT < 18);
    private static final Paint b = null;
    private boolean A;
    private Bitmap B;
    private Paint C;
    private float D;
    private float E;
    private float F;
    private float G;
    private int[] H;
    private boolean I;
    private final TextPaint J;
    private Interpolator K;
    private Interpolator L;
    private float M;
    private float N;
    private float O;
    private int P;
    private float Q;
    private float R;
    private float S;
    private int T;
    private final View c;
    private boolean d;
    private float e;
    private final Rect f;
    private final Rect g;
    private final RectF h;
    private int i = 16;
    private int j = 16;
    private float k = 15.0f;
    private float l = 15.0f;
    private ColorStateList m;
    private ColorStateList n;
    private float o;
    private float p;
    private float q;
    private float r;
    private float s;
    private float t;
    private Typeface u;
    private Typeface v;
    private Typeface w;
    private CharSequence x;
    private CharSequence y;
    private boolean z;

    static {
        if (b != null) {
            b.setAntiAlias(true);
            b.setColor(-65281);
        }
    }

    public CollapsingTextHelper(View view) {
        this.c = view;
        this.J = new TextPaint(129);
        this.g = new Rect();
        this.f = new Rect();
        this.h = new RectF();
    }

    /* access modifiers changed from: 0000 */
    public void a(Interpolator interpolator) {
        this.L = interpolator;
        i();
    }

    /* access modifiers changed from: 0000 */
    public void b(Interpolator interpolator) {
        this.K = interpolator;
        i();
    }

    /* access modifiers changed from: 0000 */
    public void a(float f2) {
        if (this.k != f2) {
            this.k = f2;
            i();
        }
    }

    /* access modifiers changed from: 0000 */
    public void a(ColorStateList colorStateList) {
        if (this.n != colorStateList) {
            this.n = colorStateList;
            i();
        }
    }

    /* access modifiers changed from: 0000 */
    public void b(ColorStateList colorStateList) {
        if (this.m != colorStateList) {
            this.m = colorStateList;
            i();
        }
    }

    /* access modifiers changed from: 0000 */
    public void a(int i2, int i3, int i4, int i5) {
        if (!a(this.f, i2, i3, i4, i5)) {
            this.f.set(i2, i3, i4, i5);
            this.I = true;
            a();
        }
    }

    /* access modifiers changed from: 0000 */
    public void b(int i2, int i3, int i4, int i5) {
        if (!a(this.g, i2, i3, i4, i5)) {
            this.g.set(i2, i3, i4, i5);
            this.I = true;
            a();
        }
    }

    /* access modifiers changed from: 0000 */
    public void a() {
        this.d = this.g.width() > 0 && this.g.height() > 0 && this.f.width() > 0 && this.f.height() > 0;
    }

    /* access modifiers changed from: 0000 */
    public void a(int i2) {
        if (this.i != i2) {
            this.i = i2;
            i();
        }
    }

    /* access modifiers changed from: 0000 */
    public int b() {
        return this.i;
    }

    /* access modifiers changed from: 0000 */
    public void b(int i2) {
        if (this.j != i2) {
            this.j = i2;
            i();
        }
    }

    /* access modifiers changed from: 0000 */
    public int c() {
        return this.j;
    }

    /* access modifiers changed from: 0000 */
    public void c(int i2) {
        TintTypedArray obtainStyledAttributes = TintTypedArray.obtainStyledAttributes(this.c.getContext(), i2, R.styleable.TextAppearance);
        if (obtainStyledAttributes.hasValue(R.styleable.TextAppearance_android_textColor)) {
            this.n = obtainStyledAttributes.getColorStateList(R.styleable.TextAppearance_android_textColor);
        }
        if (obtainStyledAttributes.hasValue(R.styleable.TextAppearance_android_textSize)) {
            this.l = (float) obtainStyledAttributes.getDimensionPixelSize(R.styleable.TextAppearance_android_textSize, (int) this.l);
        }
        this.P = obtainStyledAttributes.getInt(R.styleable.TextAppearance_android_shadowColor, 0);
        this.N = obtainStyledAttributes.getFloat(R.styleable.TextAppearance_android_shadowDx, BitmapDescriptorFactory.HUE_RED);
        this.O = obtainStyledAttributes.getFloat(R.styleable.TextAppearance_android_shadowDy, BitmapDescriptorFactory.HUE_RED);
        this.M = obtainStyledAttributes.getFloat(R.styleable.TextAppearance_android_shadowRadius, BitmapDescriptorFactory.HUE_RED);
        obtainStyledAttributes.recycle();
        if (VERSION.SDK_INT >= 16) {
            this.u = e(i2);
        }
        i();
    }

    /* access modifiers changed from: 0000 */
    public void d(int i2) {
        TintTypedArray obtainStyledAttributes = TintTypedArray.obtainStyledAttributes(this.c.getContext(), i2, R.styleable.TextAppearance);
        if (obtainStyledAttributes.hasValue(R.styleable.TextAppearance_android_textColor)) {
            this.m = obtainStyledAttributes.getColorStateList(R.styleable.TextAppearance_android_textColor);
        }
        if (obtainStyledAttributes.hasValue(R.styleable.TextAppearance_android_textSize)) {
            this.k = (float) obtainStyledAttributes.getDimensionPixelSize(R.styleable.TextAppearance_android_textSize, (int) this.k);
        }
        this.T = obtainStyledAttributes.getInt(R.styleable.TextAppearance_android_shadowColor, 0);
        this.R = obtainStyledAttributes.getFloat(R.styleable.TextAppearance_android_shadowDx, BitmapDescriptorFactory.HUE_RED);
        this.S = obtainStyledAttributes.getFloat(R.styleable.TextAppearance_android_shadowDy, BitmapDescriptorFactory.HUE_RED);
        this.Q = obtainStyledAttributes.getFloat(R.styleable.TextAppearance_android_shadowRadius, BitmapDescriptorFactory.HUE_RED);
        obtainStyledAttributes.recycle();
        if (VERSION.SDK_INT >= 16) {
            this.v = e(i2);
        }
        i();
    }

    private Typeface e(int i2) {
        TypedArray obtainStyledAttributes = this.c.getContext().obtainStyledAttributes(i2, new int[]{16843692});
        try {
            String string = obtainStyledAttributes.getString(0);
            if (string != null) {
                return Typeface.create(string, 0);
            }
            obtainStyledAttributes.recycle();
            return null;
        } finally {
            obtainStyledAttributes.recycle();
        }
    }

    /* access modifiers changed from: 0000 */
    public void a(Typeface typeface) {
        if (a(this.u, typeface)) {
            this.u = typeface;
            i();
        }
    }

    /* access modifiers changed from: 0000 */
    public void b(Typeface typeface) {
        if (a(this.v, typeface)) {
            this.v = typeface;
            i();
        }
    }

    /* access modifiers changed from: 0000 */
    public void c(Typeface typeface) {
        this.v = typeface;
        this.u = typeface;
        i();
    }

    /* access modifiers changed from: 0000 */
    public Typeface d() {
        return this.u != null ? this.u : Typeface.DEFAULT;
    }

    /* access modifiers changed from: 0000 */
    public Typeface e() {
        return this.v != null ? this.v : Typeface.DEFAULT;
    }

    /* access modifiers changed from: 0000 */
    public void b(float f2) {
        float clamp = MathUtils.clamp(f2, (float) BitmapDescriptorFactory.HUE_RED, 1.0f);
        if (clamp != this.e) {
            this.e = clamp;
            l();
        }
    }

    /* access modifiers changed from: 0000 */
    public final boolean a(int[] iArr) {
        this.H = iArr;
        if (!f()) {
            return false;
        }
        i();
        return true;
    }

    /* access modifiers changed from: 0000 */
    public final boolean f() {
        return (this.n != null && this.n.isStateful()) || (this.m != null && this.m.isStateful());
    }

    /* access modifiers changed from: 0000 */
    public float g() {
        return this.e;
    }

    /* access modifiers changed from: 0000 */
    public float h() {
        return this.l;
    }

    private void l() {
        c(this.e);
    }

    private void c(float f2) {
        d(f2);
        this.s = a(this.q, this.r, f2, this.K);
        this.t = a(this.o, this.p, f2, this.K);
        e(a(this.k, this.l, f2, this.L));
        if (this.n != this.m) {
            this.J.setColor(a(m(), n(), f2));
        } else {
            this.J.setColor(n());
        }
        this.J.setShadowLayer(a(this.Q, this.M, f2, (Interpolator) null), a(this.R, this.N, f2, (Interpolator) null), a(this.S, this.O, f2, (Interpolator) null), a(this.T, this.P, f2));
        ViewCompat.postInvalidateOnAnimation(this.c);
    }

    @ColorInt
    private int m() {
        if (this.H != null) {
            return this.m.getColorForState(this.H, 0);
        }
        return this.m.getDefaultColor();
    }

    @ColorInt
    private int n() {
        if (this.H != null) {
            return this.n.getColorForState(this.H, 0);
        }
        return this.n.getDefaultColor();
    }

    private void o() {
        float f2 = this.G;
        f(this.l);
        CharSequence charSequence = this.y;
        float f3 = BitmapDescriptorFactory.HUE_RED;
        float measureText = charSequence != null ? this.J.measureText(this.y, 0, this.y.length()) : BitmapDescriptorFactory.HUE_RED;
        int absoluteGravity = GravityCompat.getAbsoluteGravity(this.j, this.z ? 1 : 0);
        int i2 = absoluteGravity & 112;
        if (i2 == 48) {
            this.p = ((float) this.g.top) - this.J.ascent();
        } else if (i2 != 80) {
            this.p = ((float) this.g.centerY()) + (((this.J.descent() - this.J.ascent()) / 2.0f) - this.J.descent());
        } else {
            this.p = (float) this.g.bottom;
        }
        int i3 = absoluteGravity & GravityCompat.RELATIVE_HORIZONTAL_GRAVITY_MASK;
        if (i3 == 1) {
            this.r = ((float) this.g.centerX()) - (measureText / 2.0f);
        } else if (i3 != 5) {
            this.r = (float) this.g.left;
        } else {
            this.r = ((float) this.g.right) - measureText;
        }
        f(this.k);
        if (this.y != null) {
            f3 = this.J.measureText(this.y, 0, this.y.length());
        }
        int absoluteGravity2 = GravityCompat.getAbsoluteGravity(this.i, this.z ? 1 : 0);
        int i4 = absoluteGravity2 & 112;
        if (i4 == 48) {
            this.o = ((float) this.f.top) - this.J.ascent();
        } else if (i4 != 80) {
            this.o = ((float) this.f.centerY()) + (((this.J.descent() - this.J.ascent()) / 2.0f) - this.J.descent());
        } else {
            this.o = (float) this.f.bottom;
        }
        int i5 = absoluteGravity2 & GravityCompat.RELATIVE_HORIZONTAL_GRAVITY_MASK;
        if (i5 == 1) {
            this.q = ((float) this.f.centerX()) - (f3 / 2.0f);
        } else if (i5 != 5) {
            this.q = (float) this.f.left;
        } else {
            this.q = ((float) this.f.right) - f3;
        }
        q();
        e(f2);
    }

    private void d(float f2) {
        this.h.left = a((float) this.f.left, (float) this.g.left, f2, this.K);
        this.h.top = a(this.o, this.p, f2, this.K);
        this.h.right = a((float) this.f.right, (float) this.g.right, f2, this.K);
        this.h.bottom = a((float) this.f.bottom, (float) this.g.bottom, f2, this.K);
    }

    public void a(Canvas canvas) {
        float f2;
        int save = canvas.save();
        if (this.y != null && this.d) {
            float f3 = this.s;
            float f4 = this.t;
            boolean z2 = this.A && this.B != null;
            if (z2) {
                f2 = this.D * this.F;
                float f5 = this.E;
                float f6 = this.F;
            } else {
                f2 = this.J.ascent() * this.F;
                this.J.descent();
                float f7 = this.F;
            }
            if (z2) {
                f4 += f2;
            }
            float f8 = f4;
            if (this.F != 1.0f) {
                canvas.scale(this.F, this.F, f3, f8);
            }
            if (z2) {
                canvas.drawBitmap(this.B, f3, f8, this.C);
            } else {
                canvas.drawText(this.y, 0, this.y.length(), f3, f8, this.J);
            }
        }
        canvas.restoreToCount(save);
    }

    private boolean b(CharSequence charSequence) {
        boolean z2 = true;
        if (ViewCompat.getLayoutDirection(this.c) != 1) {
            z2 = false;
        }
        return (z2 ? TextDirectionHeuristicsCompat.FIRSTSTRONG_RTL : TextDirectionHeuristicsCompat.FIRSTSTRONG_LTR).isRtl(charSequence, 0, charSequence.length());
    }

    private void e(float f2) {
        f(f2);
        this.A = a && this.F != 1.0f;
        if (this.A) {
            p();
        }
        ViewCompat.postInvalidateOnAnimation(this.c);
    }

    private boolean a(Typeface typeface, Typeface typeface2) {
        return (typeface != null && !typeface.equals(typeface2)) || (typeface == null && typeface2 != null);
    }

    private void f(float f2) {
        boolean z2;
        float f3;
        boolean z3;
        if (this.x != null) {
            float width = (float) this.g.width();
            float width2 = (float) this.f.width();
            boolean z4 = true;
            if (a(f2, this.l)) {
                float f4 = this.l;
                this.F = 1.0f;
                if (a(this.w, this.u)) {
                    this.w = this.u;
                    z3 = true;
                } else {
                    z3 = false;
                }
                f3 = f4;
                z2 = z3;
            } else {
                f3 = this.k;
                if (a(this.w, this.v)) {
                    this.w = this.v;
                    z2 = true;
                } else {
                    z2 = false;
                }
                if (a(f2, this.k)) {
                    this.F = 1.0f;
                } else {
                    this.F = f2 / this.k;
                }
                float f5 = this.l / this.k;
                width = width2 * f5 > width ? Math.min(width / f5, width2) : width2;
            }
            if (width > BitmapDescriptorFactory.HUE_RED) {
                z2 = this.G != f3 || this.I || z2;
                this.G = f3;
                this.I = false;
            }
            if (this.y == null || z2) {
                this.J.setTextSize(this.G);
                this.J.setTypeface(this.w);
                TextPaint textPaint = this.J;
                if (this.F == 1.0f) {
                    z4 = false;
                }
                textPaint.setLinearText(z4);
                CharSequence ellipsize = TextUtils.ellipsize(this.x, this.J, width, TruncateAt.END);
                if (!TextUtils.equals(ellipsize, this.y)) {
                    this.y = ellipsize;
                    this.z = b(this.y);
                }
            }
        }
    }

    private void p() {
        if (this.B == null && !this.f.isEmpty() && !TextUtils.isEmpty(this.y)) {
            c((float) BitmapDescriptorFactory.HUE_RED);
            this.D = this.J.ascent();
            this.E = this.J.descent();
            int round = Math.round(this.J.measureText(this.y, 0, this.y.length()));
            int round2 = Math.round(this.E - this.D);
            if (round > 0 && round2 > 0) {
                this.B = Bitmap.createBitmap(round, round2, Config.ARGB_8888);
                new Canvas(this.B).drawText(this.y, 0, this.y.length(), BitmapDescriptorFactory.HUE_RED, ((float) round2) - this.J.descent(), this.J);
                if (this.C == null) {
                    this.C = new Paint(3);
                }
            }
        }
    }

    public void i() {
        if (this.c.getHeight() > 0 && this.c.getWidth() > 0) {
            o();
            l();
        }
    }

    /* access modifiers changed from: 0000 */
    public void a(CharSequence charSequence) {
        if (charSequence == null || !charSequence.equals(this.x)) {
            this.x = charSequence;
            this.y = null;
            q();
            i();
        }
    }

    /* access modifiers changed from: 0000 */
    public CharSequence j() {
        return this.x;
    }

    private void q() {
        if (this.B != null) {
            this.B.recycle();
            this.B = null;
        }
    }

    private static boolean a(float f2, float f3) {
        return Math.abs(f2 - f3) < 0.001f;
    }

    /* access modifiers changed from: 0000 */
    public ColorStateList k() {
        return this.n;
    }

    private static int a(int i2, int i3, float f2) {
        float f3 = 1.0f - f2;
        return Color.argb((int) ((((float) Color.alpha(i2)) * f3) + (((float) Color.alpha(i3)) * f2)), (int) ((((float) Color.red(i2)) * f3) + (((float) Color.red(i3)) * f2)), (int) ((((float) Color.green(i2)) * f3) + (((float) Color.green(i3)) * f2)), (int) ((((float) Color.blue(i2)) * f3) + (((float) Color.blue(i3)) * f2)));
    }

    private static float a(float f2, float f3, float f4, Interpolator interpolator) {
        if (interpolator != null) {
            f4 = interpolator.getInterpolation(f4);
        }
        return AnimationUtils.a(f2, f3, f4);
    }

    private static boolean a(Rect rect, int i2, int i3, int i4, int i5) {
        return rect.left == i2 && rect.top == i3 && rect.right == i4 && rect.bottom == i5;
    }
}
