package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.BAgendadosEnvEfeBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanErroWS;
import com.indra.httpclient.beans.IBeanWS;
import com.indra.httpclient.json.JSONObject;

public class BAgendadosEnvEfeResponseBean implements IBeanErroWS, IBeanWS {
    @SerializedName("body")
    public BAgendadosEnvEfeBodyResponseBean bAgendadosEnvEfeBodyResponseBean;
    @SerializedName("header")
    public PrivateHeaderBean headerBean;

    public void setJsonElement(JSONObject jSONObject) {
    }

    public BAgendadosEnvEfeResponseBean() {
    }

    public BAgendadosEnvEfeResponseBean(PrivateHeaderBean privateHeaderBean, BAgendadosEnvEfeBodyResponseBean bAgendadosEnvEfeBodyResponseBean2) {
        this.headerBean = privateHeaderBean;
        this.bAgendadosEnvEfeBodyResponseBean = bAgendadosEnvEfeBodyResponseBean2;
    }

    public Class getClassBean() {
        return getClass();
    }

    public Object getErrorBeanObject() {
        return this.bAgendadosEnvEfeBodyResponseBean;
    }
}
