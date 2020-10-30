package ar.com.santander.rio.mbanking.services.events;

import ar.com.santander.rio.mbanking.services.soap.beans.body.GetCircuitoTurnoResponseBean;

public class GetCircuitoTurnoEvent extends WebServiceEvent {
    private GetCircuitoTurnoResponseBean a;

    public GetCircuitoTurnoResponseBean getResponse() {
        return this.a;
    }

    public void setResponse(GetCircuitoTurnoResponseBean getCircuitoTurnoResponseBean) {
        this.a = getCircuitoTurnoResponseBean;
    }
}
