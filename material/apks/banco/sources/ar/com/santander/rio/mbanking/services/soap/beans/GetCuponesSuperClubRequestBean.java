package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.GetCuponesSuperClubBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanWS;
import com.indra.httpclient.json.JSONObject;

public class GetCuponesSuperClubRequestBean implements IBeanWS {
    @SerializedName("body")
    public GetCuponesSuperClubBodyRequestBean getCuponesSuperClubBodyRequestBean;
    @SerializedName("header")
    public PrivateHeaderBean headerBean;

    public void setJsonElement(JSONObject jSONObject) {
    }

    public GetCuponesSuperClubRequestBean(PrivateHeaderBean privateHeaderBean, GetCuponesSuperClubBodyRequestBean getCuponesSuperClubBodyRequestBean2) {
        this.headerBean = privateHeaderBean;
        this.getCuponesSuperClubBodyRequestBean = getCuponesSuperClubBodyRequestBean2;
    }

    public Class getClassBean() {
        return getClass();
    }
}
