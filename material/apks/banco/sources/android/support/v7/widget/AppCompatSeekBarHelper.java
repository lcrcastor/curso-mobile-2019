package android.support.v7.widget;

import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.appcompat.R;
import android.util.AttributeSet;
import android.widget.SeekBar;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

class AppCompatSeekBarHelper extends AppCompatProgressBarHelper {
    private final SeekBar a;
    private Drawable b;
    private ColorStateList c = null;
    private Mode d = null;
    private boolean e = false;
    private boolean f = false;

    AppCompatSeekBarHelper(SeekBar seekBar) {
        super(seekBar);
        this.a = seekBar;
    }

    /* access modifiers changed from: 0000 */
    public void a(AttributeSet attributeSet, int i) {
        super.a(attributeSet, i);
        TintTypedArray obtainStyledAttributes = TintTypedArray.obtainStyledAttributes(this.a.getContext(), attributeSet, R.styleable.AppCompatSeekBar, i, 0);
        Drawable drawableIfKnown = obtainStyledAttributes.getDrawableIfKnown(R.styleable.AppCompatSeekBar_android_thumb);
        if (drawableIfKnown != null) {
            this.a.setThumb(drawableIfKnown);
        }
        a(obtainStyledAttributes.getDrawable(R.styleable.AppCompatSeekBar_tickMark));
        if (obtainStyledAttributes.hasValue(R.styleable.AppCompatSeekBar_tickMarkTintMode)) {
            this.d = DrawableUtils.parseTintMode(obtainStyledAttributes.getInt(R.styleable.AppCompatSeekBar_tickMarkTintMode, -1), this.d);
            this.f = true;
        }
        if (obtainStyledAttributes.hasValue(R.styleable.AppCompatSeekBar_tickMarkTint)) {
            this.c = obtainStyledAttributes.getColorStateList(R.styleable.AppCompatSeekBar_tickMarkTint);
            this.e = true;
        }
        obtainStyledAttributes.recycle();
        d();
    }

    /* access modifiers changed from: 0000 */
    public void a(@Nullable Drawable drawable) {
        if (this.b != null) {
            this.b.setCallback(null);
        }
        this.b = drawable;
        if (drawable != null) {
            drawable.setCallback(this.a);
            DrawableCompat.setLayoutDirection(drawable, ViewCompat.getLayoutDirection(this.a));
            if (drawable.isStateful()) {
                drawable.setState(this.a.getDrawableState());
            }
            d();
        }
        this.a.invalidate();
    }

    private void d() {
        if (this.b == null) {
            return;
        }
        if (this.e || this.f) {
            this.b = DrawableCompat.wrap(this.b.mutate());
            if (this.e) {
                DrawableCompat.setTintList(this.b, this.c);
            }
            if (this.f) {
                DrawableCompat.setTintMode(this.b, this.d);
            }
            if (this.b.isStateful()) {
                this.b.setState(this.a.getDrawableState());
            }
        }
    }

    /* access modifiers changed from: 0000 */
    @RequiresApi(11)
    public void b() {
        if (this.b != null) {
            this.b.jumpToCurrentState();
        }
    }

    /* access modifiers changed from: 0000 */
    public void c() {
        Drawable drawable = this.b;
        if (drawable != null && drawable.isStateful() && drawable.setState(this.a.getDrawableState())) {
            this.a.invalidateDrawable(drawable);
        }
    }

    /* access modifiers changed from: 0000 */
    public void a(Canvas canvas) {
        if (this.b != null) {
            int max = this.a.getMax();
            int i = 1;
            if (max > 1) {
                int intrinsicWidth = this.b.getIntrinsicWidth();
                int intrinsicHeight = this.b.getIntrinsicHeight();
                int i2 = intrinsicWidth >= 0 ? intrinsicWidth / 2 : 1;
                if (intrinsicHeight >= 0) {
                    i = intrinsicHeight / 2;
                }
                this.b.setBounds(-i2, -i, i2, i);
                float width = ((float) ((this.a.getWidth() - this.a.getPaddingLeft()) - this.a.getPaddingRight())) / ((float) max);
                int save = canvas.save();
                canvas.translate((float) this.a.getPaddingLeft(), (float) (this.a.getHeight() / 2));
                for (int i3 = 0; i3 <= max; i3++) {
                    this.b.draw(canvas);
                    canvas.translate(width, BitmapDescriptorFactory.HUE_RED);
                }
                canvas.restoreToCount(save);
            }
        }
    }
}
