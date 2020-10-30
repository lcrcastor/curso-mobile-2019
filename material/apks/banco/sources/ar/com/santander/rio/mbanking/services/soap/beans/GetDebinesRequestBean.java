package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.GetDebinesBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanWS;

public class GetDebinesRequestBean extends BaseResponseBean implements IBeanWS {
    @SerializedName("body")
    private GetDebinesBodyRequestBean getDebinesBodyRequestBean;
    @SerializedName("header")
    private PrivateHeaderBean headerBean;

    public GetDebinesRequestBean(PrivateHeaderBean privateHeaderBean, GetDebinesBodyRequestBean getDebinesBodyRequestBean2) {
        this.headerBean = privateHeaderBean;
        this.getDebinesBodyRequestBean = getDebinesBodyRequestBean2;
    }

    public GetDebinesRequestBean() {
    }

    public PrivateHeaderBean getHeaderBean() {
        return this.headerBean;
    }

    public void setHeaderBean(PrivateHeaderBean privateHeaderBean) {
        this.headerBean = privateHeaderBean;
    }

    public GetDebinesBodyRequestBean getGetDebinesBodyRequestBean() {
        return this.getDebinesBodyRequestBean;
    }

    public void setGetDebinesBodyRequestBean(GetDebinesBodyRequestBean getDebinesBodyRequestBean2) {
        this.getDebinesBodyRequestBean = getDebinesBodyRequestBean2;
    }

    public Class getClassBean() {
        return getClass();
    }
}
