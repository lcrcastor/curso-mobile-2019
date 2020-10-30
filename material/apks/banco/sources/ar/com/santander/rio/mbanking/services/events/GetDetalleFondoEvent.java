package ar.com.santander.rio.mbanking.services.events;

import ar.com.santander.rio.mbanking.services.soap.beans.GetDetalleFondoResponseBean;

public class GetDetalleFondoEvent extends WebServiceEvent {
    private GetDetalleFondoResponseBean a;

    public GetDetalleFondoResponseBean getResponseBean() {
        return this.a;
    }

    public void setResponseBean(GetDetalleFondoResponseBean getDetalleFondoResponseBean) {
        this.a = getDetalleFondoResponseBean;
    }
}
