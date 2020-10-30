package ar.com.santander.rio.mbanking.services.events;

import ar.com.santander.rio.mbanking.services.soap.beans.BAgendadosEnvEfeResponseBean;

public class BAgendadosEnvEfeEvent extends WebServiceEvent {
    private BAgendadosEnvEfeResponseBean a;
    private int b;

    public BAgendadosEnvEfeResponseBean getResponse() {
        return this.a;
    }

    public void setResponse(BAgendadosEnvEfeResponseBean bAgendadosEnvEfeResponseBean) {
        this.a = bAgendadosEnvEfeResponseBean;
    }

    public int getDestinatarioPosition() {
        return this.b;
    }

    public void setDestinatarioPosition(int i) {
        this.b = i;
    }
}
