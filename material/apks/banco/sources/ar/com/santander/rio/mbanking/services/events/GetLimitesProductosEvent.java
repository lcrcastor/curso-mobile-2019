package ar.com.santander.rio.mbanking.services.events;

import ar.com.santander.rio.mbanking.services.soap.beans.body.GetLimitesProductosResponseBean;
import com.indra.httpclient.beans.IBeanWS;

public class GetLimitesProductosEvent extends WebServiceEvent {
    public GetLimitesProductosResponseBean getLimitesProductosResponseBean;

    public void setResponse(GetLimitesProductosResponseBean getLimitesProductosResponseBean2) {
        this.getLimitesProductosResponseBean = getLimitesProductosResponseBean2;
    }

    public IBeanWS getResponse() {
        return this.getLimitesProductosResponseBean;
    }
}
