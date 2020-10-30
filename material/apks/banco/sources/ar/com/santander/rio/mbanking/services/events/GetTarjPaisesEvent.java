package ar.com.santander.rio.mbanking.services.events;

import ar.com.santander.rio.mbanking.services.soap.beans.GetTarjPaisesResponseBean;

public class GetTarjPaisesEvent extends WebServiceEvent {
    private GetTarjPaisesResponseBean a;

    public GetTarjPaisesResponseBean getResponse() {
        return this.a;
    }

    public void setResponse(GetTarjPaisesResponseBean getTarjPaisesResponseBean) {
        this.a = getTarjPaisesResponseBean;
    }
}
