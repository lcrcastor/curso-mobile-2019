package ar.com.santander.rio.mbanking.view.tables;

import android.content.Context;
import android.text.InputFilter;
import android.text.InputFilter.LengthFilter;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.LinearLayout;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CElementAcc;
import ar.com.santander.rio.mbanking.view.NumericEditText;

public class ColumAmountView extends LinearLayout implements BaseColumn {
    public CElementAcc mCElementAcc;
    public NumericEditText mViewInput;

    public View getViewColumn() {
        return this;
    }

    public ColumAmountView(Context context) {
        super(context);
        a();
    }

    public ColumAmountView(Context context, String str) {
        super(context);
        a();
        setValue(str);
    }

    public ColumAmountView(Context context, String str, Integer num) {
        super(context);
        a();
        if (num != null) {
            this.mViewInput.setId(num.intValue());
        }
        setValue(str);
    }

    public ColumAmountView(Context context, String str, Integer num, boolean z) {
        super(context);
        a();
        if (num != null) {
            this.mViewInput.setId(num.intValue());
        }
        setValue(str);
    }

    public ColumAmountView(Context context, String str, int i, int i2) {
        this(context, str, Integer.valueOf(i));
    }

    public ColumAmountView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        a();
    }

    public ColumAmountView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        a();
    }

    private void a() {
        addView(((LayoutInflater) getContext().getSystemService("layout_inflater")).inflate(R.layout.layout_column_input_numeric, null));
        this.mViewInput = (NumericEditText) findViewById(R.id.vInput);
        if (this.mViewInput != null) {
            this.mViewInput.setOnFocusChangeListener(new OnFocusChangeListener() {
                public void onFocusChange(View view, boolean z) {
                    if (view instanceof NumericEditText) {
                        if (z) {
                            try {
                                if (ColumAmountView.this.mViewInput.getNumericValue() == 0.0d) {
                                    ColumAmountView.this.mViewInput.setText("");
                                }
                            } catch (Exception unused) {
                                return;
                            }
                        }
                        ColumAmountView.this.mViewInput.onFocusChangeExtend(view, z);
                    }
                }
            });
        }
        setColorInput(R.color.generic_black);
    }

    public void setValue(String str) {
        if (this.mViewInput != null) {
            this.mViewInput.setText(str);
            if (this.mCElementAcc != null) {
                this.mCElementAcc.applyFilter(this.mViewInput, str);
            }
        }
    }

    public void setColorInput(int i) {
        this.mViewInput.setTextColor(getResources().getColor(i));
    }

    public void setEditable() {
        if (this.mViewInput != null) {
            this.mViewInput.setBackground(getResources().getDrawable(R.drawable.edittext_complete));
            this.mViewInput.setFocusable(true);
            this.mViewInput.setFocusableInTouchMode(true);
            this.mViewInput.requestFocus();
        }
    }

    public void setReadOnly() {
        if (this.mViewInput != null) {
            this.mViewInput.setBackground(getResources().getDrawable(R.drawable.edittext_noborder));
            this.mViewInput.setClickable(false);
            this.mViewInput.setFocusable(false);
        }
    }

    public void setMaxLenght(int i) {
        if (this.mViewInput != null) {
            this.mViewInput.setFilters(new InputFilter[]{new LengthFilter(i)});
        }
    }

    public CElementAcc getCElementAcc() {
        return this.mCElementAcc;
    }

    public void setCElementAcc(CElementAcc cElementAcc) {
        this.mCElementAcc = cElementAcc;
        if (this.mViewInput != null) {
            setValue(this.mViewInput.getText().toString());
        }
    }

    public void setImeOpts(int i) {
        if (this.mViewInput != null) {
            this.mViewInput.setImeOptions(i);
        }
    }

    public void setMaxPlaceInteger(int i) {
        if (this.mViewInput != null) {
            this.mViewInput.setMaxPlaceInteger(i);
        }
    }

    public void setMaxPlaceDecimal(int i) {
        if (this.mViewInput != null) {
            this.mViewInput.setMaxPlaceDecimal(i);
        }
    }
}
