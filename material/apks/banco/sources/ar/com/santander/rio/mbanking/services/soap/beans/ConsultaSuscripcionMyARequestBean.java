package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.ConsultaSuscripcionMyABodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanWS;

public class ConsultaSuscripcionMyARequestBean extends BaseResponseBean implements IBeanWS {
    @SerializedName("body")
    public ConsultaSuscripcionMyABodyRequestBean consultaSuscripcionMyABodyRequestBean;
    @SerializedName("header")
    public PrivateHeaderBean headerBean;

    public ConsultaSuscripcionMyARequestBean() {
    }

    public ConsultaSuscripcionMyARequestBean(PrivateHeaderBean privateHeaderBean, ConsultaSuscripcionMyABodyRequestBean consultaSuscripcionMyABodyRequestBean2) {
        this.headerBean = privateHeaderBean;
        this.consultaSuscripcionMyABodyRequestBean = consultaSuscripcionMyABodyRequestBean2;
    }

    public Class getClassBean() {
        return getClass();
    }
}
