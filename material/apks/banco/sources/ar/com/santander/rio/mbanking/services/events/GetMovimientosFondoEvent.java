package ar.com.santander.rio.mbanking.services.events;

import ar.com.santander.rio.mbanking.services.soap.beans.GetMovimientosFondoResponseBean;

public class GetMovimientosFondoEvent extends WebServiceEvent {
    private GetMovimientosFondoResponseBean a;

    public GetMovimientosFondoResponseBean getResponseBean() {
        return this.a;
    }

    public void setResponseBean(GetMovimientosFondoResponseBean getMovimientosFondoResponseBean) {
        this.a = getMovimientosFondoResponseBean;
    }
}
