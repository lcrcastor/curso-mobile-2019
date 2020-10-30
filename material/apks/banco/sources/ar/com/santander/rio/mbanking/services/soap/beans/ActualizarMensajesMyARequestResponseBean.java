package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.ErrorBodyBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanErroWS;
import com.indra.httpclient.beans.IBeanWS;
import com.indra.httpclient.json.JSONObject;

public class ActualizarMensajesMyARequestResponseBean extends BaseResponseBean implements IBeanErroWS, IBeanWS {
    @SerializedName("body")
    public ErrorBodyBean errorBodyBean;
    @SerializedName("header")
    public PrivateHeaderBean headerBean;

    public void setJsonElement(JSONObject jSONObject) {
    }

    public ActualizarMensajesMyARequestResponseBean() {
    }

    public ActualizarMensajesMyARequestResponseBean(PrivateHeaderBean privateHeaderBean, ErrorBodyBean errorBodyBean2) {
        this.headerBean = privateHeaderBean;
        this.errorBodyBean = errorBodyBean2;
    }

    public Class getClassBean() {
        return getClass();
    }

    public Object getErrorBeanObject() {
        return this.errorBodyBean;
    }
}
