package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.ABMAliasBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanWS;

public class ABMAliasRequestBean extends BaseResponseBean implements IBeanWS {
    @SerializedName("body")
    public ABMAliasBodyRequestBean abmAliasBodyRequestBean;
    @SerializedName("header")
    public PrivateHeaderBean header;

    public ABMAliasRequestBean() {
    }

    public ABMAliasRequestBean(PrivateHeaderBean privateHeaderBean, ABMAliasBodyRequestBean aBMAliasBodyRequestBean) {
        this.header = privateHeaderBean;
        this.abmAliasBodyRequestBean = aBMAliasBodyRequestBean;
    }

    public Class getClassBean() {
        return getClass();
    }
}
