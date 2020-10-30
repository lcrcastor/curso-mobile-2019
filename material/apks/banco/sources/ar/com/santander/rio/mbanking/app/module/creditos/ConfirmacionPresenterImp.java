package ar.com.santander.rio.mbanking.app.module.creditos;

import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.commons.CAmount;
import ar.com.santander.rio.mbanking.app.commons.CConsDescripciones;
import ar.com.santander.rio.mbanking.app.commons.CCreditos;
import ar.com.santander.rio.mbanking.app.module.creditos.model.Resultado;
import ar.com.santander.rio.mbanking.app.ui.Constants;
import ar.com.santander.rio.mbanking.services.events.SolicitudPrestamoPreacordadoEvent;
import ar.com.santander.rio.mbanking.services.model.creditos.DatosSolicitudPrestamo;
import ar.com.santander.rio.mbanking.services.soap.beans.SolicitudPrestamoPreacordadoResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.AccountRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ConsultaPrestamosPermitidosBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.SolicitudPrestamoPreacordadoBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.SolicitudPrestamoPreacordadoBodyResponseBean;
import ar.com.santander.rio.mbanking.utils.UtilAccount;
import ar.com.santander.rio.mbanking.utils.UtilDate;

public class ConfirmacionPresenterImp implements ConfirmacionPresenter {
    private static final String[] d = {Constants.LEYENDA_TASA_CRED_TNA, Constants.LEYENDA_TASA_CRED_TEA, Constants.LEYENDA_TASA_CRED_CFTEA, Constants.LEYENDA_TASA_CRED_CFTEA_SIMP};
    DatosSolicitudPrestamo a;
    SolicitudPrestamoPreacordadoBodyResponseBean b;
    private CreditosView c;

    public ConfirmacionPresenterImp(CreditosView creditosView) {
        this.c = creditosView;
    }

    public void onCreatePage(DatosSolicitudPrestamo datosSolicitudPrestamo, AccountRequestBean accountRequestBean, ConsultaPrestamosPermitidosBodyResponseBean consultaPrestamosPermitidosBodyResponseBean) {
        this.c.setTitleLayout(this.c.getActContext().getResources().getString(R.string.ID138_CREDITS_LENDCONFIRM_LBL_SUPERLEND));
        this.a = datosSolicitudPrestamo;
        Resultado resultado = new Resultado();
        CAmount cAmount = new CAmount(datosSolicitudPrestamo.getImportSolicitar());
        cAmount.setSymbolCurrencyDollarOrPeso(false);
        resultado.setImporteSolicitud(cAmount.getAmount());
        if (datosSolicitudPrestamo.getImporteSolUVA() != null) {
            resultado.setImporteSolUVA(datosSolicitudPrestamo.getImporteSolUVA());
        }
        if (datosSolicitudPrestamo.getImporteCuotaUVA() != null) {
            resultado.setImporteCuotaUVA(datosSolicitudPrestamo.getImporteCuotaUVA());
        }
        if (datosSolicitudPrestamo.getCuotaPuraUVA() != null) {
            resultado.setCuotaPuraUVA(datosSolicitudPrestamo.getCuotaPuraUVA());
            resultado.setLeyendaCuotaPuraUvas(CCreditos.getLeyendaTasa(consultaPrestamosPermitidosBodyResponseBean.getListaLeyenda(), resultado));
        }
        if (datosSolicitudPrestamo.getCotizacionUVA() != null) {
            resultado.setCotizacionUVA(datosSolicitudPrestamo.getCotizacionUVA());
        }
        if (datosSolicitudPrestamo.getFechaCotizaUVA() != null) {
            resultado.setFechaCotizaUVA(UtilDate.getDateFormat(UtilDate.getDateFormat(datosSolicitudPrestamo.getFechaCotizaUVA()), Constants.FORMAT_DATE_APP, Constants.FORMAT_DATE_APP_2));
        }
        CAmount cAmount2 = new CAmount(datosSolicitudPrestamo.getImporteNetoAcred());
        cAmount2.setSymbolCurrencyDollarOrPeso(false);
        resultado.setImporteNeto(cAmount2.getAmount());
        resultado.setCuentaDestino(UtilAccount.getAbreviatureAndAccountFormat(CConsDescripciones.getConsDescripcionTPOCTACORTA(this.c.getSessionManager()), accountRequestBean.tipo, accountRequestBean.sucursalPaq, accountRequestBean.numero));
        resultado.setCantidadCuotas(this.c.getSolicitudSimulacion().getDatosSolicitudPrestamo().getCantCuotas());
        resultado.setTipoTasa(CCreditos.formatTipoTasa(datosSolicitudPrestamo.getTipoTasa(), null));
        resultado.setIndicadorUVA(this.c.getSolicitudSimulacion().getDatosSolicitudPrestamo().getIndicadorUVA());
        resultado.setDestino(datosSolicitudPrestamo.getDestinoPrestSelect());
        resultado.setFechaPrimerVencimiento(UtilDate.getDateFormat(UtilDate.getDateFormat(datosSolicitudPrestamo.getFechaPrimerVenc()), Constants.FORMAT_DATE_APP, Constants.FORMAT_DATE_APP_2));
        if (datosSolicitudPrestamo.getSeguros() != null) {
            if (!datosSolicitudPrestamo.getSeguros().equalsIgnoreCase("N/A")) {
                CAmount cAmount3 = new CAmount(datosSolicitudPrestamo.getSeguros());
                cAmount3.setSymbolCurrencyDollarOrPeso(false);
                resultado.setCargoSeguro(cAmount3.getAmount());
            } else {
                resultado.setCargoSeguro(datosSolicitudPrestamo.getSeguros());
            }
        }
        if (datosSolicitudPrestamo.getCapitalEinteres() != null) {
            CAmount cAmount4 = new CAmount(datosSolicitudPrestamo.getCapitalEinteres());
            cAmount4.setSymbolCurrencyDollarOrPeso(false);
            resultado.setCapitalIntereses(cAmount4.getAmount());
        }
        if (datosSolicitudPrestamo.getIva() != null) {
            CAmount cAmount5 = new CAmount(datosSolicitudPrestamo.getIva());
            cAmount5.setSymbolCurrencyDollarOrPeso(false);
            resultado.setIva(cAmount5.getAmount());
        }
        if (datosSolicitudPrestamo.getOtrosImpCuota() != null) {
            CAmount cAmount6 = new CAmount(datosSolicitudPrestamo.getOtrosImpCuota());
            cAmount6.setSymbolCurrencyDollarOrPeso(false);
            resultado.setOtrosImpuestos(cAmount6.getAmount());
        }
        if (datosSolicitudPrestamo.getImporteCuota() != null) {
            CAmount cAmount7 = new CAmount(datosSolicitudPrestamo.getImporteCuota());
            cAmount7.setSymbolCurrencyDollarOrPeso(false);
            resultado.setImporte(cAmount7.getAmount());
        }
        resultado.setLeyenda(datosSolicitudPrestamo.getLeyendaCanc());
        resultado.setLeyendaConf(datosSolicitudPrestamo.getLeyendaConf());
        String replace = datosSolicitudPrestamo.getTasaNomAnual().replace(".", ",");
        String replace2 = datosSolicitudPrestamo.getTasaEfectAnual().replace(".", ",");
        String replace3 = datosSolicitudPrestamo.getTasaCFTNA().replace(".", ",");
        String replace4 = datosSolicitudPrestamo.getTasaCFTNAIVA().replace(".", ",");
        if (datosSolicitudPrestamo.getTasaNomAnual() != null && !datosSolicitudPrestamo.getTasaNomAnual().isEmpty()) {
            StringBuilder sb = new StringBuilder();
            sb.append(replace);
            sb.append(Constants.SYMBOL_PERCENTAGE);
            resultado.setTasaNominalAnual(sb.toString());
        }
        if (datosSolicitudPrestamo.getTasaEfectAnual() != null && !datosSolicitudPrestamo.getTasaEfectAnual().isEmpty()) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(replace2);
            sb2.append(Constants.SYMBOL_PERCENTAGE);
            resultado.setTasaEfectAnual(sb2.toString());
        }
        if (datosSolicitudPrestamo.getTasaCFTNA() != null && !datosSolicitudPrestamo.getTasaCFTNA().isEmpty()) {
            StringBuilder sb3 = new StringBuilder();
            sb3.append(replace3);
            sb3.append(Constants.SYMBOL_PERCENTAGE);
            resultado.setTasaCFTNA(sb3.toString());
        }
        if (datosSolicitudPrestamo.getTasaCFTNAIVA() != null && !datosSolicitudPrestamo.getTasaCFTNAIVA().isEmpty()) {
            StringBuilder sb4 = new StringBuilder();
            sb4.append(replace4);
            sb4.append(Constants.SYMBOL_PERCENTAGE);
            resultado.setTasaCFTNAIVA(sb4.toString());
        }
        if (resultado.getIndicadorUVA() != null && !resultado.getIndicadorUVA().isEmpty()) {
            resultado.setIndicadorUVA(this.c.getSolicitudSimulacion().getDatosSolicitudPrestamo().getIndicadorUVA());
        }
        resultado.setLeyendaTasa(CCreditos.getLeyendaTasa(consultaPrestamosPermitidosBodyResponseBean.getListaLeyenda(), resultado));
        resultado.setCapitalIntereses(datosSolicitudPrestamo.getCapitalEinteres());
        resultado.setCargoSeguro(datosSolicitudPrestamo.getSeguros());
        resultado.setImporte(datosSolicitudPrestamo.getImporteCuota());
        this.c.setConfirmacionView(resultado);
    }

    public void sendRequestConfirmacionPrestamoPermitido() {
        this.c.showProgress();
        SolicitudPrestamoPreacordadoBodyRequestBean solicitudSimulacion = this.c.getSolicitudSimulacion();
        solicitudSimulacion.getDatosSolicitudPrestamo().setOpcion(CreditosConstants.LIQUIDACION);
        this.c.setTipoCta(solicitudSimulacion.getDatosSolicitudPrestamo().getTipoCta());
        this.c.getDataManager().solicitudPrestamoPreacordado(solicitudSimulacion, this.c.getActivity());
    }

    public void getResponseConfirmacionPrestamoPermitido(SolicitudPrestamoPreacordadoEvent solicitudPrestamoPreacordadoEvent, ConsultaPrestamosPermitidosBodyResponseBean consultaPrestamosPermitidosBodyResponseBean) {
        this.b = ((SolicitudPrestamoPreacordadoResponseBean) solicitudPrestamoPreacordadoEvent.getBeanResponse()).solicitudPrestamoPreacordadoBodyResponseBean;
        this.c.goToComprobante(this.b, consultaPrestamosPermitidosBodyResponseBean);
        this.c.dismisProgress();
    }
}
