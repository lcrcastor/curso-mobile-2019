package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.GetPolizaBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanWS;

public class GetPolizaRequestBean extends BaseResponseBean implements IBeanWS {
    @SerializedName("body")
    public GetPolizaBodyRequestBean getPolizaBodyRequestBean;
    @SerializedName("header")
    public PrivateHeaderBean header;

    public GetPolizaRequestBean() {
    }

    public GetPolizaRequestBean(PrivateHeaderBean privateHeaderBean, GetPolizaBodyRequestBean getPolizaBodyRequestBean2) {
        this.header = privateHeaderBean;
        this.getPolizaBodyRequestBean = getPolizaBodyRequestBean2;
    }

    public Class getClassBean() {
        return getClass();
    }
}
