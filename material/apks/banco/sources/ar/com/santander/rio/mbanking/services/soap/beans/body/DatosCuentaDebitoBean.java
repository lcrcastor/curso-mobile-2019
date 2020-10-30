package ar.com.santander.rio.mbanking.services.soap.beans.body;

import com.google.gson.annotations.SerializedName;

public class DatosCuentaDebitoBean {
    @SerializedName("numero")
    private String numero;
    @SerializedName("sucursal")
    private String sucursal;
    @SerializedName("tipo")
    private String tipo;
    @SerializedName("titular")
    private String titular;

    public DatosCuentaDebitoBean(String str, String str2, String str3, String str4) {
        this.tipo = str;
        this.sucursal = str2;
        this.numero = str3;
        this.titular = str4;
    }

    public String getTipo() {
        return this.tipo;
    }

    public String getSucursal() {
        return this.sucursal;
    }

    public String getNumero() {
        return this.numero;
    }

    public String getTitular() {
        return this.titular;
    }
}
