package ar.com.santander.rio.mbanking.services.events;

import ar.com.santander.rio.mbanking.services.soap.beans.RecargaCelularesResponseBean;

public class RecargaCelularesEvent extends WebServiceEvent {
    private RecargaCelularesResponseBean a;

    public RecargaCelularesResponseBean getResponse() {
        return this.a;
    }

    public void setResponse(RecargaCelularesResponseBean recargaCelularesResponseBean) {
        this.a = recargaCelularesResponseBean;
    }
}
