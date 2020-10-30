package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.GetSegurosBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanErroWS;
import com.indra.httpclient.beans.IBeanWS;
import com.indra.httpclient.json.JSONObject;

public class GetSegurosResponseBean extends BaseResponseBean implements IBeanErroWS, IBeanWS {
    @SerializedName("body")
    public GetSegurosBodyResponseBean getSegurosBodyResponseBean;
    @SerializedName("header")
    public PrivateHeaderBean header;

    public void setJsonElement(JSONObject jSONObject) {
    }

    public GetSegurosResponseBean() {
        this.getSegurosBodyResponseBean = new GetSegurosBodyResponseBean();
    }

    public GetSegurosResponseBean(PrivateHeaderBean privateHeaderBean, GetSegurosBodyResponseBean getSegurosBodyResponseBean2) {
        this.header = privateHeaderBean;
        this.getSegurosBodyResponseBean = getSegurosBodyResponseBean2;
    }

    public Object getErrorBeanObject() {
        return this.getSegurosBodyResponseBean;
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

    public GetSegurosBodyResponseBean getGetSegurosBodyResponseBean() {
        return this.getSegurosBodyResponseBean;
    }

    public void setGetSegurosBodyResponseBean(GetSegurosBodyResponseBean getSegurosBodyResponseBean2) {
        this.getSegurosBodyResponseBean = getSegurosBodyResponseBean2;
    }
}
