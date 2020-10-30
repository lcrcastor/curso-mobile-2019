package ar.com.santander.rio.mbanking.app.commons;

import ar.com.santander.rio.mbanking.app.ui.Constants;
import ar.com.santander.rio.mbanking.utils.UtilsCuentas;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

public class CAmountIU {
    public static CAmountIU getInstance() {
        return new CAmountIU();
    }

    public Double getDoubleFromInputUser(String str) {
        return getDoubleFromInputUser(str, Constants.LOCALE_DEFAULT_APP);
    }

    public Double getDoubleFromInputUser(String str, Locale locale) {
        if (str != null && !str.isEmpty()) {
            try {
                Number parse = NumberFormat.getNumberInstance(locale).parse(str.trim().replace(UtilsCuentas.SEPARAOR2, "").replace(Constants.SYMBOL_CURRENCY_DOLAR, "").replace(Constants.SYMBOL_CURRENCY_PESOS, ""));
                DecimalFormat decimalFormat = (DecimalFormat) NumberFormat.getNumberInstance(Locale.ENGLISH);
                String replace = decimalFormat.format(parse).replace(String.valueOf(decimalFormat.getDecimalFormatSymbols().getGroupingSeparator()), "");
                if (a(replace)) {
                    return Double.valueOf(Double.parseDouble(replace));
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public String getOutputUserFromDouble(Double d) {
        return getOutputUserFromDouble(d, Constants.LOCALE_DEFAULT_APP);
    }

    public String getOutputUserFromDouble(Double d, Locale locale) {
        if (!(d == null || locale == null)) {
            try {
                return new DecimalFormat("#,##0.00", DecimalFormatSymbols.getInstance(locale)).format(d);
            } catch (Exception unused) {
            }
        }
        return null;
    }

    public String getOutputUserFromString(String str, Locale locale) {
        try {
            return getOutputUserFromDouble(getDoubleFromInputUser(str, locale), locale);
        } catch (Exception unused) {
            return null;
        }
    }

    private boolean a(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (Exception unused) {
            return false;
        }
    }
}
