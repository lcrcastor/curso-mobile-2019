package ar.com.santander.rio.mbanking.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CElementAcc;
import ar.com.santander.rio.mbanking.utils.UtilDate;
import ar.com.santander.rio.mbanking.view.tables.BaseColumn;

public class DateView extends TextView implements BaseColumn {
    private CElementAcc a;

    public View getViewColumn() {
        return this;
    }

    public DateView(Context context) {
        super(context);
    }

    public DateView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public DateView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public void setStringDate(String str) {
        setText(UtilDate.getDateFormat(str));
        a();
    }

    public void setStringDate(String str, String str2) {
        setText(UtilDate.getDateFormat(str, str2));
        a();
    }

    public void setDateStr(String str) {
        setText(str);
        a();
    }

    public void setCElementAcc(CElementAcc cElementAcc) {
        this.a = cElementAcc;
        a();
    }

    private void a() {
        if (this.a != null) {
            this.a.applyFilter(this, getText().toString());
        }
    }

    public CElementAcc getCElementAcc() {
        return this.a;
    }
}
