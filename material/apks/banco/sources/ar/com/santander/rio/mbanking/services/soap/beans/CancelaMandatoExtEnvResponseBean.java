package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.CancelaMandatoExtEnvBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.HeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanErroWS;
import com.indra.httpclient.beans.IBeanWS;
import com.indra.httpclient.json.JSONObject;

public class CancelaMandatoExtEnvResponseBean implements IBeanErroWS, IBeanWS {
    @SerializedName("body")
    public CancelaMandatoExtEnvBodyResponseBean cancelaMandatoExtEnvBodyResponseBean;
    @SerializedName("header")
    public HeaderBean headerBean;

    public void setJsonElement(JSONObject jSONObject) {
    }

    public CancelaMandatoExtEnvResponseBean() {
    }

    public CancelaMandatoExtEnvResponseBean(HeaderBean headerBean2, CancelaMandatoExtEnvBodyResponseBean cancelaMandatoExtEnvBodyResponseBean2) {
        this.headerBean = headerBean2;
        this.cancelaMandatoExtEnvBodyResponseBean = cancelaMandatoExtEnvBodyResponseBean2;
    }

    public Class getClassBean() {
        return getClass();
    }

    public Object getErrorBeanObject() {
        return this.cancelaMandatoExtEnvBodyResponseBean;
    }
}
