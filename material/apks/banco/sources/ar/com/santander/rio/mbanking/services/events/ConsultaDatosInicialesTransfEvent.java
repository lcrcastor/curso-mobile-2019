package ar.com.santander.rio.mbanking.services.events;

import ar.com.santander.rio.mbanking.services.soap.beans.ConsultaDatosInicialesTransfResponseBean;

public class ConsultaDatosInicialesTransfEvent extends WebServiceEvent {
    private ConsultaDatosInicialesTransfResponseBean a;

    public ConsultaDatosInicialesTransfResponseBean getResponse() {
        return this.a;
    }

    public void setResponse(ConsultaDatosInicialesTransfResponseBean consultaDatosInicialesTransfResponseBean) {
        this.a = consultaDatosInicialesTransfResponseBean;
    }
}
