package ar.com.santander.rio.mbanking.services.events;

import ar.com.santander.rio.mbanking.services.soap.beans.GetFondosResponseBean;

public class GetFondosEvent extends WebServiceEvent {
    private GetFondosResponseBean a;

    public GetFondosResponseBean getResponseBean() {
        return this.a;
    }

    public void setResponseBean(GetFondosResponseBean getFondosResponseBean) {
        this.a = getFondosResponseBean;
    }
}
