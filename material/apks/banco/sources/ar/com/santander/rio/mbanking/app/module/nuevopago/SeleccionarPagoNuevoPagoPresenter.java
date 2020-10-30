package ar.com.santander.rio.mbanking.app.module.nuevopago;

import ar.com.santander.rio.mbanking.app.base.BasePresenter;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import ar.com.santander.rio.mbanking.services.soap.beans.body.DatosDeudaBean;
import com.squareup.otto.Bus;

public class SeleccionarPagoNuevoPagoPresenter extends BasePresenter<SeleccionarPagoNuevoPagoView> {
    public SeleccionarPagoNuevoPagoPresenter(Bus bus, IDataManager iDataManager) {
        super(bus, iDataManager);
    }

    public void showSelectPayment() {
        ((SeleccionarPagoNuevoPagoView) getBaseView()).showSelectPayment();
    }

    public void onPaymentSelected(DatosDeudaBean datosDeudaBean) {
        ((SeleccionarPagoNuevoPagoView) getBaseView()).showInvoicePreparePayment(datosDeudaBean);
    }
}
