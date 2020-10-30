package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.GetPromocionesPushBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.HeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanErroWS;
import com.indra.httpclient.beans.IBeanWS;

public class GetPromocionesPushResponseBean extends BaseResponseBean implements IBeanErroWS, IBeanWS {
    @SerializedName("body")
    public GetPromocionesPushBodyResponseBean getPromocionesPushBodyResponseBean;
    @SerializedName("header")
    public HeaderBean headerBean;

    public GetPromocionesPushResponseBean() {
        this.headerBean = new HeaderBean();
        this.getPromocionesPushBodyResponseBean = new GetPromocionesPushBodyResponseBean();
    }

    public GetPromocionesPushResponseBean(GetPromocionesPushBodyResponseBean getPromocionesPushBodyResponseBean2) {
        this.getPromocionesPushBodyResponseBean = getPromocionesPushBodyResponseBean2;
    }

    public GetPromocionesPushResponseBean(HeaderBean headerBean2, GetPromocionesPushBodyResponseBean getPromocionesPushBodyResponseBean2) {
        this.headerBean = headerBean2;
        this.getPromocionesPushBodyResponseBean = getPromocionesPushBodyResponseBean2;
    }

    public Class getClassBean() {
        return getClass();
    }

    public Object getErrorBeanObject() {
        return this.getPromocionesPushBodyResponseBean;
    }

    public GetPromocionesPushBodyResponseBean getGetPromocionesPushBodyResponseBean() {
        return this.getPromocionesPushBodyResponseBean;
    }

    public void setGetPromocionesPushBodyResponseBean(GetPromocionesPushBodyResponseBean getPromocionesPushBodyResponseBean2) {
        this.getPromocionesPushBodyResponseBean = getPromocionesPushBodyResponseBean2;
    }
}
