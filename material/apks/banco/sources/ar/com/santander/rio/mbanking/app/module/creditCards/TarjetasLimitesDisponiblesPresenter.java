package ar.com.santander.rio.mbanking.app.module.creditCards;

import ar.com.santander.rio.mbanking.services.model.general.Tarjeta;
import ar.com.santander.rio.mbanking.services.soap.beans.body.LimitesYDisponiblesTCBodyRequestBean;

public interface TarjetasLimitesDisponiblesPresenter {
    void onCreatePage();

    void sendRequestGetViajesMarcacion();

    void sendRequestLimitesYDisponiblesTC(LimitesYDisponiblesTCBodyRequestBean limitesYDisponiblesTCBodyRequestBean, Tarjeta tarjeta);
}
