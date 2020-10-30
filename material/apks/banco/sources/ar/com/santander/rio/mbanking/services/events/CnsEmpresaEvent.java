package ar.com.santander.rio.mbanking.services.events;

import ar.com.santander.rio.mbanking.services.soap.beans.CnsEmpresaResponseBean;

public class CnsEmpresaEvent extends WebServiceEvent {
    private CnsEmpresaResponseBean a;

    public CnsEmpresaResponseBean getResponse() {
        return this.a;
    }

    public void setResponse(CnsEmpresaResponseBean cnsEmpresaResponseBean) {
        this.a = cnsEmpresaResponseBean;
    }
}
