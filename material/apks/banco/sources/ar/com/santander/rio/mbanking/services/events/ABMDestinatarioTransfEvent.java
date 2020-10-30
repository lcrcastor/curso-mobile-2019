package ar.com.santander.rio.mbanking.services.events;

import ar.com.santander.rio.mbanking.services.soap.beans.ABMDestinatarioTransfResponseBean;

public class ABMDestinatarioTransfEvent extends WebServiceEvent {
    private ABMDestinatarioTransfResponseBean a;

    public ABMDestinatarioTransfResponseBean getResponse() {
        return this.a;
    }

    public void setResponse(ABMDestinatarioTransfResponseBean aBMDestinatarioTransfResponseBean) {
        this.a = aBMDestinatarioTransfResponseBean;
    }
}
