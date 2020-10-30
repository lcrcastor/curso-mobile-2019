package ar.com.santander.rio.mbanking.app.module.TIS;

import ar.com.santander.rio.mbanking.app.base.IBaseView;
import ar.com.santander.rio.mbanking.services.soap.beans.body.SolicitudLimiteTransfBodyResponseBean;

public interface ComprobanteSolicitudAumentoView extends IBaseView {
    void setComprobanteSolicitudAumentoView(SolicitudLimiteTransfBodyResponseBean solicitudLimiteTransfBodyResponseBean);
}
