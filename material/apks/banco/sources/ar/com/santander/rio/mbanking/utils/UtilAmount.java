package ar.com.santander.rio.mbanking.utils;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

public class UtilAmount {
    public static String COUNTRY_AR = "AR";
    public static String LANGUAGE_ES = "es";

    public static double getAmount(String str) {
        try {
            return NumberFormat.getInstance(new Locale(LANGUAGE_ES, COUNTRY_AR)).parse(str).doubleValue();
        } catch (ParseException unused) {
            return 0.0d;
        }
    }
}
