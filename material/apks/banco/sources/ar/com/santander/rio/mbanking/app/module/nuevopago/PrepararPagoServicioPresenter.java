package ar.com.santander.rio.mbanking.app.module.nuevopago;

import ar.com.santander.rio.mbanking.app.base.BasePresenter;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import com.squareup.otto.Bus;

public class PrepararPagoServicioPresenter extends BasePresenter<PrepararPagoServicioView> {
    public PrepararPagoServicioPresenter(Bus bus, IDataManager iDataManager) {
        super(bus, iDataManager);
    }

    public void showPrepararPago() {
        ((PrepararPagoServicioView) getBaseView()).showPrepararPago();
    }
}
