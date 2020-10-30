package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.ConsultaSuscripcionMyAResponeBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanErroWS;
import com.indra.httpclient.beans.IBeanWS;
import com.indra.httpclient.json.JSONObject;

public class ConsultaSuscripcionMyAResponeBean implements IBeanErroWS, IBeanWS {
    @SerializedName("body")
    public ConsultaSuscripcionMyAResponeBodyResponseBean consultaSuscripcionMyAResponeBodyResponseBean;
    @SerializedName("header")
    public PrivateHeaderBean headerBean;

    public void setJsonElement(JSONObject jSONObject) {
    }

    public ConsultaSuscripcionMyAResponeBean() {
    }

    public ConsultaSuscripcionMyAResponeBean(PrivateHeaderBean privateHeaderBean, ConsultaSuscripcionMyAResponeBodyResponseBean consultaSuscripcionMyAResponeBodyResponseBean2) {
        this.headerBean = privateHeaderBean;
        this.consultaSuscripcionMyAResponeBodyResponseBean = consultaSuscripcionMyAResponeBodyResponseBean2;
    }

    public Class getClassBean() {
        return getClass();
    }

    public Object getErrorBeanObject() {
        return this.consultaSuscripcionMyAResponeBodyResponseBean;
    }
}
