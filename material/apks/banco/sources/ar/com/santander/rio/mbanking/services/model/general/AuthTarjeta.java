package ar.com.santander.rio.mbanking.services.model.general;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;

public class AuthTarjeta {
    @SerializedName("autorizacion")
    private List<Autorizacion> autorizaciones;
    @SerializedName("dolares")
    private String dolares;
    @SerializedName("numero")
    private String numero;
    @SerializedName("pesos")
    private String pesos;

    public AuthTarjeta(String str, String str2, String str3, List<Autorizacion> list) {
        this.dolares = "";
        this.pesos = "";
        this.numero = "";
        this.dolares = str;
        this.pesos = str2;
        this.numero = str3;
        this.autorizaciones = list;
    }

    public AuthTarjeta() {
        this.dolares = "";
        this.pesos = "";
        this.numero = "";
        this.autorizaciones = new ArrayList();
    }

    public String getDolares() {
        return this.dolares;
    }

    public void setDolares(String str) {
        this.dolares = str;
    }

    public String getPesos() {
        return this.pesos;
    }

    public void setPesos(String str) {
        this.pesos = str;
    }

    public List<Autorizacion> getAutorizacion() {
        return this.autorizaciones;
    }

    public void setAutorizacion(List<Autorizacion> list) {
        this.autorizaciones = list;
    }

    public String getNumero() {
        return this.numero;
    }

    public void setNumero(String str) {
        this.numero = str;
    }
}
