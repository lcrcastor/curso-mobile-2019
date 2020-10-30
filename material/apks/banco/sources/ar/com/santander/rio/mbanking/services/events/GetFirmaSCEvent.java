package ar.com.santander.rio.mbanking.services.events;

import ar.com.santander.rio.mbanking.services.soap.beans.GetFirmaSCResponseBean;

public class GetFirmaSCEvent extends WebServiceEvent {
    private GetFirmaSCResponseBean a;

    public GetFirmaSCResponseBean getResponseBean() {
        return this.a;
    }

    public void setResponse(GetFirmaSCResponseBean getFirmaSCResponseBean) {
        this.a = getFirmaSCResponseBean;
    }
}
