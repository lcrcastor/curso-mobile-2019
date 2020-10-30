package ar.com.santander.rio.mbanking.app.module.creditCards;

import ar.com.santander.rio.mbanking.services.soap.beans.body.UltimoResumenTCBodyRequestBean;

public class TarjetasUltimoResumenPresenterImp implements TarjetasUltimoResumenPresenter {
    private TarjetasView a;

    public TarjetasUltimoResumenPresenterImp(TarjetasView tarjetasView) {
        this.a = tarjetasView;
    }

    public void onCreatePage() {
        this.a.setTarjetasUltimoResumenView();
    }

    public void sendRequestUltimoResumenTC(UltimoResumenTCBodyRequestBean ultimoResumenTCBodyRequestBean) {
        this.a.getDataManager().ultimoResumenTC(ultimoResumenTCBodyRequestBean);
    }
}
