package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.GetTenenciaCreditosBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanErroWS;
import com.indra.httpclient.beans.IBeanWS;
import com.indra.httpclient.json.JSONObject;

public class GetTenenciaCreditosResponseBean implements IBeanErroWS, IBeanWS {
    @SerializedName("body")
    public GetTenenciaCreditosBodyResponseBean getCategoriasBodyResponseBean;
    @SerializedName("header")
    public PrivateHeaderBean headerBean;

    public void setJsonElement(JSONObject jSONObject) {
    }

    public GetTenenciaCreditosResponseBean(PrivateHeaderBean privateHeaderBean, GetTenenciaCreditosBodyResponseBean getTenenciaCreditosBodyResponseBean) {
        this.headerBean = privateHeaderBean;
        this.getCategoriasBodyResponseBean = getTenenciaCreditosBodyResponseBean;
    }

    public GetTenenciaCreditosResponseBean() {
        this.getCategoriasBodyResponseBean = new GetTenenciaCreditosBodyResponseBean();
    }

    public Class getClassBean() {
        return getClass();
    }

    public Object getErrorBeanObject() {
        return this.getCategoriasBodyResponseBean;
    }

    public GetTenenciaCreditosBodyResponseBean getGetCategoriasBodyResponseBean() {
        return this.getCategoriasBodyResponseBean;
    }

    public void setGetCategoriasBodyResponseBean(GetTenenciaCreditosBodyResponseBean getTenenciaCreditosBodyResponseBean) {
        this.getCategoriasBodyResponseBean = getTenenciaCreditosBodyResponseBean;
    }

    public PrivateHeaderBean getHeaderBean() {
        return this.headerBean;
    }

    public void setHeaderBean(PrivateHeaderBean privateHeaderBean) {
        this.headerBean = privateHeaderBean;
    }
}
