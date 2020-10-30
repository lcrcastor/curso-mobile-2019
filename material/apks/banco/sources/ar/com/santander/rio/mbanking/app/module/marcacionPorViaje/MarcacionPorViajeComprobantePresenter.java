package ar.com.santander.rio.mbanking.app.module.marcacionPorViaje;

import ar.com.santander.rio.mbanking.app.base.BasePresenter;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import com.squareup.otto.Bus;

public class MarcacionPorViajeComprobantePresenter extends BasePresenter<MarcacionPorViajeComprobanteView> {
    public MarcacionPorViajeComprobantePresenter(Bus bus, IDataManager iDataManager) {
        super(bus, iDataManager);
    }

    public void onCreatePage(Boolean bool, String str, String str2) {
        ((MarcacionPorViajeComprobanteView) getBaseView()).setMarcacionPorViajeComprobanteView(bool, str, str2);
    }
}
