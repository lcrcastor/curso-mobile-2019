package ar.com.santander.rio.mbanking.services.events;

import ar.com.santander.rio.mbanking.services.soap.beans.SimulacionDolarResponseBean;

public class SimulacionDolarEvent extends WebServiceEvent {
    private SimulacionDolarResponseBean a;

    public SimulacionDolarResponseBean getResponse() {
        return this.a;
    }

    public void setResponse(SimulacionDolarResponseBean simulacionDolarResponseBean) {
        this.a = simulacionDolarResponseBean;
    }
}
