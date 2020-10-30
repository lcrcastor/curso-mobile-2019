package ar.com.santander.rio.mbanking.view;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import ar.com.santander.rio.mbanking.R;

public class NumericEditTextWithPrefix extends NumericEditText {
    private String a;

    public NumericEditTextWithPrefix(Context context) {
        super(context);
    }

    public NumericEditTextWithPrefix(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        setCustomAttributes(attributeSet);
    }

    /* access modifiers changed from: protected */
    public String format(String str) {
        String format = super.format(str);
        if (TextUtils.isEmpty(format)) {
            return format;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(this.a);
        sb.append(format);
        return sb.toString();
    }

    private void setCustomAttributes(AttributeSet attributeSet) {
        this.a = getContext().obtainStyledAttributes(attributeSet, R.styleable.NumericEditTextWithPrefix).getString(0);
        if (TextUtils.isEmpty(this.a)) {
            this.a = String.valueOf("");
        }
    }
}
