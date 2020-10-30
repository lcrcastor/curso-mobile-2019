package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.CnsTenenciasBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanWS;
import com.indra.httpclient.json.JSONObject;

public class CnsTenenciasRequestBean implements IBeanWS {
    @SerializedName("body")
    public CnsTenenciasBodyRequestBean cnsTenenciasBodyRequestBean;
    @SerializedName("header")
    public PrivateHeaderBean headerBean;

    public void setJsonElement(JSONObject jSONObject) {
    }

    public CnsTenenciasRequestBean(PrivateHeaderBean privateHeaderBean) {
        this.headerBean = privateHeaderBean;
    }

    public CnsTenenciasRequestBean(PrivateHeaderBean privateHeaderBean, CnsTenenciasBodyRequestBean cnsTenenciasBodyRequestBean2) {
        this.headerBean = privateHeaderBean;
        this.cnsTenenciasBodyRequestBean = cnsTenenciasBodyRequestBean2;
    }

    public Class getClassBean() {
        return getClass();
    }
}
