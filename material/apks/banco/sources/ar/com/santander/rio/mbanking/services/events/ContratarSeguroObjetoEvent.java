package ar.com.santander.rio.mbanking.services.events;

import ar.com.santander.rio.mbanking.services.soap.beans.ContratarSeguroObjetoResponseBean;

public class ContratarSeguroObjetoEvent extends WebServiceEvent {
    private ContratarSeguroObjetoResponseBean a;

    public ContratarSeguroObjetoResponseBean getResponse() {
        return this.a;
    }

    public void setResponse(ContratarSeguroObjetoResponseBean contratarSeguroObjetoResponseBean) {
        this.a = contratarSeguroObjetoResponseBean;
    }
}
