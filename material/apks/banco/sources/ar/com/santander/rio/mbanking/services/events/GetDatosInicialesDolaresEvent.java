package ar.com.santander.rio.mbanking.services.events;

import ar.com.santander.rio.mbanking.services.soap.beans.GetDatosInicialesDolaresResponseBean;

public class GetDatosInicialesDolaresEvent extends WebServiceEvent {
    private GetDatosInicialesDolaresResponseBean a;

    public GetDatosInicialesDolaresResponseBean getResponse() {
        return this.a;
    }

    public void setResponse(GetDatosInicialesDolaresResponseBean getDatosInicialesDolaresResponseBean) {
        this.a = getDatosInicialesDolaresResponseBean;
    }
}
