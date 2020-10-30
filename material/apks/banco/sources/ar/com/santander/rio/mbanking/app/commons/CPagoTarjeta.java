package ar.com.santander.rio.mbanking.app.commons;

import ar.com.santander.rio.mbanking.app.ui.Constants;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class CPagoTarjeta {
    public static final String MASK_AMOUNT_OUT = "000000000000.00";
    public static final String MASK_AMOUNT_USD_OUT = "0000000000000.00";

    public static String buildImportePagoTC(String str, Locale locale) {
        if (str != null) {
            try {
                if (!str.isEmpty()) {
                    Double doubleFromInputUser = new CAmountIU().getDoubleFromInputUser(str, new Locale("es", "AR"));
                    if (doubleFromInputUser != null) {
                        String format = new DecimalFormat("000000000000.00", DecimalFormatSymbols.getInstance(Constants.LOCALE_DEFAULT_WS)).format(doubleFromInputUser);
                        if (format != null && !format.isEmpty()) {
                            return format.replace(".", "");
                        }
                    }
                }
            } catch (Exception unused) {
            }
        }
        return "000000000000.00".replace(".", "");
    }

    public static String buildImporteDolares(String str, Locale locale) {
        if (str != null) {
            try {
                if (!str.isEmpty()) {
                    Double doubleFromInputUser = new CAmountIU().getDoubleFromInputUser(str, new Locale("es", "AR"));
                    if (doubleFromInputUser != null) {
                        String format = new DecimalFormat(MASK_AMOUNT_USD_OUT, DecimalFormatSymbols.getInstance(Constants.LOCALE_DEFAULT_WS)).format(doubleFromInputUser);
                        if (format != null && !format.isEmpty()) {
                            return format.replace(".", "");
                        }
                    }
                }
            } catch (Exception unused) {
            }
        }
        return MASK_AMOUNT_USD_OUT.replace(".", "");
    }
}
