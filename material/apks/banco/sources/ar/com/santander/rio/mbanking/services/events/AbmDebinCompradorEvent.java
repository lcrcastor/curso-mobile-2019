package ar.com.santander.rio.mbanking.services.events;

import ar.com.santander.rio.mbanking.services.soap.beans.AbmDebinCompradorResponseBean;

public class AbmDebinCompradorEvent extends WebServiceEvent {
    private AbmDebinCompradorResponseBean a;

    public AbmDebinCompradorResponseBean getResponse() {
        return this.a;
    }

    public void setResponse(AbmDebinCompradorResponseBean abmDebinCompradorResponseBean) {
        this.a = abmDebinCompradorResponseBean;
    }
}
