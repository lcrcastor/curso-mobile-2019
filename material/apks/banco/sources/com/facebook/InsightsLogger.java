package com.facebook;

import android.content.Context;
import android.os.Bundle;
import com.facebook.internal.Logger;
import java.math.BigDecimal;
import java.util.Currency;

@Deprecated
public class InsightsLogger {
    private AppEventsLogger a;

    private InsightsLogger(Context context, String str, Session session) {
        this.a = AppEventsLogger.newLogger(context, str, session);
    }

    public static InsightsLogger newLogger(Context context, String str) {
        return new InsightsLogger(context, null, null);
    }

    public static InsightsLogger newLogger(Context context, String str, String str2) {
        return new InsightsLogger(context, str2, null);
    }

    public static InsightsLogger newLogger(Context context, String str, String str2, Session session) {
        return new InsightsLogger(context, str2, session);
    }

    public void logPurchase(BigDecimal bigDecimal, Currency currency) {
        logPurchase(bigDecimal, currency, null);
    }

    public void logPurchase(BigDecimal bigDecimal, Currency currency, Bundle bundle) {
        this.a.logPurchase(bigDecimal, currency, bundle);
    }

    public void logConversionPixel(String str, double d) {
        if (str == null) {
            Logger.log(LoggingBehavior.DEVELOPER_ERRORS, "Insights", "pixelID cannot be null");
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putString("fb_offsite_pixel_id", str);
        bundle.putDouble("fb_offsite_pixel_value", d);
        this.a.logEvent("fb_log_offsite_pixel", d, bundle);
        AppEventsLogger.a();
    }
}
