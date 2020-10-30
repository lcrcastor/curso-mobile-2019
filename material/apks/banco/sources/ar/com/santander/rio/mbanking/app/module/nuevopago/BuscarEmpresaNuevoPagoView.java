package ar.com.santander.rio.mbanking.app.module.nuevopago;

import android.text.Spanned;
import ar.com.santander.rio.mbanking.app.base.IBaseView;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CnsEmpresaDatosEmpresa;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CuentaDebitoBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.DatosDeudaBean;
import java.util.List;

public interface BuscarEmpresaNuevoPagoView extends IBaseView {
    void setTxtDescription(Spanned spanned);

    void showAfipAddPayment(List<CuentaDebitoBean> list, CnsEmpresaDatosEmpresa cnsEmpresaDatosEmpresa, DatosDeudaBean datosDeudaBean);

    void showElectronicAddPayment(List<CuentaDebitoBean> list, CnsEmpresaDatosEmpresa cnsEmpresaDatosEmpresa, DatosDeudaBean datosDeudaBean);

    void showFindCompany(List<CnsEmpresaDatosEmpresa> list);

    void showPhoneAddPayment(List<CuentaDebitoBean> list, CnsEmpresaDatosEmpresa cnsEmpresaDatosEmpresa, DatosDeudaBean datosDeudaBean);

    void showScanBarcode();

    void warnNoAccounts(String str);
}
