package ar.com.santander.rio.mbanking.services.model.general;

import com.google.gson.annotations.SerializedName;

public class DatosConsumos {
    @SerializedName("codTipoTarjeta")
    private String codTipoTarjeta;
    @SerializedName("documento")
    private String documento;
    @SerializedName("habiente")
    private String habiente;
    @SerializedName("producto")
    private String producto;
    @SerializedName("tarjetaActiva")
    private String tarjetaActiva;
    @SerializedName("tipoDocumento")
    private String tipoDocuento;
    @SerializedName("titular")
    private String titular;

    public DatosConsumos(String str, String str2, String str3, String str4, String str5, String str6, String str7) {
        this.codTipoTarjeta = str;
        this.habiente = str2;
        this.documento = str3;
        this.producto = str4;
        this.tarjetaActiva = str5;
        this.tipoDocuento = str6;
        this.titular = str7;
    }

    public DatosConsumos() {
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

    public String getDocumento() {
        return this.documento;
    }

    public void setDocumento(String str) {
        this.documento = str;
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

    public String getTipoDocuento() {
        return this.tipoDocuento;
    }

    public void setTipoDocuento(String str) {
        this.tipoDocuento = str;
    }

    public String getTitular() {
        return this.titular;
    }

    public void setTitular(String str) {
        this.titular = str;
    }
}
