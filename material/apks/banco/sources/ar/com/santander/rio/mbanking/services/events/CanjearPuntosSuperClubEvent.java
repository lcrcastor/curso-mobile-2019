package ar.com.santander.rio.mbanking.services.events;

import ar.com.santander.rio.mbanking.services.soap.beans.CanjearPuntosSuperClubResponseBean;

public class CanjearPuntosSuperClubEvent extends WebServiceEvent {
    CanjearPuntosSuperClubResponseBean a;

    public CanjearPuntosSuperClubResponseBean getResponse() {
        return this.a;
    }

    public void setResponse(CanjearPuntosSuperClubResponseBean canjearPuntosSuperClubResponseBean) {
        this.a = canjearPuntosSuperClubResponseBean;
    }
}
