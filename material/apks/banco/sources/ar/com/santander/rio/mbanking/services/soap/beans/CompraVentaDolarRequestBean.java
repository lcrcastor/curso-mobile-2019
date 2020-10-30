package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.CompraVentaDolarBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanWS;
import com.indra.httpclient.json.JSONObject;

public class CompraVentaDolarRequestBean implements IBeanWS {
    @SerializedName("body")
    public CompraVentaDolarBodyRequestBean compraVentaDolarBodyRequestBean;
    @SerializedName("header")
    public PrivateHeaderBean headerBean;

    public void setJsonElement(JSONObject jSONObject) {
    }

    public CompraVentaDolarRequestBean(PrivateHeaderBean privateHeaderBean) {
        this.headerBean = privateHeaderBean;
    }

    public CompraVentaDolarRequestBean(PrivateHeaderBean privateHeaderBean, CompraVentaDolarBodyRequestBean compraVentaDolarBodyRequestBean2) {
        this.headerBean = privateHeaderBean;
        this.compraVentaDolarBodyRequestBean = compraVentaDolarBodyRequestBean2;
    }

    public Class getClassBean() {
        return getClass();
    }
}
