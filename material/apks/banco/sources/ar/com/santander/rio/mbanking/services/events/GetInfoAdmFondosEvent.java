package ar.com.santander.rio.mbanking.services.events;

import ar.com.santander.rio.mbanking.services.soap.beans.GetInfoAdmFondosResponseBean;

public class GetInfoAdmFondosEvent extends WebServiceEvent {
    private GetInfoAdmFondosResponseBean a;

    public GetInfoAdmFondosResponseBean getResponseBean() {
        return this.a;
    }

    public void setResponseBean(GetInfoAdmFondosResponseBean getInfoAdmFondosResponseBean) {
        this.a = getInfoAdmFondosResponseBean;
    }
}
