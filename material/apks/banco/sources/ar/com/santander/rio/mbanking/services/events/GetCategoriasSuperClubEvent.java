package ar.com.santander.rio.mbanking.services.events;

import ar.com.santander.rio.mbanking.services.soap.beans.GetCategoriasSuperClubResponseBean;

public class GetCategoriasSuperClubEvent extends WebServiceEvent {
    GetCategoriasSuperClubResponseBean a;

    public GetCategoriasSuperClubResponseBean getResponse() {
        return this.a;
    }

    public void setResponse(GetCategoriasSuperClubResponseBean getCategoriasSuperClubResponseBean) {
        this.a = getCategoriasSuperClubResponseBean;
    }
}
