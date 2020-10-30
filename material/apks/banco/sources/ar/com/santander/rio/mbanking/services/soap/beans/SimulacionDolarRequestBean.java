package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.SimulacionDolarBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanWS;
import com.indra.httpclient.json.JSONObject;

public class SimulacionDolarRequestBean implements IBeanWS {
    @SerializedName("header")
    public PrivateHeaderBean headerBean;
    @SerializedName("body")
    public SimulacionDolarBodyRequestBean simulacionDolarBodyRequestBean;

    public void setJsonElement(JSONObject jSONObject) {
    }

    public SimulacionDolarRequestBean(PrivateHeaderBean privateHeaderBean) {
        this.headerBean = privateHeaderBean;
    }

    public SimulacionDolarRequestBean(PrivateHeaderBean privateHeaderBean, SimulacionDolarBodyRequestBean simulacionDolarBodyRequestBean2) {
        this.headerBean = privateHeaderBean;
        this.simulacionDolarBodyRequestBean = simulacionDolarBodyRequestBean2;
    }

    public Class getClassBean() {
        return getClass();
    }
}
