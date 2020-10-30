package ar.com.santander.rio.mbanking.app.module.creditCards;

import ar.com.santander.rio.mbanking.services.model.general.Tarjeta;
import ar.com.santander.rio.mbanking.services.soap.beans.body.UltimosConsumosTCBodyRequestBean;

public interface TarjetasUltimosConsumosPresenter {
    void onCreatePage();

    void sendRequestUltimosConsumosTC(UltimosConsumosTCBodyRequestBean ultimosConsumosTCBodyRequestBean, Tarjeta tarjeta);
}
