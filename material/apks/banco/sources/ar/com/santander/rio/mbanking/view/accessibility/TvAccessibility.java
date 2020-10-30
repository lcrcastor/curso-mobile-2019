package ar.com.santander.rio.mbanking.view.accessibility;

import android.content.Context;
import android.util.AttributeSet;
import android.view.accessibility.AccessibilityManager;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility;

public abstract class TvAccessibility extends TextView {
    public abstract String applyFilters(CAccessibility cAccessibility, String str);

    public TvAccessibility(Context context) {
        super(context);
    }

    public TvAccessibility(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public TvAccessibility(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public boolean isAccessibilityEnabled() {
        Context context = getContext();
        getContext();
        return ((AccessibilityManager) context.getSystemService("accessibility")).isEnabled();
    }
}
