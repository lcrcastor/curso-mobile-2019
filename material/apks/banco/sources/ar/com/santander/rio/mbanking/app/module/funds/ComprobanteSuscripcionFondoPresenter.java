package ar.com.santander.rio.mbanking.app.module.funds;

import ar.com.santander.rio.mbanking.app.base.BasePresenter;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import ar.com.santander.rio.mbanking.services.soap.beans.body.SuscribirFondoBodyResponseBean;
import com.squareup.otto.Bus;

public class ComprobanteSuscripcionFondoPresenter extends BasePresenter<ComprobanteSuscripcionFondoView> {
    public ComprobanteSuscripcionFondoPresenter(Bus bus, IDataManager iDataManager) {
        super(bus, iDataManager);
    }

    public void onCreatePage(SuscribirFondoBodyResponseBean suscribirFondoBodyResponseBean) {
        ((ComprobanteSuscripcionFondoView) getBaseView()).setComprobanteSuscripcionView(suscribirFondoBodyResponseBean);
    }
}
