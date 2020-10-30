package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.AMAgendadosEnvEfeBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanWS;
import com.indra.httpclient.json.JSONObject;

public class AMAgendadosEnvEfeRequestBean implements IBeanWS {
    @SerializedName("body")
    public AMAgendadosEnvEfeBodyRequestBean amAgendadosEnvEfeBodyRequestBean;
    @SerializedName("header")
    public PrivateHeaderBean headerBean;

    public void setJsonElement(JSONObject jSONObject) {
    }

    public AMAgendadosEnvEfeRequestBean() {
    }

    public AMAgendadosEnvEfeRequestBean(PrivateHeaderBean privateHeaderBean, AMAgendadosEnvEfeBodyRequestBean aMAgendadosEnvEfeBodyRequestBean) {
        this.headerBean = privateHeaderBean;
        this.amAgendadosEnvEfeBodyRequestBean = aMAgendadosEnvEfeBodyRequestBean;
    }

    public Class getClassBean() {
        return getClass();
    }
}
