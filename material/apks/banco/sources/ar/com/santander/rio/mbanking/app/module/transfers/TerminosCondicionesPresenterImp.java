package ar.com.santander.rio.mbanking.app.module.transfers;

import ar.com.santander.rio.mbanking.services.soap.beans.body.LeyendasBean;

public class TerminosCondicionesPresenterImp implements TerminosCondicionesPresenter {
    public TransferenciasView transferenciasView;

    public void onCreatePage(LeyendasBean leyendasBean) {
    }

    public TerminosCondicionesPresenterImp(TransferenciasView transferenciasView2) {
        this.transferenciasView = transferenciasView2;
    }
}
