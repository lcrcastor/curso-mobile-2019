package ar.com.santander.rio.mbanking.services.events;

import ar.com.santander.rio.mbanking.services.soap.beans.EnviarSugerenciaObjetoResponseBean;

public class EnviarSugerenciaObjetoEvent extends WebServiceEvent {
    EnviarSugerenciaObjetoResponseBean a;

    public EnviarSugerenciaObjetoResponseBean getResponse() {
        return this.a;
    }

    public void setResponse(EnviarSugerenciaObjetoResponseBean enviarSugerenciaObjetoResponseBean) {
        this.a = enviarSugerenciaObjetoResponseBean;
    }
}
