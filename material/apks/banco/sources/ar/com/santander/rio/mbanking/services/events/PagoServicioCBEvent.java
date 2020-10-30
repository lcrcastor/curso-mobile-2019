package ar.com.santander.rio.mbanking.services.events;

import ar.com.santander.rio.mbanking.services.soap.beans.PagoServicioCBResponseBean;

public class PagoServicioCBEvent extends WebServiceEvent {
    private PagoServicioCBResponseBean a;

    public PagoServicioCBResponseBean getResponse() {
        return this.a;
    }

    public void setResponse(PagoServicioCBResponseBean pagoServicioCBResponseBean) {
        this.a = pagoServicioCBResponseBean;
    }
}
