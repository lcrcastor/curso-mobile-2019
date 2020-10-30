package ar.com.santander.rio.mbanking.app.module.funds;

import ar.com.santander.rio.mbanking.app.base.BasePresenter;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import ar.com.santander.rio.mbanking.services.soap.beans.body.RescatarFondoBodyResponseBean;
import com.squareup.otto.Bus;

public class ComprobanteRescateFondoPresenter extends BasePresenter<ComprobanteRescateFondoView> {
    public ComprobanteRescateFondoPresenter(Bus bus, IDataManager iDataManager) {
        super(bus, iDataManager);
    }

    public void onCreatePage(RescatarFondoBodyResponseBean rescatarFondoBodyResponseBean) {
        ((ComprobanteRescateFondoView) getBaseView()).setComprobanteRescateFondoView(rescatarFondoBodyResponseBean);
    }
}
