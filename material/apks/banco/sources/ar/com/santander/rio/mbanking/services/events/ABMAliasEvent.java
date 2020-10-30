package ar.com.santander.rio.mbanking.services.events;

import ar.com.santander.rio.mbanking.services.soap.beans.ABMAliasResponseBean;

public class ABMAliasEvent extends WebServiceEvent {
    private ABMAliasResponseBean a;

    public ABMAliasResponseBean getResponse() {
        return this.a;
    }

    public void setResponse(ABMAliasResponseBean aBMAliasResponseBean) {
        this.a = aBMAliasResponseBean;
    }
}
