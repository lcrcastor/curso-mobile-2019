package ar.com.santander.rio.mbanking.app.module.funds;

import ar.com.santander.rio.mbanking.app.base.IBaseView;
import ar.com.santander.rio.mbanking.services.soap.beans.body.RescatarFondoBodyResponseBean;

public interface ComprobanteRescateFondoView extends IBaseView {
    void setComprobanteRescateFondoView(RescatarFondoBodyResponseBean rescatarFondoBodyResponseBean);
}
