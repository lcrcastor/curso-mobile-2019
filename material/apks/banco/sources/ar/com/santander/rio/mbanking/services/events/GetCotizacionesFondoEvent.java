package ar.com.santander.rio.mbanking.services.events;

import ar.com.santander.rio.mbanking.services.soap.beans.GetCotizacionesResponseBean;

public class GetCotizacionesFondoEvent extends WebServiceEvent {
    private GetCotizacionesResponseBean a;

    public GetCotizacionesResponseBean getResponseBean() {
        return this.a;
    }

    public void setResponseBean(GetCotizacionesResponseBean getCotizacionesResponseBean) {
        this.a = getCotizacionesResponseBean;
    }
}
