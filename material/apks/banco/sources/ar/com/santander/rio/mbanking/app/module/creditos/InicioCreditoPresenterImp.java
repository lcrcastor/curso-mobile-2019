package ar.com.santander.rio.mbanking.app.module.creditos;

import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.commons.CAmount;
import ar.com.santander.rio.mbanking.app.commons.CCreditos;
import ar.com.santander.rio.mbanking.app.module.creditos.model.Inicio;
import ar.com.santander.rio.mbanking.app.module.creditos.model.Tasas;
import ar.com.santander.rio.mbanking.app.ui.Constants;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import ar.com.santander.rio.mbanking.services.model.creditos.DatosPrestamoPermitido;
import ar.com.santander.rio.mbanking.services.model.creditos.DatosPrestamoPermitido.ComparatorTipoPrestamo;
import ar.com.santander.rio.mbanking.services.soap.beans.body.AccountRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ConsultaPrestamosPermitidosBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ConsultaPrestamosPermitidosBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ConsultaSolicitudCrediticiaBodyResponseBean;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class InicioCreditoPresenterImp implements InicioCreditoPresenter {
    ConsultaSolicitudCrediticiaBodyResponseBean a;
    AccountRequestBean b;
    ConsultaPrestamosPermitidosBodyResponseBean c;
    private CreditosView d;

    public void validate() {
    }

    public InicioCreditoPresenterImp(CreditosView creditosView) {
        this.d = creditosView;
    }

    public void onCreatePage(ConsultaSolicitudCrediticiaBodyResponseBean consultaSolicitudCrediticiaBodyResponseBean, AccountRequestBean accountRequestBean, ConsultaPrestamosPermitidosBodyResponseBean consultaPrestamosPermitidosBodyResponseBean) {
        this.a = consultaSolicitudCrediticiaBodyResponseBean;
        this.b = accountRequestBean;
        this.c = consultaPrestamosPermitidosBodyResponseBean;
        this.d.setTitleLayout(this.d.getActContext().getResources().getString(R.string.ID138_CREDITS_LENDCONFIRM_LBL_SUPERLEND));
    }

    public void sendRequestConsultaPrestamosPermitidos(IDataManager iDataManager, boolean z) {
        ConsultaPrestamosPermitidosBodyRequestBean consultaPrestamosPermitidosBodyRequestBean = new ConsultaPrestamosPermitidosBodyRequestBean();
        consultaPrestamosPermitidosBodyRequestBean.setDatosCuenta(this.b);
        iDataManager.consultaPrestamosPermitidos(consultaPrestamosPermitidosBodyRequestBean, z);
    }

    public void getResponseConsultaPrestamosPermitidos(ConsultaPrestamosPermitidosBodyResponseBean consultaPrestamosPermitidosBodyResponseBean) {
        this.c = consultaPrestamosPermitidosBodyResponseBean;
        Inicio inicio = new Inicio();
        CAmount cAmount = new CAmount(this.c.getDatosPrestamos().getMaxImpPrest());
        cAmount.setSymbolCurrencyDollarOrPeso(false);
        CAmount cAmount2 = new CAmount(this.c.getDatosPrestamos().getMinImpPrest());
        cAmount2.setSymbolCurrencyDollarOrPeso(false);
        CAmount cAmount3 = new CAmount(this.a.getConsultaCalificacionCrediticia().getDatosCalificacionCrediticia().getImpDispCuota());
        cAmount3.setSymbolCurrencyDollarOrPeso(false);
        inicio.setImporteMaximoPesos(cAmount.getAmount());
        inicio.setImporteMinimoPesos(cAmount2.getAmount());
        inicio.setImporteMaximoCuotas(cAmount3.getAmount());
        List<DatosPrestamoPermitido> listaDatosPrestamoPermitido = this.c.getPrestamosPermitidos().getListaDatosPrestamoPermitido();
        if (!(this.c.getPrestamosPermitidos() == null || this.c.getPrestamosPermitidos().getListaDatosPrestamoPermitido() == null)) {
            Collections.sort(this.c.getPrestamosPermitidos().getListaDatosPrestamoPermitido(), new ComparatorTipoPrestamo());
        }
        ArrayList arrayList = new ArrayList();
        for (DatosPrestamoPermitido datosPrestamoPermitido : listaDatosPrestamoPermitido) {
            Tasas tasas = new Tasas();
            String replace = datosPrestamoPermitido.getValorTasa().replace(".", ",");
            StringBuilder sb = new StringBuilder();
            sb.append(replace);
            sb.append(Constants.SYMBOL_PERCENTAGE);
            tasas.setTasaNominalAnual(sb.toString());
            tasas.setTipo(CCreditos.formatTipoTasa(datosPrestamoPermitido.getTipoTasa(), datosPrestamoPermitido.getIndicadorUVA()));
            StringBuilder sb2 = new StringBuilder();
            sb2.append(Integer.valueOf(datosPrestamoPermitido.getMinCantCuotas()));
            sb2.append(" - ");
            sb2.append(Integer.valueOf(datosPrestamoPermitido.getMaxCantCuotas()));
            tasas.setCuotas(sb2.toString());
            arrayList.add(tasas);
        }
        Tasas tasas2 = new Tasas();
        tasas2.setLeyenda(CCreditos.getLeyenda(this.c.getListaLeyenda(), "PRE_TYC"));
        arrayList.add(tasas2);
        inicio.setTasas(arrayList);
        this.d.setInicioView(inicio);
    }

    public void onItemClicked(int i) {
        if (i <= this.c.getPrestamosPermitidos().getListaDatosPrestamoPermitido().size() && i != 0) {
            this.d.goToSimulacion((DatosPrestamoPermitido) this.c.getPrestamosPermitidos().getListaDatosPrestamoPermitido().get(i - 1));
        }
    }
}
