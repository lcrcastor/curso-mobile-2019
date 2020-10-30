package android.support.v7.widget;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.PorterDuff.Mode;
import android.graphics.Rect;
import android.graphics.Region.Op;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.support.annotation.Nullable;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.appcompat.R;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.text.AllCapsTransformationMethod;
import android.text.Layout;
import android.text.Layout.Alignment;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.TransformationMethod;
import android.util.AttributeSet;
import android.util.Property;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewConfiguration;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.CompoundButton;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import cz.msebera.android.httpclient.message.TokenParser;

public class SwitchCompat extends CompoundButton {
    private static final int[] N = {16842912};
    private static final Property<SwitchCompat, Float> b = new Property<SwitchCompat, Float>(Float.class, "thumbPos") {
        /* renamed from: a */
        public Float get(SwitchCompat switchCompat) {
            return Float.valueOf(switchCompat.z);
        }

        /* renamed from: a */
        public void set(SwitchCompat switchCompat, Float f) {
            switchCompat.setThumbPosition(f.floatValue());
        }
    };
    private int A;
    private int B;
    private int C;
    private int D;
    private int E;
    private int F;
    private int G;
    private final TextPaint H;
    private ColorStateList I;
    private Layout J;
    private Layout K;
    private TransformationMethod L;
    private final Rect M;
    ObjectAnimator a;
    private Drawable c;
    private ColorStateList d;
    private Mode e;
    private boolean f;
    private boolean g;
    private Drawable h;
    private ColorStateList i;
    private Mode j;
    private boolean k;
    private boolean l;
    private int m;
    private int n;
    private int o;
    private boolean p;
    private CharSequence q;
    private CharSequence r;
    private boolean s;
    private int t;
    private int u;
    private float v;
    private float w;
    private VelocityTracker x;
    private int y;
    /* access modifiers changed from: private */
    public float z;

    private static float a(float f2, float f3, float f4) {
        return f2 < f3 ? f3 : f2 > f4 ? f4 : f2;
    }

    public SwitchCompat(Context context) {
        this(context, null);
    }

    public SwitchCompat(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.switchStyle);
    }

    public SwitchCompat(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.d = null;
        this.e = null;
        this.f = false;
        this.g = false;
        this.i = null;
        this.j = null;
        this.k = false;
        this.l = false;
        this.x = VelocityTracker.obtain();
        this.M = new Rect();
        this.H = new TextPaint(1);
        Resources resources = getResources();
        this.H.density = resources.getDisplayMetrics().density;
        TintTypedArray obtainStyledAttributes = TintTypedArray.obtainStyledAttributes(context, attributeSet, R.styleable.SwitchCompat, i2, 0);
        this.c = obtainStyledAttributes.getDrawable(R.styleable.SwitchCompat_android_thumb);
        if (this.c != null) {
            this.c.setCallback(this);
        }
        this.h = obtainStyledAttributes.getDrawable(R.styleable.SwitchCompat_track);
        if (this.h != null) {
            this.h.setCallback(this);
        }
        this.q = obtainStyledAttributes.getText(R.styleable.SwitchCompat_android_textOn);
        this.r = obtainStyledAttributes.getText(R.styleable.SwitchCompat_android_textOff);
        this.s = obtainStyledAttributes.getBoolean(R.styleable.SwitchCompat_showText, true);
        this.m = obtainStyledAttributes.getDimensionPixelSize(R.styleable.SwitchCompat_thumbTextPadding, 0);
        this.n = obtainStyledAttributes.getDimensionPixelSize(R.styleable.SwitchCompat_switchMinWidth, 0);
        this.o = obtainStyledAttributes.getDimensionPixelSize(R.styleable.SwitchCompat_switchPadding, 0);
        this.p = obtainStyledAttributes.getBoolean(R.styleable.SwitchCompat_splitTrack, false);
        ColorStateList colorStateList = obtainStyledAttributes.getColorStateList(R.styleable.SwitchCompat_thumbTint);
        if (colorStateList != null) {
            this.d = colorStateList;
            this.f = true;
        }
        Mode parseTintMode = DrawableUtils.parseTintMode(obtainStyledAttributes.getInt(R.styleable.SwitchCompat_thumbTintMode, -1), null);
        if (this.e != parseTintMode) {
            this.e = parseTintMode;
            this.g = true;
        }
        if (this.f || this.g) {
            b();
        }
        ColorStateList colorStateList2 = obtainStyledAttributes.getColorStateList(R.styleable.SwitchCompat_trackTint);
        if (colorStateList2 != null) {
            this.i = colorStateList2;
            this.k = true;
        }
        Mode parseTintMode2 = DrawableUtils.parseTintMode(obtainStyledAttributes.getInt(R.styleable.SwitchCompat_trackTintMode, -1), null);
        if (this.j != parseTintMode2) {
            this.j = parseTintMode2;
            this.l = true;
        }
        if (this.k || this.l) {
            a();
        }
        int resourceId = obtainStyledAttributes.getResourceId(R.styleable.SwitchCompat_switchTextAppearance, 0);
        if (resourceId != 0) {
            setSwitchTextAppearance(context, resourceId);
        }
        obtainStyledAttributes.recycle();
        ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
        this.u = viewConfiguration.getScaledTouchSlop();
        this.y = viewConfiguration.getScaledMinimumFlingVelocity();
        refreshDrawableState();
        setChecked(isChecked());
    }

    public void setSwitchTextAppearance(Context context, int i2) {
        TintTypedArray obtainStyledAttributes = TintTypedArray.obtainStyledAttributes(context, i2, R.styleable.TextAppearance);
        ColorStateList colorStateList = obtainStyledAttributes.getColorStateList(R.styleable.TextAppearance_android_textColor);
        if (colorStateList != null) {
            this.I = colorStateList;
        } else {
            this.I = getTextColors();
        }
        int dimensionPixelSize = obtainStyledAttributes.getDimensionPixelSize(R.styleable.TextAppearance_android_textSize, 0);
        if (dimensionPixelSize != 0) {
            float f2 = (float) dimensionPixelSize;
            if (f2 != this.H.getTextSize()) {
                this.H.setTextSize(f2);
                requestLayout();
            }
        }
        a(obtainStyledAttributes.getInt(R.styleable.TextAppearance_android_typeface, -1), obtainStyledAttributes.getInt(R.styleable.TextAppearance_android_textStyle, -1));
        if (obtainStyledAttributes.getBoolean(R.styleable.TextAppearance_textAllCaps, false)) {
            this.L = new AllCapsTransformationMethod(getContext());
        } else {
            this.L = null;
        }
        obtainStyledAttributes.recycle();
    }

    private void a(int i2, int i3) {
        Typeface typeface;
        switch (i2) {
            case 1:
                typeface = Typeface.SANS_SERIF;
                break;
            case 2:
                typeface = Typeface.SERIF;
                break;
            case 3:
                typeface = Typeface.MONOSPACE;
                break;
            default:
                typeface = null;
                break;
        }
        setSwitchTypeface(typeface, i3);
    }

    public void setSwitchTypeface(Typeface typeface, int i2) {
        Typeface typeface2;
        float f2 = BitmapDescriptorFactory.HUE_RED;
        boolean z2 = false;
        if (i2 > 0) {
            if (typeface == null) {
                typeface2 = Typeface.defaultFromStyle(i2);
            } else {
                typeface2 = Typeface.create(typeface, i2);
            }
            setSwitchTypeface(typeface2);
            int style = ((typeface2 != null ? typeface2.getStyle() : 0) ^ -1) & i2;
            TextPaint textPaint = this.H;
            if ((style & 1) != 0) {
                z2 = true;
            }
            textPaint.setFakeBoldText(z2);
            TextPaint textPaint2 = this.H;
            if ((style & 2) != 0) {
                f2 = -0.25f;
            }
            textPaint2.setTextSkewX(f2);
            return;
        }
        this.H.setFakeBoldText(false);
        this.H.setTextSkewX(BitmapDescriptorFactory.HUE_RED);
        setSwitchTypeface(typeface);
    }

    public void setSwitchTypeface(Typeface typeface) {
        if ((this.H.getTypeface() != null && !this.H.getTypeface().equals(typeface)) || (this.H.getTypeface() == null && typeface != null)) {
            this.H.setTypeface(typeface);
            requestLayout();
            invalidate();
        }
    }

    public void setSwitchPadding(int i2) {
        this.o = i2;
        requestLayout();
    }

    public int getSwitchPadding() {
        return this.o;
    }

    public void setSwitchMinWidth(int i2) {
        this.n = i2;
        requestLayout();
    }

    public int getSwitchMinWidth() {
        return this.n;
    }

    public void setThumbTextPadding(int i2) {
        this.m = i2;
        requestLayout();
    }

    public int getThumbTextPadding() {
        return this.m;
    }

    public void setTrackDrawable(Drawable drawable) {
        if (this.h != null) {
            this.h.setCallback(null);
        }
        this.h = drawable;
        if (drawable != null) {
            drawable.setCallback(this);
        }
        requestLayout();
    }

    public void setTrackResource(int i2) {
        setTrackDrawable(AppCompatResources.getDrawable(getContext(), i2));
    }

    public Drawable getTrackDrawable() {
        return this.h;
    }

    public void setTrackTintList(@Nullable ColorStateList colorStateList) {
        this.i = colorStateList;
        this.k = true;
        a();
    }

    @Nullable
    public ColorStateList getTrackTintList() {
        return this.i;
    }

    public void setTrackTintMode(@Nullable Mode mode) {
        this.j = mode;
        this.l = true;
        a();
    }

    @Nullable
    public Mode getTrackTintMode() {
        return this.j;
    }

    private void a() {
        if (this.h == null) {
            return;
        }
        if (this.k || this.l) {
            this.h = this.h.mutate();
            if (this.k) {
                DrawableCompat.setTintList(this.h, this.i);
            }
            if (this.l) {
                DrawableCompat.setTintMode(this.h, this.j);
            }
            if (this.h.isStateful()) {
                this.h.setState(getDrawableState());
            }
        }
    }

    public void setThumbDrawable(Drawable drawable) {
        if (this.c != null) {
            this.c.setCallback(null);
        }
        this.c = drawable;
        if (drawable != null) {
            drawable.setCallback(this);
        }
        requestLayout();
    }

    public void setThumbResource(int i2) {
        setThumbDrawable(AppCompatResources.getDrawable(getContext(), i2));
    }

    public Drawable getThumbDrawable() {
        return this.c;
    }

    public void setThumbTintList(@Nullable ColorStateList colorStateList) {
        this.d = colorStateList;
        this.f = true;
        b();
    }

    @Nullable
    public ColorStateList getThumbTintList() {
        return this.d;
    }

    public void setThumbTintMode(@Nullable Mode mode) {
        this.e = mode;
        this.g = true;
        b();
    }

    @Nullable
    public Mode getThumbTintMode() {
        return this.e;
    }

    private void b() {
        if (this.c == null) {
            return;
        }
        if (this.f || this.g) {
            this.c = this.c.mutate();
            if (this.f) {
                DrawableCompat.setTintList(this.c, this.d);
            }
            if (this.g) {
                DrawableCompat.setTintMode(this.c, this.e);
            }
            if (this.c.isStateful()) {
                this.c.setState(getDrawableState());
            }
        }
    }

    public void setSplitTrack(boolean z2) {
        this.p = z2;
        invalidate();
    }

    public boolean getSplitTrack() {
        return this.p;
    }

    public CharSequence getTextOn() {
        return this.q;
    }

    public void setTextOn(CharSequence charSequence) {
        this.q = charSequence;
        requestLayout();
    }

    public CharSequence getTextOff() {
        return this.r;
    }

    public void setTextOff(CharSequence charSequence) {
        this.r = charSequence;
        requestLayout();
    }

    public void setShowText(boolean z2) {
        if (this.s != z2) {
            this.s = z2;
            requestLayout();
        }
    }

    public boolean getShowText() {
        return this.s;
    }

    public void onMeasure(int i2, int i3) {
        int i4;
        int i5;
        if (this.s) {
            if (this.J == null) {
                this.J = a(this.q);
            }
            if (this.K == null) {
                this.K = a(this.r);
            }
        }
        Rect rect = this.M;
        int i6 = 0;
        if (this.c != null) {
            this.c.getPadding(rect);
            i5 = (this.c.getIntrinsicWidth() - rect.left) - rect.right;
            i4 = this.c.getIntrinsicHeight();
        } else {
            i5 = 0;
            i4 = 0;
        }
        this.C = Math.max(this.s ? Math.max(this.J.getWidth(), this.K.getWidth()) + (this.m * 2) : 0, i5);
        if (this.h != null) {
            this.h.getPadding(rect);
            i6 = this.h.getIntrinsicHeight();
        } else {
            rect.setEmpty();
        }
        int i7 = rect.left;
        int i8 = rect.right;
        if (this.c != null) {
            Rect opticalBounds = DrawableUtils.getOpticalBounds(this.c);
            i7 = Math.max(i7, opticalBounds.left);
            i8 = Math.max(i8, opticalBounds.right);
        }
        int max = Math.max(this.n, (this.C * 2) + i7 + i8);
        int max2 = Math.max(i6, i4);
        this.A = max;
        this.B = max2;
        super.onMeasure(i2, i3);
        if (getMeasuredHeight() < max2) {
            setMeasuredDimension(getMeasuredWidthAndState(), max2);
        }
    }

    public void onPopulateAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        super.onPopulateAccessibilityEvent(accessibilityEvent);
        CharSequence charSequence = isChecked() ? this.q : this.r;
        if (charSequence != null) {
            accessibilityEvent.getText().add(charSequence);
        }
    }

    private Layout a(CharSequence charSequence) {
        if (this.L != null) {
            charSequence = this.L.getTransformation(charSequence, this);
        }
        CharSequence charSequence2 = charSequence;
        StaticLayout staticLayout = new StaticLayout(charSequence2, this.H, charSequence2 != null ? (int) Math.ceil((double) Layout.getDesiredWidth(charSequence2, this.H)) : 0, Alignment.ALIGN_NORMAL, 1.0f, BitmapDescriptorFactory.HUE_RED, true);
        return staticLayout;
    }

    private boolean a(float f2, float f3) {
        boolean z2 = false;
        if (this.c == null) {
            return false;
        }
        int thumbOffset = getThumbOffset();
        this.c.getPadding(this.M);
        int i2 = this.E - this.u;
        int i3 = (this.D + thumbOffset) - this.u;
        int i4 = this.C + i3 + this.M.left + this.M.right + this.u;
        int i5 = this.G + this.u;
        if (f2 > ((float) i3) && f2 < ((float) i4) && f3 > ((float) i2) && f3 < ((float) i5)) {
            z2 = true;
        }
        return z2;
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        this.x.addMovement(motionEvent);
        switch (motionEvent.getActionMasked()) {
            case 0:
                float x2 = motionEvent.getX();
                float y2 = motionEvent.getY();
                if (isEnabled() && a(x2, y2)) {
                    this.t = 1;
                    this.v = x2;
                    this.w = y2;
                    break;
                }
            case 1:
            case 3:
                if (this.t != 2) {
                    this.t = 0;
                    this.x.clear();
                    break;
                } else {
                    b(motionEvent);
                    super.onTouchEvent(motionEvent);
                    return true;
                }
            case 2:
                switch (this.t) {
                    case 1:
                        float x3 = motionEvent.getX();
                        float y3 = motionEvent.getY();
                        if (Math.abs(x3 - this.v) > ((float) this.u) || Math.abs(y3 - this.w) > ((float) this.u)) {
                            this.t = 2;
                            getParent().requestDisallowInterceptTouchEvent(true);
                            this.v = x3;
                            this.w = y3;
                            return true;
                        }
                    case 2:
                        float x4 = motionEvent.getX();
                        int thumbScrollRange = getThumbScrollRange();
                        float f2 = x4 - this.v;
                        float f3 = thumbScrollRange != 0 ? f2 / ((float) thumbScrollRange) : f2 > BitmapDescriptorFactory.HUE_RED ? 1.0f : -1.0f;
                        if (ViewUtils.isLayoutRtl(this)) {
                            f3 = -f3;
                        }
                        float a2 = a(this.z + f3, BitmapDescriptorFactory.HUE_RED, 1.0f);
                        if (a2 != this.z) {
                            this.v = x4;
                            setThumbPosition(a2);
                        }
                        return true;
                }
                break;
        }
        return super.onTouchEvent(motionEvent);
    }

    private void a(MotionEvent motionEvent) {
        MotionEvent obtain = MotionEvent.obtain(motionEvent);
        obtain.setAction(3);
        super.onTouchEvent(obtain);
        obtain.recycle();
    }

    private void b(MotionEvent motionEvent) {
        boolean z2;
        this.t = 0;
        boolean z3 = true;
        boolean z4 = motionEvent.getAction() == 1 && isEnabled();
        boolean isChecked = isChecked();
        if (z4) {
            this.x.computeCurrentVelocity(1000);
            float xVelocity = this.x.getXVelocity();
            if (Math.abs(xVelocity) > ((float) this.y)) {
                if (!ViewUtils.isLayoutRtl(this) ? xVelocity <= BitmapDescriptorFactory.HUE_RED : xVelocity >= BitmapDescriptorFactory.HUE_RED) {
                    z3 = false;
                }
                z2 = z3;
            } else {
                z2 = getTargetCheckedState();
            }
        } else {
            z2 = isChecked;
        }
        if (z2 != isChecked) {
            playSoundEffect(0);
        }
        setChecked(z2);
        a(motionEvent);
    }

    private void a(boolean z2) {
        this.a = ObjectAnimator.ofFloat(this, b, new float[]{z2 ? 1.0f : BitmapDescriptorFactory.HUE_RED});
        this.a.setDuration(250);
        if (VERSION.SDK_INT >= 18) {
            this.a.setAutoCancel(true);
        }
        this.a.start();
    }

    private void c() {
        if (this.a != null) {
            this.a.cancel();
        }
    }

    private boolean getTargetCheckedState() {
        return this.z > 0.5f;
    }

    /* access modifiers changed from: 0000 */
    public void setThumbPosition(float f2) {
        this.z = f2;
        invalidate();
    }

    public void toggle() {
        setChecked(!isChecked());
    }

    public void setChecked(boolean z2) {
        super.setChecked(z2);
        boolean isChecked = isChecked();
        if (getWindowToken() == null || !ViewCompat.isLaidOut(this)) {
            c();
            setThumbPosition(isChecked ? 1.0f : BitmapDescriptorFactory.HUE_RED);
            return;
        }
        a(isChecked);
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z2, int i2, int i3, int i4, int i5) {
        int i6;
        int i7;
        int i8;
        int i9;
        int i10;
        super.onLayout(z2, i2, i3, i4, i5);
        int i11 = 0;
        if (this.c != null) {
            Rect rect = this.M;
            if (this.h != null) {
                this.h.getPadding(rect);
            } else {
                rect.setEmpty();
            }
            Rect opticalBounds = DrawableUtils.getOpticalBounds(this.c);
            int max = Math.max(0, opticalBounds.left - rect.left);
            i6 = Math.max(0, opticalBounds.right - rect.right);
            i11 = max;
        } else {
            i6 = 0;
        }
        if (ViewUtils.isLayoutRtl(this)) {
            i8 = getPaddingLeft() + i11;
            i7 = ((this.A + i8) - i11) - i6;
        } else {
            i7 = (getWidth() - getPaddingRight()) - i6;
            i8 = (i7 - this.A) + i11 + i6;
        }
        int gravity = getGravity() & 112;
        if (gravity == 16) {
            i10 = (((getPaddingTop() + getHeight()) - getPaddingBottom()) / 2) - (this.B / 2);
            i9 = this.B + i10;
        } else if (gravity != 80) {
            i10 = getPaddingTop();
            i9 = this.B + i10;
        } else {
            i9 = getHeight() - getPaddingBottom();
            i10 = i9 - this.B;
        }
        this.D = i8;
        this.E = i10;
        this.G = i9;
        this.F = i7;
    }

    public void draw(Canvas canvas) {
        Rect rect;
        int i2;
        int i3;
        Rect rect2 = this.M;
        int i4 = this.D;
        int i5 = this.E;
        int i6 = this.F;
        int i7 = this.G;
        int thumbOffset = getThumbOffset() + i4;
        if (this.c != null) {
            rect = DrawableUtils.getOpticalBounds(this.c);
        } else {
            rect = DrawableUtils.INSETS_NONE;
        }
        if (this.h != null) {
            this.h.getPadding(rect2);
            thumbOffset += rect2.left;
            if (rect != null) {
                if (rect.left > rect2.left) {
                    i4 += rect.left - rect2.left;
                }
                i2 = rect.top > rect2.top ? (rect.top - rect2.top) + i5 : i5;
                if (rect.right > rect2.right) {
                    i6 -= rect.right - rect2.right;
                }
                if (rect.bottom > rect2.bottom) {
                    i3 = i7 - (rect.bottom - rect2.bottom);
                    this.h.setBounds(i4, i2, i6, i3);
                }
            } else {
                i2 = i5;
            }
            i3 = i7;
            this.h.setBounds(i4, i2, i6, i3);
        }
        if (this.c != null) {
            this.c.getPadding(rect2);
            int i8 = thumbOffset - rect2.left;
            int i9 = thumbOffset + this.C + rect2.right;
            this.c.setBounds(i8, i5, i9, i7);
            Drawable background = getBackground();
            if (background != null) {
                DrawableCompat.setHotspotBounds(background, i8, i5, i9, i7);
            }
        }
        super.draw(canvas);
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        int i2;
        super.onDraw(canvas);
        Rect rect = this.M;
        Drawable drawable = this.h;
        if (drawable != null) {
            drawable.getPadding(rect);
        } else {
            rect.setEmpty();
        }
        int i3 = this.E + rect.top;
        int i4 = this.G - rect.bottom;
        Drawable drawable2 = this.c;
        if (drawable != null) {
            if (!this.p || drawable2 == null) {
                drawable.draw(canvas);
            } else {
                Rect opticalBounds = DrawableUtils.getOpticalBounds(drawable2);
                drawable2.copyBounds(rect);
                rect.left += opticalBounds.left;
                rect.right -= opticalBounds.right;
                int save = canvas.save();
                canvas.clipRect(rect, Op.DIFFERENCE);
                drawable.draw(canvas);
                canvas.restoreToCount(save);
            }
        }
        int save2 = canvas.save();
        if (drawable2 != null) {
            drawable2.draw(canvas);
        }
        Layout layout = getTargetCheckedState() ? this.J : this.K;
        if (layout != null) {
            int[] drawableState = getDrawableState();
            if (this.I != null) {
                this.H.setColor(this.I.getColorForState(drawableState, 0));
            }
            this.H.drawableState = drawableState;
            if (drawable2 != null) {
                Rect bounds = drawable2.getBounds();
                i2 = bounds.left + bounds.right;
            } else {
                i2 = getWidth();
            }
            canvas.translate((float) ((i2 / 2) - (layout.getWidth() / 2)), (float) (((i3 + i4) / 2) - (layout.getHeight() / 2)));
            layout.draw(canvas);
        }
        canvas.restoreToCount(save2);
    }

    public int getCompoundPaddingLeft() {
        if (!ViewUtils.isLayoutRtl(this)) {
            return super.getCompoundPaddingLeft();
        }
        int compoundPaddingLeft = super.getCompoundPaddingLeft() + this.A;
        if (!TextUtils.isEmpty(getText())) {
            compoundPaddingLeft += this.o;
        }
        return compoundPaddingLeft;
    }

    public int getCompoundPaddingRight() {
        if (ViewUtils.isLayoutRtl(this)) {
            return super.getCompoundPaddingRight();
        }
        int compoundPaddingRight = super.getCompoundPaddingRight() + this.A;
        if (!TextUtils.isEmpty(getText())) {
            compoundPaddingRight += this.o;
        }
        return compoundPaddingRight;
    }

    private int getThumbOffset() {
        float f2;
        if (ViewUtils.isLayoutRtl(this)) {
            f2 = 1.0f - this.z;
        } else {
            f2 = this.z;
        }
        return (int) ((f2 * ((float) getThumbScrollRange())) + 0.5f);
    }

    private int getThumbScrollRange() {
        Rect rect;
        if (this.h == null) {
            return 0;
        }
        Rect rect2 = this.M;
        this.h.getPadding(rect2);
        if (this.c != null) {
            rect = DrawableUtils.getOpticalBounds(this.c);
        } else {
            rect = DrawableUtils.INSETS_NONE;
        }
        return ((((this.A - this.C) - rect2.left) - rect2.right) - rect.left) - rect.right;
    }

    /* access modifiers changed from: protected */
    public int[] onCreateDrawableState(int i2) {
        int[] onCreateDrawableState = super.onCreateDrawableState(i2 + 1);
        if (isChecked()) {
            mergeDrawableStates(onCreateDrawableState, N);
        }
        return onCreateDrawableState;
    }

    /* access modifiers changed from: protected */
    public void drawableStateChanged() {
        super.drawableStateChanged();
        int[] drawableState = getDrawableState();
        Drawable drawable = this.c;
        boolean z2 = false;
        if (drawable != null && drawable.isStateful()) {
            z2 = false | drawable.setState(drawableState);
        }
        Drawable drawable2 = this.h;
        if (drawable2 != null && drawable2.isStateful()) {
            z2 |= drawable2.setState(drawableState);
        }
        if (z2) {
            invalidate();
        }
    }

    public void drawableHotspotChanged(float f2, float f3) {
        if (VERSION.SDK_INT >= 21) {
            super.drawableHotspotChanged(f2, f3);
        }
        if (this.c != null) {
            DrawableCompat.setHotspot(this.c, f2, f3);
        }
        if (this.h != null) {
            DrawableCompat.setHotspot(this.h, f2, f3);
        }
    }

    /* access modifiers changed from: protected */
    public boolean verifyDrawable(Drawable drawable) {
        return super.verifyDrawable(drawable) || drawable == this.c || drawable == this.h;
    }

    public void jumpDrawablesToCurrentState() {
        super.jumpDrawablesToCurrentState();
        if (this.c != null) {
            this.c.jumpToCurrentState();
        }
        if (this.h != null) {
            this.h.jumpToCurrentState();
        }
        if (this.a != null && this.a.isStarted()) {
            this.a.end();
            this.a = null;
        }
    }

    public void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEvent(accessibilityEvent);
        accessibilityEvent.setClassName("android.widget.Switch");
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        accessibilityNodeInfo.setClassName("android.widget.Switch");
        CharSequence charSequence = isChecked() ? this.q : this.r;
        if (!TextUtils.isEmpty(charSequence)) {
            CharSequence text = accessibilityNodeInfo.getText();
            if (TextUtils.isEmpty(text)) {
                accessibilityNodeInfo.setText(charSequence);
                return;
            }
            StringBuilder sb = new StringBuilder();
            sb.append(text);
            sb.append(TokenParser.SP);
            sb.append(charSequence);
            accessibilityNodeInfo.setText(sb);
        }
    }
}
