package ar.com.santander.rio.mbanking.services.events;

import ar.com.santander.rio.mbanking.services.soap.beans.GetCuponesSuperClubResponseBean;

public abstract class GetCuponesSuperClubEvent extends WebServiceEvent {
    GetCuponesSuperClubResponseBean a;

    public GetCuponesSuperClubResponseBean getResponse() {
        return this.a;
    }

    public void setResponse(GetCuponesSuperClubResponseBean getCuponesSuperClubResponseBean) {
        this.a = getCuponesSuperClubResponseBean;
    }
}
