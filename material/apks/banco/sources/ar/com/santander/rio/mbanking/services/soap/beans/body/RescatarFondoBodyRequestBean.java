package ar.com.santander.rio.mbanking.services.soap.beans.body;

import com.google.gson.annotations.SerializedName;

public class RescatarFondoBodyRequestBean {
    @SerializedName("comision")
    private String comision;
    @SerializedName("idFondo")
    private String idFondo;
    @SerializedName("importeNeto")
    private String importeNeto;
    @SerializedName("importeRescate")
    private String importeRescate;
    @SerializedName("moneda")
    private String moneda;
    @SerializedName("nroCtaTitulo")
    private String nroCtaTitulo;
    @SerializedName("nroCuentaDestino")
    private String nroCuentaDestino;
    @SerializedName("sucCtaDestino")
    private String sucCtaDestino;
    @SerializedName("sucCuentaTitulo")
    private String sucCuentaTitulo;
    @SerializedName("tipoCtaDestino")
    private String tipoCtaDestino;
    @SerializedName("tipoCuentaTitulo")
    private String tipoCuentaTitulo;

    public RescatarFondoBodyRequestBean(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10, String str11) {
        this.tipoCuentaTitulo = str;
        this.sucCuentaTitulo = str2;
        this.nroCtaTitulo = str3;
        this.idFondo = str4;
        this.moneda = str5;
        this.importeRescate = str6;
        this.tipoCtaDestino = str7;
        this.sucCtaDestino = str8;
        this.nroCuentaDestino = str9;
        this.comision = str10;
        this.importeNeto = str11;
    }
}
