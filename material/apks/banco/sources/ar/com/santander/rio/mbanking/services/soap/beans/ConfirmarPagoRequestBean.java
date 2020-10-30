package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.ConfirmarPagoBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanWS;
import com.indra.httpclient.json.JSONObject;

public class ConfirmarPagoRequestBean implements IBeanWS {
    @SerializedName("header")
    public PrivateHeaderBean headerBean;
    @SerializedName("body")
    public ConfirmarPagoBodyRequestBean mGeConfirmarPagoBodyRequestBean;

    public void setJsonElement(JSONObject jSONObject) {
    }

    public ConfirmarPagoRequestBean(PrivateHeaderBean privateHeaderBean, ConfirmarPagoBodyRequestBean confirmarPagoBodyRequestBean) {
        this.headerBean = privateHeaderBean;
        this.mGeConfirmarPagoBodyRequestBean = confirmarPagoBodyRequestBean;
    }

    public ConfirmarPagoRequestBean(PrivateHeaderBean privateHeaderBean) {
    }

    public Class getClassBean() {
        return getClass();
    }
}
