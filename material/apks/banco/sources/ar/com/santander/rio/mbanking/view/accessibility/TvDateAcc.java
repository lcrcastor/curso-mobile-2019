package ar.com.santander.rio.mbanking.view.accessibility;

import android.content.Context;
import android.util.AttributeSet;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility;

public class TvDateAcc extends TvAccessibility {
    public TvDateAcc(Context context) {
        super(context);
    }

    public TvDateAcc(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public TvDateAcc(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public String applyFilters(CAccessibility cAccessibility, String str) {
        return cAccessibility.applyFilterDate(str);
    }
}
