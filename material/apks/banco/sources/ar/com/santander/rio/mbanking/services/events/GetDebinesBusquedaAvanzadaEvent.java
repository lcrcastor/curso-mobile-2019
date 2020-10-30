package ar.com.santander.rio.mbanking.services.events;

import ar.com.santander.rio.mbanking.services.soap.beans.GetDebinesResponseBean;

public class GetDebinesBusquedaAvanzadaEvent extends WebServiceEvent {
    private GetDebinesResponseBean a;

    public GetDebinesResponseBean getResponse() {
        return this.a;
    }

    public void setResponse(GetDebinesResponseBean getDebinesResponseBean) {
        this.a = getDebinesResponseBean;
    }
}
