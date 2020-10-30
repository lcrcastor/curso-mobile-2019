package ar.com.santander.rio.mbanking.services.events;

import ar.com.santander.rio.mbanking.services.soap.beans.SimularTransferenciaFondoResponseBean;

public class SimularTransferenciaFondoEvent extends WebServiceEvent {
    private SimularTransferenciaFondoResponseBean a;

    public SimularTransferenciaFondoResponseBean getResponseBean() {
        return this.a;
    }

    public void setResponseBean(SimularTransferenciaFondoResponseBean simularTransferenciaFondoResponseBean) {
        this.a = simularTransferenciaFondoResponseBean;
    }
}
