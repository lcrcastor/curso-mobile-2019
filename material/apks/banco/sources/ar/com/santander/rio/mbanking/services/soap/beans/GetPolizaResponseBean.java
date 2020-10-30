package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.GetPolizaBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanErroWS;
import com.indra.httpclient.beans.IBeanWS;
import com.indra.httpclient.json.JSONObject;

public class GetPolizaResponseBean extends BaseResponseBean implements IBeanErroWS, IBeanWS {
    @SerializedName("body")
    public GetPolizaBodyResponseBean getPolizaBodyResponseBean;
    @SerializedName("header")
    public PrivateHeaderBean header;

    public void setJsonElement(JSONObject jSONObject) {
    }

    public GetPolizaResponseBean() {
        this.getPolizaBodyResponseBean = new GetPolizaBodyResponseBean();
    }

    public GetPolizaResponseBean(PrivateHeaderBean privateHeaderBean, GetPolizaBodyResponseBean getPolizaBodyResponseBean2) {
        this.header = privateHeaderBean;
        this.getPolizaBodyResponseBean = getPolizaBodyResponseBean2;
    }

    public Object getErrorBeanObject() {
        return this.getPolizaBodyResponseBean;
    }

    public Class getClassBean() {
        return getClass();
    }

    public PrivateHeaderBean getHeader() {
        return this.header;
    }

    public void setHeader(PrivateHeaderBean privateHeaderBean) {
        this.header = privateHeaderBean;
    }

    public GetPolizaBodyResponseBean getGetPolizaBodyResponseBean() {
        return this.getPolizaBodyResponseBean;
    }

    public void setGetPolizaBodyResponseBean(GetPolizaBodyResponseBean getPolizaBodyResponseBean2) {
        this.getPolizaBodyResponseBean = getPolizaBodyResponseBean2;
    }
}
