package ar.com.santander.rio.mbanking.services.events;

import ar.com.santander.rio.mbanking.services.soap.beans.VerificaDatosInicialesTransfResponseBean;

public class VerificaDatosInicialesTransfEvent extends WebServiceEvent {
    private VerificaDatosInicialesTransfResponseBean a;

    public VerificaDatosInicialesTransfResponseBean getResponse() {
        return this.a;
    }

    public void setResponse(VerificaDatosInicialesTransfResponseBean verificaDatosInicialesTransfResponseBean) {
        this.a = verificaDatosInicialesTransfResponseBean;
    }
}
