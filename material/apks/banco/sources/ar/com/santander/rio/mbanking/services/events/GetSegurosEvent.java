package ar.com.santander.rio.mbanking.services.events;

import ar.com.santander.rio.mbanking.services.soap.beans.GetSegurosResponseBean;

public class GetSegurosEvent extends WebServiceEvent {
    private GetSegurosResponseBean a;

    public GetSegurosResponseBean getResponse() {
        return this.a;
    }

    public void setResponse(GetSegurosResponseBean getSegurosResponseBean) {
        this.a = getSegurosResponseBean;
    }
}
