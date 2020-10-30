package ar.com.santander.rio.mbanking.services.events;

import ar.com.santander.rio.mbanking.services.soap.beans.UltimoResumenTCResponseBean;

public class UltimoResumenTCEvent extends WebServiceEvent {
    private UltimoResumenTCResponseBean a;

    public UltimoResumenTCResponseBean getResponse() {
        return this.a;
    }

    public void setResponse(UltimoResumenTCResponseBean ultimoResumenTCResponseBean) {
        this.a = ultimoResumenTCResponseBean;
    }
}
