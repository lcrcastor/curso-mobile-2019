package ar.com.santander.rio.mbanking.services.events;

import ar.com.santander.rio.mbanking.services.soap.beans.body.ConfirmarRecalificacionResponseBean;
import com.indra.httpclient.beans.IBeanWS;

public class ConfirmarRecalificacionEvent extends WebServiceEvent {
    public ConfirmarRecalificacionResponseBean confirmarRecalificacionResponseBean;

    public void setResponse(ConfirmarRecalificacionResponseBean confirmarRecalificacionResponseBean2) {
        this.confirmarRecalificacionResponseBean = confirmarRecalificacionResponseBean2;
    }

    public IBeanWS getResponse() {
        return this.confirmarRecalificacionResponseBean;
    }

    public ConfirmarRecalificacionResponseBean getConfirmarRecalificacionResponseBean() {
        return this.confirmarRecalificacionResponseBean;
    }
}
