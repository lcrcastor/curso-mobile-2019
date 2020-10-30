package ar.com.santander.rio.mbanking.app.module.TIS;

import ar.com.santander.rio.mbanking.app.base.BasePresenter;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import ar.com.santander.rio.mbanking.services.soap.beans.body.SolicitudLimiteTransfBodyResponseBean;
import com.squareup.otto.Bus;

public class ComprobanteSolicitudAumentoPresenter extends BasePresenter<ComprobanteSolicitudAumentoView> {
    public ComprobanteSolicitudAumentoPresenter(Bus bus, IDataManager iDataManager) {
        super(bus, iDataManager);
    }

    public void onCreatePage(SolicitudLimiteTransfBodyResponseBean solicitudLimiteTransfBodyResponseBean) {
        ((ComprobanteSolicitudAumentoView) getBaseView()).setComprobanteSolicitudAumentoView(solicitudLimiteTransfBodyResponseBean);
    }
}
