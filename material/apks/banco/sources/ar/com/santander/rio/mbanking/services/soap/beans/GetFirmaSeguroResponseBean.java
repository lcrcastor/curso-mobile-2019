package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.GetFirmaSeguroBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanErroWS;
import com.indra.httpclient.beans.IBeanWS;
import com.indra.httpclient.json.JSONObject;

public class GetFirmaSeguroResponseBean extends BaseResponseBean implements IBeanErroWS, IBeanWS {
    @SerializedName("body")
    public GetFirmaSeguroBodyResponseBean getFirmaSeguroBodyResponseBean;
    @SerializedName("header")
    public PrivateHeaderBean header;

    public void setJsonElement(JSONObject jSONObject) {
    }

    public GetFirmaSeguroResponseBean() {
        this.getFirmaSeguroBodyResponseBean = new GetFirmaSeguroBodyResponseBean();
    }

    public GetFirmaSeguroResponseBean(PrivateHeaderBean privateHeaderBean, GetFirmaSeguroBodyResponseBean getFirmaSeguroBodyResponseBean2) {
        this.header = privateHeaderBean;
        this.getFirmaSeguroBodyResponseBean = getFirmaSeguroBodyResponseBean2;
    }

    public Object getErrorBeanObject() {
        return this.getFirmaSeguroBodyResponseBean;
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

    public GetFirmaSeguroBodyResponseBean getFirmaSeguroBodyResponseBean() {
        return this.getFirmaSeguroBodyResponseBean;
    }

    public void setGetSegurosBodyResponseBean(GetFirmaSeguroBodyResponseBean getFirmaSeguroBodyResponseBean2) {
        this.getFirmaSeguroBodyResponseBean = getFirmaSeguroBodyResponseBean2;
    }
}
