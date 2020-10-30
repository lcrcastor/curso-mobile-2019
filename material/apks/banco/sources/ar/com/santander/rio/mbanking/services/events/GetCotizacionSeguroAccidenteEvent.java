package ar.com.santander.rio.mbanking.services.events;

import ar.com.santander.rio.mbanking.services.soap.beans.body.GetCotizacionSeguroAccidenteResponseBean;

public class GetCotizacionSeguroAccidenteEvent extends WebServiceEvent {
    private GetCotizacionSeguroAccidenteResponseBean a;

    public GetCotizacionSeguroAccidenteResponseBean getResponse() {
        return this.a;
    }

    public void setResponse(GetCotizacionSeguroAccidenteResponseBean getCotizacionSeguroAccidenteResponseBean) {
        this.a = getCotizacionSeguroAccidenteResponseBean;
    }
}
