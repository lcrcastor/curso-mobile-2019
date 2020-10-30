package ar.com.santander.rio.mbanking.services.soap.beans.body;

import com.google.gson.annotations.SerializedName;

public class CargaDatosInicialesExtEnvBodyRequestBean {
    @SerializedName("tipoOperacion")
    public String tipoOperacion;

    public CargaDatosInicialesExtEnvBodyRequestBean() {
    }

    public CargaDatosInicialesExtEnvBodyRequestBean(String str) {
        this.tipoOperacion = str;
    }
}
