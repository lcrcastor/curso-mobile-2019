package ar.com.santander.rio.mbanking.app.module.nuevopago;

import ar.com.santander.rio.mbanking.app.base.BasePresenter;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import com.squareup.otto.Bus;

public class ComprobantePagoServicioPresenter extends BasePresenter<ComprobantePagoServicioView> {
    public ComprobantePagoServicioPresenter(Bus bus, IDataManager iDataManager) {
        super(bus, iDataManager);
    }

    public void showComprobantePago() {
        ((ComprobantePagoServicioView) getBaseView()).showComprobantePago();
    }
}
