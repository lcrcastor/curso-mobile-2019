package ar.com.santander.rio.mbanking.services.events;

import ar.com.santander.rio.mbanking.services.soap.beans.ConfirmarPagoResponseBean;

public class ConfirmarPagoEvent extends WebServiceEvent {
    ConfirmarPagoResponseBean a;

    public ConfirmarPagoResponseBean getResponse() {
        return this.a;
    }

    public void setResponse(ConfirmarPagoResponseBean confirmarPagoResponseBean) {
        this.a = confirmarPagoResponseBean;
    }
}
