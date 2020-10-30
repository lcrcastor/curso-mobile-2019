package ar.com.santander.rio.mbanking.services.soap.beans.body;

import com.google.gson.annotations.SerializedName;

public class ConsultaMandatosExtEnvBodyResponseBean extends ErrorBodyBean {
    @SerializedName("listaMandatos")
    public ConsultaMandatosExtEnvListaMandatosBean listaMandatos;

    public ConsultaMandatosExtEnvBodyResponseBean() {
    }

    public ConsultaMandatosExtEnvBodyResponseBean(ConsultaMandatosExtEnvListaMandatosBean consultaMandatosExtEnvListaMandatosBean) {
        this.listaMandatos = consultaMandatosExtEnvListaMandatosBean;
    }
}
