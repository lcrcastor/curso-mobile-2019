package ar.com.santander.rio.mbanking.services.events;

import ar.com.santander.rio.mbanking.services.model.general.Tarjeta;
import ar.com.santander.rio.mbanking.services.soap.beans.UltimosConsumosTCResponseBean;

public class UltimosConsumosTCEvent extends WebServiceEvent {
    private UltimosConsumosTCResponseBean a;
    public Tarjeta tarjetaSeleccionada;

    public Tarjeta getTarjetaSeleccionada() {
        return this.tarjetaSeleccionada;
    }

    public void setTarjetaSeleccionada(Tarjeta tarjeta) {
        this.tarjetaSeleccionada = tarjeta;
    }

    public UltimosConsumosTCResponseBean getResponse() {
        return this.a;
    }

    public void setResponse(UltimosConsumosTCResponseBean ultimosConsumosTCResponseBean) {
        this.a = ultimosConsumosTCResponseBean;
    }
}
