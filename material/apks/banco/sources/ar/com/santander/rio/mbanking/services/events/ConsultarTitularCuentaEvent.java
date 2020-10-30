package ar.com.santander.rio.mbanking.services.events;

import ar.com.santander.rio.mbanking.services.soap.beans.ConsultarTitularCuentaResponseBean;

public class ConsultarTitularCuentaEvent extends WebServiceEvent {
    private ConsultarTitularCuentaResponseBean a;

    public ConsultarTitularCuentaResponseBean getResponse() {
        return this.a;
    }

    public void setResponse(ConsultarTitularCuentaResponseBean consultarTitularCuentaResponseBean) {
        this.a = consultarTitularCuentaResponseBean;
    }
}
