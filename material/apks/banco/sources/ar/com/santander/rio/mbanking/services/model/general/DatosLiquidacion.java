package ar.com.santander.rio.mbanking.services.model.general;

import com.google.gson.annotations.SerializedName;

public class DatosLiquidacion {
    @SerializedName("affinityGroup")
    private String affinityGroup;
    @SerializedName("categoria")
    private String categoria;
    @SerializedName("codigoSucursal")
    private String codigoSucursal;
    @SerializedName("cuenta")
    private String cuenta;
    @SerializedName("documento")
    private String documento;
    @SerializedName("habiente")
    private String habiente;
    @SerializedName("producto")
    private String producto;
    @SerializedName("tarjetaActiva")
    private String tarjetaActiva;
    @SerializedName("tipoDocumentoCodigo")
    private String tipoDocumentoCodigo;
    @SerializedName("tipoTarjetaDetalle")
    private String tipoTarjetaDetalle;
    @SerializedName("titular")
    private String titular;
    @SerializedName("vencimiento")
    private String vencimiento;

    public DatosLiquidacion(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10, String str11, String str12) {
        this.affinityGroup = str;
        this.categoria = str2;
        this.codigoSucursal = str3;
        this.cuenta = str4;
        this.habiente = str5;
        this.documento = str6;
        this.producto = str7;
        this.tarjetaActiva = str8;
        this.tipoDocumentoCodigo = str9;
        this.tipoTarjetaDetalle = str10;
        this.titular = str11;
        this.vencimiento = str12;
    }

    public DatosLiquidacion() {
    }

    public String getAffinityGroup() {
        return this.affinityGroup;
    }

    public void setAffinityGroup(String str) {
        this.affinityGroup = str;
    }

    public String getCategoria() {
        return this.categoria;
    }

    public void setCategoria(String str) {
        this.categoria = str;
    }

    public String getCodigoSucursal() {
        return this.codigoSucursal;
    }

    public void setCodigoSucursal(String str) {
        this.codigoSucursal = str;
    }

    public String getCuenta() {
        return this.cuenta;
    }

    public void setCuenta(String str) {
        this.cuenta = str;
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

    public String getTipoDocumentoCodigo() {
        return this.tipoDocumentoCodigo;
    }

    public void setTipoDocumentoCodigo(String str) {
        this.tipoDocumentoCodigo = str;
    }

    public String getTipoTarjetaDetalle() {
        return this.tipoTarjetaDetalle;
    }

    public void setTipoTarjetaDetalle(String str) {
        this.tipoTarjetaDetalle = str;
    }

    public String getTitular() {
        return this.titular;
    }

    public void setTitular(String str) {
        this.titular = str;
    }

    public String getVencimiento() {
        return this.vencimiento;
    }

    public void setVencimiento(String str) {
        this.vencimiento = str;
    }
}
