package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.ABMViajesBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanWS;

public class ABMViajesRequestBean extends BaseResponseBean implements IBeanWS {
    @SerializedName("body")
    public ABMViajesBodyRequestBean abmViajesBodyRequestBean;
    @SerializedName("header")
    public PrivateHeaderBean header;

    public ABMViajesRequestBean() {
    }

    public ABMViajesRequestBean(PrivateHeaderBean privateHeaderBean, ABMViajesBodyRequestBean aBMViajesBodyRequestBean) {
        this.header = privateHeaderBean;
        this.abmViajesBodyRequestBean = aBMViajesBodyRequestBean;
    }

    public Class getClassBean() {
        return getClass();
    }
}
