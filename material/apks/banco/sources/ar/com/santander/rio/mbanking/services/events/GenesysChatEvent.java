package ar.com.santander.rio.mbanking.services.events;

import ar.com.santander.rio.mbanking.services.soap.beans.GenesysChatResponseBean;

public class GenesysChatEvent extends WebServiceEvent {
    public static final String INIT_WATSON = "1";
    public static final String SEND_CHAT_WATSON = "2";
    public static final String SEND_EMAIL_WATSON = "4";
    public static final String SESSION_EXPIRED_WATSON = "5";
    private GenesysChatResponseBean a;

    public GenesysChatResponseBean getResponse() {
        return this.a;
    }

    public void setResponse(GenesysChatResponseBean genesysChatResponseBean) {
        this.a = genesysChatResponseBean;
    }
}
