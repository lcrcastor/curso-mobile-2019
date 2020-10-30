package ar.com.santander.rio.mbanking.services.events;

import ar.com.santander.rio.mbanking.services.soap.beans.RescatarFondoResponseBean;

public class RescatarFondoEvent extends WebServiceEvent {
    private RescatarFondoResponseBean a;

    public RescatarFondoResponseBean getResponseBean() {
        return this.a;
    }

    public void setResponseBean(RescatarFondoResponseBean rescatarFondoResponseBean) {
        this.a = rescatarFondoResponseBean;
    }
}
