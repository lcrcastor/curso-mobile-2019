package ar.com.santander.rio.mbanking.view.tables;

import android.content.Context;
import android.text.InputFilter;
import android.text.InputFilter.LengthFilter;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.commons.MoneyNonDecimalTextWatcher;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CElementAcc;

public class ColumnAmountViewNonDecimal extends LinearLayout implements BaseColumn {
    public CElementAcc mCElementAcc;
    public EditText mViewInput;

    public View getViewColumn() {
        return this;
    }

    public ColumnAmountViewNonDecimal(Context context) {
        super(context);
        a();
    }

    public ColumnAmountViewNonDecimal(Context context, String str) {
        super(context);
        a();
        setValue(str);
    }

    public ColumnAmountViewNonDecimal(Context context, String str, Integer num) {
        this(context, str);
        if (num != null) {
            this.mViewInput.setId(num.intValue());
        }
    }

    public ColumnAmountViewNonDecimal(Context context, String str, int i, int i2) {
        this(context, str, Integer.valueOf(i));
    }

    public ColumnAmountViewNonDecimal(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        a();
    }

    public ColumnAmountViewNonDecimal(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        a();
    }

    private void a() {
        addView(((LayoutInflater) getContext().getSystemService("layout_inflater")).inflate(R.layout.layout_column_input_numeric, null));
        this.mViewInput = (EditText) findViewById(R.id.vInput);
        if (this.mViewInput != null) {
            this.mViewInput.setClickable(false);
            this.mViewInput.addTextChangedListener(new MoneyNonDecimalTextWatcher(this.mViewInput));
            this.mViewInput.setOnFocusChangeListener(new OnFocusChangeListener() {
                public void onFocusChange(View view, boolean z) {
                    if (z) {
                        ColumnAmountViewNonDecimal.this.mViewInput.setText("");
                    }
                }
            });
        }
        setColorInput(R.color.red_corporative_text);
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

    public View getInputView() {
        return this.mViewInput;
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
            this.mViewInput.setFocusable(false);
        }
    }

    public void setHint(String str) {
        this.mViewInput.setHint(str);
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
}
