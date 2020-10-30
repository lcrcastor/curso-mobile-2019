package ar.com.santander.rio.mbanking.services.model.general;

import com.google.gson.annotations.SerializedName;

public class DatosTarjeta {
    @SerializedName("categoria")
    private String categoria;
    @SerializedName("codTipoTarjeta")
    private String codTipoTarjeta;
    @SerializedName("habiente")
    private String habiente;
    @SerializedName("producto")
    private String producto;
    @SerializedName("tarjetaActiva")
    private String tarjetaActiva;
    @SerializedName("tipoTarjetaDetalle")
    private String tipoTarjetaDetalle;
    @SerializedName("titular")
    private String titular;

    public DatosTarjeta(String str, String str2, String str3, String str4, String str5, String str6, String str7) {
        this.categoria = str;
        this.codTipoTarjeta = str2;
        this.habiente = str3;
        this.titular = str4;
        this.producto = str5;
        this.tarjetaActiva = str6;
        this.tipoTarjetaDetalle = str7;
    }

    public DatosTarjeta() {
    }

    public String getCategoria() {
        return this.categoria;
    }

    public void setCategoria(String str) {
        this.categoria = str;
    }

    public String getCodTipoTarjeta() {
        return this.codTipoTarjeta;
    }

    public void setCodTipoTarjeta(String str) {
        this.codTipoTarjeta = str;
    }

    public String getHabiente() {
        return this.habiente;
    }

    public void setHabiente(String str) {
        this.habiente = str;
    }

    public String getTitular() {
        return this.titular;
    }

    public void setTitular(String str) {
        this.titular = str;
    }

    public String getProducto() {
        return this.producto;
    }

    public void setProducto(String str) {
        this.producto = str;
    }

    public String getTarjetaActiva() {
        return this.tarjetaActiva;
    }

    public void setTarjetaActiva(String str) {
        this.tarjetaActiva = str;
    }

    public String getTipoTarjetaDetalle() {
        return this.tipoTarjetaDetalle;
    }

    public void setTipoTarjetaDetalle(String str) {
        this.tipoTarjetaDetalle = str;
    }
}
