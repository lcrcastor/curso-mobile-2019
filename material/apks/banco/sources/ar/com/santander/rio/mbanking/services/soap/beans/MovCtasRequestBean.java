package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.MovCtasBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanWS;

public class MovCtasRequestBean extends BaseResponseBean implements IBeanWS {
    @SerializedName("header")
    public PrivateHeaderBean headerBean;
    @SerializedName("body")
    public MovCtasBodyRequestBean movCtasBodyRequestBean;

    public MovCtasRequestBean() {
    }

    public MovCtasRequestBean(PrivateHeaderBean privateHeaderBean, MovCtasBodyRequestBean movCtasBodyRequestBean2) {
        this.headerBean = privateHeaderBean;
        this.movCtasBodyRequestBean = movCtasBodyRequestBean2;
    }

    public Class getClassBean() {
        return getClass();
    }
}
