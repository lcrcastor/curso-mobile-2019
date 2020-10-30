package ar.com.santander.rio.mbanking.services.events;

import ar.com.santander.rio.mbanking.services.soap.beans.AMAgendadosEnvEfeResponseBean;

public class AMAgendadosEnvEfeEvent extends WebServiceEvent {
    private AMAgendadosEnvEfeResponseBean a;

    public AMAgendadosEnvEfeResponseBean getResponse() {
        return this.a;
    }

    public void setResponse(AMAgendadosEnvEfeResponseBean aMAgendadosEnvEfeResponseBean) {
        this.a = aMAgendadosEnvEfeResponseBean;
    }
}
