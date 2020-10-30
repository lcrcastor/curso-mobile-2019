package ar.com.santander.rio.mbanking.services.events;

import ar.com.santander.rio.mbanking.services.soap.beans.PromocionDetalleResponseBean;

public class PromocionDetalleEvent extends WebServiceEvent {
    private PromocionDetalleResponseBean a;

    public PromocionDetalleResponseBean getResponse() {
        return this.a;
    }

    public void setResponse(PromocionDetalleResponseBean promocionDetalleResponseBean) {
        this.a = promocionDetalleResponseBean;
    }
}
