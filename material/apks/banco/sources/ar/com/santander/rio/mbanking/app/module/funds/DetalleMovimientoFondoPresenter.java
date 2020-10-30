package ar.com.santander.rio.mbanking.app.module.funds;

import ar.com.santander.rio.mbanking.app.base.BasePresenter;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import ar.com.santander.rio.mbanking.services.soap.beans.body.MovimientoFondosBean;
import com.squareup.otto.Bus;

public class DetalleMovimientoFondoPresenter extends BasePresenter<DetalleMovimientoFondoView> {
    public DetalleMovimientoFondoPresenter(Bus bus, IDataManager iDataManager) {
        super(bus, iDataManager);
    }

    public void onCreatePage(MovimientoFondosBean movimientoFondosBean) {
        ((DetalleMovimientoFondoView) getBaseView()).setDetalleMovimientoFondoView(movimientoFondosBean);
    }
}
