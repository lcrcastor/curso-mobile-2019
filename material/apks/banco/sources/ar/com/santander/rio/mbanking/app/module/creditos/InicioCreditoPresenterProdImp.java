package ar.com.santander.rio.mbanking.app.module.creditos;

import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.commons.CAmount;
import ar.com.santander.rio.mbanking.app.commons.CCreditosProd;
import ar.com.santander.rio.mbanking.app.module.creditos.model.InicioProd;
import ar.com.santander.rio.mbanking.app.module.creditos.model.TasasProd;
import ar.com.santander.rio.mbanking.app.ui.Constants;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import ar.com.santander.rio.mbanking.services.events.ConsultaPrestamosPermitidosEventProd;
import ar.com.santander.rio.mbanking.services.events.ConsultaSolicitudCrediticiaEventProd;
import ar.com.santander.rio.mbanking.services.model.creditos.DatosPrestamoPermitidoProd;
import ar.com.santander.rio.mbanking.services.soap.beans.ConsultaPrestamosPermitidosResponseBeanProd;
import ar.com.santander.rio.mbanking.services.soap.beans.ConsultaSolicitudCrediticiaResponseBeanProd;
import ar.com.santander.rio.mbanking.services.soap.beans.body.AccountRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ConsultaPrestamosPermitidosBodyRequestBeanProd;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ConsultaPrestamosPermitidosBodyResponseBeanProd;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ConsultaSolicitudCrediticiaBodyResponseBeanProd;
import java.util.ArrayList;
import java.util.List;

public class InicioCreditoPresenterProdImp implements InicioCreditoPresenterProd {
    ConsultaSolicitudCrediticiaBodyResponseBeanProd a;
    ConsultaPrestamosPermitidosBodyResponseBeanProd b;
    AccountRequestBean c;
    private CreditosViewProd d;

    public void validate() {
    }

    public InicioCreditoPresenterProdImp(CreditosViewProd creditosViewProd) {
        this.d = creditosViewProd;
    }

    public void onCreatePage(ConsultaSolicitudCrediticiaEventProd consultaSolicitudCrediticiaEventProd, AccountRequestBean accountRequestBean) {
        this.a = ((ConsultaSolicitudCrediticiaResponseBeanProd) consultaSolicitudCrediticiaEventProd.getBeanResponse()).getConsultaSolicitudCrediticiaBodyResponseBeanProd();
        this.c = accountRequestBean;
        this.d.setTitleLayout(this.d.getActContext().getResources().getString(R.string.ID138_CREDITS_LENDCONFIRM_LBL_SUPERLEND));
    }

    public void sendRequestConsultaPrestamosPermitidos(IDataManager iDataManager, boolean z) {
        ConsultaPrestamosPermitidosBodyRequestBeanProd consultaPrestamosPermitidosBodyRequestBeanProd = new ConsultaPrestamosPermitidosBodyRequestBeanProd();
        consultaPrestamosPermitidosBodyRequestBeanProd.setDatosCuenta(this.c);
        iDataManager.consultaPrestamosPermitidosProd(consultaPrestamosPermitidosBodyRequestBeanProd, z);
    }

    public void getResponseConsultaPrestamosPermitidos(ConsultaPrestamosPermitidosEventProd consultaPrestamosPermitidosEventProd) {
        this.b = ((ConsultaPrestamosPermitidosResponseBeanProd) consultaPrestamosPermitidosEventProd.getBeanResponse()).consultaPrestamosPermitidosBodyResponseBeanProd;
        InicioProd inicioProd = new InicioProd();
        CAmount cAmount = new CAmount(this.b.getDatosPrestamosProd().getMaxImpPrest());
        cAmount.setSymbolCurrencyDollarOrPeso(false);
        CAmount cAmount2 = new CAmount(this.b.getDatosPrestamosProd().getMinImpPrest());
        cAmount2.setSymbolCurrencyDollarOrPeso(false);
        CAmount cAmount3 = new CAmount(this.a.getConsultaCalificacionCrediticiaProd().getDatosCalificacionCrediticiaProd().getImpDispCuota());
        cAmount3.setSymbolCurrencyDollarOrPeso(false);
        inicioProd.setImporteMaximoPesos(cAmount.getAmount());
        inicioProd.setImporteMinimoPesos(cAmount2.getAmount());
        inicioProd.setImporteMaximoCuotas(cAmount3.getAmount());
        List<DatosPrestamoPermitidoProd> listaDatosPrestamoPermitidoProd = this.b.getPrestamosPermitidosProd().getListaDatosPrestamoPermitidoProd();
        ArrayList arrayList = new ArrayList();
        for (DatosPrestamoPermitidoProd datosPrestamoPermitidoProd : listaDatosPrestamoPermitidoProd) {
            TasasProd tasasProd = new TasasProd();
            CAmount cAmount4 = new CAmount(datosPrestamoPermitidoProd.getValorTasa(), "#,##0.000");
            StringBuilder sb = new StringBuilder();
            sb.append(cAmount4.getAmount());
            sb.append(Constants.SYMBOL_PERCENTAGE);
            tasasProd.setTasaNominalAnual(sb.toString());
            tasasProd.setTipo(CCreditosProd.formatTipoTasa(datosPrestamoPermitidoProd.getTipoTasa()));
            StringBuilder sb2 = new StringBuilder();
            sb2.append(Integer.valueOf(datosPrestamoPermitidoProd.getMinCantCuotas()));
            sb2.append(" - ");
            sb2.append(Integer.valueOf(datosPrestamoPermitidoProd.getMaxCantCuotas()));
            tasasProd.setCuotas(sb2.toString());
            arrayList.add(tasasProd);
        }
        TasasProd tasasProd2 = new TasasProd();
        tasasProd2.setLeyenda(CCreditosProd.getLeyenda(this.b.getListaLeyendaProd(), "PRE_TYC"));
        arrayList.add(tasasProd2);
        inicioProd.setTasas(arrayList);
        this.d.setInicioProdView(inicioProd);
    }

    public void onItemClicked(int i) {
        if (i <= this.b.getPrestamosPermitidosProd().getListaDatosPrestamoPermitidoProd().size() && i != 0) {
            this.d.goToSimulacion((DatosPrestamoPermitidoProd) this.b.getPrestamosPermitidosProd().getListaDatosPrestamoPermitidoProd().get(i - 1));
        }
    }
}
