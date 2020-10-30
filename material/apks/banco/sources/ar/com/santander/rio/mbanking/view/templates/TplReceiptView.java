package ar.com.santander.rio.mbanking.view.templates;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import ar.com.santander.rio.mbanking.R;

public class TplReceiptView {
    private Context a;

    public TplReceiptView(Context context) {
        this.a = context;
    }

    public View getViewTemplate() {
        return ((LayoutInflater) this.a.getSystemService("layout_inflater")).inflate(R.layout.layout_tpl_receipt, null);
    }
}
