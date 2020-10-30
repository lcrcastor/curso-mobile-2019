package ar.com.santander.rio.mbanking.services.events;

import ar.com.santander.rio.mbanking.services.soap.beans.MovCtasResponseBean;

public class MovCtasEvent extends WebServiceEvent {
    private MovCtasResponseBean a;

    public MovCtasResponseBean getResponse() {
        return this.a;
    }

    public void setResponse(MovCtasResponseBean movCtasResponseBean) {
        this.a = movCtasResponseBean;
    }
}
