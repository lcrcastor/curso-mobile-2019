package ar.com.santander.rio.mbanking.utils;

import ar.com.santander.rio.mbanking.app.ui.Constants;

public class UtilTasaNominal {
    public static String getTasaFormatted(String str) {
        String str2 = "";
        if (str != null) {
            try {
                String[] split = str.split("\\.");
                if (split != null) {
                    if (split.length > 0) {
                        str2 = Integer.toString(Integer.parseInt(split[0]));
                    }
                    if (split.length > 1) {
                        StringBuilder sb = new StringBuilder();
                        sb.append(str2);
                        sb.append(",");
                        sb.append(split[1].substring(0, 2));
                        str2 = sb.toString();
                    }
                }
            } catch (Exception unused) {
            }
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append(str2);
        sb2.append(Constants.SYMBOL_PERCENTAGE);
        return sb2.toString();
    }
}
