package ar.com.santander.rio.mbanking.services.soap.beans.body;

import com.google.gson.annotations.SerializedName;

public class SuscribirFondoBodyRequestBean {
    @SerializedName("aceptaRiesgo")
    private String aceptaRiesgo;
    @SerializedName("cotizaCambio")
    private String cotizaCambio;
    @SerializedName("idEvaluacionRiesgo")
    private String idEvaluacionRiesgo;
    @SerializedName("idFondo")
    private String idFondo;
    @SerializedName("importeSusc")
    private String importeSusc;
    @SerializedName("moneda")
    private String moneda;
    @SerializedName("nroCtaOrigen")
    private String nroCtaOrigen;
    @SerializedName("nroCtaTitulo")
    private String nroCtaTitulo;
    @SerializedName("porcentComision")
    private String porcentComision;
    @SerializedName("sucCtaOrigen")
    private String sucCtaOrigen;
    @SerializedName("sucCtaTitulo")
    private String sucCtaTitulo;
    @SerializedName("tipoCtaOrigen")
    private String tipoCtaOrigen;
    @SerializedName("tipoCtaTitulo")
    private String tipoCtaTitulo;

    public SuscribirFondoBodyRequestBean(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10, String str11, String str12, String str13) {
        this.tipoCtaTitulo = str;
        this.sucCtaTitulo = str2;
        this.nroCtaTitulo = str3;
        this.idFondo = str4;
        this.importeSusc = str5;
        this.moneda = str6;
        this.tipoCtaOrigen = str7;
        this.sucCtaOrigen = str8;
        this.nroCtaOrigen = str9;
        this.porcentComision = str10;
        this.cotizaCambio = str11;
        this.aceptaRiesgo = str12;
        this.idEvaluacionRiesgo = str13;
    }

    public SuscribirFondoBodyRequestBean(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9) {
        this.tipoCtaTitulo = str;
        this.sucCtaTitulo = str2;
        this.nroCtaTitulo = str3;
        this.idFondo = str4;
        this.importeSusc = str5;
        this.moneda = str6;
        this.tipoCtaOrigen = str7;
        this.sucCtaOrigen = str8;
        this.nroCtaOrigen = str9;
    }
}
