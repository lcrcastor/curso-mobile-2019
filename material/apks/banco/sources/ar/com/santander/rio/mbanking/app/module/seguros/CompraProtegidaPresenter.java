package ar.com.santander.rio.mbanking.app.module.seguros;

import ar.com.santander.rio.mbanking.app.base.BasePresenter;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import com.squareup.otto.Bus;

public class CompraProtegidaPresenter extends BasePresenter<CompraProtegidaView> {
    public CompraProtegidaPresenter(Bus bus, IDataManager iDataManager) {
        super(bus, iDataManager);
    }

    public void onCreatePage() {
        ((CompraProtegidaView) getBaseView()).setCompraProtegidaView();
    }
}
