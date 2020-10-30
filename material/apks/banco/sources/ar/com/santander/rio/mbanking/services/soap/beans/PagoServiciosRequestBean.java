package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.PagoServiciosBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanWS;

public class PagoServiciosRequestBean extends BaseResponseBean implements IBeanWS {
    @SerializedName("header")
    public PrivateHeaderBean headerBean;
    @SerializedName("body")
    public PagoServiciosBodyRequestBean pagoServiciosBodyRequestBean;

    public PagoServiciosRequestBean() {
    }

    public PagoServiciosRequestBean(PrivateHeaderBean privateHeaderBean, PagoServiciosBodyRequestBean pagoServiciosBodyRequestBean2) {
        this.headerBean = privateHeaderBean;
        this.pagoServiciosBodyRequestBean = pagoServiciosBodyRequestBean2;
    }

    public Class getClassBean() {
        return getClass();
    }
}
