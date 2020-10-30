package ar.com.santander.rio.mbanking.services.events;

import ar.com.santander.rio.mbanking.services.soap.beans.ConsultaMandatosExtEnvResponseBean;

public class ConsultaMandatosExtEnvEvent extends WebServiceEvent {
    private ConsultaMandatosExtEnvResponseBean a;

    public ConsultaMandatosExtEnvResponseBean getResponse() {
        return this.a;
    }

    public void setResponse(ConsultaMandatosExtEnvResponseBean consultaMandatosExtEnvResponseBean) {
        this.a = consultaMandatosExtEnvResponseBean;
    }
}
