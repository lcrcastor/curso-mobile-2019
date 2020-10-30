package ar.com.santander.rio.mbanking.app.module.transfers;

import ar.com.santander.rio.mbanking.services.soap.beans.body.LeyendasBean;

public class LegalesPresenterImp implements LegalesPresenter {
    public TransferenciasView transferenciasView;

    public void onCreatePage(LeyendasBean leyendasBean) {
    }

    public LegalesPresenterImp(TransferenciasView transferenciasView2) {
        this.transferenciasView = transferenciasView2;
    }
}
