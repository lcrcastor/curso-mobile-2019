package ar.com.santander.rio.mbanking.services.events;

import ar.com.santander.rio.mbanking.services.soap.beans.CnsDeudaResponseBean;

public class CnsDeudaEvent extends WebServiceEvent {
    private CnsDeudaResponseBean a;

    public CnsDeudaResponseBean getResponse() {
        return this.a;
    }

    public void setResponse(CnsDeudaResponseBean cnsDeudaResponseBean) {
        this.a = cnsDeudaResponseBean;
    }
}
