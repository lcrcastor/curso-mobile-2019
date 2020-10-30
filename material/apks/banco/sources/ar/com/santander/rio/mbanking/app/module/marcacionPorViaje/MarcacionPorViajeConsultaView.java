package ar.com.santander.rio.mbanking.app.module.marcacionPorViaje;

import ar.com.santander.rio.mbanking.app.base.IBaseView;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetTarjPaisesBodyResponseBean;

public interface MarcacionPorViajeConsultaView extends IBaseView {
    void gotoComprobante(Boolean bool, String str, String str2);

    void setMarcacionPorViajeConsultaView(GetTarjPaisesBodyResponseBean getTarjPaisesBodyResponseBean);
}
