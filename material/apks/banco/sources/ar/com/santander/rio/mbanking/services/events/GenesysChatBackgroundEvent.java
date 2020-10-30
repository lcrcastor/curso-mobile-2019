package ar.com.santander.rio.mbanking.services.events;

import ar.com.santander.rio.mbanking.services.soap.beans.GenesysChatResponseBean;

public class GenesysChatBackgroundEvent extends WebServiceEvent {
    private GenesysChatResponseBean a;

    public GenesysChatResponseBean getResponse() {
        return this.a;
    }

    public void setResponse(GenesysChatResponseBean genesysChatResponseBean) {
        this.a = genesysChatResponseBean;
    }
}
