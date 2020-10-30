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
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.AutoCompleteTextView;
import ar.com.santander.rio.mbanking.R;

public class ClearableAutoCompleteTextView extends AutoCompleteTextView {
    boolean a = false;
    private OnClearListener b = new OnClearListener() {
        public void onClear() {
            ClearableAutoCompleteTextView.this.setText("");
        }
    };
    /* access modifiers changed from: private */
    public OnClearListener c = this.b;
    public Drawable imgClearButton = getResources().getDrawable(R.drawable.ic_clear);

    public interface OnClearListener {
        void onClear();
    }

    public boolean enoughToFilter() {
        return true;
    }

    public ClearableAutoCompleteTextView(Context context) {
        super(context);
        a();
    }

    public ClearableAutoCompleteTextView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        a();
    }

    public ClearableAutoCompleteTextView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        a();
    }

    /* access modifiers changed from: 0000 */
    public void a() {
        this.imgClearButton.setColorFilter(getResources().getColor(R.color.red_light), Mode.SRC_IN);
        this.imgClearButton.setAlpha(0);
        setCompoundDrawablesWithIntrinsicBounds(null, null, this.imgClearButton, null);
        setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {
                ClearableAutoCompleteTextView clearableAutoCompleteTextView = ClearableAutoCompleteTextView.this;
                if (clearableAutoCompleteTextView.getCompoundDrawables()[2] != null && motionEvent.getAction() == 1 && motionEvent.getX() > ((float) ((clearableAutoCompleteTextView.getWidth() - clearableAutoCompleteTextView.getPaddingRight()) - ClearableAutoCompleteTextView.this.imgClearButton.getIntrinsicWidth()))) {
                    ClearableAutoCompleteTextView.this.c.onClear();
                    ClearableAutoCompleteTextView.this.a = true;
                }
                return false;
            }
        });
        addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void afterTextChanged(Editable editable) {
                ClearableAutoCompleteTextView clearableAutoCompleteTextView = ClearableAutoCompleteTextView.this;
                boolean z = true;
                if (editable.length() < 1) {
                    z = false;
                }
                clearableAutoCompleteTextView.a(z);
            }
        });
        getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
            public void onGlobalLayout() {
                ClearableAutoCompleteTextView.this.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                int[] iArr = new int[2];
                ClearableAutoCompleteTextView.this.getLocationOnScreen(iArr);
                if (iArr[1] > 0) {
                    ClearableAutoCompleteTextView.this.setDropDownHorizontalOffset(iArr[1] * -1);
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public void a(boolean z) {
        if (z) {
            this.imgClearButton.setAlpha(255);
        } else {
            this.imgClearButton.setAlpha(0);
        }
    }

    public void setImgClearButton(Drawable drawable) {
        this.imgClearButton = drawable;
    }

    public void setOnClearListener(OnClearListener onClearListener) {
        this.c = onClearListener;
    }

    public void hideClearButton() {
        setCompoundDrawables(null, null, null, null);
    }

    public void showClearButton() {
        setCompoundDrawablesWithIntrinsicBounds(null, null, this.imgClearButton, null);
    }
}
