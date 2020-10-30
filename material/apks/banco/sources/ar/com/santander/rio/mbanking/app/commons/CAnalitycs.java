package ar.com.santander.rio.mbanking.app.commons;

import android.content.Context;
import ar.com.santander.rio.mbanking.R;

public class CAnalitycs {
    public static String getHitDetalleCajero(Context context, String str) {
        if (str != null) {
            try {
                if (str.toLowerCase().contains("link_")) {
                    return context.getString(R.string.analytics_screen_name_link);
                }
                if (str.toLowerCase().contains("santander_")) {
                    return context.getString(R.string.analytics_screen_name_rio);
                }
                if (str.toLowerCase().contains("banelco_")) {
                    return context.getString(R.string.analytics_screen_name_banelco);
                }
            } catch (Exception unused) {
            }
        }
        return null;
    }
}
