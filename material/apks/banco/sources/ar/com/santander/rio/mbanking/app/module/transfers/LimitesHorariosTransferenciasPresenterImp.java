package ar.com.santander.rio.mbanking.app.module.transfers;

import ar.com.santander.rio.mbanking.services.soap.beans.body.LeyendasBean;

public class LimitesHorariosTransferenciasPresenterImp implements LimitesHorariosTransferenciasPresenter {
    public TransferenciasView transferenciasView;

    public void onCreatePage(LeyendasBean leyendasBean) {
    }

    public LimitesHorariosTransferenciasPresenterImp(TransferenciasView transferenciasView2) {
        this.transferenciasView = transferenciasView2;
    }
}
