package ar.com.santander.rio.mbanking.services.soap.beans.body;

import com.google.gson.annotations.SerializedName;

public class VerificaDatosInicialesTransfOBBodyRequestBean {
    @SerializedName("verificaDatos")
    private VerificaDatosOBBean verificaDatosOBBean;

    public VerificaDatosInicialesTransfOBBodyRequestBean() {
    }

    public VerificaDatosInicialesTransfOBBodyRequestBean(VerificaDatosOBBean verificaDatosOBBean2) {
        this.verificaDatosOBBean = verificaDatosOBBean2;
    }

    public VerificaDatosOBBean getVerificaDatosOBBean() {
        return this.verificaDatosOBBean;
    }
}
