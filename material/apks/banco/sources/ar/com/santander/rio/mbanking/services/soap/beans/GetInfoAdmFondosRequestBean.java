package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.GetInfoAdmFondosBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanWS;

public class GetInfoAdmFondosRequestBean extends BaseResponseBean implements IBeanWS {
    @SerializedName("body")
    private GetInfoAdmFondosBodyRequestBean getInfoAdmFondosBodyRequestBean;
    @SerializedName("header")
    private PrivateHeaderBean headerBean;

    public GetInfoAdmFondosRequestBean(PrivateHeaderBean privateHeaderBean, GetInfoAdmFondosBodyRequestBean getInfoAdmFondosBodyRequestBean2) {
        this.headerBean = privateHeaderBean;
        this.getInfoAdmFondosBodyRequestBean = getInfoAdmFondosBodyRequestBean2;
    }

    public GetInfoAdmFondosRequestBean() {
    }

    public Class getClassBean() {
        return getClass();
    }
}
