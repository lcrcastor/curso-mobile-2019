package ar.com.santander.rio.mbanking.services.events;

import ar.com.santander.rio.mbanking.services.soap.beans.PagoServiciosResponseBean;

public class PagoServiciosEvent extends WebServiceEvent {
    private PagoServiciosResponseBean a;

    public PagoServiciosResponseBean getResponse() {
        return this.a;
    }

    public void setResponse(PagoServiciosResponseBean pagoServiciosResponseBean) {
        this.a = pagoServiciosResponseBean;
    }
}
