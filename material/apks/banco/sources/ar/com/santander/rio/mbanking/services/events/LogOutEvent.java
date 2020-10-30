package ar.com.santander.rio.mbanking.services.events;

import ar.com.santander.rio.mbanking.services.soap.beans.LogOutResponseBean;

public class LogOutEvent extends WebServiceEvent {
    private LogOutResponseBean a;

    public LogOutResponseBean getLogOutResponseBean() {
        return this.a;
    }

    public void setLogOutResponseBean(LogOutResponseBean logOutResponseBean) {
        this.a = logOutResponseBean;
    }
}
