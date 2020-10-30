package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.SolicitudPrestamoPreacordadoBodyRequestBeanProd;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanWS;

public class SolicitudPrestamoPreacordadoRequestBeanProd extends BaseResponseBean implements IBeanWS {
    @SerializedName("header")
    public PrivateHeaderBean headerBean;
    @SerializedName("body")
    public SolicitudPrestamoPreacordadoBodyRequestBeanProd solicitudPrestamoPreacordadoBodyRequestBeanProd;

    public SolicitudPrestamoPreacordadoRequestBeanProd(PrivateHeaderBean privateHeaderBean, SolicitudPrestamoPreacordadoBodyRequestBeanProd solicitudPrestamoPreacordadoBodyRequestBeanProd2) {
        this.solicitudPrestamoPreacordadoBodyRequestBeanProd = solicitudPrestamoPreacordadoBodyRequestBeanProd2;
        this.headerBean = privateHeaderBean;
    }

    public SolicitudPrestamoPreacordadoRequestBeanProd() {
    }

    public Class getClassBean() {
        return getClass();
    }
}
