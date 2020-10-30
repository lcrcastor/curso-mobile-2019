package ar.com.santander.rio.mbanking.services.events;

import ar.com.santander.rio.mbanking.services.soap.beans.CompraVentaDolarResponseBean;

public class CompraVentaDolarEvent extends WebServiceEvent {
    private CompraVentaDolarResponseBean a;

    public CompraVentaDolarResponseBean getResponse() {
        return this.a;
    }

    public void setResponse(CompraVentaDolarResponseBean compraVentaDolarResponseBean) {
        this.a = compraVentaDolarResponseBean;
    }
}
