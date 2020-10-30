package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.SimulacionDolarBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanErroWS;
import com.indra.httpclient.beans.IBeanWS;
import com.indra.httpclient.json.JSONObject;

public class SimulacionDolarResponseBean implements IBeanErroWS, IBeanWS {
    @SerializedName("header")
    public PrivateHeaderBean headerBean;
    @SerializedName("body")
    public SimulacionDolarBodyResponseBean simulacionDolarBodyResponseBean;

    public void setJsonElement(JSONObject jSONObject) {
    }

    public SimulacionDolarResponseBean(PrivateHeaderBean privateHeaderBean, SimulacionDolarBodyResponseBean simulacionDolarBodyResponseBean2) {
        this.headerBean = privateHeaderBean;
        this.simulacionDolarBodyResponseBean = simulacionDolarBodyResponseBean2;
    }

    public SimulacionDolarResponseBean() {
    }

    public Class getClassBean() {
        return getClass();
    }

    public Object getErrorBeanObject() {
        return this.simulacionDolarBodyResponseBean;
    }
}
