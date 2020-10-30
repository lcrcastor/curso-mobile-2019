package ar.com.santander.rio.mbanking.app.commons;

import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import ar.com.santander.rio.mbanking.utils.UtilAccount;
import ar.com.santander.rio.mbanking.utils.UtilAmount;
import ar.com.santander.rio.mbanking.utils.UtilsCuentas;

public class CNuevoPago {
    public static Boolean isAmountEditable(String str) {
        return Boolean.valueOf(Integer.parseInt(str) != 0);
    }

    public static Boolean isAmountEditable(String str, String str2) {
        boolean z = true;
        if (UtilAmount.getAmount(str2) <= 0.0d) {
            return Boolean.valueOf(true);
        }
        if (Integer.parseInt(str) == 0) {
            z = false;
        }
        return Boolean.valueOf(z);
    }

    public static Boolean isAmountNonEditable(String str) {
        return Boolean.valueOf(Integer.parseInt(str) == 0);
    }

    public static String formatMedioPago(SessionManager sessionManager, String str, String str2, String str3, String str4) {
        String abreviatureAndAccountFormat = UtilAccount.getAbreviatureAndAccountFormat(CConsDescripciones.getConsDescripcionTPOCTACORTA(sessionManager), str, str2, str3);
        if (!str4.contains(abreviatureAndAccountFormat)) {
            return str4;
        }
        String trim = str4.substring(str4.indexOf(abreviatureAndAccountFormat) + abreviatureAndAccountFormat.length()).trim();
        StringBuilder sb = new StringBuilder();
        sb.append(abreviatureAndAccountFormat.replace(UtilsCuentas.SEPARAOR2, " "));
        sb.append(UtilsCuentas.SEPARAOR2);
        sb.append(trim.replace(UtilsCuentas.SEPARAOR2, " "));
        return sb.toString();
    }
}
