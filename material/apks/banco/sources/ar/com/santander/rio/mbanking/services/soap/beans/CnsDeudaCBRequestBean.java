package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.CnsDeudaCBBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanWS;
import com.indra.httpclient.json.JSONObject;

public class CnsDeudaCBRequestBean implements IBeanWS {
    @SerializedName("body")
    public CnsDeudaCBBodyRequestBean cnsDeudaCBBodyRequestBean;
    @SerializedName("header")
    public PrivateHeaderBean headerBean;

    public void setJsonElement(JSONObject jSONObject) {
    }

    public CnsDeudaCBRequestBean(PrivateHeaderBean privateHeaderBean) {
        this.headerBean = privateHeaderBean;
    }

    public CnsDeudaCBRequestBean(PrivateHeaderBean privateHeaderBean, CnsDeudaCBBodyRequestBean cnsDeudaCBBodyRequestBean2) {
        this.headerBean = privateHeaderBean;
        this.cnsDeudaCBBodyRequestBean = cnsDeudaCBBodyRequestBean2;
    }

    public Class getClassBean() {
        return getClass();
    }
}
