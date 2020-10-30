package ar.com.santander.rio.mbanking.app.commons;

import ar.com.santander.rio.mbanking.app.ui.Constants;
import ar.com.santander.rio.mbanking.utils.UtilsCuentas;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;

public class CFormatterAmounts {
    public static Double getAmountFromStringAmount(String str) {
        try {
            String trim = str.replace(Constants.SYMBOL_CURRENCY_DOLAR, "").replace(Constants.SYMBOL_CURRENCY_PESOS, "").trim();
            if (trim.contains(".")) {
                trim = trim.replace(".", "");
            }
            if (trim.contains(",")) {
                trim = trim.replace(",", ".");
            }
            return new CFormatterAmounts().getDoubleFromString(trim);
        } catch (ParseException unused) {
            return null;
        }
    }

    public static String getAmountFromDouble(double d) {
        try {
            return new DecimalFormat("#,##0.00", DecimalFormatSymbols.getInstance(Constants.LOCALE_DEFAULT_APP)).format(d);
        } catch (Exception unused) {
            return new DecimalFormat("#,##0.00", DecimalFormatSymbols.getInstance(Constants.LOCALE_DEFAULT_APP)).format(0);
        }
    }

    public static String getAmountAndCurrencyFromDouble(double d) {
        StringBuilder sb = new StringBuilder();
        sb.append(Constants.SYMBOL_CURRENCY_PESOS);
        sb.append(UtilsCuentas.SEPARAOR2);
        sb.append(getAmountFromDouble(d));
        return sb.toString();
    }

    public Double getDoubleFromString(String str) {
        try {
            return Double.valueOf(Double.parseDouble(str));
        } catch (NumberFormatException unused) {
            return Double.valueOf(a(str));
        } catch (Exception unused2) {
            return null;
        }
    }

    public String getAmountToUser(double d) {
        try {
            return new DecimalFormat("#,##0.00", DecimalFormatSymbols.getInstance(Constants.LOCALE_DEFAULT_APP)).format(d);
        } catch (Exception unused) {
            return "";
        }
    }

    public String getAmountToWebService(double d) {
        return new DecimalFormat("000000000000.00", DecimalFormatSymbols.getInstance(Constants.LOCALE_DEFAULT_WS)).format(d);
    }

    private double a(String str) {
        if (str == null || !str.endsWith(".00")) {
            return NumberFormat.getInstance(Constants.LOCALE_DEFAULT_APP).parse(str).doubleValue();
        }
        return NumberFormat.getInstance(Constants.LOCALE_DEFAULT_WS).parse(str).doubleValue();
    }
}
