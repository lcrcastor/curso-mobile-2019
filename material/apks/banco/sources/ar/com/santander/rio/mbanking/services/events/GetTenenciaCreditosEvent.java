package ar.com.santander.rio.mbanking.services.events;

import ar.com.santander.rio.mbanking.services.soap.beans.GetTenenciaCreditosResponseBean;

public class GetTenenciaCreditosEvent extends WebServiceEvent {
    GetTenenciaCreditosResponseBean a;

    public GetTenenciaCreditosResponseBean getResponse() {
        return this.a;
    }

    public void setResponse(GetTenenciaCreditosResponseBean getTenenciaCreditosResponseBean) {
        this.a = getTenenciaCreditosResponseBean;
    }
}
