package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.VerificaDatosInicialesTransfBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanWS;

public class VerificaDatosInicialesTransfRequestBean extends BaseResponseBean implements IBeanWS {
    @SerializedName("header")
    public PrivateHeaderBean headerBean;
    @SerializedName("body")
    public VerificaDatosInicialesTransfBodyRequestBean verificaDatosInicialesTransfBodyRequestBean;

    public VerificaDatosInicialesTransfRequestBean(PrivateHeaderBean privateHeaderBean, VerificaDatosInicialesTransfBodyRequestBean verificaDatosInicialesTransfBodyRequestBean2) {
        this.headerBean = privateHeaderBean;
        this.verificaDatosInicialesTransfBodyRequestBean = verificaDatosInicialesTransfBodyRequestBean2;
    }

    public Class getClassBean() {
        return getClass();
    }

    public VerificaDatosInicialesTransfBodyRequestBean getVerificaDatosInicialesTransfBodyRequestBean() {
        return this.verificaDatosInicialesTransfBodyRequestBean;
    }

    public void setVerificaDatosInicialesTransfBodyRequestBean(VerificaDatosInicialesTransfBodyRequestBean verificaDatosInicialesTransfBodyRequestBean2) {
        this.verificaDatosInicialesTransfBodyRequestBean = verificaDatosInicialesTransfBodyRequestBean2;
    }
}
