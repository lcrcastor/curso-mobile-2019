package ar.com.santander.rio.mbanking.services.events;

import ar.com.santander.rio.mbanking.services.soap.beans.body.GetListaTjWomenResponseBean;

public class GetListaTjWomenEvent extends WebServiceEvent {
    private GetListaTjWomenResponseBean a;
    private boolean b = false;

    public GetListaTjWomenResponseBean getResponse() {
        return this.a;
    }

    public void setResponse(GetListaTjWomenResponseBean getListaTjWomenResponseBean) {
        this.a = getListaTjWomenResponseBean;
    }

    public boolean isUpdate() {
        return this.b;
    }

    public void setUpdate(boolean z) {
        this.b = z;
    }
}
