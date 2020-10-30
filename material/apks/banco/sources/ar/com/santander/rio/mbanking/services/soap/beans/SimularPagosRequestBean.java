package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.GetSimularPagosBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanWS;
import com.indra.httpclient.json.JSONObject;

public class SimularPagosRequestBean implements IBeanWS {
    @SerializedName("header")
    public PrivateHeaderBean headerBean;
    @SerializedName("body")
    public GetSimularPagosBodyRequestBean mGetSimularPagosBodyRequestBean;

    public void setJsonElement(JSONObject jSONObject) {
    }

    public SimularPagosRequestBean(PrivateHeaderBean privateHeaderBean, GetSimularPagosBodyRequestBean getSimularPagosBodyRequestBean) {
        this.headerBean = privateHeaderBean;
        this.mGetSimularPagosBodyRequestBean = getSimularPagosBodyRequestBean;
    }

    public SimularPagosRequestBean(PrivateHeaderBean privateHeaderBean) {
    }

    public Class getClassBean() {
        return getClass();
    }
}
