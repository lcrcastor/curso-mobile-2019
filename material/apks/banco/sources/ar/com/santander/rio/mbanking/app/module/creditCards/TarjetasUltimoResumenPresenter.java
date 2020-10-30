package ar.com.santander.rio.mbanking.app.module.creditCards;

import ar.com.santander.rio.mbanking.services.soap.beans.body.UltimoResumenTCBodyRequestBean;

public interface TarjetasUltimoResumenPresenter {
    void onCreatePage();

    void sendRequestUltimoResumenTC(UltimoResumenTCBodyRequestBean ultimoResumenTCBodyRequestBean);
}
