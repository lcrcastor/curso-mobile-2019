package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.GetDebinesBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanErroWS;
import com.indra.httpclient.beans.IBeanWS;

public class GetDebinesResponseBean extends BaseResponseBean implements IBeanErroWS, IBeanWS {
    @SerializedName("body")
    public GetDebinesBodyResponseBean getDebinesBodyResponseBean;
    @SerializedName("header")
    public PrivateHeaderBean headerBean;

    public GetDebinesResponseBean(GetDebinesBodyResponseBean getDebinesBodyResponseBean2) {
        this.getDebinesBodyResponseBean = getDebinesBodyResponseBean2;
    }

    public GetDebinesResponseBean() {
    }

    public Object getErrorBeanObject() {
        return this.getDebinesBodyResponseBean;
    }

    public Class getClassBean() {
        return getClass();
    }

    public GetDebinesBodyResponseBean getDebinesBodyResponseBean() {
        return this.getDebinesBodyResponseBean;
    }

    public void setGetDebinesBodyResponseBean(GetDebinesBodyResponseBean getDebinesBodyResponseBean2) {
        this.getDebinesBodyResponseBean = getDebinesBodyResponseBean2;
    }

    public PrivateHeaderBean getHeaderBean() {
        return this.headerBean;
    }

    public void setHeaderBean(PrivateHeaderBean privateHeaderBean) {
        this.headerBean = privateHeaderBean;
    }
}
