package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.GetPromocionesPushBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.HeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanWS;

public class GetPromocionesPushRequestBean extends BaseResponseBean implements IBeanWS {
    @SerializedName("body")
    public GetPromocionesPushBodyRequestBean getPromocionesPushBodyRequestBean;
    @SerializedName("header")
    public HeaderBean headerBean;

    public GetPromocionesPushRequestBean() {
        this.headerBean = new HeaderBean();
        this.getPromocionesPushBodyRequestBean = new GetPromocionesPushBodyRequestBean();
    }

    public GetPromocionesPushRequestBean(HeaderBean headerBean2) {
        this.headerBean = headerBean2;
    }

    public HeaderBean getHeaderBean() {
        return this.headerBean;
    }

    public void setHeaderBean(HeaderBean headerBean2) {
        this.headerBean = headerBean2;
    }

    public GetPromocionesPushBodyRequestBean getGetPromocionesPushBodyRequestBean() {
        return this.getPromocionesPushBodyRequestBean;
    }

    public void setGetPromocionesPushBodyRequestBean(GetPromocionesPushBodyRequestBean getPromocionesPushBodyRequestBean2) {
        this.getPromocionesPushBodyRequestBean = getPromocionesPushBodyRequestBean2;
    }

    public Class getClassBean() {
        return getClass();
    }
}
