package ar.com.santander.rio.mbanking.services.events;

import ar.com.santander.rio.mbanking.services.soap.beans.GetFirmaCredinResponseBean;

public class GetFirmaCredinEvent extends WebServiceEvent {
    private GetFirmaCredinResponseBean a;

    public GetFirmaCredinResponseBean getResponseBean() {
        return this.a;
    }

    public void setResponse(GetFirmaCredinResponseBean getFirmaCredinResponseBean) {
        this.a = getFirmaCredinResponseBean;
    }
}
