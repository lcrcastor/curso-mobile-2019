package ar.com.santander.rio.mbanking.utils;

import android.content.Context;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import ar.com.santander.rio.mbanking.app.ui.Constants;

public class UtilAccesibility {
    public static String AMEX_CAPITAL = "AMEX";
    public static String AMEX_LOWER = "amex";

    public static void sendAccessibilityEvent(Context context, String str) {
        AccessibilityManager accessibilityManager = (AccessibilityManager) context.getApplicationContext().getSystemService("accessibility");
        if (accessibilityManager.isEnabled() && accessibilityManager.isTouchExplorationEnabled()) {
            AccessibilityEvent obtain = AccessibilityEvent.obtain();
            obtain.setEventType(16384);
            obtain.getText().add(str);
            accessibilityManager.sendAccessibilityEvent(obtain);
        }
    }

    public static String replaceAMEXCapital(String str) {
        return str.replaceAll(AMEX_CAPITAL, AMEX_LOWER);
    }

    public static String getSeparatedNumber(String str) {
        return str.replaceAll(".(?=.)", "$0 ");
    }

    public static String getTasaAccesibility(String str) {
        String replace = str.replace(Constants.SYMBOL_PERCENTAGE, "");
        String[] split = replace.split(",");
        if (split.length <= 1) {
            return replace;
        }
        String str2 = split[0];
        if (!split[1].equalsIgnoreCase("0") && !split[1].equalsIgnoreCase("00")) {
            StringBuilder sb = new StringBuilder();
            sb.append(str2);
            sb.append(" coma ");
            sb.append(split[1]);
            str2 = sb.toString();
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append(str2);
        sb2.append(" %");
        return sb2.toString();
    }
}
