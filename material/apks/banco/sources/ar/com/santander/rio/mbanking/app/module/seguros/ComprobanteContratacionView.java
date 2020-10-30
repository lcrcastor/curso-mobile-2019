package ar.com.santander.rio.mbanking.app.module.seguros;

import ar.com.santander.rio.mbanking.app.base.IBaseView;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ContratarSeguroMovilBodyResponseBean;

public interface ComprobanteContratacionView extends IBaseView {
    void setComprobanteContratacionView(ContratarSeguroMovilBodyResponseBean contratarSeguroMovilBodyResponseBean);
}
