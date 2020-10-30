package ar.com.santander.rio.mbanking.services.events;

import ar.com.santander.rio.mbanking.services.soap.beans.GetPolizaResponseBean;

public class GetPolizaEvent extends WebServiceEvent {
    private GetPolizaResponseBean a;

    public GetPolizaResponseBean getResponse() {
        return this.a;
    }

    public void setResponse(GetPolizaResponseBean getPolizaResponseBean) {
        this.a = getPolizaResponseBean;
    }
}
