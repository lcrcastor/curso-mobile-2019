package ar.com.santander.rio.mbanking.services.events;

import ar.com.santander.rio.mbanking.services.soap.beans.SuscribirFondoResponseBean;

public class SuscribirFondoEvent extends WebServiceEvent {
    private SuscribirFondoResponseBean a;

    public SuscribirFondoResponseBean getResponseBean() {
        return this.a;
    }

    public void setResponseBean(SuscribirFondoResponseBean suscribirFondoResponseBean) {
        this.a = suscribirFondoResponseBean;
    }
}
