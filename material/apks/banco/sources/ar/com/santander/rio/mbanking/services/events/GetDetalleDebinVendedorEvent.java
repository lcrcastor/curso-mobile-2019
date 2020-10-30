package ar.com.santander.rio.mbanking.services.events;

import ar.com.santander.rio.mbanking.services.soap.beans.GetDetalleDebinVendedorResponseBean;

public class GetDetalleDebinVendedorEvent extends WebServiceEvent {
    private GetDetalleDebinVendedorResponseBean a;

    public GetDetalleDebinVendedorResponseBean getResponse() {
        return this.a;
    }

    public void setResponse(GetDetalleDebinVendedorResponseBean getDetalleDebinVendedorResponseBean) {
        this.a = getDetalleDebinVendedorResponseBean;
    }
}
