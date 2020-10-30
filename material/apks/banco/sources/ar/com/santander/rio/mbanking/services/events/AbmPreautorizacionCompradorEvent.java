package ar.com.santander.rio.mbanking.services.events;

import ar.com.santander.rio.mbanking.services.soap.beans.AbmPreautorizacionCompradorResponseBean;

public class AbmPreautorizacionCompradorEvent extends WebServiceEvent {
    private AbmPreautorizacionCompradorResponseBean a;

    public AbmPreautorizacionCompradorResponseBean getResponse() {
        return this.a;
    }

    public void setResponse(AbmPreautorizacionCompradorResponseBean abmPreautorizacionCompradorResponseBean) {
        this.a = abmPreautorizacionCompradorResponseBean;
    }
}
