package ar.com.santander.rio.mbanking.services.events;

import ar.com.santander.rio.mbanking.services.soap.beans.PreguntasFamiliaResponseBean;

public class PreguntasFamiliaEvent extends WebServiceEvent {
    private PreguntasFamiliaResponseBean a;

    public PreguntasFamiliaResponseBean getResponse() {
        return this.a;
    }

    public void setResponse(PreguntasFamiliaResponseBean preguntasFamiliaResponseBean) {
        this.a = preguntasFamiliaResponseBean;
    }
}
