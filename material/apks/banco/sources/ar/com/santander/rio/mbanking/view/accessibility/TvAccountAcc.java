package ar.com.santander.rio.mbanking.view.accessibility;

import android.content.Context;
import android.util.AttributeSet;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility;

public class TvAccountAcc extends TvAccessibility {
    public TvAccountAcc(Context context) {
        super(context);
    }

    public TvAccountAcc(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public TvAccountAcc(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public String applyFilters(CAccessibility cAccessibility, String str) {
        return cAccessibility.applyFilterAccount(str);
    }
}
