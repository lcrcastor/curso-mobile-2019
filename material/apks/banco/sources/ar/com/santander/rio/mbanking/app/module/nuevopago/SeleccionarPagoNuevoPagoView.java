package ar.com.santander.rio.mbanking.app.module.nuevopago;

import ar.com.santander.rio.mbanking.app.base.IBaseView;
import ar.com.santander.rio.mbanking.services.soap.beans.body.DatosDeudaBean;

public interface SeleccionarPagoNuevoPagoView extends IBaseView {
    void showInvoicePreparePayment(DatosDeudaBean datosDeudaBean);

    void showSelectPayment();
}
