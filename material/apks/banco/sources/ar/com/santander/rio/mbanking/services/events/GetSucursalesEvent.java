package ar.com.santander.rio.mbanking.services.events;

import ar.com.santander.rio.mbanking.services.soap.beans.GetSucursalesResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetSucursalesBodyResponseBean;

public class GetSucursalesEvent extends WebServiceEvent {
    private GetSucursalesBodyResponseBean a;
    private GetSucursalesResponseBean b;

    public GetSucursalesBodyResponseBean getResponse() {
        return this.a;
    }

    public void setResponse(GetSucursalesBodyResponseBean getSucursalesBodyResponseBean) {
        this.a = getSucursalesBodyResponseBean;
    }

    public GetSucursalesResponseBean getResponseBean() {
        return this.b;
    }

    public void setResponseBean(GetSucursalesResponseBean getSucursalesResponseBean) {
        this.b = getSucursalesResponseBean;
    }
}
