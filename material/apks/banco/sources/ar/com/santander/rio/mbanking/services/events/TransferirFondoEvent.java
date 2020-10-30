package ar.com.santander.rio.mbanking.services.events;

import ar.com.santander.rio.mbanking.services.soap.beans.TransferirFondoResponseBean;

public class TransferirFondoEvent extends WebServiceEvent {
    private TransferirFondoResponseBean a;

    public TransferirFondoResponseBean getResponseBean() {
        return this.a;
    }

    public void setResponseBean(TransferirFondoResponseBean transferirFondoResponseBean) {
        this.a = transferirFondoResponseBean;
    }
}
