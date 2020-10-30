package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.SolicitudPrestamoPreacordadoBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanErroWS;
import com.indra.httpclient.beans.IBeanWS;
import com.indra.httpclient.json.JSONObject;

public class SolicitudPrestamoPreacordadoResponseBean implements IBeanErroWS, IBeanWS {
    @SerializedName("header")
    public PrivateHeaderBean headerBean;
    @SerializedName("body")
    public SolicitudPrestamoPreacordadoBodyResponseBean solicitudPrestamoPreacordadoBodyResponseBean;

    public void setJsonElement(JSONObject jSONObject) {
    }

    public SolicitudPrestamoPreacordadoResponseBean() {
    }

    public SolicitudPrestamoPreacordadoResponseBean(PrivateHeaderBean privateHeaderBean, SolicitudPrestamoPreacordadoBodyResponseBean solicitudPrestamoPreacordadoBodyResponseBean2) {
        this.headerBean = privateHeaderBean;
        this.solicitudPrestamoPreacordadoBodyResponseBean = solicitudPrestamoPreacordadoBodyResponseBean2;
    }

    public Class getClassBean() {
        return getClass();
    }

    public Object getErrorBeanObject() {
        return this.solicitudPrestamoPreacordadoBodyResponseBean;
    }
}
