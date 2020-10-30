package ar.com.santander.rio.mbanking.app.module.TIS;

import ar.com.santander.rio.mbanking.app.base.IBaseView;
import ar.com.santander.rio.mbanking.services.soap.beans.body.SolicitudLimiteTransfBodyResponseBean;

public interface ConfirmarSolicitudAumentoView extends IBaseView {
    void gotoComprobanteSolicitud(SolicitudLimiteTransfBodyResponseBean solicitudLimiteTransfBodyResponseBean);

    void setConfirmarsolicitudAumentoView();
}
