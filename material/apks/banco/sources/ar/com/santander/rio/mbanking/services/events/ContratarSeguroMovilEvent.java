package ar.com.santander.rio.mbanking.services.events;

import ar.com.santander.rio.mbanking.services.soap.beans.ContratarSeguroMovilResponseBean;

public class ContratarSeguroMovilEvent extends WebServiceEvent {
    private ContratarSeguroMovilResponseBean a;

    public ContratarSeguroMovilResponseBean getResponse() {
        return this.a;
    }

    public void setResponse(ContratarSeguroMovilResponseBean contratarSeguroMovilResponseBean) {
        this.a = contratarSeguroMovilResponseBean;
    }
}
