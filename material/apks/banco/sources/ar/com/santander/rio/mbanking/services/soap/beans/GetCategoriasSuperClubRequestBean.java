package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.GetCategoriasSuperClubBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanWS;
import com.indra.httpclient.json.JSONObject;

public class GetCategoriasSuperClubRequestBean implements IBeanWS {
    @SerializedName("body")
    public GetCategoriasSuperClubBodyRequestBean getCategoriasSuperClubBodyRequestBean;
    @SerializedName("header")
    public PrivateHeaderBean headerBean;

    public void setJsonElement(JSONObject jSONObject) {
    }

    public GetCategoriasSuperClubRequestBean(PrivateHeaderBean privateHeaderBean) {
        this.headerBean = privateHeaderBean;
        this.getCategoriasSuperClubBodyRequestBean = new GetCategoriasSuperClubBodyRequestBean();
    }

    public GetCategoriasSuperClubRequestBean(PrivateHeaderBean privateHeaderBean, GetCategoriasSuperClubBodyRequestBean getCategoriasSuperClubBodyRequestBean2) {
        this.headerBean = privateHeaderBean;
        this.getCategoriasSuperClubBodyRequestBean = getCategoriasSuperClubBodyRequestBean2;
    }

    public Class getClassBean() {
        return getClass();
    }
}
