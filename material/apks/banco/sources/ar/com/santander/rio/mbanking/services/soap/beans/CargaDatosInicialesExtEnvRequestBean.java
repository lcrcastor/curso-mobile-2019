package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.CargaDatosInicialesExtEnvBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanWS;
import com.indra.httpclient.json.JSONObject;

public class CargaDatosInicialesExtEnvRequestBean implements IBeanWS {
    @SerializedName("body")
    public CargaDatosInicialesExtEnvBodyRequestBean cargaDatosInicialesExtEnvBodyRequestBean;
    @SerializedName("header")
    public PrivateHeaderBean headerBean;

    public void setJsonElement(JSONObject jSONObject) {
    }

    public CargaDatosInicialesExtEnvRequestBean(PrivateHeaderBean privateHeaderBean, CargaDatosInicialesExtEnvBodyRequestBean cargaDatosInicialesExtEnvBodyRequestBean2) {
        this.headerBean = privateHeaderBean;
        this.cargaDatosInicialesExtEnvBodyRequestBean = cargaDatosInicialesExtEnvBodyRequestBean2;
    }

    public Class getClassBean() {
        return getClass();
    }
}
