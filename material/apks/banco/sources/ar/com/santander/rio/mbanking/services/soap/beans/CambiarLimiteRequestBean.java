package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.CambiarLimiteBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanWS;

public class CambiarLimiteRequestBean extends BaseResponseBean implements IBeanWS {
    @SerializedName("body")
    public CambiarLimiteBodyRequestBean cambiarLimiteBodyRequestBean;
    @SerializedName("header")
    public PrivateHeaderBean headerBean;

    public CambiarLimiteRequestBean() {
    }

    public CambiarLimiteRequestBean(PrivateHeaderBean privateHeaderBean, CambiarLimiteBodyRequestBean cambiarLimiteBodyRequestBean2) {
        this.headerBean = privateHeaderBean;
        this.cambiarLimiteBodyRequestBean = cambiarLimiteBodyRequestBean2;
    }

    public Class getClassBean() {
        return getClass();
    }
}
