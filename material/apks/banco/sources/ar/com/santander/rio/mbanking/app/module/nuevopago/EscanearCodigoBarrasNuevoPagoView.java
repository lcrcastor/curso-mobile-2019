package ar.com.santander.rio.mbanking.app.module.nuevopago;

import ar.com.santander.rio.mbanking.app.base.IBaseView;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CnsEmpresaDatosEmpresa;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CuentaDebitoBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.DatosDeudaBean;
import java.util.List;

public interface EscanearCodigoBarrasNuevoPagoView extends IBaseView {
    void cleanScreenPreview();

    void prepareCamera();

    void releaseCamera();

    void showBarcodeUnrecognized(String str);

    void showBarcodeUnrecognizedExceededRetries();

    void showPrepararPagoAfip(DatosDeudaBean datosDeudaBean, List<CuentaDebitoBean> list);

    void showPrepararPagoElectronico(DatosDeudaBean datosDeudaBean, List<CuentaDebitoBean> list);

    void showSeleccionarEmpresa(String str, List<CnsEmpresaDatosEmpresa> list);

    void showSelectPayment(List<DatosDeudaBean> list);
}
