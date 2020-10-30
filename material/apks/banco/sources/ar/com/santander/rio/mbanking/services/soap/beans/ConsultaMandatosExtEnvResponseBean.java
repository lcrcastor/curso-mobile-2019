package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.ConsultaMandatosExtEnvBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanErroWS;
import com.indra.httpclient.beans.IBeanWS;
import com.indra.httpclient.json.JSONObject;

public class ConsultaMandatosExtEnvResponseBean implements IBeanErroWS, IBeanWS {
    @SerializedName("body")
    public ConsultaMandatosExtEnvBodyResponseBean consultaMandatosExtEnvBodyResponseBean;
    @SerializedName("header")
    public PrivateHeaderBean headerBean;

    public void setJsonElement(JSONObject jSONObject) {
    }

    public ConsultaMandatosExtEnvResponseBean() {
    }

    public ConsultaMandatosExtEnvResponseBean(PrivateHeaderBean privateHeaderBean, ConsultaMandatosExtEnvBodyResponseBean consultaMandatosExtEnvBodyResponseBean2) {
        this.headerBean = privateHeaderBean;
        this.consultaMandatosExtEnvBodyResponseBean = consultaMandatosExtEnvBodyResponseBean2;
    }

    public Class getClassBean() {
        return getClass();
    }

    public Object getErrorBeanObject() {
        return this.consultaMandatosExtEnvBodyResponseBean;
    }
}
