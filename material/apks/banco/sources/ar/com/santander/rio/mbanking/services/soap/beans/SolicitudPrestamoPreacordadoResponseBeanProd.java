package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.SolicitudPrestamoPreacordadoBodyResponseBeanProd;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanErroWS;
import com.indra.httpclient.beans.IBeanWS;
import com.indra.httpclient.json.JSONObject;

public class SolicitudPrestamoPreacordadoResponseBeanProd implements IBeanErroWS, IBeanWS {
    @SerializedName("header")
    public PrivateHeaderBean headerBean;
    @SerializedName("body")
    public SolicitudPrestamoPreacordadoBodyResponseBeanProd solicitudPrestamoPreacordadoBodyResponseBeanProd;

    public void setJsonElement(JSONObject jSONObject) {
    }

    public SolicitudPrestamoPreacordadoResponseBeanProd() {
    }

    public SolicitudPrestamoPreacordadoResponseBeanProd(PrivateHeaderBean privateHeaderBean, SolicitudPrestamoPreacordadoBodyResponseBeanProd solicitudPrestamoPreacordadoBodyResponseBeanProd2) {
        this.headerBean = privateHeaderBean;
        this.solicitudPrestamoPreacordadoBodyResponseBeanProd = solicitudPrestamoPreacordadoBodyResponseBeanProd2;
    }

    public Class getClassBean() {
        return getClass();
    }

    public Object getErrorBeanObject() {
        return this.solicitudPrestamoPreacordadoBodyResponseBeanProd;
    }
}
