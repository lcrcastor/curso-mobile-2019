package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.GetRecargasBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.HeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanWS;

public class GetRecargasRequestBean extends BaseResponseBean implements IBeanWS {
    @SerializedName("body")
    public GetRecargasBodyRequestBean getRecargasBodyRequestBean;
    @SerializedName("header")
    public HeaderBean header;

    public GetRecargasRequestBean(HeaderBean headerBean, GetRecargasBodyRequestBean getRecargasBodyRequestBean2) {
        this.header = headerBean;
        this.getRecargasBodyRequestBean = getRecargasBodyRequestBean2;
    }

    public Class getClassBean() {
        return getClass();
    }
}
