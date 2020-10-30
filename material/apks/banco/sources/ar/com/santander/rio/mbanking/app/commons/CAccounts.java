package ar.com.santander.rio.mbanking.app.commons;

import android.text.TextUtils;
import ar.com.santander.rio.mbanking.app.ui.Constants;
import ar.com.santander.rio.mbanking.app.ui.constants.LoginConstants;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import ar.com.santander.rio.mbanking.services.model.general.Cuenta;
import ar.com.santander.rio.mbanking.services.model.tenenciaCreditosModel.CuentaOperativa;
import ar.com.santander.rio.mbanking.utils.UtilAccount;
import com.crashlytics.android.Crashlytics;
import java.util.ArrayList;
import java.util.List;

public class CAccounts {
    private SessionManager a;

    private CAccounts(SessionManager sessionManager) {
        this.a = sessionManager;
    }

    public static CAccounts getInstance(SessionManager sessionManager) {
        return new CAccounts(sessionManager);
    }

    public static String getMaskAccount(String str) {
        if (str == null) {
            return "";
        }
        String trim = str.trim();
        return trim.matches(".*[0-9]{3}-[0-9]{6}/[0-9]") ? trim.replaceFirst("[0-9]{3}-[0-9]{3}", "XXX-XXX") : trim;
    }

    public static String getTypeAccount(String str) {
        if (str != null) {
            if ("00".equals(str)) {
                return "CC $";
            }
            if ("01".equals(str)) {
                return "CA $";
            }
            if ("02".equals(str)) {
                return "CTA";
            }
            if ("03".equals(str)) {
                return "CC U$S";
            }
            if ("04".equals(str)) {
                return "CA U$S";
            }
            if ("09".equals(str)) {
                return "CTA $";
            }
            if ("10".equals(str)) {
                return "CTA U$S";
            }
        }
        return "";
    }

    public List<Cuenta> getListAccountsUserInCurrencyFromSession(String str) {
        try {
            if (this.a.usuarioTieneCuentas()) {
                ArrayList arrayList = new ArrayList();
                ArrayList<Cuenta> arrayList2 = new ArrayList<>();
                if (this.a.usuarioPoseeCuentas()) {
                    arrayList2.addAll(this.a.getLoginUnico().getProductos().getCuentas().getCuentas());
                }
                if (this.a.usuarioPoseeCuentasBP()) {
                    arrayList2.addAll(this.a.getLoginUnico().getProductos().getCuentasBP().getCuentasBP());
                }
                for (Cuenta cuenta : arrayList2) {
                    if (isAccountOperational(cuenta).booleanValue()) {
                        if (TextUtils.isEmpty(str)) {
                            arrayList.add(cuenta);
                        } else if (cuenta.getTipo().equalsIgnoreCase(LoginConstants.TIPO_CUENTA_CU) || UtilAccount.getCurrencyOfAccount(this.a, cuenta).equalsIgnoreCase(str)) {
                            if (cuenta.getTipo().equalsIgnoreCase(LoginConstants.TIPO_CUENTA_CU)) {
                                if (UtilAccount.getCurrencyOfAccount(this.a, cuenta).equalsIgnoreCase(Constants.SYMBOL_CURRENCY_PESOS)) {
                                    cuenta.setTipo(LoginConstants.TIPO_CUENTA_CU_PESOS);
                                } else {
                                    cuenta.setTipo(LoginConstants.TIPO_CUENTA_CU_DOLAR);
                                }
                            }
                            arrayList.add(cuenta);
                        }
                    }
                }
                return arrayList;
            }
        } catch (NullPointerException e) {
            Crashlytics.log(6, "@dev", "Error al obtener las cuentas de la sesión");
            e.fillInStackTrace();
        } catch (Exception e2) {
            Crashlytics.log(6, "@dev", "Error desconocido al obtener las cuentas de la sesión");
            e2.fillInStackTrace();
        }
        return null;
    }

    public List<Cuenta> getListAccountsUserFromSession() {
        return getListAccountsUserInCurrencyFromSession(null);
    }

    public List<Cuenta> filterDuplicateAccounts(List<Cuenta> list) {
        boolean z = false;
        boolean z2 = false;
        for (Cuenta cuenta : list) {
            if ("09".equals(cuenta.getTipo())) {
                z = true;
            } else if ("10".equals(cuenta.getTipo())) {
                z2 = true;
            }
        }
        ArrayList arrayList = new ArrayList();
        if (!z || !z2) {
            return list;
        }
        for (Cuenta cuenta2 : list) {
            if (!"10".equals(cuenta2.getTipo())) {
                arrayList.add(cuenta2);
            }
        }
        return arrayList;
    }

    public Cuenta getDefaultAccount(List<Cuenta> list, String str) {
        for (Cuenta cuenta : list) {
            if (str.equals(cuenta.getTipo())) {
                return cuenta;
            }
        }
        if (list == null || list.size() <= 0) {
            return null;
        }
        return (Cuenta) list.get(0);
    }

    public String getAbrevAccount(Cuenta cuenta) {
        return UtilAccount.getAbreviatureAndAccountFormat(CConsDescripciones.getConsDescripcionTPOCTACORTA(this.a), cuenta.getTipo(), cuenta.getNroSuc(), cuenta.getNumero());
    }

    public Cuenta getAccountFromString(List<Cuenta> list, String str) {
        if (str != null) {
            try {
                for (Cuenta cuenta : list) {
                    if (str.equals(getAbrevAccount(cuenta))) {
                        return cuenta;
                    }
                }
            } catch (Exception unused) {
            }
        }
        return null;
    }

    public String getAbrevAccountOperativa(CuentaOperativa cuentaOperativa) {
        return UtilAccount.getAbreviatureAndAccountFormat(CConsDescripciones.getConsDescripcionTPOCTACORTA(this.a), cuentaOperativa.getTipoCta(), cuentaOperativa.getSucursal(), cuentaOperativa.getNumero());
    }

    public CuentaOperativa getAccountOperativaFromString(List<CuentaOperativa> list, String str) {
        if (str != null) {
            try {
                for (CuentaOperativa cuentaOperativa : list) {
                    if (str.equals(getAbrevAccountOperativa(cuentaOperativa))) {
                        return cuentaOperativa;
                    }
                }
            } catch (Exception unused) {
            }
        }
        return null;
    }

    public Boolean isAccountOperational(Cuenta cuenta) {
        return (cuenta.getCodPaquete().equals("2011") || cuenta.getCodPaquete().equals("2016") || cuenta.getCodPaquete().equals("2017") || cuenta.getCodPaquete().equals("2018") || cuenta.getCodPaquete().equals("2019") || cuenta.getCodPaquete().equals("2021")) ? Boolean.valueOf(false) : Boolean.valueOf(true);
    }
}
