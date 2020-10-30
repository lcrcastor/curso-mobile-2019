package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.PagoServicioCBBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanWS;
import com.indra.httpclient.json.JSONObject;

public class PagoServicioCBRequestBean implements IBeanWS {
    @SerializedName("header")
    public PrivateHeaderBean headerBean;
    @SerializedName("body")
    public PagoServicioCBBodyRequestBean pagoServicioCBBodyRequestBean;

    public void setJsonElement(JSONObject jSONObject) {
    }

    public PagoServicioCBRequestBean(PrivateHeaderBean privateHeaderBean) {
        this.headerBean = privateHeaderBean;
    }

    public PagoServicioCBRequestBean(PrivateHeaderBean privateHeaderBean, PagoServicioCBBodyRequestBean pagoServicioCBBodyRequestBean2) {
        this.headerBean = privateHeaderBean;
        this.pagoServicioCBBodyRequestBean = pagoServicioCBBodyRequestBean2;
    }

    public Class getClassBean() {
        return getClass();
    }
}
