package ar.com.santander.rio.mbanking.app.ui.utils;

import android.text.Html;
import android.text.TextUtils;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import ar.com.santander.rio.mbanking.services.model.general.Cuenta;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CuentaFondosBean;
import java.util.ArrayList;
import java.util.Iterator;

public class CuentasUtils {
    public static Cuenta getAssociatedAccount(SessionManager sessionManager, int i) {
        ArrayList arrayList = new ArrayList();
        if (!(sessionManager.getLoginUnico().getProductos().getCuentasBP() == null || sessionManager.getLoginUnico().getProductos().getCuentasBP().getCuentasBP() == null)) {
            arrayList.addAll(sessionManager.getLoginUnico().getProductos().getCuentasBP().getCuentasBP());
        }
        if (!(sessionManager.getLoginUnico().getProductos().getCuentas() == null || sessionManager.getLoginUnico().getProductos().getCuentas().getCuentas() == null)) {
            arrayList.addAll(sessionManager.getLoginUnico().getProductos().getCuentas().getCuentas());
        }
        if (!(sessionManager.getLoginUnico().getProductos().getCuentasTitulo() == null || sessionManager.getLoginUnico().getProductos().getCuentasTitulo().getCuentasTitulo() == null)) {
            arrayList.addAll(sessionManager.getLoginUnico().getProductos().getCuentasTitulo().getCuentasTitulo());
        }
        for (int i2 = 0; i2 < arrayList.size(); i2++) {
            if (((Cuenta) arrayList.get(i2)).getNroCuentaInt() == i) {
                return (Cuenta) arrayList.get(i2);
            }
        }
        return new Cuenta();
    }

    public static ArrayList<Cuenta> getAllCuentas(SessionManager sessionManager) {
        ArrayList<Cuenta> arrayList = new ArrayList<>();
        if (!(sessionManager.getLoginUnico().getProductos().getCuentasBP() == null || sessionManager.getLoginUnico().getProductos().getCuentasBP().getCuentasBP() == null)) {
            arrayList.addAll(sessionManager.getLoginUnico().getProductos().getCuentasBP().getCuentasBP());
        }
        if (!(sessionManager.getLoginUnico().getProductos().getCuentas() == null || sessionManager.getLoginUnico().getProductos().getCuentas().getCuentas() == null)) {
            arrayList.addAll(sessionManager.getLoginUnico().getProductos().getCuentas().getCuentas());
        }
        if (!(sessionManager.getLoginUnico().getProductos().getCuentasTitulo() == null || sessionManager.getLoginUnico().getProductos().getCuentasTitulo().getCuentasTitulo() == null)) {
            arrayList.addAll(sessionManager.getLoginUnico().getProductos().getCuentasTitulo().getCuentasTitulo());
        }
        return arrayList;
    }

    public static ArrayList<Cuenta> getAllCuentasBPTRL(SessionManager sessionManager) {
        ArrayList arrayList = new ArrayList();
        ArrayList<Cuenta> arrayList2 = new ArrayList<>();
        if (!(sessionManager.getLoginUnico().getProductos().getCuentasBP() == null || sessionManager.getLoginUnico().getProductos().getCuentasBP().getCuentasBP() == null)) {
            arrayList2.addAll(sessionManager.getLoginUnico().getProductos().getCuentasBP().getCuentasBP());
        }
        if (!(sessionManager.getLoginUnico().getProductos().getCuentas() == null || sessionManager.getLoginUnico().getProductos().getCuentas().getCuentas() == null)) {
            arrayList.addAll(sessionManager.getLoginUnico().getProductos().getCuentas().getCuentas());
        }
        if (!(sessionManager.getLoginUnico().getProductos().getCuentasTitulo() == null || sessionManager.getLoginUnico().getProductos().getCuentasTitulo().getCuentasTitulo() == null)) {
            arrayList.addAll(sessionManager.getLoginUnico().getProductos().getCuentasTitulo().getCuentasTitulo());
        }
        for (int i = 0; i < arrayList.size(); i++) {
            if (((Cuenta) arrayList.get(i)).getNroSucInt() == 0) {
                arrayList2.add(arrayList.get(i));
            }
        }
        return arrayList2;
    }

    public static ArrayList<Cuenta> getCuentasBP(SessionManager sessionManager) {
        ArrayList<Cuenta> arrayList = new ArrayList<>();
        if (!(sessionManager.getLoginUnico().getProductos().getCuentasBP() == null || sessionManager.getLoginUnico().getProductos().getCuentasBP().getCuentasBP() == null)) {
            arrayList.addAll(sessionManager.getLoginUnico().getProductos().getCuentasBP().getCuentasBP());
        }
        return arrayList;
    }

    public static int getCuentasBPCuentasRTLNumber(ArrayList<Cuenta> arrayList) {
        Iterator it = arrayList.iterator();
        int i = 0;
        while (it.hasNext()) {
            Cuenta cuenta = (Cuenta) it.next();
            if (cuenta.getNroSucInt() == 0 || (cuenta.getNroSucInt() >= 250 && cuenta.getNroSucInt() <= 259)) {
                i++;
            }
        }
        return i;
    }

    public static int getAssoiatedAccountIndex(ArrayList<ar.com.santander.rio.mbanking.services.soap.beans.body.Cuenta> arrayList, int i) {
        for (int i2 = 0; i2 < arrayList.size(); i2++) {
            if (((ar.com.santander.rio.mbanking.services.soap.beans.body.Cuenta) arrayList.get(i2)).getCuenta() == i) {
                return i2;
            }
        }
        return 0;
    }

    public static int getFondosAccountIndex(ArrayList<CuentaFondosBean> arrayList, int i) {
        for (int i2 = 0; i2 < arrayList.size(); i2++) {
            if (Integer.valueOf(((CuentaFondosBean) arrayList.get(i2)).getNumero()).intValue() == i) {
                return i2;
            }
        }
        return 0;
    }

    public static int getAssoiatedLoginAccountIndex(ArrayList<Cuenta> arrayList, int i) {
        for (int i2 = 0; i2 < arrayList.size(); i2++) {
            if (((Cuenta) arrayList.get(i2)).getNroCuentaInt() == i) {
                return i2;
            }
        }
        return 0;
    }

    public static int getCuentaFromString(String str) {
        try {
            if (TextUtils.isEmpty(str)) {
                return 0;
            }
            String obj = Html.fromHtml(str).toString();
            if (obj.toLowerCase().contains("privada")) {
                return Integer.parseInt(obj.replaceAll("[^\\d-]", "").split("-")[1]);
            }
            return Integer.parseInt(obj.split("í")[1].replaceAll("[^\\d-]", ""));
        } catch (Exception unused) {
            return 0;
        }
    }

    public static int getSucCuentaFromString(String str) {
        try {
            if (TextUtils.isEmpty(str)) {
                return 0;
            }
            if (str.toLowerCase().contains("privada")) {
                return Integer.parseInt(str.replaceAll("[^\\d-]", "").split("-")[0]);
            }
            return -1;
        } catch (Exception unused) {
            return 0;
        }
    }
}
