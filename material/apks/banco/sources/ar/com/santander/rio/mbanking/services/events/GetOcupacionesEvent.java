package ar.com.santander.rio.mbanking.services.events;

import ar.com.santander.rio.mbanking.services.soap.beans.GetOcupacionesResponseBean;

public class GetOcupacionesEvent extends WebServiceEvent {
    private GetOcupacionesResponseBean a;

    public GetOcupacionesResponseBean getResponse() {
        return this.a;
    }

    public void setResponse(GetOcupacionesResponseBean getOcupacionesResponseBean) {
        this.a = getOcupacionesResponseBean;
    }
}
