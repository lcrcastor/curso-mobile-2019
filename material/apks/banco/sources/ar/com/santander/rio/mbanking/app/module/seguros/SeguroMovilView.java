package ar.com.santander.rio.mbanking.app.module.seguros;

import ar.com.santander.rio.mbanking.app.base.IBaseView;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CotizacionBean;

public interface SeguroMovilView extends IBaseView {
    void goToSeleccionarCobertura(CotizacionBean cotizacionBean);

    void setContratarSeguroMovilView();
}
