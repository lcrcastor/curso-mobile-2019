package ar.com.santander.rio.mbanking.services.events;

import ar.com.santander.rio.mbanking.services.soap.beans.body.GetPromocionesBodyResponseBean;

public class GetPromocionesEvent extends WebServiceEvent {
    private GetPromocionesBodyResponseBean a;

    public GetPromocionesBodyResponseBean getResponse() {
        return this.a;
    }

    public void setResponse(GetPromocionesBodyResponseBean getPromocionesBodyResponseBean) {
        this.a = getPromocionesBodyResponseBean;
    }
}
