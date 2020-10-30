package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.GetFirmaCredinBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanErroWS;
import com.indra.httpclient.beans.IBeanWS;

public class GetFirmaCredinResponseBean extends BaseResponseBean implements IBeanErroWS, IBeanWS {
    @SerializedName("body")
    public GetFirmaCredinBodyResponseBean getFirmaCredinBodyResponseBean;
    @SerializedName("header")
    public PrivateHeaderBean headerBean;

    public GetFirmaCredinResponseBean() {
    }

    public GetFirmaCredinResponseBean(PrivateHeaderBean privateHeaderBean, GetFirmaCredinBodyResponseBean getFirmaCredinBodyResponseBean2) {
        this.headerBean = privateHeaderBean;
        this.getFirmaCredinBodyResponseBean = getFirmaCredinBodyResponseBean2;
    }

    public PrivateHeaderBean getHeaderBean() {
        return this.headerBean;
    }

    public void setHeaderBean(PrivateHeaderBean privateHeaderBean) {
        this.headerBean = privateHeaderBean;
    }

    public GetFirmaCredinBodyResponseBean getGetFirmaCredinBodyResponseBean() {
        return this.getFirmaCredinBodyResponseBean;
    }

    public void setGetFirmaCredinBodyResponseBean(GetFirmaCredinBodyResponseBean getFirmaCredinBodyResponseBean2) {
        this.getFirmaCredinBodyResponseBean = getFirmaCredinBodyResponseBean2;
    }

    public Object getErrorBeanObject() {
        return this.getFirmaCredinBodyResponseBean;
    }

    public Class getClassBean() {
        return getClass();
    }
}
