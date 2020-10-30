package ar.com.santander.rio.mbanking.services.soap.beans.body;

import com.google.gson.annotations.SerializedName;

public class GeneraMandatoExtBodyRequestBean {
    public String aceptaTyC;
    @SerializedName("mandatario")
    public GeneraMandatoExtEnvMandatarioBean mandatario;
    @SerializedName("mandato")
    public GeneraMandatoExtMandatoBean mandato;
    public String tipoOperacion;

    public GeneraMandatoExtBodyRequestBean() {
    }

    public GeneraMandatoExtBodyRequestBean(String str, String str2, GeneraMandatoExtEnvMandatarioBean generaMandatoExtEnvMandatarioBean, GeneraMandatoExtMandatoBean generaMandatoExtMandatoBean) {
        this.tipoOperacion = str;
        this.aceptaTyC = str2;
        this.mandatario = generaMandatoExtEnvMandatarioBean;
        this.mandato = generaMandatoExtMandatoBean;
    }
}
