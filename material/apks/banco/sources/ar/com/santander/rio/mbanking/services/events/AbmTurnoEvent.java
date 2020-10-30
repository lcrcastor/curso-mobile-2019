package ar.com.santander.rio.mbanking.services.events;

import ar.com.santander.rio.mbanking.services.soap.beans.AbmTurnoResponseBean;

public class AbmTurnoEvent extends WebServiceEvent {
    private AbmTurnoResponseBean a;

    public AbmTurnoResponseBean getResponse() {
        return this.a;
    }

    public void setResponse(AbmTurnoResponseBean abmTurnoResponseBean) {
        this.a = abmTurnoResponseBean;
    }
}
