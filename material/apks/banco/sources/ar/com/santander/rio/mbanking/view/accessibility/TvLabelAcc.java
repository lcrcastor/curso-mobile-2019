package ar.com.santander.rio.mbanking.view.accessibility;

import android.content.Context;
import android.util.AttributeSet;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility;

public class TvLabelAcc extends TvAccessibility {
    public TvLabelAcc(Context context) {
        super(context);
    }

    public TvLabelAcc(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public TvLabelAcc(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public String applyFilters(CAccessibility cAccessibility, String str) {
        return cAccessibility.applyFilterAmount(str);
    }
}
