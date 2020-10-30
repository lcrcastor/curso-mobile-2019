package ar.com.santander.rio.mbanking.services.events;

import ar.com.santander.rio.mbanking.services.soap.beans.StopDebitResponseBean;

public class StopDebitEvent extends WebServiceEvent {
    private StopDebitResponseBean a;

    public StopDebitResponseBean getStopDebitResponseBean() {
        return this.a;
    }

    public void setStopDebitResponseBean(StopDebitResponseBean stopDebitResponseBean) {
        this.a = stopDebitResponseBean;
    }
}
