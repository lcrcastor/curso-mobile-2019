package ar.com.santander.rio.mbanking.app.module.creditos;

import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.commons.CAccounts;
import ar.com.santander.rio.mbanking.app.commons.CAmount;
import ar.com.santander.rio.mbanking.app.commons.CConsDescripciones;
import ar.com.santander.rio.mbanking.app.commons.CCreditos;
import ar.com.santander.rio.mbanking.app.module.creditos.model.Resultado;
import ar.com.santander.rio.mbanking.app.ui.Constants;
import ar.com.santander.rio.mbanking.services.model.creditos.DatosSolicitudPrestamo;
import ar.com.santander.rio.mbanking.services.soap.beans.body.AccountRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ConsultaPrestamosPermitidosBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.SolicitudPrestamoPreacordadoBodyResponseBean;
import ar.com.santander.rio.mbanking.utils.UtilAccount;
import ar.com.santander.rio.mbanking.utils.UtilDate;

public class ComprobantePresenterImp implements ComprobantePresenter {
    private static final String[] c = {Constants.LEYENDA_TASA_CRED_TNA, Constants.LEYENDA_TASA_CRED_TEA, Constants.LEYENDA_TASA_CRED_CFTEA, Constants.LEYENDA_TASA_CRED_CFTEA_SIMP};
    DatosSolicitudPrestamo a;
    private CreditosView b;

    public ComprobantePresenterImp(CreditosView creditosView) {
        this.b = creditosView;
    }

    public void onCreatePage(SolicitudPrestamoPreacordadoBodyResponseBean solicitudPrestamoPreacordadoBodyResponseBean, AccountRequestBean accountRequestBean, ConsultaPrestamosPermitidosBodyResponseBean consultaPrestamosPermitidosBodyResponseBean) {
        this.b.setTitleLayout(this.b.getActContext().getResources().getString(R.string.ID155_CREDITS_VIEW_LBL_RECEIPT));
        this.b.setTitleContentDescription(this.b.getActContext().getResources().getString(R.string.ID155_CREDITS_VIEW_LBL_RECEIPT_DECRIPTION));
        this.a = solicitudPrestamoPreacordadoBodyResponseBean.getDatosSolicitudPrestamo();
        Resultado resultado = new Resultado();
        CAmount cAmount = new CAmount(this.a.getImportSolicitar());
        cAmount.setSymbolCurrencyDollarOrPeso(false);
        resultado.setImporteSolicitud(cAmount.getAmount());
        if (this.a.getImporteSolUVA() != null) {
            resultado.setImporteSolUVA(this.a.getImporteSolUVA());
        }
        if (this.a.getImporteCuotaUVA() != null) {
            resultado.setImporteCuotaUVA(this.a.getImporteCuotaUVA());
        }
        if (this.a.getCuotaPuraUVA() != null) {
            resultado.setCuotaPuraUVA(this.a.getCuotaPuraUVA());
        }
        if (this.a.getCotizacionUVA() != null) {
            resultado.setCotizacionUVA(this.a.getCotizacionUVA());
        }
        if (this.a.getFechaCotizaUVA() != null) {
            resultado.setFechaCotizaUVA(UtilDate.getDateFormat(UtilDate.getDateFormat(this.a.getFechaCotizaUVA()), Constants.FORMAT_DATE_APP, Constants.FORMAT_DATE_APP_2));
        }
        CAmount cAmount2 = new CAmount(this.a.getImporteNetoAcred());
        cAmount2.setSymbolCurrencyDollarOrPeso(false);
        resultado.setImporteNeto(cAmount2.getAmount());
        resultado.setCuentaDestino(CAccounts.getMaskAccount(UtilAccount.getAbreviatureAndAccountFormat(CConsDescripciones.getConsDescripcionTPOCTACORTA(this.b.getSessionManager()), accountRequestBean.tipo, accountRequestBean.sucursalPaq, accountRequestBean.numero)));
        resultado.setCantidadCuotas(this.b.getSolicitudSimulacion().getDatosSolicitudPrestamo().getCantCuotas());
        resultado.setTipoTasa(CCreditos.formatTipoTasa(this.a.getTipoTasa(), null));
        resultado.setIndicadorUVA(this.b.getSolicitudSimulacion().getDatosSolicitudPrestamo().getIndicadorUVA());
        resultado.setFechaPrimerVencimiento(UtilDate.getDateFormat(UtilDate.getDateFormat(this.a.getFechaPrimerVenc()), Constants.FORMAT_DATE_APP, Constants.FORMAT_DATE_APP_2));
        if (this.a.getSeguros() != null) {
            if (!this.a.getSeguros().equalsIgnoreCase("N/A")) {
                CAmount cAmount3 = new CAmount(this.a.getSeguros());
                cAmount3.setSymbolCurrencyDollarOrPeso(false);
                resultado.setCargoSeguro(cAmount3.getAmount());
            } else {
                resultado.setCargoSeguro(this.a.getSeguros());
            }
        }
        if (this.a.getCapitalEinteres() != null) {
            CAmount cAmount4 = new CAmount(this.a.getCapitalEinteres());
            cAmount4.setSymbolCurrencyDollarOrPeso(false);
            resultado.setCapitalIntereses(cAmount4.getAmount());
        }
        if (this.a.getIva() != null) {
            CAmount cAmount5 = new CAmount(this.a.getIva());
            cAmount5.setSymbolCurrencyDollarOrPeso(false);
            resultado.setIva(cAmount5.getAmount());
        }
        if (this.a.getOtrosImpCuota() != null) {
            CAmount cAmount6 = new CAmount(this.a.getOtrosImpCuota());
            cAmount6.setSymbolCurrencyDollarOrPeso(false);
            resultado.setOtrosImpuestos(cAmount6.getAmount());
        }
        if (this.a.getImporteCuota() != null) {
            CAmount cAmount7 = new CAmount(this.a.getImporteCuota());
            cAmount7.setSymbolCurrencyDollarOrPeso(false);
            resultado.setImporte(cAmount7.getAmount());
        }
        resultado.setNumeroComprobante(this.a.getNumComprobante());
        resultado.setLeyenda(this.a.getLeyendaCanc());
        if (this.a.getTasaNomAnual() != null && !this.a.getTasaNomAnual().isEmpty()) {
            String replace = this.a.getTasaNomAnual().replace(".", ",");
            StringBuilder sb = new StringBuilder();
            sb.append(replace);
            sb.append(Constants.SYMBOL_PERCENTAGE);
            resultado.setTasaNominalAnual(sb.toString());
        }
        if (this.a.getTasaEfectAnual() != null && !this.a.getTasaEfectAnual().isEmpty()) {
            String replace2 = this.a.getTasaEfectAnual().replace(".", ",");
            StringBuilder sb2 = new StringBuilder();
            sb2.append(replace2);
            sb2.append(Constants.SYMBOL_PERCENTAGE);
            resultado.setTasaEfectAnual(sb2.toString());
        }
        if (this.a.getTasaCFTNA() != null && !this.a.getTasaCFTNA().isEmpty()) {
            String replace3 = this.a.getTasaCFTNA().replace(".", ",");
            StringBuilder sb3 = new StringBuilder();
            sb3.append(replace3);
            sb3.append(Constants.SYMBOL_PERCENTAGE);
            resultado.setTasaCFTNA(sb3.toString());
        }
        if (this.a.getTasaCFTNAIVA() != null && !this.a.getTasaCFTNAIVA().isEmpty()) {
            String replace4 = this.a.getTasaCFTNAIVA().replace(".", ",");
            StringBuilder sb4 = new StringBuilder();
            sb4.append(replace4);
            sb4.append(Constants.SYMBOL_PERCENTAGE);
            resultado.setTasaCFTNAIVA(sb4.toString());
        }
        if (resultado.getIndicadorUVA() != null && !resultado.getIndicadorUVA().isEmpty()) {
            resultado.setIndicadorUVA(this.b.getSolicitudSimulacion().getDatosSolicitudPrestamo().getIndicadorUVA());
        }
        resultado.setSucursal(solicitudPrestamoPreacordadoBodyResponseBean.getSucursalCta());
        resultado.setTipoCta(this.a.getTipoCta());
        resultado.setNumeroCta(solicitudPrestamoPreacordadoBodyResponseBean.getNroCta());
        if (solicitudPrestamoPreacordadoBodyResponseBean.getLinkSeguro() != null) {
            resultado.setCodigoLink(solicitudPrestamoPreacordadoBodyResponseBean.getLinkSeguro().getResCod());
            resultado.setDescripcionLink(solicitudPrestamoPreacordadoBodyResponseBean.getLinkSeguro().getResDesc());
            resultado.setMostrarLink(solicitudPrestamoPreacordadoBodyResponseBean.getLinkSeguro().getMostrar());
        }
        resultado.setLeyendaTasa(CCreditos.getLeyendaTasa(consultaPrestamosPermitidosBodyResponseBean.getListaLeyenda(), resultado));
        this.b.setComprobacionView(resultado);
    }
}
