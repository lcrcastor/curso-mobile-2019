package ar.com.santander.rio.mbanking.app.module.seguros;

import ar.com.santander.rio.mbanking.app.base.IBaseView;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ContratarCompraProtegidaBodyResponseBean;

public interface ComprobanteContratacionCProtegidaView extends IBaseView {
    void setComprobanteContratacionView(ContratarCompraProtegidaBodyResponseBean contratarCompraProtegidaBodyResponseBean);
}
