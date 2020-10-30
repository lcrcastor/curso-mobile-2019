package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.AMAgendadosEnvEfeBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanErroWS;
import com.indra.httpclient.beans.IBeanWS;
import com.indra.httpclient.json.JSONObject;

public class AMAgendadosEnvEfeResponseBean implements IBeanErroWS, IBeanWS {
    @SerializedName("body")
    public AMAgendadosEnvEfeBodyResponseBean amAgendadosEnvEfeBodyResponseBean;
    @SerializedName("header")
    public PrivateHeaderBean headerBean;

    public void setJsonElement(JSONObject jSONObject) {
    }

    public AMAgendadosEnvEfeResponseBean() {
    }

    public AMAgendadosEnvEfeResponseBean(PrivateHeaderBean privateHeaderBean, AMAgendadosEnvEfeBodyResponseBean aMAgendadosEnvEfeBodyResponseBean) {
        this.headerBean = privateHeaderBean;
        this.amAgendadosEnvEfeBodyResponseBean = aMAgendadosEnvEfeBodyResponseBean;
    }

    public Class getClassBean() {
        return getClass();
    }

    public Object getErrorBeanObject() {
        return this.amAgendadosEnvEfeBodyResponseBean;
    }
}
