package ar.com.santander.rio.mbanking.services.events;

import ar.com.santander.rio.mbanking.services.model.general.Tarjeta;
import ar.com.santander.rio.mbanking.services.soap.beans.LimitesYDisponiblesTCResponseBean;

public class LimitesYDisponiblesTCEvent extends WebServiceEvent {
    private LimitesYDisponiblesTCResponseBean a;
    private Tarjeta b;

    public Tarjeta getTarjetaSeleccionada() {
        return this.b;
    }

    public void setTarjetaSeleccionada(Tarjeta tarjeta) {
        this.b = tarjeta;
    }

    public LimitesYDisponiblesTCResponseBean getResponse() {
        return this.a;
    }

    public void setResponse(LimitesYDisponiblesTCResponseBean limitesYDisponiblesTCResponseBean) {
        this.a = limitesYDisponiblesTCResponseBean;
    }
}
