package ar.com.santander.rio.mbanking.services.events;

import ar.com.santander.rio.mbanking.services.soap.beans.EnviarTokenOBPResponseBean;

public class EnviarTokenOBPEvent extends WebServiceEvent {
    private EnviarTokenOBPResponseBean a;

    public EnviarTokenOBPResponseBean getResponse() {
        return this.a;
    }

    public void setResponse(EnviarTokenOBPResponseBean enviarTokenOBPResponseBean) {
        this.a = enviarTokenOBPResponseBean;
    }
}
