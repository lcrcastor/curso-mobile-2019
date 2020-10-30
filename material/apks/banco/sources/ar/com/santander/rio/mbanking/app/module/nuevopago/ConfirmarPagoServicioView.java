package ar.com.santander.rio.mbanking.app.module.nuevopago;

import android.app.Activity;
import ar.com.santander.rio.mbanking.app.base.IBaseView;
import ar.com.santander.rio.mbanking.services.soap.beans.body.PagoServiciosBodyResponseBean;

public interface ConfirmarPagoServicioView extends IBaseView {
    Activity getActivity();

    void showAutoDebitWarning(String str);

    void showComprobantePago(PagoServiciosBodyResponseBean pagoServiciosBodyResponseBean);

    void showConfirmarPago();
}
