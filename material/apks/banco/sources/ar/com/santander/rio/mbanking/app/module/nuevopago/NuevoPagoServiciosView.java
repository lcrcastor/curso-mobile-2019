package ar.com.santander.rio.mbanking.app.module.nuevopago;

import android.content.Context;
import android.text.Spanned;
import ar.com.santander.rio.mbanking.app.base.IBaseView;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CnsEmpresaDatosEmpresa;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CuentaDebitoBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.DatosDeudaBean;
import java.util.List;

public interface NuevoPagoServiciosView extends IBaseView {
    Context getContext();

    String getTitle();

    void setAccountList(List<CuentaDebitoBean> list);

    void setResultCnsEmpresa(Boolean bool, Spanned spanned);

    void showElectronicAddPayment(List<CuentaDebitoBean> list, CnsEmpresaDatosEmpresa cnsEmpresaDatosEmpresa, DatosDeudaBean datosDeudaBean);

    void showNoPayments();

    void showOnBoarding();

    void showPaymentsList(List<DatosDeudaBean> list);

    void showPrepararPagoAfip(DatosDeudaBean datosDeudaBean);

    void showPrepararPagoConFactura(DatosDeudaBean datosDeudaBean);

    void showPrepararPagoPrepago(DatosDeudaBean datosDeudaBean);

    void showPrepararPagoSinFactura(DatosDeudaBean datosDeudaBean, String str);

    void showRecargaActivity(List<CuentaDebitoBean> list, CnsEmpresaDatosEmpresa cnsEmpresaDatosEmpresa, DatosDeudaBean datosDeudaBean);
}
