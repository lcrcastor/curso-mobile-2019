package ar.com.santander.rio.mbanking.services.soap.beans.body;

import com.google.gson.annotations.SerializedName;

public class GeneraMandatoEnvBodyRequestBean {
    public String aceptaTyC;
    @SerializedName("mandatario")
    public GeneraMandatoExtEnvMandatarioBean mandatario;
    @SerializedName("mandato")
    public GeneraMandatoEnvMandatoBean mandato;
    public String tipoOperacion;

    public GeneraMandatoEnvBodyRequestBean() {
    }

    public GeneraMandatoEnvBodyRequestBean(String str, String str2, GeneraMandatoExtEnvMandatarioBean generaMandatoExtEnvMandatarioBean, GeneraMandatoEnvMandatoBean generaMandatoEnvMandatoBean) {
        this.tipoOperacion = str;
        this.aceptaTyC = str2;
        this.mandatario = generaMandatoExtEnvMandatarioBean;
        this.mandato = generaMandatoEnvMandatoBean;
    }
}
