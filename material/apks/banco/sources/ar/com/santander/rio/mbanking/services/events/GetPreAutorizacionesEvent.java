package ar.com.santander.rio.mbanking.services.events;

import ar.com.santander.rio.mbanking.services.soap.beans.body.GetPreAutorizacionesResponseBean;

public class GetPreAutorizacionesEvent extends WebServiceEvent {
    private GetPreAutorizacionesResponseBean a;
    private boolean b = false;

    public GetPreAutorizacionesResponseBean getResponse() {
        return this.a;
    }

    public void setResponse(GetPreAutorizacionesResponseBean getPreAutorizacionesResponseBean) {
        this.a = getPreAutorizacionesResponseBean;
    }

    public boolean isUpdate() {
        return this.b;
    }

    public void setUpdate(boolean z) {
        this.b = z;
    }
}
