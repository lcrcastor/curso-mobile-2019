package ar.com.santander.rio.mbanking.app.module.creditCards;

import ar.com.santander.rio.mbanking.services.events.WebServiceEvent.TypeOption;
import ar.com.santander.rio.mbanking.services.model.general.Tarjeta;
import ar.com.santander.rio.mbanking.services.soap.beans.body.LimitesYDisponiblesTCBodyRequestBean;

public class TarjetasLimitesDisponiblesPresenterImp implements TarjetasLimitesDisponiblesPresenter {
    private TarjetasView a;

    public TarjetasLimitesDisponiblesPresenterImp(TarjetasView tarjetasView) {
        this.a = tarjetasView;
    }

    public void onCreatePage() {
        this.a.setTarjetasLimitesDisponiblesView();
    }

    public void sendRequestLimitesYDisponiblesTC(LimitesYDisponiblesTCBodyRequestBean limitesYDisponiblesTCBodyRequestBean, Tarjeta tarjeta) {
        this.a.getDataManager().limitesYDisponiblesTC(limitesYDisponiblesTCBodyRequestBean, TypeOption.INTERMDIATE_VIEW, tarjeta);
    }

    public void sendRequestGetViajesMarcacion() {
        this.a.getDataManager().getViajes();
    }
}
