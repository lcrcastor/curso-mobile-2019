package ar.com.santander.rio.mbanking.services.soap.beans.body;

import com.google.gson.annotations.SerializedName;

public class DatosTransferenciaBean {
    @SerializedName("altaDestinatario")
    private String altaDestinatario;
    @SerializedName("importe")
    private String importe;
    @SerializedName("moneda")
    private String moneda;
    @SerializedName("tipo")
    private String tipo;

    public DatosTransferenciaBean(String str, String str2, String str3, String str4) {
        this.tipo = str;
        this.importe = str2;
        this.moneda = str3;
        this.altaDestinatario = str4;
    }

    public String getTipo() {
        return this.tipo;
    }

    public String getImporte() {
        return this.importe;
    }

    public String getMoneda() {
        return this.moneda;
    }

    public String getAltaDestinatario() {
        return this.altaDestinatario;
    }
}
