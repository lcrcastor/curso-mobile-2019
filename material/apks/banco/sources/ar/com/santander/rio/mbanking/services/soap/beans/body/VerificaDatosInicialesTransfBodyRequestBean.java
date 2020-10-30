package ar.com.santander.rio.mbanking.services.soap.beans.body;

import com.google.gson.annotations.SerializedName;

public class VerificaDatosInicialesTransfBodyRequestBean {
    @SerializedName("verificaDatos")
    private VerificaDatosBean verificaDatosBean;

    public VerificaDatosInicialesTransfBodyRequestBean() {
    }

    public VerificaDatosInicialesTransfBodyRequestBean(VerificaDatosBean verificaDatosBean2) {
        this.verificaDatosBean = verificaDatosBean2;
    }

    public VerificaDatosBean getVerificaDatosBean() {
        return this.verificaDatosBean;
    }
}
