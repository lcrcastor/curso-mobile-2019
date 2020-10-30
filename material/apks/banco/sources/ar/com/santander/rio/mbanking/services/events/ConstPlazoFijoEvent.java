package ar.com.santander.rio.mbanking.services.events;

import ar.com.santander.rio.mbanking.services.soap.beans.ConstPlazoFijoResponseBean;

public class ConstPlazoFijoEvent extends WebServiceEvent {
    private ConstPlazoFijoResponseBean a;

    public ConstPlazoFijoResponseBean getResponse() {
        return this.a;
    }

    public void setResponse(ConstPlazoFijoResponseBean constPlazoFijoResponseBean) {
        this.a = constPlazoFijoResponseBean;
    }
}
