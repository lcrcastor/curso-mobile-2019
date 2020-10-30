package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.CargaDatosInicialesExtEnvBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanErroWS;
import com.indra.httpclient.beans.IBeanWS;
import com.indra.httpclient.json.JSONObject;

public class CargaDatosInicialesExtEnvResponseBean implements IBeanErroWS, IBeanWS {
    @SerializedName("body")
    public CargaDatosInicialesExtEnvBodyResponseBean cargaDatosInicialesExtEnvBodyResponseBean;
    @SerializedName("header")
    public PrivateHeaderBean headerBean;

    public void setJsonElement(JSONObject jSONObject) {
    }

    public CargaDatosInicialesExtEnvResponseBean() {
    }

    public CargaDatosInicialesExtEnvResponseBean(PrivateHeaderBean privateHeaderBean, CargaDatosInicialesExtEnvBodyResponseBean cargaDatosInicialesExtEnvBodyResponseBean2) {
        this.headerBean = privateHeaderBean;
        this.cargaDatosInicialesExtEnvBodyResponseBean = cargaDatosInicialesExtEnvBodyResponseBean2;
    }

    public Class getClassBean() {
        return getClass();
    }

    public Object getErrorBeanObject() {
        return this.cargaDatosInicialesExtEnvBodyResponseBean;
    }
}
