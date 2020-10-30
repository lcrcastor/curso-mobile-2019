package ar.com.santander.rio.mbanking.services.events;

import ar.com.santander.rio.mbanking.services.soap.beans.ConsultarAdhesionVendedorResponseBean;

public class ConsultarAdhesionVendedorEvent extends WebServiceEvent {
    private ConsultarAdhesionVendedorResponseBean a;

    public ConsultarAdhesionVendedorResponseBean getResponse() {
        return this.a;
    }

    public void setResponse(ConsultarAdhesionVendedorResponseBean consultarAdhesionVendedorResponseBean) {
        this.a = consultarAdhesionVendedorResponseBean;
    }
}
