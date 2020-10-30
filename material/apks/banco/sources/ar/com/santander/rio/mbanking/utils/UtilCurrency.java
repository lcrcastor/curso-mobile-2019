package ar.com.santander.rio.mbanking.utils;

import android.content.Context;
import android.text.Html;
import android.text.TextUtils;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.ui.Constants;
import ar.com.santander.rio.mbanking.app.ui.constants.TarjetasConstants;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class UtilCurrency {
    public static String getCurrencyFromSymbol(Context context, String str) {
        if (Constants.SYMBOL_CURRENCY_PESOS_STR.equals(str)) {
            return context.getString(R.string.TEXT_NAME_PESOS);
        }
        return context.getString(R.string.TEXT_NAME_DOLLAR);
    }

    public static String getCurrencyFromValue(Context context, boolean z) {
        if (z) {
            return context.getString(R.string.TEXT_NAME_DOLLAR);
        }
        return context.getString(R.string.TEXT_NAME_PESOS);
    }

    public static String getSimbolCurrencyFromString(String str) {
        return getSimbolCurrencyFromString(str, "");
    }

    public static String getSimbolCurrencyFromString(String str, String str2) {
        if (Constants.SYMBOL_CURRENCY_PESOS_STR.equals(str)) {
            return Constants.SYMBOL_CURRENCY_PESOS;
        }
        return Constants.SYMBOL_CURRENCY_DOLLAR_STR.equals(str) ? Constants.SYMBOL_CURRENCY_DOLAR : str2;
    }

    public static String getSimbolCurrencyFromDescription(String str) {
        return getSimbolCurrencyFromDescription(str, "");
    }

    public static String getSimbolCurrencyFromDescription(String str, String str2) {
        if (Constants.SYMBOL_CURRENCY_PESOS_DESCRIPTION.equalsIgnoreCase(str)) {
            return Constants.SYMBOL_CURRENCY_PESOS;
        }
        return Constants.SYMBOL_CURRENCY_DOLLAR_DESCRIPTION.equalsIgnoreCase(str) ? Constants.SYMBOL_CURRENCY_DOLAR : str2;
    }

    public static String getFormattedAmountInFromStringCompTarjetas(String str) {
        StringBuilder sb = new StringBuilder();
        sb.append(str.substring(0, str.length() - 2));
        sb.append(".");
        sb.append(str.substring(str.length() - 2, str.length() - 1));
        return sb.toString();
    }

    public static String getFormattedAmountInArsFromString(String str) {
        try {
            DecimalFormat decimalFormat = (DecimalFormat) NumberFormat.getNumberInstance(new Locale("es", "AR"));
            decimalFormat.applyPattern("###,###,###,##0.00");
            return decimalFormat.format(Double.parseDouble(str.replaceFirst("^0+", "")));
        } catch (Exception unused) {
            return str;
        }
    }

    public static boolean isDolares(String str) {
        boolean z = false;
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        if (Html.fromHtml(str).toString().equalsIgnoreCase(TarjetasConstants.DOLAR) || Html.fromHtml(str).toString().equalsIgnoreCase("dólares")) {
            z = true;
        }
        return z;
    }

    public static boolean isPesos(String str) {
        if (!TextUtils.isEmpty(str)) {
            return Html.fromHtml(str).toString().equalsIgnoreCase(TarjetasConstants.PESOS);
        }
        return false;
    }
}
