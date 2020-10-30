package ar.com.santander.rio.mbanking.services.events;

import ar.com.santander.rio.mbanking.services.soap.beans.SuscripcionWomenResponseBean;

public class SuscripcionWomenEvent extends WebServiceEvent {
    private SuscripcionWomenResponseBean a;

    public SuscripcionWomenResponseBean getResponse() {
        return this.a;
    }

    public void setResponse(SuscripcionWomenResponseBean suscripcionWomenResponseBean) {
        this.a = suscripcionWomenResponseBean;
    }
}
