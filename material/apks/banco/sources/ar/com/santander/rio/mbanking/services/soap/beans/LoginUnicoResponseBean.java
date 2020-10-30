package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.LoginUnicoBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.HeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanErroWS;
import com.indra.httpclient.beans.IBeanWS;

public class LoginUnicoResponseBean extends BaseResponseBean implements IBeanErroWS, IBeanWS {
    @SerializedName("body")
    public LoginUnicoBodyResponseBean LoginUnicoBodyResponseBean;
    @SerializedName("header")
    public HeaderBean headerBean;

    public Class getClassBean() {
        return null;
    }

    public Object getErrorBeanObject() {
        return null;
    }

    public HeaderBean getHeaderBean() {
        return this.headerBean;
    }

    public void setHeaderBean(HeaderBean headerBean2) {
        this.headerBean = headerBean2;
    }

    public LoginUnicoBodyResponseBean getLoginUnicoBodyResponseBean() {
        return this.LoginUnicoBodyResponseBean;
    }

    public void setLoginUnicoBodyResponseBean(LoginUnicoBodyResponseBean loginUnicoBodyResponseBean) {
        this.LoginUnicoBodyResponseBean = loginUnicoBodyResponseBean;
    }
}
