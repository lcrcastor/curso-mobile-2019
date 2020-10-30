package ar.com.santander.rio.mbanking.services.events;

import ar.com.santander.rio.mbanking.services.soap.beans.GetRecargasResponseBean;

public class GetRecargasEvent extends WebServiceEvent {
    private GetRecargasResponseBean a;

    public GetRecargasResponseBean getResponse() {
        return this.a;
    }

    public void setResponse(GetRecargasResponseBean getRecargasResponseBean) {
        this.a = getRecargasResponseBean;
    }
}
