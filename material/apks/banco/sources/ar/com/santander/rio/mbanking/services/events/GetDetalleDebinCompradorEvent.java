package ar.com.santander.rio.mbanking.services.events;

import ar.com.santander.rio.mbanking.services.soap.beans.GetDetalleDebinCompradorResponseBean;

public class GetDetalleDebinCompradorEvent extends WebServiceEvent {
    private GetDetalleDebinCompradorResponseBean a;

    public GetDetalleDebinCompradorResponseBean getResponse() {
        return this.a;
    }

    public void setResponse(GetDetalleDebinCompradorResponseBean getDetalleDebinCompradorResponseBean) {
        this.a = getDetalleDebinCompradorResponseBean;
    }
}
