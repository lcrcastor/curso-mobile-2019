package ar.com.santander.rio.mbanking.services.events;

import ar.com.santander.rio.mbanking.services.soap.beans.TransferenciasResponseBean;

public class TransferenciasEvent extends WebServiceEvent {
    private TransferenciasResponseBean a;

    public TransferenciasResponseBean getResponse() {
        return this.a;
    }

    public void setResponse(TransferenciasResponseBean transferenciasResponseBean) {
        this.a = transferenciasResponseBean;
    }
}
