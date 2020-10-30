package ar.com.santander.rio.mbanking.services.events;

import ar.com.santander.rio.mbanking.services.soap.beans.CnsDeudaCBResponseBean;

public class CnsDeudaCBEvent extends WebServiceEvent {
    CnsDeudaCBResponseBean a;

    public CnsDeudaCBResponseBean getResponse() {
        return this.a;
    }

    public void setResponse(CnsDeudaCBResponseBean cnsDeudaCBResponseBean) {
        this.a = cnsDeudaCBResponseBean;
    }
}
