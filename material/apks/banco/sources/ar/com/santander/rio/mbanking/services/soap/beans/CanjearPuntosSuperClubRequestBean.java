package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.CanjearPuntosSuperClubBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanWS;
import com.indra.httpclient.json.JSONObject;

public class CanjearPuntosSuperClubRequestBean implements IBeanWS {
    @SerializedName("body")
    public CanjearPuntosSuperClubBodyRequestBean canjearPuntosSuperClubBodyRequestBean;
    @SerializedName("header")
    public PrivateHeaderBean headerBean;

    public void setJsonElement(JSONObject jSONObject) {
    }

    public CanjearPuntosSuperClubRequestBean(PrivateHeaderBean privateHeaderBean, CanjearPuntosSuperClubBodyRequestBean canjearPuntosSuperClubBodyRequestBean2) {
        this.headerBean = privateHeaderBean;
        this.canjearPuntosSuperClubBodyRequestBean = canjearPuntosSuperClubBodyRequestBean2;
    }

    public Class getClassBean() {
        return getClass();
    }
}
