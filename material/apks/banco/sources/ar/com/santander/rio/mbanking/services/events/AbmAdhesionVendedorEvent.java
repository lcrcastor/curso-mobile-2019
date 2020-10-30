package ar.com.santander.rio.mbanking.services.events;

import ar.com.santander.rio.mbanking.services.soap.beans.AbmAdhesionVendedorResponseBean;

public class AbmAdhesionVendedorEvent extends WebServiceEvent {
    private AbmAdhesionVendedorResponseBean a;

    public AbmAdhesionVendedorResponseBean getResponse() {
        return this.a;
    }

    public void setResponse(AbmAdhesionVendedorResponseBean abmAdhesionVendedorResponseBean) {
        this.a = abmAdhesionVendedorResponseBean;
    }
}
