package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.GetViajesBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanWS;

public class GetViajesRequestBean extends BaseResponseBean implements IBeanWS {
    @SerializedName("body")
    public GetViajesBodyRequestBean getViajesBodyRequestBean;
    @SerializedName("header")
    public PrivateHeaderBean header;

    public GetViajesRequestBean() {
    }

    public GetViajesRequestBean(PrivateHeaderBean privateHeaderBean, GetViajesBodyRequestBean getViajesBodyRequestBean2) {
        this.header = privateHeaderBean;
        this.getViajesBodyRequestBean = getViajesBodyRequestBean2;
    }

    public Class getClassBean() {
        return getClassBean();
    }
}
