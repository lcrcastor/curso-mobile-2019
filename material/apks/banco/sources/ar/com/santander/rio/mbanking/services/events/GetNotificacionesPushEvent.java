package ar.com.santander.rio.mbanking.services.events;

import ar.com.santander.rio.mbanking.services.soap.beans.GetNotificacionesPushResponseBean;

public class GetNotificacionesPushEvent extends WebServiceEvent {
    private GetNotificacionesPushResponseBean a;

    public GetNotificacionesPushResponseBean getResponseBean() {
        return this.a;
    }

    public void setResponseBean(GetNotificacionesPushResponseBean getNotificacionesPushResponseBean) {
        this.a = getNotificacionesPushResponseBean;
    }
}
