package ar.com.santander.rio.mbanking.services.events;

import ar.com.santander.rio.mbanking.services.soap.beans.GetNumerosUtilesResponseBean;

public class GetNumerosUtilesEvent extends WebServiceEvent {
    GetNumerosUtilesResponseBean a;

    public GetNumerosUtilesResponseBean getResponse() {
        return this.a;
    }

    public void setResponse(GetNumerosUtilesResponseBean getNumerosUtilesResponseBean) {
        this.a = getNumerosUtilesResponseBean;
    }
}
