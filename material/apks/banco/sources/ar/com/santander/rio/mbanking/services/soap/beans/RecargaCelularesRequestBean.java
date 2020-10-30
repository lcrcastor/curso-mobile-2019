package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.RecargaCelularesBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanWS;

public class RecargaCelularesRequestBean extends BaseResponseBean implements IBeanWS {
    @SerializedName("header")
    public PrivateHeaderBean headerBean;
    @SerializedName("body")
    public RecargaCelularesBodyRequestBean recargaCelularesBodyRequestBean;

    public RecargaCelularesRequestBean() {
    }

    public RecargaCelularesRequestBean(PrivateHeaderBean privateHeaderBean, RecargaCelularesBodyRequestBean recargaCelularesBodyRequestBean2) {
        this.headerBean = privateHeaderBean;
        this.recargaCelularesBodyRequestBean = recargaCelularesBodyRequestBean2;
    }

    public Class getClassBean() {
        return getClass();
    }
}
