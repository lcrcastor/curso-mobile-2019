package ar.com.santander.rio.mbanking.services.events;

import ar.com.santander.rio.mbanking.services.soap.beans.GetProximasCuotasResponseBean;

public class GetProximasCuotasCreditoEvent extends WebServiceEvent {
    private GetProximasCuotasResponseBean a;

    public GetProximasCuotasResponseBean getResponse() {
        return this.a;
    }

    public void setResponse(GetProximasCuotasResponseBean getProximasCuotasResponseBean) {
        this.a = getProximasCuotasResponseBean;
    }
}
