package ar.com.santander.rio.mbanking.services.events;

import ar.com.santander.rio.mbanking.services.soap.beans.GetCotizacionSeguroMovilResponseBean;

public class GetCotizacionSeguroMovilEvent extends WebServiceEvent {
    private GetCotizacionSeguroMovilResponseBean a;

    public GetCotizacionSeguroMovilResponseBean getResponse() {
        return this.a;
    }

    public void setResponse(GetCotizacionSeguroMovilResponseBean getCotizacionSeguroMovilResponseBean) {
        this.a = getCotizacionSeguroMovilResponseBean;
    }
}
