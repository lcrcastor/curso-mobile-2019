package ar.com.santander.rio.mbanking.services.events;

import ar.com.santander.rio.mbanking.services.soap.beans.GetDetallePreAutorizacionCompradorResponseBean;

public class GetDetallePreAutorizacionCompradorEvent extends WebServiceEvent {
    private GetDetallePreAutorizacionCompradorResponseBean a;

    public GetDetallePreAutorizacionCompradorResponseBean getGetDetallePreAutorizacionCompradorResponseBean() {
        return this.a;
    }

    public void setGetDetallePreAutorizacionCompradorResponseBean(GetDetallePreAutorizacionCompradorResponseBean getDetallePreAutorizacionCompradorResponseBean) {
        this.a = getDetallePreAutorizacionCompradorResponseBean;
    }
}
