package ar.com.santander.rio.mbanking.services.events;

import ar.com.santander.rio.mbanking.services.soap.beans.body.GetCajerosDetalleBodyResponseBean;

public class GetCajerosDetalleEvent extends WebServiceEvent {
    private GetCajerosDetalleBodyResponseBean a;

    public GetCajerosDetalleBodyResponseBean getResponse() {
        return this.a;
    }

    public void setResponse(GetCajerosDetalleBodyResponseBean getCajerosDetalleBodyResponseBean) {
        this.a = getCajerosDetalleBodyResponseBean;
    }
}
