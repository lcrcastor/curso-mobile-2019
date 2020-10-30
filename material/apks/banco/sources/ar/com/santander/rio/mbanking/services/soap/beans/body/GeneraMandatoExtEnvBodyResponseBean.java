package ar.com.santander.rio.mbanking.services.soap.beans.body;

import com.google.gson.annotations.SerializedName;

public class GeneraMandatoExtEnvBodyResponseBean extends ErrorBodyBean {
    @SerializedName("comprobante")
    public GeneraMandatoExtEnvComprobanteBean comprobante;

    public GeneraMandatoExtEnvBodyResponseBean() {
    }

    public GeneraMandatoExtEnvBodyResponseBean(GeneraMandatoExtEnvComprobanteBean generaMandatoExtEnvComprobanteBean) {
        this.comprobante = generaMandatoExtEnvComprobanteBean;
    }
}
