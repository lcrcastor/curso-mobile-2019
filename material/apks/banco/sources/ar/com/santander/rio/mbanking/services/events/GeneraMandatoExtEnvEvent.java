package ar.com.santander.rio.mbanking.services.events;

import ar.com.santander.rio.mbanking.services.soap.beans.GeneraMandatoExtEnvResponseBean;

public class GeneraMandatoExtEnvEvent extends WebServiceEvent {
    private GeneraMandatoExtEnvResponseBean a;

    public GeneraMandatoExtEnvResponseBean getResponse() {
        return this.a;
    }

    public void setResponse(GeneraMandatoExtEnvResponseBean generaMandatoExtEnvResponseBean) {
        this.a = generaMandatoExtEnvResponseBean;
    }
}
