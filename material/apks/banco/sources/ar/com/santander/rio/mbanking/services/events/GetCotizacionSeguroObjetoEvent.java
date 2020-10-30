package ar.com.santander.rio.mbanking.services.events;

import ar.com.santander.rio.mbanking.services.soap.beans.body.GetCotizacionSeguroObjetoResponseBean;

public class GetCotizacionSeguroObjetoEvent extends WebServiceEvent {
    private GetCotizacionSeguroObjetoResponseBean a;

    public GetCotizacionSeguroObjetoResponseBean getGetCotizacionSeguroObjetoResponseBean() {
        return this.a;
    }

    public void setGetCotizacionSeguroObjetoResponseBean(GetCotizacionSeguroObjetoResponseBean getCotizacionSeguroObjetoResponseBean) {
        this.a = getCotizacionSeguroObjetoResponseBean;
    }
}
