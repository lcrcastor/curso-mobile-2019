package ar.com.santander.rio.mbanking.services.events;

import ar.com.santander.rio.mbanking.services.soap.beans.VerificaDatosInicialesTransfOBResponseBean;

public class VerificaDatosInicialesTransfOBEvent extends WebServiceEvent {
    private VerificaDatosInicialesTransfOBResponseBean a;

    public VerificaDatosInicialesTransfOBResponseBean getResponseOB() {
        return this.a;
    }

    public void setResponseOB(VerificaDatosInicialesTransfOBResponseBean verificaDatosInicialesTransfOBResponseBean) {
        this.a = verificaDatosInicialesTransfOBResponseBean;
    }
}
