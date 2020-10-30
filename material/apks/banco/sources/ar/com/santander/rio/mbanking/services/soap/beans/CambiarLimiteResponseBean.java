package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.CambiarLimiteBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanErroWS;
import com.indra.httpclient.beans.IBeanWS;
import com.indra.httpclient.json.JSONObject;

public class CambiarLimiteResponseBean implements IBeanErroWS, IBeanWS {
    @SerializedName("body")
    public CambiarLimiteBodyResponseBean cambiarLimiteBodyResponseBean;
    @SerializedName("header")
    public PrivateHeaderBean headerBean;

    public void setJsonElement(JSONObject jSONObject) {
    }

    public CambiarLimiteResponseBean() {
    }

    public CambiarLimiteResponseBean(PrivateHeaderBean privateHeaderBean, CambiarLimiteBodyResponseBean cambiarLimiteBodyResponseBean2) {
        this.headerBean = privateHeaderBean;
        this.cambiarLimiteBodyResponseBean = cambiarLimiteBodyResponseBean2;
    }

    public Object getErrorBeanObject() {
        return this.cambiarLimiteBodyResponseBean;
    }

    public Class getClassBean() {
        return getClass();
    }

    public void setCambiarLimiteBodyResponseBean(CambiarLimiteBodyResponseBean cambiarLimiteBodyResponseBean2) {
        this.cambiarLimiteBodyResponseBean = cambiarLimiteBodyResponseBean2;
    }
}
