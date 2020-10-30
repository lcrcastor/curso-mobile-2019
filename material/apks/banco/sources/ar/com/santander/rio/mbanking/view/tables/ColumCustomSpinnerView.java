package ar.com.santander.rio.mbanking.view.tables;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CElementAcc;
import ar.com.santander.rio.mbanking.view.CustomSpinner;

public class ColumCustomSpinnerView extends LinearLayout implements BaseColumn {
    private CElementAcc a;
    public CustomSpinner vgCustomSpinner;

    public View getViewColumn() {
        return this;
    }

    public ColumCustomSpinnerView(Context context) {
        super(context);
        a();
    }

    public ColumCustomSpinnerView(Context context, String str) {
        super(context);
        a();
        setLabelSpinner(str);
    }

    public ColumCustomSpinnerView(Context context, String str, int i) {
        super(context);
        a();
        setLabelSpinner(str);
        this.vgCustomSpinner.setId(i);
    }

    public ColumCustomSpinnerView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        a();
    }

    public ColumCustomSpinnerView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        a();
    }

    private void a() {
        addView(((LayoutInflater) getContext().getSystemService("layout_inflater")).inflate(R.layout.layout_column_custom_spinner, null));
        this.vgCustomSpinner = (CustomSpinner) findViewById(R.id.vgCustomSpinner);
    }

    public void setLabelSpinner(String str) {
        this.vgCustomSpinner.setLabel(str);
        if (this.a != null) {
            this.a.applyFilter(this.vgCustomSpinner, str);
        }
    }

    public CustomSpinner getViewSpinner() {
        return this.vgCustomSpinner;
    }

    public void setCElementAcc(CElementAcc cElementAcc) {
        this.a = cElementAcc;
    }

    public CElementAcc getCElementAcc() {
        return this.a;
    }
}
