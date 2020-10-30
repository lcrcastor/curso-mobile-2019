package ar.com.santander.rio.mbanking.app.module.funds;

import ar.com.santander.rio.mbanking.app.base.IBaseView;
import ar.com.santander.rio.mbanking.services.soap.beans.body.TransferirFondoBodyResponseBean;

public interface ComprobanteTransferirFondoView extends IBaseView {
    void setComprobanteTransferirFondoView(TransferirFondoBodyResponseBean transferirFondoBodyResponseBean);
}
