package ar.com.santander.rio.mbanking.services.events;

import ar.com.santander.rio.mbanking.services.soap.beans.GetViajesResponseBean;

public class GetViajesEvent extends WebServiceEvent {
    private GetViajesResponseBean a;

    public GetViajesResponseBean getResponse() {
        return this.a;
    }

    public void setResponse(GetViajesResponseBean getViajesResponseBean) {
        this.a = getViajesResponseBean;
    }
}
