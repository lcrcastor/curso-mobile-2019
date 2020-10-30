package ar.com.santander.rio.mbanking.services.events;

import ar.com.santander.rio.mbanking.services.soap.beans.body.TenenciaCustodiaResponseBean;
import com.indra.httpclient.beans.IBeanWS;

public class TenenciaCustodiaEvent extends WebServiceEvent {
    public TenenciaCustodiaResponseBean response;

    public void setResponse(TenenciaCustodiaResponseBean tenenciaCustodiaResponseBean) {
        this.response = tenenciaCustodiaResponseBean;
    }

    public IBeanWS getResponse() {
        return this.response;
    }
}
