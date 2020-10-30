package ar.com.santander.rio.mbanking.services.events;

import ar.com.santander.rio.mbanking.services.soap.beans.GetCotizacionCompraProtegidaResponseBean;

public class GetCotizacionCompraProtegidaEvent extends WebServiceEvent {
    private GetCotizacionCompraProtegidaResponseBean a;

    public GetCotizacionCompraProtegidaResponseBean getResponse() {
        return this.a;
    }

    public void setResponse(GetCotizacionCompraProtegidaResponseBean getCotizacionCompraProtegidaResponseBean) {
        this.a = getCotizacionCompraProtegidaResponseBean;
    }
}
