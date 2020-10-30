package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.header.HeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanErroWS;
import com.indra.httpclient.beans.IBeanWS;

public class LogOutResponseBean extends BaseResponseBean implements IBeanErroWS, IBeanWS {
    @SerializedName("header")
    public HeaderBean headerBean;
    @SerializedName("body")
    public LogOutResponseBean logOutResponseBean;

    public Class getClassBean() {
        return null;
    }

    public Object getErrorBeanObject() {
        return null;
    }

    public LogOutResponseBean() {
    }

    public LogOutResponseBean(HeaderBean headerBean2, LogOutResponseBean logOutResponseBean2) {
        this.headerBean = headerBean2;
        this.logOutResponseBean = logOutResponseBean2;
    }

    public HeaderBean getHeaderBean() {
        return this.headerBean;
    }

    public void setHeaderBean(HeaderBean headerBean2) {
        this.headerBean = headerBean2;
    }

    public LogOutResponseBean getLogOutResponseBean() {
        return this.logOutResponseBean;
    }

    public void setLogOutResponseBean(LogOutResponseBean logOutResponseBean2) {
        this.logOutResponseBean = logOutResponseBean2;
    }
}
