package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.VerificaDatosInicialesTransfOBBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanWS;

public class VerificaDatosInicialesTransfOBRequestBean extends BaseResponseBean implements IBeanWS {
    @SerializedName("header")
    public PrivateHeaderBean headerBean;
    @SerializedName("body")
    public VerificaDatosInicialesTransfOBBodyRequestBean verificaDatosInicialesTransfOBBodyRequestBean;

    public VerificaDatosInicialesTransfOBRequestBean(PrivateHeaderBean privateHeaderBean, VerificaDatosInicialesTransfOBBodyRequestBean verificaDatosInicialesTransfOBBodyRequestBean2) {
        this.headerBean = privateHeaderBean;
        this.verificaDatosInicialesTransfOBBodyRequestBean = verificaDatosInicialesTransfOBBodyRequestBean2;
    }

    public Class getClassBean() {
        return getClass();
    }

    public VerificaDatosInicialesTransfOBBodyRequestBean getVerificaDatosInicialesTransfOBBodyRequestBean() {
        return this.verificaDatosInicialesTransfOBBodyRequestBean;
    }

    public void VerificaDatosInicialesTransfOBBodyRequestBean(VerificaDatosInicialesTransfOBBodyRequestBean verificaDatosInicialesTransfOBBodyRequestBean2) {
        this.verificaDatosInicialesTransfOBBodyRequestBean = verificaDatosInicialesTransfOBBodyRequestBean2;
    }
}
