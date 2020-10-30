package ar.com.santander.rio.mbanking.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.View.MeasureSpec;
import ar.com.santander.rio.mbanking.R;

public class AutoFitTextView extends AppCompatTextView {
    private int a;
    private int b;
    private Mode c;
    private boolean d;
    private int e;
    private int f;

    enum Mode {
        Width,
        Height,
        Both,
        None
    }

    public AutoFitTextView(Context context) {
        super(context);
        this.a = 1;
        this.b = 1000;
        this.c = Mode.None;
    }

    public AutoFitTextView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public AutoFitTextView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.a = 1;
        this.b = 1000;
        this.c = Mode.None;
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.AutoFitTextView, i, 0);
        this.b = obtainStyledAttributes.getDimensionPixelSize(0, this.b);
        this.a = obtainStyledAttributes.getDimensionPixelSize(1, this.a);
        obtainStyledAttributes.recycle();
    }

    private void a() {
        if (getWidth() > 0 && getHeight() > 0 && this.c != Mode.None) {
            int width = getWidth();
            int height = getHeight();
            this.d = true;
            float f2 = (float) this.b;
            float f3 = (float) this.a;
            getTextSize();
            while (f2 - f3 > 0.5f) {
                float f4 = (f2 + f3) / 2.0f;
                if (a(f4, width, height)) {
                    f2 = f4;
                } else {
                    f3 = f4;
                }
            }
            setTextSize(0, f3);
            measure(this.e, this.f);
            this.d = false;
        }
    }

    private boolean a(float f2, int i, int i2) {
        boolean z = false;
        setTextSize(0, f2);
        measure(0, 0);
        if (this.c == Mode.Both) {
            if (getMeasuredWidth() >= i || getMeasuredHeight() >= i2) {
                z = true;
            }
            return z;
        } else if (this.c == Mode.Width) {
            if (getMeasuredWidth() >= i) {
                z = true;
            }
            return z;
        } else {
            if (getMeasuredHeight() >= i2) {
                z = true;
            }
            return z;
        }
    }

    private Mode a(int i, int i2) {
        int mode = MeasureSpec.getMode(i);
        int mode2 = MeasureSpec.getMode(i2);
        if (mode == 1073741824 && mode2 == 1073741824) {
            return Mode.Both;
        }
        if (mode == 1073741824) {
            return Mode.Width;
        }
        if (mode2 == 1073741824) {
            return Mode.Height;
        }
        return Mode.None;
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        if (!this.d) {
            this.e = i;
            this.f = i2;
            this.c = a(i, i2);
            a();
        }
    }

    /* access modifiers changed from: protected */
    public int getSuggestedMinimumWidth() {
        Drawable background = getBackground();
        setBackground(null);
        int suggestedMinimumWidth = super.getSuggestedMinimumWidth();
        setBackground(background);
        return suggestedMinimumWidth;
    }

    /* access modifiers changed from: protected */
    public int getSuggestedMinimumHeight() {
        Drawable background = getBackground();
        setBackground(null);
        int suggestedMinimumHeight = super.getSuggestedMinimumHeight();
        setBackground(background);
        return suggestedMinimumHeight;
    }

    public void setBackground(Drawable drawable) {
        if (VERSION.SDK_INT > 16) {
            super.setBackground(drawable);
        } else {
            setBackgroundDrawable(drawable);
        }
    }

    /* access modifiers changed from: protected */
    public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        a();
    }

    /* access modifiers changed from: protected */
    public void onSizeChanged(int i, int i2, int i3, int i4) {
        if (i != i3 || i2 != i4) {
            a();
        }
    }

    public int getMinTextSize() {
        return this.a;
    }

    public void setMinTextSize(int i) {
        this.a = i;
        a();
    }

    public int getMaxTextSize() {
        return this.b;
    }

    public void setMaxTextSize(int i) {
        this.b = i;
        a();
    }
}
