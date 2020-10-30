package ar.com.santander.rio.mbanking.app.module.transfers.abmDestinatarioTransf;

import ar.com.santander.rio.mbanking.app.base.BasePresenter;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import com.squareup.otto.Bus;

public class AltaManualDestinatarioTransferenciaPresenter extends BasePresenter<AltaManualDestinatarioTransferenciaView> {
    public AltaManualDestinatarioTransferenciaPresenter(Bus bus, IDataManager iDataManager) {
        super(bus, iDataManager);
    }

    public void onCreatePage() {
        ((AltaManualDestinatarioTransferenciaView) getBaseView()).setAltaManualDestinatarioView();
    }
}
