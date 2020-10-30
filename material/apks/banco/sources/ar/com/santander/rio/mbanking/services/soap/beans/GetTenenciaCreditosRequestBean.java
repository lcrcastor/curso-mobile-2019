package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.GetCategoriasSuperClubBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetTenenciaCreditosBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanWS;
import com.indra.httpclient.json.JSONObject;

public class GetTenenciaCreditosRequestBean implements IBeanWS {
    @SerializedName("body")
    public GetTenenciaCreditosBodyRequestBean getTenenciaCreditosBodyRequestBean = new GetTenenciaCreditosBodyRequestBean();
    @SerializedName("header")
    public PrivateHeaderBean headerBean;

    public void setJsonElement(JSONObject jSONObject) {
    }

    public GetTenenciaCreditosRequestBean(PrivateHeaderBean privateHeaderBean) {
        this.headerBean = privateHeaderBean;
    }

    public GetTenenciaCreditosRequestBean(PrivateHeaderBean privateHeaderBean, GetCategoriasSuperClubBodyRequestBean getCategoriasSuperClubBodyRequestBean) {
        this.headerBean = privateHeaderBean;
    }

    public Class getClassBean() {
        return getClass();
    }
}
