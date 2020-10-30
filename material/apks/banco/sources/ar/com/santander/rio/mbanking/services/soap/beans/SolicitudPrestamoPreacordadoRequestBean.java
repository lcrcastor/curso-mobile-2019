package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.SolicitudPrestamoPreacordadoBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanWS;

public class SolicitudPrestamoPreacordadoRequestBean extends BaseResponseBean implements IBeanWS {
    @SerializedName("header")
    public PrivateHeaderBean headerBean;
    @SerializedName("body")
    public SolicitudPrestamoPreacordadoBodyRequestBean solicitudPrestamoPreacordadoBodyRequestBean;

    public SolicitudPrestamoPreacordadoRequestBean(PrivateHeaderBean privateHeaderBean, SolicitudPrestamoPreacordadoBodyRequestBean solicitudPrestamoPreacordadoBodyRequestBean2) {
        this.solicitudPrestamoPreacordadoBodyRequestBean = solicitudPrestamoPreacordadoBodyRequestBean2;
        this.headerBean = privateHeaderBean;
    }

    public SolicitudPrestamoPreacordadoRequestBean() {
    }

    public Class getClassBean() {
        return getClass();
    }
}
