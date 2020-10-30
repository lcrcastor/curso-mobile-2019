package ar.com.santander.rio.mbanking.services.events;

import ar.com.santander.rio.mbanking.services.soap.beans.CnsDeudaRecargaResponseBean;

public class CnsDeudaRecargaEvent extends WebServiceEvent {
    private CnsDeudaRecargaResponseBean a;

    public CnsDeudaRecargaResponseBean getResponse() {
        return this.a;
    }

    public void setResponse(CnsDeudaRecargaResponseBean cnsDeudaRecargaResponseBean) {
        this.a = cnsDeudaRecargaResponseBean;
    }
}
