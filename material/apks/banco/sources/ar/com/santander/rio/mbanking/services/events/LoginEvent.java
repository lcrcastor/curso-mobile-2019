package ar.com.santander.rio.mbanking.services.events;

import ar.com.santander.rio.mbanking.services.soap.beans.LoginUnicoResponseBean;

public class LoginEvent extends WebServiceEvent {
    private LoginUnicoResponseBean a;

    public LoginUnicoResponseBean getLoginUnicoResponseBean() {
        return this.a;
    }

    public void setLoginUnicoResponseBean(LoginUnicoResponseBean loginUnicoResponseBean) {
        this.a = loginUnicoResponseBean;
    }
}
