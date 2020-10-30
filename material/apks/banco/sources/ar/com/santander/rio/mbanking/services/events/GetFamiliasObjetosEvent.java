package ar.com.santander.rio.mbanking.services.events;

import ar.com.santander.rio.mbanking.services.soap.beans.body.GetFamiliasObjetosResponseBean;

public class GetFamiliasObjetosEvent extends WebServiceEvent {
    private GetFamiliasObjetosResponseBean a;

    public GetFamiliasObjetosResponseBean getResponse() {
        return this.a;
    }

    public void setResponse(GetFamiliasObjetosResponseBean getFamiliasObjetosResponseBean) {
        this.a = getFamiliasObjetosResponseBean;
    }
}
