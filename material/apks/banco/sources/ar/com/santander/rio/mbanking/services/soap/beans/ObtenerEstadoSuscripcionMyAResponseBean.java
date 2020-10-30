package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.ObtenerEstadoSuscripcionMyABodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.HeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanErroWS;
import com.indra.httpclient.beans.IBeanWS;

public class ObtenerEstadoSuscripcionMyAResponseBean extends BaseResponseBean implements IBeanErroWS, IBeanWS {
    @SerializedName("header")
    public HeaderBean headerBean;
    @SerializedName("body")
    public ObtenerEstadoSuscripcionMyABodyResponseBean obtenerEstadoSuscripcionMyABodyResponseBean;

    public ObtenerEstadoSuscripcionMyAResponseBean() {
        this.headerBean = new HeaderBean();
        this.obtenerEstadoSuscripcionMyABodyResponseBean = new ObtenerEstadoSuscripcionMyABodyResponseBean();
    }

    public ObtenerEstadoSuscripcionMyAResponseBean(ObtenerEstadoSuscripcionMyABodyResponseBean obtenerEstadoSuscripcionMyABodyResponseBean2) {
        this.obtenerEstadoSuscripcionMyABodyResponseBean = obtenerEstadoSuscripcionMyABodyResponseBean2;
    }

    public ObtenerEstadoSuscripcionMyAResponseBean(HeaderBean headerBean2, ObtenerEstadoSuscripcionMyABodyResponseBean obtenerEstadoSuscripcionMyABodyResponseBean2) {
        this.headerBean = headerBean2;
        this.obtenerEstadoSuscripcionMyABodyResponseBean = obtenerEstadoSuscripcionMyABodyResponseBean2;
    }

    public Class getClassBean() {
        return getClass();
    }

    public Object getErrorBeanObject() {
        return this.obtenerEstadoSuscripcionMyABodyResponseBean;
    }

    public HeaderBean getHeaderBean() {
        return this.headerBean;
    }

    public void setHeaderBean(HeaderBean headerBean2) {
        this.headerBean = headerBean2;
    }

    public ObtenerEstadoSuscripcionMyABodyResponseBean getObtenerEstadoSuscripcionMyABodyResponseBean() {
        return this.obtenerEstadoSuscripcionMyABodyResponseBean;
    }

    public void setObtenerEstadoSuscripcionMyABodyResponseBean(ObtenerEstadoSuscripcionMyABodyResponseBean obtenerEstadoSuscripcionMyABodyResponseBean2) {
        this.obtenerEstadoSuscripcionMyABodyResponseBean = obtenerEstadoSuscripcionMyABodyResponseBean2;
    }
}
