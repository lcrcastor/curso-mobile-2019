package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.GeneraMandatoExtEnvBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.HeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanErroWS;
import com.indra.httpclient.beans.IBeanWS;
import com.indra.httpclient.json.JSONObject;

public class GeneraMandatoExtEnvResponseBean implements IBeanErroWS, IBeanWS {
    @SerializedName("body")
    public GeneraMandatoExtEnvBodyResponseBean generaMandatoExtEnvBodyResponseBean;
    @SerializedName("header")
    public HeaderBean headerBean;
    private JSONObject jsonObject;

    public void setJsonElement(JSONObject jSONObject) {
    }

    public GeneraMandatoExtEnvResponseBean() {
    }

    public GeneraMandatoExtEnvResponseBean(HeaderBean headerBean2, GeneraMandatoExtEnvBodyResponseBean generaMandatoExtEnvBodyResponseBean2) {
        this.headerBean = headerBean2;
        this.generaMandatoExtEnvBodyResponseBean = generaMandatoExtEnvBodyResponseBean2;
    }

    public Class getClassBean() {
        return getClass();
    }

    public Object getErrorBeanObject() {
        return this.generaMandatoExtEnvBodyResponseBean;
    }
}
