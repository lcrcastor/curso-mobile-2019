package ar.com.santander.rio.mbanking.services.soap.beans.body;

import com.google.gson.annotations.SerializedName;

public class CancelaMandatoExtEnvBodyRequestBean {
    @SerializedName("idMandato")
    public String idMandato;

    public CancelaMandatoExtEnvBodyRequestBean() {
    }

    public CancelaMandatoExtEnvBodyRequestBean(String str) {
        this.idMandato = str;
    }
}
