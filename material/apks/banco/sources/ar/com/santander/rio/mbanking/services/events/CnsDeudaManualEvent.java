package ar.com.santander.rio.mbanking.services.events;

import ar.com.santander.rio.mbanking.services.soap.beans.CnsDeudaManualResponseBean;

public class CnsDeudaManualEvent extends WebServiceEvent {
    private CnsDeudaManualResponseBean a;

    public CnsDeudaManualResponseBean getResponse() {
        return this.a;
    }

    public void setResponse(CnsDeudaManualResponseBean cnsDeudaManualResponseBean) {
        this.a = cnsDeudaManualResponseBean;
    }
}
