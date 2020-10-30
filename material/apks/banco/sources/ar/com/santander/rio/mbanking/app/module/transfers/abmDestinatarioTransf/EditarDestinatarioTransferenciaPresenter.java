package ar.com.santander.rio.mbanking.app.module.transfers.abmDestinatarioTransf;

import ar.com.santander.rio.mbanking.app.base.BasePresenter;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import ar.com.santander.rio.mbanking.services.soap.beans.VerificaDatosInicialesTransfOBResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CtaMigradaBean;
import com.squareup.otto.Bus;

public class EditarDestinatarioTransferenciaPresenter extends BasePresenter<EditarDestinatarioTransferenciaView> {
    public EditarDestinatarioTransferenciaPresenter(Bus bus, IDataManager iDataManager) {
        super(bus, iDataManager);
    }

    public void onCreatePage(VerificaDatosInicialesTransfOBResponseBean verificaDatosInicialesTransfOBResponseBean, CtaMigradaBean ctaMigradaBean) {
        ((EditarDestinatarioTransferenciaView) getBaseView()).setEditarDestinatarioView(verificaDatosInicialesTransfOBResponseBean, ctaMigradaBean);
    }

    public void onCreatePage() {
        ((EditarDestinatarioTransferenciaView) getBaseView()).setEditarDestinatarioView();
    }
}
