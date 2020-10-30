package ar.com.santander.rio.mbanking.app.commons.accessibilty;

import android.content.Context;
import android.view.View;
import android.view.accessibility.AccessibilityManager;

public abstract class CElementBaseAcc implements CElementAcc {
    private CAccessibility a;

    /* access modifiers changed from: protected */
    public abstract String getStringAfterApplyFilter(CAccessibility cAccessibility, String str);

    public boolean isAccessibilityEnabled(Context context) {
        return ((AccessibilityManager) context.getSystemService("accessibility")).isEnabled();
    }

    public boolean isExploreByTouchEnabled(Context context) {
        return ((AccessibilityManager) context.getSystemService("accessibility")).isTouchExplorationEnabled();
    }

    private void a(View view, String str) {
        try {
            this.a = new CAccessibility(view.getContext());
            try {
                view.setContentDescription(getStringAfterApplyFilter(this.a, str));
            } catch (Exception e) {
                e.fillInStackTrace();
                view.setContentDescription(str);
            }
        } catch (Exception e2) {
            e2.fillInStackTrace();
            view.setContentDescription(str);
        }
    }

    public void applyFilter(View view, String str) {
        a(view, str);
    }
}
