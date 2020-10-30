package ar.com.santander.rio.mbanking.services.soap.beans.body;

import com.google.gson.annotations.SerializedName;

public class CnsTenenciasDatosPFBean {
    @SerializedName("accionAlVto")
    public String accionAlVto;
    @SerializedName("canalApertura")
    public String canalApertura;
    @SerializedName("capital")
    public String capital;
    @SerializedName("capitalUVI")
    public String capitalUVI;
    @SerializedName("certificado")
    public String certificado;
    @SerializedName("divisa")
    public String divisa;
    @SerializedName("fechaDeAlta")
    public String fechaDeAlta;
    @SerializedName("fechaProxVencimiento")
    public String fechaProxVencimiento;
    @SerializedName("impuestos")
    public String impuestos;
    @SerializedName("infoAdicional")
    public String infoAdicional;
    @SerializedName("intereses")
    public String intereses;
    @SerializedName("nroCuenta")
    public String nroCuenta;
    @SerializedName("subproducto")
    public String subproducto;
    @SerializedName("sucursalDeRadicacion")
    public String sucursalDeRadicacion;
    @SerializedName("tasaTNA")
    public String tasaTNA;
    @SerializedName("tituloDetalle")
    public String tituloDetalle;
    @SerializedName("tituloResumen")
    public String tituloResumen;
    @SerializedName("totalACobrar")
    public String totalACobrar;
    @SerializedName("valorUVA")
    public String valorUVA;

    public CnsTenenciasDatosPFBean() {
    }

    public CnsTenenciasDatosPFBean(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10, String str11, String str12, String str13, String str14, String str15, String str16, String str17, String str18, String str19) {
        this.divisa = str;
        this.fechaProxVencimiento = str2;
        this.tasaTNA = str3;
        this.capital = str4;
        this.capitalUVI = str5;
        this.totalACobrar = str6;
        this.tituloDetalle = str7;
        this.intereses = str8;
        this.impuestos = str9;
        this.fechaDeAlta = str10;
        this.certificado = str11;
        this.sucursalDeRadicacion = str12;
        this.subproducto = str13;
        this.canalApertura = str14;
        this.accionAlVto = str15;
        this.tituloResumen = str16;
        this.valorUVA = str17;
        this.infoAdicional = str18;
        this.nroCuenta = str19;
    }
}
