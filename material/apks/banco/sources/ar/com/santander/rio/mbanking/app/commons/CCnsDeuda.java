package ar.com.santander.rio.mbanking.app.commons;

import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import ar.com.santander.rio.mbanking.services.model.general.Cuenta;
import ar.com.santander.rio.mbanking.services.model.general.Tarjeta;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ListGroupBean;
import ar.com.santander.rio.mbanking.utils.UtilCurrency;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CCnsDeuda {
    public Map<String, Cuenta> mapCuenta = new HashMap();

    public static String getCodigo(SessionManager sessionManager, int i) {
        return ((Tarjeta) sessionManager.getCnsDeuda().getTarjetas().getTarjetas().get(i)).getCodigo();
    }

    public static String getNombreTarjeta(SessionManager sessionManager, int i) {
        return ((Tarjeta) sessionManager.getCnsDeuda().getTarjetas().getTarjetas().get(i)).getNombreTarjeta();
    }

    public static String getNumeroTarjeta(SessionManager sessionManager, int i) {
        return ((Tarjeta) sessionManager.getCnsDeuda().getTarjetas().getTarjetas().get(i)).getNumTarjeta();
    }

    public static String getNumeroTarjetaFormateado(SessionManager sessionManager, int i) {
        return ((Tarjeta) sessionManager.getCnsDeuda().getTarjetas().getTarjetas().get(i)).getNumTarjetaFormatted();
    }

    public static String getTipoTarjeta(SessionManager sessionManager, int i) {
        return ((Tarjeta) sessionManager.getCnsDeuda().getTarjetas().getTarjetas().get(i)).getTipoTarjeta();
    }

    public static String getExpireDate(SessionManager sessionManager, int i) {
        return ((Tarjeta) sessionManager.getCnsDeuda().getTarjetas().getTarjetas().get(i)).getVencimiento();
    }

    public static String getExpireDateFormatted(SessionManager sessionManager, int i) {
        return ((Tarjeta) sessionManager.getCnsDeuda().getTarjetas().getTarjetas().get(i)).getVencimientoFormatted();
    }

    public static String getCurrentDate(SessionManager sessionManager) {
        return sessionManager.getCnsDeuda().getFechaActual();
    }

    public static String getImporteP(SessionManager sessionManager, int i) {
        return ((Tarjeta) sessionManager.getCnsDeuda().getTarjetas().getTarjetas().get(i)).getImporteP();
    }

    public static String getImportePM(SessionManager sessionManager, int i) {
        return UtilCurrency.getFormattedAmountInArsFromString(((Tarjeta) sessionManager.getCnsDeuda().getTarjetas().getTarjetas().get(i)).getImportePM());
    }

    public static String getImportePFormatted(SessionManager sessionManager, int i) {
        return ((Tarjeta) sessionManager.getCnsDeuda().getTarjetas().getTarjetas().get(i)).getImportePFormatted();
    }

    public static String getImportePConvertido(SessionManager sessionManager, int i) {
        return ((Tarjeta) sessionManager.getCnsDeuda().getTarjetas().getTarjetas().get(i)).getImportePConvertido();
    }

    public static String getImporteTotalPesos(SessionManager sessionManager, int i) {
        return ((Tarjeta) sessionManager.getCnsDeuda().getTarjetas().getTarjetas().get(i)).getImporte_total_pesos();
    }

    public static String getImporteD(SessionManager sessionManager, int i) {
        return ((Tarjeta) sessionManager.getCnsDeuda().getTarjetas().getTarjetas().get(i)).getImporteD();
    }

    public static String getImporteDFormatted(SessionManager sessionManager, int i) {
        return ((Tarjeta) sessionManager.getCnsDeuda().getTarjetas().getTarjetas().get(i)).getImporteDFormatted();
    }

    public static String getImporteDConvertido(SessionManager sessionManager, int i) {
        return ((Tarjeta) sessionManager.getCnsDeuda().getTarjetas().getTarjetas().get(i)).getImporteDConvertido();
    }

    public static String getImporteTotalDolares(SessionManager sessionManager, int i) {
        return ((Tarjeta) sessionManager.getCnsDeuda().getTarjetas().getTarjetas().get(i)).getImporte_total_dolares();
    }

    public static String getQuoteOfTheDay(SessionManager sessionManager) {
        StringBuilder sb = new StringBuilder();
        sb.append(((ListGroupBean) CConsDescripciones.getConsDescripcionMONEDADESCSIMBOLO(sessionManager).getListGroupBeans().get(1)).getLabel());
        sb.append("1 = ");
        return sb.toString();
    }

    public static String getQuoteOfTheDayArs(SessionManager sessionManager) {
        StringBuilder sb = new StringBuilder();
        sb.append(((ListGroupBean) CConsDescripciones.getConsDescripcionMONEDADESCSIMBOLO(sessionManager).getListGroupBeans().get(0)).getLabel());
        sb.append(UtilCurrency.getFormattedAmountInArsFromString(sessionManager.getCnsDeuda().getCotizacionDolar()));
        return sb.toString();
    }

    public static String getQuoteOfTheDayOriginal(SessionManager sessionManager) {
        return UtilCurrency.getFormattedAmountInArsFromString(sessionManager.getCnsDeuda().getCotizacionDolar());
    }

    public static String getFormaPagoTCredito(SessionManager sessionManager, int i) {
        return ((Tarjeta) sessionManager.getCnsDeuda().getTarjetas().getTarjetas().get(i)).getFormaPagoTCredito();
    }

    public static String getAmountPayableTotal(SessionManager sessionManager) {
        return ((ListGroupBean) CConsDescripciones.getConsDescripcionCOMBOFORMAPAGO(sessionManager).getListGroupBeans().get(1)).getLabel();
    }

    public static String getAmountPayableMinimum(SessionManager sessionManager) {
        return ((ListGroupBean) CConsDescripciones.getConsDescripcionCOMBOFORMAPAGO(sessionManager).getListGroupBeans().get(0)).getLabel();
    }

    public static String getAmountPayableOther(SessionManager sessionManager) {
        return ((ListGroupBean) CConsDescripciones.getConsDescripcionCOMBOFORMAPAGO(sessionManager).getListGroupBeans().get(2)).getLabel();
    }

    public static String getFormaMoneda(SessionManager sessionManager) {
        return sessionManager.getCnsDeuda().getCuentas().getFormaMoneda();
    }

    public static String getCurrencyArs(SessionManager sessionManager) {
        return ((ListGroupBean) CConsDescripciones.getConsDescripcionCOMBOTARMONEDA(sessionManager).getListGroupBeans().get(0)).getLabel();
    }

    public static String getCurrencyUsd(SessionManager sessionManager) {
        return ((ListGroupBean) CConsDescripciones.getConsDescripcionCOMBOTARMONEDA(sessionManager).getListGroupBeans().get(1)).getLabel();
    }

    public static String getCurrencyArsUsd(SessionManager sessionManager) {
        return ((ListGroupBean) CConsDescripciones.getConsDescripcionCOMBOTARMONEDA(sessionManager).getListGroupBeans().get(2)).getLabel();
    }

    public static String getPaymentDateToday(SessionManager sessionManager) {
        return ((ListGroupBean) CConsDescripciones.getConsDescripcionCOMBOFECHAPAGO(sessionManager).getListGroupBeans().get(0)).getLabel();
    }

    public static String getPaymentDateVencimiento(SessionManager sessionManager) {
        return ((ListGroupBean) CConsDescripciones.getConsDescripcionCOMBOFECHAPAGO(sessionManager).getListGroupBeans().get(1)).getLabel();
    }

    public static List<Cuenta> getCuentas(SessionManager sessionManager) {
        return sessionManager.getCnsDeuda().getCuentas().getCuentas();
    }

    public static String getTipoCuenta(SessionManager sessionManager, int i) {
        return ((Cuenta) sessionManager.getCnsDeuda().getCuentas().getCuentas().get(i)).getTipo();
    }

    public static String getTipoCuentaDebito(SessionManager sessionManager, int i) {
        return ((Tarjeta) sessionManager.getCnsDeuda().getTarjetas().getTarjetas().get(i)).getTipoCuentaDebito();
    }

    public static String getSucursalCuenta(SessionManager sessionManager, int i) {
        if (((Cuenta) sessionManager.getCnsDeuda().getCuentas().getCuentas().get(i)).getSucursal().length() == 4) {
            return ((Cuenta) sessionManager.getCnsDeuda().getCuentas().getCuentas().get(i)).getSucursal().substring(1);
        }
        return ((Cuenta) sessionManager.getCnsDeuda().getCuentas().getCuentas().get(i)).getSucursal();
    }

    public static String getProgramado(SessionManager sessionManager, int i) {
        return ((Tarjeta) sessionManager.getCnsDeuda().getTarjetas().getTarjetas().get(i)).getProgramado();
    }

    public static String getSucursalCuentaDebito(SessionManager sessionManager, int i) {
        return ((Tarjeta) sessionManager.getCnsDeuda().getTarjetas().getTarjetas().get(i)).getSucursalCuentaDebito();
    }

    public static String getSucursalCuentaT(SessionManager sessionManager, int i) {
        return ((Tarjeta) sessionManager.getLoginUnico().getProductos().getTarjetas().getTarjetas().get(i)).getNroSuc();
    }

    public String getNumeroCuentaFormateado(SessionManager sessionManager, int i) {
        Cuenta cuenta = (Cuenta) sessionManager.getCnsDeuda().getCuentas().getCuentas().get(i);
        StringBuilder sb = new StringBuilder();
        sb.append(cuenta.getNumero().substring(9, 15));
        sb.append("/");
        sb.append(cuenta.getNumero().substring(15));
        return sb.toString();
    }

    public void addMapCuentas(SessionManager sessionManager, String str, int i) {
        this.mapCuenta.put(str, (Cuenta) sessionManager.getCnsDeuda().getCuentas().getCuentas().get(i));
    }

    public static String getNumeroCuentaDebito(SessionManager sessionManager, int i) {
        return ((Tarjeta) sessionManager.getCnsDeuda().getTarjetas().getTarjetas().get(i)).getNroCuentaDebito();
    }

    public static String getNumeroCuentaT(SessionManager sessionManager, int i) {
        return ((Tarjeta) sessionManager.getLoginUnico().getProductos().getTarjetas().getTarjetas().get(i)).getNumero();
    }

    public static String getNumeroCuentaProducto(SessionManager sessionManager, int i) {
        return ((Tarjeta) sessionManager.getCnsDeuda().getTarjetas().getTarjetas().get(i)).getNumCuentaProduc();
    }

    public static String getStopMensajeMes(SessionManager sessionManager) {
        return sessionManager.getCnsDeuda().getStopMensajeMes();
    }

    public static String getStopDebitRecordatorio(SessionManager sessionManager) {
        return sessionManager.getCnsDeuda().getStopRecordatorio();
    }
}
