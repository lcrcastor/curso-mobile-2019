package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.GetOcupacionesBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanWS;

public class GetOcupacionesRequestBean extends BaseResponseBean implements IBeanWS {
    @SerializedName("body")
    public GetOcupacionesBodyRequestBean getOcupacionesBodyRequestBean;
    @SerializedName("header")
    public PrivateHeaderBean header;

    public GetOcupacionesRequestBean() {
    }

    public GetOcupacionesRequestBean(PrivateHeaderBean privateHeaderBean, GetOcupacionesBodyRequestBean getOcupacionesBodyRequestBean2) {
        this.header = privateHeaderBean;
        this.getOcupacionesBodyRequestBean = getOcupacionesBodyRequestBean2;
    }

    public Class getClassBean() {
        return getClass();
    }
}
