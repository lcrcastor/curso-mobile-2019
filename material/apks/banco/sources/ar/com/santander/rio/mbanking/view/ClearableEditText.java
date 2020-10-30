package ar.com.santander.rio.mbanking.view;

import android.content.Context;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.EditText;
import ar.com.santander.rio.mbanking.R;

public class ClearableEditText extends EditText {
    boolean a;
    /* access modifiers changed from: private */
    public TextWatcher b;
    /* access modifiers changed from: private */
    public int c;
    private int d;
    private int e;
    private OnClearListener f;
    /* access modifiers changed from: private */
    public OnClearListener g;
    public Drawable imgClearButton;

    public interface OnClearListener {
        void onClear();
    }

    public ClearableEditText(Context context) {
        super(context);
        this.imgClearButton = getResources().getDrawable(R.drawable.ic_clear);
        this.a = false;
        this.c = 1;
        this.d = 255;
        this.f = new OnClearListener() {
            public void onClear() {
                ClearableEditText.this.setText("");
            }
        };
        this.g = this.f;
        this.e = getResources().getColor(R.color.red_light);
        a();
    }

    public ClearableEditText(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.imgClearButton = getResources().getDrawable(R.drawable.ic_clear);
        this.a = false;
        this.c = 1;
        this.d = 255;
        this.f = new OnClearListener() {
            public void onClear() {
                ClearableEditText.this.setText("");
            }
        };
        this.g = this.f;
        setCustomAttributes(attributeSet);
        a();
    }

    public ClearableEditText(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.imgClearButton = getResources().getDrawable(R.drawable.ic_clear);
        this.a = false;
        this.c = 1;
        this.d = 255;
        this.f = new OnClearListener() {
            public void onClear() {
                ClearableEditText.this.setText("");
            }
        };
        this.g = this.f;
        setCustomAttributes(attributeSet);
        a();
    }

    private void setCustomAttributes(AttributeSet attributeSet) {
        this.e = getContext().obtainStyledAttributes(attributeSet, R.styleable.ClearableEditText).getColor(0, getResources().getColor(R.color.red_light));
    }

    public void callOnClear() {
        this.g.onClear();
        this.a = true;
    }

    /* access modifiers changed from: 0000 */
    public void a() {
        this.imgClearButton.setColorFilter(this.e, Mode.SRC_IN);
        setCompoundDrawablesWithIntrinsicBounds(null, null, this.imgClearButton, null);
        a(length() >= this.c);
        setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {
                ClearableEditText clearableEditText = ClearableEditText.this;
                if (clearableEditText.getCompoundDrawables()[2] != null && motionEvent.getAction() == 1 && motionEvent.getX() > ((float) ((clearableEditText.getWidth() - clearableEditText.getPaddingRight()) - ClearableEditText.this.imgClearButton.getIntrinsicWidth()))) {
                    ClearableEditText.this.g.onClear();
                    ClearableEditText.this.a = true;
                }
                return false;
            }
        });
        addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                if (ClearableEditText.this.b != null) {
                    ClearableEditText.this.b.beforeTextChanged(charSequence, i, i2, i3);
                }
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                if (ClearableEditText.this.b != null) {
                    ClearableEditText.this.b.onTextChanged(charSequence, i, i2, i3);
                }
            }

            public void afterTextChanged(Editable editable) {
                ClearableEditText.this.a(editable.length() >= ClearableEditText.this.c);
                if (ClearableEditText.this.b != null) {
                    ClearableEditText.this.b.afterTextChanged(editable);
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public void a(boolean z) {
        if (z) {
            this.imgClearButton.setAlpha(this.d);
        } else {
            this.imgClearButton.setAlpha(0);
        }
    }

    public void setImgClearButton(Drawable drawable) {
        this.imgClearButton = drawable;
        this.d = this.imgClearButton.getAlpha();
    }

    public void setOnClearListener(OnClearListener onClearListener) {
        this.g = onClearListener;
    }

    public void hideClearButton() {
        setCompoundDrawables(null, null, null, null);
    }

    public void showClearButton() {
        setCompoundDrawablesWithIntrinsicBounds(null, null, this.imgClearButton, null);
    }

    public void setOnTextWatcher(TextWatcher textWatcher) {
        this.b = textWatcher;
    }

    public void minLengthToShowClearButton(int i) {
        this.c = i;
    }

    public void setOriginalAlpha(int i) {
        this.d = i;
    }
}
