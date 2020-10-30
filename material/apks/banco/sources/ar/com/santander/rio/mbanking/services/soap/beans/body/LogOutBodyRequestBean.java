package ar.com.santander.rio.mbanking.services.soap.beans.body;

import com.google.gson.annotations.SerializedName;

public class LogOutBodyRequestBean {
    public String idEncuesta;
    public ListaRespuestas listaRespuestas;
    @SerializedName("sesionUsuario")
    public String sessionUser;

    public LogOutBodyRequestBean() {
    }

    public LogOutBodyRequestBean(String str) {
        this.sessionUser = str;
    }

    public LogOutBodyRequestBean(String str, String str2, ListaRespuestas listaRespuestas2) {
        this.sessionUser = str;
        this.idEncuesta = str2;
        this.listaRespuestas = listaRespuestas2;
    }
}
