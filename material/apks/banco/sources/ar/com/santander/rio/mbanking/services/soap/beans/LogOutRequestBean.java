package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.LogOutBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanWS;

public class LogOutRequestBean extends BaseResponseBean implements IBeanWS {
    @SerializedName("header")
    public PrivateHeaderBean headerBean;
    @SerializedName("body")
    public LogOutBodyRequestBean logOutBodyRequestBean;

    public LogOutRequestBean() {
        this.headerBean = new PrivateHeaderBean();
        this.logOutBodyRequestBean = new LogOutBodyRequestBean();
    }

    public LogOutRequestBean(PrivateHeaderBean privateHeaderBean, LogOutBodyRequestBean logOutBodyRequestBean2) {
        this.headerBean = privateHeaderBean;
        this.logOutBodyRequestBean = logOutBodyRequestBean2;
    }

    public Class getClassBean() {
        return getClass();
    }
}
