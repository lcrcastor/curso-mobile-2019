package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.ConsultaDatosInicialesTransfBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanWS;

public class ConsultaDatosInicialesTransfRequestBean extends BaseResponseBean implements IBeanWS {
    @SerializedName("body")
    public ConsultaDatosInicialesTransfBodyRequestBean consultaDatosInicialesTransfBodyRequestBean;
    @SerializedName("header")
    public PrivateHeaderBean headerBean;

    public ConsultaDatosInicialesTransfRequestBean(PrivateHeaderBean privateHeaderBean, ConsultaDatosInicialesTransfBodyRequestBean consultaDatosInicialesTransfBodyRequestBean2) {
        this.headerBean = privateHeaderBean;
        this.consultaDatosInicialesTransfBodyRequestBean = consultaDatosInicialesTransfBodyRequestBean2;
    }

    public Class getClassBean() {
        return getClass();
    }

    public ConsultaDatosInicialesTransfBodyRequestBean getconsultaDatosInicialesTransfBodyRequestBean() {
        return this.consultaDatosInicialesTransfBodyRequestBean;
    }

    public void setconsultaDatosInicialesTransfBodyRequestBean(ConsultaDatosInicialesTransfBodyRequestBean consultaDatosInicialesTransfBodyRequestBean2) {
        this.consultaDatosInicialesTransfBodyRequestBean = consultaDatosInicialesTransfBodyRequestBean2;
    }
}
