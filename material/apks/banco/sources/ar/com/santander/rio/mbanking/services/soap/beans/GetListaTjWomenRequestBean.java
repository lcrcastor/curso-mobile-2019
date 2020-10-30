package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.header.HeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanWS;

public class GetListaTjWomenRequestBean extends BaseResponseBean implements IBeanWS {
    @SerializedName("header")
    public HeaderBean headerBean;

    public GetListaTjWomenRequestBean() {
        this.headerBean = new HeaderBean();
    }

    public GetListaTjWomenRequestBean(HeaderBean headerBean2) {
        this.headerBean = headerBean2;
    }

    public Class getClassBean() {
        return getClass();
    }
}
