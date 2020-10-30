package ar.com.santander.rio.mbanking.services.events;

import ar.com.santander.rio.mbanking.services.soap.beans.PagoTarjetaResponseBean;

public class PagoTarjetaEvent extends WebServiceEvent {
    private PagoTarjetaResponseBean a;

    public void setResponseBean(PagoTarjetaResponseBean pagoTarjetaResponseBean) {
    }

    public PagoTarjetaResponseBean getPagoTarjetaResponseBean() {
        return this.a;
    }

    public void setPagoTarjetaResponseBean(PagoTarjetaResponseBean pagoTarjetaResponseBean) {
        this.a = pagoTarjetaResponseBean;
    }
}
