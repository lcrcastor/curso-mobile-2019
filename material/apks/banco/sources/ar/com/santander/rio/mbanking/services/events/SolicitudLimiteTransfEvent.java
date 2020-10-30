package ar.com.santander.rio.mbanking.services.events;

import ar.com.santander.rio.mbanking.services.soap.beans.SolicitudLimiteTransfResponseBean;

public class SolicitudLimiteTransfEvent extends WebServiceEvent {
    private SolicitudLimiteTransfResponseBean a;

    public SolicitudLimiteTransfResponseBean getResponse() {
        return this.a;
    }

    public void setResponse(SolicitudLimiteTransfResponseBean solicitudLimiteTransfResponseBean) {
        this.a = solicitudLimiteTransfResponseBean;
    }
}
