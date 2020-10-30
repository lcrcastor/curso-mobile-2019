package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.LoginUnicoBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.HeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanWS;

public class LoginUnicoRequestBean extends BaseResponseBean implements IBeanWS {
    @SerializedName("header")
    public HeaderBean headerBean;
    @SerializedName("body")
    public LoginUnicoBodyRequestBean loginUnicoBodyRequestBean;

    public LoginUnicoRequestBean() {
        this.headerBean = new HeaderBean();
        this.loginUnicoBodyRequestBean = new LoginUnicoBodyRequestBean();
    }

    public LoginUnicoRequestBean(LoginUnicoBodyRequestBean loginUnicoBodyRequestBean2) {
        this.loginUnicoBodyRequestBean = loginUnicoBodyRequestBean2;
    }

    public LoginUnicoRequestBean(HeaderBean headerBean2, LoginUnicoBodyRequestBean loginUnicoBodyRequestBean2) {
        this.headerBean = headerBean2;
        this.loginUnicoBodyRequestBean = loginUnicoBodyRequestBean2;
    }

    public Class getClassBean() {
        return getClass();
    }
}
