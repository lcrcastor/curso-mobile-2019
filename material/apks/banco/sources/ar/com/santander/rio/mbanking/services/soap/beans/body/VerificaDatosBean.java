package ar.com.santander.rio.mbanking.services.soap.beans.body;

import com.google.gson.annotations.SerializedName;

public class VerificaDatosBean {
    @SerializedName("alias")
    private String alias;
    @SerializedName("dni")
    private String dni;
    @SerializedName("fechaNac")
    private String fechaNac;
    @SerializedName("numero")
    private String numero;
    @SerializedName("sucursal")
    private String sucursal;
    @SerializedName("tipo")
    private String tipo;
    @SerializedName("tipoDestino")
    private String tipoDestino;

    public VerificaDatosBean(String str, String str2, String str3, String str4, String str5, String str6, String str7) {
        this.tipoDestino = str;
        this.tipo = str2;
        this.sucursal = str3;
        this.numero = str4;
        this.dni = str5;
        this.fechaNac = str6;
        this.alias = str7;
    }

    public String getTipoDestino() {
        return this.tipoDestino;
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

    public String getDni() {
        return this.dni;
    }

    public String getFechaNac() {
        return this.fechaNac;
    }

    public String getAlias() {
        return this.alias;
    }
}
