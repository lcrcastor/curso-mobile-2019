package ar.com.santander.rio.mbanking.app.module.nuevopago;

import ar.com.santander.rio.mbanking.app.base.IBaseView;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CuentaDebitoBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.DatosDeudaBean;
import java.util.List;

public interface AgregarPagoNuevoPagoView extends IBaseView {
    void showAddPayment();

    void showInvalidDataAlert(String str);

    void showSelectPayment(List<CuentaDebitoBean> list, DatosDeudaBean datosDeudaBean);

    void showSelectPayment(List<CuentaDebitoBean> list, List<DatosDeudaBean> list2, String str);

    void showToastAlert(String str);
}
