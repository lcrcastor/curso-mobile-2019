package ar.com.santander.rio.mbanking.app.module.marcacionPorViaje;

import ar.com.santander.rio.mbanking.app.base.BasePresenter;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetTarjPaisesBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ViajeBean;
import com.squareup.otto.Bus;

public class MarcacionPorViajeDestinosPresenter extends BasePresenter<MarcacionPorViajeDestinosView> {
    public MarcacionPorViajeDestinosPresenter(Bus bus, IDataManager iDataManager) {
        super(bus, iDataManager);
    }

    public void onCreatePage(ViajeBean viajeBean, GetTarjPaisesBodyResponseBean getTarjPaisesBodyResponseBean) {
        ((MarcacionPorViajeDestinosView) getBaseView()).setMarcacionPorViajeDestinosView(viajeBean, getTarjPaisesBodyResponseBean);
    }
}
