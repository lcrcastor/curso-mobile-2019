package ar.com.santander.rio.mbanking.services.soap.beans.body;

import com.google.gson.annotations.SerializedName;

public class LimitesYDisponiblesTCBodyRequestBean {
    @SerializedName("codigo_banco")
    private String codigo_banco;
    @SerializedName("numero_cuenta")
    private String numero_cuenta;
    @SerializedName("tarjeta")
    private String tarjeta;

    public LimitesYDisponiblesTCBodyRequestBean(String str, String str2, String str3) {
        this.numero_cuenta = str;
        this.tarjeta = str2;
        this.codigo_banco = str3;
    }

    public LimitesYDisponiblesTCBodyRequestBean() {
    }

    public String getNumero_cuenta() {
        return this.numero_cuenta;
    }

    public void setNumero_cuenta(String str) {
        this.numero_cuenta = str;
    }

    public String getTarjeta() {
        return this.tarjeta;
    }

    public void setTarjeta(String str) {
        this.tarjeta = str;
    }

    public String getCodigo_banco() {
        return this.codigo_banco;
    }

    public void setCodigo_banco(String str) {
        this.codigo_banco = str;
    }
}
