package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.header.HeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanWS;

public class RetryServerRequestBean extends BaseResponseBean implements IBeanWS {
    @SerializedName("header")
    public HeaderBean headerBean;
    @SerializedName("body")
    public TimeServer timeServer;

    public RetryServerRequestBean() {
    }

    public RetryServerRequestBean(TimeServer timeServer2) {
        this.headerBean = new HeaderBean();
        this.timeServer = timeServer2;
    }

    public RetryServerRequestBean(HeaderBean headerBean2, TimeServer timeServer2) {
        this.headerBean = headerBean2;
        this.timeServer = timeServer2;
    }

    public Class getClassBean() {
        return getClass();
    }
}
