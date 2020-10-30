package ar.com.santander.rio.mbanking.services.events;

import ar.com.santander.rio.mbanking.services.soap.beans.SimularSuscripcionFondoResponseBean;

public class SimularSuscripcionFondoEvent extends WebServiceEvent {
    private SimularSuscripcionFondoResponseBean a;

    public SimularSuscripcionFondoResponseBean getResponseBean() {
        return this.a;
    }

    public void setResponseBean(SimularSuscripcionFondoResponseBean simularSuscripcionFondoResponseBean) {
        this.a = simularSuscripcionFondoResponseBean;
    }
}
