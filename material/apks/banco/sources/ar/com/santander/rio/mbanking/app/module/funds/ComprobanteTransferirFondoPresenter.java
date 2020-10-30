package ar.com.santander.rio.mbanking.app.module.funds;

import ar.com.santander.rio.mbanking.app.base.BasePresenter;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import ar.com.santander.rio.mbanking.services.soap.beans.body.TransferirFondoBodyResponseBean;
import com.squareup.otto.Bus;

public class ComprobanteTransferirFondoPresenter extends BasePresenter<ComprobanteTransferirFondoView> {
    public ComprobanteTransferirFondoPresenter(Bus bus, IDataManager iDataManager) {
        super(bus, iDataManager);
    }

    public void onCreatePage(TransferirFondoBodyResponseBean transferirFondoBodyResponseBean) {
        ((ComprobanteTransferirFondoView) getBaseView()).setComprobanteTransferirFondoView(transferirFondoBodyResponseBean);
    }
}
