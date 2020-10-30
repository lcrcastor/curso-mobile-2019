package ar.com.santander.rio.mbanking.services.soap.beans.body;

import ar.com.santander.rio.mbanking.services.soap.beans.BaseResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanWS;

public class BlanqueoPinRequestBean extends BaseResponseBean implements IBeanWS {
    @SerializedName("body")
    public BlanqueoPinBodyRequestBean blanqueoPinBodyRequestBean;
    @SerializedName("header")
    public PrivateHeaderBean header;

    public BlanqueoPinRequestBean() {
    }

    public BlanqueoPinRequestBean(PrivateHeaderBean privateHeaderBean, BlanqueoPinBodyRequestBean blanqueoPinBodyRequestBean2) {
        this.header = privateHeaderBean;
        this.blanqueoPinBodyRequestBean = blanqueoPinBodyRequestBean2;
    }

    public Class getClassBean() {
        return getClass();
    }
}
