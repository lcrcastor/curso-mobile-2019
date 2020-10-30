package ar.com.santander.rio.mbanking.app.module.creditCards;

import ar.com.santander.rio.mbanking.services.model.general.Tarjeta;
import ar.com.santander.rio.mbanking.services.soap.beans.body.UltimosConsumosTCBodyRequestBean;

public class TarjetasUltimosConsumosPresenterImp implements TarjetasUltimosConsumosPresenter {
    private TarjetasView a;

    public TarjetasUltimosConsumosPresenterImp(TarjetasView tarjetasView) {
        this.a = tarjetasView;
    }

    public void onCreatePage() {
        this.a.setTarjetasUltimosConsumosView();
    }

    public void sendRequestUltimosConsumosTC(UltimosConsumosTCBodyRequestBean ultimosConsumosTCBodyRequestBean, Tarjeta tarjeta) {
        this.a.getDataManager().ultimosConsumosTC(ultimosConsumosTCBodyRequestBean, tarjeta);
    }
}
