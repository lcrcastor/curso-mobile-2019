package ar.com.santander.rio.mbanking.app.module.marcacionPorViaje;

import ar.com.santander.rio.mbanking.app.base.IBaseView;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetTarjPaisesBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ViajeBean;

public interface MarcacionPorViajeDestinosView extends IBaseView {
    void setMarcacionPorViajeDestinosView(ViajeBean viajeBean, GetTarjPaisesBodyResponseBean getTarjPaisesBodyResponseBean);
}
