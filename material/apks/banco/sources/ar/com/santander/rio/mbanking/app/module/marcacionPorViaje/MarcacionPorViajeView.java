package ar.com.santander.rio.mbanking.app.module.marcacionPorViaje;

import ar.com.santander.rio.mbanking.app.base.IBaseView;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetTarjPaisesBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ViajeBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ViajesBean;

public interface MarcacionPorViajeView extends IBaseView {
    void goToDetalleViajeEditable(GetTarjPaisesBodyResponseBean getTarjPaisesBodyResponseBean);

    void goToDetalleViajeNoEditable(ViajeBean viajeBean);

    void setMarcacionPorViajeRes4View(String str, String str2);

    void setMarcacionPorViajeView(ViajesBean viajesBean);

    void setMarcacionPorViajeView(ViajesBean viajesBean, String str);
}
