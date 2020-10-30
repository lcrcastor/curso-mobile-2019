package ar.com.santander.rio.mbanking.services.events;

import ar.com.santander.rio.mbanking.services.soap.beans.GetDetalleCuotaTenenciaCreditoResponseBean;

public class GetDetalleCuotaTenenciaCreditoEvent extends WebServiceEvent {
    private GetDetalleCuotaTenenciaCreditoResponseBean a;

    public GetDetalleCuotaTenenciaCreditoResponseBean getResponse() {
        return this.a;
    }

    public void setResponse(GetDetalleCuotaTenenciaCreditoResponseBean getDetalleCuotaTenenciaCreditoResponseBean) {
        this.a = getDetalleCuotaTenenciaCreditoResponseBean;
    }
}
