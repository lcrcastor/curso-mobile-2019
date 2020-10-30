package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.ObtenerEstadoSuscripcionMyABodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanWS;

public class ObtenerEstadoSuscripcionMyARequestBean extends BaseResponseBean implements IBeanWS {
    @SerializedName("header")
    public PrivateHeaderBean headerBean;
    @SerializedName("body")
    public ObtenerEstadoSuscripcionMyABodyRequestBean obtenerEstadoSuscripcionMyABodyRequestBean;

    public ObtenerEstadoSuscripcionMyARequestBean() {
    }

    public ObtenerEstadoSuscripcionMyARequestBean(PrivateHeaderBean privateHeaderBean, ObtenerEstadoSuscripcionMyABodyRequestBean obtenerEstadoSuscripcionMyABodyRequestBean2) {
        this.headerBean = privateHeaderBean;
        this.obtenerEstadoSuscripcionMyABodyRequestBean = obtenerEstadoSuscripcionMyABodyRequestBean2;
    }

    public Class getClassBean() {
        return getClass();
    }
}
