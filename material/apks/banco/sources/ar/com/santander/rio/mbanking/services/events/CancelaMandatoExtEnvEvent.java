package ar.com.santander.rio.mbanking.services.events;

import ar.com.santander.rio.mbanking.services.soap.beans.CancelaMandatoExtEnvResponseBean;

public class CancelaMandatoExtEnvEvent extends WebServiceEvent {
    private CancelaMandatoExtEnvResponseBean a;

    public CancelaMandatoExtEnvResponseBean getResponse() {
        return this.a;
    }

    public void setResponse(CancelaMandatoExtEnvResponseBean cancelaMandatoExtEnvResponseBean) {
        this.a = cancelaMandatoExtEnvResponseBean;
    }
}
