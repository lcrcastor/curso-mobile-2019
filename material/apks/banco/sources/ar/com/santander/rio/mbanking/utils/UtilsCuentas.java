package ar.com.santander.rio.mbanking.utils;

public class UtilsCuentas {
    public static final String SEPARAOR2 = " ";
    public static final String SEPARATOR1 = "-";
    public static final String SEPARATOR3 = "/";

    public static String formatNumeroCuenta(String str) {
        return str.substring(str.length() - 7, str.length()).replaceFirst("^0+(?!$)", "");
    }

    public static String formatSucursalCuenta(String str) {
        return str.length() > 3 ? str.substring(str.length() - 3, str.length()) : str;
    }

    public static String formatNumeroCuenta(String str, String str2, String str3) {
        return str.concat(SEPARAOR2).concat(str2).concat("-").concat(str3.substring(0, str3.length() - 1).concat("/")).concat(str3.substring(str3.length() - 1));
    }
}
