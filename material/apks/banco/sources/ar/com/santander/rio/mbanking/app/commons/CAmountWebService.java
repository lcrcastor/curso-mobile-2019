package ar.com.santander.rio.mbanking.app.commons;

import ar.com.santander.rio.mbanking.app.ui.Constants;
import ar.com.santander.rio.mbanking.utils.UtilsCuentas;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class CAmountWebService {
    public static final String MASK_AMOUNT_OUT = "000000000000.00";
    public static final String REGEX_AMOUNT_IN = "^[+|-]?[0-9]{1,12}(\\.[0-9]{1,2})?";

    public static Double getAmountFromWebService(String str) {
        if (str == null) {
            return Double.valueOf(0.0d);
        }
        if (str != null) {
            try {
                String replace = str.trim().replace(UtilsCuentas.SEPARAOR2, "");
                if (replace.matches(REGEX_AMOUNT_IN)) {
                    return Double.valueOf(Double.parseDouble(replace));
                }
            } catch (Exception unused) {
            }
        }
        return Double.valueOf(0.0d);
    }

    public static String getAmountToWebService(Double d, boolean z) {
        if (d != null) {
            String str = "";
            if (z) {
                try {
                    str = d.doubleValue() >= 0.0d ? Constants.SYMBOL_POSITIVE : "";
                } catch (Exception unused) {
                }
            }
            String format = new DecimalFormat("000000000000.00", DecimalFormatSymbols.getInstance(Constants.LOCALE_DEFAULT_WS)).format(d);
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append(format);
            return sb.toString();
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append(d.doubleValue() >= 0.0d ? Constants.SYMBOL_POSITIVE : "");
        sb2.append("000000000000.00");
        return sb2.toString();
    }
}
