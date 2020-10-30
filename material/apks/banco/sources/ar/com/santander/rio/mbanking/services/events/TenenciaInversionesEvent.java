package ar.com.santander.rio.mbanking.services.events;

import ar.com.santander.rio.mbanking.services.soap.beans.body.TenenciaInversionesResponseBean;
import com.indra.httpclient.beans.IBeanWS;

public class TenenciaInversionesEvent extends WebServiceEvent {
    public TenenciaInversionesResponseBean response;

    public void setResponse(TenenciaInversionesResponseBean tenenciaInversionesResponseBean) {
        this.response = tenenciaInversionesResponseBean;
    }

    public IBeanWS getResponse() {
        return this.response;
    }
}
