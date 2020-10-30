package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.RegistrarSuscripcionBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanErroWS;
import com.indra.httpclient.beans.IBeanWS;

public class RegistrarSuscripcionResponseBean extends BaseResponseBean implements IBeanErroWS, IBeanWS {
    @SerializedName("header")
    public PrivateHeaderBean headerBean;
    @SerializedName("body")
    public RegistrarSuscripcionBodyResponseBean registrarSuscripcionBodyResponseBean;

    public RegistrarSuscripcionResponseBean(RegistrarSuscripcionBodyResponseBean registrarSuscripcionBodyResponseBean2) {
        this.registrarSuscripcionBodyResponseBean = registrarSuscripcionBodyResponseBean2;
    }

    public RegistrarSuscripcionResponseBean() {
    }

    public Class getClassBean() {
        return getClass();
    }

    public Object getErrorBeanObject() {
        return this.registrarSuscripcionBodyResponseBean;
    }

    public RegistrarSuscripcionBodyResponseBean getRegistrarSuscripcionBodyResponseBean() {
        return this.registrarSuscripcionBodyResponseBean;
    }

    public void setRegistrarSuscripcionBodyResponseBean(RegistrarSuscripcionBodyResponseBean registrarSuscripcionBodyResponseBean2) {
        this.registrarSuscripcionBodyResponseBean = registrarSuscripcionBodyResponseBean2;
    }
}
