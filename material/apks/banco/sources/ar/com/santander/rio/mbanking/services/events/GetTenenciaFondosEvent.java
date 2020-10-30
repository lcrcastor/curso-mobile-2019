package ar.com.santander.rio.mbanking.services.events;

import ar.com.santander.rio.mbanking.services.soap.beans.GetTenenciaFondosResponseBean;

public class GetTenenciaFondosEvent extends WebServiceEvent {
    private GetTenenciaFondosResponseBean a;

    public GetTenenciaFondosResponseBean getResponseBean() {
        return this.a;
    }

    public void setResponseBean(GetTenenciaFondosResponseBean getTenenciaFondosResponseBean) {
        this.a = getTenenciaFondosResponseBean;
    }
}
