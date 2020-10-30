package ar.com.santander.rio.mbanking.services.events;

import ar.com.santander.rio.mbanking.services.soap.beans.body.GetCajerosBodyResponseBean;

public class GetCajerosEvent extends WebServiceEvent {
    private GetCajerosBodyResponseBean a;

    public GetCajerosBodyResponseBean getResponse() {
        return this.a;
    }

    public void setResponse(GetCajerosBodyResponseBean getCajerosBodyResponseBean) {
        this.a = getCajerosBodyResponseBean;
    }
}
