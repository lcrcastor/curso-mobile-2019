package ar.com.santander.rio.mbanking.services.soap.beans.body;

import com.google.gson.annotations.SerializedName;

public class DatosCuentasDestinoBSRBean {
    @SerializedName("alias")
    private String alias;
    @SerializedName("cbuDestino")
    private String cbuDestino;
    @SerializedName("codConcepto")
    private String codConcepto;
    @SerializedName("cuentaPropia")
    private String cuentaPropia;
    @SerializedName("email")
    private String email;
    @SerializedName("nombre")
    private String nombre;
    @SerializedName("numero")
    private String numero;
    @SerializedName("refConcepto")
    private String refConcepto;
    @SerializedName("sucursal")
    private String sucursal;
    @SerializedName("tipo")
    private String tipo;
    @SerializedName("titular")
    private String titular;

    public DatosCuentasDestinoBSRBean(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10, String str11) {
        this.tipo = str;
        this.titular = str2;
        this.sucursal = str3;
        this.numero = str4;
        this.nombre = str5;
        this.cuentaPropia = str6;
        this.codConcepto = str7;
        this.refConcepto = str8;
        this.email = str9;
        this.cbuDestino = str10;
        this.alias = str11;
    }

    public String getTipo() {
        return this.tipo;
    }

    public String getTitular() {
        return this.titular;
    }

    public String getSucursal() {
        return this.sucursal;
    }

    public String getNumero() {
        return this.numero;
    }

    public String getNombre() {
        return this.nombre;
    }

    public String getCuentaPropia() {
        return this.cuentaPropia;
    }

    public String getCodConcepto() {
        return this.codConcepto;
    }

    public String getRefConcepto() {
        return this.refConcepto;
    }

    public String getEmail() {
        return this.email;
    }

    public String getCbuDestino() {
        return this.cbuDestino;
    }

    public String getAlias() {
        return this.alias;
    }
}
