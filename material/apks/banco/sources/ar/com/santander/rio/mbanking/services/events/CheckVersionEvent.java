package ar.com.santander.rio.mbanking.services.events;

import ar.com.santander.rio.mbanking.services.soap.beans.CheckVersionResponseBean;

public class CheckVersionEvent extends WebServiceEvent {
    public CheckVersionResponseBean response;

    public CheckVersionResponseBean getResponse() {
        return this.response;
    }

    public void setResponse(CheckVersionResponseBean checkVersionResponseBean) {
        this.response = checkVersionResponseBean;
    }
}
