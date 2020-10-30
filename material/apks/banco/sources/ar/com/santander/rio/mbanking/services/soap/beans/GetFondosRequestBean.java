package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.GetFondosBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanWS;

public class GetFondosRequestBean extends BaseResponseBean implements IBeanWS {
    @SerializedName("body")
    private GetFondosBodyRequestBean getFondosBodyRequestBean;
    @SerializedName("header")
    private PrivateHeaderBean headerBean;

    public GetFondosRequestBean(PrivateHeaderBean privateHeaderBean, GetFondosBodyRequestBean getFondosBodyRequestBean2) {
        this.headerBean = privateHeaderBean;
        this.getFondosBodyRequestBean = getFondosBodyRequestBean2;
    }

    public GetFondosRequestBean() {
    }

    public Class getClassBean() {
        return getClass();
    }
}
