package ar.com.santander.rio.mbanking.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CElementAcc;
import ar.com.santander.rio.mbanking.view.tables.BaseColumn;

public class AmountView extends AutoFitTextView implements BaseColumn {
    private CElementAcc a;

    public View getViewColumn() {
        return this;
    }

    public AmountView(Context context) {
        super(context);
        a();
    }

    public AmountView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        a();
    }

    private void a() {
        setSingleLine(true);
    }

    public void setAmount(String str) {
        try {
            setText(str);
            if (this.a != null) {
                this.a.applyFilter(this, str);
            }
        } catch (Exception unused) {
        }
    }

    public void setColorAmount(boolean z) {
        if (z) {
            setColorAmountPossitive();
        } else {
            setColorAmountNegative();
        }
    }

    public void setColorAmountNegative() {
        setTextColor(getResources().getColor(R.color.generic_black));
    }

    public void setColorAmountPossitive() {
        setTextColor(getResources().getColor(R.color.generic_black));
    }

    public CElementAcc getCElementAcc() {
        return this.a;
    }

    public void setCElementAcc(CElementAcc cElementAcc) {
        this.a = cElementAcc;
        setAmount(getText().toString());
    }
}
