package ar.com.santander.rio.mbanking.services.events;

import ar.com.santander.rio.mbanking.services.soap.beans.body.TenenciaTitulosResponseBean;
import com.indra.httpclient.beans.IBeanWS;

public class TenenciaTitulosEvent extends WebServiceEvent {
    public TenenciaTitulosResponseBean response;

    public void setResponse(TenenciaTitulosResponseBean tenenciaTitulosResponseBean) {
        this.response = tenenciaTitulosResponseBean;
    }

    public IBeanWS getResponse() {
        return this.response;
    }
}
