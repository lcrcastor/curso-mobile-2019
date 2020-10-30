package ar.com.santander.rio.mbanking.services.events;

import ar.com.santander.rio.mbanking.services.soap.beans.ABMViajesResponseBean;

public class ABMViajesEvent extends WebServiceEvent {
    private ABMViajesResponseBean a;

    public ABMViajesResponseBean getResponse() {
        return this.a;
    }

    public void setResponse(ABMViajesResponseBean aBMViajesResponseBean) {
        this.a = aBMViajesResponseBean;
    }
}
