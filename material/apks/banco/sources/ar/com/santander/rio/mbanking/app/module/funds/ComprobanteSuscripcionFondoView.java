package ar.com.santander.rio.mbanking.app.module.funds;

import ar.com.santander.rio.mbanking.app.base.IBaseView;
import ar.com.santander.rio.mbanking.services.soap.beans.body.SuscribirFondoBodyResponseBean;

public interface ComprobanteSuscripcionFondoView extends IBaseView {
    void setComprobanteSuscripcionView(SuscribirFondoBodyResponseBean suscribirFondoBodyResponseBean);
}
