package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.ConsultaAgendadosEnvEfeBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanErroWS;
import com.indra.httpclient.beans.IBeanWS;
import com.indra.httpclient.json.JSONObject;

public class ConsultaAgendadosEnvEfeResponseBean implements IBeanErroWS, IBeanWS {
    @SerializedName("body")
    public ConsultaAgendadosEnvEfeBodyResponseBean consultaAgendadosEnvEfeBodyResponseBean;
    @SerializedName("header")
    public PrivateHeaderBean headerBean;

    public void setJsonElement(JSONObject jSONObject) {
    }

    public ConsultaAgendadosEnvEfeResponseBean() {
    }

    public ConsultaAgendadosEnvEfeResponseBean(PrivateHeaderBean privateHeaderBean, ConsultaAgendadosEnvEfeBodyResponseBean consultaAgendadosEnvEfeBodyResponseBean2) {
        this.headerBean = privateHeaderBean;
        this.consultaAgendadosEnvEfeBodyResponseBean = consultaAgendadosEnvEfeBodyResponseBean2;
    }

    public Class getClassBean() {
        return getClass();
    }

    public Object getErrorBeanObject() {
        return this.consultaAgendadosEnvEfeBodyResponseBean;
    }
}
