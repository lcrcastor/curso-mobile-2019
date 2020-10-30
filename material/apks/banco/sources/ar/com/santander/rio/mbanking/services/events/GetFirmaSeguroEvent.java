package ar.com.santander.rio.mbanking.services.events;

import ar.com.santander.rio.mbanking.services.soap.beans.GetFirmaSeguroResponseBean;

public class GetFirmaSeguroEvent extends WebServiceEvent {
    private GetFirmaSeguroResponseBean a;

    public GetFirmaSeguroResponseBean getResponse() {
        return this.a;
    }

    public void setResponse(GetFirmaSeguroResponseBean getFirmaSeguroResponseBean) {
        this.a = getFirmaSeguroResponseBean;
    }
}
