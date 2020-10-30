package ar.com.santander.rio.mbanking.services.events;

import ar.com.santander.rio.mbanking.services.soap.beans.CargaDatosInicialesExtEnvResponseBean;

public class CargaDatosInicialesExtEnvEvent extends WebServiceEvent {
    CargaDatosInicialesExtEnvResponseBean a;

    public CargaDatosInicialesExtEnvResponseBean getResponse() {
        return this.a;
    }

    public void setResponse(CargaDatosInicialesExtEnvResponseBean cargaDatosInicialesExtEnvResponseBean) {
        this.a = cargaDatosInicialesExtEnvResponseBean;
    }
}
