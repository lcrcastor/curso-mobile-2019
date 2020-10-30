package ar.com.santander.rio.mbanking.services.events;

import ar.com.santander.rio.mbanking.services.soap.beans.ContratarSeguroAccidenteResponseBean;

public class ContratarSeguroAccidenteEvent extends WebServiceEvent {
    private ContratarSeguroAccidenteResponseBean a;

    public ContratarSeguroAccidenteResponseBean getResponse() {
        return this.a;
    }

    public void setResponse(ContratarSeguroAccidenteResponseBean contratarSeguroAccidenteResponseBean) {
        this.a = contratarSeguroAccidenteResponseBean;
    }
}
