package ar.com.santander.rio.mbanking.app.module.TIS;

import android.content.Context;
import ar.com.santander.rio.mbanking.app.base.BasePresenter;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import com.squareup.otto.Bus;

public class SolicitudAumentoPresenter extends BasePresenter<SolicitudAumentoView> {
    public SolicitudAumentoPresenter(Bus bus, IDataManager iDataManager) {
        super(bus, iDataManager);
    }

    public void onCreatePage() {
        ((SolicitudAumentoView) getBaseView()).setSolicitudAumentoView();
    }

    public Context getContext() {
        return ((SolicitudAumentoView) getBaseView()).getContext();
    }
}
