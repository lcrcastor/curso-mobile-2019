package ar.com.santander.rio.mbanking.services.events;

import ar.com.santander.rio.mbanking.services.soap.beans.SimularPagosResponseBean;

public class SimularPagoCuotaEvent extends WebServiceEvent {
    SimularPagosResponseBean a;

    public SimularPagosResponseBean getResponse() {
        return this.a;
    }

    public void setResponse(SimularPagosResponseBean simularPagosResponseBean) {
        this.a = simularPagosResponseBean;
    }
}
