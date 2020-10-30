package ar.com.santander.rio.mbanking.app.module.nuevopago;

import ar.com.santander.rio.mbanking.app.base.IBaseView;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CuentaDebitoBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.DatosDeudaBean;
import java.util.List;

public interface SeleccionarEmpresaNuevoPagoView extends IBaseView {
    void showPrepararPagoAfip(DatosDeudaBean datosDeudaBean, List<CuentaDebitoBean> list);

    void showPrepararPagoElectronico(DatosDeudaBean datosDeudaBean, List<CuentaDebitoBean> list);

    void showSelectCompany();

    void showSelectPayment(List<DatosDeudaBean> list);
}
