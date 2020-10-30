package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.BAgendadosEnvEfeBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanWS;
import com.indra.httpclient.json.JSONObject;

public class BAgendadosEnvEfeRequestBean implements IBeanWS {
    @SerializedName("body")
    public BAgendadosEnvEfeBodyRequestBean bAgendadosEnvEfeBodyRequestBean;
    @SerializedName("header")
    public PrivateHeaderBean headerBean;

    public void setJsonElement(JSONObject jSONObject) {
    }

    public BAgendadosEnvEfeRequestBean() {
    }

    public BAgendadosEnvEfeRequestBean(PrivateHeaderBean privateHeaderBean, BAgendadosEnvEfeBodyRequestBean bAgendadosEnvEfeBodyRequestBean2) {
        this.headerBean = privateHeaderBean;
        this.bAgendadosEnvEfeBodyRequestBean = bAgendadosEnvEfeBodyRequestBean2;
    }

    public Class getClassBean() {
        return getClass();
    }
}
