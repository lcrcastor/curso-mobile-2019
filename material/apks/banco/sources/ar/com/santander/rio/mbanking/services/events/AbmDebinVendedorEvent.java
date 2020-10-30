package ar.com.santander.rio.mbanking.services.events;

import ar.com.santander.rio.mbanking.services.soap.beans.AbmDebinVendedorResponseBean;

public class AbmDebinVendedorEvent extends WebServiceEvent {
    private AbmDebinVendedorResponseBean a;

    public AbmDebinVendedorResponseBean getResponse() {
        return this.a;
    }

    public void setResponse(AbmDebinVendedorResponseBean abmDebinVendedorResponseBean) {
        this.a = abmDebinVendedorResponseBean;
    }
}
