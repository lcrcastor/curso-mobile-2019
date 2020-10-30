package ar.com.santander.rio.mbanking.services.events;

import ar.com.santander.rio.mbanking.services.soap.beans.CnsTasasPFTResponseBean;

public class CnsTasasPFTEvent extends WebServiceEvent {
    private CnsTasasPFTResponseBean a;

    public CnsTasasPFTResponseBean getResponse() {
        return this.a;
    }

    public void setResponse(CnsTasasPFTResponseBean cnsTasasPFTResponseBean) {
        this.a = cnsTasasPFTResponseBean;
    }
}
