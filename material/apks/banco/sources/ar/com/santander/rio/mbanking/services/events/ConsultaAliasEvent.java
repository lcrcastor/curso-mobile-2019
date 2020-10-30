package ar.com.santander.rio.mbanking.services.events;

import ar.com.santander.rio.mbanking.services.soap.beans.ConsultaAliasResponseBean;

public class ConsultaAliasEvent extends WebServiceEvent {
    private ConsultaAliasResponseBean a;

    public ConsultaAliasResponseBean getResponse() {
        return this.a;
    }

    public void setResponse(ConsultaAliasResponseBean consultaAliasResponseBean) {
        this.a = consultaAliasResponseBean;
    }
}
