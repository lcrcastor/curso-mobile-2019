package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.GetLimitesExtraccionBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanWS;

public class GetLimitesExtraccionRequestBean extends BaseResponseBean implements IBeanWS {
    @SerializedName("body")
    public GetLimitesExtraccionBodyRequestBean getLimitesExtraccionBodyRequestBean;
    @SerializedName("header")
    public PrivateHeaderBean headerBean;

    public GetLimitesExtraccionRequestBean() {
    }

    public GetLimitesExtraccionRequestBean(PrivateHeaderBean privateHeaderBean, GetLimitesExtraccionBodyRequestBean getLimitesExtraccionBodyRequestBean2) {
        this.headerBean = privateHeaderBean;
        this.getLimitesExtraccionBodyRequestBean = getLimitesExtraccionBodyRequestBean2;
    }

    public Class getClassBean() {
        return getClass();
    }
}
