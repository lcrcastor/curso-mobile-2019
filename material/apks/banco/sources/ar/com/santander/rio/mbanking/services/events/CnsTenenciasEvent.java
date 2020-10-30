package ar.com.santander.rio.mbanking.services.events;

import ar.com.santander.rio.mbanking.services.soap.beans.CnsTenenciasResponseBean;

public class CnsTenenciasEvent extends WebServiceEvent {
    private CnsTenenciasResponseBean a;

    public CnsTenenciasResponseBean getResponse() {
        return this.a;
    }

    public void setResponse(CnsTenenciasResponseBean cnsTenenciasResponseBean) {
        this.a = cnsTenenciasResponseBean;
    }
}
