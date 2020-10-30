package ar.com.santander.rio.mbanking.app.commons.accessibilty;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.commons.CStringNumberParse;
import ar.com.santander.rio.mbanking.app.ui.Constants;
import ar.com.santander.rio.mbanking.app.ui.constants.TarjetasConstants;
import ar.com.santander.rio.mbanking.app.ui.constants.TransferenciasConstants.TipoCuentaDestino;
import ar.com.santander.rio.mbanking.utils.UtilDate;
import ar.com.santander.rio.mbanking.utils.UtilString;
import ar.com.santander.rio.mbanking.utils.UtilsCuentas;
import com.crashlytics.android.Crashlytics;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CFiltersAccessibility {
    public static String BARRA_STRING = "/";
    public static String CAJA_AHORRO_STRING = "CA";
    public static String CUENTA_CORRIENTE_STRING = "CC";
    public static String CUENTA_STRING = "CTA";
    public static String CUENTA_UNICA_STRING = "CU";
    public static String DOLARES_STRING = "U\\$S";
    public static String GUION_STRING = "-";
    public static String HORAS_STRING = "hs";
    public static String MOVIMIENTOS_STRING = "Mov\\.";
    public static String PESOS_STRING = "\\$";
    public static String VENCIMIENTO_STRING = "vto";
    static String a = "un";
    static String b = "uno";
    static String c = "una";
    private static final Map<String, String> d = new HashMap();
    private static final Map<String, String> e = new HashMap();
    public static final Map<String, String> mapAccesibility = new HashMap();
    private Context f;

    static {
        d.put("01", "enero");
        d.put("02", "febrero");
        d.put("03", "marzo");
        d.put("04", "abril");
        d.put("05", "mayo");
        d.put(TarjetasConstants.CODIGO_TARJETA_MASTERCARD, "junio");
        d.put(TarjetasConstants.CODIGO_TARJETA_VISA, "julio");
        d.put("08", "agosto");
        d.put("09", "septiembre");
        d.put("10", "octubre");
        d.put(TipoCuentaDestino.CAJA_AHORRO_PESOS, "noviembre");
        d.put(TipoCuentaDestino.CAJA_AHORRO_DOLARES, "diciembre");
        d.put(TarjetasConstants.DESC_TARJETA_AMEX, TarjetasConstants.MARCA_AMEX);
        d.put(TarjetasConstants.DESC_TARJETA_VISA, "visa");
        d.put("BCRA", "be,ce,erre,a");
        mapAccesibility.put(PESOS_STRING, "Pesos");
        mapAccesibility.put(DOLARES_STRING, "Dólares");
        mapAccesibility.put(VENCIMIENTO_STRING, "Vencimiento");
        mapAccesibility.put(HORAS_STRING, "Horas");
        mapAccesibility.put(CUENTA_UNICA_STRING, "Cuenta Única");
        mapAccesibility.put(CUENTA_STRING, "Cuenta");
        mapAccesibility.put(CUENTA_CORRIENTE_STRING, "Cuenta Corriente");
        mapAccesibility.put(CAJA_AHORRO_STRING, "Caja de Ahorro");
        mapAccesibility.put(MOVIMIENTOS_STRING, "Movimientos");
        mapAccesibility.put(GUION_STRING, " guión ");
        mapAccesibility.put(BARRA_STRING, " barra ");
    }

    public CFiltersAccessibility(Context context) {
        this.f = context;
    }

    public static String filterTasaValue(String str) {
        String replace = str.replace(Constants.SYMBOL_PERCENTAGE, "");
        String[] split = replace.split(",");
        if (split.length <= 1) {
            return replace;
        }
        String str2 = split[0];
        if (!split[1].equalsIgnoreCase("0") && !split[1].equalsIgnoreCase("00")) {
            StringBuilder sb = new StringBuilder();
            sb.append(str2);
            sb.append(" coma ");
            sb.append(split[1]);
            str2 = sb.toString();
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append(str2);
        sb2.append(" %");
        return sb2.toString();
    }

    public String filterSellado(String str) {
        str.replace("IVA y Sellados", "IVA, y, Se,lla,dos");
        return str;
    }

    public String filterRenovCapEInt(String str) {
        return str.replace(a((int) R.string.IN_RENOV_CAP_E_INT), a((int) R.string.OUT_RENOV_CAP_E_INT));
    }

    public String filterHs(String str) {
        return str.replace(a((int) R.string.IN_HS), a((int) R.string.OUT_HS)).replace(a((int) R.string.IN_HS_2), a((int) R.string.OUT_HS)).replace("min", "minutos").replace("min.", "minutos");
    }

    public String filterVto(String str) {
        return str != null ? str.toLowerCase().replace(a((int) R.string.IN_VTO), a((int) R.string.OUT_VTO)) : str;
    }

    public String filterSuc(String str) {
        return str != null ? str.toLowerCase().replace(a((int) R.string.IN_SUC), a((int) R.string.OUT_SUC)) : str;
    }

    public String filterMov(String str) {
        return str.toLowerCase().replace(a((int) R.string.IN_MOV).toLowerCase(), a((int) R.string.OUT_MOV).toLowerCase());
    }

    public String filterUVIs(String str) {
        return str.toLowerCase().replace(a((int) R.string.IN_UVIs).toLowerCase(), a((int) R.string.OUT_UVIs).toLowerCase()).toLowerCase().replace(a((int) R.string.IN_UVI).toLowerCase(), a((int) R.string.OUT_UVI).toLowerCase());
    }

    public String filterCurrency(String str) {
        if (str.contains(a((int) R.string.IN_AMOUNT_CURRENCY_DOLLARS))) {
            return str.replace(a((int) R.string.IN_AMOUNT_CURRENCY_DOLLARS), a((int) R.string.AMOUNT_CURRENCY_DOLLARS, ""));
        }
        if (str.contains(a((int) R.string.IN_AMOUNT_CURRENCY_DOLLARS_MIN))) {
            return str.replace(a((int) R.string.IN_AMOUNT_CURRENCY_DOLLARS_MIN), a((int) R.string.AMOUNT_CURRENCY_DOLLARS, ""));
        }
        if (str.contains(a((int) R.string.IN_AMOUNT_CURRENCY_PESOS))) {
            return str.replace(a((int) R.string.IN_AMOUNT_CURRENCY_PESOS), a((int) R.string.AMOUNT_CURRENCY_PESOS, ""));
        }
        return str.contains("BCO") ? str.replace("BCO", "{} banco") : str;
    }

    public String filterCodigoPostal(String str) {
        return str.contains(a((int) R.string.CODIGO_POSTAL)) ? str.replace(a((int) R.string.CODIGO_POSTAL), a((int) R.string.CODIGO_POSTAL_NAME, "")) : str;
    }

    public String filterCABA(String str) {
        return str.replace(a((int) R.string.ID_CABA), a((int) R.string.ID_CIUDAD_AUTONOMA_DE_BUENOS_AIRES));
    }

    public String filterCBU(String str) {
        if (str.contains(a((int) R.string.IN_CBU))) {
            return str.replace(a((int) R.string.IN_CBU), a((int) R.string.OUT_CBU));
        }
        return str.contains(a((int) R.string.IN_CBU).toLowerCase()) ? str.replace(a((int) R.string.IN_CBU).toLowerCase(), a((int) R.string.OUT_CBU)) : str;
    }

    public String filterOperador(String str) {
        return str != null ? "Operador\n".concat(str) : str;
    }

    public String filterUsuario(String str) {
        return str != null ? "Vos\n".concat(str) : str;
    }

    public String filterCuadroDeTexto(String str) {
        return str != null ? "cuadro de edición \n".concat(str) : str;
    }

    public String filterCBUCVU(String str) {
        return str.toUpperCase().contains(a((int) R.string.IN_CBU_CVU).toUpperCase()) ? str.toUpperCase().replace(a((int) R.string.IN_CBU_CVU), a((int) R.string.OUT_CBU_CVU)) : str;
    }

    public String filterTabNuevoDestinatarioCBUCVU(String str) {
        if (str.contains(a((int) R.string.ID_3813_TRANSFERENCE_BY_CBU_OR_ALIAS))) {
            return str.replace(a((int) R.string.ID_3813_TRANSFERENCE_BY_CBU_OR_ALIAS), a((int) R.string.OUT_CBU_CVU_TAB_NEW_DEST));
        }
        return str.contains(a((int) R.string.ID_3813_TRANSFERENCE_BY_CBU_OR_ALIAS).toLowerCase()) ? str.replace(a((int) R.string.ID_3813_TRANSFERENCE_BY_CBU_OR_ALIAS).toLowerCase(), a((int) R.string.OUT_CBU_CVU_TAB_NEW_DEST)) : str;
    }

    public String filterNumeroGradoConBarra(String str) {
        if (str.contains(a((int) R.string.IN_NUMERO_GRADO))) {
            str = str.replace(a((int) R.string.IN_NUMERO_GRADO), a((int) R.string.OUT_NUMERO_GRADO));
        }
        if (str.contains(a((int) R.string.IN_NUMERO_GRADO2))) {
            str = str.replace(a((int) R.string.IN_NUMERO_GRADO2), a((int) R.string.OUT_NUMERO_GRADO2));
        }
        if (str.contains(a((int) R.string.IN_NUMERO_GRADO_MINUSCULA).toLowerCase())) {
            str = str.replace(a((int) R.string.IN_NUMERO_GRADO_MINUSCULA).toLowerCase(), a((int) R.string.OUT_NUMERO_GRADO_MINUSCULA));
        }
        if (str.contains(a((int) R.string.IN_NUMERO_GRADO_NRO_1).toLowerCase())) {
            str = str.replace(a((int) R.string.IN_NUMERO_GRADO_NRO_1).toLowerCase(), a((int) R.string.OUT_NUMERO_GRADO_NRO_1));
        }
        if (str.contains(a((int) R.string.IN_NUMERO_GRADO_NRO_1))) {
            str = str.replace(a((int) R.string.IN_NUMERO_GRADO_NRO_1), a((int) R.string.OUT_NUMERO_GRADO_NRO_1));
        }
        if (str.contains(a((int) R.string.IN_NUMERO_GRADO_NRO_2).toLowerCase())) {
            str = str.replace(a((int) R.string.IN_NUMERO_GRADO_NRO_2).toLowerCase(), a((int) R.string.OUT_NUMERO_GRADO_NRO_2));
        }
        if (str.contains(a((int) R.string.IN_BARRA))) {
            str = str.replace(a((int) R.string.IN_BARRA), a((int) R.string.OUT_BARRA));
        } else if (str.contains(a((int) R.string.IN_BARRA).toLowerCase())) {
            str = str.replace(a((int) R.string.IN_BARRA).toLowerCase(), a((int) R.string.OUT_BARRA));
        }
        if (str.contains(a((int) R.string.IN_ID))) {
            str = str.replace(a((int) R.string.IN_ID), a((int) R.string.OUT_ID));
        } else if (str.contains(a((int) R.string.IN_ID).toLowerCase())) {
            str = str.replace(a((int) R.string.IN_ID).toLowerCase(), a((int) R.string.OUT_ID));
        }
        if (str.contains(a((int) R.string.IN_TERCER))) {
            str.replace(a((int) R.string.IN_TERCER), a((int) R.string.OUT_TERCER));
        }
        if (str.contains(a((int) R.string.IN_TERCER2))) {
            str.replace(a((int) R.string.IN_TERCER2), a((int) R.string.OUT_TERCER2));
        }
        return str;
    }

    public String filterDateEmpty(String str) {
        return str.replace(a((int) R.string.IN_DATE_EMPTY), a((int) R.string.OUT_DATE_EMPTY));
    }

    public String filterDateFormatShortYear(String str) {
        if (UtilDate.isDate(str, Constants.FORMAT_DATE_APP)) {
            return UtilDate.getDateFormat(str, Constants.FORMAT_DATE_APP, Constants.FORMAT_DATE_APP_2);
        }
        if (UtilDate.isDate(str, Constants.FORMAT_DATE_APP_3)) {
            return UtilDate.getDateFormat(str, Constants.FORMAT_DATE_APP_3, Constants.FORMAT_DATE_APP_2);
        }
        return UtilDate.isDate(str, Constants.FORMAT_DATE_APP_4) ? UtilDate.getDateFormat(str, Constants.FORMAT_DATE_APP_4, Constants.FORMAT_DATE_APP_2) : str;
    }

    public String filterDateTimeText(String str) {
        String[] split = str.split(UtilsCuentas.SEPARAOR2);
        String str2 = "";
        if (split.length != 2) {
            return str2;
        }
        return String.format("%1$s. %2$s", new Object[]{filterDateText(split[0]), filterTimeText(split[1])});
    }

    public String filterSingularNounGenre(String str, String str2) {
        try {
            if (str.equals("1")) {
                str = str2;
            }
            return str;
        } catch (Exception e2) {
            e2.fillInStackTrace();
            return str;
        }
    }

    public String filterDateText(String str) {
        String[] split = str.split("/");
        return split.length == 3 ? "".concat(split[0].replaceFirst("^0*", "")).concat(" de ").concat(a(split[1])).concat(" de ").concat(split[2]) : str;
    }

    public String filterDateInText(String str) {
        Matcher matcher = Pattern.compile("([0-9]{1,2})[- \\/.]([0-9]{1,2})[- \\/.](19|20)\\d\\d").matcher(str);
        if (!matcher.find()) {
            return str;
        }
        String group = matcher.group();
        return str.replace(group, filterDateText(group));
    }

    public String filterTimeText(String str) {
        String[] split = str.split(":");
        String str2 = "";
        if (split.length != 3) {
            return str2;
        }
        for (int i = 0; i < split.length; i++) {
            split[i] = split[i].replaceFirst("^0", "");
        }
        String str3 = "%1$s hora%2$s, %3$s minuto%4$s y %5$s segundo%6$s";
        Object[] objArr = new Object[6];
        objArr[0] = filterSingularNounGenre(split[0], c);
        objArr[1] = !split[0].equals("1") ? "s" : "";
        objArr[2] = filterSingularNounGenre(split[1], a);
        objArr[3] = !split[1].equals("1") ? "s" : "";
        objArr[4] = filterSingularNounGenre(split[2], a);
        objArr[5] = !split[2].equals("1") ? "s" : "";
        return String.format(str3, objArr);
    }

    private String a(String str) {
        return (String) d.get(str);
    }

    public String filterSymbolAccounts(String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        try {
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            linkedHashMap.put("CU", a((int) R.string.IN_ACCOUNT_CU));
            linkedHashMap.put("CTA", a((int) R.string.IN_ACCOUNT_CTA));
            linkedHashMap.put("CC", a((int) R.string.IN_ACCOUNT_CC));
            linkedHashMap.put("CA", a((int) R.string.IN_ACCOUNT_CA));
            for (String str2 : linkedHashMap.keySet()) {
                StringBuilder sb = new StringBuilder();
                sb.append(str2);
                sb.append(".U\\$S.[0-9]{3}-[0-9]{6}\\/[0-9]{1}");
                if (!str.matches(sb.toString())) {
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append(str2);
                    sb2.append(".U\\$S.[X]{3}-[X]{3}[0-9]{3}\\/[0-9]{1}");
                    if (!str.matches(sb2.toString())) {
                        StringBuilder sb3 = new StringBuilder();
                        sb3.append(str2);
                        sb3.append(" U$S");
                        if (!str.contains(sb3.toString())) {
                            StringBuilder sb4 = new StringBuilder();
                            sb4.append(str2);
                            sb4.append("U$S");
                            if (!str.contains(sb4.toString())) {
                                StringBuilder sb5 = new StringBuilder();
                                sb5.append(str2);
                                sb5.append(".\\$.[0-9]{3}-[0-9]{6}\\/[0-9]{1}");
                                if (!str.matches(sb5.toString())) {
                                    StringBuilder sb6 = new StringBuilder();
                                    sb6.append(str2);
                                    sb6.append(".\\$.[X]{3}-[X]{3}[0-9]{3}\\/[0-9]{1}");
                                    if (!str.matches(sb6.toString())) {
                                        StringBuilder sb7 = new StringBuilder();
                                        sb7.append(str2);
                                        sb7.append(" $");
                                        if (!str.contains(sb7.toString())) {
                                            StringBuilder sb8 = new StringBuilder();
                                            sb8.append(str2);
                                            sb8.append("$");
                                            if (!str.contains(sb8.toString())) {
                                                StringBuilder sb9 = new StringBuilder();
                                                sb9.append(str2);
                                                sb9.append(".[0-9]{3}-[0-9]{6}\\/[0-9]{1}");
                                                if (str.matches(sb9.toString())) {
                                                    return str.replace(str2, ((String) linkedHashMap.get(str2)).toString());
                                                }
                                                if (str.equalsIgnoreCase(str2)) {
                                                    return str.replace(str2, ((String) linkedHashMap.get(str2)).toString());
                                                }
                                            }
                                        }
                                    }
                                }
                                StringBuilder sb10 = new StringBuilder();
                                sb10.append(str2);
                                sb10.append(" $");
                                return str.replace(sb10.toString(), a((int) R.string.OUT_ACCOUNT_PESOS, ((String) linkedHashMap.get(str2)).toString()));
                            }
                        }
                    }
                }
                StringBuilder sb11 = new StringBuilder();
                sb11.append(str2);
                sb11.append(" U$S");
                return str.replace(sb11.toString(), a((int) R.string.OUT_ACCOUNT_DOLLARS, ((String) linkedHashMap.get(str2)).toString()));
            }
        } catch (Exception e2) {
            e2.fillInStackTrace();
            Log.e("@dev", "Error en accesibilidad", e2);
        }
        return str;
    }

    public String filterAccountsNumber(String str) {
        String a2 = a(str, "([0-9]{3}-[0-9]{6}\\/[0-9]{1})");
        if (a2 == null) {
            a2 = a(str, "([X]{3}-[X]{3}[0-9]{3}\\/[0-9]{1})");
        }
        if (a2 == null) {
            a2 = a(str, "([0-9]+\\/[0-9]{1})");
        }
        if (a2 != null) {
            String str2 = "";
            String[] split = a2.split("");
            for (int i = 0; i < split.length; i++) {
                if (split[i].length() > 0) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(str2);
                    sb.append(b(split[i]));
                    sb.append(", ");
                    str2 = sb.toString();
                }
            }
            if (str2.length() > 0) {
                return str.replace(a2, str2);
            }
        }
        return str;
    }

    public List<String> getFormatAmount(String str) {
        String str2 = "((?:\\$|U\\$S)[\\s]*[\\(]?[\\+|\\-]?(?:[0-9]{1,3}[,|\\.](?:[0-9]{3}[,|\\.])*[0-9]{3}|[0-9]+)(?:[\\.|,][0-9][0-9])?[\\)]?)";
        ArrayList arrayList = new ArrayList();
        try {
            Matcher matcher = Pattern.compile(str2).matcher(str);
            while (matcher.find()) {
                arrayList.add(matcher.group(0));
            }
        } catch (Exception e2) {
            e2.fillInStackTrace();
            Log.e("@dev", "Error:", e2);
            arrayList.add(str);
        }
        return arrayList;
    }

    public List<String> getFormatAmountAccount(String str) {
        String str2 = "([\\s]*[\\(]?[\\+|\\-]?(?:[0-9]{1,3}[,|\\.](?:[0-9]{3}[,|\\.])*[0-9]{3}|[0-9]+)(?:[\\.|,][0-9][0-9])?[\\)]?)";
        ArrayList arrayList = new ArrayList();
        try {
            Matcher matcher = Pattern.compile(str2).matcher(str);
            while (matcher.find()) {
                arrayList.add(matcher.group(0));
            }
        } catch (Exception e2) {
            Crashlytics.logException(e2);
            Log.e("@dev", "Error:", e2);
            arrayList.add(str);
        }
        return arrayList;
    }

    public String filterAmount(String str) {
        boolean z;
        String str2;
        for (String str3 : getFormatAmount(str)) {
            String str4 = new String(str3);
            if (str3 != null) {
                boolean z2 = true;
                if (str3.contains(a((int) R.string.IN_AMOUNT_CURRENCY_DOLLARS))) {
                    str2 = str3.replace(a((int) R.string.IN_AMOUNT_CURRENCY_DOLLARS), "");
                    z = false;
                } else {
                    str2 = str3.replace(a((int) R.string.IN_AMOUNT_CURRENCY_PESOS), "");
                    z = true;
                }
                if (!str2.contains("(") || !str2.contains(")")) {
                    z2 = false;
                } else {
                    str2 = str2.replace("(", "").replace(")", "");
                }
                try {
                    String a2 = a(Constants.LOCALE_DEFAULT_APP, str2.trim(), z);
                    if (z2) {
                        StringBuilder sb = new StringBuilder();
                        sb.append(a2);
                        sb.append(UtilsCuentas.SEPARAOR2);
                        sb.append(a((int) R.string.AMOUNT_NEGATIVE));
                        a2 = sb.toString();
                    }
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append(" . ");
                    sb2.append(a2);
                    str = str.replace(str4, sb2.toString());
                } catch (NumberFormatException e2) {
                    e2.fillInStackTrace();
                }
            }
        }
        return str;
    }

    public String filterAmountAccount(String str) {
        boolean z;
        String str2;
        for (String str3 : getFormatAmountAccount(str)) {
            String str4 = new String(str3);
            if (str3 != null) {
                boolean z2 = true;
                if (str3.contains(a((int) R.string.IN_AMOUNT_CURRENCY_DOLLARS))) {
                    str2 = str3.replace(a((int) R.string.IN_AMOUNT_CURRENCY_DOLLARS), "");
                    z = false;
                } else {
                    str2 = str3.replace(a((int) R.string.IN_AMOUNT_CURRENCY_PESOS), "");
                    z = true;
                }
                if (!str2.contains("(") || !str2.contains(")")) {
                    z2 = false;
                } else {
                    str2 = str2.replace("(", "").replace(")", "");
                }
                try {
                    String b2 = b(Constants.LOCALE_DEFAULT_APP, str2.trim(), z);
                    if (z2) {
                        StringBuilder sb = new StringBuilder();
                        sb.append(b2);
                        sb.append(UtilsCuentas.SEPARAOR2);
                        sb.append(a((int) R.string.AMOUNT_NEGATIVE));
                        b2 = sb.toString();
                    }
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append(" . ");
                    sb2.append(b2);
                    str = str.replace(str4, sb2.toString());
                } catch (NumberFormatException e2) {
                    Crashlytics.logException(e2);
                }
            }
        }
        return str;
    }

    private String a(Locale locale, String str, boolean z) {
        int i;
        int i2;
        String sb;
        String str2 = "";
        CStringNumberParse cStringNumberParse = new CStringNumberParse(locale);
        if (z) {
            i2 = R.string.AMOUNT_CURRENCY_PESO;
            i = R.string.AMOUNT_CURRENCY_PESOS;
        } else {
            i2 = R.string.AMOUNT_CURRENCY_DOLLAR;
            i = R.string.AMOUNT_CURRENCY_DOLLARS;
        }
        try {
            String integralPart = cStringNumberParse.getIntegralPart(str, false);
            String fractionalPart = cStringNumberParse.getFractionalPart(str);
            if (Long.parseLong(integralPart) == 0 && Long.parseLong(fractionalPart) == 0) {
                return a(i, "0");
            }
            if (Long.parseLong(integralPart) != 1) {
                str2 = a(i, integralPart);
            } else if (Long.parseLong(integralPart) == 1) {
                str2 = a(i2, filterSingularNounGenre(integralPart, a));
            }
            if ("".equals(fractionalPart)) {
                fractionalPart = "0";
            }
            if (Integer.parseInt(fractionalPart) > 1) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append(str2);
                sb2.append(a((int) R.string.AMOUNT_CENT_PL, fractionalPart));
                sb = sb2.toString();
            } else if (Integer.parseInt(fractionalPart) == 1) {
                StringBuilder sb3 = new StringBuilder();
                sb3.append(str2);
                sb3.append(a((int) R.string.AMOUNT_CENT, filterSingularNounGenre(fractionalPart, a)));
                sb = sb3.toString();
            } else {
                str = str2;
                return str;
            }
            str = sb;
            return str;
        } catch (Exception unused) {
        }
    }

    private String b(Locale locale, String str, boolean z) {
        String sb;
        String str2 = "";
        CStringNumberParse cStringNumberParse = new CStringNumberParse(locale);
        try {
            String integralPart = cStringNumberParse.getIntegralPart(str, false);
            String fractionalPart = cStringNumberParse.getFractionalPart(str);
            if (Long.parseLong(integralPart) == 0 && Long.parseLong(fractionalPart) == 0) {
                return "0";
            }
            if (Long.parseLong(integralPart) != 1) {
                str2 = integralPart;
            } else if (Long.parseLong(integralPart) == 1) {
                str2 = filterSingularNounGenre(integralPart, a);
            }
            if ("".equals(fractionalPart)) {
                fractionalPart = "0";
            }
            if (Integer.parseInt(fractionalPart) > 1) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append(str2);
                sb2.append(a((int) R.string.AMOUNT_CENT_PL, fractionalPart));
                sb = sb2.toString();
            } else if (Integer.parseInt(fractionalPart) == 1) {
                StringBuilder sb3 = new StringBuilder();
                sb3.append(str2);
                sb3.append(a((int) R.string.AMOUNT_CENT, filterSingularNounGenre(fractionalPart, a)));
                sb = sb3.toString();
            } else {
                str = str2;
                return str;
            }
            str = sb;
            return str;
        } catch (Exception e2) {
            Crashlytics.logException(e2);
        }
    }

    public String filterTimeFormat(String str) {
        if (str != null) {
            String[] split = str.split(":");
            if (split != null && split.length > 0) {
                String str2 = "";
                if (split.length >= 1) {
                    str2 = this.f.getResources().getQuantityString(R.plurals.hour, Integer.parseInt(split[0]), new Object[]{Integer.valueOf(Integer.parseInt(split[0]))});
                }
                if (split.length >= 2) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(str2);
                    sb.append(UtilsCuentas.SEPARAOR2);
                    sb.append(this.f.getResources().getQuantityString(R.plurals.minute, Integer.parseInt(split[1]), new Object[]{Integer.valueOf(Integer.parseInt(split[1]))}));
                    sb.append(UtilsCuentas.SEPARAOR2);
                    str2 = sb.toString();
                }
                if (split.length >= 3) {
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append(str2);
                    sb2.append(UtilsCuentas.SEPARAOR2);
                    sb2.append(this.f.getResources().getQuantityString(R.plurals.seconds, Integer.parseInt(split[2]), new Object[]{Integer.valueOf(Integer.parseInt(split[2]))}));
                    sb2.append(UtilsCuentas.SEPARAOR2);
                    str2 = sb2.toString();
                }
                return str2;
            }
        }
        return "";
    }

    public String filterTimeAvailability(String str) {
        String str2 = "(de [0-9]{1,2}(hs| hs|))( a [0-9]{1,2}(hs| hs))";
        Matcher matcher = Pattern.compile(str2).matcher(str);
        if (matcher.find()) {
            for (int i = 0; i < matcher.groupCount(); i++) {
                str = str.replaceFirst(str2, filterHs(matcher.group(i)));
            }
        }
        return str;
    }

    public String filterPesos(String str) {
        return str.contains(a((int) R.string.IN_AMOUNT_CURRENCY_PESOS)) ? str.replace(a((int) R.string.IN_AMOUNT_CURRENCY_PESOS), a((int) R.string.SUPER_AHORRO_PESOS)) : str;
    }

    public String filterTelephone(String str) {
        String str2 = "([0-9]{4}-)([0-9]{3}-)([0-9]{4})";
        String str3 = "([0-9]{4}-)([0-9]{4})";
        String str4 = "([0-9]{4}-)([0-9]{3}-)([0-9]{3}-)([0-9]{4})";
        String str5 = "([0-9]{4}-)([0-9]{3})";
        Pattern compile = Pattern.compile(str2);
        Pattern compile2 = Pattern.compile(str3);
        Pattern compile3 = Pattern.compile(str4);
        Pattern compile4 = Pattern.compile(str5);
        Matcher matcher = compile.matcher(str);
        if (matcher.find()) {
            String str6 = str;
            for (int i = 0; i < matcher.groupCount(); i++) {
                str6 = str6.replaceFirst(str2, filterPhoneNumber(matcher.group(i)));
            }
            str = str6;
        }
        Matcher matcher2 = compile2.matcher(str);
        if (matcher2.find()) {
            String str7 = str;
            for (int i2 = 0; i2 < matcher2.groupCount(); i2++) {
                str7 = str7.replaceFirst(str3, filterPhoneNumber(matcher2.group(i2)));
            }
            str = str7;
        }
        Matcher matcher3 = compile3.matcher(str);
        if (matcher3.find()) {
            String str8 = str;
            for (int i3 = 0; i3 < matcher3.groupCount(); i3++) {
                str8 = str8.replaceFirst(str4, filterPhoneNumber(matcher3.group(i3)));
            }
            str = str8;
        }
        Matcher matcher4 = compile4.matcher(str);
        if (matcher4.find()) {
            for (int i4 = 0; i4 < matcher4.groupCount(); i4++) {
                str = str.replaceFirst(str5, filterPhoneNumber(matcher4.group(i4)));
            }
        }
        return str;
    }

    public String filterBetweenTimeFormat(String str) {
        Exception e2;
        String str2;
        if (str != null) {
            try {
                Matcher matcher = Pattern.compile("([0-2]?[0-9]:[0-5][0-9]:?[0-5]?[0-9]?)").matcher(str);
                boolean z = false;
                while (matcher.find()) {
                    str2 = str.replaceAll("hs", "").replaceAll("\\.", "");
                    try {
                        String group = matcher.group();
                        String[] split = group.split(":");
                        int length = split.length;
                        if (length == 1) {
                            Integer num = new Integer(Integer.parseInt(split[0]));
                            StringBuilder sb = new StringBuilder();
                            sb.append(num);
                            sb.append(" horas");
                            str = str2.replace(group, sb.toString());
                        } else if (length == 2) {
                            Integer num2 = new Integer(Integer.parseInt(split[0]));
                            Integer num3 = new Integer(Integer.parseInt(split[1]));
                            if (num3.intValue() > 0) {
                                StringBuilder sb2 = new StringBuilder();
                                sb2.append(num2);
                                sb2.append(" horas i ");
                                sb2.append(num3);
                                sb2.append(" minutos");
                                str = str2.replace(group, sb2.toString());
                            } else {
                                StringBuilder sb3 = new StringBuilder();
                                sb3.append(num2);
                                sb3.append(" horas");
                                str = str2.replace(group, sb3.toString());
                            }
                        } else if (length == 3) {
                            Integer num4 = new Integer(Integer.parseInt(split[0]));
                            Integer num5 = new Integer(Integer.parseInt(split[1]));
                            Integer num6 = new Integer(Integer.parseInt(split[2]));
                            if (num6.intValue() > 0) {
                                StringBuilder sb4 = new StringBuilder();
                                sb4.append(num4);
                                sb4.append(" horas, ");
                                sb4.append(num5);
                                sb4.append(" minutos i ");
                                sb4.append(num6);
                                sb4.append(" segundos");
                                str = str2.replace(group, sb4.toString());
                            } else if (num5.intValue() > 0) {
                                StringBuilder sb5 = new StringBuilder();
                                sb5.append(num4);
                                sb5.append(" horas i ");
                                sb5.append(num5);
                                sb5.append(" minutos");
                                str = str2.replace(group, sb5.toString());
                            } else {
                                StringBuilder sb6 = new StringBuilder();
                                sb6.append(num4);
                                sb6.append(" horas");
                                str = str2.replace(group, sb6.toString());
                            }
                        } else {
                            str = str2;
                        }
                        z = true;
                    } catch (Exception e3) {
                        e2 = e3;
                        e2.fillInStackTrace();
                        str = str2;
                        return str;
                    }
                }
                if (!z) {
                    str = filterHs(str);
                }
                return str;
            } catch (Exception e4) {
                str2 = str;
                e2 = e4;
                e2.fillInStackTrace();
                str = str2;
                return str;
            }
        }
        return str;
    }

    public String filterTasaInteres(String str) {
        if (str == null) {
            str = "";
        }
        if (str.contains(a((int) R.string.IN_TNA))) {
            str = str.replace(a((int) R.string.IN_TNA), a((int) R.string.OUT_TNA));
        } else if (str.contains(a((int) R.string.IN_TEA))) {
            str = str.replace(a((int) R.string.IN_TEA), a((int) R.string.OUT_TEA));
        }
        return str.contains(GUION_STRING) ? str.replace(GUION_STRING, a((int) R.string.GUION)) : str;
    }

    public String filterChacterToCharacter(String str) {
        if (str != null) {
            String[] split = str.split("");
            if (split != null && split.length > 0) {
                String str2 = "";
                for (String b2 : split) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(str2);
                    sb.append(b(b2));
                    sb.append(", ");
                    str2 = sb.toString();
                }
                return str2;
            }
        }
        return str;
    }

    public String filterCharacterToCharacterSpecifict(String str) {
        Matcher b2 = b(str, "( [0-9]{5})");
        Matcher b3 = b(str, "( [0-9]{7}.)");
        if (b2 != null) {
            String str2 = str;
            for (int i = 0; i < b2.groupCount(); i++) {
                String group = b2.group(i);
                str2 = str2.replace(group, filterChacterToCharacter(group));
            }
            str = str2;
        }
        if (b3 != null) {
            for (int i2 = 0; i2 < b3.groupCount(); i2++) {
                String group2 = b3.group(i2);
                str = str.replace(group2, filterChacterToCharacter(group2));
            }
        }
        return str;
    }

    public String filterNumberToNumber(String str) {
        if (str != null) {
            String[] split = str.split("");
            if (split != null && split.length > 0) {
                String str2 = "";
                for (int i = 0; i < split.length; i++) {
                    if (UtilString.isNumber(split[i])) {
                        StringBuilder sb = new StringBuilder();
                        sb.append(str2);
                        sb.append(split[i]);
                        sb.append(", ");
                        str2 = sb.toString();
                    } else {
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append(str2);
                        sb2.append(split[i]);
                        str2 = sb2.toString();
                    }
                }
                return str2;
            }
        }
        return str;
    }

    public String filterPhoneNumber(String str) {
        return filterNumberToNumber(str).replace(",", "").replace("(", "").replace(")", ", ").replace("-", ", ");
    }

    public String filterControlNumber(String str) {
        return str != null ? str.toLowerCase().replace(a((int) R.string.IN_NRO).toLowerCase(), a((int) R.string.OUT_NRO).toLowerCase()) : str;
    }

    public String filterStringComun(String str) {
        return filterCBU(filterCurrency(filterShorcuts(filterUsted(filterIPC(filterSuc(filterVto(filterHs(filterUVIs(filterMov(filterScapeCharacter(d(filterCon(str)))))))))))));
    }

    public String filterScapeCharacter(String str) {
        if (str == null) {
            return str;
        }
        String str2 = "";
        int i = 0;
        while (i < str.length()) {
            int i2 = i + 1;
            if (i2 <= str.length()) {
                StringBuilder sb = new StringBuilder();
                sb.append(str2);
                sb.append(b(str.substring(i, i2)));
                str2 = sb.toString();
            }
            i = i2;
        }
        return str2;
    }

    private String b(String str) {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("-", a((int) R.string.UNICODE_HYPHEN_MINUS));
        linkedHashMap.put("/", a((int) R.string.UNICODE_SOLIDUS));
        for (String str2 : linkedHashMap.keySet()) {
            if (str2.equals(str)) {
                str = str.replaceAll(str2, (String) linkedHashMap.get(str2));
            }
        }
        return str;
    }

    public String guionFilter(String str) {
        return str.replace("-", a((int) R.string.UNICODE_HYPHEN_MINUS));
    }

    public String bcoFilter(String str) {
        return str.replace("BCO.", a((int) R.string.CONTENT_DESCRIPTION_BCO_BANCO));
    }

    public String ctaFilter(String str) {
        return str.replace("CTA", a((int) R.string.IN_ACCOUNT_CTA));
    }

    public String xboxConsole(String str) {
        return str.replace("XBOX", "EX BOXT");
    }

    public String emailFilter(String str) {
        return str.replace(a((int) R.string.IN_EMAIL), a((int) R.string.OUT_EMAIL));
    }

    public String administracionFilter(String str) {
        return str.replace(a((int) R.string.SUSCRIPTION_ADMINISTRACION), "Admi nistra cion");
    }

    public String LiteralConceptoTransfFilter(String str) {
        if (str.contains(a((int) R.string.IN_SUSCRIP_OBLIG_NEG))) {
            str = str.replace(a((int) R.string.IN_SUSCRIP_OBLIG_NEG), a((int) R.string.OUT_SUSCRIP_OBLIG_NEG));
        }
        if (str.contains(a((int) R.string.IN_BIENES_REG_NO_HAB))) {
            str = str.replace(a((int) R.string.IN_BIENES_REG_NO_HAB), a((int) R.string.OUT_BIENES_REG_NO_HAB));
        }
        if (str.contains(a((int) R.string.IN_BIENES_REG_HAB))) {
            str = str.replace(a((int) R.string.IN_BIENES_REG_HAB), a((int) R.string.OUT_BIENES_REG_HAB));
        }
        return str.contains(a((int) R.string.IN_INMOBILIARIAS_HAB)) ? str.replace(a((int) R.string.IN_INMOBILIARIAS_HAB), a((int) R.string.OUT_INMOBILIARIAS_HAB)) : str;
    }

    public String CantidadDeCuotasFilter(String str) {
        return str.replace("-", a((int) R.string.A));
    }

    public String filterDistance(String str) {
        if (str != null) {
            if (str.contains(a((int) R.string.IN_KM))) {
                return str.replace(a((int) R.string.IN_KM), a((int) R.string.OUT_KM));
            }
            if (str.contains(a((int) R.string.IN_MTS))) {
                return str.replace(a((int) R.string.IN_MTS), a((int) R.string.OUT_MTS));
            }
        }
        return "";
    }

    public String filterDynamicAmount(String str) {
        Matcher matcher = Pattern.compile("[-+]?[0-9]*\\.?[0-9]+").matcher(str);
        while (matcher.find()) {
            str = str.replace(matcher.group(), c(matcher.group()));
        }
        return str;
    }

    public String filterMnemonicos(String str) {
        for (Entry entry : e.entrySet()) {
            str = str.replaceAll((String) entry.getKey(), (String) entry.getValue());
        }
        return str;
    }

    public String scapeOneCharacter(String str, String str2) {
        if (str != null) {
            String[] split = str.split("");
            if (split != null && split.length > 0) {
                String str3 = "";
                for (int i = 0; i < split.length; i++) {
                    if (str2.toLowerCase().equals(split[i].toLowerCase())) {
                        StringBuilder sb = new StringBuilder();
                        sb.append(str3);
                        sb.append(split[i]);
                        sb.append(", ");
                        str3 = sb.toString();
                    } else {
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append(str3);
                        sb2.append(split[i]);
                        str3 = sb2.toString();
                    }
                }
                return str3;
            }
        }
        return str;
    }

    private String c(String str) {
        String str2;
        NumberFormatException e2;
        String str3 = "";
        try {
            Double valueOf = Double.valueOf(str);
            String d2 = Double.toString(valueOf.doubleValue());
            String substring = d2.substring(d2.indexOf(46) + 1);
            str2 = str3.concat(String.valueOf(valueOf.intValue())).concat(UtilsCuentas.SEPARAOR2);
            if (substring == null) {
                return str2;
            }
            try {
                if ("0".equalsIgnoreCase(substring) || "00".equalsIgnoreCase(substring)) {
                    return str2;
                }
                if (substring.length() == 1) {
                    substring = substring.concat("0");
                }
                return str2.concat(" con ").concat(substring).concat(UtilsCuentas.SEPARAOR2).concat(this.f.getString(R.string.AMOUNT_CENT));
            } catch (NumberFormatException e3) {
                e2 = e3;
                e2.fillInStackTrace();
                return str2;
            }
        } catch (NumberFormatException e4) {
            String str4 = str3;
            e2 = e4;
            str2 = str4;
            e2.fillInStackTrace();
            return str2;
        }
    }

    private String a(String str, String str2) {
        Matcher matcher = Pattern.compile(str2).matcher(str);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

    private Matcher b(String str, String str2) {
        Matcher matcher = Pattern.compile(str2).matcher(str);
        if (matcher.find()) {
            return matcher;
        }
        return null;
    }

    private String a(int i) {
        return this.f.getString(i);
    }

    private String a(int i, String str) {
        return this.f.getString(i, new Object[]{str});
    }

    public String filterTalbackUnifications(String str) {
        return str.replaceAll(" y ", ". i ").replaceAll(",", ".");
    }

    public String filterMaskAccount(String str) {
        String replaceAll = str.replaceAll(DOLARES_STRING, (String) mapAccesibility.get(DOLARES_STRING)).replaceAll(CUENTA_UNICA_STRING, (String) mapAccesibility.get(CUENTA_UNICA_STRING)).replaceAll(CUENTA_STRING, (String) mapAccesibility.get(CUENTA_STRING)).replaceAll(CUENTA_CORRIENTE_STRING, (String) mapAccesibility.get(CUENTA_CORRIENTE_STRING)).replaceAll(CAJA_AHORRO_STRING, (String) mapAccesibility.get(CAJA_AHORRO_STRING));
        String[] split = replaceAll.split("-");
        return split.length > 1 ? split[0].concat(" guión ").concat(filterChacterToCharacter(split[1])) : replaceAll;
    }

    public String filterCon(String str) {
        return str != null ? str.toLowerCase().replace(a((int) R.string.IN_CON), a((int) R.string.OUT_CON)) : str;
    }

    private String d(String str) {
        return str != null ? str.toLowerCase().replace(a((int) R.string.IN_SIN), a((int) R.string.OUT_SIN)) : str;
    }

    public String filterCTFNA(String str) {
        if (str != null) {
            if (str.contains("CFTEA s/imp")) {
                str = str.replace("CFTEA s/imp", "C, F, T, E, A, SIN IMPUESTO");
            }
            if (str.contains("CFTEA s/Imp")) {
                str = str.replace("CFTEA s/Imp", "C, F, T, E, A, SIN IMPUESTO");
            }
            if (str.contains("CFTEA S/imp")) {
                str = str.replace("CFTEA S/imp", "C, F, T, E, A, SIN IMPUESTO");
            }
            if (str.contains("CFTEA S/Imp")) {
                str = str.replace("CFTEA S/Imp", "C, F, T, E, A, SIN IMPUESTO");
            }
            if (str.contains("CFTEA c/imp")) {
                str = str.replace("CFTEA c/imp", "C, F, T, E, A, CON IMPUESTO");
            }
            if (str.contains("CFTEA c/Imp")) {
                str = str.replace("CFTEA c/Imp", "C, F, T, E, A, CON IMPUESTO");
            }
            if (str.contains("CFTEA C/imp")) {
                str = str.replace("CFTEA C/imp", "C, F, T, E, A, CON IMPUESTO");
            }
            if (str.contains("CFTEA C/Imp")) {
                str = str.replace("CFTEA C/Imp", "C, F, T, E, A, CON IMPUESTO");
            }
        }
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(",  ");
        return sb.toString();
    }

    public String filterCTFEA_IMP(String str, String str2) {
        str.replace("CFTNA /IVA", "C, F, T, N, A CON IVA");
        String replace = str.replace("CFTEA s/imp", "C, F, T, E, A SIN IMPUESTO");
        StringBuilder sb = new StringBuilder();
        sb.append(replace);
        sb.append(str2);
        return sb.toString();
    }

    public String filterCTFNA(String str, String str2) {
        if (str != null) {
            if (str.contains("CFTEA s/imp")) {
                str = str.replace("CFTEA s/imp", "C, F, T, E, A, SIN IMPUESTO");
            }
            if (str.contains("CFTEA s/Imp")) {
                str = str.replace("CFTEA s/Imp", "C, F, T, E, A, SIN IMPUESTO");
            }
            if (str.contains("CFTEA S/imp")) {
                str = str.replace("CFTEA S/imp", "C, F, T, E, A, SIN IMPUESTO");
            }
            if (str.contains("CFTEA S/Imp")) {
                str = str.replace("CFTEA S/Imp", "C, F, T, E, A, SIN IMPUESTO");
            }
            if (str.contains("CFTEA c/imp")) {
                str = str.replace("CFTEA c/imp", "C, F, T, E, A, CON IMPUESTO");
            }
            if (str.contains("CFTEA c/Imp")) {
                str = str.replace("CFTEA c/Imp", "C, F, T, E, A, CON IMPUESTO");
            }
            if (str.contains("CFTEA C/imp")) {
                str = str.replace("CFTEA C/imp", "C, F, T, E, A, CON IMPUESTO");
            }
            if (str.contains("CFTEA C/Imp")) {
                str = str.replace("CFTEA C/Imp", "C, F, T, E, A, CON IMPUESTO");
            }
        }
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(",  ");
        sb.append(str2);
        return sb.toString();
    }

    public String filterCUIT(String str) {
        return str.replace("CUIT", "Cuit");
    }

    public String filterGuion(String str) {
        return str.replace(" - ", ",");
    }

    public String filterUsted(String str) {
        return str.replace(" Ud", " usted").replace(" Ud.", " usted").replace(" ud.", " usted").replace(" ud", " usted");
    }

    public String filterShorcuts(String str) {
        if (str == null) {
            return str;
        }
        if (str.contains("/")) {
            str = str.replace("/", "barra");
        }
        if (str.contains("herram")) {
            str = str.replace("herram", "herramientas");
        }
        if (str.contains("herram ")) {
            str = str.replace("herram ", "herramientas");
        }
        if (str.contains("cancelac ")) {
            str = str.replace("cancelac ", "cancelación ");
        }
        if (str.contains("equip")) {
            str = str.replace("equip", "equipos ");
        }
        return str.contains("art hog") ? str.replace("art hog", "artículos para el hogar") : str;
    }

    public String filterIPC(String str) {
        return str.replace("IPC", "I,P,C");
    }

    public String filter_OY(String str) {
        return str.replace("y/o", "y, o");
    }

    public String filterGuionDescription(String str) {
        return str.replace(GUION_STRING, a((int) R.string.GUION));
    }

    public String filterImport(String str, String str2) {
        String filterAmount = filterAmount(str2);
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(filterAmount);
        return sb.toString();
    }

    public String filter_acronym_Tasas(String str, String str2) {
        String replace = str.replace("TEA", "T, E, A").replace("TNA", "T, N, A");
        StringBuilder sb = new StringBuilder();
        sb.append(replace);
        sb.append(filterTasaValue(str2));
        return sb.toString();
    }

    public String filter_acronym_Tasas(String str) {
        return str.replace("TEA", "T, E, A").replace("TNA", "T, N, A");
    }

    public String filter_Tel(String str) {
        str.replace("Tel", "Telefono");
        return str.replace("TEL", "Telefono");
    }

    public String filter_BsAs(String str) {
        String replace = str.replace("BS AS", "Buenos Aires");
        if (replace.equals(str)) {
            replace = str.replace("BS. AS.", "Buenos Aires");
        }
        if (replace.equals(str)) {
            replace = str.replace("bs as", "Buenos Aires");
        }
        if (replace.equals(str)) {
            replace = str.replace("bs. as.", "Buenos Aires");
        }
        if (replace.equals(str)) {
            replace = str.replace("Bs As", "Buenos Aires");
        }
        return replace.equals(str) ? str.replace("Bs. As.", "Buenos Aires") : replace;
    }

    public String filter_Ciudad(String str) {
        String replace = str.replace("Cdad", "Ciudad");
        return replace.equals(str) ? str.replace("cdad", "Ciudad") : replace;
    }

    public String filter_Email(String str) {
        return str.replace("e-mail", "imail");
    }

    public String filter_IGJ(String str) {
        return str.replace("IGJ", "I, G, J");
    }

    public String filter_paginas(String str) {
        return str.replace("www", " doble ve doble ve doble ve").replace(".ar", " punto Ar").replace("RGAA", " R G A A ");
    }

    public String filter_pisos(String str) {
        String str2 = "([0-9]{1,2})(°( )?[pP]iso)";
        Matcher matcher = Pattern.compile(str2).matcher(str);
        if (!matcher.find()) {
            return str;
        }
        String replace = filterPhoneNumber(matcher.group(1)).replace(UtilsCuentas.SEPARAOR2, "");
        if (replace.equals("1")) {
            return str.replaceFirst(str2, "primer piso");
        }
        if (replace.equals("2")) {
            return str.replaceFirst(str2, "segundo piso");
        }
        if (replace.equals("3")) {
            return str.replaceFirst(str2, "tercer piso");
        }
        if (replace.equals("4")) {
            return str.replaceFirst(str2, "cuarto piso");
        }
        if (replace.equals("5")) {
            return str.replaceFirst(str2, "quinto piso");
        }
        if (replace.equals("6")) {
            return str.replaceFirst(str2, "sexto piso");
        }
        if (replace.equals("7")) {
            return str.replaceFirst(str2, "septimo piso");
        }
        if (replace.equals("8")) {
            return str.replaceFirst(str2, "octavo piso");
        }
        if (replace.equals("9")) {
            return str.replaceFirst(str2, "noveno piso");
        }
        return replace.equals("10") ? str.replaceFirst(str2, "decimo piso") : str;
    }

    public String filterDireccion(String str) {
        String replace = !str.replace("av.", "Avenida").equals(str) ? str.replace("Av.", "Avenida") : str;
        if (!str.replace("av", "Avenida").equals(str)) {
            replace = str.replace("Av", "Avenida");
        }
        return filterGuion(replace);
    }

    public String filterCTFNA_IVA(String str) {
        String replace = !str.replace("CFTEA /imp", "C, F, T, E, A, CON IMPUESTO").equals(str) ? str.replace("CFTEA /imp", "C, F, T, E, A, CON IMPUESTO") : str;
        String replace2 = str.replace("CFTEA s/imp", "C, F, T, E, A, SIN IMPUESTO");
        if (!replace2.equals(str)) {
            replace = replace2;
        }
        String replace3 = str.replace("CFTEA s/imp", "C, F, T, E, A, SIN IMPUESTO");
        if (!replace3.equals(str)) {
            replace = replace3;
        }
        String replace4 = str.replace("CFTEA S/imp", "C, F, T, E, A, SIN IMPUESTO");
        if (!replace4.equals(str)) {
            replace = replace4;
        }
        String replace5 = str.replace("C, F, T, E, A S/imp", "C, F, T, E, A, SIN IMPUESTO");
        if (!replace5.equals(str)) {
            replace = replace5;
        }
        String replace6 = str.replace("C, F, T, E, A /imp", "C, F, T, E, A, CON IMPUESTO");
        if (!replace6.equals(str)) {
            replace = replace6;
        }
        String replace7 = str.replace("CFTEA c/imp", "C, F, T, E, A, CON IMPUESTO");
        if (!replace7.equals(str)) {
            replace = replace7;
        }
        String replace8 = str.replace("CFTEA C/imp", "C, F, T, E, A, CON IMPUESTO");
        return !replace8.equals(str) ? replace8 : replace;
    }

    public String filterCTFNACSImpuesto(String str) {
        if (str == null) {
            return str;
        }
        if (str.contains("CFTEA s/imp")) {
            str = str.replace("CFTEA s/imp", "C, F, T, E, A, SIN IMPUESTO");
        }
        if (str.contains("CFTEA s/Imp")) {
            str = str.replace("CFTEA s/Imp", "C, F, T, E, A, SIN IMPUESTO");
        }
        if (str.contains("CFTEA S/imp")) {
            str = str.replace("CFTEA S/imp", "C, F, T, E, A, SIN IMPUESTO");
        }
        if (str.contains("CFTEA S/Imp")) {
            str = str.replace("CFTEA S/Imp", "C, F, T, E, A, SIN IMPUESTO");
        }
        if (str.contains("CFTEA c/imp")) {
            str = str.replace("CFTEA c/imp", "C, F, T, E, A, CON IMPUESTO");
        }
        if (str.contains("CFTEA c/Imp")) {
            str = str.replace("CFTEA c/Imp", "C, F, T, E, A, CON IMPUESTO");
        }
        if (str.contains("CFTEA C/imp")) {
            str = str.replace("CFTEA C/imp", "C, F, T, E, A, CON IMPUESTO");
        }
        return str.contains("CFTEA C/Imp") ? str.replace("CFTEA C/Imp", "C, F, T, E, A, CON IMPUESTO") : str;
    }

    public String filterSSNNumero(String str) {
        if (str.contains(this.f.getString(R.string.SSN_NUMERO_IN_1))) {
            return str.replace(this.f.getString(R.string.SSN_NUMERO_IN_1), this.f.getString(R.string.SSN_NUMERO_OUT_1));
        }
        return str.contains(this.f.getString(R.string.SSN_NUMERO_IN_2)) ? str.replace(this.f.getString(R.string.SSN_NUMERO_IN_2), this.f.getString(R.string.SSN_NUMERO_OUT_2)) : str;
    }
}
