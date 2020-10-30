package ar.com.santander.rio.mbanking.app.module.creditos;

import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.commons.CAmount;
import ar.com.santander.rio.mbanking.app.commons.CConsDescripciones;
import ar.com.santander.rio.mbanking.app.commons.CCreditosProd;
import ar.com.santander.rio.mbanking.app.module.creditos.model.ResultadoProd;
import ar.com.santander.rio.mbanking.app.ui.Constants;
import ar.com.santander.rio.mbanking.services.events.SolicitudPrestamoPreacordadoEventProd;
import ar.com.santander.rio.mbanking.services.model.creditos.DatosSolicitudPrestamoProd;
import ar.com.santander.rio.mbanking.services.soap.beans.SolicitudPrestamoPreacordadoResponseBeanProd;
import ar.com.santander.rio.mbanking.services.soap.beans.body.SolicitudPrestamoPreacordadoBodyRequestBeanProd;
import ar.com.santander.rio.mbanking.services.soap.beans.body.SolicitudPrestamoPreacordadoBodyResponseBeanProd;
import ar.com.santander.rio.mbanking.utils.UtilAccount;
import ar.com.santander.rio.mbanking.utils.UtilDate;

public class ConfirmacionPresenterProdImp implements ConfirmacionPresenterProd {
    DatosSolicitudPrestamoProd a;
    SolicitudPrestamoPreacordadoBodyResponseBeanProd b;
    private CreditosViewProd c;

    public ConfirmacionPresenterProdImp(CreditosViewProd creditosViewProd) {
        this.c = creditosViewProd;
    }

    public void onCreatePage(DatosSolicitudPrestamoProd datosSolicitudPrestamoProd) {
        this.c.setTitleLayout(this.c.getActContext().getResources().getString(R.string.ID138_CREDITS_LENDCONFIRM_LBL_SUPERLEND));
        this.a = datosSolicitudPrestamoProd;
        ResultadoProd resultadoProd = new ResultadoProd();
        CAmount cAmount = new CAmount(datosSolicitudPrestamoProd.getImportSolicitar());
        cAmount.setSymbolCurrencyDollarOrPeso(false);
        resultadoProd.setImporteSolicitud(cAmount.getAmount());
        CAmount cAmount2 = new CAmount(datosSolicitudPrestamoProd.getImporteNetoAcred());
        cAmount2.setSymbolCurrencyDollarOrPeso(false);
        resultadoProd.setImporteNeto(cAmount2.getAmount());
        resultadoProd.setCuentaDestino(UtilAccount.getAbreviatureAndAccountFormat(CConsDescripciones.getConsDescripcionTPOCTACORTA(this.c.getSessionManager()), this.c.getCuenta().getTipo(), this.c.getCuenta().getSucursalPaq(), this.c.getCuenta().getNumero()));
        resultadoProd.setCantidadCuotas(this.c.getSolicitudSimulacion().getDatosSolicitudPrestamoProd().getCantCuotas());
        resultadoProd.setTipoTasa(CCreditosProd.formatTipoTasa(datosSolicitudPrestamoProd.getTipoTasa()));
        CAmount cAmount3 = new CAmount(datosSolicitudPrestamoProd.getTasaNomAnual(), "#,##0.000");
        StringBuilder sb = new StringBuilder();
        sb.append(cAmount3.getAmount());
        sb.append(Constants.SYMBOL_PERCENTAGE);
        resultadoProd.setTasaNominalAnual(sb.toString());
        resultadoProd.setDestino(datosSolicitudPrestamoProd.getDestinoPrestSelect());
        resultadoProd.setFechaPrimerVencimiento(UtilDate.getDateFormat(UtilDate.getDateFormat(datosSolicitudPrestamoProd.getFechaPrimerVenc()), Constants.FORMAT_DATE_APP, Constants.FORMAT_DATE_APP_2));
        CAmount cAmount4 = new CAmount(datosSolicitudPrestamoProd.getCapitalEinteres());
        cAmount4.setSymbolCurrencyDollarOrPeso(false);
        resultadoProd.setCapitalIntereses(cAmount4.getAmount());
        if (!datosSolicitudPrestamoProd.getSeguros().equalsIgnoreCase("N/A")) {
            CAmount cAmount5 = new CAmount(datosSolicitudPrestamoProd.getSeguros());
            cAmount5.setSymbolCurrencyDollarOrPeso(false);
            resultadoProd.setCargoSeguro(cAmount5.getAmount());
        } else {
            resultadoProd.setCargoSeguro(datosSolicitudPrestamoProd.getSeguros());
        }
        CAmount cAmount6 = new CAmount(datosSolicitudPrestamoProd.getIva());
        cAmount6.setSymbolCurrencyDollarOrPeso(false);
        resultadoProd.setIva(cAmount6.getAmount());
        CAmount cAmount7 = new CAmount(datosSolicitudPrestamoProd.getOtrosImpCuota());
        cAmount7.setSymbolCurrencyDollarOrPeso(false);
        resultadoProd.setOtrosImpuestos(cAmount7.getAmount());
        CAmount cAmount8 = new CAmount(datosSolicitudPrestamoProd.getImporteCuota());
        cAmount8.setSymbolCurrencyDollarOrPeso(false);
        resultadoProd.setImporte(cAmount8.getAmount());
        resultadoProd.setLeyenda(datosSolicitudPrestamoProd.getLeyendaCanc());
        resultadoProd.setLeyendaConf(datosSolicitudPrestamoProd.getLeyendaConf());
        CAmount cAmount9 = new CAmount(datosSolicitudPrestamoProd.getTasaEfectAnual(), "#,##0.000");
        CAmount cAmount10 = new CAmount(datosSolicitudPrestamoProd.getTasaCFTNA(), "#,##0.000");
        CAmount cAmount11 = new CAmount(datosSolicitudPrestamoProd.getTasaCFTNAIVA(), "#,##0.000");
        StringBuilder sb2 = new StringBuilder();
        sb2.append(cAmount9.getAmount());
        sb2.append(Constants.SYMBOL_PERCENTAGE);
        resultadoProd.setTasaEfectAnual(sb2.toString());
        StringBuilder sb3 = new StringBuilder();
        sb3.append(cAmount10.getAmount());
        sb3.append(Constants.SYMBOL_PERCENTAGE);
        resultadoProd.setTasaCFTNA(sb3.toString());
        StringBuilder sb4 = new StringBuilder();
        sb4.append(cAmount11.getAmount());
        sb4.append(Constants.SYMBOL_PERCENTAGE);
        resultadoProd.setTasaCFTNAIVA(sb4.toString());
        this.c.setConfirmacionView(resultadoProd);
    }

    public void sendRequestConfirmacionPrestamoPermitido() {
        this.c.showProgress();
        SolicitudPrestamoPreacordadoBodyRequestBeanProd solicitudSimulacion = this.c.getSolicitudSimulacion();
        solicitudSimulacion.getDatosSolicitudPrestamoProd().setOpcion(CreditosConstantsProd.LIQUIDACION);
        this.c.getDataManager().solicitudPrestamoPreacordadoProd(solicitudSimulacion, this.c.getActivity());
    }

    public void getResponseConfirmacionPrestamoPermitido(SolicitudPrestamoPreacordadoEventProd solicitudPrestamoPreacordadoEventProd) {
        this.b = ((SolicitudPrestamoPreacordadoResponseBeanProd) solicitudPrestamoPreacordadoEventProd.getBeanResponse()).solicitudPrestamoPreacordadoBodyResponseBeanProd;
        this.c.goToComprobante(this.b.getDatosSolicitudPrestamoProd());
        this.c.dismisProgress();
    }
}
