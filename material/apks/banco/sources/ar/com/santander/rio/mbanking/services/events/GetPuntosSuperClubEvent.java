package ar.com.santander.rio.mbanking.services.events;

import ar.com.santander.rio.mbanking.services.soap.beans.GetPuntosSuperClubResponseBean;

public class GetPuntosSuperClubEvent extends WebServiceEvent {
    GetPuntosSuperClubResponseBean a;

    public GetPuntosSuperClubResponseBean getResponse() {
        return this.a;
    }

    public void setResponse(GetPuntosSuperClubResponseBean getPuntosSuperClubResponseBean) {
        this.a = getPuntosSuperClubResponseBean;
    }
}
