package ar.com.santander.rio.mbanking.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.commons.CConsDescripciones;
import ar.com.santander.rio.mbanking.app.ui.Constants;
import ar.com.santander.rio.mbanking.app.ui.constants.LoginConstants;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import ar.com.santander.rio.mbanking.services.model.general.Cuenta;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CuentaOperativaBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ListGroupBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ListTableBean;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

public class UtilAccount {
    public static String getAccountFormat(String str, String str2) {
        if (str != null) {
            try {
                if (str.length() < 3) {
                    str = a(str);
                }
            } catch (Exception unused) {
                StringBuilder sb = new StringBuilder();
                sb.append(str);
                sb.append("-");
                sb.append(str2);
                return sb.toString();
            }
        }
        String substring = str.substring(str.length() - 3, str.length());
        if (str2 != null && str2.length() < 7) {
            str2 = b(str2);
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append(str2.substring(str2.length() - 7, str2.length() - 1));
        sb2.append("/");
        sb2.append(str2.substring(str2.length() - 1, str2.length()));
        String sb3 = sb2.toString();
        StringBuilder sb4 = new StringBuilder();
        sb4.append(substring);
        sb4.append("-");
        sb4.append(sb3);
        return sb4.toString();
    }

    public static String getAbreviatureAccount(ListTableBean listTableBean, String str) {
        try {
            for (ListGroupBean listGroupBean : listTableBean.listGroupBeans) {
                if (str.equals(listGroupBean.code)) {
                    return listGroupBean.getLabel();
                }
            }
        } catch (Exception e) {
            Log.e("@dev", "Error al parsear las cuentas para obtener la abreviatura", e);
        }
        return "";
    }

    public static String formatNumeroCuenta(String str) {
        if (str.length() > 7) {
            return str.substring(str.length() - 7, str.length()).replaceFirst("^+(?!$)", "");
        }
        while (str.length() < 7) {
            str = "0".concat(str);
        }
        return str;
    }

    public static String formatNumeroCuentaDebito(String str) {
        return str.length() >= 7 ? str.substring(str.length() - 7, str.length()) : str;
    }

    public static String formatSucursalCuenta(String str) {
        return str.length() > 3 ? str.substring(str.length() - 3, str.length()) : str;
    }

    public static String getAbreviatureAndAccountFormat(ListTableBean listTableBean, String str, String str2, String str3) {
        StringBuilder sb = new StringBuilder();
        sb.append(getAbreviatureAccount(listTableBean, str));
        sb.append(UtilsCuentas.SEPARAOR2);
        sb.append(getAccountFormat(str2, str3));
        return sb.toString();
    }

    public static String getCurrencyOfAccount(SessionManager sessionManager, Cuenta cuenta) {
        if (getAbreviatureAndAccountFormat(CConsDescripciones.getConsDescripcionTPOCTACORTA(sessionManager), cuenta.getTipo(), cuenta.getNroSuc(), cuenta.getNumero()).toUpperCase().contains(Constants.SYMBOL_CURRENCY_DOLAR.toUpperCase())) {
            return Constants.SYMBOL_CURRENCY_DOLAR;
        }
        return Constants.SYMBOL_CURRENCY_PESOS;
    }

    public static String getCurrencyOfAccount(SessionManager sessionManager, CuentaOperativaBean cuentaOperativaBean) {
        if (getAbreviatureAndAccountFormat(CConsDescripciones.getConsDescripcionTPOCTACORTA(sessionManager), cuentaOperativaBean.getTipoCta(), cuentaOperativaBean.getSucursal(), cuentaOperativaBean.getNumero()).toUpperCase().contains(Constants.SYMBOL_CURRENCY_DOLAR.toUpperCase())) {
            return Constants.SYMBOL_CURRENCY_DOLAR;
        }
        return Constants.SYMBOL_CURRENCY_PESOS;
    }

    private static String a(String str) {
        try {
            if (UtilString.isNumericDouble(str)) {
                return new DecimalFormat(Constants.FORMAT_ABREV_SUC_ACCOUNT).format(Double.valueOf(str));
            }
        } catch (Exception unused) {
        }
        return str;
    }

    private static String b(String str) {
        try {
            if (UtilString.isNumericDouble(str)) {
                return new DecimalFormat("0000000").format(Double.valueOf(str));
            }
        } catch (Exception unused) {
        }
        return str;
    }

    public static String formatCuentaTitulo(String str) {
        String str2 = "";
        if (!TextUtils.isEmpty(str)) {
            if (str != null) {
                try {
                    if (str.length() < 8) {
                        str = b(str);
                    }
                } catch (Exception unused) {
                }
            }
            StringBuilder sb = new StringBuilder();
            sb.append(str.substring(str.length() - 8, str.length() - 1));
            sb.append("/");
            sb.append(str.substring(str.length() - 1, str.length()));
            return sb.toString();
        }
        return str2;
    }

    public static String getAccountType(SessionManager sessionManager, String str) {
        try {
            for (ListGroupBean listGroupBean : CConsDescripciones.getConsDescripcionTPOCTACORTA(sessionManager).listGroupBeans) {
                if (str.equalsIgnoreCase(listGroupBean.getLabel().toString())) {
                    return listGroupBean.code;
                }
            }
        } catch (Exception e) {
            Log.e("@dev", "Error al parsear las cuentas para obtener el tipo", e);
        }
        return "";
    }

    public static String getAccountTypeDescription(SessionManager sessionManager, String str, String str2) {
        String str3 = "";
        String str4 = "";
        if (TextUtils.isEmpty(str2)) {
            if (TextUtils.isEmpty(str)) {
                return "";
            }
            String[] split = str.split(UtilsCuentas.SEPARAOR2);
            for (int i = 0; i < split.length - 1; i++) {
                StringBuilder sb = new StringBuilder();
                sb.append(str4);
                sb.append(UtilsCuentas.SEPARAOR2);
                sb.append(split[i]);
                str4 = sb.toString();
            }
            str2 = getAccountType(sessionManager, str4.trim());
        }
        ListTableBean consDescripcionTPOCTALARGA = CConsDescripciones.getConsDescripcionTPOCTALARGA(sessionManager);
        if (consDescripcionTPOCTALARGA != null) {
            Iterator it = consDescripcionTPOCTALARGA.listGroupBeans.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                ListGroupBean listGroupBean = (ListGroupBean) it.next();
                if (listGroupBean.code.equalsIgnoreCase(str2)) {
                    str3 = listGroupBean.getLabel();
                    break;
                }
            }
        }
        return str3;
    }

    public static String getOBAccountTypeDescription(Context context, String str) {
        String str2 = "";
        if (TextUtils.isEmpty(str)) {
            return str2;
        }
        if (str.equalsIgnoreCase(context.getString(R.string.IDXX_OB_ACCOUNT_TYPE_01))) {
            return context.getString(R.string.IDXX_OB_ACCOUNT_TYPE_01_DESCRIPTION);
        }
        if (str.equalsIgnoreCase(context.getString(R.string.IDXX_OB_ACCOUNT_TYPE_02))) {
            return context.getString(R.string.IDXX_OB_ACCOUNT_TYPE_02_DESCRIPTION);
        }
        if (str.equalsIgnoreCase(context.getString(R.string.IDXX_OB_ACCOUNT_TYPE_11))) {
            return context.getString(R.string.IDXX_OB_ACCOUNT_TYPE_11_DESCRIPTION);
        }
        if (str.equalsIgnoreCase(context.getString(R.string.IDXX_OB_ACCOUNT_TYPE_12))) {
            return context.getString(R.string.IDXX_OB_ACCOUNT_TYPE_12_DESCRIPTION);
        }
        if (str.equalsIgnoreCase(context.getString(R.string.IDXX_OB_ACCOUNT_TYPE_22))) {
            return context.getString(R.string.IDXX_OB_ACCOUNT_TYPE_22__DESCRIPTION);
        }
        return str.equalsIgnoreCase(context.getString(R.string.IDXX_OB_ACCOUNT_TYPE_21)) ? context.getString(R.string.IDXX_OB_ACCOUNT_TYPE_21_DESCRIPTION) : str2;
    }

    public static String getAccountNumber(String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        return str.split(UtilsCuentas.SEPARAOR2)[str.split(UtilsCuentas.SEPARAOR2).length - 1];
    }

    public static boolean validarAlias(String str) {
        return str.length() >= 6 && str.length() <= 20 && Pattern.compile("^[a-zA-Z0-9.-]+$").matcher(str).matches();
    }

    public static Boolean isCBUValid(String str) {
        boolean z;
        if (str.length() == 22) {
            char[] charArray = str.toCharArray();
            int numericValue = Character.getNumericValue(charArray[0]);
            int numericValue2 = (((((((numericValue * 7) + Character.getNumericValue(charArray[1])) + (Character.getNumericValue(charArray[2]) * 3)) + (Character.getNumericValue(charArray[3]) * 9)) + (Character.getNumericValue(charArray[4]) * 7)) + Character.getNumericValue(charArray[5])) + (Character.getNumericValue(charArray[6]) * 3)) % 10;
            if (Character.getNumericValue(charArray[7]) == (numericValue2 == 0 ? 0 : 10 - numericValue2)) {
                int numericValue3 = Character.getNumericValue(charArray[8]);
                int numericValue4 = Character.getNumericValue(charArray[9]);
                int numericValue5 = Character.getNumericValue(charArray[10]);
                int numericValue6 = Character.getNumericValue(charArray[11]);
                int numericValue7 = Character.getNumericValue(charArray[12]);
                int numericValue8 = Character.getNumericValue(charArray[13]);
                int numericValue9 = Character.getNumericValue(charArray[14]);
                int numericValue10 = Character.getNumericValue(charArray[15]);
                int numericValue11 = Character.getNumericValue(charArray[16]);
                int numericValue12 = Character.getNumericValue(charArray[17]);
                int numericValue13 = Character.getNumericValue(charArray[18]);
                int numericValue14 = (((((((((((((numericValue3 * 3) + (numericValue4 * 9)) + (numericValue5 * 7)) + numericValue6) + (numericValue7 * 3)) + (numericValue8 * 9)) + (numericValue9 * 7)) + numericValue10) + (numericValue11 * 3)) + (numericValue12 * 9)) + (numericValue13 * 7)) + Character.getNumericValue(charArray[19])) + (Character.getNumericValue(charArray[20]) * 3)) % 10;
                if (Character.getNumericValue(charArray[21]) == (numericValue14 == 0 ? 0 : 10 - numericValue14)) {
                    z = true;
                    return Boolean.valueOf(z);
                }
            }
        }
        z = false;
        return Boolean.valueOf(z);
    }

    public List<Cuenta> getAllAccountsInfo(SessionManager sessionManager) {
        ArrayList arrayList = new ArrayList();
        if (!(sessionManager == null || sessionManager.getLoginUnico().getProductos() == null)) {
            for (Cuenta cuenta : sessionManager.getLoginUnico().getProductos().getCuentas().getCuentas()) {
                if (cuenta.getTipo().equalsIgnoreCase(LoginConstants.TIPO_CUENTA_CU)) {
                    Cuenta cuenta2 = (Cuenta) cuenta.clone();
                    cuenta2.setCuentaEnDolares(false);
                    cuenta2.setTipo("09");
                    cuenta2.setFormattedName(getAbreviatureAndAccountFormat(CConsDescripciones.getConsDescripcionTPOCTACORTA(sessionManager), cuenta2.getTipo(), cuenta2.getNroSuc(), cuenta2.getNumero()));
                    arrayList.add(cuenta2);
                    Cuenta cuenta3 = (Cuenta) cuenta.clone();
                    cuenta3.setCuentaEnDolares(true);
                    cuenta3.setTipo("10");
                    cuenta3.setFormattedName(getAbreviatureAndAccountFormat(CConsDescripciones.getConsDescripcionTPOCTACORTA(sessionManager), cuenta3.getTipo(), cuenta3.getNroSuc(), cuenta3.getNumero()));
                    arrayList.add(cuenta3);
                } else {
                    cuenta.setFormattedName(getAbreviatureAndAccountFormat(CConsDescripciones.getConsDescripcionTPOCTACORTA(sessionManager), cuenta.getTipo(), cuenta.getNroSuc(), cuenta.getNumero()));
                    arrayList.add(cuenta);
                }
            }
        }
        return arrayList;
    }

    public List<Cuenta> getAccountsPesosInfo(SessionManager sessionManager) {
        ArrayList arrayList = new ArrayList();
        for (Cuenta cuenta : getAllAccountsInfo(sessionManager)) {
            if (cuenta.getTipo().equalsIgnoreCase(LoginConstants.TIPO_CUENTA_CU_PESOS)) {
                arrayList.add(cuenta);
            }
        }
        return arrayList;
    }

    public static Cuenta getAssociatedAccount(SessionManager sessionManager, int i) {
        for (int i2 = 0; i2 < sessionManager.getLoginUnico().getProductos().getCuentas().getCuentas().size(); i2++) {
            if (((Cuenta) sessionManager.getLoginUnico().getProductos().getCuentas().getCuentas().get(i2)).getNroCuentaInt() == i) {
                return (Cuenta) sessionManager.getLoginUnico().getProductos().getCuentas().getCuentas().get(i2);
            }
        }
        return new Cuenta();
    }
}
