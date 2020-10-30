package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.RegistrarSuscripcionBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanWS;

public class RegistrarSuscripcionRequestBean extends BaseResponseBean implements IBeanWS {
    @SerializedName("header")
    public PrivateHeaderBean headerBean;
    @SerializedName("body")
    public RegistrarSuscripcionBodyRequestBean registrarSuscripcionBodyRequestBean;

    public RegistrarSuscripcionRequestBean(PrivateHeaderBean privateHeaderBean, RegistrarSuscripcionBodyRequestBean registrarSuscripcionBodyRequestBean2) {
        this.headerBean = privateHeaderBean;
        this.registrarSuscripcionBodyRequestBean = registrarSuscripcionBodyRequestBean2;
    }

    public Class getClassBean() {
        return getClass();
    }

    public RegistrarSuscripcionBodyRequestBean getRegistrarSuscripcionBodyRequestBean() {
        return this.registrarSuscripcionBodyRequestBean;
    }

    public void setRegistrarSuscripcionBodyRequestBean(RegistrarSuscripcionBodyRequestBean registrarSuscripcionBodyRequestBean2) {
        this.registrarSuscripcionBodyRequestBean = registrarSuscripcionBodyRequestBean2;
    }
}
