package ar.com.santander.rio.mbanking.view;

import android.content.Context;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View.MeasureSpec;
import android.widget.TextView;

public class FontFitTextView extends TextView {
    private Paint a;

    public FontFitTextView(Context context) {
        super(context);
        a();
    }

    public FontFitTextView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        a();
    }

    private void a() {
        this.a = new Paint();
        this.a.set(getPaint());
    }

    private void a(String str, int i) {
        if (i > 0) {
            int paddingLeft = (i - getPaddingLeft()) - getPaddingRight();
            float f = 100.0f;
            this.a.set(getPaint());
            float f2 = 2.0f;
            while (f - f2 > 0.5f) {
                float f3 = (f + f2) / 2.0f;
                this.a.setTextSize(f3);
                if (this.a.measureText(str) >= ((float) paddingLeft)) {
                    f = f3;
                } else {
                    f2 = f3;
                }
            }
            setTextSize(0, f2);
        }
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        int size = MeasureSpec.getSize(i);
        int measuredHeight = getMeasuredHeight();
        a(getText().toString(), size);
        setMeasuredDimension(size, measuredHeight);
    }

    /* access modifiers changed from: protected */
    public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        a(charSequence.toString(), getWidth());
    }

    /* access modifiers changed from: protected */
    public void onSizeChanged(int i, int i2, int i3, int i4) {
        if (i != i3) {
            a(getText().toString(), i);
        }
    }
}
