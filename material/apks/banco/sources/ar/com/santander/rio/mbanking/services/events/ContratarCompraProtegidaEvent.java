package ar.com.santander.rio.mbanking.services.events;

import ar.com.santander.rio.mbanking.services.soap.beans.ContratarCompraProtegidaResponseBean;

public class ContratarCompraProtegidaEvent extends WebServiceEvent {
    ContratarCompraProtegidaResponseBean a;

    public ContratarCompraProtegidaResponseBean getResponse() {
        return this.a;
    }

    public void setResponse(ContratarCompraProtegidaResponseBean contratarCompraProtegidaResponseBean) {
        this.a = contratarCompraProtegidaResponseBean;
    }
}
