package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.GetLimitesExtraccionBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanErroWS;
import com.indra.httpclient.beans.IBeanWS;
import com.indra.httpclient.json.JSONObject;

public class GetLimitesExtraccionResponseBean implements IBeanErroWS, IBeanWS {
    @SerializedName("body")
    public GetLimitesExtraccionBodyResponseBean getLimitesExtraccionBodyResponseBean;
    @SerializedName("header")
    public PrivateHeaderBean headerBean;

    public void setJsonElement(JSONObject jSONObject) {
    }

    public GetLimitesExtraccionResponseBean() {
    }

    public GetLimitesExtraccionResponseBean(PrivateHeaderBean privateHeaderBean, GetLimitesExtraccionBodyResponseBean getLimitesExtraccionBodyResponseBean2) {
        this.headerBean = privateHeaderBean;
        this.getLimitesExtraccionBodyResponseBean = getLimitesExtraccionBodyResponseBean2;
    }

    public Object getErrorBeanObject() {
        return this.getLimitesExtraccionBodyResponseBean;
    }

    public Class getClassBean() {
        return getClass();
    }

    public void setGetLimitesExtraccionBodyResponseBean(GetLimitesExtraccionBodyResponseBean getLimitesExtraccionBodyResponseBean2) {
        this.getLimitesExtraccionBodyResponseBean = getLimitesExtraccionBodyResponseBean2;
    }
}
