package ar.com.santander.rio.mbanking.view.accessibility;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.accessibility.AccessibilityManager;
import android.widget.EditText;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility;

public abstract class EtAccessibility extends EditText {
    /* access modifiers changed from: private */
    public CAccessibility a;

    public abstract String applyFilters(CAccessibility cAccessibility, String str);

    public EtAccessibility(Context context) {
        super(context);
    }

    public EtAccessibility(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public EtAccessibility(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public boolean isAccessibilityEnabled() {
        Context context = getContext();
        getContext();
        return ((AccessibilityManager) context.getSystemService("accessibility")).isEnabled();
    }

    public boolean isExploreByTouchEnabled() {
        Context context = getContext();
        getContext();
        return ((AccessibilityManager) context.getSystemService("accessibility")).isTouchExplorationEnabled();
    }

    public void setTextAccesibility(final String str) {
        setText(str);
        try {
            new Handler().post(new Runnable() {
                public void run() {
                    EtAccessibility.this.a = new CAccessibility(EtAccessibility.this.getContext());
                    try {
                        EtAccessibility.this.setContentDescription(EtAccessibility.this.applyFilters(EtAccessibility.this.a, str));
                    } catch (Exception unused) {
                        EtAccessibility.this.setContentDescription(str);
                    }
                }
            });
        } catch (Exception unused) {
            setContentDescription(str);
        }
    }
}
