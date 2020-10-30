package ar.com.santander.rio.mbanking.services.events;

import ar.com.santander.rio.mbanking.services.soap.beans.ConsultaAgendadosEnvEfeResponseBean;

public class ConsultaAgendadosEnvEfeEvent extends WebServiceEvent {
    ConsultaAgendadosEnvEfeResponseBean a;

    public ConsultaAgendadosEnvEfeResponseBean getResponse() {
        return this.a;
    }

    public void setResponse(ConsultaAgendadosEnvEfeResponseBean consultaAgendadosEnvEfeResponseBean) {
        this.a = consultaAgendadosEnvEfeResponseBean;
    }
}
