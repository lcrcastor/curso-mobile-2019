package ar.com.santander.rio.mbanking.app.module.TIS;

import ar.com.santander.rio.mbanking.app.base.BasePresenter;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import com.squareup.otto.Bus;

public class SolicitudAumentoSecondPagePresenter extends BasePresenter<SolicitudAumentoSecondPageView> {
    public SolicitudAumentoSecondPagePresenter(Bus bus, IDataManager iDataManager) {
        super(bus, iDataManager);
    }

    public void onCreatePage() {
        ((SolicitudAumentoSecondPageView) getBaseView()).setSolicitarAumentoSecondPageView();
    }
}
